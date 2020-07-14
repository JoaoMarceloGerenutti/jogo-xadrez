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
		colocarNovaPeca('C', 1, new Tower(tabuleiro, Color.BRANCO));
		colocarNovaPeca('C', 2, new Tower(tabuleiro, Color.BRANCO));
		colocarNovaPeca('D', 2, new Tower(tabuleiro, Color.BRANCO));
		colocarNovaPeca('E', 2, new Tower(tabuleiro, Color.BRANCO));
		colocarNovaPeca('E', 1, new Tower(tabuleiro, Color.BRANCO));
		colocarNovaPeca('D', 1, new King(tabuleiro, Color.BRANCO));

		colocarNovaPeca('C', 7, new Tower(tabuleiro, Color.PRETO));
		colocarNovaPeca('C', 8, new Tower(tabuleiro, Color.PRETO));
		colocarNovaPeca('D', 7, new Tower(tabuleiro, Color.PRETO));
		colocarNovaPeca('E', 7, new Tower(tabuleiro, Color.PRETO));
		colocarNovaPeca('E', 8, new Tower(tabuleiro, Color.PRETO));
		colocarNovaPeca('D', 8, new King(tabuleiro, Color.PRETO));
	}
}
