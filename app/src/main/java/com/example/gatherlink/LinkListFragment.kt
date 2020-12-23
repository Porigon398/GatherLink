package com.example.gatherlink

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

    /** class name for log output. */
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
        GatherLinkLog.debug(TAG, "scrapeHtml: scraping " + mMainActivity.mWebViewUrl)

        // use a coroutine for HTTP communication
        launch {
            // fetch and parse HTML file
            doc = Jsoup.connect(mMainActivity.mWebViewUrl).get()
        }
        // wait 5000ms
        Thread.sleep(10000)

        // title strings from current page's URL
        val titleFromUrl = mMainActivity.mWebViewUrl.removePrefix("https://ja.m.wikipedia.org/wiki/")
        // get "a-tag" from DOM
        val aTag = doc.select("a")
        // if first "edit" link appeared
        var isMainSectionStarted = false
        // if first "how to use references" link appeared
        var isMainSectionEnded = false

        // I DON'T UNDERSTAND but can get some text
        for(headline in aTag) {
            if(headline.absUrl("href").indexOf("action=edit&section=0") != -1) {
                isMainSectionStarted = true
            } else if(headline.absUrl("href").indexOf("Help:%E8%84%9A%E6%B3%A8/%E8%AA%AD%E8%80%85%E5%90%91%E3%81%91") != -1) {
                isMainSectionEnded = true
            }

            if(headline.absUrl("href").indexOf(titleFromUrl) == -1
                && headline.absUrl("href").indexOf("action=edit") == -1
                && headline.absUrl("href").indexOf("cite_note") == -1
                && headline.absUrl("href").indexOf("cite_ref") == -1
                && isMainSectionStarted && !isMainSectionEnded) {
                    textView.append(headline.text() + "\n")
                    GatherLinkLog.debug(TAG, "[text: displayed] " + headline.text())
            } else {
                GatherLinkLog.debug(TAG, "[text: NOT displayed] " + headline.text())
            }
            GatherLinkLog.debug(TAG, "[link] " + headline.absUrl("href"))
        }

        GatherLinkLog.exit(TAG, "scrapeHtml")
    }
}