package qrbws

import grails.converters.JSON

class Solicitacao {

    static final String DEVOLUCAO = "Devolução"
    static final String RESERVA = "Reserva"
    static final String RENOVACAO = "Renovação"
    static final String EMPRESTIMO = "Empréstimo"

    String tipo
    Boolean liberada
    Boolean ativa

    static belongsTo = [emprestimo: Emprestimo]

    static constraints = {
    }

    static void marshaller() {
        JSON.registerObjectMarshaller(Solicitacao) {
            [
                    'id'      : it.id,
                    'tipo'    : it.tipo,
                    'liberada': it.liberada,
                    'ativa'   : it.ativa
            ]
        }
    }
}
