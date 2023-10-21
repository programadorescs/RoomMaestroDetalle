package pe.pcs.roommaestrodetalle.ui.reportedetallepedido

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.pcs.roommaestrodetalle.ui.core.ResponseStatus
import pe.pcs.roommaestrodetalle.domain.model.ReporteDetallePedido
import pe.pcs.roommaestrodetalle.domain.usecase.pedido.ListarDetallePedidoUseCase
import pe.pcs.roommaestrodetalle.ui.core.makeCall
import javax.inject.Inject

@HiltViewModel
class ReporteDetallePedidoViewModel @Inject constructor(
    private val listarDetallePedidoUseCase: ListarDetallePedidoUseCase
) : ViewModel() {

    private val _listaDetalle = MutableStateFlow<List<ReporteDetallePedido>>(emptyList())
    val listaDetalle: StateFlow<List<ReporteDetallePedido>> = _listaDetalle

    private val _uiState =
        MutableStateFlow<ResponseStatus<List<ReporteDetallePedido>>>(ResponseStatus.Loading())
    val uiState: StateFlow<ResponseStatus<List<ReporteDetallePedido>>> = _uiState

    private fun handleUiState(responseStatus: ResponseStatus<List<ReporteDetallePedido>>) {
        if (responseStatus is ResponseStatus.Success)
            _listaDetalle.value = responseStatus.data

        _uiState.value = responseStatus
    }

    fun listarDetalle(idPedido: Int) {
        viewModelScope.launch {
            _uiState.value = ResponseStatus.Loading()

            handleUiState(
                makeCall { listarDetallePedidoUseCase(idPedido) }
            )
        }
    }

}