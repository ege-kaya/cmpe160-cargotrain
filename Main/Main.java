package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import CargoTrain.Train;
import Util.Cargo;
import Util.Station;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		readAndInitialize(args[0], args[1]);
	}

	public static void readAndInitialize(String inputFile, String outputFile) throws FileNotFoundException {
		Station[] stations;
		Scanner readFile = new Scanner(new File(inputFile));
		// parses first line of the input file
		String firstLine = readFile.nextLine();
		Scanner readLine = new Scanner(firstLine);
		int nOfCarriages = readLine.nextInt();
		int carriageCapacity = readLine.nextInt();
		Train train = new Train(nOfCarriages, carriageCapacity);
		int nOfStops = readLine.nextInt();
		stations = new Station[nOfStops];
		for (int i = 0; i < nOfStops; i++) {
			stations[i] = new Station(i, outputFile);
		}

		while (readFile.hasNextLine()) {
			String line = readFile.nextLine();
			Scanner parseLine = new Scanner(line);
			int idCargo = parseLine.nextInt();
			int sourceStation = parseLine.nextInt();
			int targetStation = parseLine.nextInt();
			int cargoSize = parseLine.nextInt();
			Cargo c = new Cargo(idCargo, sourceStation, cargoSize, targetStation);
			stations[sourceStation].addToQueue(c);
			parseLine.close();
		}
		readFile.close();
		readLine.close();
		execute(train, stations);
	}

	public static void execute(Train train, Station[] stations) throws FileNotFoundException {
		for (Station s : stations) {
			s.process(train);
		}
	}
}