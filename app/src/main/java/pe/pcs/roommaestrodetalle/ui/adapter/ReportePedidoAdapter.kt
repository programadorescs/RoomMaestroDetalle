package pe.pcs.roommaestrodetalle.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pe.pcs.roommaestrodetalle.core.UtilsCommon
import pe.pcs.roommaestrodetalle.data.model.PedidoModel
import pe.pcs.roommaestrodetalle.databinding.ItemsPedidoBinding

class ReportePedidoAdapter(
    private val iOnClickListener: IOnClickListener
): ListAdapter<PedidoModel, ReportePedidoAdapter.BindViewHolder>(DiffCallback) {

    interface IOnClickListener {
        fun clickAnular(entidad: PedidoModel)
        fun clickDetalle(entidad: PedidoModel)
    }

    private object DiffCallback: DiffUtil.ItemCallback<PedidoModel>() {
        override fun areItemsTheSame(oldItem: PedidoModel, newItem: PedidoModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PedidoModel, newItem: PedidoModel): Boolean {
            return oldItem == newItem
        }
    }

    inner class BindViewHolder(private val binding: ItemsPedidoBinding): RecyclerView.ViewHolder(binding.root) {
        fun enlazar(entidad: PedidoModel) {
            binding.tvTitulo.text = "Pedido: ${entidad.id.toString()}"
            binding.tvFecha.text = entidad.fecha
            binding.tvTotal.text = UtilsCommon.formatearDoubleString(entidad.total)
            binding.tvCliente.text = entidad.cliente
            binding.tvEstado.text = if (entidad.estado.lowercase() == "anulado") entidad.estado.uppercase() else entidad.estado

            binding.btAnular.setOnClickListener {
                iOnClickListener.clickAnular(entidad)
            }

            binding.btDetalle.setOnClickListener {
                iOnClickListener.clickDetalle(entidad)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindViewHolder {
        return BindViewHolder(
            ItemsPedidoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BindViewHolder, position: Int) {
        val item = getItem(position)

        holder.enlazar(item)
    }
}