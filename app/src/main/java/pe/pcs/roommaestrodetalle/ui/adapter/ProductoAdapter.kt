package pe.pcs.roommaestrodetalle.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pe.pcs.roommaestrodetalle.ui.utils.UtilsCommon
import pe.pcs.roommaestrodetalle.databinding.ItemsProductoBinding
import pe.pcs.roommaestrodetalle.domain.model.Producto

class ProductoAdapter(
    private val iOnClickListener: IOnClickListener
): ListAdapter<Producto, ProductoAdapter.BindViewHolder>(DiffCallback) {

    interface IOnClickListener {
        fun clickEditar(entidad: Producto)
        fun clickEliminar(entidad: Producto)
    }

    private object DiffCallback: DiffUtil.ItemCallback<Producto>() {
        override fun areItemsTheSame(oldItem: Producto, newItem: Producto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Producto, newItem: Producto): Boolean {
            return oldItem == newItem
        }
    }

    inner class BindViewHolder(private val binding: ItemsProductoBinding): RecyclerView.ViewHolder(binding.root) {
        fun enlazar(entidad: Producto) {
            binding.tvTitulo.text = entidad.descripcion
            binding.tvCosto.text = UtilsCommon.formatearDoubleString(entidad.costo)
            binding.tvPrecio.text = UtilsCommon.formatearDoubleString(entidad.precio)

            binding.ibEditar.setOnClickListener {
                iOnClickListener.clickEditar(entidad)
            }

            binding.ibEliminar.setOnClickListener {
                iOnClickListener.clickEliminar(entidad)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindViewHolder {
        return BindViewHolder(
            ItemsProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BindViewHolder, position: Int) {
        val item = getItem(position)

        holder.enlazar(item)
    }
}