package qrbws

import grails.transaction.Transactional
import qrbws.sender.messages.IMessageCreator
import qrbws.sender.senders.SenderSMS

@Transactional
class SenderSMSService {

    void sendRegister(UserAccount userAccount, IMessageCreator messageCreator) {
        new SenderSMS(messageCreator: messageCreator).send(userAccount)
    }
}
