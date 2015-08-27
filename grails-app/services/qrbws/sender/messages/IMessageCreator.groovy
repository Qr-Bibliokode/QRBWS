package qrbws.sender.messages

import qrbws.UserAccount

interface IMessageCreator {
    MessageType getType();

    String create(UserAccount userAccount);
}