/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.TreeModel;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.invite.NewListTempletCollection;
import com.kingdee.eas.fdc.invite.NewListTempletColumnCollection;
import com.kingdee.eas.fdc.invite.NewListTempletColumnInfo;
import com.kingdee.eas.fdc.invite.NewListTempletEntryCollection;
import com.kingdee.eas.fdc.invite.NewListTempletEntryInfo;
import com.kingdee.eas.fdc.invite.NewListTempletFactory;
import com.kingdee.eas.fdc.invite.NewListTempletInfo;
import com.kingdee.eas.fdc.invite.NewListTempletPageCollection;
import com.kingdee.eas.fdc.invite.NewListTempletPageInfo;
import com.kingdee.eas.fdc.invite.NewListTempletValueCollection;
import com.kingdee.eas.fdc.invite.NewListTempletValueInfo;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class TempletDistributeUI extends AbstractTempletDistributeUI {
	private CostCenterOrgUnitInfo currentOrg = SysContext.getSysContext()
			.getCurrentCostUnit();

	/**
	 * output class constructor
	 */
	public TempletDistributeUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		// CtrlUnitInfo cuInfo =
		// SysContext.getSysContext().getCurrentCtrlUnit();
		// if (cuInfo == null) {
		// throw new OUException(OUException.CU_CAN_NOT_NULL);
		// }
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY,
				"", null, null, null);
		this.treeOrg.setModel(orgTreeModel);
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) ((TreeModel) this.treeOrg
				.getModel()).getRoot();
		this.removeCurOrgNode(root);
		this.treeOrg.expandAllNodes(true, root);
		this.treeOrg.setShowCheckBox(true);
		root.setCheckBoxVisible(false);

	}

	public void removeCurOrgNode(DefaultKingdeeTreeNode node) {
		OrgStructureInfo org = (OrgStructureInfo) node.getUserObject();
		String orgId = org.getUnit().getId().toString();
		if (currentOrg.getId().toString().equals(orgId)) {
			if(node.isLeaf()){
			if (node.getParent() != null)
				this.treeOrg.removeNodeFromParent(node);
			}else{
				node.setCheckBoxVisible(false);
			}
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			this.removeCurOrgNode((DefaultKingdeeTreeNode) node.getChildAt(i));
		}
	}

	protected void traceNode(DefaultKingdeeTreeNode root,
			FullOrgUnitCollection orgColl) throws Exception {

		OrgStructureInfo oui = (OrgStructureInfo) root.getUserObject();
		FullOrgUnitInfo info = oui.getUnit();

		if (root.isChecked()) {
			orgColl.add(info);
		}

		for (int i = 0; i < root.getChildCount(); i++) {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) root
					.getChildAt(i);
			traceNode(node, orgColl);
		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected void btnNo_actionPerformed(ActionEvent e) throws Exception {
		this.destroyWindow();
	}

	protected void btnYes_actionPerformed(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) ((TreeModel) this.treeOrg
				.getModel()).getRoot();
		FullOrgUnitCollection selectOrgs = new FullOrgUnitCollection();
		this.traceNode(root, selectOrgs);
		if (selectOrgs.size() == 0) {
			MsgBox.showError("没有选择组织!");
			return;
		}
		String templetId = (String) this.getUIContext().get("templetId");
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("inviteType.*"));
		sic.add(new SelectorItemInfo("oriOrg.*"));
		sic.add(new SelectorItemInfo("pages.*"));
		sic.add(new SelectorItemInfo("pages.pageHead.*"));
		sic.add(new SelectorItemInfo("pages.headType.*"));
		sic.add(new SelectorItemInfo("pages.templetColumns.*"));
		sic.add(new SelectorItemInfo("pages.entrys.*"));
		sic.add(new SelectorItemInfo("pages.templetColumns.headColumn.*"));
		sic
				.add(new SelectorItemInfo(
						"pages.templetColumns.headColumn.parent.*"));
		sic.add(new SelectorItemInfo("pages.entrys.item.*"));
		sic.add(new SelectorItemInfo("pages.entrys.values.*"));
		NewListTempletInfo templetInfo = NewListTempletFactory
				.getRemoteInstance().getNewListTempletInfo(
						new ObjectUuidPK(BOSUuid.read(templetId)), sic);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		// view.setFilter(filter);
		// filter.getFilterItems().add(
		// new FilterItemInfo("head", templetInfo.getId().toString()));
		// view.getSorter().add(new SorterItemInfo("level"));
		// TempletColumnCollection templetColumns = TempletColumnFactory
		// .getRemoteInstance().getTempletColumnCollection(view);
		// templetInfo.getColumns().clear();
		// templetInfo.getColumns().addCollection(templetColumns);

