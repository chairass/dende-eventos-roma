val usuarios = mutableListOf<Usuario>()
val eventos = mutableListOf<Evento>()
val ingressos = mutableListOf<Ingresso>()

// FUNÇÕES DO REPOSITÓRIO DE USUÁRIOS
fun adicionarUsuario(usuario: Usuario){
    usuarios.add(usuario)
}

fun atualizarUsuario(usuarioAntigo: Usuario, usuarioNovo: Usuario ){
    usuarios.remove(usuarioNovo)
    usuarios.add(usuarioNovo)
}

fun buscarUsuarioEmail(email: String): Usuario? {
    return usuarios.find { it.email.equals(email, ignoreCase = true) }
}

fun buscarUsuarioLogin(email: String, senha: String): Usuario? {
    return usuarios.find { it.email.equals(email, ignoreCase = true) && it
        .senha == senha }
}

//FUNÇÕES DO REPOSITÓRIO DE EVENTOS

fun adicionarEvento(evento: Evento){
    eventos.add(evento)
}

fun atualizarEventos(eventoAntigo: Evento, eveentoNovo: Evento){
    val index = eventos.indexOf(eventoAntigo)
    if (index != -1){
        eventos[index] = eveentoNovo
    }
}

fun listarEventosAtivos(): List<Evento> {
    return eventos.filter { it.ativo }
}

fun listarEventosDoOrganizador(emailOrganizador: String): List<Evento> {
    return eventos.filter { it.emailOrganizador.equals(emailOrganizador, ignoreCase = true) }
}

fun organizadorTemEventoAtivo(emailOrganizador: String): Boolean {
    return eventos.any { it.emailOrganizador.equals(emailOrganizador, ignoreCase = true) && it.ativo }
}

// FUNÇÕES DO REPOSITÓRIO DE INGRESSOS
fun adicionarIngresso(ingresso: Ingresso) {
    ingressos.add(ingresso)
}

fun atualizarIngresso(ingressoAntigo: Ingresso, ingressoNovo: Ingresso) {
    ingressos.remove(ingressoAntigo)
    ingressos.add(ingressoNovo)
}

fun listarIngressosDoUsuario(emailUsuario: String): List<Ingresso> {
    return ingressos.filter { it.emailUsuario.equals(emailUsuario, ignoreCase = true) }
}

fun contarIngressosVendidos(nomeEvento: String): Int{
    return ingressos.count { it.nomeEvento.equals(nomeEvento, ignoreCase = true) }
}

fun usuarioJaComprouIngresso(emailUsuario: String, nomeEvento: String): Boolean{
    return ingressos.any {
        it.emailUsuario.equals(emailUsuario, ignoreCase = true) &&
        it.nomeEvento.equals(nomeEvento, ignoreCase = true) &&
        !it.cancelado
    }
}


