package hanto.studentAJRZL.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

import java.util.HashMap;
import java.util.Map;

public class BaseHantoGame implements HantoGame {

	protected HantoPlayerColor currentPlayColor;
	protected boolean isGameEnded = false;
	protected Map<HantoCoordinate, HantoPiece> board;

	/**
	 * Constructor for Base hanto game
	 * 
	 * @param moveFirst
	 */
	public BaseHantoGame(HantoPlayerColor movesFirst) {
		currentPlayColor = movesFirst;
		board = new HashMap<HantoCoordinate, HantoPiece>();
	}

	/**
	 * This method uses template method pattern to execute a move in the game.
	 * It is called for every move that must be made.
	 * 
	 * @param pieceType
	 *            the piece type that is being moved
	 * @param from
	 *            the coordinate where the piece begins. If the coordinate is
	 *            null, then the piece begins off the board (that is, it is
	 *            placed on the board in this move).
	 * @param to
	 *            the coordinated where the piece is after the move has been
	 *            made.
	 * @return the result of the move
	 * @throws HantoException
	 *             if there are any problems in making the move (such as
	 *             specifying a coordinate that does not have the appropriate
	 *             piece, or the color of the piece is not the color of the
	 *             player who is moving.
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param where
	 *            the coordinate to query
	 * @return the piece at the specified coordinate or null if there is no
	 *         piece at that position
	 */
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		HantoCoordinate coord = new HantoPieceCoordinate(where.getX(),
				where.getY());
		return board.get(coord);
	}

	/**
	 * @return a printable representation of the board.
	 */
	@Override
	public String getPrintableBoard() {
		// Should return an empty string if the board has no pieces.
		String printedBoard = "";
		for (HantoCoordinate key : board.keySet()) {
			HantoPiece piece = board.get(key);
			printedBoard += piece.getColor() + " " + piece.getType() + ": ("
					+ key.getX() + ", " + key.getY() + ")\n";
		}
		return printedBoard;
	}

}
