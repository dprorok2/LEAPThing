package sigmusic.thing.hand;

public class HandThing {
	public enum Mode {THEREMIN, SEQUENCER, DRUMS};
	
	private Mode currMode;
	private final int ID;
	
	public HandThing(int ID) {
		this.ID = ID;
		this.currMode = Mode.THEREMIN;
	}
	
	public HandThing(int ID, Mode currMode) {
		this.ID = ID;
		this.currMode = currMode;
	}
}
