package chess;

import boardgame.Board;
import chess.pieces.King;
import chess.pieces.Tower;

public class ChessMatch {

	private Board tabuleiro;
	
	public ChessMatch() {
		tabuleiro = new Board(8, 8);
		setupInicial();
	}
	
	public ChessPiece[][] obterPecas() {
		ChessPiece[][] matriz = new ChessPiece[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				matriz[i][j] = (ChessPiece) tabuleiro.peca(i, j);
			}
		}
		return matriz;
	}
	
	private void colocarNovaPeca(char coluna, int linha, ChessPiece peca) {
		tabuleiro.colocarPeca(peca, new ChessPosition(coluna, linha).paraPosicao());
	}
	
	private void setupInicial() {
		colocarNovaPeca('B', 6, new Tower(tabuleiro, Color.BRANCO));
		colocarNovaPeca('E', 8, new King(tabuleiro, Color.PRETO));
		colocarNovaPeca('E', 1, new King(tabuleiro, Color.BRANCO));
	}
}
