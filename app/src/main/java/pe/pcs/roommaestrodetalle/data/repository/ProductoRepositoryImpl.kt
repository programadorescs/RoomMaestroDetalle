package pe.pcs.roommaestrodetalle.data.repository

import pe.pcs.roommaestrodetalle.data.dao.ProductoDao
import pe.pcs.roommaestrodetalle.data.entity.toDatabase
import pe.pcs.roommaestrodetalle.domain.model.Producto
import pe.pcs.roommaestrodetalle.domain.model.toDomain
import pe.pcs.roommaestrodetalle.domain.repository.ProductoRepository
import javax.inject.Inject

class ProductoRepositoryImpl @Inject constructor(
    private val productoDao: ProductoDao
) : ProductoRepository {

    override suspend fun obtenerProductoPorId(id: Int): Producto? {
        return productoDao.obtenerProductoPorId(id)?.toDomain()
    }

    override suspend fun listarPorDescripcion(dato: String): List<Producto> {
        return productoDao.listarPorDescripcion(dato).map {
            it.toDomain()
        }
    }

    override suspend fun grabar(entidad: Producto): Int {
        return if (entidad.id == 0)
            productoDao.insertar(entidad.toDatabase()).toInt()
        else
            productoDao.actualizar(entidad.toDatabase())
    }

    override suspend fun eliminar(entidad: Producto): Int {
        return productoDao.eliminar(entidad.toDatabase())
    }


}