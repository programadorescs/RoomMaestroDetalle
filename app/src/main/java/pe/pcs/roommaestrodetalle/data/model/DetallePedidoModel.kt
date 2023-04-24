package pe.pcs.roommaestrodetalle.data.model

import androidx.room.*
import pe.pcs.roommaestrodetalle.domain.model.DetallePedido

@Entity(tableName = "detalle_pedido",
    foreignKeys = [
        ForeignKey(
            entity = PedidoModel::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idpedido"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProductoModel::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idproducto"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DetallePedidoModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "idpedido", index = true) var idpedido: Int = 0,
    @ColumnInfo(name = "idproducto", index = true) var idproducto: Int = 0,
    @ColumnInfo(name = "cantidad") var cantidad: Int = 0,
    @ColumnInfo(name = "precio") var precio: Double = 0.0,
    @ColumnInfo(name = "importe") var importe: Double = 0.0,
    @Ignore var descripcion: String = ""
)

fun DetallePedido.toDatabase() = DetallePedidoModel(
    id = id,
    cantidad = cantidad,
    precio = precio,
    importe = importe,
    idpedido = idpedido,
    idproducto = idproducto,
    descripcion = descripcion
)