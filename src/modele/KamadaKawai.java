//Auteur : Quentin Dumont

package modele;

import java.util.*;

public class KamadaKawai {

  private static final double REPULSION = 2.0;
  private static final double EPSILON = 0.0001;
  private static final double LEARNING_RATE = 0.001; //taux d'apprentissage pour la descente de gradients

  private int displayWidth;
  //private int displayHeight;

  private int nbSommets;
  private GrapheModele graphe; //le graphe qui contient les méthodes pour faire le APSP

  private int[][] chemins; //matrice des All-Pairs-Shortest-Path
  private double[][] forcesAretes; //matrice de force des arêtes
  private double[][] longueurs; //longueurs réelles des arêtes

  private double longueurArete; //longueur d'une arête pour une unité de distance
  private HashMap<Integer, double[]> positions; //structure qui stocke les coordonnées d'un paragraphe

  private double maxForce = Double.MAX_VALUE;

  public KamadaKawai(GrapheModele graphe, int displayWidth, int displayHeight) {
    this.graphe = graphe;
    this.nbSommets = graphe.getNbSommets();
    this.chemins = graphe.allPairsShortestPath();
    this.longueurArete = this.findLongueurArete(displayWidth);
    this.displayWidth = displayWidth;
    //this.displayHeight = displayHeight;

    this.fillMatrices(); //remplit les deux autres matrices
    this.fillPositions(); //place les paragraphes à des positions arbitraires

    
    //int replacements = 0;
    while(this.maxForce > EPSILON)
    {
      int keySommetMax = this.findMaxForce();
      double forcePara = this.maxForce;
      //System.out.println("coucou");
      
      int max_iterations = 0;
      while(forcePara > EPSILON)
      {
        if(!(this.positions.get(keySommetMax)[0] - LEARNING_RATE * energyDeriv(keySommetMax, false) < 0 || this.positions.get(keySommetMax)[0] - LEARNING_RATE * energyDeriv(keySommetMax, false) > displayWidth))
        {
          this.positions.get(keySommetMax)[0] -= LEARNING_RATE * energyDeriv(keySommetMax, false);
        }

        if(!(this.positions.get(keySommetMax)[1] - LEARNING_RATE * energyDeriv(keySommetMax, true) < 0 || this.positions.get(keySommetMax)[1] - LEARNING_RATE * energyDeriv(keySommetMax, false) > displayHeight))
        {
          this.positions.get(keySommetMax)[1] -= LEARNING_RATE * energyDeriv(keySommetMax, true);
        }
        forcePara = this.forceOfSommet(keySommetMax);
        System.out.println("Force du paragraphe "+keySommetMax+" : "+forcePara);

        if(max_iterations > 100000)
          {
            //replacements += 1;
            this.randomPosition(keySommetMax);
            keySommetMax = this.findMaxForce();
            forcePara = this.maxForce;
            max_iterations = 0;
          }
          else
            max_iterations += 1;
        /*if(replacements < 10)
        {
          
        }
        else break;*/
      }
      //if(replacements >= 10) break;
    }
  }

  /*
   * CREATION DE LA MATRICE DE TOUS LES CHEMINS DE LONGUEUR MINIMALE
   * ENTRE PAIRES DE SOMMETS.
   */

  public HashMap<Integer, double[]> getPositions(){return this.positions;}

  private void fillPositions() {
    this.positions = new HashMap<>();
    for (int x = 0; x < this.nbSommets; x++) {
      Random generator = new Random(System.currentTimeMillis()+(x*10));
      this.positions.put((x + 1), new double[] {generator.nextInt(displayWidth), generator.nextInt(displayWidth)});
    }
  }

  private void randomPosition(int keySommet) {
      Random generator = new Random(System.currentTimeMillis());
      this.positions.put(keySommet, new double[] {generator.nextInt(displayWidth), generator.nextInt(displayWidth)});
  }

