package com.mtkg.downloader;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.os.Bundle;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Button;
import android.widget.EditText;
import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;

public class MainActivity extends AppCompatActivity {

    Button btn;
    EditText editText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.download);
        editText = findViewById(R.id.edit_text);

        btn.setOnClickListener( view -> {
            YTDownload(18);
            YTDownload(22);
        });
    }

    public void YTDownload(final int itag){
        String url = editText.getText().toString().toString();
        @SuppressLint("StaticFieldLeak")
        YouTubeUriExtractor youTubeUriExtractor = new YouTubeUriExtractor(this) {
            @Override
            public void onUrisAvailable(String videoId, String videoTitle, SparseArray<YtFile> ytFiles) {
                if (ytFiles != null) {
                    String downloadURL = ytFiles.get(itag).getUrl();
                    Log.e("Download URL: ", downloadURL);
                    DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadURL));
                    request.setTitle(videoTitle);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MOVIES, videoTitle + ".mp4");

                    if (downloadManager != null) {
                        downloadManager.enqueue(request);
                    }
                }
            }
        };
        youTubeUriExtractor.execute(url);
    }

}