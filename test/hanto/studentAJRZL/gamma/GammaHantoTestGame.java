package hanto.studentAJRZL.gamma;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoTestGame;
import hanto.common.HantoTestGameFactory;
import hanto.common.MoveResult;

public class GammaHantoTestGame implements HantoTestGame {
	HantoTestGame gamma = HantoTestGameFactory.getInstance().makeHantoTestGame(HantoGameID.GAMMA_HANTO);

	/**
	 * Constructor of the GammaHantoTestGame
	 */
	public GammaHantoTestGame(HantoPlayerColor movesFirst) {
		// TODO fill in this later
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		return gamma.makeMove(pieceType, from, to);
	}

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return gamma.getPieceAt(where);
	}

	@Override
	public String getPrintableBoard() {
		return gamma.getPrintableBoard();
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
