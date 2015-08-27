package qrbws.sender

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import qrbws.UserAccount


class SenderEmail implements Sender {
    IMessageCreator messageCreator;

    private static final Logger LOGGER = LoggerFactory.getLogger(this)

    @Override
    public void send(UserAccount userAccount) {
        String userName = userAccount.person.name
        String userPhone = userAccount.person.phone
        String messageType = messageCreator.type.description

        LOGGER.info """INICIO - E-MAIL ${messageType} para ${userName} - ${userPhone}"""
        createMessage(userAccount)

    }

    private String createMessage(UserAccount userAccount) {
        return messageCreator.create(userAccount);
    }
}
