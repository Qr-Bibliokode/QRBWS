package qrbws.sender.senders

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import qrbws.UserAccount
import qrbws.sender.messages.IMessageCreator

class SenderEmail implements Sender {

    def mailService

    IMessageCreator messageCreator;

    private static final Logger LOGGER = LoggerFactory.getLogger(this)

    @Override
    public void send(UserAccount userAccount) {
        String userName = userAccount.person.name
        String userEmail = userAccount.person.email
        String messageType = messageCreator.type.description
        LOGGER.info """INICIO - E-MAIL ${messageType} para ${userName} - ${userEmail}"""
        mailService.sendMail {
            to userEmail
            subject messageType
            text createMessage(userAccount)
        }
        LOGGER.info """FINAL - SMS ${messageType} enviado com sucesso para ${userName} - ${userEmail}"""
    }

    private String createMessage(UserAccount userAccount) {
        return messageCreator.create(userAccount);
    }
}
