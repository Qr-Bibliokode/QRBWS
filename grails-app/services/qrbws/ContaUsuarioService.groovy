package qrbws

import grails.transaction.Transactional
import qrbws.sender.messages.IMessageCreator

@Transactional
class ContaUsuarioService {

    SenderEmailService senderEmailService
    SenderSMSService senderSMSService

    void sendSMS(ContaUsuario contaUsuario, IMessageCreator messageCreator) {
        senderSMSService.sendSMS(contaUsuario, messageCreator)
    }

    void sendEmail(ContaUsuario contaUsuario, IMessageCreator messageCreator) {
        senderEmailService.sendEmail(contaUsuario, messageCreator)
    }
}
