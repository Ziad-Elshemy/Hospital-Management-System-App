package com.route.myapplication.hms.ui.api

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


class ApiManager {
    companion object{
    private var retrofit : Retrofit?=null;

        private fun getInstance():Retrofit{

        if (retrofit == null){
            val logging = HttpLoggingInterceptor(
                object :HttpLoggingInterceptor.Logger{
                    override fun log(message: String) {
                        Log.e("api",message)
                    }
                }
            )

            val READ_TIMEOUT : Long = 15
            val CONNECT_TIMEOUT : Long = 15

            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .build()

            val gson = GsonBuilder()
                .setLenient()
                .create()
            retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.1.2:5200/")
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        return retrofit!!;
    }
    public fun getApis():Services{
        return getInstance().create(Services::class.java);
    }
}
}