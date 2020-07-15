package application;

import java.util.Scanner;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessMatch partidaXadrez = new ChessMatch();
		
		while (true) {
			UI.imprimirTabuleiro(partidaXadrez.obterPecas());
			System.out.println("-----------------");
			System.out.print("Origem: ");
			ChessPosition origem = UI.lerPosicaoTabuleiro(sc);
			
			System.out.println();
			System.out.print("Destino: ");
			ChessPosition destino = UI.lerPosicaoTabuleiro(sc);
			
			ChessPiece pecaCapturada = partidaXadrez.fazerMovimentoXadrez(origem, destino);
		}
	}

}
