package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    private ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
        Position p = new Position(0, 0);
        //se for branco ele tem que andar para cima.
        if(getColor() == Color.BRANCO){
            p.setValues(position.getRow()-1, position.getColumn());
            //verificar se o peão pode ir para lá
            if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }
            //verificar se pode ir duas casas para frente no primeiro movimento dela
            p.setValues(position.getRow()-2, position.getColumn());
            Position p2 = new Position(position.getRow()-1, position.getColumn());
            //verificar se o peão pode ir para lá, testando se existe, se esta vazia e se a quantidade de movimentos da peça for =0
            if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0){
                mat[p.getRow()][p.getColumn()] = true;
            }
            //Verifica se tem alguma peça adversaria nas diagonais para poder captura-la (Diagonal esquerda)
            p.setValues(position.getRow()-1, position.getColumn() - 1);
            if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }

            //Verifica se tem alguma peça adversaria nas diagonais para poder captura-la (Diagonal direita)
            p.setValues(position.getRow()-1, position.getColumn() + 1);
            if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }

            // En passant brancas
            if(position.getRow() == 3){
                Position left = new Position(position.getRow(), position.getColumn()-1);
                
                if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left)==chessMatch.getEnPassantVulnerable()){
                    mat[left.getRow()-1][left.getColumn()] = true;
                }
                Position right = new Position(position.getRow(), position.getColumn()+1);
                if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right)==chessMatch.getEnPassantVulnerable()){
                    mat[right.getRow()-1][right.getColumn()] = true;
                }
            }

        }else{
            //Peão preto vai para baixo assim mudando os sinais para + 
            p.setValues(position.getRow()+1, position.getColumn());
            //verificar se o peão pode ir para lá
            if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }
            //verificar se pode ir duas casas para frente no primeiro movimento dela
            p.setValues(position.getRow()+2, position.getColumn());
            Position p2 = new Position(position.getRow()+1, position.getColumn());
            //verificar se o peão pode ir para lá, testando se existe, se esta vazia e se a quantidade de movimentos da peça for =0
            if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0){
                mat[p.getRow()][p.getColumn()] = true;
            }
            //Verifica se tem alguma peça adversaria nas diagonais para poder captura-la (Diagonal esquerda)
            p.setValues(position.getRow()+1, position.getColumn() - 1);
            if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }

            //Verifica se tem alguma peça adversaria nas diagonais para poder captura-la (Diagonal direita)
            p.setValues(position.getRow()+1, position.getColumn() + 1);
            if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }

            // En passant pretas
            if(position.getRow() == 4){
                Position left = new Position(position.getRow(), position.getColumn()-1);
                
                if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left)==chessMatch.getEnPassantVulnerable()){
                    mat[left.getRow()+1][left.getColumn()] = true;
                }
                Position right = new Position(position.getRow(), position.getColumn()+1);
                if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right)==chessMatch.getEnPassantVulnerable()){
                    mat[right.getRow()+1][right.getColumn()] = true;
                }
            }
        }

        return mat;
    }

    @Override
    public String toString() {
        return "P";
    }
    
    
}
