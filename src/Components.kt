// FUNÇÕES DE ENTRADA DE DADOS (Leitura e Validação)

//Prende o fluxo num laço até que uma entrada válida seja fornecida

fun readInt(message: String, errorMessage: String, range: IntRange = 0..Int.MAX_VALUE): Int{
    var input: Int?
    var valido: Boolean

    do {
        print(message)
        //Tenta converter o texto para número
        input = readlnOrNull()?.toIntOrNull()

        //A entrada só é válida se não for nula e estiver dentro do range
        valido = input != null && input in range

        if (!valido) {
            println(errorMessage)
        }
    } while (!valido) //Repete a pergunta enquanto a flag for falsa

    // O !! garante ao Kotlin que a variável não é nula neste ponto.
    return input!!
}

fun readDouble(message: String, errorMessage: String, minValue: Double = 0.0, maxValue: Double = Double.MAX_VALUE): Double {
    var input: Double?
    var valido: Boolean

    do {
        print(message)
        //Tenta converter o texto para número
        input = readlnOrNull()?.toDoubleOrNull()

        //Valida se a conversão deu certo e se ele respeita os limites mínimo e máximo
        valido = input != null && input >= minValue && input <= maxValue

        if (!valido) {
            println(errorMessage)
        }
    } while (!valido)

    return input!!
}

//Lê textos, garantindo que o utilizador não deixa campos obrigatórios em branco (minLength).
fun readString(message: String, errorMessage: String, minLength: Int = 0): String {
    var input: String?
    var valido: Boolean

    do {
        print(message)
        input = readlnOrNull()

        // Verifica se o utilizador digitou algo e se cumpre o tamanho mínimo exigido.
        valido = input != null && input.length >= minLength

        if (!valido){
            println(errorMessage)
        }
    }while (!valido)

    return input!!
}

//FUNÇÃO DE SAÍDA DE DADOS

// Cria uma tabela visual dinâmica para listar dados de forma limpa.
fun printTable(header: String, items: List<Any>) {
    // Cria uma linha separadora com base no tamanho do título
    val tracos = "-".repeat(header.length + 20)

    println("\n$tracos")
    println(" $header")
    println(tracos)

    if (items.isEmpty()) {
        println(" Nenhum registo encontrado.")
    } else {
        // Percorre a lista genérica (Any) e imprime cada item. O Kotlin usa o .toString() das Data Classes automaticamente.
        items.forEach { item ->
            println(" $item")
        }
    }
    println("$tracos\n")
}
