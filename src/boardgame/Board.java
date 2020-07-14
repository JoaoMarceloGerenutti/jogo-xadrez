package boardgame;

public class Board {

	private Integer linha;
	private Integer coluna;
	private Piece[][] pecas;
	
	public Board(Integer linha, Integer coluna) {
		this.linha = linha;
		this.coluna = coluna;
		pecas = new Piece[linha][coluna];
	}

	public Integer getLinha() {
		return linha;
	}

	public void setLinha(Integer linha) {
		this.linha = linha;
	}

	public Integer getColuna() {
		return coluna;
	}

	public void setColuna(Integer coluna) {
		this.coluna = coluna;
	}
	
	public Piece peca(int linha, int coluna) {
		return pecas[linha][coluna];
	}
	
	public Piece peca(Position posicao) {
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void colocarPeca(Piece peca, Position posicao) {
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
}
