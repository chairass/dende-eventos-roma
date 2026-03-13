import kotlinx.datetime.LocalDate

enum class Modalidade{PRESENCIAL, REMOTO, HIBRIDO}
enum class TipoUsuario{COMUM, ORGANIZADOR}
enum class TipoEventos{SHOW, PALESTRA, CURSO, SOCIAL,CORPORATIVO,RELIGIOSO,ESPORTIVO, AULA, TREINAMENTO, SEMINARIO}
enum class Sexo{MASCULINO, FEMININO, OUTRO}

data class Usuario(
    val nome:String,
    val data: LocalDate,
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