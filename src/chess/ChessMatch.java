package chess;

import java.security.InvalidParameterException;
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
	private ChessPiece enPassantVulneravel;
	private ChessPiece promovido;

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

	public ChessPiece getEnPassantVulneravel() {
		return enPassantVulneravel;
	}
	
	public ChessPiece getPromovido() {
		return promovido;
	}

	public ChessPiece[][] obterPecas() {
		ChessPiece[][] matriz = new ChessPiece[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
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

		ChessPiece pecaMovida = (ChessPiece) tabuleiro.peca(destino);

		// #MOVIMENTO ESPECIAL PROMOÇÃO
		promovido = null;
		if (pecaMovida instanceof Pawn) {
			if (pecaMovida.getCor() == Color.BRANCO && destino.getLinha() == 0 || pecaMovida.getCor() == Color.PRETO && destino.getLinha() == 7) {
				promovido = (ChessPiece)tabuleiro.peca(destino);
				promovido = recolocarPecaPromovida("Q");
			}
		}
		
		cheque = (testeCheque(oponente(jogadorAtual))) ? true : false;

		if (testeChequeMate(oponente(jogadorAtual))) {
			chequeMate = true;
		} else {
			proximoTurno();
		}

		// #MOVIMENTO ESPECIAL EN PASSANT
		if (pecaMovida instanceof Pawn
				&& (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)) {
			enPassantVulneravel = pecaMovida;
		} else {
			enPassantVulneravel = null;
		}

		return (ChessPiece) pecaCapturada;
	}
	
	public ChessPiece recolocarPecaPromovida(String tipo) {
		if (promovido == null) {
			throw new IllegalStateException("Nao ha peca para ser promovida!");
		}
		if (!tipo.equals("B") && !tipo.equals("N") && !tipo.equals("T") && !tipo.equals("Q")) {
			throw new InvalidParameterException("Tipo invalido para promocao!"); 
		}
		
		Position pos = promovido.getPosicaoXadrez().paraPosicao();
		Piece p = tabuleiro.removerPeca(pos);
		pecasNoTabuleiro.remove(p);
		
		ChessPiece novaPeca = novaPeca(tipo, promovido.getCor());
		tabuleiro.colocarPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);
		
		return novaPeca;
	}
	
	private ChessPiece novaPeca(String tipo, Color cor) {
		if (tipo.equals("B")) return new Bishop(tabuleiro, cor);
		if (tipo.equals("N")) return new Knight(tabuleiro, cor);
		if (tipo.equals("Q")) return new Queen(tabuleiro, cor);
		return new Tower(tabuleiro, cor);
	}

	private Piece facaMovimento(Position origem, Position destino) {
		ChessPiece p = (ChessPiece) tabuleiro.removerPeca(origem);
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
			ChessPiece torre = (ChessPiece) tabuleiro.removerPeca(origemT);
			tabuleiro.colocarPeca(torre, destinoT);
			torre.aumentarContadorMovimento();
		}

		// #MOVIMENTO ESPECIAL ROGUE GRANDE
		if (p instanceof King && destino.getColuna() == origem.getColuna() - 2) {
			Position origemT = new Position(origem.getLinha(), origem.getColuna() - 4);
			Position destinoT = new Position(origem.getLinha(), origem.getColuna() - 1);
			ChessPiece torre = (ChessPiece) tabuleiro.removerPeca(origemT);
			tabuleiro.colocarPeca(torre, destinoT);
			torre.aumentarContadorMovimento();
		}

		// #MOVIMENTO ESPECIAL EN PASSANT
		if (p instanceof Pawn) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
				Position posicaoPeao;
				if (p.getCor() == Color.BRANCO) {
					posicaoPeao = new Position(destino.getLinha() + 1, destino.getColuna());
				} else {
					posicaoPeao = new Position(destino.getLinha() - 1, destino.getColuna());
				}
				pecaCapturada = tabuleiro.removerPeca(posicaoPeao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
		}
		return pecaCapturada;
	}

	private void desfazerMovimento(Position origem, Position destino, Piece pecaCapturada) {
		ChessPiece p = (ChessPiece) tabuleiro.removerPeca(destino);
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
			ChessPiece torre = (ChessPiece) tabuleiro.removerPeca(destinoT);
			tabuleiro.colocarPeca(torre, origemT);
			torre.diminuirContadorMovimento();
		}

		// #MOVIMENTO ESPECIAL ROGUE GRANDE
		if (p instanceof King && destino.getColuna() == origem.getColuna() - 2) {
			Position origemT = new Position(origem.getLinha(), origem.getColuna() - 4);
			Position destinoT = new Position(origem.getLinha(), origem.getColuna() - 1);
			ChessPiece torre = (ChessPiece) tabuleiro.removerPeca(destinoT);
			tabuleiro.colocarPeca(torre, origemT);
			torre.diminuirContadorMovimento();
		}

		// #MOVIMENTO ESPECIAL EN PASSANT
		if (p instanceof Pawn) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == enPassantVulneravel) {
				ChessPiece peao = (ChessPiece) tabuleiro.removerPeca(destino);
				Position posicaoPeao;
				if (p.getCor() == Color.BRANCO) {
					posicaoPeao = new Position(3, destino.getColuna());
				} else {
					posicaoPeao = new Position(4, destino.getColuna());
				}
				tabuleiro.colocarPeca(peao, posicaoPeao);
			}
		}
	}

	private void validarPosicaoOrigem(Position posicao) {
		if (!tabuleiro.temUmaPeca(posicao)) {
			throw new ChessException("Nao existe peca na posicao de origem!");
		}
		if (jogadorAtual != ((ChessPiece) tabuleiro.peca(posicao)).getCor()) {
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
		List<Piece> lista = pecasNoTabuleiro.stream().filter(x -> ((ChessPiece) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Piece p : lista) {
			if (p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("Nao existe o rei " + cor + " no tabuleiro");
	}

	private boolean testeCheque(Color cor) {
		Position posicaoRei = king(cor).getPosicaoXadrez().paraPosicao();
		List<Piece> pecasOponente = pecasNoTabuleiro.stream().filter(x -> ((ChessPiece) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
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
		List<Piece> lista = pecasNoTabuleiro.stream().filter(x -> ((ChessPiece) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Piece p : lista) {
			boolean[][] matriz = p.movimentosPossiveis();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (matriz[i][j]) {
						Position origem = ((ChessPiece) p).getPosicaoXadrez().paraPosicao();
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
		// PEÇAS PRETAS ATRAS
		colocarNovaPeca('A', 8, new Tower(tabuleiro, Color.PRETO));
		colocarNovaPeca('B', 8, new Knight(tabuleiro, Color.PRETO));
		colocarNovaPeca('C', 8, new Bishop(tabuleiro, Color.PRETO));
		colocarNovaPeca('E', 8, new King(tabuleiro, Color.PRETO, this));
		colocarNovaPeca('D', 8, new Queen(tabuleiro, Color.PRETO));
		colocarNovaPeca('F', 8, new Bishop(tabuleiro, Color.PRETO));
		colocarNovaPeca('G', 8, new Knight(tabuleiro, Color.PRETO));
		colocarNovaPeca('H', 8, new Tower(tabuleiro, Color.PRETO));
		// PEÇAS PRETAS FRENTE
		colocarNovaPeca('A', 7, new Pawn(tabuleiro, Color.PRETO, this));
		colocarNovaPeca('B', 7, new Pawn(tabuleiro, Color.PRETO, this));
		colocarNovaPeca('C', 7, new Pawn(tabuleiro, Color.PRETO, this));
		colocarNovaPeca('E', 7, new Pawn(tabuleiro, Color.PRETO, this));
		colocarNovaPeca('D', 7, new Pawn(tabuleiro, Color.PRETO, this));
		colocarNovaPeca('F', 7, new Pawn(tabuleiro, Color.PRETO, this));
		colocarNovaPeca('G', 7, new Pawn(tabuleiro, Color.PRETO, this));
		colocarNovaPeca('H', 7, new Pawn(tabuleiro, Color.PRETO, this));

		// PEÇAS BRANCAS ATRAS
		colocarNovaPeca('A', 1, new Tower(tabuleiro, Color.BRANCO));
		colocarNovaPeca('B', 1, new Knight(tabuleiro, Color.BRANCO));
		colocarNovaPeca('C', 1, new Bishop(tabuleiro, Color.BRANCO));
		colocarNovaPeca('D', 1, new Queen(tabuleiro, Color.BRANCO));
		colocarNovaPeca('E', 1, new King(tabuleiro, Color.BRANCO, this));
		colocarNovaPeca('F', 1, new Bishop(tabuleiro, Color.BRANCO));
		colocarNovaPeca('G', 1, new Knight(tabuleiro, Color.BRANCO));
		colocarNovaPeca('H', 1, new Tower(tabuleiro, Color.BRANCO));
		// PEÇAS BRANCAS FRENTE
		colocarNovaPeca('A', 2, new Pawn(tabuleiro, Color.BRANCO, this));
		colocarNovaPeca('B', 2, new Pawn(tabuleiro, Color.BRANCO, this));
		colocarNovaPeca('C', 2, new Pawn(tabuleiro, Color.BRANCO, this));
		colocarNovaPeca('D', 2, new Pawn(tabuleiro, Color.BRANCO, this));
		colocarNovaPeca('E', 2, new Pawn(tabuleiro, Color.BRANCO, this));
		colocarNovaPeca('F', 2, new Pawn(tabuleiro, Color.BRANCO, this));
		colocarNovaPeca('G', 2, new Pawn(tabuleiro, Color.BRANCO, this));
		colocarNovaPeca('H', 2, new Pawn(tabuleiro, Color.BRANCO, this));
	}
}
