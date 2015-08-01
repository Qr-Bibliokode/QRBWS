package groovy.qrbws.controllers

import grails.converters.JSON
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import qrbws.Holiday
import qrbws.HolidayController
import spock.lang.Specification

@TestFor(HolidayController)
@Mock(Holiday)
class HolidayControllerSpec extends Specification {

    def holiday = new Holiday()

    def setup() {
        Holiday.withNewSession() { session ->
            holiday = new Holiday(description: 'Dia das mães', startDate: new Date('10/07/2015'), finalDate: new Date('10/07/2015')).save()
        }
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
        Holiday holidayResponse = JSON.parse(response.contentAsString)
        assert holidayResponse.description.equals('Dia das mães')
    }

    void "test update is called after persist"() {
        when:
        holiday.description = "Dia dos pais"
        holiday.save()
        response.format = 'json'
        controller.show(holiday)

        then:
        Holiday holidayResponse = JSON.parse(response.contentAsString)
        assert holidayResponse.description.equals('Dia dos pais')
    }

    void "test show() return a holiday when is called"() {
        when:
        response.format = 'json'
        controller.show(holiday)

        then:
        response.status == 200
        Holiday holidayResponse = JSON.parse(response.contentAsString)
        assert holidayResponse.description.equals('Dia das mães')
    }

    void "test create() return a holiday"() {
        when:
        response.format = 'json'
        params.description = "Day of dor"
        controller.create()

        then:
        response.status == 200
        Holiday holidayResponse = JSON.parse(response.contentAsString)
        assert holidayResponse.description.equals('Day of dor')
    }

    void "test save() persist a holiday"() {
        when:
        request.method = 'POST'
        response.format = 'json'
        controller.save(holiday)

        then:
        response.status == 201
        Holiday holidayResponse = JSON.parse(response.contentAsString)
        assert holidayResponse.description.equals('Dia das mães')
    }

    void "test edit() is called after persist"() {
        when:
        holiday.description = "123123"
        response.format = 'json'
        controller.edit(holiday)

        then:
        response.status == 200
        Holiday holidayResponse = JSON.parse(response.contentAsString)
        assert holidayResponse.description.equals('123123')
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