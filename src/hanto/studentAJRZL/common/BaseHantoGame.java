/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentAJRZL.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Base Hanto Game class.
 * 
 * @author anthonyjruffa
 * 
 */
public abstract class BaseHantoGame implements HantoGame {
	protected HantoCoordinate blueButterflyCoordiate;
	protected HantoCoordinate redButterflyCoordiate;

	protected HantoPlayerColor currentPlayColor;
	protected boolean isGameEnded = false;
	protected Map<HantoCoordinate, HantoPiece> board;

	/**
	 * Constructor for Base hanto game
	 * 
	 * @param movesFirst
	 */
	protected BaseHantoGame(HantoPlayerColor movesFirst) {
		currentPlayColor = movesFirst;
		board = new HashMap<HantoCoordinate, HantoPiece>();
	}

	/**
	 * This method uses template method pattern to execute a move in the game. It is called for
	 * every move that must be made.
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
		movePiece(pieceType, from, to);
		alterPlayerTurn();
		return checkGameStatus();
	}

	/**
	 * Move the piece from the given source coordinate to the given destination coordinate
	 * 
	 * @param pieceType
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	private void movePiece(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		// create objects to store into the board
		HantoPiece newPiece = new HantoGamePiece(currentPlayColor, pieceType);
		HantoCoordinate coord = new HantoPieceCoordinate(to.getX(), to.getY());

		// check if the given destination coordinate is adjacent to any piece on the board
		validateAdjacentCoordinate(coord);

		// store the coordinate if the piece is butterfly
		if (pieceType == HantoPieceType.BUTTERFLY) {
			switch (currentPlayColor) {
				case BLUE:
					validateButterflyExistence(blueButterflyCoordiate);
					blueButterflyCoordiate = coord;
					break;
				case RED:
					validateButterflyExistence(redButterflyCoordiate);
					redButterflyCoordiate = coord;
					break;
				default:
					throw new HantoException("Invalid color.");
			}
		}

		// putting the piece on board
		board.put(coord, newPiece);
	}

	/**
	 * Throws exception if the player attempts to place more than one butterfly on board
	 * 
	 * @param butterflyCoordinate
	 * @throws HantoException
	 */
	private void validateButterflyExistence(HantoCoordinate butterflyCoordinate)
			throws HantoException {
		if (butterflyCoordinate != null) {
			throw new HantoException("Can't place more than one butterfly in beta hanto game.");
		}
	}

	/**
	 * @param where the coordinate to query
	 * @return the piece at the specified coordinate or null if there is no piece at that position
	 */
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		HantoCoordinate coord = new HantoPieceCoordinate(where.getX(), where.getY());
		return board.get(coord);
	}

	/**
	 * @return a printable representation of the board.
	 */
	@Override
	public String getPrintableBoard() {
		// Should return an empty string if the board has no pieces.
		String printedBoard = "";
		for (HantoCoordinate key : board.keySet()) {
			HantoPiece piece = board.get(key);
			printedBoard += piece.getColor() + " " + piece.getType() + ": (" + key.getX() + ", "
					+ key.getY() + ")\n";
		}
		return printedBoard;
	}

	/**
	 * Check the game status and return the result after a move
	 * 
	 * @return the result of a move
	 */
	protected MoveResult checkGameStatus() {
		MoveResult result = MoveResult.OK;

		if (isPieceBeingSurrounded(blueButterflyCoordiate)) {
			result = MoveResult.RED_WINS;
		}

		if (isPieceBeingSurrounded(redButterflyCoordiate)) {
			result = MoveResult.BLUE_WINS;
		}

		// check if game ends
		if (result != MoveResult.OK) {
			isGameEnded = true;
		}
		return result;
	}

	/**
	 * Alternate the player turn for next move
	 * 
	 * @throws HantoException
	 */
	protected void alterPlayerTurn() throws HantoException {
		switch (currentPlayColor) {
			case BLUE:
				currentPlayColor = HantoPlayerColor.RED;
				break;
			case RED:
				currentPlayColor = HantoPlayerColor.BLUE;
				break;
			default:
				throw new HantoException("Invalid player color");
		}
	}

	/**
	 * Throws exception if the first move attempts to place piece at coordinate other than origin.
	 * 
	 * @param to
	 * @throws HantoException
	 */
	protected void validateFirstMoveCoordinate(HantoCoordinate to) throws HantoException {
		if ((board.size() == 0 && currentPlayColor == HantoPlayerColor.BLUE)
				&& (to.getX() != 0 || to.getY() != 0)) {
			throw new HantoException("First piece must be placed at origin");
		}
	}

	/**
	 * Throws exception if the piece is not placed next to any piece
	 * 
	 * @param coord
	 * @throws HantoException
	 */
	private void validateAdjacentCoordinate(HantoCoordinate coord) throws HantoException {
		if (!board.isEmpty() && !isAnyPieceAdjacentTo(coord)) {
			throw new HantoException("A piece must be placed next to another.");
		}
	}

	/**
	 * Throws exception if the player attempts to make an action after the game ends
	 * 
	 * @throws HantoException
	 */
	protected void validateGameInProgress() throws HantoException {
		if (isGameEnded) {
			throw new HantoException("Can't place a piece after the game is ended.");
		}
	}

	/**
	 * Check if there is any piece adjacent to the given coordinate
	 * 
	 * @param coord
	 * @return true if there is. False otherwise
	 */
	protected boolean isAnyPieceAdjacentTo(HantoCoordinate coord) {
		Collection<HantoCoordinate> adjacentTiles = ((HantoPieceCoordinate) coord)
				.getAdjacentCoordinates();

		for (HantoCoordinate tile : adjacentTiles) {
			if (getPieceAt(tile) != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if the given butterfly is surrounded
	 * 
	 * @param pieceCoordinate
	 * @return true if it is surrounded; false otherwise.
	 */
	protected boolean isPieceBeingSurrounded(HantoCoordinate pieceCoordinate) {
		boolean isSurrounded = true;

		if (pieceCoordinate != null) {
			Collection<HantoCoordinate> adjacentTiles = ((HantoPieceCoordinate) pieceCoordinate)
					.getAdjacentCoordinates();

			for (HantoCoordinate coord : adjacentTiles) {
				if (getPieceAt(coord) == null) {
					isSurrounded = false;
				}
			}
		} else {
			isSurrounded = false;
		}

		return isSurrounded;
	}

}
