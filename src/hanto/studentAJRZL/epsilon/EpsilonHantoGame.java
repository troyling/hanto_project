/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentAJRZL.epsilon;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoPrematureResignationException;
import hanto.common.MoveResult;
import hanto.studentAJRZL.common.BaseHantoGame;
import hanto.studentAJRZL.common.HantoPieceCoordinate;

/**
 * Class for the epsilon hanto game.
 * 
 * @author anthonyjruffa
 * 
 */
public class EpsilonHantoGame extends BaseHantoGame {

	/**
	 * Constructor for delta hanto game
	 * 
	 * @param movesFirst
	 */
	public EpsilonHantoGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
		NUM_BUTTERFLY_ALLOWED = 1;
		NUM_SPARROW_ALLOWED = 2;
		NUM_CRAB_ALLOWED = 6;
		NUM_HORSE_ALLOWED = 4;
	}

	@Override
	protected void validateAllowedPieceType(HantoPieceType pieceType)
			throws HantoException {
		if (pieceType != HantoPieceType.BUTTERFLY
				&& pieceType != HantoPieceType.SPARROW
				&& pieceType != HantoPieceType.CRAB
				&& pieceType != HantoPieceType.HORSE) {
			throw new HantoException(
					"The piece you are trying to place is not allowed.");
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isPieceAllowedToFly(HantoPieceType pieceType) {
		return pieceType == HantoPieceType.SPARROW;
	}
	
	/**
	 * {@inheritDoc}
	 */
	protected int getAllowedWalkingDistance() {
		return 1;
	}
	
	/**
	 * Validate if the jumping is valid
	 * Jumping is valid when all of the followings are true
	 * 1) both given coordinates are on the same line
	 * 2) other coordinates on the line between the given ones must be occupied
	 * 3) distance between the given coordinates must be less than 5
	 * 
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
//	private void validateJumping(HantoCoordinate from, HantoCoordinate to)
//			throws HantoException {
//		final HantoPieceCoordinate fromCoord = new HantoPieceCoordinate(from);
//		final HantoPieceCoordinate toCoord = new HantoPieceCoordinate(to);
//		if (!isLineContiguousTo(fromCoord, toCoord)) {
//			throw new HantoException("Jump invalid");
//		}
//	}
	
	/**
	 * {@inheritDoc}
	 */
	protected boolean isPieceAllowedToJump(HantoPieceType pieceType) {
		return pieceType == HantoPieceType.HORSE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getAllowedFlyingDistance() {
		return 4;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		MoveResult result = MoveResult.OK;
		if (isPlayerResigning(pieceType, from, to)) {
			// check for premature resignation
			validateResignation(pieceType, from, to);
			if (currentPlayerColor == HantoPlayerColor.BLUE) {
				result = MoveResult.RED_WINS;
			} else {
				result = MoveResult.BLUE_WINS;
			}
		} else {
			result = super.makeMove(pieceType, from, to);
		}
		return result;
	}

	/**
	 * Check if the player is resigning at the right time. According to the
	 * rule, a player can resign only when 1) the player can't place any
	 * additional piece onto the board 2) moving any of the player's pieces will
	 * result into disconnection
	 * 
	 * @param pieceType
	 * @param from
	 * @param to
	 * @throws HantoPrematureResignationException
	 */
	private void validateResignation(HantoPieceType pieceType,
			HantoCoordinate from, HantoCoordinate to)
			throws HantoPrematureResignationException {

		if (isCurrentPlayerAllowedToPlacePiece()
				|| isCurrentPlayerAllowedToMoveAnyPiece()) {
			throw new HantoPrematureResignationException();
		}
	}

}
