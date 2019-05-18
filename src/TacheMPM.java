import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TacheMPM {
    private String nom;
    private int duree; //En jours
    private int debutAuPlusTot;
    private int debutAuPlusTard;
    private List<String> tachesAnt = new ArrayList<>();
    private List<String> tachesImmediatAnt = new ArrayList<>();

    public TacheMPM(){

    }

    public TacheMPM(String nom) {
        this.nom = nom;
    }

    public TacheMPM(String nom, int duree) {
        this.nom = nom;
        this.duree = duree;
    }

    public TacheMPM(String nom, int duree, String tachesAnt) {
        this.nom = nom;
        this.duree = duree;
        this.tachesAnt = Arrays.asList(tachesAnt.split(","));
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getDebutAuPlusTot() {
        return debutAuPlusTot;
    }

    public void setDebutAuPlusTot(int debutAuPlusTot) {
        this.debutAuPlusTot = debutAuPlusTot;
    }

    public int getDebutAuPlusTard() {
        return debutAuPlusTard;
    }

    public void setDebutAuPlusTard(int debutAuPlusTard) {
        this.debutAuPlusTard = debutAuPlusTard;
    }

    public List<String> getTachesAnt() {
        return tachesAnt;
    }

    public List<String> getTachesImmediatAnt() {
        return tachesImmediatAnt;
    }
}
