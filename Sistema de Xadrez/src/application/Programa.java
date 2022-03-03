package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessPartida;
import chess.ChessPiece;
import chess.ChessPosicao;

public class Programa {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessPartida chessPartida = new ChessPartida();
		
		List<ChessPiece> capturado = new ArrayList<>();
		
		while(!chessPartida.getCheckMate()) {
			try {
				UI.clearScreen();
				UI.printMatch(chessPartida, capturado);
				System.out.println();
				System.out.print("Origem: ");
				ChessPosicao origem = UI.leChessPosicao(sc);
				
				boolean[][] movPossivel = chessPartida.movPossivel(origem);
				UI.clearScreen();
				UI.printBoard(chessPartida.getPieces(), movPossivel);
				
				System.out.println();
				System.out.print("Destino: ");
				ChessPosicao destino = UI.leChessPosicao(sc);
				
				ChessPiece capturaPiece = chessPartida.performChessMove(origem, destino); 
				
				if (capturaPiece != null) {
					capturado.add(capturaPiece);
				}
			}catch(ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.clearScreen();
		UI.printMatch(chessPartida, capturado);
	}
}