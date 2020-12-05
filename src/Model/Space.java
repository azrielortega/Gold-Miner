package Model;

public class Space {

	private int spaceType;
	private int x;
	private int y;
	public Space (int x, int y) {
		spaceType = 1;
		this.x = x;
		this.y = y;
	}

	public void setSpaceType(int type) {
		spaceType = type;
	}

	public int getSpaceType() {
		return spaceType;
	}
}
