package pe.pcs.roommaestrodetalle.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.pcs.roommaestrodetalle.data.model.ProductoModel
import pe.pcs.roommaestrodetalle.data.repository.ProductoRepositorio

class ProductoViewModel: ViewModel() {

    private val repositorio = ProductoRepositorio()

    var lista = MutableLiveData<List<ProductoModel>>()

    private var _itemProducto = MutableLiveData<ProductoModel?>()
    val itemProducto: LiveData<ProductoModel?> = _itemProducto

    private val _progressBar = MutableLiveData<Boolean>()
    var progressBar: LiveData<Boolean> = _progressBar

    var mErrorStatus = MutableLiveData<String?>()

    var operacionExitosa = MutableLiveData<Boolean>()

    // Para el item seleccionado
    fun setItemProducto(item: ProductoModel?) {
        _itemProducto.postValue(item)
    }

    fun listar(dato: String) {
        _progressBar.postValue(true)

        viewModelScope.launch {
            try {
                lista.postValue(repositorio.getListarPorNombre(dato))
            } catch (e: Exception) {
                mErrorStatus.postValue(e.message)
            } finally {
                _progressBar.postValue(false)
            }
        }
    }

    fun grabar(producto: ProductoModel) {
        _progressBar.postValue(true)

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    val rpta = if(producto.id == 0)
                        repositorio.insertar(producto)
                    else
                        repositorio.actualizar(producto)

                    lista.postValue(repositorio.getListarTodo())
                    rpta.toLong()
                } catch (e: Exception) {
                    mErrorStatus.postValue(e.message)
                    0
                } finally {
                    _progressBar.postValue(false)
                }
            }

            operacionExitosa.postValue(result > 0)
        }
    }

    fun eliminar(producto: ProductoModel) {
        _progressBar.postValue(true)

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    val rpta = repositorio.eliminar(producto)
                    lista.postValue(repositorio.getListarTodo())
                    rpta
                } catch (e: Exception) {
                    mErrorStatus.postValue(e.message)
                    0
                } finally {
                    _progressBar.postValue(false)
                }
            }

            operacionExitosa.postValue(result > 0)
        }
    }

}