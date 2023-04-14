package pe.pcs.roommaestrodetalle.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import pe.pcs.roommaestrodetalle.core.UtilsCommon
import pe.pcs.roommaestrodetalle.core.UtilsMessage
import pe.pcs.roommaestrodetalle.data.EstadoRespuesta
import pe.pcs.roommaestrodetalle.data.model.ProductoModel
import pe.pcs.roommaestrodetalle.databinding.FragmentOperacionProductoBinding
import pe.pcs.roommaestrodetalle.ui.viewmodel.ProductoViewModel

@AndroidEntryPoint
class OperacionProductoFragment : Fragment() {

    private lateinit var binding: FragmentOperacionProductoBinding
    private val viewModel: ProductoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOperacionProductoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.itemProducto.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.etDescripcion.setText(it.descripcion)
                binding.etCosto.setText(it.costo.toString())
                binding.etPrecio.setText(it.precio.toString())
            }
        })

        viewModel.statusInt.observe(viewLifecycleOwner) {
            when (it) {
                is EstadoRespuesta.Loading -> binding.progressBar.isVisible = true
                is EstadoRespuesta.Error -> {
                    binding.progressBar.isVisible = false

                    if (it.message.isNotEmpty())
                        UtilsMessage.showAlertOk(
                            "ERROR", it.message, requireContext()
                        )

                    it.message = ""
                }
                is EstadoRespuesta.Success -> {
                    binding.progressBar.isVisible = false

                    if (it.data > 0) {
                        UtilsMessage.showToast("¡Felicidades, el registro fue grabado!")
                        UtilsCommon.limpiarEditText(requireView())
                        binding.etDescripcion.requestFocus()
                        viewModel.setItemProducto(null)
                    }

                    it.data = 0
                }
            }
        }

        binding.fabGrabar.setOnClickListener {
            UtilsCommon.ocultarTeclado(it)

            if (binding.etDescripcion.text.toString().isEmpty() ||
                binding.etCosto.text.toString().isEmpty() ||
                binding.etPrecio.text.toString().isEmpty()
            ) {
                UtilsMessage.showAlertOk(
                    "ADVERTENCIA",
                    "Todos los datos son requeridos.",
                    requireContext()
                )

                return@setOnClickListener
            }

            val entidad = ProductoModel().apply {
                id = viewModel.itemProducto.value?.id ?: 0
                descripcion = binding.etDescripcion.text.toString().trim()
                costo = binding.etCosto.text.toString().toDouble()
                precio = binding.etPrecio.text.toString().toDouble()
            }

            viewModel.grabar(entidad)
        }
    }
}