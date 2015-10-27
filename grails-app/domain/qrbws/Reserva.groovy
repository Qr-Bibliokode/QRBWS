package qrbws

import grails.converters.JSON
import grails.rest.Resource

@Resource(uri = '/api/reserva', formats = ['json'])
class Reserva {

    Date dataInicio
    Date dataFim
    Boolean ativa

    ContaUsuario contaUsuario
    Livro livro

    static constraints = {
    }

    static void marshaller() {
        JSON.registerObjectMarshaller(Reserva) {
            [
                    'id'          : it.id,
                    'dataInicio'  : it.dataInicio,
                    'dataFim'     : it.dataFim,
                    'ativa'       : it.ativa,
                    'contaUsuario': it.contaUsuario,
                    'livro'       : it.livro
            ]
        }
    }
}
