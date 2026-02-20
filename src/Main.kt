import kotlinx.datetime.*

enum class Modalidade{PRESENCIAL, REMOTO, HIBRIDO}
enum class TipoUsuario{COMUM, ORGANIZADOR}
enum class TipoEventos{SHOW, PALESTRA, CURSO, SOCIAL,CORPORATIVO,RELIGIOSO,ESPORTIVO, AULA, TREINAMENTO, SEMINARIO}
enum class Sexo{MASCULINO, FEMININO, OUTRO}

data class Usuario(
    val nome:String,
    val data:String,
    val sexo:Sexo,
    val email:String,
    val senha:String,
    val tipo:TipoUsuario,
    val ativo:Boolean = true,
    val cnpj:String?=null,
    val razaoSocial:String?=null,
    val nomeFantasia:String?=null
)

data class Evento(
    val nome:String,
    val descricao:String,
    val pagina:String,
    val dataInicio:String,
    val dataFim:String,
    val tipo:TipoEventos,
    val ligadoPrincipal:String?,
    val modalidade:Modalidade,
    val capacidadeMax:Int,
    val local:String,
    val ativo:Boolean = false,
    val preco:Double,
    val temEstorno:Boolean,
    val taxaEstorno:Double,
    var emailOrganizador:String
)

data class Ingresso(
    val emailUsuario: String,
    val nomeEvento:String,
    val valorPago:Double,
    val cancelado:Boolean = false
)

