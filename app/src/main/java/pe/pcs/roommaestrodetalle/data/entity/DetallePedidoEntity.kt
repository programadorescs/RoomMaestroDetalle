package pe.pcs.roommaestrodetalle.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import pe.pcs.roommaestrodetalle.domain.model.DetallePedido

@Entity(
    tableName = "detalle_pedido",
    foreignKeys = [
        ForeignKey(
            entity = PedidoEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idpedido"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProductoEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idproducto"),
            onDelete = ForeignKey.CASCADE
        )
    ],
    ignoredColumns = ["descripcion"]
)
data class DetallePedidoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "idpedido", index = true) var idpedido: Int = 0,
    @ColumnInfo(name = "idproducto", index = true) var idproducto: Int = 0,
    @ColumnInfo(name = "cantidad") var cantidad: Int = 0,
    @ColumnInfo(name = "precio") var precio: Double = 0.0,
    @ColumnInfo(name = "importe") var importe: Double = 0.0
) {
    var descripcion: String = ""
}

fun DetallePedido.toDatabase() = DetallePedidoEntity(
    id = id,
    cantidad = cantidad,
    precio = precio,
    importe = importe,
    idpedido = idpedido,
    idproducto = idproducto
)