package pe.pcs.roommaestrodetalle.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.pcs.roommaestrodetalle.data.model.ProductoModel
import pe.pcs.roommaestrodetalle.data.repository.ProductoRepository
import javax.inject.Inject

@HiltViewModel
class ProductoViewModel @Inject constructor(
    private val repository : ProductoRepository
) : ViewModel() {

    var lista = MutableLiveData<List<ProductoModel>>()

    private var _itemProducto = MutableLiveData<ProductoModel?>()
    val itemProducto: LiveData<ProductoModel?> = _itemProducto

    private val _progressBar = MutableLiveData<Boolean>()
    var progressBar: LiveData<Boolean> = _progressBar

    private var _msgError = MutableLiveData<String?>()
    val msgError: LiveData<String?> = _msgError

    var operacionExitosa = MutableLiveData<Boolean>()

    // Para el item seleccionado
    fun setItemProducto(item: ProductoModel?) {
        _itemProducto.postValue(item)
    }

    fun limpiarMsgError() {
        _msgError.postValue("")
    }

    fun listar(dato: String) {
        _progressBar.postValue(true)

        viewModelScope.launch {
            try {
                lista.postValue(repository.getListarPorNombre(dato))
            } catch (e: Exception) {
                _msgError.postValue(e.message)
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
                    val rpta = repository.grabar(producto)

                    lista.postValue(repository.getListarTodo())
                    rpta.toLong()
                } catch (e: Exception) {
                    _msgError.postValue(e.message)
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
                    val rpta = repository.eliminar(producto)
                    lista.postValue(repository.getListarTodo())
                    rpta
                } catch (e: Exception) {
                    _msgError.postValue(e.message)
                    0
                } finally {
                    _progressBar.postValue(false)
                }
            }

            operacionExitosa.postValue(result > 0)
        }
    }

}