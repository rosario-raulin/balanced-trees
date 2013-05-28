

package de.raulin.rosario.balancedTrees;

public class AVLTree<K extends Comparable<K>, V> extends AbstractTree<K, V> {

	class AVLNode extends Node {
		int size;
		
		public AVLNode(K key, V value, AVLNode left, AVLNode right) {
			this.key = key;
			this.value = value;
			this.left = left;
			this.right = right;
			this.size = 1;
		}
		
		public AVLNode(K key, V value) {
			this(key, value, null, null);
		}
		
		@SuppressWarnings("unchecked")
		public AVLNode getLeft() {
			return (AVLNode) left;
		}
		
		@SuppressWarnings("unchecked")
		public AVLNode getRight() {
			return (AVLNode) right;
		}
		
		public int getDifference() {
			int rightHeight = (right != null) ? getRight().size : 0;
			int leftHeight = (left != null) ? getLeft().size : 0;
			return rightHeight - leftHeight;
		}
		
		public int getMaxHeight() {
			int rightHeight = (right != null) ? getRight().size : 0;
			int leftHeight = (left != null) ? getLeft().size : 0;
			return Math.max(leftHeight, rightHeight);
		}
		
		public boolean leftLeaning() {
			return getDifference() == -2;
		}
		
		public boolean rightLeaning() {
			return getDifference() == 2;
		}
	}
	
	private int size(AVLNode node) {
		if (node == null) return 0;
		else return node.size;
	}
	
	private AVLNode rotateLeft(AVLNode node) {
		//System.out.println("rotate left: " + node.key);
		AVLNode x = node.getRight();
		node.right = x.left;
		x.left = node;
		x.size = node.size;
		node.size = Math.max(size(node.getLeft()), size(node.getRight())) + 1;
		return x;
	}
	
	private AVLNode rotateRight(AVLNode node) {
		//System.out.println("rotate right: " + node.key);
		AVLNode x = node.getLeft();
		node.left = x.right;
		x.right = node;
		x.size = node.size;
		node.size = Math.max(size(node.getLeft()), size(node.getRight())) + 1;
		return x;
	}
	
	private AVLNode insert(K key, V value, AVLNode node) {
		if (node == null) {
			return new AVLNode(key,value);
		} else {
			int compare = key.compareTo(node.key);
			if (compare == 0) {
				--size;
				node.value = value;
				return node;
			}
			if (compare < 0) {
				node.left = insert(key, value, node.getLeft());
			} else {
				node.right = insert(key, value, node.getRight());
			}
			
			if (node.leftLeaning()) {
				AVLNode left = node.getLeft();
				if (left != null && left.getDifference() > 0) {
					node.left = rotateLeft(left);
				}
				node = rotateRight(node);
			}
			if (node.rightLeaning()) {
				AVLNode right = node.getRight();
				if (right != null && right.getDifference() < 0) {
					node.right = rotateRight(right);
				}
				node = rotateLeft(node);
			}
			
			node.size = node.getMaxHeight() + 1;
			
			return node;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void insert(K key, V value) {
		++size;
		head = insert(key, value, (AVLNode)head);
	}

	@Override
	public void remove(K key) {
		throw new UnsupportedOperationException();
	}

	public static void main(String[] args) {
//		//final Random randGen = new Random();
//		//final Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
//		final IMap<Integer, Integer> map = new RedBlackTree<Integer, Integer>();
//		
//		long start = System.nanoTime();
//		for (int i = 0; i < 10000000; ++i) {
//			//map.insert(randGen.nextInt(10000000), i);
//			//map.put(randGen.nextInt(10000000), i);
//			map.insert(i, i+1);
//		}
//		double used = (double)(System.nanoTime() - start) / 1000000000.0;
//		System.out.println("insert(): " + used);
//		
//		start = System.nanoTime();
//		for (int i = 0; i < map.size(); ++i) {
//			map.get(i);
//		}
//		used = (double)(System.nanoTime() - start)/1000000000.0;
//		System.out.println("get(): " + used);
//		
//		// System.out.println(map.getLevelOrder());
		
		final Integer[] toBeInserted = {2,5,3,9,7,1,4,6};
		IMap<Integer,Integer> map = new RedBlackTree<Integer, Integer>();
		
		for (final Integer i : toBeInserted) {
			map.insert(i, i+1);
		}
		
		System.out.println(map.size());
		System.out.println(map.getLevelOrder());
		
	}
}
