package app.ft.ftapp.android.di

import android.app.Application
import app.ft.ftapp.android.MainActivity
import app.ft.ftapp.android.MyApplication
import com.hse.auth.di.AuthComponent
import com.hse.auth.di.AuthModule
import com.hse.core.di.AppModule
import com.hse.core.di.BaseAppComponent
import com.hse.core.di.CoreModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, AuthModule::class, CoreModule::class])
interface AppComponent : BaseAppComponent {

    fun inject(app: MyApplication)
    fun inject(activity: MainActivity)

    fun loginComponent(): AuthComponent.Factory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}