fun main() {
    val usuarios = mutableListOf<Usuario>()
    val eventos = mutableListOf<Evento>()
    val ingressos = mutableListOf<Ingresso>()

    var usuarioLogado: Usuario? = null

    var opcao: Int

    do {
        println("\n=== Dende Eventos ===")

        when (usuarioLogado){
            null -> {
                println("1 - Cadastrar Usuário")
                println("2 - Cadastrar Organizador")
                println("3 - Fazer Login")
                println("7 - Reativar Conta")
                println("11 - Sair")
            }
            else -> {
                println("Olá ${usuarioLogado.nome}! (Perfil: ${usuarioLogado.tipo}")
                println("4 - Ver Perfil ")
                println("5 - Alterar Perfil")
                println("6 - Inativar Conta")

                when (usuarioLogado.tipo) {
                    TipoUsuario.ORGANIZADOR -> {
                        println("8 - Cadastrar Eventos")
                        println("12 - Meus Eventos")
                    }
                    TipoUsuario.COMUM -> {
                        println("9 - Comprar Ingresso")
                        println("13 - Feed de Eventos")
                        println("14 - Meus Ingressos")
                    }
                }

                println("10 - Logout")
                println("11 - Sair")
            }
        }

        println("\nEscolha uma opção: ")

        opcao = readLine()?.toIntOrNull() ?: 0

        when (opcao) {

            //DEIVID
            1 -> {
                println("=== Preencha as informações do usuário ===")
                println("Nome:")
                val nome = readLine()!!


                println("Data de Nascimento (dd/mm/yyyy):")
                val dataNascimento = readLine()!!
                var dataValida = true

                if (dataNascimento.length != 10){
                    dataValida = false
                }

                if (dataNascimento[2] != '/' || dataNascimento[5] != '/'){
                    dataValida = false
                }

                if (dataValida){
                    val dia = dataNascimento.substring(0, 2).toIntOrNull()
                    val mes = dataNascimento.substring(3, 5).toIntOrNull()
                    val ano = dataNascimento.substring(6, 10).toIntOrNull()

                    if (dia == null || mes == null || ano == null){
                        dataValida = false
                    } else {

                        if (mes !in 1..12){
                            dataValida = false
                        }

                        if (dia !in 1..31){
                            dataValida = false
                        }

                        if (mes == 2 && dia > 29){
                            dataValida = false
                        }

                        if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 30){
                            dataValida = false
                        }
                    }
                }

                if (!dataValida){
                    println("Data inválida")
                    return
                }


                println("Sexo (Masculino, Feminino, Outro):")
                val sexoInput = readLine()!!.uppercase()
                val sexo = try {
                    Sexo.valueOf(sexoInput)
                } catch (e: Exception) {
                    println("Sexo inválido!")
                    return
                }


                println("Email:")
                val email = readLine()!!
                var emailValido = true

                if(email.contains(" ")){
                    emailValido = false
                }

                if (email.count{it == '@'} != 1){
                    emailValido = false
                }

                if(email.startsWith("@") || email.endsWith("@")){
                    emailValido = false
                }

                if(emailValido){

                    val posicaoArroba = email.indexOf('@')

                    val parteDepoisDoArroba = email.substring(posicaoArroba + 1)

                    if (!parteDepoisDoArroba.contains('.')){
                        emailValido = false
                    }

                    if (email.endsWith(".")){
                        emailValido = false
                    }
                }

                if(!emailValido){
                    println("Email inválido")
                    return
                }


                println("Senha:")
                val senha = readLine()!!

                val emailExistente = usuarios.any { it.email.equals(email, true)}

                if (emailExistente) {
                    println("Email já utilizado, tente outro!")
                } else {
                    val usuario = Usuario(nome = nome, data = dataNascimento , sexo = sexo, email = email, senha = senha, tipo = TipoUsuario.COMUM, ativo = true, cnpj = null, razaoSocial = null, nomeFantasia = null)
                    usuarios.add(usuario)
                    println("Usuário cadastrado com sucesso!")
                }

            }

            //DEIVID
            2 -> {
                println("=== Preencha as informações do organizador ===")
                println("Nome:")
                val nome = readLine()!!


                println("Data de Nascimento (dd/mm/yyyy:")
                val dataNascimento = readLine()!!
                var dataValida = true

                if (dataNascimento.length != 10){
                    dataValida = false
                }

                if (dataNascimento[2] != '/' || dataNascimento[5] != '/'){
                    dataValida = false
                }

                if (dataValida){
                    val dia = dataNascimento.substring(0, 2).toIntOrNull()
                    val mes = dataNascimento.substring(3, 5).toIntOrNull()
                    val ano = dataNascimento.substring(6, 10).toIntOrNull()

                    if (dia == null || mes == null || ano == null){
                        dataValida = false
                    } else {

                        if (mes !in 1..12){
                            dataValida = false
                        }

                        if (dia !in 1..31){
                            dataValida = false
                        }

                        if (mes == 2 && dia > 29){
                            dataValida = false
                        }

                        if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 30){
                            dataValida = false
                        }
                    }
                }

                if (!dataValida){
                    println("Data inválida")
                    return
                }


                println("Sexo (Masculino, Feminino, Outro):")
                val sexoInput = readLine()!!.uppercase()
                val sexo = try {
                    Sexo.valueOf(sexoInput)
                } catch (e: Exception) {
                    println("Sexo inválido!")
                    return
                }


                println("Email:")
                val email = readLine()!!
                var emailValido = true

                if(email.contains(" ")){
                    emailValido = false
                }

                if (email.count{it == '@'} != 1){
                    emailValido = false
                }

                if(email.startsWith("@") || email.endsWith("@")){
                    emailValido = false
                }

                if(emailValido){

                    val posicaoArroba = email.indexOf('@')

                    val parteDepoisDoArroba = email.substring(posicaoArroba + 1)

                    if (!parteDepoisDoArroba.contains('.')){
                        emailValido = false
                    }

                    if (email.endsWith(".")){
                        emailValido = false
                    }
                }

                if(!emailValido){
                    println("Email inválido")
                    return
                }


                println("Senha:")


                val senha = readLine()!!
                println("Voce é uma empresa? Responda com SIM ou NAO")


                val empresa = readLine()!!

                val cnpj = null
                val razaoSocial = null
                val nomeFantasia = null

                if (empresa.equals("SIM")){
                    println("CNPJ:")
                    val cnpj = readLine()!!
                    println("Razao Social")
                    val razaoSocial = readLine()!!
                    println("Nome fantasia:")
                    val nomeFantasia = readLine()!!
                }

                val emailExistente = usuarios.any { it.email.equals(email, true)}

                if (emailExistente) {
                    println("Email já utilizado, tente outro!")
                } else {
                    val usuario = Usuario(nome = nome, data = dataNascimento , sexo = sexo, email = email, senha = senha, tipo = TipoUsuario.ORGANIZADOR, ativo = true, cnpj = cnpj, razaoSocial = razaoSocial, nomeFantasia = nomeFantasia)
                    usuarios.add(usuario)
                    println("Usuário cadastrado com sucesso!")
                }

            }

            3 -> {}//DEIVID

            //LEONARDO
            4 -> run {
                val usuario = usuarioLogado
                when (usuario){
                    null -> println("Você precisa estar logado para ver o perfil.")
                    else -> {
                        println("\n === Meu Perfil ===")
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

                        when (usuario.tipo) {
                            TipoUsuario.ORGANIZADOR -> {
                                when (usuario.cnpj){
                                    null -> println("Atuando como pessoa Física.")
                                    else -> {
                                        println("CNPJ: ${usuario.cnpj}")
                                        println("Razão Social ${usuario.razaoSocial} ")
                                        println("Nome Fantasia: ${usuario.nomeFantasia}")
                                    }
                                }
                            }
                            TipoUsuario.COMUM -> {}
                        }
                    }
                }
            }

            //LEONARDO
            5 -> {
                val usuario = usuarioLogado
                when (usuario) {
                    null -> println("Você precisa estar logado para alterar o perfil.")
                    else -> {
                        println("\n=== Alterar dados ===")

                        var novoNome = usuario.nome
                        var novoSexo = usuario.sexo
                        var novaSenha = usuario.senha

                        println("Nome atual: ${usuario.nome}")
                        println("Deseja trocar? (SIM ou NAO)")
                        when (readln()?.uppercase()){
                            "SIM" -> {
                                println("Novo nome: ")
                                novoNome = readln() ?: usuario.nome
                            }
                        }

                        println("Sexo Atual: ${usuario.sexo}")
                        println("Deseja trocar? (SIM ou NAO)")
                        when (readln()?.uppercase()){
                            "SIM" -> {
                                println("Novo sexo (MASCULINO, FEMININO , OUTRO):")
                                val sexoInput = readln()?.uppercase() ?: ""
                                val sexoConvertido = try {
                                    Sexo.valueOf(sexoInput)
                                } catch (e: Exception){
                                    null
                                }

                                when (sexoConvertido) {
                                    null -> println("Sexo inválido. Mantendo o atual.")
                                    else -> novoSexo = sexoConvertido
                                }
                            }
                        }

                        println("Deseja trocas a senha? (SIM ou NAO)")
                        when (readln()?.uppercase()) {
                            "SIM" -> {
                                println("Nova senha:")
                                novaSenha = readln() ?: usuario.senha
                            }
                        }

                        val usuarioAtualizado = usuario.copy(
                            nome = novoNome,
                            sexo = novoSexo,
                            senha = novaSenha
                        )

                        usuarios.remove(usuario)
                        usuarios.add(usuarioAtualizado)
                        usuarioLogado = usuarioAtualizado

                        println("Perfil atualizado com sucesso!")
                    }
                }
            }
            //LEONARDO
            6 -> run{
                val usuario = usuarioLogado
                when (usuario) {
                    null -> println("Você precisa estar logado para inativar a conta")
                    else -> {
                        println("\n=== Inativar Conta ===")
                        println("Tem certeza que deseja inativar sua conta? (SIM / NAO)")

                        when (readln()?.uppercase()){
                            "SIM" ->{
                                when (usuario.tipo){
                                    TipoUsuario.COMUM -> {
                                        val inativado = usuario.copy(ativo = false)
                                        usuarios.remove(usuario)
                                        usuarios.add(inativado)
                                        usuarioLogado = null
                                        println("Conta inativada com sucesso!")
                                    }
                                    TipoUsuario.ORGANIZADOR -> {
                                        val temEventoAtivo = eventos.any {
                                            it.emailOrganizador == usuario.email && it.ativo
                                        }

                                        when (temEventoAtivo) {
                                            true -> println("ERRO: Você possui eventos ativos! Desative seus eventos antes de inativar a conta.")
                                            false -> {
                                                val  inativado = usuario.copy(ativo = false)
                                                usuarios.remove(usuario)
                                                usuarios.add(inativado)
                                                usuarioLogado = null
                                                println("Conta de organizador inativada com sucesso.")
                                            }
                                        }
                                    }
                                }
                            }
                            else -> println("Operação cancelada.")
                        }
                    }
                }
            }

            //LEONARDO
            7 -> run {
                when (usuarioLogado) {
                    null -> {
                        println("\n=== Reativar Conta ===")
                        println("Email:")
                        val email = readln() ?: ""
                        println("Senha:")
                        val senha = readln() ?: ""

                        val usuarioEncontrado = usuarios.find { it.email.equals(email, ignoreCase = true) && it.senha == senha }

                        when (usuarioEncontrado) {
                            null -> println("Credenciais incorretas ou usuário não encontrado.")
                            else -> {
                                when (usuarioEncontrado.ativo){
                                    true -> println("Esta conta já está ativa!")
                                    false -> {
                                        val reativado = usuarioEncontrado.copy(ativo = true)
                                        usuarios.remove(usuarioEncontrado)
                                        usuarios.add(reativado)
                                        println("Conta reativada com sucesso! Volte ao menu para fazer Login.")
                                    }
                                }
                            }
                        }
                    }
                    else -> println("Você já está logado. Saia primeiro para reativar uma conta inativa.")
                }
            }

            8 -> {}//DYLAN
            9 -> {}//CHAIRA
            10 -> {}//DEIVID
            11 -> {break}//DEIVID
            12 -> {}//DYLAN
            13 -> {}//CHAIRA
            14 -> {}//CHAIRA
        }
    } while (opcao != 11)
}

