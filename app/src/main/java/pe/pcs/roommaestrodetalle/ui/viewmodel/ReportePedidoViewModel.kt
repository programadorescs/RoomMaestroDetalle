package pe.pcs.roommaestrodetalle.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.pcs.roommaestrodetalle.domain.ResponseStatus
import pe.pcs.roommaestrodetalle.domain.model.Pedido
import pe.pcs.roommaestrodetalle.domain.model.ReporteDetallePedido
import pe.pcs.roommaestrodetalle.domain.usecase.pedido.AnularPedidoUseCase
import pe.pcs.roommaestrodetalle.domain.usecase.pedido.ListarDetallePedidoUseCase
import pe.pcs.roommaestrodetalle.domain.usecase.pedido.ListarPedidoPorFechaUseCase
import javax.inject.Inject

@HiltViewModel
class ReportePedidoViewModel @Inject constructor(
    private val anularPedidoUseCase: AnularPedidoUseCase,
    private val listarPedidoPorFechaUseCase: ListarPedidoPorFechaUseCase,
    private val listarDetallePedidoUseCase: ListarDetallePedidoUseCase
): ViewModel() {

    private val _listaPedido = MutableLiveData<List<Pedido>?>()
    val listaPedido: LiveData<List<Pedido>?> = _listaPedido

    private val _listaDetalle = MutableLiveData<List<ReporteDetallePedido>?>()
    val listaDetalle: LiveData<List<ReporteDetallePedido>?> = _listaDetalle

    private val _itemPedido = MutableLiveData<Pedido?>()
    val itemPedido: LiveData<Pedido?> = _itemPedido

    private val _statusListaPedido = MutableLiveData<ResponseStatus<List<Pedido>>?>()
    val statusListaPedido: LiveData<ResponseStatus<List<Pedido>>?> = _statusListaPedido

    private val _statusListaDetalle = MutableLiveData<ResponseStatus<List<ReporteDetallePedido>>?>()
    val statusListaDetalle: LiveData<ResponseStatus<List<ReporteDetallePedido>>?> = _statusListaDetalle

    private val _statusInt = MutableLiveData<ResponseStatus<Int>?>()
    val statusInt: LiveData<ResponseStatus<Int>?> = _statusInt

    init {
        _listaPedido.value = mutableListOf()
        _listaDetalle.value = mutableListOf()
    }

    fun resetApiResponseStatus() {
        _statusListaPedido.value = null
    }

    fun resetApiResponseStatusDetalle() {
        _statusListaDetalle.value = null
    }

    fun resetApiResponseStatusInt() {
        _statusInt.value = null
    }

    fun setItem(entidad: Pedido?) {
        _itemPedido.postValue(entidad)
    }

    private fun handleResponseStatusPedido(responseStatus: ResponseStatus<List<Pedido>>) {
        if (responseStatus is ResponseStatus.Success) {
            _listaPedido.value = responseStatus.data
        }

        _statusListaPedido.value = responseStatus
    }

    private fun handleResponseStatusDetalle(responseStatus: ResponseStatus<List<ReporteDetallePedido>>) {
        if (responseStatus is ResponseStatus.Success) {
            _listaDetalle.value = responseStatus.data
        }

        _statusListaDetalle.value = responseStatus
    }

    private fun handleResponseStatusInt(responseStatus: ResponseStatus<Int>) {
        _statusInt.value = responseStatus
    }

    fun anularPedido(id: Int, desde: String, hasta: String) {
        viewModelScope.launch {
            _statusInt.value = ResponseStatus.Loading()
            handleResponseStatusInt(anularPedidoUseCase(id))
            handleResponseStatusPedido(listarPedidoPorFechaUseCase(desde, hasta))
        }
    }

    fun listarPedido(desde: String, hasta: String) {
        viewModelScope.launch {
            _statusListaPedido.value = ResponseStatus.Loading()
            handleResponseStatusPedido(listarPedidoPorFechaUseCase(desde, hasta))
        }
    }

    fun listarDetalle(idPedido: Int) {
        viewModelScope.launch {
            _statusListaDetalle.value = ResponseStatus.Loading()
            handleResponseStatusDetalle(listarDetallePedidoUseCase(idPedido))
        }
    }
}