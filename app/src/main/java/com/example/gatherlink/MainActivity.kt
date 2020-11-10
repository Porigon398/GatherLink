package com.example.gatherlink

import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*

/**
　* Main Activity.
 */
class MainActivity : AppCompatActivity() {
    /** WebView. */
    lateinit var mWebView: WebView
    /** LinkListFragment instance. */
    private lateinit var mFragment: Fragment
    /** Fragment Transaction. */
    private lateinit var mTransaction: FragmentTransaction

    /** URL of the page show in WebView. */
    var mWebViewUrl = "https://google.com"
    /** if LinkList is displayed. */
    private var mIsDisplayingLinkList = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set a tool bar
        setSupportActionBar(findViewById(R.id.toolBar))
        // WebView
        mWebView = findViewById(R.id.webView)

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
                mFragment = LinkListFragment()

                // let LinkListFragment slide in
                startSlideIn()
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
            mWebView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * set URL & show WebView.
     */
    private fun showWebView() {
        // OMAJINAI
        mWebView.webViewClient = WebViewClient()
        // set URL want to open in WebView
        mWebView.loadUrl(mWebViewUrl)
    }

    /**
     * start Fragment slide in.
     * */
    private fun startSlideIn() {
        // use FragmentTransaction
        mTransaction = supportFragmentManager.beginTransaction()

        // set animations for Fragment's transition
        mTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
            android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        // add Fragment
        mTransaction.add(R.id.webView, mFragment)
        // adjust Fragment transaction
        mTransaction.commit()

        // set a flag...
        mIsDisplayingLinkList = true
    }

    /**
     * start Fragment slide out.
     * */
    private fun startSlideOut() {
        // use FragmentTransaction
        mTransaction = supportFragmentManager.beginTransaction()

        // set animations for Fragment's transition
        mTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
            android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        // remove Fragment
        mTransaction.remove(mFragment)
        // adjust Fragment transaction
        mTransaction.commit()

        // set a flag...
        mIsDisplayingLinkList = false
    }
}