package qrbws

import grails.rest.Resource

@Resource(uri = '/api/categoria', formats=['json'])
class Categoria {

    String descricao

    static constraints = {
        descricao maxSize: 50
    }
}
