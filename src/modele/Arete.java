//Auteur : Quentin Dumont

package modele;

public class Arete {

  private Paragraphe[] sommets;
  private int poids;

  public Arete(Paragraphe sommet1, Paragraphe sommet2, int poids)
  {
    this.sommets = new Paragraphe[] {sommet1, sommet2};

    /*Pour l'instant, on admet que toutes les arêtes ont le même poids.
    Ce ne sera pas le cas si on considère les probabilités de gagner un combat par exemple.*/
    this.poids = poids;
  }

  public Paragraphe getSommet1(){return this.sommets[0];}
  public Paragraphe getSommet2(){return this.sommets[1];}
  public int getPoids(){return this.poids;}
}
