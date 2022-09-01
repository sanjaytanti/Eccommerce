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
import com.shalinibusinesssolutions.ecommerce.databinding.ActivitySignInBinding
import com.shalinibusinesssolutions.ecommerce.ui.api.RetrofitClient
import com.shalinibusinesssolutions.ecommerce.ui.repository.AppRepository
import com.shalinibusinesssolutions.ecommerce.ui.utility.Resource
import com.shalinibusinesssolutions.ecommerce.ui.utility.SharedPreferencesUtil
import com.shalinibusinesssolutions.ecommerce.ui.viewmodel.AddUserViewModel
import com.shalinibusinesssolutions.ecommerce.ui.viewmodel.LoginUserViewModel
import com.shalinibusinesssolutions.ecommerce.ui.viewmodel.ViewModelFactory

class SignInActivity : AppCompatActivity(), View.OnClickListener {

    private val binding: ActivitySignInBinding by lazy {
        ActivitySignInBinding.inflate(
            layoutInflater
        )
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var loginUserViewModel: LoginUserViewModel
    private lateinit var addUserViewModel: AddUserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val currentuser = auth.currentUser
        binding.tvRegisterHere.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
        initViewModel()
        setContentView(binding.root)

    }

    private fun initViewModel() {
        val appRepository = AppRepository(RetrofitClient.apiservice)

        loginUserViewModel = ViewModelProvider(
            this,
            ViewModelFactory(appRepository, this)
        )[LoginUserViewModel::class.java]


        addUserViewModel = ViewModelProvider(
            this,
            ViewModelFactory(appRepository, this)
        )[AddUserViewModel::class.java]

    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnLogin -> {

                loginUser(
                    binding.etLoginEmail.text.toString().trim(),
                    binding.etLoginPass.text.toString().trim()
                )

            }
            R.id.tvRegisterHere -> {

                startActivity(Intent(this, RegistrationActivity::class.java))

            }

        }
    }

    private fun loginUser(email: String, password: String) {
        if (TextUtils.isEmpty(email)) {
            binding.etLoginEmail.setError("Email Cannot be empty ")
            binding.etLoginEmail.requestFocus()

        } else if (TextUtils.isEmpty(password)) {
            binding.etLoginPass.setError("Email Cannot be empty ")
            binding.etLoginPass.requestFocus()

        } else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {

                    loginUserViewModel.loginUser(email, password)
                    loginUserViewModel.response.observe(this, Observer { event ->

                        event.getContentIfNotHandled().let { resource ->
                            when (resource) {
                                is Resource.Success -> {


                                    resource.data.let { UsetResponse ->

                                        if (UsetResponse?.status.toString() == "true") {


                                            var userId = UsetResponse!!.data.Id
                                            var userName = UsetResponse!!.data.UserName
                                            SharedPreferencesUtil().setUserId(
                                                userId.toString(),
                                                this
                                            )
                                            SharedPreferencesUtil().setUserName(
                                                userName.toString(),
                                                this
                                            )

                                            startActivity(Intent(this, MainActivity::class.java))
                                            binding.progressbar.visibility = View.GONE
                                        }
                                        else if (UsetResponse?.status == "user not found") {
                                            addUserViewModel.addUser(email, password)
                                            addUserViewModel.response.observe(
                                                this,
                                                Observer { event ->

                                                    event.getContentIfNotHandled().let { resource ->

                                                        when (resource) {
                                                            is Resource.Success -> {
                                                                var UserId =
                                                                    UsetResponse!!.data.Id
                                                                var UserName =
                                                                    UsetResponse!!.data.UserName
                                                                SharedPreferencesUtil().setUserId(
                                                                    UserId.toString(),
                                                                    this
                                                                )
                                                                SharedPreferencesUtil().setUserName(
                                                                    UserName.toString(),
                                                                    this
                                                                )

                                                                startActivity(
                                                                    Intent(
                                                                        this,
                                                                        MainActivity::class.java
                                                                    )
                                                                )
                                                                binding.progressbar.visibility =
                                                                    View.GONE

                                                            }
                                                            is Resource.Error -> {
                                                                binding.progressbar.visibility =
                                                                    View.GONE

                                                            }
                                                            is Resource.Loading -> {

                                                            }

                                                        }
                                                    }

                                                })
                                            binding.progressbar.visibility = View.GONE

                                        } else {

                                            Toast.makeText(
                                                this,
                                                "" + UsetResponse?.message.toString(),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            binding.progressbar.visibility = View.GONE
                                        }
                                    }
                                }
                                is Resource.Error -> {
                                    Toast.makeText(
                                        this,
                                        " " + resource.message.toString(),
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


                } else {
                    Toast.makeText(
                        this,
                        "Log Error" + task.exception?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        }

    }

}