package com.example.thread_led;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public abstract class Find_bt extends Activity implements  OnItemClickListener{
	private ListView listViewitem;
	private BluetoothAdapter bluetoothAdapter;
	private HashMap< String, String> map;
	private ArrayList< HashMap<String , String >> arrayList;
	private SimpleAdapter adapter;
	public void OnCreate(Bundle savedInstanceState  ) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_bt);
		listViewitem=(ListView)findViewById(R.id.bt_list);
		findblutooth();
		listViewitem.setOnItemClickListener(this);
		
	
		
	}
	private void findblutooth()
	{
		bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
		if(bluetoothAdapter==null)
			{Toast.makeText(Find_bt.this, "can't support", 300);
		finish();
			}
		while(!bluetoothAdapter.isEnabled())
		{
			bluetoothAdapter.enable();
			
			
		}
		arrayList=new ArrayList<HashMap<String,String>>();
		Set<BluetoothDevice> bluetoothDevices=bluetoothAdapter.getBondedDevices();
		if(bluetoothDevices.size()>0)
		{
			for(BluetoothDevice bDevice : bluetoothDevices)
			{
				map=new HashMap<String, String>();
				map.put("arry_name", bDevice.getName());
				map.put("address", bDevice.getAddress());
				arrayList.add(map);
			}
			
			
		}
		adapter=new SimpleAdapter( this, arrayList, R.layout.activity_main, new String[]{"array_name","adress"},new int[]{R.id.bt_name,R.id.bt_adress});
		listViewitem.setAdapter(adapter);
		
		
		
		
	}
	private String str1;
	public  void onitemclick(AdapterView<?> arg0,View adg1,int arg2,long arg3) {
		str1=arrayList.get(arg2).get("adress");
		Intent intent=new Intent();
		Bundle bundle =new Bundle();
		bundle.putString("STR", str1);
		intent.putExtras(bundle);
		setResult(RESULT_OK,intent);
		finish();
		
	}
	
	
	
	
	

}
