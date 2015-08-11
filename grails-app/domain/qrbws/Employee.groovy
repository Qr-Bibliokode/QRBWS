package qrbws

import grails.rest.Resource

@Resource(uri = '/api/employee', formats=['json'])
class Employee extends Person {

    String code

    static constraints = {
        code unique: true, blank: false, maxSize: 5
    }
}
