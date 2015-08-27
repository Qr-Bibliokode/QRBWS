package qrbws.sender

import qrbws.UserAccount

interface IMessageCreator {
    MessageType getType();

    String create(UserAccount userAccount);
}