package groovy.qrbws.domain

import grails.test.mixin.TestFor
import org.apache.commons.lang.StringUtils
import qrbws.Author
import spock.lang.Specification

@TestFor(Author)
public class AuthorSpec extends Specification {

    void "Test that name must begin with an upper case letter"() {
        when: 'the name begins with a lower letter'
        Author author = new Author(name: 'ferran', notes: 'best author in 2009')

        then: 'validation should pass'
        author.validate()

        when: 'the name begins with an upper case letter'
        author = new Author(name: 'Ferran', notes: 'best author of world')

        then: 'validation should pass'
        author.validate()
    }

    void "Test that name don't can have numbers"() {

        when: 'the name have a number'
        Author author = new Author(name: 'ferran1989', notes: 'best author in 2009')

        then: 'validation should fail'
        !author.validate()
    }

    void "Test notes can be null"() {

        when: 'notes be null'
        Author author = new Author(name: 'ferran', notes: null)

        then: 'validation should pass'
        author.validate()
    }

    void "Test name can't be null"() {

        when: 'name be null'
        Author author = new Author(name: null)

        then: 'validation should fail'
        !author.validate()
    }

    void "Test name can't be blank"() {

        when: 'name be blank'
        Author author = new Author(name: '')

        then: 'validation should fail'
        !author.validate()
    }

    void "Test name can not exceed 254 characters"() {

        when: 'name be 255 characers'
        Author author = new Author(name: StringUtils.leftPad("", 255, '*'))

        then: 'validation should fail'
        !author.validate()
    }
}
