package qrbws

import grails.rest.Resource
import qrbws.sender.messages.MessageCreatorEmailRegister
import qrbws.sender.messages.MessageCreatorSMSRegister

@Resource(uri = '/api/userAccount', formats = ['json'])
class UserAccount {

    String login
    String password
    Person person
    Boolean enabled

    def senderEmailService
    def senderSMSService

    UserAccount() {
        this.enabled = true
    }

    def afterInsert() {
        person.phone != null ? senderSMSService.sendSMS(this, new MessageCreatorSMSRegister()) : ''
        senderEmailService.sendEmail(this, new MessageCreatorEmailRegister())
    }

    static constraints = {
        login blank: false, unique: true, size: 5..20
        password blank: false, size: 5..20
    }
}
