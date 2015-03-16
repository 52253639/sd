/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;

/**
 * 部门月度计划申报表，项目月度计划申报表 过滤界面
 */
public class FDCDepMonBudgetAcctFilterUI extends
		AbstractFDCDepMonBudgetAcctFilterUI {
	private static final Logger logger = CoreUIObject
			.getLogger(FDCDepMonBudgetAcctFilterUI.class);

	// 责任人是否可以选择其他部门的人员,默认false后续要求时再取值
	private boolean canSelectOtherOrgPerson = false;

	/**
	 * output class constructor
	 */
	public FDCDepMonBudgetAcctFilterUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output btnOK_actionPerformed method
	 */
	protected void btnOK_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		// 责任部门
		if (prmtRespDept.getValue() != null
				&& prmtRespDept.getValue() instanceof AdminOrgUnitInfo) {
			getUIContext().put(
					"deptmentId",
					((AdminOrgUnitInfo) prmtRespDept.getValue()).getId()
							.toString());
//			getUIContext().put("deptmentInfo", (AdminOrgUnitInfo) prmtRespDept.getValue());
		} else {
			AdminOrgUnitInfo orgUnitInfo = SysContext.getSysContext().getCurrentAdminUnit();
	    	getUIContext().put("deptmentId", orgUnitInfo.getId().toString());
	    	//不能将deptmentId直接置空赋一个null,因为后续会引用这个ID做某些校验，置空会引发中断 by cassiel_peng
//			getUIContext().put("deptmentId", null);
		}
		// 责任人
		if (prmtRespPerson.getValue() != null
				&& prmtRespPerson.getValue() instanceof PersonInfo) {
			getUIContext()
					.put(
							"respPersonId",
							((PersonInfo) prmtRespPerson.getValue()).getId()
									.toString());

		} else {
			getUIContext().put("respPersonId", null);
		}
		// 制单人(合同)
		if (prmtCreator.getValue() != null
				&& prmtCreator.getValue() instanceof UserInfo) {
			getUIContext().put("creatorId",
					((UserInfo) prmtCreator.getValue()).getId().toString());

		} else {
			getUIContext().put("creatorId", null);
		}
		// 付款计划最后修改人（分录）
		if (prmtEditor.getValue() != null
				&& prmtEditor.getValue() instanceof UserInfo) {
			getUIContext().put("editorId",
					((UserInfo) prmtEditor.getValue()).getId().toString());

		} else {
			getUIContext().put("editorId", null);
		}
		destroyWindow();
		super.btnOK_actionPerformed(e);
	}
	public boolean destroyWindow() {
		return super.destroyWindow();
	}
	public void clear() {
		super.clear();
		getUIContext().put("deptmentId", null);
		getUIContext().put("respPersonId", null);
		getUIContext().put("creatorId", null);
		getUIContext().put("editorId", null);
	}
	/**
	 * output btnNO_actionPerformed method
	 */
	protected void btnNO_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		destroyWindow();
		super.btnNO_actionPerformed(e);
	}

	public void onLoad() throws Exception {
		// TODO 自动生成方法存根
		String cuId = SysContext.getSysContext().getCurrentCtrlUnit().getId()
				.toString();
		FDCClientUtils.setRespDeptF7(prmtRespDept, this,
				canSelectOtherOrgPerson ? null : cuId);

		FDCClientUtils.setPersonF7(prmtRespPerson, this,
				canSelectOtherOrgPerson ? null : cuId);
/*		this.prmtRespPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");
		prmtRespPerson.setEditFormat("$name$");
		prmtRespPerson.setCommitFormat("$name$");*/
//		prmtRespPerson.setEditFormat("$name$");
//		prmtRespPerson.setCommitFormat("$name$");
//		prmtCreator.setCommitFormat("$number$");
//		prmtEditor.setCommitFormat("$number$");
		super.onLoad();
	}

	/**
	 * @return AdminOrgUnitInfo 行政组织
	 * @throws BOSException
	 */
	public Map showUI(Map uiContext) throws BOSException {
		getWindow(uiContext).show();
		return getWindow(uiContext).getUIObject().getUIContext();
	}

	private IUIWindow window = null;

	private IUIWindow getWindow(Map context) throws BOSException {
		if (window == null) {
			window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
					FDCDepMonBudgetAcctFilterUI.class.getName(), context, null,
					null);
		}
		return window;
	}

}