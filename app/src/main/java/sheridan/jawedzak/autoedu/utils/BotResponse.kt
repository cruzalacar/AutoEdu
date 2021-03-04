package sheridan.jawedzak.autoedu.utils

import sheridan.jawedzak.autoedu.utils.Constants.OPEN_GOOGLE
import sheridan.jawedzak.autoedu.utils.Constants.OPEN_LOCATION
import sheridan.jawedzak.autoedu.utils.Constants.OPEN_SEARCH

object BotResponse {

    fun basicResponses(_message: String): String {

        val random = (0..1).random()
        val message =_message.toLowerCase()

        return when {
            //Hello
            message.contains("hello") -> {
                when (random) {
                    0 -> "Hello, how may I help you?"
                    1 -> "Hello, did you need any assistance with the application?"
                    else -> "error" }
            }

            //How are you?
            message.contains("how are you") -> {
                when (random) {
                    0 -> "I'm doing fine, thanks!"
                    1 -> "I'm hungry..."
                    else -> "error"
                }
            }

            //Open Google
            message.contains("open") && message.contains("google")-> {
                OPEN_GOOGLE
            }
            //Search on the internet
            message.contains("search")-> {
                OPEN_SEARCH
            }
            //Open Location
            message.contains("location")-> {
                OPEN_LOCATION
            }

            //When the programme doesn't understand...
            else -> {
                when (random) {
                    0 -> "Sorry, I don't understand please try again"
                    1 -> "Sorry, please try again as I didn't understand you"
                    else -> "error"
                }
            }
        }
    }
}