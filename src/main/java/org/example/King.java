package org.example;

import java.util.HashSet;
import java.util.Set;

public class King extends Piece {

    public King(Board board, Coordinate position, Type type) {
        super(type.getType(), !(board ==null) ?board.getCellAt(position):null);
    }

    @Override
    public Set<Coordinate> getNextMovements() {

        Coordinate position = getCell().getCoordinate();
        Set<Coordinate> nextMovements = new HashSet<>();

        if(getCell()==null) return nextMovements;

        if (canAddToNextMovements(position.up()))
            nextMovements.add(position.up());

        if (canAddToNextMovements(position.up().right()))
            nextMovements.add(position.up().right());

        if (canAddToNextMovements(position.right()))
            nextMovements.add(position.right());

        if (canAddToNextMovements(position.down().right()))
            nextMovements.add(position.down().right());

        if (canAddToNextMovements(position.down()))
            nextMovements.add(position.down());

        if (canAddToNextMovements(position.down().left()))
            nextMovements.add(position.down().left());

        if (canAddToNextMovements(position.left()))
            nextMovements.add(position.left());

        if (canAddToNextMovements(position.up().left()))
            nextMovements.add(position.up().left());

        return nextMovements;
    }

    public boolean check(){
        Board tablero = this.getCell().getBoard();
        Piece.Color colorRival = Piece.Color.WHITE;
        if (this.getColor()== Piece.Color.WHITE){
            colorRival = Piece.Color.BLACK;
        }
        Set<Coordinate> positionsColors = tablero.getNextMovements(colorRival);
        if (positionsColors.contains(this.getCell().getCoordinate())){
            return true;
        }
        return false;
    }
    private void checkCoordinate(Coordinate coordinate, Set<Coordinate> nextMovements) {

        if (getCell() == null) return;

        Board board = getCell().getBoard();

        if (!board.contains(coordinate)) return;

        if (board.getCellAt(coordinate).isEmpty() || board.getCellAt(coordinate).getPiece().getColor() != getColor())
            nextMovements.add(coordinate);

    }

    public enum Type {
        BLACK(Piece.Type.BLACK_KING), WHITE(Piece.Type.WHITE_KING);
        private Piece.Type type;

        Type(Piece.Type type) {
            this.type = type;
        }

        public Piece.Type getType() {
            return type;
        }
    }
}
