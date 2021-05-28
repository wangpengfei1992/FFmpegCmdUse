package com.wpf.ffmpegusedemo1

import android.util.Log
import com.wpf.ffmpegcmd.CmdList
import com.wpf.ffmpegcmd.FFmpegCmd

class AudioEditor {
    companion object{
        const val TAG = "AudioEditor"
    }
    fun addTextWatermark(fromPath: String, outPath: String, duration: Long, listener: FFmpegCmd.OnCmdExecListener) {
        //ffmpeg -f s16le -ar 16000 -ac 1 -i input.pcm output.wav
        val param = StringBuilder()
        val cmd = CmdList()
        cmd.add("ffmpeg")
        cmd.add("-f")
        cmd.add("s16le")
        cmd.add("-ar")
        cmd.add("16000")
        cmd.add("-ac")
        cmd.add("1")
        cmd.add("-i")
        cmd.add(fromPath)
        cmd.add(outPath)
        execCmd(cmd, duration, listener)
    }

    private fun execCmd(cmd: CmdList, duration: Long, listener: FFmpegCmd.OnCmdExecListener) {
        val cmds: Array<String> = cmd.toArray(arrayOfNulls<String>(cmd.size))
        var cmdLog = ""
        for (ss in cmds) {
            cmdLog = "$cmdLog $ss"
        }
        Log.e(TAG, "cmd:$cmdLog")
        FFmpegCmd.exec(cmds, duration, listener)

    }
}