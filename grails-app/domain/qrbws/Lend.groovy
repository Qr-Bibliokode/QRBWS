package qrbws

import grails.rest.Resource

@Resource(uri = '/api/lend', formats=['json'])
class Lend {

    UserAccount userAccount
    Book book
    Date dateOut
    Date dateIn
    boolean returned
    boolean avised

    static constraints = {
        userAccount nullable: false
        book nullable: false
    }
}
