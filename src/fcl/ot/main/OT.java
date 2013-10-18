package fcl.ot.main;

public class OT {
	
	

	public static void main(String[] args) {
		Operation o1 = new Operation().insert(0, "a", 0);
		Operation o2 = new Operation().insert(0, "b", 1);
		Operation o3 = new Operation().insert(0, "x", 0);
		Operation o4 = new Operation().insert(0, "y", 1);
		
		Operation d1 = new Operation().delete(0, 1);
		//Operation d2 = new Operation().delete(0, 2);
		
		Operation r1 = new Operation().replace(0, 2, "7");
		
		Operation[] text1 = {o1, o2};
		Operation[] text2 = {o3, o4};
		Operation[] text3 = {d1, r1};
		
		Server server = Server.getInstance();
		
		Client client1, client2, client3;
		client1 = new Client(text1);
		client2 = new Client(text2);
		client3 = new Client(text3);
		//-------------------------------
		//scene 1 2 3 4
		scene3(client1, client2, client3, server);
		
		//--------------------------------
		//print(client1, client2, client3, server);
		
	}
	
	public static void scene1 (Client client1, Client client2, Client client3, Server server) {
		client1.send();
		client2.send();
		server.receive();
		server.receive();
		server.printState();
		client1.receive();
		client1.receive();
		client2.receive();
		client2.receive();
		client3.receive();
		client3.receive();
		print(client1, client2, client3, server);
	}
	
	public static void scene2 (Client client1, Client client2, Client client3, Server server) {
		client1.send();
		client1.send();
		
		client2.send();
		client2.send();
		
		server.receive();
		server.receive();
		
		server.receive();
		server.receive();
		server.receive();
		
		client1.receive();
		client1.receive();
		client1.receive();
		client1.receive();
		
		client2.receive();
		client2.receive();
		client2.receive();
		client2.receive();
		
		server.receive();
		
		client3.receive();
		client3.receive();
		client3.receive();
		client3.receive();
		print(client1, client2, client3, server);
	}
	
	public static void scene3 (Client client1, Client client2, Client client3, Server server) {
		client1.send();
		client1.printState();
		client2.send();
		client2.printState();
		client1.send();
		client1.printState();
		client2.send();
		client2.printState();
		
		server.receive();
		server.receive();
		server.receive();
		server.receive();
		
		server.printState();
		
		client1.receive();
		client1.receive();
		client1.receive();
		client1.receive();
		
		client2.receive();
		client2.receive();
		client2.receive();
		client2.receive();
		
		client3.receive();
		client3.receive();
		client3.receive();
		client3.receive();
		
		/*
			Servers: [ , a, z, b, y]
			Client1: [ , a, z, b, y]
			Client2: [ , a, z, y, b]
			Client3: [ , a, z, b, y]
			
Servers: [x, y, b, a]
Client1: [b, y, a, x]
Client2: [x, y, a, b]
Client3: [x, y, b, a]*/
		print(client1, client2, client3, server);
	}
	
	public static void scene4 (Client client1, Client client2, Client client3, Server server) {
		client1.send();
		client2.send();		
		
		server.receive();
		server.receive();	
		
		
		client2.send();
		client1.send();
		
		server.receive();
		server.receive();
		
		client1.receive();
		client1.receive();
		client1.receive();
		client1.receive();
		
		client2.receive();
		client2.receive();
		client2.receive();
		client2.receive();
		
		client3.receive();
		client3.receive();
		client3.receive();
		client3.receive();
		print(client1, client2, client3, server);
	}
	
	
	public static void print (Client c1, Client c2, Client c3, Server s) {
		System.out.println("\nResult:");
		System.out.print("Servers: ");
		s.printState();
		System.out.print("Client1: ");
		c1.printState();
		System.out.print("Client2: ");
		c2.printState();
		System.out.print("Client3: ");
		c3.printState();
	}

}
