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

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentAJRZL.HantoGameFactory;
import hanto.studentAJRZL.common.HantoPieceCoordinate;
import hanto.studentAJRZL.epsilon.EpsilonHantoGame;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;

/**
 * This is the implementation of our AI for the Hanto project
 * 
 * @author troyling
 */
public class HantoPlayer implements HantoGamePlayer {
	HantoPlayerColor myColor;
	HantoGame game;

	@Override
	public void startGame(HantoGameID version, HantoPlayerColor myColor, boolean doIMoveFirst) {
		this.myColor = myColor;
		HantoPlayerColor movesFirst;
		if (doIMoveFirst) {
			game = HantoGameFactory.getInstance().makeHantoGame(version, myColor);
		} else {
			// Set the color that moves first.
			movesFirst = (myColor == HantoPlayerColor.BLUE) ? HantoPlayerColor.RED
					: HantoPlayerColor.BLUE;
			game = HantoGameFactory.getInstance().makeHantoGame(version, movesFirst);
		}
	}

	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove) {
		if (opponentsMove == null) {
			// we move first
			try {
				game.makeMove(HantoPieceType.BUTTERFLY, null, new HantoPieceCoordinate(0, 0));
			} catch (HantoException e) {
				// do nothing
				e.getMessage();
			}
			return new HantoMoveRecord(HantoPieceType.BUTTERFLY, null, new HantoPieceCoordinate(0,
					0));
		}
		HantoPieceType oppoPieceType = opponentsMove.getPiece();
		HantoCoordinate oppoFrom = opponentsMove.getFrom();
		HantoCoordinate oppoTo = opponentsMove.getTo();
		try {
			game.makeMove(oppoPieceType, oppoFrom, oppoTo);
		} catch (HantoException e) {
			System.out.println(e.getMessage());
		}
		return ((EpsilonHantoGame) game).getPossibleMove();
	}
}
