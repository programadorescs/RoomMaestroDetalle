package pe.pcs.roommaestrodetalle.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pe.pcs.roommaestrodetalle.core.UtilsCommon
import pe.pcs.roommaestrodetalle.data.model.ProductoModel
import pe.pcs.roommaestrodetalle.databinding.ItemsProductoBinding

class ProductoAdapter(
    private val iOnClickListener: IOnClickListener
): ListAdapter<ProductoModel, ProductoAdapter.BindViewHolder>(DiffCallback) {

    interface IOnClickListener {
        fun clickEditar(entidad: ProductoModel)
        fun clickEliminar(entidad: ProductoModel)
    }

    private object DiffCallback: DiffUtil.ItemCallback<ProductoModel>() {
        override fun areItemsTheSame(oldItem: ProductoModel, newItem: ProductoModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductoModel, newItem: ProductoModel): Boolean {
            return oldItem == newItem
        }
    }

    inner class BindViewHolder(private val binding: ItemsProductoBinding): RecyclerView.ViewHolder(binding.root) {
        fun enlazar(entidad: ProductoModel) {
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