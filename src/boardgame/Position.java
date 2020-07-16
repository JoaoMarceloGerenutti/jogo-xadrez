package boardgame;

public class Position {

	private Integer linha;
	private Integer coluna;
	
	public Position() {
	}

	public Position(Integer linha, Integer coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}

	public Integer getLinha() {
		return linha;
	}

	public Integer getColuna() {
		return coluna;
	}
	
	public void setLinha(Integer linha) {
		this.linha = linha;
	}

	public void setColuna(Integer coluna) {
		this.coluna = coluna;
	}

	public void setarValores(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	@Override
	public String toString() {
		return linha 
			+ ", "
			+ coluna;
	}
}
