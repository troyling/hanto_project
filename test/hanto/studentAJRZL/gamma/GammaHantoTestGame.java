package hanto.studentAJRZL.gamma;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoTestGame;
import hanto.common.MoveResult;

public class GammaHantoTestGame implements HantoTestGame {

	/**
	 * Constructor of the GammaHantoTestGame
	 */
	public GammaHantoTestGame(HantoPlayerColor movesFirst) {
		// TODO fill in this later
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		return null;
	}

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return null;
	}

	@Override
	public String getPrintableBoard() {
		return null;
	}

	@Override
	public void initializeBoard(PieceLocationPair[] initialPieces) {
	}

	@Override
	public void setTurnNumber(int turnNumber) {
	}

	@Override
	public void setPlayerMoving(HantoPlayerColor player) {
	}
}
