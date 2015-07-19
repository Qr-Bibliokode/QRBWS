package qrbws

import grails.rest.Resource

@Resource(uri='/user')
class User {

    String login
    String password
    Person person
    Status status

    static constraints = {
        login blank: false, unique: true, size: 5..20
        password blank: false, size: 5..20
    }
}
