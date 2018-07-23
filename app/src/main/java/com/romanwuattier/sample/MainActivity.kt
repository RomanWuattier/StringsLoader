package com.romanwuattier.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.romanwuattier.loader.StringsLoader
import com.romanwuattier.loader.LoaderCallback
import com.romanwuattier.loader.ConverterType

class MainActivity : AppCompatActivity() {

    companion object Launcher {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val tvKey1 by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.key1) }
    private val tvKey2 by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.key2) }
    private val tvKey3 by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.key3) }
    private val tvComposedKey1 by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.composed_key1) }
    private val tvComposedKey2 by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.composed_key2) }
    private val getKeys by lazy(LazyThreadSafetyMode.NONE) { findViewById<Button>(R.id.getKeys) }
    private val clearKeys by lazy(LazyThreadSafetyMode.NONE) { findViewById<Button>(R.id.clearKeys) }
    private val loadLocal by lazy(LazyThreadSafetyMode.NONE) { findViewById<Button>(R.id.loadLocal) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getKeys.setOnClickListener { setKeys() }
        clearKeys.setOnClickListener { clearKeys() }
        loadLocal.setOnClickListener { loadFromLocal() }
    }

    private fun setKeys() {
        val string2 = StringsLoader.get("key.2")
        tvKey2.text = string2

        val string3 = StringsLoader.get("key.3")
        tvKey3.text = string3

        val string1 = StringsLoader.get("key.1")
        tvKey1.text = string1

        val composedString1 = StringsLoader.get("composed.key.1", 100)
        tvComposedKey1.text = composedString1

        val composedString2 = StringsLoader.get("composed.key.2", 2, "local composed string")
        tvComposedKey2.text = composedString2
    }

    private fun clearKeys() {
        tvKey1.text = ""
        tvKey2.text = ""
        tvKey3.text = ""
    }

    private fun loadFromLocal() {
        StringsLoader.loadFromLocal("strings.json", this, ConverterType.JSON, object : LoaderCallback {
            override fun onComplete() {
                setKeys()
            }

            override fun onError(throwable: Throwable) {
                // An exception occurred
            }
        })
    }
}

