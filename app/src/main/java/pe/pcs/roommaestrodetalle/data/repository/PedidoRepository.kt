package pe.pcs.roommaestrodetalle.data.repository

import pe.pcs.roommaestrodetalle.data.dao.PedidoDao
import pe.pcs.roommaestrodetalle.data.entity.DetallePedidoEntity
import pe.pcs.roommaestrodetalle.data.entity.PedidoEntity
import pe.pcs.roommaestrodetalle.data.entity.ReporteDetallePedidoEntity
import javax.inject.Inject

class PedidoRepository @Inject constructor(
    private val pedidoDao: PedidoDao
) {

    suspend fun insertarPedido(pedido: PedidoEntity, listaDetalle: List<DetallePedidoEntity>): Int {
        pedido.detalles = listaDetalle
        return pedidoDao.insertarTransaccion(pedido)
    }

    suspend fun anularPedido(id: Int): Int {
        return pedidoDao.anularPedido(id)
    }

    suspend fun listarPedidoPorFecha(desde: String, hasta: String): List<PedidoEntity> {
        return pedidoDao.listarPedidoPorFecha(desde, hasta)
    }

    suspend fun listarDetallePedido(id: Int): List<ReporteDetallePedidoEntity> {
        return pedidoDao.listarDetallePedido(id)
    }

}