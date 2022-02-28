package chess;

import boardgame.Piece;
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
	
	public ChessPiece performChessMove(ChessPosicao posOrigem, ChessPosicao posDestino) {
		Posicao origem = posOrigem.toPosicao();
		Posicao destino = posDestino.toPosicao();
		validarPosOrigem(origem);
		Piece capturaPiece = fazerJogada(origem, destino);
		return (ChessPiece)capturaPiece;
	}
	
	private void validarPosOrigem(Posicao posicao) {
		if(!tabuleiro.thereIsAPiece(posicao)) {
			throw new ChessException("Nao existe posicao no local de origem.");
		}
		if(!tabuleiro.piece(posicao).isThereAnyPossibleMove()) throw new ChessException("Não existe movimento possivel para Peça escolhida.");
	}
	
	private Piece fazerJogada(Posicao origem, Posicao destino) {
		Piece p = tabuleiro.removePiece(origem);
		Piece capturaPiece = tabuleiro.removePiece(destino);
		tabuleiro.placePiece(p, destino);
		return capturaPiece;
	}
	

	private void placeNovoLugar(char coluna, int linha, ChessPiece piece) {
		tabuleiro.placePiece(piece, new ChessPosicao(coluna, linha).toPosicao());
	}
	
	private void initialSetup() {
		placeNovoLugar('b', 6, new Torre(tabuleiro, Cor.WHITE));
		placeNovoLugar('e', 8, new Rei(tabuleiro, Cor.BLACK));
		placeNovoLugar('e', 1, new Rei(tabuleiro, Cor.WHITE));
		placeNovoLugar('c', 1, new Torre(tabuleiro, Cor.WHITE));
        placeNovoLugar('c', 2, new Torre(tabuleiro, Cor.WHITE));
        placeNovoLugar('d', 2, new Torre(tabuleiro, Cor.WHITE));
        placeNovoLugar('e', 2, new Torre(tabuleiro, Cor.WHITE));
        placeNovoLugar('d', 1, new Rei(tabuleiro, Cor.WHITE));

        placeNovoLugar('c', 7, new Torre(tabuleiro, Cor.BLACK));
        placeNovoLugar('c', 8, new Torre(tabuleiro, Cor.BLACK));
        placeNovoLugar('d', 7, new Torre(tabuleiro, Cor.BLACK));
        placeNovoLugar('e', 7, new Torre(tabuleiro, Cor.BLACK));
        placeNovoLugar('d', 8, new Rei(tabuleiro, Cor.BLACK));
        }


}