/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;

import org.apache.log4j.Logger;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JHTMLEditor;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.LanguageManager;
import com.kingdee.bos.ctrl.excel.impl.facade.wizzard.WizzardIO;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.util.SimpleFileFilter;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
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
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.attachment.util.Resrcs;
import com.kingdee.eas.base.attachment.util.StringUtil4File;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.invite.AppraiseResultFactory;
import com.kingdee.eas.fdc.invite.InviteContentCollection;
import com.kingdee.eas.fdc.invite.InviteContentFactory;
import com.kingdee.eas.fdc.invite.InviteContentInfo;
import com.kingdee.eas.fdc.invite.InviteFileItemCollection;
import com.kingdee.eas.fdc.invite.InviteFileItemEntryFactory;
import com.kingdee.eas.fdc.invite.InviteFileItemEntryInfo;
import com.kingdee.eas.fdc.invite.InviteFileItemFactory;
import com.kingdee.eas.fdc.invite.InviteFileItemInfo;
import com.kingdee.eas.fdc.invite.InviteFileItemTypeEnum;
import com.kingdee.eas.fdc.invite.InviteGetUserPortalFacadeFactory;
import com.kingdee.eas.fdc.invite.InviteProjectException;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.InviteProjectStateEnum;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.TemplateFileCategoryFactory;
import com.kingdee.eas.fdc.invite.TemplateFileCategoryInfo;
import com.kingdee.eas.fdc.invite.TemplateFileInfo;
import com.kingdee.eas.fdc.invite.client.TreeUtil.ISearchTreeEvent;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.framework.client.workflow.DefaultWorkflowUIEnhancement;
import com.kingdee.eas.framework.client.workflow.IWorkflowUIEnhancement;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.enums.EnumUtils;

/**
 * "技术条款"、"图纸目录、"邀请书/招标须知、"合同条款"  共用的编辑界面
 */
public class InviteFileItemEditUI extends AbstractInviteFileItemEditUI {
	private boolean alreadySaved = false;
	private boolean isReversion = false;

	private static final Logger logger = CoreUIObject
			.getLogger(InviteFileItemEditUI.class);

	private MyWebBrowser htmlEditor = null;

	// 如果将其定义为局部变量，则要求给为final。
	private List fileCategoryCols = new ArrayList();

	private InviteFileItemTypeEnum curItemType = InviteFileItemTypeEnum.TECHCLAUSE;

	private FullOrgUnitInfo orgUnitInfo = SysContext.getSysContext()
			.getCurrentFIUnit().castToFullOrgUnitInfo();

	private String UI_CLASS_PARAM = null;
	private boolean canSelectOtherOrgPerson = false;

	/**
	 * output class constructor
	 */
	public InviteFileItemEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();

