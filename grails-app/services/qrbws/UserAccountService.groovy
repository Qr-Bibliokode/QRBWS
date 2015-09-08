package qrbws

import grails.transaction.Transactional
import qrbws.sender.messages.IMessageCreator

@Transactional
class UserAccountService {

    def senderEmailService
    def senderSMSService

    def sendSMS(UserAccount userAccount, IMessageCreator messageCreator) {
        senderSMSService.sendSMS(userAccount, messageCreator)
    }

    def sendEmail(UserAccount userAccount, IMessageCreator messageCreator) {
        senderEmailService.sendEmail(userAccount, messageCreator)
    }
}
