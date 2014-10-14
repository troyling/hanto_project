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
import hanto.studentAJRZL.beta.BetaHantoGame;
import hanto.tournament.HantoMoveRecord;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Base Hanto Game class.
 * 
 * @author anthonyjruffa
 * 
 */
public abstract class BaseHantoGame implements HantoGame {
	// Constants
	protected int MAX_TURN = Integer.MAX_VALUE;
	protected int MAX_WALKING_DISTANCE = 1;
	protected int MAX_FLYING_DISTANCE = Integer.MAX_VALUE;
	
	// pieces allowed
	protected Map<HantoPieceType, Integer> maxPiecesAllowed;
	protected Map<HantoCoordinate, HantoPiece> board;
	protected Map<HantoPieceType, Integer> redPieces;
	protected Map<HantoPieceType, Integer> bluePieces;

	protected HantoCoordinate blueButterflyCoordinate;
	protected HantoCoordinate redButterflyCoordinate;
	protected HantoPlayerColor currentPlayerColor;
	protected HantoPlayerColor movesFirst;
	protected boolean isGameEnded = false;
	protected int numTurns;

	/**
	 * Constructor for Base hanto game
	 * 
	 * @param movesFirst
	 */
	protected BaseHantoGame(HantoPlayerColor movesFirst) {
		this.movesFirst = movesFirst;
		currentPlayerColor = movesFirst;
		board = new HashMap<HantoCoordinate, HantoPiece>();
		numTurns = 1;
		maxPiecesAllowed = initPiecesAllowedOnBoard();
		redPieces = initPiecesAllowedOnBoard();
		bluePieces = initPiecesAllowedOnBoard();
	}
	
	/**
	 * 
	 * @return Initialized map with all pieces allowed set to zero
	 */
	private Map<HantoPieceType, Integer> initPiecesAllowedOnBoard() {
		Map<HantoPieceType, Integer> map = new HashMap<HantoPieceType, Integer>();
		map.put(HantoPieceType.BUTTERFLY, 0);
		map.put(HantoPieceType.SPARROW, 0);
		map.put(HantoPieceType.CRAB, 0);
		map.put(HantoPieceType.CRANE, 0);
		map.put(HantoPieceType.DOVE, 0);
		map.put(HantoPieceType.HORSE, 0);
		return map;
	}

	/**
	 * Set the turn number
	 * 
	 * @param num the number to set it to
	 */
	public void setTurnNumber(int num) {
		numTurns = num;
	}

	/**
	 * Set the current player color
	 * 
	 * @param Color for the current player
	 */
	public void setCurrentPlayerColor(HantoPlayerColor currentPlayerColor) {
		this.currentPlayerColor = currentPlayerColor;
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
		performMove(pieceType, from, to);
		postMakeMoveCheck();
		alterPlayerTurn();
		return checkGameStatus();
	}

