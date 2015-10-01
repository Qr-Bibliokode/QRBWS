package qrbws

import qrbws.sender.messages.MessageCreatorEmailRegister

class SenderEmailController {

    SenderEmailService senderEmailService

    void sendEmailRegister(ContaUsuario contaUsuario) {
        senderEmailService.sendEmail(contaUsuario, new MessageCreatorEmailRegister())
    }
}
