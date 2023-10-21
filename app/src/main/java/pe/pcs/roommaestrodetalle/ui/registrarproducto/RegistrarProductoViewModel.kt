package pe.pcs.roommaestrodetalle.ui.registrarproducto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.pcs.roommaestrodetalle.ui.core.ResponseStatus
import pe.pcs.roommaestrodetalle.domain.model.Producto
import pe.pcs.roommaestrodetalle.domain.usecase.producto.GrabarProductoUseCase
import pe.pcs.roommaestrodetalle.domain.usecase.producto.ObtenerProductoUseCase
import pe.pcs.roommaestrodetalle.ui.core.makeCall
import javax.inject.Inject

@HiltViewModel
class RegistrarProductoViewModel @Inject constructor(
    private val grabarProductoUseCase: GrabarProductoUseCase,
    private val obtenerProductoUseCase: ObtenerProductoUseCase
) : ViewModel() {

    private val _item = MutableStateFlow<Producto?>(null)
    val item: StateFlow<Producto?> = _item

    private val _uiState = MutableStateFlow<ResponseStatus<Int>>(ResponseStatus.Loading())
    val uiState: StateFlow<ResponseStatus<Int>> = _uiState

    private val _uiStateProducto =
        MutableStateFlow<ResponseStatus<Producto?>>(ResponseStatus.Loading())
    val uiStateProducto: StateFlow<ResponseStatus<Producto?>> = _uiStateProducto

    private fun handleUiStateProducto(responseStatus: ResponseStatus<Producto?>) {
        if (responseStatus is ResponseStatus.Success)
            _item.value = responseStatus.data

        _uiStateProducto.value = responseStatus
    }

    private fun handleUiState(responseStatus: ResponseStatus<Int>) {
        _uiState.value = responseStatus
    }

    fun resetItem() {
        _item.value = null
    }

    fun obtenerProducto(id: Int) {
        viewModelScope.launch {
            _uiStateProducto.value = ResponseStatus.Loading()

            handleUiStateProducto(
                makeCall { obtenerProductoUseCase(id) }
            )
        }
    }

    fun grabar(entidad: Producto) {
        viewModelScope.launch {
            _uiState.value = ResponseStatus.Loading()

            handleUiState(
                makeCall { grabarProductoUseCase(entidad) }
            )
        }
    }

}