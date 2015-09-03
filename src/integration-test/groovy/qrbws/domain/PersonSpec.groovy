package qrbws.domain

import grails.test.mixin.TestFor
import org.apache.commons.lang.StringUtils
import qrbws.Person
import spock.lang.Specification

@TestFor(Person)
class PersonSpec extends Specification {

    Person person

    def setup() {
        person = new Person(name: "felansu", email: "gaferran@gmail.com")
    }

    void "Test that name must begin with an upper case letter"() {

        when: 'the name begins with a lower letter'
        person.name = 'ferran'

        then: 'validation should pass'
        person.validate()

        when: 'the name begins with an upper case letter'
        person.name = 'Ferran'

        then: 'validation should pass'
        person.validate()
    }

    void "Test that name don't can have numbers"() {

        when: 'the name have a number'
        person.name = 'ferran1989'

        then: 'validation should fail'
        !person.validate()
    }

    void "Test name can not exceed 254 characters"() {

        when: 'name be 255 characers'
        person.name = StringUtils.leftPad("", 255, '*')

        then: 'validation should fail'
        !person.validate()

        when: 'name be 254 characers'
        person.name = StringUtils.leftPad("", 254, '*')

        then: 'validation should pass'
        person.validate()
    }

    void "Test name can not be blank"() {

        when: 'name is blank'
        person.name = ' '

        then: 'validation should fail'
        !person.validate()
    }

    void "Test email can not be blank"() {

        when: 'email is blank'
        person.email = ' '

        then: 'validation should fail'
        !person.validate()
    }

    void "Test email is an email"() {

        when: 'email is not a email'
        person.email = 'reallyImNotAnEmail??'

        then: 'validation should fail'
        !person.validate()
    }


    void "Test email can not exceed 254 characters"() {

        when: 'email be 255 characers'
        person.email = StringUtils.leftPad("", 255, '*')

        then: 'validation should fail'
        !person.validate()

        when: 'email be 254 characers'
        person.email = StringUtils.leftPad("email@email.com", 254, '*')

        then: 'validation should pass'
        person.validate()
    }

    void "Test phone can not exceed 15 characters"() {

        when: 'phone be 16 characers'
        person.phone = StringUtils.leftPad("", 16, '*')

        then: 'validation should fail'
        !person.validate()

        when: 'phone be 15 characers'
        person.phone = StringUtils.leftPad("", 15, '*')

        then: 'validation should pass'
        person.validate()
    }
}
