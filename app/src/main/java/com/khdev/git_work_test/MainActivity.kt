package com.khdev.git_work_test

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.khdev.git_work_test.databinding.ActivityMainBinding

var setSecondThread: Thread? = null
var runnable: Runnable? = null
var str_login = ArrayList<String>()
var str_email = ArrayList<String>()
var str_organization = ArrayList<String>()
var str_following = ArrayList<String>()
var str_followers = ArrayList<String>()
var str_date = ArrayList<String>()
var str_id = ArrayList<String>()
var str_avatar = ArrayList<String>()
val apiSample = "https://api.github.com/users?per_page=100"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.setLayoutManager(linearLayoutManager)
        init()
//        binding.container.setOnRefreshListener {
//            init()
//        }

    }

    fun init() {
        runnable = Runnable { getUri() }
        setSecondThread = Thread(runnable)
        setSecondThread!!.start()
    }


    fun getCtg(){
        val handler = Handler()
        handler.postDelayed({
            val helperAdapter = HelperAdapter(
                str_login, str_id, str_avatar, this)
            binding.recyclerView.setAdapter(helperAdapter)
        },1000)
    }


    fun getUri() {
        str_login.clear()
        str_id.clear()
        str_avatar .clear()
        val reqQueue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonArrayRequest(Request.Method.GET,apiSample, null, { res ->
            for (i in 0 until res.length()) {
                val respObj = res.getJSONObject(i)
                val id = respObj.getString("id")
                val login = respObj.getString("login")
                val avatar_url = respObj.getString("avatar_url")
                str_avatar.add(avatar_url)
                str_id.add(id)
                str_login.add(login)
            }
            //Log.d("TEST_API", str_Sostav.toString())
            //Toast.makeText(context,"$str_Name\n\n$str_Image",Toast.LENGTH_LONG).show()
            getCtg()
        }, {err ->
            Log.d("Volley Sample Fail", err.message.toString())
        })
        reqQueue.add(request)
    }
}