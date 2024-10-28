package org.iesharia.hiittimer

import android.content.Context
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.util.Log

class CounterDown(var context: Context, var segundos: Int, var loquehacealhacertick: (Long) -> Unit) {
    private var counterState: Boolean = false
    private var tiempoRestante: Long = segundos * 1000L
    private var myCounter: CountDownTimer? = null
    private var mediaPlayer: MediaPlayer? = null

    // Inicializador de la clase
    init {
        // Creamos un MediaPlayer para reproducir un pitido corto al iniciar el temporizador
        mediaPlayer = MediaPlayer.create(context, R.raw.pitidocorto)
        mediaPlayer?.setOnCompletionListener {
            it.release()
            mediaPlayer = MediaPlayer.create(context, R.raw.pitidocorto)
        }
    }

    // Método para configurar un nuevo temporizador
    fun crearCounter(tiempo: Long) {
        myCounter = object : CountDownTimer(tiempo, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tiempoRestante = millisUntilFinished
                if (counterState) loquehacealhacertick(millisUntilFinished / 1000)

                if (tiempoRestante in 1500L..4000L) {
                    mediaPlayer?.start()
                }
            }

            // Método que se ejecuta cuando el temporizador llega a cero
            override fun onFinish() {
                counterState = false
                Log.i("dam2", "Temporizador finalizado")
            }
        }
    }

    // Método para iniciar el temporizador
    fun start() {
        counterState = true
        crearCounter(tiempoRestante)
        myCounter?.start()
    }

    // Método para cancelar el temporizador
    fun cancel() {
        counterState = false
        myCounter?.cancel()
        mediaPlayer?.stop()
        mediaPlayer?.release()
    }

}

