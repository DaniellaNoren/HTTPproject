package plugin.storage.getStatisticsPlugin;

//Behöver troligtvis inte ett object för detta men har inte tillräckligt med tid kvar för att hitta en sätt att
//konvertera till json på ett bättre sätt
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
