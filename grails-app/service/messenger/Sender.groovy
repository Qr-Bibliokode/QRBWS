package messenger

import qrbws.UserAccount

interface Sender {
    void send(UserAccount user)
}
