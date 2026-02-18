enum class Modalidade{PRESENCIAL, REMOTO, HIBRIDO}
enum class TipoUsuario{COMUM, ORGANIZADOR}
enum class TipoEventos{SHOW, PALESTRA, CURSO, SOCIAL,CORPORATIVO,RELIGIOSO,ESPORTIVO, AULA, TREINAMENTO, SEMINARIO}
enum class Sexo{MASCULINO, FEMININO, OUTRO}

data class Usuario(val nome:String,
                   val data:String,
                   val sexo:String,
                   val email:String,
                   val senha:String,
                   val tipo:TipoUsuario,
                   val ativo:Boolean = true,
                   val cnpj:String?=null,
                   val razaoSocial:String?=null,
                   val nomeFantasia:String?=null)

data class Evento(val nome:String,
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


                  var emailOrganizador:String)

data class Ingresso(val emailUsuario: String,
                    val nomeEvento:String,
                    val valorPago:Double,
                    val cancelado:Boolean)

fun main() {
    val usuarios = mutableListOf<Usuario>()
    val eventos = mutableListOf<Evento>()
    val ingressos = mutableListOf<Ingresso>()


    var opcao: Int

    do {
        println("\n=== Dende Eventos ===")

        println("1 - Cadastrar usuário")
        println("2 - Cadastrar organizador")
        println("3 - Cadastrar evento")
        println("4 - Listar eventos")
        println("5- Sair")

        opcao = readLine()!!.toInt()

        when (opcao) {
            1 -> {
                println("=== Preencha as informações do usuário ===")
                println("Nome:")
                val nome = readLine()!!
                println("Data de Nascimento:")
                val dataNascimento = readLine()!!
                println("Sexo:")
                val sexo = readLine()!!
                println("Email:")
                val email = readLine()!!
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

            2 -> {
                println("=== Preencha as informações do organizador ===")
                println("Nome:")
                val nome = readLine()!!
                println("Data de Nascimento:")
                val dataNascimento = readLine()!!
                println("Sexo:")
                val sexo = readLine()!!
                println("Email:")
                val email = readLine()!!
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
            3 -> {}
            4 -> {}
            5 -> {break}
        }
    } while (opcao != 5)

    println(usuarios)
}

