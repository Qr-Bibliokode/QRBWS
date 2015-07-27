package groovy.qrbws.domain

import grails.test.mixin.TestFor
import org.apache.commons.lang.StringUtils
import qrbws.Author
import spock.lang.Specification

@TestFor(Author)
public class AuthorSpec extends Specification {

    void "Test that name must begin with an upper case letter"() {
        when: 'the name begins with a lower letter'
        def p = new Author(name: 'ferran', notes: 'best author in 2009')

        then: 'validation should pass'
        p.validate()

        when: 'the name begins with an upper case letter'
        p = new Author(name: 'Ferran', notes: 'best author of world')

        then: 'validation should pass'
        p.validate()
    }

    void "Test that name don't can have numbers"() {

        when: 'the name have a number'
        def p = new Author(name: 'ferran1989', notes: 'best author in 2009')

        then: 'validation should fail'
        !p.validate()
    }

    void "Test notes can be null"() {

        when: 'notes be null'
        def p = new Author(name: 'ferran', notes: null)

        then: 'validation should pass'
        p.validate()
    }

    void "Test name can't be null"() {

        when: 'name be null'
        def p = new Author(name: null)

        then: 'validation should fail'
        !p.validate()
    }

    void "Test name can't be blank"() {

        when: 'name be blank'
        def p = new Author(name: '')

        then: 'validation should fail'
        !p.validate()
    }

    void "Test name can not exceed 254 characters"() {

        when: 'name be 255 characers'
        def p = new Author(name: StringUtils.leftPad("", 255, '*'))

        then: 'validation should fail'
        !p.validate()
    }
}
