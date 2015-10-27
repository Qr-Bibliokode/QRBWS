class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.${format})?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(view: "/index")
        "500"(view: '/error')

        "/api/autor"(resources: "autor")

        "/api/categoria"(resources: "categoria")

        "/api/contaUsuario/pagarMulta"(controller: "multa", action: 'pagar')
        "/api/contaUsuario/verificarMultas"(controller: "contaUsuario", action: 'verificarMultas')
        "/api/contaUsuario"(resources: "contaUsuario")

        "/api/comentario"(resources: "comentario")

        "/api/emprestimo/emprestar"(controller: 'emprestimo', action: 'emprestar')
        "/api/emprestimo/devolver"(controller: 'emprestimo', action: 'devolver')
        "/api/emprestimo/renovar"(controller: 'emprestimo', action: 'renovar')
        "/api/emprestimo/liberar"(controller: 'emprestimo', action: 'liberar')
        "/api/emprestimo/obtenhaHistoricoEmprestimosPorLivro"(controller: 'emprestimo', action: 'obtenhaHistoricoEmprestimosPorLivro')
        "/api/emprestimo"(resources: "emprestimo")

        "/api/feriado"(resources: "feriado")

        "/api/funcionario"(resources: "funcionario")

        "/api/idioma"(resources: "idioma")

        "/api/estudante"(resources: "estudante")

        "/api/livro"(resources: "livro")

        "/api/reserva"(resources: "reserva")

        "/api/stock"(resources: "stock")

    }
}