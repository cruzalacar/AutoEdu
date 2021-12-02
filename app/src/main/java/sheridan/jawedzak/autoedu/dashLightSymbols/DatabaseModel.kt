package sheridan.jawedzak.autoedu.dashLightSymbols

//database model used for symbol information
class DatabaseModel {
    var name: String = ""
    var trigger: HashMap<String, Any?> = hashMapOf()
    var description: String = ""
    var solution: String = ""
    var icon: String = ""
    var steps: HashMap<String, Any?> = hashMapOf()
    var common: Boolean = false
    var video: String = ""

    constructor(){
    }

    //retrieve symbol list
    constructor(name: String, trigger: HashMap<String, Any?>, description: String, solution: String, icon: String,
                steps: HashMap<String, Any?>, common: Boolean, video: String){
        this.name = name
        this.trigger = trigger
        this.description = description
        this.solution = solution
        this.icon = icon
        this.steps = steps
        this.common = common
        this.video = video
    }
}
