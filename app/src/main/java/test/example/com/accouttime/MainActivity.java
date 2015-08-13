package test.example.com.accouttime;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity implements View.OnClickListener {
    private EditText input_time;
    private Button getTime;
    private Button startTime;
    private Button stopTime;
    private TextView time;
    private int i = 0;
    private Timer timer = null;
    private TimerTask task = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        input_time = (EditText) findViewById(R.id.time);
        getTime = (Button) findViewById(R.id.getTime);
        startTime = (Button) findViewById(R.id.startTime);
        stopTime = (Button) findViewById(R.id.stopTime);
        time = (TextView) findViewById(R.id.tv);
        startTime.setOnClickListener(this);
        stopTime.setOnClickListener(this);
        input_time.setOnClickListener(this);
        time.setOnClickListener(this);
        getTime.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getTime:
                //我们平时用到Integer.parseInt("123");其实默认是调用了int i =Integer.parseInt("123"，10);
                //其中10代表的默认是10进制的，转换的过程可以看成：
                //i=  1*10*10+2*10+3
                //若是int i = Integer.parseInt("123"，16);即可以看成：
                //i  = 1*16*16+2*16+3根据：Character.MIN_RADIX=2和Character.MAX_RADIX=36 则，parseInt(String s, int radix)参数中
                //radix的范围是在2~36之间，超出范围会抛异常。其中s的长度也不能超出7，否则也会抛异常。
                //integer是一个包装类，提供包装类是为了在各种类型间通过各种方法的调用进行转换
                time.setText(input_time.getText().toString());
                i = Integer.parseInt(input_time.getText().toString());
                break;
            case R.id.startTime:
                startTime();
                break;
            case R.id.stopTime:
                stopTime();
                break;
            default:
                break;
        }
    }

    /*
    * handler运行在主线程中（UI线程），它与子线程通过message对象来传递数据；
    * 此时，Hanler承担着接受子线程传过来的message对象（里面包含数据），子线程用sendmessage方法
    * 把这些消息放入主线程队列中，配合主线程进行更新UI；
    * */
    private Handler mhandler = new Handler() {
        public void handleMessage(Message msg) {
    //用于处理消息的函数，从消息队列中取值执行，一个消息执行一次
            time.setText(msg.arg1 + "");//msg对象的arg成员变量传递消息，优点是系统性能小号较少
            startTime();
        };
    };

    public void startTime() {
        timer = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {

                i--;

                System.out.println(i);
                    Message message = mhandler.obtainMessage();
                    message.arg1 = i;
                    mhandler.sendMessage(message);

            }

        };
        timer.schedule(task, 1000);
    }

    public void stopTime() {
        timer.cancel();
    }


}
