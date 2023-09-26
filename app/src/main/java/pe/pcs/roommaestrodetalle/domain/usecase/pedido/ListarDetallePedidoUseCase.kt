package pe.pcs.roommaestrodetalle.domain.usecase.pedido

import pe.pcs.roommaestrodetalle.core.ResponseStatus
import pe.pcs.roommaestrodetalle.core.makeCall
import pe.pcs.roommaestrodetalle.domain.model.ReporteDetallePedido
import pe.pcs.roommaestrodetalle.domain.repository.PedidoRepository
import javax.inject.Inject

class ListarDetallePedidoUseCase @Inject constructor(private val repository: PedidoRepository) {

    suspend operator fun invoke(idPedido: Int): ResponseStatus<List<ReporteDetallePedido>> {

        return makeCall {
            repository.listarDetallePedido(idPedido)
        }

    }

}