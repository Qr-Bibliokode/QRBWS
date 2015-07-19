package qrbws

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

import static org.springframework.http.HttpStatus.CREATED

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(AuthorController)
@Mock(Author)
class AuthorControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test a valid request method"() {
        when:
        request.method = 'POST'
        controller.save(new Author(name: 'AuthorTest'))

        then:
        assert response.status == CREATED.value()
    }
}