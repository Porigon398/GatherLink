package com.example.gatherlink

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
　* Main Activity.
 */
class MainActivity : AppCompatActivity() {
    /** URL of the page show in WebView */
    private var mWebViewUrl = "https://google.com"
    /** LinkList Fragment instance */
    private lateinit var fragment: Fragment
    /** Fragment Transaction */
    private lateinit var transaction: FragmentTransaction
    /** if LinkList is displayed */
    private var mIsDisplayingLinkList = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set a tool bar
        setSupportActionBar(findViewById(R.id.toolBar))

        // set URL & show WebView
        showWebView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // load res/menu/resources.xml
        menuInflater.inflate(R.menu.resources, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // id of items on a tool bar
        val id = item.itemId

        // when Link Button selected
        if(id == R.id.linkButton) {
            // when LinkList isn't displayed
            if(!mIsDisplayingLinkList) {
                // make Fragment
                fragment = LinkListFragment()

                // let LinkListFragment slide in
                startSlideIn()

                // scrape & parse HTML
                scrapeHtml()
            } else {
                // let LinkListFragment slide out
                startSlideOut()
            }
        }
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // when a device's back key pressed
        if(keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            // when LinkList is displayed
            if(mIsDisplayingLinkList) {
                // let LinkListFragment slide out
                startSlideOut()
            }
            // go back to the previous page (not to the home screen)
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
        webView.loadUrl(mWebViewUrl)
    }

    /**
     * scrape & parse HTML.
     */
    private fun scrapeHtml() {
        // DOM(?)
        lateinit var doc: Document

        // get current page's URL
        mWebViewUrl = webView.url

        // use a coroutine for HTTP communication
        launch {
            // fetch and parse HTML file
            doc = Jsoup.connect(mWebViewUrl).get()
        }
        // wait 2000ms
        Thread.sleep(5000)

        // get "a-tag" from DOM
        val newsHeadlines = doc.select("a")
        // I DON'T UNDERSTAND but can get some URL
        for(headline in newsHeadlines) {
            Log.d("hoge", headline.absUrl("href"))
        }
    }

    /**
     * start Fragment slide in.
     * */
    private fun startSlideIn() {
        // use FragmentTransaction
        transaction = supportFragmentManager.beginTransaction()

        // set animations for Fragment's transition
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
            android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        // add Fragment
        transaction.add(R.id.webView, fragment)
        // adjust Fragment transaction
        transaction.commit()

        // set a flag...
        mIsDisplayingLinkList = true
    }

    /**
     * start Fragment slide out.
     * */
    private fun startSlideOut() {
        // use FragmentTransaction
        transaction = supportFragmentManager.beginTransaction()

        // set animations for Fragment's transition
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
            android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        // remove Fragment
        transaction.remove(fragment)
        // adjust Fragment transaction
        transaction.commit()

        // set a flag...
        mIsDisplayingLinkList = false
    }
}