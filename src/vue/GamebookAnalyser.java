//Auteur : Alexis Pestel

package vue;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFileChooser.*;
import java.io.File;

import modele.*;

/*
 * Le symbole (*) signifie que le code fonctionne si les problèmes de threads sont réglés.
 */

public class GamebookAnalyser extends JFrame /* implements ActionListener (*) */ {

  // Initialisation des variables
  private JMenuItem bF1;
  private JLabel informations;
  private String sortie;
  private File fichier;

  // Initialise les données à utiliser pour l'affichage
  String FILE_PATH = "../lib/lonewolf.json";
  String REGEX_SECTION = ".*\"([0-9]+)\":.*";
  String REGEX_BRANCHES = ".*\"section\": \"([0-9]+)\".*";

  ParaMaker maker = new ParaMaker(FILE_PATH,REGEX_SECTION,REGEX_BRANCHES);

  public GamebookAnalyser(int width, int height) {

    super("GAMEBOOK ANALYSER");

    // Initialise la fenêtre de vue
    Container cp = this.getContentPane();
    cp.setLayout(new BorderLayout());

    // Initialise la vue du graphe
    VueGraph graph = new VueGraph(maker.getParas(), width, height);
    JScrollPane scrollFrame = new JScrollPane(graph);
    cp.add(scrollFrame, BorderLayout.CENTER);

    // Iniitialise le JLabel détaillant le graphe
    informations = new JLabel("EXTENSION : " + getExtension(FILE_PATH) + "    TOTAL = " + maker.getNbParas(), SwingConstants.CENTER);
    Font font2 = new Font("Courier", Font.BOLD, 15);
    informations.setFont(font2);
    cp.add(informations, BorderLayout.SOUTH);

    // Iniitialise la Menubar (*)
    JMenuBar menubar = new JMenuBar();
    JMenu bFichier = new JMenu("Fichier ▾");
    bF1 = new JMenuItem("Ouvrir...");

    // Ajoute l'action au bouton "Ouvrir..."
    //bF1.addActionListener(this);

    // Ajoute les éléments principaux de la Menubar à la vue
    bFichier.add(bF1);
    menubar.add(bFichier);
    this.setJMenuBar(menubar);

    // Fonctions veillant au bon fonctionnement de l'affichage
    this.pack();
    this.setSize(1280, 720);
    this.setResizable(false);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setVisible(true);
  }

  // Extrait l'extension d'un fichier à partir d'un chemin donné
  public String getExtension(String path) {
    return path.substring(path.lastIndexOf('.')+1);
  }

  // A décommenter si les problèmes de threads sont réglés (*)
  /*@Override
  public void actionPerformed(ActionEvent e) {
    if(e.getSource() == bF1) {
      JFileChooser dialogue = new JFileChooser(new File("."));

      if(dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
          fichier = dialogue.getSelectedFile();
          sortie = fichier.getPath();
          if(getExtension(sortie).equals("json") || getExtension(sortie).equals("txt")) {
            ParaMaker maker = new ParaMaker(sortie,REGEX_SECTION,REGEX_BRANCHES);
            VueGraph graph = new VueGraph(maker.getParas(), width, height);
            JScrollPane scrollFrame = new JScrollPane(graph);
            cp.add(scrollFrame, BorderLayout.CENTER);
            informations.setText("EXTENSION : " + getExtension(sortie) + "    TOTAL = " + maker.getNbParas());
          }
          else {
            informations.setText("FICHIER NON PRIS EN CHARGE");
          }
      }
    }
  }*/
}
