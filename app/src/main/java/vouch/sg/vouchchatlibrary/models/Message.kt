package vouch.sg.vouchchatlibrary.models

class Message {

    var bot: String? = null
    var message: String? = null
    var type: String? = null

    constructor() {

    }

    constructor(bot: String, message: String, type:String) {
        this.bot = bot
        this.message = message
        this.type = type
    }

}
