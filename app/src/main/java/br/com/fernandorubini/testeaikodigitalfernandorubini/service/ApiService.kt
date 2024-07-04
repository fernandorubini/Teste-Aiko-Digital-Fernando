package br.com.fernandorubini.testeaikodigitalfernandorubini.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import util.OlhoVivoApi

class ApiService {
    companion object {
        const val BASE_URL = "https://aiko-olhovivo-proxy.aikodigital.io/"
        const val BASE_URL_POSITION = "https://aiko-olhovivo-proxy.aikodigital.io/Posicao"
        const val AUTH_TOKEN = "95ae2835204efe48ba6480d54257de4d82e44d07ba62ec3655ff698fd6c1fe27"

        fun create(): OlhoVivoApi {
            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $AUTH_TOKEN")
                        .build()
                    chain.proceed(request)
                }
                .build()
            val retrofit  = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(OlhoVivoApi::class.java)
        }
    }
}