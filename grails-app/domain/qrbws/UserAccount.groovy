package qrbws

import grails.rest.Resource

@Resource(uri = '/api/userAccount', formats = ['json'])
class UserAccount {

    String login
    String password
    Person person
    Boolean enabled

    def senderEmailService

    UserAccount() {
        this.enabled = true
    }

    def afterInsert() {
       // person.phone != null ? senderEmailService.sendSMSRegister(this) : ''
        senderEmailService.sendEmailRegister(this)
    }

    static constraints = {
        login blank: false, unique: true, size: 5..20
        password blank: false, size: 5..20
    }
}
