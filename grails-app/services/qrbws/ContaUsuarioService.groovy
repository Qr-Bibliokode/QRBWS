package qrbws

import grails.transaction.Transactional
import groovy.time.TimeCategory
import qrbws.sender.messages.IMessageCreator

@Transactional
class ContaUsuarioService {

    SenderEmailService senderEmailService
    SenderSMSService senderSMSService

    void sendSMS(ContaUsuario contaUsuario, IMessageCreator messageCreator) {
        senderSMSService.sendSMS(contaUsuario, messageCreator)
    }

    void sendEmail(ContaUsuario contaUsuario, IMessageCreator messageCreator) {
        senderEmailService.sendEmail(contaUsuario, messageCreator)
    }

    def verificarMultas(Long id) {
        geraMultas(id)
        ContaUsuario.get(id).multas
    }

    void geraMultas(Long id) {
        ContaUsuario contaUsuario = ContaUsuario.get(id)
        List<Emprestimo> emprestimos = Emprestimo.findAllByContaUsuarioAndDevolvido(contaUsuario, false)
        emprestimos.each { Emprestimo emprestimo ->
            Multa multa = Multa.findByEmprestimo(emprestimo) ?: new Multa()
            if (emprestimo.dataLimiteDevolucao.before(new Date())) {
                multa.valor = calcularValorMulta(emprestimo.dataLimiteDevolucao)
                multa.emprestimo = multa.emprestimo ?: emprestimo
                contaUsuario.multas << multa
                contaUsuario.save flush: true
            }
        }
    }

    Double calcularValorMulta(Date dataLimiteDevolucao) {
        int diasMulta = 0
        use(TimeCategory) { diasMulta = (new Date() - dataLimiteDevolucao).days }
        return diasMulta * 2.50
    }

}
