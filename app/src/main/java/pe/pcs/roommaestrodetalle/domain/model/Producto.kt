package pe.pcs.roommaestrodetalle.domain.model

import pe.pcs.roommaestrodetalle.data.model.ProductoModel

data class Producto(
    var id: Int = 0,
    var descripcion: String = "",
    var costo: Double = 0.0,
    var precio: Double = 0.0
)

//mapear los modelos, puede existir uno o mas funciones para data local y/o remoto
fun ProductoModel.toDomain() = Producto(
    id = id,
    descripcion = descripcion,
    costo = costo,
    precio = precio
)