
import Objects.*;
import au.com.bytecode.opencsv.CSVWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] argv) {
        String cityName = "C:\\Users\\azozs\\Desktop\\untitled1\\dataset\\Karada.csv";
        Object[] in = parseCSV(cityName);
        ArrayList<Motors> motorsArrayList = (ArrayList<Motors>) in[0];
        System.out.println(motorsArrayList.size());
        CityObject city = (CityObject) in[1];
        boolean[] a = (boolean[]) in[2];
        checkingTheRoad(motorsArrayList, city, a);
        prettyPrint(motorsArrayList);
    }

    private static void checkingTheRoad(ArrayList<Motors> in, CityObject city, boolean[] s) {
        Motors freeOne;
        while ((freeOne = Motors.firstFree(in)).clock < city.maximumCityTime) {
            Object[] rr = city.bestRideE(freeOne, s);
            Ride bestRide = (Ride) rr[0];
            s = (boolean[]) rr[1];
            if (bestRide != null) {
                freeOne.takeRide(bestRide);
                s[bestRide.ID] = true;
                // System.out.println(freeOne.ID + " best rides = " + bestRide.ID);
            } else {
                freeOne.kill(city.maximumCityTime);
            }
        }
        for (int t = 0; t < 5; t++) {
            for (Motors motors : in) {
                in.set(motors.ID, motors);
                System.out.println(motors.ID + " no of rides = " + motors.rides.get(2).ID + " rides = " + motors.rides.size());
            }
        }
    }

    private static void prettyPrint(ArrayList<Motors> fleet) {
        BufferedWriter bw = null;
        for (Motors motors : fleet) {
            try {
                String outputFile1 = "C:\\Users\\azozs\\Desktop\\untitled1\\outpokedex1.csv";
                FileWriter fileWriter = new FileWriter(outputFile1, true);
                bw = new BufferedWriter(fileWriter);
                for (String pokemon : motors.toString().split(",")) {
                    System.out.println(pokemon);
                    bw.write(pokemon.replace(" ", ","));
                    bw.append('\n');
                    bw.flush();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            // System.out.println(motors.ID + " no of rides = " + motors.rides.get(0).ID + " rides = " + motors.rides.size());
        }
    }

    public static Object[] parseCSV(String filename) {
        Reader reader = null;
        CSVParser csvParser = null;
        try {
            reader = Files.newBufferedReader(Paths.get(filename));
            csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        int R = 0;
        int C = 0;
        int F = 0;
        int N = 0;
        int B = 0;
        int T = 0;
        ArrayList<Ride> ridesList = null;
        ArrayList<Motors> fleet = null;

        Iterator<CSVRecord> recordIter = csvParser.iterator();
        int i = 0;
        while (recordIter.hasNext()) {
            CSVRecord record = recordIter.next();

            if (i == 0) {
                R = Integer.valueOf(record.get(0));
                C = Integer.valueOf(record.get(1));
                F = Integer.valueOf(record.get(2));
                N = Integer.valueOf(record.get(3));
                B = Integer.valueOf(record.get(4));
                T = Integer.valueOf(record.get(5));

                fleet = new ArrayList<>();
                ridesList = new ArrayList<>();

                for (int j = 0; j < F; ++j) {
                    fleet.add(new Motors(j));
                }

            } else {
                int sx = Integer.valueOf(record.get(0));
                int sy = Integer.valueOf(record.get(1));
                int fx = Integer.valueOf(record.get(2));
                int fy = Integer.valueOf(record.get(3));
                Coor start = new Coor(sx, sy);
                Coor finish = new Coor(fx, fy);
                int ts = Integer.valueOf(record.get(4));
                int tf = Integer.valueOf(record.get(5));
                ridesList.add(new Ride(i, start, finish, ts, tf));
            }
            i++;
        }

        CityObject city = new CityObject(R, C, B, T, ridesList);
        boolean[] s = new boolean[ridesList.size()];

        return new Object[]{fleet, city, s};
    }

}

