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
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPrintableBoard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initializeBoard(PieceLocationPair[] initialPieces) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTurnNumber(int turnNumber) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPlayerMoving(HantoPlayerColor player) {
		// TODO Auto-generated method stub

	}

}
