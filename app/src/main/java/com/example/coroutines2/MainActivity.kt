package com.example.coroutines2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private  val RESULT_1 = "Result #1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {

            // IO, Main, Default
            CoroutineScope(IO).launch {
                fakeApiRequest()
            }
        }
    }

    private suspend fun fakeApiRequest(){
        val result1 = getResult1FromApi()
        println("debug: $result1")
    }

    private suspend fun getResult1FromApi(): String{
        logThread("getResult1FromApi")
        delay(1000)
        return RESULT_1
    }

    private fun logThread(methodName: String) {
        println("debug: ${methodName}: ${Thread.currentThread().name}")
    }
}
