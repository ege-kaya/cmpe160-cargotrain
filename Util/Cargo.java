package Util;

public class Cargo {
	private int id;
	private int loadingStation;
	private int size;
	private int targetStation;

	public Cargo(int id, int loadingStation, int size, int targetStation) {
		this.id = id;
		this.loadingStation = loadingStation;
		this.size = size;
		this.targetStation = targetStation;
	}

	public String toString() {
		return this.id + " " + this.loadingStation + " " + this.targetStation + " " + this.size;
	}

	public int getSize() {
		return size;
	}

	public int getTargetStation() {
		return targetStation;
	}
}