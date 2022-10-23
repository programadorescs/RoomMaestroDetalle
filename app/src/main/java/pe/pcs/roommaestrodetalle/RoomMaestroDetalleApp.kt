package pe.pcs.roommaestrodetalle

import android.app.Application
import android.content.Context
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RoomMaestroDetalleApp: Application() {

    companion object {
        private var instancia: RoomMaestroDetalleApp? = null

        fun getAppContext(): Context {
            return instancia!!.applicationContext
        }
    }

    init {
        instancia = this
    }

    override fun onCreate() {
        super.onCreate()

        // AdMob
        MobileAds.initialize(this)
    }

}