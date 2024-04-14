package redblack;


public class Node {
    int data;
    Node parent;
    Node left;
    Node right;
    int color; // 0 pentru negru, 1 pentru roșu
    public Node getLeft() {
        return left;
    }
    public Node getRight() {
        return right;
    }

    // Metoda pentru obținerea culorii nodului
    public int getColor() {
        return color;
    }

	public int getData() {
		// TODO Auto-generated method stub
		return data;
	}
	public Object getParent() {
		return parent;
	}
	public void setParent(Object parent2) {
		setParent(parent2);
		
	}
}