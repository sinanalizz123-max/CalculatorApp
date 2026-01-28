package com.netmonitor.app

import android.app.Application
import android.util.Log
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter

class NetMonitorApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Thread.setDefaultUncaughtExceptionHandler { _, e ->
            try {
                val file = File(getExternalFilesDir(null), "crash.log")
                PrintWriter(FileWriter(file, true)).use {
                    it.println("---- CRASH ----")
                    e.printStackTrace(it)
                }
            } catch (t: Throwable) {
                Log.e("CrashLogger", "Failed to write crash", t)
            }
        }
    }
}
