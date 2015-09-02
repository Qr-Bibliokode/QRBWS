package groovy.qrbws.domain

import grails.test.mixin.TestFor
import org.apache.commons.lang.StringUtils
import qrbws.Book
import spock.lang.Specification

@TestFor(Book)
class BookSpec extends Specification {

    Book book

    def setup() {
        book = new Book()
    }

    void "Test that isbn can't be null or blank"() {

        when: 'the isbn is null'
        book.title = 'Book Test'
        book.isbn = null

        then: 'validation should fail'
        !book.validate()

        when: 'the isbn is blank'
        book.title = 'Book Test'
        book.isbn = ' '

        then: 'validation should fail'
        !book.validate()

        when: 'the isbn is filled'
        book.title = 'Book Test'
        book.isbn = '1'

        then: 'validation should pass'
        book.validate()
    }

    void "Test that isbn can't have more than 17 characters"() {

        when: 'the isbn have 18 characters'
        book.title = 'Book Test'
        book.isbn = StringUtils.leftPad("", 18, '1')

        then: 'validation should fail'
        !book.validate()

        when: 'the isbn have 17 characters'
        book.title = 'Book Test'
        book.isbn = StringUtils.leftPad("", 17, '1')

        then: 'validation should pass'
        book.validate()
    }

    void "Test that isbn is unique"() {

        when: 'the isbn repeated try save'
        new Book(title: 'Book Test', isbn: '123').save(flush: true)
        book.title = 'Book Copy'
        book.isbn = '123'

        then: 'validation should fail'
        !book.validate()
    }

    void "Test that title can't be null or blank"() {

        when: 'the title is null'
        book.title = null
        book.isbn = '123'

        then: 'validation should fail'
        !book.validate()

        when: 'the title is blank'
        book.title = ' '
        book.isbn = '123'

        then: 'validation should fail'
        !book.validate()

        when: 'the isbn is filled'
        book.title = 'Book Test'
        book.isbn = '123'

        then: 'validation should pass'
        book.validate()
    }

    void "Test that title can't have more than 254 characters"() {

        when: 'the title have 255 characters'
        book.title = StringUtils.leftPad("", 255, '*')
        book.isbn = '123'

        then: 'validation should fail'
        !book.validate()

        when: 'the title have 254 characters'
        book.title = StringUtils.leftPad("", 254, '*')
        book.isbn = '123'

        then: 'validation should pass'
        book.validate()
    }

    void "Test that title is unique"() {

        when: 'the isbn repeated try save'
        new Book(title: 'Book Test', isbn: '123').save(flush: true)
        book.title = 'Book Test'
        book.isbn = '123456'

        then: 'validation should fail'
        !book.validate()
    }

    void "Test that synopsis can be null"() {

        when: 'the synopsis is null'
        book.title = 'book'
        book.isbn = '123'
        book.synopsis = null

        then: 'validation should pass'
        book.validate()

        when: 'the synopsis is blank'
        book.title = ''
        book.isbn = '123'
        book.synopsis = ''

        then: 'validation should fail'
        !book.validate()
    }


    void "Test that synopsis can be between 5 and 5000 characters"() {

        when: 'the synopsis have 4 characters'
        book.title = 'Book'
        book.isbn = '123'
        book.synopsis = '1234'

        then: 'validation should fail'
        !book.validate()

        when: 'the synopsis have 5001 characters'
        book.title = 'title'
        book.isbn = '123'
        book.synopsis = StringUtils.leftPad("", 5001, '*')

        then: 'validation should fail'
        !book.validate()

        when: 'the synopsis have 5001 characters'
        book.title = 'title'
        book.isbn = '123'
        book.synopsis = '12345'

        then: 'validation should pass'
        book.validate()
    }

    void "Test that pages can be null"() {

        when: 'the pages is null'
        book.title = 'book'
        book.isbn = '123'
        book.pages = null

        then: 'validation should pass'
        book.validate()

    }

    void "Test that pages can have a maximum value of 5"() {

        when: 'the pages have value 4'
        book.title = 'Book'
        book.isbn = '123'
        book.pages = 1234

        then: 'validation should pass'
        book.validate()

        when: 'the pages have value 10000'
        book.title = 'title'
        book.isbn = '123'
        book.pages = 10000

        then: 'validation should fail'
        !book.validate()

        when: 'the synopsis have value 250'
        book.title = 'title'
        book.isbn = '123'
        book.pages = 250

        then: 'validation should pass'
        book.validate()
    }

    void "Test that pages can't have negative value"() {

        when: 'the pages have a negative value'
        book.title = 'Book'
        book.isbn = '123'
        book.pages = -1

        then: 'validation should fail'
        !book.validate()
    }
}
