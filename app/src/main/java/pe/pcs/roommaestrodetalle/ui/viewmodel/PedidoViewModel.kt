package pe.pcs.roommaestrodetalle.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.pcs.roommaestrodetalle.data.model.DetallePedidoModel
import pe.pcs.roommaestrodetalle.data.model.PedidoModel
import pe.pcs.roommaestrodetalle.data.model.ProductoModel
import pe.pcs.roommaestrodetalle.data.repository.PedidoRepository
import javax.inject.Inject

@HiltViewModel
class PedidoViewModel @Inject constructor(
    private val repository : PedidoRepository
): ViewModel() {

    var listaProducto = MutableLiveData<List<ProductoModel>>()

    private var _listaCarrito = MutableLiveData<MutableList<DetallePedidoModel>>()
    var listaCarrito: MutableLiveData<MutableList<DetallePedidoModel>> = _listaCarrito

    private var _totalItem = MutableLiveData<Int>()
    var totalItem: LiveData<Int> = _totalItem

    private var _totalImporte = MutableLiveData<Double>()
    var totalImporte: LiveData<Double> = _totalImporte

    private var _itemProducto = MutableLiveData<ProductoModel?>()
    val itemProducto: LiveData<ProductoModel?> = _itemProducto

    private val _progressBar = MutableLiveData<Boolean>()
    var progressBar: LiveData<Boolean> = _progressBar

    private var _msgError = MutableLiveData<String?>()
    val msgError: LiveData<String?> = _msgError

    var operacionExitosa = MutableLiveData<Boolean>()

    init {
        _listaCarrito.value = mutableListOf()
    }

    fun limpiarMsgError() {
        _msgError.postValue("")
    }

    // Para el item seleccionado
    fun setItemProducto(item: ProductoModel?) {
        _itemProducto.value = item
    }

    // Para el item seleccionado
    fun agregarProductoCarrito(item: DetallePedidoModel) {
        _listaCarrito.value?.add(item)

        _totalItem.postValue(
            listaCarrito.value?.sumOf { it.cantidad }
        )

        _totalImporte.postValue(
            listaCarrito.value?.sumOf { it.cantidad * it.precio }
        )
    }

    fun quitarProductoCarrito(item: DetallePedidoModel) {
        _listaCarrito.value?.remove(item)

        _totalItem.postValue(
            listaCarrito.value?.sumOf { it.cantidad }
        )

        _totalImporte.postValue(
            listaCarrito.value?.sumOf { it.cantidad * it.precio }
        )

        listaCarrito.postValue(_listaCarrito.value)
    }

    fun limpiarCarrito() {
        _listaCarrito.value = mutableListOf()

        _totalItem.postValue(
            listaCarrito.value?.sumOf { it.cantidad }
        )

        _totalImporte.postValue(
            listaCarrito.value?.sumOf { it.cantidad * it.precio }
        )
    }

    fun listarCarrito() {
        listaCarrito.postValue(_listaCarrito.value)
    }

    fun setAumentarCantidadProducto(item: DetallePedidoModel) {
        _listaCarrito.value?.forEach {
            if(it.idproducto == item.idproducto) {
                it.cantidad++
                it.importe = it.cantidad * it.precio
            }
        }

        _totalItem.postValue(
            listaCarrito.value?.sumOf { it.cantidad }
        )

        _totalImporte.postValue(
            listaCarrito.value?.sumOf { it.cantidad * it.precio }
        )

        listaCarrito.postValue(_listaCarrito.value)
    }

    fun setDisminuirCantidadProducto(item: DetallePedidoModel) {
        // Recorre la lista para disminuir la cantidad del producto seleccionado
        _listaCarrito.value?.forEach {
            if(it.idproducto == item.idproducto && it.cantidad > 1) {
                it.cantidad--
                it.importe = it.cantidad * it.precio
            }
        }

        // Obtiene la cantidad de producto en el carrito
        _totalItem.postValue(
            listaCarrito.value?.sumOf { it.cantidad }
        )

        // Suma el importe de los productos para obtener el total a pagar
        _totalImporte.postValue(
            listaCarrito.value?.sumOf { it.cantidad * it.precio }
        )

        listaCarrito.postValue(_listaCarrito.value)
    }

    fun listarProducto(dato: String) {
        _progressBar.postValue(true)

        viewModelScope.launch {
            try {
                listaProducto.postValue(repository.listarProducto(dato))
            } catch (e: Exception) {
                _msgError.postValue(e.message)
            } finally {
                _progressBar.postValue(false)
            }
        }
    }

    fun registrarPedido(pedido: PedidoModel, detallePedido: List<DetallePedidoModel>) {
        _progressBar.postValue(true)

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    repository.insertarPedido(pedido, detallePedido)
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

}