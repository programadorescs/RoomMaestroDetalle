package pe.pcs.roommaestrodetalle.data.repository

import pe.pcs.roommaestrodetalle.data.EstadoRespuesta
import pe.pcs.roommaestrodetalle.data.dao.ProductoDao
import pe.pcs.roommaestrodetalle.data.hacerLlamada
import pe.pcs.roommaestrodetalle.data.model.ProductoModel
import javax.inject.Inject

class ProductoRepository @Inject constructor(
    private val productoDao : ProductoDao
) {
    suspend fun listarTodo(): EstadoRespuesta<List<ProductoModel>> {
        return hacerLlamada {
            productoDao.listarTodo()
        }
    }

    suspend fun listarPorDescripcion(dato: String): EstadoRespuesta<List<ProductoModel>> {
        return hacerLlamada {
            productoDao.listarPorDescripcion(dato)
        }
    }

    suspend fun grabar(entity: ProductoModel): EstadoRespuesta<Int> {
        return hacerLlamada {
            if(entity.id == 0)
                productoDao.insertar(entity).toInt()
            else
                productoDao.actualizar(entity)
        }
    }

    suspend fun eliminar(entity: ProductoModel): EstadoRespuesta<Int> {
        return hacerLlamada { productoDao.eliminar(entity) }
    }

}