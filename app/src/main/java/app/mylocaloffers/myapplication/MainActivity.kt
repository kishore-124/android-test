package app.mylocaloffers.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginViewModel = ViewModelProvider.NewInstanceFactory().create(LoginViewModel::class.java)
        loginViewModel.homeResponse.observe(this) {
            var response = it
            temp_text_view.text = "" + response.main?.temp.toString() + "°"
            weather_text_view.text = "" + response.weather?.get(0)?.main.toString()
            tv_humidity.text = "Humidity : " + response.main?.humidity + "%"
            tv_wind.text = "Wind : " + response.wind?.speed + " km/hr"
            tv_country.text = "Country : " + response.sys?.country.toString()
            tv_name.text = "Name : " + response.name.toString()
        }
        loginViewModel.deleteResponse.observe(this) {
            if (it.status.equals("success")) {
                SharedPrefranceHelper(this).clear()
                startActivity(Intent(this, LoginScreen::class.java))
                finish()
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
        tv_editProfile.setOnClickListener { startActivity(Intent(this, EditProfile::class.java)) }
        tv_logout.setOnClickListener { v ->
            loginViewModel.logout(this)
        }
        loginViewModel.home(this)
    }
}