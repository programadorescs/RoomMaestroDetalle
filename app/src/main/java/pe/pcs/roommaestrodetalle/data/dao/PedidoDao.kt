package pe.pcs.roommaestrodetalle.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import pe.pcs.roommaestrodetalle.data.model.DetallePedidoModel
import pe.pcs.roommaestrodetalle.data.model.PedidoModel
import pe.pcs.roommaestrodetalle.data.model.ReporteDetallePedidoModel

@Dao
interface PedidoDao {

    @Insert
    suspend fun insertarPedido(pedido: PedidoModel): Long

    @Insert
    suspend fun insertarDetallePedido(detallePedidoEntity: DetallePedidoModel): Long

    @Transaction
    suspend fun insertarTransaccion(pedido: PedidoModel, lista: List<DetallePedidoModel>) {
        val _id = insertarPedido(pedido)

        for (i in lista.indices){
            lista[i].idpedido = _id.toInt()
        }

        lista.forEach {
            insertarDetallePedido(it)
        }
    }

    @Query("SELECT id, fecha, total FROM pedido")
    suspend fun getListarTodoPedido(): List<PedidoModel>

    @Query("SELECT id, fecha, total FROM pedido WHERE date(fecha) >= :desde AND date(fecha) <= :hasta")
    suspend fun getListarPedidoPorFecha(desde: String, hasta: String): List<PedidoModel>

    @Query("SELECT id, idpedido, idproducto,descripcion, cantidad, precio, importe FROM detalle_pedido WHERE idpedido = :codigo")
    suspend fun getListarDetallePedido(codigo: Int): List<DetallePedidoModel>
}