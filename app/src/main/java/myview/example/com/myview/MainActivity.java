package myview.example.com.myview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private int mTotalProgress = 90;
    private int mCurrentProgress = 0;
    //进度条
    private CircleView mTasksView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTasksView = (CircleView) findViewById(R.id.tasks_view);

        new Thread(new ProgressRunable()).start();
    }

    class ProgressRunable implements Runnable {
        @Override
        public void run() {
            while (mCurrentProgress < mTotalProgress) {
                mCurrentProgress += 1;
                mTasksView.setProgress(mCurrentProgress);
                try {
                    Thread.sleep(90);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
