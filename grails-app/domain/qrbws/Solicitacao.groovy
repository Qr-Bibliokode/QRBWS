package qrbws

import grails.converters.JSON

class Solicitacao {

    static final String DEVOLUCAO = "Devolução"
    static final String RESERVA = "Reserva"
    static final String RENOVACAO = "Renovação"
    static final String EMPRESTIMO = "Empréstimo"

    String tipo
    Emprestimo emprestimo

    static constraints = {
        tipo unique: true
        emprestimo nullable: false, unique: true
    }

    static void marshaller() {
        JSON.registerObjectMarshaller(Solicitacao) {
            [
                    'id'  : it.id,
                    'tipo': it.tipo
            ]
        }
    }
}
