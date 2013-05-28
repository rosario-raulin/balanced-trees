// Copyright 2013 Rosario Raulin

// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at

//     http://www.apache.org/licenses/LICENSE-2.0

// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package de.raulin.rosario.balancedTrees;

public class RedBlackTree<K extends Comparable<K>, V> extends
		AbstractTree<K, V> {

	private final static boolean RED = true;
	private final static boolean BLACK = false;
	
	class RBNode extends Node {
		private boolean color;
		private int size;
		
		public RBNode(K key, V value, boolean color) {
			super(key,value);
			this.color = color;
			this.size = 1;
		}
		
		@SuppressWarnings("unchecked")
		public RBNode getLeft() {
			return (RBNode) left;
		}
		
		@SuppressWarnings("unchecked")
		public RBNode getRight() {
			return (RBNode) right;
		}
	}
	
	private int size(RBNode n) {
		if (n == null) return 0;
		else return n.size;
	}
	
	private boolean isRed(RBNode node) {
		if (node == null) return false;
		else return node.color == RED;
	}
	
	private void flipColors(RBNode node) {
		node.color = RED;
		node.getLeft().color = BLACK;
		node.getRight().color = BLACK;
	}
	
	private RBNode rotateLeft(RBNode node) {
		//System.out.println("rotate left: " + node.key);
		RBNode x = node.getRight();
		node.right = x.left;
		x.left = node;
		x.color = node.color;
		node.color = RED;
		x.size = node.size;
		node.size = Math.max(size(node.getLeft()), size(node.getRight())) + 1;
		return x;
	}
	
	private RBNode rotateRight(RBNode node) {
		//System.out.println("rotate right: " + node.key);
		RBNode x = node.getLeft();
		node.left = x.right;
		x.right = node;
		x.color = node.color;
		node.color = RED;
		x.size = node.size;
		node.size = Math.max(size(node.getLeft()), size(node.getRight())) + 1;
		return x;
	}
	
	private RBNode insert(K key, V value, RBNode node) {
		if (node == null) {
			return new RBNode(key, value, RED);
		}
		int compare = key.compareTo(node.key);
		if (compare < 0) {
			node.left = insert(key, value, node.getLeft());
		} else if (compare > 0) {
			node.right = insert(key,value, node.getRight());
		} else {
			--size;
			node.value = value;
		}
		
		if (isRed(node.getRight()) && !isRed(node.getLeft())) node = rotateLeft(node);
		if (isRed(node.getLeft()) && isRed(node.getLeft().getLeft())) node = rotateRight(node);
		if (isRed(node.getLeft()) && isRed(node.getRight())) flipColors(node);
		
		node.size = size(node.getLeft()) + size(node.getRight()) + 1; 
		
		return node;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void insert(K key, V value) {
		++size;
		RBNode h = (RBNode) head;
		h = insert(key, value, h);
		h.color = BLACK;
		head = h;
	}

	@Override
	public void remove(K key) {
		throw new UnsupportedOperationException();
	}

}
