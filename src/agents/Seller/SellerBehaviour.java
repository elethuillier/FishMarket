package agents.Seller;

import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.WakerBehaviour;

public class SellerBehaviour extends Agent {
	// private final Seller seller;
	// publishmessage

	// To_Announce
	protected void setup() {
		FSMBehaviour fsm = new FSMBehaviour(this) {
			public int onEnd() {
				System.out.println("FSM behaviour terminé");
				// seller.doDelete();
				return super.onEnd();
			}
		};

		// Definition des etats
		fsm.registerFirstState(new annonce(), "1");
		// fsm.registerState(new attenteOffre1(), "2");
		// fsm.registerState(new attenteOffre2(), "3");
		// fsm.registerState(new reponseOffre(), "4");
		// fsm.registerState(new attribuerPack(), "5");
		// fsm.registerState(new attentePaiement(), "6");

		// definition des transactions
		
		fsm.registerDefaultTransition("1", "1");
		fsm.registerTransition("1", "1", 1);
		fsm.registerTransition("1", "2", 2);
		fsm.registerTransition("2", "3", 3);
		fsm.registerTransition("2", "4", 4);
		fsm.registerTransition("3", "3", 3);
		fsm.registerTransition("3", "1", 1); // Attention, appeler repBid Behaviour avant d'aller à l'état 1
		fsm.registerTransition("4", "5", 0); // Un seul choix par defaut
		fsm.registerTransition("5", "6", 0); // Un seul choix par defaut

		addBehaviour(fsm);
	}

	private class annonce extends OneShotBehaviour{
		boolean recu = false;
		int valRetour = 1;
		@Override
		public void action() {
			System.out.println("execution de l'etat " + getBehaviourName());
			System.out.println("Boolean : " + recu);

			//Timer d'attente pour reception d'un message "5s"
			/* addBehaviour(new WWakerBehaviour(this, 5000) {
				@Override
				protected void handleElapsedTimeout()
			}); */
			
			if (recu) {
				System.out.println("return 2 pour passe à l'etat 2");
				//TODO 
			}else {
				System.out.println("return 1 pour passer à l'état 1");
				//TODO 
			}
		}
		
		public int onEnd() {
			return valRetour;
		}
	
    } // Fin Annonce
	
	private class attenteOffre extends OneShotBehaviour{
		boolean recu = false;
		int valRetour = 3;
		int cpt = 0;
		@Override
		public void action() {
			System.out.println("execution de l'etat " + getBehaviourName());
			System.out.println("Boolean : " + recu);

			//Timer d'attente pour reception d'un message "5s"
			try {				
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (recu && cpt == 1) { // Qu'un seul bid
				System.out.println("return 4 pour passe à l'etat 4");
				//TODO 
			}else if (recu) {
				// Plusieurs bid
				System.out.println("return 3 pour passer à l'état 3");
				//TODO 
			}else {
				
			}
		}
		
		public int onEnd() {
			return valRetour;
		}
	
    } // Fin Annonce

}

//Dans le OnEnd de chaque behaviour, je decide de la suite  if cooldown etc