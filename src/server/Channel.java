import java.util.LinkedList;

public class Chanel {
	Network network;
	int index;

	// Send a message
	public void sendMessage(int destination, String message) {
		synchronized(network,queues[destination]) {
			network.queues[destination].push(message);
		}
	}

	// Receive a message
	public String receiveMessage() {
		synchronized(network.queues[index]) {
			if (!network.queues[index].isEmpty())
				return network.queues[index].pop();
			else
				return null;
		}
	}

	// distinguish?
	public void decide(int decision) {
		if (index<(network.getnumProposers() + network.getnumAcceptors()))
			throw new Error("Tidak dapat menerima value!");

		else if (decision>=network.getnumProposers())
			throw new Error("Bukan initial value coy.");

		synchronized(network) {
			if (network.decision==-1)
				network.decision=decision;
			else {
				if (network.decision!=decision)
					System.out.println("Terdapat disagreement");
			}
		}
	}

	// initial value untuk proposer
	public int getInitialValue() {
		if (index >= network.numProposers())
			throw new Error("Gak usah tanya non-proposer mah");
		return index;
	}
}