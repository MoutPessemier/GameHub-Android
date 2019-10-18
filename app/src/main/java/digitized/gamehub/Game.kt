package digitized.gamehub

class Game {

    private var name: String
    private var desciption: String
    private var rules: String
    private var requirements: String
    private var type: GameType


    constructor(
        name: String,
        desciption: String,
        rules: String,
        requirements: String,
        type: GameType
    ) {
        this.name = name
        this.desciption = desciption
        this.rules = rules
        this.requirements = requirements
        this.type = type
    }


}