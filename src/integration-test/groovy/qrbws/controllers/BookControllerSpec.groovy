package groovy.qrbws.controllers

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import qrbws.Book
import qrbws.BookController
import spock.lang.Specification

@TestFor(BookController)
@Mock(Book)
class BookControllerSpec extends Specification {

    Book book

    def setup() {
        Book.withNewSession() { session ->
            book = new Book(title: 'Book Test', isbn: '123').save()
        }
    }

    void "test allowed methods"() {
        when:
        def allowedMethods = controller.allowedMethods

        then:
        allowedMethods == [save: 'POST', update: 'PUT', delete: 'DELETE']
    }

    void "test index() include a book"() {
        given:
        book.save()

        when:
        response.format = 'json'
        controller.index()

        then:
        response.status == 200
        Book bookResponse = JSON.parse(response.contentAsString)
        bookResponse.title == book.title
        bookResponse.isbn == book.isbn
    }

    void "test update is called after persist"() {
        when:
        book.title = "Book Edited"
        book.save()
        response.format = 'json'
        controller.show(book)

        then:
        response.status == 200
        Book bookResponse = JSON.parse(response.contentAsString)
        bookResponse.title == book.title
        bookResponse.isbn == book.isbn
    }

    void "test show() return a book when is called"() {
        when:
        response.format = 'json'
        controller.show(book)

        then:
        response.status == 200
        Book bookResponse = JSON.parse(response.contentAsString)
        bookResponse.title == book.title
        bookResponse.isbn == book.isbn
    }

    void "test create() return a book"() {
        when:
        response.format = 'json'
        params.title = "Book Created"
        params.isbn = "321"
        controller.create()

        then:
        response.status == 200
        Book bookResponse = JSON.parse(response.contentAsString)
        bookResponse.title == params.title
        bookResponse.isbn == params.isbn
    }

    void "test save() persist a book"() {
        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(book)

        then:
        response.status == 201
        Book bookResponse = JSON.parse(response.contentAsString)
        bookResponse.title == book.title
        bookResponse.isbn == book.isbn
    }

    void "test edit() is called after persist"() {
        when:
        book.title = "Other Book"
        book.isbn = "123456"
        response.format = 'json'
        controller.edit(book)

        then:
        response.status == 200
        Book bookResponse = JSON.parse(response.contentAsString)
        bookResponse.title == book.title
        bookResponse.isbn == book.isbn
    }

    void "test delete() is called after persist"() {
        when:
        request.method = 'DELETE'
        response.format = 'json'
        controller.delete(book)

        then:
        response.status == 204
        response.contentAsString == ''

        when:
        controller.show(book)

        then:
        response.status == 204
    }


    void "test return 'not found' when try delete an inexistent book"() {
        when:
        request.method = 'DELETE'
        response.format = 'json'
        controller.delete()

        then:
        response.status == 404
    }

    void "test return 'not found' when try save an inexistent book"() {
        given:
        def book

        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(book)

        then:
        response.status == 404
    }

    void "test return 'not found' when try update an inexistent book"() {
        given:
        def book

        when:
        request.method = 'PUT'
        response.format = 'json'
        controller.update(book)

        then:
        response.status == 404
    }
}