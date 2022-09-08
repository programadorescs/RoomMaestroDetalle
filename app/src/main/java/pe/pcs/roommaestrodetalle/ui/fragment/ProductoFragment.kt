package pe.pcs.roommaestrodetalle.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pe.pcs.roommaestrodetalle.R
import pe.pcs.roommaestrodetalle.core.UtilsMessage
import pe.pcs.roommaestrodetalle.data.model.ProductoModel
import pe.pcs.roommaestrodetalle.databinding.FragmentProductoBinding
import pe.pcs.roommaestrodetalle.ui.adapter.ProductAdapter
import pe.pcs.roommaestrodetalle.ui.adapter.ProductoAdapter
import pe.pcs.roommaestrodetalle.ui.viewmodel.ProductoViewModel


class ProductoFragment : Fragment(), ProductAdapter.IOnClickListener { //ProductoAdapter.IClickListener

    private lateinit var binding: FragmentProductoBinding
    private val viewModel: ProductoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvLista.layoutManager = LinearLayoutManager(requireContext())

        //binding.rvLista.adapter = ProductoAdapter(this)
        binding.rvLista.adapter = ProductAdapter(this)

        viewModel.lista.observe(viewLifecycleOwner, Observer {
            //(binding.rvLista.adapter as ProductoAdapter).differ.submitList(it)
            (binding.rvLista.adapter as ProductAdapter).submitList(it)
        })

        viewModel.progressBar.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = it
        })

        viewModel.mErrorStatus.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()) {
                UtilsMessage.showAlertOk(
                    "ERROR", it, requireContext()
                )

                viewModel.mErrorStatus.postValue("")
            }
        })

        viewModel.operacionExitosa.observe(viewLifecycleOwner, Observer {
            if(it) {
                UtilsMessage.showToast("¡Felicidades, registro anulado correctamente!")
                viewModel.operacionExitosa.postValue(false)
            }
        })

        binding.fabNuevo.setOnClickListener {
            flagRetorno = true
            viewModel.setItemProducto(null)
            Navigation.findNavController(it).navigate(R.id.action_nav_producto_to_operacionProductoFragment)
        }

        binding.etBuscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (flagRetorno)
                    flagRetorno = false
                else {
                    viewModel.listar(p0.toString().trim())
                }
            }

        })

        if(viewModel.lista.value == null)
            viewModel.listar(binding.etBuscar.text.toString().trim())
    }

    override fun clickEliminar(entidad: ProductoModel) {
        MaterialAlertDialogBuilder(requireContext()).apply {
            setCancelable(false)
            setTitle("ELIMINAR")
            setMessage("¿Desea eliminar el registro: ${entidad.descripcion}")

            setPositiveButton("SI") { dialog, _ ->
                viewModel.eliminar(entidad)
                dialog.dismiss()
            }

            setNegativeButton("NO") { dialog, _ ->
                dialog.cancel()
            }
        }.create().show()
    }

    override fun clickEditar(entidad: ProductoModel) {
        flagRetorno = true
        viewModel.setItemProducto(entidad)
        Navigation.findNavController(requireView()).navigate(R.id.action_nav_producto_to_operacionProductoFragment)
    }

    companion object {
        private var flagRetorno = false
    }
}