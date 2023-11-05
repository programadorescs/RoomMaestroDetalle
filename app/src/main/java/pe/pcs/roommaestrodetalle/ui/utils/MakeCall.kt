package pe.pcs.roommaestrodetalle.ui.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

suspend fun <T> makeCall(
    call: suspend () -> T
): ResponseStatus<T> {
    return withContext(Dispatchers.IO) {
        try{
            ResponseStatus.Success(call())
        } catch (e: UnknownHostException) {
            // UnknownHostException -> Error de internet o red
            ResponseStatus.Error(e.message.toString())
        } catch (e: Exception) {
            ResponseStatus.Error(e.message.toString())
        }
    }
}