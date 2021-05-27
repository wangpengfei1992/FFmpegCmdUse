package com.wpf.ffmpegusedemo1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.wpf.ffmpegcmd.FFmpegCmd.OnCmdExecListener
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var mContext: MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = this
        var changeText = findViewById<TextView>(R.id.start)
        changeText.setOnClickListener {
            if (initPermission()){
                var soundPath = "/storage/emulated/0/opusaudiodemo/recorder_file/"+ "recorder.pcm"
                Log.e("wpf", "pcm路径:$soundPath")
                if (File(soundPath).exists()){
                    var savePath = "/storage/emulated/0/wpf/FFmpegUseDemo1/"+ "recorder.wav"
                    var saveFile = File(savePath)
                    if (!saveFile.parentFile.exists())saveFile.parentFile.mkdirs()
                    var audioEditor = AudioEditor()
                    audioEditor.addTextWatermark(soundPath,savePath,
                            50000,  object : OnCmdExecListener {
                        override fun onSuccess() {
                            Log.e("wpf", "onSuccess")
                        }

                        override fun onFailure() {
                            Log.e("wpf", "onFailure")
                        }

                        override fun onProgress(progress: Float) {
                            Log.e("wpf", "$progress")
                        }

                    })
                }else{
                    Log.e("wpf", "pcm不存在")
                }

            }
        }

    }
    private val CALLS_STATE = arrayOf( Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE)
    private fun initPermission(): Boolean {
        var permission:Int = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 先判断有没有权限
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = Uri.parse("package:" + mContext.getPackageName())
                startActivityForResult(intent, 1)
                return false
            }else{
                return true
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 先判断有没有权限
            if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ),
                        1
                )
                return false
            }else{
                return true
            }
        }else {
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mContext, CALLS_STATE, 1);
                return false
            }else{
                return true
            }
        }
    }
}