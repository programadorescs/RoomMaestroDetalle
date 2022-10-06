package pe.pcs.roommaestrodetalle.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.pcs.roommaestrodetalle.data.model.PedidoModel
import pe.pcs.roommaestrodetalle.data.model.ReporteDetallePedidoModel
import pe.pcs.roommaestrodetalle.data.repository.PedidoRepository

class ReportePedidoViewModel: ViewModel() {

    private val repository = PedidoRepository()

    private val _listaPedido = MutableLiveData<List<PedidoModel>>()
    val listaPedido: LiveData<List<PedidoModel>> = _listaPedido

    private val _listaDetalle = MutableLiveData<List<ReporteDetallePedidoModel>>()
    val listaDetalle: LiveData<List<ReporteDetallePedidoModel>> = _listaDetalle

    private val _itemPedido = MutableLiveData<PedidoModel?>()
    val itemPedido: LiveData<PedidoModel?> = _itemPedido

    private var _mErrorStatus = MutableLiveData<String?>()
    val mErrorStatus: LiveData<String?> = _mErrorStatus

    private val _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean> = _progressBar

    init {
        _listaPedido.value = mutableListOf()
        _listaDetalle.value = mutableListOf()
    }

    fun limpiarMsgError() {
        _mErrorStatus.postValue("")
    }

    fun setItem(entidad: PedidoModel?) {
        _itemPedido.postValue(entidad)
    }

    fun listarPedido(desde: String, hasta: String) {
        _progressBar.postValue(true)

        viewModelScope.launch {
            try {
                _listaPedido.postValue(repository.listarPedidoPorFecha(desde, hasta))
            } catch (e: Exception) {
                _mErrorStatus.postValue(e.message)
            } finally {
                _progressBar.postValue(false)
            }
        }
    }

    fun listarDetalle(idPedido: Int) {
        _progressBar.postValue(true)

        viewModelScope.launch {
            try {
                _listaDetalle.postValue(repository.listarDetallePedido(idPedido))
            } catch (e: Exception) {
                _mErrorStatus.postValue(e.message)
            } finally {
                _progressBar.postValue(false)
            }
        }
    }
}