package org.example;

import com.diogonunes.jcolor.Attribute;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.diogonunes.jcolor.Ansi.colorize;

public class DeletedPieceManagerListImp implements IDeletedPieceManager{

    private Board board;

    List<Piece> pieceList;

    public DeletedPieceManagerListImp(Board board){
        this.board = board;
        pieceList = new ArrayList<>();
    }

    @Override
    public String toString(){
        String aux;
        Set<Piece> piezas = new LinkedHashSet<>();
        piezas.add(new Pawn(null,null, Pawn.Type.WHITE));
        piezas.add(new Rook(null,null,Rook.Type.WHITE));
        piezas.add(new Knight(null,null,Knight.Type.WHITE));
        piezas.add(new Bishop(null,null,Bishop.Type.WHITE));
        piezas.add(new Queen(null,null,Queen.Type.WHITE));
        piezas.add(new King(null,null,King.Type.WHITE));
        piezas.add(new Pawn(null,null,Pawn.Type.BLACK));
        piezas.add(new Rook(null,null,Rook.Type.BLACK));
        piezas.add(new Knight(null,null,Knight.Type.BLACK));
        piezas.add(new Bishop(null,null,Bishop.Type.BLACK));
        piezas.add(new Queen(null,null,Queen.Type.BLACK));
        piezas.add(new King(null,null,King.Type.BLACK));

        aux = "         REMAINING PIECES         \n";
        for (Piece pieza: piezas) {
            aux += colorize(" ", Attribute.BACK_COLOR(100,100,100)) + colorize(pieza.toString(), Attribute.BACK_COLOR(100,100,100)) + colorize(" ", Attribute.BACK_COLOR(100,100,100));
        }
        aux += "\n";
        for (Piece pieza: piezas){
            aux += colorize(" ", Attribute.BACK_COLOR(180,180,180)) + colorize(board.countType(pieza.getType()) + "", Attribute.BACK_COLOR(180,180,180), Attribute.TEXT_COLOR(0,0,0)) + colorize(" ", Attribute.BACK_COLOR(180,180,180));
        }
        aux += "\n\n";

        aux += "         DELETED PIECES         \n";

        for (Piece pieza: piezas) {
            aux += colorize(" ", Attribute.BACK_COLOR(100,100,100)) + colorize(pieza.toString(), Attribute.BACK_COLOR(100,100,100)) + colorize(" ", Attribute.BACK_COLOR(100,100,100));
        }
        aux += "\n";
        for (Piece pieza: piezas){
            aux += colorize(" ", Attribute.BACK_COLOR(180,180,180)) + colorize(count(pieza.getType()) + "", Attribute.BACK_COLOR(180,180,180), Attribute.TEXT_COLOR(0,0,0)) + colorize(" ", Attribute.BACK_COLOR(180,180,180));
        }

        return aux;


    }

    @Override
    public void addPiece(Piece piece) {
        pieceList.add(piece);
    }

    @Override
    public Piece removeLast() {
        Piece piece;

        if (pieceList==null){
            return null;
        }
        piece = pieceList.get(pieceList.size()-1);
        pieceList.remove(piece);
        return piece;
    }

    @Override
    public int count(Piece.Type pieceType) {
        int contador = 0;
        if (pieceList==null){
            return 0;
        }
        for (Piece piece:pieceList) {
            if (piece.getType()==pieceType){
                contador++;
            }
        }
        return contador;
    }

}
