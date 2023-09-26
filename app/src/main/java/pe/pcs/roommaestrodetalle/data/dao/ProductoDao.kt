package pe.pcs.roommaestrodetalle.data.dao

import androidx.room.*
import pe.pcs.roommaestrodetalle.data.entity.ProductoEntity

@Dao
interface ProductoDao {

    @Query("SELECT id, descripcion, costo, precio FROM producto WHERE id = :id")
    suspend fun obtenerProductoPorId(id: Int): ProductoEntity?

    @Query("SELECT id, descripcion, costo, precio FROM producto WHERE descripcion LIKE '%' || :dato || '%'")
    suspend fun listarPorDescripcion(dato: String): List<ProductoEntity>

    @Insert
    suspend fun insertar(producto: ProductoEntity): Long

    @Update
    suspend fun actualizar(producto: ProductoEntity): Int

    @Delete
    suspend fun eliminar(producto: ProductoEntity): Int
}