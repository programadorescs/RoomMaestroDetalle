package pe.pcs.roommaestrodetalle.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.pcs.roommaestrodetalle.data.dao.PedidoDao
import pe.pcs.roommaestrodetalle.data.dao.ProductoDao
import pe.pcs.roommaestrodetalle.data.model.DetallePedidoModel
import pe.pcs.roommaestrodetalle.data.model.PedidoModel
import pe.pcs.roommaestrodetalle.data.model.ProductoModel

@Database(
    entities = [
        ProductoModel::class,
        PedidoModel::class,
        DetallePedidoModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    // Aqui estara toas las funciones que engloban a las interfaces Dao

    abstract fun productoDao() : ProductoDao

    abstract fun pedidoDao(): PedidoDao
}