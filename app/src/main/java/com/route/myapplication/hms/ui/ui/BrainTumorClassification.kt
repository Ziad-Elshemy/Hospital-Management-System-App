package com.route.myapplication.hms.ui.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.route.myapplication.hms.R

class BrainTumorClassification : AppCompatActivity() {

    lateinit var web_view : WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brain_tumor_classification)

        web_view=findViewById(R.id.webView)

        web_view.settings.javaScriptEnabled = true

        web_view.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }
        }

        web_view.loadUrl("https://www.netflix.com/eg-en/")


    }

    override fun onBackPressed() {
        if (web_view.canGoBack())
        {
            web_view.goBack()
        }else{
            super.onBackPressed()
        }
    }
}