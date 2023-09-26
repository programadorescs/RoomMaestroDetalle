package pe.pcs.roommaestrodetalle.domain.usecase.pedido

import pe.pcs.roommaestrodetalle.core.ResponseStatus
import pe.pcs.roommaestrodetalle.core.makeCall
import pe.pcs.roommaestrodetalle.data.entity.toDatabase
import pe.pcs.roommaestrodetalle.data.repository.PedidoRepository
import pe.pcs.roommaestrodetalle.domain.model.Pedido
import javax.inject.Inject

class RegistrarPedidoUseCase @Inject constructor(private val repository: PedidoRepository) {

    suspend operator fun invoke(entidad: Pedido): ResponseStatus<Int> {

        return makeCall {
            repository.insertarPedido(
                entidad.toDatabase(),
                entidad.detalles.map {
                    it.toDatabase()
                })
        }

    }

}