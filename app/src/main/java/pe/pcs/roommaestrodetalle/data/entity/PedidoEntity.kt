package pe.pcs.roommaestrodetalle.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pe.pcs.roommaestrodetalle.domain.model.Pedido

@Entity(
    tableName = "pedido",
    ignoredColumns = ["detalles"]
)
data class PedidoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "fecha") var fecha: String = "",
    @ColumnInfo(name = "total") var total: Double = 0.0,
    @ColumnInfo(name = "cliente") var cliente: String = "",
    @ColumnInfo(name = "estado") var estado: String = ""
) {
    var detalles: List<DetallePedidoEntity> = emptyList()
}

fun Pedido.toDatabase() = PedidoEntity(
    id = id,
    fecha = fecha,
    cliente = cliente,
    estado = estado,
    total = total
)