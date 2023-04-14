package pe.pcs.roommaestrodetalle.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.pcs.roommaestrodetalle.data.EstadoRespuesta
import pe.pcs.roommaestrodetalle.data.model.PedidoModel
import pe.pcs.roommaestrodetalle.data.model.ReporteDetallePedidoModel
import pe.pcs.roommaestrodetalle.data.repository.PedidoRepository
import javax.inject.Inject

@HiltViewModel
class ReportePedidoViewModel @Inject constructor(
    private val repository : PedidoRepository
): ViewModel() {

    private val _listaPedido = MutableLiveData<List<PedidoModel>?>()
    val listaPedido: LiveData<List<PedidoModel>?> = _listaPedido

    private val _listaDetalle = MutableLiveData<List<ReporteDetallePedidoModel>?>()
    val listaDetalle: LiveData<List<ReporteDetallePedidoModel>?> = _listaDetalle

    private val _itemPedido = MutableLiveData<PedidoModel?>()
    val itemPedido: LiveData<PedidoModel?> = _itemPedido

    private val _statusListaPedido = MutableLiveData<EstadoRespuesta<List<PedidoModel>>>()
    val statusListaPedido: LiveData<EstadoRespuesta<List<PedidoModel>>> = _statusListaPedido

    private val _statusListaDetalle = MutableLiveData<EstadoRespuesta<List<ReporteDetallePedidoModel>>>()
    val statusListaDetalle: LiveData<EstadoRespuesta<List<ReporteDetallePedidoModel>>> = _statusListaDetalle

    private val _statusInt = MutableLiveData<EstadoRespuesta<Int>>()
    val statusInt: LiveData<EstadoRespuesta<Int>> = _statusInt

    init {
        _listaPedido.value = mutableListOf()
        _listaDetalle.value = mutableListOf()
    }

    fun setItem(entidad: PedidoModel?) {
        _itemPedido.postValue(entidad)
    }

    private fun handleResponseStatusPedido(responseStatus: EstadoRespuesta<List<PedidoModel>>) {
        if (responseStatus is EstadoRespuesta.Success) {
            _listaPedido.value = responseStatus.data
        }

        _statusListaPedido.value = responseStatus
    }

    private fun handleResponseStatusDetalle(responseStatus: EstadoRespuesta<List<ReporteDetallePedidoModel>>) {
        if (responseStatus is EstadoRespuesta.Success) {
            _listaDetalle.value = responseStatus.data
        }

        _statusListaDetalle.value = responseStatus
    }

    private fun handleResponseStatusInt(responseStatus: EstadoRespuesta<Int>) {
        _statusInt.value = responseStatus
    }

    fun anularPedido(id: Int, desde: String, hasta: String) {
        viewModelScope.launch {
            _statusInt.value = EstadoRespuesta.Loading()
            handleResponseStatusInt(repository.anularPedido(id))
            handleResponseStatusPedido(repository.listarPedidoPorFecha(desde, hasta))
        }
    }

    fun listarPedido(desde: String, hasta: String) {
        viewModelScope.launch {
            _statusListaPedido.value = EstadoRespuesta.Loading()
            handleResponseStatusPedido(repository.listarPedidoPorFecha(desde, hasta))
        }
    }

    fun listarDetalle(idPedido: Int) {
        viewModelScope.launch {
            _statusListaDetalle.value = EstadoRespuesta.Loading()
            handleResponseStatusDetalle(repository.listarDetallePedido(idPedido))
        }
    }
}