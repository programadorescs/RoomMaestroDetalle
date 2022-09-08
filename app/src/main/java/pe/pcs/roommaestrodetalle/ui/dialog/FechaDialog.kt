package pe.pcs.roommaestrodetalle.ui.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import java.util.*

class FechaDialog: DialogFragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var txtDato: EditText

    companion object {
        fun newInstance(txtFecha: EditText): FechaDialog {
            /*val fragment = FechaDialog()
            fragment.txtDato = txtFecha
            return fragment*/

            return FechaDialog().apply {
                txtDato = txtFecha
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //return super.onCreateDialog(savedInstanceState)
        val c = Calendar.getInstance()

        return DatePickerDialog(
            requireContext(),
            this,
            c[Calendar.YEAR],
            c[Calendar.MONTH],
            c[Calendar.DAY_OF_MONTH]
        )
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        txtDato.setText(
            p0?.year.toString() + "-" +
                    String.format("%02d", p0?.month!! + 1) + "-" +
                    String.format("%02d", p0.dayOfMonth)
        )
    }

}