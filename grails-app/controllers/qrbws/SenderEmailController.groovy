package qrbws

import qrbws.sender.messages.MessageCreatorEmailAvisoDevolucao
import qrbws.sender.messages.MessageCreatorEmailRegister

class SenderEmailController {

    SenderEmailService senderEmailService

    void sendEmailRegister(ContaUsuario contaUsuario) {
        senderEmailService.sendEmail(contaUsuario, new MessageCreatorEmailRegister())
    }

    void sendSMSAvisoDevolucao(ContaUsuario contaUsuario) {
        senderEmailService.sendEmail(contaUsuario, new MessageCreatorEmailAvisoDevolucao())
    }
}
