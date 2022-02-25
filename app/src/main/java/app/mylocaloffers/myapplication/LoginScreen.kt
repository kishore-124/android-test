package app.mylocaloffers.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_login_screen.*

class LoginScreen : AppCompatActivity() {
    lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        loginViewModel = ViewModelProvider.NewInstanceFactory().create(LoginViewModel::class.java)
        loginViewModel.signInResponse.observe(this) {
            var response = it
            btn_go.visibility = View.VISIBLE
            progress_rating.visibility = View.GONE
            if (it.code == 401) {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            } else if (it.code == 200) {
                if (response.status.equals("success")) {
                    it.data?.user?.let { it1 -> SharedPrefranceHelper(this).setUser(it1) }
                    SharedPrefranceHelper(this).setValue(
                        SharedPrefranceHelper.token,
                        response.data?.token.toString()
                    )
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

        }

        tv_register.setOnClickListener {
            startActivity(Intent(this, RegisterScreen::class.java))
        }
        btn_go.setOnClickListener {
            var loginRequest = LoginRequest()
            loginRequest.email = et_email.text.toString()
            loginRequest.password = et_password.text.toString()
            var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            when {
                et_email.text.toString() == "" -> {
                    Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
                }
                et_password.text.toString() == "" -> {
                    Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    btn_go.visibility = View.GONE
                    progress_rating.visibility = View.VISIBLE
                    loginViewModel.signIn(loginRequest, this)
                }
            }
        }

    }
}
