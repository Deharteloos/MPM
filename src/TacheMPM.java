import java.util.*;

public class TacheMPM {
    private String nom;
    private int duree; //En jours
    private int debutAuPlusTot;
    private int debutAuPlusTard;
    private List<String> tachesAnt = new ArrayList<>();
    private List<String> tachesImmediatAnt = new ArrayList<>();
    private boolean last = false;

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

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
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

    public void setTachesAnt(List<String> tachesAnt) {
        this.tachesAnt = tachesAnt;
    }

    public void setTachesImmediatAnt(List<String> tachesImmediatAnt) {
        this.tachesImmediatAnt = tachesImmediatAnt;
    }

    public List<String> getTachesImmediatAnt() {
        return tachesImmediatAnt;
    }

    public void setDebutAuPlusTot(HashMap<String, TacheMPM> listeTaches) {
        int tmp;
        if(tachesImmediatAnt.size()== 0)
            this.debutAuPlusTot = 0;
        else{
            tmp = listeTaches.get(tachesImmediatAnt.get(0)).getDuree() + listeTaches.get(tachesImmediatAnt.get(0)).getDebutAuPlusTot();
            for(String tache : tachesImmediatAnt){
                int temp = listeTaches.get(tache).getDuree() + listeTaches.get(tache).getDebutAuPlusTot();
                if(tmp < temp)
                    tmp = temp;
            }
            this.debutAuPlusTot = tmp;
        }
    }

    public void setDebutAuPlusTard(HashMap<String, TacheMPM> listeTaches) {
        int tmp;
        if(tachesImmediatAnt.size() == 0) {}
        if(this.getNom().equals("FIN"))
            this.debutAuPlusTard = this.debutAuPlusTot;
        else {
            List<TacheMPM> list = new ArrayList<>();
            for(Map.Entry<String, TacheMPM> entry : listeTaches.entrySet()) {
                String key = entry.getKey();
                TacheMPM value = entry.getValue();
                if(value.tachesImmediatAnt.contains(this.getNom()))
                    list.add(value);
            }
            //Iterator<TacheMPM> it = list.iterator();
            //int daptard = 0;
            //if(it.hasNext()) daptard = it.next().getDebutAuPlusTard();
            tmp = list.get(0).getDebutAuPlusTard() - this.duree;
            for(TacheMPM tache : list){
                int temp = tache.getDebutAuPlusTard() - this.duree;
                if(tmp > temp)
                    tmp = temp;
            }
            /*tmp = listeTaches.get(tachesImmediatAnt.get(0)).getDebutAuPlusTard() - listeTaches.get(tachesImmediatAnt.get(0)).getDebutAuPlusTot();
            for(String tache : tachesImmediatAnt){
                int temp = this.duree - listeTaches.get(tache).getDebutAuPlusTot();
                if(tmp > temp)
                    tmp = temp;
            }*/
            this.debutAuPlusTard = tmp;
        }
    }
}
