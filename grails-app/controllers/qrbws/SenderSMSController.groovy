package qrbws

import qrbws.sender.messages.MessageCreatorSMSAvisoDevolucao
import qrbws.sender.messages.MessageCreatorSMSRegister

class SenderSMSController {

    SenderSMSService senderSMSService

    void sendSMSRegister(ContaUsuario contaUsuario) {
        senderSMSService.sendSMS(contaUsuario, new MessageCreatorSMSRegister())
    }

    void sendSMSAvisoDevolucao(ContaUsuario contaUsuario) {
        senderSMSService.sendSMS(contaUsuario, new MessageCreatorSMSAvisoDevolucao())
    }
}
