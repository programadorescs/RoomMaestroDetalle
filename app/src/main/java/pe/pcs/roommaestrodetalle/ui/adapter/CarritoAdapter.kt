package pe.pcs.roommaestrodetalle.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
        fun clickEliminar(entidad: DetallePedidoModel)
    }

    inner class BindViewHolder(private val binding: ItemsCarritoBinding): RecyclerView.ViewHolder(binding.root) {

        fun enlazar(entidad: DetallePedidoModel) {
            binding.tvDescripcion.text = entidad.descripcion
            binding.tvCantidad.text = entidad.cantidad.toString()
            binding.tvImporte.text = UtilsCommon.formatearDoubleString(entidad.cantidad * entidad.precio)


            binding.ibMas.setOnClickListener { iOnClickListener.clickMas(entidad) }
            binding.ibMenos.setOnClickListener { iOnClickListener.clickMenos(entidad) }
            binding.ibEliminar.setOnClickListener { iOnClickListener.clickEliminar(entidad) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindViewHolder {
        return BindViewHolder(
            ItemsCarritoBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: BindViewHolder, position: Int) {
        val entidad = lista[position]

        holder.enlazar(entidad)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    fun setData(_lista: List<DetallePedidoModel>) {
        this.lista = _lista
        notifyDataSetChanged()
    }
}