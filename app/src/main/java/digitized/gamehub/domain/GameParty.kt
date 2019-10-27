package digitized.gamehub.domain

import java.util.*

class GameParty {

    private var name: String
    private var date: Date
    private var maxSize: Int
    private var participants: Array<User>

    constructor(name: String, date: Date, maxSize: Int, participants: Array<User>) {
        this.name = name
        this.date = date
        this.maxSize = maxSize
        this.participants = participants
    }
}