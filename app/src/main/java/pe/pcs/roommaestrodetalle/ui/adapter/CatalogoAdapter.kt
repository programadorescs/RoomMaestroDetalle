package pe.pcs.roommaestrodetalle.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.pcs.roommaestrodetalle.R
import pe.pcs.roommaestrodetalle.core.UtilsCommon
import pe.pcs.roommaestrodetalle.data.model.ProductoModel
import pe.pcs.roommaestrodetalle.databinding.ItemsCatalogoBinding

class CatalogoAdapter(
    private val lista: List<ProductoModel>,
    private val iClickListener: IClickListener
): RecyclerView.Adapter<CatalogoAdapter.CaViewHolder>() {

    interface IClickListener {
        fun clickItem(entidad: ProductoModel)
    }

    inner class CaViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemsCatalogoBinding.bind(view)

        fun enlazar(entidad: ProductoModel) {
            binding.tvTitulo.text = entidad.descripcion
            binding.tvPrecio.text = UtilsCommon.formatearDoubleString(entidad.precio)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaViewHolder {
        // Pasamos la vista que sera inflada
        return CaViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.items_catalogo, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CaViewHolder, position: Int) {
        // Otener cada item de la lista
        val item = lista[position]

        holder.enlazar(item)

        holder.binding.ibAgregar.setOnClickListener {
            iClickListener.clickItem(item)
        }
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}