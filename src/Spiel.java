import java.time.LocalDate;

public class Spiel {
    private int Id;
    private String Team1;
    private String Team2;
    private String Spielort;
    private int Kapazität;
    private String Datum;

    public Spiel() {}

    public Spiel(int id, String team1, String team2, String spielort, int kapazität, String datum) {
        Id = id;
        Team1 = team1;
        Team2 = team2;
        Spielort = spielort;
        Kapazität = kapazität;
        Datum = datum;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTeam1() {
        return Team1;
    }

    public void setTeam1(String team1) {
        Team1 = team1;
    }

    public String getTeam2() {
        return Team2;
    }

    public void setTeam2(String team2) {
        Team2 = team2;
    }

    public String getSpielort() {
        return Spielort;
    }

    public void setSpielort(String spielort) {
        Spielort = spielort;
    }

    public int getKapazität() {
        return Kapazität;
    }

    public void setKapazität(int kapazität) {
        Kapazität = kapazität;
    }

    public String getDatum() {
        return Datum;
    }

    public void setDatum(String datum) {
        Datum = datum;
    }

    // Helper method to get the date as LocalDate
    public LocalDate getLocalDate() {
        return LocalDate.parse(Datum);
    }

    @Override
    public String toString() {
        return "Spiel{" +
                "Id=" + Id +
                ", Team1='" + Team1 + '\'' +
                ", Team2='" + Team2 + '\'' +
                ", Spielort='" + Spielort + '\'' +
                ", Kapazität=" + Kapazität +
                ", Datum='" + Datum + '\'' +
                '}';
    }
}
