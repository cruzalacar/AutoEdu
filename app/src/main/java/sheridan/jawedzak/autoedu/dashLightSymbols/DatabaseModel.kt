package sheridan.jawedzak.autoedu.dashLightSymbols

class DatabaseModel {
    var name: String = ""
    var solution: String = ""
    var icon: String = ""

    constructor(){

    }

    constructor(name: String, solution: String, icon: String){
        this.name = name
        this.solution = solution
        this.icon = icon
    }

}
