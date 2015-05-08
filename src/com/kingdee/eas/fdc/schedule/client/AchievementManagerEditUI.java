package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.io.File;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.ext.IFilterInfoProducer;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.attachment.AttachmentCollection;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.cp.dm.ContentTypeEnum;
import com.kingdee.eas.cp.dm.archive.ArchiveDocumentParamsInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.FDCAttachmentUtil;
import com.kingdee.eas.fdc.basedata.util.FDCCPDMUtil;
import com.kingdee.eas.fdc.schedule.AchievementManagerFactory;
import com.kingdee.eas.fdc.schedule.AchievementManagerInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;
import com.kingdee.util.Uuid;

/**
 * 阶段性成果 编辑界面
 * 
 * 由于需要关联进度汇报上的任务的ID来确保进度汇报只能打开本次汇报关联的数据把任务的真实ID存入sourcebillid上
 * 打开进主汇报时按sourcebillid来过滤，打开属性卡时按realateTask.id过滤
 */
public class AchievementManagerEditUI extends AbstractAchievementManagerEditUI {
	private static final Logger logger = CoreUIObject.getLogger(AchievementManagerEditUI.class);

	public AchievementManagerEditUI() throws Exception {
		super();
	}

	public void storeFields() {
		super.storeFields();
		editData.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
	}