	private void performMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) {
		final HantoCoordinate toCoord = new HantoPieceCoordinate(to);
		if (from == null) {
			// placing piece
			if (currentPlayerColor == HantoPlayerColor.BLUE) {
				if (pieceType == HantoPieceType.BUTTERFLY) {
					blueButterflyCoordinate = toCoord;
				}
				bluePieces.put(pieceType, bluePieces.get(pieceType) + 1);
			} else {
				if (pieceType == HantoPieceType.BUTTERFLY) {
					redButterflyCoordinate = toCoord;
				}
				redPieces.put(pieceType, redPieces.get(pieceType) + 1);
			}
		}
		placePiece(pieceType, currentPlayerColor, to);	
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
	 * This method is used to place a piece on board
	 * 
	 * @param pieceType
	 * @param player
	 * @param to
	 */
	public void placeHantoPieceOnBoard(HantoPieceType pieceType, HantoPlayerColor player,
			HantoCoordinate to) {
		// create objects to store into the board
		final HantoCoordinate toCoord = new HantoPieceCoordinate(to);
		// store the coordinate if the piece is butterfly
		if (player == HantoPlayerColor.BLUE) {
			if (pieceType == HantoPieceType.BUTTERFLY) {
				blueButterflyCoordinate = toCoord;
			}
			bluePieces.put(pieceType, bluePieces.get(pieceType) + 1);
		} else {
			if (pieceType == HantoPieceType.BUTTERFLY) {
				redButterflyCoordinate = toCoord;
			}
			redPieces.put(pieceType, redPieces.get(pieceType) + 1);
		}
		placePiece(pieceType, player, to);
	}
	
	/**
	 * Actually put the piece on board
	 * 
	 * @param pieceType
	 * @param player
	 * @param to
	 */
	private void placePiece(HantoPieceType pieceType, HantoPlayerColor player,
			HantoCoordinate to) {
		final HantoPiece newPiece = new HantoGamePiece(player, pieceType);
		final HantoCoordinate toCoord = new HantoPieceCoordinate(to);
		board.put(toCoord, newPiece);
	}

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
		// places are not allowed to place next to the other players' after Beta
		if (numTurns > 1 && from == null && !(this instanceof BetaHantoGame)) {
			validatePiecePlacedNextToOwnColor(to);
		}
		validateGameInProgress();
		validateDestinationCoordinate(to);
		validateFirstMoveCoordinate(to);
		validateAdjacentCoordinate(to);
		if (from != null) {
			// moving a piece
			validateMovingPiece(pieceType, from, to);
			board.remove(new HantoPieceCoordinate(from));
		} else {
			// placing a piece
			validateAllowedPieceType(pieceType);
		}
	}

	/**
	 * This function can be overridden by subclasses to add any necessary validation after making
	 * the move. The overridden function should use super() to run the default validations.
	 * 
	 * @throws HantoException
	 */
	protected void postMakeMoveCheck() throws HantoException {
		validatePiecesAreContiguous();
	}

	/**
	 * Validate if the player can place the given piece
	 * 
	 * @param pieceType
	 * @throws HantoException
	 */
	private void validateAllowedPieceType(HantoPieceType pieceType) throws HantoException {
		boolean isAllowed = true;
		if (currentPlayerColor == HantoPlayerColor.BLUE) {
			isAllowed = bluePieces.get(pieceType) + 1 <= maxPiecesAllowed.get(pieceType);
		} else {
			isAllowed = redPieces.get(pieceType) + 1 <= maxPiecesAllowed.get(pieceType);
		}
		
		if (!isAllowed) {
			throw new HantoException("The piece you are trying to place is not allowed.");
		}
	}
			

	/**
	 * Check if player's attempt to move a piece is valid
	 * 
	 * @param pieceType
	 * @param from
	 * @param newPiece
	 * @param toCoord
	 * @return true if valid; false otherwise.
	 * 
	 * @throws HantoException
	 */
	private void validateMovingPiece(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		// moving a piece
		final HantoCoordinate toCoord = new HantoPieceCoordinate(to);
		final HantoCoordinate fromCoord = new HantoPieceCoordinate(from);
		final HantoPiece newPiece = new HantoGamePiece(currentPlayerColor, pieceType);

		validatePieceAtFromCoordinate(fromCoord, newPiece);
		validateWalkDistance(pieceType, fromCoord, toCoord);
	}
	
	/**
	 * Check if the walk is within the allowed distance
	 * 
	 * @param pieceType
	 * @param fromCoord
	 * @param toCoord
	 * @throws HantoException
	 */
	private void validateWalkDistance(HantoPieceType pieceType, HantoCoordinate fromCoord,
			HantoCoordinate toCoord) throws HantoException {
		final int distance = ((HantoPieceCoordinate) fromCoord).getDistanceTo(toCoord);
		if (isPieceAllowedToFly(pieceType)) {
			if (distance > MAX_FLYING_DISTANCE) {
				throw new HantoException(
						"Invalid fly.");
			}
		} else if (isPieceAllowedToJump(pieceType)) {
			if (!isLineContiguousTo(fromCoord, toCoord)) {
				throw new HantoException("Invalid jump");
			}
		} else {
			// walking
			if (fromCoord != null && toCoord != null) {
				validateWalk(fromCoord, toCoord);
			}
			
			if (distance > MAX_WALKING_DISTANCE) {
				throw new HantoException("Can't walk further than allowed distance.");
			}	
		}
	}

	/**
	 * Determine if the piece is allowed to fly. By default is false for all pieces, hanto game
	 * variant which allows flying should override this method.
	 * 
	 * @param pieceType the piece type
	 * @return true if so; false otherwise
	 */
	protected boolean isPieceAllowedToFly(HantoPieceType pieceType) {
		return false;
	}
	
	/**
	 * Determine if the piece is allowed to jump. By default is false for all pieces, hanto game
	 * variant which allows flying should override this method.
	 * 
	 * @param pieceType the piece type
	 * @return true if so; false otherwise
	 */
	protected boolean isPieceAllowedToJump(HantoPieceType pieceType) {
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
					"The piece you are trying to move does not match to the one you provided.");
		}
	}

	/**
	 * Check if the given pieces are the same
	 * 
	 * @param pieceOnBoard
	 * @param piece
	 * @return true if two pieces are equal. False otherwise.
	 */
	private boolean isPieceEqual(HantoPiece piece1, HantoPiece piece2) {
		return piece1.getColor() == piece2.getColor() && piece1.getType() == piece2.getType();
	}

	/**
	 * Check the game status and return the result after a move
	 * 
	 * @return the result of a move
	 */
	protected MoveResult checkGameStatus() {
		MoveResult result = MoveResult.OK;

		if (numTurns > MAX_TURN) {
			result = MoveResult.DRAW;
		}

		if (isPieceBeingSurrounded(blueButterflyCoordinate)) {
			result = MoveResult.RED_WINS;
		}

		if (isPieceBeingSurrounded(redButterflyCoordinate)) {
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
	private void alterPlayerTurn() throws HantoException {
		if (currentPlayerColor == HantoPlayerColor.BLUE) {
			currentPlayerColor = HantoPlayerColor.RED;
		} else {
			currentPlayerColor = HantoPlayerColor.BLUE;
		}
		// Increment the number of turns if the first player is moving again.
		if (currentPlayerColor == movesFirst) {
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
		final HantoPieceCoordinate c = new HantoPieceCoordinate(coord);
		if (!board.isEmpty() && !isAnyPieceAdjacentTo(c)) {
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
		if (coord == null) {
			throw new HantoException("Piece must be placed somewhere on board");
		}
		
		final HantoPieceCoordinate c = new HantoPieceCoordinate(coord);
		if (board.get(c) != null) {
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
		if (!isBoardContiguous(board)) {
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
	 * Check if the walk is valid
	 * 
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	protected void validateWalk(HantoCoordinate from, HantoCoordinate to) throws HantoException {
		boolean isWalkValid = false;
		HantoPieceCoordinate fromCoord = new HantoPieceCoordinate(from);
		HantoPieceCoordinate toCoord = new HantoPieceCoordinate(to);
		Collection<HantoCoordinate> commonNeighbors = fromCoord.getCommonNeighbors(toCoord);

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

	/**
	 * Check if the current player intends to resign
	 * 
	 * @param pieceType
	 * @param from
	 * @param to
	 * @return true if so; false otherwise
	 */
	protected boolean isPlayerResigning(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) {
		return pieceType == null && from == null && to == null;
	}

	/**
	 * Determine if the current player can place any piece on board
	 * 
	 * @return true if so; false otherwise
	 */
	protected boolean isCurrentPlayerAllowedToPlacePiece() {
		return getCurrentPlayerNumPiecesOnBoard() < getMaxNumPieceOnBoard();
	}

	/**
	 * Determine if the current player can move any of his pieces on board
	 * 
	 * @return true if so; false otherwise
	 */
	protected boolean isCurrentPlayerAllowedToMoveAnyPiece() {
		Map<HantoCoordinate, HantoPiece> testBoard;

		for (HantoCoordinate c : board.keySet()) {
			HantoPiece p = board.get(c);
			if (p.getColor() == currentPlayerColor) {
				// check if the board would remain contiguous when the piece is
				// removed from the board
				testBoard = new HashMap<HantoCoordinate, HantoPiece>(board);
				testBoard.remove(c);
				if (isBoardContiguous(testBoard)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @return all the coordinates of pieces that are able to move without causing disconneciton
	 */
	private Collection<HantoCoordinate> getMoveablePieceCoords() {
		Collection<HantoCoordinate> moveablePieces = new ArrayList<HantoCoordinate>();
		Map<HantoCoordinate, HantoPiece> testBoard;

		for (HantoCoordinate c : board.keySet()) {
			HantoPiece p = board.get(c);
			if (p.getColor() == currentPlayerColor) {
				// check if the board would remain contiguous when the piece is
				// removed from the board
				testBoard = new HashMap<HantoCoordinate, HantoPiece>(board);
				testBoard.remove(c);
				if (isBoardContiguous(testBoard)) {
					moveablePieces.add(c);
				}
			}
		}
		return moveablePieces;
	}

	/**
	 * Determine if the given board is contiguous
	 * 
	 * @param testBoard
	 * @return true if it is contiguous; false otherwise
	 */
	private boolean isBoardContiguous(Map<HantoCoordinate, HantoPiece> testBoard) {
		HantoCoordinate start = null;
		boolean isContiguous = false;

		for (HantoCoordinate coord : testBoard.keySet()) {
			start = coord;
			break;
		}

		Collection<HantoCoordinate> visited = new LinkedList<HantoCoordinate>();
		Queue<HantoCoordinate> queue = new LinkedList<HantoCoordinate>();

		visited.add(start);
		queue.add(start);

		while (!queue.isEmpty()) {
			HantoPieceCoordinate c = (HantoPieceCoordinate) queue.poll();

			for (HantoCoordinate coord : c.getAdjacentCoordinates()) {
				if (testBoard.get(coord) != null) {
					if (!visited.contains(coord)) {
						visited.add(coord);
						queue.add(coord);
					}
				}
			}
		}

		if (visited.size() == testBoard.size()) {
			isContiguous = true;
		}
		return isContiguous;
	}

	/**
	 * Return the total number of pieces the current player has placed
	 * 
	 * @return Number of pieces the current player placed
	 */
	private int getCurrentPlayerNumPiecesOnBoard() {
		int numPieces = 0;
		for (HantoCoordinate c : board.keySet()) {
			HantoPiece p = board.get(c);
			if (p.getColor() == currentPlayerColor) {
				numPieces++;
			}
		}
		return numPieces;
	}

	/**
	 * Return the maximum number of pieces each player can place on board
	 * 
	 * @return number of pieces player can place
	 */
	private int getMaxNumPieceOnBoard() {
		int max = 0;
		for (HantoPieceType t : maxPiecesAllowed.keySet()) {
			max += maxPiecesAllowed.get(t);
		}
		return max;
	}
	
	/**
	 * Determine if the given hex coordinates are on the same line 
	 * and the coordinates between them are all occupied by pieces.
	 * 
	 * @param from
	 * @param to
	 * @return true if so; false otherwise
	 */
	protected boolean isLineContiguousTo(HantoCoordinate from, HantoCoordinate to) {
		boolean isContiguous = false;
		final HantoPieceCoordinate fromCoord = new HantoPieceCoordinate(from);
		final Collection<HantoCoordinate> coordsOnLine = fromCoord.getCoordOnTheLineTo(to);
		
		// only check when the the coordinates are on the same line
		if (fromCoord.getDistanceTo(to) > 1 && coordsOnLine.size() > 0) {
			isContiguous = true;
			for (HantoCoordinate c : coordsOnLine) {
				if (board.get(c) == null) {
					isContiguous = false;
					break;
				}
			}
		}
		
		return isContiguous;
	}
	
	/**
	 * Check if the player is placing the piece only next to its own
	 * 
	 * @param to
	 * @throws HantoException
	 */
	private void validatePiecePlacedNextToOwnColor(HantoCoordinate to) throws HantoException {
		HantoPieceCoordinate toCoord = new HantoPieceCoordinate(to);
		for (HantoCoordinate c : toCoord.getAdjacentCoordinates()) {
			if (board.get(c) != null && board.get(c).getColor() != currentPlayerColor) {
				throw new HantoException("You must not place a piece adjacent to other player's.");
			}
		}
	}
	
	public HantoMoveRecord getPossibleMove() {
		HantoMoveRecord move = new HantoMoveRecord(null, null, null);
		
		if (!isCurrentPlayerAllowedToMoveAnyPiece() && !isCurrentPlayerAllowedToPlacePiece()) {
			return move;
		} else if (isCurrentPlayerAllowedToMoveAnyPiece()) {
			Collection<HantoCoordinate> fromCoords = getMoveablePieceCoords();
			Collection<HantoCoordinate> toCoords = getUnoccupiedCoords();
			for (HantoCoordinate from : fromCoords) {
				for (HantoCoordinate to : toCoords) {
					try {
						makeMove(getPieceAt(from).getType(), from, to);
						return new HantoMoveRecord(getPieceAt(from).getType(), from, to);
					} catch (HantoException e) {
						// do nothing
					}
				}
			}
		} else if (isCurrentPlayerAllowedToPlacePiece()) {
			// check to see what pieces are still available
			// should have priority for the types
			// look up coordinates
			// add to the moves
			
		}
		return move;
	}

	/**
	 * @return All unoccupied coordinates which are adjacent to the pieces on board
	 */
	private Collection<HantoCoordinate> getUnoccupiedCoords() {
		Collection<HantoCoordinate> coords = new ArrayList<HantoCoordinate>();
		Collection<HantoCoordinate> visited = new ArrayList<HantoCoordinate>();
		for (HantoCoordinate c : board.keySet()) {
			if (!visited.contains(c)) {
				visited.add(c);
				// check if the neighbors are occupied
				Collection<HantoCoordinate> neighbors = ((HantoPieceCoordinate) c).getAdjacentCoordinates();
				for (HantoCoordinate neighbor : neighbors) {
					visited.add(neighbor); // speed up the looping
					if (board.get(neighbor) == null) {
						coords.add(neighbor);
					}
				}
			}
		}
		return coords;
	}
}
