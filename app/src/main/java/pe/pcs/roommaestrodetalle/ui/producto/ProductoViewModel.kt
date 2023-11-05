package pe.pcs.roommaestrodetalle.ui.producto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.pcs.roommaestrodetalle.ui.utils.ResponseStatus
import pe.pcs.roommaestrodetalle.domain.model.Producto
import pe.pcs.roommaestrodetalle.domain.usecase.producto.EliminarProductoUseCase
import pe.pcs.roommaestrodetalle.domain.usecase.producto.ListarProductoUseCase
import pe.pcs.roommaestrodetalle.ui.utils.makeCall
import javax.inject.Inject

@HiltViewModel
class ProductoViewModel @Inject constructor(
    private val listarUseCase: ListarProductoUseCase,
    private val eliminarUseCase: EliminarProductoUseCase
) : ViewModel() {

    private var _lista = MutableLiveData<List<Producto>?>()
    val lista: LiveData<List<Producto>?> = _lista

    private val _stateList = MutableLiveData<ResponseStatus<List<Producto>>>()
    val stateList: LiveData<ResponseStatus<List<Producto>>> = _stateList

    private val _stateDelete = MutableLiveData<ResponseStatus<Int>>()
    val stateDelete: LiveData<ResponseStatus<Int>> = _stateDelete

    private fun handleStateList(responseStatus: ResponseStatus<List<Producto>>) {
        if (responseStatus is ResponseStatus.Success)
            _lista.value = responseStatus.data

        _stateList.value = responseStatus
    }

    private fun handleStateDelete(responseStatus: ResponseStatus<Int>) {
        _stateDelete.value = responseStatus
    }

    fun resetStateDelete() {
        _stateDelete.value = ResponseStatus.Success(0)
    }

    fun listar(dato: String) {
        viewModelScope.launch {
            _stateList.value = ResponseStatus.Loading()

            handleStateList(
                makeCall { listarUseCase(dato) }
            )
        }
    }

    fun eliminar(producto: Producto) {
        viewModelScope.launch {
            _stateList.value = ResponseStatus.Loading()

            handleStateDelete(
                makeCall { eliminarUseCase(producto) }
            )

            handleStateList(
                makeCall { listarUseCase("") }
            )
        }
    }

}