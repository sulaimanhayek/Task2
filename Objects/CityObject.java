package Objects;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

public class CityObject {
    public int rowOfCity;
    public int ColOfCity;
    public int bonus;
    public int maximumCityTime;
    public ArrayList<Ride> rides;

    public CityObject(int rowOfCity, int colOfCity, int bouns, int maximumCityTime, ArrayList<Ride> rides) {
        this.rowOfCity = rowOfCity;
        ColOfCity = colOfCity;
        this.bonus = bouns;
        this.maximumCityTime = maximumCityTime;
        this.rides = rides;
    }
    public  Object[] bestRideE(Motors motors, boolean[] s) {
        double bestScore = -1;
        Ride bestRide = null;
        for (int i = 0; i < s.length - 1; ++i) {
            if (!s[i] && motors.canComplete(this.rides.get(i))) {
                Ride currentRide = this.rides.get(i);
                double currentScore = motors.points(currentRide, this.bonus) / motors.totalTimeSpent(currentRide);
                if (currentScore > bestScore) {
                    bestScore = currentScore;
                    bestRide = currentRide;
                }
            }
        }

        return new Object[]{bestRide,s};
    }

        public ArrayList<Ride> remaining() {
        ArrayList<Ride> rem = new ArrayList<>();
        for (int i = 0; i < Taken.taken.length; ++i) {
            if (!Taken.taken[i]) {
                rem.add(this.rides.get(i));
            }
        }

        return rem;
    }
    
}
