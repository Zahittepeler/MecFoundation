package com.halasteknoloji.meckurbanyonetim.api

import com.halasteknoloji.meckurbanyonetim.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.CookieHandler
import java.net.CookieManager
import java.util.concurrent.TimeUnit

object ApiModule {

    private var client: OkHttpClient? = null
    private var moshiConverterFactory: MoshiConverterFactory? = null
    private var retrofit: Retrofit? = null
    private val BASE_URL: String = "https://qurbanpanel.ngcloudmedia.com/"
    var cookieHandler: CookieHandler = CookieManager()


        fun buildMoshiConverter(): MoshiConverterFactory?  {

            if (moshiConverterFactory == null) {
                moshiConverterFactory = MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            }

            return moshiConverterFactory
        }




    fun buildClient(): OkHttpClient? {
       if (client == null) {
           client = OkHttpClient.Builder()
               .cookieJar(JavaNetCookieJar(cookieHandler))
               .connectTimeout(1, TimeUnit.MINUTES)
               .readTimeout(1, TimeUnit.MINUTES)
               .writeTimeout(1, TimeUnit.MINUTES)
               .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                   level = if (BuildConfig.DEBUG)
                       HttpLoggingInterceptor.Level.BODY
                   else
                       HttpLoggingInterceptor.Level.NONE
               })
               .build()
       }

        return client
    }


    fun buildRetrofit(): Retrofit? {
        if (retrofit == null) {
            retrofit = buildMoshiConverter()?.let {
                Retrofit.Builder()
                    .addConverterFactory(it)
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .baseUrl(BASE_URL)
                    .client(buildClient())
                    .build()
            }
        }

        return retrofit
    }


    fun createApiService(): ApiService? {
        return buildRetrofit()?.create(ApiService::class.java)
    }
}