package sheridan.jawedzak.autoedu.chatBot

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sheridan.jawedzak.autoedu.R
import sheridan.jawedzak.autoedu.utils.Constants.RECEIVE_ID
import sheridan.jawedzak.autoedu.utils.Constants.SEND_ID
import kotlinx.android.synthetic.main.message_item.view.*

//reading messages from recycler view
class MessagingAdapter: RecyclerView.Adapter<MessagingAdapter.MessageViewHolder>() {

    //list of messages from user and bot (view whole conversation)
    var messagesList = mutableListOf<Message>()

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
            }
        }
    }

    //calling message item to view strings
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        )
    }

    //counting list of messages from recycler view
    override fun getItemCount(): Int {
        return messagesList.size
    }

    //positioning messages on recycler view
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = messagesList[position]

        //sending message
        when (currentMessage.id) {
            SEND_ID -> {
                holder.itemView.tv_message.apply {
                    //choosing response from 0 or 1
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                //when clicked, message will be deleted
                holder.itemView.tv_bot_message.visibility = View.GONE
            }
            //receiving messages
            RECEIVE_ID -> {
                holder.itemView.tv_bot_message.apply {
                    //view message
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                //when clicked, message will be deleted
                holder.itemView.tv_message.visibility = View.GONE
            }
        }
    }

    //retrieve users message
    fun insertMessage(message: Message) {
        //add message to recycler view to view message
        this.messagesList.add(message)
        notifyItemInserted(messagesList.size)
    }

}