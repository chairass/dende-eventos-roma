fun cadastrarEvento(usuarioLogado: Usuario) {
    // Parte de cadastro do novo evento e sua descrição
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

    // Cria o  Evento
    val novoEvento = Evento(
        nome = nome, descricao = descricao, pagina = pagina, dataInicio = dataInicio,
        dataFim = dataFim, tipo = tipo, ligadoPrincipal = null, modalidade = modalidade,
        capacidadeMax = capacidadeMax, local = local, ativo = true, preco = preco,
        temEstorno = temEstorno, taxaEstorno = taxaEstorno, emailOrganizador = usuarioLogado.email
    )

    // Chama a função do Repositorio.kt para salvar
    adicionarEvento(novoEvento)
    println(" Evento '$nome' cadastrado com sucesso!")
}


fun meusEventos(usuarioLogado: Usuario){
    val meusEventos = listarEventosDoOrganizador(usuarioLogado.email)

    if (meusEventos.isEmpty()) {
        println("\nVocê ainda não possui eventos cadastrados.")
        return
    }

    // Transforma a lista de eventos numa lista de textos para a tabela
    val linhasDaTabela = meusEventos.mapIndexed { index, evento ->
        val status = if (evento.ativo) "ATIVO" else "INATIVO"
        "${(index + 1).toString().padEnd(2)} | [$status] | ${evento.nome.padEnd(15)} | ${evento.dataInicio} | R$ ${evento.preco}"
    }

    // Chama a tabela mágica!
    printTable("MEUS EVENTOS CADASTRADOS", linhasDaTabela)

    println("\nOpções:")
    println("1 - Alterar um Evento")
    println("2 - Ativar/Desativar um Evento")
    println("3 - Voltar")
    print("Escolha: ")

    when (readlnOrNull()?.toIntOrNull()) {
        1 -> {
            print("Digite o número do evento que deseja alterar: ")
            val idx = (readlnOrNull()?.toIntOrNull() ?: 0) - 1
            if (idx in meusEventos.indices) {
                val eventoAlvo = meusEventos[idx]
                print("Novo nome (ou enter para manter '${eventoAlvo.nome}'): ")
                val novoNome = readlnOrNull()
                val nomeFinal = if (novoNome.isNullOrBlank()) eventoAlvo.nome else novoNome

                val eventoAtualizado = eventoAlvo.copy(nome = nomeFinal)

                // Usa o repositório para atualizar o evento antigo
                atualizarEventos(eventoAlvo, eventoAtualizado)
                println(" Evento atualizado com sucesso!")
            } else {
                println(" Evento inválido.")
            }
        }
        2 -> {
            print("Digite o número do evento que deseja ativar/desativar: ")
            val idx = (readlnOrNull()?.toIntOrNull() ?: 0) - 1
            if (idx in meusEventos.indices) {
                val eventoAlvo = meusEventos[idx]
                val eventoAtualizado = eventoAlvo.copy(ativo = !eventoAlvo.ativo)

                // Usa o repositório para atualizar o status do evento
                atualizarEventos(eventoAlvo, eventoAtualizado)
                println(" Status alterado para: ${if (eventoAtualizado.ativo) "ATIVO" else "INATIVO"}")
            } else {
                println(" Evento inválido.")
            }
        }
        3 -> return
        else -> println(" Opção inválida.")
    }
}

