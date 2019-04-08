package Assignment2;

import java.io.*;
import java.net.*;
import java.util.*;

public class Node1 extends Thread
{
	public static Thread link_thread = null;
	public static Thread numbered_thread = null;
	public static final String node_id = "P0";
	public static final int pid = 0;
	public static int[] port_numbers = new int[]{ 5050, 5051, 5052, 5053 }; 
	private static final int num_nodes = port_numbers.length;
	public static String[] eventId = new String[]{"w", "x", "y", "z"};
	private static InetAddress server_address = InetAddress.getLoopbackAddress();
	public static ArrayList<Node_Event> buff_event = new ArrayList<>();
	public static int[] vector_time = new int[num_nodes];
	private static ServerSocket sock_conn;
	private static int counter_t = 0;
		
		public Node1() {
			System.out.println("Process: " + Node1.node_id);
			vector_time = new int[num_nodes];
			Arrays.fill(vector_time, 0);
		}
		
		public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
			Node1 Node1_thread = new Node1();
			Node1_thread.initialize();
			if(Node4.link_thread != null){
				Node4.link_thread.join();
			}
		}
		
		public void initialize() throws UnknownHostException, IOException, InterruptedException{
			link_thread = (new Thread(){
				@Override
				public void run(){
					serverConnect();
					return;
				}
			});
			link_thread.start();
			StringBuilder vectorTime_initial = display(Node1.vector_time);
			System.out.println("Initial Vector-Time: "+vectorTime_initial.toString());
			fncall_send();
		}

		public void serverConnect(){
			int port_connect = port_numbers[pid];
			try {
				sock_conn = new ServerSocket(port_connect, 0, server_address);
				while(true){
					Socket c_sock = sock_conn.accept();
					ObjectInputStream ip_stream = new ObjectInputStream(c_sock.getInputStream());
					final Node_Event new_event = (Node_Event)(ip_stream.readObject());
					c_sock.close();
					(new Thread(){
						@Override
						public void run(){
							buff_event.add(new_event);
							vectorTime_Order();
							return;
						}
					}).start();
				}
			} catch (IOException exp) {
				exp.printStackTrace();
			} catch (ClassNotFoundException exp) {
				exp.printStackTrace();
			}
		}

		public void vectorTime_Order() {
				int n = buff_event.size();
				boolean recvd = false;
				for(int i=0; i < n; i++){
					Node_Event event = buff_event.get(i);
					int event_pid = event.getpid();
					int[] event_timeVector = event.getvector_time();
					int k = event_timeVector.length;
					for(int j=0; j <= k ; j++){
						if(j == k){ break; }
						if(event_timeVector[event_pid] != (Node1.vector_time[event_pid] + 1)){ break; }
						if((j != event_pid) && event_timeVector[j] > Node1.vector_time[j]){ break; }
						if(j == (k - 1)){
							int[] past_vt = new int[Node1.vector_time.length];
							past_vt = Arrays.copyOf(Node1.vector_time, past_vt.length);
							Node1.vector_time[event_pid] = Math.max(Node1.vector_time[event_pid], event_timeVector[event_pid]);
							recvd = true;
							fncall_recv(past_vt); }
					}
					if(recvd){ buff_event.remove(i); }
				}
		}

		public void fncall_send() throws UnknownHostException, IOException, InterruptedException{
			 System.out.println("starting to send the message in 25 secs...");
			   Thread.sleep(25000);
			    Node1.vector_time[pid] = Node1.vector_time[pid] + 1;
				Node_Event n_event = new Node_Event();
				n_event.setpid(pid);
				n_event.setnode_id(node_id);
				n_event.setevent_id(eventId[pid]);
				n_event.setvector_time(Node1.vector_time);	
				
				for(int port : port_numbers){
					if(port == port_numbers[pid]) { continue;}
					//System.out.println("Port number trying to be connected : " + port);
					Socket socket = new Socket("127.0.0.1", port);
					//System.out.println("Connection established");
					ObjectOutputStream op_stream = new ObjectOutputStream(socket.getOutputStream());
					op_stream.writeObject(n_event);
					op_stream.close();
					socket.close();
				}
		}


		public void fncall_recv(int[] past_vectorTime){
			StringBuilder past_vtString = new StringBuilder("[");
			for(int i : past_vectorTime){
				past_vtString.append(String.valueOf(i));
				past_vtString.append(", ");
			}
			int punct = past_vtString.lastIndexOf(",");
			past_vtString.replace(punct, punct+1, " ]");

			StringBuilder vectorTimeString = new StringBuilder("[");
			for(int i : Node1.vector_time){
				vectorTimeString.append(String.valueOf(i));
				vectorTimeString.append(", ");
			}
			int punct1 = vectorTimeString.lastIndexOf(",");
			vectorTimeString.replace(punct, punct1+1, " ]");

			System.out.println("Events Timestamp: "+past_vtString +" ---> "+vectorTimeString.toString());
			counter_t += 1;
		}

		public StringBuilder display(int[] vectorTime) {
			StringBuilder vectorTimeStr = new StringBuilder("[");
			for(int i : vectorTime){
				vectorTimeStr.append(String.valueOf(i));
				vectorTimeStr.append(", ");
			}
			int punct = vectorTimeStr.lastIndexOf(",");
			vectorTimeStr.replace(punct, punct+1, " ]");
			return vectorTimeStr;
		}

}

