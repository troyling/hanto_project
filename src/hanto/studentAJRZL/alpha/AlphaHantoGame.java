/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentAJRZL.alpha;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentAJRZL.common.HantoGamePiece;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for the Alpha Hanto Game instances.
 * 
 * @author anthonyjruffa
 * 
 */
public class AlphaHantoGame implements HantoGame {

	private HantoPlayerColor color;
	private Map<HantoCoordinate, HantoGamePiece> board = new HashMap<HantoCoordinate, HantoGamePiece>();

	/**
	 * Constructor for the Alpha Hanto game.
	 * 
	 * @param color
	 */
	public AlphaHantoGame(HantoPlayerColor color) {
		this.color = color;
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		board.put(to, new HantoGamePiece(color, pieceType));
		switch (color) {
			case BLUE:
				color = HantoPlayerColor.RED;
				return MoveResult.OK;
			case RED:
				return MoveResult.DRAW;
			default:
				throw new HantoException("Invalid piece.");
		}
	}

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return board.get(where);
	}

	@Override
	public String getPrintableBoard() {
		return null;
	}
}
