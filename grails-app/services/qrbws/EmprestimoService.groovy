package qrbws

import grails.transaction.Transactional

@Transactional
class EmprestimoService {

    StockService stockService
    MultaService multaService
    FeriadoService feriadoService

    Emprestimo emprestar(Emprestimo emprestimo) {
        // TODO: Verificar se o usuário tem algum livro estorando o limite de devolução
        // TODO: Verificar se o usuário excede o limite de livros emprestados (3)
        // TODO: Verificar se tem stock
        // TODO: Verificar do estoque disponível deste livro, quantas reservas ativas tem

        if (verificarTemMultasSemPagar(emprestimo.contaUsuario)) {
            // TODO: Implementar que deve ser feito
        } else {
            emprestimo.dateEmprestimo = new Date()
            emprestimo.dateDevolucao = calcularDataDevolucao()
            emprestimo.save flush: true
            stockService.desconta(emprestimo)
        }
        emprestimo
    }

    Emprestimo devolution(Emprestimo emprestimo) {
        if (verificarTemMultasSemPagar(emprestimo.contaUsuario)) {
            // TODO: Implementar que deve ser feito
        } else {
            emprestimo.dateDevolucao = new Date()
            emprestimo.devolvido = true
            stockService.incrementa(emprestimo)
        }
        emprestimo
    }

    Boolean verificarTemMultasSemPagar(ContaUsuario contaUsuario) {
        multaService.verificarTemMultasSemPagar(contaUsuario)
    }

    Date calcularDataDevolucao() {
        feriadoService.calcularDataDevolucao()
    }

    // TODO: Implementar função para realizar renovação da devolução

}
