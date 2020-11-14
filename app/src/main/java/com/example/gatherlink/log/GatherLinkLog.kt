package com.example.gatherlink.log

import android.util.Log

/**
 * Original log class.
 */
class GatherLinkLog {
    companion object {
        /** enter sign. */
        private const val ENTER = "<"
        /** exit sign. */
        private const val EXIT = ">"

        /**
         * output enter log.
         * @param tag tag
         * @param msg message
         */
        fun enter(tag: String, msg: String) {
            debug(tag, "$msg $ENTER")
        }

        /**
         * output exit log.
         * @param tag tag
         * @param msg message
         */
        fun exit(tag: String, msg: String) {
            debug(tag, "$msg $EXIT")
        }

        /**
         * output debug log.
         * @param tag tag
         * @param msg message
         */
        fun debug(tag: String, msg: String) {
            Log.d(tag, msg)
        }

        /**
         * output error log.
         * @param tag tag
         * @param msg message
         */
        fun error(tag: String, msg: String) {
            Log.e(tag, msg)
        }

        /**
         * output information log.
         * @param tag tag
         * @param msg message
         */
        fun information(tag: String, msg: String) {
            Log.i(tag, msg)
        }

        /**
         * output warning log.
         * @param tag tag
         * @param msg message
         */
        fun warning(tag: String, msg: String) {
            Log.w(tag, msg)
        }
    }
}