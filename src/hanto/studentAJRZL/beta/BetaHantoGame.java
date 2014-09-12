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

import java.util.HashMap;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentAJRZL.common.HantoGamePiece;
import hanto.studentAJRZL.common.HantoPieceCoordinate;

public class BetaHantoGame implements HantoGame {
	
	private HantoPlayerColor currentPlayColor;
	private HantoCoordinate blueButterflyCoordiate;
	private HantoCoordinate redButterflyCoordiate;
	private int turn = 0;
	
	private Map<HantoCoordinate, HantoPiece> board = new HashMap<HantoCoordinate, HantoPiece>();
	
	/**
	 * Constructor for beta hanto game
	 * @param movesFirst
	 */
	public BetaHantoGame(HantoPlayerColor movesFirst) {
		this.currentPlayColor = movesFirst;
	}
	
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		
		if (board.get(to) != null) {
			throw new HantoException("Can't place a piece on an occupied tile.");
		} 
		
		HantoPiece newPiece = new HantoGamePiece(currentPlayColor, pieceType);
		
		board.put(to, newPiece);
		
		// store the butterflies coordinate
		switch (newPiece.getColor()) {
			case BLUE:
				blueButterflyCoordiate = to;
				break;
			case RED:
				redButterflyCoordiate = to;
				break;
			default:
				throw new HantoException("Invalid color.");
		}
		
		alterPlayerColor();
		
		return checkGameStatus();	
	}
	
	private MoveResult checkGameStatus() {
		MoveResult result = MoveResult.OK;
		
		
		
		return result;
	}

	void alterPlayerColor() throws HantoException {
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

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPrintableBoard() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
