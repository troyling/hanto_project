/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentAJRZL.gamma;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentAJRZL.common.BaseHantoGame;
import hanto.studentAJRZL.common.HantoPieceCoordinate;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Gamma Hanto Game class.
 * 
 * @author anthonyjruffa
 * 
 */
public class GammaHantoGame extends BaseHantoGame {
	private final int MAX_BOARD_SIZE = 20;

	public GammaHantoGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getMaxBoardSize() {
		return MAX_BOARD_SIZE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void preMakeMoveCheck(HantoPieceType pieceType,
			HantoCoordinate from, HantoCoordinate to) throws HantoException {
		if (from != null && to != null) {
			validateCanWalk(from, to);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getAllowedWalkingDistance() {
		return 1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void postMakeMoveCheck() throws HantoException {
		// validatePiecesAreContiguous();
	}

	private void validateCanWalk(HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		boolean isWalkValid = false;
		HantoPieceCoordinate fromCoord = new HantoPieceCoordinate(from.getX(),
				from.getY());
		HantoPieceCoordinate toCoord = new HantoPieceCoordinate(to.getX(),
				to.getY());
		Collection<HantoCoordinate> commonNeighbors = new LinkedList<HantoCoordinate>();
		Collection<HantoCoordinate> fromNeighbors = fromCoord
				.getAdjacentCoordinates();
		Collection<HantoCoordinate> toNeighbors = toCoord
				.getAdjacentCoordinates();

		// find out common tiles
		for (HantoCoordinate coord : fromNeighbors) {
			if (toNeighbors.contains(coord)) {
				commonNeighbors.add(coord);
			}
		}

		// check if either neighbor is not occupied
		for (HantoCoordinate coord : commonNeighbors) {
			if (board.get((HantoPieceCoordinate) coord) == null) {
				isWalkValid = true;
			}
		}

		if (!isWalkValid) {
			throw new HantoException("Walk is not valid.");
		}
	}

}
