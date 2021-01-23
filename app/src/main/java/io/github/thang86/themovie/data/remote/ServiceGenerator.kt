package io.github.thang86.themovie.data.remote

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.github.thang86.themovie.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 *
 * Created by Thang86
 */
object ServiceGenerator {
    private val httpClient: OkHttpClient.Builder by lazy { OkHttpClient.Builder() }

    //    private val cacheDir: File by lazy { App.applicationContext().cacheDir }
    private val builder = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())

    val cacheSize = (5 * 1024 * 1024).toLong()

//    val myCache = Cache(cacheDir, cacheSize)

    // service have not token
    fun <S> createService(serviceClass: Class<S>): S {
        httpClient.apply {
            readTimeout(3, TimeUnit.MINUTES)
            connectTimeout(3, TimeUnit.MINUTES)
//            cache(myCache)
            addInterceptor { chain ->

                val requestBuilder = chain.request().newBuilder()
//                        .addHeader("Cache-Control", "public, max-age=" + 5)
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .addHeader("Accept", "application/json")
                    .method(chain.request().method(), chain.request().body())
//                val requestBuilder = if (NetwortUtils.hasNetwork()!!) {
//                    chain.request().newBuilder()
//                        .addHeader("Cache-Control", "public, max-age=" + 5)
//                        .addHeader("Content-Type", "application/json; charset=utf-8")
//                        .addHeader("Accept", "application/json")
//                        .method(chain.request().method(), chain.request().body())
//                } else {
//                    chain.request().newBuilder()
//                        .addHeader(
//                            "Cache-Control",
//                            "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
//                        )
//                        .addHeader("Content-Type", "application/json; charset=utf-8")
//                        .addHeader("Accept", "application/json")
//                        .method(chain.request().method(), chain.request().body())
//                }
                val request = requestBuilder.build()
                chain.proceed(request)
            }
        }

        val gson = GsonBuilder()
            .setLenient()
            .create()
        val client = httpClient.build()
        val retrofit =
            builder.client(client).addConverterFactory(GsonConverterFactory.create(gson)).build()
        return retrofit.create(serviceClass)
    }

    fun <S> createServiceToken(serviceClass: Class<S>): S {
        httpClient.apply {
            readTimeout(3, TimeUnit.MINUTES)
            connectTimeout(3, TimeUnit.MINUTES)
            addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .method(chain.request().method(), chain.request().body()).build()
                chain.proceed(requestBuilder)
            }
        }
        val client = httpClient.build()
        val retrofit = builder.client(client).build()
        return retrofit.create(serviceClass)
    }
}
