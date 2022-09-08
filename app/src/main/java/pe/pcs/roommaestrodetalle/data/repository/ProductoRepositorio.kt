package pe.pcs.roommaestrodetalle.data.repository

import pe.pcs.roommaestrodetalle.RoomMaestroDetalleApp.Companion.db
import pe.pcs.roommaestrodetalle.data.model.ProductoModel

class ProductoRepositorio {

    //private val productoDao = db.productoDao()

    /*
    lazy: La inicialización de la variable productoDao, solo se realizará durante la
    primera vez en que se haga uso de dicha variable.
    Las demas llamadas devolvera siempre el mismo valor inicial.

    lazy es una función que durante la primera invocación ejecuta el lambda que se le
    haya pasado y en posteriores invocaciones retornará el valor computado inicialmente
     */
    private val productoDao by lazy {
        db.productoDao()
    }

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