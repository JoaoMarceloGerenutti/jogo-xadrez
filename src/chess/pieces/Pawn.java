package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board tabuleiro, Color cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "♙";
	}
}
