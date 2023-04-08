package app.ft.ftapp.android

import android.app.Application
import app.ft.ftapp.data.db.DatabaseDriverFactory
import app.ft.ftapp.di.DIFactory

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        DIFactory.initCtx = this
        DIFactory.driverFactory = DatabaseDriverFactory(this)
    }
}