package pe.pcs.roommaestrodetalle.data.repository

import pe.pcs.roommaestrodetalle.data.ResponseStatus
import pe.pcs.roommaestrodetalle.data.dao.ProductoDao
import pe.pcs.roommaestrodetalle.data.makeCall
import pe.pcs.roommaestrodetalle.data.model.ProductoModel
import javax.inject.Inject

class ProductoRepository @Inject constructor(
    private val productoDao : ProductoDao
) {
    suspend fun listarTodo(): ResponseStatus<List<ProductoModel>> {
        return makeCall {
            productoDao.listarTodo()
        }
    }

    suspend fun listarPorDescripcion(dato: String): ResponseStatus<List<ProductoModel>> {
        return makeCall {
            productoDao.listarPorDescripcion(dato)
        }
    }

    suspend fun grabar(entity: ProductoModel): ResponseStatus<Int> {
        return makeCall {
            if(entity.id == 0)
                productoDao.insertar(entity).toInt()
            else
                productoDao.actualizar(entity)
        }
    }

    suspend fun eliminar(entity: ProductoModel): ResponseStatus<Int> {
        return makeCall { productoDao.eliminar(entity) }
    }

}