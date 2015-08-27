package qrbws

import grails.rest.Resource

@Resource(uri = '/api/userAccount', formats=['json'])
class UserAccount {

    String login
    String password
    Person person
    Status status

    def senderSMSController

    def afterInsert() {
        senderSMSController.sendSMSRegister(this)
    }

    static constraints = {
        login blank: false, unique: true, size: 5..20
        password blank: false, size: 5..20
    }
}
