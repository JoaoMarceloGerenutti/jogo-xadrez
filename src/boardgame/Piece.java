package boardgame;

public abstract class Piece {

	protected Position posicao;
	private Board tabuleiro;

	public Piece(Board tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicao = null;
	}

	protected Board getTabuleiro() {
		return tabuleiro;
	}
	
	public abstract boolean[][] movimentosPossiveis();
	
	public boolean movimentoPossivel(Position posicao) {
		return movimentosPossiveis()[posicao.getLinha()][posicao.getColuna()];
	}

	public boolean temAlgumMovimentoPossivel() {
		boolean[][] matriz = movimentosPossiveis();
		for (int i=0; i<matriz.length; i++) {
			for (int j=0; j<matriz.length; j++) {
				if (matriz[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}
