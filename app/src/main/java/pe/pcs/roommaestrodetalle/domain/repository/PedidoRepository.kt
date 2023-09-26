package pe.pcs.roommaestrodetalle.domain.repository

import pe.pcs.roommaestrodetalle.domain.model.Pedido
import pe.pcs.roommaestrodetalle.domain.model.ReporteDetallePedido

interface PedidoRepository {

    suspend fun insertarPedido(pedido: Pedido): Int

    suspend fun anularPedido(id: Int): Int

    suspend fun listarPedidoPorFecha(desde: String, hasta: String): List<Pedido>

    suspend fun listarDetallePedido(id: Int): List<ReporteDetallePedido>

}