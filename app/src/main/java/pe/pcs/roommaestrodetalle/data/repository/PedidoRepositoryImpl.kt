package pe.pcs.roommaestrodetalle.data.repository

import pe.pcs.roommaestrodetalle.data.dao.PedidoDao
import pe.pcs.roommaestrodetalle.data.entity.toDatabase
import pe.pcs.roommaestrodetalle.domain.model.Pedido
import pe.pcs.roommaestrodetalle.domain.model.ReporteDetallePedido
import pe.pcs.roommaestrodetalle.domain.model.toDomain
import pe.pcs.roommaestrodetalle.domain.repository.PedidoRepository
import javax.inject.Inject

class PedidoRepositoryImpl @Inject constructor(
    private val pedidoDao: PedidoDao
) : PedidoRepository {

    override suspend fun insertarPedido(pedido: Pedido): Int {
        return pedidoDao.insertarTransaccion(
            pedido.toDatabase(),
            pedido.detalles.map {
                it.toDatabase()
            }
        )
    }

    override suspend fun anularPedido(id: Int): Int {
        return pedidoDao.anularPedido(id)
    }

    override suspend fun listarPedidoPorFecha(desde: String, hasta: String): List<Pedido> {
        return pedidoDao.listarPedidoPorFecha(desde, hasta).map {
            it.toDomain()
        }
    }

    override suspend fun listarDetallePedido(id: Int): List<ReporteDetallePedido> {
        return pedidoDao.listarDetallePedido(id).map {
            it.toDomain()
        }
    }

}