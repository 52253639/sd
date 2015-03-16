/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.FDCUtils;

/**
 * @author cassiel_peng
 * @date 2009-12-18 月度付款计划序时簿的过滤界面 本身这个过滤面板上还有工程项目和合同类型两个过滤条件，但是后来去掉了.所以注释了很多代码
 */
public class MonPayPlanFilterUI extends AbstractMonPayPlanFilterUI {

	// private TreeSelectDialog projectSelectDlg;

	private static final Logger logger = CoreUIObject
			.getLogger(MonPayPlanFilterUI.class);

	private Map listUICtx = null;

	public void setListUICtx(Map listUICtx) {
		this.listUICtx = listUICtx;
	}

	public MonPayPlanFilterUI() throws Exception {
		super();
	}

	public void storeFields() {
		super.storeFields();
	}
	//责任人、行政部门支持在全集团范围内选择 
	private boolean selectRespDeptInGroup() {
		boolean selectRespDeptInGropu=false;
		try {
			selectRespDeptInGropu=FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_SELECTPERSON);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return selectRespDeptInGropu;
	}
	public void onLoad() throws Exception {
		 String cuId=SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
		 super.onLoad();
		 
		 FDCClientUtils.setRespDeptF7(kDBizPromptBox1, this, selectRespDeptInGroup()?null:cuId);
		 
		 FDCClientUtils.initSupplierF7(this, kDBizPromptBox2, cuId);
		 
		 kDBizPromptBox1.setEditable(false);
		 kDBizPromptBox2.setEditable(false);
	}

	protected void btnOK_actionPerformed(ActionEvent e) throws Exception {

		FDCCustomerParams param = new FDCCustomerParams();
		
		// 责任部门
		if (kDBizPromptBox1.getValue() != null
				&& kDBizPromptBox1.getValue() instanceof AdminOrgUnitInfo) {
			AdminOrgUnitInfo admin = (AdminOrgUnitInfo) this.kDBizPromptBox1
					.getValue();
			String adminId = admin.getId() == null ? "" : admin.getId()
					.toString();
			param.add("adminId", adminId);
		} // 乙方单位
		if (kDBizPromptBox2.getValue() != null
				&& kDBizPromptBox2.getValue() instanceof SupplierInfo) {
			SupplierInfo supplier = (SupplierInfo) this.kDBizPromptBox2
					.getValue();
			String supplierId = supplier.getId() == null ? "" : supplier
					.getId().toString();
			param.add("supplierId", supplierId);
		}

		if (this.listUICtx != null) {
			this.listUICtx.put("QUERYPARAM", param);
		}
		destroyWindow();
	}

	protected void btnCancle_actionPerformed(ActionEvent e) throws Exception {
		destroyWindow();
		super.btnCancle_actionPerformed(e);
	}

	/*
	 * protected void
	 * btnProjectSelect_actionPerformed(java.awt.event.ActionEvent e) throws
	 * Exception { if (this.projectSelectDlg == null) {
	 * this.initProjectDlg(null); } Object object =
	 * projectSelectDlg.showDialog(); setProjectByTree(object);
	 * super.btnProjectSelect_actionPerformed(e); }
	 */
	/*
	 * private void initProjectDlg(String[] projectIds) throws Exception {
	 * ProjectTreeBuilder builder = new ProjectTreeBuilder(true); KDTree tree =
	 * new KDTree(); builder.build(this.listUI, tree, this.actionListOnLoad);
	 * TreeModel treeModel = tree.getModel();
	 * FDCHelper.setTreeForbidNode((DefaultKingdeeTreeNode) treeModel.getRoot(),
	 * "isIsEnabled", Boolean.FALSE); projectSelectDlg = new
	 * TreeSelectDialog(this, treeModel, "getId,toString", projectIds); }
	 * 
	 * private void setProjectByTree(Object object) { List projectIdList = new
	 * ArrayList(); if (object != null) { List projectList = (List) object;
	 * String text = ""; for (int i = 0; i < projectList.size(); i++) { if
	 * (projectList.get(i) instanceof ProjectInfo) { ProjectInfo project =
	 * (ProjectInfo) projectList.get(i); if (project.isIsLeaf()) { if
	 * (!text.equals("")) { text += ","; } text += project.getName(); }
	 * projectIdList.add(project.getId().toString()); } }
	 * this.kDTextField1.setText(text); Object[] ids = projectIdList.toArray(new
	 * String[] {}); if (FDCHelper.isEmpty(ids)) {
	 * this.kDTextField1.setUserObject(null); } else {
	 * this.kDTextField1.setUserObject(ids); } } }
	 * 
	 * 
	 * private void initContractTypeDlg(String[] contractTypeIds) throws
	 * Exception, BOSException { TreeModel treeModel =
	 * FDCClientHelper.createDataTree(ContractTypeFactory.getRemoteInstance(),
	 * null); FDCHelper.setTreeForbidNode((DefaultKingdeeTreeNode)
	 * treeModel.getRoot(), "isIsEnabled", Boolean.FALSE); pro = new
	 * TreeSelectDialog(this, treeModel, "getId,toString", contractTypeIds); }
	 * private void setContractTypeByTree(Object object) { List
	 * contractTypeIdList = new ArrayList(); if (object != null) { List
	 * contractTypeList = (List) object; String text = ""; for (int i = 0; i <
	 * contractTypeList.size(); i++) { if (contractTypeList.get(i) instanceof
	 * ContractTypeInfo) { ContractTypeInfo contractType = (ContractTypeInfo)
	 * contractTypeList .get(i);
	 * contractTypeIdList.add(contractType.getId().toString()); if (contractType
	 * != null && contractType.isIsLeaf()) { if (!text.equals("")) { text +=
	 * ","; } text += contractType.getName(); } } }
	 * this.kDTextField2.setText(text); Object[] ids =
	 * contractTypeIdList.toArray(new String[] {}); if (FDCHelper.isEmpty(ids))
	 * { this.kDTextField2.setUserObject(null); } else {
	 * this.kDTextField2.setUserObject(ids);
	 * 
	 * } } } protected void
	 * btnContractTypeSelectProjectSelect_actionPerformed(java
	 * .awt.event.ActionEvent e) throws Exception { if (this.pro == null) {
	 * this.initContractTypeDlg(null); } Object object = pro.showDialog();
	 * setContractTypeByTree(object);
	 * super.btnContractTypeSelectProjectSelect_actionPerformed(e); }
	 */
}