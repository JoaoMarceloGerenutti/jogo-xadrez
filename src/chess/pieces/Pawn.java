package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board tabuleiro, Color cor) {
		super(tabuleiro, cor);
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Position p = new Position(0, 0);
		
		if (getCor() == Color.BRANCO) {
			p.setarValores(posicao.getLinha() -1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setarValores(posicao.getLinha() -2, posicao.getColuna());
			Position p2 = new Position(posicao.getLinha() -1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temUmaPeca(p2) && getContadorMovimento() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setarValores(posicao.getLinha() -1, posicao.getColuna() -1);
			if (getTabuleiro().posicaoExiste(p) && temUmaPecaInimiga(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setarValores(posicao.getLinha() -1, posicao.getColuna() +1);
			if (getTabuleiro().posicaoExiste(p) && temUmaPecaInimiga(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
		} else {
			p.setarValores(posicao.getLinha() +1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setarValores(posicao.getLinha() +2, posicao.getColuna());
			Position p2 = new Position(posicao.getLinha() +1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temUmaPeca(p2) && getContadorMovimento() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setarValores(posicao.getLinha() +1, posicao.getColuna() -1);
			if (getTabuleiro().posicaoExiste(p) && temUmaPecaInimiga(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setarValores(posicao.getLinha() +1, posicao.getColuna() +1);
			if (getTabuleiro().posicaoExiste(p) && temUmaPecaInimiga(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
		}
		return matriz;
	}
	
	@Override
	public String toString() {
		return "P";
	}
}