//		view = new EntityViewInfo();
//		filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("id", templetId, CompareType.NOTEQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("name", templetInfo.getName()));
		NewListTempletCollection nameEquels = NewListTempletFactory
				.getRemoteInstance().getNewListTempletCollection(view);
		Map nameEquelMap = new HashMap();
		for (int i = 0; i < nameEquels.size(); i++) {
			nameEquelMap.put(nameEquels.get(i).getOrgUnit().getId().toString(),
					nameEquels.get(i).getOrgUnit());
		}
		filter.getFilterItems().clear();
		filter.getFilterItems().add(
				new FilterItemInfo("id", templetId, CompareType.NOTEQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("number", templetInfo.getNumber()));
		nameEquels = NewListTempletFactory.getRemoteInstance()
				.getNewListTempletCollection(view);
		Map numEquelMap = new HashMap();
		for (int i = 0; i < nameEquels.size(); i++) {
			numEquelMap.put(nameEquels.get(i).getOrgUnit().getId().toString(),
					nameEquels.get(i).getOrgUnit());
		}
		for (int i = 0; i < selectOrgs.size(); i++) {
			FullOrgUnitInfo org = selectOrgs.get(i);
			if (numEquelMap.containsKey(org.getId().toString())) {
				MsgBox.showError(org.getName() + " 存在相同编码模板!");
				return;
			}
			if (nameEquelMap.containsKey(org.getId().toString())) {
				MsgBox.showError(org.getName() + " 存在相同名称模板!");
				return;
			}
		}
		for (int i = 0; i < selectOrgs.size(); i++) {
			FullOrgUnitInfo org = selectOrgs.get(i);
			NewListTempletInfo templetClone = (NewListTempletInfo) templetInfo.clone();
			NewListTempletPageCollection pages = templetInfo.getPages();
			NewListTempletPageCollection pagesColne = (NewListTempletPageCollection) pages
					.clone();
			for (int j = 0, pageCount = pagesColne.size(); j < pageCount; j++) {
				NewListTempletPageInfo cpPageInfo = pagesColne.get(j);
				cpPageInfo.setId(BOSUuid.create(cpPageInfo.getBOSType()));

				NewListTempletPageInfo pageInfo = pages.get(j);
				NewListTempletColumnCollection cpCols = (NewListTempletColumnCollection) pageInfo
						.getTempletColumns().clone();
				for (int m = 0, columnCount = cpCols.size(); m < columnCount; m++) {
					NewListTempletColumnInfo cpColInfo = cpCols.get(m);
					cpColInfo.put("srcId", cpColInfo.getId()); // 设置valInfo中的col
					cpColInfo.setId(BOSUuid.create(cpColInfo.getBOSType()));
					cpColInfo.setPage(cpPageInfo);
				}
				cpPageInfo.getTempletColumns().clear();
				cpPageInfo.getTempletColumns().addCollection(cpCols);

				NewListTempletEntryCollection cpEntrys = (NewListTempletEntryCollection) pageInfo
						.getEntrys().clone();
				for (int k = 0, entrysCount = cpEntrys.size(); k < entrysCount; k++) {
					NewListTempletEntryInfo cpEntryInfo = cpEntrys.get(k);
					cpEntryInfo.setId(BOSUuid.create(cpEntryInfo.getBOSType()));
					cpEntryInfo.setHead(cpPageInfo);

					NewListTempletValueCollection cpValues = (NewListTempletValueCollection) cpEntryInfo
							.getValues().clone();
					for (int n = 0, valueCount = cpValues.size(); n < valueCount; n++) {
						NewListTempletValueInfo cpValInfo = cpValues.get(n);
						cpValInfo.setId(BOSUuid.create(cpValInfo.getBOSType()));

						// set the copied colInfo
						BOSUuid srcColId = cpValInfo.getColumn().getId();
						for (int cn = 0; cn < cpCols.size(); cn++) {
							NewListTempletColumnInfo cpColInfo = cpCols.get(cn);
							if (srcColId.equals(cpColInfo.get("srcId"))) {
								cpValInfo.setColumn(cpColInfo);
							}
						}
						cpValInfo.setEntry(cpEntryInfo);
					}
					cpEntryInfo.getValues().clear();
					cpEntryInfo.getValues().addCollection(cpValues);
				}
				cpPageInfo.getEntrys().clear();
				cpPageInfo.getEntrys().addCollection(cpEntrys);
			}
			templetClone.getPages().clear();
			templetClone.getPages().addCollection(pagesColne);

			templetClone.setId(BOSUuid.create(templetClone.getBOSType()));
			templetClone.setOrgUnit(org);
			templetClone.setOriOrg(templetInfo.getOrgUnit());
			NewListTempletFactory.getRemoteInstance().submit(templetClone);
		}
		this.destroyWindow();
	}

	public static void showViewUI(CoreUI ui, String templetId)
			throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("templetId", templetId);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(TempletDistributeUI.class.getName(), uiContext, null,
						"View");
		uiWindow.show();
	}
}