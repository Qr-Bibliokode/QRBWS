package qrbws

import grails.rest.Resource

@Resource(uri = '/api/feriado', formats=['json'])
class Feriado {

    String descricao
    Date dataInicio
    Date dataFim

    static constraints = {
        descricao unique: true, blank: false, maxSize: 45, matches: '[^0-9\\-]+'
    }
}
