package boardgame;

import chess.ChessPosicao;

public class Posicao {

	private int linha;
	private int coluna;
	public Posicao(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	public int getLinha() {
		return linha;
	}
	public void setLinha(int linha) {
		this.linha = linha;
	}
	public int getColuna() {
		return coluna;
	}
	public void getColuna(int coluna) {
		this.coluna = coluna;
	}
	public void setValues(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}

	protected static ChessPosicao fromPosition(Posicao posicao) {
		return new ChessPosicao((char)('a' + posicao.getColuna()), 8 - posicao.getLinha());
	}

	@Override
	public String toString() {
		return linha + ", " + coluna;
	}

}