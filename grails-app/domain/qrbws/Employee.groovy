package qrbws

class Employee extends Person{

    String code

    static constraints = {
        code unique: true, blank: false, size: 5..5
    }
}
