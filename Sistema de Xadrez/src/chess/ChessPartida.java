package chess;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.pieces.Rei;
import chess.pieces.Torre;

public class ChessPartida {

	private Tabuleiro tabuleiro;


	public ChessPartida() {
		tabuleiro = new Tabuleiro(8,8);
		initialSetup();
	}

	public ChessPiece[][] getPieces(){
		ChessPiece[][] mat = new ChessPiece[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for(int i=0; i<tabuleiro.getLinhas(); i++) {
			for(int j=0; j<tabuleiro.getColunas(); j++) {
				mat[i][j] = (ChessPiece) tabuleiro.piece(i, j); // feito um downcast para reconher que é uma peça de xadrez
			}
		}
		return mat;
	}

	private void initialSetup() {
		tabuleiro.placePiece(new Torre(tabuleiro, Cor.WHITE), new Posicao(2,1));
		tabuleiro.placePiece(new Rei(tabuleiro, Cor.BLACK), new Posicao(0,4));
		tabuleiro.placePiece(new Rei(tabuleiro, Cor.WHITE), new Posicao(7,4));
	}


}