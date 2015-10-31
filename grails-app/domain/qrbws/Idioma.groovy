package qrbws

import grails.rest.Resource

@Resource(uri = '/api/idioma', formats = ['json'])
class Idioma {

    String descricao

    static constraints = {
        descricao blank: false, maxSize: 45, unique: true
    }
}
