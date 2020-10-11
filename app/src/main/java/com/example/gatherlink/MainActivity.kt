package com.example.gatherlink

import android.os.Bundle
import android.view.KeyEvent
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
        webView.webViewClient = WebViewClient()
        // set URL want to open in WebView
        webView.loadUrl("http://www.google.com")
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // when device's back key pressed
        if(keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            // go back to previous page (not to home screen)
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}