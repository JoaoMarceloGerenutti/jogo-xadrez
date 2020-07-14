package application;

import chess.ChessMatch;

public class Program {

	public static void main(String[] args) {

		ChessMatch partidaXadrex = new ChessMatch();
		UI.imprimirTabuleiro(partidaXadrex.obterPecas());
	}

}
