package pe.pcs.roommaestrodetalle.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.pcs.roommaestrodetalle.R
import pe.pcs.roommaestrodetalle.core.UtilsCommon
import pe.pcs.roommaestrodetalle.data.model.PedidoModel
import pe.pcs.roommaestrodetalle.databinding.ItemsPedidoRealizadoBinding

class ReportePedidoAdapter(
    private val lista: List<PedidoModel>,
    private val iClickListener: IClickListener
): RecyclerView.Adapter<ReportePedidoAdapter.RpaViewHolder>() {

    interface IClickListener {
        fun clickAnular(entidad: PedidoModel)
        fun clickDetalle(entidad: PedidoModel)
    }

    inner class RpaViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemsPedidoRealizadoBinding.bind(view)

        fun enlazar(entidad: PedidoModel) {
            binding.tvTitulo.text = "Pedido: ${entidad.id.toString()}"
            binding.tvFecha.text = entidad.fecha
            binding.tvTotal.text = UtilsCommon.formatearDoubleString(entidad.total)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RpaViewHolder {
        return RpaViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.items_pedido_realizado, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RpaViewHolder, position: Int) {
        val item = lista[position]

        holder.enlazar(item)

        holder.binding.btAnular.setOnClickListener {
            iClickListener.clickAnular(item)
        }

        holder.binding.btDetalle.setOnClickListener {
            iClickListener.clickDetalle(item)
        }
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}