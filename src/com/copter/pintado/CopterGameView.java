package com.copter.pintado;

import java.util.ArrayList;

import com.copter.R;
import com.copter.juego.Coordenada;
import com.copter.juego.Copter;
import com.copter.juego.ElementoCopter;
import com.copter.juego.Obstaculo;
import com.copter.pintado.HiloPantalla;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CopterGameView extends SurfaceView implements SurfaceHolder.Callback{
	
	private HiloPantalla hiloPantalla;
	private HiloCopter hiloCopter;
	private Paint pincelV, pincelB;
	private ElementoCopter copter;
	private ElementoCopter obstaculo;
	private int ran;
	private Bitmap imgcopter, imgexplosion;
	private ArrayList<Obstaculo> listaBarras;
	private int ultBarra;
	private int marcador;
	private boolean sonido;
	private boolean muerto;

	public CopterGameView(Context context, boolean s) {
		super(context);
		getHolder().addCallback(this);
		sonido=s;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		muerto=false;
		marcador=0;
		ran=(int) Math.floor((getHeight()-(getHeight()*20/100)-0+ 1) * Math.random() + 0);//saca un numero aleatorio entre 0 y getHeight()-(getHeight()*20/100)
		imgcopter=BitmapFactory.decodeResource(getResources(), R.drawable.copter);
		imgexplosion=BitmapFactory.decodeResource(getResources(), R.drawable.imgexplosion);
		pincelV=new Paint();
		pincelV.setColor(Color.GREEN);
		pincelB=new Paint();
		pincelB.setColor(Color.WHITE);
		pincelB.setTextSize(20);
		
		listaBarras=new ArrayList<Obstaculo>();
		
		copter=new Copter(new Coordenada(getWidth()*10/100, getHeight()/2), imgcopter.getWidth(), imgcopter.getHeight(), marcador,muerto);
		obstaculo=new Obstaculo(new Coordenada(getWidth(), ran), 20, getHeight()*20/100);
		listaBarras.add((Obstaculo) obstaculo);
		
		hiloPantalla = new HiloPantalla(getHolder(), this);
        hiloPantalla.setRunning(true);
        hiloPantalla.start();

        hiloCopter=new HiloCopter((Copter) copter, listaBarras, new Rect(0, 0, getWidth(), getHeight()), this.getContext(), sonido);
        hiloCopter.setRunning(true);
        hiloCopter.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		hiloPantalla.setRunning(false);
		hiloCopter.setRunning(false);
		while (retry) {
			try {
				hiloPantalla.join();
				hiloCopter.join();
				retry = false;
			} catch (InterruptedException e) { }
		}
	}
	
	public void onDraw(Canvas canvas){
		if(hiloPantalla.isRunning()){
			canvas.drawColor(Color.BLACK);
			if(((Copter) copter).isMuerto()){
				int x, y;
				x=copter.getOrigenX()+imgcopter.getWidth()-imgexplosion.getWidth();
				y=copter.getOrigenY()+imgcopter.getHeight()-imgexplosion.getHeight();
				canvas.drawBitmap(imgexplosion, x, y, null);
			}else{
				canvas.drawBitmap(imgcopter, copter.getOrigenX(), copter.getOrigenY(), null);
			}
			
			ultBarra=listaBarras.size();
			for(int i=0; i<ultBarra; i++){
				canvas.drawRect(listaBarras.get(i).getR(), pincelV);
				ultBarra=listaBarras.size();
			}
			
			canvas.drawText(((Copter) copter).getMarcador()+"", 10, 20, pincelB);
		}
	}
	
	public boolean onTouchEvent(MotionEvent event){
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				((Copter) copter).setArriba(true);
				break;
			case MotionEvent.ACTION_UP:
				((Copter) copter).setArriba(false);
				break;
		}
		return true;
	}

}
