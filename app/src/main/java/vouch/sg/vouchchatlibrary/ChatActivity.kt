package vouch.sg.vouchchatlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import org.json.JSONException
import com.github.nkzawa.emitter.Emitter
import org.json.JSONObject
import vouch.sg.vouchchatlibrary.models.Message
import vouch.sg.vouchchatlibrary.adapter.ChatBoxAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager


class ChatActivity : AppCompatActivity() {

    private var mSocket:Socket? = null
    var myRecylerView: RecyclerView? = null
    var chatBoxAdapter: ChatBoxAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        myRecylerView = findViewById(R.id.messageList) as RecyclerView
        val mLayoutManager = LinearLayoutManager(applicationContext)
        myRecylerView!!.setLayoutManager(mLayoutManager)
        myRecylerView!!.setItemAnimator(DefaultItemAnimator())

        mSocket = getmSocket()
        mSocket?.connect()
        setListening()

    }

    fun getmSocket(): Socket? {
        var mSocket:Socket? = null
            if(mSocket==null){
                try{
                    mSocket= IO.socket("http://68.183.189.114:4001")
                    Toast.makeText(this.applicationContext, "Connected!! " ,Toast.LENGTH_LONG).show()
                    Log.d("konek", "Connected!!")
                }catch (e: Exception){
                    e.printStackTrace();
                    Log.d("error connecting","to server")
                }
            }
            return mSocket;
    }

//    companion object {
//        private val URL = "http://192.168.100.26:3000"
//    }

    private fun setListening() {
        val MessageList = ArrayList<Message>()
        mSocket?.on("newMsg", object : Emitter.Listener{
            override fun call(vararg args: Any) {
                try {
                    val messageJson = JSONObject(args[0].toString())
                    val message = messageJson.getString("content")
                    val type = messageJson.getString("type")
                    val fromBot = messageJson.getString("fromBot")

                    runOnUiThread {
                        val m = Message(fromBot, message, type)
                        MessageList.add(m)
                        chatBoxAdapter = ChatBoxAdapter(MessageList)
                        chatBoxAdapter!!.notifyDataSetChanged()
                        myRecylerView?.setAdapter(chatBoxAdapter)

                        Log.d("fromBot","From Bot: "+fromBot)
                        Log.d("type","Type :"+type)
                        Log.d("content","Connect: "+message)
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        })
    }
}