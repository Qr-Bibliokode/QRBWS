package qrbws.sender.messages

import qrbws.ContaUsuario

class MessageCreatorSMSRegister implements IMessageCreator {

    MessageType getType() {
        return MessageType.CADASTRO_USUARIO;
    }

    String create(ContaUsuario contaUsuario) {
        String mensagem = "Parabéns ${contaUsuario.pessoa.nome}, o cadastro foi realizado com sucesso ! Verifique seu e-mail, Qr-Bibliokode Team"
        mensagem.replace(" ", "%20")
    }
}
