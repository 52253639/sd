package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.invite.ListingItemFactory;
import com.kingdee.eas.fdc.invite.NewListingTempletDistributFacadeFactory;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.client.MsgBox;

public class TempletDistribute extends CompanyTreeSelectUI {
	
	private static final Logger logger = CoreUIObject
	.getLogger(TempletDistribute.class);
	
	public OrgUnitInfo currentOrg = SysContext.getSysContext()
	.getCurrentCostUnit();

	public TempletDistribute() throws Exception {
		super();
		// TODO 自动生成构造函数存根
		this.kDCheckBoxIsTreeModel.setVisible(false);
		this.kDCheckBoxHasChildren.setVisible(false);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8073491220373240382L;
	
	protected boolean traceNode(DefaultKingdeeTreeNode root,
			FullOrgUnitCollection orgColl) throws Exception {

		if(root.getUserObject() instanceof OrgStructureInfo){
			OrgStructureInfo oui = (OrgStructureInfo) root.getUserObject();
			FullOrgUnitInfo info = oui.getUnit();
			if(currentOrg.getLongNumber().startsWith(info.getLongNumber())||
					info.getLongNumber().startsWith(currentOrg.getLongNumber())){
				MsgBox.showInfo("所选择的组织中存在与当前组织存在上下级关系的组织！");
				return false;
			}
			orgColl.add(info);
		}
		

		for (int i = 0; i < root.getChildCount(); i++) {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) root
					.getChildAt(i);
			if(!traceNode(node, orgColl))
				return false;
		}
		return true;
	}
	private void doDistribute(KDTreeNode root) throws Exception{
		FullOrgUnitCollection selectOrgs = new FullOrgUnitCollection();
		if(!this.traceNode(root, selectOrgs)){
			return;
		}
		if (selectOrgs.size() == 0) {
			MsgBox.showError("没有选择组织!");
			return;
		}
		
		String templetId = (String) this.getUIContext().get("templetId");
		NewListingTempletDistributFacadeFactory.getRemoteInstance().Distribute(templetId,selectOrgs);
		this.destroyWindow();
	}
	
	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {

		TreeModel tree = (KingdeeTreeModel) treeSelected.getModel();
		KDTreeNode root = (KDTreeNode) tree.getRoot();
		doDistribute(root);
	}
	public static void showViewUI(CoreUI ui, String templetId)
			throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("templetId", templetId);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(TempletDistribute.class.getName(), uiContext, null,
						"View");
		uiWindow.show();
	}
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		this.destroyWindow();
	}

}
