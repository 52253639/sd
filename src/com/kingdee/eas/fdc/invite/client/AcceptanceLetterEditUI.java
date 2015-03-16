/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.sql.RowSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.master.cssp.UsedStatusEnum;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.AcceptanceLetterFactory;
import com.kingdee.eas.fdc.invite.AcceptanceLetterInfo;
import com.kingdee.eas.fdc.invite.AppraiseResultCollection;
import com.kingdee.eas.fdc.invite.AppraiseResultEntryCollection;
import com.kingdee.eas.fdc.invite.AppraiseResultEntryInfo;
import com.kingdee.eas.fdc.invite.AppraiseResultFactory;
import com.kingdee.eas.fdc.invite.AppraiseResultInfo;
import com.kingdee.eas.fdc.invite.InviteFormEnum;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.client.offline.util.AttachmentPermissionUtil;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;


/**
 * @(#)			AcceptanceLetterEditUI			
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		中标通知书 编辑界面
 *		
 * @author		蒲磊		<p>
 * @createDate	2011-8-9	<p>	 
 * @version		EAS7.0		
 * @see					
 */
public class AcceptanceLetterEditUI extends AbstractAcceptanceLetterEditUI {
	/** */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = CoreUIObject
			.getLogger(AcceptanceLetterEditUI.class);
	private FullOrgUnitInfo orgUnitInfo = SysContext.getSysContext().getCurrentFIUnit().castToFullOrgUnitInfo();

	private AppraiseResultEntryCollection AppEntryCols = new AppraiseResultEntryCollection();
	private Set supplierIdSet = new HashSet();
	
	private final String inviteResourcePath = "com.kingdee.eas.fdc.invite.client.InviteResource";
	
	/**
	 * 是否修订，从ListUI传过来的参数
	 */
	private boolean isRevise = false; 

