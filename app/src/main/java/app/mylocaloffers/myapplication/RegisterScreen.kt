package app.mylocaloffers.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_login_screen.*
import kotlinx.android.synthetic.main.activity_register_screen.*
import kotlinx.android.synthetic.main.activity_register_screen.btn_go
import kotlinx.android.synthetic.main.activity_register_screen.et_email
import kotlinx.android.synthetic.main.activity_register_screen.et_password
import kotlinx.android.synthetic.main.activity_register_screen.progress_login
import kotlinx.android.synthetic.main.activity_register_screen.progress_rating
import org.json.JSONObject


class RegisterScreen : AppCompatActivity() {
    lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_screen)
        loginViewModel = ViewModelProvider.NewInstanceFactory().create(LoginViewModel::class.java)
        loginViewModel.signUpResponse.observe(this) {
            btn_go.visibility = View.VISIBLE
            progress_rating.visibility = View.GONE
            if (it.code != 401) {
                if (it.status.equals("success")) {

                    it.data?.user?.let { it1 -> SharedPrefranceHelper(this).setUser(it1) }
                    SharedPrefranceHelper(this).setValue(
                        SharedPrefranceHelper.token,
                        it.data?.token.toString()
                    )
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Invalid email and password", Toast.LENGTH_SHORT).show()

            }
        }
        btn_go.setOnClickListener {
            when {
                et_name.text.toString().equals("") -> {
                    Toast.makeText(this, "Enter NAme", Toast.LENGTH_SHORT).show()
                }
                et_email.text.toString().equals("") -> {
                    Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
                }
                et_password.text.toString().equals("") -> {
                    Toast.makeText(this, "Enter PAssword", Toast.LENGTH_SHORT).show()
                }
                et_mobile.text.toString().equals("") -> {
                    Toast.makeText(this, "Enter Mobile", Toast.LENGTH_SHORT).show()
                }
                et_city.text.toString().equals("") -> {
                    Toast.makeText(this, "Enter City", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    var value = JsonObject()
                    var jsonObject = HashMap<String, JsonObject>()
                    value.addProperty("name", et_name.text.toString())
                    value.addProperty("email", et_email.text.toString())
                    value.addProperty("password", et_password.text.toString())
                    value.addProperty("phone_number", et_mobile.text.toString())
                    value.addProperty("city", et_city.text.toString())
                    jsonObject["user"] = value
                    btn_go.visibility = View.GONE
                    progress_rating.visibility = View.VISIBLE
                    loginViewModel.signUp(jsonObject, this)
                }
            }
        }
    }
}