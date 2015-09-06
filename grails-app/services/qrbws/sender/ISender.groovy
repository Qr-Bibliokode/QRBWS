package qrbws.sender

import qrbws.UserAccount

interface ISender {
    void send(UserAccount user)
}
