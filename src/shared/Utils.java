package shared;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.util.Arrays;

public class Utils {
    public static void register(Agent a, ServiceDescription sd) {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(a.getAID());
        dfd.addServices(sd);

        try {
            DFService.register(a, dfd );
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }

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

    public static class BackgroundColor {
        public static final String RED = "red";
        public static final String BLACK = "black";
        public static final String GREEN = "green";
    }

    public static class LabelContent {
        public static final String CREATE = "Créez votre annonce";
        public static final String MODE = "Choisissez votre mode de fonctionnement (manuel ou automatique) et saisissez un prix";
        public static final String ONE_PROPOSITION = "Une seule proposition pour le moment";
        public static final String WAITING_PROPOSITION = "En attente de proposition";
        public static final String SUBSCRIBE_MANUAL = "Abonnements effectués. Vous pouvez maintenenant faire des propositions en sélectionnant une offre";
        public static final String SUBSCRIBE_AUTO = "Abonnements effectués. L'agent va maintenant faire des propositions automatiquement";
        public static final String PAYED = "Paiement reçue. Enchères terminées !";
        public static final String BUYER_PAYED = "Paiement envoyé avec succés. Enchères terminées !";
        public static final String PROPOSED = "Proposition faite, en attente de la décision du vendeur";
    }

    public enum ControlMode {
        MANUAL, AUTO
    }

    public static class IdMapException extends RuntimeException {
        public IdMapException(String message) {
            super(message);
        }
    }

    public static class StopWatch {
        private long cooldown;
        private long start_time;
        public StopWatch(long cooldown) {
            this.cooldown = cooldown * 1000;
        }
        public void start() {
            start_time = System.currentTimeMillis();
        }
        public boolean done() {
            return (System.currentTimeMillis() - start_time > cooldown);
        }
        public long delta() {
            return cooldown - (System.currentTimeMillis() - start_time);
        }
    }
}
