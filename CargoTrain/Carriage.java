package CargoTrain;

import java.util.Stack;

import Util.Cargo;

public class Carriage {
	protected int emptySlot;
	protected Stack<Cargo> cargos = new Stack<Cargo>();
	protected Carriage next;
	protected Carriage prev;

	public Carriage(int capacity) {
		this.emptySlot = capacity;
	}

	public boolean isFull() {
		return emptySlot == 0;
	}

	protected void push(Cargo cargo) {
		this.emptySlot -= cargo.getSize();
		cargos.push(cargo);
	}

	protected Cargo pop() {
		Cargo popped = cargos.pop();
		this.emptySlot += popped.getSize();
		return popped;
	}

	public int getEmptySlot() {
		return emptySlot;
	}
}