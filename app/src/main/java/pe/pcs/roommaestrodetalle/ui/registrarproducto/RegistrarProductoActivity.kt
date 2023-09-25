package pe.pcs.roommaestrodetalle.ui.registrarproducto

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pe.pcs.roommaestrodetalle.core.ResponseStatus
import pe.pcs.roommaestrodetalle.core.UtilsCommon
import pe.pcs.roommaestrodetalle.core.UtilsMessage
import pe.pcs.roommaestrodetalle.databinding.ActivityRegistrarProductoBinding
import pe.pcs.roommaestrodetalle.domain.model.Producto

@AndroidEntryPoint
class RegistrarProductoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarProductoBinding
    private val viewModel: RegistrarProductoViewModel by viewModels()
    private val args: RegistrarProductoActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUIState()
        initListeners()

        viewModel.obtenerProducto(args.idProducto)
    }

    private fun initListeners() {

        binding.btAtras.setOnClickListener { onBackPressed() }

        binding.fabGrabar.setOnClickListener {
            UtilsCommon.ocultarTeclado(it)

            if (binding.etDescripcion.text.toString().isEmpty() ||
                binding.etCosto.text.toString().isEmpty() ||
                binding.etPrecio.text.toString().isEmpty()
            ) {
                UtilsMessage.showAlertOk(
                    "ADVERTENCIA",
                    "Todos los datos son requeridos.",
                    this
                )

                return@setOnClickListener
            }

            viewModel.grabar(
                Producto().apply {
                    id = viewModel.item.value?.id ?: 0
                    descripcion = binding.etDescripcion.text.toString().trim()
                    costo = binding.etCosto.text.toString().toDouble()
                    precio = binding.etPrecio.text.toString().toDouble()
                }
            )
        }

    }

    private fun initUIState() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStateProducto.collect {
                    when (it) {
                        is ResponseStatus.Error -> {
                            binding.progressBar.isVisible = false

                            if (it.message.isNotEmpty())
                                UtilsMessage.showAlertOk(
                                    "ERROR", it.message, this@RegistrarProductoActivity
                                )
                        }

                        is ResponseStatus.Loading -> binding.progressBar.isVisible = true
                        is ResponseStatus.Success -> {
                            binding.progressBar.isVisible = false

                            if (it.data == null)
                                return@collect

                            binding.etDescripcion.setText(it.data.descripcion)
                            binding.etCosto.setText(UtilsCommon.formatearDoubleString(it.data.costo))
                            binding.etPrecio.setText(UtilsCommon.formatearDoubleString(it.data.precio))
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is ResponseStatus.Error -> {
                            binding.progressBar.isVisible = false

                            if (it.message.isNotEmpty())
                                UtilsMessage.showAlertOk(
                                    "ERROR", it.message, this@RegistrarProductoActivity
                                )
                        }

                        is ResponseStatus.Loading -> binding.progressBar.isVisible = true
                        is ResponseStatus.Success -> {
                            binding.progressBar.isVisible = false

                            if (it.data > 0) {
                                UtilsMessage.showToast("¡Felicidades, el registro fue grabado!")
                                UtilsCommon.limpiarEditText(binding.root.rootView)
                                binding.etDescripcion.requestFocus()
                                viewModel.resetItem()
                            }
                        }
                    }
                }
            }
        }

    }
}