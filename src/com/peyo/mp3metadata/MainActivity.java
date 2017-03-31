package com.peyo.mp3metadata;

import android.Manifest;
import android.app.Activity;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class MainActivity extends Activity {
	private static final String TAG = "MP3MetaData";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String permissions[] = { Manifest.permission.READ_EXTERNAL_STORAGE };
		requestPermissions(permissions, 0);
	}

	@Override
	protected void onResume() {
		super.onResume();
		new Thread(new Runnable() {
			@Override
			public void run() {
				String dirPath = Environment.getExternalStorageDirectory() + "/Music";
				File dir = new File(dirPath);
				File[] files = dir.listFiles();
				if (files != null) {
					for (int i = 0; i < 20; i++) {
						for (File file : files) {
							readMetadata(file.getPath());
						}
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).run();
	}

	private void readMetadata(String path) {
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		retriever.setDataSource(path);

		Log.i(TAG, "===================================");


		String title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
		Log.i(TAG, "Title : " + title);

		String artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
		Log.i(TAG, "Artist : " + artist);

		String album = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
		Log.i(TAG, "Album : " + album);

		String albumartist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST);
		Log.i(TAG, "AlbumArtist : " + albumartist);

		String genre = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
		Log.i(TAG, "Genre : " + genre);

		String  tracks = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_NUM_TRACKS);
		Log.i(TAG, "Number of Tracks : " + tracks);

		String date = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE);
		Log.i(TAG, "Date : " + date);

		byte[] data = retriever.getEmbeddedPicture();
		if (data != null) {
			Log.i(TAG, "Size of AlbumArt :" + data.length);
		}

		retriever.release();
	}
}
