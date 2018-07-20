package jsp.co.za.jspandroid.Activities;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jsp.co.za.jspandroid.R;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        delayActivity();
    }
    private void delayActivity()
    {
        new CountDownTimer(2000,100){
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent=new Intent(LandingActivity.this,MainActivity.class);
                startActivity(intent);
            }
        }.start();
    }


}
