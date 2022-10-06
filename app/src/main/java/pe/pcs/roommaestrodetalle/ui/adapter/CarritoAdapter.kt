package pe.pcs.roommaestrodetalle.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.pcs.roommaestrodetalle.R
import pe.pcs.roommaestrodetalle.core.UtilsCommon
import pe.pcs.roommaestrodetalle.data.model.DetallePedidoModel
import pe.pcs.roommaestrodetalle.databinding.ItemsCarritoBinding

class CarritoAdapter(
    private val iOnClickListener: IOnClickListener
): RecyclerView.Adapter<CarritoAdapter.BindViewHolder>() {

    private var lista: List<DetallePedidoModel> = listOf()

    interface  IOnClickListener {
        fun clickMas(entidad: DetallePedidoModel)
        fun clickMenos(entidad: DetallePedidoModel)
        fun clickElimnar(entidad: DetallePedidoModel)
    }

    inner class BindViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemsCarritoBinding.bind(view)

        fun enlazar(entidad: DetallePedidoModel) {
            binding.tvDescripcion.text = entidad.descripcion
            binding.tvCantidad.text = entidad.cantidad.toString()
            binding.tvImporte.text = UtilsCommon.formatearDoubleString(entidad.cantidad * entidad.precio)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindViewHolder {
        return BindViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.items_carrito, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BindViewHolder, position: Int) {
        val entidad = lista[position]

        holder.enlazar(entidad)

        holder.binding.ibMas.setOnClickListener { iOnClickListener.clickMas(entidad) }
        holder.binding.ibMenos.setOnClickListener { iOnClickListener.clickMenos(entidad) }
        holder.binding.ibEliminar.setOnClickListener { iOnClickListener.clickElimnar(entidad) }
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    fun setData(_lista: List<DetallePedidoModel>) {
        this.lista = _lista
        notifyDataSetChanged()
    }
}