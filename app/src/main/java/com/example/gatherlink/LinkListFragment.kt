package com.example.gatherlink

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_link_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get MainActivity instance
        mMainActivity = activity as MainActivity

        // scrape & parse HTML
        scrapeHtml()
    }

    /**
     * scrape & parse HTML.
     */
    private fun scrapeHtml() {
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
            Log.d("hoge", headline.absUrl("href"))
        }
    }
}