package messenger

import org.apache.log4j.Logger
import qrbws.UserAccount

class SenderEmail implements Sender {
    private static final Logger LOGGER = Logger.getLogger(SenderEmail.class);

    private final MessageCreator messageCreator;

    public SenderEmail(MessageCreator messageCreator) {
        this.messageCreator = messageCreator;
    }

    @Override
    public void send(UserAccount userAccount) {
        LOGGER.info("INICIO - E-MAIL '" + this.messageCreator.type.description + "' para '" + userAccount.person.email + "' . . .");
        createMessage(userAccount)

    }

    private String createMessage(UserAccount userAccount) {
        return this.messageCreator.create(userAccount);
    }
}
