package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		String escolha;
		Scanner sc = new Scanner(System.in);
		UI.clearScreen();
		System.out.println("-----Menu Principal-----");
		System.out.println("   1 - Iniciar jogo");
		System.out.println("");
		System.out.println("   2 - Ranking");
		
		System.out.print("Digite a opcao desejada:");
		escolha = sc.nextLine();
		UI.clearScreen();
		if(escolha.equals("1")){
			
			UI.clearScreen();
			System.out.print("Nome do Jogador das pecas brancas:");
			UI.player1  = sc.nextLine();
			System.out.println("");
			System.out.print("Nome do Jogador das pecas pretas:");
			UI.player2 = sc.nextLine();
			UI.clearScreen();
			ChessMatch chessMatch = new ChessMatch();
			List<ChessPiece> captured = new ArrayList<>();
			
			while(!chessMatch.getCheckMate()) {
				try {
					UI.clearScreen();
					UI.printMatch(chessMatch, captured);
					System.out.println();
					System.out.print("Posicao de Origem: ");
					ChessPosition source = UI.readChessPosition(sc);
					
					boolean[][] possibleMoves = chessMatch.possibleMoves(source);
					UI.clearScreen();
					UI.printBoard(chessMatch.getPieces(), possibleMoves);
					System.out.println();
					System.out.print("Posicao de Destino: ");
					ChessPosition target = UI.readChessPosition(sc);
					
					ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
					
					if(capturedPiece != null) {
						captured.add(capturedPiece);
					}

					if(chessMatch.getPromoted() != null){
						System.out.print("Entre com a peca para a promocao (B/C/T/Q): ");
						String type = sc.nextLine();
						chessMatch.replacePromotedPiece(type);
					}
				}
				catch(ChessException e) {
					System.out.println(e.getMessage());
					sc.nextLine();
				}
				catch(InputMismatchException e) {
					System.out.println(e.getMessage());
					sc.nextLine();
				}
			}
			UI.clearScreen();
			UI.printMatch(chessMatch, captured);
		}
		else if(escolha.equals("2")) {
			UI.clearScreen();
			UI.printRanking();
		}
		
		else {
			System.out.println("Opcao Invalida");
		}
	}

}
