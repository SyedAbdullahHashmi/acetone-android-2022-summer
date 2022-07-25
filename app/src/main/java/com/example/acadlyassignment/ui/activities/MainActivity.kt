package com.example.acadlyassignment.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.WorkInfo
import com.example.acadlyassignment.MyApplication
import com.example.acadlyassignment.R
import com.example.acadlyassignment.ui.workmanager.WorkManagerViewModel
import com.example.acadlyassignment.ui.workmanager.WorkManagerViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val workManagerViewModel: WorkManagerViewModel by viewModels {
        WorkManagerViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonCreate.setOnClickListener {
            workManagerViewModel.createWorkRequest()
            startActivity(Intent(this, ChildActivity::class.java))
        }

        workManagerViewModel.outputWorkInfos.observe(this, workInfosObserver())

    }

    private fun workInfosObserver(): Observer<List<WorkInfo>> {
        return Observer { listOfWorkInfo ->
            if (listOfWorkInfo.isNullOrEmpty()) {
                return@Observer
            }
        }
    }

    override fun onResume() {
        super.onResume()
        MyApplication().activityResumed()
    }

    override fun onPause() {
        super.onPause()
        MyApplication().activityPaused()
    }

}