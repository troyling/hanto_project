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

/**
 * Class for the Alpha Hanto Game instances.
 * 
 * @author anthonyjruffa
 * 
 */
public class AlphaHantoGame implements HantoGame {

	private HantoPlayerColor currentPlayercolor;
	private HantoCoordinate blueButterflyCoord;
	private HantoCoordinate redButterflyCoord;

	/**
	 * Constructor for the Alpha Hanto game.
	 * 
	 * @param currentPlayercolor
	 */
	public AlphaHantoGame(HantoPlayerColor currentPlayercolor) {
		this.currentPlayercolor = currentPlayercolor;
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		if (!pieceType.equals(HantoPieceType.BUTTERFLY)) {
			// The piece must be a butterfly.
			throw new HantoException("Invalid piece.");
		}

		if (from != null) {
			throw new HantoException("Can't move piece in alpha game.");
		}

		MoveResult result = null;

		switch (currentPlayercolor) {
			case BLUE:
				if (to.getX() != 0 || to.getY() != 0) {
					// The blue piece must be placed at the origin.
					throw new HantoException("Invalid move.");
				}

				blueButterflyCoord = to;

				// Change the color to red since it will be red's turn.
				currentPlayercolor = HantoPlayerColor.RED;
				result = MoveResult.OK;
				break;
			case RED:
				if (!((Math.abs(to.getX()) <= 1 && Math.abs(to.getY()) <= 1) && to.getX() != to
						.getY())) {
					// Throw an exception if the red piece is not placed in a valid space.
					throw new HantoException("Invalid move.");
				}

				redButterflyCoord = to;

				result = MoveResult.DRAW;
				break;
			default:
				break;
		}
		return result;
	}

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		HantoPiece piece = null;

		if (where.getX() == blueButterflyCoord.getX() && where.getY() == blueButterflyCoord.getY()) {
			piece = new HantoGamePiece(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY);
		} else if (where.getX() == redButterflyCoord.getX()
				&& where.getY() == redButterflyCoord.getY()) {
			piece = new HantoGamePiece(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY);
		}

		return piece;
	}

	@Override
	public String getPrintableBoard() {
		String printBoard = "";
		if (blueButterflyCoord != null && redButterflyCoord != null) {
			printBoard = "Blue Butterfly: (" + blueButterflyCoord.getX() + ", "
					+ blueButterflyCoord.getY() + ")\n" + "Red Butterfly: ("
					+ redButterflyCoord.getX() + ", " + redButterflyCoord.getY() + ")";
		}
		return printBoard;
	}
}
