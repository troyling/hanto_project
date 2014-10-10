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

import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentAJRZL.common.BaseHantoGame;

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
	protected void validateAllowedPieceType(HantoPieceType pieceType) throws HantoException {
	}

}
