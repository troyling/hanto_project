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


/**
 * This is the implementation of our AI for the Hanto project
 * 
 * @author troyling
 */
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentAJRZL.HantoGameFactory;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;

import java.util.Collection;

public class HantoPlayer implements HantoGamePlayer {
	HantoPlayerColor myColor;
	HantoGame game;

	@Override
	public void startGame(HantoGameID version, HantoPlayerColor myColor, boolean doIMoveFirst) {
		this.myColor = myColor;
		if (doIMoveFirst) {
			game = HantoGameFactory.getInstance().makeHantoGame(version, myColor);
		} else {
			if (myColor == HantoPlayerColor.BLUE) {
				game = HantoGameFactory.getInstance().makeHantoGame(version, HantoPlayerColor.RED);
			} else {
				game = HantoGameFactory.getInstance().makeHantoGame(version, HantoPlayerColor.BLUE);
			}
		}
	}

	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove) {
		MoveResult result = MoveResult.OK;
		Collection<HantoCoordinate> availableCoords;
		HantoPieceType oppoPieceType = opponentsMove.getPiece();
		HantoCoordinate oppoFrom = opponentsMove.getFrom();
		HantoCoordinate oppoTo = opponentsMove.getTo();

		try {
			result = game.makeMove(oppoPieceType, oppoFrom, oppoTo);
		} catch (HantoException e) {
			e.printStackTrace();
		}
		
		if (result == MoveResult.OK) {
			// figure out strategy
			
		}
		
		return null;
	}
}