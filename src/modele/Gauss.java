//Auteur : Quentin Dumont

package modele;

public class Gauss {
    
    private double[][] matrice;
    private int hauteur;
    private int longueur;

    private double[] solutions;

    public Gauss(double[][] gaussMatrice)
    {
        this.matrice = gaussMatrice;
        this.hauteur = gaussMatrice.length;
        this.longueur = gaussMatrice[0].length;
        this.solutions = new double[this.hauteur];

        int dernierPivot = 0;
        //System.out.println(this);
        for(int j = 0; j < this.longueur; j++)
        {
            /*System.out.println("Nouveau tour de boucle");
            System.out.println(this);*/
            for(int i = dernierPivot; i < this.hauteur; i++)
            {
                if(this.matrice[i][j] != 0)
                {
                    /*si la ligne ne correspond pas à celle du dernier pivot,
                    on permute les deux lignes*/
                    if(i != dernierPivot)
                    {
                        //System.out.println("Permutation");
                        this.swapLines(dernierPivot,i);
                        //System.out.println(this);
                    }
                      
                    //calcul du pivot et mise à jour de la ligne
                    double coeffPivot = this.matrice[dernierPivot][j];
                    for(int element = 0; element < this.longueur; element++)
                      this.matrice[dernierPivot][element] *= Math.pow(coeffPivot,-1);
                    /*System.out.println("Calcul du pivot");
                    System.out.println(this);*/

                    //normalisation des autres lignes
                    for(int ligne = 0; ligne < this.hauteur; ligne++)
                    {
                      if(ligne != dernierPivot)
                      {
                        double coeff = this.matrice[ligne][j];
                        for(int element = 0; element < this.longueur; element++)
                        {
                          this.matrice[ligne][element] -= coeff*this.matrice[dernierPivot][element];
                        }
                      }
                      /*System.out.println("Normalisation de la ligne "+ligne);
                      System.out.println(this);*/
                    }
                    //on incrémente la ligne du pivot
                    dernierPivot += 1;     
                }
            }
        }
        System.out.println(this);
        for(int i = 0; i < this.hauteur; i++)
          this.solutions[i] =(int) this.matrice[i][this.longueur-1];
    }

    private void swapLines(int line1, int line2)
    {
        double[] tmp = new double[this.longueur];
        for(int i = 0; i < this.longueur; i++)
          tmp[i] = this.matrice[line1][i];
        
        for(int i = 0; i < this.longueur; i++)
          this.matrice[line1][i] = this.matrice[line2][i];

        for(int i = 0; i < this.longueur; i++)
          this.matrice[line2][i] = tmp[i];
    }

    public double[] getSolutions(){return this.solutions;}

    @Override
    public String toString()
    {
        String chaine = "---------\n";
        for(int i = 0; i < this.matrice.length; i++)
        {
            for(int j = 0; j < this.matrice[0].length; j++)
              chaine += this.matrice[i][j]+" ";
            chaine += "\n";            
        }
        return chaine;
    }
}
