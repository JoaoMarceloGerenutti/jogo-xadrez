package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Tower extends ChessPiece {

	public Tower(Board tabuleiro, Color cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "♖";
	}
}
