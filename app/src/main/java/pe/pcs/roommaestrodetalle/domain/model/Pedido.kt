package pe.pcs.roommaestrodetalle.domain.model

import pe.pcs.roommaestrodetalle.data.model.PedidoModel

data class Pedido(
    var id: Int = 0,
    var fecha: String = "",
    var cliente: String = "",
    var estado: String = "",
    var total: Double = 0.0,
    var detalles: List<DetallePedido>? = emptyList()
)

//mapear los modelos, puede existir uno o mas funciones para data local y/o remoto
fun PedidoModel.toDomain() = Pedido(
    id = id,
    fecha = fecha,
    cliente = cliente,
    estado = estado,
    total = total,
    detalles = detalles?.map {
        it.toDomain()
    }
)