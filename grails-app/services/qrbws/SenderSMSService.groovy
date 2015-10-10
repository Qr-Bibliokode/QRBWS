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

    void sendSMS(ContaUsuario contaUsuario, IMessageCreator messageCreator) {
        this.messageCreator = messageCreator;
        send(contaUsuario);
    }

    void send(ContaUsuario contaUsuario) {
        String userName = contaUsuario.pessoa.nome
        String userPhone = contaUsuario.pessoa.celular
        String messageType = messageCreator.type.description

        try {
            LOGGER.info """INICIO - SMS ${messageType} para ${userName} - ${userPhone}"""
            mountUrl(contaUsuario).toURL().text;
            LOGGER.info """FINAL - SMS ${messageType} enviado com sucesso para ${userName} - ${userPhone}"""
        } catch (IOException e) {
            LOGGER.error "Aconteceu um erro: ${e}"
        }
    }

    private String mountUrl(ContaUsuario contaUsuario) {
        [
                url    : 'http://192.168.0.12:9090/sendsms?',
                phone  : "phone=${contaUsuario.pessoa.celular}",
                message: "&text=${createMessage(contaUsuario)}"
        ].inject([]) { result, entry ->
            result << "${entry.value}"
        }.join('')
    }

    private String createMessage(ContaUsuario contaUsuario) {
        messageCreator.create(contaUsuario).replace(" ", "%20")
    }
}
