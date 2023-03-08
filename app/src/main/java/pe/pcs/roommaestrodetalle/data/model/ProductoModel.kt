package pe.pcs.roommaestrodetalle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "producto",
    indices = [Index(value = ["descripcion"], unique = true)]
)
data class ProductoModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "descripcion") var descripcion: String = "",
    @ColumnInfo(name = "costo") var costo: Double = 0.0,
    @ColumnInfo(name = "precio") var precio: Double = 0.0
)