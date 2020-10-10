package com.example.gatherlink

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
　* Main Activity.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // OMAJINAI
        webview.webViewClient = WebViewClient()
        // set URL want to open in WebView
        webview.loadUrl("http://www.google.com")
    }
}