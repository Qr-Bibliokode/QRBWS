package qrbws

class SenderSMSController {

    SenderSMSService senderSMSService

    void sendSMSRegister(UserAccount userAccount) {
        senderSMSService.sendRegister userAccount
    }
}
