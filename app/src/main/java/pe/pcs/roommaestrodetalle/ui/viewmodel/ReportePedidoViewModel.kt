package pe.pcs.roommaestrodetalle.ui.viewmodel

import android.os.Message
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.pcs.roommaestrodetalle.data.model.PedidoModel
import pe.pcs.roommaestrodetalle.data.model.ReporteDetallePedidoModel
import pe.pcs.roommaestrodetalle.data.repository.PedidoRepository
import javax.inject.Inject

@HiltViewModel
class ReportePedidoViewModel @Inject constructor(
    private val repository : PedidoRepository
): ViewModel() {

    private val _listaPedido = MutableLiveData<List<PedidoModel>>()
    val listaPedido: LiveData<List<PedidoModel>> = _listaPedido

    private val _listaDetalle = MutableLiveData<List<ReporteDetallePedidoModel>>()
    val listaDetalle: LiveData<List<ReporteDetallePedidoModel>> = _listaDetalle

    private val _itemPedido = MutableLiveData<PedidoModel?>()
    val itemPedido: LiveData<PedidoModel?> = _itemPedido

    private var _msgError = MutableLiveData<String?>()
    val msgError: LiveData<String?> = _msgError

    private val _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean> = _progressBar

    var operacionExitosa = MutableLiveData<Boolean>()

    init {
        _listaPedido.value = mutableListOf()
        _listaDetalle.value = mutableListOf()
    }

    fun limpiarMsgError() {
        _msgError.postValue("")
    }

    fun setItem(entidad: PedidoModel?) {
        _itemPedido.postValue(entidad)
    }

    fun anularPedido(id: Int, desde: String, hasta: String) {
        _progressBar.postValue(true)

        viewModelScope.launch {
             val result = withContext(Dispatchers.IO) {
                 try {
                     repository.anularPedido(id)
                     _listaPedido.postValue(repository.listarPedidoPorFecha(desde, hasta))
                     true
                 } catch (e: Exception) {
                     _msgError.postValue(e.message)
                     false
                 } finally {
                     _progressBar.postValue(false)
                 }
             }

            operacionExitosa.postValue(result)
        }
    }

    fun listarPedido(desde: String, hasta: String) {
        _progressBar.postValue(true)

        viewModelScope.launch {
            try {
                _listaPedido.postValue(repository.listarPedidoPorFecha(desde, hasta))
            } catch (e: Exception) {
                _msgError.postValue(e.message)
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
                _msgError.postValue(e.message)
            } finally {
                _progressBar.postValue(false)
            }
        }
    }
}