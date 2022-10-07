package pe.pcs.roommaestrodetalle.data.repository

import pe.pcs.roommaestrodetalle.data.dao.ProductoDao
import pe.pcs.roommaestrodetalle.data.model.ProductoModel
import javax.inject.Inject

class ProductoRepository @Inject constructor(
    private val productoDao : ProductoDao
) {

    suspend fun getListarTodo(): List<ProductoModel> {
        return productoDao.getListarTodo()
    }

    suspend fun getListarPorNombre(dato: String): List<ProductoModel> {
        return productoDao.getListarPorNombre(dato)
    }

    suspend fun insertar(entity: ProductoModel): Long {
        return productoDao.insertar(entity)
    }

    suspend fun actualizar(entity: ProductoModel): Int {
        return productoDao.actualizar(entity)
    }

    suspend fun eliminar(entity: ProductoModel): Int {
        return productoDao.eliminar(entity)
    }

}