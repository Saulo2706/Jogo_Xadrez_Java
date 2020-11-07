package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    public Pawn(Board board, Color color) {
        super(board, color);
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

        }else{
            //Peão preto vai para baixo assim mudando os sinais para + 
            p.setValues(position.getRow()+1, position.getColumn());
            //verificar se o peão pode ir para lá
            if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }
            //verificar se pode ir duas casas para frente no primeiro movimento dela
            p.setValues(position.getRow()+2, position.getColumn());
            Position p2 = new Position(position.getRow()-1, position.getColumn());
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
        }

        return mat;
    }

    @Override
    public String toString() {
        return "P";
    }
    
    
}
