package chess;

import boardgame.Position;

public class ChessPosition {

	private char coluna;
	private int  linha;
	
	public ChessPosition(char coluna, int linha) {
		if (coluna < 'A' || coluna > 'H' || linha < 1 || linha > 8) {
			throw new ChessException("Erro ao instanciar PosiçãoTabuleiro. Valores válidos são de A1 a H8!");
		}
		this.coluna = coluna;
		this.linha = linha;
	}

	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}
	
	protected Position paraPosicao() {
		return new Position(8 - linha, coluna - 'A');
	}
	
	protected static ChessPosition daPosicao(Position posicao) {
		return new ChessPosition((char)('A' - posicao.getColuna()), 8 - posicao.getLinha());
	}
	
	@Override
	public String toString() {
		return "" + coluna + linha;
	}
}
