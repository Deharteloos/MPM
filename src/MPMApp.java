import java.util.*;

public class MPMApp {

    public static void main(String args[]) {
        HashMap<String, TacheMPM> listeTaches = new HashMap<>();
        /*listeTaches.put("A", new TacheMPM("A", 2));
        listeTaches.put("B", new TacheMPM("B", 4));
        listeTaches.put("C", new TacheMPM("C", 4, "A"));
        listeTaches.put("D", new TacheMPM("D", 5, "A,B"));
        listeTaches.put("E", new TacheMPM("E", 6, "C,D"));*/

        listeTaches.put("A", new TacheMPM("A", 10));
        listeTaches.put("B", new TacheMPM("B", 14, "D"));
        listeTaches.put("C", new TacheMPM("C", 14, "A,D,B,H"));
        listeTaches.put("D", new TacheMPM("D", 8, "A"));
        listeTaches.put("E", new TacheMPM("E", 12, "A"));
        listeTaches.put("F", new TacheMPM("F", 22, "A,D"));
        listeTaches.put("G", new TacheMPM("G", 25, "D,F"));
        listeTaches.put("H", new TacheMPM("H", 18, "A,E,I"));
        listeTaches.put("I", new TacheMPM("I", 6, "A,D,E"));

        MPM(listeTaches);
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
                Set<String> tmp_ = new HashSet<>();
                for(String tache : taAnt)
                    tmp.addAll(listeTaches.get(tache).getTachesAnt());
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

        Map<String, TacheMPM> listetaches_ = new HashMap<>();
        //listetaches_.putAll(listeTaches);
        for(Map.Entry<String, TacheMPM> entry : listeTaches.entrySet()) {
            String key = entry.getKey();
            TacheMPM value = entry.getValue();
            TacheMPM t = new TacheMPM();
            t.setLast(value.isLast());
            t.setNom(value.getNom());
            t.setDuree(value.getDuree());
            t.setDebutAuPlusTot(value.getDebutAuPlusTot());
            t.setDebutAuPlusTard(value.getDebutAuPlusTard());
            List<String> l = new ArrayList<>();
            for(String s : value.getTachesImmediatAnt()){
                String tmp = new String(s);
                l.add(tmp);
            }
            t.setTachesImmediatAnt(l);
            listetaches_.put(key, t);
        }

        //Regroupement des tâches par niveau
        HashMap<String, TacheMPM> listeCopie = new HashMap<>();
        HashMap<String, TacheMPM> listeCopie_ = new HashMap<>();
        listeCopie.putAll(listetaches_);
        listeCopie_.putAll(listetaches_);
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


        //Ajout tache fin
        TacheMPM fin = new TacheMPM("FIN");
        List<TacheMPM> l = new ArrayList<>();
        //Spécification des taches avant la tache de fin
        List<String> stringList = new ArrayList<>();
        for(TacheMPM tache : niveaux.get(new Integer(i-1))){
            stringList.add(tache.getNom());
            tache.setLast(true);
        }
        for(TacheMPM tache_ : niveaux.get(new Integer(i-2))){
            tache_.setLast(true);
            for(TacheMPM tache : niveaux.get(new Integer(i-1))){
                if(tache.getTachesImmediatAnt().contains(tache_.getNom()))
                    tache_.setLast(false);
            }
            if(tache_.isLast())
                stringList.add(tache_.getNom());
        }
        fin.setTachesAnt(stringList);
        fin.setTachesImmediatAnt(stringList);
        l.add(fin);
        niveaux.put(new Integer(i), l);

        //Calcul des dates de début au plus tot et de début au plus tard
        for(Map.Entry<Integer, List<TacheMPM>> entry : niveaux.entrySet()) {
            Integer key = entry.getKey();
            List<TacheMPM> value = entry.getValue();
            for(TacheMPM tacheMPM : value){
                tacheMPM.setDebutAuPlusTot(listeTaches);
                listeTaches.put(tacheMPM.getNom(), tacheMPM);
            }
        }
        for(int j = niveaux.size(); j > 0; j--){
            List<TacheMPM> value = niveaux.get(new Integer(j));
            for(TacheMPM tacheMPM : value){
                tacheMPM.setDebutAuPlusTard(listeTaches);
                listeTaches.put(tacheMPM.getNom(), tacheMPM);
            }
        }





        /**
         * Représentation du graphe par liste d'adjacences
         * Chaque élément de la liste est représenté avec le formalisme suivant:
         *  (nom_de_la_tache, durée, date_debut_plus_tot, date_debut_plus_tard)
         */
        System.out.print("(DEBUT, 0, 0, 0) -> [");
        List<TacheMPM> taches = niveaux.get(1);
        for(TacheMPM tch : taches){
            afficherTache(tch);
            System.out.print(" ; ");
        }
        System.out.println("]");
        for(int a = 1; a < niveaux.size(); a++){
            for(TacheMPM t : niveaux.get(a)){
                afficherTache(t);
                System.out.print(" -> [");
                for(TacheMPM t_ : niveaux.get(a+1)){
                    if(t_.getTachesImmediatAnt().contains(t.getNom())) {
                        afficherTache(t_);
                        System.out.print(" ; ");
                    }
                }
                System.out.println("]");
            }
        }


        //Affichage du chemin critique
        System.out.println();
        System.out.println();
        System.out.println("----> Chemin critique <----");
        System.out.print("DEBUT");
        for(Map.Entry<String, TacheMPM> entry : listeTaches.entrySet()) {
            String key = entry.getKey();
            TacheMPM value = entry.getValue();
            if(value.getDebutAuPlusTard() == value.getDebutAuPlusTot()){
                System.out.print( " => ");
                afficherTache(value);
            }
        }
        System.out.println();
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

    public static void afficherTache(TacheMPM tache) {
        System.out.print("(");
        System.out.print(tache.getNom()+", "+tache.getDuree()+", "+tache.getDebutAuPlusTot()+", "+tache.getDebutAuPlusTard());
        System.out.print(")");
    }

}
