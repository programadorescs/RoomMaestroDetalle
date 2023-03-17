package pe.pcs.roommaestrodetalle.data.repository

import pe.pcs.roommaestrodetalle.data.EstadoRespuesta
import pe.pcs.roommaestrodetalle.data.dao.PedidoDao
import pe.pcs.roommaestrodetalle.data.dao.ProductoDao
import pe.pcs.roommaestrodetalle.data.hacerLlamada
import pe.pcs.roommaestrodetalle.data.model.PedidoModel
import pe.pcs.roommaestrodetalle.data.model.ProductoModel
import pe.pcs.roommaestrodetalle.data.model.ReporteDetallePedidoModel
import javax.inject.Inject

class PedidoRepository @Inject constructor(
    private val pedidoDao : PedidoDao,
    private val productoDao : ProductoDao
) {
    // Implementamos las funciones suspendidas del dao
    // Estas funciones devolveran el EstadoRespuesta (Listas o Int)

    suspend fun insertarPedido(pedido: PedidoModel): EstadoRespuesta<Int> {
        return hacerLlamada {
            pedidoDao.insertarTransaccion(pedido)
        }
    }

    suspend fun anularPedido(id: Int): EstadoRespuesta<Int> {
        return hacerLlamada {
            pedidoDao.anularPedido(id)
        }
    }

    suspend fun listarProducto(dato: String): EstadoRespuesta<List<ProductoModel>> {
        return hacerLlamada {
            productoDao.listarPorDescripcion(dato)
        }
    }

    suspend fun listarPedidoPorFecha(desde: String, hasta: String): EstadoRespuesta<List<PedidoModel>> {
        return hacerLlamada{
            pedidoDao.listarPedidoPorFecha(desde, hasta)
        }
    }

    suspend fun listarDetallePedido(id: Int): EstadoRespuesta<List<ReporteDetallePedidoModel>> {
        return hacerLlamada{
            pedidoDao.listarDetallePedido(id)
        }
    }

}