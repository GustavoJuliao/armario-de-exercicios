package chess;

import boardgame.Posicao;

public class ChessPosicao {
	private char coluna;
	private int linha;

	public ChessPosicao(char coluna, int linha) {
		if(coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) throw new ChessException("Erro ao instanciar a posicao do xadrez. Valores válidos apenas entre A1 e H8.");
		this.coluna = coluna;
		this.linha = linha;
	}

	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}
	
	protected Posicao toPosicao() {
		return new Posicao( 8 - linha, coluna - 'a');
	}
	protected static ChessPosicao fromPosicao(Posicao posicao) {
		return new ChessPosicao((char)('a' - posicao.getColuna()), 8 - posicao.getLinha());
	}	
	
	@Override
	public String toString() {
		return "" + coluna + linha;
	}
	
}
