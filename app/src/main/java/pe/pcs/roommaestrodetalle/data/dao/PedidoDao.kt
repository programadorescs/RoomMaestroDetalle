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
    suspend fun insertarTransaccion2(pedido: PedidoModel, lista: List<DetallePedidoModel>) {
        val _id = insertarPedido(pedido)

        for (i in lista.indices){
            lista[i].idpedido = _id.toInt()
        }

        lista.forEach {
            insertarDetallePedido(it)
        }
    }

    @Transaction
    suspend fun insertarTransaccion(pedido: PedidoModel): Int {
        val _id = insertarPedido(pedido)

        for (i in pedido.detalles!!.indices){
            pedido.detalles!![i].idpedido = _id.toInt()
        }

        pedido.detalles!!.forEach {
            insertarDetallePedido(it)
        }

        return _id.toInt()
    }

    @Query("UPDATE Pedido SET estado='anulado' WHERE id=:id")
    suspend fun anularPedido(id: Int): Int

    @Query("SELECT * FROM pedido WHERE date(fecha) >= :desde AND date(fecha) <= :hasta  order by id desc")
    suspend fun listarPedidoPorFecha(desde: String, hasta: String): List<PedidoModel>

    @Transaction
    @Query("SELECT producto.descripcion, cantidad, detalle_pedido.precio, importe " +
            "FROM detalle_pedido, producto " +
            "WHERE producto.id = detalle_pedido.idproducto AND idpedido = :codigo")
    suspend fun listarDetallePedido(codigo: Int): List<ReporteDetallePedidoModel>

}