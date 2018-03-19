package com.romanwuattier.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.romanwuattier.stringsloader.StringsLoader

class MainActivity : AppCompatActivity() {

    companion object Launcher {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val tvKey1 by lazy { findViewById<TextView>(R.id.key1) }
    private val tvKey2 by lazy { findViewById<TextView>(R.id.key2) }
    private val tvKey3 by lazy { findViewById<TextView>(R.id.key3) }
    private val getKeys by lazy { findViewById<Button>(R.id.getKeys) }
    private val clearKeys by lazy { findViewById<Button>(R.id.clearKeys) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getKeys.setOnClickListener({ setKeys() })
        clearKeys.setOnClickListener({ clearKeys() })
    }

    private fun setKeys() {
        val string2 = StringsLoader.provideTranslationLoader().get("key.2")
        tvKey2.text = string2

        val string3 = StringsLoader.provideTranslationLoader().get("key.3")
        tvKey3.text = string3

        val string1 = StringsLoader.provideTranslationLoader().get("key.1")
        tvKey1.text = string1
    }

    private fun clearKeys() {
        tvKey1.text = ""
        tvKey2.text = ""
        tvKey3.text = ""
    }
}
