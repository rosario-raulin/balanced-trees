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

public class BinaryTree<K extends Comparable<K>, V> extends AbstractTree<K, V> {

	@Override
	public void insert(K key, V value) {
		++size;
		final Node pos = getNode(key);
		
		if (pos == null) {
			head = new Node(key,value);
		}
		else if (pos.key == key) {
			--size;
			pos.value = value;
		} else {
			if (key.compareTo(pos.key) < 0) {
				pos.left = new Node(key, value);
			} else {
				pos.right = new Node(key,value);
			}
		}
	}
	
	private void changeChild(Node parent, Node curr, Node to) {
		if (curr == head) {
			head = to;
		}
		else if (parent.left == curr) {
			parent.left = to;
		} else {
			assert(parent.right == curr);
			parent.right = to;
		}
	}

	@Override
	public void remove(K key) {
		Node parent = head;
		Node curr = head;
			
		while (curr != null) {
			int compare = key.compareTo(curr.key);
			
			if (compare == 0) {
				break;
			}
			
			parent = curr;
			if (compare < 0) {
				curr = curr.left;
			} else {
				curr = curr.right;
			}
		}
		
		if (curr == null) return;
		--size;

		Node left = curr.left;
		Node right = curr.right;

		if (left == null && right == null) {
			changeChild(parent, curr, null);
			if (curr == head) head = null;
		} else if (left != null && right != null) {
			Node minRParent = right;
			Node minR = right;
			
			while (minR.left != null) {
				minRParent = minR;
				minR = minR.left;
			}
			
			if (minRParent != minR) {
				minRParent.left = minR.right;
				minR.right = right;
			}
			minR.left = left;
			changeChild(parent,curr,minR);
		} else {
			if (left == null) {
				assert(right != null);
				changeChild(parent, curr, right);
			} else {
				changeChild(parent, curr, left);
			}
		}
	}

	public static void main(String[] args) {
		final IMap<Integer,Integer> map = new BinaryTree<Integer,Integer>();
		
		for (int i = 0; i < 100000; ++i) {
			map.insert(i, i+1);
		}
		
		System.out.println(map.get(99999));
	}
}
