package com.shalinibusinesssolutions.ecommerce.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shalinibusinesssolutions.ecommerce.R
import com.shalinibusinesssolutions.ecommerce.databinding.ActivityRegistrationBinding
import com.shalinibusinesssolutions.ecommerce.ui.api.RetrofitClient
import com.shalinibusinesssolutions.ecommerce.ui.repository.AppRepository
import com.shalinibusinesssolutions.ecommerce.ui.utility.Resource
import com.shalinibusinesssolutions.ecommerce.ui.viewmodel.AddUserViewModel
import com.shalinibusinesssolutions.ecommerce.ui.viewmodel.ViewModelFactory

class RegistrationActivity : AppCompatActivity(), View.OnClickListener {
    private val binding: ActivityRegistrationBinding by lazy {
        ActivityRegistrationBinding.inflate(
            layoutInflater
        )
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var addUserViewModel: AddUserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViewModel()
        auth = Firebase.auth
        binding.btnRegister.setOnClickListener(this)
        binding.tvLoginHere.setOnClickListener(this)


    }

    private fun initViewModel() {
        val appRepository = AppRepository(RetrofitClient.apiservice)
        addUserViewModel = ViewModelProvider(
            this,
            ViewModelFactory(appRepository, this)
        )[AddUserViewModel::class.java]


    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.tvLoginHere -> {
                startActivity(Intent(this, SignInActivity::class.java))

            }
            R.id.btnRegister -> {
                createUser(binding.etRegEmail.text.toString(), binding.etRegPass.text.toString())

            }
        }
    }

    private fun createUser(email: String, password: String) {
        if (TextUtils.isEmpty(email)) {
            binding.etRegEmail.setError("Email Cannot be empty ")
            binding.etRegEmail.requestFocus()
        } else if (TextUtils.isEmpty(password)) {
            binding.etRegPass.setError("Password cannot be empty")
            binding.etRegPass.requestFocus()
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {
                        addUserViewModel.addUser(email, password)
                        addUserViewModel.response.observe(this, Observer { event ->

                            event.getContentIfNotHandled().let { resource ->

                                when (resource) {
                                    is Resource.Success -> {
                                        resource.data.let { UserStatusResponse ->
                                            Toast.makeText(
                                                this,
                                                "" + UserStatusResponse?.message.toString(),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            binding.progressbar.visibility = View.GONE
                                            startActivity(Intent(this, SignInActivity::class.java))
                                        }

                                    }
                                    is Resource.Error -> {
                                        Toast.makeText(
                                            this,
                                            "" + resource?.message.toString(),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        binding.progressbar.visibility = View.GONE
                                    }
                                    is Resource.Loading -> {
                                        binding.progressbar.visibility = View.VISIBLE
                                    }

                                }
                            }

                        })

//                   Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()
                        //
                    } else {
                        Toast.makeText(
                            this,
                            "Registration Error" + task.exception?.message,
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }

        }

    }

}