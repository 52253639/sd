/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.invite.FDCInviteException;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.pm.QualityCheckPointInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;

/**
 *
 * @author 肖飙彪_金蝶深圳分公司
 *
 */
public class InviteTypeEditUI extends AbstractInviteTypeEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(InviteTypeEditUI.class);

	/**
	 * output class constructor
	 */
	public InviteTypeEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * 保存
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		if (this.getOprtState().equals(STATUS_ADDNEW)) {
			this.setOprtState(STATUS_VIEW);
		}
	}

	/**
	 * 提交
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancel_actionPerformed(e);
	}

	protected void showResultMessage(String message) {
		// setMessageText(EASResource.getString(message));
		setMessageText(message);
		// setMessageIcon(SHOW_MESSAGE_ICON_ERROR);
		// setMessageBgcolor(SHOW_MESSAGE_BG_ERROR);
		showMessage();
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCancel_actionPerformed(e);
	}

	/**
	 * output actionFirst_actionPerformed
	 */
	public void actionFirst_actionPerformed(ActionEvent e) throws Exception {
		super.actionFirst_actionPerformed(e);
	}

	/**
	 * output actionPre_actionPerformed
	 */
	public void actionPre_actionPerformed(ActionEvent e) throws Exception {
		super.actionPre_actionPerformed(e);
	}

	/**
	 * output actionNext_actionPerformed
	 */
	public void actionNext_actionPerformed(ActionEvent e) throws Exception {
		super.actionNext_actionPerformed(e);
	}

	/**
	 * output actionLast_actionPerformed
	 */
	public void actionLast_actionPerformed(ActionEvent e) throws Exception {
		super.actionLast_actionPerformed(e);
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionCopy_actionPerformed
	 */
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopy_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		
		this.txtLongNumber.setEnabled(true);
		this.bizName.setEnabled(true);
		this.txtDescription.setEnabled(true);
		
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		
		
		super.actionEdit_actionPerformed(e);
		if(OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())){//判断是否是系统预设
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
			this.menuItemAddNew.setEnabled(true);
			this.menuItemEdit.setEnabled(true);
			this.menuItemRemove.setEnabled(false);
			this.txtLongNumber.setEnabled(false);
			this.bizName.setEnabled(false);
			this.txtDescription.setEnabled(false);
			this.editData.getCU().setId(BOSUuid.read(OrgConstants.SYS_CU_ID));
			this.editData.getOrg().setId(BOSUuid.read(OrgConstants.SYS_CU_ID));
		
			
		}
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		
		
		super.actionRemove_actionPerformed(e);
	}

	
	

	/**
	 * output actionSubmitOption_actionPerformed
	 */
	public void actionSubmitOption_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSubmitOption_actionPerformed(e);
	}

	protected IObjectValue createNewData() {
		InviteTypeInfo inviteTypeInfo = new InviteTypeInfo();
		inviteTypeInfo.setIsEnabled(true);
		return inviteTypeInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return InviteTypeFactory.getRemoteInstance();
	}

	public void loadFields() {
		super.loadFields();
		Map map = getUIContext();
		InviteTypeInfo fatherInviteTypeInfo = (InviteTypeInfo) map
				.get(UIContext.PARENTNODE);

		if (null != fatherInviteTypeInfo) {
			String fatherLongNum = fatherInviteTypeInfo.getLongNumber();
			fatherLongNum = fatherLongNum.replaceAll("!", ".");
			this.txtUpperNum.setText(fatherLongNum);// 设置上级编码

			String operateState = this.getOprtState();
			if (STATUS_ADDNEW.equals(operateState)) {
				this.txtLongNumber.setText(fatherLongNum + ".");
			} else if (STATUS_EDIT.equals(operateState)) {
				String longNumber = this.editData.getLongNumber();
				longNumber = longNumber.replaceAll("!", ".");
				this.txtLongNumber.setText(longNumber);

			} else if (STATUS_VIEW.equals(operateState)) {
				String longNumber = this.editData.getLongNumber();
				longNumber = longNumber.replaceAll("!", ".");
				this.txtLongNumber.setText(longNumber);
			}
		} else {
			if ((STATUS_ADDNEW.equals(getOprtState()))) {// 新增状态
				// this.txtLongNumber.setText(strTemp + ".");
			} else if (STATUS_EDIT.equals(getOprtState())) {

				if (OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId()
						.toString())) {
					this.btnRemove.setEnabled(false);
				}
			} else if (STATUS_VIEW.equals(getOprtState())) {
				String strTemp = this.editData.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				// parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
				this.txtLongNumber.setText(strTemp);

			}
		}
