import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.uti.Date;

import java.lang.Thread;

enum MSG_TYPE {PREPARE, PROMISE, ACCEPT, ACCEPTED,LEARN,NACK_PROMISE,NACK_ACCEPTED};

public class Paxos 
{
	Server server;
	DatagramSocket clientSocket;
	int MAX_PROPNUM = Integer.MAX_VALUE;
	private int n; // number of request

	public Paxos(){
		this.server = server;
	}

	public void sendRequest(String sentence, String host, int port)
	{
		clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress();
		byte[] sendData = new byte[1024];
		System.out.println(sentence);
		sendData = sentence.getBytes();
		DatagramPacket sendPacket = new DatagramPacket();
		clientSocket.send(sendPacket);
	}

	public void runPaxos() {
		int n_proses = server.getnumProposers() + server.getnumAcceptors() + server.getnumLearners();
		for (int i=0; i<n_proses; i++){
			Thread t = new Thread(this, ""+i);
			t.start();
		}
	}	

	public int nextProposalNumber(int PID, int cur_Pnum){
		int i = 0;
		while (true) {
			if (i<=cur_Pnum){
				i++;
			} else {
				if (i%server.getnumProposers()==PID)
					return i;
				else
					i++;
			}
			if (i==MAX_PROPNUM)
				return -1;
		}
	}

