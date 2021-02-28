package agents.Seller;

import java.io.IOException;
import java.util.Calendar;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import shared.Performatives;
import shared.messages.AnnounceMessage;
import shared.messages.BidMessage;

public class announce extends Behaviour {
	private final Seller seller;
	private AnnounceMessage announce;

	public announce(Seller seller) {
		this.seller = seller;
	}

	private static final long serialVersionUID = 1L;
	private int inProgress = 0;
	public static final int ANNONCE = 1;
	public static final int ATTENTE = 2;
	public static final int FIN_ANNONCE = 3;
	public long cooldown = -1;

	@Override
	public void action() {
		System.out.println("execution de l'etat " + getBehaviourName());

		switch (inProgress) {
		case ANNONCE:

			// Recuperer Action ID ici ??
			if (true) {// TODO Remplacer par "Il y a une enchere en cours ? "
				System.out.println(myAgent.getAID().getName() + " Mise en ligne de l'annonce");

				ACLMessage message = new ACLMessage(shared.Performatives.to_announce);
				AnnounceMessage annonce = new AnnounceMessage(seller.my_auctionsID.get(0), 50.0);
				try {
					message.setContentObject(annonce);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				message.clearAllReceiver();
				message.setSender(myAgent.getAID());
				myAgent.send(message);
				inProgress = ATTENTE;
				cooldown = System.currentTimeMillis();
			}
			break;

		case ATTENTE:
			ACLMessage message = myAgent.receive();
			if (message != null) {
				// TODO
				if (message.getPerformative() == Performatives.to_bid) {
					System.out.println(myAgent.getAID().getName() + " Offre recue");

					Object serial = null;
					try {
						serial = message.getContentObject();
					} catch (UnreadableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (serial != null) {
						if (serial instanceof BidMessage) {
							BidMessage bid = (BidMessage) serial;
							// Lire le prix de l'offre ?
						}

						inProgress = FIN_ANNONCE;
					}
				}
			}

			break;
		default:
			break;
		}

		// CoolDown dépassé, on renvoie une annonce dans l'enchère avec un prix plus bas
		// (prix actuel - pas défini par l'user)
		if (cooldown > 0 && (cooldown + seller.cooldown < Calendar.getInstance().getTimeInMillis())) {
			System.out.println(myAgent.getAID().getName() + " pas d'offre reçue, prix baisse");
			ACLMessage message = new ACLMessage(shared.Performatives.to_announce);
			AnnounceMessage annonce = new AnnounceMessage(seller.my_auctionsID.get(0), 40.0); // Get price actuel - Pas
																								// décrément
			try {
				message.setContentObject(annonce);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			message.clearAllReceiver();
			message.setSender(myAgent.getAID());
			myAgent.send(message);
		}
	}

	// Overrided functions
	@Override
	public boolean done() {
		return (inProgress == FIN_ANNONCE);
	}

	@Override
	public int onEnd() {
		cooldown = -1;
		inProgress = ANNONCE;
		return 2;
	}
}
