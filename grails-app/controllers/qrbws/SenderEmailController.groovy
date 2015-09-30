package qrbws

import qrbws.sender.messages.MessageCreatorEmailRegister

class SenderEmailController {

    SenderEmailService senderEmailService

    void sendEmailRegister(ContaUsuario userAccount) {
        senderEmailService.sendEmail(userAccount, new MessageCreatorEmailRegister())
    }
}
