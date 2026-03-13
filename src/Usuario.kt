import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.periodUntil
import kotlinx.datetime.todayIn

fun cadastrarUsuarioComum(){

}

fun cadastrarOrganizador(){

}

fun fazerLogin(){

}

// Recebe o usuário logado como parâmetro para evitar o uso de variáveis globais
fun verPerfil(usuario: Usuario) {
    println("\n=== Meu Perfil ===")
    println("Nome: ${usuario.nome}")
    println("Email: ${usuario.email}")
    println("Sexo: ${usuario.sexo}")
    println("Tipo da Conta: ${usuario.tipo}")

    // Extrai os dados da String "dd/mm/yyyy" criando uma lista de 3 posições
    val partes = usuario.data.split("/")
    val diaNasc = partes.getOrNull(0)?.toIntOrNull() ?: 1
    val mesNasc = partes.getOrNull(1)?.toIntOrNull() ?: 1
    val anoNasc = partes.getOrNull(2)?.toIntOrNull() ?: 2000

    // Converte para LocalDate para usar a matemática de datas nativa do Kotlin
    val dataNascimento = LocalDate(anoNasc, mesNasc, diaNasc)
    val hoje = Clock.System.todayIn(TimeZone.currentSystemDefault())

    // Calcula a diferença exata entre o nascimento e o dia de hoje
    val periodo = dataNascimento.periodUntil(hoje)

    println("Data de Nascimento: ${usuario.data} (${periodo.years} anos, ${periodo.months} meses, ${periodo.days} dias)")

    // Regra de Negócio: Apenas organizadores exibem informações de empresa
    if (usuario.tipo == TipoUsuario.ORGANIZADOR) {
        if (usuario.cnpj == null) {
            println("Atuando como pessoa Física.")
        } else {
            println("CNPJ: ${usuario.cnpj}")
            println("Razão Social: ${usuario.razaoSocial}")
            println("Nome Fantasia: ${usuario.nomeFantasia}")
        }
    }
}

//Retorna um usuário para poder atualizar a sessão do utilizador logado
fun alterarPerfil(usuario: Usuario): Usuario{
    println("\n=== Alterar dados ===")
    println("Nome atual: ${usuario.nome}")

    //Utiliza o componente seguro para ler a string e forçar uma resposta não vazia
    val mudarNome = readString("Deseja trocar o nome? (SIM ou NAO): ", "Digite SIM ou NAO", 3).uppercase()
    val novoNome = if (mudarNome == "SIM") readString("Novo nome: ", "O nome não pode ser vazio",2) else usuario.nome

    println("Sexo atual: ${usuario.sexo}")
    val mudarSexo = readString("Deseja trocar o sexo? (SIM ou NAO): ", "Digite SIM ou NAO", 3).uppercase()
    var novoSexo = usuario.sexo
    if (mudarSexo == "SIM") {
        val sexoInput = readString("Novo sexo (MASCULINO, FEMININO, OUTRO): ", "Valor inválido",5).uppercase()
        try {
            // Tenta converter o texto digitado diretamente para o Enum correspondente
            novoSexo = Sexo.valueOf(sexoInput)
        } catch (e: Exception) {
            println("Sexo inválido. Mantendo o atual")
        }
    }

    println("Senha atual: oculta por segurança")
    val mudarSenha = readString("Deseja trocar a senha? (SIM ou NAO): ","Digite SIM ou NAO: ", 3).uppercase()
    val novaSenha = if (mudarSenha == "SIM") readString("Nova senha: ", "A senha não pode ser vazia", 4) else usuario.senha

    //Aplica o conceito de IMUTABILIDADE: Cria um clone do usuário apenas com os campos novos
    val usuarioAtualizado = usuario.copy(
        nome = novoNome,
        sexo = novoSexo,
        senha = novaSenha
    )

    //Atualiza os dados usuário
    atualizarUsuario(usuario, usuarioAtualizado)
    println("Perfil atualizado com sucesso!")

    return usuarioAtualizado
}

fun inativarConta(usuario: Usuario): Usuario?{
    println("\n=== Inativar Conta ===")
    val confirmacao = readString("Tem certeza que deseja intivar sua conta? (SIM / NAO): ", "Digite SIM ou NAO",3).uppercase()

    if (confirmacao == "SIM") {
        //Organizador não pode inativar sua conta se tiver eventos ativos
        if (usuario.tipo == TipoUsuario.ORGANIZADOR) {
            if (organizadorTemEventoAtivo(usuario.email)) {
                println("ERRO: Você possui eventos ativos! Desative seus eventos antes de inativar a conta")
                return usuario
            }
        }

        //Altera o status do usuário para inativo e desloga ele
        val inativado = usuario.copy(ativo = false)
        atualizarUsuario(usuario, inativado)
        println("Conta inativada com sucesso! Você foi desconectado.")
        return null
    }
    println("Operação cancelada.")
    return usuario
}

fun reativarConta() {
    println("\n=== Reativar Conta ===")
    val email = readString("Email: ", "O email não pode ser vazio",5)
    val senha = readString("Senha: ", "A senha não pode ser vazia",4)

    //Busca credenciais do usuário
    val  usuarioEncontrado = buscarUsuarioLogin(email, senha)

    if (usuarioEncontrado == null){
        println("Credenciais incorreas ou usuário não encontrado.")
    } else{
        if (usuarioEncontrado.ativo) {
            //Impede o usuário atvio de reativar a conta
            println("Esta conta já está ativa!")
        } else {
            val reativado = usuarioEncontrado.copy(ativo = true)
            atualizarUsuario(usuarioEncontrado, reativado)
            println("Conta reativada com sucesso! Volte ao menu para fazer Login.")
        }
    }
}