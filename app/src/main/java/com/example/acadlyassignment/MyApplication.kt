package com.example.acadlyassignment

import android.app.Application

class MyApplication :Application() {

    fun isActivityVisible(): Boolean {
        return activityVisible
    }

    fun activityResumed() {
        activityVisible = true
    }

    fun activityPaused() {
        activityVisible = false
    }

    private var activityVisible = false
}