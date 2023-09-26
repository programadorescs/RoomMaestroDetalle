package pe.pcs.roommaestrodetalle.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import pe.pcs.roommaestrodetalle.data.entity.DetallePedidoEntity
import pe.pcs.roommaestrodetalle.data.entity.PedidoEntity
import pe.pcs.roommaestrodetalle.data.entity.ReporteDetallePedidoEntity

@Dao
interface PedidoDao {

    @Insert
    suspend fun insertarPedido(pedido: PedidoEntity): Long

    @Insert
    suspend fun insertarDetallePedido(detallePedidoEntity: DetallePedidoEntity): Long

    @Transaction
    suspend fun insertarTransaccion(
        pedido: PedidoEntity,
        detalles: List<DetallePedidoEntity>
    ): Int {
        val _id = insertarPedido(pedido)

        detalles.forEach {
            it.idpedido = _id.toInt()
            insertarDetallePedido(it)
        }

        return _id.toInt()
    }

    @Query("UPDATE Pedido SET estado='anulado' WHERE id=:id")
    suspend fun anularPedido(id: Int): Int

    @Query("SELECT * FROM pedido WHERE date(fecha) >= :desde AND date(fecha) <= :hasta  order by id desc")
    suspend fun listarPedidoPorFecha(desde: String, hasta: String): List<PedidoEntity>

    @Transaction
    @Query(
        "SELECT producto.descripcion, cantidad, detalle_pedido.precio, importe " +
                "FROM detalle_pedido, producto " +
                "WHERE producto.id = detalle_pedido.idproducto AND idpedido = :codigo"
    )
    suspend fun listarDetallePedido(codigo: Int): List<ReporteDetallePedidoEntity>

}