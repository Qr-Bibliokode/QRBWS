package qrbws

import grails.transaction.Transactional
import qrbws.sender.messages.IMessageCreator
import qrbws.sender.senders.SenderEmail

@Transactional
class SenderEmailService {

    void sendRegister(UserAccount userAccount, IMessageCreator messageCreator) {
        new SenderEmail(messageCreator: messageCreator).send(userAccount)
    }
}
