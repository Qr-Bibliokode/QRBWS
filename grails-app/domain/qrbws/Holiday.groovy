package qrbws

class Holiday {

    String description
    Date startDate
    Date finalDate

    static constraints = {
        description blank: false, maxSize: 45, matches: "[^0-9\\-]+"
    }
}
