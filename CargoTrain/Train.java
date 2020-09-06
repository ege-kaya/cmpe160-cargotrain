package CargoTrain;

import java.util.Queue;

import Util.Cargo;

public class Train {
	private int carCapacity;
	private int length;
	private Carriage head;
	private Carriage tail;

	public Train(int length, int carCapacity) {
		if (length == 0) {
			return;
		}
		this.length = length;
		this.carCapacity = carCapacity;
		// creates head carriage
		Carriage first = new Carriage(carCapacity);
		head = first;
		if (length == 1) {
			tail = first;
			return;
		}
		Carriage current = head;

		// creates carriages in-between (if they exist)
		for (int i = 1; i < length - 1; i++) {
			current.next = new Carriage(carCapacity);
			Carriage temp = current;
			current = current.next;
			current.prev = temp;
		}
		// creates tail carriage (if there are more than 1 carriages)
		Carriage last = new Carriage(carCapacity);
		current.next = last;
		last.prev = current;
		tail = last;
	}

	public void load(Queue<Cargo> cargos) {
		// repeats for each cargo in the queue
		while (!cargos.isEmpty()) {
			Carriage current = head;
			// polls the cargo to be loaded (FIFO)
			Cargo load = cargos.poll();
			// traverses through carriages until it has enough empty space
			while (current != null && load.getSize() > current.emptySlot) {
				current = current.next;
			}
			// we stopped traversing either because we're at an available carriage, or
			// because we hit a null carriage. if it's the former, pushes the load into the
			// stack
			if (current != null) {
				current.push(load);
			} else {
				// add a new carriage
				this.add();
				current = tail;
				current.push(load);
			}
		}
		removeEmpty();
	}

	private void add() {
		if (head == null) {
			Carriage c = new Carriage(carCapacity);
			head = c;
			tail = head;
			this.length++;
		} else {
			Carriage c = new Carriage(carCapacity);
			tail.next = c;
			c.prev = tail;
			tail = c;
			this.length++;
		}
	}

	private void removeEmpty() {
		Carriage current = head;
		if (head == null) { // there are no carriages in the train
			return;
		}

		while (current != null) {
			if (current.cargos.isEmpty()) { // if the carriage is empty, removes it, then goes forward by 1
											// carriage
				if (current == head && head == tail) { // there's only 1 carriage and it's empty: update both head and
														// tail
					head = null;
					tail = head;
					current = null;
					length--;
				} else if (current == head) { // more than 1 carriage and the head is empty: update head
					current.next.prev = null;
					head = current.next;
					current.next = null;
					current = head;
					length--;
				} else if (current == tail) { // more than 1 carriage and the tail is empty: update tail
					tail = current.prev;
					current.prev.next = null;
					current.prev = null;
					current = tail;
					length--;
					return;
				} else { // more than 2 carriages, somewhere in the middle is empty
					current.prev.next = current.next;
					current.next.prev = current.prev;
					current.prev = null;
					Carriage temp = current.next;
					current.next = null;
					current = temp;
					length--;
				}
			} else { // if the carriage is not empty, just goes forward by 1 carriage
				current = current.next;
			}
		}
	}

	public void unload(Queue<Cargo> cargos) {
		Carriage current = head;
		if (head == null) {
			return;
		}

		if (current == tail) {
			while (!current.cargos.isEmpty()) {
				cargos.add(current.pop());
			}
		} else {
			while (current != null) {
				while (!current.cargos.isEmpty()) {
					cargos.add(current.pop());
				}
				current = current.next;
			}
		}
	}

	public Carriage getHead() {
		return head;
	}

	public int getLength() {
		return length;
	}
}
