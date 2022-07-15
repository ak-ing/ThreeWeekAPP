package com.rick.thridweekapp.api

import android.util.Log
import com.rick.thridweekapp.util.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

/**
 * Created by Rick on 2022/7/14 11:27.
 * God bless my code!
 */
object ServiceCreator {

    private val retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
    }

    private val loggingInterceptor by lazy {
        HttpLoggingInterceptor {
            Log.d("HttpLoggingInterceptor", "http---> $it")
        }.apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    fun <T> create(clazz: Class<T>) = retrofit.create(clazz)

}