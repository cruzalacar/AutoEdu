package sheridan.jawedzak.autoedu.utils

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object Time {

    //time used to set how long the chatbot should reply after the user sent a message
    fun timeStamp(): String {
        //time in milliseconds
        val timeStamp = Timestamp(System.currentTimeMillis())
        val sdf = SimpleDateFormat("HH:mm")
        val time = sdf.format(Date(timeStamp.time))
        return time.toString()
    }
}