package com.example.acadlyassignment.ui.workmanager

import android.R
import android.app.AlertDialog
import android.content.Context
import android.os.CountDownTimer
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.acadlyassignment.BuildConfig
import com.example.acadlyassignment.MyApplication
import com.example.acadlyassignment.data.model.ApiRequest
import com.example.acadlyassignment.data.model.ApiResponse
import com.example.acadlyassignment.data.repository.RetrofitInstance
import com.example.acadlyassignment.data.repository.RetrofitService
import retrofit2.Call
import retrofit2.Response


class MyWorker(context: Context, workParams : WorkerParameters) : Worker(context,workParams){
    override fun doWork(): Result {
     return callApi()
    }

    private fun callApi(): Result {
        val call: Call<ApiResponse> =  RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
            .hitApi(ApiRequest(BuildConfig.EMAIL))
        val response: Response<ApiResponse> = call.execute()
        if (response.code() == 200) {
            val data: ApiResponse? = response.body()
            if (data?.message.equals("success")){
                if (MyApplication().isActivityVisible()){
                    //show dialog
                    createDialog("Completed",response.body()?.message)
                }else{
                    //show notification
                    createNotification()
                }
            }else if (data?.message.equals("retry")){
                object : CountDownTimer(30000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        //do nothing
                    }

                    override fun onFinish() {
                        //hit api
                        callApi()
                    }
                }.start()
            }
        } else {
            createDialog("Error",response.body()?.message)
            return Result.retry()
        }
        return Result.success()
    }

    fun createDialog(title: String,message: String?){
        AlertDialog.Builder(applicationContext)
            .setTitle(title)
            .setMessage(message ?: "")
            .setNeutralButton("OK", null)
            .setIcon(R.drawable.ic_dialog_alert)
            .show()
    }

    fun createNotification(){
        //create local notification
    }
}