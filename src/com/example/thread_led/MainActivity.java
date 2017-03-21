package com.example.thread_led;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.math.BigInteger;
import java.security.PublicKey;
import java.util.UUID;

import android.support.v7.app.ActionBarActivity;
import android.R.anim;
import android.R.string;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private TextView tv1;
	String name=null;
	private String str;
	private BufferedOutputStream out8051;
	private BufferedInputStream in8051;
	private BluetoothSocket socket8051;
	private BluetoothAdapter bluetoothAdapter;
	private final UUID UUID8051=UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	byte[] array_51;
	byte[] result;
	private Button end,thread_on,thread_off,open;
	private int p0_data;
	private Thread thread;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	findid();
	Intent intent=new Intent();
	intent.setClass(MainActivity.this, Find_bt.class);
	startActivityForResult(intent,1);
	tv1=(TextView)findViewById(R.id.tv1);
	end.setOnClickListener(new endOnclick());
	open.setOnClickListener(new btn_open());
	thread_off.setOnClickListener(new thinterrupt());
	thread_on.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			thread=new Thread(new myrunable());
			thread.start();
		}
	});
	
	
	}
	public class thinterrupt implements OnClickListener{
		

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		if(thread!=null)
		{
			while(thread.isAlive())
			{
				thread.interrupt();
			}
		}
		}
		
		
	}
	public  class btn_open implements OnClickListener {
	

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			bluetoothAdapter.disable();
			while(!bluetoothAdapter.isEnabled())
			{
				bluetoothAdapter.enable();
			}
		}
		
		
		
	}
	public class endOnclick implements OnClickListener{
		
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(thread!=null)
			{
				while(thread.isAlive())
				{
					thread.interrupt();
				}
				
			}
			
			android.os.Process.killProcess(android.os.Process.myPid());
		}
		
		
	}
	public class myrunable implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(socket8051!=null)
			{
				while(true)
				{
					try{
						out8051.write(BigInteger.valueOf(512*6).toByteArray());
						out8051.flush();
						p0_data=in8051.read();
						out8051.write(BigInteger.valueOf(p0_data+512).toByteArray());
						out8051.flush();
					}
					catch(IOException e)
					{e.printStackTrace();}
					
						try {
							Thread.sleep(5);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							return;
						}
					
					}
				
				
				}else Toast.makeText(MainActivity.this, "还没连接", 300).show();
				
			}
		}
		private void findid()
		{
			end=(Button)findViewById(R.id.end);
			thread_off=(Button)findViewById(R.id.thread_end);
			thread_on=(Button)findViewById(R.id.theread_btn);
			open=(Button)findViewById(R.id.btn_open);
			
		}
		protected void OnActivityresult (int requestcode,int resultcode,Intent data){
			if(requestcode==1)
			{
			if(resultcode==RESULT_OK)
			{
				Bundle bundle=data.getExtras();
				str=bundle.getString("STR");
				tv1.setText(str);
				bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
				BluetoothDevice device=bluetoothAdapter.getRemoteDevice(str);
				try{
					socket8051=device.createRfcommSocketToServiceRecord(UUID8051);
					socket8051.connect();
					out8051=new BufferedOutputStream(socket8051.getOutputStream());
					in8051=new BufferedInputStream(socket8051.getInputStream());
					array_51=BigInteger.valueOf(512+255).toByteArray();
					out8051.write(array_51);
					out8051.flush();
					
					
				}catch(IOException e)
				{}
			}
			if(resultcode==RESULT_CANCELED)
			{	}
		}
	}
	
}


