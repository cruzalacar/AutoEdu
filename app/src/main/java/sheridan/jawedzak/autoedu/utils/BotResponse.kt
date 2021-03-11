package sheridan.jawedzak.autoedu.utils

import sheridan.jawedzak.autoedu.utils.Constants.OPEN_GOOGLE
import sheridan.jawedzak.autoedu.utils.Constants.OPEN_LOCATION
import sheridan.jawedzak.autoedu.utils.Constants.OPEN_SEARCH

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
                    0 -> "Hello, how may I help you?"
                    1 -> "Hello, did you need any assistance with the application?"
                    //otherwise
                    else -> "Sorry, I don't udnerstand"
                }
            }

            //when users says - how are you
            message.contains("how are you") -> {
                //bot will choose to say 0 or 1
                when (random) {
                    0 -> "I'm doing fine, thanks!"
                    1 -> "I'm hungry..."
                    //otherwise
                    else -> "Sorry, I don't understand"
                }
            }

            //when user types - google
            message.contains("open") && message.contains("google")-> {
                //open google
                OPEN_GOOGLE
            }
            //when user types - search
            message.contains("search")-> {
                //open google search
                OPEN_SEARCH
            }
            //when user types - location
            message.contains("location")-> {
                //open location
                OPEN_LOCATION
            }

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