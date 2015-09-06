package qrbws

import grails.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import qrbws.sender.messages.IMessageCreator
import qrbws.sender.ISender

@Transactional
class SenderSMSService implements ISender {

    IMessageCreator messageCreator;

    private static final Logger LOGGER = LoggerFactory.getLogger(this)

    void sendSMS(UserAccount userAccount, IMessageCreator messageCreator) {
        this.messageCreator = messageCreator;
        send(userAccount);
    }

    void send(UserAccount userAccount) {
        String userName = userAccount.person.name
        String userPhone = userAccount.person.phone
        String messageType = messageCreator.type.description

        try {
            LOGGER.info """INICIO - SMS ${messageType} para ${userName} - ${userPhone}"""
            mountUrl(userAccount).toURL().text;
            LOGGER.info """FINAL - SMS ${messageType} enviado com sucesso para ${userName} - ${userPhone}"""
        } catch (IOException e) {
            println e
            LOGGER.error "Aconteceu um erro: ${e}"
        }
    }

    private String mountUrl(UserAccount userAccount) {
        [
                url    : 'http://192.168.0.13:9090/sendsms?',
                phone  : "phone=${userAccount.person.phone}",
                message: "&text=${createMessage(userAccount)}"
        ].inject([]) { result, entry ->
            result << "${entry.value}"
        }.join('')
    }

    private String createMessage(UserAccount userAccount) {
        return messageCreator.create(userAccount)
    }
}
