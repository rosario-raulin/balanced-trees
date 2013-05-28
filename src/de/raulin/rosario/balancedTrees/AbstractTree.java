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

import java.util.LinkedList;
import java.util.Queue;

public abstract class AbstractTree<K extends Comparable<K>, V> implements IMap<K, V> {

	protected class Node {
		K key;
		V value;
		Node left;
		Node right;
		
		protected Node() {}
		
		public Node(K key, V value, Node left, Node right) {
			this.key = key;
			this.value = value;
			this.left = left;
			this.right = right;
		}
		
		public Node(K key, V value) {
			this(key,value,null,null);
		}
	}
	
	protected int size;
	protected Node head;
	
	public AbstractTree() {
		this.size = 0;
		this.head = null;
	}
	
	protected Node getNode(K key) {
		Node prev = head;
		Node curr = head;
		
		while (curr != null) {
			prev = curr;
			int compare = key.compareTo(curr.key);
			if (compare == 0) {
				break;
			}
			else if (compare < 0) {
				curr = curr.left;
			} else {
				curr = curr.right;
			}
		}
		
		return prev;
	}
	
	@Override
	public V get(K key) {
		final Node n = getNode(key);
		return (n != null && n.key.compareTo(key) == 0) ? n.value : null;
	}

	@Override
	public int size() {
		return size;
	}
	
	@Override
	public String getLevelOrder() {
		final StringBuilder builder = new StringBuilder();
		final Queue<Node> q = new LinkedList<Node>();
		q.add(head);
		while (!q.isEmpty()) {
			final Node n = q.poll();
			builder.append(n.key);
			builder.append(" ");
			if (n.left != null) {
				q.add(n.left);
			}
			if (n.right != null) {
				q.add(n.right);
			}
		}
		return builder.toString();
	}
}
