package pe.pcs.roommaestrodetalle.data.repository

import pe.pcs.roommaestrodetalle.data.dao.PedidoDao
import pe.pcs.roommaestrodetalle.data.model.PedidoModel
import pe.pcs.roommaestrodetalle.data.model.ReporteDetallePedidoModel
import javax.inject.Inject

class PedidoRepository @Inject constructor(
    private val pedidoDao : PedidoDao
) {

    suspend fun insertarPedido(pedido: PedidoModel): Int {
        return pedidoDao.insertarTransaccion(pedido)
    }

    suspend fun anularPedido(id: Int): Int {
        return pedidoDao.anularPedido(id)
    }

    suspend fun listarPedidoPorFecha(desde: String, hasta: String): List<PedidoModel> {
        return pedidoDao.listarPedidoPorFecha(desde, hasta)
    }

    suspend fun listarDetallePedido(id: Int): List<ReporteDetallePedidoModel> {
        return pedidoDao.listarDetallePedido(id)
    }

}