package pe.pcs.roommaestrodetalle.domain.usecase.producto

import pe.pcs.roommaestrodetalle.domain.ResponseStatus
import pe.pcs.roommaestrodetalle.domain.makeCall
import pe.pcs.roommaestrodetalle.data.repository.ProductoRepository
import pe.pcs.roommaestrodetalle.domain.model.Producto
import pe.pcs.roommaestrodetalle.domain.model.toDomain
import javax.inject.Inject

class ListarProductoUseCase @Inject constructor(val repository: ProductoRepository) {

    suspend operator fun invoke(dato: String): ResponseStatus<List<Producto>> {

        return makeCall {
            repository.listarPorDescripcion(dato).map {
                it.toDomain()
            }
        }

    }

}