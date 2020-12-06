package Model;

public class GoldMiner {

	private Board board;
	private Miner miner;

	private int minerType;

	private int ctrGold;
	private int ctrBeacon;
	private int ctrPit;

	public GoldMiner(int minerType) {
		miner = new Miner();
		board = new Board();
		ctrGold = 0;
		ctrBeacon = 0;
		ctrPit = 0;
		this.minerType = minerType;
	}
	//INITIALIZATION
	public void setBoard(int size) {
		board.setBoard(size);
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
	
	public int scan (){
		int temp;
		switch(miner.getFront()){
			case 1:
				temp = miner.getPositionX() - 1;
				while(temp >= 0){
					if (getSpaceType(temp, miner.getPositionY()) != 1)
						return getSpaceType(temp, miner.getPositionY());

					temp --;
				}
				return 1;
			case 2:
				temp = miner.getPositionY() + 1;
				while(temp < board.getSize()){
					if (getSpaceType(miner.getPositionX(), temp) != 1)
						return getSpaceType(miner.getPositionX(), temp);

					temp ++;
				}
				return 1;
			case 3:
				temp = miner.getPositionX() + 1;
				while(temp < board.getSize()){
					if (getSpaceType(temp, miner.getPositionY()) != 1)
						return getSpaceType(temp, miner.getPositionY());

					temp ++;
				}
				return 1;
			case 4:
				temp = miner.getPositionY() - 1;
				while(temp >= 0){
					if (getSpaceType(miner.getPositionX(), temp) != 1)
						return getSpaceType(miner.getPositionX(), temp);

					temp --;
				}
				return 1;
		}
		return -1;
	}

	public boolean isEdge (int x, int y){
		return x == 0 || x == board.getSize() || y == 0 || y == board.getSize();
	}

	//BOARD FUNCTIONS
	public void printBoard() {
		board.printBoard();
	}

	public void setSpaceType (int x, int y, int type){
		switch(type){
			case 1:
				switch(board.getSpaceType(x, y)) {
					case 2:
						ctrPit--;
						break;
					case 3:
						ctrBeacon--;
						break;
					case 4:
						ctrGold--;
						break;
				}
				break;
			case 2:
				ctrPit ++;
				break;
			case 3:
				ctrBeacon++;
				break;
			case 4:
				ctrGold++;
				break;
		}
		board.setSpaceType(x, y, type);
	}

	public int getSpaceType (int x, int y){
		return board.getSpaceType(x, y);
	}

	public int getCtrGold (){
		return ctrGold;
	}

	public int getCtrBeacon(){
		return ctrBeacon;
	}

	public int getCtrPit (){
		return ctrPit;
	}

	public int getMinerX (){
		return miner.getPositionX();
	}

	public int getMinerY (){
		return miner.getPositionY();
	}
}
