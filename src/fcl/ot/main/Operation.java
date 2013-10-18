package fcl.ot.main;

import java.util.*;



public class Operation {
	
	private int startPosition;
	private int endPosition;
	private int toEnd;
	private String data;
	private byte operation;
	private Client client;
	private Operation syncReferenceToOperation;
	
	
	Operation () {
		
	}
	
	Operation insert(int position, String d) {
		operation = 1;
		startPosition = position;
		data = d;
		return this;
	}
	

	
	Operation delete(int p1, int p2) {
		operation = 2;
		startPosition = p1;
		endPosition = p2;
		return this;
	}
	
	Operation replace (int p1, int p2, String d) {
		operation = 3;
		data = d;
		startPosition = p1;
		endPosition = p2;
		return this;
	}
	
	Operation zeroOperation (Operation o) {
		syncReferenceToOperation = o;
		operation = 0;
		startPosition = 0;
		endPosition = 0;
		data = "";
		return this;
	}
	
	Operation clone (Operation o) {
		this.operation = o.operation;
		this.startPosition = o.startPosition;
		this.endPosition = o.endPosition;
		this.toEnd = o.toEnd;
		this.data = o.data;
		this.client = o.client;
		this.syncReferenceToOperation = o;
		return this;
	}
	
	public void checkState(List<String> state) {
		
			toEnd = state.size() - 1 - startPosition;
			System.out.println("startp " + startPosition + " statesize " + state.size() + " toend " + toEnd);
		 
	}
	
	public void applyOperation(List<String> state) {
		//System.out.println(this);
		switch (operation) {
		case 1: 
			if (startPosition >= state.size()) {
				int i=state.size();
				while (startPosition > i) {
					i ++;
					state.add(" ");
				}
				if ((state.size() - toEnd) != startPosition ) {
					int tp = startPosition;
					tp = state.size() - toEnd;
					state.add(tp, data);
				} else {
					state.add(startPosition, data);
				}
			} else {
				if (startPosition < state.size()) {
					if (state.get(startPosition).equals(" ")) {
						state.set(startPosition, data); 
					} else {
						
						if (toEnd > 0) {
							int tp = startPosition;
							tp = state.size() - toEnd;
							state.add(startPosition, data);
						} 
						if (toEnd == 0) {
							state.add(data);
						} 
						
					}
					
				}
			}
			/*if (startPosition >= state.size()) {
				int i=state.size();
				while (startPosition > i) {
					i ++;
					state.add(" ");
					
				}
				state.add(startPosition, data);
			}
			else {
				if (startPosition < state.size()) {
					if (state.get(startPosition).equals(" ")) {
						state.set(startPosition, data); 
					} else {
						state.add(startPosition, data); 
					}
				}
				
			}*/ 
			break;
		case 2: 
			if (startPosition < state.size()) {
				state.subList(startPosition, endPosition).clear();
			} 
			break;
		case 3: 
			if (startPosition >= state.size()) {
				state.add(data);
			} else {
				state.subList(startPosition, endPosition).clear();
				state.add(startPosition, data);
			}
			break;
		default: System.out.println("applyOperation not working!");
		}
		
	}
	
	public boolean syncOperation(List<String> state) {
		System.out.println("[TEMP] Sync of " + this);
		switch (operation) {
		case 0: 
			
			return true;
		case 1: 
			if (startPosition < state.size()) {
				if (state.get(startPosition).equals(" ")) {
					state.set(startPosition, data); 
				} else {
					state.add(data); 
				}
			} else {
				int i=state.size();
				while (startPosition > i) {
					i ++;
					state.add(" ");
					
				}
				state.add(startPosition, data);
			}
			return false;
		case 2: 
			
		case 3: 
			
		default: {
			System.out.println("syncOperation not working!");
			return false;
		}
		}
	}

	
	String getData() {
		return data;
	}
	
	public Client getClient() {
		return client;
	}
	
	public void setClient(Client c) {
		client = c;
	}
	
	Operation getReferenceToOperation () {
		return syncReferenceToOperation;
	}
	
	
	
	public boolean isSync () {
		if (operation == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public String toString () {
		switch (operation) {
		case 0: return "sync operation";
		case 1: return "insert " + data + "(" + toEnd + ")" + " to " + startPosition + " position"; 
		case 2: return "delete " + "data" + " from " + startPosition + "-" + endPosition + " positions";
		case 3: return "replace and set to " + data + " from " + startPosition + "-" + endPosition + " positions";
		default: return "no operations";
		}
	}

	

}
