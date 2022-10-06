package pe.pcs.roommaestrodetalle.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.pcs.roommaestrodetalle.R
import pe.pcs.roommaestrodetalle.core.UtilsCommon
import pe.pcs.roommaestrodetalle.data.model.ReporteDetallePedidoModel
import pe.pcs.roommaestrodetalle.databinding.ItemsDetallePedidoRealizadoBinding

class ReporteDetallePedidoAdapter(
    private val lista: List<ReporteDetallePedidoModel>
): RecyclerView.Adapter<ReporteDetallePedidoAdapter.RdpaViewHolder>() {

    inner class RdpaViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemsDetallePedidoRealizadoBinding.bind(view)

        fun enlazar(entidad: ReporteDetallePedidoModel) {
            binding.tvDescripcion.text = entidad.descripcion
            binding.tvPrecio.text = UtilsCommon.formatearDoubleString(entidad.precio)
            binding.tvCantidad.text = "x ${entidad.cantidad}"
            binding.tvImporte.text = UtilsCommon.formatearDoubleString(entidad.importe)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RdpaViewHolder {
        return RdpaViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.items_detalle_pedido_realizado, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RdpaViewHolder, position: Int) {
        val item = lista[position]

        holder.enlazar(item)
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}