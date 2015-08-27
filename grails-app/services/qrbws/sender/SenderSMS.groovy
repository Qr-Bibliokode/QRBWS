package qrbws.sender

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import qrbws.UserAccount

class SenderSMS implements Sender {

    IMessageCreator messageCreator;

    private static final Logger LOGGER = LoggerFactory.getLogger(this)

    void send(UserAccount userAccount) {
        String userName = userAccount.person.name
        String userPhone = userAccount.person.phone
        String messageType = messageCreator.type.description

        try {
            LOGGER.info """INICIO - SMS ${messageType} para ${userName} - ${userPhone}"""
            URL url = new URL(mountUrl(userAccount));
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            uc.disconnect();
            LOGGER.info """FINAL - SMS ${messageType} enviado com sucesso para ${userName} - ${userPhone}"""
        } catch (IOException e) {
            LOGGER.error "Aconteceu um erro: ${e}"
        }
    }

    private String mountUrl(UserAccount userAccount) {
        [
                url        : 'http://127.0.0.1:9501/api?action=sendmessage&',
                user       : 'username=admin',
                pass       : '&password=spectra',
                phone      : "&recipient=${userAccount.person.phone}",
                messageType: '&messagetype=SMS:TEXT',
                message    : "&messagedata=${createMessage(userAccount)}",
                phoneSender: '&originator=+556281984246',
                provider   : '&serviceprovider=GSMModem1',
                format     : '&responseformat=html'
        ].inject([]) { result, entry ->
            result << "${entry.value}"
        }.join('')
    }

    private String createMessage(UserAccount userAccount) {
        return messageCreator.create(userAccount)
    }

}
