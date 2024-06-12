package org.example;

public class Player {
    private String nombre;
    private Piece.Color color;

    public Player(String nombre){
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Piece.Color getColor() {
        return color;
    }
    public void setColor(Piece.Color color) {
        this.color = color;
    }

    @Override
    public String toString(){
        return nombre;
    }
}
