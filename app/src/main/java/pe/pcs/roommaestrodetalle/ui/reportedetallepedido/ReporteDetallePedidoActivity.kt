package pe.pcs.roommaestrodetalle.ui.reportedetallepedido

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pe.pcs.roommaestrodetalle.ui.utils.ResponseStatus
import pe.pcs.roommaestrodetalle.ui.utils.UtilsCommon
import pe.pcs.roommaestrodetalle.ui.utils.UtilsMessage
import pe.pcs.roommaestrodetalle.databinding.ActivityReporteDetallePedidoBinding
import pe.pcs.roommaestrodetalle.ui.adapter.ReporteDetallePedidoAdapter

@AndroidEntryPoint
class ReporteDetallePedidoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReporteDetallePedidoBinding
    private val viewModel: ReporteDetallePedidoViewModel by viewModels()
    private val args: ReporteDetallePedidoActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReporteDetallePedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        initAdapter()
        initUIState()

        binding.tvPedido.text = "Pedido: ${args.idPedido.toString()}"
        binding.tvImporte.text = UtilsCommon.formatearDoubleString(args.totImporte.toDouble())

        viewModel.listarDetalle(args.idPedido)
    }

    private fun initListener() {
        binding.btAtras.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun initAdapter() {
        binding.rvLista.apply {
            layoutManager = LinearLayoutManager(this@ReporteDetallePedidoActivity)
            adapter = ReporteDetallePedidoAdapter(emptyList())
        }
    }

    private fun initUIState() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Escuchar
                viewModel.uiState.collect {
                    when (it) {
                        is ResponseStatus.Error -> {
                            binding.progressBar.isVisible = false

                            if (it.message.isNotEmpty())
                                UtilsMessage.showAlertOk(
                                    "ERROR", it.message, this@ReporteDetallePedidoActivity
                                )
                        }

                        is ResponseStatus.Loading -> binding.progressBar.isVisible = true
                        is ResponseStatus.Success -> {
                            binding.progressBar.isVisible = false

                            binding.rvLista.adapter = ReporteDetallePedidoAdapter(it.data)
                        }
                    }
                }
            }
        }

    }
}