package pe.pcs.roommaestrodetalle.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pe.pcs.roommaestrodetalle.data.EstadoRespuesta
import pe.pcs.roommaestrodetalle.data.model.DetallePedidoModel
import pe.pcs.roommaestrodetalle.data.model.PedidoModel
import pe.pcs.roommaestrodetalle.data.model.ProductoModel
import pe.pcs.roommaestrodetalle.data.repository.PedidoRepository
import javax.inject.Inject

@HiltViewModel
class PedidoViewModel @Inject constructor(
    private val repository : PedidoRepository
): ViewModel() {

    var listaProducto = MutableLiveData<List<ProductoModel>?>()

    private var _listaCarrito = MutableLiveData<MutableList<DetallePedidoModel>>()
    var listaCarrito: MutableLiveData<MutableList<DetallePedidoModel>> = _listaCarrito

    private var _totalItem = MutableLiveData<Int>()
    var totalItem: LiveData<Int> = _totalItem

    private var _totalImporte = MutableLiveData<Double>()
    var totalImporte: LiveData<Double> = _totalImporte

    private var _itemProducto = MutableLiveData<ProductoModel?>()
    val itemProducto: LiveData<ProductoModel?> = _itemProducto

    private val _status = MutableLiveData<EstadoRespuesta<List<ProductoModel>>>()
    val status: LiveData<EstadoRespuesta<List<ProductoModel>>> = _status

    private val _statusInt = MutableLiveData<EstadoRespuesta<Int>>()
    val statusInt: LiveData<EstadoRespuesta<Int>> = _statusInt

    private val _mensaje = MutableLiveData<String>()
    val mensaje: LiveData<String> = _mensaje

    init {
        _listaCarrito.value = mutableListOf()
    }

    fun setLimpiarMensaje() {
        _mensaje.postValue("")
    }

    // Para el item seleccionado
    fun setItemProducto(item: ProductoModel?) {
        _itemProducto.value = item
    }

    fun agregarProductoCarrito(cantidad: Int, precio: Double) {
        if (cantidad == 0 || precio == 0.0) return

        if (itemProducto.value == null) return

        listaCarrito.value?.forEach {
            if (it.idproducto == itemProducto.value?.id) {
                setItemProducto(null)
                _mensaje.postValue("Ya existe este elemento en su lista...")
                return
            }
        }

        val entidad = DetallePedidoModel().apply {
            idproducto = itemProducto.value!!.id
            this.descripcion = itemProducto.value!!.descripcion
            this.cantidad = cantidad
            this.precio = precio
            this.importe = cantidad * precio
        }

        setItemProducto(null)

        // Agrega el producto a la lista
        _listaCarrito.value?.add(entidad)

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

    private fun handleResponseStatus(responseStatus: EstadoRespuesta<List<ProductoModel>>) {
        if (responseStatus is EstadoRespuesta.Success) {
            listaProducto.value = responseStatus.data
        }

        _status.value = responseStatus
    }

    private fun handleResponseStatusInt(responseStatus: EstadoRespuesta<Int>) {
        if (responseStatus is EstadoRespuesta.Success) {
            _statusInt.value = responseStatus
        }

        _statusInt.value = responseStatus
    }

    fun listarProducto(dato: String) {
        viewModelScope.launch {
            _status.value = EstadoRespuesta.Loading()
            handleResponseStatus(repository.listarProducto(dato))
        }
    }

    fun registrarPedido(pedido: PedidoModel) {
        viewModelScope.launch {
            _statusInt.value = EstadoRespuesta.Loading()
            handleResponseStatusInt(repository.insertarPedido(pedido))
        }
    }

}