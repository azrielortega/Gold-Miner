package Model;

public class Miner {
	int positionX;
	int positionY;
	int front;
	/*
	 * 1 - north
	 * 2 - east
	 * 3 - south
	 * 4 - west
	 */
	boolean finish;
	
	public Miner () {
		positionX = 0;
		positionY = 0;
		front = '1';
		finish = false;
	}
	
	public int getPositionX() {
		return positionX;
	}
	
	public int getPOsitionY () {
		return positionY;
	}
	
	//fix isEdge function
	public void move (){
		switch (front) {
		case 1:
			positionX --;
			break;
		case 2:
			positionY ++;
			break;
		case 3:
			positionX ++;
			break;
		case 4:
			positionY --;
			break;
		}
	}
	
	public void rotate (char direction) {
		switch (direction) {
		case 'L':
			front --;
			if (front == 0)
				front = 4;
			break;
		case 'R':
			front ++;
			if (front == 5)
				front = 1;
			break;
		}
	}
}