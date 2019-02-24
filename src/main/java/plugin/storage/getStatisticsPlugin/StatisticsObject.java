package plugin.storage.getStatisticsPlugin;

public class StatisticsObject {

    private String timeOfDay;
    private String counter;

    public StatisticsObject(String timeOfDay, String counter) {
        this.timeOfDay = timeOfDay;
        this.counter = counter;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public String getCounter() {
        return counter;
    }

    @Override
    public String toString() {
        return "StatisticsObject{" +
                "timeOfDay='" + timeOfDay + '\'' +
                ", counter='" + counter + '\'' +
                '}';
    }
}
