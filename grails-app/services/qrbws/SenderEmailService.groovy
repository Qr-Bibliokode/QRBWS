package qrbws

import grails.transaction.Transactional
import qrbws.sender.messages.IMessageCreator
import qrbws.sender.ISender

@Transactional
class SenderEmailService implements ISender {

    def mailService

    IMessageCreator messageCreator;

    void sendEmail(UserAccount userAccount, IMessageCreator messageCreator) {
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
