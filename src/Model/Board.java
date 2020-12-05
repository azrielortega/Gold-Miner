package Model;
import java.util.ArrayList;

public class Board {
	private ArrayList<ArrayList<Space>> spaces;
	private int size;

	public Board () {
		spaces = new ArrayList<>();
	}

	public void setBoard(int size) {
		this.size = size;
		initializeValues();
		spaces.get(0).get(0).setSpaceType(5);
	}
	public void initializeValues() {

		for (int i = 0; i < size; i++) {
			ArrayList<Space> temp = new ArrayList<>();
			for (int j = 0; j < size; j++) {
				temp.add(new Space(i, j));
			}
			spaces.add(temp);
		}
	}

	public void printBoard() {
		for (int i = 0; i < spaces.size(); i++) {
			for (int j = 0; j < spaces.size(); j++) {
				System.out.print(spaces.get(i).get(j).getSpaceType() + " ");
			}
			System.out.println();
		}
	}

	public void setSpaceType(int x, int y, int spaceType){
		spaces.get(x).get(y).setSpaceType(spaceType);
	}

	public int getSpaceType (int x, int y){
		return spaces.get(x).get(y).getSpaceType();
	}
}
