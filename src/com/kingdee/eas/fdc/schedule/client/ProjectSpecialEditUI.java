/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.ProjectSpecialFactory;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * @(#)		    ProjectSpecialEditUI.java 版权： 金蝶国际软件集团有限公司版权所有 描述： 项目专项编辑界面
 * @author 王维强
 * @version EAS7.0
 * @createDate 2011-8-1
 * @see
 */
public class ProjectSpecialEditUI extends AbstractProjectSpecialEditUI {
	/**
	 * 
	 */
	private static final String NUMBER_DEF_TEXT = "01";
	private static final Logger logger = CoreUIObject.getLogger(ProjectSpecialEditUI.class);
	public ProjectSpecialListUI listui;
	public CurProjectInfo projectInfo;
	// 当前操作状态
	// private String State = "";
	// 工程项目树ID
	private String proId = "";
	// 树选择状态
	private String treeSel = "";
	/**
	 * output class constructor
	 */
	public ProjectSpecialEditUI() throws Exception {
		super();
	}

	/**
	 * 初始化按钮
	 */
	protected void initWorkButton() {
		super.initWorkButton();
	}

	/**
	 * 
	 */
	public void storeFields() {
		super.storeFields();
		this.editData.setAdminPerson((PersonInfo)this.prmtAdminPerson.getValue());
	}

	/**
	 * 加载对象
	 */
	public void loadFields() {
		super.loadFields();
		if (getOprtState().equals(OprtState.ADDNEW)) {
			setNumber();
		}
		
		if (treeSel.equals("yes")) {
			btnAddNew.setEnabled(true);
		}
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return txtName;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	/**
	 * @param e
	 * @throws Exception
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		if (treeSel.equals("no")) {
			this.uiWindow.close();
		}
	}
	
	/**
	 * @param e
	 * @throws Exception
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		if (treeSel.equals("no")) {
			this.uiWindow.close();
		}
	}
	 
	protected void showSubmitSuccess() {
		// TODO Auto-generated method stub
		// super.showSubmitSuccess();
		ProjectSpecialListUI parentUI = (ProjectSpecialListUI) getUIContext().get("Owner");
		parentUI.setMainStatusBar(parentUI.getMainStatusBar());
		parentUI.setShowMessagePolicy(0);
		parentUI.setMessageText("项目专项设置保存成功！");
		parentUI.showMessage();
	}
	/**
	 * @param e
	 * @throws Exception
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}
	

	protected IObjectValue createNewData() {
		ProjectSpecialListUI projectSpecialListUI = (ProjectSpecialListUI) getUIContext().get("Owner");
		ProjectSpecialInfo info = new ProjectSpecialInfo();
		info.setCurProject(projectSpecialListUI.projectInfo);
		return info;
	}

	/**
	 * 数据验证
	 * 
	 * @param e
	 * @throws Exception
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		// 项目专项编码不能为空
		if (txtNumber.getText() == null || "".equals(txtNumber.getText())) {
			handlerException("scheduleNumber");
		}
		// 项目专项名称不能为空
		if (editData.getName() == null || "".equals(editData.getName())) {
			handlerException("scheduleName");
		}
		if (checkNumber() && !getOprtState().equals(OprtState.EDIT)) {
			MsgBox.showWarning("专项编码" + editData.getNumber() + "重复，不能保存");
			SysUtil.abort();
		}
	}

	/**
	 * @return
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("adminPerson.name"));
		sic.add(new SelectorItemInfo("adminPerson.id"));
		sic.add(new SelectorItemInfo("adminPerson.number"));
		sic.add(new SelectorItemInfo("adminDept.id"));
		sic.add(new SelectorItemInfo("adminDept.name"));
		sic.add(new SelectorItemInfo("adminDept.number"));
		sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.number"));
		sic.add(new SelectorItemInfo("curProject.name"));
		return sic;
	}

	public boolean checkNumber() throws BOSException, NumberFormatException, SQLException {
		projectInfo = (CurProjectInfo) editData.get("curProject");
		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" select FNumber as num from T_SCH_ProjectSpecial where FCurProjectID ='" + projectInfo.getId().toString()
				+ "'order by FNumber desc ");
		rs = sql.executeQuery();
		while (rs.next()) {
			if (txtNumber.getText().equals(rs.getString("num"))) {
				return true;
			}
		}
		return false;
	}

	private void handlerException(String agr) {
		MsgBox.showWarning(this, getResource(agr));
		SysUtil.abort();
	}

	/**
	 * @description 获取资源文件
	 */
	private String getResource(String msg) {
		return EASResource.getString("com.kingdee.eas.fdc.schedule.ScheduleResource", msg);

	}

	protected ICoreBase getBizInterface() throws Exception {
		return ProjectSpecialFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		listui = (ProjectSpecialListUI) getUIContext().get("listui");
		this.btnCancelCancel.setVisible(false);
		this.btnRemove.setVisible(false);
		initSeting();
	}

	protected boolean isModifySave() {
		return false;
	}

	/**
	 * 获取同一工程项目下专项编码
	 */
	IRowSet rs = null;

	private void setNumber() {
		try {
			proId = ((CurProjectInfo) editData.get("curProject")).getId().toString();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		if (proId == null || proId.equals("")) {
			txtNumber.setText(NUMBER_DEF_TEXT);
			return;
		}
		// SQL查询部门下专项编码
		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" select FNumber as num from T_SCH_ProjectSpecial where FCurProjectID ='" + proId + "'order by FNumber desc ");
		try {
			rs = sql.executeQuery();
			// 初始set"01"
			txtNumber.setText(NUMBER_DEF_TEXT);
			while (rs.next()) {
				try {
					long curNum = Long.parseLong(rs.getString("num"));
					// 编码成功转换为Long继续，否则braek
					if (curNum > 0 && curNum < 9) {
						txtNumber.setText("0" + (curNum + 1L));
					} else {
						txtNumber.setText("" + (curNum + 1L));
					}
					break;
				} catch (Exception e) {
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化
	 */
	public void initSeting() {
		prmtAdminDept.setQueryInfo("com.kingdee.eas.sem.mp.app.AdminOrgUnitF7");
		prmtAdminPerson.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
		windowTitle = "项目专项设置编辑界面";
		if (!SysContext.getSysContext().getCurrentOrgUnit().isIsCostOrgUnit()) {
			FDCMsgBox.showInfo("当前组织不是成本中心，不能进入！");
			SysUtil.abort();
		}
		try {
			proId = ((CurProjectInfo) editData.get("curProject")).getId().toString();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		treeSel = (String) getUIContext().get("treeSelect");
		if (treeSel.equals("no")) {
			btnAddNew.setEnabled(false);
		}
		if (projectInfo == null) {
			projectInfo = (CurProjectInfo) editData.get("curProject");
		}
		String cuid = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
		// 行政组织F7控件分层显示,false为选择非名细行政组织
		FDCClientUtils.setRespDeptF7(this.prmtAdminDept, this);
		FDCClientUtils.setPersonF7(this.prmtAdminPerson,this,null);
	}

	/**
	 * @param e
	 * @throws Exception
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}

	private void setBtnAttr() {
		btnCancelCancel.setVisible(false);
		btnCancel.setVisible(false);
		btnRemove.setVisible(false);
	}

	protected FDCDataBaseInfo getEditData() {
		return new ProjectSpecialInfo();
	}
}