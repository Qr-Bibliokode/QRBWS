package qrbws

import grails.transaction.Transactional
import qrbws.sender.messages.IMessageCreator
import qrbws.sender.ISender

@Transactional
class SenderEmailService implements ISender {

    def mailService

    IMessageCreator messageCreator;

    void sendEmail(ContaUsuario userAccount, IMessageCreator messageCreator) {
        this.messageCreator = messageCreator;
        send(userAccount);
    }

    @Override
    public void send(ContaUsuario userAccount) {
        String userName = userAccount.pessoa.nome
        String userEmail = userAccount.pessoa.email
        String messageType = messageCreator.type.description
        log.info """INICIO - E-MAIL ${messageType} para ${userName} - ${userEmail}"""
        mailService.sendMail {
            to userEmail
            subject messageType
            text createMessage(userAccount)
        }
        log.info """FINAL - SMS ${messageType} enviado com sucesso para ${userName} - ${userEmail}"""
    }

    private String createMessage(ContaUsuario userAccount) {
        return messageCreator.create(userAccount);
    }

}
