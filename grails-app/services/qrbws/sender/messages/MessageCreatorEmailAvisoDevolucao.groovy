package qrbws.sender.messages

import qrbws.ContaUsuario

class MessageCreatorEmailAvisoDevolucao implements IMessageCreator {
    MessageType getType() {
        MessageType.CADASTRO_USUARIO;
    }

    String create(ContaUsuario contaUsuario) {
        """<h2>Oi ${contaUsuario.pessoa.nome}!</h2><hr/>
<p><strong>Informamos que deve realizar a deovlução do livro !</span></p>
<hr/>
Obrigado por utilizar o nosso sistema, Qr-Bibliokode Team"""
    }
}