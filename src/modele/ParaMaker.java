//Auteur : Quentin Dumont

package modele;

import java.io.*;
import java.util.Map;
import java.util.regex.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ParaMaker {

  private Loader loader;
  private Extractor extractor;
  private ArrayList<Paragraphe> paras;

  public ParaMaker(String filePath, String regSection, String regEnfants)
  {
    this.loader = new Loader(filePath);
    this.extractor = new Extractor(regSection, regEnfants);
    this.paras = new ArrayList<Paragraphe>();

    HashMap<Integer, ArrayList<Integer>> dicoE = this.dicoEnfants();
    HashMap<Integer, ArrayList<Integer>> dicoP = this.dicoParents(dicoE);

    for(int i = 1; i <= dicoE.size(); i++)
    {
      ArrayList<Integer> enfants = dicoE.get(i);
      ArrayList<Integer> parents = dicoP.get(i);
      this.paras.add(new Paragraphe(i,enfants,parents));
    }

    this.loader.closeFile();
  }

  public int getNbParas(){return this.paras.size();}
  public ArrayList<Paragraphe> getParas(){return this.paras;}

  private HashMap<Integer,ArrayList<Integer>> dicoEnfants()
  {
    HashMap<Integer,ArrayList<Integer>> enfants = new HashMap<Integer,ArrayList<Integer>>();
    String paraSection = "";
    String line = "";
    boolean isSaving = false;
    try
    {
      while ((line = this.loader.getBook().readLine()) != null) {

        if(!isSaving)
        {
          if(Pattern.matches(this.extractor.getRegSection(),line))
          {
            isSaving = true;
            paraSection += line + "\n";
          }
        }
        else
        {
          if(Pattern.matches(this.extractor.getRegSection(),line))
          {
            enfants.put(this.extractor.getNumber(paraSection),this.extractor.getEnfants(paraSection));
            paraSection = line + "\n";
          }
          else
          {
            paraSection += line + "\n";
          }
        }
      }
    }
    catch (IOException e) {System.err.println("Erreur fatale lors de la lecture du fichier.");}
    enfants.put(this.extractor.getNumber(paraSection),this.extractor.getEnfants(paraSection));
    return enfants;
  }

  private HashMap<Integer,ArrayList<Integer>> dicoParents(HashMap<Integer,ArrayList<Integer>> dicoEnfants)
  {
    HashMap<Integer,ArrayList<Integer>> dicoParents = new HashMap<Integer,ArrayList<Integer>>();
    //création des clés du dico des Parents
    for (Map.Entry<Integer, ArrayList<Integer>> dico : dicoEnfants.entrySet()) {
      Integer cle = dico.getKey();
      dicoParents.put(cle,new ArrayList<Integer>());
    }
    //remplissage des arraylists du dico des Parents
    for (Map.Entry<Integer, ArrayList<Integer>> dico : dicoEnfants.entrySet()) {
      //on parcourt les listes du dico des Enfants
      ArrayList<Integer> enfants = dico.getValue();
      for (int i = 0; i < enfants.size(); i++)
      {
        //on ajoute la clé du dico des Enfants à la liste des Parents
        dicoParents.get(enfants.get(i)).add(dico.getKey());
      }
    }
    return dicoParents;
  }


}
