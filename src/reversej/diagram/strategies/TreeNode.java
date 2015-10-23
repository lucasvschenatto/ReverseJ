package reversej.diagram.strategies;

import java.util.LinkedList;
import java.util.List;

import reversej.information.Information;

class TreeNode{

    private List<Information> nodeInformations;
    private TreeNode parent;
    private List<TreeNode> childrenMethods;
    
    public TreeNode() {
    	this.nodeInformations = new LinkedList<Information>();
        this.childrenMethods = new LinkedList<TreeNode>();
    }
    public TreeNode getRoot() {
		if(parent != null)
			return parent.getRoot();
		return this;
	}
	public TreeNode(Information info) {
    	this.nodeInformations = new LinkedList<Information>();
        this.nodeInformations.add(info);
        this.childrenMethods = new LinkedList<TreeNode>();
    }
    
    public TreeNode addChild(Information info) {
        TreeNode childNode = new TreeNode(info);
        childNode.parent = this;
        this.childrenMethods.add(childNode);
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
    	for (TreeNode child : childrenMethods) {
			List<Information> childInfo = child.getAllInfoInTree();
			allInfo.addAll(childInfo);
		}
    	return allInfo;
    }
}