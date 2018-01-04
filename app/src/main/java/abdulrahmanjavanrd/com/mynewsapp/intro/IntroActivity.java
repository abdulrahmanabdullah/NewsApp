package abdulrahmanjavanrd.com.mynewsapp.intro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import abdulrahmanjavanrd.com.mynewsapp.R;
import abdulrahmanjavanrd.com.mynewsapp.app.MainActivity;
import abdulrahmanjavanrd.com.mynewsapp.error.CheckError;

public class IntroActivity extends AppCompatActivity {

    private final String TAG = IntroActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        new GoTOMainActivity().start();
    }

    private class GoTOMainActivity extends Thread {

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
//                Log.e(CheckError.TAG, "Can't forward to MainActivity");
            }
            Intent mIntent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(mIntent);
            IntroActivity.this.finish();
        }

    }
}
