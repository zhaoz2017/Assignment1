public class LiftRide {
  int time;
  int liftId;

  public int getTime() {
    return time;
  }

  public void setTime(int time) {
    this.time = time;
  }

  public int getLiftId() {
    return liftId;
  }

  public void setLiftId(int liftId) {
    this.liftId = liftId;
  }

  public LiftRide(int time, int liftId) {
    this.time = time;
    this.liftId = liftId;
  }

  @Override
  public String toString() {
    return "LiftRide{" +
        "time=" + time +
        ", liftId=" + liftId +
        '}';
  }
}
