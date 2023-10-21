package pe.pcs.roommaestrodetalle.domain.usecase.producto

import pe.pcs.roommaestrodetalle.domain.model.Producto
import pe.pcs.roommaestrodetalle.domain.repository.ProductoRepository
import javax.inject.Inject

class ListarProductoUseCase @Inject constructor(val repository: ProductoRepository) {

    suspend operator fun invoke(dato: String): List<Producto> {

        return repository.listarPorDescripcion(dato)

    }

}