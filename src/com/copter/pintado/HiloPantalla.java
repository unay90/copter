package com.copter.pintado;


import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class HiloPantalla extends Thread {

	private boolean run;
	private SurfaceHolder sh;
    private CopterGameView view;


	public HiloPantalla(SurfaceHolder sh, CopterGameView view){
		this.sh = sh;
		this.view = view;
        run = false;
	}
	
	public void setRunning(boolean run) {
	      this.run = run;
	}
	
	public boolean isRunning(){
		return run;
	}

	public void run(){
		Canvas canvas;
		canvas=null;
		while(run){
			try{
				canvas = sh.lockCanvas(null);
				view.onDraw(canvas);
			}
			finally {
				if(canvas != null){
					sh.unlockCanvasAndPost(canvas);
				}   
			}
		}
	}
}
