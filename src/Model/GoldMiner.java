package Model;

public class GoldMiner {
	
	Board board;
	Miner miner;
	
	int minerType;

	public GoldMiner(int minerType) {
		miner = new Miner();
		this.minerType = minerType;
	}
//INITIALIZATION
	public void createBoard (int size) {
		board = new Board(size);
	}
	
	public int setBoardSize (int size) {
		return size;
	}
	
	public Miner getMiner() {
		return miner;
	}
	
	public Board getBoard () {
		return board;
	}
//AI FUNCTIONS
	
	public void move () {
		miner.move();
	}
	
	public void rotate (char direction) {
		miner.rotate(direction);
	}
}
