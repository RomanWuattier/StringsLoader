package com.romanwuattier.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.romanwuattier.loader.LoaderCallback
import com.romanwuattier.loader.converter.ConverterType
import com.romanwuattier.stringsloader.StringsLoader

class SplashScreenActivity : AppCompatActivity() {

    private val URL = "https://api.myjson.com/bins/nqkqb"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        StringsLoader.provideInstance().load(URL, ConverterType.JSON, object : LoaderCallback {
            override fun onComplete() {
                navigateToNextPage()
                finish()
            }

            override fun onError(throwable: Throwable) {
                // An exception occurred
            }
        })
    }

    private fun navigateToNextPage() {
        MainActivity.start(this)
    }
}