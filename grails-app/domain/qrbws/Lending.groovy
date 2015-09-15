package qrbws

class Lending {

    Date dateOut
    Date dateIn
    boolean returned
    boolean avised

    UserAccount userAccount
    Book book

    Lending() {
        this.dateIn = new Date()
    }

    static constraints = {
        userAccount nullable: false
        book nullable: false
        dateOut nullable: true
    }
}
