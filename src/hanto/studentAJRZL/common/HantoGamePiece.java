/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design. The course was taken at
 * Worcester Polytechnic Institute.
 * 
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentAJRZL.common;

import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * Class for the Hanto Game pieces.
 * 
 * @author anthonyjruffa
 * 
 */
public class HantoGamePiece implements HantoPiece {

	private final HantoPlayerColor color;
	private final HantoPieceType type;

	/**
	 * Constructor for a Hanto Game piece.
	 * 
	 * @param color
	 * @param type
	 */
	public HantoGamePiece(HantoPlayerColor color, HantoPieceType type) {
		this.color = color;
		this.type = type;
	}

	@Override
	public HantoPlayerColor getColor() {
		return color;
	}

	@Override
	public HantoPieceType getType() {
		return type;
	}
}
