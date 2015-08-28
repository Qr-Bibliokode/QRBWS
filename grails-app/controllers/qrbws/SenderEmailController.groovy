package qrbws

import qrbws.sender.messages.MessageCreatorEmailRegister

class SenderEmailController {

    SenderEmailService senderEmailService

    void sendEmailRegister(UserAccount userAccount) {
        senderEmailService.sendRegister(userAccount, new MessageCreatorEmailRegister())
    }
}
