package qrbws.sender.messages

import qrbws.ContaUsuario

interface IMessageCreator {
    MessageType getType();

    String create(ContaUsuario userAccount);
}