package boardgame;

public class Board {

	private Integer linhas;
	private Integer colunas;
	private Piece[][] pecas;
	
	public Board(Integer linhas, Integer colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new BoardException("Erro ao criar o tabuleiro: é necessario que exista pelo menos uma linha e uma coluna!");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Piece[linhas][colunas];
	}

	public Integer getLinhas() {
		return linhas;
	}

	public Integer getColunas() {
		return colunas;
	}
	
	public Piece peca(int linha, int coluna) {
		if (!posicaoExiste(linha, coluna)) {
			throw new BoardException("Posição não está no tabuleiro!");
		}
		return pecas[linha][coluna];
	}
	
	public Piece peca(Position posicao) {
		if (!posicaoExiste(posicao)) { 
			throw new BoardException("Posição não está no tabuleiro!");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void colocarPeca(Piece peca, Position posicao) {
		if (temUmaPeca(posicao)) {
			throw new BoardException("Já tem uma peça na posição " + posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	private boolean posicaoExiste(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}
	
	public boolean posicaoExiste(Position posicao) {
		return posicaoExiste(posicao.getLinha(), posicao.getColuna());
	}
	
	public boolean temUmaPeca(Position posicao) {
		if (!posicaoExiste(posicao)) {
			throw new BoardException("Posição não está no tabuleiro!");
		}
		return peca(posicao) != null;
	}
}
