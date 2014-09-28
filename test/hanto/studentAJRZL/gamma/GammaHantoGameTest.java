/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was taken at
 * Worcester Polytechnic Institute.
 * 
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentAJRZL.gamma;

import static org.junit.Assert.assertEquals;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoTestGame;
import hanto.common.MoveResult;

import org.junit.Test;

/**
 * Test cases for beta hanto game
 * 
 * @author troyling
 * 
 */
public class GammaHantoGameTest {
	/**
	 * Internal class for these test cases.
	 * 
	 * @version Sep 13, 2014
	 */

	class TestHantoCoordinate implements HantoCoordinate {
		private final int x, y;

		/**
		 * Constructor for test coordinate
		 * 
		 * @param x
		 * @param y
		 */
		TestHantoCoordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}

		/*
		 * 
		 * @see hanto.common.HantoCoordinate#getX()
		 */
		@Override
		public int getX() {
			return x;
		}

		/*
		 * 
		 * @see hanto.common.HantoCoordinate#getY()
		 */
		@Override
		public int getY() {
			return y;
		}
	}

	HantoTestGame game;

	/**
	 * Check to make sure an unfilled printable board is an empty string.
	 */
	@Test
	public void testThatUnfilledBoardIsEmpty() {
		game = new GammaHantoTestGame(HantoPlayerColor.BLUE);
		String printedBoard = game.getPrintableBoard();
		assertEquals("", printedBoard);
	}

	/**
	 * Test if the piece is moved correctly
	 */
	@Test
	public void testValidMovePiece() throws HantoException {
		// initial test
		game = new GammaHantoTestGame(HantoPlayerColor.BLUE);
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.RED, HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		// both butterflies' move should be valid
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 0), new TestHantoCoordinate(1, 0)));
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 1), new TestHantoCoordinate(1, 1)));
	}

	/**
	 * Test if the piece is moved incorrectly
	 */
	@Test(expected = HantoException.class)
	public void testInvalidMovePiece() throws HantoException {
		// initial test
		game = new GammaHantoTestGame(HantoPlayerColor.BLUE);
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[4];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.RED, HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 1));
		initialPieces[2] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.BLUE, HantoPieceType.SPARROW,
				new TestHantoCoordinate(1, -1));
		initialPieces[3] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.RED, HantoPieceType.SPARROW,
				new TestHantoCoordinate(0, 2));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(3);
		game.setPlayerMoving(HantoPlayerColor.BLUE);

		// both butterflies' move should be valid
		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0),
				new TestHantoCoordinate(1, 0));
	}

	/**
	 * Test for red winning the game
	 */
	@Test
	public void testRedWinsBySurroundingRedButterfly() throws HantoException {
		// initial test
		game = new GammaHantoTestGame(HantoPlayerColor.BLUE);
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[9];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.RED, HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 1));
		initialPieces[2] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.BLUE, HantoPieceType.SPARROW,
				new TestHantoCoordinate(1, -1));
		initialPieces[3] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.RED, HantoPieceType.SPARROW,
				new TestHantoCoordinate(1, 0));
		initialPieces[4] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.BLUE, HantoPieceType.SPARROW,
				new TestHantoCoordinate(-1, 1));
		initialPieces[5] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.RED, HantoPieceType.SPARROW,
				new TestHantoCoordinate(-1, 0));
		initialPieces[6] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.BLUE, HantoPieceType.SPARROW,
				new TestHantoCoordinate(1, -2));
		initialPieces[7] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.RED, HantoPieceType.SPARROW,
				new TestHantoCoordinate(-1, -1));
		initialPieces[8] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.BLUE, HantoPieceType.SPARROW,
				new TestHantoCoordinate(2, -2));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(5);
		game.setPlayerMoving(HantoPlayerColor.RED);

		System.out.println("Before moving:\n" + game.getPrintableBoard());
		// both butterflies' move should be valid
		assertEquals(MoveResult.RED_WINS,
				game.makeMove(HantoPieceType.SPARROW, new TestHantoCoordinate(
						-1, -1), new TestHantoCoordinate(0, -1)));
		
		System.out.println("After moving:\n" + game.getPrintableBoard());
	}

	/**
	 * Test for moving the blue butterfly which would result into disconnection
	 * between other pieces
	 */
	@Test(expected = HantoException.class)
	public void testBlueButterflyCannotMove() throws HantoException {
		// initial test
		game = new GammaHantoTestGame(HantoPlayerColor.BLUE);
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[4];

		initialPieces[0] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.RED, HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 1));
		initialPieces[2] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.BLUE, HantoPieceType.SPARROW,
				new TestHantoCoordinate(-1, 0));
		initialPieces[3] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.RED, HantoPieceType.SPARROW,
				new TestHantoCoordinate(1, 0));
		
		game.initializeBoard(initialPieces);
		game.setTurnNumber(3);
		game.setPlayerMoving(HantoPlayerColor.BLUE);
		
		game.makeMove(HantoPieceType.SPARROW, new TestHantoCoordinate(
				0, 0), new TestHantoCoordinate(0, -1));
	}
	
	/**
	 * Attempts to move a piece to a null destination. Exception is expected.
	 */
	@Test(expected = HantoException.class)
	public void testMovePieceToNullDestionCoordinate() throws HantoException {
		// init test
		game = new GammaHantoTestGame(HantoPlayerColor.BLUE);
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];
		
		initialPieces[0] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.RED, HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);
		
		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0), null);
	}
	
	/**
	 * Attempts to move a piece to an occupied destination. Exception is expected.
	 */
	@Test(expected = HantoException.class)
	public void testMovePieceToOccupiedDestionCoordinate() throws HantoException {
		// init test
		game = new GammaHantoTestGame(HantoPlayerColor.BLUE);
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];
		
		initialPieces[0] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.RED, HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);
		
		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0), new TestHantoCoordinate(0, 1));
	}
	
	/**
	 * Test for validly moving a piece.
	 */
	@Test
	public void testMovePieceWithoutResultingInDisconnection() throws HantoException {
		// init test
		game = new GammaHantoTestGame(HantoPlayerColor.BLUE);
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[6];
		
		initialPieces[0] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.RED, HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 1));
		initialPieces[2] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.BLUE, HantoPieceType.SPARROW,
				new TestHantoCoordinate(1, -1));
		initialPieces[3] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.RED, HantoPieceType.SPARROW,
				new TestHantoCoordinate(1, 1));
		initialPieces[4] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.BLUE, HantoPieceType.SPARROW,
				new TestHantoCoordinate(2, -1));
		initialPieces[5] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.RED, HantoPieceType.SPARROW,
				new TestHantoCoordinate(2, 0));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(4);
		game.setPlayerMoving(HantoPlayerColor.BLUE);
		
		System.out.println("Before: \n" + game.getPrintableBoard());
		assertEquals(MoveResult.OK, game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0), new TestHantoCoordinate(1, 0)));
		System.out.println("After: \n" + game.getPrintableBoard());
	}
	
	/**
	 * Attempts to move a sparrow which in fact is a butterfly
	 */
	@Test(expected = HantoException.class)
	public void testMoveWrongPiece() throws HantoException {
		// init test
		game = new GammaHantoTestGame(HantoPlayerColor.BLUE);
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];
		
		initialPieces[0] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.RED, HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);
		
		game.makeMove(HantoPieceType.SPARROW, new TestHantoCoordinate(0, 0), new TestHantoCoordinate(1, 0));
	}
	
	/**
	 * Attempts to move a piece for 2 steps, which is not allowed in gamma.
	 */
	@Test(expected = HantoException.class)
	public void testAttemptToMoveTwoSteps() throws HantoException {
		// init test
		game = new GammaHantoTestGame(HantoPlayerColor.BLUE);
		HantoTestGame.PieceLocationPair[] initialPieces = new HantoTestGame.PieceLocationPair[2];
		
		initialPieces[0] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 0));
		initialPieces[1] = new HantoTestGame.PieceLocationPair(
				HantoPlayerColor.RED, HantoPieceType.BUTTERFLY,
				new TestHantoCoordinate(0, 1));

		game.initializeBoard(initialPieces);
		game.setTurnNumber(2);
		game.setPlayerMoving(HantoPlayerColor.BLUE);
		
		game.makeMove(HantoPieceType.BUTTERFLY, new TestHantoCoordinate(0, 0), new TestHantoCoordinate(1, 1));
	}
}
