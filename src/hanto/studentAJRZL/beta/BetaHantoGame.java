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
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentAJRZL.common.HantoGamePiece;
import hanto.studentAJRZL.common.HantoPieceCoordinate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for Beta hanto game
 * 
 * @author troyling
 * 
 */
public class BetaHantoGame implements HantoGame {

	private HantoPlayerColor currentPlayColor;
	private HantoCoordinate blueButterflyCoordiate;
	private HantoCoordinate redButterflyCoordiate;
	private boolean isGameEnded = false;

	private Map<HantoCoordinate, HantoPiece> board = new HashMap<HantoCoordinate, HantoPiece>();

	/**
	 * Constructor for beta hanto game
	 * 
	 * @param movesFirst
	 */
	public BetaHantoGame(HantoPlayerColor movesFirst) {
		currentPlayColor = movesFirst;
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
		String printedBoard = "";
		for (HantoCoordinate key : board.keySet()) {
			HantoPiece piece = board.get(key);
			printedBoard += piece.getColor() + " " + piece.getType() + ": (" + key.getX() + ", "
					+ key.getY() + ")\n";
		}
		return printedBoard;
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

		// create objects to store into the board
		HantoPiece newPiece = new HantoGamePiece(currentPlayColor, pieceType);
		HantoCoordinate coord = new HantoPieceCoordinate(to.getX(), to.getY());

		// check if the given destination coordinate is adjacent to any piece on the board
		validateAdjacentCoordinate(coord);

		// putting the piece on board
		board.put(coord, newPiece);

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

		// change player color in preparation for next move
		alterPlayerColor();

		return checkGameStatus();
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
	 * Throws exception if the player attempts to make an action after the game ends
	 * 
	 * @throws HantoException
	 */
	private void validateGameInProgress() throws HantoException {
		if (isGameEnded) {
			throw new HantoException("Can't place a piece after the game is ended.");
		}
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
	 * Throws exception if the first move attempts to place piece at coordinate other than origin.
	 * 
	 * @param to
	 * @throws HantoException
	 */
	private void validateFirstMoveCoordinate(HantoCoordinate to) throws HantoException {
		if (board.size() == 0 && currentPlayColor == HantoPlayerColor.BLUE) {
			if (to.getX() != 0 || to.getY() != 0) {
				throw new HantoException("First piece must be placed at origin");
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
	 * Check if there is any piece adjacent to the given coordinate
	 * 
	 * @param coord
	 * @return true if there is. False otherwise
	 */
	private boolean isAnyPieceAdjacentTo(HantoCoordinate coord) {
		boolean result = false;
		Collection<HantoCoordinate> adjacentTiles = ((HantoPieceCoordinate) coord)
				.getAdjacentCoordinates();

		for (HantoCoordinate tile : adjacentTiles) {
			if (getPieceAt(tile) != null) {
				result = true;
			}
		}

		return result;
	}

	/**
	 * Check the game status and return the result after a move
	 * 
	 * @return the result of a move
	 */
	private MoveResult checkGameStatus() {
		MoveResult result = MoveResult.OK;

		if (isButterflyBeingSurrounded(blueButterflyCoordiate)) {
			result = MoveResult.RED_WINS;
		}

		if (isButterflyBeingSurrounded(redButterflyCoordiate)) {
			result = MoveResult.BLUE_WINS;
		}

		if (board.size() == 12) {
			result = MoveResult.DRAW;
		}

		// check if game ends
		if (result != MoveResult.OK) {
			isGameEnded = true;
		}

		return result;
	}

	/**
	 * Check if the given butterfly is surrounded
	 * 
	 * @param blueButterflyCoordiate
	 * @return true if it is surrounded; false otherwise.
	 */
	private boolean isButterflyBeingSurrounded(HantoCoordinate butterflyCoordiate) {
		boolean isSurrounded = true;

		if (butterflyCoordiate != null) {
			Collection<HantoCoordinate> adjacentTiles = ((HantoPieceCoordinate) butterflyCoordiate)
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

	/**
	 * Alternate the player color for next move
	 * 
	 * @throws HantoException
	 */
	private void alterPlayerColor() throws HantoException {
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
}
