package chess;

import boardgame.Piece;
import boardgame.Posicao;
import boardgame.Tabuleiro;

public abstract class ChessPiece extends Piece{

	private Cor cor;
	private int moveCount;
	
	public ChessPiece(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public int getMoveCount() {
		return moveCount;
	}

	public void increaseMoveCount() {
		moveCount++;
	}

	public void decreaseMoveCount() {
		moveCount--;
	}
	
	public ChessPosicao getChessPosicao() {
		return ChessPosicao.fromPosicao(posicao);
	}
	
	protected boolean isThereOpponentPiece(Posicao posicao) {
		ChessPiece p = (ChessPiece)getTabuleiro().piece(posicao);
		return p != null && p.getCor() != cor;
	}

}