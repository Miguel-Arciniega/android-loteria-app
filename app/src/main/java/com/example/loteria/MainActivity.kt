package com.example.loteria

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loteria.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val loteria = Loteria(this)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.iniciar.setOnClickListener{
            if(!loteria.ready){
                this.loteria.barajear()
                try {
                    loteria.start()
                }catch (e:Exception){
                    System.out.println("Explotó")
                }
            }else{
                loteria.ready = false
            }
        }

        binding.suspender.setOnClickListener{
            loteria.pausa = !loteria.pausa
        }
    }
}