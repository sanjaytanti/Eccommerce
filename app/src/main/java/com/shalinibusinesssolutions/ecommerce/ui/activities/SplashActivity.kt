package com.shalinibusinesssolutions.ecommerce.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shalinibusinesssolutions.ecommerce.databinding.ActivitySplashBinding
import com.shalinibusinesssolutions.ecommerce.ui.utility.SharedPreferencesUtil

class SplashActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Handler().postDelayed({
            auth = Firebase.auth

            val currentuser = auth.currentUser

            var userName = SharedPreferencesUtil().getUserName(this).toString()
            var userId = SharedPreferencesUtil().getUserId(this).toString()

            if (userId == "") ////  if (currentuser!=null)
            {
                val intent = Intent(this@SplashActivity, SignInActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)


            } else {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)


            }

        }, 3000)
    }
}