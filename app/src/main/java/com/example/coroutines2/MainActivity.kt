package com.example.coroutines2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    val JOB_TIMEOUT = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            setNewText("Click")
            CoroutineScope(IO).launch {
                fakeApiRequest()
            }
        }
    }

    private suspend fun fakeApiRequest(){
        withContext(IO){
            val job = withTimeoutOrNull(JOB_TIMEOUT){
                val result1 = getResult1FromApi()
                setTextOnMainThread("Got $result1")

                val result2 = getResult2FromApi()
                setTextOnMainThread("Got $result2")
            }

            if (job == null) {
                val cancelMessage = "Cancelling job... Job took longer than $JOB_TIMEOUT ms"
                println("debug: $cancelMessage")
                setTextOnMainThread(cancelMessage)
            }

            //val job2 = launch {  }
            //job2.join()
            //job2.cancel()
        }
    }

    private fun setNewText(input: String){
        val newText = text.text.toString() + "\n$input"
        text.text = newText
    }

    private suspend fun setTextOnMainThread(input: String){
        withContext(Main){
            setNewText(input)
        }
    }

    private suspend fun getResult1FromApi(): String{
        delay(1000)
        return "Result1"
    }

    private suspend fun getResult2FromApi(): String{
        delay(1000)
        return "Result2"
    }
}
