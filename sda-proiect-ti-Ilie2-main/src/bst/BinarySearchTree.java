package bst;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class BinarySearchTree {
	private TreeNode root;

    public BinarySearchTree() {
        this.root = null;
    }

    public void insert(int val) {
        root = insertRec(root, val);
    }

    private TreeNode insertRec(TreeNode root, int val) {
        if (root == null) {
            root = new TreeNode(val);
            return root;
        }

        if (val < root.val) {
            root.left = insertRec(root.left, val);
        } else if (val > root.val) {
            root.right = insertRec(root.right, val);
        }

        return root;
    }

    public boolean search(int val) {
        return searchRec(root, val);
    }

    private boolean searchRec(TreeNode root, int val) {
        if (root == null || root.val == val) {
            return root != null;
        }

        if (val < root.val) {
            return searchRec(root.left, val);
        } else {
            return searchRec(root.right, val);
        }
    }

    public void delete(int val) {
        root = deleteRec(root, val);
    }

    private TreeNode deleteRec(TreeNode root, int val) {
        if (root == null) {
            return null;
        }

        if (val < root.val) {
            root.left = deleteRec(root.left, val);
        } else if (val > root.val) {
            root.right = deleteRec(root.right, val);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            root.val = minValue(root.right);
            root.right = deleteRec(root.right, root.val);
        }

        return root;
    }

    private int minValue(TreeNode root) {
        int minValue = root.val;
        while (root.left != null) {
            minValue = root.left.val;
            root = root.left;
        }
        return minValue;
    }

    public void inOrderTraversal() {
        inOrderTraversal(root);
    }

    private void inOrderTraversal(TreeNode root) {
        if (root != null) {
            inOrderTraversal(root.left);
            System.out.print(root.val + " ");
            inOrderTraversal(root.right);
        }
    }

    public DefaultTreeModel getTreeModel() {
        if (root != null) {
            DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(root.val);
            populateTreeModel(root, rootNode);
            return new DefaultTreeModel(rootNode);
        }
        return null;
    }
    private void populateTreeModel(TreeNode node, DefaultMutableTreeNode parentNode) {
        if (node != null) {
            DefaultMutableTreeNode leftNode = new DefaultMutableTreeNode(node.left != null ? node.left.val : null);
            DefaultMutableTreeNode rightNode = new DefaultMutableTreeNode(node.right != null ? node.right.val : null);

            parentNode.add(leftNode);
            parentNode.add(rightNode);

            populateTreeModel(node.left, leftNode);
            populateTreeModel(node.right, rightNode);
        }
    }
}
