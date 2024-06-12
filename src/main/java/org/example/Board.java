package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Board {

    private Map<Coordinate, Cell> cells;
    private DeletedPieceManagerListImp piezasBorradas;

    public Board() {
        cells = new HashMap<>();

        for (int row = 1; row <= 8; row++)
            for (char col = 'A'; col <= 'H'; col++)
                cells.put(new Coordinate(col, row), new Cell(this, new Coordinate(col, row)));
        placePieces();
        piezasBorradas = new DeletedPieceManagerListImp(this);

    }
    public DeletedPieceManagerListImp getPiezasBorradas() {
        return piezasBorradas;
    }

    public Set<Coordinate> getNextMovements(Piece.Color piceColor){
        return cells.values().stream()
                .filter(cell -> !cell.isEmpty())
                .map(cell -> cell.getPiece())
                .filter(piece -> piece.getColor()==piceColor)
                .flatMap(piece -> piece.getNextMovements().stream())
                .collect(Collectors.toSet());
    }
    public Piece getKing(Piece.Color pieceColor){
        Optional optional = cells.values().stream()
                .filter(cell -> !cell.isEmpty())
                .map(cell -> cell.getPiece())
                .filter(piece -> piece instanceof King)
                .map(piece -> (King)piece)
                .filter(king -> king.getColor()==pieceColor)
                .findFirst();
        if (optional.isPresent()){
            return (King)optional.get();
        }
        return null;
    }
    public int countType(Piece.Type type){
        return (int)cells.values().stream()
                .filter(cell -> !cell.isEmpty())
                .filter(cell -> cell.getPiece().getType()==type)
                .count();
    }
    public void placePieces(){
        //Peones
        for (int i = 'A'; i <= 'H'; i++) {
            new Pawn(this, new Coordinate((char) i, 7), Pawn.Type.WHITE);
        }
        for (int i = 'A'; i <= 'H'; i++) {
            new Pawn(this, new Coordinate((char) i, 2), Pawn.Type.BLACK);
        }
        //Torres
        new Rook(this, new Coordinate('A', 1), Rook.Type.BLACK);
        new Rook(this, new Coordinate('H', 1), Rook.Type.BLACK);
        new Rook(this, new Coordinate('A', 8), Rook.Type.WHITE);
        new Rook(this, new Coordinate('H', 8), Rook.Type.WHITE);
        //Caballos
        new Knight(this, new Coordinate('B', 1), Knight.Type.BLACK);
        new Knight(this, new Coordinate('G', 1), Knight.Type.BLACK);
        new Knight(this, new Coordinate('B', 8), Knight.Type.WHITE);
        new Knight(this, new Coordinate('G', 8), Knight.Type.WHITE);
        //Alfiles
        new Bishop(this, new Coordinate('C', 1), Bishop.Type.BLACK);
        new Bishop(this, new Coordinate('F', 1), Bishop.Type.BLACK);
        new Bishop(this, new Coordinate('C', 8), Bishop.Type.WHITE);
        new Bishop(this, new Coordinate('F', 8), Bishop.Type.WHITE);
        //Reinas
        new Queen(this, new Coordinate('D', 1), Queen.Type.BLACK);
        new Queen(this, new Coordinate('D', 8), Queen.Type.WHITE);
        //Reyes
        new King(this, new Coordinate('E', 1), King.Type.BLACK);
        new King(this, new Coordinate('E', 8), King.Type.WHITE);


    }
    public boolean contains(Coordinate c) {
        return cells.containsKey(c);
    }

    public Cell getCellAt(Coordinate c) {
        return cells.get(c);
    }

    public void highLight(Collection<Coordinate> coordinates) {
        for (Coordinate c : coordinates)
            getCellAt(c).highlight();
    }

    public void removeHighLight() {
        cells.values().stream().forEach(cell -> cell.removeHighLight());
    }


    @Override
    public String toString() {
        String aux = "    A  B  C  D  E  F  G  H\n";

        for (int row = 1; row <= 8; row++) {
            aux += " " + row + " ";
            for (char col = 'A'; col <= 'H'; col++)
                aux += cells.get(new Coordinate(col, row));

            aux += " " + row + "\n";
        }
        aux += "    A  B  C  D  E  F  G  H\n\n";
        aux += piezasBorradas.toString();

        return aux;
    }
}
