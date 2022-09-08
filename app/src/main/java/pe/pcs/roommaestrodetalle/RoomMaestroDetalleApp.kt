package pe.pcs.roommaestrodetalle

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.android.gms.ads.MobileAds
import pe.pcs.roommaestrodetalle.data.database.PedidoDB

class RoomMaestroDetalleApp: Application() {

    companion object {
        lateinit var db: PedidoDB
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

        db = Room.databaseBuilder(
            this,
            PedidoDB::class.java,
            "pedidoDB"
        ).build()
    }

}