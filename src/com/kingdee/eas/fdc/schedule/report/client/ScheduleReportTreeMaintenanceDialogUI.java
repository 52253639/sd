/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.report.client;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.ExpandVetoException;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.schedule.ScheduleChartHelper;
import com.kingdee.eas.util.SysUtil;

/**
 * 描述:报表组织视图维护弹出窗
 * @author adward_huang  date:2012-10-24
 * @version EAS6.1
 */
public class ScheduleReportTreeMaintenanceDialogUI extends AbstractScheduleReportTreeMaintenanceDialogUI {

	private static final long serialVersionUID = -6862587800037359302L;
	private static final Logger logger = CoreUIObject.getLogger(ScheduleReportTreeMaintenanceDialogUI.class);

	/**
	 * output class constructor
	 */
	public ScheduleReportTreeMaintenanceDialogUI() throws Exception {
		super();
	}

	/**
	 * @see com.kingdee.eas.fdc.schedule.client.report.ScheduleReportOrgQueryUI#buildDefaultTree()
	 */
	@Override
	protected void buildDefaultTree() throws Exception {

		final ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();
		kDOrgTree.addTreeWillExpandListener(new TreeWillExpandListener() {

			public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
			}

			public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
				projectTreeBuilder.updateTreeNodeColor(kDOrgTree);
			}

		});
		projectTreeBuilder.build(this, kDOrgTree, actionOnLoad);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) kDOrgTree.getModel().getRoot();
		node.setCheckBoxEnabled(false);
		node.setCheckBoxVisible(false);
	}

	/**
	 * @see com.kingdee.eas.fdc.schedule.client.report.ScheduleReportOrgQueryUI#actionConfirm_actionPerformed(java.awt.event.ActionEvent)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void actionConfirm_actionPerformed(ActionEvent e) throws Exception {

		Map<String, DefaultKingdeeTreeNode> checkedMap = getCheckedTreeNodes();
		if (checkedMap == null || checkedMap.isEmpty()) {
			FDCMsgBox.showWarning(this, "请选择节点数据!");
			SysUtil.abort();
		}
		// 2.用于记录构建自定义树结构的节点选择信息
		List<Map.Entry<String, DefaultKingdeeTreeNode>> sortedMapNodeToList = ScheduleChartHelper.sortedMapByLevel(checkedMap);
		Object[] checkedMapObj = new Object[] { sortedMapNodeToList };
		((F7ScheduleReportTreeMaintenaceDialog) this.getUIContext().get("F7Dialog")).setCanceled(false);
		this.getUIContext().put(RETURNDATA, checkedMapObj);
		destroyWindow();

	}

	/**
	 * 描述：获取选中的子节点信息,包括ID和名称
	 * @param node
	 * @param idMap
	 * @Author：adward_huang
	 * @CreateTime：2012-9-20
	 */
	protected void getCheckedNodeChildNodes(DefaultKingdeeTreeNode node, Map<String, DefaultKingdeeTreeNode> nodeMap) {

		if (node.isChecked()) {
			setCheckedParentNode(node, nodeMap);
		} else if (!node.isLeaf() && node.isChecked()) {
			if (node.getUserObject() instanceof OrgStructureInfo) {
				OrgStructureInfo orgUnitInfo = (OrgStructureInfo) node.getUserObject();
				nodeMap.put(orgUnitInfo.getId().toString(), node);
			}
			for (int i = 0; i < node.getChildCount(); i++) {
				DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode) node.getChildAt(i);
				getCheckedNodeChildNodes(child, nodeMap);
			}
		}
	}

	protected void setCheckedParentNode(DefaultKingdeeTreeNode node, Map<String, DefaultKingdeeTreeNode> nodeMap) {

		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			nodeMap.put(projectInfo.getId().toString(), node);
		} else if (node.getUserObject() instanceof FullOrgUnitInfo) {
			FullOrgUnitInfo orgUnitInfo = (FullOrgUnitInfo) node.getUserObject();
			nodeMap.put(orgUnitInfo.getId().toString(), node);
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo orgUnitInfo = (OrgStructureInfo) node.getUserObject();
			nodeMap.put(orgUnitInfo.getId().toString(), node);
		}
	}

	public Object getData() {
		return getUIContext().get(RETURNDATA);
	}

	@SuppressWarnings("unchecked")
	public void setData(Object map) {
		getUIContext().put(RETURNDATA, map);
	}

}