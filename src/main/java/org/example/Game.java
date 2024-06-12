package org.example;

import java.util.*;



public class Game {

    private Board board;
    List<Player> jugadores;
    boolean isturnoblanco;
    boolean fin;
    public Game(){
        board = new Board();
        jugadores = new LinkedList<>();
        isturnoblanco = true;
        fin = false;
        askPlayers();
        startGame();
        System.out.println(board.getKing(Piece.Color.WHITE)== null?"El ganador es : " + getJugadores().get(1):"El ganador es : " + getJugadores().get(0));
    }

    public void askPlayers(){
        //boolean fin = false;
        jugadores.add(new Player(Input.getString("Name of the Player1: ")));
        jugadores.add(new Player(Input.getString("Name of the Player2: ")));
        String anterior = null;
        jugadores.get(0).setColor(Input.getOptionWhiteBlack("Which color do you want to be Player1? "));
        if (jugadores.get(0).getColor().equals(Piece.Color.WHITE)){
            jugadores.get(1).setColor(Piece.Color.BLACK);
        } else {
            jugadores.get(1).setColor(Piece.Color.WHITE);
        }
    }

    public void startGame(){

        Player blancas = null;
        Optional optional = jugadores.stream().filter(player -> player.getColor().equals(Piece.Color.WHITE)).findFirst();
        if (optional.isPresent()){
            blancas = (Player) optional.get();
        }
        Player negras = null;
        optional = jugadores.stream().filter(player -> player.getColor().equals(Piece.Color.BLACK)).findFirst();
        if (optional.isPresent()){
            negras = (Player) optional.get();
        }
        while (!fin){
            if (isturnoblanco){
                turno(blancas);
                isturnoblanco = !isturnoblanco;
            } else {
                turno(negras);
                isturnoblanco = !isturnoblanco;
            }
        }
    }

    public void turno(Player player){
        System.out.println(player.getNombre() + " turn");

        Coordinate coordinate = null;
        Coordinate coordinateMoverse;
        Set<Coordinate> posibleMoves;
         do {
            coordinate = null;
            board.removeHighLight();
             System.out.println(board);
             if (board.getNextMovements(player.getColor()== Piece.Color.WHITE?Piece.Color.BLACK:Piece.Color.WHITE)
                     .contains(board.getKing(player.getColor()).getCell().getCoordinate())){
                 System.out.println("Esta en jaque: "+player.getNombre());
             }
             System.out.println("Wich piece do you wanna move?");
             while (!selectPieza(coordinate, player)){
                 coordinate = new Coordinate(Input.getString("Give me a valid letter(A-H) and the piece has to be yours: ").charAt(0),
                         Input.getInteger("Give me a valid number(1-8) and the piece has to be yours: "));
             }
              posibleMoves = board.getCellAt(coordinate).getPiece().getNextMovements();

                    board.highLight(posibleMoves);

             System.out.println(board);
             if (board.getNextMovements(player.getColor()== Piece.Color.WHITE?Piece.Color.BLACK:Piece.Color.WHITE)
                     .contains(board.getKing(player.getColor()).getCell().getCoordinate())){
                 System.out.println("Esta en jaque: "+player.getNombre());
             }

             System.out.println("Where do you wanna move that piece?");

                  coordinateMoverse = new Coordinate('0', 0);
                coordinateMoverse = new Coordinate(Input.getString("Give me a valid letter(A-H) Where your piece can move: ").charAt(0),
                     Input.getInteger("Give me a valid number(1-8) Where your piece can move: "));

         }while (!board.contains(coordinateMoverse) || !posibleMoves.contains(coordinateMoverse));

         if (!board.getCellAt(coordinateMoverse).isEmpty() &&
                 (board.getCellAt(coordinateMoverse).getPiece().getType() == Piece.Type.BLACK_KING
                         || board.getCellAt(coordinateMoverse).getPiece().getType() == Piece.Type.WHITE_KING )){
             fin = true;

         }

         board.getCellAt(coordinate).getPiece().moveTo(coordinateMoverse);
         board.removeHighLight();


        }


    public boolean selectPieza(Coordinate coordinate, Player player){


        return board.contains(coordinate) && !board.getCellAt(coordinate).isEmpty()
                && board.getCellAt(coordinate).getPiece().getColor().equals(player.getColor());
    }

    public Board getBoard() {
        return board;
    }
    public List<Player> getJugadores() {
        return jugadores;
    }
}
