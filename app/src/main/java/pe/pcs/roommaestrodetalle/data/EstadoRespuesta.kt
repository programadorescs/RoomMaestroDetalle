package pe.pcs.roommaestrodetalle.data

// <T> funciona para cualquier tipo de datos que metamos aqui
sealed class EstadoRespuesta<T> {
    class Success<T>(var data: T): EstadoRespuesta<T>()
    class Loading<T>: EstadoRespuesta<T>()
    class Error<T>(var message: String): EstadoRespuesta<T>()
}