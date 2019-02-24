package plugin.getDataPlugin;

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
}
