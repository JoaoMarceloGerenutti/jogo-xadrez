package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Tower;

public class ChessMatch {

	private int turno;
	private Color jogadorAtual;
	private Board tabuleiro;
	private boolean cheque;
	private boolean chequeMate;
	
	private List<Piece> pecasNoTabuleiro = new ArrayList<>();
	private List<Piece> pecasCapturadas = new ArrayList<>();
	
	public ChessMatch() {
		tabuleiro = new Board(8, 8);
		turno = 1;
		jogadorAtual = Color.BRANCO;
		setupInicial();
	}
	
	public int getTurno() {
		return turno;
	}

	public Color getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getCheque() {
		return cheque;
	}
	
	public boolean getChequeMate() {
		return chequeMate;
	}

	public ChessPiece[][] obterPecas() {
		ChessPiece[][] matriz = new ChessPiece[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				matriz[i][j] = (ChessPiece) tabuleiro.peca(i, j);
			}
		}
		return matriz;
	}
	
	public boolean[][] movimentosPossiveis(ChessPosition posicaoOrigem) {
		Position posicao = posicaoOrigem.paraPosicao();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	
	public ChessPiece fazerMovimentoXadrez(ChessPosition posicaoOrigem, ChessPosition posicaoDestino) {
		Position origem = posicaoOrigem.paraPosicao();
		Position destino = posicaoDestino.paraPosicao();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Piece pecaCapturada = facaMovimento(origem, destino);
		
		if (testeCheque(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new ChessException("Voce nao pode se colocar em cheque!");
		}
		
		cheque = (testeCheque(oponente(jogadorAtual))) ? true : false;
		
		if (testeChequeMate(oponente(jogadorAtual))) {
			chequeMate = true;
		} else {
			proximoTurno();
		}
		
		return (ChessPiece)pecaCapturada;
	}
	
	private Piece facaMovimento(Position origem, Position destino) {
		ChessPiece p = (ChessPiece)tabuleiro.removerPeca(origem);
		p.aumentarContadorMovimento();
		Piece pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);
		
		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		
		// #MOVIMENTO ESPECIAL ROGUE PEQUENO
		if (p instanceof King && destino.getColuna() == origem.getColuna() + 2) {
			Position origemT = new Position(origem.getLinha(), origem.getColuna() + 3);
			Position destinoT = new Position(origem.getLinha(), origem.getColuna() + 1);
			ChessPiece torre = (ChessPiece)tabuleiro.removerPeca(origemT);
			tabuleiro.colocarPeca(torre, destinoT);
			torre.aumentarContadorMovimento();
		}
		
		// #MOVIMENTO ESPECIAL ROGUE GRANDE
		if (p instanceof King && destino.getColuna() == origem.getColuna() - 2) {
			Position origemT = new Position(origem.getLinha(), origem.getColuna() - 4);
			Position destinoT = new Position(origem.getLinha(), origem.getColuna() - 1);
			ChessPiece torre = (ChessPiece)tabuleiro.removerPeca(origemT);
			tabuleiro.colocarPeca(torre, destinoT);
			torre.aumentarContadorMovimento();
		}
		return pecaCapturada;
	}
	
	private void desfazerMovimento(Position origem, Position destino, Piece pecaCapturada) {
		ChessPiece p = (ChessPiece)tabuleiro.removerPeca(destino);
		p.diminuirContadorMovimento();
		tabuleiro.colocarPeca(p, origem);
		
		if (pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
		// #MOVIMENTO ESPECIAL ROGUE PEQUENO
		if (p instanceof King && destino.getColuna() == origem.getColuna() + 2) {
			Position origemT = new Position(origem.getLinha(), origem.getColuna() + 3);
			Position destinoT = new Position(origem.getLinha(), origem.getColuna() + 1);
			ChessPiece torre = (ChessPiece)tabuleiro.removerPeca(destinoT);
			tabuleiro.colocarPeca(torre, origemT);
			torre.diminuirContadorMovimento();
		}
		
		// #MOVIMENTO ESPECIAL ROGUE GRANDE
		if (p instanceof King && destino.getColuna() == origem.getColuna() - 2) {
			Position origemT = new Position(origem.getLinha(), origem.getColuna() - 4);
			Position destinoT = new Position(origem.getLinha(), origem.getColuna() - 1);
			ChessPiece torre = (ChessPiece)tabuleiro.removerPeca(destinoT);
			tabuleiro.colocarPeca(torre, origemT);
			torre.diminuirContadorMovimento();
		}
	}
	
	private void validarPosicaoOrigem(Position posicao) {
		if (!tabuleiro.temUmaPeca(posicao)) {
			throw new ChessException("Nao existe peca na posicao de origem!");
		}
		if (jogadorAtual != ((ChessPiece)tabuleiro.peca(posicao)).getCor()) {
			throw new ChessException("A peca escolhida nao e sua!");
		}
		if (!tabuleiro.peca(posicao).temAlgumMovimentoPossivel()) {
			throw new ChessException("Nao existe nenhum movimento possivel para peca escolhida!");
		}
	}
	
	private void validarPosicaoDestino(Position origem, Position destino) {
		if (!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new ChessException("A peca escolhida nao pode se mover para posicao de destino!");
		}
	}
	
	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Color.BRANCO) ? Color.PRETO : Color.BRANCO;
	}
	
	private Color oponente(Color cor) {
		return (cor == Color.BRANCO) ? Color.PRETO : Color.BRANCO;
	}
	
	private ChessPiece king(Color cor) {
		List<Piece> lista = pecasNoTabuleiro.stream().filter(x -> ((ChessPiece)x).getCor() == cor).collect(Collectors.toList());
		for (Piece p : lista) {
			if (p instanceof King) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("Nao existe o rei " + cor + " no tabuleiro");
	}
	
	private boolean testeCheque(Color cor) {
		Position posicaoRei = king(cor).getPosicaoXadrez().paraPosicao();
		List<Piece> pecasOponente = pecasNoTabuleiro.stream().filter(x -> ((ChessPiece)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Piece p : pecasOponente) {
			boolean[][] matriz = p.movimentosPossiveis();
			if (matriz[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testeChequeMate(Color cor) {
		if (!testeCheque(cor)) {
			return false;
		}
		List<Piece> lista = pecasNoTabuleiro.stream().filter(x -> ((ChessPiece)x).getCor() == cor).collect(Collectors.toList());
		for (Piece p : lista) {
			boolean[][] matriz = p.movimentosPossiveis();
			for (int i=0; i<tabuleiro.getLinhas(); i++) {
				for (int j=0; j<tabuleiro.getColunas(); j++) {
					if (matriz[i][j]) {
						Position origem = ((ChessPiece)p).getPosicaoXadrez().paraPosicao();
						Position destino = new Position(i, j);
						Piece pecaCapturada = facaMovimento(origem, destino);
						boolean testeCheque = testeCheque(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!testeCheque) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	private void colocarNovaPeca(char coluna, int linha, ChessPiece peca) {
		tabuleiro.colocarPeca(peca, new ChessPosition(coluna, linha).paraPosicao());
		pecasNoTabuleiro.add(peca);
	}
	
	private void setupInicial() {
		//PEÇAS PRETAS ATRAS
		colocarNovaPeca('A', 8, new Tower(tabuleiro, Color.PRETO));
		colocarNovaPeca('B', 8, new Knight(tabuleiro, Color.PRETO));
		colocarNovaPeca('C', 8, new Bishop(tabuleiro, Color.PRETO));
		colocarNovaPeca('E', 8, new King(tabuleiro, Color.PRETO, this));
		colocarNovaPeca('D', 8, new Queen(tabuleiro, Color.PRETO));
		colocarNovaPeca('F', 8, new Bishop(tabuleiro, Color.PRETO));
		colocarNovaPeca('G', 8, new Knight(tabuleiro, Color.PRETO));
		colocarNovaPeca('H', 8, new Tower(tabuleiro, Color.PRETO));
		//PEÇAS PRETAS FRENTE
		colocarNovaPeca('A', 7, new Pawn(tabuleiro, Color.PRETO));
		colocarNovaPeca('B', 7, new Pawn(tabuleiro, Color.PRETO));
		colocarNovaPeca('C', 7, new Pawn(tabuleiro, Color.PRETO));
		colocarNovaPeca('E', 7, new Pawn(tabuleiro, Color.PRETO));
		colocarNovaPeca('D', 7, new Pawn(tabuleiro, Color.PRETO));
		colocarNovaPeca('F', 7, new Pawn(tabuleiro, Color.PRETO));
		colocarNovaPeca('G', 7, new Pawn(tabuleiro, Color.PRETO));
		colocarNovaPeca('H', 7, new Pawn(tabuleiro, Color.PRETO));
		
		//PEÇAS BRANCAS ATRAS
		colocarNovaPeca('A', 1, new Tower(tabuleiro, Color.BRANCO));
		colocarNovaPeca('B', 1, new Knight(tabuleiro, Color.BRANCO));
		colocarNovaPeca('C', 1, new Bishop(tabuleiro, Color.BRANCO));
		colocarNovaPeca('D', 1, new Queen(tabuleiro, Color.BRANCO));
		colocarNovaPeca('E', 1, new King(tabuleiro, Color.BRANCO, this));
		colocarNovaPeca('F', 1, new Bishop(tabuleiro, Color.BRANCO));
		colocarNovaPeca('G', 1, new Knight(tabuleiro, Color.BRANCO));
		colocarNovaPeca('H', 1, new Tower(tabuleiro, Color.BRANCO));
		//PEÇAS BRANCAS FRENTE
		colocarNovaPeca('A', 2, new Pawn(tabuleiro, Color.BRANCO));
		colocarNovaPeca('B', 2, new Pawn(tabuleiro, Color.BRANCO));
		colocarNovaPeca('C', 2, new Pawn(tabuleiro, Color.BRANCO));
		colocarNovaPeca('D', 2, new Pawn(tabuleiro, Color.BRANCO));
		colocarNovaPeca('E', 2, new Pawn(tabuleiro, Color.BRANCO));
		colocarNovaPeca('F', 2, new Pawn(tabuleiro, Color.BRANCO));
		colocarNovaPeca('G', 2, new Pawn(tabuleiro, Color.BRANCO));
		colocarNovaPeca('H', 2, new Pawn(tabuleiro, Color.BRANCO));
	}
}
