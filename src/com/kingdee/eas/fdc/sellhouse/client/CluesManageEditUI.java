/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerCollection;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerFactory;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo;
import com.kingdee.eas.fdc.sellhouse.CluesManageFactory;
import com.kingdee.eas.fdc.sellhouse.CluesManageInfo;
import com.kingdee.eas.fdc.sellhouse.CluesSourceEnum;
import com.kingdee.eas.fdc.sellhouse.CluesStatusEnum;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceTrackInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class CluesManageEditUI extends AbstractCluesManageEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(CluesManageEditUI.class);
	// 用来判断是否为售楼组织
	private Map map = FDCSysContext.getInstance().getOrgMap();
    //项目，保存从其他功能点传过来的项目
	private SellProjectInfo sellProject = new SellProjectInfo();
	private boolean isDirect = true;
	/**
	 * output class constructorx
	 */
	public CluesManageEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.txtNumber.setEnabled(false);
		btnCancel.setVisible(false);
		btnCancelCancel.setVisible(false);
		this.btnAttachment.setVisible(false);
		// 重新获取项目
		if (this.getUIContext().get("info") == null) {
			getUIContext().put("info", editData.getSellProject());
			
		}
		sellProject = (SellProjectInfo)this.getUIContext().get("info");
		 if (editData.getCluesStatus() != null
				&& !editData.getCluesStatus().equals("")) {
				this.actionEdit.setEnabled(false);
				this.actionRemove.setEnabled(false);
		} else {
			this.actionEdit.setEnabled(true);
		}

		if (!map.containsKey(SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString())) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		} else {
			if (STATUS_ADDNEW.equals(getOprtState())) {
				actionEdit.setEnabled(false);
				actionRemove.setEnabled(false);
			} else if (STATUS_EDIT.equals(getOprtState())) {
				actionEdit.setEnabled(false);
				this.prmtPropertyConsultant.setEnabled(false);
				//this.comboSource.setEnabled(false);
				this.prmtCognizePath.setEnabled(false);
			} else if (STATUS_VIEW.equals(getOprtState())) {
				actionSubmit.setEnabled(false);
			} else {
				actionAddNew.setEnabled(true);
				actionEdit.setEnabled(true);
				actionRemove.setEnabled(true);
			}
		}

		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("group.id", CRMConstants.KNOW_TYPE_GROUP_ID, CompareType.EQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("CU.id", SysContext.getSysContext().getCurrentCtrlUnit().getId().toString(), CompareType.EQUALS));
		viewInfo.setFilter(filter);
		prmtCognizePath.setEntityViewInfo(viewInfo);

		EntityViewInfo view= NewCommerceHelper.getPermitSalemanView(editData.getSellProject(),null);
		prmtPropertyConsultant.setEntityViewInfo(view);
		
		queryCluesTrack();
		tblTrack.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		tblTrack.setEditable(false);
		if (tblTrack.getColumn("trackDate") != null) {
			tblTrack.getColumn("trackDate").getStyleAttributes()
					.setNumberFormat("yyyy-MM-dd");
		}

		if (!STATUS_ADDNEW.equals(getOprtState())) {
			queryCluesQustion();
		}
		tblQuestionPaper.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		tblQuestionPaper.setEditable(false);
		if (tblQuestionPaper.getColumn("bizDate") != null) {
			tblQuestionPaper.getColumn("bizDate").getStyleAttributes()
					.setNumberFormat("yyyy-MM-dd");
		}
		if (tblQuestionPaper.getColumn("createTime") != null) {
			tblQuestionPaper.getColumn("createTime").getStyleAttributes()
					.setNumberFormat("yyyy-MM-dd");
		}
		if (tblQuestionPaper.getColumn("lastUpdateTime") != null) {
			tblQuestionPaper.getColumn("lastUpdateTime").getStyleAttributes()
					.setNumberFormat("yyyy-MM-dd");
		}
		
		comboSource.removeItem(CluesSourceEnum.CHOOCEROOM);
		comboSource.removeItem(CluesSourceEnum.SINCERITYPRUCHASE);
		
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
		this.txtDescription.setAccessAuthority(CtrlCommonConstant.PRECISION_MAX);
		this.txtDescription.setMaxLength(255);
		if(!this.getOprtState().equals(OprtState.ADDNEW)){
			isDirect=false;
		}
		
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		
		if (editData.getCluesStatus() != null
				&& !editData.getCluesStatus().equals("")) {
			this.btnAddTrack.setEnabled(false);
		}
	}

	protected IObjectValue createNewData() {
		CluesManageInfo cluesManageInfo = new CluesManageInfo();
		cluesManageInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		cluesManageInfo.setCreator(SysContext.getSysContext()
				.getCurrentUserInfo());
		cluesManageInfo.setPropertyConsultant(SysContext.getSysContext()
				.getCurrentUserInfo());

		SellProjectInfo sellProjectInfo = (SellProjectInfo) this.getUIContext()
				.get("info");
		if (sellProjectInfo != null) {
			cluesManageInfo.setSellProject(sellProjectInfo);
		}
		BOSUuid bosid = BOSUuid.create(cluesManageInfo.getBOSType());
		cluesManageInfo.setId(bosid);
//			try {
//				ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
//				OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentSaleUnit().castToFullOrgUnitInfo();
//				boolean existCodingRule = iCodingRuleManager.isExist(
//						new CluesManageInfo(), orgUnit.getId().toString());
//				if (existCodingRule) {
//					String retNumber = iCodingRuleManager.getNumber(cluesManageInfo, orgUnit
//							.getId().toString());
//					cluesManageInfo.setNumber(retNumber);
//				}else {
//					Timestamp time = new Timestamp(new Date().getTime());
//					String milliSecond = String.valueOf(time).substring(20, 23);
//					String timeStr = String.valueOf(time).replaceAll("-", "").replaceAll(" ", "").replaceAll(":", "").substring(0,14)+milliSecond;
//					String number = String.valueOf(timeStr)+"001";
//					cluesManageInfo.setNumber(number);
//				}
//			} catch (EASBizException e) {
//				e.printStackTrace();
//			} catch (BOSException e) {
//				e.printStackTrace();
//			}
		cluesManageInfo.setSource(CluesSourceEnum.VISIT);
		return cluesManageInfo;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		if (StringUtils.isEmpty(this.txtNumber.getText())) {
			MsgBox.showInfo("线索编码不能为空！");
			abort();
		}

		if (StringUtils.isEmpty(this.txtName.getText())) {
			MsgBox.showInfo("线索名称不能为空！");
			abort();
		}

		super.verifyInput(e);
		if (this.txtPhone.getText() == null
				|| this.txtPhone.getText().trim().length() < 7) {
			MsgBox.showInfo(this, "联系电话必须输入,且至少需要输入7位以上!");
			this.abort();
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtPropertyConsultant);
		FDCClientVerifyHelper.verifyEmpty(this, this.comboSource);
		checkNumber();
		checkPhone();
	}

	/**
	 * 判断线索编码不能重复
	 * 
	 * @throws BOSException
	 */
	public void checkNumber() throws Exception {
		String number = this.txtNumber.getText().trim();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", number));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId()));
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			if (CluesManageFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("线索编码已存在,不能重复");
				SysUtil.abort();
			}
		}
		if(SHECustomerFactory.getRemoteInstance().exists(filter)){
			MsgBox.showInfo("客户编码已存在,不能重复");
			SysUtil.abort();
		}
	}

	/**
	 * 判断电话不能重复
	 * 
	 * @throws BOSException
	 */
	public void checkPhone() throws Exception {
		String phone = this.txtPhone.getText().trim();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("phone", phone));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId()));
		if (this.oprtState.equals(OprtState.ADDNEW)) {
			if (CluesManageFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("联系电话已存在,不能重复");
				SysUtil.abort();
			}
		}
	}

	/**
	 * 查询线索跟进资料
	 */
	public void queryCluesTrack() throws Exception {
		tblTrack.removeRows();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("trackDate");
		sic.add("trackContent");
		sic.add("interactionType");
		sic.add("saleMan.*");
		FilterInfo filter = new FilterInfo();
		if (editData.getId() != null && !editData.getId().equals("")) {
			filter.getFilterItems().add(
					new FilterItemInfo("clues.id", this.editData.getId()));
			view.setSelector(sic);
			view.setFilter(filter);

			CommerceChanceTrackCollection trackColl = CommerceChanceTrackFactory
					.getRemoteInstance().getCommerceChanceTrackCollection(view);
			if (trackColl != null && trackColl.size() > 0) {
				for (int i = 0; i < trackColl.size(); i++) {
					CommerceChanceTrackInfo trackInfo = trackColl.get(i);
					tblTrack.checkParsed();
					tblTrack.setEditable(false);
					tblTrack.getSelectManager().setSelectMode(
							KDTSelectManager.ROW_SELECT);
					IRow addRow = tblTrack.addRow();

					if (trackInfo != null) {
						addRow.getCell("id").setValue(trackInfo.getId());
						addRow.getCell("trackDate").setValue(trackInfo.getTrackDate());
						if (trackInfo.getSaleMan() != null) {
							addRow.getCell("saleMan.name").setValue(trackInfo.getSaleMan().getName());
						} else {
							addRow.getCell("saleMan.name").setValue("");
						}
						addRow.getCell("interactionType").setValue(trackInfo.getInteractionType());
						addRow.getCell("trackContent").setValue(trackInfo.getTrackContent());
					}
				}
			}
		}
	}

	/**
	 * 查询线索问卷
	 */
	public void queryCluesQustion() throws Exception {
		tblQuestionPaper.removeRows();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("questionPaper.topric");
		sic.add("creator.*");
		sic.add("lastUpdateUser.*");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("cluesManage", this.editData.getId()));
		view.setSelector(sic);
		view.setFilter(filter);

		QuestionPaperAnswerCollection questionColl = QuestionPaperAnswerFactory
				.getRemoteInstance().getQuestionPaperAnswerCollection(view);
		if (questionColl != null && questionColl.size() > 0) {
			for (int i = 0; i < questionColl.size(); i++) {
				QuestionPaperAnswerInfo questionInfo = questionColl.get(i);
				tblQuestionPaper.checkParsed();
				tblQuestionPaper.setEditable(false);
				tblQuestionPaper.getSelectManager().setSelectMode(
						KDTSelectManager.ROW_SELECT);
				IRow addRow = tblQuestionPaper.addRow();

				if (questionInfo != null) {
					addRow.getCell("number").setValue(questionInfo.getNumber());
					addRow.getCell("bizDate").setValue(
							questionInfo.getBizDate());
					if (questionInfo.getQuestionPaper() != null) {
						addRow.getCell("questionPaper.topric").setValue(
								questionInfo.getQuestionPaper().getTopric());
					} else {
						addRow.getCell("questionPaper.topric").setValue("");
					}
					if (questionInfo.getCreator() != null) {
						addRow.getCell("creator.name").setValue(
								questionInfo.getCreator().getName());
					} else {
						addRow.getCell("creator.name").setValue("");
					}
					addRow.getCell("createTime").setValue(
							questionInfo.getCreateTime());

					if (questionInfo.getLastUpdateUser() != null) {
						addRow.getCell("lastUpdateUser.name").setValue(
								questionInfo.getLastUpdateUser().getName());
					} else {
						addRow.getCell("lastUpdateUser.name").setValue("");
					}
					addRow.getCell("lastUpdateTime").setValue(
							questionInfo.getLastUpdateTime());

				}
			}
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		this.prmtPropertyConsultant.setEnabled(true);
		//this.comboSource.setEnabled(true);
		this.prmtCognizePath.setEnabled(true);
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
	
		if(this.getOprtState().equals(OprtState.ADDNEW)){
			isDirect = false;
		}
		this.queryCluesQustion();
		this.queryCluesTrack();
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		this.prmtPropertyConsultant.setEnabled(false);
		//this.comboSource.setEnabled(false);
		this.prmtCognizePath.setEnabled(false);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData.getId() != null && !this.editData.getId().equals("")) {
			CluesManageInfo info = CluesManageFactory.getRemoteInstance()
					.getCluesManageInfo(
							"select id,cluesStatus where id='"
									+ this.editData.getId().toString() + "'");
			if (info.getCluesStatus() != null
					&& !info.getCluesStatus().equals("")) {
				FDCMsgBox.showWarning(this, "本记录已转商机或交易，不能删除!");
				this.abort();
			}
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("clues.id", this.editData.getId()));
			view.setFilter(filter);

			// 删除分录中的跟进记录
			CommerceChanceTrackCollection trackColl = CommerceChanceTrackFactory
					.getRemoteInstance().getCommerceChanceTrackCollection(view);
			if (trackColl != null && trackColl.size() > 0) {
				FDCMsgBox.showWarning(this, "已有跟进，不能删除!");
				this.abort();
			}

			 // 删除分录中的问卷记录
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("cluesManage", this.editData.getId()));
			view.setFilter(filter);
			QuestionPaperAnswerCollection questionColl = QuestionPaperAnswerFactory
					.getRemoteInstance().getQuestionPaperAnswerCollection(view);
			if (questionColl != null && questionColl.size() > 0) {
				FDCMsgBox.showWarning(this, "已有问卷，不能删除!");
				this.abort();
			}
		}
