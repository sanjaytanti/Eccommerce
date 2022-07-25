package com.shalinibusinesssolutions.ecommerce.ui.activities.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.shalinibusinesssolutions.ecommerce.R
import com.shalinibusinesssolutions.ecommerce.databinding.ActivityErrorBinding
import com.shalinibusinesssolutions.ecommerce.ui.activities.MainActivity

class ErrorActivity : AppCompatActivity(),View.OnClickListener {

    lateinit var binding: ActivityErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityErrorBinding.inflate(layoutInflater)
        binding.llContinue.setOnClickListener(this)
        setContentView(binding.root)
    }

    override fun onClick(p0: View?) {
        when(p0?.id)
        {
            R.id.ll_continue->
            {
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)

            }
        }
    }
}