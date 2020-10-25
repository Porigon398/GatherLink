package com.example.gatherlink

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
　* Main Activity.
 */
class MainActivity : AppCompatActivity() {
    /** URL of page show in WebView at first */
    val WEB_VIEW_URL = "http://www.google.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set tool bar
        setSupportActionBar(findViewById(R.id.toolBar))

        // set URL & show WebView
        showWebView()
        // scrape & parse HTML
        scrapeHtml()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // load res/menu/resources.xml
        menuInflater.inflate(R.menu.resources, menu)
        return true
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

    /**
    * set URL & show WebView.
    */
    private fun showWebView() {
        // OMAJINAI
        webView.webViewClient = WebViewClient()
        // set URL want to open in WebView
        webView.loadUrl(WEB_VIEW_URL)
    }

    /**
     * scrape & parse HTML.
     */
    private fun scrapeHtml() {
        /** DOM(?) */
        lateinit var doc: Document
        /** HTML page's title */
        var title = ""

        // use coroutine for HTTP communication
        launch {
            // fetch and parse HTML file
            doc = Jsoup.connect(WEB_VIEW_URL).get()
        }
        // wait 2000ms
        Thread.sleep(5000)

        // get HTML page's title from DOM
        title = doc.title()
        // output HTML page's title
        Log.d("hoge", title)
    }
}