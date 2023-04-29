package pe.pcs.roommaestrodetalle.domain.usecase.producto

import pe.pcs.roommaestrodetalle.data.model.toDatabase
import pe.pcs.roommaestrodetalle.core.ResponseStatus
import pe.pcs.roommaestrodetalle.core.makeCall
import pe.pcs.roommaestrodetalle.data.repository.ProductoRepository
import pe.pcs.roommaestrodetalle.domain.model.Producto
import javax.inject.Inject

class EliminarProductoUseCase @Inject constructor(private val repository: ProductoRepository) {

    suspend operator fun invoke(entidad: Producto): ResponseStatus<Int> {
        return makeCall {
            repository.eliminar(entidad.toDatabase())
        }
    }

}