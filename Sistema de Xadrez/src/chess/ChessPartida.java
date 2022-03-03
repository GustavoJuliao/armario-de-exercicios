package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Piece;
import boardgame.Posicao;
import boardgame.Tabuleiro;
import chess.pieces.Bispo;
import chess.pieces.Cavalo;
import chess.pieces.Peao;
import chess.pieces.Rainha;
import chess.pieces.Rei;
import chess.pieces.Torre;

public class ChessPartida {

	private int turno;
	private Cor currentPlayer;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	
	private List<Piece> pecasNaMesa = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	

	public ChessPartida() {
		tabuleiro = new Tabuleiro(8,8);
		turno = 1;
		currentPlayer = Cor.WHITE;
		initialSetup();
	}
	
	public int getTurno() {
		return turno;
	}

	public Cor getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}
	
	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
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
	
	
	public boolean[][] movPossivel(ChessPosicao posOrigem) {
		Posicao posicao = posOrigem.toPosicao();
		validarPosOrigem(posicao);
		return tabuleiro.piece(posicao).movPossivel();
	}
	
	
	public ChessPiece performChessMove(ChessPosicao posOrigem, ChessPosicao posDestino) {
		Posicao origem = posOrigem.toPosicao();
		Posicao destino = posDestino.toPosicao();
		validarPosOrigem(origem);
		validarPosDestino(origem, destino);
		Piece capturaPiece = fazerJogada(origem, destino);
		if (testCheck(currentPlayer)) {
			undoMove(origem, destino, capturaPiece);
			throw new ChessException("You can't put yourself in check");
		}

		ChessPiece movedPiece = (ChessPiece)tabuleiro.piece(destino);
		check = (testCheck(oponente(currentPlayer))) ? true : false;
		if (testCheckMate(oponente(currentPlayer))) {
			checkMate = true;
		}
		else {
			proxTurno();
		}

		if (movedPiece instanceof Peao && (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)) {
			enPassantVulnerable = movedPiece;
		}
		else {
			enPassantVulnerable = null;
		}
		
		return (ChessPiece)capturaPiece;
	}
	
	private Piece fazerJogada(Posicao origem, Posicao destino) {
		ChessPiece p = (ChessPiece)tabuleiro.removePiece(origem);
		p.increaseMoveCount();
		Piece capturaPiece = tabuleiro.removePiece(destino);
		tabuleiro.placePiece(p, destino);
		if (capturaPiece != null) {
			pecasNaMesa.remove(capturaPiece);
			capturedPieces.add(capturaPiece);
		}
		// #movimento especial de troca entre torre e rei
		if(p instanceof Rei  && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(destino.getLinha(), destino.getColuna() + 1);
			ChessPiece rook = (ChessPiece)tabuleiro.removePiece(origemT);
			tabuleiro.placePiece(rook, destinoT);
			rook.increaseMoveCount();
		}
		// #movimento especial de troca entre torre e rainha
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(destino.getLinha(), destino.getColuna() - 1);
			ChessPiece rook = (ChessPiece)tabuleiro.removePiece(origemT);
			tabuleiro.placePiece(rook, destinoT);
			rook.increaseMoveCount();
		}
		
		// #specialmove en passant
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && capturaPiece == null) {
				Posicao pawnPosicao;
				if (p.getCor() == Cor.WHITE) {
					pawnPosicao = new Posicao(destino.getLinha() + 1, destino.getColuna());
				}
				else {
					pawnPosicao = new Posicao(destino.getLinha() - 1, destino.getColuna());
				}
				capturaPiece = tabuleiro.removePiece(pawnPosicao);
				capturedPieces.add(capturaPiece);
				pecasNaMesa.remove(capturaPiece);
			}
		}		
		return capturaPiece;
	}
	
	private void undoMove(Posicao origem, Posicao destino, Piece pieceCapturada) {
		Piece p = tabuleiro.removePiece(destino);
		tabuleiro.placePiece(p, origem);

		if (pieceCapturada != null) {
			tabuleiro.placePiece(pieceCapturada, destino);
			capturedPieces.remove(pieceCapturada);
			pecasNaMesa.add(pieceCapturada);
		}
		if(p instanceof Rei  && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(destino.getLinha(), destino.getColuna() + 1);
			ChessPiece rook = (ChessPiece)tabuleiro.removePiece(origemT);
			tabuleiro.placePiece(rook, destinoT);
			rook.increaseMoveCount();
		}
		// #movimento especial de troca entre torre e rainha
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(destino.getLinha(), destino.getColuna() - 1);
			ChessPiece rook = (ChessPiece)tabuleiro.removePiece(origemT);
			tabuleiro.placePiece(rook, destinoT);
			rook.increaseMoveCount();
		}
		// specialmove en passant
		/*if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pieceCapturada == enPassantVulnerable) {
				ChessPiece peao=  (ChessPiece)tabuleiro.removePiece(destino);
				Posicao peaoPosicao;
				if (p.getCor() == Cor.WHITE) {
					peaoPosicao = new Posicao(3, destino.getColuna());
				}
				else {
					peaoPosicao = new Posicao(destino.getLinha() - 1, destino.getColuna());
				}
				pieceCapturada = tabuleiro.removePiece(peaoPosicao);
				capturedPieces.add(capturaPiece);
				}
		}*/
	}
	
	private void validarPosOrigem(Posicao posicao) {
		if(!tabuleiro.thereIsAPiece(posicao)) {
			throw new ChessException("Nao existe posicao no local de origem.");
		}
		if (currentPlayer != ((ChessPiece)tabuleiro.piece(posicao)).getCor()) {
			throw new ChessException("A peca escolhida nao é sua.");
		}
		if(!tabuleiro.piece(posicao).isThereAnyPossibleMove()) throw new ChessException("Não existe movimento possivel para Peça escolhida.");
	}
	
	private void validarPosDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.piece(origem).movPossivel(destino)) {
			throw new ChessException("A posicao escolhida não pode se mover para a posicao escolhida");
		}
	}
		

	private void proxTurno() {
		turno++;
		currentPlayer = (currentPlayer == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;
	}

	private Cor oponente(Cor color) {
		return (color == Cor.WHITE) ? Cor.BLACK : Cor.WHITE;
	}

	private ChessPiece rei(Cor cor) {
		List<Piece> list = pecasNaMesa.stream().filter(x -> ((ChessPiece)x).getCor() == cor).collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof Rei) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("There is no " + cor + " king on the board");
	}

	private boolean testCheck(Cor cor) {
		Posicao posicaoRei = rei(cor).getChessPosicao().toPosicao();
		List<Piece> opponentPieces = pecasNaMesa.stream().filter(x -> ((ChessPiece)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.movPossivel();
			if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Cor cor) {
		if (!testCheck(cor)) {
			return false;
		}
		List<Piece> list = pecasNaMesa.stream().filter(x -> ((ChessPiece)x).getCor() == cor).collect(Collectors.toList());
		for (Piece p : list) {
			boolean[][] mat = p.movPossivel();
			for (int i=0; i<tabuleiro.getLinhas(); i++) {
				for (int j=0; j<tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((ChessPiece)p).getChessPosicao().toPosicao();
						Posicao destino = new Posicao(i, j);
						Piece pieceCapturada = fazerJogada(origem, destino);
						boolean testCheck = testCheck(cor);
						undoMove(origem, destino, pieceCapturada);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}	
	
	private void placeNovoLugar(char coluna, int linha, ChessPiece piece) {
		tabuleiro.placePiece(piece, new ChessPosicao(coluna, linha).toPosicao());
		pecasNaMesa.add(piece);
	}
	
	private void initialSetup() {
		placeNovoLugar('a', 1, new Torre(tabuleiro, Cor.WHITE));
		placeNovoLugar('b', 1, new Cavalo(tabuleiro, Cor.WHITE));
		placeNovoLugar('h', 1, new Torre(tabuleiro, Cor.WHITE));
		placeNovoLugar('e', 1, new Rei(tabuleiro, Cor.WHITE, this));
		placeNovoLugar('d', 1, new Rainha(tabuleiro, Cor.WHITE));
		placeNovoLugar('a', 2, new Peao(tabuleiro, Cor.WHITE, this));
        placeNovoLugar('b', 2, new Peao(tabuleiro, Cor.WHITE, this));
        placeNovoLugar('c', 2, new Peao(tabuleiro, Cor.WHITE, this));
        placeNovoLugar('d', 2, new Peao(tabuleiro, Cor.WHITE, this));
        placeNovoLugar('e', 2, new Peao(tabuleiro, Cor.WHITE, this));
        placeNovoLugar('f', 2, new Peao(tabuleiro, Cor.WHITE, this));
        placeNovoLugar('g', 1, new Cavalo(tabuleiro, Cor.WHITE));
        placeNovoLugar('g', 2, new Peao(tabuleiro, Cor.WHITE, this));
        placeNovoLugar('h', 2, new Peao(tabuleiro, Cor.WHITE, this));
        
        
        placeNovoLugar('a', 8, new Torre(tabuleiro, Cor.BLACK));
        placeNovoLugar('b', 8, new Cavalo(tabuleiro, Cor.BLACK));
        placeNovoLugar('c', 8, new Bispo(tabuleiro, Cor.BLACK));
        placeNovoLugar('e', 8, new Rei(tabuleiro, Cor.BLACK, this));
        placeNovoLugar('d', 8, new Rainha(tabuleiro, Cor.BLACK));
        placeNovoLugar('f', 8, new Bispo(tabuleiro, Cor.BLACK));
        placeNovoLugar('g', 8, new Cavalo(tabuleiro, Cor.BLACK));
        placeNovoLugar('h', 8, new Torre(tabuleiro, Cor.BLACK));
        placeNovoLugar('a', 7, new Peao(tabuleiro, Cor.BLACK, this));
        placeNovoLugar('b', 7, new Peao(tabuleiro, Cor.BLACK, this));
        placeNovoLugar('c', 7, new Peao(tabuleiro, Cor.BLACK, this));
        placeNovoLugar('d', 7, new Peao(tabuleiro, Cor.BLACK, this));
        placeNovoLugar('e', 7, new Peao(tabuleiro, Cor.BLACK, this));
        placeNovoLugar('f', 7, new Peao(tabuleiro, Cor.BLACK, this));
        placeNovoLugar('g', 7, new Peao(tabuleiro, Cor.BLACK, this));
        placeNovoLugar('h', 7, new Peao(tabuleiro, Cor.BLACK, this));
        }


}