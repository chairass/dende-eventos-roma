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

fun verPerfil(usuario: Usuario) {
    println("\n=== Meu Perfil ===")
    println("Nome: ${usuario.nome}")
    println("Email: ${usuario.email}")
    println("Sexo: ${usuario.sexo}")
    println("Tipo da Conta: ${usuario.tipo}")

    val partes = usuario.data.split("/")
    val diaNasc = partes.getOrNull(0)?.toIntOrNull() ?: 1
    val mesNasc = partes.getOrNull(1)?.toIntOrNull() ?: 1
    val anoNasc = partes.getOrNull(2)?.toIntOrNull() ?: 2000

    val dataNascimento = LocalDate(anoNasc, mesNasc, diaNasc)
    val hoje = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val periodo = dataNascimento.periodUntil(hoje)

    println("Data de Nascimento: ${usuario.data} (${periodo.years} anos, ${periodo.months} meses, ${periodo.days} dias)")

    if (usuario.tipo == TipoUsuario.ORGANIZADOR) {
        if (usuario.cnpj == null){
            println("Atuando como pessoa Física")
        }else {
            println("CNPJ: ${usuario.cnpj}")
            println("Razão Social: ${usuario.razaoSocial}")
            println("Nome Fantasia: ${usuario.nomeFantasia}")
        }
    }
}

fun alterarPerfil(usuario: Usuario): Usuario{
    println("\n=== Alterar dados ===")
    println("Nome atual: ${usuario.nome}")

    val mudarNome = readStrig("Deseja trocar o nome? (SIM ou NAO): ", "Digite SIM ou NAO", 3).uppercase()
    val novoNome = if (mudarNome == "SIM") readStrig("Novo nome: ", "O nome não pode ser vazio",2) else usuario.nome

    println("Sexo atual: ${usuario.sexo}")
    val mudarSexo = readStrig("Deseja trocar o sexo? (SIM ou NAO): ", "Digite SIM ou NAO", 3).uppercase()
    var novoSexo = usuario.sexo
    if (mudarSexo == "SIM") {
        val sexoInput = readStrig("Novo sexo (MASCULINO, FEMININO, OUTRO): ", "Valor inválido",5).uppercase()
        try {
            novoSexo = Sexo.valueOf(sexoInput)
        } catch (e: Exception) {
            println("Sexo inválido. Mantendo o atual")
        }
    }

    println("Senha atual: oculta por segurança")
    val mudarSenha = readStrig("Deseja trocar a senha? (SIM ou NAO): ","Digite SIM ou NAO: ", 3).uppercase()
    val novaSenha = if (mudarSenha == "SIM") readStrig("Nova senha: ", "A senha não pode ser vazia", 4) else usuario.senha

    val usuarioAtualizado = usuario.copy(
        nome = novoNome,
        sexo = novoSexo,
        senha = novaSenha
    )

    atualizarUsuario(usuario, usuarioAtualizado)
    println("Perfil atualizado com sucesso!")

    return usuarioAtualizado
}

fun inativarConta(usuario: Usuario): Usuario?{
    println("\n=== Inativar Conta ===")
    val confirmacao = readStrig("Tem certeza que deseja intivar sua conta? (SIM / NAO): ", "Digite SIM ou NAO",3).uppercase()

    if (confirmacao == "SIM") {
        if (usuario.tipo == TipoUsuario.ORGANIZADOR) {
            if (organizadorTemEventoAtivo(usuario.email)) {
                println("ERRO: Você possui eventos ativos! Desative seus eventos antes de inativar a conta")
                return usuario
            }
        }

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
    val email = readStrig("Email: ", "O email não pode ser vazio",5)
    val senha = readStrig("Senha: ", "A senha não pode ser vazia",4)

    val  usuarioEncontrado = buscarUsuarioLogin(email, senha)

    if (usuarioEncontrado == null){
        println("Credenciais incorreas ou usuário não encontrado.")
    } else{
        if (usuarioEncontrado.ativo) {
            println("Esta conta já está ativa!")
        } else {
            val reativado = usuarioEncontrado.copy(ativo = true)
            atualizarUsuario(usuarioEncontrado, reativado)
            println("Conta reativada com sucesso! Volte ao menu para fazer Login.")
        }
    }
}