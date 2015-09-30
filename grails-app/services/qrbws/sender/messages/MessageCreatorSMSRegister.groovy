package qrbws.sender.messages

import qrbws.ContaUsuario

class MessageCreatorSMSRegister implements IMessageCreator {

    MessageType getType() {
        return MessageType.CADASTRO_USUARIO;
    }

    String create(ContaUsuario userAccount) {
        String mensagem = "Parabéns ${userAccount.pessoa.nome}, o cadastro foi realizado com sucesso ! Verifique seu e-mail, Qr-Bibliokode Team"
        mensagem.replace(" ", "%20")
    }
}
