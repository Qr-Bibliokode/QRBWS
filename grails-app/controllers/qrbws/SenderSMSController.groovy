package qrbws

import qrbws.sender.messages.MessageCreatorSMSRegister

class SenderSMSController {

    SenderSMSService senderSMSService

    void sendSMSRegister(ContaUsuario contaUsuario) {
        senderSMSService.sendRegister(contaUsuario, new MessageCreatorSMSRegister())
    }

    // TODO: Implementar sendSMSAvisoDevolucao
}
