package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {
	static String ganhador;
	public static String player1; 
	public static String player2; 
	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	// https://stackoberflow.com/questions/2979383/java-clear-the-console
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	
	public static ChessPosition readChessPosition(Scanner sc) {
		try {
			String s = sc.nextLine();
			char column = s.charAt(0);
			int row = Integer.parseInt(s.substring(1));
			return new ChessPosition(column, row);
		}
		catch(RuntimeException e) {
			throw new InputMismatchException("Erro na leitura de ChessPosition. Valores validos de a1 a h8");
		}

	}

	public static void menu(){
		Locale.setDefault(Locale.US);
		String escolha;
		Scanner sc = new Scanner(System.in);
		UI.clearScreen();
		System.out.println(ANSI_YELLOW+"******************************"+ANSI_RESET+"********************************");
		System.out.println("                     -----Chess"+ANSI_YELLOW+"Game-----                  "+ ANSI_RESET);
		System.out.println("******************************"+ANSI_YELLOW+"********************************"+ ANSI_RESET);
		
		System.out.println(ANSI_YELLOW+"                       1 - Iniciar jogo"+ANSI_RESET);
		System.out.println("");
		System.out.println("                       2 - Ranking");
		System.out.println("");
		System.out.println(ANSI_YELLOW+"                       3 - Indice de pecas"+ANSI_RESET);
		System.out.println("");
		System.out.print("                       Digite a opcao desejada:");
		escolha = sc.nextLine();

		UI.clearScreen();
		if(escolha.equals("1")){
			
			UI.clearScreen();
			System.out.print("Nome do Jogador das pecas brancas:");
			UI.player1  = sc.nextLine();
			System.out.println("");
			System.out.print(ANSI_YELLOW+"Nome do Jogador das pecas pretas:"+ANSI_RESET);
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
						String type = sc.nextLine().toUpperCase();
						while(!type.equals("B") && !type.equals("C") && !type.equals("T") && !type.equals("Q")){
							System.out.print("Valor Invalido!! Entre novamente com a peca para a promocao (B/C/T/Q): ");
							type = sc.nextLine().toUpperCase();
						}
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

		else if(escolha.equals("3")) {
			System.out.println(ANSI_YELLOW+"Peca      -     Letra"+ANSI_RESET);
			System.out.println("");
			System.out.println("Bispo     -      B");
			System.out.println("Cavalo    -      C");
			System.out.println("Torre     -      T");
			System.out.println("Peao      -      P");
			System.out.println("Rainha    -      Q");
			System.out.println("Rei       -      R");
			System.out.println();
			System.out.print("Para voltar ao menu precione enter...");
			escolha = sc.nextLine();
			menu();
		}
		
		else {
			System.out.println("Opcao Invalida");
			menu();
		}
	}
	
	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
		printBoard(chessMatch.getPieces());
		System.out.println();
		printCapturedPieces(captured);
		System.out.println("Turno: " + chessMatch.getTurn());
		if(!chessMatch.getCheckMate()) {
			System.out.println("Aguardando o Jogador: " + chessMatch.getCurrentPlayer());
			if(chessMatch.getCheck()) {
				System.out.println("Voce esta em CHECK!!");
			}
		}else {
			System.out.println("");
			System.out.println("CHECKMATE!!!");
			if(chessMatch.getCurrentPlayer() == Color.BRANCO) {
				ganhador=player1;
			}else {
				ganhador=player2;
			}//ESCRITA EM ARQUIVO
				String[] lines = new String[] {player1+"			"+player2+"			"+ganhador}; // colando valores em um vetor de string
				String path = "../bin/rank.txt"; // caminho do arquivo que receber� o conte�do que ser� escrito
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(path,true))) { // cria o arquivo se nao existir e grava o
																						// novo conteudo --- para continuar a
																						// gravacao no arquivo passa o argumento
																						// "true" em FileWriter ficando
																						// FileWriter(path,true)
					for (String line : lines) { // para gravar cada item (line) do vetor (lines)
						bw.write(line);
						bw.newLine();
					}
				} catch (IOException e) { // tratando excecao
					e.printStackTrace();
				}
	
			
			System.out.println("O ganhador e: "+ganhador);
		}

	}
	
	public static void printRanking() {//LEITURA EM ARQUIVO
		String escolha;
		Scanner sc = new Scanner(System.in);
		String path = "../bin/rank.txt";
		try (BufferedReader br = new BufferedReader(new FileReader(path))) { // forma otimizada para ler o arquivo pela classe filereader
			String line = br.readLine();
			System.out.println("Player1		"+ANSI_YELLOW+"	Player2"+ANSI_GREEN+"			Ganhador"+ANSI_RESET);
			while (line != null) {
				System.out.println(line);
				line = br.readLine();
			}
			System.out.println();
			System.out.print("Para voltar ao menu precione enter...");
			escolha = sc.nextLine();
			menu();
			
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void printBoard(ChessPiece[][] pieces) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j], possibleMoves[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}


	private static void printPiece(ChessPiece piece, boolean background) {
		if(background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        }
        else {
            if (piece.getColor() == Color.BRANCO) {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
	}
	
	private static void printCapturedPieces(List<ChessPiece> captured) {
		List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.BRANCO).collect(Collectors.toList());
		List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.PRETO).collect(Collectors.toList());
		System.out.println("Pecas Capturadas:");
		System.out.print("Brancas: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(white.toArray()));
		System.out.print(ANSI_RESET);
		System.out.print("Pretas: ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(black.toArray()));
		System.out.print(ANSI_RESET);
		System.out.println();
		
		
	}
}