	protected void prmtRelateTask_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception {
		super.prmtRelateTask_dataChanged(e);
		if (prmtRelateTask.getValue() == null) {
			prmtAchievementType.setValue("");
			prmtPath.setValue("");
		}
		if (editData.getAchievementType() != null) {
			return;
		}
		
		if (prmtRelateTask.getValue() != null) {
			String taskid = ((FDCScheduleTaskInfo) prmtRelateTask.getValue()).getId().toString();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("achievementType.id");
			sic.add("achievementType.name");
			sic.add("achievementType.docDirectory.id");
			sic.add("achievementType.docDirectory.name");
			sic.add("achievementType.docDirectory.docArea.id");
			sic.add("achievementType.docDirectory.docArea.name");
			FDCScheduleTaskInfo taskInfo = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskInfo(new ObjectUuidPK(taskid), sic);
			if (taskInfo != null && taskInfo.getAchievementType() != null) {
				prmtAchievementType.setValue(taskInfo.getAchievementType());
				if (taskInfo.getAchievementType() != null && taskInfo.getAchievementType().getDocDirectory() != null) {
					prmtPath.setValue(taskInfo.getAchievementType().getDocDirectory());
				}
			} else {
				FDCMsgBox.showError("当前任务的成果类型为空，请先在主/专项计划编制中选择成果类型后，再时行填报！");
				prmtRelateTask.setDataNoNotify(null);
				abort();
			}
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {	
		/*
		 * 审批后不能修改成果信息，因此这里需要判断
		 */
		if (editData.getState() == FDCBillStateEnum.AUDITTING || editData.getState() == FDCBillStateEnum.AUDITTED) {
			FDCMsgBox.showInfo("此成果类别已审批，不允许修改！");
			SysUtil.abort();
		}
		kdbtnAdd.setEnabled(true);
		kdbtndel.setEnabled(true);
		super.actionEdit_actionPerformed(e);
		// 修改后保存可用
		btnSave.setEnabled(true);
		if (OprtState.EDIT.equals(getOprtState())) {
			// 成果类别不允许修改
			prmtAchievementType.setEditable(false);
			prmtAchievementType.setEnabled(false);
			// 归档目录
			prmtPath.setEditable(false);
			prmtPath.setEnabled(false);
			// 审批，反审批
			btnAudit.setVisible(true);
			btnUnAudit.setVisible(true);
		}
		// 编辑状态的已提交单据 审批可用，其他全灰显
		if (OprtState.EDIT.equals(getOprtState()) && editData.getState().equals(FDCBillStateEnum.SUBMITTED)) {
			btnAudit.setEnabled(true);
		} else {
			btnAudit.setVisible(true);
			btnAudit.setEnabled(false);
		}
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		// 提示变化
		if (this.editData.getState().equals(FDCBillStateEnum.AUDITTED)) {
			FDCMsgBox.showInfo("已审批不能在审批");
			SysUtil.abort();
		}
		;
		super.actionAudit_actionPerformed(e);
		if (editData.getState() == FDCBillStateEnum.AUDITTED) {
			btnUnAudit.setVisible(true);
			btnUnAudit.setEnabled(true);
			btnAudit.setEnabled(false);
		}
				
		// 审批后将修改按钮灰线
		btnEdit.setEnabled(false);

		AttachmentCollection attCol = FDCAttachmentUtil.getAttachmentsInfo(editData.getId().toString());
		File[] toUpload = new File[attCol.size()];
		for (int i = 0; i < attCol.size(); i++) {
			File saveFile = FDCAttachmentUtil.downloadAtt(attCol.get(i).getId().toString(), null);
			toUpload[i] = saveFile;
		}
		// 业务单据Info，可以是单据或基础资料（用于设置文档编号、下载文档等）
		AchievementManagerInfo info = new AchievementManagerInfo();
		info.setId(editData.getId());
		// 标题
		String title = editData.getName();
		// 文档标识，可空
		String mark = null;
		// 文档上传的栏目
		// CategoryInfo categroy = (CategoryInfo)
		// (CategoryFactory.getRemoteInstance
		// ().getValue(editData.getDocID().toString()));
		// 文档类型，包括word、excel、PDF、HTML四种

		ContentTypeEnum enum2 = null;
		// 创建一个文档基本信息Info

		if (!FDCHelper.isEmpty(editData.getDocPath())) {
			ArchiveDocumentParamsInfo adpi = FDCCPDMUtil.createParams(editData, title, mark, editData.getDocPath(), enum2);
			try {
				FDCCPDMUtil.upLoadFileToCP(adpi, toUpload);
			} catch (Exception ex) {
				logger.error(ex);
			}
		}		
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		if (editData.getState() == FDCBillStateEnum.SUBMITTED) {
			btnAudit.setVisible(true);
			btnAudit.setEnabled(true);
			btnUnAudit.setEnabled(false);
			btnEdit.setEnabled(true);
		}		
	}

	/**
	 * 下面表格对应的按钮新政
	 */
	public void actionAdd_actionPerformed(ActionEvent e) throws Exception {
		super.actionAdd_actionPerformed(e);
		// 房地产附件管理工具类,包含上传、下载、查看、打开、删除附件等功能
		if (editData.getId() == null) {
			FDCMsgBox.showInfo("请先保存单据！");
			SysUtil.abort();
		}
		KDFileChooser chooser = new KDFileChooser(System.getProperty("user.home"));
		chooser.setFileSelectionMode(KDFileChooser.FILES_ONLY);
		chooser.setMultiSelectionEnabled(false);
		int retVal = chooser.showOpenDialog(this);
		if (retVal == KDFileChooser.CANCEL_OPTION) {
			return;
		}
		File upFile = chooser.getSelectedFile();
		// 上传一个文件到附件管理，并关联业务单据
		String attID = FDCAttachmentUtil.uploadAtt(editData.getId().toString(), upFile);
		// UIContext uicontext = new UIContext(this);
		// 给表格添加行显示上传的附件信息，set附件信息
		// 该方法取得一个业务单据关联的所有附件信息集合
		AttachmentCollection attCol = FDCAttachmentUtil.getAttachmentsInfo(editData.getId().toString());
		kDTable.removeRows();
		for (int i = 0; i < attCol.size(); i++) {
			
			IRow row = kDTable.addRow();
			row.getCell("attID").setValue(attCol.get(i).getId());
			row.getCell("attName").setValue(attCol.get(i).getName().substring(0, attCol.get(i).getName().length() - 6));
			row.getCell("attType").setValue(attCol.get(i).getType());
			row.getCell("attSize").setValue(attCol.get(i).getSize());
		}
	}

	public void actionDel_actionPerformed(ActionEvent e) throws Exception {

		super.actionDel_actionPerformed(e);
		int row = this.kDTable.getSelectManager().getActiveRowIndex();
		// 删除附件
		if (row < 0) {
			FDCMsgBox.showInfo("请选择需要删除的成果文档");
			SysUtil.abort();
		}
		if (!confirmRemove()) {
			return;
		}
		String attid = kDTable.getRow(row).getCell("attID").getValue().toString();
		FDCAttachmentUtil.deleteAtt(editData.getId().toString(), attid);
		kDTable.removeRow(row);
	}

	protected boolean confirmRemove() {
		return MsgBox.isYes(MsgBox.showConfirm2(this, "是否删除选择的成果文档？"));
	}

	/**
	 * output actionDownLoad_actionPerformed
	 */
	public void actionDownLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionDownLoad_actionPerformed(e);
		int row = this.kDTable.getSelectManager().getActiveRowIndex();
		if (row < 0) {
			FDCMsgBox.showInfo("请您选择想要下载的附件");
			SysUtil.abort();
		}
		String attId = this.kDTable.getRow(row).getCell("attID").getValue().toString();
		String attType = this.kDTable.getRow(row).getCell("attType").getValue().toString();
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("name");
		sic.add("simpleName");
		sic.add("sizeInByte");
		AttachmentInfo att = AttachmentFactory.getRemoteInstance().getAttachmentInfo(new ObjectUuidPK(attId), sic);

		KDFileChooser fc = new KDFileChooser(System.getProperty("user.home"));
		fc.setFileSelectionMode(KDFileChooser.FILES_ONLY);
		fc.setMultiSelectionEnabled(false);
		String fileName = StringUtils.cnulls(att.getName()) + "." + StringUtils.cnulls(att.getSimpleName());
		fc.setSelectedFile(new File(fileName));
		int retVal = fc.showSaveDialog(this);
		if (retVal == KDFileChooser.CANCEL_OPTION) {
			return;
		}
		// 若传入文件为空，则下载至临时文件夹并返回路径 参数为附件ID和下载后的文件
		File saveFile = null;
		saveFile = fc.getSelectedFile();
		FDCAttachmentUtil.downloadAtt(att, saveFile);
		FDCMsgBox.showInfo("成果文档下载完成");
	}

	public void actionOpen_actionPerformed(ActionEvent e) throws Exception {
		super.actionOpen_actionPerformed(e);
		int row = this.kDTable.getSelectManager().getActiveRowIndex();
		// 打开附件
		if (row < 0) {
			FDCMsgBox.showInfo("请选中想要打开的附件");
			SysUtil.abort();
		}
		// 传入附件ID
		String attId = kDTable.getRow(row).getCell("attID").getValue().toString();
		FDCAttachmentUtil.openAtt(attId);
	}

	protected void attachListeners() {
	}

	protected void detachListeners() {
	}

	protected ICoreBase getBizInterface() throws Exception {
		return AchievementManagerFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return kDTable;
	}

	protected KDTextField getNumberCtrl() {
		return txtName;
	}

	protected IObjectValue createNewData() {
		AchievementManagerInfo info = new AchievementManagerInfo();
		info.setNumber(Uuid.randomUUID().toString());
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		return info;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initF7();
		initBtn();

		if (OprtState.VIEW.equals(getOprtState()) && editData.getState().equals(FDCBillStateEnum.SUBMITTED)) {
			btnAudit.setEnabled(true);
		}
		if (OprtState.VIEW.equals(getOprtState())) {
			btnEdit.setEnabled(true);
			// 查看是，这两个按钮灰显
			kdbtnAdd.setEnabled(false);
			kdbtndel.setEnabled(false);
			// 成果类别不允许修改
			prmtAchievementType.setEditable(false);
			prmtAchievementType.setEnabled(false);
		}

		// 初始化页面加载该附件分录
		if (editData != null && editData.getId() != null) {
			AttachmentCollection attCol = FDCAttachmentUtil.getAttachmentsInfo(editData.getId().toString());
			for (int i = 0; i < attCol.size(); i++) {
				IRow row = kDTable.addRow();
				row.getCell("attID").setValue(attCol.get(i).getId().toString());
				row.getCell("attName").setValue(attCol.get(i).getName().substring(0, attCol.get(i).getName().length() - 6));
				row.getCell("attType").setValue(attCol.get(i).getType().toString());
				row.getCell("attSize").setValue(attCol.get(i).getSize().toString());
			}
		}
		// 进度管理过来的话，如何初始化
		// initFromScheduleManager();
		if (editData.getState() == FDCBillStateEnum.AUDITTED) {
			btnUnAudit.setEnabled(true);
		} else {
			btnUnAudit.setEnabled(false);
		}
		this.windowTitle = "阶段性成果编辑界面";
		// 状态为已审批时，也灰显
		if (editData != null && editData.getState() != null && editData.getState().equals(FDCBillStateEnum.AUDITTED)) {
			kdbtnAdd.setEnabled(false);
			kdbtndel.setEnabled(false);
			btnUnAudit.setEnabled(true);
			btnEdit.setEnabled(false);
		} else {
			btnUnAudit.setEnabled(false);
		}
		// 设置附件表格不能编辑
		kDTable.getStyleAttributes().setLocked(true);
		
		// 新增/查看/修改+已审批
		if ((OprtState.ADDNEW.equals(getOprtState())
				|| OprtState.EDIT .equals(getOprtState())
				|| OprtState.VIEW.equals(getOprtState()))
				&& editData.getState() != null
				&& editData.getState().equals(FDCBillStateEnum.AUDITTED)) {
			btnUnAudit.setEnabled(true);
		} else {
			btnUnAudit.setEnabled(false);
		}
		// 修改/查看+已提交
		if (OprtState.VIEW.equals(getOprtState()) && editData != null && editData.getState().equals(FDCBillStateEnum.SUBMITTED)) {
			btnAudit.setEnabled(true);
		} else {
			btnAudit.setEnabled(false);
		}
		initFromScheduleManager();
	}

	private void initFromScheduleManager() {
		if(getUIContext().get("isFromSchReport") != null ){
			boolean isFromSchReport = ((Boolean)(getUIContext().get("isFromSchReport"))).booleanValue();
			if(isFromSchReport){
				this.prmtRelateTask.setEnabled(false);
				this.prmtRelateTask.setValue(editData.getRelateTask());
			}
		}

	}

	public void onShow() throws Exception {
		super.onShow();
		// 反审批出来
		btnUnAudit.setVisible(true);
		controlButtonState();
		// onload 里面控制不了，写到这里
		if (OprtState.VIEW.equals(getOprtState())) {
			if (editData != null && editData.getState() != null && editData.getState().equals(FDCBillStateEnum.AUDITTED)) {
				btnEdit.setEnabled(false);
			} else {
				btnEdit.setEnabled(true);
			}
			// 查看是，这两个按钮灰显
			kdbtnAdd.setEnabled(false);
			kdbtndel.setEnabled(false);
			// 成果类别不允许修改
			prmtAchievementType.setEditable(false);
			prmtAchievementType.setEnabled(false);
		}
	}

	/**
	 * @discription <如果是以主(专)项计划编制与调整进入，设置为不能进行编辑操作>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/09/27>
	 */
	public void controlButtonState() {
		Object obj = this.getUIContext().get("enableState");
		if (null != obj && "enableState".equals(obj.toString().trim())) {
			this.btnEdit.setEnabled(false);
			this.btnAudit.setEnabled(false);
			this.btnAuditResult.setEnabled(false);
			this.btnUnAudit.setEnabled(false);
		}
	}

	private void initF7() {
		prmtCreator.setDisplayFormat("$person.name$");
		prmtCreator.setEditable(false);
		prmtCreator.setEnabled(false);
		prmtRelateTask.setQueryInfo("com.kingdee.eas.fdc.schedule.app.FDCScheduleTaskMyQuery");
		// 设置不可手动输入
		prmtRelateTask.setEditable(false);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo info = new FilterInfo();
		// 设置可以选择业务类型为阶段性成果的主项任务
		info.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
		info.getFilterItems().add(new FilterItemInfo("bizType1.ID", "Rz+dS7ECSfqM4kEJqGawYWLF6cA="));
		 if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof AchievementManagerListUI) {
			if (getUIContext() != null && getUIContext().get("curInfo") != null) {
				CurProjectInfo curInfo = (CurProjectInfo) getUIContext().get("curInfo");
				info.getFilterItems().add(new FilterItemInfo("project.name", curInfo.getName()));
			}
		}
		info.getFilterItems().add(new FilterItemInfo("schedule.projectSpecial.id", null, CompareType.EQUALS));
		prmtRelateTask.setEntityViewInfo(view);
		
		prmtRelateTask.setFilterInfoProducer(new IFilterInfoProducer(){

			public FilterInfo getFilterInfo() {
				FilterInfo info = new FilterInfo();
				info.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
				info.getFilterItems().add(new FilterItemInfo("bizType1.ID", "Rz+dS7ECSfqM4kEJqGawYWLF6cA="));
				 if (getUIContext().get("Owner") != null && getUIContext().get("Owner") instanceof AchievementManagerListUI) {
					if (getUIContext() != null && getUIContext().get("curInfo") != null) {
						CurProjectInfo curInfo = (CurProjectInfo) getUIContext().get("curInfo");
						info.getFilterItems().add(new FilterItemInfo("project.name", curInfo.getName()));
					}
				}
				info.getFilterItems().add(new FilterItemInfo("schedule.projectSpecial.id", null, CompareType.EQUALS));
				return info;
			}

			public void setCurrentCtrlUnit(CtrlUnitInfo cui) {
			}

			public void setCurrentMainBizOrgUnit(OrgUnitInfo oui, OrgType ot) {
			}});

		prmtRelateTask.setDisplayFormat("$name$");
		prmtCreator.setEditable(false);
		// 以后要删掉
		prmtAchievementType.setQueryInfo("com.kingdee.eas.fdc.schedule.app.AchievementTypeQuery");
		prmtAchievementType.setDisplayFormat("$name$");
		prmtAchievementType.setEditable(false);
		prmtAchievementType.setEnabled(false);
		// 因为归档目录可以自己编辑
		prmtPath.setQueryInfo("com.kingdee.eas.fdc.schedule.app.Copy_CategoryQuery");
		SelectorItemCollection col = new SelectorItemCollection();
		col.add(new SelectorItemInfo("name"));
		col.add(new SelectorItemInfo("id"));
		col.add(new SelectorItemInfo("docArea.name"));
		prmtPath.setSelectorCollection(col);
		prmtPath.setDisplayFormat("$docArea.name$ / $name$");
		prmtPath.setEditable(false);
		prmtPath.setEnabled(false);
	}

