import java.util.*;

public class MPMApp {

    public static void main(String args[]) {
        HashMap<String, TacheMPM> listeTaches = new HashMap<>();
        /*listeTaches.put("A", new TacheMPM("A", 2));
        listeTaches.put("B", new TacheMPM("B", 4));
        listeTaches.put("C", new TacheMPM("C", 4, "A"));
        listeTaches.put("D", new TacheMPM("D", 5, "A,B"));
        listeTaches.put("E", new TacheMPM("E", 6, "C,D"));*/

        listeTaches.put("A", new TacheMPM("A", 4));
        listeTaches.put("B", new TacheMPM("B", 4, "D"));
        listeTaches.put("C", new TacheMPM("C", 4, "A,D,B,H"));
        listeTaches.put("D", new TacheMPM("D", 4, "A"));
        listeTaches.put("E", new TacheMPM("E", 4, "A"));
        listeTaches.put("F", new TacheMPM("F", 4, "A,D"));
        listeTaches.put("G", new TacheMPM("G", 4, "D,F"));
        listeTaches.put("H", new TacheMPM("H", 4, "A,E,I"));
        listeTaches.put("I", new TacheMPM("I", 4, "A,D,E"));

        Map<Integer, List<TacheMPM>> niv = MPM(listeTaches);

        System.out.println("azertyuiop");
    }

    public static Map<Integer, List<TacheMPM>> MPM(HashMap<String, TacheMPM> listeTaches) {

        //Détermination des taches immédiatement antérieures
        for(Map.Entry<String, TacheMPM> entry : listeTaches.entrySet()) {
            String key = entry.getKey();
            TacheMPM value = entry.getValue();
            if(value.getTachesAnt().size() == 0) ;
            else if(value.getTachesAnt().size() == 1) value.getTachesImmediatAnt().addAll(value.getTachesAnt());
            else{
                Set<String> tmp = new HashSet<>();
                List<String> taAnt = value.getTachesAnt();
                for(String tache : taAnt)
                    tmp.addAll(listeTaches.get(tache).getTachesAnt());
                Set<String> tmp_ = new HashSet<>();
                while(tmp.size() != 0){
                    Iterator<String> it = tmp.iterator();
                    Set<String> temp = new HashSet<>();
                    tmp_.addAll(tmp);
                    while(it.hasNext()) {
                        String t = it.next();
                        temp.addAll(listeTaches.get(t).getTachesAnt());
                    }
                    tmp.clear();
                    tmp.addAll(temp);
                }
                for(String tache : listeTaches.get(key).getTachesAnt())
                    if(!tmp_.contains(tache))
                        listeTaches.get(key).getTachesImmediatAnt().add(tache);
            }
        }

        //Regroupement des tâches par niveau
        HashMap<String, TacheMPM> listeCopie = new HashMap<>();
        HashMap<String, TacheMPM> listeCopie_ = new HashMap<>();
        listeCopie.putAll(listeTaches);
        listeCopie_.putAll(listeTaches);
        Map<Integer, List<TacheMPM>> niveaux = new HashMap<>();
        int i = 1, size_ = listeCopie.size();
        while(size_ != 0){
            List<TacheMPM> liste = new ArrayList<>();
            for(Map.Entry<String, TacheMPM> entry : listeCopie.entrySet()) {
                String key = entry.getKey();
                TacheMPM value = entry.getValue();
                if(value.getTachesImmediatAnt().size() == 0){
                    liste.add(listeTaches.get(key));
                    listeCopie_.remove(key);
                    size_--;
                }
            }
            listeCopie.clear();
            listeCopie.putAll(listeCopie_);
            removeStep(listeCopie, liste);
            niveaux.put(new Integer(i), liste);
            i++;
        }

        return niveaux;
    }

    public static void removeStep(HashMap<String, TacheMPM> listeCopie, List<TacheMPM> liste){
        for(TacheMPM t : liste){
            String nom = t.getNom();
            for(Map.Entry<String, TacheMPM> entry : listeCopie.entrySet()) {
                //String key = entry.getKey();
                TacheMPM value = entry.getValue();
                value.getTachesImmediatAnt().remove(nom);
            }
        }
    }

}
