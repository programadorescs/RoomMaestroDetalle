package pe.pcs.roommaestrodetalle.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pe.pcs.roommaestrodetalle.core.UtilsCommon
import pe.pcs.roommaestrodetalle.core.UtilsMessage
import pe.pcs.roommaestrodetalle.data.EstadoRespuesta
import pe.pcs.roommaestrodetalle.databinding.FragmentReporteDetallePedidoBinding
import pe.pcs.roommaestrodetalle.ui.adapter.ReporteDetallePedidoAdapter
import pe.pcs.roommaestrodetalle.ui.viewmodel.ReportePedidoViewModel

@AndroidEntryPoint
class ReporteDetallePedidoFragment : Fragment() {

    private lateinit var binding: FragmentReporteDetallePedidoBinding
    private val viewModel: ReportePedidoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReporteDetallePedidoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvLista.layoutManager = LinearLayoutManager(requireContext())

        viewModel.listaDetalle.observe(viewLifecycleOwner, Observer {
            binding.rvLista.adapter = it?.let { it1 -> ReporteDetallePedidoAdapter(it1) }
        })

        viewModel.statusListaDetalle.observe(viewLifecycleOwner) {
            when(it) {
                is EstadoRespuesta.Error -> {
                    binding.progressBar.isVisible = false
                    UtilsMessage.showAlertOk(
                        "ERROR", it.message, requireContext()
                    )
                }
                is EstadoRespuesta.Loading -> binding.progressBar.isVisible = true
                is EstadoRespuesta.Success -> binding.progressBar.isVisible = false
            }
        }

        viewModel.itemPedido.observe(viewLifecycleOwner, Observer {
            if(viewModel.itemPedido.value != null) {
                binding.tvPedido.text = "Pedido: ${viewModel.itemPedido.value?.id ?: 0}"
                binding.tvImporte.text = UtilsCommon.formatearDoubleString(viewModel.itemPedido.value?.total ?: 0.0)
            }
        })

        viewModel.listarDetalle(viewModel.itemPedido.value?.id ?: 0)
    }
}