package bili;

public class BTree {
    public static class Node {
        private int n;
        private int[] keys;
        private Node[] children;
        private boolean leaf;

        Node(int t) {
            setKeys(new int[2 * t - 1]);
            setChildren(new Node[2 * t]);
            setLeaf(true);
            setN(0);
        }

        public int findKey(int key) {
            int index = 0;
            while (index < getN() && getKeys()[index] < key) {
                index++;
            }
            return index;
        }

		public boolean isLeaf() {
			return leaf;
		}

		public void setLeaf(boolean leaf) {
			this.leaf = leaf;
		}

		public Node[] getChildren() {
			return children;
		}

		public void setChildren(Node[] children) {
			this.children = children;
		}

		public int getN() {
			return n;
		}

		public void setN(int n) {
			this.n = n;
		}

		public int[] getKeys() {
			return keys;
		}

		public void setKeys(int[] keys) {
			this.keys = keys;
		}

        // Alte metode pentru Node
    }

    private Node root; // rădăcina arborelui
    private final int t; // factorul de ramificare

    public BTree(int t) {
        this.t = t;
        Node x = allocateNode();
        setRoot(x);
    }

    private Node allocateNode() {
        return new Node(t);
    }
    public void insert(int key) {
        Node r = getRoot();
        if (r.getN() == 2 * t - 1) {
            Node s = allocateNode();
            setRoot(s);
            s.setLeaf(false);
            s.setN(0);
            s.getChildren()[0] = r;
            splitChild(s, 0, r);
            insertNonfull(s, key);
        } else {
            insertNonfull(r, key);
        }
    }

    private void insertNonfull(Node x, int key) {
        int i = x.getN() - 1;
        if (x.isLeaf()) {
            while (i >= 0 && key < x.getKeys()[i]) {
                x.getKeys()[i + 1] = x.getKeys()[i];
                i--;
            }
            x.getKeys()[i + 1] = key;
            x.setN(x.getN() + 1);
        } else {
            while (i >= 0 && key < x.getKeys()[i]) {
                i--;
            }
            i++;
            if (x.getChildren()[i].getN() == 2 * t - 1) {
                splitChild(x, i, x.getChildren()[i]);
                if (key > x.getKeys()[i]) {
                    i++;
                }
            }
            insertNonfull(x.getChildren()[i], key);
        }
    }

    private void splitChild(Node x, int i, Node y) {
        // Creează un nou nod care va deveni noul copil al lui x
        Node z = allocateNode();
        z.setLeaf(y.isLeaf());
        z.setN(t - 1);

        // Mută cheile superioare ale lui y în z
        for (int j = 0; j < t - 1; j++) {
            z.getKeys()[j] = y.getKeys()[j + t];
        }

        // Dacă y nu este frunza, mută și copiii lui y în z
        if (!y.isLeaf()) {
            for (int j = 0; j < t; j++) {
                z.getChildren()[j] = y.getChildren()[j + t];
            }
        }

        // Redimensionează y, acum va conține doar primele t-1 chei
        y.setN(t - 1);

        // Facem loc pentru noul copil (z) în x
        for (int j = x.getN(); j > i; j--) {
            x.getChildren()[j + 1] = x.getChildren()[j];
        }

        // Conectăm noul copil (z) la x
        x.getChildren()[i + 1] = z;

        // Mută cheia mediană a lui y în x
        x.getKeys()[i] = y.getKeys()[t - 1];

        // Incrementăm numărul de chei în x
        x.setN(x.getN() + 1);
    }

	public void delete(int key) {
        if (getRoot() == null) {
            System.out.println("The tree is empty");
            return;
        }

        delete(getRoot(), key);

        if (getRoot().getN() == 0) {
            @SuppressWarnings("unused")
			Node tmp = getRoot();
            if (getRoot().isLeaf()) {
                setRoot(null);
            } else {
                setRoot(getRoot().getChildren()[0]);
            }

            // Dezechipeaza nodul radacina vechi
            tmp = null;
        }
    }

