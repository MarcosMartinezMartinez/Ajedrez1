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
        jugadores.add(new Player(Input.getString("Nombre del Jugador1: ")));
        jugadores.add(new Player(Input.getString("Nombre del Jugador2: ")));
        String anterior = null;
        jugadores.get(0).setColor(Input.getOptionWhiteBlack("¿De qué color quieres que sea el Jugador 1?(Blak/white) "));
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
        System.out.println(player.getNombre() + " turno");

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
             System.out.println("¿Qué pieza quieres mover?");
             while (!selectPieza(coordinate, player)){
                 coordinate = new Coordinate(Input.getString("Dame una letra válida (A-H) y la pieza tiene que ser tuya: ").charAt(0),
                         Input.getInteger("Dame un número válido (1-8) y la pieza tiene que ser tuya: "));
             }
              posibleMoves = board.getCellAt(coordinate).getPiece().getNextMovements();

                    board.highLight(posibleMoves);

             System.out.println(board);
             if (board.getNextMovements(player.getColor()== Piece.Color.WHITE?Piece.Color.BLACK:Piece.Color.WHITE)
                     .contains(board.getKing(player.getColor()).getCell().getCoordinate())){
                 System.out.println("Esta en jaque: "+player.getNombre());
             }

             System.out.println("¿Dónde quieres mover esa pieza?");

                  coordinateMoverse = new Coordinate('0', 0);
                coordinateMoverse = new Coordinate(Input.getString("Dame una letra válida (A-H) donde tu pieza puede moverse: ").charAt(0),
                     Input.getInteger("Dame un número válido (1-8) donde tu pieza puede moverse: "));

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
