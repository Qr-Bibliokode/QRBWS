package qrbws

import grails.rest.Resource

@Resource(uri = '/api/student', formats=['json'])
class Student extends Person {

    String enrollment

    static constraints = {
        enrollment unique: true, maxSize: 5, nullable: false, blank: false
    }
}
