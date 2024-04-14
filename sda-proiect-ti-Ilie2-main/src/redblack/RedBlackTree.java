package redblack;

public class RedBlackTree {
    private Node root;
    private Node TNULL;

    public RedBlackTree() {
        TNULL = new Node();
        TNULL.color = 0;
        TNULL.left = null;
        TNULL.right = null;
        root = TNULL;
    }
    
    // Metoda pentru inserarea unui nod în arbore
    public void insert(int key) {
        Node node = new Node();
        node.parent = null;
        node.data = key;
        node.left = TNULL;
        node.right = TNULL;
        node.color = 1; // Nodul nou este inițial roșu

        Node y = null;
        Node x = this.root;

        while (x != TNULL) {
            y = x;
            if (node.data < x.data) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.data < y.data) {
            y.left = node;
        } else {
            y.right = node;
        }

        if (node.parent == null) {
            node.color = 0;
        } else if (node.parent.parent != null) {
            fixInsert(node);
        }
    }

    // Metoda pentru ștergerea unui nod din arbore
    public void delete(int key) {
        Node z = search(key);
        if (z != null) {
            deleteNode(z);
        }
    }

    private void deleteNode(Node z) {
        Node y = z;
        int yOriginalColor = y.color;
        Node x;

        if (z.left == TNULL) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == TNULL) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = findMinNode(z.right);
            yOriginalColor = y.color;
            x = y.right;

            if (y.parent == z) {
                x.parent = y;
            } else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }

            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }

        if (yOriginalColor == 0) {
            fixDelete(x);
        }
    }

    // Metoda pentru căutarea unui nod cu o anumită valoare în arbore
    public Node search(int key) {
        return searchHelper(this.root, key);
    }

    private Node searchHelper(Node root, int key) {
        if (root == TNULL || key == root.data) {
            return root;
        }

        if (key < root.data) {
            return searchHelper(root.left, key);
        } else {
            return searchHelper(root.right, key);
        }
    }

    // Metoda pentru găsirea nodului cu valoarea minimă
    public Node findMin() {
        return findMinNode(this.root);
    }

    private Node findMinNode(Node node) {
        while (node.left != TNULL) {
            node = node.left;
        }
        return node;
    }

    // Metoda pentru găsirea nodului cu valoarea maximă
    public Node findMax() {
        return findMaxNode(this.root);
    }

    private Node findMaxNode(Node node) {
        while (node.right != TNULL) {
            node = node.right;
        }
        return node;
    }

    // Alte metode auxiliare pentru rotații și operații specifice arborelui Red-Black
    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;

        if (y.left != TNULL) {
            y.left.parent = x;
        }

        y.parent = x.parent;

        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node y) {
        Node x = y.left;
        y.left = x.right;

        if (x.right != TNULL) {
            x.right.parent = y;
        }

        x.parent = y.parent;

        if (y.parent == null) {
            root = x;
        } else if (y == y.parent.right) {
            y.parent.right = x;
        } else {
            y.parent.left = x;
        }

        x.right = y;
        y.parent = x;
    }

    private void fixInsert(Node k) {
        while (k.parent.color == 1) {
            if (k.parent == k.parent.parent.right) {
                Node u = k.parent.parent.left; // unchiul
                if (u.color == 1) {
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        rightRotate(k);
                    }
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    leftRotate(k.parent.parent);
                }
            } else {
                Node u = k.parent.parent.right; // unchiul
                if (u.color == 1) {
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        leftRotate(k);
                    }
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    rightRotate(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = 0;
    }

    private void fixDelete(Node x) {
        while (x != root && x.color == 0) {
            if (x == x.parent.left) {
                Node w = x.parent.right;
                if (w.color == 1) {
                    w.color = 0;
                    x.parent.color = 1;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                if (w.left.color == 0 && w.right.color == 0) {
                    w.color = 1;
                    x = x.parent;
                } else {
                    if (w.right.color == 0) {
                        w.left.color = 0;
                        w.color = 1;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    w.color = x.parent.color;
                    x.parent.color = 0;
                    w.right.color = 0;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                Node w = x.parent.left;
                if (w.color == 1) {
                    w.color = 0;
                    x.parent.color = 1;
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                if (w.right.color == 0 && w.right.color == 0) {
                    w.color = 1;
                    x = x.parent;
                } else {
                    if (w.left.color == 0) {
                        w.right.color = 0;
                        w.color = 1;
                        leftRotate(w);
                        w = x.parent.left;
                    }
                    w.color = x.parent.color;
                    x.parent.color = 0;
                    w.left.color = 0;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.color = 0;
    }
    private void transplant(Node u, Node v) {
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

	public Node getRoot() {
		return root;
	}
	}
    
    // Alte metode pentru rotații, schimbare de culori, etc. vor fi adăugate aici
