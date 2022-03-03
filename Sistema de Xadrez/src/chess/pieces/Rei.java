package chess.pieces;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.ChessPartida;
import chess.ChessPiece;
import chess.Cor;

public class Rei extends ChessPiece {

	
	private ChessPartida chessPartida;
	
	public Rei(Tabuleiro tabuleiro, Cor cor, ChessPartida chessPartida) {
		super(tabuleiro, cor);
		this.chessPartida = chessPartida;
	}
	

	@Override
	public String toString() {
		return "K";
	}
	
	private boolean podeMover(Posicao posicao) {
		ChessPiece p = (ChessPiece)getTabuleiro().piece(posicao);
		return p == null || p.getCor() != getCor();
	}
	
	private boolean testRookCastling(Posicao posicao) {
		ChessPiece p = (ChessPiece)getTabuleiro().piece(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getMoveCount() == 0;
	}
	
	
	@Override
	public boolean[][] movPossivel(){
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);

		// above
		p.setValues(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// below
		p.setValues(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// left
		p.setValues(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// right
		p.setValues(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// nw
		p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// ne
		p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// sw
		p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// se
		p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// #specialmove castling
				if (getMoveCount() == 0 && !chessPartida.getCheck()) {
					// #specialmove castling kingside rook
					Posicao posT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
					if (testRookCastling(posT1)) {
						Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
						Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
						if (getTabuleiro().piece(p1) == null && getTabuleiro().piece(p2) == null) {
							mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
						}
					}
					// #specialmove castling queenside rook
					Posicao posT2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
					if (testRookCastling(posT2)) {
						Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
						Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
						Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
						if (getTabuleiro().piece(p1) == null && getTabuleiro().piece(p2) == null && getTabuleiro().piece(p3) == null) {
							mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
						}
					}
				}

		
		return mat;
	}
}