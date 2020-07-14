package chess;

import boardgame.Board;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
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
		//PEÇAS PRETAS ATRAS
		colocarNovaPeca('A', 8, new Tower(tabuleiro, Color.PRETO));
		colocarNovaPeca('B', 8, new Knight(tabuleiro, Color.PRETO));
		colocarNovaPeca('C', 8, new Bishop(tabuleiro, Color.PRETO));
		colocarNovaPeca('E', 8, new Queen(tabuleiro, Color.PRETO));
		colocarNovaPeca('D', 8, new King(tabuleiro, Color.PRETO));
		colocarNovaPeca('F', 8, new Bishop(tabuleiro, Color.PRETO));
		colocarNovaPeca('G', 8, new Knight(tabuleiro, Color.PRETO));
		colocarNovaPeca('H', 8, new Tower(tabuleiro, Color.PRETO));
		//PEÇAS PRETAS FRENTE
		colocarNovaPeca('A', 7, new Pawn(tabuleiro, Color.PRETO));
		colocarNovaPeca('B', 7, new Pawn(tabuleiro, Color.PRETO));
		colocarNovaPeca('C', 7, new Pawn(tabuleiro, Color.PRETO));
		colocarNovaPeca('E', 7, new Pawn(tabuleiro, Color.PRETO));
		colocarNovaPeca('D', 7, new Pawn(tabuleiro, Color.PRETO));
		colocarNovaPeca('F', 7, new Pawn(tabuleiro, Color.PRETO));
		colocarNovaPeca('G', 7, new Pawn(tabuleiro, Color.PRETO));
		colocarNovaPeca('H', 7, new Pawn(tabuleiro, Color.PRETO));
		
		//PEÇAS BRANCAS
		colocarNovaPeca('A', 1, new Tower(tabuleiro, Color.BRANCO));
		colocarNovaPeca('B', 1, new Knight(tabuleiro, Color.BRANCO));
		colocarNovaPeca('C', 1, new Bishop(tabuleiro, Color.BRANCO));
		colocarNovaPeca('D', 1, new King(tabuleiro, Color.BRANCO));
		colocarNovaPeca('E', 1, new Queen(tabuleiro, Color.BRANCO));
		colocarNovaPeca('F', 1, new Bishop(tabuleiro, Color.BRANCO));
		colocarNovaPeca('G', 1, new Knight(tabuleiro, Color.BRANCO));
		colocarNovaPeca('H', 1, new Tower(tabuleiro, Color.BRANCO));
		//PEÇAS BRANCAS FRENTE
		colocarNovaPeca('A', 2, new Pawn(tabuleiro, Color.BRANCO));
		colocarNovaPeca('B', 2, new Pawn(tabuleiro, Color.BRANCO));
		colocarNovaPeca('C', 2, new Pawn(tabuleiro, Color.BRANCO));
		colocarNovaPeca('D', 2, new Pawn(tabuleiro, Color.BRANCO));
		colocarNovaPeca('E', 2, new Pawn(tabuleiro, Color.BRANCO));
		colocarNovaPeca('F', 2, new Pawn(tabuleiro, Color.BRANCO));
		colocarNovaPeca('G', 2, new Pawn(tabuleiro, Color.BRANCO));
		colocarNovaPeca('H', 2, new Pawn(tabuleiro, Color.BRANCO));
	}
}
