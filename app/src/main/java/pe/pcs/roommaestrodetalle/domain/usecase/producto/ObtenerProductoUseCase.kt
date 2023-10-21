package pe.pcs.roommaestrodetalle.domain.usecase.producto

import pe.pcs.roommaestrodetalle.domain.model.Producto
import pe.pcs.roommaestrodetalle.domain.repository.ProductoRepository
import javax.inject.Inject

class ObtenerProductoUseCase @Inject constructor(
    private val repository: ProductoRepository
) {

    suspend operator fun invoke(id: Int): Producto? {
        return repository.obtenerProductoPorId(id)
    }

}