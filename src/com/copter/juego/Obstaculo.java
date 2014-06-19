package com.copter.juego;

public class Obstaculo extends ElementoCopter{

	private final int VELOCIDAD=1;
	
	public Obstaculo(Coordenada origen, int ancho, int alto){
		super(origen, ancho, alto);
	}
	
	public void mover(){
		setOrigenX(origen.getX()-VELOCIDAD);
	}

	public boolean puedoMover(Copter copter) {
		if(chocarCon(0, VELOCIDAD, copter.getR())){
			return false;
		}
		return true;
	}
}
