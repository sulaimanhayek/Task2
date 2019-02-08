package Objects;

import java.util.ArrayList;

public class Motors {

    public int ID;
    public Coor pos;
    public int clock;
    public ArrayList<Ride> rides;

    public Motors() {
        this(-1, 0, 0);
    }

    public Motors(int id) {
        this(id, 0, 0);
    }

    public Motors(int id, int x, int y) {
        this(id, new Coor(x, y), 0);
    }

    public Motors(int id, Coor pos, int clock) {
        this.ID = id;
        this.pos = pos;
        this.clock = clock;
        this.rides = new ArrayList<>();
    }

    public void takeRide(Ride ride) {
        this.rides.add(ride);
        this.clock += this.totalTimeSpent(ride);
        this.pos = ride.endsCorr;
    }

    public void kill(int maxtime) {
        this.clock = maxtime;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("" + rides.size());
        for (Ride r : rides) {
            str.append(" ").append(r.ID);
        }
        return str.toString();
    }

    public boolean canComplete(Ride ride) {
        return this.clock + this.pos.distanceTo(ride.startCorr) + ride.rideDistance <= ride.EndsTime;
    }

    public double points(Ride ride, int bonus) {
        if (this.clock + this.pos.distanceTo(ride.startCorr) > ride.startTime) {
            bonus = 0;
        }

        return ride.rideDistance + bonus;
    }


    public int timeToWait(Ride ride) {
        return  (ride.startTime - this.clock - this.pos.distanceTo(ride.startCorr));
    }


    public int totalTimeSpent(Ride ride) {
        return this.timeWasted(ride) + ride.rideDistance;
    }


    public int timeWasted(Ride ride) {
        int wait = this.timeToWait(ride);
        if (wait < 0) {
            wait = 0;
        }
        return this.pos.distanceTo(ride.startCorr) + wait;
    }

    public int totalScore(int bonus) {
        int score = 0;
        Motors explorer = new Motors();
        for (Ride ride : this.rides) {
            score += explorer.points(ride, bonus);
            explorer.takeRide(ride);
        }

        return score;
    }
    public static Motors firstFree(ArrayList<Motors> motors) {
        int pos_min = 0;
        for (int i = 1; i < motors.size(); ++i) {
            if (motors.get(i).clock < motors.get(pos_min).clock) {
                pos_min = i;
            }
        }

        return motors.get(pos_min);
    }
}
