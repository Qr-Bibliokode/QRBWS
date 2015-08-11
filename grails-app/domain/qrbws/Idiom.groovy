package qrbws

import grails.rest.Resource

@Resource(uri = '/api/idiom', formats=['json'])
class Idiom {

    String description

    static constraints = {
        description blank: false, maxSize: 45, unique: true
    }
}
