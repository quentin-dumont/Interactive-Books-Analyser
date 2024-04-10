//Auteurs : Olivier Burnel et Quentin Dumont

package modele;

import java.util.regex.*;
import java.util.ArrayList;

public class Extractor {

    private String regSection;
    private String regEnfants;

    public Extractor(String regSection, String regEnfants)
    {
      this.regSection = regSection;
      this.regEnfants = regEnfants;
    }

    public String getRegSection(){return this.regSection;}
    public String getRegEnfants(){return this.regEnfants;}

    public int getNumber(String data)
    {
        Pattern p = Pattern.compile(this.regSection);
        Matcher m = p.matcher(data);
        if (m.find())
        {
            return Integer.parseInt(m.group(1));
        }
        return -1;
    }

    public ArrayList<Integer> getEnfants(String data)
    {
        ArrayList<Integer> enfants = new ArrayList<Integer>();
        Pattern p = Pattern.compile(this.regEnfants);
        Matcher m = p.matcher(data);

        while(m.find())
        {
            enfants.add(Integer.parseInt(m.group(1)));
        }
        return enfants;
    }
}
