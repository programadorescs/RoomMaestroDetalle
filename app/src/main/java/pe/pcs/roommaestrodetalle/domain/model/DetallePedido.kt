package pe.pcs.roommaestrodetalle.domain.model

import pe.pcs.roommaestrodetalle.data.model.DetallePedidoModel

data class DetallePedido(
    var id: Int = 0,
    var cantidad: Int = 0,
    var costo: Double = 0.0,
    var precio: Double = 0.0,
    var importe: Double = 0.0,
    var idpedido: Int = 0,
    var idproducto: Int = 0,
    var descripcion: String = ""
)

fun DetallePedidoModel.toDomain() = DetallePedido(
    id = id,
    cantidad = cantidad,
    precio = precio,
    importe = importe,
    idpedido = idpedido,
    idproducto = idproducto,
    descripcion = descripcion
)
