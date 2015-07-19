package qrbws

import grails.rest.Resource

@Resource(uri='/student')
class Student extends Person{

    String enrollment

    static constraints = {
        enrollment unique: true, maxSize: 5
    }
}
