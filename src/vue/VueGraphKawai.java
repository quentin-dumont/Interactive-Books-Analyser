//Auteur : Quentin Dumont

package vue;

import java.util.*;
import javax.swing.*;
import java.awt.*;

import modele.*;

public class VueGraphKawai extends JPanel {

  private static final int TAILLE_PARA = 20;

  private int displayWidth;
  private int displayHeight;

  private int nbSommets;
  private HashMap<Integer, ArrayList<Arete>> aretes;
  private HashMap<Integer, double[]> positions;

  public VueGraphKawai(ArrayList<Paragraphe> sommets, int displayWidth, int displayHeight)
  {
    GrapheModele graphe = new GrapheModele(sommets);
    KamadaKawai kawai = new KamadaKawai(graphe, displayWidth, displayHeight);

    this.setPreferredSize(
      new Dimension(displayWidth,
                    displayHeight)
    );

    this.nbSommets = sommets.size();
    this.aretes = graphe.getAretes();
    this.positions = kawai.getPositions();
    this.displayWidth = displayWidth;
    this.displayHeight = displayHeight;
  }

  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    for(int i = 1; i <= this.nbSommets; i++)
    {
      int coordX = (int) Math.floor(this.positions.get(i)[0]);
      int coordY = (int) Math.floor(this.positions.get(i)[1]);
      String label = ""+i;

      this.drawCenteredCircle(g,coordX,coordY, TAILLE_PARA);
      g.drawString(label, coordX-TAILLE_PARA/2, coordY);
    }

    for(int i = 0; i < this.nbSommets; i++)
    {
      ArrayList<Arete> aretesSom = this.aretes.get(i+1);
      for(int j = 0; j < aretesSom.size(); j++)
      {
        Integer keySommet2 = aretesSom.get(j).getSommet2().getNumber();

        int coordX1 = (int) Math.round(this.positions.get(i+1)[0]*this.displayWidth);
        int coordY1 = (int) Math.round(this.positions.get(i+1)[1]*this.displayHeight);
        int coordX2 = (int) Math.round(this.positions.get(keySommet2)[0]*this.displayWidth);
        int coordY2 = (int) Math.round(this.positions.get(keySommet2)[1]*this.displayHeight);

        this.drawArrowLine(g, coordX1, coordY1, coordX2, coordY2, 15, 7);
      }
    }
  }

  private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) 
  {
    int dx = x2 - x1, dy = y2 - y1;
    double D = Math.sqrt(dx*dx + dy*dy);
    double xm = D - d, xn = xm, ym = h, yn = -h, x;
    double sin = dy / D, cos = dx / D;
    x = xm*cos - ym*sin + x1;
    ym = xm*sin + ym*cos + y1;
    xm = x;
    x = xn*cos - yn*sin + x1;
    yn = xn*sin + yn*cos + y1;
    xn = x;
    int[] xpoints = {x2, (int) xm, (int) xn};
    int[] ypoints = {y2, (int) ym, (int) yn};
    g.drawLine(x1, y1, x2, y2);
    g.fillPolygon(xpoints, ypoints, 3);
  }

  public void drawCenteredCircle(Graphics g, int coordX, int coordY, int rayon)
  {
    int diametre = rayon*2;
    //on centre X et Y en soustrayant le rayon souhaité
    g.drawOval(coordX-rayon, coordY-rayon, diametre, diametre);
  }

  public void drawCenteredString(Graphics g, String text, Rectangle rect) {
      FontMetrics metrics = g.getFontMetrics(g.getFont());
      int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
      int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
      g.drawString(text, x, y);
    }





}
