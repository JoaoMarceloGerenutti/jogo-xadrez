package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	public static void limparTela() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	
	public static ChessPosition lerPosicaoTabuleiro(Scanner sc) {
		try {
			String s = sc.nextLine();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new ChessPosition(coluna, linha);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Erro lendo posicao de xadrez. Posicoes validas sao de A1 a H8!");
		}
	}
	
	public static void imprimirPartida(ChessMatch partidaXadrez, List<ChessPiece> capturado) {
		imprimirTabuleiro(partidaXadrez.obterPecas());
		System.out.println();
		mostrarPecasCapturadas(capturado);
		System.out.println();
		System.out.println("Turno: " + partidaXadrez.getTurno());
		System.out.println("Esperando o jogador " + partidaXadrez.getJogadorAtual());
		if (partidaXadrez.getCheque()) {
			System.out.println("CHEQUE!");
		}
	}
	
	public static void imprimirTabuleiro(ChessPiece[][] pecas) {
		for (int i=0; i<pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j=0; j<pecas.length; j++) {
				imprimirPeca(pecas[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  A B C D E F G H");
	}
	
	public static void imprimirTabuleiro(ChessPiece[][] pecas, boolean[][] movimentosPossiveis) {
		for (int i=0; i<pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j=0; j<pecas.length; j++) {
				imprimirPeca(pecas[i][j], movimentosPossiveis[i][j]);
			}
			System.out.println();
		}
		System.out.println("  A B C D E F G H");
	}
	
	private static void imprimirPeca(ChessPiece peca, boolean fundoTela) {
		if (fundoTela) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (peca == null) {
			System.out.print("-" + ANSI_RESET);
		} else {
			if (peca.getCor() == Color.BRANCO) {
				System.out.print(ANSI_PURPLE + peca + ANSI_RESET);
			} else {
				System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}
	
	private static void mostrarPecasCapturadas(List<ChessPiece> capturado) {
		List<ChessPiece> branco = capturado.stream().filter(x -> x.getCor() == Color.BRANCO).collect(Collectors.toList());
		List<ChessPiece> preto = capturado.stream().filter(x -> x.getCor() == Color.PRETO).collect(Collectors.toList());
			
		System.out.println("Pecas capturadas:");
		System.out.print("Branca: ");
		System.out.print(ANSI_PURPLE);
		System.out.println(Arrays.toString(branco.toArray()));
		System.out.print(ANSI_RESET);
			
		System.out.print("Preta: ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(preto.toArray()));
		System.out.print(ANSI_RESET);
	}
	
}
