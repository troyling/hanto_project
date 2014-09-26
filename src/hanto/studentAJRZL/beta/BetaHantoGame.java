/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentAJRZL.beta;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentAJRZL.common.BaseHantoGame;

/**
 * Class for Beta hanto game
 * 
 * @author troyling
 * 
 */
public class BetaHantoGame extends BaseHantoGame {
	
	/**
	 * Constructor for beta hanto game
	 * 
	 * @param movesFirst
	 */
	public BetaHantoGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
	}

	/**
	 * This method executes a move in the game. It is called for every move that must be made.
	 * 
	 * @param pieceType the piece type that is being moved
	 * @param from the coordinate where the piece begins. If the coordinate is null, then the piece
	 *            begins off the board (that is, it is placed on the board in this move).
	 * @param to the coordinated where the piece is after the move has been made.
	 * @return the result of the move
	 * @throws HantoException if there are any problems in making the move (such as specifying a
	 *             coordinate that does not have the appropriate piece, or the color of the piece is
	 *             not the color of the player who is moving.
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		// ensure player is not placing other piece type onto the board
		validatePieceType(pieceType);

		// check if the game has already ended
		validateGameInProgress();

		// check if the destination coordinate is occupied
		validateDestinationCoordinate(to);

		// piece can only be placed, not moved
		validateMove(from);

		// first piece must be placed at origin
		validateFirstMoveCoordinate(to);

		// check if butterfly is placed
		validateBufferflyPresence(pieceType);
						
		return super.makeMove(pieceType, from, to);
	}

	/**
	 * Throws exception if the player attempts to place a piece other than butterfly or sparrow
	 * 
	 * @throws HantoException
	 */
	private void validatePieceType(HantoPieceType pieceType) throws HantoException {
		if (pieceType != HantoPieceType.BUTTERFLY && pieceType != HantoPieceType.SPARROW) {
			throw new HantoException("Can't place piece other than butterfly or sparrow.");
		}
	}
	
	/**
	 * Throw exception if butterfly is not placed by the end of 4th turn.
	 * 
	 * @param pieceType
	 * @throws HantoException
	 */
	private void validateBufferflyPresence(HantoPieceType pieceType) throws HantoException {
		if ((board.size() == 6 || board.size() == 7) && pieceType != HantoPieceType.BUTTERFLY) {
			if ((currentPlayColor == HantoPlayerColor.BLUE && blueButterflyCoordiate == null)
					|| (currentPlayColor == HantoPlayerColor.RED && redButterflyCoordiate == null)) {
				throw new HantoException("Butterfly must be placed by 4th turn.");
			}
		}
	}

	

	/**
	 * Throws exception if the move intends to move rather than to place a piece
	 * 
	 * @param from
	 * @throws HantoException
	 */
	private void validateMove(HantoCoordinate from) throws HantoException {
		if (from != null) {
			throw new HantoException("Can't move a piece in Beta hanto.");
		}
	}

	/**
	 * Throws exception if the destination coordinate is occupied.
	 * 
	 * @param to
	 * @throws HantoException
	 */
	private void validateDestinationCoordinate(HantoCoordinate coord) throws HantoException {
		// check if the given destination coordinate is occupied
		if (coord == null) {
			throw new HantoException("Piece must be placed on board");
		}
		
		if (board.get(coord) != null) {
			throw new HantoException("The given destination coordinate has been occupied.");
		}
	}

	

	/**
	 * Check the game status and return the result after a move
	 * 
	 * @return the result of a move
	 */
	@Override
	protected MoveResult checkGameStatus() {
		MoveResult result = MoveResult.DRAW;
		
		if (board.size() == 12) {
			result = MoveResult.DRAW;
		}

		return result;
	}

	

	
}
