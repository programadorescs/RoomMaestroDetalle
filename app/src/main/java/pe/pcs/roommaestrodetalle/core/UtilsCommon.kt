package pe.pcs.roommaestrodetalle.core

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.EditText
import pe.pcs.roommaestrodetalle.RoomMaestroDetalleApp
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import kotlin.math.pow
import kotlin.math.roundToLong

object UtilsCommon {

    fun ocultarTeclado(view: View) {
        val imm = RoomMaestroDetalleApp.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun limpiarEditTextCheckBox(view: View) {
        val it: Iterator<View> = view.touchables.iterator()
        while (it.hasNext()) {
            val v = it.next()
            if (v is CheckBox) v.isChecked = false
            if (v is EditText) v.setText("")
        }
    }

    fun limpiarEditText(view: View) {
        val it: Iterator<View> = view.touchables.iterator()
        while (it.hasNext()) {
            val v = it.next()
            if (v is EditText) v.setText("")
        }
    }

    fun padLeftFixed_Three(input: String): String {
        return if (input.length < 3) "000".substring(input.length) + input else input
    }

    fun padLeftFixed_Eight(input: String): String {
        return if (input.length < 8) "00000000".substring(input.length) + input else input
    }

    fun formatearDecimales(numero: Double, numeroDecimales: Int): Double {
        return (numero * 10.0.pow(numeroDecimales.toDouble())).roundToLong() / 10.0.pow(numeroDecimales.toDouble())
    }

    fun redondeoDecimales(numero: Double, numeroDecimales: Int): Double {
        val redondeado = BigDecimal(numero)
            .setScale(numeroDecimales, RoundingMode.HALF_UP)
        return redondeado.toDouble()
    }

    fun formatearDosDecimales(valor: Double): Double {
        val formato = DecimalFormat("#0.00")

        val dfs = DecimalFormatSymbols()
        dfs.decimalSeparator = '.'

        formato.decimalFormatSymbols = dfs

        return formato.format(valor).toDouble()
    }

    fun formatearDoubleString(valor: Double): String {
        val formato = DecimalFormat("#0.00")

        val dfs = DecimalFormatSymbols()
        dfs.decimalSeparator = '.'

        formato.decimalFormatSymbols = dfs

        return formato.format(valor).toString()
    }

}