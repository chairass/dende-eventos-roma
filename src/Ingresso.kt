fun comprarIngresso(usuarioLogado: Usuario){
    println("\n=== Comprar Ingresso ===")
    val eventosAtivos = listarEventosAtivos()

    if (eventosAtivos.isEmpty()) {
        println("Nenhum evento disponível para compra.")
        return
    }

    // Mostra os eventos para o usuário escolher
    eventosAtivos.forEachIndexed { index, evento ->
        println("${index + 1}. ${evento.nome} - R$ ${evento.preco}")
    }

    print("\nDigite o número do evento que deseja comprar (ou 0 para cancelar): ")
    val escolha = readlnOrNull()?.toIntOrNull() ?: 0

    if (escolha in 1..eventosAtivos.size) {
        val eventoEscolhido = eventosAtivos[escolha - 1]

        // Se o usuário tente comprar o ingresso repetido
        if (usuarioJaComprouIngresso(usuarioLogado.email, eventoEscolhido.nome)) {
            println(" Você já possui um ingresso ativo para este evento!")
            return
        }

        // Se as vagas estejam esgotadas
        val vendidos = contarIngressosVendidos(eventoEscolhido.nome)
        if (vendidos >= eventoEscolhido.capacidadeMax) {
            println(" Ingressos esgotados para o evento '${eventoEscolhido.nome}'!")
            return
        }

        println("Confirmar compra para '${eventoEscolhido.nome}' no valor de R$ ${eventoEscolhido.preco}? (SIM/NAO)")
        val confirmacao = readlnOrNull()?.uppercase()

        if (confirmacao == "SIM") {
            val novoIngresso = Ingresso(
                emailUsuario = usuarioLogado.email,
                nomeEvento = eventoEscolhido.nome,
                valorPago = eventoEscolhido.preco,
                cancelado = false
            )

            // Salva o ingresso usando o repositório
            adicionarIngresso(novoIngresso)
            println(" Ingresso comprado com sucesso! Verifique a aba 'Meus Ingressos'.")
        } else {
            println("Compra cancelada.")
        }
    } else if (escolha != 0) {
        println("Opção inválida.")
    }
}

fun feedEventos(usuarioLogado: Usuario){
    // Puxa apenas os eventos que estão com ativo = true
    val eventosAtivos = listarEventosAtivos()

    if (eventosAtivos.isEmpty()) {
        println("\nNenhum evento ativo no momento. Volte mais tarde!")
        return
    }

    // Cria uma lista de textos formatados com os dados do evento
    val linhasDaTabela = eventosAtivos.map { evento ->
        val ingressosVendidos = contarIngressosVendidos(evento.nome)
        val vagas = evento.capacidadeMax - ingressosVendidos

        // O padEnd(15) garante que as colunas fiquem alinhadas mesmo com nomes de tamanhos diferentes
        "${evento.nome.padEnd(15)} | Data: ${evento.dataInicio} | Preço: R$ ${evento.preco} | Vagas: $vagas | Local: ${evento.local} (${evento.modalidade})"
    }

    // Chama a SUA função de UI para imprimir a tabela!
    printTable("FEED DE EVENTOS DISPONÍVEIS", linhasDaTabela)
}

fun meusIngressos(usuarioLogado: Usuario){
    // Puxa o histórico de compras do usuário
    val meusIngressos = listarIngressosDoUsuario(usuarioLogado.email)

    if (meusIngressos.isEmpty()) {
        println("\nVocê ainda não comprou nenhum ingresso.")
        return
    }

    val linhasDaTabela = meusIngressos.mapIndexed { index, ingresso ->
        val status = if (ingresso.cancelado) "CANCELADO" else "ATIVO"
        "${(index + 1).toString().padEnd(2)} | [$status] | Evento: ${ingresso.nomeEvento.padEnd(15)} | Valor Pago: R$ ${ingresso.valorPago}"
    }

    printTable("MEU HISTÓRICO DE INGRESSOS", linhasDaTabela)

        // Submenu para Cancelamento, aparece quando houver  ingresso ativo
        val ingressosAtivos = meusIngressos.filter { !it.cancelado }
        if (ingressosAtivos.isNotEmpty()) {
            println("\nDeseja cancelar algum ingresso?")
            print("Digite o número do ingresso (ou 0 para voltar): ")

            val escolha = readlnOrNull()?.toIntOrNull() ?: 0

            if (escolha in 1..meusIngressos.size) {
                val ingressoAlvo = meusIngressos[escolha - 1]

                if (ingressoAlvo.cancelado) {
                    println(" Este ingresso já se encontra cancelado.")
                } else {
                    // Busca o evento original para ver a política de estorno
                    val evento = eventos.find { it.nome == ingressoAlvo.nomeEvento }

                    if (evento != null && evento.temEstorno) {
                        val valorReembolso = ingressoAlvo.valorPago * (1.0 - (evento.taxaEstorno / 100.0))
                        println("Este evento possui taxa de estorno de ${evento.taxaEstorno}%.")
                        println("Valor a ser reembolsado: R$ $valorReembolso")
                    } else {
                        println("️Este evento NÃO permite estorno. O cancelamento não gerará devolução do valor.")
                    }

                    println("Tem certeza que deseja cancelar? (SIM/NAO)")
                    val confirmacao = readlnOrNull()?.uppercase()

                    if (confirmacao == "SIM") {
                        val ingressoCancelado = ingressoAlvo.copy(cancelado = true)
                        atualizarIngresso(ingressoAlvo, ingressoCancelado)
                        println(" Ingresso cancelado com sucesso!")
                    } else {
                        println("Operação abortada.")
                    }
                }
            } else if (escolha != 0) {
                println(" Opção inválida.")
            }
        }
    }