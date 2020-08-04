import java.util.ArrayList;
import java.util.Collections;

public class Tree {
	
	private Node root;
	
	public Tree() {
		root=null;
	}
	
	public boolean insert(int x) {
		if(root==null) {
			root=new Node(x);
			return true;
		}
		else {
			if(root.search(x)!=null) {
				return false;
			}
			else {
				root.insertKey(x);
				if(root.isFull()) {
					root=root.split();
				}
				return true;
			}
		}	
	}
	
	public int size(int x) {
		Node xNode=root.search(x);
		if(xNode==null) {
			return 0;
		}
		else
			return xNode.size;
	}
	
	class Node {
		private ArrayList<Integer> keys=new ArrayList<Integer>(3);
		private ArrayList<Node> children=new ArrayList<Node>(4);
		private int size;
		
		public Node(int x) {
			this.keys.add(x);
			this.size++;
		}
		
		public boolean isLeaf() {
			return this.children.isEmpty();
		}
		
		public boolean isFull() {
			return this.keys.size()>2;
		}
		
		public Node search(int x) {
			if(this.keys.contains(x)) {
				return this;
			}
			else if(!this.isLeaf()){
				int index=0;
				while(index<this.keys.size() && this.keys.get(index)<x) {
					index++;
				}
				return this.children.get(index).search(x);
			}
			else {
				return null;
			}
		}
		
		public void insertKey(int x) {
			int keyIndex=0;
			while(keyIndex<this.keys.size() && this.keys.get(keyIndex)<x) {
				keyIndex++;	
			}
			if(!this.isLeaf()) {
				this.children.get(keyIndex).insertKey(x);
				if(this.children.get(keyIndex).isFull()) {
					Node splitRoot=this.children.get(keyIndex).split();
					this.children.remove(keyIndex);
					
					int childIndex=0;
					while(childIndex<this.keys.size() && this.keys.get(childIndex)<splitRoot.keys.get(0)) {
						childIndex++;
					}
					
					this.keys.add(childIndex, splitRoot.keys.get(0));
					this.children.addAll(childIndex, splitRoot.children);
				}
				this.size++;
			}
			else {
				this.keys.add(keyIndex, x);
				this.size++;
			}
			Collections.sort(this.keys);
		}
		
		public Node split() {
			Node subRoot=new Node(this.keys.get(1));
			Node subLeft=new Node(this.keys.get(0));
			Node subRight=new Node(this.keys.get(2));
			
			subRoot.children.add(subLeft);
			subRoot.children.add(subRight);
			subRoot.size+=subLeft.size+subRight.size;
			
			if(!this.isLeaf()) {
				subLeft.children.add(this.children.get(0));
				subLeft.children.add(this.children.get(1));
				subRight.children.add(this.children.get(2));
				subRight.children.add(this.children.get(3));
				subRoot.size+=subLeft.children.get(0).size+subLeft.children.get(1).size+subRight.children.get(0).size+subRight.children.get(1).size;
				subLeft.size+=subLeft.children.get(0).size+subLeft.children.get(1).size;
				subRight.size+=subRight.children.get(0).size+subRight.children.get(1).size;
			}
			
			return subRoot;
		}
	}
}