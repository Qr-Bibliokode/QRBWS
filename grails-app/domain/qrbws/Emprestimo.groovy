package qrbws

import grails.converters.JSON

class Emprestimo {

    Date dataEmprestimo
    Date dataDevolucao
    Date dataLimiteDevolucao
    boolean devolvido
    boolean avisado
    Integer renovacoes

    ContaUsuario contaUsuario
    Livro livro

    static hasOne = [solicitacao: Solicitacao]

    static constraints = {
        dataEmprestimo nullable: true
        dataDevolucao nullable: true
        dataLimiteDevolucao nullable: true
        renovacoes nullable: true, min: 0
        solicitacao nullable: true
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
                    'solicitacao'        : it.solicitacao,
                    'renovacoes'         : it.renovacoes,
                    'contaUsuario'       : it.contaUsuario,
                    'livro'              : it.livro
            ]
        }
    }
}
