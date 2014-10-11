package hanto.tournament;

import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;

public class HantoPlayer implements HantoGamePlayer {

	@Override
	public void startGame(HantoGameID version, HantoPlayerColor myColor, boolean doIMoveFirst) {
	}

	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove) {
		return null;
	}
}
