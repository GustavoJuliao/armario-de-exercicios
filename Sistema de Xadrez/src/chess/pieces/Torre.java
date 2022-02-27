package chess.pieces;

import boardgame.Tabuleiro;
import chess.ChessPiece;
import chess.Cor;

public class Torre extends ChessPiece{

	public Torre(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "T";
	}

}