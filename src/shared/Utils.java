package shared;

import java.util.Arrays;

public class Utils {

    public static boolean isNumeric(String strNum) {
        if (strNum == null) return false;
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean areNumeric(String[] strNums) {
        return Arrays.stream(strNums).allMatch(Utils::isNumeric);
    }

    public class BackgroundColor {
        public static final String RED = "";
        public static final String BLACK = "";
        public static final String GREEN = "";
    }

    public class LabelContent {
        public static final String CREATE = "Créez votre annonce";
        public static final String MODE = "Choisissez votre mode de fonctionnement (manuel ou automatique)";
        public static final String ONE_PROPOSITION = "Une seule proposition pour le moment";
        public static final String WAITING_PROPOSITION = "En attente de proposition";
        public static final String SUBSCRIBE_MANUAL = "Abonnements effectués. Vous pouvez maintenenant faire des propositions en sélectionnant une offre";
        public static final String SUBSCRIBE_AUTO = "Abonnements effectués. L'agent va maintenant faire des propositions automatiquement";
        public static final String PAYED = "Paiement reçue. Enchères terminées !";
        public static final String PROPOSED = "Proposition faite";
    }

    public enum ControlMode {
        MANUAL, AUTO
    }
}
