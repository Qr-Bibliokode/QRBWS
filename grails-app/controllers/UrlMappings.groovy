class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.${format})?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(view: "/index")
        "500"(view: '/error')

        "/api/contaUsuario/verificarMultas"(controller: "contaUsuario", action: 'verificarMultas')
        "/api/contaUsuario"(resources: "contaUsuario")

        "/api/emprestimo/emprestar"(controller: 'emprestimo', action: 'emprestar')
        "/api/emprestimo/devolver"(controller: 'emprestimo', action: 'devolver')
        "/api/emprestimo/renovar"(controller: 'emprestimo', action: 'renovar')
        "/api/emprestimo"(resources: "emprestimo")

        "/api/multa/pagar"(controller: 'multa', action: 'pagar')
        "/api/multa"(resources: "multa")
    }
}