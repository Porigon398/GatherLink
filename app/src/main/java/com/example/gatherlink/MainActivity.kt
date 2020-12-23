package com.example.gatherlink

import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.gatherlink.log.GatherLinkLog
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Main Activity.
 */
class MainActivity : AppCompatActivity() {
    /** WebView. */
    lateinit var mWebView: GatherLinkWebView
    /** LinkListFragment instance. */
    private lateinit var mFragment: Fragment
    /** Fragment Transaction. */
    private lateinit var mTransaction: FragmentTransaction

    /** URL of the page show in WebView. */
    var mWebViewUrl = "https://ja.wikipedia.org/wiki/%E3%83%A1%E3%82%A4%E3%83%B3%E3%83%9A%E3%83%BC%E3%82%B8"
    /** if LinkList is displayed. */
    var mIsDisplayingLinkList = false

    /** class name for log output. */
    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GatherLinkLog.enter(TAG, "onCreate")

        setContentView(R.layout.activity_main)

        // set WebView
        mWebView = findViewById(R.id.webView)
        // set a tool bar
        setSupportActionBar(findViewById(R.id.toolBar))

        // set URL & show WebView
        showWebView()

        GatherLinkLog.exit(TAG, "onCreate")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        GatherLinkLog.enter(TAG, "onCreateOptionsMenu")

        // load res/menu/resources.xml
        menuInflater.inflate(R.menu.resources, menu)

        GatherLinkLog.exit(TAG, "onCreateOptionsMenu")

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        GatherLinkLog.enter(TAG, "onOptionsItemSelected")

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
        GatherLinkLog.exit(TAG, "onOptionsItemSelected")

        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        GatherLinkLog.enter(TAG, "onKeyDown")

        // when a device's back key pressed
        if(keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            // when LinkList is displayed
            if(mIsDisplayingLinkList) {
                // let LinkListFragment slide out
                startSlideOut()
            }
            // go back to the previous page (not to the home screen)
            mWebView.goBack()

            GatherLinkLog.exit(TAG, "onKeyDown")

            return true
        }
        GatherLinkLog.exit(TAG, "onKeyDown")

        return super.onKeyDown(keyCode, event)
    }

    /**
     * set URL & show WebView.
     */
    private fun showWebView() {
        GatherLinkLog.enter(TAG, "showWebView")

        // OMAJINAI
        mWebView.webViewClient = WebViewClient()
        // set URL want to open in WebView
        mWebView.loadUrl(mWebViewUrl)

        GatherLinkLog.exit(TAG, "showWebView")
    }

    /**
     * start Fragment slide in.
     */
    private fun startSlideIn() {
        GatherLinkLog.enter(TAG, "startSlideIn")

        // use FragmentTransaction
        mTransaction = supportFragmentManager.beginTransaction()

        // set animations for Fragment's transition
        mTransaction.setCustomAnimations(
            android.R.anim.slide_in_left, android.R.anim.slide_out_right,
            android.R.anim.slide_in_left, android.R.anim.slide_out_right
        )
        // add Fragment
        mTransaction.add(R.id.fragmentZone, mFragment)
        // adjust Fragment transaction
        mTransaction.commit()

        // set a flag...
        mIsDisplayingLinkList = true

        GatherLinkLog.exit(TAG, "startSlideIn")
    }

    /**
     * start Fragment slide out.
     */
    private fun startSlideOut() {
        GatherLinkLog.enter(TAG, "startSlideOut")

        // use FragmentTransaction
        mTransaction = supportFragmentManager.beginTransaction()

        // set animations for Fragment's transition
        mTransaction.setCustomAnimations(
            android.R.anim.slide_in_left, android.R.anim.slide_out_right,
            android.R.anim.slide_in_left, android.R.anim.slide_out_right
        )
        // remove Fragment
        mTransaction.remove(mFragment)
        // adjust Fragment transaction
        mTransaction.commit()

        // set a flag...
        mIsDisplayingLinkList = false

        GatherLinkLog.exit(TAG, "startSlideOut")
    }
}