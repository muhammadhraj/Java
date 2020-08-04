import java.util.*;

public class Schedule {
	Job start;
	ArrayList<Job> jobs=new ArrayList<Job>();
	ArrayList<Edge> edges=new ArrayList<Edge>();
	
	public Schedule() {
		start=new Job(0, null);
	}
	
	public Job insert(int time) {
		Job newJob=new Job(time, start);
		jobs.add(newJob);
		edges.add(new Edge(start, newJob));
		return newJob;
	}
	
	public Job get(int index) {
		return jobs.get(index);
	}
	
	public int finish() {
		ArrayList<Job> topOrder=topSort();
		if(topOrder.size()!=jobs.size()+1) {
			return -1;
		}
		
		for(Job job:topOrder) {
			job.finishTime=0;
			job.pi=null;
		}
		start.finishTime=0;
		
		for(int i=0;i<topOrder.size();i++) {
			for(Edge edge:edges) {
				if(edge.source.equals(topOrder.get(i))) {
					if(edge.source.finishTime+edge.destination.duration>edge.destination.finishTime) {
						edge.destination.finishTime=edge.source.finishTime+edge.destination.duration;
						edge.destination.startTime=edge.source.finishTime;
						edge.destination.pi=edge.source;
					}
				}
			}
		}
		int finishTime=0;
		for(Job job:topOrder) {
			if(job.finishTime>finishTime) {
				finishTime=job.finishTime;
			}
		}
		return finishTime;
	}
	
	public ArrayList<Job> topSort() {
		jobs.add(start);
		ArrayList<Job> sorted=new ArrayList<Job>();
		
		Queue<Job> q=new LinkedList<Job>();
		
		for(Job job:jobs) {
			job.inDegree=0;
			for(Edge edge:edges) {
				if(edge.destination==job) {
					job.inDegree++;
				}
			}
			if(job.inDegree==0) {
				q.add(job);
			}
		}
		
		while(!q.isEmpty()) {
			Job sortedJob=q.remove();
			sorted.add(sortedJob);
			for(Edge edge:edges) {
				if(edge.source.equals(sortedJob)) {
					edge.destination.inDegree--;
					if(edge.destination.inDegree==0) {
						q.add(edge.destination);
					}
				}
			}
		}
		jobs.remove(start);
		
		return sorted;
	}

	class Job{
		private int duration;
		private int startTime;
		private int finishTime;
		private Job parent;
		private ArrayList<Job> prereqs=new ArrayList<Job>();
		private int inDegree;
		private Job pi;
		//private ArrayList<Job> incomingJobs=new ArrayList<Job>();

		private Job(int duration, Job parent){
			this.duration=duration;
			this.parent=parent;
			this.startTime=0;
			this.finishTime=startTime+duration;
		}
		
		public void requires(Job j) {
			this.prereqs.add(j);
			this.parent=j;
//			for(Job prereq:prereqs) {
//				if(prereq.finishTime>this.startTime) {
//					this.startTime=prereq.finishTime;
//					this.finishTime=startTime+duration;
//				}
//			}
			ArrayList<Edge> removed=new ArrayList<Edge>();
			//this.startTime=j.finishTime;
			for(Edge edge:edges) {
				if((edge.destination.equals(this) && edge.source.equals(start))) {
					removed.add(edge);
				}
			}
			edges.removeAll(removed);
			edges.add(new Edge(j, this));
		}
		
		public int start() {
			if(topSort().size()!=jobs.size()+1 && hasPrereqs()) {
				return -1;
			}
			else {
//				for(Job prereq:prereqs) {
//					if(prereq.finishTime>startTime) {
//						startTime=prereq.finishTime;
//					}
//				}
				return startTime;
			}
		}
		
		public boolean hasPrereqs() {
			return !prereqs.isEmpty();
		}
	}
	
	class Edge{
		Job source;
		Job destination;
		
		public Edge(Job source, Job destination) {
			this.source=source;
			this.destination=destination;
		}
	}
}
