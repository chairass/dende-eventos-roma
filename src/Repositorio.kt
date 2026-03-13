val usuarios = mutableListOf<Usuario>()
val eventos = mutableListOf<Evento>()
val ingressos = mutableListOf<Ingresso>()

// FUNÇÕES DO REPOSITÓRIO DE USUÁRIOS

//Adiciona um novo usuário
fun adicionarUsuario(usuario: Usuario){
    usuarios.add(usuario)
}

//Atualiza os dados do usuário
fun atualizarUsuario(usuarioAntigo: Usuario, usuarioNovo: Usuario ){
    usuarios.remove(usuarioAntigo)
    usuarios.add(usuarioNovo)
}

//Busca credenciais do usuário
fun buscarUsuarioEmail(email: String): Usuario? {
    return usuarios.find { it.email.equals(email, ignoreCase = true) }
}

fun buscarUsuarioLogin(email: String, senha: String): Usuario? {
    return usuarios.find { it.email.equals(email, ignoreCase = true) && it
        .senha == senha }
}

//FUNÇÕES DO REPOSITÓRIO DE EVENTOS

//Adiciona um evento novo
fun adicionarEvento(evento: Evento){
    eventos.add(evento)
}

//Atualiza os eventos
fun atualizarEventos(eventoAntigo: Evento, eveentoNovo: Evento){
    val index = eventos.indexOf(eventoAntigo)
    if (index != -1){
        eventos[index] = eveentoNovo
    }
}

//Exibe os eventos ativos
fun listarEventosAtivos(): List<Evento> {
    return eventos.filter { it.ativo }
}

//Exibe os eventos de um organizador
fun listarEventosDoOrganizador(emailOrganizador: String): List<Evento> {
    return eventos.filter { it.emailOrganizador.equals(emailOrganizador, ignoreCase = true) }
}

//Verifica se o organizador tem eventos ativos
fun organizadorTemEventoAtivo(emailOrganizador: String): Boolean {
    return eventos.any { it.emailOrganizador.equals(emailOrganizador, ignoreCase = true) && it.ativo }
}

// FUNÇÕES DO REPOSITÓRIO DE INGRESSOS
fun adicionarIngresso(ingresso: Ingresso) {
    ingressos.add(ingresso)
}

//Atualiza ingressos
fun atualizarIngresso(ingressoAntigo: Ingresso, ingressoNovo: Ingresso) {
    ingressos.remove(ingressoAntigo)
    ingressos.add(ingressoNovo)
}

//Exibe os ingressos do usuário comum
fun listarIngressosDoUsuario(emailUsuario: String): List<Ingresso> {
    return ingressos.filter { it.emailUsuario.equals(emailUsuario, ignoreCase = true) }
}

//Conta quantos ingressos foram vendidos
fun contarIngressosVendidos(nomeEvento: String): Int{
    return ingressos.count { it.nomeEvento.equals(nomeEvento, ignoreCase = true) }
}

//Verifica se o já comprou os ingressos
fun usuarioJaComprouIngresso(emailUsuario: String, nomeEvento: String): Boolean{
    return ingressos.any {
        it.emailUsuario.equals(emailUsuario, ignoreCase = true) &&
        it.nomeEvento.equals(nomeEvento, ignoreCase = true) &&
        !it.cancelado
    }
}


