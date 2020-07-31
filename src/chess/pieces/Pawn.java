package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
	
	private ChessMatch partidaXadrez;

	public Pawn(Board tabuleiro, Color cor, ChessMatch partidaXadrez) {
		super(tabuleiro, cor);
		this.partidaXadrez = partidaXadrez;
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
			
			//MOVIMENTO ESPECIAL EN PASSANT BRANCO
			if (posicao.getLinha() == 3) {
				Position esquerda = new Position(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExiste(esquerda) && temUmaPecaInimiga(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.getEnPassantVulneravel()) {
					matriz[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				}
				Position direita = new Position(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExiste(direita) && temUmaPecaInimiga(direita) && getTabuleiro().peca(direita) == partidaXadrez.getEnPassantVulneravel()) {
					matriz[direita.getLinha() - 1][direita.getColuna()] = true;
				}
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
			
			//MOVIMENTO ESPECIAL EN PASSANT PRETO
			if (posicao.getLinha() == 4) {
				Position esquerda = new Position(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExiste(esquerda) && temUmaPecaInimiga(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.getEnPassantVulneravel()) {
					matriz[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				}
				Position direita = new Position(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExiste(direita) && temUmaPecaInimiga(direita) && getTabuleiro().peca(direita) == partidaXadrez.getEnPassantVulneravel()) {
					matriz[direita.getLinha() + 1][direita.getColuna()] = true;
				}
			}
		}
		return matriz;
	}
	
	@Override
	public String toString() {
		return "P";
	}
}
