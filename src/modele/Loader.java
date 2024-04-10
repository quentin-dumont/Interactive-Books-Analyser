//Auteur : Alexis Pestel

package modele;

import java.io.*;

public class Loader {

  private BufferedReader book;

  public Loader(String filePath)
  {
    try{this.book = new BufferedReader(new FileReader(filePath));}
    catch (FileNotFoundException e){System.err.println("Le fichier spécifié est introuvable.");}
  }

  public BufferedReader getBook(){return this.book;}

  public void closeFile()
  {
    try {this.book.close();}
    catch (IOException e) {System.err.println("Erreur fatale lors de la fermeture du fichier.");}
  }

}
