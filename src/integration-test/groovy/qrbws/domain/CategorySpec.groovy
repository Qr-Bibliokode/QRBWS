package groovy.qrbws.domain

import grails.test.mixin.TestFor
import org.apache.commons.lang.StringUtils
import qrbws.Category
import spock.lang.Specification

@TestFor(Category)
class CategorySpec extends Specification {

    Category category

    def setup() {
        category = new Category()
    }

    void "Test description can't be null"() {

        when: 'description be null'
        category.description = null

        then: 'validation should fail'
        !category.validate()

        when: 'description be filled'
        category.description = 'Science'

        then: 'validation should pass'
        category.validate()
    }

    void "Test description can not exceed 254 characters"() {

        when: 'description be 255 characers'
        category.description = StringUtils.leftPad("", 255, '*')

        then: 'validation should fail'
        !category.validate()

        when: 'description be 254 characers'
        category.description = StringUtils.leftPad("", 254, '*')

        then: 'validation should pass'
        category.validate()
    }

}
