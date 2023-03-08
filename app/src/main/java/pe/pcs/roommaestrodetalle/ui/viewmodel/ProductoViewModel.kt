package pe.pcs.roommaestrodetalle.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.pcs.roommaestrodetalle.data.ResponseStatus
import pe.pcs.roommaestrodetalle.data.model.ProductoModel
import pe.pcs.roommaestrodetalle.data.repository.ProductoRepository
import javax.inject.Inject

@HiltViewModel
class ProductoViewModel @Inject constructor(
    private val repository: ProductoRepository
) : ViewModel() {

    var lista = MutableLiveData<List<ProductoModel>?>()

    private var _itemProducto = MutableLiveData<ProductoModel?>()
    val itemProducto: LiveData<ProductoModel?> = _itemProducto

    private val _progressBar = MutableLiveData<Boolean>()
    var progressBar: LiveData<Boolean> = _progressBar

    private val _status = MutableLiveData<ResponseStatus<List<ProductoModel>>>()
    val status: LiveData<ResponseStatus<List<ProductoModel>>> = _status

    private val _statusInt = MutableLiveData<ResponseStatus<Int>>()
    val statusInt: LiveData<ResponseStatus<Int>> = _statusInt


    // Para el item seleccionado
    fun setItemProducto(item: ProductoModel?) {
        _itemProducto.postValue(item)
    }

    private fun handleResponseStatus(responseStatus: ResponseStatus<List<ProductoModel>>) {
        if (responseStatus is ResponseStatus.Success) {
            lista.value = responseStatus.data
        }

        _status.value = responseStatus
    }

    private fun handleResponseStatusInt(responseStatus: ResponseStatus<Int>) {
        if (responseStatus is ResponseStatus.Success) {
            _statusInt.value = responseStatus
        }

        _statusInt.value = responseStatus
    }

    fun listar(dato: String) {
        viewModelScope.launch {
            _status.value = ResponseStatus.Loading()
            handleResponseStatus(repository.listarPorDescripcion(dato))
        }
    }

    fun grabar(producto: ProductoModel) {
        viewModelScope.launch {
            _status.value = ResponseStatus.Loading()

            handleResponseStatusInt(repository.grabar(producto))
            handleResponseStatus(repository.listarTodo())
        }
    }

    fun eliminar(producto: ProductoModel) {
        viewModelScope.launch {
            _status.value = ResponseStatus.Loading()

            handleResponseStatusInt(repository.eliminar(producto))
            handleResponseStatus(repository.listarTodo())
        }
    }

}