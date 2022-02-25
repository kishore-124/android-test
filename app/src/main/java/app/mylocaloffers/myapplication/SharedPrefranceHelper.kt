package app.mylocaloffers.myapplication


import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.view.inputmethod.InputMethodManager

class SharedPrefranceHelper(context: Context) {
    private var sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor

    init {
        sharedPreferences = context.getSharedPreferences("userDetail", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun setUser(loginResponse: User) {
        editor.putString(city, loginResponse.city)
        editor.putString(firstName, loginResponse.name)
        editor.putString(contact, loginResponse.phoneNumber)
        editor.putString(emailId, loginResponse.email)
        editor.putString(uid, loginResponse.id.toString())
        editor.commit()
    }
    fun setUser(loginResponse: UpdateUser) {
        editor.putString(city, loginResponse.city)
        editor.putString(firstName, loginResponse.name)
        editor.putString(contact, loginResponse.phoneNumber)
        editor.putString(emailId, loginResponse.email)
        editor.putString(uid, loginResponse.id.toString())
        editor.commit()
    }

    fun getValue(key: String): String {
        return sharedPreferences.getString(key, "")!!
    }

    fun getValueInt(key: String): Int {
        var sharedPreferences = sharedPreferences.getString(key, "")!!
        if (sharedPreferences.equals("")) {
            return 0
        }
        return sharedPreferences.toInt()
    }

    fun setValue(key: String, value: String) {
        editor.putString(key, value)
        editor.commit()
        editor.apply()
    }

    fun clear() {
        editor.clear()
        editor.commit()
    }

    companion object {
        val firstName = "firstName"
        val lastName = "lastName"
        val token = "token"
        val city = "city"
        val contact = "contact"
        val emailId = "emailId"
        val gender = "gender"
        val profilePic = "profilePic"
        val uid = "userId"
        val payModeID = "payModeID"
        val wallet = "wallet"
        val refCode = "refCode"

        var maleHashMap = HashMap<String, Int>()
        var feMaleHashMap = HashMap<String, Int>()
        var othersHashMap = HashMap<String, Int>()

        fun hideKeyboard(activity: Activity) {
            val view: View =
                (if (activity.currentFocus == null) View(activity) else activity.currentFocus)!!
            val inputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun GetToken(context: Context): String {
            var sharedPreferences = context.getSharedPreferences("userDetail", Context.MODE_PRIVATE)
            return sharedPreferences.getString(token, "")!!
        }
    }
}