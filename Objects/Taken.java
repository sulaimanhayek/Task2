package Objects;

import java.util.ArrayList;

public class Taken {
  public static boolean[] taken;

    public Taken(ArrayList<Ride> rides) {
        taken = new boolean[rides.size()];
    }
}
