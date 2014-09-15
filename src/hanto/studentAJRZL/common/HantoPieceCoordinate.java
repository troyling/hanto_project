/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was taken at
 * Worcester Polytechnic Institute.
 * 
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentAJRZL.common;

import hanto.common.HantoCoordinate;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class for the coordinates of the game.
 * 
 * @author anthonyjruffa
 * 
 */
public class HantoPieceCoordinate implements HantoCoordinate {

	private final int x;
	private final int y;

	/**
	 * Constructor for the Hanto Piece Coordinates.
	 * 
	 * @param x
	 * @param y
	 */
	public HantoPieceCoordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}
	
	public Collection<HantoCoordinate> getAdjacentCoordinates() {
		Collection<HantoCoordinate> adjacentCoordinates = new ArrayList<HantoCoordinate>();
		
		adjacentCoordinates.add(new HantoPieceCoordinate(x - 1, y));
		adjacentCoordinates.add(new HantoPieceCoordinate(x + 1, y));
		adjacentCoordinates.add(new HantoPieceCoordinate(x, y - 1));
		adjacentCoordinates.add(new HantoPieceCoordinate(x, y + 1));
		adjacentCoordinates.add(new HantoPieceCoordinate(x - 1, y + 1));
		adjacentCoordinates.add(new HantoPieceCoordinate(x + 1, y - 1));
		
		return adjacentCoordinates;
	}
}
