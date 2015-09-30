package qrbws

import grails.rest.Resource

@Resource(uri = '/api/estudante', formats=['json'])
class Estudante extends Pessoa {

    String matricula

    static constraints = {
        matricula unique: true, maxSize: 5, nullable: false, blank: false
    }
}
