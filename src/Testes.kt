

fun main() {
    println("=== INICIANDO BATERIA DE TESTES ===\n")

    //Limpar o Banco de Dados (Listas)
    usuarios.clear()
    eventos.clear()
    ingressos.clear()

    //USUÁRIOS
    println("--- TESTES DE USUÁRIO ---")

    // Criando os Mocks
    val organizador = Usuario(
        nome = "Dendê Produções",
        data = "01/01/1990",
        sexo = Sexo.OUTRO,
        email = "contato@dende.com",
        senha = "123",
        tipo = TipoUsuario.ORGANIZADOR,
        cnpj = "12.345.678/0001-99"
    )

    val cliente = Usuario(
        nome = "Leonardo",
        data = "15/05/2000",
        sexo = Sexo.MASCULINO,
        email = "leo@email.com",
        senha = "123",
        tipo = TipoUsuario.COMUM
    )

    print("1. Cadastrar e Buscar Organizador: ")
    adicionarUsuario(organizador)
    val orgSalvo = buscarUsuarioEmail("contato@dende.com")
    if (orgSalvo != null && orgSalvo.nome == "Dendê Produções") println("SUCESSO") else println("FALHOU")

    print("2. Cadastrar e Buscar Cliente (Login): ")
    adicionarUsuario(cliente)
    val clienteSalvo = buscarUsuarioLogin("leo@email.com", "123")
    if (clienteSalvo != null) println("SUCESSO") else println("FALHOU")

    // EVENTOS
    println("\n--- TESTES DE EVENTOS ---")

    // Criando um evento ativo
    val eventoShow = Evento(
        nome = "Show de Rock",
        descricao = "Banda Local",
        pagina = "www.rock.com",
        dataInicio = "20/12/2026",
        dataFim = "20/12/2026",
        tipo = TipoEventos.SHOW,
        ligadoPrincipal = null,
        modalidade = Modalidade.PRESENCIAL,
        capacidadeMax = 100,
        local = "Praça Principal",
        ativo = true,
        preco = 50.0,
        temEstorno = true,
        taxaEstorno = 10.0,
        emailOrganizador = "contato@dende.com"
    )

    print("3. Criar Evento e Listar Ativos: ")
    adicionarEvento(eventoShow)
    val ativos = listarEventosAtivos()
    if (ativos.size == 1 && ativos[0].nome == "Show de Rock") println("SUCESSO") else println("FALHOU")

    print("4. Regra de Bloqueio (Organizador Tem Evento Ativo?): ")
    val temEvento = organizadorTemEventoAtivo("contato@dende.com")
    if (temEvento) println("SUCESSO") else println("FALHOU")


    // MÓDULO 3: INGRESSOS
    println("\n--- TESTES DE INGRESSOS ---")

    // Simulando a compra de um ingresso
    val ingressoComprado = Ingresso(
        emailUsuario = "leo@email.com",
        nomeEvento = "Show de Rock",
        valorPago = 50.0
    )

    print("5. Simular Compra de Ingresso: ")
    adicionarIngresso(ingressoComprado)
    val comprasDoLeo = listarIngressosDoUsuario("leo@email.com")
    if (comprasDoLeo.size == 1) println("SUCESSO") else println("FALHOU")

    print("6. Regra de Compra Duplicada (Usuário já comprou?): ")
    val jaComprou = usuarioJaComprouIngresso("leo@email.com", "Show de Rock")
    if (jaComprou) println("SUCESSO") else println("FALHOU")

    print("7. Contar Ingressos Vendidos (Lotação): ")
    val totalVendidos = contarIngressosVendidos("Show de Rock")
    if (totalVendidos == 1) println("SUCESSO") else println("FALHOU")

    println("\n=== FIM DA BATERIA DE TESTES ===")
}