package qrbws

import grails.converters.JSON
import grails.rest.Resource

@Resource(uri = '/api/stock', formats = ['json'])

class Stock {

    Livro livro
    Integer disponivel
    Integer total

    static constraints = {
        livro nullable: false, unique: true
        disponivel min: 0
        total min: 0
    }

    static void marshaller() {
        JSON.registerObjectMarshaller(Stock) {
            [
                    'id'        : it.id,
                    'livro'     : it.livro,
                    'disponivel': it.disponivel,
                    'total'     : it.total
            ]
        }
    }
}
