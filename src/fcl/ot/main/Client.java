package fcl.ot.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class Client {
	//private String state = "";
	//private char[] assumedUserTextInput;
	static int nID;
	int id;
	
	Server server = Server.getInstance();
	
	private List <Operation> inputKeyboardBuffer = new LinkedList <Operation> ();
	private List <Operation> buffer = new LinkedList <Operation> ();
	private List <Operation> operationHistory = new LinkedList<Operation>();
	
	private List <String> state = new ArrayList <String>();
	private List <String> stateNoSync = new ArrayList <String>();
	

	private boolean syncstatus = true;
	private Set <Operation> sync = new HashSet <Operation> ();
	
	//private Map <Integer, String> state = new TreeMap <Integer, String>();
	
	public Client () {
		id=++nID;
		server.connectToServer(this);
		
	}
	
	public Client(Operation[] operations) {
		id=++nID;
		server.connectToServer(this);
		for (Operation itr: operations) {
			inputKeyboardBuffer.add(itr);
		}
		
	}


	public void send() {
		List <Operation> serverBuffer = server.getBuffer();
		if (!inputKeyboardBuffer.isEmpty()) {
			
				Operation o = inputKeyboardBuffer.get(0);
				inputKeyboardBuffer.remove(0);
				o.setClient(this);
				
				o.applyOperation(state);
				System.out.print("-Client" + id + " send state: ");
				o.checkState(state);
				operationHistory.add(o);
				setDeSynchronizationStatus(o);
				
				serverBuffer.add(new Operation().clone(o));
			
		} else {
			System.out.println("[ERROR] Can't run client send");
		}
	}
	
	public void receive() {
		System.out.println("\nClient " + id + " receive buffer: " +  buffer);
		
		if (!buffer.isEmpty()) {
			Operation o = buffer.get(0);
			System.out.println("[TEMP]");
			if (!this.isSynchronized()) {
				System.out.println("[TEMP] before synccheck " + o + " == " + sync);
				if (o.isSync()) {
					System.out.println("[TEMP] Syncing");
					setSynchronizationStatus(o.getReferenceToOperation().getReferenceToOperation());
					//if (syncstatus || !syncstatus) {
						o.syncOperation(state); 
						//o.applyOperation(state);
						System.out.println(state);
						
					//}
					operationHistory.add(o);
					buffer.remove(0);
					
				} else {
					Operation deSyncOperation = new Operation().clone(o);
					deSyncOperation.applyOperation(state);
					operationHistory.add(deSyncOperation);
					/*if (deSyncOperation.syncOperation(state)) {
						operationHistory.add(deSyncOperation);
					}*/
					buffer.remove(0);
				}
				
				
			} else {
				
				operationHistory.add(o);
				
				o.applyOperation(state);
				buffer.remove(0);
				
			}
		
		}
	}
	
	void setSynchronizationStatus (Operation o) {
		
			if (sync.isEmpty()) {
				syncstatus = true;
			} else {
				
				sync.remove(o);
				if (sync.isEmpty()) {
					syncstatus = true;
				}
			}
		
		
	}
	
	void setDeSynchronizationStatus (Operation o) {
		//Operation synclone = new Operation().clone(o);
		
		sync.add(o);
		syncstatus = false;
	}
	
	boolean isSynchronized() {
		return syncstatus;
	}
	
	
	List<Operation> getBuffer() {
		return buffer;
	}
 	/*
	private void input(Character msg) {
		state += msg.toString();
	}
	
	void sendToBuffer(int pos, Character msg) {
		new Operation(this).insert(pos, msg.toString());
	}
	
	void update(Operation operation) {
		if (this == operation.getClient()) {
			return;
		}
		else {
			state += operation.getData();
		}
		//Server.getInstance().getUpdates();
		
		
	}
*/
	
	void printState() {
		/*
		for (Operation ops: operationHistory) {
			ops.applyOperation(state);
		}
		*/
		System.out.println(state);
	}
}
