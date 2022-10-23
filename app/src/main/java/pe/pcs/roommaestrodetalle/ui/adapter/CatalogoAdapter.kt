package pe.pcs.roommaestrodetalle.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pe.pcs.roommaestrodetalle.core.UtilsCommon
import pe.pcs.roommaestrodetalle.data.model.ProductoModel
import pe.pcs.roommaestrodetalle.databinding.ItemsCatalogoBinding

class CatalogoAdapter(
    private val iOnClickListener: IOnClickListener
): ListAdapter<ProductoModel, CatalogoAdapter.BindViewHolder>(DiffCallback) {

    interface IOnClickListener {
        fun clickItem(entidad: ProductoModel)
    }

    private object DiffCallback: DiffUtil.ItemCallback<ProductoModel>() {
        override fun areItemsTheSame(oldItem: ProductoModel, newItem: ProductoModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductoModel, newItem: ProductoModel): Boolean {
            return oldItem == newItem
        }
    }

    inner class BindViewHolder(private val binding: ItemsCatalogoBinding): RecyclerView.ViewHolder(binding.root) {
        fun enlazar(entidad: ProductoModel) {
            binding.tvTitulo.text = entidad.descripcion
            binding.tvPrecio.text = UtilsCommon.formatearDoubleString(entidad.precio)

            binding.ibAgregar.setOnClickListener { iOnClickListener.clickItem(entidad) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindViewHolder {
        return BindViewHolder(
            ItemsCatalogoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BindViewHolder, position: Int) {
        // Otener cada item de la lista
        val item = getItem(position)

        holder.enlazar(item)
    }
}