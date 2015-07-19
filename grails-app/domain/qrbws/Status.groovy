package qrbws

import grails.rest.Resource

@Resource(uri='/status')
class Status {

    String description

    static constraints = {
        description blank: false, maxSize: 45
    }

}
