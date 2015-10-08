package qrbws

import grails.rest.Resource

@Resource(uri = '/api/autor', formats=['json'])
class Autor {

    String nome
    String informacoesAdicionais

    static belongsTo = Livro

    static constraints = {
        nome blank: false, maxSize: 50, matches: '[^0-9\\-]+'
        informacoesAdicionais nullable: true
    }
}
