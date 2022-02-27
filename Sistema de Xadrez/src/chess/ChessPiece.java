package chess;

import boardgame.Piece;
import boardgame.Tabuleiro;

public class ChessPiece extends Piece{

	private Cor cor;

	public ChessPiece(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
}