	private void initBtn() {
		kdbtnAdd.setEnabled(true);
		kdbtnAdd.setVisible(true);
		kDContainer.addButton(kdbtnAdd);
		kdbtndel.setEnabled(true);
		kdbtndel.setVisible(true);
		kDContainer.addButton(kdbtndel);
		kdbtndownload.setEnabled(true);
		kdbtndownload.setVisible(true);
		kDContainer.addButton(kdbtndownload);
		kdbtnopen.setEnabled(true);
		kdbtnopen.setVisible(true);
		kDContainer.addButton(kdbtnopen);
		// 屏蔽掉附件管理按钮
		btnAttachment.setEnabled(false);
		btnAttachment.setVisible(false);
		// 在阶段性成果管理的新增、查看、编辑界面中，功能按钮始终是不变的
		btnAudit.setVisible(true);
		btnUnAudit.setVisible(true);
		btnPrint.setVisible(true);
		btnPrintPreview.setVisible(true);
		btnPrint.setEnabled(true);
		btnPrintPreview.setEnabled(true);		
	}

	protected void initWorkButton() {
		super.initWorkButton();
		// 新增干掉
		btnAddNew.setEnabled(false);
		btnAddNew.setVisible(false);
		// 删除干掉
		btnRemove.setEnabled(false);
		btnRemove.setVisible(false);
		// 复制干掉
		btnCopy.setEnabled(false);
		btnCopy.setVisible(false);
		// 附件管理干掉
		btnAttachment.setEnabled(false);
		btnAttachment.setVisible(false);
		// 前一
		btnPre.setEnabled(false);
		btnPre.setVisible(false);
		// 第一
		btnFirst.setEnabled(false);
		btnFirst.setVisible(false);
		// 后一
		btnNext.setEnabled(false);
		btnNext.setVisible(false);
		// 最后
		btnLast.setEnabled(false);
		btnLast.setVisible(false);
		// 上查
		btnTraceUp.setEnabled(false);
		btnTraceUp.setVisible(false);
		// 下查
		btnTraceDown.setEnabled(false);
		btnTraceDown.setVisible(false);
		// 拉式生成
		btnCreateFrom.setEnabled(false);
		btnCreateFrom.setVisible(false);
		// 新增分录
		btnAddLine.setEnabled(false);
		btnAddLine.setVisible(false);
		// 下一步处理人
		btnNextPerson.setEnabled(false);
		btnNextPerson.setVisible(false);
		// 插入分录
		btnInsertLine.setEnabled(false);
		btnInsertLine.setVisible(false);
		// 删除分录
		btnRemoveLine.setEnabled(false);
		btnRemoveLine.setVisible(false);
		// 计算器
		btnCalculator.setEnabled(false);
		btnCalculator.setVisible(false);
		// 审批出来
		btnAudit.setVisible(true);
		btnAudit.setEnabled(true);

		// 多级干掉
		btnMultiapprove.setEnabled(false);
		btnMultiapprove.setVisible(false);
		
		kdbtnAdd.setIcon(EASResource.getIcon("imgTbtn_addline"));
		kdbtndel.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		kdbtndownload.setIcon(EASResource.getIcon("imgTbtn_downbill"));
		kdbtnopen.setIcon(EASResource.getIcon("imgTbtn_open"));
	}

