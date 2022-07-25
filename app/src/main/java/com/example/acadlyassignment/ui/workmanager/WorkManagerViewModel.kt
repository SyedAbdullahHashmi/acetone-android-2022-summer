package com.example.acadlyassignment.ui.workmanager

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.work.*
import java.util.concurrent.TimeUnit


class WorkManagerViewModel(application: Application) : ViewModel() {
    private val workManager = WorkManager.getInstance(application)
    internal val outputWorkInfos: LiveData<List<WorkInfo>> =
        workManager.getWorkInfosByTagLiveData("OUTPUT")


    fun createWorkRequest() {
        val constaints: Constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicWork = PeriodicWorkRequestBuilder<MyWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constaints).build()
        workManager.enqueueUniquePeriodicWork("unique_worker",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWork)
    }
}

class WorkManagerViewModelFactory(private val application: Application) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return if (modelClass.isAssignableFrom(WorkManagerViewModel::class.java)) {
            WorkManagerViewModel(application) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}