package sheridan.jawedzak.autoedu.dashLightSymbols

//database model used for symbol information
class DatabaseModel {
    var name: String = ""
    var trigger: String = ""
    var description: String = ""
    var solution: String = ""
    var icon: String = ""
    var tools: String = ""
    //var steps: String = ""
    var video: String = ""


    constructor(){

    }

    //retrieve symbol list
    constructor(name: String, trigger: String, description: String, solution: String, icon: String, tools: String,  video: String){
        this.name = name
        this.trigger = trigger
        this.description = description
        this.solution = solution
        this.icon = icon
        this.tools = tools
        //this.steps = steps
        this.video = video
    }
}