		// TODO 这段代码会造成，编辑进入没有修改，直接退出也会提示是否要保存，
		// 因为修改了editData， By Owen_wen 2011-05-06
		storeTreeNodes(this.treeCategory); 
	}

	protected void lockContainer(java.awt.Container container) {
		if (kDLabelContainer1.getName().equals(container.getName())) {
			return;
		} else {
			super.lockContainer(container);
		}
	}

	/**
	 * 当选择不同的文件模板类型，如果之前的已经修改，先提示用户是否确定更改 如果不更改直接返回，如果更改就跟新数据
	 */
	protected void prmtFileTemplate_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e)
			throws Exception {
		if (e.getNewValue() == null) {
			initCategoryTree();

			return;
		}

		TemplateFileInfo oldFileInfo = (TemplateFileInfo) e.getOldValue();
		TemplateFileInfo newFileInfo = (TemplateFileInfo) e.getNewValue();

		if (oldFileInfo == null) {
			refreshCategoryTree(getTemplateFileFilterForTree(newFileInfo), getSelectorForTree(),
					TemplateFileCategoryFactory.getRemoteInstance());
		} else {
			if (!newFileInfo.getId().equals(oldFileInfo.getId())) {
				if (MsgBox.isYes(MsgBox.showConfirm2("选择不同的文件后，已编辑的文件目录会被删除，确定改变吗?"))) {
					// refreshCategoryTree(getTemplateFileFilterForTree(
					// newFileInfo),
					// getSelectorForTree(),TemplateFileCategoryFactory.
					// getRemoteInstance());
					refreshCategoryTree(getTemplateFileFilterForTree(newFileInfo), getSelectorForTree(),
							TemplateFileCategoryFactory.getRemoteInstance());
				} else {
					return;
				}
			}
		}

	}

	/**
	 * 当选择不同的招标立项后，设置相关的值供其使用
	 */

	protected void prmtInviteProject_dataChanged(DataChangeEvent e)
			throws Exception {
		Object newProject = e.getNewValue();
		//Update by zhiyuan_tang 2010/11/20 
		//根本就没有地方给UIContext的billType赋值，原始版本修改界面提交时，提示此招标立项已经使用，修改成取界面上的文件类型
//		Object billType = getUIContext().get("billType");
		InviteFileItemTypeEnum billType = (InviteFileItemTypeEnum)comboFileItemType.getSelectedItem();
		
		if (newProject != null) {
			if (newProject instanceof InviteProjectInfo) {
				InviteProjectInfo project = (InviteProjectInfo) newProject;

				// 修改为自动带出立项编码 update by msb 2010-04-26
				this.txtName.setText(project.getNumber());

				// 不能再次选择的判断 add by msb 2010/04/30
				if (!this.isFirstOnload()) {
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("inviteProject.id", project.getId().toString()));
					// 文件类型过滤
					if (billType != null) {
						filter.getFilterItems().add(new FilterItemInfo("fileItemType", billType.getValue()));
					}
					if (editData.getId() != null)
						filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString(), CompareType.NOTEQUALS));
					if (!isReversion) {
						if (this.getBizInterface().exists(filter)) {
							FDCMsgBox.showWarning(this, "此招标立项已经使用，请重新选择");
							
							// 去掉监听 Added By Owen_wen 2010-11-03
							EventListener[] listeners = prmtInviteProject.getListeners(DataChangeListener.class);
							for(int i=0;i<listeners.length;i++){
								prmtInviteProject.removeDataChangeListener((DataChangeListener)listeners[i]);
							}
							prmtInviteProject.setValue(e.getOldValue());
							// 再加上监听 Added By Owen_wen 2010-11-03
							for(int i=0;i<listeners.length;i++){
								prmtInviteProject.addDataChangeListener((DataChangeListener)listeners[i]);
							}
							
							if (e.getOldValue() == null) {
								this.prmtCurProject.setValue(null);
							}
							return;
						}
					}
					
				/*	if (project.getProject() != null) {
						this.prmtCurProject.setData(project.getProject());
					}*/
				}
				
				/**
				 * 由于保存的时候会判断编码是否已经存在， 因此加上当前的文件类型和所选的立项项目编码和作为新的编码
				 */
				// this.txtNumber.setText(project.getNumber()+curItemType.getValue());

				// if(project.getProject() != null &&
				// project.getProject().getId() != null)
				// {
				// IObjectPK pk = new
				// ObjectUuidPK(project.getProject().getId().toString());
				//
				// CurProjectInfo curProjectInfo =
				// CurProjectFactory.getRemoteInstance().getCurProjectInfo(pk);
				// this.prmtCurProject.setValue(curProjectInfo);
				// }
			/*	this.prmtCurProject.setValue(project.getProject());
				if (((InviteProjectInfo) newProject).getInviteMode() != null)
					this.prmtInviteMode.setValue(project.getInviteMode().getName());*/
				if (((InviteProjectInfo) newProject).getOrgUnit() != null)
					this.txtInviteProjectOrg.setText(project.getOrgUnit().getName());
			}
		} else {
			this.txtName.setText(null);
			this.prmtCurProject.setValue(null);
			this.prmtInviteMode.setDataNoNotify(null);
			this.txtInviteProjectOrg.setText(null, false);
		}
	}

	/**
	 * 根据listUI中传过来的文件类型，重新设置界面的title
	 */
	protected void reSetUITitle() {
		if (getUIContext().get("INVITEFILEITEMTYPE") != null) {
			InviteFileItemTypeEnum tempEnum = InviteFileItemTypeEnum.getEnum(String.valueOf(getUIContext().get(
					"INVITEFILEITEMTYPE")));
			String title = "";
			if (InviteFileItemTypeEnum.TECHCLAUSE.equals(tempEnum)) {
				title = title + "技术条款";

				curItemType = InviteFileItemTypeEnum.TECHCLAUSE;

			} else if (InviteFileItemTypeEnum.BLUEPRINTCONTENT.equals(tempEnum)) {
				title = title + "图纸目录";
				curItemType = InviteFileItemTypeEnum.BLUEPRINTCONTENT;
			} else if (InviteFileItemTypeEnum.INVITEINSTRUCTION.equals(tempEnum)) {
				title = title + "邀请书/招标须知";
				curItemType = InviteFileItemTypeEnum.INVITEINSTRUCTION;
			} else if (InviteFileItemTypeEnum.CONTRACTCLAUSE.equals(tempEnum)) {
				title = title + "合同条款序";
				curItemType = InviteFileItemTypeEnum.CONTRACTCLAUSE;
			} else {
				title = title + getUIContext().get("INVITEFILEITEMTYPE");
			}

			setUITitle(title);
		}
	}

	protected void fetchInitParam() throws Exception {

		if (company == null) {
			return;
		}
		// 启用成本财务一体化
		Map paramItem = (Map) ActionCache.get("FDCBillEditUIHandler.comParamItem");
		if (paramItem == null) {
			paramItem = FDCUtils.getDefaultFDCParam(null, company.getId().toString());
		}
		if (paramItem.get(FDCConstants.FDC_PARAM_SELECTPERSON) != null) {
			this.canSelectOtherOrgPerson = Boolean.valueOf(
					paramItem.get(FDCConstants.FDC_PARAM_SELECTPERSON).toString()).booleanValue();
		}
		if (getUIContext().get("INVITEFILEITEMTYPE") != null) {
			UI_CLASS_PARAM = String.valueOf(getUIContext().get("INVITEFILEITEMTYPE"));
		}
	}

	public void onLoad() throws Exception {

		if (getOprtState().equals(OprtState.VIEW)) {
			this.alreadySaved = true;
		}

		prmtInviteMode.setEnabled(false);
		beforeActionCheckPermission(null);
		super.onLoad();
		// 显示嵌入的文本编辑器
		showEditor(null);
		// 立项编号、立项名称、文件类型、所属项目
		if (editData.getVersion() > 1.0) {
			isReversion = true;
		}

		this.comboAttachmentNameList.setEditable(false);
		this.btnViewAttachment.setEnabled(true);

		reSetUITitle();

		// 初始化toolbar和小控件的状态
		initHeadStyle();

		// 初始化立项名称[修改为取编码 update by msb 2010/04/26]
		if (editData != null && editData.getInviteProject() != null) {
			this.txtName.setText(editData.getInviteProject().getNumber());
		}

		// 初始化文件目录树
		initFileCategoryArea();
		Component[] components = this.menuTable1.getPopupMenu().getComponents();

		for (int i = components.length - 1; i >= 0; --i) {
			this.menuTable1.remove(i);
		}
		this.actionCalculator.setVisible(false);
		// 新增状态不用户上传正文和查看标书
		if (this.getOprtState() == OprtState.ADDNEW) {

			this.actionUpLoadFile.setEnabled(false);
			this.actionViewFile.setEnabled(false);
			this.actionAttachment.setEnabled(false);

		}

		if (getOprtState().equals(OprtState.VIEW)) {
			if (!checkCanOperate()) {
				this.actionAddNew.setEnabled(false);
				this.actionEdit.setEnabled(false);
				this.actionRemove.setEnabled(false);
				this.actionAttachment.setEnabled(false);

				this.actionUpLoadFile.setEnabled(false);
				this.actionViewFile.setEnabled(false);
			} else {

				if (editData.getState().equals(FDCBillStateEnum.SAVED)) {
					this.actionAddNew.setEnabled(true);
					this.actionEdit.setEnabled(true);
					this.actionSave.setEnabled(false);
					this.actionSubmit.setEnabled(false);
					this.actionRemove.setEnabled(true);
					this.actionAttachment.setEnabled(true);

					this.actionUpLoadFile.setEnabled(true);
					this.actionViewFile.setEnabled(true);

				} else if (editData.getState().equals(
						FDCBillStateEnum.SUBMITTED)) {
					this.actionAddNew.setEnabled(true);
					this.actionEdit.setEnabled(true);
					this.actionSave.setEnabled(false);
					this.actionSubmit.setEnabled(false);
					this.actionRemove.setEnabled(true);
					this.actionAttachment.setEnabled(true);

					this.actionUpLoadFile.setEnabled(true);
					this.actionViewFile.setEnabled(true);
				} else {
					this.actionAddNew.setEnabled(true);
					this.actionEdit.setEnabled(false);
					this.actionSubmit.setEnabled(false);
					this.actionRemove.setEnabled(false);
					this.actionAttachment.setEnabled(true);

					this.actionUpLoadFile.setEnabled(false);
					this.actionViewFile.setEnabled(false);
				}
			}
		}

		this.prmtFileTemplate.setRequired(false);

		if (editData != null
				&& FDCBillStateEnum.SUBMITTED.equals(editData.getState())) {
			this.actionSave.setEnabled(false);
		}

		// 如果附件名列表中没有数据，那么查看附件按钮置灰 zouwen
		boolean hasAttachment = getAttachmentNamesToShow();
		if (!hasAttachment) {
			this.btnViewAttachment.setEnabled(false);
			this.btnViewAttachment.setVisible(true);
		} else {
			this.btnViewAttachment.setEnabled(true);
			this.btnViewAttachment.setVisible(true);
		}
		// this.comboAttachmentNameList.setEditable(true);
		// 如果是新增单据，"查看正文"按钮也置灰(因为如果是"新增单据"附件列表一定为空，所以不需要再判断"查看附件"按钮)
		if (STATUS_ADDNEW.equals(getOprtState())) {
			this.btnViewContrnt.setEnabled(false);
			this.btnViewContrnt.setVisible(true);
		}

		if (this.getUIContext().containsKey("CANTADDNEW")) {
			this.actionAddNew.setEnabled(false);
		}

		if (this.getUIContext().containsKey("CANVIEW")) {
			this.btnViewFile.setEnabled(true);
		}

		this.comboAttachmentNameList.setEnabled(true);
		this.comboAttachmentNameList.setReadOnly(false);

		if (isReversion) {
			this.prmtInviteProject.setEnabled(false);
			this.prmtCurProject.setEnabled(false);
			this.comboFileItemType.setEnabled(false);
		}
	}

	// 根据业务单据的ID得到该单据所有的关联附件并且将附件名称显示在下拉列表框中
	// 因为UI编辑区的"查看附件"和"查看正文"都只是在工作流中用来查看,因此禁止"维护"，故不需要处理权限。zouwen

	private boolean getAttachmentNamesToShow() throws Exception {
		this.comboAttachmentNameList.removeAllItems();
		String boID = getSelectBOID();
		boolean hasAttachment = false;
		if (boID == null) {
			return hasAttachment;
		}
		SelectorItemCollection itemColl = new SelectorItemCollection();
		itemColl.add(new SelectorItemInfo("id"));
		itemColl.add(new SelectorItemInfo("attachment.name"));
		itemColl.add(new SelectorItemInfo("attachment.id"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(itemColl);

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems()
				.add(new FilterItemInfo("boID", getSelectBOID()));
		view.setFilter(filter);

		BoAttchAssoCollection boAttchColl = BoAttchAssoFactory
				.getRemoteInstance().getBoAttchAssoCollection(view);
		if (boAttchColl != null && boAttchColl.size() > 0) {
			hasAttachment = true;
			this.btnViewAttachment.setEnabled(true);// 改变btnViewAttachment可以操作
			for (int i = 0; i < boAttchColl.size(); i++) {
				AttachmentInfo attachment = (AttachmentInfo) boAttchColl.get(i)
						.getAttachment();
				this.comboAttachmentNameList.addItem(attachment);
				this.comboAttachmentNameList.setUserObject(attachment);
			}
		}
		return hasAttachment;
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

	protected void attachListeners() {
	}

	protected void detachListeners() {

	}

	protected ICoreBase getBizInterface() throws Exception {
		return InviteFileItemFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		KDTextField number = new KDTextField();
		number.setVisible(false);
		return number;
	}

	protected IObjectValue createNewData() {
		InviteFileItemInfo fileItem = new InviteFileItemInfo();
		fileItem.setId(BOSUuid.create(fileItem.getBOSType()));

		if (getUIContext().get("isReversion") != null) {
			isReversion = Boolean.valueOf(
					getUIContext().get("isReversion").toString())
					.booleanValue();
		}

		if (getUIContext().get("INVITEFILEITEMTYPE") != null) {
			InviteFileItemTypeEnum tempEnum = InviteFileItemTypeEnum
					.getEnum(getUIContext().get("INVITEFILEITEMTYPE")
							.toString());
			fileItem.setFileItemType(tempEnum);
		}
		fileItem.setState(FDCBillStateEnum.SAVED);

		fileItem.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		// try {
		// fileItem.setCreateTime(FDCDateHelper.getServerTimeStamp());
		// } catch (BOSException e) {
		// e.printStackTrace();
		// }

		fileItem.setOrgUnit(SysContext.getSysContext().getCurrentFIUnit()
				.castToFullOrgUnitInfo());
		if (isReversion) {
			if (getUIContext().get("initData") != null) {
				fileItem = (InviteFileItemInfo) getUIContext().get("initData");
			}
		}
		this.getUIContext().remove("initData");
		this.getUIContext().remove("isReversion");
		return fileItem;
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		InviteFileItemEntryInfo itemEntryInfo = new InviteFileItemEntryInfo();
		return itemEntryInfo;
	}

	/**
	 * 初始化toolbar按钮的状态和单价头的控件的状态
	 */
	protected void initHeadStyle() {
		this.actionPre.setEnabled(false);
		this.actionPre.setVisible(false);
		this.actionFirst.setEnabled(false);

		this.actionFirst.setVisible(false);
		//当删除第一条记录时，报记录不存在的错。原因是将actionNext设置成不可用（enabled= false）,
		//删除时会根据这个actionNext的状态来判断是取前一行还是后一行
//		this.actionNext.setEnabled(false);
		this.actionNext.setVisible(false);

		this.actionLast.setVisible(false);
		this.actionLast.setEnabled(false);

		this.actionTraceUp.setEnabled(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setEnabled(false);
		this.actionTraceDown.setVisible(false);

		this.contFileItemType.setEnabled(false);
		this.comboFileItemType.setEnabled(false);

		this.contName.setEnabled(false);
		this.txtName.setEnabled(false);
		this.contCurProject.setEnabled(false);
		this.prmtCurProject.setEnabled(false);

		this.actionCopyFrom.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);

		this.actionSubmitOption.setVisible(false);

		Component[] components = this.menuWorkflow.getPopupMenu()
				.getComponents();

		for (int i = components.length - 1; i >= 0; --i) {
			this.menuWorkflow.remove(i);
		}

		this.actionAddLine.setEnabled(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setEnabled(false);
		this.actionRemoveLine.setVisible(false);
		this.actionInsertLine.setEnabled(false);
		this.actionInsertLine.setVisible(false);

		this.prmtInviteProject.setDisplayFormat("$name$");
		
		//update by david_yang PT043562 2011.04.02 
		this.prmtInviteProject.setCommitFormat("$name$");
		this.prmtInviteProject.setEditFormat("$name$");

		this.prmtInviteProject
				.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7InviteProjectQuery");

		this.prmtRespDept.setRequired(true);
		this.prmtInviteProject.setRequired(true);

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

		prmtFileTemplate.addSelectorListener(new SelectorListener() {

			public void willShow(SelectorEvent e) {
				try {
					setTemplateFileFilter(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});

		this.contCategory.setEnableActive(false);
		this.contCategory.setTitle("文件目录");

		this.prmtFileTemplate.setRequired(true);

		this.prmtFileTemplate.setDisplayFormat("$number$ $name$");
		this.prmtFileTemplate.setCommitFormat("$number$");
		this.prmtFileTemplate.setEditFormat("$number$");

		this.prmtFileTemplate
				.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7TemplateFileQuery");

		this.actionCopy.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);

		String cuId = SysContext.getSysContext().getCurrentCtrlUnit().getId()
				.toString();
		FDCClientUtils.setRespDeptF7(prmtRespDept, this,
				canSelectOtherOrgPerson ? null : cuId);
		
		//add by david_yang PT043562 2011.04.02 (扩充name到255个字符)
		txtName.setMaxLength(255);

	}

	protected void setTemplateFileFilter(SelectorEvent e) throws Exception {
		EntityViewInfo view = new EntityViewInfo();

		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("state"));
		view.getSelector().add(new SelectorItemInfo("templateType.name"));
		view.getSelector().add(new SelectorItemInfo("number"));
		view.getSelector().add(new SelectorItemInfo("name"));

		FilterInfo filter = new FilterInfo();

		// 状态过滤
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));

		view.setFilter(filter);
		prmtFileTemplate.setEntityViewInfo(view);

		if (prmtInviteProject.getSelector() != null
				&& prmtFileTemplate.getSelector() instanceof com.kingdee.eas.framework.client.ListUI) {
			((com.kingdee.eas.framework.client.ListUI) prmtFileTemplate.getSelector()).setFilterForQuery(filter);
			((com.kingdee.eas.framework.client.ListUI) prmtFileTemplate.getSelector()).onLoad();

		} else {
			if (filter.getFilterItems().size() <= 0) {
				return;
			}
			prmtFileTemplate.getEntityViewInfo().setFilter(filter);
			prmtFileTemplate.getQueryAgent().resetRuntimeEntityView();
			prmtFileTemplate.setRefresh(true);
		}
	}

	/**
	 * 如果是新增界面直接创建一颗空的目录树 如果是编辑或者查看的话构建一个看文件模块分录的树
	 * 
	 * @throws Exception
	 */
	protected void initFileCategoryArea() throws Exception {
		if (OprtState.ADDNEW.equals(getOprtState())) {
			if (!isReversion) {
				initCategoryTree();
			} else {
				refreshCategoryTree(getInviteFileItemFilterForTree(editData),
						getSelectorForTree(), InviteFileItemEntryFactory
								.getRemoteInstance());
			}

		} else if (OprtState.EDIT.equals(getOprtState())
				|| OprtState.VIEW.equals(getOprtState())) {
			refreshCategoryTree(getInviteFileItemFilterForTree(editData),
					getSelectorForTree(), InviteFileItemEntryFactory
							.getRemoteInstance());
		}
	}

	/**
	 * 获取所选的当前组织 可以不用通过传递获取，通过获取系统上下文中的当前财务组织
	 * 
	 * @return
	 */
	private OrgStructureInfo getSelectOrgUnit() {
		OrgStructureInfo orgUnitInfo = null;
		if (getUIContext().get("ORG_UNIT") != null && getUIContext().get("ORG_UNIT") instanceof OrgStructureInfo) {
			orgUnitInfo = (OrgStructureInfo) (getUIContext().get("ORG_UNIT"));
		}

		return orgUnitInfo;
	}

	protected SelectorItemCollection getInviteProjectNewSelector() {
		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("state"));

		sic.add(new SelectorItemInfo("project.id"));
		sic.add(new SelectorItemInfo("project.number"));
		sic.add(new SelectorItemInfo("project.name"));

		sic.add(new SelectorItemInfo("inviteForm"));
		sic.add(new SelectorItemInfo("respPerson.number"));
		sic.add(new SelectorItemInfo("prjDate"));

		sic.add(new SelectorItemInfo("inviteType.longNumber"));
		sic.add(new SelectorItemInfo("inviteType.id"));
		sic.add(new SelectorItemInfo("inviteType.number"));
		sic.add(new SelectorItemInfo("inviteType.name"));

		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("inviteProjectState"));
		sic.add(new SelectorItemInfo("orgUnit.id"));
		sic.add(new SelectorItemInfo("inviteMode.*"));
		sic.add(new SelectorItemInfo("orgUnit.*"));

		return sic;
	}

	/**
	 * 设置招标立项F7的过滤条件 该F7的过滤条件包括四个： 1：所选的当前组织 2：所选的招标立项类型 3：文件类型
	 * 4：已经被引用过的招标立项不能重复使用，即一个招标立项立项之多只能对应一个招标文件模块 5:
	 * 取消4中的过滤，添加未被“已审批”【评标结果报审】引用 update by msb 2010-4-26
	 * 
	 * @param e
	 * @throws Exception
	 */
	protected void setInviteProjectFilter(SelectorEvent e) throws Exception {
		EntityViewInfo view = new EntityViewInfo();

		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("number"));
		view.getSelector().add(new SelectorItemInfo("name"));
		view.getSelector().add(new SelectorItemInfo("state"));

		view.getSelector().add(new SelectorItemInfo("project.id"));
		view.getSelector().add(new SelectorItemInfo("project.number"));
		view.getSelector().add(new SelectorItemInfo("project.name"));

		view.getSelector().add(new SelectorItemInfo("inviteForm"));
		view.getSelector().add(new SelectorItemInfo("respPerson.number"));
		view.getSelector().add(new SelectorItemInfo("prjDate"));

		view.getSelector().add(new SelectorItemInfo("inviteType.longNumber"));
		view.getSelector().add(new SelectorItemInfo("inviteType.id"));
		view.getSelector().add(new SelectorItemInfo("inviteType.number"));
		view.getSelector().add(new SelectorItemInfo("inviteType.name"));

		view.getSelector().add(new SelectorItemInfo("state"));
		view.getSelector().add(new SelectorItemInfo("inviteProjectState"));
		view.getSelector().add(new SelectorItemInfo("orgUnit.id"));

		prmtInviteProject.setSelectorCollection(getInviteProjectNewSelector());

		FilterInfo filter = new FilterInfo();

		// 状态过滤:已通过审批，非已审批
		// update by msb 2010/04/29
		Set states = getPassAuditStateForInvProject();
		if(!states.isEmpty()){
			filter.getFilterItems().add(
					new FilterItemInfo("inviteProjectState",states,CompareType.INCLUDE));
		}
		// filter.getFilterItems().add(
		// new FilterItemInfo("inviteProjectState",
		// InviteProjectStateEnum.AUDITTED_VALUE));
		
		filter.getFilterItems().add(
				new FilterItemInfo("parent.id", null, CompareType.EQUALS));

		// 选择当前组织下的招标立项
		if (orgUnitInfo != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("orgUnit.id", orgUnitInfo.getId()
							.toString()));
		}

		// 招标立项自身的过滤条件

		// 招标类型过滤条件
		if (getSelectInviteType() != null
				&& (getSelectInviteType() instanceof com.kingdee.eas.fdc.invite.InviteTypeInfo)) {
			FilterInfo typeFilter = new FilterInfo();
			InviteTypeInfo typeInfo = (InviteTypeInfo) getSelectInviteType();
			BOSUuid id = typeInfo.getId();

			Set idSet = getInviteTypeIdSet(id);
			filter.getFilterItems().add(
					new FilterItemInfo("inviteType.id", idSet,
							CompareType.INCLUDE));

		}

		// 已定标过滤 add by msb 2010/04/26
		Set appraisedPrjSet = getAppraisedInvireProjectId();
		if ((appraisedPrjSet != null) && (!appraisedPrjSet.isEmpty())) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", appraisedPrjSet,
							CompareType.NOTINCLUDE));
		}

		view.setFilter(filter);
		prmtInviteProject.setEntityViewInfo(view);

		if (prmtInviteProject.getSelector() != null
				&& prmtInviteProject.getSelector() instanceof com.kingdee.eas.framework.client.ListUI) {
			((com.kingdee.eas.framework.client.ListUI) prmtInviteProject.getSelector()).setFilterForQuery(filter);
			((com.kingdee.eas.framework.client.ListUI) prmtInviteProject.getSelector()).onLoad();

		} else {
			if (filter.getFilterItems().size() <= 0) {
				return;
			}
			prmtInviteProject.getEntityViewInfo().setFilter(filter);
			prmtInviteProject.getQueryAgent().resetRuntimeEntityView();
			prmtInviteProject.setRefresh(true);
		}
	}

	/**
	 * 获取已定标的招标立项 （已审核的投标结果）
	 * 
	 * @author msb 2010/4/26
	 * @return
	 * @throws Exception
	 */
	private Set getAppraisedInvireProjectId() throws Exception {
		Set idSet = AppraiseResultFactory.getRemoteInstance().getInviteProjectId();
		return idSet;
	}

	/**
	 * 获取通过审批且定标之前的招标立项的状态
	 * @author msb 2010/04/29
	 * 1SAVED|2SUBMITED|3AUDITTING|4AUDITTED|5FILEMAKING|
	 * 6ANSWERING|7APPRAISING|8FIXED|9SIGNEDCONTRACT
	 * @return
	 */
	private Set getPassAuditStateForInvProject(){
		Set prjStateSet = new HashSet();
		List lstStatus = EnumUtils.getEnumList(InviteProjectStateEnum.class);
		for(Iterator iterator = lstStatus.iterator();iterator.hasNext();){
			InviteProjectStateEnum state = (InviteProjectStateEnum)iterator.next();
			String stateStr = state.getValue();
			if(stateStr.length()>=1){
				int seq = Integer.parseInt(stateStr.substring(0, 1));
				if(seq >=4 && seq <=7){
					prjStateSet.add(stateStr);
				}
			}
		}
		return prjStateSet;
	}
	/**
	 * 过滤掉已经被选择建立相应的文件模块的招标立项 不尽是关联了项目的ID，而且还有要是同一类型下的招标立项
	 * 
	 * @throws BOSException
	 */
	private Set getFilterInviteProjectId() throws BOSException {
		FilterInfo projectFilter = new FilterInfo();

		if (getSelectOrgUnit() != null) {
			OrgStructureInfo orgUnit = getSelectOrgUnit();
			String orgUnitLongNumber = orgUnit.getLongNumber();

			// FilterInfo filterOrgUnit = new FilterInfo();
			// filterOrgUnit.getFilterItems().add(new
			// FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "!%",
			// CompareType.LIKE));
			projectFilter.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber));

			projectFilter.getFilterItems().add(
					new FilterItemInfo("inviteProjectState", InviteProjectStateEnum.AUDITTED_VALUE));
			// filterOrgUnit.setMaskString("#0 or #1");

			// projectFilter.mergeFilter(filterOrgUnit, "and");
		}

		FilterInfo fileType = new FilterInfo();
		fileType.getFilterItems().add(new FilterItemInfo("fileItemType", curItemType));

		projectFilter.mergeFilter(fileType, "and");

		EntityViewInfo prjView = new EntityViewInfo();
		prjView.setFilter(projectFilter);

		InviteFileItemCollection itemCols = InviteFileItemFactory.getRemoteInstance()
				.getInviteFileItemCollection(prjView);

		Iterator itemIter = itemCols.iterator();

		Set itemIdSet = new HashSet();
		while (itemIter.hasNext()) {
			InviteFileItemInfo tmpInfo = (InviteFileItemInfo) itemIter.next();
			itemIdSet.add(tmpInfo.getInviteProject().getId().toString());
		}

		return itemIdSet;
	}

	private void setLookAndFeel() {
		try {
			String systemLookAndFeelClassName = UIManager
					.getSystemLookAndFeelClassName();
			if (!"com.kingdee.bos.ctrl.swing.plaf.KingdeeLookAndFeel"
					.equals(systemLookAndFeelClassName)) {
				UIManager
						.setLookAndFeel("com.kingdee.bos.ctrl.swing.plaf.KingdeeLookAndFeel");
			}
		} catch (Exception e) {
			handUIException(e);
		}
	}

	/**
	 * 显示文本编辑器 通过实现URL调用服务端第三方已经实现的好网页将其嵌套在该界面中。
	 * 
	 * @param initText
	 */
	private void showEditor(String initText) {
		setLookAndFeel();
		
		if (!NativeInterface.isOpen())
			NativeInterface.open();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				String configurationScript = "FCKConfig.ToolbarSets[\"Default\"] = [ [&apos;Preview&apos;,&apos;-&apos;,&apos;Templates&apos;],"
						+ "[&apos;Cut&apos;,&apos;Copy&apos;,&apos;Paste&apos;,&apos;PasteText&apos;,&apos;PasteWord&apos;,&apos;-&apos;,&apos;SpellCheck&apos;],"
						+ "[&apos;Undo&apos;,&apos;Redo&apos;,&apos;-&apos;,&apos;Find&apos;,&apos;Replace&apos;,&apos;-&apos;,&apos;SelectAll&apos;,&apos;RemoveFormat&apos;],"
						+ "[&apos;TextField&apos;,&apos;Textarea&apos;,&apos;ImageButton&apos;,&apos;HiddenField&apos;],"
						+ "&apos;/&apos;,"
						+ "[&apos;Bold&apos;,&apos;Italic&apos;,&apos;Underline&apos;,&apos;StrikeThrough&apos;,&apos;-&apos;,&apos;Subscript&apos;,&apos;Superscript&apos;],"
						+ "[&apos;OrderedList&apos;,&apos;UnorderedList&apos;,&apos;-&apos;,&apos;Outdent&apos;,&apos;Indent&apos;,&apos;Blockquote&apos;,&apos;CreateDiv&apos;],"
						+ "[&apos;JustifyLeft&apos;,&apos;JustifyCenter&apos;,&apos;JustifyRight&apos;,&apos;JustifyFull&apos;],"
						+ "[&apos;Link&apos;,&apos;Unlink&apos;,&apos;Anchor&apos;],"
						+ "[&apos;Image&apos;,&apos;Flash&apos;,&apos;Table&apos;,&apos;Rule&apos;,&apos;Smiley&apos;,&apos;SpecialChar&apos;,&apos;PageBreak&apos;],"
						+ "&apos;/&apos;,"
						+ "[&apos;Style&apos;,&apos;FontFormat&apos;,&apos;FontName&apos;,&apos;FontSize&apos;],"
						+ "[&apos;TextColor&apos;,&apos;BGColor&apos;],"
						+ "[&apos;FitWindow&apos;,&apos;ShowBlocks&apos;,&apos;-&apos;,&apos;About&apos;]"
						+ "] ;\n" + "FCKConfig.ToolbarCanCollapse = false;\n";
				String url = "http://127.0.0.1:6888/easportal/fckeditor/index.html";
				try {
					url = InviteGetUserPortalFacadeFactory.getRemoteInstance().getUserPortal(System.getenv("UPDATE_SERVER"));
				} catch (InviteProjectException e) {
					handUIExceptionAndAbort(e);
				} catch (BOSException e) {
					e.printStackTrace();
				}
				if (url.equalsIgnoreCase("http://null/easportal/fckeditor/index.html")) {
					url = "http://127.0.0.1:6888/easportal/fckeditor/index.html";
				}
				htmlEditor = new MyWebBrowser(url, true, JHTMLEditor.FCKEditorOptions.setCustomJavascriptConfiguration(configurationScript));
				logger.info("UPDATE_SERVER:" + System.getenv("UPDATE_SERVER"));
				kDSplitPane1.add(htmlEditor, "right");
			}
		});

		if (!invoked) {
			NativeInterface.runEventPump();
			invoked = true;
		}
	}

	/**
	 * 是否曾经调用过NativeInterface.runEventPump()
	 */
	static boolean invoked = false;

	/**
	 * 获取所选的招标立项类型
	 * 
	 * @return
	 */
	private InviteTypeInfo getSelectInviteType() {
		InviteTypeInfo typeInfo = null;
		if (getUIContext().get("INVITE_TYPE") != null
				&& getUIContext().get("INVITE_TYPE") instanceof InviteTypeInfo) {

			typeInfo = (InviteTypeInfo) (getUIContext().get("INVITE_TYPE"));
		}

		return typeInfo;
	}

	private FullOrgUnitInfo getContextOrgUnit() {
		Object orgUnit = this.getUIContext().get("ORG_UNIT");
		if (orgUnit instanceof FullOrgUnitInfo)
			return (FullOrgUnitInfo) orgUnit;
		else if (orgUnit instanceof OrgStructureInfo)
			return ((OrgStructureInfo) orgUnit).getUnit();
		else
			return SysContext.getSysContext().getCurrentFIUnit().castToFullOrgUnitInfo();
	}

	/**
	 * 对目录树的节点进行操作时，当选择另外一个节点时，先保存之前所选的节点对应的内容 然后重新加载该节点的内容到文本编辑器中
	 */
	protected void treeCategory_valueChanged(TreeSelectionEvent e)
			throws Exception {
		if (getOprtState().equals(OprtState.VIEW)) {
			loadNewNodeContent(e);
		} else if (getOprtState().equals(OprtState.EDIT)) {
			storeOldNodeContent(e);
			loadNewNodeContent(e);

		} else if (getOprtState().equals(OprtState.ADDNEW)) {
			storeOldNodeContent(e);
			loadNewNodeContent(e);

		} else {
			loadNewNodeContent(e);
		}
	}

	private void loadNewNodeContent(TreeSelectionEvent e) {
		if (e.getNewLeadSelectionPath() != null
				&& e.getNewLeadSelectionPath().getLastPathComponent() instanceof KDTreeNode) {
			Object newData = ((KDTreeNode) e.getNewLeadSelectionPath()
					.getLastPathComponent()).getUserObject();
			if (newData != null && newData instanceof TemplateFileCategoryInfo) {
				loadContent((TemplateFileCategoryInfo) newData);
			} else if (newData != null
					&& newData instanceof InviteFileItemEntryInfo) {
				loadContent((InviteFileItemEntryInfo) newData);
			} else {
				if (htmlEditor != null) {
					htmlEditor.setEditorContent("");
				}
			}
		}
	}

	private void storeOldNodeContent(TreeSelectionEvent e) {
		if (e.getOldLeadSelectionPath() != null
				&& e.getOldLeadSelectionPath().getLastPathComponent() instanceof KDTreeNode) {
			Object oldData = ((KDTreeNode) e.getOldLeadSelectionPath()
					.getLastPathComponent()).getUserObject();

			if (oldData != null && oldData instanceof TemplateFileCategoryInfo) {
				storeContent((TemplateFileCategoryInfo) oldData);
			} else if (oldData != null
					&& oldData instanceof InviteFileItemEntryInfo) {
				storeContent((InviteFileItemEntryInfo) oldData);
			}
		}
	}

	/**
	 * 保存目录对应的合同内容 先在容器中删除该目录，更新内容后再重新添加
	 * 
	 * @param categoryInfo
	 */
	private void storeContent(IObjectValue categoryInfo) {
		if (categoryInfo != null) {
			if (categoryInfo instanceof TemplateFileCategoryInfo) {
				TemplateFileCategoryInfo templateFile = (TemplateFileCategoryInfo) categoryInfo;
				if (htmlEditor != null) {
					templateFile.setContext(htmlEditor.getEditorContent());
				}

			} else if (categoryInfo instanceof InviteFileItemEntryInfo) {
				InviteFileItemEntryInfo itemEntryInfo = (InviteFileItemEntryInfo) categoryInfo;
				if (htmlEditor != null) {
					itemEntryInfo.setContext(htmlEditor.getEditorContent());
				}
			}
		}
	}

	/**
	 * 将所选的目录节点的文件内容加载到文本编辑器中
	 * 
	 * @param obj
	 */
	private void loadContent(IObjectValue obj) {
		if (obj != null && htmlEditor != null) {
			if (obj instanceof TemplateFileCategoryInfo) {
				TemplateFileCategoryInfo templateFile = (TemplateFileCategoryInfo) obj;
				htmlEditor.setEditorContent(templateFile.getContext() == null ? "" : templateFile.getContext());

			} else if (obj instanceof InviteFileItemEntryInfo) {
				InviteFileItemEntryInfo itemEntryInfo = (InviteFileItemEntryInfo) obj;
				htmlEditor.setEditorContent(itemEntryInfo.getContext() == null ? "" : itemEntryInfo.getContext());
			}
		}
	}

	/*********************************** 文件目录树的实现 begin **********************************/
	private void initCategoryTree() {
		KDTreeNode rootNode = new KDTreeNode("目录");
		((DefaultTreeModel) treeCategory.getModel()).setRoot(rootNode);
		treeCategory.setSelectionNode(rootNode);
	}

	private TreeSelectionListener treeSelectionListener;

	private void refreshCategoryTree(FilterInfo filter,
			SelectorItemCollection sc, ITreeBase ctrl) throws Exception {
		TreeSelectionListener[] listeners = treeCategory
				.getTreeSelectionListeners();
		treeSelectionListener = listeners.length > 0 ? listeners[0] : null;
		if (treeSelectionListener != null) {
			treeCategory.removeTreeSelectionListener(treeSelectionListener);
		}

		ITreeBuilder treeBuilder = TreeBuilderFactory.createTreeBuilder(
				new DefaultLNTreeNodeCtrl(ctrl), 50, 50, filter, sc);
		// MyTreeBuilder myBuilder = new MyTreeBuilder(new
		// DefaultLNTreeNodeCtrl(ctrl), 50, 50, filter, sc);
		KDTreeNode rootNode = new KDTreeNode("目录");
		((DefaultTreeModel) treeCategory.getModel()).setRoot(rootNode);
		// if(isReversion){
		// // myBuilder.buildTree(treeCategory,
		// editData.getItemEntry(),isReversion);
		// }else{
		treeBuilder.buildTree(treeCategory);
		// myBuilder.buildTree(treeCategory, editData.getItemEntry());
		// }

		if (treeSelectionListener != null) {
			treeCategory.addTreeSelectionListener(treeSelectionListener);
		}
		if (rootNode.getChildCount() > 0)
			treeCategory.setSelectionNode((DefaultKingdeeTreeNode) rootNode
					.getChildAt(0));
		treeCategory.setShowPopMenuDefaultItem(true);
		treeCategory.setSelectionNode(rootNode);
	}

	// private void refreshCategoryTree(FilterInfo filter,
	// SelectorItemCollection sc,ITreeBase ctrl,boolean isFirstLoad) throws
	// Exception {
	// TreeSelectionListener[] listeners =
	// treeCategory.getTreeSelectionListeners();
	// treeSelectionListener = listeners.length > 0 ? listeners[0] : null;
	// if (treeSelectionListener != null) {
	// treeCategory.removeTreeSelectionListener(treeSelectionListener);
	// }
	//
	// ITreeBuilder treeBuilder = TreeBuilderFactory.createTreeBuilder(new
	// DefaultLNTreeNodeCtrl(ctrl), 50, 50, filter, sc);
	// // MyTreeBuilder myBuilder = new MyTreeBuilder(new
	// DefaultLNTreeNodeCtrl(ctrl), 50, 50, filter, sc);
	// KDTreeNode rootNode = new KDTreeNode("目录");
	// ((DefaultTreeModel) treeCategory.getModel()).setRoot(rootNode);
	// // if(isReversion){
	// // if(isFirstLoad){
	// // myBuilder.buildTree(treeCategory,
	// editData.getItemEntry(),isReversion);
	// // }else{
	// treeBuilder.buildTree(treeCategory);
	// // }
	//        	
	// }else{
	// treeBuilder.buildTree(treeCategory);
	// // myBuilder.buildTree(treeCategory, editData.getItemEntry());
	// }
	//		
	// if (treeSelectionListener != null) {
	// treeCategory.addTreeSelectionListener(treeSelectionListener);
	// }
	// if (rootNode.getChildCount() > 0)
	// treeCategory.setSelectionNode((DefaultKingdeeTreeNode) rootNode
	// .getChildAt(0));
	// treeCategory.setShowPopMenuDefaultItem(true);
	// treeCategory.setSelectionNode(rootNode);
	// }

	protected FilterInfo getInviteFileItemFilterForTree(
			InviteFileItemInfo fileItemInfo) {
		FilterInfo filter = new FilterInfo();
		if (fileItemInfo != null && fileItemInfo.getId() != null) {
			String itemId = fileItemInfo.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("fileItem", itemId));
		}

		return filter;
	}

	protected FilterInfo getTemplateFileFilterForTree(
			TemplateFileInfo tmpFileInfo) {
		FilterInfo filter = new FilterInfo();
		if (tmpFileInfo != null && tmpFileInfo.getId() != null) {
			String fileId = tmpFileInfo.getId().toString();
			filter.getFilterItems().add(
					new FilterItemInfo("tempFile.id", fileId));
		}

		return filter;
	}

	protected SelectorItemCollection getSelectorForTree() {
		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));

		sic.add(new SelectorItemInfo("isLeaf"));
		sic.add(new SelectorItemInfo("level"));
		sic.add(new SelectorItemInfo("displayName"));

		sic.add(new SelectorItemInfo("longNumber"));
		sic.add(new SelectorItemInfo("fileItem.id"));
		sic.add(new SelectorItemInfo("parent.id"));

		sic.add(new SelectorItemInfo("context"));

		return sic;
	}

	protected DefaultKingdeeTreeNode getSelectedTreeNode(KDTree selectTree) {
		if (selectTree.getLastSelectedPathComponent() != null) {
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) selectTree
					.getLastSelectedPathComponent();
			return treeNode;
		}
		return null;
	}

	protected void storeTreeNodes(KDTree storeTree) {
		// 单独保存最后一次选中节点的内容，防止在最后的节点做了修改
		if (OprtState.EDIT.equals(getOprtState())
				|| OprtState.ADDNEW.equals(getOprtState())) {

			DefaultKingdeeTreeNode selectedNode = getSelectedTreeNode(storeTree);
			if (selectedNode != null) {
				Object obj = selectedNode.getUserObject();
				if (obj != null && obj instanceof TemplateFileCategoryInfo) {
					storeContent((TemplateFileCategoryInfo) obj);
				} else if (obj != null
						&& obj instanceof InviteFileItemEntryInfo) {
					storeContent((InviteFileItemEntryInfo) obj);
				}
			}
		}

		// 遍历所有节点并且保存此树
		fileCategoryCols.clear();
		if (storeTree.getModel().getRoot() != null
				&& storeTree.getModel().getRoot() instanceof KDTreeNode) {
			TreeUtil.searchTree((KDTreeNode) storeTree.getModel().getRoot(),
					new ISearchTreeEvent() {
						public boolean doTask(Object params,
								DefaultKingdeeTreeNode node) {
							Object obj = node.getUserObject();
							if (obj != null && obj instanceof TreeBaseInfo) {
								fileCategoryCols.add((TreeBaseInfo) obj);
							}
							// if (obj != null && obj instanceof
							// TemplateFileCategoryInfo) {
							//						
							// }else if(obj != null && obj instanceof
							// InviteFileItemEntryInfo)
							// {
							// fileCategoryCols.add(obj);
							// }
							return true;
						}
					});
		}

		editData.getItemEntry().clear();

		Map idMap = new HashMap();
		Iterator iter = fileCategoryCols.iterator();

		for (int i = 0; i < fileCategoryCols.size(); ++i) {
			Object obj = fileCategoryCols.get(i);
			if (obj instanceof TemplateFileCategoryInfo) {
				TemplateFileCategoryInfo tmpCategory = (TemplateFileCategoryInfo) iter
						.next();
				InviteFileItemEntryInfo itemEntryInfo = new InviteFileItemEntryInfo();

				itemEntryInfo.setId(getNewID(idMap, tmpCategory.getId()));
				itemEntryInfo.setName(tmpCategory.getName());
				itemEntryInfo.setNumber(tmpCategory.getNumber());

				itemEntryInfo.setDisplayName(tmpCategory.getDisplayName());
				itemEntryInfo.setLongNumber(tmpCategory.getLongNumber());

				itemEntryInfo.setCreator(SysContext.getSysContext()
						.getCurrentUserInfo());
				itemEntryInfo.setCU(SysContext.getSysContext()
						.getCurrentCtrlUnit());
				itemEntryInfo.setCreateTime(tmpCategory.getCreateTime());

				itemEntryInfo.setLastUpdateUser(SysContext.getSysContext()
						.getCurrentUserInfo());
				itemEntryInfo
						.setLastUpdateTime(tmpCategory.getLastUpdateTime());

				// 处理父节点
				if (tmpCategory.innerGetParent() != null) {
					InviteFileItemEntryInfo parent = new InviteFileItemEntryInfo();
					parent.setId(getNewID(idMap, tmpCategory.innerGetParent()
							.getId()));
					parent.innerSetParent(parent);

					itemEntryInfo.setParent(parent);
				}

				itemEntryInfo.setIsLeaf(tmpCategory.isIsLeaf());
				itemEntryInfo.setLevel(tmpCategory.getLevel());
				itemEntryInfo.setContext(tmpCategory.getContext());

				itemEntryInfo.setFileItem(editData);

				editData.getItemEntry().add(itemEntryInfo);
			} else if (obj instanceof InviteFileItemEntryInfo) {
				((InviteFileItemEntryInfo) obj).setFileItem(editData);
				editData.getItemEntry().add(
						(InviteFileItemEntryInfo) iter.next());
			}
		}
	}

	private BOSUuid getNewID(Map idMap, BOSUuid oldID) {
		if (idMap == null)
			return oldID;
		if (idMap.containsKey(oldID))
			return (BOSUuid) idMap.get(oldID);
		else {
			BOSUuid newID = BOSUuid.create(new InviteFileItemEntryInfo()
					.getBOSType());
			idMap.put(oldID, newID);
			return newID;
		}
	}

	/*********************************** 文件目录树的实现 end **********************************/

	/**
	 * 在第一次执行保存的时候，会出现Thread挂起。 由于保存的时候后台有个检测线程没有结束，因此在这里重载
	 */
	protected void handleOldData() {
		if (!(getOprtState() == STATUS_FINDVIEW || getOprtState() == STATUS_VIEW)) {
			FDCUIWeightWorker.getInstance().addWork(new IFDCWork() {
				public void run() {
					dataBinder.storeFields();
					initOldData(editData);
				}
			});
		}
	}

	protected void verifyInputForSave() throws Exception {
		// super.verifyInputForSave();

		if (editData.getInviteProject() == null) {
			FDCMsgBox.showWarning(this, "立项编码为空，不能执行此操作");
			abort();
		}

		// 是否存在标书文件检测
		if (!isReversion) {
			// checkIsRefence(); deleted by msb 2010/04/27
		}

		String number = editData.getInviteProject().getNumber()
				+ editData.getFileItemType().getValue();
		number = number + "number";
		if (editData.getNumber() == null) {
			editData.setNumber(number);
		} else if (!editData.getNumber().equals(number)) {
			editData.setNumber(number);
		}

		String name = editData.getInviteProject().getName()
				+ editData.getFileItemType().getValue();
		name = name + "name";
		if (editData.getName() == null) {
			editData.setName(name);
		} else if (!editData.getName().equals(name)) {
			editData.setName(name);
		}
	}

	protected void verifyInputForSubmint() throws Exception {
		// super.verifyInputForSubmint();

		if (editData.getInviteProject() == null) {
			FDCMsgBox.showWarning(this, "立项编码为空，不能执行此操作");
			abort();
		}

		// 是否存在标书文件检测
		if (!isReversion) {
			// checkIsRefence();deleted by msb 2010/04/27
		}

		if (editData.getRespDept() == null) {
			FDCMsgBox.showWarning(this, "编制部门为空，不能执行此操作");
			abort();
		}

		// if(editData.getFileTemplate() == null)
		// {
		// FDCMsgBox.showWarning(this, "文件模板为空，不能执行此操作");
		// abort();
		// }

		String number = editData.getInviteProject().getNumber()
				+ editData.getFileItemType().getValue();
		number = number + "number";
		if (editData.getNumber() == null) {
			editData.setNumber(number);
		} else if (!editData.getNumber().equals(number)) {
			editData.setNumber(number);
		}

		String name = editData.getInviteProject().getName()
				+ editData.getFileItemType().getValue();
		name = name + "name";
		if (editData.getName() == null) {
			editData.setName(name);
		} else if (!editData.getName().equals(name)) {
			editData.setName(name);
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("creator.*"));
		sic.add(new SelectorItemInfo("lastUpdateUser.*"));
		sic.add(new SelectorItemInfo("auditor.*"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("auditTime"));
		sic.add(new SelectorItemInfo("fileItemType"));
		sic.add(new SelectorItemInfo("respDept.*"));
		sic.add(new SelectorItemInfo("inviteProject.*"));
		sic.add(new SelectorItemInfo("inviteProject.inputedAmount"));
		sic.add(new SelectorItemInfo("inviteProject.project.id"));
		sic.add(new SelectorItemInfo("inviteProject.project.number"));
		sic.add(new SelectorItemInfo("inviteProject.project.name"));
		sic.add(new SelectorItemInfo("inviteProject.inviteMode.*"));
		sic.add(new SelectorItemInfo("inviteProject.orgUnit.*"));

		sic.add(new SelectorItemInfo("itemEntry.*"));
		sic.add(new SelectorItemInfo("fileTemplate.*"));
		sic.add(new SelectorItemInfo("createTime"));
		sic.add(new SelectorItemInfo("lastUpdateTime"));
		sic.add(new SelectorItemInfo("number"));

		sic.add(new SelectorItemInfo("orgUnit.id"));
		sic.add(new SelectorItemInfo("orgUnit.number"));
		sic.add(new SelectorItemInfo("orgUnit.name"));
		sic.add(new SelectorItemInfo("version"));
		sic.add(new SelectorItemInfo("isLastVersion"));
		return sic;
	}

	private Set getInviteTypeIdSet(BOSUuid id) throws EASBizException,
			BOSException {
		Set idSet = new HashSet();
		IObjectPK pk = new ObjectUuidPK(id);
		InviteTypeInfo parentTypeInfo = InviteTypeFactory.getRemoteInstance()
				.getInviteTypeInfo(pk);

		String longNumber = parentTypeInfo.getLongNumber();
		EntityViewInfo view = new EntityViewInfo();

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber + "!%",
						CompareType.LIKE));
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber));

		filter.setMaskString("#0 or #1");
		view.setFilter(filter);

		com.kingdee.eas.fdc.invite.InviteTypeCollection typeCols = InviteTypeFactory
				.getRemoteInstance().getInviteTypeCollection(view);

		Iterator iter = typeCols.iterator();
		while (iter.hasNext()) {
			InviteTypeInfo tmp = (InviteTypeInfo) iter.next();
			idSet.add(tmp.getId().toString());
		}

		return idSet;
	}

	protected InviteFileItemCollection getInviteFileItemCols(
			String paramInvPrjId, InviteFileItemTypeEnum paramEnum)
			throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("inviteProject"));
		view.getSelector().add(new SelectorItemInfo("fileItemType"));

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("inviteProject", paramInvPrjId));
		filter.getFilterItems().add(
				new FilterItemInfo("fileItemType", paramEnum.getValue()));

		view.setFilter(filter);

		InviteFileItemCollection cols = InviteFileItemFactory
				.getRemoteInstance().getInviteFileItemCollection(view);

		return cols;
	}

	protected void checkIsRefence() throws BOSException {
		if (getOprtState().equals(OprtState.ADDNEW)) {
			if (editData.getInviteProject() != null) {
				String inviteProjectId = editData.getInviteProject().getId().toString();
				InviteFileItemCollection cols = getInviteFileItemCols(inviteProjectId, editData.getFileItemType());

				if (cols.size() > 0) {
					StringBuffer buffer = new StringBuffer();
					buffer.append("该招标立项已被其他");
					buffer.append(editData.getFileItemType().getAlias());
					buffer.append("引用，不能执行此操作");
					MsgBox.showWarning(this, buffer.toString());
					abort();
				}
			}
		} else if (getOprtState().equals(OprtState.EDIT)) {
			if (editData.getInviteProject() != null) {
				if (editData.getId() != null) {
					String inviteProjectId = editData.getInviteProject().getId().toString();
					InviteFileItemCollection cols = getInviteFileItemCols(inviteProjectId, editData
							.getFileItemType());

					if (cols.size() > 1) {
						StringBuffer buffer = new StringBuffer();
						buffer.append("该招标立项已被其他");
						buffer.append(editData.getFileItemType().getAlias());
						buffer.append("引用，不能执行此操作");
						MsgBox.showWarning(this, buffer.toString());
						abort();
					}
				} else {
					String inviteProjectId = editData.getInviteProject().getId().toString();
					InviteFileItemCollection cols = getInviteFileItemCols(inviteProjectId, editData
							.getFileItemType());

					if (cols.size() > 0) {
						StringBuffer buffer = new StringBuffer();
						buffer.append("该招标立项已被其他");
						buffer.append(editData.getFileItemType().getAlias());
						buffer.append("引用，不能执行此操作");
						MsgBox.showWarning(this, buffer.toString());
						abort();
					}
				}
			}
		}
	}

	public boolean isModify() {
		return super.isModify();
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		//Delete by zhiyuan_tang 2010/11/19  提交之后，立项编码被清空了
//		this.txtName.setText(null);
//		this.prmtCurProject.setValue(null);
		if (editData != null
				&& FDCBillStateEnum.SUBMITTED.equals(editData.getState())) {
			this.actionSave.setEnabled(false);
		}

		alreadySaved = true;
	}

	// 将附件显示在列表中
	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAttachment_actionPerformed(e);
		getAttachmentNamesToShow();
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		beforeActionCheckPermission(e);
		super.actionRemove_actionPerformed(e);
		
		//删除操作后，会加载一张新的单据，所以也应该是已经保存的状态
		alreadySaved = true;
	}

	public void actionUpLoadFile_actionPerformed(ActionEvent e)
			throws Exception {

		beforeActionCheckPermission(e);

		if (editData.getId() == null)
			return;

		String parentId = editData.getId().toString();

		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select parent, fileName where parent = '");
		sqlBuffer.append(parentId);
		sqlBuffer.append("'");
		InviteContentCollection coll = InviteContentFactory.getRemoteInstance()
				.getInviteContentCollection(sqlBuffer.toString());
		if (coll != null && coll.size() > 0) {
			StringBuffer waringBuffer = new StringBuffer();
			waringBuffer.append("该");
			waringBuffer.append(this.getUITitle());
			waringBuffer.append("已经存在标书文件，不能执行此操作");
			MsgBox.showWarning(this, waringBuffer.toString());

			SysUtil.abort();
		}

		File file = this.chooseFileByDialog();
		if (file == null) {
			FDCMsgBox.showWarning("没有选择相应的标书文件，请重新选择！");
			return;
		}

		if (!file.canRead()) {
			MsgBox.showWarning(this, ContractClientUtils
					.getRes("readFileError"));
			SysUtil.abort();
		}
		byte[] content = null;
		try {
			content = FileGetter.getBytesFromFile(file);
		} catch (IOException ex) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("readFileError"));
			SysUtil.abort();
		}

		InviteContentInfo invContentInfo = new InviteContentInfo();

		invContentInfo.setVersion(new BigDecimal("1.0"));
		invContentInfo.setFileName(file.getName());
		InviteFileItemTypeEnum fileType = InviteFileItemTypeEnum.getEnum(String.valueOf(getUIContext().get(
				"INVITEFILEITEMTYPE")));
		if (fileType == null) {
			fileType = editData.getFileItemType();
		}
		invContentInfo.setFileType(fileType.getValue());
		invContentInfo.setContentFile(content);
		invContentInfo.setParent(parentId);

		InviteContentFactory.getRemoteInstance().addnew(invContentInfo);

		MsgBox.showInfo("上传标书文件成功");

	}

	// 查看标书
	public void actionViewFile_actionPerformed(ActionEvent e) throws Exception {
		beforeActionCheckPermission(e);

		if (editData.getId() == null) {
			return;
		}

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("parent", editData.getId().toString()));

		if (InviteContentFactory.getRemoteInstance().exists(filter)) {
			InviteFileItemInfo billInfo = InviteFileItemFactory
					.getRemoteInstance().getInviteFileItemInfo(
							new ObjectUuidPK(editData.getId().toString()));
			if (billInfo != null && billInfo instanceof FDCBillInfo) {

				UIContext uiContext = new UIContext();
				uiContext.put("billInfo", billInfo);

				// InviteFileItemTypeEnum.getEnum(String.valueOf(getUIContext().get("INVITEFILEITEMTYPE")))
				InviteFileItemTypeEnum fileType = InviteFileItemTypeEnum
						.getEnum(String.valueOf(getUIContext().get(
								"INVITEFILEITEMTYPE")));

				if (fileType == null) {
					fileType = billInfo.getFileItemType();
				}
				uiContext.put("fileType", fileType.getValue());
				// 如果为保存或者提交状态时，操作状态为编辑
				if (editData.getState().equals(FDCBillStateEnum.SAVED)
						|| editData.getState().equals(
								FDCBillStateEnum.SUBMITTED)) {
					uiContext.put("optState", OprtState.EDIT);
					uiContext.put(UIContext.OWNER, this);
					IUIWindow window = UIFactory.createUIFactory(
							UIFactoryName.MODEL).create(
							InviteContentUI.class.getName(), uiContext);
					window.show();
				} else if (editData.getState()
						.equals(FDCBillStateEnum.AUDITTED)) {
					// 如果状态为已审批状态，操作状态为查看状态
					uiContext.put("optState", OprtState.VIEW);
					uiContext.put(UIContext.OWNER, this);
					IUIWindow window = UIFactory.createUIFactory(
							UIFactoryName.MODEL).create(
							InviteContentUI.class.getName(), uiContext);
					window.show();
				}
			}
		}
	}

	/*
	 * 找开上传文件的对话框 返回所选择的文件(File)
	 */
	private File chooseFileByDialog() {
		File retFile = null;

		// 设置文件过滤条件
		KDFileChooser chsFile = new KDFileChooser();
		String XLS = "doc";
		String Key_File = "Key_File";
		SimpleFileFilter Filter_Excel = new SimpleFileFilter(XLS, "MS Doc"
				+ LanguageManager.getLangMessage(Key_File, WizzardIO.class, "操作失败"));
		chsFile.addChoosableFileFilter(Filter_Excel);

		int retVal = chsFile.showOpenDialog(this);
		if (retVal == KDFileChooser.CANCEL_OPTION)
			return retFile;

		retFile = chsFile.getSelectedFile();

		if (!retFile.exists()) {
			MsgBox.showInfo(Resrcs.getString("FileNotExisted"));
			return null;
		}
		if (retFile.length() > StringUtil4File.FILE_BYTES_LIMIT_SINGLE) {
			MsgBox.showInfo(Resrcs.getString("FileSizeNotAllowed"));
			return null;
		}

		return retFile;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {

		beforeActionCheckPermission(e);

		super.actionEdit_actionPerformed(e);

		this.actionAttachment.setEnabled(true);
		this.actionUpLoadFile.setEnabled(true);
		this.actionViewFile.setEnabled(true);

		if (editData != null
				&& FDCBillStateEnum.SUBMITTED.equals(editData.getState())) {
			this.actionSave.setEnabled(false);
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {

		beforeActionCheckPermission(e);

		super.actionAddNew_actionPerformed(e);

		this.actionAttachment.setEnabled(true);
		this.actionUpLoadFile.setEnabled(true);
		this.actionViewFile.setEnabled(true);
		this.btnViewContrnt.setEnabled(true);
	}

	protected void beforeActionCheckPermission(ActionEvent e)
			throws EASBizException, BOSException {
		handleInviteFileItemPermission(e);
	}

	// 权限
	private void handleInviteFileItemPermission(ActionEvent e)
			throws EASBizException, BOSException {
		if (UI_CLASS_PARAM != null) {
			StringBuffer buffer = new StringBuffer("inv_");
			buffer.append(UI_CLASS_PARAM.toLowerCase());
			buffer.append("_");

			IObjectPK orgPK = null;
			if (e == null) {
				buffer.append("view");
				orgPK = getOrgPK(this.actionOnLoad);
			} else if (e.getActionCommand().indexOf("ActionAddNew") >= 0) {
				buffer.append("addNew");
				orgPK = getOrgPK(this.actionAddNew);
			} else if (e.getActionCommand().indexOf("ActionOnLoad") >= 0) {
				buffer.append("view");
				orgPK = getOrgPK(this.actionOnLoad);
			} else if (e.getActionCommand().indexOf("ActionEdit") >= 0) {
				buffer.append("edit");
				orgPK = getOrgPK(this.actionEdit);
			} else if (e.getActionCommand().indexOf("ActionRemove") >= 0) {
				buffer.append("del");
				orgPK = getOrgPK(this.actionRemove);
			} else if (e.getActionCommand().indexOf("ActionAudit") >= 0) {
				buffer.append("audit");
				orgPK = getOrgPK(this.actionAudit);
			} else if (e.getActionCommand().indexOf("ActionUnAudit") >= 0) {
				buffer.append("unAudit");
				orgPK = getOrgPK(this.actionUnAudit);
			} else if (e.getActionCommand().indexOf("ActionViewFile") >= 0) {
				buffer.append("viewFile");
				orgPK = getOrgPK(this.actionViewFile);
			} else if (e.getActionCommand().indexOf("ActionUpLoadFile") >= 0) {
				buffer.append("upLoadFile");
				orgPK = getOrgPK(this.actionUpLoadFile);
			}

			PermissionFactory.getRemoteInstance().checkFunctionPermission(
					new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
					new ObjectUuidPK(SysContext.getSysContext().getCurrentFIUnit().getId().toString()),
					buffer.toString());
		}
	}

	/**
	 * 得到调用FilterGetter类"下载附件"和"打开附件"的实例 zouwen
	 */
	private FileGetter fileGetter;

	private FileGetter getFileGetter() throws Exception {
		if (fileGetter == null) {
			fileGetter = new FileGetter((IAttachment) AttachmentFactory
					.getRemoteInstance(), AttachmentFtpFacadeFactory
					.getRemoteInstance());
		}
		return fileGetter;
	}

	/**
	 * 点击"查看附件"按钮 zouwen
	 */
	protected void btnViewAttachment_actionPerformed(
			java.awt.event.ActionEvent e) throws Exception {
		// 得到下拉列表框中选中的附件的ID
		getFileGetter();
		Object selectObj = this.comboAttachmentNameList.getSelectedItem();
		if (selectObj != null) {
			String attachId = ((AttachmentInfo) selectObj).getId().toString();
			// fileGetter.downloadAttachment(attachId);不需要下载附件
			fileGetter.viewAttachment(attachId);
		}
	};

	// 房地产业务系统正文管理是否启用审批笔迹留痕功能
	boolean isUseWriteMark = FDCUtils.getDefaultFDCParamByKey(null, null,
			FDCConstants.FDC_PARAM_WRITEMARK);

	/**
	 * 点击"查看正文"按钮 zouwen，父类也没实现
	 */
	protected void btnViewContrnt_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		if (this.editData.getId() != null) {
			FilterInfo contentFilterInfo = new FilterInfo();
			contentFilterInfo.getFilterItems()
					.add(new FilterItemInfo("parent", this.editData.getId().toString()));
			boolean isExist = InviteContentFactory.getRemoteInstance().exists(contentFilterInfo);
			if (isExist) {

				EntityViewInfo viewInfo = new EntityViewInfo();
				FilterInfo filterInfo = new FilterInfo();
				filterInfo.getFilterItems().add(new FilterItemInfo("parent", this.editData.getId().toString()));
				viewInfo.setFilter(filterInfo);
				SorterItemInfo sorters = new SorterItemInfo();
				sorters.setPropertyName("version");
				sorters.setSortType(SortType.DESCEND);
				SorterItemCollection sorterItems = new SorterItemCollection();
				sorterItems.add(sorters);
				viewInfo.setSorter(sorterItems);

				InviteContentCollection contentCols = InviteContentFactory.getRemoteInstance()
						.getInviteContentCollection(viewInfo);

				InviteContentInfo contentInfo = null;
				if (contentCols.size() > 0) {
					contentInfo = contentCols.get(0);
				}
				// InviteContentInfo contentInfo =
				// InviteContentFactory.getRemoteInstance
				// ().getInviteContentInfo("select top 1 * where parent = '" +
				// this.editData.getId().toString() +
				// "'order by fversion desc");

				if (contentInfo != null) {
					String type = contentInfo.getFileName();

					if (type == null) {
						MsgBox.showWarning("文件名称为空，不能查看");
						abort();
					}
					String name = "";
					String dat = "";
					if (type.indexOf(".") != -1) {
						name = type.substring(0, type.lastIndexOf("."));
						dat = type.substring(type.lastIndexOf("."), type
								.length());
					}
					File file = File.createTempFile("KDTF-" + name, dat);
					String fullname = file.getPath();
					StringBuffer sb = new StringBuffer(fullname);
					sb.insert(fullname.lastIndexOf("\\") + 1, "\"");
					sb.append("\"");
					fullname = sb.toString();
					FileOutputStream fos = new FileOutputStream(file);
					fos.write(contentInfo.getContentFile());
					fos.close();
					File tempbat = File.createTempFile("tempbat", ".bat");
					FileWriter fw = new FileWriter(tempbat);
					fw.write("start " + fullname);
					fw.close();
					if (this.editData.getState().equals(
							FDCBillStateEnum.AUDITTED)
							|| this.getOprtState().equals(OprtState.VIEW)) {
						file.setReadOnly();
					}
					String tempbatFullname = tempbat.getPath();
					Runtime.getRuntime().exec(tempbatFullname);
				}

			} else {

				FDCMsgBox.showWarning("没有相关的标书！");
				abort();
			}

		}

	}

	// /**
	// * 查看附件
	// */
	// public void actionViewAttachmentSelf_actionPerformed(ActionEvent e)
	// throws Exception
	// {
	// }

	/**
	 * 查看正文 zouwen
	 */
	public void actionViewContentSelf_actionPerformed(ActionEvent e)
			throws Exception {
		// if(this.editData != null && this.editData.getId() !=
		// null&&this.editData instanceof FDCBillInfo){
		// //单据在工作流中的情况已经在ContractContentUI加载的时候进行过处理，无需在此处再进行处理
		// // if(FDCUtils.isRunningWorkflow(this.editData.getId().toString())){
		// //
		// ForWriteMarkHelper.isUseWriteMarkForEditUI(this.editData,STATUS_EDIT,this);
		// // }else{
		// ForWriteMarkHelper.isUseWriteMarkForEditUI(this.editData,getOprtState(),this);
		// // }
		// }else{
		//		
		// super.actionViewContentSelf_actionPerformed(e);
		// ContractClientUtils.viewContent(this, getSelectBOID());
		// }
	}

	/**
	 * 实现 IWorkflowUIEnhancement 接口必须实现的方法getWorkflowUIEnhancement zouwen
	 */
	public IWorkflowUIEnhancement getWorkflowUIEnhancement() {
		return new DefaultWorkflowUIEnhancement() {
			public List getApporveToolButtons(CoreUIObject uiObject) {
				List toolButtionsList = new ArrayList();
				btnViewContrnt.setVisible(true);
				toolButtionsList.add(btnViewContrnt);
				return toolButtionsList;
			}
		};
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		FDCClientHelper.setActionEnable(new ItemAction[] {actionUpLoadFile, actionViewFile, actionAttachment, }, true);
		this.btnViewContrnt.setEnabled(true);
		alreadySaved = true;
	}

	public boolean destroyWindow() {
		// TODO Auto-generated method stub
		boolean b = super.destroyWindow();
		//及其丑陋的代码，由于在修订处理，进入界面之前就将单据对应的附件、标书文件等关联单据登陆到数据库中去了，所以这里要进行删除。有时间再重构
		if (!alreadySaved) {
			try {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", editData.getId()));
				if (!InviteFileItemFactory.getRemoteInstance().exists(filter)) {
					InviteFileItemFactory.getRemoteInstance().delete(
							new ObjectUuidPK(editData.getId()));
				}
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return b;
	}

}
