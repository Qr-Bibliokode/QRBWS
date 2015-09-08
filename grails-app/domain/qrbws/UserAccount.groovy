package qrbws

class UserAccount {

    String login
    String password
    Person person
    Boolean enabled

    UserAccount() {
        this.enabled = true
    }

    static constraints = {
        login blank: false, unique: true, size: 5..20
        password blank: false, size: 5..20
    }
}
