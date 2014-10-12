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

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.CRAB;
import static hanto.common.HantoPieceType.CRANE;
import static hanto.common.HantoPieceType.HORSE;
import static hanto.common.HantoPieceType.SPARROW;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;
import static hanto.common.MoveResult.BLUE_WINS;
import static hanto.common.MoveResult.OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoPrematureResignationException;
import hanto.common.HantoTestGame;
import hanto.common.HantoTestGame.PieceLocationPair;
import hanto.common.HantoTestGameFactory;
import hanto.common.MoveResult;
import hanto.studentAJRZL.HantoGameFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test cases for EpsilonHanto
 * 
 * @version Oct 11, 2014
 */
public class EpsilonHantoMasterTest {
	/**
	 * Internal class for these test cases.
	 * 
	 * @version Sep 13, 2014
	 */
	class TestHantoCoordinate implements HantoCoordinate {
		private final int x, y;

		public TestHantoCoordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}

		/*
		 * @see hanto.common.HantoCoordinate#getX()
		 */
		@Override
		public int getX() {
			return x;
		}

		/*
		 * @see hanto.common.HantoCoordinate#getY()
		 */
		@Override
		public int getY() {
			return y;
		}

	}

	private static HantoTestGameFactory factory;
	private HantoGame game;
	private HantoTestGame testGame;

	@BeforeClass
	public static void initializeClass() {
		factory = HantoTestGameFactory.getInstance();
	}

	@Before
	public void setup() {
		// By default, blue moves first.
		testGame = factory.makeHantoTestGame(HantoGameID.EPSILON_HANTO);
		game = testGame;
	}

	@Test
	public void bluePlacesButterflyFirst() throws HantoException {
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, piece.getColor());
		assertEquals(BUTTERFLY, piece.getType());
	}

	@Test
	public void redMovesFirstUsingHantoGameFactory() throws HantoException {
		game = HantoGameFactory.getInstance().makeHantoGame(HantoGameID.EPSILON_HANTO, RED);
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(RED, piece.getColor());
		assertEquals(BUTTERFLY, piece.getType());
	}

	@Test
	public void placeHorse() throws HantoException {
		final MoveResult mr = game.makeMove(HORSE, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, piece.getColor());
		assertEquals(HORSE, piece.getType());
	}

	@Test
	public void sparrowFliesFourSpaces() throws HantoException {
		final PieceLocationPair[] board = new PieceLocationPair[] {
				plPair(BLUE, BUTTERFLY, 0, 0), plPair(RED, BUTTERFLY, 0, 1),
				plPair(BLUE, SPARROW, 0, -1), plPair(RED, SPARROW, 0, 2)
		};
		testGame.initializeBoard(board);
		testGame.setPlayerMoving(BLUE);
		testGame.setTurnNumber(3);
		final MoveResult mr = game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(0, 3));
		assertEquals(OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(0, 3));
		assertEquals(BLUE, piece.getColor());
		assertEquals(SPARROW, piece.getType());
		assertNull(game.getPieceAt(makeCoordinate(0, -1)));
	}

	@Test(expected = HantoException.class)
	public void sparrowTriesToFlyTooFar() throws HantoException {
		final PieceLocationPair[] board = new PieceLocationPair[] {
				plPair(BLUE, BUTTERFLY, 0, 0), plPair(RED, BUTTERFLY, 0, 1),
				plPair(BLUE, SPARROW, 0, -1), plPair(RED, SPARROW, 0, 2), plPair(BLUE, CRAB, 0, 3)
		};
		testGame.initializeBoard(board);
		testGame.setPlayerMoving(BLUE);
		testGame.setTurnNumber(3);
		game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(0, 4));
	}

	@Test(expected = HantoException.class)
	public void movePieceNotInGame() throws HantoException {
		game.makeMove(CRANE, null, makeCoordinate(0, 0));
	}

	@Test
	public void horseJumps() throws HantoException {
		final PieceLocationPair[] board = new PieceLocationPair[] {
				plPair(BLUE, BUTTERFLY, 0, 0), plPair(RED, BUTTERFLY, 0, 1),
				plPair(BLUE, HORSE, 0, -1), plPair(RED, SPARROW, 0, 2)
		};
		testGame.initializeBoard(board);
		testGame.setPlayerMoving(BLUE);
		testGame.setTurnNumber(3);
		assertEquals(OK, game.makeMove(HORSE, makeCoordinate(0, -1), makeCoordinate(0, 3)));
		final HantoPiece piece = game.getPieceAt(makeCoordinate(0, 3));
		assertEquals(HORSE, piece.getType());
		assertEquals(BLUE, piece.getColor());
		assertNull(game.getPieceAt(makeCoordinate(0, -1)));
	}

	@Test(expected = HantoException.class)
	public void horseTriesToJumpAGap() throws HantoException {
		final PieceLocationPair[] board = new PieceLocationPair[] {
				plPair(BLUE, BUTTERFLY, 0, 0), plPair(RED, BUTTERFLY, -1, 1),
				plPair(BLUE, HORSE, 1, -1), plPair(RED, SPARROW, -3, 3),
				plPair(BLUE, SPARROW, -2, 1), plPair(RED, SPARROW, -3, 2)
		};
		testGame.initializeBoard(board);
		testGame.setPlayerMoving(BLUE);
		testGame.setTurnNumber(3);
		game.makeMove(HORSE, makeCoordinate(1, -1), makeCoordinate(-4, 4));
	}

	@Test(expected = HantoException.class)
	public void sparrowCausesDisconnectedGroup() throws HantoException {
		final PieceLocationPair[] board = new PieceLocationPair[] {
				plPair(BLUE, BUTTERFLY, 0, 0), plPair(RED, BUTTERFLY, -1, 1),
				plPair(BLUE, HORSE, 2, -2), plPair(RED, SPARROW, 1, -1)
		};
		testGame.initializeBoard(board);
		testGame.setPlayerMoving(RED);
		testGame.setTurnNumber(3);
		game.makeMove(SPARROW, makeCoordinate(1, -1), makeCoordinate(0, 1));
	}

	@Test(expected = HantoException.class)
	public void horseCausesDisconnectedGroup() throws HantoException {
		final PieceLocationPair[] board = new PieceLocationPair[] {
				plPair(BLUE, BUTTERFLY, 0, 0), plPair(RED, BUTTERFLY, -1, 1),
				plPair(BLUE, HORSE, 2, -2), plPair(RED, HORSE, 1, -1)
		};
		testGame.initializeBoard(board);
		testGame.setPlayerMoving(RED);
		testGame.setTurnNumber(3);
		game.makeMove(HORSE, makeCoordinate(1, -1), makeCoordinate(-2, 2));
	}

	@Test(expected = HantoPrematureResignationException.class)
	public void resignWhileThereAreStillMoves() throws HantoException {
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(null, null, null);
	}

	@Test
	public void crabMoves() throws HantoException {
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(CRAB, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));
		assertEquals(OK, game.makeMove(CRAB, makeCoordinate(0, -1), makeCoordinate(1, -1)));
		final HantoPiece piece = game.getPieceAt(makeCoordinate(1, -1));
		assertEquals(CRAB, piece.getType());
		assertEquals(BLUE, piece.getColor());
		assertNull(game.getPieceAt(makeCoordinate(0, -1)));
	}

	@Test
	public void blueWins() throws HantoException {
		final PieceLocationPair[] board = new PieceLocationPair[] {
				plPair(BLUE, BUTTERFLY, 0, 0), plPair(RED, BUTTERFLY, 0, 1),
				plPair(BLUE, HORSE, 0, -1), plPair(RED, HORSE, -1, 2), plPair(BLUE, CRAB, -1, 1),
				plPair(RED, CRAB, 1, 1), plPair(BLUE, SPARROW, -1, 0), plPair(RED, SPARROW, 1, 0)
		};
		testGame.initializeBoard(board);
		testGame.setPlayerMoving(BLUE);
		testGame.setTurnNumber(6);
		assertEquals(BLUE_WINS, game.makeMove(HORSE, makeCoordinate(0, -1), makeCoordinate(0, 2)));
	}

	@Test(expected = HantoException.class)
	public void placePieceNextToOpponent() throws HantoException {
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(HORSE, null, makeCoordinate(1, 0));
	}

	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y) {
		return new TestHantoCoordinate(x, y);
	}

	/**
	 * Factory method to create a piece location pair.
	 * 
	 * @param player the player color
	 * @param pieceType the piece type
	 * @param x starting location
	 * @param y end location
	 * @return
	 */
	private PieceLocationPair plPair(HantoPlayerColor player, HantoPieceType pieceType, int x, int y) {
		return new PieceLocationPair(player, pieceType, new TestHantoCoordinate(x, y));
	}
}