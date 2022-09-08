package pe.pcs.roommaestrodetalle.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pe.pcs.roommaestrodetalle.R
import pe.pcs.roommaestrodetalle.core.UtilsCommon
import pe.pcs.roommaestrodetalle.data.model.ProductoModel
import pe.pcs.roommaestrodetalle.databinding.ItemsProductoBinding

class ProductAdapter(
    private val iOnClickListener: IOnClickListener
): ListAdapter<ProductoModel, ProductAdapter.BindViewHolder>(DiffCallback) {

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

    inner class BindViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemsProductoBinding.bind(view)

        fun bind(entidad: ProductoModel) {
            binding.tvTitulo.text = entidad.descripcion
            binding.tvCosto.text = UtilsCommon.formatearDosDecimales(entidad.costo).toString()
            binding.tvPrecio.text = UtilsCommon.formatearDosDecimales(entidad.precio).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindViewHolder {
        return BindViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.items_producto, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BindViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.binding.ibEditar.setOnClickListener {
            iOnClickListener.clickEditar(item)
        }

        holder.binding.ibEliminar.setOnClickListener {
            iOnClickListener.clickEliminar(item)
        }
    }
}