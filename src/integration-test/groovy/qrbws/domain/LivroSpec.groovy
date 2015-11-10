package qrbws.domain

import grails.test.mixin.TestFor
import org.apache.commons.lang.StringUtils
import qrbws.Livro
import qrbws.Estoque
import spock.lang.Specification

@TestFor(Livro)
class LivroSpec extends Specification {

    Livro book
    Estoque estoque

    def setup() {
        estoque = new Estoque(disponivel: 10, total: 10)
        book = new Livro(estoque: estoque)
    }

    void "Test that isbn can't be null or blank"() {

        when: 'the isbn is null'
        book.titulo = 'Livro Test'
        book.isbn = null

        then: 'validation should fail'
        !book.validate()

        when: 'the isbn is blank'
        book.titulo = 'Livro Test'
        book.isbn = ' '

        then: 'validation should fail'
        !book.validate()

        when: 'the isbn is filled'
        book.titulo = 'Livro Test'
        book.isbn = '1'

        then: 'validation should pass'
        book.validate()
    }

    void "Test that isbn can't have more than 17 characters"() {

        when: 'the isbn have 18 characters'
        book.titulo = 'Livro Test'
        book.isbn = StringUtils.leftPad("", 18, '1')

        then: 'validation should fail'
        !book.validate()

        when: 'the isbn have 17 characters'
        book.titulo = 'Livro Test'
        book.isbn = StringUtils.leftPad("", 17, '1')

        then: 'validation should pass'
        book.validate()
    }

    void "Test that isbn is unique"() {

        when: 'the isbn repeated try save'
        new Livro(titulo: 'Livro Test', isbn: '123', estoque: estoque).save(flush: true)
        book.titulo = 'Livro Copy'
        book.isbn = '123'

        then: 'validation should fail'
        !book.validate()
    }

    void "Test that title can't be null or blank"() {

        when: 'the titulo is null'
        book.titulo = null
        book.isbn = '123'

        then: 'validation should fail'
        !book.validate()

        when: 'the titulo is blank'
        book.titulo = ' '
        book.isbn = '123'

        then: 'validation should fail'
        !book.validate()

        when: 'the isbn is filled'
        book.titulo = 'Livro Test'
        book.isbn = '123'

        then: 'validation should pass'
        book.validate()
    }

    void "Test that title can't have more than 254 characters"() {

        when: 'the titulo have 255 characters'
        book.titulo = StringUtils.leftPad("", 255, '*')
        book.isbn = '123'

        then: 'validation should fail'
        !book.validate()

        when: 'the titulo have 254 characters'
        book.titulo = StringUtils.leftPad("", 254, '*')
        book.isbn = '123'

        then: 'validation should pass'
        book.validate()
    }

    void "Test that title is unique"() {

        when: 'the isbn repeated try save'
        new Livro(titulo: 'Livro Test', isbn: '123', estoque: estoque).save(flush: true)
        book.titulo = 'Livro Test'
        book.isbn = '123456'

        then: 'validation should fail'
        !book.validate()
    }

    void "Test that synopsis can be null"() {

        when: 'the sinopse is null'
        book.titulo = 'book'
        book.isbn = '123'
        book.sinopse = null

        then: 'validation should pass'
        book.validate()

        when: 'the sinopse is blank'
        book.titulo = ''
        book.isbn = '123'
        book.sinopse = ''

        then: 'validation should fail'
        !book.validate()
    }


    void "Test that synopsis can be between 5 and 5000 characters"() {

        when: 'the sinopse have 4 characters'
        book.titulo = 'Livro'
        book.isbn = '123'
        book.sinopse = '1234'

        then: 'validation should fail'
        !book.validate()

        when: 'the sinopse have 5001 characters'
        book.titulo = 'titulo'
        book.isbn = '123'
        book.sinopse = StringUtils.leftPad("", 5001, '*')

        then: 'validation should fail'
        !book.validate()

        when: 'the sinopse have 5001 characters'
        book.titulo = 'titulo'
        book.isbn = '123'
        book.sinopse = '12345'

        then: 'validation should pass'
        book.validate()
    }

    void "Test that pages can be null"() {

        when: 'the paginas is null'
        book.titulo = 'book'
        book.isbn = '123'
        book.paginas = null

        then: 'validation should pass'
        book.validate()

    }

    void "Test that pages can have a maximum value of 5"() {

        when: 'the paginas have value 4'
        book.titulo = 'Livro'
        book.isbn = '123'
        book.paginas = 1234

        then: 'validation should pass'
        book.validate()

        when: 'the paginas have value 10000'
        book.titulo = 'titulo'
        book.isbn = '123'
        book.paginas = 10000

        then: 'validation should fail'
        !book.validate()

        when: 'the sinopse have value 250'
        book.titulo = 'titulo'
        book.isbn = '123'
        book.paginas = 250

        then: 'validation should pass'
        book.validate()
    }

    void "Test that pages can't have negative value"() {

        when: 'the paginas have a negative value'
        book.titulo = 'Livro'
        book.isbn = '123'
        book.paginas = -1

        then: 'validation should fail'
        !book.validate()
    }
}