//		if(OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())){
			actionAddNew.setEnabled(true);
			actionRemove.setEnabled(true);
//		}else{
//			actionAddNew.setEnabled(false);
//			actionEdit.setEnabled(false);
//			actionRemove.setEnabled(false);
//		}
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		Map map = getUIContext();
		Object parentObj = map.get(UIContext.PARENTNODE);
		// 编码是否为空
		if (this.txtLongNumber.getText() == null
				|| this.txtLongNumber.getText().trim().equals("")) {
			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
		} else {
			String number = "";
			if (parentObj != null) {
				InviteTypeInfo parentInviteTypeInfo = (InviteTypeInfo) parentObj;
				this.editData.setParent(parentInviteTypeInfo);

				String parentNumber = parentInviteTypeInfo.getLongNumber();// 上级编码
				parentNumber = parentNumber.replaceAll("!", ".");

				if (STATUS_ADDNEW.equals(getOprtState())) { // 新增状态
					String longNumber = txtLongNumber.getText();
					if ((longNumber.equals(parentNumber + "."))
							|| (longNumber.length() < parentNumber.length() + 1)) {
						// 编码不完整
						throw new FDCBasedataException(
								FDCBasedataException.NUMBER_CHECK_2);
					}

					if ((longNumber.substring(parentNumber.length() + 1,
							longNumber.length()).indexOf(".") > 0)// 用户打了点
							|| (!longNumber.substring(0, parentNumber.length())
									.equals(parentNumber))) {// 初始父编码被修改，编码不符合规范
						throw new FDCBasedataException(
								FDCBasedataException.NUMBER_CHECK_3);
					}

					String longNum = this.txtLongNumber.getText();

					number = longNum.substring(longNum.lastIndexOf(".") + 1,
							longNum.length());

				} else {
					String longNum = this.txtLongNumber.getText();

					number = longNum.substring(longNum.lastIndexOf(".") + 1,
							longNum.length());
					this.editData.setNumber(number);
				}
			} else {
				if (!(this.txtLongNumber.getText().indexOf(".") < 0)) {
					// 编码不符合规范
					throw new FDCBasedataException(
							FDCBasedataException.NUMBER_CHECK_3);
				}
				number = this.txtLongNumber.getText();
			}

			this.editData.setNumber(number);
			String temp = this.txtLongNumber.getText();
			temp = temp.replace('.', '!');
			this.editData.setLongNumber(temp);
		}
		// 编码
		FilterInfo filterInfo = new FilterInfo();
		filterInfo
				.appendFilterItem("longNumber", this.editData.getLongNumber());
		if (STATUS_EDIT.equals(getOprtState())) {
			filterInfo.getFilterItems().add(
					new FilterItemInfo("id", this.editData.getId().toString(),
							CompareType.NOTEQUALS));
		}

		if (InviteTypeFactory.getRemoteInstance().exists(filterInfo)) {
			throw new FDCInviteException(
					FDCInviteException.NUMBER_IS_OVER_IN_ONE_ORG);
		}

		// 名称是否为空
		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(
				this.bizName, this.editData, "name");
		if (flag) {
			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
		}

		filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("name", this.editData.getName());
		if (STATUS_EDIT.equals(getOprtState())) {
			filterInfo.getFilterItems().add(
					new FilterItemInfo("id", this.editData.getId().toString(),
							CompareType.NOTEQUALS));
		}

		if (InviteTypeFactory.getRemoteInstance().exists(filterInfo)) {
			throw new FDCInviteException(FDCInviteException.NAME_IS_OVER);
		}
