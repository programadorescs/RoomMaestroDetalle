package pe.pcs.roommaestrodetalle.data.repository

import pe.pcs.roommaestrodetalle.data.dao.ProductoDao
import pe.pcs.roommaestrodetalle.data.entity.ProductoEntity
import javax.inject.Inject

class ProductoRepository @Inject constructor(
    private val productoDao : ProductoDao
) {

    suspend fun obtenerProductoPorId(id: Int): ProductoEntity? {
        return productoDao.obtenerProductoPorId(id)
    }

    suspend fun listarPorDescripcion(dato: String): List<ProductoEntity> {
        return productoDao.listarPorDescripcion(dato)
    }

    suspend fun grabar(entity: ProductoEntity): Int {
        return if(entity.id == 0)
            productoDao.insertar(entity).toInt()
        else
            productoDao.actualizar(entity)
    }

    suspend fun eliminar(entity: ProductoEntity): Int {
        return productoDao.eliminar(entity)
    }

}