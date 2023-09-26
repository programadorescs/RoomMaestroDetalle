package pe.pcs.roommaestrodetalle.domain.usecase.producto

import pe.pcs.roommaestrodetalle.core.ResponseStatus
import pe.pcs.roommaestrodetalle.core.makeCall
import pe.pcs.roommaestrodetalle.data.repository.ProductoRepository
import pe.pcs.roommaestrodetalle.domain.model.Producto
import pe.pcs.roommaestrodetalle.domain.model.toDomain
import javax.inject.Inject

class ObtenerProductoUseCase @Inject constructor(
    private val repository: ProductoRepository
) {

    suspend operator fun invoke(id: Int): ResponseStatus<Producto?> {
        return makeCall {
            repository.obtenerProductoPorId(id)?.toDomain()
        }
    }

}