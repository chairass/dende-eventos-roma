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
                println("Olá ${usuarioLogado.nome}! (Perfil: ${usuarioLogado.tipo})")
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

                if (dataNascimento.length != 10) {
                    dataValida = false
                }

                if (dataNascimento[2] != '/' || dataNascimento[5] != '/') {
                    dataValida = false
                }

                if (dataValida) {
                    val dia = dataNascimento.substring(0, 2).toIntOrNull()
                    val mes = dataNascimento.substring(3, 5).toIntOrNull()
                    val ano = dataNascimento.substring(6, 10).toIntOrNull()

                    if (dia == null || mes == null || ano == null) {
                        dataValida = false
                    } else {

                        if (mes !in 1..12) {
                            dataValida = false
                        }

                        if (dia !in 1..31) {
                            dataValida = false
                        }

                        if (mes == 2 && dia > 29) {
                            dataValida = false
                        }

                        if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 30) {
                            dataValida = false
                        }
                    }
                }

                if (!dataValida) {
                    println("Data inválida")
                    continue
                }


                println("Sexo (Masculino, Feminino, Outro):")
                val sexoInput = readLine()!!.uppercase()
                val sexo = try {
                    Sexo.valueOf(sexoInput)
                } catch (e: Exception) {
                    println("Sexo inválido!")
                    continue
                }


                println("Email:")
                val email = readLine()!!
                var emailValido = true

                when {
                    email.contains(" ") -> emailValido = false
                    email.count { it == '@' } != 1 -> emailValido = false
                    email.startsWith("@") || email.endsWith("@") -> emailValido = false
                }

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
                    continue
                }


                println("Senha:")
                val senha = readLine()!!

                val emailExistente = usuarios.any { it.email.equals(email, true) }

                when (emailExistente) {

                    true -> println("Email já utilizado, tente outro!")

                    false -> {
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
                        usuarios.add(usuario)
                        println("Usuário cadastrado com sucesso!")
                    }
                }
            }

            //DEIVID
            2 -> {
                println("=== Preencha as informações do organizador ===")
                println("Nome:")
                val nome = readLine()!!


                println("Data de Nascimento (dd/mm/yyyy:)")
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
                    continue
                }


                println("Sexo (Masculino, Feminino, Outro):")
                val sexoInput = readLine()!!.uppercase()
                val sexo = try {
                    Sexo.valueOf(sexoInput)
                } catch (e: Exception) {
                    println("Sexo inválido!")
                    continue
                }


                println("Email:")
                val email = readLine()!!
                var emailValido = true

                when {
                    email.contains(" ") -> emailValido = false
                    email.count { it == '@'} != 1 -> emailValido = false
                    email.startsWith("@") || email.endsWith("@") -> emailValido = false
                }

                if(emailValido){

                    val posicaoArroba = email.indexOf('@')

                    val parteDepoisDoArroba = email.substring(posicaoArroba + 1)

                    when {
                        !parteDepoisDoArroba.contains('.') -> emailValido = false
                        email.endsWith(".") -> emailValido = false
                    }
                }

                if(!emailValido){
                    println("Email inválido")
                    continue
                }


                println("Senha:")


                val senha = readLine()!!
                println("Voce é uma empresa? Responda com SIM ou NAO")


                val empresa = readLine()!!

                var cnpj: String? = null
                var razaoSocial: String? = null
                var nomeFantasia: String? = null

                when(empresa.uppercase()) {
                    "SIM" -> {
                        println("CNPJ:")
                        cnpj = readLine()!!
                        println("Razao Social")
                        razaoSocial = readLine()!!
                        println("Nome fantasia:")
                        nomeFantasia = readLine()!!
                    }
                }

                val emailExistente = usuarios.any { it.email.equals(email, true)}

                when (emailExistente) {
                    true -> println("Email já utilizado, tente outro!")

                    false -> {
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

                    usuarios.add(usuario)
                    println("Usuário cadastrado com sucesso!")
                    }
                }

            }

            3 -> {
                if (usuarioLogado != null){
                    println("Já existe um usuário logado.")
                    continue
                }

                print("Email:")
                val email = readln()!!

                print("Senha:")
                val senha = readLine()!!

                val usuario = usuarios.find { it.email.equals(email, true) && it.senha == senha && it.ativo}

                if (usuario == null) {
                    println("Credenciais incorretas ou conta inativa")
                } else {
                    usuarioLogado = usuario
                    println("Login realizado com sucesso!")
                }
            }//DEIVID

            // Ver Perfil
            4 -> run {
                val usuario = usuarioLogado
                when (usuario){
                    //Verifica se o usuario está logado
                    null -> println("Você precisa estar logado para ver o perfil.")
                    else -> {
                        println("\n === Meu Perfil ===")
                        println("Nome: ${usuario.nome}")
                        println("Email: ${usuario.email}")
                        println("Sexo: ${usuario.sexo}")
                        println("Tipo da Conta: ${usuario.tipo}")

                        //Fatia a data, pegando a string "dd/mm/yyyy" e quebra em três pedaços usando "/" como separador
                        val partes = usuario.data.split("/")
                        //getOrNull e toIntOrNull evita q o programa quebre se a data estiver mal formatada
                        val diaNasc = partes.getOrNull(0)?.toIntOrNull() ?: 1
                        val mesNasc = partes.getOrNull(1)?.toIntOrNull() ?: 1
                        val anoNasc = partes.getOrNull(2)?.toIntOrNull() ?: 2000

                        //Cria um objeto de data real a partir dos números acima
                        val dataNascimento = LocalDate(anoNasc, mesNasc, diaNasc)

                        //Pega a data de "hoje" lendo o relogio do sistema local
                        val hoje = Clock.System.todayIn(TimeZone.currentSystemDefault())

                        //Calcula a diferença entre as datas
                        val periodo = dataNascimento.periodUntil(hoje)

                        println("Data de Nascimento: ${usuario.data} (${periodo.years} anos, ${periodo.months} meses, ${periodo.days} dias)")

                        //Verifica o tipo do usuário. Se ele for comum, não faz nada
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

                        //Criar avriaveis temporárias que recebem os dados atuais
                        var novoNome = usuario.nome
                        var novoSexo = usuario.sexo
                        var novaSenha = usuario.senha

                        println("Nome atual: ${usuario.nome}")
                        println("Deseja trocar? (SIM ou NAO)")
                        //Lê a resposta, converte para maiúsculo
                        when (readln()?.uppercase()){
                            "SIM" -> {
                                println("Novo nome: ")
                                //Se o usuário apertar Enter sem digitar nada, o '?: usuario.nome' mantém o nome atual
                                novoNome = readln() ?: usuario.nome
                            }
                        }

                        println("Sexo Atual: ${usuario.sexo}")
                        println("Deseja trocar? (SIM ou NAO)")
                        //Lê a resposta, converte para maiúsculo
                        when (readln()?.uppercase()){
                            "SIM" -> {
                                println("Novo sexo (MASCULINO, FEMININO , OUTRO):")
                                val sexoInput = readln()?.uppercase() ?: ""
                                //Tenta converter a palavra digitada para o Enum sexo. O try/catch impede que feche o programa caso o usuario digite um valor n esperado
                                val sexoConvertido = try {
                                    Sexo.valueOf(sexoInput)
                                } catch (e: Exception){
                                    null //se der erro, ele guarda null
                                }

                                //Só atualiza a variável temporário se a conversão der certo
                                when (sexoConvertido) {
                                    null -> println("Sexo inválido. Mantendo o atual.")
                                    else -> novoSexo = sexoConvertido
                                }
                            }
                        }

                        println("Deseja trocas a senha? (SIM ou NAO)")
                        //Lê a resposta, converte para maiúsculo
                        when (readln()?.uppercase()) {
                            "SIM" -> {
                                println("Nova senha:")
                                //Se o usuário apertar Enter sem digitar nada, o '?: usuario.nome' mantém o nome atual
                                novaSenha = readln() ?: usuario.senha
                            }
                        }

                        //Usamos o método .copy() para criar um clone exato do usuário logado, e sustitui APENAS os campo dentro do parênteses
                        val usuarioAtualizado = usuario.copy(
                            nome = novoNome,
                            sexo = novoSexo,
                            senha = novaSenha
                        )

                        //Remove o antigo usuário da lista, adiciona o novo (atualizado) e diz q a sessão continua com ele
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
                //Checa o login e pede confirmação
                when (usuario) {
                    null -> println("Você precisa estar logado para inativar a conta")
                    else -> {
                        println("\n=== Inativar Conta ===")
                        println("Tem certeza que deseja inativar sua conta? (SIM / NAO)")


                        when (readln()?.uppercase()){
                            "SIM" ->{
                                when (usuario.tipo){
                                    //Se for comum, ele inativa, remove o antigo, adiciona o novo e desloga
                                    TipoUsuario.COMUM -> {
                                        val inativado = usuario.copy(ativo = false)
                                        usuarios.remove(usuario)
                                        usuarios.add(inativado)
                                        usuarioLogado = null
                                        println("Conta inativada com sucesso!")
                                    }
                                    TipoUsuario.ORGANIZADOR -> {
                                        // '.any { }' percorre a lista e eventos e retorna TRUE se achar PELO MENOS UM evento ativo
                                        val temEventoAtivo = eventos.any {
                                            it.emailOrganizador == usuario.email && it.ativo
                                        }

                                        when (temEventoAtivo) {
                                            //Se retornar TRUE, bloqueia a inativação
                                            true -> println("ERRO: Você possui eventos ativos! Desative seus eventos antes de inativar a conta.")
                                            //Se retornar FALSE, segue a mesma lógica de inatividade do usuário
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
                        //Pede as Credenciais
                        println("Email:")
                        val email = readln() ?: ""
                        println("Senha:")
                        val senha = readln() ?: ""

                        //O '.find { }' e devolve o PRIMEIRO que atender à condição
                        val usuarioEncontrado = usuarios.find { it.email.equals(email, ignoreCase = true) && it.senha == senha }

                        when (usuarioEncontrado) {
                            null -> println("Credenciais incorretas ou usuário não encontrado.")
                            else -> {
                                //Verifica o status da conta encontrada.
                                when (usuarioEncontrado.ativo){
                                    true -> println("Esta conta já está ativa!") //Impede de reativar quem não precisa
                                    false -> {
                                        //Cria um clone com ativo = true e troca na lista de usuários
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

            8 -> {
                if (usuarioLogado != null && usuarioLogado.tipo == TipoUsuario.ORGANIZADOR) {
                    println("\n=== Cadastrar Novo Evento ===")
                    print("Nome do evento: ")
                    val nome = readlnOrNull() ?: ""

                    print("Descrição: ")
                    val descricao = readlnOrNull() ?: ""

                    print("Página/URL: ")
                    val pagina = readlnOrNull() ?: ""

                    print("Data de Início (dd/mm/yyyy): ")
                    val dataInicio = readlnOrNull() ?: ""

                    print("Data de Fim (dd/mm/yyyy): ")
                    val dataFim = readlnOrNull() ?: ""

                    println("Tipo do Evento (SHOW, PALESTRA, CURSO, SOCIAL, CORPORATIVO, RELIGIOSO, ESPORTIVO, AULA, TREINAMENTO, SEMINARIO): ")
                    val tipoInput = readlnOrNull()?.uppercase() ?: ""
                    val tipo = try { TipoEventos.valueOf(tipoInput) } catch (e: Exception) { TipoEventos.SHOW }

                    println("Modalidade (PRESENCIAL, REMOTO, HIBRIDO): ")
                    val modalidadeInput = readlnOrNull()?.uppercase() ?: ""
                    val modalidade = try { Modalidade.valueOf(modalidadeInput) } catch (e: Exception) { Modalidade.PRESENCIAL }

                    print("Capacidade Máxima: ")
                    val capacidadeMax = readlnOrNull()?.toIntOrNull() ?: 0

                    print("Local: ")
                    val local = readlnOrNull() ?: ""

                    print("Preço do Ingresso (R$): ")
                    val preco = readlnOrNull()?.toDoubleOrNull() ?: 0.0

                    print("Permite Estorno? (SIM/NAO): ")
                    val temEstorno = readlnOrNull()?.uppercase() == "SIM"

                    var taxaEstorno = 0.0
                    if (temEstorno) {
                        print("Taxa de Estorno (%): ")
                        taxaEstorno = readlnOrNull()?.toDoubleOrNull() ?: 0.0
                    }

                    val novoEvento = Evento(
                        nome = nome, descricao = descricao, pagina = pagina, dataInicio = dataInicio,
                        dataFim = dataFim, tipo = tipo, ligadoPrincipal = null, modalidade = modalidade,
                        capacidadeMax = capacidadeMax, local = local, ativo = true, preco = preco,
                        temEstorno = temEstorno, taxaEstorno = taxaEstorno, emailOrganizador = usuarioLogado.email
                    )

                    eventos.add(novoEvento)
                    println("✅ Evento '$nome' cadastrado com sucesso!")
                } else {
                    println("❌ Acesso negado. Apenas organizadores podem cadastrar eventos.")
                }
            }  //Dylan
            9 -> {
                if(usuarioLogado != null && usuarioLogado.tipo == TipoUsuario.COMUM) {

                    println("\n=== Eventos Disponíveis ===")

                    val eventosDisponiveis = eventos.filter { it.ativo }

                    if (eventosDisponiveis.isEmpty()){
                        println("Nenhum evento disponível no momento.")
                    } else {

                        eventosDisponiveis.forEachIndexed { index, evento ->
                            println("${index + 1} - ${evento.nome} | ${evento.dataInicio} | R$ ${evento.preco}")
                        }

                        println("Digite o número do evento:")
                        val escolha = readLine()?.toIntOrNull()

                        if (escolha == null || escolha !in 1..eventosDisponiveis.size) {
                            println("Evento inválido")
                            continue
                        }

                        val eventoEscolhido = eventosDisponiveis[escolha - 1]

                        val ingressosVendidos = ingressos.count {
                            it.nomeEvento == eventoEscolhido.nome && !it.cancelado
                        }

                        if (ingressosVendidos >= eventoEscolhido.capacidadeMax) {
                            println("Evento esgotado")
                            continue
                        }

                        val jaComprou = ingressos.any {
                            it.emailUsuario == usuarioLogado.email && it.nomeEvento == eventoEscolhido.nome && !it.cancelado
                        }

                        if (jaComprou) {
                            println("Voce já comprou ingresso para esse evento")
                            continue
                        }

                        val ingresso = Ingresso(
                            emailUsuario = usuarioLogado.email,
                            nomeEvento = eventoEscolhido.nome,
                            valorPago = eventoEscolhido.preco
                        )

                        ingressos.add(ingresso)

                        println("Ingresso comprado com sucesso!")
                    }
                } else {
                    println("Apenas usuários comuns podem comprar ingressos!")
                }
            }//CHAIRA
            10 -> {
                usuarioLogado = null
                println("Logout realizado com sucesso!")
            }//DEIVID
            11 -> {break}//DEIVID
            12 -> {
                if (usuarioLogado != null && usuarioLogado.tipo == TipoUsuario.ORGANIZADOR) {
                    println("\n=== Meus Eventos ===")
                    val meusEventos = eventos.filter { it.emailOrganizador == usuarioLogado.email }

                    if (meusEventos.isEmpty()) {
                        println("Você ainda não possui eventos cadastrados.")
                    } else {
                        meusEventos.forEachIndexed { index, evento ->
                            val status = if (evento.ativo) "ATIVO" else "INATIVO"
                            println("${index + 1}. [${status}] ${evento.nome} - ${evento.dataInicio} (R$ ${evento.preco})")
                        }

                        println("\nOpções:")
                        println("1 - Alterar um Evento")
                        println("2 - Ativar/Desativar um Evento")
                        println("3 - Voltar")
                        print("Escolha: ")

                        when (readLine()?.toIntOrNull()) {
                            1 -> {
                                print("Digite o número do evento que deseja alterar: ")
                                val idx = (readLine()?.toIntOrNull() ?: 0) - 1
                                if (idx in meusEventos.indices) {
                                    val eventoAlvo = meusEventos[idx]
                                    print("Novo nome (ou enter para manter '${eventoAlvo.nome}'): ")
                                    val novoNome = readLine()
                                    val nomeFinal = if (novoNome.isNullOrBlank()) eventoAlvo.nome else novoNome

                                    // Atualizando o evento
                                    val eventoAtualizado = eventoAlvo.copy(nome = nomeFinal)
                                    eventos[eventos.indexOf(eventoAlvo)] = eventoAtualizado
                                    println("✅ Evento atualizado com sucesso!")
                                } else {
                                    println("Evento inválido.")
                                }
                            }
                            2 -> {
                                print("Digite o número do evento que deseja ativar/desativar: ")
                                val idx = (readLine()?.toIntOrNull() ?: 0) - 1
                                if (idx in meusEventos.indices) {
                                    val eventoAlvo = meusEventos[idx]
                                    val eventoAtualizado = eventoAlvo.copy(ativo = !eventoAlvo.ativo)
                                    eventos[eventos.indexOf(eventoAlvo)] = eventoAtualizado
                                    println("✅ Status do evento alterado para: ${if (eventoAtualizado.ativo) "ATIVO" else "INATIVO"}")
                                }
                            }
                            3 -> {} // Volta ao menu principal
                            else -> println("Opção inválida.")
                        }
                    }
                }
            }
            13 -> {}//CHAIRA
            14 -> {}//CHAIRA
        }
    } while (opcao != 11)
}

