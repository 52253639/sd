/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.swing.filechooser.FileFilter;

import org.apache.log4j.Logger;
import org.apache.poi1.hwpf.extractor.WordExtractor;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.attachment.util.Resrcs;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.invite.InviteContentCollection;
import com.kingdee.eas.fdc.invite.InviteContentFactory;
import com.kingdee.eas.fdc.invite.InviteContentInfo;
import com.kingdee.eas.fdc.contract.ContractContentCollection;
import com.kingdee.eas.fdc.contract.ContractContentFactory;
import com.kingdee.eas.fdc.contract.ContractContentInfo;
import com.kingdee.eas.fdc.contract.ContractModelInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.ForWriteMarkHelper;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.contract.client.ContractContentUI;
import com.kingdee.eas.fdc.invite.BaseInviteEntryInfo;
import com.kingdee.eas.fdc.invite.BaseInviteInfo;
import com.kingdee.eas.fdc.invite.InviteBaseFileInfo;
import com.kingdee.eas.fdc.invite.InviteClarifyEntryFactory;
import com.kingdee.eas.fdc.invite.InviteDocumentsFactory;
import com.kingdee.eas.fdc.invite.InviteDocumentsInfo;
import com.kingdee.eas.fdc.invite.InviteDocumentsPointsInfo;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.InviteTenderPlanningCollection;
import com.kingdee.eas.fdc.invite.InviteTenderPlanningFactory;
import com.kingdee.eas.fdc.invite.InviteTenderPlanningInfo;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.client.InviteBaseFileEditUI.FileModifyThread;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.framework.CoreBillEntryBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

public class InviteDocumentsEditUI extends AbstractInviteDocumentsEditUI
{
	private static final Logger logger = CoreUIObject.getLogger(InviteDocumentsEditUI.class);
	private  FileGetter fileGetter;
	private  FileGetter getFileGetter() throws Exception {
        if (fileGetter == null)
            fileGetter = new FileGetter((IAttachment) AttachmentFactory.getRemoteInstance(), AttachmentFtpFacadeFactory.getRemoteInstance());
        return fileGetter;
    }
	
	protected void verifyInputForSave() throws Exception {
		super.verifyInputForSave();
		FDCClientVerifyHelper.verifyEmpty(this, this.txtScope);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtPay);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtDiff);
	}
	
	public InviteDocumentsEditUI() throws Exception {
		super();
	}
	
    public void storeFields()
	{
		super.storeFields();
	}
	public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		loadOther();
		
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
		
		this.prmtInviteBaseFile.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		if(this.editData.getInviteBaseFile()!=null){
			this.prmtInviteBaseFile.setEnabled(false);
		}else{
			this.prmtInviteBaseFile.setEnabled(true);
		}
		this.prmtContractModel.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		if(this.editData.getContractModel()!=null){
			this.prmtContractModel.setEnabled(false);
		}else{
			this.prmtContractModel.setEnabled(true);
		}
	}
	
	protected void loadOther() {
//		super.loadOther();
		try {
			fillAttachmnetTable();
			
//			loadBaseFile();
			
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
	protected void initControl() {
		super.initControl();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		InviteTypeInfo type = this.editData.getInviteProject().getInviteType();
		if(type !=null){
			view.getFilter().getFilterItems().add(new FilterItemInfo("inviteType.id",type.getId().toString()));
		}
		view.getFilter().getFilterItems().add(new FilterItemInfo("isLastVer",Boolean.TRUE));
		this.prmtInviteBaseFile.setEntityViewInfo(view);
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		view.setFilter(filter);
		
		view.getFilter().getFilterItems().add(new FilterItemInfo("isLastVer",Boolean.TRUE));
		prmtContractModel.setEntityViewInfo(view);
		
//		if( this.editData.getState() == FDCBillStateEnum.AUDITTED) {
//			prmtInviteBaseFile.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
//			prmtContractModel.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
//			prmtInviteBaseFile.setEnabled(false);
//			prmtContractModel.setEnabled(false);
//			btnAgreementText.setEnabled(false);
//			btnAttach.setEnabled(false);
//		}
		
		this.actionPrintPreview.setVisible(true);
		this.actionPrintPreview.setEnabled(true);
		
		this.actionPrint.setVisible(true);
		this.actionPrint.setEnabled(true);
		
		this.btnAttachment.setVisible(false);
		KDWorkButton btnInviteUpLoad = new KDWorkButton();
		KDWorkButton btnUpLoad = new KDWorkButton();
		KDWorkButton btnAgreementText = new KDWorkButton();
		KDWorkButton btnAttachment = new KDWorkButton();

		this.actionInviteUpLoad.putValue("SmallIcon", EASResource.getIcon("imgTbtn_affixmanage"));
		btnInviteUpLoad = (KDWorkButton)this.contEntry.add(this.actionInviteUpLoad);
		btnInviteUpLoad.setText("上传招标文件");
		btnInviteUpLoad.setSize(new Dimension(140, 19));
		
		this.actionUpLoad.putValue("SmallIcon", EASResource.getIcon("imgTbtn_affixmanage"));
		btnUpLoad = (KDWorkButton)this.contEntry.add(this.actionUpLoad);
		btnUpLoad.setText("上传标准合同");
		btnUpLoad.setSize(new Dimension(140, 19));
		
		this.actionAgreementText.putValue("SmallIcon", EASResource.getIcon("imgTbtn_affixmanage"));
		btnAgreementText = (KDWorkButton)this.contEntry.add(this.actionAgreementText);
		btnAgreementText.setText("上传补充条款");
		btnAgreementText.setSize(new Dimension(140, 19));

		this.actionAttachment.putValue("SmallIcon", EASResource.getIcon("imgTbtn_affixmanage"));
		btnAttachment = (KDWorkButton) this.contEntry.add(this.actionAttachment);
		btnAttachment.setText("附件管理");
		btnAttachment.setSize(new Dimension(140, 19));
		
		KDWorkButton btnInviteDownLoad = new KDWorkButton();
		this.actionInviteDownLoad.putValue("SmallIcon", EASResource.getIcon("imgTbtn_affixmanage"));
		btnInviteDownLoad = (KDWorkButton) this.contInviteBaseFile.add(this.actionInviteDownLoad);
		btnInviteDownLoad.setText("下载招标文件");
		btnInviteDownLoad.setSize(new Dimension(140, 19));
		
		KDWorkButton btnDownLoad = new KDWorkButton();
		this.actionDownLoad.putValue("SmallIcon", EASResource.getIcon("imgTbtn_affixmanage"));
		btnDownLoad = (KDWorkButton) this.contContractModel.add(this.actionDownLoad);
		btnDownLoad.setText("下载标准合同范本");
		btnDownLoad.setSize(new Dimension(140, 19));
	}

	protected ICoreBase getBizInterface() throws Exception {
		return InviteDocumentsFactory.getRemoteInstance();
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();;
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		
		sic.add(new SelectorItemInfo("entry.*"));
		
		sic.add(new SelectorItemInfo("inviteProject.inviteType.*"));
		sic.add(new SelectorItemInfo("inviteProject.programmingContract.*"));
		sic.add(new SelectorItemInfo("inviteProject.project.*"));
		
		sic.add(new SelectorItemInfo("inviteProject.*"));
		
		sic.add(new SelectorItemInfo("agreementID"));
		
		return sic;
	}
	protected void attachListeners() {
		addDataChangeListener(this.prmtInviteBaseFile);
		addDataChangeListener(this.prmtContractModel);
	}

	protected void detachListeners() {
		removeDataChangeListener(this.prmtInviteBaseFile);
		removeDataChangeListener(this.prmtContractModel);
	}
	protected String getTDFileName() {
		return "/bim/fdc/invite/InviteDocument";
	}
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.invite.app.InviteDocumentPrintQuery");
	}
	
	protected BaseInviteInfo createNewDate() {
		InviteDocumentsInfo info=new InviteDocumentsInfo();
		
		info.setId(BOSUuid.create(info.getBOSType()));
		createBaseInvite(info);
		
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("inviteProject.id");
		sic.add("scope");
		sic.add("payMethod");
		sic.add("state");
		sic.add("auditTime");
		view.setSelector(sic);
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("inviteProject.id", info.getInviteProject().getId().toString()));
		view.setFilter(filter);
		
		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo auditTime = new SorterItemInfo("auditTime");
		auditTime.setSortType(SortType.DESCEND);
		view.setSorter(sorter);
		
		try {
			InviteTenderPlanningCollection coll = InviteTenderPlanningFactory.getRemoteInstance().getInviteTenderPlanningCollection(view);
			if(coll !=null && coll.size()>0) {
				InviteTenderPlanningInfo tennderPlanning = coll.get(0);
				info.setScope(tennderPlanning.getScope());
				info.setPayMethod(tennderPlanning.getPayMethod());
			}
		} catch (BOSException e) {
			this.handleException(e);
		}
		
		return info;
	}
	protected CoreBillEntryBaseInfo createNewEntryDate() {
		return null;
	}
	private boolean ctrlAgreementBtnStatus() throws EASBizException, BOSException{
		boolean isEdit = false;
	    if(STATUS_ADDNEW.equals(getOprtState())||STATUS_EDIT.equals(getOprtState()))
	    {
	        isEdit = true;
	    }
	    return isEdit;
	}
