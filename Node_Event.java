package Assignment2;

import java.io.Serializable;

public class Node_Event implements Serializable {

	private static final long serialVersionUID = 1L;
	private int pid;
	private String node_id;
	private String event_id;
	private int[] vector_time;

	
	public int getpid() {
		return pid;
	}

	public void setpid(int pid) {
		this.pid = pid;
	}
	
	public String getnode_id() {
		return node_id;
	}
	
	public void setnode_id(String node_id) {
		this.node_id = node_id;
	}
	
	public String getevent_id() {
		return event_id;
	}
	public void setevent_id(String event_id) {
		this.event_id = event_id;
	}
	
	public int[] getvector_time() {
		return vector_time;
	}

	public void setvector_time(int[] vector_time) {
		this.vector_time = vector_time;
	}

}
