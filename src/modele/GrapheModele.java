//Auteur : Quentin Dumont

package modele;

import java.util.*;

public class GrapheModele {

    private ArrayList<Paragraphe> sommets;
    public static int NOT_VOISIN = 100000;
    /*La structure suivante permet d'accéder en temps constant aux voisins du sommet, tout en gardant
    une information cruciale pour réaliser par la suite un graphe probabiliste : le poids des arêtes*/
    //(K,V) -> (Numéro de paragraphe, Liste de ses arêtes)
    private HashMap<Integer, ArrayList<Arete>> aretes = new HashMap<>();

    public GrapheModele(ArrayList<Paragraphe> sommets)
    {
        this.sommets = sommets;

        for(int i = 0; i < sommets.size(); i++)
        {
            ArrayList<Integer> enfants = sommets.get(i).getEnfants();
            ArrayList<Arete> aretesDuSommet = new ArrayList<>();
            for(int j = 0; j < enfants.size(); j++)
            {
                //on retire 1 à l'index de l'enfant car l'ArrayList indexe à partir de 0 et les numéros des paras commencent à 1.
                aretesDuSommet.add(new Arete(sommets.get(i), sommets.get(enfants.get(j)-1), 1));
            }
            this.aretes.put((i+1), aretesDuSommet);
        }
    }

    public HashMap<Integer, ArrayList<Arete>> getAretes(){return this.aretes;}
    public int getNbSommets(){return this.sommets.size();}

    @Override
    public String toString()
    {
        return "Graphe composé de "+this.sommets.size()+" sommets et "+this.aretes.size()+" arêtes.";
    }

    public int[][] allPairsShortestPath() {
        int[][] allPairsShortestPath = new int[this.sommets.size()][this.sommets.size()];
        for (int i = 0; i < this.sommets.size(); i++) {
          allPairsShortestPath[i] = this.dijkstra(i);
        }
        return allPairsShortestPath;
      }

    //Retourne une liste des plus courtes distances entre le sommet de départ et les autres sommets
    private int[] dijkstra(int indexSommet)
    {
        int compteur = this.sommets.size();
        int[] distances = new int[compteur];
        boolean[] visited = new boolean[compteur];

        for(int i = 0; i < this.sommets.size(); i++)
        {
            visited[i] = false;
            distances[i] = NOT_VOISIN;
        }
        distances[indexSommet] = 0;

        //Tant que tous les sommets n'ont pas été visités
        while(compteur > 0)
        {
            /*on initialise une distance de base à NOT_VOISIN,
            et un supposé "sommet le plus proche" à -1*/
            int minimum = NOT_VOISIN;
            int sommet1 = -1;
            //on parcourt tous les sommets à la recherche du plus proche non visité
            for(int som = 0; som < this.sommets.size(); som++)
            {
                if(!visited[som] && distances[som] < minimum)
                {
                    sommet1 = som; //on enregistre ce sommet
                    minimum = distances[som]; //on fixe la distance minimale à celle du plus proche
                    visited[som] = true;

                    /*on récupère les voisins du sommet le plus proche (grâce aux arêtes)
                    et on met à jour leurs distances par rapport à l'origine*/
                    ArrayList<Arete> aretesSommet1 = this.aretes.get(sommet1+1);
                    for(int ar = 0; ar < aretesSommet1.size(); ar++)
                    {
                        Arete arete = aretesSommet1.get(ar); //on sélectionne l'arête concernée
                        int sommet2 = arete.getSommet2().getNumber()-1; //on récupère le deuxième sommet situé à son extrêmité

                        /*on met à jour la distance entre le sommet2 et l'origine si elle est plus grande
                        que la distance entre le premier sommet (+ le poids de l'arête reliant
                        les deux sommets) et l'origine.
                        */
                        if(distances[sommet2] > distances[sommet1] + arete.getPoids())
                            distances[sommet2] = distances[sommet1] + arete.getPoids();
                    }
                }
            }
            compteur -= 1; //on décrémente le compteur de sommets non visités
        }
        //affichage compréhensible du tableau de distances
        /*for(int i = 0; i < distances.length; i++)
        {
            System.out.println("Nombre d'étapes pour aller du paragraphe "+(indexSommet+1)+
            " au paragraphe "+(i+1)+" : "+distances[i]);
        }*/
        return distances;
    }


}
