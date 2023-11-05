package pe.pcs.roommaestrodetalle.ui.utils

import android.widget.EditText
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

object UtilsDate {

    fun mostrarCalendario(etFecha: EditText, fragmentManager: FragmentManager) {
        val picker = MaterialDatePicker.Builder.datePicker().build()
        picker.addOnPositiveButtonClickListener { selection: Long ->
            etFecha.setText(
                SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).format(selection + 86400000L)
            )
        }
        picker.show(fragmentManager, picker.toString())
    }

    fun mostrarAnioActual(etFecha: EditText) {
        etFecha.setText(
            SimpleDateFormat("yyyy", Locale.ROOT).format(System.currentTimeMillis())
        )
    }

    fun mostrarFechaActual(etFecha: EditText) {
        etFecha.setText(
            SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).format(System.currentTimeMillis())
        )
    }

    fun obtenerFechaActual(): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ROOT).format(System.currentTimeMillis())
    }

    fun obtenerFechaHoraActual(): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ROOT).format(System.currentTimeMillis())
    }

}