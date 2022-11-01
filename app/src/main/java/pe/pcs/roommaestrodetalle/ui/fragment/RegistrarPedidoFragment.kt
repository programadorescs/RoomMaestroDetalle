package pe.pcs.roommaestrodetalle.ui.fragment

import android.os.Bundle
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
import dagger.hilt.android.AndroidEntryPoint
import pe.pcs.roommaestrodetalle.core.UtilsAdmob
import pe.pcs.roommaestrodetalle.core.UtilsCommon
import pe.pcs.roommaestrodetalle.core.UtilsDate
import pe.pcs.roommaestrodetalle.core.UtilsMessage
import pe.pcs.roommaestrodetalle.data.model.DetallePedidoModel
import pe.pcs.roommaestrodetalle.data.model.PedidoModel
import pe.pcs.roommaestrodetalle.databinding.FragmentRegistrarPedidoBinding
import pe.pcs.roommaestrodetalle.ui.adapter.CarritoAdapter
import pe.pcs.roommaestrodetalle.ui.viewmodel.PedidoViewModel

@AndroidEntryPoint
class RegistrarPedidoFragment : Fragment(), CarritoAdapter.IOnClickListener {

    private lateinit var binding: FragmentRegistrarPedidoBinding
    private val viewModel: PedidoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrarPedidoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvLista.layoutManager = LinearLayoutManager(requireContext())

        binding.rvLista.adapter = CarritoAdapter(this)

        viewModel.progressBar.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = it
        })

        viewModel.listaCarrito.observe(viewLifecycleOwner, Observer {
            (binding.rvLista.adapter as CarritoAdapter).setData(it)
        })

        viewModel.totalImporte.observe(viewLifecycleOwner, Observer {
            binding.tvImporte.text = UtilsCommon.formatearDoubleString(it)
        })

        viewModel.msgError.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()) {
                UtilsMessage.showAlertOk("ERROR", it, requireContext())
                viewModel.limpiarMsgError()
            }
        })

        viewModel.operacionExitosa.observe(viewLifecycleOwner, Observer {
            if(!it) return@Observer

            MaterialAlertDialogBuilder(requireContext()).apply {
                setTitle("CONFORME")
                setMessage("¡El pedido fue registrado correctamente!")
                setCancelable(false)
                setPositiveButton("Aceptar") { dialog, _ ->

                    // Mostrar anuncio
                    UtilsAdmob.interstitial?.show(requireActivity())
                    // Carga un nuevo anuncio para el siguiente click
                    UtilsAdmob.initInterstitial()

                    viewModel.limpiarCarrito()
                    viewModel.operacionExitosa.postValue(false)

                    binding.etCliente.setText("")

                    Navigation.findNavController(requireView()).popBackStack()
                    dialog.cancel()

                }
            }.create().show()
        })

        binding.fabCarrito.setOnClickListener {
            UtilsCommon.ocultarTeclado(it)

            if(!viewModel.listaCarrito.value.isNullOrEmpty()) {
                val pedido = PedidoModel().apply {
                    fecha = UtilsDate.obtenerFechaActual()
                    total = viewModel.totalImporte.value!!
                    cliente = binding.etCliente.text.toString().trim().ifEmpty { "Publico general" }
                    estado = "vigente"
                    detalles = viewModel.listaCarrito.value
                }

                viewModel.registrarPedido(pedido, viewModel.listaCarrito.value!!)
            } else {
                UtilsMessage.showAlertOk(
                    "ADVERTENCIA",
                    "No exsite items en el carrito",
                    requireContext())
            }
        }

        viewModel.listarCarrito()
    }

    override fun clickMas(entidad: DetallePedidoModel) {
        viewModel.setAumentarCantidadProducto(entidad)
    }

    override fun clickMenos(entidad: DetallePedidoModel) {
        viewModel.setDisminuirCantidadProducto(entidad)
    }

    override fun clickElimnar(entidad: DetallePedidoModel) {
        MaterialAlertDialogBuilder(requireContext()).apply {
            setCancelable(false)
            setTitle("QUITAR")
            setMessage("¿Desea quitar el registro: ${entidad.descripcion}?")

            setPositiveButton("SI") {dialog, _ ->
                viewModel.quitarProductoCarrito(entidad)

                if(viewModel.listaCarrito.value?.size!! == 0) {
                    binding.etCliente.setText("")
                    Navigation.findNavController(requireView()).popBackStack()
                }

                dialog.dismiss()
            }

            setNegativeButton("NO"){ dialog, _ ->
                dialog.cancel()
            }
        }.create().show()
    }
}