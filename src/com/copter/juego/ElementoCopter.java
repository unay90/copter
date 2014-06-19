package com.copter.juego;

import android.graphics.Rect;

public abstract class ElementoCopter {
	protected Coordenada origen;
	protected int ancho;
	protected int alto;
	
	public ElementoCopter(Coordenada origen, int ancho, int alto){
		this.origen=origen;
		this.ancho=ancho;
		this.alto=alto;
	}
	
	public int getOrigenY(){
		return origen.getY();
	}
	
	public int getOrigenX(){
		return origen.getX();
	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}
	
	public Rect getR(){
		return new Rect(getOrigenX(), getOrigenY(), getOrigenX()+ancho, getOrigenY()+alto);
	}
	
	public void setOrigenX(int newX){
		origen.setX(newX);
	}
	
	public void setOrigenY(int newY){
		origen.setY(newY);
	}
	
	public boolean puedoMover(int x, int y, Rect screen){
		return screen.contains(origen.getX()+x, origen.getY()+y, origen.getX()+ancho+x, origen.getY()+alto+y);
	}
	
	public boolean chocarCon(int x, int y, Rect barra){
		if(barra.contains(origen.getX()+x, origen.getY()+y)){
			return true;
		}
		if(barra.contains(origen.getX()+ancho+x, origen.getY()+y)){
			return true;
		}
		if(barra.contains(origen.getX()+x, origen.getY()+alto+y)){
			return true;
		}
		if(barra.contains(origen.getX()+ancho+x, origen.getY()+alto+y)){
			return true;
		}
		if(barra.contains(origen.getX()+ancho/2+x, origen.getY()+y)){
			return true;
		}
		if(barra.contains(origen.getX()+ancho/2+x, origen.getY()+alto+y)){
			return true;
		}
		return false;
	}
}
