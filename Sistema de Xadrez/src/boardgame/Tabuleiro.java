package boardgame;

public class Tabuleiro {

	private int linhas, colunas;
	private Piece[][] pieces;


	public Tabuleiro(int linhas, int colunas) {
		if(linhas <1 || colunas < 1) {
			throw new TabuleiroException("Erro ao criar o tabuleiro: é necessário que pelo menos tenha uma linha e uma coluna.");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pieces = new Piece[linhas][colunas];
	}


	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}	

	public Piece piece(int linha, int coluna) {
		if(!posicaoExiste(linha,coluna)) {
			throw new TabuleiroException("Posicao não está no tabuleiro");
		}
		return pieces[linha][coluna];
	}

	public Piece piece(Posicao posicao) {
		if(!posicaoExiste(posicao)) {
			throw new TabuleiroException("Posicao não está no tabuleiro");
		}
		return pieces[posicao.getLinha()][posicao.getColuna()];
	}


	public void placePiece(Piece piece, Posicao posicao) {
		if(thereIsAPiece(posicao)) {
			throw new TabuleiroException("Posicao já está ocupada "+ posicao);
		}
		pieces[posicao.getLinha()][posicao.getColuna()] = piece;
		piece.posicao = posicao;
	}

	private boolean posicaoExiste(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;		
	}

	public boolean posicaoExiste(Posicao posicao) {
		return posicaoExiste(posicao.getLinha(),posicao.getColuna());
	}

	public boolean thereIsAPiece(Posicao posicao) {
		if(!posicaoExiste(posicao)) {
			throw new TabuleiroException("Posicao não está no tabuleiro");
		}
		return piece(posicao) != null;
	}



}