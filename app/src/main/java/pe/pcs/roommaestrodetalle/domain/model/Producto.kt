package pe.pcs.roommaestrodetalle.domain.model

import pe.pcs.roommaestrodetalle.data.entity.ProductoEntity

data class Producto(
    var id: Int = 0,
    var descripcion: String = "",
    var costo: Double = 0.0,
    var precio: Double = 0.0
)

//mapear los modelos, puede existir uno o mas funciones para data local y/o remoto
fun ProductoEntity.toDomain() = Producto(
    id = id,
    descripcion = descripcion,
    costo = costo,
    precio = precio
)