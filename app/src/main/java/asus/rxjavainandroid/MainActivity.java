package asus.rxjavainandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    private TextView tv, tv1;
    private Button btn1;
    private Observable<TextView> observable;
    private Subscriber subscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        observable = Observable.create(subscriber -> {
            this.subscriber = subscriber;
            tv = (TextView) findViewById(R.id.tv);
            tv1 = (TextView) findViewById(R.id.tv1);
            btn1 = (Button) findViewById(R.id.btn1);
        });

        observable.map(tv-> {
            switch (tv.getId()) {
                case R.id.tv:
                    tv.setText(R.string.tv);
                    tv.setOnClickListener(v -> toast("66666"));
                    break;
                case R.id.tv1:
                    tv.setText(R.string.tv1);
                    tv.setOnClickListener(v -> toast("333333333333"));
                    break;
                case R.id.btn1:
                    btn1.setOnClickListener(v -> toast(v.getId() + "==="));
            }
            return tv;
        }).subscribe();

        observable.interval(1, TimeUnit.SECONDS).take(11)
                .subscribe(t->runOnUiThread(()-> {
                    if(subscriber.isUnsubscribed()) return;
                    if (t.equals(5L)) {
                        tv.setTextSize(20);
                    }
                    tv.setText(t+"seconds");
                }));







    }

    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


}
