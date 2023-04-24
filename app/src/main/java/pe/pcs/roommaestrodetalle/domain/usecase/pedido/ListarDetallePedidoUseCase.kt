package pe.pcs.roommaestrodetalle.domain.usecase.pedido

import pe.pcs.roommaestrodetalle.domain.ResponseStatus
import pe.pcs.roommaestrodetalle.domain.makeCall
import pe.pcs.roommaestrodetalle.data.repository.PedidoRepository
import pe.pcs.roommaestrodetalle.domain.model.ReporteDetallePedido
import pe.pcs.roommaestrodetalle.domain.model.toDomain
import javax.inject.Inject

class ListarDetallePedidoUseCase @Inject constructor(private val repository: PedidoRepository) {

    suspend operator fun invoke(idPedido: Int): ResponseStatus<List<ReporteDetallePedido>> {

        return makeCall {
            repository.listarDetallePedido(idPedido).map {
                it.toDomain()
            }
        }

    }

}