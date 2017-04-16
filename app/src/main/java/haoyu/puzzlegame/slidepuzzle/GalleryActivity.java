package haoyu.puzzlegame.slidepuzzle;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;


import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    private GridView gridView;
    private int[] img = {R.drawable.easy_1,R.drawable.easy_2,R.drawable.easy_3,R.drawable.easy_4,R.drawable.easy_5,R.drawable.easy_6,R.drawable.easy_7,
            R.drawable.easy_8,R.drawable.medium_1,R.drawable.medium_2,R.drawable.medium_3,R.drawable.medium_4,R.drawable.medium_5,R.drawable.medium_6,
            R.drawable.medium_7,R.drawable.medium_8,R.drawable.hard_1,R.drawable.hard_2,R.drawable.hard_3,R.drawable.hard_4,R.drawable.hard_5,
            R.drawable.hard_6,R.drawable.hard_7,R.drawable.hard_8};
    private String[] imgName = {"Easy - Level 1","Easy - Level 2","Easy - Level 3","Easy - Level 4","Easy - Level 5","Easy - Level 6",
            "Easy - Level 7","Easy - Level 8","Medium - Level 1","Medium - Level 2","Medium - Level 3","Medium - Level 4","Medium - Level 5",
            "Medium - Level 6","Medium - Level 7","Medium - Level 8","Hard - Level 1","Hard - Level 2","Hard - Level 3","Hard - Level 4",
            "Hard - Level 5","Hard - Level 6","Hard - Level 7","Hard - Level 8"};
    private ArrayList<GridItem> gridData = null;
    private TextView gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        gallery = (TextView)findViewById(R.id.title_text);
        gallery.setText("Gallery");

        Intent intent = new Intent(GalleryActivity.this, MusicGallery.class);
        startService(intent);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        gridView = (GridView) findViewById(R.id.grid);
        gridData = new ArrayList<>();
        for (int i=0; i<img.length; i++) {
            GridItem item = new GridItem();
            item.setTitle(imgName[i]);
            item.setImage(img[i]);
            gridData.add(item);
        }

        GridViewAdapter adapter = new GridViewAdapter(this, R.layout.grid_item, gridData);
        gridView.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent(GalleryActivity.this,MusicGallery.class);
        stopService(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = new Intent(GalleryActivity.this,MusicGallery.class);
        startService(intent);
    }

}
