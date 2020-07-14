package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Tower;

public class ChessMatch {

	private Board tabuleiro;
	
	public ChessMatch() {
		tabuleiro = new Board(8, 8);
		setupInicial();
	}
	
	public ChessPiece[][] obterPecas() {
		ChessPiece[][] matriz = new ChessPiece[tabuleiro.getLinha()][tabuleiro.getColuna()];
		for (int i=0; i<tabuleiro.getLinha(); i++) {
			for (int j=0; j<tabuleiro.getColuna(); j++) {
				matriz[i][j] = (ChessPiece) tabuleiro.peca(i, j);
			}
		}
		return matriz;
	}
	
	private void setupInicial() {
		tabuleiro.colocarPeca(new Tower(tabuleiro, Color.BRANCO), new Position(2, 1));
		tabuleiro.colocarPeca(new King(tabuleiro, Color.PRETO), new Position(0, 4));
		tabuleiro.colocarPeca(new King(tabuleiro, Color.BRANCO), new Position(7, 4));
	}
}
