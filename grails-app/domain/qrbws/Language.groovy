package qrbws

import grails.rest.Resource

@Resource(uri='/language')
class Language {

    String description

    static constraints = {
        description blank: false, maxSize: 45
    }
}
