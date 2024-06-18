package com.example.mainapp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.arthenica.ffmpegkit.FFmpegKit;
import com.arthenica.ffmpegkit.FFmpegSession;
import com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mainapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               String dir= getFolderDirPath(MainActivity.this,"123");
                String dir =getFileRoot(MainActivity.this)+"/123";
                Log.i(TAG, "onClick: dir:"+dir);
                String inputPath=dir+"/1.mp4";
                String outputPath=dir+"/1_out_h265.mp4";
                String command = "-i "+inputPath+" -c:v libx265 -c:a copy -y "+outputPath;
//                String command ="-version";
                FFmpegKit.executeAsync(command, session -> {
                    Log.i(TAG, "apply: "+session.toString());
                    Log.i(TAG, "onClick:  session.getReturnCode():"+ session.getReturnCode());
                });
            }
        });
    }

    public static String getFileRoot(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File external = context.getExternalFilesDir(null);
            if (external != null) {
                return external.getAbsolutePath();
            }
        }
        return context.getFilesDir().getAbsolutePath();
    }

    public static String getFolderDirPath(Context context, String dstDirPathToCreate) {
        File dstFileDir = new File(Environment.getExternalStorageDirectory(), dstDirPathToCreate);
        if (!dstFileDir.exists() && !dstFileDir.mkdirs()) {
            Log.e(TAG, "Failed to create file dir path--->" + dstDirPathToCreate);
            return null;
        }
        return dstFileDir.getAbsolutePath();
    }

}