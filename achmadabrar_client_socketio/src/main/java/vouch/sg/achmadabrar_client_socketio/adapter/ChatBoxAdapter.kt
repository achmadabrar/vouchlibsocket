package vouch.sg.achmadabrar_client_socketio.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vouch.sg.achmadabrar_client_socketio.R
import vouch.sg.achmadabrar_client_socketio.models.Message

class ChatBoxAdapter (private val dataPelangganDua:ArrayList<Message>): RecyclerView.Adapter<ChatBoxAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
    }

    override fun getItemCount(): Int {
        return dataPelangganDua.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(dataPelangganDua[position])
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindItems(itemlist:Message){
            val message = itemView.findViewById<TextView>(R.id.content)
            val message2 = itemView.findViewById<TextView>(R.id.content2)
            val pict1 = itemView.findViewById<ImageView>(R.id.pict1)
            val pict2 = itemView.findViewById<ImageView>(R.id.pict2)
            val card2 = itemView.findViewById<CardView>(R.id.card2)
            val card = itemView.findViewById<CardView>(R.id.card)

            if(itemlist.bot.equals("false")){
                card2.visibility = View.VISIBLE
                card.visibility = View.GONE

                if(itemlist.type.equals("text")) {
                    message2.visibility = View.VISIBLE
                    pict2.visibility = View.GONE
                    message2.text = itemlist.message
                }
                else{
                    message2.visibility = View.GONE
                    pict2.visibility = View.VISIBLE
                    Glide.with(itemView.context).load(itemlist.message).into(pict2)
                }
            }
            else {
                card2.visibility = View.GONE
                card.visibility = View.VISIBLE

                if(itemlist.type.equals("text")) {
                    message.visibility = View.VISIBLE
                    pict1.visibility = View.GONE
                    message.text = itemlist.message
                }
                else{
                    message.visibility = View.GONE
                    pict1.visibility = View.VISIBLE
                    Glide.with(itemView.context).load(itemlist.message).into(pict1)
                }
            }


        }
    }
}