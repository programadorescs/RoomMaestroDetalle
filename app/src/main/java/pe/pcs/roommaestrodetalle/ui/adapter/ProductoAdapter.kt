package pe.pcs.roommaestrodetalle.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pe.pcs.roommaestrodetalle.R
import pe.pcs.roommaestrodetalle.core.UtilsCommon
import pe.pcs.roommaestrodetalle.data.model.ProductoModel
import pe.pcs.roommaestrodetalle.databinding.ItemsProductoBinding

class ProductoAdapter(
    private val iClickListener: IClickListener
): RecyclerView.Adapter<ProductoAdapter.MyViewHolder>() {

    interface IClickListener {
        fun clickEliminar(entidad: ProductoModel)
        fun clickEditar(entidad: ProductoModel)
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemsProductoBinding.bind(view)

        fun enlazar(entidad: ProductoModel) {
            binding.tvTitulo.text = entidad.descripcion
            binding.tvCosto.text = UtilsCommon.formatearDosDecimales(entidad.costo).toString()
            binding.tvPrecio.text = UtilsCommon.formatearDosDecimales(entidad.precio).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Dibuja (infla) la vista
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.items_producto, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Obtiene un elemento de la lista
        val item = differ.currentList[position]

        // Envia el elemento al binding
        holder.enlazar(item)

        holder.binding.ibEditar.setOnClickListener {
            iClickListener.clickEditar(item)
        }

        holder.binding.ibEliminar.setOnClickListener {
            iClickListener.clickEliminar(item)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<ProductoModel>(){
        override fun areItemsTheSame(oldItem: ProductoModel, newItem: ProductoModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductoModel, newItem: ProductoModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this , diffCallBack)
}