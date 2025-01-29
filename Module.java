import java.awt.Font;
import java.awt.Component;

/**
 * Décrivez votre classe Module ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class Module {
    public static String camelCase(final String pStr) {
        String vStr = "";
        String vUpper = pStr.toUpperCase();
        String[] vUpperArray = vUpper.split(" ");
        for(String vE : vUpperArray) {
            vStr += vE.charAt(0);
            vStr += vE.substring(1).toLowerCase() + " ";
        }
        return vStr;
    }
    
    
    public static void setFontSize(final Component pComp, final int pSize) {
        Font vFont = pComp.getFont();
        Font vNewFont = new Font(vFont.getFontName(), vFont.getStyle(), pSize);
        pComp.setFont(vNewFont);
    }
    
    public static double round(final double pNb) {
        return Math.round(pNb * 1000)/1000.0;
    }
}
