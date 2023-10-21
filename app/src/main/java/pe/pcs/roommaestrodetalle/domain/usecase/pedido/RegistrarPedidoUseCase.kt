package pe.pcs.roommaestrodetalle.domain.usecase.pedido

import pe.pcs.roommaestrodetalle.domain.model.Pedido
import pe.pcs.roommaestrodetalle.domain.repository.PedidoRepository
import javax.inject.Inject

class RegistrarPedidoUseCase @Inject constructor(private val repository: PedidoRepository) {

    suspend operator fun invoke(entidad: Pedido): Int {

        return repository.insertarPedido(entidad)

    }

}