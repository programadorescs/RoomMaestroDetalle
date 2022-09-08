package pe.pcs.roommaestrodetalle.data.repository

import pe.pcs.roommaestrodetalle.RoomMaestroDetalleApp.Companion.db
import pe.pcs.roommaestrodetalle.data.model.DetallePedidoModel
import pe.pcs.roommaestrodetalle.data.model.PedidoModel
import pe.pcs.roommaestrodetalle.data.model.ProductoModel
import pe.pcs.roommaestrodetalle.data.model.ReporteDetallePedidoModel

class PedidoRepository {

    // Instanciar el dao (pedidoDao())
    private val pedidoDao = db.pedidoDao()
    private val productoDao = db.productoDao()

    // Implementamos las funciones suspendidas del dao
    // Estas funciones devolveran listas u objetos

    suspend fun listarProducto(dato: String): List<ProductoModel> {
        return productoDao.getListarPorNombre(dato)
    }

    suspend fun getListarTodo(): List<PedidoModel> {
        return pedidoDao.getListarTodoPedido()
    }

    suspend fun getListarPorFecha(desde: String, hasta: String): List<PedidoModel> {
        return pedidoDao.getListarPedidoPorFecha(desde, hasta)
    }

    suspend fun getListarDetallePedido(id: Int): List<DetallePedidoModel> {
        return pedidoDao.getListarDetallePedido(id)
    }

    suspend fun insertarPedido(pedido: PedidoModel, detalle: List<DetallePedidoModel>) {
        pedidoDao.insertarTransaccion(pedido, detalle)
    }

}