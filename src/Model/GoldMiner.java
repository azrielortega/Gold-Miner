package Model;

public class GoldMiner {

	private Miner miner;

	private int minerType;

	private int ctrGold;
	private int ctrBeacon;
	private int ctrPit;

	private int rotate;
	private int move;
	private int scan;

	private Board board;
	/*
	1 - dirt
	2 - pit
	3 - beacon
	4 - gold
	 */

	private Board memory;
	/*
	1 - unexplored
	2 - scanned
	3 - don't explore (pit)
	4 - goal
	5 - beacon location
	6 - possible gold location
	7 - explored
	 */

	public GoldMiner(int minerType) {
		miner = new Miner();
		board = new Board();
		memory = new Board();

		ctrGold = 0;
		ctrBeacon = 0;
		ctrPit = 0;

		rotate = 0;
		move = 0;
		scan = 0;

		this.minerType = minerType;
	}
	//INITIALIZATION
	public void setBoard(int size) {
		board.setBoard(size);
		memory.setBoard(size);
		memory.setSpaceType(0, 0, 1);
	}

	public Miner getMiner() {
		return miner;
	}

	public Board getBoard () {
		return board;
	}
//AI FUNCTIONS

	public void move () {
		move ++;
		miner.move();
	}

	public void rotate (char direction) {
		rotate++;
		miner.rotate(direction);
	}
	
	public int scan (){
		int temp;
		int mTemp = 0;

		int x = -1;
		int y = -1;

		boolean found = false;
		scan++;

		switch(miner.getFront()){
			case 1:
				temp = miner.getPositionX();
				while(temp >= 0){
					if (getSpaceType(temp, miner.getPositionY()) != 1 && getSpaceType(temp, miner.getPositionY()) != 5){
						mTemp = getSpaceType(temp, miner.getPositionY());
						x = temp;
						y = getMinerY();
						found = true;
						break;
					}
					else if(getSpaceMemory(temp, miner.getPositionY()) == 1)
						memory.setSpaceType(temp, miner.getPositionY(), 2);

					temp --;
				}
				break;
			case 2:
				temp = miner.getPositionY();
				while(temp < board.getSize()){
					if (getSpaceType(miner.getPositionX(), temp) != 1 && getSpaceType(miner.getPositionX(), temp) != 5) {
						mTemp = getSpaceType(miner.getPositionX(), temp);
						found = true;
						x = getMinerX();
						y = temp;
						break;
					}
					else if(getSpaceMemory(miner.getPositionX(), temp) == 1)
						memory.setSpaceType(miner.getPositionX(), temp, 2);
					temp ++;
				}
				break;
			case 3:
				temp = miner.getPositionX();
				while(temp < board.getSize()){
					if (getSpaceType(temp, miner.getPositionY()) != 1 && getSpaceType(temp, miner.getPositionY()) != 5) {
						mTemp = getSpaceType(temp, miner.getPositionY());
						found = true;
						x = temp;
						y = getMinerY();
						break;
					}
					else if(getSpaceMemory(temp, miner.getPositionY()) == 1)
						memory.setSpaceType(temp, getMinerY(), 2);

					temp ++;
				}
				break;
			case 4:
				temp = miner.getPositionY();
				while(temp >= 0){
					if (getSpaceType(miner.getPositionX(), temp) != 1 && getSpaceType(miner.getPositionX(), temp) != 5) {
						mTemp = getSpaceType(miner.getPositionX(), temp);
						found = true;
						x = getMinerX();
						y = temp;
						break;
					}
					else if(getSpaceMemory(miner.getPositionX(), temp) == 1)
						memory.setSpaceType(miner.getPositionX(), temp, 2);

					temp --;
				}
		}
		if (found){
			switch(mTemp){
				case 2://PIT
					updateMemory(x, y, 3);
					break;
				case 3://BEACON
					updateMemory(x, y, 2);
					expandBeacon(x, y);
					break;
				case 4://GOLD
					updateMemory(x, y, 4);
					break;
			}
		}

		return mTemp;
	}

	public void expandBeacon(int x, int y){
		//NORTH
		for (int i = 0; i < x; i++){
			if(memory.getSpaceType(i, y) == 1)
				updateMemory(i, y, 6);
		}
		//SOUTH
		for (int i = board.getSize() - 1; i > x; i--){
			if(memory.getSpaceType(i, y) == 1)
				updateMemory(i, y, 6);
		}
		//WEST
		for (int i = board.getSize() - 1; i > y; i--){
			if(memory.getSpaceType(x, i) == 1)
				updateMemory(x, i, 6);
		}
		//EAST
		for (int i = 0; i < y; i++){
			if(memory.getSpaceType(x, i) == 1)
				updateMemory(x, i, 6);
		}
	}

	public boolean isEdge (int x, int y){
		return x <= -1 || x >= board.getSize() || y <= -1 || y >= board.getSize();
	}

	//MEMORY FUNCTIONS
	public void printMemory(){
		memory.printBoard();
	}

	public void updateMemory (int x, int y, int type){
		memory.setSpaceType(x, y, type);
	}

	public int getSpaceMemory (int x, int y){
		return memory.getSpaceType(x, y);
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

	public boolean isEnd(){
		return false;
	}

	//UI FUNCTIONS

	public int getRotate(){
		return rotate;
	}

	public int getScan(){
		return scan;
	}

	public int getMove(){
		return move;
	}
}
