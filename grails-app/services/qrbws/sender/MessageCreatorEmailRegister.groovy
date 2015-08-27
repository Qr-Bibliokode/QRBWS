package qrbws.sender

import qrbws.UserAccount

class MessageCreatorEmailRegister implements IMessageCreator {
    MessageType getType() {
        return MessageType.CADASTRO_USUARIO;
    }

    String create(UserAccount userAccount) {
        return """<h2>Parabéns ${userAccount.person.name}, o cadastro foi realizado com sucesso !</h2><hr/>
<p><strong>Usuário: </strong><span>${userAccount.login}</span></p>
<p><strong>Senha: </strong><span>${userAccount.password}</span></p>
<hr/>
Obrigado por utilizar o nosso sistema, Qr-Bibliokode Team <img src=\"https://mail.google.com/mail/u/0/e/360\" goomoji=\"360\" style=\"margin: 0px 0.2ex; vertical-align: middle;\">"""
    }
}