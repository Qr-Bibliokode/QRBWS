package qrbws

import grails.rest.Resource

@Resource(uri = '/api/stock', formats=['json'])

class Stock {

    Livro livro
    Integer disponivel
    Integer total

    static constraints = {
        livro nullable: false, unique: true
        disponivel min: 0
        total min: 0
    }
}
