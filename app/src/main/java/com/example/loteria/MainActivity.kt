package com.example.loteria

import android.R
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.loteria.databinding.ActivityMainBinding

@SuppressLint("SetTextI18n")
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val loteria = Loteria(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.iniciar.setOnClickListener{
            iniciar()
        }

        binding.suspender.setOnClickListener{
            loteria.pausa = !loteria.pausa

            AlertDialog.Builder(this)
                .setTitle("¡Loteria!")
                .setMessage("Fin de la partida.") // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Terminar partida") { _, _ ->
                    iniciar()
                }
                .setNegativeButton("Ver las cartas restantes") { _, _ ->
                    loteria.pausa = false
                }
                .setIcon(R.drawable.ic_dialog_alert)
                .show()
        }
    }

    fun iniciar(){
        binding.iniciar.text = "REINICIAR"
        binding.suspender.visibility = View.VISIBLE

        if(!loteria.ready){
            this.loteria.barajear()
            try {
                loteria.start()
            }catch (e:Exception){
                println("Explotó")
            }
        }else{
            loteria.ready = false
        }
    }
}