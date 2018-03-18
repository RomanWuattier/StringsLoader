package com.romanwuattier.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.romanwuattier.stringsloader.StringsLoader
import com.romanwuattier.stringsloader.StringsLoaderCallback
import com.romanwuattier.stringsloader.converter.ConverterType

class SplashScreenActivity : AppCompatActivity() {

    private val URL = "https://api.myjson.com/bins/nqkqb"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        StringsLoader.provideTranslationLoader().load(URL, ConverterType.JSON, object : StringsLoaderCallback {
            override fun onComplete() {
                navigateToNextPage()
                finish()
            }

            override fun onError() {
                // Do something
            }
        })
    }

    private fun navigateToNextPage() {
        MainActivity.start(this)
    }
}