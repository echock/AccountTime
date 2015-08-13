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
                //����ƽʱ�õ�Integer.parseInt("123");��ʵĬ���ǵ�����int i =Integer.parseInt("123"��10);
                //����10�����Ĭ����10���Ƶģ�ת���Ĺ��̿��Կ��ɣ�
                //i=  1*10*10+2*10+3
                //����int i = Integer.parseInt("123"��16);�����Կ��ɣ�
                //i  = 1*16*16+2*16+3���ݣ�Character.MIN_RADIX=2��Character.MAX_RADIX=36 ��parseInt(String s, int radix)������
                //radix�ķ�Χ����2~36֮�䣬������Χ�����쳣������s�ĳ���Ҳ���ܳ���7������Ҳ�����쳣��
                //integer��һ����װ�࣬�ṩ��װ����Ϊ���ڸ������ͼ�ͨ�����ַ����ĵ��ý���ת��
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
    * handler���������߳��У�UI�̣߳����������߳�ͨ��message�������������ݣ�
    * ��ʱ��Hanler�е��Ž������̴߳�������message��������������ݣ������߳���sendmessage����
    * ����Щ��Ϣ�������̶߳����У�������߳̽��и���UI��
    * */
    private Handler mhandler = new Handler() {
        public void handleMessage(Message msg) {
    //���ڴ�����Ϣ�ĺ���������Ϣ������ȡִֵ�У�һ����Ϣִ��һ��
            time.setText(msg.arg1 + "");//msg�����arg��Ա����������Ϣ���ŵ���ϵͳ����С�Ž���
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