    private void delete(Node x, int key) {
        int i = x.findKey(key);

        if (i < x.getN() && x.getKeys()[i] == key) {
            if (x.isLeaf()) {
                removeFromLeaf(x, i);
            } else {
                removeFromNonLeaf(x, i);
            }
        } else {
            if (x.isLeaf()) {
                System.out.println("The key " + key + " does not exist in the tree");
                return;
            }

            boolean last = (i == x.getN());

            if (x.getChildren()[i].getN() < t) {
                fill(x, i);
            }

            if (last && i > x.getN()) {
                delete(x.getChildren()[i - 1], key);
            } else {
                delete(x.getChildren()[i], key);
            }
        }
    }

    private void removeFromLeaf(Node x, int i) {
        for (int j = i + 1; j < x.getN(); j++) {
            x.getKeys()[j - 1] = x.getKeys()[j];
        }

        x.setN(x.getN() - 1);
    }

    private void removeFromNonLeaf(Node x, int i) {
        int k = x.getKeys()[i];

        // Cazul în care copilul care precedă k are cel puțin t chei
        if (x.getChildren()[i].getN() >= t) {
            int predecessor = predecessor(x.getChildren()[i], k);
            x.getKeys()[i] = predecessor;
            delete(x.getChildren()[i], predecessor);
        }
        // Cazul în care copilul care urmează k are cel puțin t chei
        else if (x.getChildren()[i + 1].getN() >= t) {
            int successor = successor(x.getChildren()[i + 1], k);
            x.getKeys()[i] = successor;
            delete(x.getChildren()[i + 1], successor);
        }
        // Dacă ambii copii au doar t-1 chei, îmbină-i
        else {
            merge(x, i);
            delete(x.getChildren()[i], k);
        }
    }

    private void fill(Node x, int i) {
        if (i != 0 && x.getChildren()[i - 1].getN() >= t) {
            borrowFromLeft(x, i);
        } else if (i != x.getN() && x.getChildren()[i + 1].getN() >= t) {
            borrowFromRight(x, i);
        } else {
            if (i != x.getN()) {
                merge(x, i);
            } else {
                merge(x, i - 1);
            }
        }
    }

    private void borrowFromLeft(Node x, int i) {
        Node child = x.getChildren()[i];
        Node sibling = x.getChildren()[i - 1];

        // Mută o cheie de la copilul i-1 către x.keys[i]
        for (int j = child.getN() - 1; j >= 0; j--) {
            child.getKeys()[j + 1] = child.getKeys()[j];
        }
        child.getKeys()[0] = x.getKeys()[i - 1];

        // Mută ultimul copil al fratelui la primul copil al copilului
        if (!child.isLeaf()) {
            for (int j = child.getN(); j >= 0; j--) {
                child.getChildren()[j + 1] = child.getChildren()[j];
            }
            child.getChildren()[0] = sibling.getChildren()[sibling.getN()];
        }

        // Copiază ultima cheie a fratelui în x.keys[i-1]
        x.getKeys()[i - 1] = sibling.getKeys()[sibling.getN() - 1];

        child.setN(child.getN() + 1);
        sibling.setN(sibling.getN() - 1);
    }

    private void borrowFromRight(Node x, int i) {
        Node child = x.getChildren()[i];
        Node sibling = x.getChildren()[i + 1];

        // Copiază prima cheie a fratelui în x.keys[i]
        child.getKeys()[child.getN()] = x.getKeys()[i];

        // Mută primul copil al fratelui la ultimul copil al copilului
        if (!child.isLeaf()) {
            child.getChildren()[child.getN() + 1] = sibling.getChildren()[0];
        }

        // Copiază prima cheie a fratelui în x.keys[i]
        x.getKeys()[i] = sibling.getKeys()[0];

        // Mută celelalte chei și copii în fratelui
        for (int j = 1; j < sibling.getN(); j++) {
            sibling.getKeys()[j - 1] = sibling.getKeys()[j];
        }
        if (!sibling.isLeaf()) {
            for (int j = 1; j <= sibling.getN(); j++) {
                sibling.getChildren()[j - 1] = sibling.getChildren()[j];
            }
        }

        child.setN(child.getN() + 1);
        sibling.setN(sibling.getN() - 1);
    }

