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
        GatherLinkLog.debug(TAG, getString(R.string.log_scarping_text) + mMainActivity.mWebViewUrl)

        // use a coroutine for HTTP communication
        launch {
            // fetch and parse HTML file
            doc = Jsoup.connect(mMainActivity.mWebViewUrl).get()
        }
        // wait 5000ms
        Thread.sleep(10000)

        // title strings from current page's URL
        val titleFromUrl = mMainActivity.mWebViewUrl.removePrefix(getString(R.string.wikipedia_common_link))
        // get "a-tag" from DOM
        val aTag = doc.select("a")
        // if first "edit" link appeared
        var isMainSectionStarted = false
        // if first "how to use references" link appeared
        var isMainSectionEnded = false

        // I DON'T UNDERSTAND but can get some URL
        for(headline in aTag) {
            // when the strings included in URL
            if(headline.absUrl("href").indexOf(getString(R.string.first_edit_link)) != -1) {
                // set a flag meaning first "edit" link appeared
                isMainSectionStarted = true
            }
            // when the strings included in URL
            else if(headline.absUrl("href").indexOf(getString(R.string.how_to_use_references_link)) != -1) {
                // set a flag meaning first "how to use references" link appeared
                isMainSectionEnded = true
            }

            // when satisfy following requirements
            // - not include title strings
            // - not include edit URL
            // - not include "[xx] (link for references)"
            // - not include "^ (link for a word has references)"
            // - first "edit" link already appeared
            // - first "how to use references" link doesn't appeared
            if(headline.absUrl("href").indexOf(titleFromUrl) == -1
                && headline.absUrl("href").indexOf(getString(R.string.edit_link)) == -1
                && headline.absUrl("href").indexOf(getString(R.string.to_reference_link)) == -1
                && headline.absUrl("href").indexOf(getString(R.string.to_referenced_link)) == -1
                && isMainSectionStarted && !isMainSectionEnded) {
                    // add a title of URL to the text view
                    textView.append(headline.text() + "\n")
                    GatherLinkLog.debug(TAG, getString(R.string.log_displayed_text) + headline.text())
            } else {
                GatherLinkLog.debug(TAG, getString(R.string.log_not_displayed_text) + headline.text())
            }
            GatherLinkLog.debug(TAG, getString(R.string.log_link) + headline.absUrl("href"))
        }

        GatherLinkLog.exit(TAG, "scrapeHtml")
    }
}