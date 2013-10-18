package fcl.ot.main;


import java.util.*;


public class Server {
	public static Server instance;
	private List <Operation> operationHistory = new LinkedList<Operation>();
	private List <Operation> buffer = new LinkedList <Operation> ();
	private Set <Client> clients = new HashSet<Client>();
	
	private List <String> state = new ArrayList <String>();
	
	//private Map <Integer, String> state = new TreeMap <Integer, String>();
	//private State state = new State();
	//private String state = "";
	//private StringBuilder stateBuffer = new StringBuilder(state);
	
	
	
	private Server() {}
	
	public static Server getInstance() {
		if (instance==null) {
			instance = new Server();
		}
		
		return instance;
	}
	
	public void receive() {
		System.out.println ("Server receive buffer: " + buffer);
		if (!buffer.isEmpty()) {
			Operation o = buffer.get(0);
			buffer.remove(0);
			operationHistory.add(o);
			o.applyServerOperation(state);
			printState();
			System.out.println("Server OperationHistory: " + operationHistory);
			o.checkState(state);
			send(o);
			
			
		}
	}
	
	private void send(Operation o) {
		
		for (Client c: clients) { 
			if ( c == o.getClient()) { 
				Operation tempOperation = new Operation().zeroOperation(o);
				List <Operation> bufferclient = c.getBuffer();
				bufferclient.add(tempOperation);
			} else {
				Operation tempOperation = o; //new Operation().clone(o);
				List <Operation> list = c.getBuffer();
				list.add(tempOperation);
			}
			
		}
		
	}
	
	
	
	/*
	void receive1(Operation operation) {
		
		stateBuffer.append(operation.getData());
		System.out.println(operation.lastOperation());
		operationHistory.add(operation);
		update(operation);
	}
	
	void update(Operation o) {
		sendUpdatesToClients(o);
		state += stateBuffer.toString();
		stateBuffer.delete(0, stateBuffer.capacity());
		printState();
	}
	
	private void sendUpdatesToClients(Operation operation) {
		Iterator<Client> client = clientHistory.iterator();
        while(client.hasNext()) {
        	client.next().update(operation);
		}
	}

	State getState() {
		return state;
	}


*/		
	void printState() {
		System.out.println(state);
	}
	
	public void connectToServer(Client client) {
		clients.add(client);
	}
	
	List<Operation> getBuffer() {
		return buffer;
	}

}
