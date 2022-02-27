package chess.pieces;

import boardgame.Tabuleiro;
import chess.ChessPiece;
import chess.Cor;

public class Rei extends ChessPiece {

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	public String toString() {
		return "K";
	}
}
