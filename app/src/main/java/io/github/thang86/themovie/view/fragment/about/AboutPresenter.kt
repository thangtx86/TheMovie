package io.github.thang86.themovie.view.fragment.about

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 *
 * Created by Thang86
 */
class AboutPresenter : LifecycleObserver {
    private var contract: AboutContract? = null

    fun setCallback(contract: AboutContract): AboutPresenter {
        this.contract = contract
        return this
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun init() {
        contract!!.inCreate()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun inResume() {
        contract!!.inResume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun indestroy() {
        contract!!.indestroy()
    }

    companion object {
        val instance = AboutPresenter()
    }
}
