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

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.SPARROW;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;
import static hanto.common.MoveResult.BLUE_WINS;
import static hanto.common.MoveResult.DRAW;
import static hanto.common.MoveResult.OK;
import static hanto.common.MoveResult.RED_WINS;
import static org.junit.Assert.assertEquals;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.MoveResult;
import hanto.studentAJRZL.HantoGameFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test cases for Beta Hanto.
 * @version Sep 14, 2014
 */
public class BetaHantoMasterTest
{
	/**
	 * Internal class for these test cases.
	 * @version Sep 13, 2014
	 */
	class TestHantoCoordinate implements HantoCoordinate
	{
		private final int x, y;
		
		public TestHantoCoordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		/*
		 * @see hanto.common.HantoCoordinate#getX()
		 */
		@Override
		public int getX()
		{
			return x;
		}

		/*
		 * @see hanto.common.HantoCoordinate#getY()
		 */
		@Override
		public int getY()
		{
			return y;
		}

	}
	
	private static HantoGameFactory factory;
	private HantoGame game;
	
	@BeforeClass
	public static void initializeClass()
	{
		factory = HantoGameFactory.getInstance();
	}
	
	@Before
	public void setup()
	{
		// By default, blue moves first.
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO);
	}
	
	@Test
	public void bluePlacesInitialButterflyAtOrigin() throws HantoException
	{
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}
	
	@Test
	public void redMovesFirstAndPlacesButterfly() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, RED);
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(RED, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}
	
	@Test
	public void firstMovePlacesSparrow() throws HantoException
	{
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	@Test(expected=HantoException.class)
	public void blueDoesNotPlaceButterflyByMove4() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));	// Blue
		game.makeMove(SPARROW, null, makeCoordinate(0, 1)); // Red
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));	// Blue
		game.makeMove(SPARROW, null, makeCoordinate(0, 3)); // Red
		game.makeMove(SPARROW, null, makeCoordinate(0, 4));	// Blue
		game.makeMove(SPARROW, null, makeCoordinate(0, 5)); // Red
		game.makeMove(SPARROW, null, makeCoordinate(0, 6));	// Blue
	}
	
	@Test(expected=HantoException.class)
	public void redDoesNotPlaceButterflyByMove4() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));	// Blue
		game.makeMove(SPARROW, null, makeCoordinate(0, 1)); // Red
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));	// Blue
		game.makeMove(SPARROW, null, makeCoordinate(0, 3)); // Red
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 4));	// Blue
		game.makeMove(SPARROW, null, makeCoordinate(0, 5)); // Red
		game.makeMove(SPARROW, null, makeCoordinate(0, 6));	// Blue
		game.makeMove(SPARROW, null, makeCoordinate(0, 7)); // Red
	}
	
	@Test(expected=HantoException.class)
	public void placePieceInNonAdjacentPosition() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(3, -2));
	}
	
	@Test(expected=HantoException.class)
	public void firstMoveNotAt0_0() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 0));
	}
	
	@Test(expected=HantoException.class)
	public void attemptToMoveAPiece() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));	// Blue
		game.makeMove(SPARROW, null, makeCoordinate(0, 1)); // Red
		game.makeMove(SPARROW, makeCoordinate(0,0), makeCoordinate(0, -1));	// Blue
	}
	
	@Test(expected=HantoException.class)
	public void attemptToPlaceTwoButterflies() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
	}
	
	@Test(expected=HantoException.class)
	public void attemptToPlacePieceOnOccupiedLocation() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
	}
	
	@Test
	public void blueMovesFirstAndWins() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, null, makeCoordinate(1,-1));
		game.makeMove(SPARROW, null, makeCoordinate(1, -2));
		game.makeMove(SPARROW, null, makeCoordinate(0, -2));
		game.makeMove(SPARROW, null, makeCoordinate(-1, -1));
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		assertEquals(BLUE_WINS, mr);
	}
	
	@Test
	public void redWins() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		assertEquals(RED_WINS, mr);
	}
	
	@Test
	public void drawAfterSixMoves() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1)); // 2
		game.makeMove(SPARROW, null, makeCoordinate(0, -2));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2)); // 3
		game.makeMove(SPARROW, null, makeCoordinate(0, -3));
		game.makeMove(SPARROW, null, makeCoordinate(0, 3)); // 4
		game.makeMove(SPARROW, null, makeCoordinate(0, -4));
		game.makeMove(SPARROW, null, makeCoordinate(0, 4)); // 5
		game.makeMove(SPARROW, null, makeCoordinate(0, -5));
		game.makeMove(SPARROW, null, makeCoordinate(0, 5)); // 6
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, -6));
		assertEquals(DRAW, mr);
	}
	
	@Test
	public void winOnLastMove() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1)); // 2
		game.makeMove(SPARROW, null, makeCoordinate(0, -2));
		game.makeMove(SPARROW, null, makeCoordinate(0, 2)); // 3
		game.makeMove(SPARROW, null, makeCoordinate(0, -3));
		game.makeMove(SPARROW, null, makeCoordinate(0, 3)); // 4
		game.makeMove(SPARROW, null, makeCoordinate(0, -4));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0)); // 5
		game.makeMove(SPARROW, null, makeCoordinate(-1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, -1)); // 6
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		assertEquals(RED_WINS, mr);
	}
	
	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new TestHantoCoordinate(x, y);
	}
}
