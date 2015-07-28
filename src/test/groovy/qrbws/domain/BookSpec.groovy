package groovy.qrbws.domain

import grails.test.mixin.TestFor
import org.apache.commons.lang.StringUtils
import qrbws.Book
import spock.lang.Specification

@TestFor(Book)
class BookSpec extends Specification {
    void "Test that isbn can't be null or blank"() {
        given:
        def book

        when: 'the isbn is null'
        book = new Book(title: 'Book Test', isbn: null)

        then: 'validation should fail'
        !book.validate()

        when: 'the isbn is blank'
        book = new Book(title: 'Book Test', isbn: '')

        then: 'validation should fail'
        !book.validate()

        when: 'the isbn is filled'
        book = new Book(title: 'Book Test', isbn: '1')

        then: 'validation should pass'
        book.validate()
    }

    void "Test that isbn can't have more than 17 characters"() {
        given:
        def book

        when: 'the isbn have 18 characters'
        book = new Book(title: 'Book Test', isbn: StringUtils.leftPad("", 18, '*'))

        then: 'validation should fail'
        !book.validate()

        when: 'the isbn have 17 characters'
        book = new Book(title: 'Book Test', isbn: StringUtils.leftPad("", 17, '*'))

        then: 'validation should pass'
        book.validate()
    }

    void "Test that isbn is unique"() {
        given:
        new Book(title: 'Book Test', isbn: '123').save(flush: true)
        def bookCopy

        when: 'the isbn repeated try save'
        bookCopy = new Book(title: 'Book Copy', isbn: '123')

        then: 'validation should fail'
        !bookCopy.validate()
    }

    void "Test that title can't be null or blank"() {
        given:
        def book

        when: 'the title is null'
        book = new Book(title: null, isbn: '123')

        then: 'validation should fail'
        !book.validate()

        when: 'the title is blank'
        book = new Book(title: '', isbn: '123')

        then: 'validation should fail'
        !book.validate()

        when: 'the isbn is filled'
        book = new Book(title: 'Book Test', isbn: '123')

        then: 'validation should pass'
        book.validate()
    }

    void "Test that title can't have more than 254 characters"() {
        given:
        def book

        when: 'the title have 255 characters'
        book = new Book(title: StringUtils.leftPad("", 255, '*'), isbn: '123')

        then: 'validation should fail'
        !book.validate()

        when: 'the title have 254 characters'
        book = new Book(title: StringUtils.leftPad("", 254, '*'), isbn: '123')

        then: 'validation should pass'
        book.validate()
    }

    void "Test that title is unique"() {
        given:
        book = new Book(title: 'Book Test', isbn: '123').save(flush: true)
        def bookCopy

        when: 'the isbn repeated try save'
        bookCopy = new Book(title: 'Book Test', isbn: '123456')

        then: 'validation should fail'
        !bookCopy.validate()
    }

    void "Test that synopsis can be null"() {
        given:
        def book

        when: 'the synopsis is null'
        book = new Book(title: 'book', isbn: '123', synopsis: null)

        then: 'validation should pass'
        book.validate()

        when: 'the synopsis is blank'
        book = new Book(title: '', isbn: '123', synopsis: '')

        then: 'validation should fail'
        !book.validate()
    }


    void "Test that synopsis can be between 5 and 5000 characters"() {
        given:
        def book

        when: 'the synopsis have 4 characters'
        book = new Book(title: 'Book', isbn: '123', synopsis: '1234')

        then: 'validation should fail'
        !book.validate()

        when: 'the synopsis have 5001 characters'
        book = new Book(title: 'title', isbn: '123', synopsis: StringUtils.leftPad("", 5001, '*'))

        then: 'validation should fail'
        !book.validate()

        when: 'the synopsis have 5001 characters'
        book = new Book(title: 'title', isbn: '123', synopsis: '12345')

        then: 'validation should pass'
        book.validate()
    }

    void "Test that pages can be null"() {
        given:
        def book

        when: 'the pages is null'
        book = new Book(title: 'book', isbn: '123', pages: null)

        then: 'validation should pass'
        book.validate()

    }

    void "Test that pages can have a maximum value of 5"() {
        given:
        def book

        when: 'the pages have value 4'
        book = new Book(title: 'Book', isbn: '123', pages: 1234)

        then: 'validation should pass'
        book.validate()

        when: 'the pages have value 10000'
        book = new Book(title: 'title', isbn: '123', pages: 10000)

        then: 'validation should fail'
        !book.validate()

        when: 'the synopsis have value 250'
        book = new Book(title: 'title', isbn: '123', pages: 250)

        then: 'validation should pass'
        book.validate()
    }

    void "Test that pages can't have negative value"() {
        given:
        def book

        when: 'the pages have a negative value'
        book = new Book(title: 'Book', isbn: '123', pages: -1)

        then: 'validation should fail'
        !book.validate()
    }
}
