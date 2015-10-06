package qrbws

import grails.transaction.Transactional
import groovy.time.TimeCategory

@Transactional
class EmprestimoService {

    StockService stockService
    MultaService multaService
    FeriadoService feriadoService
    ReservaService reservaService

    Emprestimo emprestar(Emprestimo emprestimo) {
        if (!validaEmprestimo(emprestimo).hasErrors()) {
            montaDatasEmprestimo(emprestimo)
            emprestimo.save flush: true
            descontaStock(emprestimo.livro)
        }
        emprestimo
    }

    def devolver(Emprestimo emprestimo) {
        // TODO: Verificar se tem que gerar multa
        emprestimo.dataDevolucao = new Date()
        emprestimo.devolvido = true
        incrementaStock(emprestimo.livro)
        emprestimo
    }

    Boolean temMultasSemPagar(ContaUsuario contaUsuario) {
        multaService.temMultasSemPagar(contaUsuario)
    }

    Date calcularDataDevolucao() {
        feriadoService.calcularDataDevolucao()
    }

    Boolean temStock(Livro livro) {
        stockService.temStock(livro)
    }

    Boolean temEmprestimoForaDeData(ContaUsuario contaUsuario) {
        boolean temEmprestimoForaDeData
        Emprestimo.findAllByContaUsuarioAndDevolvido(contaUsuario, false).find {
            if (use(TimeCategory) { new Date() - 1.day }.after(it.dataLimiteDevolucao)) {
                temEmprestimoForaDeData = true
            }
        }
        return temEmprestimoForaDeData
    }

    Boolean excedeLimiteEmprestimos(ContaUsuario contaUsuario) {
        Emprestimo.findAllByContaUsuarioAndDevolvido(contaUsuario, false).size() >= 3
    }

    Boolean existemReservasAtivasSuperiorADisponivel(Livro livro) {
        reservaService.existemReservasAtivasSuperiorADisponivel(livro) >= Stock.findByLivro(livro).disponivel
    }

    void descontaStock(Livro livro) {
        stockService.desconta(livro)
    }

    void incrementaStock(Livro livro) {
        stockService.incrementa(livro)
    }

    void montaDatasEmprestimo(Emprestimo emprestimo) {
        emprestimo.dataEmprestimo = new Date()
        emprestimo.dataLimiteDevolucao = calcularDataDevolucao()
    }

    Emprestimo validaEmprestimo(Emprestimo emprestimo) {
        if (temMultasSemPagar(emprestimo.contaUsuario)) {
            emprestimo.errors.reject('contausuario.multa.contem', ['emprestimo', 'class Emprestimo'] as Object[], null)
        }

        if (!temStock(emprestimo.livro)) {
            emprestimo.errors.reject('stock.livro.indisponivel', ['emprestimo', 'class Emprestimo'] as Object[], null)
        }

        if (temEmprestimoForaDeData(emprestimo.contaUsuario)) {
            emprestimo.errors.reject('emprestimo.invalido.passou.data.devolucao', ['emprestimo', 'class Emprestimo'] as Object[], null)
        }

        if (excedeLimiteEmprestimos(emprestimo.contaUsuario)) {
            emprestimo.errors.reject('emprestimo.invalido.passou.limite.emprestimos', ['emprestimo', 'class Emprestimo'] as Object[], null)
        }

        if (existemReservasAtivasSuperiorADisponivel(emprestimo.livro)) {
            emprestimo.errors.reject('emprestimo.invalido.existem.reservas', ['emprestimo', 'class Emprestimo'] as Object[], null)
        }
        emprestimo

    }

    // TODO: Implementar função para realizar renovação da devolução

}
