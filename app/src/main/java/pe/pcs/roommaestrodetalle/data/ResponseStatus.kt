package pe.pcs.roommaestrodetalle.data

// <T> funciona para cualquier tipo de datos que metamos aqui
sealed class ResponseStatus<T> {
    class Success<T>(var data: T): ResponseStatus<T>()
    class Loading<T>: ResponseStatus<T>()
    class Error<T>(val message: String): ResponseStatus<T>()
    //class SuccessMessage<T>(val message: String): ResponseStatus<T>()
}