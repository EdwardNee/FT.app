package app.ft.ftapp.presentation.viewmodels

import app.ft.ftapp.di.DIFactory

open class BaseViewModel {
    protected val kodein = DIFactory.di
}