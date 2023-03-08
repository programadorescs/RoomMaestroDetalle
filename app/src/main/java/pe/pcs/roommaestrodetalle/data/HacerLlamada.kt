package pe.pcs.roommaestrodetalle.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

suspend fun <T> hacerLlamada(
    call: suspend () -> T
): EstadoRespuesta<T> {
    return withContext(Dispatchers.IO) {
        try{
            EstadoRespuesta.Success(call())
        } catch (e: UnknownHostException) {
            // UnknownHostException -> Error de internet o red
            EstadoRespuesta.Error(e.message.toString())
        } catch (e: Exception) {
            EstadoRespuesta.Error(e.message.toString())
        }
    }
}