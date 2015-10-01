package qrbws

import grails.transaction.Transactional
import qrbws.sender.messages.IMessageCreator

@Transactional
class ContaUsuarioService {

    def senderEmailService
    def senderSMSService

    def sendSMS(ContaUsuario contaUsuario, IMessageCreator messageCreator) {
        senderSMSService.sendSMS(contaUsuario, messageCreator)
    }

    def sendEmail(ContaUsuario contaUsuario, IMessageCreator messageCreator) {
        senderEmailService.sendEmail(contaUsuario, messageCreator)
    }
}
