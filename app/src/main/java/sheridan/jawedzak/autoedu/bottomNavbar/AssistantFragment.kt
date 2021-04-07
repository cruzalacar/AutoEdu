package sheridan.jawedzak.autoedu.bottomNavbar

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_bot.*
import kotlinx.coroutines.*
import sheridan.jawedzak.autoedu.R
import sheridan.jawedzak.autoedu.chatBot.Message
import sheridan.jawedzak.autoedu.chatBot.MessagingAdapter
import sheridan.jawedzak.autoedu.utils.Constants
import sheridan.jawedzak.autoedu.utils.Time
import sheridan.jawedzak.autoedu.utils.Constants.RECEIVE_ID
import sheridan.jawedzak.autoedu.utils.Constants.SEND_ID
import sheridan.jawedzak.autoedu.utils.BotResponse
import sheridan.jawedzak.autoedu.utils.Constants.OPEN_GOOGLE
import sheridan.jawedzak.autoedu.utils.Constants.OPEN_SEARCH


class AssistantFragment : Fragment() {

    //calling message from data list
    var messagesList = mutableListOf<Message>()
    //message adapter to retrieve messages
    private lateinit var adapter: MessagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //first line when user opens the chat
        customBotMessage("Hello, welcome to AutoEDU how may I help you today?")

        //send message button
        btn_send.setOnClickListener {
            sendMessage()
        }

        //scroll back to position when user clicks on text view
        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(100)
                withContext(Dispatchers.Main) {
                    rv_messages.scrollToPosition(adapter.itemCount - 1)

                }
            }
        }

        //recycler view used when add/input messages
        adapter = MessagingAdapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(activity)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assistant, container, false)
    }

    override fun onStart() {
        super.onStart()
        //In case there are messages, scroll to bottom when re-opening app
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    //sending message to bot
    private fun sendMessage() {
        val message = et_message.text.toString()
        val timeStamp = Time.timeStamp()

        //receiving message from user
        if (message.isNotEmpty()) {
            //add message to local list
            messagesList.add(Message(message, Constants.SEND_ID, timeStamp))
            et_message.setText("")

            //inserting/sending message to bot
            adapter.insertMessage(Message(message, Constants.SEND_ID, timeStamp))
            rv_messages.scrollToPosition(adapter.itemCount - 1)

            //bot responding to users message
            botResponse(message)
        }
    }

    //bot responding to user
    private fun botResponse(message: String) {
        //response delay after 1000 milliseconds
        val timeStamp = Time.timeStamp()
        GlobalScope.launch {
            delay(1000)

            withContext(Dispatchers.Main) {
                //bot receiving the response
                val response = BotResponse.basicResponses(message)

                //adding response to local list/recycler view to see the messages
                messagesList.add(Message(response, Constants.RECEIVE_ID, timeStamp))

                //inserts message into the adapter/message list from 0 to 1
                adapter.insertMessage(Message(response, Constants.RECEIVE_ID, timeStamp))

                //allow user to scroll to view past and recent messages
                rv_messages.scrollToPosition(adapter.itemCount - 1)

                //open google page
                when (response) {
                    Constants.OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        //google site
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    //open google search
                    Constants.OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfterLast("search")
                        //google search site
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }

                }
            }
        }
    }

    //bot will respond to user after 1000 milliseconds
    private fun customBotMessage(message: String) {
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {

                //waiting to reply to user
                val timeStamp = Time.timeStamp()

                //replying back to user
                messagesList.add(Message(message, Constants.RECEIVE_ID, timeStamp))
                adapter.insertMessage(Message(message, Constants.RECEIVE_ID, timeStamp))

                //scrolling up and down to view messages
                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }
}