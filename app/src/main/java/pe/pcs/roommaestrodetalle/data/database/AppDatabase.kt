package pe.pcs.roommaestrodetalle.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pe.pcs.roommaestrodetalle.data.dao.PedidoDao
import pe.pcs.roommaestrodetalle.data.dao.ProductoDao
import pe.pcs.roommaestrodetalle.data.entity.DetallePedidoEntity
import pe.pcs.roommaestrodetalle.data.entity.PedidoEntity
import pe.pcs.roommaestrodetalle.data.entity.ProductoEntity

@Database(
    entities = [
        ProductoEntity::class,
        PedidoEntity::class,
        DetallePedidoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // Aqui estaran todas las funciones que engloban a las interface Dao

    abstract fun productoDao(): ProductoDao

    abstract fun pedidoDao(): PedidoDao
}