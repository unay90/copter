package com.copter;

import com.copter.pintado.CopterGameView;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class CopterJuego extends Activity{
	
	private CopterGameView copterGameView;
	private boolean sonido;
	protected Bundle bun;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//deja la aplicacion a toda la pantalla y en horizontal
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		bun=getIntent().getExtras();
		sonido=bun.getBoolean("sonido");
		copterGameView=new CopterGameView(this, sonido);
		setContentView(copterGameView);
	}
}
