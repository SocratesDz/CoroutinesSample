package com.socratesdiaz.coroutinesamples

import android.app.Application
import com.socratesdiaz.coroutinesamples.base.diModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DI
import org.kodein.di.DIAware

class App: Application(), DIAware {
    @ExperimentalCoroutinesApi
    override val di: DI by DI.lazy {
        import(diModule(this@App))
    }
}