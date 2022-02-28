package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessPartida;
import chess.ChessPiece;
import chess.ChessPosicao;

public class Programa {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessPartida chessPartida = new ChessPartida();
		
		while(true) {
			try {
				UI.printBoard(chessPartida.getPieces());
				System.out.println();
				System.out.print("Origem: ");
				ChessPosicao origem = UI.leChessPosicao(sc);
				
				System.out.println();
				System.out.print("Destino: ");
				ChessPosicao destino = UI.leChessPosicao(sc);
				
				ChessPiece capturaPiece = chessPartida.performChessMove(origem, destino); 
			}catch(ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
	}
}