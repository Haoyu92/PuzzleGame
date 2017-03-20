package haoyu.puzzlegame.slidepuzzle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GameOverSecond extends AppCompatActivity {

    private Button replay;
    private TextView totalTimeShow;
    private EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Intent intent = getIntent();
        final String data = intent.getStringExtra("extra_data")+" S";

        replay=(Button) findViewById(R.id.replay);
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameOverSecond.this,WelcomeActivity.class);
                startActivity(intent);

                SharedPreferences.Editor editor = getSharedPreferences("dataSecond", MODE_PRIVATE).edit();
                editor.putString("name",name.getText().toString());
                editor.putString("time",data);
                editor.commit();
            }
        });

        totalTimeShow = (TextView) findViewById(R.id.totalTimeShow);
        totalTimeShow.setText(totalTimeShow.getText()+ data);
        name=(EditText) findViewById(R.id.name);
    }
}
