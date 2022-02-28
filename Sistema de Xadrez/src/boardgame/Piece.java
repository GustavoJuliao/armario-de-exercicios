package boardgame;

public abstract class Piece {

	protected Posicao posicao;

	private Tabuleiro tabuleiro;

	public Piece(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	public abstract boolean[][] movPossivel();
	
	public boolean movPossivel(Posicao posicao){
		return movPossivel()[posicao.getLinha()][posicao.getColuna()];
	}
	
	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = movPossivel();
		for(int i = 0;i < mat.length; i++) {
			for(int j = 0; j < mat.length; j++) {
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}


}