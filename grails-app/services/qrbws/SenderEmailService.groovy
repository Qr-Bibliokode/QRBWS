package qrbws

import grails.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import qrbws.sender.messages.IMessageCreator
import qrbws.sender.senders.Sender

@Transactional
class SenderEmailService implements Sender {

    def mailService

    IMessageCreator messageCreator;

    void sendRegister(UserAccount userAccount, IMessageCreator messageCreator) {
        this.messageCreator = messageCreator;
       send(userAccount);
    }

    @Override
    public void send(UserAccount userAccount) {
        String userName = userAccount.person.name
        String userEmail = userAccount.person.email
        String messageType = messageCreator.type.description
        log.info """INICIO - E-MAIL ${messageType} para ${userName} - ${userEmail}"""
        mailService.sendMail {
            to userEmail
            subject messageType
            text createMessage(userAccount)
        }
        log.info """FINAL - SMS ${messageType} enviado com sucesso para ${userName} - ${userEmail}"""
    }

    private String createMessage(UserAccount userAccount) {
        return messageCreator.create(userAccount);
    }

}