  private void fillMatrices() //remplit forcesAretes et longueurs
  {
    this.forcesAretes = new double[this.nbSommets][this.nbSommets];
    this.longueurs = new double[this.nbSommets][this.nbSommets];
    for(int i = 0; i < this.nbSommets-1; i++)
    {
      for(int j = (i); j < this.nbSommets; j++)
      {
        if(this.chemins[i][j] < Integer.MAX_VALUE && i != j)
        {
          this.longueurs[i][j] = this.longueurArete * this.chemins[i][j];
          this.forcesAretes[i][j] = REPULSION / Math.pow(this.chemins[i][j], 2);
        }
        else
        {
          this.longueurs[i][j] = Integer.MAX_VALUE;
          this.forcesAretes[i][j] = 0;
        }
      }
    }
  }

  private double findLongueurArete(int displayWidth)
  {
    // calcul de la longueur d'une arête de base
    double maxDist = 0;
    for (int i = 0; i < chemins.length; i++) {
      for (int j = 0; j < chemins.length; j++) {
        if (i < j) {
          if (chemins[i][j] != Integer.MAX_VALUE && chemins[i][j] > maxDist)
          {
            maxDist = chemins[i][j];
          }
        }
      }
    }
    System.out.println("Une unité d'arête : "+(displayWidth/maxDist));
    return displayWidth/maxDist;
  }

  private double energy()
  {
    double energy = 0;
    for(int i = 0; i < this.nbSommets-1; i++)
    {
      for(int j = (i+1); j < this.nbSommets; j++)
      {
          double[] posPara1 = this.positions.get((i+1));
          double[] posPara2 = this.positions.get((j+1));
          double distX = Math.pow((posPara1[0] - posPara2[0]),2);
          double distY = Math.pow((posPara1[1] - posPara2[1]),2);
          energy += 0.5*this.forcesAretes[i][j]*(distX +
                                       distY +
                                       Math.pow(this.longueurs[i][j], 2) -
                                       (2*this.longueurs[i][j]) *
                                       Math.sqrt(distX + distY));
      }
    }
    return energy;
  }

  /*
    Trouve le paragraphe qui produit le plus de force (donc
    trouve celui qu'il faut déplacer en priorité.)
    Retourne une clé à utiliser dans la HashMap positions.
  */
  private int findMaxForce()
  {
    Double maxForce = Double.MIN_VALUE;
    Integer keyMaxSommet = -1;
    for(int i = 1; i <= this.nbSommets; i++)
    {

      double force = forceOfSommet(i);
      if(force > maxForce)
      {
        maxForce = force;
        keyMaxSommet = i;
      }
    }
    this.maxForce = maxForce;
    return keyMaxSommet;
  }

  private double forceOfSommet(int keySommet)
  {
    return Math.sqrt(
        Math.pow(energyDeriv(keySommet,false),2) +
        Math.pow(energyDeriv(keySommet,true),2)
      );
  }

  private double energyDeriv(int keySommet, boolean isDy)
  {
    /*la ligne ci-dessous définit la seule différence entre
    les deux dérivées partielles. Cela évite d'écrire deux
    fonctions quasi identiques.*/
    int coordSommet = isDy ? 1 : 0;
    double[] posSommet = this.positions.get(keySommet);
    double res = 0;
    /*on ne parcourt que sur le sommet en question, car les positions
    des autres sont supposées constantes et la dérivée de leur énergie
    est donc égale à 0.*/
    for(int j = 0; j < this.nbSommets; j++)
    {
      if(j != keySommet-1 /*&& this.chemins[keySommet-1][j] != Integer.MAX_VALUE*/)
      {
        double[] posSommet2 = this.positions.get((j+1));
        double distanceReelle = Math.sqrt(Math.pow(posSommet[0]-posSommet2[0],2) + Math.pow(posSommet[1]-posSommet2[1],2));

        res +=
          0.5 * this.forcesAretes[keySommet-1][j] * (
            2*(posSommet[coordSommet] - posSommet2[coordSommet]) - 
            (
              2*this.longueurs[keySommet-1][j]*(posSommet[coordSommet] - posSommet2[coordSommet]) 
              / distanceReelle
            )
          );
      }
    }
    return res;
  }
/*
  if(j != keySommet-1)
  {
    double[] posSommet2 = this.positions.get((j+1));
    double distanceReelle = Math.sqrt(Math.pow(posSommet[0]-posSommet2[0],2) + Math.pow(posSommet[1]-posSommet2[1],2));
    0.5
  }
*/
}
