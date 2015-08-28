package qrbws

import qrbws.sender.messages.MessageCreatorSMSRegister

class SenderSMSController {

    SenderSMSService senderSMSService

    void sendSMSRegister(UserAccount userAccount) {
        senderSMSService.sendRegister(userAccount, new MessageCreatorSMSRegister())
    }
}
