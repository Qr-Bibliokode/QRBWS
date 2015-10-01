package qrbws

import grails.rest.Resource

@Resource(uri = '/api/funcionario', formats=['json'])
class Funcionario extends Pessoa {

    String codigo

    static constraints = {
        codigo unique: true, blank: false, maxSize: 5
    }
}
