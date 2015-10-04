package qrbws.sender.messages

import qrbws.ContaUsuario

class MessageCreatorSMSAvisoDevolucao implements IMessageCreator {

    MessageType getType() {
        MessageType.AVISO_DEVOLUCAO;
    }

    String create(ContaUsuario contaUsuario) {
        "Oi ${contaUsuario.pessoa.nome}!, informamos que deve realizar a devolução do livro, Qr-Bibliokode Team"
    }
}
