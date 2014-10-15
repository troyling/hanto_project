/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentAJRZL.tournament;

import static org.junit.Assert.assertNull;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentAJRZL.HantoGameFactory;
import hanto.tournament.HantoMoveRecord;

import org.junit.Before;
import org.junit.Test;

public class HantoPlayerTest {
	private HantoPlayer p1;
	private HantoPlayer p2;
	private HantoGame game;

	@Before
	public void setup() {
		game = HantoGameFactory.getInstance().makeHantoGame(HantoGameID.EPSILON_HANTO,
				HantoPlayerColor.BLUE);
		p1 = new HantoPlayer();
		p2 = new HantoPlayer();
		p1.startGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.BLUE, true);
		p2.startGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.RED, false);
	}

	@Test
	public void testValidGame() {
		// play a game
		HantoException ex = null;
		HantoMoveRecord record = null;
		MoveResult result;

		try {
			while (true) {
				record = p1.makeMove(record);
				result = game.makeMove(record.getPiece(), record.getFrom(), record.getTo());
				if (result != MoveResult.OK) {
					break;
				}
				record = p2.makeMove(record);
				result = game.makeMove(record.getPiece(), record.getFrom(), record.getTo());
				if (result != MoveResult.OK) {
					break;
				}
			}
		} catch (HantoException e) {
			ex = e;
		}
		assertNull(ex);
	}
}
