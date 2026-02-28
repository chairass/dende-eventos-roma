// FUNÇÕES DE ENTRADA DE DADOS (Leitura e Validação)

fun readInt(message: String, errorMessage: String, range: IntRange = 0..Int.MAX_VALUE): Int{
    var input: Int?
    var valido: Boolean

    do {
        print(message)
        input = readlnOrNull()?.toIntOrNull()

        valido = input != null && input in range

        if (!valido) {
            println(errorMessage)
        }
    } while (!valido)

    return input!!
}

fun readDouble(message: String, errorMessage: String, minValue: Double = 0.0, maxValue: Double = Double.MAX_VALUE): Double {
    var input: Double?
    var valido: Boolean

    do {
        print(message)
        input = readlnOrNull()?.toDoubleOrNull()

        valido = input != null && input >= minValue && input <= maxValue

        if (!valido) {
            println(errorMessage)
        }
    } while (!valido)

    return input!!
}

fun readStrig(message: String, errorMessage: String, minLength: Int = 0): String {
    var input: String?
    var valido: Boolean

    do {
        print(message)
        input = readlnOrNull()

        valido = input != null && input.length >= minLength

        if (!valido){
            println(errorMessage)
        }
    }while (!valido)

    return input!!
}

//FUNÇÃO DE SAÍDA DE DADOS

fun printTable(header: String, items: List<Any>) {
    val tracos = "-".repeat(header.length + 20)

    println("\n$tracos")
    println(" $header")
    println(tracos)

    if (items.isEmpty()) {
        println(" Nenhum registo encontrado.")
    } else {
        items.forEach { item ->
            println(" $item")
        }
    }
    println("$tracos\n")
}

