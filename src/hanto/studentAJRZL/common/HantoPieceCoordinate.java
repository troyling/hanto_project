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
import java.util.LinkedList;
import java.util.Queue;

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

	/**
	 * @return the X-coordinate
	 */
	@Override
	public int getX() {
		return x;
	}

	/**
	 * @return the Y-coordinate
	 */
	@Override
	public int getY() {
		return y;
	}

	/**
	 * Equals function for the hashable class.
	 * 
	 * @return true when both x and y coordinate equals to the given object
	 */
	public boolean equals(Object obj) {
		boolean isEqual = false;

		if (obj instanceof HantoCoordinate) {
			isEqual = (x == ((HantoCoordinate) obj).getX())
					&& (y == ((HantoCoordinate) obj).getY());
		}

		return isEqual;
	}

	/**
	 * Hashing function for the class
	 * 
	 * @return the hash code of the current object
	 */
	public int hashCode() {
		int hash = x;
		hash = hash * 31 + y;
		return hash;
	}

	/**
	 * Get the coordinates for the adjacent spaces.
	 * 
	 * @return a collection of the space coordinates.
	 */
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

	/**
	 * Calculate the distance between the coordinate and the given coordinate
	 * 
	 * @param destCoord
	 * @return the distance between two coordinates
	 */
	public int getDistanceTo(HantoCoordinate coord) {
		int distance = 0;
		final HantoPieceCoordinate destCoord = new HantoPieceCoordinate(
				coord.getX(), coord.getY());

		if (!this.equals(destCoord)) {
			// BFS
			Collection<HantoPieceCoordinate> visited = new LinkedList<HantoPieceCoordinate>();
			Queue<HantoPieceCoordinate> queue = new LinkedList<HantoPieceCoordinate>();

			visited.add(this);
			queue.add(this);

			int count = 0;

			while (!queue.isEmpty()) {

				HantoPieceCoordinate c = queue.poll();

				count++;

				if (c.equals(destCoord)) {
					break;
				}

				for (HantoCoordinate neighbor : c.getAdjacentCoordinates()) {
					if (!visited.contains(neighbor)) {
						visited.add(((HantoPieceCoordinate) neighbor));
						queue.add(((HantoPieceCoordinate) neighbor));
					}
				}
			}

			distance = 1;

			while (getTotalNumTiles(distance) < count) {
				distance++;
			}
		}

		return distance;
	}

	/**
	 * Calculate the total number of tiles without the origin in the nth layer
	 * 
	 * @param layer
	 * @return number of tiles
	 */
	private int getTotalNumTiles(int layer) {
		return 3 * layer * (layer + 1);
	}

}
