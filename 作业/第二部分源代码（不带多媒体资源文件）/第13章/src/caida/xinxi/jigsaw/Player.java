package caida.xinxi.jigsaw;

import java.io.Serializable;

public class Player implements Comparable<Player>,Serializable{
	
	private static final long serialVersionUID = 1L;
	String name;
	int step=0;

	public Player(String name, int step) {
		super();
		this.name = name;
		this.step = step;
	}
	
	public int getStep(){
		return step;
	}
	
	public String getName(){
		return name;
	}

	@Override
	public int compareTo(Player o) {
		if((this.step -o.step )==0){
			return 1;
		}
		else{
			return (this.step -o.step );
		}
	}

}
