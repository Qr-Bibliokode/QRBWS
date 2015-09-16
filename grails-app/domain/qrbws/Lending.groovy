package qrbws

class Lending {

    // TODO: Change the name of dateOut and dateIn
    Date dateOut
    Date dateIn
    Date dateLimit
    boolean returned
    boolean avised

    UserAccount userAccount
    Book book

    static constraints = {
        userAccount nullable: false
        book nullable: false
        dateOut nullable: true
        dateIn nullable: true
        //TODO: Change this, this date should be calculated and nullable false
        dateLimit nullable: true
    }
}
