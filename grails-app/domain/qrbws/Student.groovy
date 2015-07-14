package qrbws

class Student extends Person{

    String enrollment

    static constraints = {
        enrollment unique: true, maxSize: 5
    }
}
