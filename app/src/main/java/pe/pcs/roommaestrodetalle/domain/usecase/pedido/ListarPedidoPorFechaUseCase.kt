package pe.pcs.roommaestrodetalle.domain.usecase.pedido

import pe.pcs.roommaestrodetalle.core.ResponseStatus
import pe.pcs.roommaestrodetalle.core.makeCall
import pe.pcs.roommaestrodetalle.domain.model.Pedido
import pe.pcs.roommaestrodetalle.domain.repository.PedidoRepository
import javax.inject.Inject

class ListarPedidoPorFechaUseCase @Inject constructor(private val repository: PedidoRepository) {

    suspend operator fun invoke(desde: String, hasta: String): ResponseStatus<List<Pedido>> {

        return makeCall {
            repository.listarPedidoPorFecha(desde, hasta)
        }

    }

}