package com.example.kasava;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Services extends Service {
    private static final String TAG = "VideoRecordingService";
    private MediaRecorder mediaRecorder;
    private static final int RECORDING_DURATION = 10 * 60 * 1000; // 10 minutes
    private final Handler handler = new Handler();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startRecording();
        return START_STICKY;
    }

    private void startRecording() {
        setupMediaRecorder();
        try {
            mediaRecorder.start();
            Log.d(TAG, "Recording started");
        } catch (IllegalStateException e) {
            Log.e(TAG, "Error starting recording: " + e.getMessage());
            stopSelf(); // Stop the service if starting fails
            return; // Handle the error appropriately
        }

        // Schedule a task to stop and start recording again after a certain duration
        handler.postDelayed(this::splitRecording, RECORDING_DURATION);
    }

    private void splitRecording() {
        try {
            mediaRecorder.stop();
            saveRecording(); // Save the current recording
            Log.d(TAG, "Recording split and saved");
        } catch (RuntimeException e) {
            Log.e(TAG, "Error stopping recording: " + e.getMessage());
            // Handle error appropriately (e.g., restart or log)
            return;
        }

        // Prepare a new MediaRecorder instance for the next recording
        setupMediaRecorder();
        try {
            mediaRecorder.start();
            Log.d(TAG, "Recording resumed");
        } catch (IllegalStateException e) {
            Log.e(TAG, "Error resuming recording: " + e.getMessage());
            stopSelf(); // Stop the service if resuming fails
            return; // Handle the error appropriately
        }

        // Schedule the next split
        handler.postDelayed(this::splitRecording, RECORDING_DURATION);
    }

    private void setupMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        try {
            // Set up MediaRecorder settings (audio and video sources, output format, etc.)
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);

            // Set output file path using a timestamp
            String currentFilePath = getOutputFilePath();
            mediaRecorder.setOutputFile(currentFilePath);
            mediaRecorder.setVideoSize(1920, 1080); // Set the video size
            mediaRecorder.setVideoFrameRate(30); // Set the frame rate
            mediaRecorder.setOrientationHint(90); // Set orientation if necessary

            // Prepare the MediaRecorder
            mediaRecorder.prepare();
            Log.d(TAG, "MediaRecorder prepared");
        } catch (Exception e) {
            Log.e(TAG, "Error setting up MediaRecorder: " + e.getMessage());
            if (mediaRecorder != null) {
                mediaRecorder.release();
                mediaRecorder = null;
            }
        }
    }

    private String getOutputFilePath() {
        // Generate a new file path with a timestamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return getExternalFilesDir(Environment.DIRECTORY_MOVIES) + "/VID_" + timeStamp + ".mp4";
    }

    private void saveRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.reset(); // Reset the MediaRecorder
            mediaRecorder.release(); // Release resources
            mediaRecorder = null; // Set to null to avoid re-use
            Log.d(TAG, "MediaRecorder resources released");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
                Log.d(TAG, "Recording stopped on service destroy");
            } catch (RuntimeException e) {
                Log.e(TAG, "Error stopping recording on destroy: " + e.getMessage());
            } finally {
                mediaRecorder.release();
                mediaRecorder = null;
            }
        }
        handler.removeCallbacksAndMessages(null); // Clean up any pending posts
        Log.d(TAG, "Service destroyed");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null; // We don't provide binding, so return null
    }
}
