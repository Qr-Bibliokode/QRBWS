package qrbws

import grails.rest.Resource

@Resource(uri = '/holiday')
class Holiday {

    String description
    Date startDate
    Date finalDate

    static constraints = {
        description unique: true, blank: false, maxSize: 45, matches: '[^0-9\\-]+'
    }
}