//		super.actionRemove_actionPerformed(e);
		if (confirmRemove()) {
			CluesManageFactory.getRemoteInstance().delete(new ObjectUuidPK(BOSUuid.read(this.editData.getId().toString())));
			this.actionAddNew_actionPerformed(e);
		}
	}

	protected FDCDataBaseInfo getEditData() {
		return editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CluesManageFactory.getRemoteInstance();
	}

	protected void tblTrack_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			IRow row = tblTrack.getRow(e.getRowIndex());
			Object idObj = row.getCell("id").getValue();
			if (idObj == null) {
				return;
			}
			String idStr = "";
			if (idObj instanceof String) {
				idStr = (String) idObj;
			} else if (idObj instanceof BOSUuid) {
				idStr = ((BOSUuid) idObj).toString();
			}
			if (!idStr.equals("")) {
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.ID, idStr);
				ObjectUuidPK pk = new ObjectUuidPK(idStr);
				IObjectValue objectValue = DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(), pk);
				String uiClassName = "";
				if (objectValue instanceof CommerceChanceTrackInfo) {
					uiClassName = "com.kingdee.eas.fdc.sellhouse.client.CluesManageTrakcEditUI";
					IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(uiClassName, uiContext, null, OprtState.VIEW);
					uiWindow.show();
				}
			}
			
			queryCluesTrack();
		}
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
		this.setOprtState(STATUS_VIEW);
		this.btnSubmit.setEnabled(false);
		this.btnEdit.setEnabled(true);
		isDirect = false;
	}

	protected void btnAddTrack_actionPerformed(ActionEvent e) throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("phone");
		sic.add("cluesStatus");
		sic.add("sellProject.*");
		sic.add("propertyConsultant.*");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", editData.getId()));
		view.setSelector(sic);
		view.setFilter(filter);

		SHECustomerInfo customerInfo = new SHECustomerInfo();
		if (editData.getCluesStatus() != null&& editData.getCluesStatus().equals(CluesStatusEnum.CUSTOMER)) {
			view = new EntityViewInfo();
			sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("number");
			sic.add("phone");
			sic.add("*");
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("clues.id", editData.getId()));
			view.setSelector(sic);
			view.setFilter(filter);
			customerInfo = (SHECustomerInfo) SHECustomerFactory.getRemoteInstance().getDataBaseCollection(view).get(0);
		}

		UIContext uiContext = new UIContext(this);
		uiContext.put("id", editData.getId());
		uiContext.put("number", this.txtNumber.getText());
		uiContext.put("cluesCustomer", editData);
		uiContext.put("sheCustomer", customerInfo);
		uiContext.put("sellProject", this.prmtSellProject.getValue());
		uiContext.put("propertyConsultant", this.prmtPropertyConsultant.getValue());
		//与直接新增客户一样
		uiContext.put("addnewDerict", "derict");

		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(CluesManageTrakcEditUI.class.getName(), uiContext, null, "ADDNEW");
		((Window)uiWindow).setAlwaysOnTop(true);
		uiWindow.show();
		
		queryCluesTrack();
	}
	public boolean destroyWindow() {
		if(isDirect){
			if(tblTrack.getRowCount()>0){
				if(!MsgBox.isYes(MsgBox.showConfirm2(this,"是否删除对应的跟进?"))){
					SysUtil.abort();
				}
				try {
					CommerceChanceTrackFactory.getRemoteInstance().delete("where clues.id = '"+editData.getId()+"'");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		else{
			this.getUIContext().put("commerceChance", editData.getId());
		}
		return super.destroyWindow();
	}
}