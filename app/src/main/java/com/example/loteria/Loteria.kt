package com.example.loteria

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.media.MediaPlayer
import android.view.View
import kotlinx.coroutines.*
import java.io.IOException
import kotlin.coroutines.EmptyCoroutineContext

@SuppressLint("SetTextI18n")
class Loteria(este: MainActivity) :Thread() {

    var esteHilo = este

    private var baraja = Baraja
    private var contador = 0
    var pausa = true
    var ready = false

    @SuppressLint("SetTextI18n")
    override fun run() {
        super.run()
        while (true) {
            if (!ready) {
                contador = 0

                esteHilo.runOnUiThread {
                    esteHilo.binding.numCartas.text = (contador + 1).toString() + "/54"
                }

                cuentaRegresiva()
                pausa = false
            } else {
                if (!pausa) {
                    cambiarImagen()
                }
            }
            sleep(2500L)
        }
    }

    private fun cambiarImagen(){
        if (contador < 54) {
            esteHilo.runOnUiThread {
                esteHilo.binding.image.setImageResource(baraja[contador].imagen)
                reproducir(contador)
                esteHilo.binding.numCartas.text = (contador + 1).toString() + "/54"
            }
            contador++
        }
    }

    fun cuentaRegresiva() = GlobalScope.launch {
        var contador = 0
        esteHilo.runOnUiThread {
            esteHilo.binding.image.visibility = View.INVISIBLE
            esteHilo.binding.txtcontador.visibility = View.VISIBLE
        }
        (contador..2).forEach {
            esteHilo.runOnUiThread {
                esteHilo.binding.txtcontador.text = (3-contador+1).toString()
            }
            contador++
            delay(900)
        }
        if (contador == 3) {
            esteHilo.runOnUiThread {
                esteHilo.binding.image.visibility = View.VISIBLE
                esteHilo.binding.txtcontador.visibility = View.INVISIBLE
            }
            ready = true
        }
    }

    fun reproducir(pos:Int) = GlobalScope.launch {
        val contador = pos
        try {
            val mp = MediaPlayer.create(esteHilo, baraja[contador].audio)
            mp.start()
            delay(3200L)
            mp.reset()
        } catch (e: IOException) {
            AlertDialog.Builder(esteHilo)
                .setTitle("FALLO EN EL AUDIO")
                .setMessage(e.message)
                .setNeutralButton("ACEPTAR") { d, _ -> d.dismiss() }
                .show()
        }
    }

    fun barajear() {
        this.baraja.shuffle()
    }
}