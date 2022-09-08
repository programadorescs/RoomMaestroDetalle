package pe.pcs.roommaestrodetalle.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pe.pcs.roommaestrodetalle.core.UtilsCommon
import pe.pcs.roommaestrodetalle.databinding.CuadroCantidadBinding

class CantidadDialog: DialogFragment() {

    private lateinit var mContexto: Context
    private var titulo: String = ""
    private var mensaje: String = ""
    private var precio: Double = 0.0
    private lateinit var iEnviarListener: IEnviarListener
    private lateinit var binding: CuadroCantidadBinding

    companion object {
        private var cantidad: Int = 1

        fun newInstance(contexto: Context, mTitulo: String, mMensaje: String, mPrecio: Double, myListener: IEnviarListener): CantidadDialog {
            val instancia = CantidadDialog()
            instancia.isCancelable = false
            instancia.mContexto = contexto
            instancia.titulo = mTitulo
            instancia.mensaje = mMensaje
            instancia.precio = mPrecio
            instancia.iEnviarListener = myListener
            cantidad = 1

            return instancia
        }
    }

    interface IEnviarListener {
        fun enviarItem(cantidad: Int, precio: Double)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //return super.onCreateDialog(savedInstanceState)
        binding = CuadroCantidadBinding.inflate(LayoutInflater.from(requireContext()))

        binding.etPrecio.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        binding.etPrecio.setText(UtilsCommon.formatearDosDecimales(precio).toString())

        binding.ibMenos.setOnClickListener {
            if(cantidad > 1){
                cantidad--
                binding.tvCantidad.text = cantidad.toString()
            }
        }

        binding.ibMas.setOnClickListener {
            cantidad++
            binding.tvCantidad.text = cantidad.toString()
        }

        return MaterialAlertDialogBuilder(requireContext()).apply {
            setView(binding.root)
            setCancelable(false)
            setTitle(titulo)
            setMessage(mensaje)

            setPositiveButton("Aceptar") { dialog, _ ->
                if(binding.etPrecio.text.isNullOrEmpty()){
                    binding.etPrecio.setText(UtilsCommon.formatearDosDecimales(precio).toString())
                }

                if(binding.etPrecio.text.toString().toDouble() == 0.0){
                    binding.etPrecio.setText(UtilsCommon.formatearDosDecimales(precio).toString())
                }

                iEnviarListener.enviarItem(binding.tvCantidad.text.toString().toInt(), binding.etPrecio.text.toString().toDouble())

                dialog.dismiss()
            }

            setNegativeButton("Cancelar") { dialog, _ ->
                iEnviarListener.enviarItem(0, 0.0)
                dialog.dismiss()
            }
        }.create()
    }

}