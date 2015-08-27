package qrbws

import grails.transaction.Transactional
import qrbws.sender.MessageCreatorSMSRegister
import qrbws.sender.SenderSMS

@Transactional
class SenderSMSService {

    private static final SenderSMS SENDER = new SenderSMS(messageCreator: new MessageCreatorSMSRegister())

    void sendRegister(UserAccount userAccount) {
        SENDER.send userAccount
    }
}