//		
//		//合同类型不能为空
//		if(editData.getContractType() == null)
//		{
//			com.kingdee.eas.util.client.MsgBox.showWarning("合同类型不能为空");
//			SysUtil.abort();
//		}
		
		// 描述超界处理
		if (this.txtDescription.getText() != null
				&& this.txtDescription.getText().length() > 255) {
			this.editData.setDescription(this.txtDescription.getText().substring(
					0, 254));
		}
		this.editData.setOrg((FullOrgUnitInfo) SysContext.getSysContext()
				.getCurrentOrgUnit());
		if (map.containsKey("updateTable") && map.containsKey("updateID")
				&& !map.containsKey("alreadUpdate")) {
			this.editData.getExtendedProperties().put("updateTable",
					map.get("updateTable"));
			this.editData.getExtendedProperties().put("updateID",
					map.get("updateID"));
			map.put("alreadUpdate", "");
		}

	}
     //初始化编辑数据
	protected void initOldData(IObjectValue dataObject) {
		Map map = getUIContext();
		InviteTypeInfo parentInfo = (InviteTypeInfo) map
				.get(UIContext.PARENTNODE);

		
		
		if (map.get(UIContext.PARENTNODE) != null) {
			if ((STATUS_ADDNEW.equals(getOprtState()))) {// 新增状态
				String strTemp = parentInfo.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				((InviteTypeInfo) dataObject).setLongNumber(strTemp + ".");
			} else if (STATUS_EDIT.equals(getOprtState())) {
				String strTemp = ((InviteTypeInfo) dataObject).getLongNumber();
				strTemp = strTemp.replace('!', '.');
				((InviteTypeInfo) dataObject).setLongNumber(strTemp);
			}
		}
		this.txtLongNumber.requestFocus();
		super.initOldData(dataObject);
	}

	private InviteTypeInfo getPanrentInfo(){
		if(editData.getParent() != null){
			return editData.getParent();
		}else if(getUIContext().get(UIContext.PARENTNODE)!=null){
			return (InviteTypeInfo) getUIContext().get(UIContext.PARENTNODE);
		}else{
			return null;
		}
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		setBtnStatus();
		this.prmtContractType.setRequired(true);
		txtLongNumber.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				InviteTypeInfo parent = getPanrentInfo();
				if (/* STATUS_VIEW.equals(getOprtState()) || */parent == null)
					return;
				String longNumber = parent.getLongNumber().replace('!', '.') ;
				if(STATUS_ADDNEW.equals(getOprtState())){
					longNumber = longNumber+"." ;
				}
				if(STATUS_EDIT.equals(getOprtState())){
					longNumber = longNumber.substring(0,longNumber.lastIndexOf('.')+1) ;
				}
				if (!txtLongNumber.getText().startsWith(longNumber)) {
					txtLongNumber.setText(longNumber);
					txtLongNumber.setSelectionStart(longNumber.length());
				}
			}

			public void keyTyped(KeyEvent e) {
			}
		});
		
		
	}

	private void setBtnStatus() {
		this.btnAttachment.setVisible(false);
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);
		this.btnCopy.setVisible(false);
		this.btnFirst.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnPre.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnSave.setVisible(false);

		if (STATUS_ADDNEW.equals(getOprtState())) {// 新增状态

		} else if (STATUS_EDIT.equals(getOprtState())) {// 修改状态

			if (OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())) {
				if(this.txtDescription.getText().equals("系统预设")){
					this.btnAddNew.setEnabled(true);
					this.btnEdit.setEnabled(true);
					this.menuItemAddNew.setEnabled(true);
					this.menuItemEdit.setEnabled(true);
					this.menuItemRemove.setEnabled(false);
					this.txtLongNumber.setEnabled(false);
					this.bizName.setEnabled(false);
					this.txtDescription.setEnabled(false);
				}
				
				this.btnRemove.setEnabled(false);
			}
		} else if (STATUS_VIEW.equals(getOprtState())) {// 查看状态
			if (SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)) {
				if(OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())){
					this.btnAddNew.setEnabled(true);
					this.btnEdit.setEnabled(true);
					this.menuItemAddNew.setEnabled(true);
					this.menuItemEdit.setEnabled(true);
					this.menuItemRemove.setEnabled(false);
					this.txtLongNumber.setEnabled(false);
					this.bizName.setEnabled(false);
					this.txtDescription.setEnabled(false);
				}else{
					this.btnAddNew.setEnabled(true);
					this.btnEdit.setEnabled(true);
					this.menuItemAddNew.setEnabled(true);
					this.menuItemEdit.setEnabled(true);
					this.menuItemRemove.setEnabled(true);
				}
			} else {
				this.btnAddNew.setEnabled(false);
				this.btnEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);
				this.btnCancel.setVisible(false);
				this.btnCancelCancel.setVisible(false);
				this.menuItemAddNew.setEnabled(false);
				this.menuItemEdit.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("longNumber"));
		sic.add(new SelectorItemInfo("isEnabled"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("parent.*"));
		sic.add(new SelectorItemInfo("CU.id"));
		
		sic.add(new SelectorItemInfo("contractType.id"));
		sic.add(new SelectorItemInfo("contractType.number"));
		sic.add(new SelectorItemInfo("contractType.name"));
		sic.add(new SelectorItemInfo("contractType.longNumber"));
		return sic;
	}
}