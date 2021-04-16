package sheridan.jawedzak.autoedu.utils

//import sheridan.jawedzak.autoedu.utils.Constants.OPEN_GOOGLE
//import sheridan.jawedzak.autoedu.utils.Constants.OPEN_LOCATION
//import sheridan.jawedzak.autoedu.utils.Constants.OPEN_SEARCH

object BotResponse {

    //conversation functionality from user
    fun basicResponses(_message: String): String {
        //bot will choose random lines to say from 0 to 1
        val random = (0..1).random()
        //read message from user
        val message =_message.toLowerCase()

        return when {
            //when user types - hello
            message.contains("hello") -> {
                //bot will choose to say 0 or 1
                when (random) {
                    0 -> "Hello, how may I help you today?"
                    1 -> "Hello, did you need any assistance with the application?"
                    //otherwise
                    else -> "Sorry, I don't understand can you try rephrasing."
                }
            }

            message.contains("help") -> {
                //bot will choose to say 0 or 1
                when (random) {
                    0 -> "Welcome, what do you need help with?"
                    1 -> "Welcome, did you need any assistance with the application?"
                    //otherwise
                    else -> "Sorry, I don't understand can you try rephrasing."
                }
            }

            message.contains("find dash light symbol") -> {
                //bot will choose to say 0 or 1
                when (random) {
                    0 -> "Dashboard > Search > (search for a symbol)"
                    1 -> "Dashboard > Search > (search for a symbol)"
                    //otherwise
                    else -> "Sorry, I don't understand can you try rephrasing."
                }
            }

            message.contains("what is this app about") -> {
                //bot will choose to say 0 or 1
                when (random) {
                    0 -> "This app guides you on to fix your dash light symbols on your own."
                    1 -> "This app guides you on to fix your dash light symbols on your own."
                    //otherwise
                    else -> "Sorry, I don't understand can you try rephrasing."
                }
            }

            message.contains("scan dash light symbol") -> {
                //bot will choose to say 0 or 1
                when (random) {
                    0 -> "Dashboard > Camera > (Take a photo)"
                    1 -> "Dashboard > Camera > (Take a photo)"
                    //otherwise
                    else -> "Sorry, I don't understand can you try rephrasing."
                }
            }

            message.contains("recent search") -> {
                //bot will choose to say 0 or 1
                when (random) {
                    0 -> "Dashboard > History"
                    1 -> " "
                    //otherwise
                    else -> "Sorry, I don't understand can you try rephrasing."
                }
            }

            message.contains("who are you") -> {
                //bot will choose to say 0 or 1
                when (random) {
                    0 -> "I am your virtual assistant. Always here to talk if you have questions about the app"
                    1 -> "I am your virtual assistant. Always here to talk if you have questions about the app"
                    //otherwise
                    else -> "Sorry, I don't understand can you try rephrasing."
                }
            }

//            message.contains(" ") -> {
//                //bot will choose to say 0 or 1
//                when (random) {
//                    0 -> " "
//                    1 -> " "
//                    //otherwise
//                    else -> "Sorry, I don't understand can you try rephrasing."
//                }
//            }






            //when user types - google
//            message.contains("open") && message.contains("google")-> {
//                //open google
//                OPEN_GOOGLE
//            }
            //when user types - search
//            message.contains("search")-> {
//                //open google search
//                OPEN_SEARCH
//            }
            //when user types - location
//            message.contains("location")-> {
//                //open location
//                OPEN_LOCATION
//            }

            //when bot does not understand what the user says
            else -> {
                //bot will choose to say 0 or 1
                when (random) {
                    0 -> "Sorry, I don't understand please try again"
                    1 -> "Sorry, please try again as I didn't understand you"
                    else -> "error"
                }
            }
        }
    }
}