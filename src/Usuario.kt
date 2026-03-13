import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.periodUntil
import kotlinx.datetime.todayIn

fun cadastrarUsuarioComum(){

    println("=== Preencha as informações do usuário ===")

    //Le o nome utilizando o componente de validaçao de String
    val nome = readString("Nome", "Nome inválido", 1)

    //Solicita a data de nascimento no formato dd/mm/yyyy
    val dataInput = readString("Data de Nascimento (dd/mm/yyyy):", "Data inválida", 10)

    //Converte a string digitada para LocalDate
    val dataNascimento = try {

        //Divide a data em dia, mes e ano
        val (d, m, a) = dataInput.split("/").map { it.toInt() }

        //Cria um objeto LocalDate com os valores convertidos
        LocalDate(a, m, d)

    } catch (e: Exception) {
        //Se a conversao falhar, informa erro e cancela cadastro
        println("Data inválida")
        return
    }

    //Le o sexo digitado pelo usuario e converte para letras maiusculas
    val sexoInput = readString("Sexo (MASCULINO, FEMININO, OUTRO):",
        "Sexo inválido",
        1).uppercase()

    //Tenta converter o texto para o enum Sexo
    val sexo = try {
        Sexo.valueOf(sexoInput)
    } catch (e: Exception) {
        println("Sexo inválido!")
        return
    }


    //Solicita email
    val email = readString("Email:", "Email inválido", 4)

    //Variavel criada para controlar a validacao do email
    var emailValido = true

    //Primeira validacao do email
    when {
        email.contains(" ") -> emailValido = false
        email.count { it == '@' } != 1 -> emailValido = false
        email.startsWith("@") || email.endsWith("@") -> emailValido = false
    }

    //Segunda validacao
    if (emailValido) {

        val posicaoArroba = email.indexOf('@')

        val parteDepoisDoArroba = email.substring(posicaoArroba + 1)

        when {
            !parteDepoisDoArroba.contains('.') -> emailValido = false
            email.endsWith(".") -> emailValido = false
        }
    }

    //Caso nao seja valido, cancela o cadastro
    if (!emailValido) {
        println("Email inválido")
        return
    }

    //Pede senha do usuario
    val senha = readString("Senha:", "Senha inválida", 4)


    //Verifica se já existe usuario com o mesmo email
    if(Repositorio.buscarUsuarioEmail(email) != null) {
        println("Email já utilizado, tente outro!")
        return
    }


    // Cria o objeto Usuario com os dados informados
    val usuario = Usuario(
        nome = nome,
        data = dataNascimento,
        sexo = sexo,
        email = email,
        senha = senha,
        tipo = TipoUsuario.COMUM,
        ativo = true,
        cnpj = null,
        razaoSocial = null,
        nomeFantasia = null
    )

    //Adiciona usuario ao repositorio
    Repositorio.adicionarUsuario(usuario)
    println("Usuário cadastrado com sucesso!")
}

fun cadastrarOrganizador(){

    println("=== Preencha as informações do organizador ===")

    val nome = readString("Nome", "Nome inválido", 1)

    val dataInput = readString("Data de Nascimento (dd/mm/yyyy):", "Data inválida", 10)

    // Converte a data digitada para LocalDate
    val dataNascimento = try {
        val (d, m, a) = dataInput.split("/").map { it.toInt() }
        LocalDate(a, m, d)
    } catch (e: Exception) {
        println("Data inválida")
        return
    }

    // Lê o sexo do organizador
    val sexoInput = readString("Sexo (MASCULINO, FEMININO, OUTRO):",
        "Sexo inválido",
        1).uppercase()

    // Converte o texto para enum Sexo
    val sexo = try {
        Sexo.valueOf(sexoInput)
    } catch (e: Exception) {
        println("Sexo inválido!")
        return
    }


    // Lê o email
    val email = readString("Email:", "Email inválido", 4)

    //Controla validacao de email
    var emailValido = true

    //Primeira validacao
    when {
        email.contains(" ") -> emailValido = false
        email.count { it == '@' } != 1 -> emailValido = false
        email.startsWith("@") || email.endsWith("@") -> emailValido = false
    }

    //Segunda validacao
    if (emailValido) {

        val posicaoArroba = email.indexOf('@')

        val parteDepoisDoArroba = email.substring(posicaoArroba + 1)

        when {
            !parteDepoisDoArroba.contains('.') -> emailValido = false
            email.endsWith(".") -> emailValido = false
        }
    }

    if (!emailValido) {
        println("Email inválido")
        return
    }

    //Pede a senha ao usuario
    val senha = readString("Senha:", "Senha inválida", 4)

    //Pergunta se o organizador é uma empresa
    val empresa = readString("Você é uma empresa? Digite com SIM ou NAO", "Resposta inválida", 3)

    //Dados da empresa começam nulo
    var cnpj: String? = null
    var razaoSocial: String? = null
    var nomeFantasia: String? = null

    //Quando for empresa, solicita os dados empresariais
    when(empresa.uppercase()) {
        "SIM" -> {
            cnpj = readString("CNPJ:", "CNPJ inválido", 14)
            razaoSocial = readString("Razão Social:", "Razão social inválida", 5)
            nomeFantasia = readString("Nome fantasia:", "Nome inválido", 5)
        }
        "NAO" -> {}
        else -> {
            println("Resposta inválida")
            return
        }
    }


    //Verifica se email já existe
    if(Repositorio.buscarUsuarioEmail(email) != null) {
        println("Email já utilizado, tente outro!")
        return
    }


    //Cria objeto do tipo organizador
    val usuario = Usuario(
        nome = nome,
        data = dataNascimento,
        sexo = sexo,
        email = email,
        senha = senha,
        tipo = TipoUsuario.ORGANIZADOR,
        ativo = true,
        cnpj = cnpj,
        razaoSocial = razaoSocial,
        nomeFantasia = nomeFantasia
    )

    //Adiciona ao repositorio
    Repositorio.adicionarUsuario(usuario)
    println("Usuário cadastrado com sucesso!")

}

fun fazerLogin(){

    //Verifica se já existe um usuario logado
    if (usuarioLogado != null){
        println("Já existe um usuário logado.")
        return
    }

    //solicita as credenciais
    val email = readString("Email:", "Email inválido", 4)
    val senha = readString("Senha:", "Senha inválida", 4)

    //Busca usuario usando email e senha
    val usuario = buscarUsuarioLogin(email, senha)

    //Caso nao encontre o usuario
    if (usuario == null) {
        println("Credenciais incorretas ou conta inativa")
    }
    //Caso o usuario esteja inativo
    else if (!usuario.ativo) {
        println("Conta inativa")
    }
    //Login bem sucedido
    else {
        usuarioLogado = usuario
        println("Login realizado com sucesso!")
    }
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