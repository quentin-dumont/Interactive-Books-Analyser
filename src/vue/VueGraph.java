//Auteur : Quentin Dumont

package vue;

import java.util.*;
import javax.swing.*;
import java.awt.*;

//import des packages utiles de JUNG
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.visualization.*;
import edu.uci.ics.jung.algorithms.layout.*;
import org.apache.commons.collections15.Transformer;

//import du modèle pour l'extraction des paragraphes
import modele.*;

public class VueGraph extends JPanel {

  public VueGraph(ArrayList<Paragraphe> sommets, int displayWidth, int displayHeight) {
    //On crée un graphe orienté vide
    Graph<Integer, String> graphe = new DirectedSparseGraph<Integer, String>();

    //On lui donne les sommets à partir de notre liste de paras
    for (int i = 0; i < sommets.size(); i++) {
      graphe.addVertex(sommets.get(i).getNumber());
    }

    //On lui donne ensuite les arêtes, toujours à partir des paras

    for(int i = 0; i < sommets.size(); i++)
    {
        ArrayList<Integer> enfants = sommets.get(i).getEnfants();
        for(int j = 0; j < enfants.size(); j++)
        {
          graphe.addEdge(sommets.get(i).getNumber()+"-"+enfants.get(j), sommets.get(i).getNumber(), enfants.get(j));
        }
    }

    //Le KamadaKawaiLayout de JUNG nous permet d'ajuster l'espacement entre les sommets.
    KKLayout<Integer,String> kamadaLayout = new KKLayout<Integer, String>(graphe);
    kamadaLayout.setLengthFactor(1.8);

     /*
     * On crée la représentation graphique, en utilisant l'algorithme de
     * kamada kawai.
     */
    VisualizationViewer<Integer, String> representation = new VisualizationViewer<>(
        kamadaLayout,
        new Dimension(displayWidth, displayHeight)
    );

    //On étiquette les sommets
    representation.getRenderContext().setVertexLabelTransformer(new Etiquette());

    

    this.add(representation);
  }

  // Sert à afficher le numéro du paragraphe à côté de sa représentation graphique
  public class Etiquette implements Transformer<Integer, String> {
    @Override
    public String transform(Integer sommet) {
      return sommet.toString();
    }
  }

}
