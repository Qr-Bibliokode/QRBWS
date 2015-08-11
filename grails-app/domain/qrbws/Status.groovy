package qrbws

import grails.rest.Resource

@Resource(uri = '/api/status', formats=['json'])
class Status {

    String description

    static constraints = {
        description blank: false, maxSize: 45
    }

}
