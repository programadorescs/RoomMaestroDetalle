package pe.pcs.roommaestrodetalle.ui.registrarpedido

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import pe.pcs.roommaestrodetalle.core.ResponseStatus
import pe.pcs.roommaestrodetalle.core.UtilsAdmob
import pe.pcs.roommaestrodetalle.core.UtilsCommon
import pe.pcs.roommaestrodetalle.core.UtilsDate
import pe.pcs.roommaestrodetalle.core.UtilsMessage
import pe.pcs.roommaestrodetalle.databinding.FragmentRegistrarPedidoBinding
import pe.pcs.roommaestrodetalle.domain.model.DetallePedido
import pe.pcs.roommaestrodetalle.domain.model.Pedido
import pe.pcs.roommaestrodetalle.ui.adapter.CarritoAdapter

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

        viewModel.listaCarrito.observe(viewLifecycleOwner) {
            (binding.rvLista.adapter as CarritoAdapter).setData(it)
        }

        viewModel.totalImporte.observe(viewLifecycleOwner) {
            binding.tvImporte.text = UtilsCommon.formatearDoubleString(it)
        }

        viewModel.stateRegistrar.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseStatus.Loading -> binding.progressBar.isVisible = true
                is ResponseStatus.Error -> {
                    binding.progressBar.isVisible = false

                    if (it.message.isNotEmpty())
                        UtilsMessage.showAlertOk(
                            "ERROR", it.message, requireContext()
                        )
                }

                is ResponseStatus.Success -> {
                    binding.progressBar.isVisible = false

                    if (it.data > 0) {
                        MaterialAlertDialogBuilder(requireContext()).apply {
                            setTitle("CONFORME")
                            setMessage("¡El pedido fue registrado correctamente!")
                            setCancelable(false)
                            setPositiveButton("Aceptar") { dialog, _ ->

                                // Mostrar anuncio
                                UtilsAdmob.interstitial?.show(requireActivity())
                                // Carga un nuevo anuncio para el siguiente click
                                UtilsAdmob.initInterstitial()

                                binding.etCliente.setText("")

                                viewModel.limpiarCarrito()
                                viewModel.resetStateRegistrar()

                                Navigation.findNavController(requireView()).popBackStack()
                                dialog.cancel()
                            }
                        }.create().show()
                    }
                }
            }
        }

        binding.fabCarrito.setOnClickListener {
            UtilsCommon.ocultarTeclado(it)

            if (!viewModel.listaCarrito.value.isNullOrEmpty()) {
                viewModel.registrarPedido(
                    Pedido().apply {
                        fecha = UtilsDate.obtenerFechaActual()
                        total = viewModel.totalImporte.value!!
                        cliente = binding.etCliente.text.toString().trim().ifEmpty { "Publico general" }
                        estado = "vigente"
                        detalles = viewModel.listaCarrito.value!!
                    }
                )
            } else {
                UtilsMessage.showAlertOk(
                    "ADVERTENCIA",
                    "No exsite items en el carrito",
                    requireContext()
                )
            }
        }

        viewModel.listarCarrito()
    }

    override fun clickMas(entidad: DetallePedido) {
        viewModel.setAumentarCantidadProducto(entidad)
    }

    override fun clickMenos(entidad: DetallePedido) {
        viewModel.setDisminuirCantidadProducto(entidad)
    }

    override fun clickEliminar(entidad: DetallePedido) {
        MaterialAlertDialogBuilder(requireContext()).apply {
            setCancelable(false)
            setTitle("QUITAR")
            setMessage("¿Desea quitar el registro: ${entidad.descripcion}?")

            setPositiveButton("SI") { dialog, _ ->
                viewModel.quitarProductoCarrito(entidad)

                if (viewModel.listaCarrito.value?.size!! == 0) {
                    binding.etCliente.setText("")
                    Navigation.findNavController(requireView()).popBackStack()
                }

                dialog.dismiss()
            }

            setNegativeButton("NO") { dialog, _ ->
                dialog.cancel()
            }
        }.create().show()
    }
}