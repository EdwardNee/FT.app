package app.ft.ftapp.android

import android.util.Log
import app.ft.ftapp.android.di.AppComponent
import app.ft.ftapp.android.di.DaggerAppComponent
import app.ft.ftapp.data.db.DatabaseDriverFactory
import app.ft.ftapp.di.DIFactory
import com.hse.auth.di.AuthComponent
import com.hse.auth.di.AuthComponentProvider
import com.hse.core.BaseApplication

class MyApplication: BaseApplication(), AuthComponentProvider {
    override fun onCreate() {
        super.onCreate()
        Log.d("TAG_OF_F", "onCreate: ${this.packageName}")

        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()

        (appComponent as AppComponent).inject(this)

        DIFactory.initCtx = this
        DIFactory.driverFactory = DatabaseDriverFactory(this)
    }

    override fun provideAuthComponent(): AuthComponent {
        return (appComponent as AppComponent).loginComponent().create()
    }
}