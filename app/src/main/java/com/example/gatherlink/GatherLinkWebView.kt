package com.example.gatherlink

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import com.example.gatherlink.log.GatherLinkLog

/**
 * Custom WebView class.
 */
class GatherLinkWebView : WebView {
    /** MainActivity instance. */
    private lateinit var mMainActivity: MainActivity

    /** class name for log output. */
    private val TAG = GatherLinkWebView::class.java.simpleName

    // ?????
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttrs: Int) : super(
        context,
        attrs,
        defStyleAttrs
    )

    override fun onViewAdded(child: View?) {
        super.onViewAdded(child)
        GatherLinkLog.enter(TAG, "onViewAdded")

        // get MainActivity instance
        mMainActivity = context as MainActivity

        GatherLinkLog.exit(TAG, "onViewAdded")
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        GatherLinkLog.enter(TAG, "onTouchEvent")

        performClick()

        // when LinkList isn't displayed
        if(!mMainActivity.mIsDisplayingLinkList) {
            GatherLinkLog.exit(TAG, "onTouchEvent")

            // adapt default action for any touch event
            return super.onTouchEvent(event)
        }
        GatherLinkLog.exit(TAG, "onTouchEvent")

        // when LinkList is displayed
        // do nothing for any touch event
        return true
    }

    override fun performClick(): Boolean {
        GatherLinkLog.enter(TAG, "performClick")

        GatherLinkLog.exit(TAG, "performClick")

        return super.performClick()
    }
}