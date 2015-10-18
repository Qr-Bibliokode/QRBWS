package qrbws

import grails.converters.JSON

class Emprestimo {

    Date dataEmprestimo
    Date dataDevolucao
    Date dataLimiteDevolucao
    boolean devolvido
    boolean avisado
    boolean solicitacaoLiberada
    Integer renovacoes

    ContaUsuario contaUsuario
    Livro livro

    static constraints = {
        dataEmprestimo nullable: true
        dataDevolucao nullable: true
        dataLimiteDevolucao nullable: true
        renovacoes nullable: true, min: 0
    }

    public Emprestimo() {
        renovacoes = 0
    }

    static void marshaller() {
        JSON.registerObjectMarshaller(Emprestimo) {
            [
                    'id'                 : it.id,
                    'dataEmprestimo'     : it.dataEmprestimo,
                    'dataDevolucao'      : it.dataDevolucao,
                    'dataLimiteDevolucao': it.dataLimiteDevolucao,
                    'devolvido'          : it.devolvido,
                    'avisado'            : it.avisado,
                    'solicitacaoLiberada': it.solicitacaoLiberada,
                    'renovacoes'         : it.renovacoes,
                    'contaUsuario'       : it.contaUsuario,
                    'livro'              : it.livro
            ]
        }
    }
}
