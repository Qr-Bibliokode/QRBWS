package messenger

import org.apache.log4j.Logger
import qrbws.UserAccount

class SenderSMS implements Sender {
    private static final Logger LOGGER = Logger.getLogger(SenderSMS.class);

    private final MessageCreator messageCreator;

    public SenderSMS(MessageCreator messageCreator) {
        this.messageCreator = messageCreator;
    }

    @Override
    public void send(UserAccount userAccount) {
        LOGGER.info("""INICIO - SMS ${messageCreator.type.description} para ${userAccount.person.name} - ${userAccount.person.phone}. . .""")
        String requestUrl
        String urlBase = 'http://127.0.0.1:9501/api?action=sendmessage&'
        String recipient = userAccount.person.phone
        String username = 'admin'
        String password = 'spectra'
        String originator = "00556281984246"

        try {
            requestUrl = """${urlBase}username=${username}&password=${password}&recipient=${recipient}&messagetype=SMS:TEXT&messagedata=${gerarConteudo(userAccount)}&originator=${originator}&serviceprovider=GSMModem1&responseformat=html"""
            URL url = new URL(requestUrl);
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            LOGGER.info(uc.getResponseMessage());
            uc.disconnect();
            LOGGER.info("""FINAL - SMS ${messageCreator.type} enviado com sucesso para ${userAccount.person.name} - ${userAccount.person.phone}""");
        } catch (IOException e) {
            LOGGER.error("Erro ao tentar mandar SMS. ", e);
        }
    }

    private String gerarConteudo(UserAccount userAccount) {
        return this.messageCreator.create(userAccount);
    }
}
