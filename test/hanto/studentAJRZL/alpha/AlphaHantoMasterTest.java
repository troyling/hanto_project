package hanto.studentAJRZL.alpha;

import hanto.HantoGameFactory;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class AlphaHantoMasterTest

{

	/**
	 * 
	 * Internal class for these test cases.
	 * 
	 * @version Sep 13, 2014
	 */

	class TestHantoCoordinate implements HantoCoordinate

	{

		private final int x, y;

		public TestHantoCoordinate(int x, int y) {
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

	private static final HantoPieceType BUTTERFLY = HantoPieceType.BUTTERFLY;
	private static final HantoPieceType SPARROW = HantoPieceType.SPARROW;
	private static final Object OK = MoveResult.OK;
	private static HantoGameFactory factory;
	private HantoGame game;

	@BeforeClass
	public static void initializeClass() {
		factory = HantoGameFactory.getInstance();
	}

	@Before
	public void setup() {
		game = factory.makeHantoGame(HantoGameID.ALPHA_HANTO);
	}

	@Test
	public void getAnAlphaHantoGameFromTheFactory() {
		assertTrue(game instanceof AlphaHantoGame);
	}

	@Test
	public void blueMakesValidFirstMove() throws HantoException {
		final MoveResult mr = game.makeMove(BUTTERFLY, null,
				new TestHantoCoordinate(0, 0));
		assertEquals(OK, mr);
	}

	@Test
	public void afterFirstMoveBlueButterflyIsAt0_0() throws HantoException {
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		final HantoPiece p = game.getPieceAt(new TestHantoCoordinate(0, 0));
		assertEquals(BUTTERFLY, p.getType());
		assertEquals(HantoPlayerColor.BLUE, p.getColor());
	}

	@Test(expected = HantoException.class)
	public void bluePlacesNonButterfly() throws HantoException {
		game.makeMove(SPARROW, null, new TestHantoCoordinate(0, 0));
	}

	@Test
	public void redPlacesButterflyNextToBlueButterfly() throws HantoException {
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(0, 1));
		final HantoPiece p = game.getPieceAt(new TestHantoCoordinate(0, 1));
		assertEquals(BUTTERFLY, p.getType());
		assertEquals(HantoPlayerColor.RED, p.getColor());
	}

	@Test(expected = HantoException.class)
	public void blueAttemptsToPlaceButterflyAtWrongLocation()
			throws HantoException {
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(-1, 1));
	}

	@Test
	public void redMakesValidSecondMoveAndGameIsDrawn() throws HantoException {
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		final MoveResult mr = game.makeMove(BUTTERFLY, null,
				new TestHantoCoordinate(-1, 1));
		assertEquals(MoveResult.DRAW, mr);
	}

	@Test(expected = HantoException.class)
	public void redPlacesButterflyNonAdjacentToBlueButterfly()
			throws HantoException {
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(0, 2));
	}

	@Test(expected = HantoException.class)
	public void attemptToMoveRatherThanPlace() throws HantoException {
		game.makeMove(BUTTERFLY, new TestHantoCoordinate(0, 1),
				new TestHantoCoordinate(0, 0));
	}

}