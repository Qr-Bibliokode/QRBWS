package qrbws.sender

import qrbws.UserAccount

interface Sender {
    void send(UserAccount user)
}
