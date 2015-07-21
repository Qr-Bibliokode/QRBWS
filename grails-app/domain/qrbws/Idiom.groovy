package qrbws

import grails.rest.Resource

@Resource(uri='/idiom')
class Idiom {

    String description

    static constraints = {
        description blank: false, maxSize: 45
    }
}
