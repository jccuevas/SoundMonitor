package es.ujaen.soundmonitor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.DatagramSocket;

public class MainActivity extends AppCompatActivity {


    Button mConnect = null;
    Button mDisconnect = null;
    TextView mText=null;
    DatagramSocket mMainServer= null;
    Thread mMainThread = null;
    boolean mServerUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mText = (TextView)findViewById(R.id.text_meesage);

        mConnect = (Button)findViewById(R.id.button_connect);
        mConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainThread = new Thread(new Server());
                mMainThread.start();

            }
        });

        mDisconnect = (Button)findViewById(R.id.button_diconnect);
        mDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mServerUp=false;
            }
        });

    }

    public class Server implements Runnable{
        int times =0;
        public void run(){

            mText.post(new Runnable() {
                @Override
                public void run() {
                    mText.setText("Starting server...");
                }
            });
            do{
                mServerUp=true;






                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mText.post(new Runnable() {
                    @Override
                    public void run() {
                        mText.setText("Again up..."+times);

                    }
                });
                times++;
            }while(mServerUp);
            shutdown();
            //mMainServer.close();


        }

        public void shutdown(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mText.setText("Shutting down the server...");
                }
            });


        }
    }
}
