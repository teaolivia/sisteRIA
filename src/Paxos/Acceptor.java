import java.util.HashMap;

public class Acceptor {
	ProposalID promisedProposalID = null;
	Integer lastAcceptedValue = null;

	public ProposalID getpromisedProposalID(){
		return promisedProposalID;
	}

	public Integer getLastAcceptedValue(){
		return lastAcceptedValue;
	}

	public HashMap<ProposalID, Integer> receivePromise(ProposalID proposalID){
		if (promisedProposalID == null) {
			
		}
	}

	public Boolean receiveAccept(ProposalID proposalID, Integer acceptValue){

	}

}