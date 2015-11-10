package qrbws

import grails.transaction.Transactional

@Transactional
class EstoqueService {

    Estoque desconta(Livro livro) {
        Estoque estoque = Estoque.findByLivro(livro)
        estoque.disponivel = estoque.disponivel - 1
        estoque.save flush: true
    }

    Estoque incrementa(Livro livro) {
        Estoque estoque = Estoque.findByLivro(livro)
        estoque.disponivel = estoque.disponivel + 1
        estoque.save flush: true
    }

    boolean temEstoque(Livro livro) {
        Estoque estoque = Estoque.findByLivro(livro)
        estoque ? estoque.disponivel > 0 : false
    }
}
