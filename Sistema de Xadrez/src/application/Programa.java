package application;

import chess.ChessPartida;

public class Programa {

	public static void main(String[] args) {

		ChessPartida cp = new ChessPartida();

		UI.printBoard(cp.getPieces());


	}

}