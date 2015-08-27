package qrbws.sender.senders

import qrbws.UserAccount

interface Sender {
    void send(UserAccount user)
}
