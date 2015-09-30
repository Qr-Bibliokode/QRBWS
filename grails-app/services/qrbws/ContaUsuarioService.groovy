package qrbws

import grails.transaction.Transactional
import qrbws.sender.messages.IMessageCreator

@Transactional
class ContaUsuarioService {

    def senderEmailService
    def senderSMSService

    def sendSMS(ContaUsuario userAccount, IMessageCreator messageCreator) {
        senderSMSService.sendSMS(userAccount, messageCreator)
    }

    def sendEmail(ContaUsuario userAccount, IMessageCreator messageCreator) {
        senderEmailService.sendEmail(userAccount, messageCreator)
    }
}
