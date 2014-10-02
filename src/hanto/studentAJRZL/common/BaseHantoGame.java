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
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

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
	protected HantoPlayerColor movesFirst;
	protected boolean isGameEnded = false;
	protected Map<HantoCoordinate, HantoPiece> board;
	protected int numTurns;

	/**
	 * Constructor for Base hanto game
	 * 
	 * @param movesFirst
	 */
	protected BaseHantoGame(HantoPlayerColor movesFirst) {
		this.movesFirst = movesFirst;
		currentPlayColor = movesFirst;
		board = new HashMap<HantoCoordinate, HantoPiece>();
		numTurns = 0;
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
		preMakeMoveCheck(pieceType, from, to);
		movePiece(pieceType, from, to);
		postMakeMoveCheck();
		alterPlayerTurn();
		return checkGameStatus();
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
	 * Get the maximum number of pieces can placed on the board for a hanto game
	 * 
	 * @return the max size of the board
	 */
	protected abstract int getMaxNumPiecesOnBoard();

	/**
	 * This function can be overridden by subclasses to add any necessary validation before actually
	 * making the move. The overridden function should use super() to run the default validations.
	 * 
	 * @param pieceType
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	protected void preMakeMoveCheck(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		validateAllowedPieceType(pieceType);
	}

	/**
	 * This function can be overridden by subclasses to add any necessary validation after making
	 * the move. The overridden function should use super() to run the default validations.
	 * 
	 * @throws HantoException
	 */
	protected void postMakeMoveCheck() throws HantoException {
		validatePiecesAreContiguous();
		validateNumPieceOnBoard();
	}

	/**
	 * This function should be overridden by subclasses to check for piece types that are allowed in
	 * the game
	 * 
	 * @param pieceType
	 * @throws HantoException
	 */
	protected abstract void validateAllowedPieceType(HantoPieceType pieceType)
			throws HantoException;

	/**
	 * This function should be overridden by subclasses to return the distance a hanto piece is
	 * allowed to walk
	 * 
	 * @return the distance a hanto piece can walk.
	 */
	protected abstract int getAllowedWalkingDistance();

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
		// check if the game has already ended
		validateGameInProgress();

		if (to == null) {
			throw new HantoException("Piece must be placed somewhere on board");
		}

		// create objects to store into the board
		final HantoPiece newPiece = new HantoGamePiece(currentPlayColor, pieceType);
		final HantoCoordinate toCoord = new HantoPieceCoordinate(to.getX(), to.getY());
		HantoCoordinate fromCoord = null;

		// first piece must be placed at origin
		validateFirstMoveCoordinate(toCoord);

		// check if the destination coordinate is occupied
		validateDestinationCoordinate(toCoord);

		// check if the given destination coordinate is adjacent to any piece on
		// the board
		validateAdjacentCoordinate(toCoord);

		// check if user is moving a piece
		if (from != null) {
			fromCoord = new HantoPieceCoordinate(from.getX(), from.getY());

			// check if the piece we are moving is valid
			validatePieceAtFromCoordinate(fromCoord, newPiece);

			// check if the piece is moving at the allowed pace
			validateWalkDistance(pieceType, fromCoord, toCoord);

			// remove the piece at the original location
			board.remove(fromCoord);
		}

		// store the coordinate if the piece is butterfly
		if (pieceType == HantoPieceType.BUTTERFLY) {
			switch (currentPlayColor) {
				case BLUE:
					if (fromCoord == null) {
						// placing a piece
						validateButterflyExistence(blueButterflyCoordiate);
					}
					blueButterflyCoordiate = toCoord;
					break;
				case RED:
					if (fromCoord == null) {
						validateButterflyExistence(redButterflyCoordiate);
					}
					redButterflyCoordiate = toCoord;
					break;
				default:
					throw new HantoException("Invalid color.");
			}
		}
		// putting the piece on board
		board.put(toCoord, newPiece);
	}

	/**
	 * Check if the walk is within the allowed distance
	 * 
	 * @param pieceType
	 * @param fromCoord
	 * @param toCoord
	 * @throws HantoException
	 */
	private void validateWalkDistance(HantoPieceType pieceType,
			HantoCoordinate fromCoord, HantoCoordinate toCoord)
			throws HantoException {
		final int distance = ((HantoPieceCoordinate) fromCoord)
				.getDistanceTo(toCoord);
		if (distance > getAllowedWalkingDistance()) {
			if (!isPieceAllowedToFly(pieceType)) {
				throw new HantoException(
						"Can't walk further than allowed distance. Only sparrow can fly in delta hato game.");

			}
		}
	}

	/**
	 * Determine if the piece is allowed to fly. By default is false for all
	 * pieces, hanto game variant which allows flying should override this
	 * method.
	 * 
	 * @param pieceType
	 * @return true if so; flase otherwise
	 */
	protected boolean isPieceAllowedToFly(HantoPieceType pieceType) {
		return false;
	}

	/**
	 * Check if there is a piece with right type at this location
	 * 
	 * @param fromCoord
	 * @param newPiece
	 * @throws HantoException
	 */
	private void validatePieceAtFromCoordinate(HantoCoordinate coord, HantoPiece piece)
			throws HantoException {
		if (piece.getType() == null) {
			throw new HantoException("Piece must not be null.");
		}

		final HantoPiece pieceOnBoard = board.get(coord);

		if (pieceOnBoard == null) {
			throw new HantoException("There is no hanto piece at this location.");
		}

		if (!isPieceEqual(pieceOnBoard, piece)) {
			throw new HantoException(
					"The piece you are trying to move does not match to the one you provided. ");
		}
	}

	/**
	 * Check if the given pieces are the same
	 * 
	 * @param pieceOnBoard
	 * @param piece
	 * @return true if two pieces are equal. False otherwise.
	 */
	// TODO move this method to HantoPiece class
	private boolean isPieceEqual(HantoPiece piece1, HantoPiece piece2) {
		return piece1.getColor() == piece2.getColor() && piece1.getType() == piece2.getType();
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
	 * Check the game status and return the result after a move
	 * 
	 * @return the result of a move
	 */
	protected MoveResult checkGameStatus() {
		MoveResult result = MoveResult.OK;

		if (board.size() == getMaxNumPiecesOnBoard()
				&& isGameEndedAfterPlacingAllPieces()) {
			result = MoveResult.DRAW;
		}

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
	 * Determine if a game should end after all pieces allowed are placed on board
	 * 
	 * @return true if so; false otherwise
	 */
	private boolean isGameEndedAfterPlacingAllPieces() {
		// TODO this will contain more test as we allow pieces to fly and etc..
		return getAllowedWalkingDistance() == 0;
	}

	/**
	 * Alternate the player turn for next move
	 * 
	 * @throws HantoException
	 */
	private void alterPlayerTurn() throws HantoException {
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

		// Increment the number of turns if the first player is moving again.
		if (currentPlayColor == movesFirst) {
			numTurns++;
		}
	}

	/**
	 * Throws exception if the first move attempts to place piece at coordinate other than origin.
	 * 
	 * @param to
	 * @throws HantoException
	 */
	private void validateFirstMoveCoordinate(HantoCoordinate to) throws HantoException {
		if (board.size() == 0 && (to.getX() != 0 || to.getY() != 0)) {
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
	 * Throws exception if the destination coordinate is occupied.
	 * 
	 * @param to
	 * @throws HantoException
	 */
	private void validateDestinationCoordinate(HantoCoordinate coord) throws HantoException {
		if (board.get(coord) != null) {
			throw new HantoException("The given destination coordinate has been occupied.");
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
	 * Check if there is any piece adjacent to the given coordinate
	 * 
	 * @param coord
	 * @return true if there is. False otherwise
	 */
	private boolean isAnyPieceAdjacentTo(HantoCoordinate coord) {
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
	 * Check if all the pieces on board are contiguous
	 * 
	 * @throws HantoException
	 */
	private void validatePiecesAreContiguous() throws HantoException {
		HantoCoordinate start = null;

		for (HantoCoordinate coord : board.keySet()) {
			start = coord;
			break;
		}

		Collection<HantoCoordinate> visited = new LinkedList<HantoCoordinate>();
		Queue<HantoCoordinate> queue = new LinkedList<HantoCoordinate>();

		visited.add(start);
		queue.add(start);

		while (!queue.isEmpty()) {
			HantoPieceCoordinate c = (HantoPieceCoordinate) queue.poll();

			if (!visited.contains(c)) {
				visited.add(c);
			}

			for (HantoCoordinate coord : c.getAdjacentCoordinates()) {
				if (board.get(coord) != null) {
					if (!visited.contains(coord)) {
						visited.add(coord);
						queue.add(coord);
					}
				}
			}
		}

		if (board.size() != visited.size()) {
			throw new HantoException("Pieces are not contiguous");
		}
	}

	/**
	 * Check if the given butterfly is surrounded
	 * 
	 * @param pieceCoordinate
	 * @return true if it is surrounded; false otherwise.
	 */
	private boolean isPieceBeingSurrounded(HantoCoordinate pieceCoordinate) {
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

	/**
	 * Check if there are more pieces placed than allowed
	 * 
	 * @throws HantoException
	 */
	private void validateNumPieceOnBoard() throws HantoException {
		if (board.size() > getMaxNumPiecesOnBoard()) {
			throw new HantoException("You can't place more pieces than allowed");
		}
	}

}
