package haoyu.puzzlegame.slidepuzzle;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private ImageView iv[][] = new ImageView[4][3];
    private ImageView null_iv;
    private GestureDetector gd;
    private boolean isStart = false;
    private boolean isAniRun = false;
    private Chronometer timer;
    //private Button pause;
    private Button resume;
    private TextView level;
    private Button refresh;
    private Button hint;
    private int[] levelImage = new int[]{R.drawable.medium_1,R.drawable.medium_2,R.drawable.medium_3,R.drawable.medium_4,R.drawable.medium_5,R.drawable.medium_6,R.drawable.medium_7,R.drawable.medium_8};
    private int levelNum = 0;
    private int isPause;
    long timeWhenStopped = 0;
    Dialog dia;
    long totalTime = 0;
    private TextView step;
    private int intStep=0;



    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
    public boolean dispatchTouchEvent(MotionEvent event) {
        gd.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(SecondActivity.this, MusicMain.class);
        startService(intent);

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }



        gd = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                int type = detectDicr(e1.getX(),e1.getY(),e2.getX(),e2.getY());
                moveBoxByGesture(type,true);
                return false;
            }
        });
        setContentView(R.layout.activity_main);
        timer = (Chronometer) findViewById(R.id.chronometer);
        //pause = (Button) findViewById(R.id.pause);
        //pause.setOnClickListener(isStop);
        step = (TextView)findViewById(R.id.step);
        step.setText("0");

        resume = (Button) findViewById(R.id.resume);
        resume.setText("Start");
        resume.setOnClickListener(isResume);
        hint = (Button) findViewById(R.id.hint);
        hint.setOnClickListener(isHint);
        refresh = (Button) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                level.setText("Level "+(levelNum+1));
                timer.setBase(SystemClock.elapsedRealtime());
                timeWhenStopped = 0;
                timer.start();

                Bitmap bm = ((BitmapDrawable)getResources().getDrawable(levelImage[levelNum])).getBitmap();
                int bm_height = bm.getHeight() / 4;
                int bm_width = bm.getWidth() / 3;
                for (int i = 0; i < iv.length; i++) {
                    for (int j = 0; j < iv[0].length; j++) {

                        Bitmap bm1 = Bitmap.createBitmap(bm,j * bm_width,i * bm_height,bm_width,bm_height);
                        iv[i][j].setImageBitmap(bm1);
                        iv[i][j].setPadding(1,1,1,1);
                        Box box = new Box(j,i,bm1);
                        iv[i][j].setTag(box);
                    }
                }

                GridLayout gl = (GridLayout) findViewById(R.id.gl);
                gl.removeAllViews();
                for (int i = 0; i < iv.length; i++) {
                    for (int j = 0; j < iv[0].length; j++) {
                        gl.addView(iv[i][j]);
                    }
                }
                setNullImageView(iv[3][2]);
                isStart = false;
                setRandom();
                isStart = true;

                timer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                timer.start();
                isPause = 0;
            }
        });
        init();
    }



    private void init(){
        Bitmap bm = ((BitmapDrawable)getResources().getDrawable(R.drawable.medium_1)).getBitmap();
        int bm_height = bm.getHeight() / 4;
        int bm_width = bm.getWidth() / 3;
        for (int i = 0; i < iv.length; i++) {
            for (int j = 0; j < iv[0].length; j++) {
                iv[i][j] = new ImageView(this);
                Bitmap bm1 = Bitmap.createBitmap(bm,j * bm_width,i * bm_height,bm_width,bm_height);
                iv[i][j].setImageBitmap(bm1);
                iv[i][j].setPadding(1,1,1,1);
                Box box = new Box(j,i,bm1);
                iv[i][j].setTag(box);
            }
        }

        GridLayout gl = (GridLayout) findViewById(R.id.gl);
        for (int i = 0; i < iv.length; i++) {
            for (int j = 0; j < iv[0].length; j++) {
                gl.addView(iv[i][j]);
            }
        }
        setNullImageView(iv[3][2]);
        setRandom();
        isPause = 1;

        level = (TextView) findViewById(R.id.title_text);
        level.setText("Medium - Level 1");

        resume.setText("Pause");
        timer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        timer.start();
        isPause = 0;
    }

    private View.OnClickListener isResume = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isPause==1) {
                resume.setText("Pause");
                timer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                timer.start();
                isPause = 0;
            }
            else if(isPause==0){
                resume.setText("Start");
                timeWhenStopped = timer.getBase() - SystemClock.elapsedRealtime();
                timer.stop();
                isPause = 1;
            }
        }
    };


    private View.OnClickListener isHint = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hint();
        }
    };

    private void setNullImageView(ImageView iv){
        iv.setImageBitmap(null);
        null_iv = iv;
    }

    private boolean isAroundNull(ImageView iv){
        Box null_b = (Box) null_iv.getTag();
        Box b = (Box) iv.getTag();
        if (null_b.x == b.x && null_b.y - 1 == b.y){
            return true;
        }else if (null_b.x - 1 == b.x && null_b.y == b.y){
            return true;
        }else if (null_b.x + 1 == b.x && null_b.y == b.y){
            return true;
        }else if (null_b.x == b.x && null_b.y + 1 == b.y){
            return true;
        }
        return false;
    }

    private class Box{
        int x;
        int y;
        Bitmap p;
        int p_x;
        int p_y;

        Box(int x, int y, Bitmap p) {
            this.x = x;
            this.y = y;
            this.p = p;
            this.p_x = x;
            this.p_y = y;
        }


        boolean isTrue() {
            return (x == p_x && y == p_y);
        }
    }

    private void setAnimation(final ImageView iv){
        if(isPause==1){
        }
        else {
            TranslateAnimation ta = null;
            if (iv.getX() > null_iv.getX()) {
                ta = new TranslateAnimation(0.1f, -iv.getWidth(), 0.1f, 0.1f);
            } else if (iv.getX() < null_iv.getX()) {
                ta = new TranslateAnimation(0.1f, iv.getWidth(), 0.1f, 0.1f);
            } else if (iv.getY() > null_iv.getY()) {
                ta = new TranslateAnimation(0.1f, 0.1f, 0.1f, -iv.getWidth());
            } else if (iv.getY() < null_iv.getY()) {
                ta = new TranslateAnimation(0.1f, 0.1f, 0.1f, iv.getWidth());
            }
            assert ta != null;
            ta.setDuration(120);
            ta.setFillAfter(true);
            ta.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    isAniRun = true;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    isAniRun = false;
                    iv.clearAnimation();
                    intStep++;
                    step.setText(String.valueOf(intStep));
                    changeData(iv);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            iv.startAnimation(ta);
        }
    }

    private int detectDicr(float start_x,float start_y,float end_x,float end_y){
        boolean isLeftOrRight = Math.abs(start_x - end_x) > Math.abs(start_y - end_y);
        if (isLeftOrRight){
            if (start_x - end_x > 0){
                return 3;
            }else if (start_x - end_x < 0){
                return 4;
            }
        }else {
            if (start_y - end_y > 0){
                return 1;
            }else if (start_y - end_y < 0){
                return 2;
            }
        }
        return 0;
    }

    private void changeData(ImageView iv){
        if(isAniRun){
            return;
        }
        Box b1 = (Box) iv.getTag();
        null_iv.setImageBitmap(b1.p);
        Box nul_b = (Box) null_iv.getTag();
        nul_b.p = b1.p;
        nul_b.p_x = b1.p_x;
        nul_b.p_y = b1.p_y;
        setNullImageView(iv);
        if (isStart){
            isGameOver();
        }
    }

    private void moveBoxByGesture(int ges,boolean isAnime){
        Box null_box = (Box) null_iv.getTag();
        int new_x = null_box.x;
        int new_y = null_box.y;
        if (ges == 1){
            new_y++;
        }else if (ges == 2){
            new_y--;
        }else if (ges == 3){
            new_x++;
        }else if (ges == 4){
            new_x--;
        }
        if (new_x >= 0 && new_x < iv[0].length && new_y >= 0 && new_y < iv.length){
            if (isAnime){
                setAnimation(iv[new_y][new_x]);
            }else{
                changeData(iv[new_y][new_x]);
            }
        }
    }

    private void setRandom(){
        for (int i = 0; i < 800; i++) {
            int flag = (int) ((Math.random() * 4) + 1);
            moveBoxByGesture(flag,false);
        }
        isStart = true;
    }

    private void isGameOver(){
        boolean isOver = false;
        boolean isBreakFromj = false;
        for (int i = 0; i < iv.length; i++) {
            if (isBreakFromj){
                break;
            }
            for (int j = 0; j < iv[0].length; j++) {
                if (iv[i][j] == null_iv){
                    continue;
                }
                Box box = (Box) iv[i][j].getTag();
                if (box.isTrue()){
                    isOver = true;
                }else if (!box.isTrue()){
                    isOver = false;
                    isBreakFromj = true;
                    break;
                }
            }
        }
        if (isOver){
            totalTime = totalTime + calculateElapsedTime(timer);
            timeWhenStopped = timer.getBase() - SystemClock.elapsedRealtime();
            timer.stop();
            passLevel();
        }
    }

    private long calculateElapsedTime(Chronometer mChronometer) {

        long stoppedSeconds = 0;
        String chronoText = mChronometer.getText().toString();
        String array[] = chronoText.split(":");
        if (array.length == 2) {
            stoppedSeconds = Integer.parseInt(array[0]) * 60 + Integer.parseInt(array[1]);
        } else if (array.length == 3) {
            stoppedSeconds = Integer.parseInt(array[0]) * 60 * 60 + Integer.parseInt(array[1]) * 60 + Integer.parseInt(array[2]);
        }

        return stoppedSeconds;

    }

    private void passLevel() {

        levelNum++;
        if(levelNum==8){
            Intent intent = new Intent(SecondActivity.this, GameOverSecond.class);
            intent.putExtra("extra_data", Long.toString(totalTime));
            startActivity(intent);
        }

        AlertDialog.Builder dialog = new AlertDialog.Builder(SecondActivity.this);
        dialog.setTitle("Congratulations");
        dialog.setPositiveButton("Next Level", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                level.setText("Medium - Level "+(levelNum+1));

                timer.setBase(SystemClock.elapsedRealtime());
                timeWhenStopped = 0;
                timer.start();

                Bitmap bm = ((BitmapDrawable)getResources().getDrawable(levelImage[levelNum])).getBitmap();
                int bm_height = bm.getHeight() / 4;
                int bm_width = bm.getWidth() / 3;
                for (int i = 0; i < iv.length; i++) {
                    for (int j = 0; j < iv[0].length; j++) {

                        Bitmap bm1 = Bitmap.createBitmap(bm,j * bm_width,i * bm_height,bm_width,bm_height);
                        iv[i][j].setImageBitmap(bm1);
                        iv[i][j].setPadding(1,1,1,1);
                        Box box = new Box(j,i,bm1);
                        iv[i][j].setTag(box);
                    }
                }

                GridLayout gl = (GridLayout) findViewById(R.id.gl);
                gl.removeAllViews();
                for (int i = 0; i < iv.length; i++) {
                    for (int j = 0; j < iv[0].length; j++) {
                        gl.addView(iv[i][j]);
                    }
                }
                setNullImageView(iv[3][2]);
                isStart = false;
                setRandom();
                isStart = true;

                intStep = 0;
                step.setText(String.valueOf(intStep));
            }
        });
        dialog.show();
    }



    public void hint(){

        if(isPause==0){
            resume.setText("Start");
            timeWhenStopped = timer.getBase() - SystemClock.elapsedRealtime();
            timer.stop();
            isPause = 1;
        }

        Context context = SecondActivity.this;
        dia = new Dialog(context, R.style.edit_AlertDialog_style);
        dia.setContentView(R.layout.hint);

        ImageView imageView = (ImageView) dia.findViewById(R.id.hint);
        imageView.setBackgroundResource(levelImage[levelNum]);
        dia.show();
        dia.setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent(SecondActivity.this,MusicMain.class);
        stopService(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = new Intent(SecondActivity.this,MusicMain.class);
        startService(intent);
    }

}
