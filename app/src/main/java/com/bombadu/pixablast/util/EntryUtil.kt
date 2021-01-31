package com.bombadu.pixablast.util

object EntryUtil {

    fun validateEntry(
        caption: String,
        name: String
    ) : Boolean {

        if (caption.isEmpty()) {
            return false
        }

        if (name.isEmpty()) {
            return false
        }

        return true
    }
}