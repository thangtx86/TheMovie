package io.github.thang86.themovie

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.google.gson.Gson

/**
 *
 * Created by Thang86
 */
class App : MultiDexApplication() {
    init {
        instance = this
        gSon = Gson()
    }
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }

    companion object {
        private var instance: App? = null
        private var gSon: Gson? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
        fun getGson() : Gson {
            return gSon!!
        }
    }
}

