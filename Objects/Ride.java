package Objects;

public class Ride {
    public int startTime;
    public int EndsTime;
    public Coor startCorr;
    public Coor endsCorr;
    public int ID;
    public int rideDistance;

    public Ride(int id, Coor start, Coor finish, int startTime, int finishTime) {
        this.startTime = startTime;
        this.EndsTime = finishTime;
        this.startCorr = start;
        this.endsCorr = finish;
        this.ID = id;
        this.rideDistance = startCorr.distanceTo(endsCorr);
    }
}
