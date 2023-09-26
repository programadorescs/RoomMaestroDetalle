package pe.pcs.roommaestrodetalle.domain.model

import pe.pcs.roommaestrodetalle.data.entity.ReporteDetallePedidoEntity

data class ReporteDetallePedido(
    var descripcion: String = "",
    var cantidad: Int = 0,
    var precio: Double = 0.0,
    var importe: Double = 0.0
)

fun ReporteDetallePedidoEntity.toDomain() = ReporteDetallePedido(
    descripcion = descripcion,
    cantidad = cantidad,
    precio = precio,
    importe = importe
)
