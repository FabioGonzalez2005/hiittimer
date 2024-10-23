package org.iesharia.hiittimer

import android.content.Context
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.util.Log

class CounterDown(var context: Context, var segundos: Int, var loquehacealhacertick: (Long) -> Unit) {
    private var counterState: Boolean = false
    private var tiempoRestante: Long = segundos * 1000L
    private var myCounter: CountDownTimer? = null

    fun crearCounter(tiempo: Long) {
        myCounter = object : CountDownTimer(tiempo, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                tiempoRestante = millisUntilFinished
                if (counterState) loquehacealhacertick(millisUntilFinished / 1000)
                if (tiempoRestante == 3000L || tiempoRestante == 2000L || tiempoRestante == 1000L) {
                    reproducirPitidoCorto()
                }
            }

            override fun onFinish() {
                counterState = false
                Log.i("dam2", "Temporizador finalizado")
            }
        }
    }

    fun start() {
        counterState = true
        crearCounter(tiempoRestante)
        myCounter?.start()
    }

    fun cancel() {
        counterState = false
        myCounter?.cancel()
    }

    private fun reproducirPitidoCorto() {
        val mediaPlayer = MediaPlayer.create(context, R.raw.pitidocorto)
        mediaPlayer.setOnCompletionListener {
            it.release()
        }
        mediaPlayer.start()
    }
}