//	public void actionAgreementText_actionPerformed(ActionEvent e) throws Exception {
//		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
//		String boID = getSelectBOID();
//        if(boID == null){
//            return;
//        }
//        if(this.editData.getAgreementID()!=null){
//			acm.showAttachmentListUIByBoID(this.editData.getAgreementID(), this,ctrlAgreementBtnStatus());
//        }else{
//        	this.editData.setAgreementID(BOSUuid.create(this.editData.getBOSType()).toString());
//        	acm.showAttachmentListUIByBoID(this.editData.getAgreementID(), this,ctrlAgreementBtnStatus());
//        }
//        fillAttachmnetTable();
//	}
//	public void actionViewContrntSelf_actionPerformed(ActionEvent e) throws Exception {
//		if(isUseWriteMark&&this.editData != null && this.editData.getId() != null&&this.editData instanceof FDCBillInfo){
//			UIContext uiContext = new UIContext();
//			uiContext.put("billInfo",this.editData);
//			uiContext.put("optState", getOprtState());
//			uiContext.put(UIContext.OWNER,this);
//			IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(InviteContentUI.class.getName(), uiContext);
//			window.show();
//		}else{
//			viewContent(this, getSelectBOID());
//		}
//	}
//	private void viewContent(final CoreUIObject ui, final String id) throws BOSException, IOException, FileNotFoundException {
//		LongTimeDialog dialog = UITools.getDialog(ui);
//		if (dialog == null)
//			return;
//		dialog.setLongTimeTask(new ILongTimeTask() {
//			public Object exec() throws Exception {
//				EntityViewInfo view=new EntityViewInfo();
//				FilterInfo filter=new FilterInfo();
//				view.setFilter(filter);
//				String sql="select top 1 fid from T_INV_InviteContent where fparent='"+id+"' order by fcreateTime desc";
//				filter.getFilterItems().add(new FilterItemInfo("id",sql,CompareType.INNER));
//				view.getSelector().add("id");
//				view.getSelector().add("fileType");
//				view.getSelector().add("contentFile");
//				final InviteContentCollection col = InviteContentFactory.getRemoteInstance().getInviteContentCollection(view);
//				if(col.size()==0){
//					MsgBox.showWarning(ui, "招标文件没有上传正文！");
//					return null;
//				}
//			    InviteContentInfo contentInfo=col.get(0);
//				String type = contentInfo.getFileType();
//				File file = File.createTempFile("temp", "." + type);
//				String fullname = file.getPath();
//				FileOutputStream fos = new FileOutputStream(file);
//				fos.write(contentInfo.getContentFile());
//				fos.close();
//				File tempbat = File.createTempFile("tempbat", ".bat");
//			    FileWriter fw = new FileWriter(tempbat);
//			    fw.write("start " + fullname);
//			    fw.close();
//			    String tempbatFullname = tempbat.getPath();
//			    Process process = Runtime.getRuntime().exec(tempbatFullname);
//			    process.toString();
//			    return null;
//			}
//			public void afterExec(Object result) throws Exception {
//				
//			}});
//		if (dialog != null)
//			dialog.show();
//	}
//	
//	public void actionViewContractModel_actionPerformed(ActionEvent e) throws Exception {
//		if(isUseWriteMark&&this.editData != null && this.editData.getId() != null&&this.editData instanceof FDCBillInfo){
//			UIContext uiContext = new UIContext();
//			uiContext.put("billInfo",this.editData);
//			uiContext.put("optState", getOprtState());
//			uiContext.put(UIContext.OWNER,this);
//			IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(InviteContractModelContentUI.class.getName(), uiContext);
//			window.show();
//		}else{
//			viewContractModelContent(this, getSelectBOID());
//		}
//	}
//	private void viewContractModelContent(final CoreUIObject ui, final String id) throws BOSException, IOException, FileNotFoundException {
//		LongTimeDialog dialog = UITools.getDialog(ui);
//		if (dialog == null)
//			return;
//		dialog.setLongTimeTask(new ILongTimeTask() {
//			public Object exec() throws Exception {
//				EntityViewInfo view=new EntityViewInfo();
//				FilterInfo filter=new FilterInfo();
//				view.setFilter(filter);
//				String sql="select top 1 fid from T_Con_contractContent where fparent='"+id+"' order by fcreateTime desc";
//				filter.getFilterItems().add(new FilterItemInfo("id",sql,CompareType.INNER));
//				view.getSelector().add("id");
//				view.getSelector().add("fileType");
//				view.getSelector().add("contentFile");
//				final ContractContentCollection col = ContractContentFactory.getRemoteInstance().getContractContentCollection(view);
//				if(col.size()==0){
//					MsgBox.showWarning(ui, "招标文件没有上传标准合同范本！");
//					return null;
//				}
//				ContractContentInfo contentInfo=col.get(0);
//				String type = contentInfo.getFileType();
//				File file = File.createTempFile("temp", "." + type);
//				String fullname = file.getPath();
//				FileOutputStream fos = new FileOutputStream(file);
//				fos.write(contentInfo.getContentFile());
//				fos.close();
//				File tempbat = File.createTempFile("tempbat", ".bat");
//			    FileWriter fw = new FileWriter(tempbat);
//			    fw.write("start " + fullname);
//			    fw.close();
//			    String tempbatFullname = tempbat.getPath();
//			    Process process = Runtime.getRuntime().exec(tempbatFullname);
//			    process.toString();
//			    return null;
//			}
//			public void afterExec(Object result) throws Exception {
//				
//			}});
//		if (dialog != null)
//			dialog.show();
//	}
	protected void prmtInviteBaseFile_dataChanged(DataChangeEvent e) throws Exception {
		boolean isChanged = true;
		isChanged = BizCollUtil.isF7ValueChanged(e);
		if (!isChanged) {
			return;
		}
		deleteTypeAttachment(this.editData.getId().toString(),"招标文件");
		
		deleteInviteContent();
		
		InviteBaseFileInfo inviteBaseFile = (InviteBaseFileInfo)this.prmtInviteBaseFile.getValue();
		if(inviteBaseFile != null){
			//利用合同范本ID 去找合同范本
			EntityViewInfo viewInfo = new EntityViewInfo();
			viewInfo.getSelector().add("id");
			viewInfo.getSelector().add("fileName");
			viewInfo.getSelector().add("version");
			viewInfo.getSelector().add("fileType");
			viewInfo.getSelector().add("createTime");
			viewInfo.getSelector().add("creator.*");
			viewInfo.getSelector().add("contentFile");
			SorterItemInfo sorterItemInfo = new SorterItemInfo("fileType");
			sorterItemInfo.setSortType(SortType.ASCEND);
			viewInfo.getSorter().add(sorterItemInfo);
			sorterItemInfo = new SorterItemInfo("version");
			sorterItemInfo.setSortType(SortType.DESCEND);
			viewInfo.getSorter().add(sorterItemInfo);
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.appendFilterItem("parent", inviteBaseFile.getId().toString());
			viewInfo.setFilter(filterInfo);
			InviteContentCollection contentCollection = InviteContentFactory.getRemoteInstance().getInviteContentCollection(viewInfo);
			InviteContentInfo model = (InviteContentInfo)contentCollection.get(0);
			if(model!=null){
				String fullname = model.getFileType();
				editData.setInviteBaseFile(inviteBaseFile);
				BigDecimal version = new BigDecimal("1.0");
				InviteContentInfo contentInfo = new InviteContentInfo();
				contentInfo.setVersion(version);
				contentInfo.setParent(editData.getId().toString());
				contentInfo.setFileType(fullname);
				contentInfo.setContentFile(model.getContentFile());
				InviteContentFactory.getRemoteInstance().save(contentInfo);
				
				addModeAttachmentInfo(this.editData.getId().toString(),"招标文件",model.getFileType(),FormetFileSize(model.getContentFile().length),model.getContentFile(),"1");
			}
			this.prmtInviteBaseFile.setEnabled(false);
		}else{
			this.prmtInviteBaseFile.setEnabled(false);
		}
		fillAttachmnetTable();
	}
	protected void prmtContractModel_dataChanged(DataChangeEvent e) throws Exception {
		boolean isChanged = true;
		isChanged = BizCollUtil.isF7ValueChanged(e);
		if (!isChanged) {
			return;
		}
		deleteTypeAttachment(this.editData.getId().toString(),"标准合同");
		
		deleteContractContent();
		
		ContractModelInfo contractModel = (ContractModelInfo)this.prmtContractModel.getValue();
		if(contractModel!=null){
			//利用合同范本ID 去找合同范本
			EntityViewInfo viewInfo = new EntityViewInfo();
			viewInfo.getSelector().add("id");
			viewInfo.getSelector().add("fileName");
			viewInfo.getSelector().add("version");
			viewInfo.getSelector().add("fileType");
			viewInfo.getSelector().add("createTime");
			viewInfo.getSelector().add("creator.*");
			viewInfo.getSelector().add("contentFile");
			SorterItemInfo sorterItemInfo = new SorterItemInfo("fileType");
			sorterItemInfo.setSortType(SortType.ASCEND);
			viewInfo.getSorter().add(sorterItemInfo);
			sorterItemInfo = new SorterItemInfo("version");
			sorterItemInfo.setSortType(SortType.DESCEND);
			viewInfo.getSorter().add(sorterItemInfo);
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.appendFilterItem("parent", contractModel.getId().toString());
			viewInfo.setFilter(filterInfo);
			ContractContentCollection contentCollection = ContractContentFactory.getRemoteInstance().getContractContentCollection(viewInfo);
			ContractContentInfo model = (ContractContentInfo)contentCollection.get(0);
			if(model!=null){
				String fullname = model.getFileType();
				editData.setContractModel(contractModel);
				BigDecimal version = new BigDecimal("1.0");
				ContractContentInfo contentInfo = new ContractContentInfo();
				contentInfo.setVersion(version);
				contentInfo.setParent(editData.getId().toString());
				contentInfo.setFileType(fullname);
				contentInfo.setContentFile(model.getContentFile());
				ContractContentFactory.getRemoteInstance().save(contentInfo);
				
				addModeAttachmentInfo(this.editData.getId().toString(),"标准合同",model.getFileType(),FormetFileSize(model.getContentFile().length),model.getContentFile(),"1");
			}
			this.prmtContractModel.setEnabled(false);
		}else{
			this.prmtContractModel.setEnabled(true);
		}
		fillAttachmnetTable();
	}
	private void deleteContractContent() throws BOSException{
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" delete from T_CON_ContractContent where fparent='"+this.editData.getId().toString()+"'");
		_builder.executeUpdate();
		
	}
	private void deleteInviteContent() throws BOSException{
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" delete from T_INV_InviteContent where fparent='"+this.editData.getAgreementID()+"'");
		_builder.executeUpdate();
	}
	protected void closeDeleteAttachment() {
		if(editData!=null){
			try {
				if(InviteDocumentsFactory.getRemoteInstance().exists(new ObjectUuidPK(this.editData.getId()))){
					return;
				}
//				FilterInfo filter=new FilterInfo();
//				filter.getFilterItems().add(new FilterItemInfo("parent",this.editData.getId().toString()+"BASE"));
//				InviteContentFactory.getRemoteInstance().delete(filter);
//				
//				filter=new FilterInfo();
//				filter.getFilterItems().add(new FilterItemInfo("parent",this.editData.getId().toString()));
//				InviteContentFactory.getRemoteInstance().delete(filter);
//				
//				ContractContentFactory.getRemoteInstance().delete(filter);
				
//				FilterInfo filter = new FilterInfo();
//				filter.getFilterItems().add(new FilterItemInfo("parent",editData.getId().toString()));
//				ContractContentFactory.getRemoteInstance().delete(filter);
//				
//				InviteContentFactory.getRemoteInstance().delete(filter);
				deleteContractContent();
				deleteInviteContent();
				if(this.editData.getAgreementID()!=null){
					deleteAttachment(this.editData.getAgreementID());
				}
				deleteAttachment(this.editData.getId().toString());
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}
	protected void initTable() {
		this.kdtEntry.checkParsed();
		this.kdtEntry.getStyleAttributes().setLocked(true);
		
		String formatString = "yyyy-MM-dd";
		this.kdtEntry.getColumn("date").getStyleAttributes().setNumberFormat(formatString);

//		this.kdtBaseFileEntry.checkParsed();
//		this.kdtBaseFileEntry.getStyleAttributes().setLocked(true);
//		this.kdtBaseFileEntry.getColumn("createTime").getStyleAttributes().setNumberFormat(formatString);
	}
	private void fillAttachmnetTable() throws EASBizException, BOSException {
		this.kdtEntry.removeRows();
		String boId = null;
		if (this.editData.getId() == null) {
			return;
		} else {
			boId = this.editData.getId().toString();
		}

		if (boId != null) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("id"));
			sic.add(new SelectorItemInfo("attachment.id"));
			sic.add(new SelectorItemInfo("attachment.name"));
			sic.add(new SelectorItemInfo("attachment.createTime"));
			sic.add(new SelectorItemInfo("attachment.attachID"));
			sic.add(new SelectorItemInfo("assoType"));
			sic.add(new SelectorItemInfo("boID"));

			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID", boId));
			filter.getFilterItems().add(new FilterItemInfo("attachment.beizhu", "1",CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("attachment.beizhu", null,CompareType.EQUALS));
			//添加补充协议
//			filter.getFilterItems().add(new FilterItemInfo("boID", this.editData.getAgreementID()));
			filter.setMaskString("#0 and (#1 or #2)");
			EntityViewInfo evi = new EntityViewInfo();
			evi.getSorter().add(new SorterItemInfo("boID"));
			evi.getSorter().add(new SorterItemInfo("attachment.name"));
			evi.setFilter(filter);
			evi.setSelector(sic);
			BoAttchAssoCollection cols = null;
			try {
				cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(evi);
			} catch (BOSException e) {
				e.printStackTrace();
			}
//			boolean flag = true;
			if (cols != null && cols.size() > 0) {
				for (Iterator it = cols.iterator(); it.hasNext();) {
					BoAttchAssoInfo boaInfo = (BoAttchAssoInfo)it.next();
//					if(boId.equals(boaInfo.getBoID())){
//						AttachmentInfo attachment = boaInfo.getAttachment();
//							IRow row = this.kdtEntry.addRow();
//							row.getCell("id").setValue(attachment.getId().toString());
//							row.getCell("seq").setValue(attachment.getAttachID());
//							row.getCell("name").setValue(attachment.getName());
//							row.getCell("date").setValue(attachment.getCreateTime());
//							row.getCell("isAgreement").setValue(Boolean.FALSE);
//					}else{
						AttachmentInfo attachment = boaInfo.getAttachment();
						IRow row = this.kdtEntry.addRow();
						row.getCell("id").setValue(attachment.getId().toString());
						row.getCell("seq").setValue(attachment.getAttachID());
						row.getCell("name").setValue(attachment.getName());
						row.getCell("date").setValue(attachment.getCreateTime());
						row.getCell("type").setValue(boaInfo.getAssoType());
//						flag =false;
//					}
				}
			}
		}
	}
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		super.actionAttachment_actionPerformed(e);
		this.fillAttachmnetTable();
	}
	protected void kdtEntry_tableClicked(KDTMouseEvent e) throws Exception {
		if(e.getType() == 1 && e.getButton() == 1 && e.getClickCount() == 2){
			IRow row  =  this.kdtEntry.getRow(e.getRowIndex());
			getFileGetter();
			Object selectObj= row.getCell("id").getValue();
			if(selectObj!=null){
				String attachId=selectObj.toString();
				fileGetter.viewAttachment(attachId);
			}
        }
	}
	private File chooseFileByDialog() {
		File retFile = null;
		KDFileChooser fc = new KDFileChooser(System.getProperty("user.home"));
		fc.setFileSelectionMode(0);
		fc.setMultiSelectionEnabled(false);
		int retVal = fc.showOpenDialog(this);
		if (retVal == 1)
			return retFile;
		retFile = fc.getSelectedFile();
		if (!retFile.exists()) {
			MsgBox.showInfo(Resrcs.getString("FileNotExisted"));
			return null;
		}
		if (retFile.length() > 0x3200000L) {
			MsgBox.showInfo(Resrcs.getString("FileSizeNotAllowed"));
			return null;
		} else {
			return retFile;
		}
	}
