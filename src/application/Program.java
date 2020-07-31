package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessMatch partidaXadrez = new ChessMatch();
		List<ChessPiece> capturado = new ArrayList<>();
		
		while (!partidaXadrez.getChequeMate()) {
			try {
				UI.limparTela();
				UI.imprimirPartida(partidaXadrez, capturado);
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
				
				if (pecaCapturada != null) {
					capturado.add(pecaCapturada);
				}
				
				if (partidaXadrez.getPromovido() != null) {
					System.out.print("Digite a letra para promocao (B/N/T/Q): ");
					String tipo = sc.nextLine().toUpperCase();
					while (!tipo.equals("B") && !tipo.equals("N") && !tipo.equals("T") && !tipo.equals("Q")) {
						System.out.print("Valor invalido! Entre com a letra para promocao (B/N/T/Q): ");
						tipo = sc.nextLine().toUpperCase();
					}
					partidaXadrez.recolocarPecaPromovida(tipo);
				}
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.limparTela();
		UI.imprimirPartida(partidaXadrez, capturado);
	}

}
