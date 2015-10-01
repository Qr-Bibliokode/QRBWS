package qrbws

import grails.transaction.Transactional
import qrbws.sender.messages.IMessageCreator
import qrbws.sender.ISender

@Transactional
class SenderEmailService implements ISender {

    def mailService

    IMessageCreator messageCreator;

    void sendEmail(ContaUsuario contaUsuario, IMessageCreator messageCreator) {
        this.messageCreator = messageCreator;
        send(contaUsuario);
    }

    @Override
    public void send(ContaUsuario contaUsuario) {
        String userName = contaUsuario.pessoa.nome
        String userEmail = contaUsuario.pessoa.email
        String messageType = messageCreator.type.description
        log.info """INICIO - E-MAIL ${messageType} para ${userName} - ${userEmail}"""
        mailService.sendMail {
            to userEmail
            subject messageType
            text createMessage(contaUsuario)
        }
        log.info """FINAL - SMS ${messageType} enviado com sucesso para ${userName} - ${userEmail}"""
    }

    private String createMessage(ContaUsuario contaUsuario) {
        return messageCreator.create(contaUsuario);
    }

}
