package com.khdev.git_work_test

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.khdev.git_work_test.databinding.InfoUserBinding
import com.squareup.picasso.Picasso

class User_Info() : AppCompatActivity() {
    var apiSample = "https://api.github.com/users/"
    private lateinit var binding: InfoUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = InfoUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val arguments = intent.extras
        val name = arguments!!["login"]
        apiSample = apiSample + name
        init()

    }
    fun init() {
        runnable = Runnable { getUri() }
        setSecondThread = Thread(runnable)
        setSecondThread!!.start()
    }

    fun getUri() {
        val reqQueue: RequestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(Request.Method.GET,apiSample, null, { res ->
                val email = res.getString("email")
                val login = res.getString("login")
                val avatar_url = res.getString("avatar_url")
                val organization = res.getString("type")
                val followers = res.getString("followers")
                val following = res.getString("following")
                val date = res.getString("created_at")
            Picasso.get().load(avatar_url).into(binding.avatarUser)
            binding.followingUser.text = "  Followers: $followers\n  Following: $following\n  Created at: $date"
            binding.nameUser.text = login
            binding.nameUser.text = " Email: $email\n Type: $organization"
            //Toast.makeText(context,"$str_Name\n\n$str_Image",Toast.LENGTH_LONG).show()

        }, {err ->
            Log.d("Volley Sample Fail", err.message.toString())
        })
        reqQueue.add(request)
    }
}