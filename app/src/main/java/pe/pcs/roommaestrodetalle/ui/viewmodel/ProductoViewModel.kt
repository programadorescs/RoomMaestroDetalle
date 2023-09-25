package pe.pcs.roommaestrodetalle.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.pcs.roommaestrodetalle.core.ResponseStatus
import pe.pcs.roommaestrodetalle.domain.model.Producto
import pe.pcs.roommaestrodetalle.domain.usecase.producto.EliminarProductoUseCase
import pe.pcs.roommaestrodetalle.domain.usecase.producto.GrabarProductoUseCase
import pe.pcs.roommaestrodetalle.domain.usecase.producto.ListarProductoUseCase
import javax.inject.Inject

@HiltViewModel
class ProductoViewModel @Inject constructor(
    private val listarUseCase: ListarProductoUseCase,
    private val grabarProductoUseCase: GrabarProductoUseCase,
    private val eliminarUseCase: EliminarProductoUseCase
) : ViewModel() {

    var lista = MutableLiveData<List<Producto>?>()

    private var _itemProducto = MutableLiveData<Producto?>()
    val itemProducto: LiveData<Producto?> = _itemProducto

    private val _status = MutableLiveData<ResponseStatus<List<Producto>>>()
    val status: LiveData<ResponseStatus<List<Producto>>> = _status

    private val _statusInt = MutableLiveData<ResponseStatus<Int>>()
    val statusInt: LiveData<ResponseStatus<Int>> = _statusInt

    // Para el item seleccionado
    fun setItemProducto(item: Producto?) {
        _itemProducto.postValue(item)
    }

    private fun handleResponseStatus(responseStatus: ResponseStatus<List<Producto>>) {
        if (responseStatus is ResponseStatus.Success) {
            lista.value = responseStatus.data
        }

        _status.value = responseStatus
    }

    private fun handleResponseStatusInt(responseStatus: ResponseStatus<Int>) {
        _statusInt.value = responseStatus
    }

    fun listar(dato: String) {
        viewModelScope.launch {
            _status.value = ResponseStatus.Loading()
            handleResponseStatus(listarUseCase(dato))
        }
    }

    fun grabar(producto: Producto) {
        viewModelScope.launch {
            _statusInt.value = ResponseStatus.Loading()

            handleResponseStatusInt(grabarProductoUseCase(producto))
            handleResponseStatus(listarUseCase(""))
        }
    }

    fun eliminar(producto: Producto) {
        viewModelScope.launch {
            _status.value = ResponseStatus.Loading()

            handleResponseStatusInt(eliminarUseCase(producto))
            handleResponseStatus(listarUseCase(""))
        }
    }

}