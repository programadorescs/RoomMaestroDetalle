package pe.pcs.roommaestrodetalle.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pe.pcs.roommaestrodetalle.core.UtilsCommon
import pe.pcs.roommaestrodetalle.data.model.DetallePedidoModel
import pe.pcs.roommaestrodetalle.databinding.ItemsCarritoBinding

class ListaCarritoAdapter(
    private val itemClick: IItemClickListener
): ListAdapter<DetallePedidoModel, BaseListViewHolder<*>>(DiffUtilCallback) {

    interface IItemClickListener {
        fun clickMas(entidad: DetallePedidoModel)
        fun clickMenos(entidad: DetallePedidoModel)
        fun clickElimnar(entidad: DetallePedidoModel)
    }

    private object DiffUtilCallback : DiffUtil.ItemCallback<DetallePedidoModel>() {
        override fun areItemsTheSame(
            oldItem: DetallePedidoModel,
            newItem: DetallePedidoModel
        ): Boolean = oldItem.idproducto == newItem.idproducto

        override fun areContentsTheSame(
            oldItem: DetallePedidoModel,
            newItem: DetallePedidoModel
        ): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseListViewHolder<*> {
        return BindViewHolderList(
            ItemsCarritoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseListViewHolder<*>, position: Int) {
        //holder.bind(getItem(position) as Nothing, position)
        when (holder) {
            is BindViewHolderList -> holder.bind(getItem(position), position)
        }
    }

    inner class BindViewHolderList(private val binding: ItemsCarritoBinding): BaseListViewHolder<DetallePedidoModel>(binding.root){
        override fun bind(item: DetallePedidoModel, position: Int) {
            binding.tvDescripcion.text = item.descripcion
            binding.tvCantidad.text = item.cantidad.toString()
            binding.tvImporte.text = UtilsCommon.formatearDoubleString(item.cantidad * item.precio)

            binding.ibMas.setOnClickListener { itemClick.clickMas(item) }
            binding.ibMenos.setOnClickListener { itemClick.clickMenos(item) }
            binding.ibEliminar.setOnClickListener { itemClick.clickElimnar(item) }
        }
    }
}