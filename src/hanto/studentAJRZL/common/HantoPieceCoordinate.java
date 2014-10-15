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
	 * Constructor for converting HantoCoordinate to HantoPieceCoordinate
	 * 
	 * @param coord
	 */
	public HantoPieceCoordinate(HantoCoordinate coord) {
		x = coord.getX();
		y = coord.getY();
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
	 * Return the common neighbors between two tiles
	 * 
	 * @param coord
	 * @return common neighbors
	 */
	public Collection<HantoCoordinate> getCommonNeighbors(HantoPieceCoordinate coord) {
		Collection<HantoCoordinate> commonNeighbors = new LinkedList<HantoCoordinate>();
		Collection<HantoCoordinate> neighbors1 = getAdjacentCoordinates();
		Collection<HantoCoordinate> neighbors2 = coord.getAdjacentCoordinates();

		// find out common tiles
		for (HantoCoordinate c : neighbors1) {
			if (neighbors2.contains(c)) {
				commonNeighbors.add(c);
			}
		}
		return commonNeighbors;
	}

	/**
	 * Calculate the distance between the coordinate and the given coordinate
	 * 
	 * @param coord
	 * @return the distance between two coordinates
	 */
	public int getDistanceTo(HantoCoordinate coord) {
		// calculate the distance using formula
		int z1 = 0 - x - y;
		int z2 = 0 - coord.getX() - coord.getY();

		return (Math.abs(x - coord.getX()) + Math.abs(y - coord.getY()) + Math.abs(z1 - z2)) / 2;

	}

	/**
	 * Determine if the given coordinate is on the same line with this coordinate
	 * 
	 * @param coord
	 * @return true if so; false otherwise
	 */
	private boolean isCoordinateOnSameLine(HantoCoordinate coord) {
		boolean isOnLine = false;
		int delX = x - coord.getX();
		int delY = y - coord.getY();

		if ((delX == 0 && delY != 0) || (delX != 0 && delY == 0)
				|| (delX != 0 && delX * -1 == delY)) {
			isOnLine = true;
		}
		return isOnLine;
	}

	/**
	 * Return the coordinates in between this coordinate and the given coordinate
	 * 
	 * @param coord
	 * @return a collection of coordinates
	 */
	public Collection<HantoCoordinate> getCoordOnTheLineTo(HantoCoordinate coord) {
		Collection<HantoCoordinate> coordsOnLine = new ArrayList<HantoCoordinate>();

		if (isCoordinateOnSameLine(coord)) {
			final HantoPieceCoordinate destCoord = new HantoPieceCoordinate(coord);
			HantoPieceCoordinate nextCoord = this;
			int delX = destCoord.getX() - x;
			int delY = destCoord.getY() - y;
			if (delX == 0) {

				// vertical
				int increment = delY / Math.abs(delY);

				while (!nextCoord.equals(destCoord)) {
					nextCoord = new HantoPieceCoordinate(nextCoord.getX(), nextCoord.getY()
							+ increment);
					if (!nextCoord.equals(destCoord)) {
						coordsOnLine.add(nextCoord);
					}
				}
			} else if (delY == 0) {
				// bottom left to upper right
				int increment = delX / Math.abs(delX);

				while (!nextCoord.equals(destCoord)) {
					nextCoord = new HantoPieceCoordinate(nextCoord.getX() + increment,
							nextCoord.getY());
					if (!nextCoord.equals(destCoord)) {
						coordsOnLine.add(nextCoord);
					}
				}
			} else {
				// upper left to bottom right
				while (!nextCoord.equals(destCoord)) {
					int incrementX = delX / Math.abs(delX);
					int incrementY = -1 * incrementX;

					nextCoord = new HantoPieceCoordinate(nextCoord.getX() + incrementX,
							nextCoord.getY() + incrementY);
					if (!nextCoord.equals(destCoord)) {
						coordsOnLine.add(nextCoord);
					}
				}
			}
		}
		return coordsOnLine;
	}
}