	protected void initDataStatus() {
		super.initDataStatus();
		if (editData != null) {
			if (FDCBillStateEnum.SAVED.equals(editData.getState())) {
				btnAudit.setVisible(false);
				btnUnAudit.setVisible(false);
			} else if (FDCBillStateEnum.SUBMITTED.equals(editData.getState())) {
				btnAudit.setVisible(true);
				btnUnAudit.setVisible(false);
			} else if (FDCBillStateEnum.AUDITTING.equals(editData.getState())) {
				actionEdit.setEnabled(false);
				btnAudit.setVisible(false);
				btnUnAudit.setVisible(false);
			} else if (FDCBillStateEnum.AUDITTED.equals(editData.getState())) {
				btnAudit.setVisible(false);
				btnUnAudit.setVisible(true);
			}
		} else {
			btnAudit.setEnabled(false);
			btnUnAudit.setEnabled(false);
			btnAuditResult.setEnabled(false);
		}
		btnAudit.setVisible(true);
		btnUnAudit.setVisible(true);
	}

	public void loadFields() {
		super.loadFields();
		if(getUIContext().get("taskinfo")!=null){
			editData.setRelateTask((FDCScheduleTaskInfo) getUIContext().get("taskinfo"));
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("name"));
        
		sic.add(new SelectorItemInfo("docPath.id"));
    	sic.add(new SelectorItemInfo("docPath.number"));
		sic.add(new SelectorItemInfo("docPath.name"));
		
		sic.add(new SelectorItemInfo("creator.id"));
    	sic.add(new SelectorItemInfo("creator.number"));
		sic.add(new SelectorItemInfo("creator.name"));

		sic.add(new SelectorItemInfo("achievementType.id"));
		sic.add(new SelectorItemInfo("achievementType.number"));
		sic.add(new SelectorItemInfo("achievementType.name"));

		sic.add(new SelectorItemInfo("relateTask.id"));
		sic.add(new SelectorItemInfo("relateTask.number"));
		sic.add(new SelectorItemInfo("relateTask.name"));
		sic.add(new SelectorItemInfo("relateTask.srcID"));
    	
		sic.add(new SelectorItemInfo("achievementType.id"));
		sic.add(new SelectorItemInfo("achievementType.name"));
		sic.add(new SelectorItemInfo("achievementType.number"));
		sic.add(new SelectorItemInfo("creator.person.id"));
		sic.add(new SelectorItemInfo("creator.person.name"));
		sic.add(new SelectorItemInfo("creator.person.number"));
		return sic;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if (prmtRelateTask.getValue() == null) {
			FDCMsgBox.showInfo("请选择任务名称");
			abort();
		}
	}

