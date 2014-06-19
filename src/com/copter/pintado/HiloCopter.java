package com.copter.pintado;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Rect;
import android.media.MediaPlayer;

import com.copter.MainActivity;
import com.copter.R;
import com.copter.juego.Coordenada;
import com.copter.juego.Copter;
import com.copter.juego.ElementoCopter;
import com.copter.juego.Obstaculo;

public class HiloCopter extends Thread {
	private boolean run;
	private Copter copter;
	private ArrayList<Obstaculo> listaBarras;
	private Rect screen;
	private int ran;
	private ElementoCopter ultBarra, barra;
	private int nUltimaBarra;
	private MediaPlayer explosion;
	private boolean sonido;

	public HiloCopter(Copter copter, ArrayList<Obstaculo> lb, Rect r, Context context, boolean s){
        run = false;
        this.copter=copter;
        listaBarras=lb;
        screen=r;
        explosion=MediaPlayer.create(context, R.raw.explosion);
        sonido=s;
	}
	
	public void setRunning(boolean run) {
	      this.run = run;
	}

	public void run(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while(run){
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(copter.isMuerto()){
				reInitJuego();
				try {
					copter.setMuerto(false);
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
			if(copter.puedoMover(screen)){
				for(Obstaculo i : listaBarras){
					if(copter.chocarCon(0, copter.getVelocidad(), i.getR())){
						copter.setMuerto(true);;
						continue;
					}
				}
				copter.mover();
			}else{
				copter.setMuerto(true);
				continue;
			}
			for(Obstaculo i : listaBarras){
				if(i.puedoMover(copter)){
					i.mover();
				}else{
					copter.setMuerto(true);
					continue;
				}
			}
			nUltimaBarra=listaBarras.size();
			ultBarra=listaBarras.get(nUltimaBarra-1);
			if(ultBarra.getOrigenX()+150<screen.right){
				ran=(int) Math.floor((screen.bottom-(screen.bottom*20/100)-0+ 1) * Math.random() + 0);//saca un numero aleatorio entre 0 y alto-100
				barra=new Obstaculo(new Coordenada(screen.right, ran), 20, screen.bottom*20/100);
				listaBarras.add((Obstaculo) barra);
			}
			if(listaBarras.get(0).getOrigenX()+20<screen.left){
				listaBarras.remove(0);
				copter.setMarcador(copter.getMarcador()+10);
			}
		}
	}
	
	public void reInitJuego(){
		if(sonido){
			explosion.start();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		copter.setOrigenY(copter.getInicioY());
		ran=(int) Math.floor((screen.bottom-(screen.bottom*20/100)-0+ 1) * Math.random() + 0);//saca un numero aleatorio entre 0 y alto-100
		barra.setOrigenY(ran);
		barra.setOrigenX(screen.right);
		barra=new Obstaculo(new Coordenada(screen.right, ran), 20, screen.bottom*20/100);
		listaBarras.removeAll(listaBarras);
		listaBarras.add((Obstaculo) barra);
		comprobarPuntuacion();
		copter.setMarcador(0);
	}
	
	private void comprobarPuntuacion(){
		if(MainActivity.puntuacion<copter.getMarcador()){
			MainActivity.guardarPuntuacion(copter.getMarcador());
		}
	}
}
