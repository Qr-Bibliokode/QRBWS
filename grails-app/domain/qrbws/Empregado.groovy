package qrbws

import grails.rest.Resource

@Resource(uri = '/api/empregado', formats=['json'])
class Empregado extends Pessoa {

    String codigo

    static constraints = {
        codigo unique: true, blank: false, maxSize: 5
    }
}
