package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	private ChessMatch partidaXadrez;
	
	public King(Board tabuleiro, Color cor, ChessMatch partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
	}

	private boolean podeMover(Position posicao) {
		ChessPiece p = (ChessPiece)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}
	
	private boolean testeTorreRogue(Position posicao) {
		ChessPiece p = (ChessPiece)getTabuleiro().peca(posicao);
		return p != null && p instanceof Tower && p.getCor() == getCor() && p.getContadorMovimento() == 0;
	}
	
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Position p = new Position(0, 0);
		
		//ACIMA
		p.setarValores(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//BAIXO
		p.setarValores(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//ESQUERDA
		p.setarValores(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//ESQUERDA
		p.setarValores(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//CIMA-ESQUERDA
		p.setarValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//CIMA-DIREITA
		p.setarValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//BAIXO-ESQUERDA
		p.setarValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//BAIXO-DIREITA
		p.setarValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		// #MOVIMENTO ESPECIAL ROGUE
		if (getContadorMovimento() == 0 && !partidaXadrez.getCheque()) {
			// #MOVIMENTO ESPECIAL ROGUE PEQUENO
			Position posT1 = new Position(posicao.getLinha(), posicao.getColuna() + 3);
			if (testeTorreRogue(posT1)) {
				Position p1 = new Position(posicao.getLinha(), posicao.getColuna() + 1);
				Position p2 = new Position(posicao.getLinha(), posicao.getColuna() + 2);
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
					matriz[posicao.getLinha()][posicao.getColuna() + 2] = true;
				}
			}
			// #MOVIMENTO ESPECIAL ROGUE GRANDE
						Position posT2 = new Position(posicao.getLinha(), posicao.getColuna() - 4);
						if (testeTorreRogue(posT2)) {
							Position p1 = new Position(posicao.getLinha(), posicao.getColuna() - 1);
							Position p2 = new Position(posicao.getLinha(), posicao.getColuna() - 2);
							Position p3 = new Position(posicao.getLinha(), posicao.getColuna() - 3);
							if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
								matriz[posicao.getLinha()][posicao.getColuna() - 2] = true;
							}
						}
		}
		
		return matriz;
	}

	@Override
	public String toString() {
		return "K";
	}
	
}
