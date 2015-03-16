package com.kingdee.eas.fdc.invite.client;

import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.eas.framework.client.tree.KDTreeNode;

public class TreeUtil {

	public static KDTreeNode getSelectedTreeNode(KDTree tree) {
		Object object = tree.getLastSelectedPathComponent();
		if (object != null)
			return (KDTreeNode) object;
		else
			return null;
	}

	public static void searchTree(DefaultKingdeeTreeNode node,
			ISearchTreeEvent callBack) {
		if (callBack.doTask(null, node))
			for (int i = 0; i < node.getChildCount(); i++) {
				if (node.getChildAt(i) instanceof DefaultKingdeeTreeNode) {
					DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) node
							.getChildAt(i);
					searchTree(child, callBack);
				}
			}
	}

	public interface ISearchTreeEvent {
		public boolean doTask(Object params, DefaultKingdeeTreeNode node);
	}

}