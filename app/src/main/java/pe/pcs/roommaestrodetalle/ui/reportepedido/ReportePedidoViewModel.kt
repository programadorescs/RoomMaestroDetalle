package pe.pcs.roommaestrodetalle.ui.reportepedido

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.pcs.roommaestrodetalle.core.ResponseStatus
import pe.pcs.roommaestrodetalle.domain.model.Pedido
import pe.pcs.roommaestrodetalle.domain.usecase.pedido.AnularPedidoUseCase
import pe.pcs.roommaestrodetalle.domain.usecase.pedido.ListarPedidoPorFechaUseCase
import javax.inject.Inject

@HiltViewModel
class ReportePedidoViewModel @Inject constructor(
    private val anularPedidoUseCase: AnularPedidoUseCase,
    private val listarPedidoPorFechaUseCase: ListarPedidoPorFechaUseCase
) : ViewModel() {

    private val _listaPedido = MutableLiveData<List<Pedido>?>(mutableListOf())
    val listaPedido: LiveData<List<Pedido>?> = _listaPedido

    private val _stateListaPedido = MutableLiveData<ResponseStatus<List<Pedido>>>()
    val stateListaPedido: LiveData<ResponseStatus<List<Pedido>>> = _stateListaPedido

    private val _stateAnular = MutableLiveData<ResponseStatus<Int>>()
    val stateAnular: LiveData<ResponseStatus<Int>> = _stateAnular

    private fun handleStateListaPedido(responseStatus: ResponseStatus<List<Pedido>>) {
        if (responseStatus is ResponseStatus.Success)
            _listaPedido.value = responseStatus.data

        _stateListaPedido.value = responseStatus
    }

    private fun handleStateAnular(responseStatus: ResponseStatus<Int>) {
        _stateAnular.value = responseStatus
    }

    fun resetStateAnular() {
        _stateAnular.value = ResponseStatus.Success(0)
    }

    fun anularPedido(id: Int, desde: String, hasta: String) {
        viewModelScope.launch {
            _stateAnular.value = ResponseStatus.Loading()

            handleStateAnular(anularPedidoUseCase(id))
            handleStateListaPedido(listarPedidoPorFechaUseCase(desde, hasta))
        }
    }

    fun listarPedido(desde: String, hasta: String) {
        viewModelScope.launch {
            _stateListaPedido.value = ResponseStatus.Loading()

            handleStateListaPedido(listarPedidoPorFechaUseCase(desde, hasta))
        }
    }
}