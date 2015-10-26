package reversej.diagram.strategies;

import java.util.LinkedList;
import java.util.List;

import reversej.diagram.Information;

class TreeNode{

    private List<Information> nodeInformations;
    private TreeNode parent;
    private List<TreeNode> children;
    
    public TreeNode() {
    	this.nodeInformations = new LinkedList<Information>();
        this.children = new LinkedList<TreeNode>();
    }
    public TreeNode getRoot() {
		if(parent != null)
			return parent.getRoot();
		return this;
	}
	public TreeNode(Information info) {
    	this.nodeInformations = new LinkedList<Information>();
        this.nodeInformations.add(info);
        this.children = new LinkedList<TreeNode>();
    }
    
    public TreeNode(List<Information> nodeInformations) {
    	this.nodeInformations = new LinkedList<Information>();
        this.nodeInformations.addAll(nodeInformations);
        this.children = new LinkedList<TreeNode>();
	}
	public TreeNode addChild(Information info) {
        TreeNode childNode = new TreeNode(info);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }
    
    public void addInfo(Information info){
    	nodeInformations.add(info);
    }
    public TreeNode getParent(){
    	return parent;
    }
    public List<Information> getAllInfoInTree(){
    	List<Information> allInfo = nodeInformations;
    	for (TreeNode child : children) {
			List<Information> childInfo = child.getAllInfoInTree();
			allInfo.addAll(childInfo);
		}
    	return allInfo;
    }
    public List<Information> getNodeInfo(){
    	return nodeInformations;
    }
    public List<TreeNode> getChildren(){
    	return children;
    }
    public void printAll(){
    	for (Information information : nodeInformations) {
    		System.out.println(information.describe());
		}
    	for (TreeNode treeNode : children) {
			treeNode.printAll();
		}    	
    }
	public List<TreeNode> removeChildren() {
		List<TreeNode> toReturn = children;
		children = new LinkedList<TreeNode>();
		return toReturn;
	}
	public void addChildren(List<TreeNode> newChildren) {
		children.addAll(newChildren);
	}
	public void addChild(List<Information> nodeInformations) {
		children.add(new TreeNode(nodeInformations));		
	}
}