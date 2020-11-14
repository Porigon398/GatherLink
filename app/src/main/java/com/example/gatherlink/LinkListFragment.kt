package com.example.gatherlink

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gatherlink.log.GatherLinkLog
import kotlinx.android.synthetic.main.fragment_link_list.*
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
 * LinkList Fragment.
 */
class LinkListFragment : Fragment() {
    /** MainActivity instance. */
    private lateinit var mMainActivity: MainActivity

    /** class name for log output */
    private val TAG = LinkListFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        GatherLinkLog.enter(TAG, "onCreateView")

        GatherLinkLog.exit(TAG, "onCreateView")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_link_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GatherLinkLog.enter(TAG, "onViewCreated")

        // get MainActivity instance
        mMainActivity = activity as MainActivity

        // scrape & parse HTML
        scrapeHtml()

        GatherLinkLog.exit(TAG, "onViewCreated")
    }

    /**
     * scrape & parse HTML.
     */
    private fun scrapeHtml() {
        GatherLinkLog.enter(TAG, "scrapeHtml")

        // DOM(?)
        lateinit var doc: Document

        // get current page's URL
        mMainActivity.mWebViewUrl = mMainActivity.mWebView.url

        // use a coroutine for HTTP communication
        launch {
            // fetch and parse HTML file
            doc = Jsoup.connect(mMainActivity.mWebViewUrl).get()
        }
        // wait 5000ms
        Thread.sleep(5000)

        // get "a-tag" from DOM
        val aTag = doc.select("a")
        // I DON'T UNDERSTAND but can get some URL
        for(headline in aTag) {
            textView.append(headline.absUrl("href") + "\n")
            GatherLinkLog.debug(TAG, "[link] " + headline.absUrl("href"))
        }

        GatherLinkLog.exit(TAG, "scrapeHtml")
    }
}