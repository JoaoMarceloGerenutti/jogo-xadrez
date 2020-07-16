package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessMatch partidaXadrez = new ChessMatch();
		
		while (true) {
			try {
				UI.limparTela();
				UI.imprimirPartida(partidaXadrez);
				System.out.println("--------------------------");
				System.out.print("Origem: ");
				ChessPosition origem = UI.lerPosicaoTabuleiro(sc);
				
				boolean[][] movimentosPossiveis = partidaXadrez.movimentosPossiveis(origem);
				UI.limparTela();
				UI.imprimirTabuleiro(partidaXadrez.obterPecas(), movimentosPossiveis);
				System.out.println("--------------------------");
				System.out.println();
				System.out.print("Destino: ");
				ChessPosition destino = UI.lerPosicaoTabuleiro(sc);
				
				ChessPiece pecaCapturada = partidaXadrez.fazerMovimentoXadrez(origem, destino);
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
	}

}
