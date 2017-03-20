package haoyu.puzzlegame.slidepuzzle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    private TextView easyPlayer;
    private TextView mediumPlayer;
    private TextView hardPlayer;
    private TextView easyGrade;
    private TextView mediumGrade;
    private TextView hardGrade;
    private TextView achievement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_score);

        achievement = (TextView)findViewById(R.id.title_text);
        achievement.setText("Latest Achievement");

        Intent intent = new Intent(ScoreActivity.this, MusicGallery.class);
        startService(intent);

        easyPlayer = (TextView)findViewById(R.id.easyPlayer);
        easyGrade = (TextView)findViewById(R.id.easyGrade);

        mediumPlayer = (TextView)findViewById(R.id.mediumPlayer);
        mediumGrade = (TextView)findViewById(R.id.mediumGrade);

        hardPlayer = (TextView)findViewById(R.id.hardPlayer);
        hardGrade = (TextView)findViewById(R.id.hardGrade);


        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        String name = pref.getString("name", "");
        String time = pref.getString("time", "");
        easyGrade.setText(time);
        easyPlayer.setText(name);


        SharedPreferences pref2 = getSharedPreferences("dataSecond", MODE_PRIVATE);
        String name2 = pref2.getString("name", "");
        String time2 = pref2.getString("time", "");
        mediumGrade.setText(time2);
        mediumPlayer.setText(name2);


        SharedPreferences pref3 = getSharedPreferences("dataThird", MODE_PRIVATE);
        String name3 = pref3.getString("name", "");
        String time3 = pref3.getString("time", "");
        hardGrade.setText(time3);
        hardPlayer.setText(name3);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent(ScoreActivity.this,MusicGallery.class);
        stopService(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = new Intent(ScoreActivity.this,MusicGallery.class);
        startService(intent);
    }

}
