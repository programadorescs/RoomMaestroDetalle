package pe.pcs.roommaestrodetalle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import pe.pcs.roommaestrodetalle.domain.model.Pedido

@Entity(tableName = "pedido")
data class PedidoModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "fecha") var fecha: String = "",
    @ColumnInfo(name = "total") var total: Double = 0.0,
    @ColumnInfo(name = "cliente") var cliente: String = "",
    @ColumnInfo(name = "estado") var estado: String = "",
    @Ignore var detalles: List<DetallePedidoModel>? = null
)

fun Pedido.toDatabase() = PedidoModel(
    id = id,
    fecha = fecha,
    cliente = cliente,
    estado = estado,
    total = total,
    detalles = detalles?.map {
        it.toDatabase()
    }
)