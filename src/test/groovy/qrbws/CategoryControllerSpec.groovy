package qrbws

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

import static org.springframework.http.HttpStatus.CREATED

@TestFor(CategoryController)
@Mock(Category)
class CategoryControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test a valid request method"() {
        when:
        request.method = 'POST'
        controller.save(new Category(description: 'CategoryTest'))

        then:
        assert response.status == CREATED.value()
    }
}
