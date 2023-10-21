package pe.pcs.roommaestrodetalle.domain.usecase.producto

import pe.pcs.roommaestrodetalle.domain.model.Producto
import pe.pcs.roommaestrodetalle.domain.repository.ProductoRepository
import javax.inject.Inject

class EliminarProductoUseCase @Inject constructor(private val repository: ProductoRepository) {

    suspend operator fun invoke(entidad: Producto): Int {
        return repository.eliminar(entidad)
    }

}