package app.mylocaloffers.myapplication

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


interface ApiClient {
    companion object {
        var url = "https://weatherappstaging124.herokuapp.com/api/v1/"
        fun <S> createService(serviceClass: Class<S>?, context: Context): S {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain ->
                    val original: Request = chain.request()
                    val request: Request = original.newBuilder()
                        .method(original.method, original.body)
                        .build()
                    val response: Response = chain.proceed(request)
                    Log.d("MyApp", "Code : " + response.code)
                    if (response.code == 401) {
                        response
                    } else response
                })
                .retryOnConnectionFailure(true)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val builder = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
            return builder.create(serviceClass)
        }

    }
}