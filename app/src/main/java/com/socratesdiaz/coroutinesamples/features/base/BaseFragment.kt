package com.socratesdiaz.coroutinesamples.features.base

import androidx.fragment.app.Fragment
import org.kodein.di.DI
import org.kodein.di.DIAware

open class BaseFragment: Fragment(), DIAware {
    override val di: DI by lazy { (activity?.application as DIAware).di }
}