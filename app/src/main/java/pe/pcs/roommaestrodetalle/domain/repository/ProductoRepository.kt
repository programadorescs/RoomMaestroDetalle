package pe.pcs.roommaestrodetalle.domain.repository

import pe.pcs.roommaestrodetalle.domain.model.Producto

interface ProductoRepository {

    suspend fun obtenerProductoPorId(id: Int): Producto?

    suspend fun listarPorDescripcion(dato: String): List<Producto>

    suspend fun grabar(entidad: Producto): Int

    suspend fun eliminar(entidad: Producto): Int

}