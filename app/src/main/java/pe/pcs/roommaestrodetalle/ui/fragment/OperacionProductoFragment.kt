package pe.pcs.roommaestrodetalle.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import pe.pcs.roommaestrodetalle.core.UtilsCommon
import pe.pcs.roommaestrodetalle.core.UtilsMessage
import pe.pcs.roommaestrodetalle.data.model.ProductoModel
import pe.pcs.roommaestrodetalle.databinding.FragmentOperacionProductoBinding
import pe.pcs.roommaestrodetalle.ui.viewmodel.ProductoViewModel


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
            if(it != null) {
                binding.etDescripcion.setText(it.descripcion)
                binding.etCosto.setText(it.costo.toString())
                binding.etPrecio.setText(it.precio.toString())
                binding.etExistencia.setText(it.existencia.toString())
            }
        })

        viewModel.mErrorStatus.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()) {
                UtilsMessage.showAlertOk(
                    "ERROR",
                    it,
                    requireContext()
                )

                viewModel.mErrorStatus.postValue("")
            }
        })

        viewModel.operacionExitosa.observe(viewLifecycleOwner, Observer {
            if(it) {
                UtilsMessage.showToast("¡Felicidades, el registro fue grabado!")
                UtilsCommon.limpiarEditText(requireView())
                binding.etDescripcion.requestFocus()
                viewModel.operacionExitosa.postValue(false)
                viewModel.setItemProducto(null)
            }
        })

        binding.fabGrabar.setOnClickListener {
            if(binding.etDescripcion.text.toString().isEmpty() ||
                binding.etCosto.text.toString().isEmpty() ||
                binding.etPrecio.text.toString().isEmpty() ||
                binding.etExistencia.text.toString().isEmpty()) {
                UtilsMessage.showAlertOk(
                    "ADVERTENCIA",
                    "Todos los datos son requeridos.",
                requireContext())

                return@setOnClickListener
            }

            val entidad = ProductoModel().apply {
                id = viewModel.itemProducto.value?.id ?: 0
                descripcion = binding.etDescripcion.text.toString().trim()
                costo = binding.etCosto.text.toString().toDouble()
                precio = binding.etPrecio.text.toString().toDouble()
                existencia = binding.etExistencia.text.toString().toInt()
            }

            viewModel.grabar(entidad)
        }
    }
}