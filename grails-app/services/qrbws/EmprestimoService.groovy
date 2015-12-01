package qrbws

import grails.transaction.Transactional
import groovy.time.TimeCategory

import static qrbws.Solicitacao.*

@Transactional
class EmprestimoService {

    EstoqueService estoqueService
    MultaService multaService
    ContaUsuarioService contaUsuarioService
    FeriadoService feriadoService
    ReservaService reservaService

    Emprestimo emprestar(Emprestimo emprestimo) {
        if (!validaEmprestimo(emprestimo)?.hasErrors()) {
            verificaSolicitacaoEmprestimo(emprestimo, EMPRESTIMO)
            montaDatasEmprestimo(emprestimo)
            if (emprestimo.solicitacao.liberada && emprestimo.solicitacao.ativa) {
                descontaEstoque(emprestimo.livro)
                emprestimo.solicitacao.ativa = false
            }
            emprestimo.save flush: true
        }
        emprestimo
    }

    def devolver(Emprestimo emprestimo) {
        contaUsuarioService.verificarMultas(emprestimo.contaUsuario.id)
        if (emprestimo.devolvido) {
            emprestimo.errors.reject('emprestimo.invalido.ja.devolvido')
            return emprestimo
        }
        verificaSolicitacaoEmprestimo(emprestimo, DEVOLUCAO)
        if (emprestimo.solicitacao.liberada && emprestimo.solicitacao.ativa) {
            emprestimo.dataDevolucao = new Date()
            emprestimo.devolvido = true
            incrementaEstoque(emprestimo.livro)
        }
        emprestimo.save flush: true
    }

    Emprestimo renovar(Emprestimo emprestimo) {
        if (temMultasSemPagar(emprestimo.contaUsuario)) {
            emprestimo.errors.reject('contausuario.multa.contem')
            return emprestimo
        }

        if (emprestimo.renovacoes > 0) {
            // TODO: Incluir este parâmetro na tabela configurações da biblioteca
            emprestimo.errors.reject('emprestimo.invalido.passou.renovacoes', [1] as Object[], null)
        } else {
            if (emprestimo.solicitacao.liberada) {
                emprestimo.devolvido = false
                emprestimo.dataLimiteDevolucao = feriadoService.calcularDataDevolucao()
                emprestimo.dataDevolucao = null
                emprestimo.renovacoes++
                emprestimo.save flush: true
            } else {
                solicitaLiberacao(emprestimo, RENOVACAO)
            }
        }
        emprestimo
    }

    Emprestimo liberar(Emprestimo emprestimo) {
        liberaSolicitacao(emprestimo)
        switch (emprestimo.solicitacao.tipo) {
            case EMPRESTIMO:
                emprestar(emprestimo)
                break
            case DEVOLUCAO:
                devolver(emprestimo)
                break
            case RENOVACAO:
                renovar(emprestimo)
                break
        }
    }

    Emprestimo liberaSolicitacao(Emprestimo emprestimo) {
        emprestimo.solicitacao.liberada = true
        emprestimo.solicitacao.ativa = true
        emprestimo.save flush: true
    }

    Boolean temMultasSemPagar(ContaUsuario contaUsuario) {
        multaService.temMultasSemPagar(contaUsuario)
    }

    Date calcularDataDevolucao() {
        feriadoService.calcularDataDevolucao()
    }

    Boolean temEstoque(Livro livro) {
        estoqueService.temEstoque(livro)
    }

    Boolean temEmprestimoForaDeData(ContaUsuario contaUsuario, temEmprestimoForaDeData = null) {
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

    Boolean existemReservasAtivasSuperiorAEstoqueDisponivel(Livro livro) {
        reservaService.existemReservasAtivasSuperiorADisponivel(livro) >= Estoque.findByLivro(livro).disponivel
    }


    int existeEmprestimoAtivoComMesmoLivro(Emprestimo emprestimo) {
        Emprestimo.findAllByContaUsuarioAndLivroAndDevolvidoAndIdNotEqual(
                emprestimo.contaUsuario,
                emprestimo.livro,
                false,
                emprestimo.id ?: 0).size()
    }

    void descontaEstoque(Livro livro) {
        estoqueService.desconta(livro)
    }

    void incrementaEstoque(Livro livro) {
        estoqueService.incrementa(livro)
    }

    void montaDatasEmprestimo(Emprestimo emprestimo) {
        emprestimo.dataEmprestimo = new Date()
        emprestimo.dataLimiteDevolucao = calcularDataDevolucao()
    }

    Emprestimo validaEmprestimo(Emprestimo emprestimo) {
        if (!emprestimo.contaUsuario.enabled) {
            emprestimo.errors.reject('contausuario.desabilitada')
            return emprestimo
        }

        if (temMultasSemPagar(emprestimo.contaUsuario)) {
            emprestimo.errors.reject('contausuario.multa.contem')
            return emprestimo
        }

        if (!temEstoque(emprestimo.livro)) {
            emprestimo.errors.reject('estoque.livro.indisponivel')
            return emprestimo
        }

        if (temEmprestimoForaDeData(emprestimo.contaUsuario)) {
            emprestimo.errors.reject('emprestimo.invalido.passou.data.devolucao')
            return emprestimo
        }

        if (excedeLimiteEmprestimos(emprestimo.contaUsuario)) {
            emprestimo.errors.reject('emprestimo.invalido.passou.limite.emprestimos')
            return emprestimo
        }

        if (existemReservasAtivasSuperiorAEstoqueDisponivel(emprestimo.livro)) {
            emprestimo.errors.reject('emprestimo.invalido.existem.reservas')
            return emprestimo
        }

        if (existeEmprestimoAtivoComMesmoLivro(emprestimo)) {
            emprestimo.errors.reject('emprestimo.invalido.ja.existe.ativo')
            return emprestimo
        }
        emprestimo
    }


    Emprestimo verificaSolicitacaoEmprestimo(Emprestimo emprestimo, String tipo) {
        if (!emprestimo.solicitacao?.ativa) {
            // TODO: Verificar usuário logado, se for administrador a solicitação terá que ser liberada
            emprestimo = solicitaLiberacao(emprestimo, tipo)
        } else {
            emprestimo = atualizaSolicitacao(emprestimo, tipo)
        }
        emprestimo
    }

    Emprestimo solicitaLiberacao(Emprestimo emprestimo, String tipo) {
        if (emprestimo.solicitacao?.id) {
            emprestimo.solicitacao.tipo = tipo
            emprestimo.solicitacao.liberada = false
            emprestimo.solicitacao.ativa = true
        } else {
            emprestimo.solicitacao = new Solicitacao(tipo: tipo, liberada: false, ativa: true)
        }
        emprestimo.save(flush: true)
    }

    Emprestimo atualizaSolicitacao(Emprestimo emprestimo, String tipo) {
        emprestimo.solicitacao.tipo = tipo
        emprestimo.solicitacao.ativa = true
        emprestimo.save(flush: true)
    }

    List<Emprestimo> obtenhaHistoricoEmprestimosPorLivro(Livro livro) {
        Emprestimo.findAllByLivro(livro)
    }
// TODO: Implementar função que avisa usuário
}
