package com.copter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private TextView tvJugar, tvSonido, tvSalir, tvPuntuacionMax;
	private boolean sonido;
	public static int puntuacion;
	private static SharedPreferences preferencias;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//deja la aplicacion a toda la pantalla y en vertical
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		tvJugar=(TextView)findViewById(R.id.tvJugar);
		tvSonido=(TextView)findViewById(R.id.tvSonido);
		tvSalir=(TextView)findViewById(R.id.tvSalir);
		tvPuntuacionMax=(TextView)findViewById(R.id.tvMaxPuntuacion);
		
		preferencias=getSharedPreferences("copter", Context.MODE_PRIVATE);
		puntuacion=preferencias.getInt("puntuacion", 0);
		
		sonido=true;
		
		tvJugar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				empezarJuego();
			}
		});
		tvSonido.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(sonido){
					sonido=false;
					tvSonido.setText("Sonido: OFF");
				}else{
					sonido=true;
					tvSonido.setText("Sonido: ON");
				}
			}
		});
		tvSalir.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.exit(0);
			}
		});
		tvPuntuacionMax.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mostrarPuntuacion();
			}
		});
	}
	
	private void empezarJuego(){
		Intent i=new Intent(this, CopterJuego.class);
		i.putExtra("sonido", sonido);
		this.startActivity(i);
	}
	
	private void mostrarPuntuacion(){
		puntuacion=preferencias.getInt("puntuacion", 0);
		Toast.makeText(getApplicationContext(), puntuacion+"", Toast.LENGTH_SHORT).show();
	}
	
	public static void guardarPuntuacion(int p){
		Editor editor=preferencias.edit();
		editor.putInt("puntuacion", p);
		editor.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
