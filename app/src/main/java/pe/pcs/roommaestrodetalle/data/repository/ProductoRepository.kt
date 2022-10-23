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

    suspend fun grabar(entity: ProductoModel): Int {
        return if(entity.id == 0)
            productoDao.insertar(entity).toInt()
        else
            productoDao.actualizar(entity)
    }

    suspend fun eliminar(entity: ProductoModel): Int {
        return productoDao.eliminar(entity)
    }

}