    private void merge(Node x, int i) {
        Node child = x.getChildren()[i];
        Node sibling = x.getChildren()[i + 1];

        // Mută cheia i-ului în mijlocul copilului
        child.getKeys()[t - 1] = x.getKeys()[i];

        // Copiază cheile din sibling la sfârșitul copilului
        for (int j = 0; j < sibling.getN(); j++) {
            child.getKeys()[j + t] = sibling.getKeys()[j];
        }

        // Copiază copiii din sibling la sfârșitul copilului, dacă nu este frunza
        if (!child.isLeaf()) {
            for (int j = 0; j <= sibling.getN(); j++) {
                child.getChildren()[j + t] = sibling.getChildren()[j];
            }
        }

        // Mută cheile din x după eliminarea cheii i
        for (int j = i + 1; j < x.getN(); j++) {
            x.getKeys()[j - 1] = x.getKeys()[j];
        }

        // Mută copiii din x după eliminarea cheii i
        for (int j = i + 2; j <= x.getN(); j++) {
            x.getChildren()[j - 1] = x.getChildren()[j];
        }

        child.setN(child.getN() + sibling.getN() + 1);
        x.setN(x.getN() - 1);

        // Dezalocă sibling
        sibling = null;
    }
    private int predecessor(Node x, int k) {
        int i;
        for (i = 0; i < x.getN() && k > x.getKeys()[i]; i++) {
            // Găsește prima cheie mai mare decât k
        }

        if (x.isLeaf()) {
            if (i > 0) {
                // Dacă există chei mai mici decât k, returnează cea mai mare dintre ele
                return x.getKeys()[i - 1];
            } else {
                // Nu există chei mai mici decât k în acest nod
                // În acest caz, ar trebui să căutați în părinții acestui nod
                // sau să returnați o valoare care indică că nu există predecesor
                // în acest nod.
                return -1; // Presupunem că -1 indică că nu există predecesor
            }
        } else {
            // Dacă nodul nu este o frunză, mergem în cel mai din dreapta copil
            return predecessor(x.getChildren()[i], k);
        }
    }

    private int successor(Node x, int k) {
        int i;
        for (i = 0; i < x.getN() && k >= x.getKeys()[i]; i++) {
            // Găsește prima cheie mai mare sau egală cu k
        }

        if (x.isLeaf()) {
            if (i < x.getN()) {
                // Dacă există chei mai mari decât k, returnează cea mai mică dintre ele
                return x.getKeys()[i];
            } else {
                // Nu există chei mai mari decât k în acest nod
                // În acest caz, ar trebui să căutați în părinții acestui nod
                // sau să returnați o valoare care indică că nu există succesor
                // în acest nod.
                return -1; // Presupunem că -1 indică că nu există succesor
            }
        } else {
            // Dacă nodul nu este o frunză, mergem în cel mai din stânga copil
            return successor(x.getChildren()[i], k);
        }
    }
    public void indentedDisplay() {
        System.out.println("The B-tree is");
        display(getRoot(), 0);
    }

    private void display(Node x, int indent) {
        if (x == null) {
            return;
        }
        boolean b = x.isLeaf();
        for (int i = 0; i < x.getN(); i++) {
            if (!b) {
                display(x.getChildren()[i], indent + 2);
            }
            for (int j = 0; j < indent; j++) {
                System.out.print(' ');
            }
            System.out.println(x.getKeys()[i]);
        }
        if (!b) {
            display(x.getChildren()[x.getN()], indent + 2);
        }
    }

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

}