	public boolean destroyWindow() {
		try {
			Object obj = getUIContext().get("Owner");
			if (obj instanceof ScheduleTaskProgressReportEditUI) {
				ScheduleTaskProgressReportEditUI ui = (ScheduleTaskProgressReportEditUI) obj;
				ui.addAchieveRow(editData, kDTable);
			} else if (obj instanceof AchievementManagerListUI) {
				AchievementManagerListUI ui = (AchievementManagerListUI) obj;
				ui.fillTable();
			} else if (obj instanceof FDCScheduleTaskPropertiesNewUI) {
				FDCScheduleTaskPropertiesNewUI parentUI = (FDCScheduleTaskPropertiesNewUI) obj;
				parentUI.refreshAchievementManagerTableData(editData);
			}
		} catch (BOSException e) {
			handUIException(e);
		}
		
		return super.destroyWindow();
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		if (editData.getState() == FDCBillStateEnum.SUBMITTED) {
			btnAudit.setEnabled(true);
		}
		setOprtState(OprtState.VIEW);
		// 提交后，显示出审批和反审批
		btnAudit.setVisible(true);
		btnUnAudit.setVisible(true);
	}
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		// setOprtState(OprtState.VIEW);
		btnAudit.setVisible(true);
		btnUnAudit.setVisible(true);
		btnSubmit.setEnabled(true);
		if (editData.getState() == FDCBillStateEnum.SUBMITTED) {
			btnAudit.setEnabled(true);
		}
		
	}
	private boolean isRunningWorkFlow() throws BOSException {
		return isRunningWorkflow(editData.getId().toString());

	}

	public static boolean isRunningWorkflow(String objId) throws BOSException {
		boolean hasWorkflow = false;
		IEnactmentService service2 = EnactmentServiceFactory.createRemoteEnactService();
		ProcessInstInfo procInsts[] = service2.getProcessInstanceByHoldedObjectId(objId);
		int i = 0;
		int n = procInsts.length;
		do {
			if (i >= n)
				break;
			if ("open.running".equals(procInsts[i].getState())) {
				hasWorkflow = true;
				break;
			}
			i++;
		} while (true);
		return hasWorkflow;
	}
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		if (isRunningWorkFlow()) {
			super.actionWorkFlowG_actionPerformed(e);
		} else {
			FDCMsgBox.showInfo("没有相应的工作流");
			abort();
		}
	}
	
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
        if (editData == null || editData.getId() == null) {
			FDCMsgBox.showError("请先保存单据后再进行打印！");
			abort();
		}
		AchievementMangerDataProvider dataPvd = new AchievementMangerDataProvider(editData.getString("id"), getTDQueryPK());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.print(getTDFileName(), dataPvd, javax.swing.SwingUtilities.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		 if (editData == null || editData.getId() == null) {
			FDCMsgBox.showError("请先保存单据后再进行预览！");
			abort();
		}
		AchievementMangerDataProvider dataPvd = new AchievementMangerDataProvider(editData.getString("id"), getTDQueryPK());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.printPreview(getTDFileName(), dataPvd, javax.swing.SwingUtilities.getWindowAncestor(this));
	}
	
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.schedule.app.AchievementManagerTDQuery");
	}

	protected String getTDFileName() {
		return "/bim/fdc/process/achievementmanager";
	}
}