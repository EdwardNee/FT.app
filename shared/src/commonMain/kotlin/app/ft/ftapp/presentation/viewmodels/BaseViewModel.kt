package app.ft.ftapp.presentation.viewmodels

import app.ft.ftapp.di.DIFactory
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel /*: ViewModel()*/ {
    protected val kodein = DIFactory.di

//    private val isShowProgressMutable = MutableStateFlow<Boolean>(false)
//    val isShowProgress: StateFlow<Boolean>
//        get() = isShowProgressMutable
//
//    protected fun showProgress() {
//        isShowProgressMutable.value = true
//    }
//
//    protected fun hideProgress() {
//        isShowProgressMutable.value = false
//    }
}