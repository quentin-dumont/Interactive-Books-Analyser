//Auteur : Quentin Dumont

package modele;

import java.util.ArrayList;

public class Paragraphe {

  private Integer number;
  private ArrayList<Integer> enfants;
  private ArrayList<Integer> parents;

  public Paragraphe(Integer number, ArrayList<Integer> enfants, ArrayList<Integer> parents)
  {
    this.number = number;
    this.enfants = enfants;
    this.parents = parents;
  }

  public Integer getNumber(){return this.number;}
  public ArrayList<Integer> getEnfants(){return this.enfants;}
  public ArrayList<Integer> getParents(){return this.parents;}

  @Override
  public String toString()
  {
    return "Paragraphe numéro "+this.number+" ";/*+
           "Parents : "+this.parents+"\n"+
           "Enfants : "+this.enfants;*/
  }

}
