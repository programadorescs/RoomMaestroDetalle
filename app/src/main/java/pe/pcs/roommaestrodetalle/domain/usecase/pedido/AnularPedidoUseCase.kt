package pe.pcs.roommaestrodetalle.domain.usecase.pedido

import pe.pcs.roommaestrodetalle.domain.repository.PedidoRepository
import javax.inject.Inject

class AnularPedidoUseCase @Inject constructor(private val repository: PedidoRepository) {

    suspend operator fun invoke(idPedido: Int): Int {

        return repository.anularPedido(idPedido)

    }

}