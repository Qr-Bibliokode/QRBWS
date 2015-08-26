package messenger

import qrbws.UserAccount

class MCSRegister implements MessageCreator {
    MessageType getType() {
        return MessageType.CADASTRO_USUARIO;
    }

    String create(UserAccount userAccount) {
        return """Parabéns ${userAccount.person.name}, o cadastro foi realizado com sucesso !
Usuário: ${userAccount.login}
Senha: ${userAccount.password}
Obrigado por utilizar o nosso sistema, Qr-Bibliokode Team"""
    }
}
