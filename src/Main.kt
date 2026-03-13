import kotlinx.datetime.*

fun main() {
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
            1 -> cadastrarUsuarioComum()
            2 -> cadastrarOrganizador()
            3 -> usuarioLogado

            4 -> if (usuarioLogado != null) verPerfil(usuarioLogado) else println("Você precisa estar logado.")
            5 -> if (usuarioLogado != null) usuarioLogado = alterarPerfil(usuarioLogado) else println("Você precisa estar logado.")
            6 -> if (usuarioLogado != null) usuarioLogado = inativarConta(usuarioLogado) else println("Você precisa estar logado.")
            7 -> reativarConta()

            8 -> if (usuarioLogado?.tipo == TipoUsuario.ORGANIZADOR) cadastrarEvento(usuarioLogado) else println("Acesso negado.")
            9 -> if (usuarioLogado?.tipo == TipoUsuario.COMUM) comprarIngresso(usuarioLogado) else println("Acesso negado.")

            10 -> {
                usuarioLogado = null
                println("Logout realizado com sucesso!")
            }
            11 -> println("Saindo do Sistema...")

            12 -> if (usuarioLogado?.tipo == TipoUsuario.ORGANIZADOR) meusEventos(usuarioLogado) else println("Acesso negado.")
            13 -> if (usuarioLogado?.tipo == TipoUsuario.COMUM) feedEventos(usuarioLogado) else println("Acesso negado.")
            14 -> if (usuarioLogado?.tipo == TipoUsuario.COMUM) meusIngressos(usuarioLogado) else println("Acesso negado.")

            else -> if (opcao != 11) println("Opção inválida.")
        }
    } while (opcao != 11)
}