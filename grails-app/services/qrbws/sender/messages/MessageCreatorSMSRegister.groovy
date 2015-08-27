package qrbws.sender.messages

import qrbws.UserAccount

class MessageCreatorSMSRegister implements IMessageCreator {

    MessageType getType() {
        return MessageType.CADASTRO_USUARIO;
    }

    String create(UserAccount userAccount) {
        String mensagem = """Parabéns ${userAccount.person.name}, o cadastro foi realizado com sucesso ! Usuário: ${userAccount.login} Senha: ${userAccount.password} Obrigado por utilizar o nosso sistema, Qr-Bibliokode Team"""
        mensagem.replace(" ", "%20")
    }
}