	/**
	 * output class constructor
	 */
	public AcceptanceLetterEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		this.editData.setDescription(this.txtDescription.getText());
		super.storeFields();
	}

	public void loadFields() {
		super.loadFields();
		if (this.editData != null) {
			if (this.editData.getDescription() != null) {
				this.txtDescription.setText(this.editData.getDescription());
			}
		}
	}
	protected String getTDFileName() {
		return "/bim/fdc/invite/AcceptanceLetter";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.invite.app.AcceptanceLetterForPrintQuery");
	}

	/**
	 * 描述：附件管理，除了在已审批的单据，其它的都可以上传，但只能删除自己上传的附件
	 * add by liuguangyue 2010-04-28
	 */
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		String boID = getSelectBOID();
		if (boID == null) {
			return;
		}
		boolean isEdit = false;
		if (STATUS_ADDNEW.equals(getOprtState())
				|| STATUS_EDIT.equals(getOprtState())) {
			isEdit = true;
		}

		// add liuguangyue 2010-4-28
		isEdit = AttachmentPermissionUtil.checkAuditingAttachmentEdit(editData.getState(), boID,isEdit);
		// modify liuguangyue 2010-04-27
		AttachmentUIContextInfo info = new AttachmentUIContextInfo();
		info.setBoID(boID);
		MultiApproveUtil.showAttachmentManager(info, this, editData, String.valueOf("1"), isEdit);

		// super.actionAttachment_actionPerformed(e);
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;
		}
		AcceptanceLetterDataProvider data = new AcceptanceLetterDataProvider(
				editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;

		}
		AcceptanceLetterDataProvider data = new AcceptanceLetterDataProvider(
				editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		if(editData.getSupplier() != null)
		prmtSupplier.setDataNoNotify(editData.getSupplier());
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {		
		super.actionEdit_actionPerformed(e);
		if(editData.getInviteForm().equals(InviteFormEnum.TENDERDISCUSSION) && !isHasAppraiseResult(editData.getInviteProject().getId().toString())){
				enableCtrl();
		}
		if(isRevise){
		   this.prmtInviteProject.setEnabled(false);
		   this.txtNumber.setEnabled(false);
		}
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);

		this.actionAddNew.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.actionSubmit.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionSave.setEnabled(false);

		this.actionAudit.setEnabled(false);
		this.actionAudit.setVisible(true);

		this.actionUnAudit.setEnabled(true);
		this.actionUnAudit.setVisible(true);
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);

		this.actionAddNew.setEnabled(true);
		this.actionEdit.setEnabled(true);
		this.actionSubmit.setEnabled(false);
		this.actionRemove.setEnabled(true);
		this.actionSave.setEnabled(false);

		this.actionAudit.setEnabled(true);
		this.actionAudit.setVisible(true);

		this.actionUnAudit.setEnabled(false);
		this.actionUnAudit.setVisible(true);
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected IObjectValue createNewData() {
		AcceptanceLetterInfo object = new AcceptanceLetterInfo();
		object.setState(FDCBillStateEnum.SAVED);
		object.setVersion(1.0);
		object.setOrgUnit(orgUnitInfo);
		object.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		Date createDate = new Date();
		object.setCreateTime(new Timestamp(createDate.getTime()));
		if(getUIContext().get("isRevise")!= null ){
			isRevise = Boolean.valueOf(getUIContext().get("isRevise").toString()).booleanValue();
			if(isRevise && getUIContext().get("vo") != null){
				object = (AcceptanceLetterInfo) getUIContext().get("vo");
			}
		}
		return object;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return AcceptanceLetterFactory.getRemoteInstance();
	}

	private boolean checkCanOperate() {
		boolean flag = true;

		String orgId = editData.getOrgUnit().getId().toString();
		if (orgUnitInfo.getId().toString().equals(orgId)) {
			flag = true;
		} else {
			flag = false;
		}

		return flag;
	}

	public void onLoad() throws Exception {
		super.onLoad();
        if(editData.getVersion()>1.0){
        	isRevise = true;
        }
		
		initHeadStyle();

		if (getOprtState().equals(OprtState.VIEW)) {
			if (!checkCanOperate()) {
				this.actionAddNew.setEnabled(false);
				this.actionEdit.setEnabled(false);
				this.actionSubmit.setEnabled(false);
				this.actionRemove.setEnabled(false);

				this.actionWorkFlowG.setEnabled(false);
				this.actionAuditResult.setEnabled(false);
			} else {
				if (editData.getState().equals(FDCBillStateEnum.SAVED)) {
					this.actionAddNew.setEnabled(true);
					this.actionEdit.setEnabled(true);
					this.actionSave.setEnabled(false);
					this.actionSubmit.setEnabled(false);
					this.actionRemove.setEnabled(true);
				} else if (editData.getState().equals(
						FDCBillStateEnum.SUBMITTED)) {
					this.actionAddNew.setEnabled(true);
					this.actionEdit.setEnabled(true);
					this.actionSave.setEnabled(false);
					this.actionSubmit.setEnabled(false);
					this.actionRemove.setEnabled(true);
				} else if (editData.getState()
						.equals(FDCBillStateEnum.AUDITTED)) {
					this.actionAddNew.setEnabled(false);
					this.actionEdit.setEnabled(false);
					this.actionSave.setEnabled(false);
					this.actionSubmit.setEnabled(false);
					this.actionRemove.setEnabled(false);
				} else {
					this.actionAddNew.setEnabled(false);
					this.actionEdit.setEnabled(false);
					this.actionSave.setEnabled(false);
					this.actionSubmit.setEnabled(false);
					this.actionRemove.setEnabled(false);
				}
			}

			this.actionWorkFlowG.setEnabled(true);
			this.actionAuditResult.setEnabled(true);
		} else if (getOprtState().equals(OprtState.EDIT)) {
			if (editData.getState().equals(FDCBillStateEnum.SUBMITTED)) {
				this.actionAddNew.setEnabled(true);
				this.actionEdit.setEnabled(false);
				this.actionSave.setEnabled(false);
				this.actionSubmit.setEnabled(true);
				this.actionRemove.setEnabled(true);
			}
			if(editData.getInviteProject() != null &&!isHasAppraiseResult(editData.getInviteProject().getId().toString())){
				enableCtrl();
			}			
		}

		this.actionAudit.setEnabled(false);
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.actionUnAudit.setEnabled(false);

		this.actionTraceDown.setEnabled(false);
		this.actionTraceDown.setVisible(false);
		
		this.actionPrintPreview.setEnabled(true);
		this.actionPrintPreview.setVisible(true);

		this.actionPrint.setEnabled(true);
		this.actionPrint.setVisible(true);		
		
		initSupplierF7(this.prmtHigherPriceUnit);
		initSupplierF7(this.prmtHighestPriceUnit);
		initSupplierF7(this.prmtMiddlePriceUnit);
		initSupplierF7(this.prmtLowerPriceUnit);
		initSupplierF7(this.prmtLowestPriceUnit);		
		
		this.txtInviteAmount.setRequired(false);
		if(isRevise){
			this.txtNumber.setEnabled(false);
			this.prmtInviteProject.setEnabled(false);
			this.prmtSupplier.setEnabled(false);
			this.txtInviteAmount.setEnabled(true);
			if(editData.getInviteProject().getInviteForm()!=null && editData.getInviteProject().getInviteForm().equals(InviteFormEnum.TENDERDISCUSSION))
			{
				this.txtQuantity.setEnabled(true);
			}
			
			if(getOprtState().equals(OprtState.VIEW)){
				contInviteProject.setEnabled(false);
			    prmtInviteProject.setEnabled(false);
				contInviteAmount.setEnabled(false);
			}
		}
		
		initSupplierF7();

		if (txtInviteAmount.isEnabled()) {
			txtInviteAmount.setRequired(true);
		}
	}

	/**
	 * 初始化供应商F7（中标单位）
	 */
	private void initSupplierF7() {
		prmtSupplier.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				logger.info(((KDBizPromptBox) e.getSource()).getEntityViewInfo());
				prmtSupplier.getQueryAgent().resetRuntimeEntityView();
				if (editData.getInviteProject() != null) {
					String projectId = editData.getInviteProject().getId().toString();
					Set supplierSet = getAppraiseResultSupplier(projectId);
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					view.setFilter(filter);
					if (supplierSet.size() > 0) {
						filter.getFilterItems().add(new FilterItemInfo("id", supplierSet, CompareType.INCLUDE));
					}
					filter.getFilterItems().add(new FilterItemInfo("usedStatus", String.valueOf(UsedStatusEnum.APPROVED.getValue())));
					prmtSupplier.setEntityViewInfo(view);
				}
			}
		});
		
		prmtSupplier.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				if (editData.getInviteProject() == null) {
					FDCMsgBox.showInfo(EASResource.getString(inviteResourcePath, "chooseInvitePrjFirst"));
					prmtSupplier.setDataNoNotify(null);
					return;
				}
				
				if (eventObj.getNewValue() != null) {
					SupplierInfo supplier = (SupplierInfo) eventObj.getNewValue();
						
					String projectId = editData.getInviteProject().getId().toString();
					FDCSQLBuilder builder = new FDCSQLBuilder();
					builder.appendSql("select are.FBidAmount, are.ftreatAmount from t_inv_appraiseresultentry are");
					builder.appendSql(" inner join t_inv_appraiseresult ar on are.fparentid = ar.fid ");
					builder.appendSql(" where ar.finviteprojectid = ? and are.fsupplierid = ?");
					builder.addParam(projectId);
					builder.addParam(supplier.getId().toString());
					try {
						RowSet rs = builder.executeQuery();
						while (rs.next()) {
							// 优先取谈判价，如果谈判价为空或为0,则取投标价
							if (rs.getBigDecimal("ftreatAmount") == null || FDCHelper.ZERO.compareTo(rs.getBigDecimal("ftreatAmount")) == 0) {
								txtInviteAmount.setValue(rs.getBigDecimal("FBidAmount"));
							} else
								txtInviteAmount.setValue(rs.getBigDecimal("ftreatAmount"));
						}
					} catch (BOSException e) {
						logger.info(e.getMessage());
					} catch (SQLException e) {
						logger.info(e.getMessage());
					}
				}
			}
		});
	}

	/**
	 * 获取已中标的评标结果报审分录中供应商的ID集
	 * 
	 * @author owen_wen 2011-04-21
	 * 
	 * @param projectId
	 *            招标立项ID
	 * @return
	 */
	private Set getAppraiseResultSupplier(String projectId){
		Set supplierSet = new HashSet();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("inviteproject.id",projectId));
		view.setFilter(filter);
		
		try {
			AppraiseResultCollection cols = AppraiseResultFactory.getRemoteInstance().getAppraiseResultCollection(view);
			if(cols.size()>0){
				AppraiseResultInfo info = cols.get(0);
				AppraiseResultEntryInfo entry = null;
				for(Iterator it = info.getEntrys().iterator();it.hasNext();){
					entry = (AppraiseResultEntryInfo) it.next();
					if (entry.isHit()) {
						supplierSet.add(entry.getSupplier().getId().toString());
					}
				}
			}			
		} catch (BOSException e1) {
			logger.debug(e1.getMessage(), e1);
			e1.printStackTrace();
		}
		return supplierSet;
	}
	
	
	protected boolean isShowAttachmentAction() {
		return true;
	}

	protected void initWorkButton() {
		super.initWorkButton();

		this.actionCopy.setEnabled(false);
		this.actionCopy.setVisible(false);
		
		this.actionPre.setEnabled(false);
		this.actionPre.setVisible(false);

		this.actionFirst.setEnabled(false);
		this.actionFirst.setVisible(false);
		this.actionNext.setEnabled(false);
		this.actionNext.setVisible(false);

		this.actionLast.setVisible(false);
		this.actionLast.setEnabled(false);

		this.actionCancel.setEnabled(false);
		this.actionCancel.setVisible(false);

		this.actionCancelCancel.setEnabled(false);
		this.actionCancelCancel.setVisible(false);

		this.actionTraceUp.setEnabled(false);
		this.actionTraceUp.setVisible(false);

		this.actionNextPerson.setEnabled(false);
		this.actionNextPerson.setVisible(false);
		this.actionCalculator.setEnabled(false);
		this.actionCalculator.setVisible(false);

		this.actionMultiapprove.setEnabled(false);
		this.actionMultiapprove.setVisible(false);
		this.actionCreateFrom.setEnabled(false);
		this.actionCreateFrom.setVisible(false);

		this.actionAuditResult.setEnabled(false);
		this.actionAuditResult.setVisible(false);

		//edit by liuguangyue 放开附件管理功能 2010-04-28
//		this.actionAttachment.setEnabled(false);
//		this.actionAttachment.setVisible(false);

		this.actionAddLine.setEnabled(false);
		this.actionAddLine.setVisible(false);

		this.actionInsertLine.setEnabled(false);
		this.actionInsertLine.setVisible(false);

		this.actionRemoveLine.setEnabled(false);
		this.actionRemoveLine.setVisible(false);
	}

	private void disableCtrl(){
		prmtSupplier.setEnabled(false);
		prmtHighestPriceUnit.setEnabled(false);
		prmtHigherPriceUnit.setEnabled(false);
		prmtLowestPriceUnit.setEnabled(false);
		prmtLowerPriceUnit.setEnabled(false);
		prmtMiddlePriceUnit.setEnabled(false);
		
		txtHigherPrice.setEnabled(false);
		txtHighestPrice.setEnabled(false);
		txtLowerPrice.setEnabled(false);
		txtLowestPrice.setEnabled(false);
		txtMiddlePrice.setEnabled(false);
		
		txtQuantity.setEnabled(false);
		txtInviteAmount.setEnabled(false);
	}
	
	private void enableCtrl(){
		prmtSupplier.setEnabled(true);
		prmtHighestPriceUnit.setEnabled(true);
		prmtHigherPriceUnit.setEnabled(true);
		prmtLowestPriceUnit.setEnabled(true);
		prmtLowerPriceUnit.setEnabled(true);
		prmtMiddlePriceUnit.setEnabled(true);
		
		txtHigherPrice.setEnabled(true);
		txtHighestPrice.setEnabled(true);
		txtLowerPrice.setEnabled(true);
		txtLowestPrice.setEnabled(true);
		txtMiddlePrice.setEnabled(true);
		
		txtQuantity.setEnabled(true);
		txtInviteAmount.setEnabled(true);
		txtInviteAmount.setRequired(true);		
	}
	
	protected void initHeadStyle() {
		this.txtNumber.setRequired(true);
		this.txtInviteAmount.setRequired(true);
		this.prmtSupplier.setRequired(true);
		
		//设置招标立项必录与查询语句
		this.prmtInviteProject.setRequired(true);
		this.prmtInviteProject.setDisplayFormat("$name$");
		this.prmtInviteProject.setEditFormat("$name$");
		this.prmtInviteProject.setCommitFormat("$name$");

		this.prmtInviteProject.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7InviteProjectQuery");

		prmtInviteProject.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				try {
					setInviteProjectFilter(e);
				} catch (BOSException e1) {
					e1.printStackTrace();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});

		prmtInviteProject.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				if (checkIsDataChange(eventObj))
					return;
				
				try {
					setInviteProjectRelationValues(eventObj);
					InviteProjectInfo info = (InviteProjectInfo) eventObj.getNewValue();
					if(info !=null){
//						clear();
						/*if(info.getInviteMode() != null ){
							prmtInviteMode.setData(info.getInviteMode());
						}*/
						if(info.getOrgUnit() != null){
							txtInviteProjectOrg.setText(info.getOrgUnit().getName());
						}
						//如果是议标状态并且没有做评标结果报审
//						if(info.getInviteForm()!= null && info.getInviteForm().equals(InviteFormEnum.TENDERDISCUSSION)){
//							enableCtrl();
//							clear();
//						}else{
//							disableCtrl();
//						}
					}else{
						prmtInviteMode.setDataNoNotify(null);
						txtInviteProjectOrg.setText(null,false);
					}
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}

			/**
			 * 新的招标立项与旧的招标立项如果相同直接退出，不用处理
			 * 
			 * @param e
			 * @return
			 */
			private boolean checkIsDataChange(DataChangeEvent e) {
				if (e.getOldValue() == null)
					return false;

				if (e.getNewValue() != null) {
					InviteProjectInfo invitePrjInfo1 = (InviteProjectInfo) e.getNewValue();
					InviteProjectInfo invitePrjInfo2 = (InviteProjectInfo) e.getOldValue();

					return (invitePrjInfo1.getId().toString().equals(invitePrjInfo2.getId().toString()));
				}

				return false;
			}
		});

		txtInviteAmount.setPrecision(2);
		txtInviteAmount.setNegatived(false);
		txtInviteAmount.setSupportedEmpty(false);

		this.prmtSupplier.setDisplayFormat("$name$");
		this.prmtSupplier.setEditFormat("$name$");
		this.prmtSupplier.setCommitFormat("$name$");
		this.prmtSupplier.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7SupplierDefaultQuery");
		this.prmtHighestPriceUnit.setDisplayFormat("$name$");
		this.prmtHigherPriceUnit.setDisplayFormat("$name$");
		this.prmtMiddlePriceUnit.setDisplayFormat("$name$");
		this.prmtLowerPriceUnit.setDisplayFormat("$name$");
		this.prmtLowestPriceUnit.setDisplayFormat("$name$");

		this.txtDescription.setMaxLength(200);
		this.txtNumber.setMaxLength(80);

		this.txtQuantity.setRemoveingZeroInDispaly(true);
		this.txtQuantity.setPrecision(0);		
		
		//add by david_yang PT043562 2011.04.02 (扩充name到255个字符)
		this.txtNumber.setMaxLength(255);
	}
    private void clear(){
    	prmtSupplier.setData(null);
		prmtHighestPriceUnit.setData(null);
		prmtHigherPriceUnit.setData(null);
		prmtLowestPriceUnit.setData(null);
		prmtLowerPriceUnit.setData(null);
		prmtMiddlePriceUnit.setData(null);
		
		txtHigherPrice.setText(null);
		txtHighestPrice.setText(null);
		txtLowerPrice.setText(null);
		txtLowestPrice.setText(null);
		txtMiddlePrice.setText(null);
		
		txtQuantity.setText(null);
		txtInviteAmount.setText(null);
    }
	
	private void setBidSupplierFilter(SelectorEvent e) throws Exception {
		EntityViewInfo view = new EntityViewInfo();

		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("number"));
		view.getSelector().add(new SelectorItemInfo("name"));
		view.getSelector().add(new SelectorItemInfo("simpleName"));

		view.getSelector().add(new SelectorItemInfo("industry.name"));
		view.getSelector().add(new SelectorItemInfo("address"));
		view.getSelector().add(new SelectorItemInfo("isInternalCompany"));

		view.getSelector().add(new SelectorItemInfo("taxRegisterNo"));
		view.getSelector().add(new SelectorItemInfo("bizRegisterNo"));
		view.getSelector().add(new SelectorItemInfo("artificialPerson"));

		view.getSelector().add(new SelectorItemInfo("usedStatus"));
		view.getSelector().add(new SelectorItemInfo("effectedStatus"));
		view.getSelector().add(new SelectorItemInfo("description"));

		view.getSelector().add(new SelectorItemInfo("internalCompany.number"));
		view.getSelector().add(new SelectorItemInfo("internalCompany.name"));

		prmtSupplier.setSelectorCollection(getBidSupplierSelector());

		Set preHitSupId = getPreHitSupplier();

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", preHitSupId, CompareType.INCLUDE));

		view.setFilter(filter);
		prmtSupplier.setEntityViewInfo(view);

		if (prmtSupplier.getSelector() != null
				&& prmtSupplier.getSelector() instanceof com.kingdee.eas.framework.client.ListUI) {
			((com.kingdee.eas.framework.client.ListUI) prmtSupplier
					.getSelector()).setFilterForQuery(filter);
			((com.kingdee.eas.framework.client.ListUI) prmtSupplier
					.getSelector()).onLoad();

		} else {
			if (filter.getFilterItems().size() <= 0) {
				return;
			}
			prmtSupplier.getEntityViewInfo().setFilter(filter);
			prmtSupplier.getQueryAgent().resetRuntimeEntityView();
			prmtSupplier.setRefresh(true);
		}
	}

	private Set getPreHitSupplier() throws BOSException {
		Set supIds = new HashSet();

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("inviteProject"));
		view.getSelector().add(new SelectorItemInfo("orgUnit"));
		view.getSelector().add(new SelectorItemInfo("entrys.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.supplier.*"));

		if (prmtInviteProject.getValue() != null) {
			supIds = supplierIdSet;
			return supIds;
		} else {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("orgUnit", orgUnitInfo.getId()
							.toString()));
			view.setFilter(filter);
		}

		AppraiseResultCollection cols = AppraiseResultFactory
				.getRemoteInstance().getAppraiseResultCollection(view);

		AppEntryCols.clear();

		Iterator colsIter = cols.iterator();
		while (colsIter.hasNext()) {
			AppraiseResultInfo tmpAppInfo = (AppraiseResultInfo) colsIter
					.next();
			AppraiseResultEntryCollection entryCols = tmpAppInfo.getEntrys();
			Iterator entryColsIter = entryCols.iterator();

			while (entryColsIter.hasNext()) {
				AppraiseResultEntryInfo entryInfo = (AppraiseResultEntryInfo) entryColsIter
						.next();

				if (entryInfo.isPreHit() || entryInfo.isHit()) {
					supIds.add(entryInfo.getSupplier().getId().toString());
					AppEntryCols.add(entryInfo);
				}
			}
		}

		return supIds;
	}

	protected SelectorItemCollection getBidSupplierSelector() {
		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("simpleName"));

		sic.add(new SelectorItemInfo("industry.name"));
		sic.add(new SelectorItemInfo("address"));
		sic.add(new SelectorItemInfo("isInternalCompany"));

		sic.add(new SelectorItemInfo("taxRegisterNo"));
		sic.add(new SelectorItemInfo("bizRegisterNo"));
		sic.add(new SelectorItemInfo("artificialPerson"));

		sic.add(new SelectorItemInfo("usedStatus"));
		sic.add(new SelectorItemInfo("effectedStatus"));
		sic.add(new SelectorItemInfo("description"));

		sic.add(new SelectorItemInfo("internalCompany.number"));
		sic.add(new SelectorItemInfo("internalCompany.name"));

		return sic;
	}

	private boolean isHasAppraiseResult(String inviteProjectId){
		boolean isHasAppraiseResult = false;
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("inviteProject"));
		view.getSelector().add(new SelectorItemInfo("entrys.*"));
		view.getSelector().add(new SelectorItemInfo("entrys.supplier.*"));
		view.getSelector().add(new SelectorItemInfo("state"));

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("inviteProject", inviteProjectId));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));

		view.setFilter(filter);
		// 取出评标结果报审的相关记录
		try {
			AppraiseResultCollection resultCols = AppraiseResultFactory.getRemoteInstance().getAppraiseResultCollection(view);
			if(resultCols.size() > 0 ){
				isHasAppraiseResult = true;
			}
		} catch (BOSException e) {
			logger.debug(e.getMessage(), e);
			e.printStackTrace();
		}
		return isHasAppraiseResult;
	}

	/**
	 * 设置与招标立项相关的
	 * 
	 * @param e
	 * @throws BOSException
	 */
	private void setInviteProjectRelationValues(DataChangeEvent e) throws BOSException {
		if (e.getNewValue() != null	&& e.getNewValue() instanceof InviteProjectInfo) {
			AppEntryCols.clear();
			// 更新可选中标单位集合
			InviteProjectInfo projectInfo = (InviteProjectInfo) e.getNewValue();
			editData.setInviteProject(projectInfo);			
			this.comboInviteForm.setSelectedItem(projectInfo.getInviteForm());
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add(new SelectorItemInfo("id"));
			view.getSelector().add(new SelectorItemInfo("inviteProject"));
			view.getSelector().add(new SelectorItemInfo("entrys.*"));
			view.getSelector().add(new SelectorItemInfo("entrys.supplier.*"));
			view.getSelector().add(new SelectorItemInfo("state"));

			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("inviteProject", projectInfo.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));

			view.setFilter(filter);
			// 取出评标结果报审的相关记录
			AppraiseResultCollection resultCols = AppraiseResultFactory.getRemoteInstance().getAppraiseResultCollection(view);
			if (resultCols.size() == 1) {
				AppraiseResultInfo resultInfo = (AppraiseResultInfo) resultCols.get(0);
				AppraiseResultEntryCollection entryCols = resultInfo.getEntrys();
				Iterator entryColsIter = entryCols.iterator();
				while (entryColsIter.hasNext()) {
					AppraiseResultEntryInfo entryInfo = (AppraiseResultEntryInfo) entryColsIter.next();
					if (entryInfo.isHit()) {
						prmtSupplier.setDataNoNotify(entryInfo.getSupplier());
						// 优先取谈判价
						if (entryInfo.getTreatAmount() == null 
								|| FDCHelper.ZERO.compareTo(entryInfo.getTreatAmount()) == 0)
							txtInviteAmount.setValue(entryInfo.getBidAmount());
						else
							txtInviteAmount.setValue(entryInfo.getTreatAmount());
						break;
					}
				}

				this.txtQuantity.setValue(new Integer(entryCols.size()));
				//设置供应商信息
				setInviteSupplierInformation(resultInfo);
				if(projectInfo.getInviteForm().equals(InviteFormEnum.TENDERDISCUSSION)){
					disableCtrl();
				}
			}//议标状态并没做报价结果评审
			else if(resultCols.size()==0 && projectInfo.getInviteForm().equals(InviteFormEnum.TENDERDISCUSSION) ){
				enableCtrl();
				clear();
			}

		} else {
			this.txtQuantity.setValue(null);
			setInviteSupplierAndBidAmount(null, prmtHighestPriceUnit,txtHighestPrice);
			setInviteSupplierAndBidAmount(null, prmtHigherPriceUnit,txtHigherPrice);
			setInviteSupplierAndBidAmount(null, prmtMiddlePriceUnit,txtMiddlePrice);
			setInviteSupplierAndBidAmount(null, prmtLowerPriceUnit,txtLowerPrice);
			setInviteSupplierAndBidAmount(null, prmtLowestPriceUnit,txtLowestPrice);
			this.comboInviteForm.setSelectedItem(null);
			AppEntryCols.clear();
			supplierIdSet.clear();
		}
	}

	private AppraiseResultEntryInfo getHighestPriceUnit(Object[] paramEntryCols) {
		if(paramEntryCols.length==0)
			return null;
		AppraiseResultEntryInfo tmpUnit = (AppraiseResultEntryInfo) paramEntryCols[paramEntryCols.length - 1];
		return tmpUnit;
	}

	private AppraiseResultEntryInfo getHigherPriceUnit(Object[] paramEntryCols) {
		AppraiseResultEntryInfo tmpUnit = null;
		if (paramEntryCols.length==0)
			return null;
		if (paramEntryCols.length > 1) {
			tmpUnit = (AppraiseResultEntryInfo) paramEntryCols[paramEntryCols.length - 1 - 1];
		} else {
			tmpUnit = (AppraiseResultEntryInfo) paramEntryCols[0];
		}

		return tmpUnit;
	}

	private AppraiseResultEntryInfo getMiddlePriceUnit(Object[] paramEntryCols) {
		if (paramEntryCols.length==0)
			return null;
		AppraiseResultEntryInfo tmpUnit = null;
		if (paramEntryCols.length % 2 == 0) {
			tmpUnit = (AppraiseResultEntryInfo) paramEntryCols[paramEntryCols.length / 2 - 1];
		} else {
			tmpUnit = (AppraiseResultEntryInfo) paramEntryCols[(paramEntryCols.length + 1) / 2 - 1];
		}
		return tmpUnit;
	}

	private AppraiseResultEntryInfo getLowerPriceUnit(Object[] paramEntryCols) {
		if (paramEntryCols.length==0)
			return null;
		AppraiseResultEntryInfo tmpUnit = null;
		if (paramEntryCols.length > 1) {
			tmpUnit = (AppraiseResultEntryInfo) paramEntryCols[1];
		} else {
			tmpUnit = (AppraiseResultEntryInfo) paramEntryCols[0];
		}

		return tmpUnit;
	}

	private AppraiseResultEntryInfo getLowestPriceUnit(Object[] paramEntryCols) {
		if (paramEntryCols.length==0)
			return null;
		AppraiseResultEntryInfo tmpUnit = (AppraiseResultEntryInfo) paramEntryCols[0];
		return tmpUnit;
	}

	private void setInviteSupplierInformation(
			AppraiseResultInfo paramAppResultInfo) {
		AppraiseResultEntryCollection entryCols = paramAppResultInfo
				.getEntrys();
		if (entryCols.size() > 0) {
			Set tmpSet = new HashSet();
			for (Iterator iter = entryCols.iterator(); iter.hasNext();) {
				AppraiseResultEntryInfo entryInfo = (AppraiseResultEntryInfo) iter
						.next();
				if (entryInfo.getTotalSeq() != 0) {
					tmpSet.add(entryInfo);
				}
			}
			Object[] array = tmpSet.toArray();
			// 排序
			for (int i = 1; i < array.length; ++i) {
				int j = i - 1;
				AppraiseResultEntryInfo tmpI = (AppraiseResultEntryInfo) array[i];
				while (j >= 0
						&& (tmpI.getTotalSeq() < ((AppraiseResultEntryInfo) array[j])
								.getTotalSeq())) {
					array[j + 1] = array[j];
					j = j - 1;
				}

				array[j + 1] = tmpI;
			}

			// 获取最高投标价
			AppraiseResultEntryInfo highestPriceUnit = getHighestPriceUnit(array);
			setInviteSupplierAndBidAmount(highestPriceUnit, prmtHighestPriceUnit, txtHighestPrice);

			// 获取次高投标价
			AppraiseResultEntryInfo higherPriceUnit = getHigherPriceUnit(array);
			setInviteSupplierAndBidAmount(higherPriceUnit, prmtHigherPriceUnit, txtHigherPrice);

			// 获取中间投标价
			AppraiseResultEntryInfo middlePriceUnit = getMiddlePriceUnit(array);
			setInviteSupplierAndBidAmount(middlePriceUnit, prmtMiddlePriceUnit, txtMiddlePrice);

			// 获取次低投标价
			AppraiseResultEntryInfo lowerPriceUnit = getLowerPriceUnit(array);
			setInviteSupplierAndBidAmount(lowerPriceUnit, prmtLowerPriceUnit, txtLowerPrice);

			// 获取最低投标价
			AppraiseResultEntryInfo lowestPriceUnit = getLowestPriceUnit(array);
			setInviteSupplierAndBidAmount(lowestPriceUnit, prmtLowestPriceUnit, txtLowestPrice);
		}	
	}

	private void setInviteSupplierAndBidAmount(Object paramObj,
			KDBizPromptBox paramPrmt, KDFormattedTextField paramAmount) {
		if (paramObj != null) {
			AppraiseResultEntryInfo entryInfo = (AppraiseResultEntryInfo) paramObj;
			paramPrmt.setValue(entryInfo.getSupplier());
			/***
			 * 优先取谈判价
			 */
			if (entryInfo.getTreatAmount() == null)
				paramAmount.setValue(entryInfo.getBidAmount());
			else
				paramAmount.setValue(entryInfo.getTreatAmount());
		} else {
			paramPrmt.setValue(null);
			paramAmount.setValue(null);
		}
	}

	private void initSupplierF7(KDBizPromptBox f7){
		FDCClientUtils.initSupplierF7(this, f7);
	}
	
	private void setInviteProjectFilter(SelectorEvent e) throws Exception {
		EntityViewInfo view = new EntityViewInfo();

		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("orgUnit.id"));
		view.getSelector().add(new SelectorItemInfo("orgUnit.longNumber"));

		view.getSelector().add(new SelectorItemInfo("inviteType.id"));
		view.getSelector().add(new SelectorItemInfo("inviteType.longNumber"));

		view.getSelector().add(new SelectorItemInfo("number"));
		view.getSelector().add(new SelectorItemInfo("name"));

		view.getSelector().add(new SelectorItemInfo("project.id"));
		view.getSelector().add(new SelectorItemInfo("project.number"));
		view.getSelector().add(new SelectorItemInfo("project.name"));

		view.getSelector().add(new SelectorItemInfo("inviteForm"));
		view.getSelector().add(new SelectorItemInfo("respPerson.number"));
		view.getSelector().add(new SelectorItemInfo("prjDate"));

		view.getSelector().add(new SelectorItemInfo("inviteType.number"));
		view.getSelector().add(new SelectorItemInfo("inviteType.name"));
		view.getSelector().add(new SelectorItemInfo("inviteType.longNumber"));

		view.getSelector().add(new SelectorItemInfo("inviteProjectState"));
		view.getSelector().add(new SelectorItemInfo("state"));

		view.getSelector().add(new SelectorItemInfo("createTime"));
		view.getSelector().add(new SelectorItemInfo("creator.name"));
		view.getSelector().add(new SelectorItemInfo("auditTime"));
		view.getSelector().add(new SelectorItemInfo("auditor.name"));
		

		FilterInfo filter = new FilterInfo();
		//获取当前财务组织
		FullOrgUnitInfo orgUnitInfo = null;
		if (null != SysContext.getSysContext().getCurrentFIUnit()) {
			orgUnitInfo = SysContext.getSysContext().getCurrentFIUnit().castToFullOrgUnitInfo();
		}
		
		// 根据所选的组织架构过滤招标立项
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",orgUnitInfo.getId().toString()));
		
		Set idSet = getAppraiseResultedInviteProject();
		if(idSet.size()>0){
			filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE, CompareType.EQUALS));
		}
		//过滤状态为“已签合同”、“已定标”的招标立项
		filter.getFilterItems().add(new FilterItemInfo("inviteProjectState", "8FIXED",CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("inviteProjectState", "9SIGNEDCONTRACT",CompareType.NOTEQUALS));
		
		/** R111121-0371 对于议标类型的招标立项，无需走招标文件编制及评标定标过程，给客户规划的方案是直接做了议标形式的招标立项后就直接做中标通知书*/
		FilterInfo tenderDiscussionFilter = new FilterInfo();
		tenderDiscussionFilter.getFilterItems().add(new FilterItemInfo("inviteForm", InviteFormEnum.TENDERDISCUSSION_VALUE));
		tenderDiscussionFilter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		tenderDiscussionFilter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE, CompareType.EQUALS));
		tenderDiscussionFilter.getFilterItems().add(new FilterItemInfo("orgUnit.id", orgUnitInfo.getId().toString()));
		filter.mergeFilter(tenderDiscussionFilter, "or");
        view.setFilter(filter);
		prmtInviteProject.setEntityViewInfo(view);
		prmtInviteProject.setSelectorCollection(getInviteProjectSelector());

		if (prmtInviteProject.getSelector() != null
				&& prmtInviteProject.getSelector() instanceof com.kingdee.eas.framework.client.ListUI) {
			((com.kingdee.eas.framework.client.ListUI) prmtInviteProject
					.getSelector()).setFilterForQuery(filter);
			((com.kingdee.eas.framework.client.ListUI) prmtInviteProject
					.getSelector()).onLoad();

		} else {
			prmtInviteProject.getQueryAgent().resetRuntimeEntityView();
			prmtInviteProject.getEntityViewInfo().setFilter(filter);
			prmtInviteProject.setRefresh(true);
		}
	}

	private SelectorItemCollection getInviteProjectSelector() {
		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("orgUnit.id"));
		sic.add(new SelectorItemInfo("orgUnit.longNumber"));
		sic.add(new SelectorItemInfo("inviteType.id"));

		sic.add(new SelectorItemInfo("inviteType.longNumber"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("project.id"));

		sic.add(new SelectorItemInfo("project.number"));
		sic.add(new SelectorItemInfo("project.name"));
		sic.add(new SelectorItemInfo("inviteForm"));
		sic.add(new SelectorItemInfo("respPerson.number"));

		sic.add(new SelectorItemInfo("prjDate"));
		sic.add(new SelectorItemInfo("inviteType.number"));
		sic.add(new SelectorItemInfo("inviteType.name"));
		sic.add(new SelectorItemInfo("inviteType.longNumber"));

		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("creator.name"));
		sic.add(new SelectorItemInfo("auditTime"));
		sic.add(new SelectorItemInfo("auditor.name"));
		sic.add(new SelectorItemInfo("inviteMode.*"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		return sic;
	}

	/**
	 * description		获取评标结果关联的招标立项IDSet
	 * @author			蒲磊<p>	
	 * @createDate		2011-8-10<p>
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see							
	 */
	private Set getAppraiseResultedInviteProject() throws BOSException {
		Set idSet = new HashSet();

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("inviteProject"));
		view.getSelector().add(new SelectorItemInfo("state"));
		view.getSelector().add(new SelectorItemInfo("orgUnit"));

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("orgUnit", this.orgUnitInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
//		FilterInfo  tenderDiscussionFilter = new FilterInfo();
//		tenderDiscussionFilter.getFilterItems().add(new FilterItemInfo("inviteProject.inviteForm",InviteFormEnum.TENDERDISCUSSION_VALUE));
//		tenderDiscussionFilter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
//        filter.mergeFilter(tenderDiscussionFilter, "or");
		view.setFilter(filter);

		AppraiseResultCollection cols = AppraiseResultFactory
				.getRemoteInstance().getAppraiseResultCollection(view);
		Iterator iter = cols.iterator();
		while (iter.hasNext()) {
			AppraiseResultInfo resultInfo = (AppraiseResultInfo) iter.next();
			idSet.add(resultInfo.getInviteProject().getId().toString());
		}

		return idSet;
	}

	protected void attachListeners() {
	}

	protected void detachListeners() {
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected void verifyInputForSave() throws Exception {
		super.verifyInputForSave();
		ICodingRuleManager codingManager = getEncodingRule();

		if (this.editData != null) {

			boolean isUseCodingRule = codingManager.isExist(this.editData,
					SysContext.getSysContext().getCurrentOrgUnit().getId()
							.toString());
			if (!isUseCodingRule) {
				if (StringUtils.isEmpty(editData.getNumber())) {
					FDCMsgBox.showWarning(this, "单据编码不能为空");
					abort();
				}

				if (editData.getNumber().trim().length() > 80) {
					FDCMsgBox.showWarning(this, "单据编码长度不能超过80字符");
					abort();
				}
			}
		}

		if (editData.getInviteProject() == null) {
			FDCMsgBox.showWarning(this, "立项名称不能为空");
			abort();
		}

		if (!StringUtils.isEmpty(editData.getDescription())) {
			if (editData.getDescription().trim().length() > 200) {
				FDCMsgBox.showWarning(this, "备注长度不能超过200字符");
				abort();
			}
		}

		this.editData.setName(editData.getInviteProject().getName());				
	}

	public ICodingRuleManager getEncodingRule() {

		ICodingRuleManager codingRuleManager = null;
		try {
			if (this.getUIContext().get("codingRuleManager") == null) {
				codingRuleManager = CodingRuleManagerFactory
						.getRemoteInstance();
				this.getUIContext().put("codingRuleManager", codingRuleManager);
			} else {

				codingRuleManager = (ICodingRuleManager) this.getUIContext()
						.get("codingRuleManager");
			}

		} catch (BOSException e) {
			logger.debug(e.getMessage(), e);
			e.printStackTrace();
		}
		return codingRuleManager;
	}

	protected void verifyInputForSubmint() throws Exception {
		super.verifyInputForSubmint();

		ICodingRuleManager codingManager = getEncodingRule();

		if (this.editData != null) {
			boolean isUseCodingRule = codingManager.isExist(this.editData,
					SysContext.getSysContext().getCurrentOrgUnit().getId()
							.toString());
			if (!isUseCodingRule) {
				if (StringUtils.isEmpty(editData.getNumber())) {
					FDCMsgBox.showWarning(this, "单据编码不能为空");
					abort();
				}

				if (editData.getNumber().trim().length() > 80) {
					FDCMsgBox.showWarning(this, "单据编码长度不能超过80字符");
					abort();
				}

			}
		}

		// if(StringUtils.isEmpty(editData.getNumber())){
		// FDCMsgBox.showWarning(this,"单据编码不能为空");
		// abort();
		// }

		if (editData.getInviteProject() == null) {
			FDCMsgBox.showWarning(this, "立项名称不能为空");
			abort();
		}
		
		if (editData.getSupplier() == null) {
			FDCMsgBox.showWarning(this, "中标单位不能为空");
			abort();
		}

		if(editData.getInviteAmount() == null){
			FDCMsgBox.showWarning(this, "中标金额不能为空！");
			abort();
		}
		
		if (!StringUtils.isEmpty(editData.getDescription())) {
			if (editData.getDescription().trim().length() > 200) {
				FDCMsgBox.showWarning(this, "备注长度不能超过200字符");
				abort();
			}
		}

		// 一个招标立项只能有一个中标通知书
		if (!isRevise) { // 只有不是修订时才校验，因为修订只能修改中标价格
			checkIsExist();
		}
		
		//考虑到测试直接提交
		if (this.editData.getName() == null
				|| this.editData.getName().equals("")) {
			this.editData.setName(this.editData.getInviteProject().getName());
		}
	}

	/**
	 * 获取所选的当前组织 可以不用通过传递获取，通过获取系统上下文中的当前财务组织
	 * 
	 * @return
	 */
	private OrgStructureInfo getSelectOrgUnit() {
		OrgStructureInfo orgUnitInfo = null;
		if (getUIContext().get("ORG_UNIT") != null
				&& getUIContext().get("ORG_UNIT") instanceof OrgStructureInfo) {
			orgUnitInfo = (OrgStructureInfo) (getUIContext().get("ORG_UNIT"));
		}

		return orgUnitInfo;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("orgUnit"));
		sic.add(new SelectorItemInfo("inviteProject.inviteMode.*"));
		sic.add(new SelectorItemInfo("inviteProject.orgUnit.*"));
		sic.add(new SelectorItemInfo("version"));
		sic.add(new SelectorItemInfo("supplier.name"));
		sic.add(new SelectorItemInfo("supplier.number"));
		sic.add(new SelectorItemInfo("supplier.id"));
		sic.add(new SelectorItemInfo("isLastVersion"));
		return sic;
	}

	/**
	 * 检查是否已经存在中标通知书，一个招标立项有几个中标单位就有几份中标通知书，不能重复提交
	 * 
	 * @author owen_wen 2011-04-19
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private void checkIsExist() throws EASBizException, BOSException {
		String inviteProjectID = editData.getInviteProject().getId().toString();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("inviteProject", inviteProjectID));
		filter.getFilterItems().add(new FilterItemInfo("supplier", editData.getSupplier().getId()));
		if (editData.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString(), CompareType.NOTEQUALS));
		}
		if (AcceptanceLetterFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showWarning("该招标立项下的供应商已经存在中标通知书，不能执行此操作。");
			this.abort();
		}
	}
}