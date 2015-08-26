package messenger

import qrbws.UserAccount

interface MessageCreator {
    MessageType getType();

    String create(UserAccount userAccount);
}