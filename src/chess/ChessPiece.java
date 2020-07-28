package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece{

	private Color cor;
	private int contadorMovimento;
	
	public ChessPiece(Board tabuleiro, Color cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Color getCor() {
		return cor;
	}
	
	public int getContadorMovimento() {
		return contadorMovimento;
	}
	
	public void aumentarContadorMovimento() {
		contadorMovimento++;
	}
	
	public void diminuirContadorMovimento() {
		contadorMovimento--;
	}
	
	public ChessPosition getPosicaoXadrez() {
		return ChessPosition.daPosicao(posicao);
	}
	
	protected boolean temUmaPecaInimiga(Position posicao) {
		ChessPiece p = (ChessPiece)getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}
	
}
