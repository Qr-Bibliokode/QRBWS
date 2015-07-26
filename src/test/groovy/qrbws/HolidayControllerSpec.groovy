package groovy.qrbws

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import qrbws.Holiday
import qrbws.HolidayController
import spock.lang.Specification

@TestFor(HolidayController)
@Mock(Holiday)
class HolidayControllerSpec extends Specification {

    def holiday

    def setup() {
        Holiday.withNewSession() { session ->
            holiday = new Holiday(description: 'Dia das mães', startDate: new Date('10/07/2015'),finalDate:new Date('10/07/2015')).save()
        }
    }

    String makeJson(def value) {
        """{"class":"qrbws.Holiday","id":1,"description":"${value}","finalDate":"2015-10-07T03:00:00Z","startDate":"2015-10-07T03:00:00Z"}"""
    }

    String makeJsonList(def value) {
        "[" + makeJson(value) + "]"
    }

    String makeJsonCreate(def value) {
        """{"class":"qrbws.Holiday","id":null,"description":"${value}","finalDate":null,"startDate":null}"""
    }

    void "test allowed methods"() {
        when:
        def allowedMethods = controller.allowedMethods

        then:
        allowedMethods == [save: 'POST', update: 'PUT', delete: 'DELETE']
    }

    void "test index() include a holiday"() {
        given:
        holiday.save()

        when:
        response.format = 'json'
        controller.index(1)

        then:
        response.status == 200
        response.contentAsString == makeJsonList(holiday.description)
    }

    void "test update is called after persist"() {
        when:
        holiday.description = "Dia dos pais"
        holiday.save()
        response.format = 'json'
        controller.show(holiday)

        then:
        response.contentAsString == makeJson(holiday.description)
    }

    void "test show() return a holiday when is called"() {
        when:
        response.format = 'json'
        controller.show(holiday)

        then:
        response.status == 200
        response.contentAsString == makeJson(holiday.description)
    }

    void "test create() return a holiday"() {
        when:
        response.format = 'json'
        params.description = "Dia da dor"
        controller.create()

        then:
        response.status == 200
        response.contentAsString == makeJsonCreate(params.description)
    }

    void "test save() persist a holiday"() {
        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(holiday)

        then:
        response.status == 201
        response.contentAsString == makeJson(holiday.description)
    }

    void "test edit() is called after persist"() {
        when:
        holiday.description = "123123"
        response.format = 'json'
        controller.edit(holiday)

        then:
        response.status == 200
        response.contentAsString == makeJson(holiday.description)
    }

    void "test delete() is called after persist"() {
        when:
        request.method = 'DELETE'
        response.format = 'json'
        controller.delete(holiday)

        then:
        response.status == 204
        response.contentAsString == ''

        when:
        controller.show(holiday)

        then:
        response.status == 204
    }


    void "test return 'not found' when try delete an inexistent holiday"() {
        when:
        request.method = 'DELETE'
        response.format = 'json'
        controller.delete(null)

        then:
        response.status == 404
    }

    void "test return 'not found' when try save an inexistent holiday"() {
        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(null)

        then:
        response.status == 404
    }

    void "test return 'not found' when try update an inexistent holiday"() {
        when:
        request.method = 'PUT'
        response.format = 'json'
        controller.update(null)

        then:
        response.status == 404
    }
}