//	public void actionAddBaseFile_actionPerformed(ActionEvent e) throws Exception {
//		File file = chooseFileByDialog();
//		if (file == null)
//			return;
//		if (!file.canRead()) {
//			MsgBox.showWarning(this, ContractClientUtils.getRes("readFileError"));
//			SysUtil.abort();
//		}
//		String fullname = file.getName();
//		FilterInfo filter=new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("fileName",fullname));
//		filter.getFilterItems().add(new FilterItemInfo("parent",this.editData.getId().toString()+"BASE"));
//		if (InviteContentFactory.getRemoteInstance().exists(filter)){
//			MsgBox.showWarning("标底文件'" + fullname+ "'已存在！");
//			return;
//		}
//		
//		byte content[] = (byte[]) null;
//		try {
//			content = FileGetter.getBytesFromFile(file);
//			if (content == null || content.length == 0) {
//				MsgBox.showWarning("所添加的附件大小不能为0字节！");
//				SysUtil.abort();
//			}
//		} catch (IOException ex) {
//			MsgBox.showWarning(this, ContractClientUtils.getRes("readFileError"));
//			SysUtil.abort();
//		}
//		InviteContentInfo contentInfo = new InviteContentInfo();
//		contentInfo.setParent(this.editData.getId().toString()+"BASE");
//		contentInfo.setFileName(fullname);
//		contentInfo.setContentFile(content);
//		InviteContentFactory.getRemoteInstance().addnew(contentInfo);
//		loadBaseFile();
//		this.editData.setDescription("ISMODIFY");
//	}
//	protected void loadBaseFile() throws BOSException{
//		this.kdtBaseFileEntry.removeRows();
//		EntityViewInfo view=new EntityViewInfo();
//		FilterInfo filter=new FilterInfo();
//		view.getSorter().add(new SorterItemInfo("createTime"));
//		view.getSelector().add(new SelectorItemInfo("*"));
//		view.getSelector().add(new SelectorItemInfo("creator.*"));
//		filter.getFilterItems().add(new FilterItemInfo("parent",this.editData.getId().toString()+"BASE"));
//		view.setFilter(filter);
//		
//		InviteContentCollection col=InviteContentFactory.getRemoteInstance().getInviteContentCollection(view);
//		for(int i=0;i<col.size();i++){
//			IRow row=this.kdtBaseFileEntry.addRow();
//			InviteContentInfo contentInfo=col.get(i);
//			row.setUserObject(contentInfo);
//			row.getCell("creatorName").setValue(contentInfo.getCreator().getName());
//			row.getCell("createTime").setValue(contentInfo.getCreateTime());
//			row.getCell("fileName").setValue(contentInfo.getFileName());
//		}
//	}
//	public void actionDelBaseFile_actionPerformed(ActionEvent e) throws Exception {
//		if (this.kdtBaseFileEntry.getSelectManager().size() == 0) {
//			MsgBox.showWarning(this,EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_MustSelected"));
//			SysUtil.abort();
//		}
//		int rtn = MsgBox.showConfirm2("是否删除该标底文件？");
//		if (rtn == 2) {
//			return;
//		} else {
//			IRow row = KDTableUtil.getSelectedRow(kdtBaseFileEntry);
//			InviteContentInfo contentInfo=(InviteContentInfo) row.getUserObject();
//			InviteContentFactory.getRemoteInstance().delete(new ObjectUuidPK(contentInfo.getId()));
//			loadBaseFile();
//			this.editData.setDescription("ISMODIFY");
//		}
//	}
//	protected void kdtBaseFileEntry_tableClicked(KDTMouseEvent e) throws Exception {
//		if(e.getType() == 1 && e.getButton() == 1 && e.getClickCount() == 2){
//			LongTimeDialog dialog = UITools.getDialog(this);
//			if (dialog == null)
//				return;
//			dialog.setLongTimeTask(new ILongTimeTask() {
//				public Object exec() throws Exception {
//					KDTable table = kdtBaseFileEntry;
//					if (table.getSelectManager().size() == 0) {
//						MsgBox.showWarning(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_MustSelected"));
//						SysUtil.abort();
//					}
//					IRow row = KDTableUtil.getSelectedRow(table);
//					InviteContentInfo contentInfo = (InviteContentInfo) row.getUserObject();
//					if (contentInfo == null) {
//						FDCMsgBox.showWarning("标底文件不存在！");
//						return null;
//					} else {
//						File file = viewContent(contentInfo);
//						file.setReadOnly();
//						return null;
//					}
//				}
//				public void afterExec(Object result) throws Exception {
//
//				}
//			});
//			if (dialog != null)
//				dialog.show();
//		}
//	}
//	public File viewContent(InviteContentInfo contentInfo)throws BOSException, IOException, FileNotFoundException,EASBizException {
//		String type = contentInfo.getFileName();
//		String name = "";
//		String dat = "";
//		if (type.indexOf(".") != -1) {
//			name = type.substring(0, type.lastIndexOf("."));
//			dat = type.substring(type.lastIndexOf("."), type.length());
//		}
//		File file = File.createTempFile("temp" + name, dat);
//		String fullname = file.getPath();
//		StringBuffer sb = new StringBuffer(fullname);
//		sb.insert(fullname.lastIndexOf("\\") + 1, "\"");
//		sb.append("\"");
//		fullname = sb.toString();
//		FileOutputStream fos = new FileOutputStream(file);
//		fos.write(contentInfo.getContentFile());
//		fos.close();
//		File tempbat = File.createTempFile("tempbat", ".bat");
//		FileWriter fw = new FileWriter(tempbat);
//		fw.write("start " + fullname);
//		fw.close();
//		String tempbatFullname = tempbat.getPath();
//		Runtime.getRuntime().exec(tempbatFullname);
//		return file;
//	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
//		if (oprtType.equals(OprtState.VIEW)) {
////			this.actionViewContractModel.setEnabled(false);
////			this.actionViewContrntSelf.setEnabled(false);
//			this.actionAddBaseFile.setEnabled(false);
//			this.actionDelBaseFile.setEnabled(false);
//		} else {
////			this.actionViewContractModel.setEnabled(true);
////			this.actionViewContrntSelf.setEnabled(true);
//			this.actionAddBaseFile.setEnabled(true);
//			this.actionDelBaseFile.setEnabled(true);
//		}
		if (!oprtType.equals(OprtState.EDIT)&&!oprtType.equals(OprtState.ADDNEW)) {
			this.actionInviteUpLoad.setEnabled(false);
			this.actionUpLoad.setEnabled(false);
			this.actionAgreementText.setEnabled(false);
		}else{
			this.actionInviteUpLoad.setEnabled(true);
			this.actionUpLoad.setEnabled(true);
			this.actionAgreementText.setEnabled(true);
		}
	}
	
	private HashMap getProviderDataMap() {
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put(BaseInviteEditUI.INVITEPROJECTID, this.editData.getInviteProject().getId().toString());
		
		return map;
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
		InviteDocumentsPrintDataProvider dataPvd = new InviteDocumentsPrintDataProvider(
				editData.getString("id"), getTDQueryPK(), getProviderDataMap());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		
		appHlp.print(getTDFileName(), dataPvd, javax.swing.SwingUtilities
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
		InviteDocumentsPrintDataProvider dataPvd = new InviteDocumentsPrintDataProvider(
				editData.getString("id"), getTDQueryPK(), getProviderDataMap());
//		dataPvd.setBailId(editData.getBail().getId().toString());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), dataPvd, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}
	public void actionInviteDownLoad_actionPerformed(ActionEvent e) throws Exception {
		if(this.prmtInviteBaseFile.getValue()==null){
			FDCMsgBox.showInfo(this,"请先选择招标文件！");
			return;
		}
		String id=null;
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add(new SelectorItemInfo("attachment.id"));
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view=new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("attachment.attachID",this.editData.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("boID",this.editData.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("assoType","招标文件"));
		view.setFilter(filter);
		view.setSelector(sic);
		
		BoAttchAssoCollection cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		if(cols.size()==0){
			sic=new SelectorItemCollection();
			sic.add(new SelectorItemInfo("fileType"));
			sic.add(new SelectorItemInfo("contentFile"));
			FDCSQLBuilder _builder = new FDCSQLBuilder();
			_builder.appendSql(" select top 1 fid from T_INV_InviteContent where fparentid='"+this.editData.getId().toString()+"' order by fversion desc");
			final IRowSet rowSet = _builder.executeQuery();
			if(rowSet.size()>0){
				while (rowSet.next()) {
					ContractContentInfo info=ContractContentFactory.getRemoteInstance().getContractContentInfo(new ObjectUuidPK(rowSet.getString("fid")),sic);
					id=addModeAttachmentInfo(this.editData.getId().toString(),"招标文件",info.getFileType(),FormetFileSize(info.getContentFile().length),info.getContentFile(),"1");
				}
			}else{
				FDCMsgBox.showInfo(this,"无招标文件！");
				return;
			}
		}else if(cols.size()>1){
			FDCMsgBox.showInfo(this,"招标文件存在多个！");
			return;
		}else{
			id=cols.get(0).getAttachment().getId().toString(); 
		}
		String file=getFileGetter().downloadAttachment(id, this);
		if(file!=null&&!"".equals(file)){
			FDCMsgBox.showInfo(this,"招标文件下载成功！");
		}
	}

	public void actionInviteUpLoad_actionPerformed(ActionEvent e) throws Exception {
		if(this.prmtInviteBaseFile.getValue()==null){
			FDCMsgBox.showInfo(this,"请先选择招标文件！");
			return;
		}
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add(new SelectorItemInfo("attachment.file"));
		sic.add(new SelectorItemInfo("attachment.name"));
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view=new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("attachment.attachID",this.editData.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("boID",this.editData.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("assoType","招标文件"));
		
		view.setFilter(filter);
		view.setSelector(sic);
		
		BoAttchAssoCollection cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		if(cols.size()==0){
			FDCMsgBox.showInfo(this,"无招标文件！");
			return;
		}else if(cols.size()>1){
			FDCMsgBox.showInfo(this,"招标文件存在多个！");
			return;
		}
		
		KDFileChooser fc = new KDFileChooser(System.getProperty("user.home"));
		fc.setFileSelectionMode(0);
		fc.setMultiSelectionEnabled(false);
		FileFilter ff = new FileFilter(){
            public boolean accept(File pathname) {
                if (pathname.isFile()&&
                		pathname.getName().toUpperCase().endsWith(".DOC")) {
                    return true;
                }
                return false;
            }
			public String getDescription() {
				return ".DOC";
			}};
		fc.setFileFilter(ff);
		int retVal = fc.showOpenDialog(this);
		if(retVal == 0){
			File retFile=fc.getSelectedFile();
			if(retFile!=null){
				if(!retFile.exists()){
					FDCMsgBox.showInfo(this,Resrcs.getString("FileNotExisted"));
		        }else if(retFile!=null&&retFile.length() > 52428800L){
		        	FDCMsgBox.showInfo(this,Resrcs.getString("FileSizeNotAllowed"));
		        }else{
		        	File file = File.createTempFile("temp"+cols.get(0).getAttachment().getName(), ".DOC");
		    		FileOutputStream fos = new FileOutputStream(file);
		    		fos.write(cols.get(0).getAttachment().getFile());
		    		fos.close();
		    		
		    		FileInputStream in = new FileInputStream(file);
		    		FileInputStream newIn = new FileInputStream(retFile);
		    		try {
						WordExtractor extractor = new WordExtractor(in);
						String instr = extractor.getParagraphText()[0];
						if(instr.indexOf("\r\n")>0){
							instr=instr.split("\r\n")[0];
						}
						if(instr.indexOf("\r")>0){
							instr=instr.split("\r")[0];
						}
						in.close();
						
						WordExtractor newExtractor = new WordExtractor(newIn);
						String newInstr = newExtractor.getParagraphText()[0];
						if(newInstr.indexOf("\r\n")>0){
							newInstr=newInstr.split("\r\n")[0];
						}
						if(newInstr.indexOf("\r")>0){
							newInstr=newInstr.split("\r")[0];
						}
						newIn.close();
						
						if(!newInstr.equals(instr)){
			        		FDCMsgBox.showInfo(this,"招标文件版本不一致！");
			        		return;
			        	}
					} catch (Exception e2) {
						FDCMsgBox.showInfo(this,"请使用标准合同的Microsoft Word版本！");
		        		return;
					}finally{
						in.close();
						newIn.close();
					}
		        	
		        	long len = retFile.length();
		    		byte[] bytes = new byte[(int)len];
		    		
		    		FileInputStream fi=new FileInputStream(retFile);
		    		BufferedInputStream bi=new BufferedInputStream(fi);
		    		int size=fi.available();
		    		bi.read(bytes);
		    		deleteTypeAttachment(this.editData.getId().toString(),"招标文件");
		    		addModeAttachmentInfo(this.editData.getId().toString(),"招标文件",retFile.getName(),FormetFileSize(size),bytes,null);
		    		bi.close();
		    		fi.close();
		    		
		    		FDCMsgBox.showInfo(this,"招标文件上传成功！");
		    		fillAttachmnetTable();
		        }
			}
		}
	}
	public void actionDownLoad_actionPerformed(ActionEvent e) throws Exception {
		if(this.prmtContractModel.getValue()==null){
			FDCMsgBox.showInfo(this,"请先选择标准合同！");
			return;
		}
		String id=null;
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add(new SelectorItemInfo("attachment.id"));
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view=new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("attachment.attachID",this.editData.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("boID",this.editData.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("assoType","标准合同"));
		view.setFilter(filter);
		view.setSelector(sic);
		
		BoAttchAssoCollection cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		if(cols.size()==0){
			sic=new SelectorItemCollection();
			sic.add(new SelectorItemInfo("fileType"));
			sic.add(new SelectorItemInfo("contentFile"));
			FDCSQLBuilder _builder = new FDCSQLBuilder();
			_builder.appendSql(" select top 1 fid from T_CON_ContractContent where fparent='"+this.editData.getId().toString()+"' order by fversion desc");
			final IRowSet rowSet = _builder.executeQuery();
			if(rowSet.size()>0){
				while (rowSet.next()) {
					ContractContentInfo info=ContractContentFactory.getRemoteInstance().getContractContentInfo(new ObjectUuidPK(rowSet.getString("fid")),sic);
					id=addModeAttachmentInfo(this.editData.getId().toString(),"标准合同",info.getFileType(),FormetFileSize(info.getContentFile().length),info.getContentFile(),"1");
				}
			}else{
				FDCMsgBox.showInfo(this,"无标准合同！");
				return;
			}		 
		}else if(cols.size()>1){
			FDCMsgBox.showInfo(this,"标准合同存在多个！");
			return;
		}else{
			id=cols.get(0).getAttachment().getId().toString(); 
		}
		String file=getFileGetter().downloadAttachment(id, this);
		if(file!=null&&!"".equals(file)){
			FDCMsgBox.showInfo(this,"标准合同下载成功！");
		}
	}

	public void actionUpLoad_actionPerformed(ActionEvent e) throws Exception {
		if(this.prmtContractModel.getValue()==null){
			FDCMsgBox.showInfo(this,"请先选择标准合同！");
			return;
		}
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add(new SelectorItemInfo("attachment.file"));
		sic.add(new SelectorItemInfo("attachment.name"));
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view=new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("attachment.attachID",this.editData.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("boID",this.editData.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("assoType","标准合同"));
		
		view.setFilter(filter);
		view.setSelector(sic);
		
		BoAttchAssoCollection cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		if(cols.size()==0){
			FDCMsgBox.showInfo(this,"无标准合同！");
			return;
		}else if(cols.size()>1){
			FDCMsgBox.showInfo(this,"标准合同存在多个！");
			return;
		}
		
		KDFileChooser fc = new KDFileChooser(System.getProperty("user.home"));
		fc.setFileSelectionMode(0);
		fc.setMultiSelectionEnabled(false);
		FileFilter ff = new FileFilter(){
            public boolean accept(File pathname) {
                if (pathname.isFile()&&
                		pathname.getName().toUpperCase().endsWith(".DOC")) {
                    return true;
                }
                return false;
            }
			public String getDescription() {
				return ".DOC";
			}};
		fc.setFileFilter(ff);
		int retVal = fc.showOpenDialog(this);
		if(retVal == 0){
			File retFile=fc.getSelectedFile();
			if(retFile!=null){
				if(!retFile.exists()){
					FDCMsgBox.showInfo(this,Resrcs.getString("FileNotExisted"));
		        }else if(retFile!=null&&retFile.length() > 52428800L){
		        	FDCMsgBox.showInfo(this,Resrcs.getString("FileSizeNotAllowed"));
		        }else{
		        	File file = File.createTempFile("temp"+cols.get(0).getAttachment().getName(), ".DOC");
		    		FileOutputStream fos = new FileOutputStream(file);
		    		fos.write(cols.get(0).getAttachment().getFile());
		    		fos.close();
		    		
		    		FileInputStream in = new FileInputStream(file);
		    		FileInputStream newIn = new FileInputStream(retFile);
		    		try {
						WordExtractor extractor = new WordExtractor(in);
						String instr = extractor.getParagraphText()[0];
						if(instr.indexOf("\r\n")>0){
							instr=instr.split("\r\n")[0];
						}
						if(instr.indexOf("\r")>0){
							instr=instr.split("\r")[0];
						}
						in.close();
						
						WordExtractor newExtractor = new WordExtractor(newIn);
						String newInstr = newExtractor.getParagraphText()[0];
						if(newInstr.indexOf("\r\n")>0){
							newInstr=newInstr.split("\r\n")[0];
						}
						if(newInstr.indexOf("\r")>0){
							newInstr=newInstr.split("\r")[0];
						}
						newIn.close();
						
						if(!newInstr.equals(instr)){
			        		FDCMsgBox.showInfo(this,"标准合同版本不一致！");
			        		return;
			        	}
					} catch (Exception e2) {
						FDCMsgBox.showInfo(this,"请使用标准合同的Microsoft Word版本！");
		        		return;
					}finally{
						in.close();
						newIn.close();
					}
		        	
		        	long len = retFile.length();
		    		byte[] bytes = new byte[(int)len];
		    		
		    		FileInputStream fi=new FileInputStream(retFile);
		    		BufferedInputStream bi=new BufferedInputStream(fi);
		    		int size=fi.available();
		    		bi.read(bytes);
		    		deleteTypeAttachment(this.editData.getId().toString(),"标准合同");
		    		addModeAttachmentInfo(this.editData.getId().toString(),"标准合同",retFile.getName(),FormetFileSize(size),bytes,null);
		    		bi.close();
		    		fi.close();
		    		
		    		FDCMsgBox.showInfo(this,"标准合同上传成功！");
		    		fillAttachmnetTable();
		        }
			}
		}
	}
	public void actionAgreementText_actionPerformed(ActionEvent e)throws Exception {
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		String boID = getSelectBOID();
        if(boID == null)
        {
            return;
        }
        if(this.editData.getAgreementID()!=null){
			acm.showAttachmentListUIByBoID(this.editData.getAgreementID(), this,ctrlAgreementBtnStatus());
        }else{
        	this.editData.setAgreementID(BOSUuid.create(this.editData.getBOSType()).toString());
        	acm.showAttachmentListUIByBoID(this.editData.getAgreementID(), this,ctrlAgreementBtnStatus());
        }
        EntityViewInfo view = new EntityViewInfo();
        FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("boID",this.editData.getAgreementID()));
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("attachment.size");
		sel.add("attachment.file");
		sel.add("attachment.name");
		view.setSelector(sel);
		view.setFilter(filter);
		BoAttchAssoCollection attColl = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		deleteTypeAttachment(this.editData.getId().toString(),"补充协议");
		for(int i = 0 ; i < attColl.size(); i ++){
	    	addModeAttachmentInfo(this.editData.getId().toString(),"补充协议",attColl.get(i).getAttachment().getName(),attColl.get(i).getAttachment().getSize(),attColl.get(i).getAttachment().getFile(),null);
		}
        fillAttachmnetTable();
	}
	private String addModeAttachmentInfo(String boId,String type,String fullname,String size,byte[] bytes,String beizhu) throws EASBizException, BOSException, IOException{
		AttachmentInfo info = new AttachmentInfo();
		info.setType("Microsoft Word 文档");
		info.setIsShared(false);
		if(fullname.indexOf(".")>0){
			info.setName(fullname.substring(0,fullname.lastIndexOf(".")));
		}else{
			info.setName(fullname);
		}
		info.setFile(bytes);
		info.setSize(size);
		info.setSimpleName("doc");
		info.setAttachID(boId);
		info.setBeizhu(beizhu);
		info.setDescription(type+"请勿删除！");
		BoAttchAssoInfo boInfo = new BoAttchAssoInfo();
		boInfo.setBoID(boId);
		boInfo.setAssoType(type);
		boInfo.setAttachment(info);
		info.getBoAttchAsso().clear();
		info.getBoAttchAsso().add(boInfo);
		IObjectPK pk=AttachmentFactory.getRemoteInstance().addnew(info);
		return pk.toString();
	}
	public String FormetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}
	protected void deleteTypeAttachment(String id,String type) throws BOSException, EASBizException{
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("boID" , id));
		filter.getFilterItems().add(new FilterItemInfo("assoType",type));
		
		view.setFilter(filter);
		BoAttchAssoCollection col=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		for(int i=0;i<col.size();i++){
			FilterInfo deleteFilter = new FilterInfo();
			deleteFilter.getFilterItems().add(new FilterItemInfo("id" , col.get(i).getAttachment().getId().toString()));
			AttachmentFactory.getRemoteInstance().delete(deleteFilter);
		}
	}
}