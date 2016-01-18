package com.golovatiuk.musicplayer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ListView mListView;
    private ArrayList<String> filesFromDirList;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        filesFromDirList = new ArrayList<>();
//        File musicDir = new File(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString());
//
//        Log.d("###", MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString());

        getCursorForAudio();

        mListView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.item, filesFromDirList);
        mListView.setAdapter(adapter);
        mListView.setDividerHeight(5);
    }

//    private void getAllFilesOfDir(File directory) {
//        final File[] files = directory.listFiles();
//
//        if ( files != null ) {
//            for ( File file : files ) {
//                if ( file != null ) {
//                    if ( file.isDirectory() ) {  // it is a folder...
//                        getAllFilesOfDir(file);
//                    } else {
//                        if (file.getName().endsWith(".mp3")) {
//                            filesFromDirList.add(file.getAbsolutePath());
//                        }
//                    }
//                }
//            }
//        }
//    }

    private void getCursorForAudio() {
        ContentResolver cr = mContext.getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cur = cr.query(uri, null, selection, null, sortOrder);
        int count = 0;

        if(cur != null)
        {
            count = cur.getCount();

            if(count > 0)
            {
                while(cur.moveToNext())
                {
                    String file = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA));
                    if (file.endsWith(".mp3")) {
                        filesFromDirList.add(file);
                    }
                }
            }
        }

        cur.close();
    }
}
