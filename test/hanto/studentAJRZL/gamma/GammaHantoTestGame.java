package hanto.studentAJRZL.gamma;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoTestGame;
import hanto.common.MoveResult;
import hanto.studentAJRZL.HantoGameFactory;

public class GammaHantoTestGame implements HantoTestGame {
	HantoGame testGame;

	/**
	 * Constructor of the GammaHantoTestGame
	 */
	public GammaHantoTestGame(HantoPlayerColor movesFirst) {
		testGame = HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO, movesFirst);
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		return testGame.makeMove(pieceType, from, to);
	}

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return testGame.getPieceAt(where);
	}

	@Override
	public String getPrintableBoard() {
		return testGame.getPrintableBoard();
	}

	@Override
	public void initializeBoard(PieceLocationPair[] initialPieces) {
		for(PieceLocationPair p : initialPieces) {
			try {
				makeMove(p.pieceType, null, p.location);
			} catch (HantoException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setTurnNumber(int turnNumber) {
	}

	@Override
	public void setPlayerMoving(HantoPlayerColor player) {
	}
}
