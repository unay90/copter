package com.copter.juego;

import android.graphics.Rect;

public class Copter extends ElementoCopter{
	
	private int inicioY;
	private final int VELOCIDAD=1;
	private boolean arriba=false;
	private int marcador;
	private boolean muerto;

	public Copter(Coordenada origen, int ancho, int alto, int m, boolean mu){
		super(origen, ancho, alto);
		inicioY=origen.getY();
		marcador=m;
		muerto=mu;
	}

	public boolean isMuerto() {
		return muerto;
	}

	public void setMuerto(boolean muerto) {
		this.muerto = muerto;
	}

	public int getMarcador() {
		return marcador;
	}

	public void setMarcador(int marcador) {
		this.marcador = marcador;
	}

	public int getInicioY(){
		return inicioY;
	}
	
	public boolean puedoMover(Rect screen) {
		if(!puedoMover(0, VELOCIDAD, screen)){
			return false;
		}
		return true;
	}
	
	public void mover(){
		if(arriba){
			setOrigenY(origen.getY()-VELOCIDAD);
		}else{
			setOrigenY(origen.getY()+VELOCIDAD);
		}
	}
	
	public void setArriba(boolean a){
		arriba=a;
	}
	
	public int getVelocidad(){
		return VELOCIDAD;
	}
}
