package Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import CargoTrain.Train;

public class Station {
	private int id;
	private Queue<Cargo> cargoQueue = new LinkedList<Cargo>();
	private static PrintStream printStream;

	public Station(int id, String outputFile) throws FileNotFoundException {
		this.id = id;
		Station.printStream = new PrintStream(new File(outputFile));
	}

	public void process(Train train) throws FileNotFoundException {
		train.unload(cargoQueue);
		Iterator<Cargo> it = cargoQueue.iterator();
		Queue<Cargo> toBeLoaded = new LinkedList<Cargo>();
		while (it.hasNext()) {
			Cargo box = it.next();
			if (box.getTargetStation() != this.id) {
				toBeLoaded.add(box);
			} else {
				printStream.println(box);
			}
		}
		train.load(toBeLoaded);
		printStream.println(this.id + " " + train.getLength());
	}

	public void addToQueue(Cargo c) {
		this.cargoQueue.add(c);
	}
}