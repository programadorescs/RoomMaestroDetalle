package pe.pcs.roommaestrodetalle

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp
import pe.pcs.roommaestrodetalle.data.database.AppDatabase

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

    // Crea la DB al iniciar la app
    override fun onCreate() {
        super.onCreate()

        // AdMob
        MobileAds.initialize(this)
    }

}