	public void run() {
		try {
			Thread t = Thread.currentThread();
			int myindex = Integer.parseInt(t.getName());
			Channel c = server.getChannel(myindex);
			if(myindex<server.getnumProposers()){
				int cur_Pnum = 0;
				int CONSENSUS = 0;
				Message promise_queue[] = new Message[server.numAcceptors()];
				int Acceptor_decisions[] = new int[server.numAcceptors()];
				String guaranteed_value = null;
				int guaranteed_Pnum = -1;
				int guaranteed_flag = 0;
				int TIME_OUT = 5000;
				Date start_time = new Date();
				while (true) {
					if(c.isDistinguished()){
						while(c.receiveMessage()!=null){}

						cur_Pnum = nextProposalNumber(myindex,cur_Pnum);
					for(int a = server.getnumProposers(); a<(server.getnumProposers()+server.getnumAcceptors()))
							Message m_prepare = new Message(MSG_TYPE.PREPARE,myindex,a,cur_Pnum,-1,null);
							c.sendMessage(a,m_prepare.createMessage());						
					}
					while(true){
						if(!c.isDistinguished())
							break;
						String msg = c.receiveMessage();
						Date cur_time = new Date();
						long elapsed_time = cur_time.getTime() - start_time.getTime();
						if(elapsed_time>TIME_OUT){
							while(c.receiveMessage()!=null){}
							cur_Pnum = nextProposalNumber(myindex,cur_Pnum);
								for(int a=server.getnumProposers(); a<(server.getnumProposers() + server.getnumAcceptors());a++){
									Message m_prepare = new Message(MSG_TYPE.PREPARE,myindex,a,cur_Pnum,-1,null);
									c.sendMessage(a,m_prepare.createMessage());
								}
								start_time = new Date();
								guaranteed_value = null;
								guaranteed_Pnum=-1;  
                				guaranteed_flag=0;
                				TIME_OUT*=2;
						}
						if(msg!=null){
              			start_time=new Date(); 
              			Message m=new Message(MSG_TYPE.PREPARE,-1,-1,-1,-1,null); //create a dummy Message object
              			m.parseMessage(msg); 
              			//parse the received message to the Message object.
              			switch(m.msg_type){
                		case PROMISE: //if the proposer received promise, send a accept only if it contains Pnum=cur_Pnum.
                  		if(guaranteed_flag==1 && m.Pnum==guaranteed_Pnum){ //We already got a guarantee from majority so just accept what we already agreed
                    	Message m_accept=new Message(MSG_TYPE.ACCEPT,myindex,m.AID,m.Pnum,-1,guaranteed_value);   
                    	c.sendMessage(m.AID,m_accept.createMessage());
                  	}
                  	if(m.Pnum==cur_Pnum){ 
                  		f(guaranteed_flag==0){
                      Message msg_promQ=promise_queue[m.AID-network.numProposers()]; 

                      //Add messages to local queue or discard those messages
                      if(msg_promQ==null){
                        promise_queue[m.AID-network.numProposers()]=m; //new PROMISE recvd
                      }
                      else{
                        if(m.Value==null && msg_promQ.Value==null && msg_promQ.Pnum<m.Pnum){
                          promise_queue[m.AID-network.numProposers()]=m; //new latest PROMISE recvd to replace existing unknown value.
                        }
                        if(m.Value!=null && msg_promQ.Value!=null && msg_promQ.Pnum<m.Pnum){
                          promise_queue[m.AID-network.numProposers()]=m; //new latest PROMISE recvd to replace existing known value.
                        }
                        if(m.Value!=null && msg_promQ.Value==null){
                          promise_queue[m.AID-network.numProposers()]=m; //new latest PROMISE recvd with value to replace existing unknown value.
                        }
                      }
                    //end of adding messages to local queue
  
                    //loop through all message in local queue and check for majority
                      int valid_msg=0;
                      for(int a=network.numProposers();a<(network.numProposers()+network.numAcceptors());a++){
                        if(promise_queue[a-network.numProposers()]!=null){
                          Message m_promise_queue=promise_queue[a-network.numProposers()];//create a dummy Message object
                          valid_msg++;
                          if(m_promise_queue.Value!=null) {
                            if(guaranteed_Pnum<m_promise_queue.Value_Pnum){
                              guaranteed_value=m_promise_queue.Value;
                              guaranteed_Pnum=m_promise_queue.Value_Pnum;
                            }
                          }
                        } 

                    	// end main phase
                  		}

                  		// setelah mendapatkan majority (voting) lalu accept
                  		if(valid_msg>(network.numAcceptors()/2)){ 
                        if(guaranteed_value==null){
                          guaranteed_value=""+c.getInitialValue();
                          guaranteed_Pnum=cur_Pnum;
                        }  
                        if(c.isDistinguished()){                    
                          for(int a=network.numProposers();a<(network.numProposers()+network.numAcceptors());a++){
                            if(promise_queue[a-network.numProposers()]!=null){
                              Message m_accept=new Message(MSG_TYPE.ACCEPT,myindex,a,m.Pnum,-1,guaranteed_value);   
                              c.sendMessage(a,m_accept.createMessage());
                            }
                          } // end for
                      }
                      guaranteed_flag=1; // flag set
					}
				}
			}
			break;
			case NACK_PROMISE:
				if(c.isDistinguished() && guaranteed_Pnum <= m.Pnum){
					cur_Pnum = nextProposalNumber(myindex,cur_Pnum>m.Pnum?cur_Pnum:m.Pnum);
					// Acceptor should send the highest promised Pnum so far. so that Proposer can generate a even higher Pnum.
				if(cur_Pnum==-1)
                      c.sleep();
                    while(c.receiveMessage()!=null){} //Clear all previous messages and start fresh prepare
                    for(int a=network.numProposers();a<(network.numProposers()+network.numAcceptors());a++){
                      Message m_newprepare=new Message(MSG_TYPE.PREPARE,myindex,a,cur_Pnum,-1,null);
                      c.sendMessage(a,m_newprepare.createMessage());
                      promise_queue[a-network.numProposers()]=null;
                    }  
                  }
                  break;

            case ACCEPTED: //value has been accepted. Keep quiet.
                  Acceptor_decisions[m.AID-network.numProposers()]=1;
                  int decision_made=0;
                  for(int i=0;i<network.numAcceptors();i++){
                    if(Acceptor_decisions[m.AID-network.numProposers()]==1)
                      decision_made++;
                  }
                  if(decision_made>network.numAcceptors()/2) 
                    CONSENSUS=1;
                  break;


                case NACK_ACCEPTED: 
                  Acceptor_decisions[m.AID-network.numProposers()]=-1;
                  int rerun=0;
                  for(int i=0;i<network.numAcceptors();i++){
                    if(Acceptor_decisions[m.AID-network.numProposers()]==-1)
                      rerun++;
                  }
                  if(rerun>network.numAcceptors()/2){ //now start a new run reset all state variables
                    CONSENSUS=0; //1->after reaching 
                    promise_queue=new Message[network.numAcceptors()]; 
                    Acceptor_decisions=new int[network.numAcceptors()]; //1->Accept , -1->NACK_Accept
                    guaranteed_value=null;
                    guaranteed_Pnum=-1;  
                    guaranteed_flag=0;
                    cur_Pnum=nextProposalNumber(myindex,cur_Pnum);
                    if(cur_Pnum==-1)
                      System.out.println("Reached MAX_PROPNUM, bail out by throwing exception");
                    c.sleep();
                    while(c.receiveMessage()!=null){} //Clear all previous messages and start fresh prepare

                    for(int a=network.numProposers();a<(network.numProposers()+network.numAcceptors());a++){
                      Message m_prepare=new Message(MSG_TYPE.PREPARE,myindex,a,cur_Pnum,-1,null);
                      c.sendMessage(a,m_prepare.createMessage());
                    }
                  }
                  break;
                }
              }  
              else{ 
                c.sleep(); //Message queue empty so sleeping.
              }  
            }
          }
          else{
            c.sleep();
          }
        }
      }
      // kode untuk acceptor
       if(myindex>=network.numProposers() && myindex<(network.numProposers()+network.numAcceptors())) {
      /* Initialize the highest proposal number received so far
       * and the value if any proposal has been accepted */
        
        int max_pnum = -1;                // highest pnum for which acceptor has send a PROMISE
        String val = null;                      // value it has accepted
        int val_pnum = -1;                // corresponding pnum for the accepted value above
        boolean hasAccepted = false;
        //Acceptor code goes here
        while(true) {
            
          Message m1 = new Message(MSG_TYPE.PROMISE, -1, -1, -1, -1, null);
          String msg_st = c.receiveMessage();
          Message m2;
          if(msg_st != null) {
            m1.parseMessage(msg_st);
            // switch case to handle all msg_type
            switch (m1.msg_type) {
            case PREPARE:
            /*  This is when the message received is PREPARE.
            *  If the proposal number is greater than highest proposal number this acceptor has PROMISEd,
            *  it will send a PROMISE message.
            *
            *  It will also handle the case when the acceptor has accepted a value but the proposal number
            *  is greater than it has PROMISEd, by sending a PROMISE with the val and corresponding val_pnum
            *  set to one it has accepted. */
              if(m1.Pnum >= max_pnum) {        // change max proposal number promised only when it is greater than current
                max_pnum = m1.Pnum;
                m2 = new Message(MSG_TYPE.PROMISE, m1.PID, m1.AID, max_pnum, val_pnum, val);
                c.sendMessage(m1.PID, m2.createMessage());
              }
              else {
                m2 = new Message(MSG_TYPE.NACK_PROMISE, m1.PID, m1.AID, max_pnum, -1, null);    // sending a NACK_PROMISE when proposal's pnum < max_pnum
                c.sendMessage(m1.PID, m2.createMessage());
              }
              break;
              
            case ACCEPT:
              if(m1.Pnum == max_pnum) {
                val = m1.Value;
                val_pnum = m1.Pnum;
                hasAccepted = true;
                /* Send the accepted value to learner only when it is accepted for the first time
                * hasAccepted boolean takes care of this */
                Message m3 = new Message(MSG_TYPE.LEARN, m1.PID, m1.AID, max_pnum, val_pnum, val);
                /* send message to all learners */
                int totalprocess=network.numProposers()+network.numAcceptors()+network.numLearners();
              
                for(int i=network.numProposers()+network.numAcceptors(); i<totalprocess; i++) {
                  c.sendMessage(i, m3.createMessage());
                }

                m2 = new Message(MSG_TYPE.ACCEPTED, m1.PID, m1.AID, max_pnum, val_pnum, val);
              }
              else {
                /* Send a NACK_ACCEPTED message with current max_pnum when received Pnum in ACCEPT message is less than max_pnum
                * which was promised by the acceptor. */
                m2 = new Message(MSG_TYPE.NACK_ACCEPTED, m1.PID, m1.AID, max_pnum, -1,null);
              }
              c.sendMessage(m1.PID, m2.createMessage());
              break;
            
            default:
              break;
            }
          }
        }
      }
      if(myindex>=(network.numProposers()+network.numAcceptors())){
        //Learner code goes here
        HashMap<String, Integer> learner_map = new HashMap<String, Integer>(); //format : <<AID.PropNum,Value>>
        HashMap<Integer, Integer> DECIDED = new HashMap<Integer, Integer>(); //DECIDED for each Prop_Num null->not decided and 1->decided
        while(true){
          String Msg_Learn=c.receiveMessage();
          if(Msg_Learn!=null){
            Message msg_learn=new Message(MSG_TYPE.PREPARE,-1,-1,-1,-1,null); //create a dummy Message object
            msg_learn.parseMessage(Msg_Learn);
            //putting decided value in hashmap
            learner_map.put(msg_learn.AID+"."+msg_learn.Pnum,Integer.parseInt(msg_learn.Value));            
            HashMap<Integer, Integer> learn_val = new HashMap<Integer, Integer>();
            for(int i=network.numProposers();i<network.numProposers()+network.numAcceptors()&&DECIDED.get(msg_learn.Pnum)==null;i++){
              if(learner_map.get(i+"."+msg_learn.Pnum)!=null){
                int cur_learn_val=learner_map.get(i+"."+msg_learn.Pnum);  
                if(learn_val.get(cur_learn_val)==null){
                  learn_val.put(cur_learn_val,1);
                }  
                else{ 
                  learn_val.put(cur_learn_val,learn_val.get(cur_learn_val)+1);
                  if(learn_val.get(cur_learn_val)>network.numAcceptors()/2){
                    c.decide(cur_learn_val);
//                    System.out.println("Value Learnt for Prop_Num:"+msg_learn.Pnum+":"+ cur_learn_val + " by learner " + myindex);
                    DECIDED.put(msg_learn.Pnum,1); 
                  }
                }
              }
            } // end for
          } // end if
          c.sleep();
        }
      }
    }
    catch(Exception e){e.printStackTrace();}
		}
	}

/*
	public boolean IsUnique(){
		// check the uniqueness of ID
	}

	public void SortRole(){
		// assign and sort role
	}

	public void Propose(){
		// prepare
	}

	public void Agree(){

	}

	public void Commit(){

	}

	public void Accept(){

	}

	public void Reject(){

	}

	public boolean Fail(){

	} */

}