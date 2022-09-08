package pe.pcs.roommaestrodetalle.data.dao

import androidx.room.*
import pe.pcs.roommaestrodetalle.data.model.ProductoModel

@Dao
interface ProductoDao {

    @Query("SELECT * FROM producto ORDER BY descripcion ASC")
    suspend fun getListarTodo(): List<ProductoModel>

    @Query("SELECT * FROM producto WHERE descripcion LIKE '%' || :dato || '%'")
    suspend fun getListarPorNombre(dato: String): List<ProductoModel>

    @Insert
    suspend fun insertar(producto: ProductoModel): Long

    @Update
    suspend fun actualizar(producto: ProductoModel): Int

    @Delete
    suspend fun eliminar(producto: ProductoModel): Int
}