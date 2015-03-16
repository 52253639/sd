/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.KDLayout.Constraints;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
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
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.basedata.master.cssp.ISupplier;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierLinkManInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierPurchaseInfoCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierPurchaseInfoFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierPurchaseInfoInfo;
import com.kingdee.eas.basedata.master.cssp.client.SupplierEditUI;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.invite.FDCInviteException;
import com.kingdee.eas.fdc.invite.IInviteProject;
import com.kingdee.eas.fdc.invite.InquiryResultEntryInfo;
import com.kingdee.eas.fdc.invite.InquiryResultFactory;
import com.kingdee.eas.fdc.invite.InquiryResultInfo;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.InviteTypeCollection;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.client.offline.util.AttachmentPermissionUtil;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.scm.common.client.GeneralKDPromptSelectorAdaptor;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 询价结果编辑界面
 * 
 * @author mashuanbao
 * 
 */
public class InquiryResultEditUI extends AbstractInquiryResultEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(InquiryResultEditUI.class);

	// 自定义按钮的锚定设置：参考DetailPanel的实现
	private final int anchor = Constraints.ANCHOR_TOP
			| Constraints.ANCHOR_RIGHT;
	// 自定义按钮
	private KDWorkButton btnAddLine = null;
	private KDWorkButton btnDelLine = null;
	private KDWorkButton btnViewLine = null;

	CostCenterOrgUnitInfo currentOrg = null;
	CtrlUnitInfo cu = null;

	private boolean isEvent = true;

	private Map supplierIDs = new HashMap();

	// 供应商F7
	private KDBizPromptBox prmtSupplier = null;

	public InquiryResultEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {

		// 初始化成本中心和CU
		initOrgData();

		// 初始化供应商F7
		initSupplierF7();

		// 初始化F7过滤
		initF7Filter();

		// 初始化自定义按钮
		initUserDefineBtn();

		// 初始化分录设置
		initKDTEntrys();

		super.onLoad();
	}

	public void loadFields() {
		super.loadFields();
		supplierIDs.clear();
		try {

			// 获取附件
			getAttachmentNamesToShow();

			// 处理分录显示数据不全
			dealEntryDataDisplay();
		} catch (Exception e) {
			handleException(e);
		}
	}

	public void storeFields() {
		super.storeFields();

	}

	/**
	 * 初始化当前成本中心和控制单元
	 */
	private void initOrgData() {
		currentOrg = SysContext.getSysContext().getCurrentCostUnit();
		cu = SysContext.getSysContext().getCurrentCtrlUnit();
	}

	protected void initWorkButton() {
		super.initWorkButton();

		// 显示审核、反审核
		actionAudit.setEnabled(true);
		actionUnAudit.setEnabled(true);

		btnAudit.setVisible(true);
		btnUnAudit.setVisible(true);

		// 屏蔽不需显示的按钮和菜单项
		actionPre.setVisible(false);
		actionNext.setVisible(false);
		actionLast.setVisible(false);
		actionFirst.setVisible(false);

		actionCreateFrom.setVisible(false);
		actionCreateTo.setVisible(false);

		actionCopy.setVisible(false);
		actionCopyFrom.setVisible(false);

		menuView.setVisible(false);

		actionSubmitOption.setVisible(false);

	}
	
	/**
	 * 审核、反审核是否显示和可用的控制
	 */
	protected void setAuditButtonStatus(String oprtType){
    	if(STATUS_VIEW.equals(oprtType)) {
    		actionAudit.setVisible(true);
    		actionUnAudit.setVisible(true);
    		actionAudit.setEnabled(true);
    		actionUnAudit.setEnabled(true);
    		
    		FDCBillInfo bill = (FDCBillInfo)editData;
    		if(editData!=null){
    			if(FDCBillStateEnum.AUDITTED.equals(bill.getState())){
    	    		actionUnAudit.setVisible(true);
    	    		actionUnAudit.setEnabled(true);   
    	    		actionAudit.setVisible(false);
    	    		actionAudit.setEnabled(false);
    			}else{
    	    		actionUnAudit.setVisible(false);
    	    		actionUnAudit.setEnabled(false);   
    	    		actionAudit.setVisible(true);
    	    		actionAudit.setEnabled(true);
    			}
    		}
    	}else {
    		actionAudit.setVisible(true);
    		actionUnAudit.setVisible(true);
    		actionAudit.setEnabled(false);
    		actionUnAudit.setEnabled(false);
    	}
    }

	/**
	 * 初始分录设置
	 */
	private void initKDTEntrys() {
		// 设置分录标题
		kdtEntrys_detailPanel.setTitle("竞价单位");
		// 添加自定义按钮
		addUserDefineBtn();
		// 设置选择模式-锁定表格
		kdtEntrys.getStyleAttributes().setLocked(true);
		kdtEntrys.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}

	/**
	 * 初始化自定义按钮
	 */
	private void initUserDefineBtn() {
		int width = this.kdtEntrys.getBounds().width;
		if (btnAddLine == null) {
			// 添加
			btnAddLine = new KDWorkButton("添加");
		}
		if (btnDelLine == null) {
			// 删除
			btnDelLine = new KDWorkButton("删除");
		}
		if (btnViewLine == null) {
			// 查看供方详细信息
			btnViewLine = new KDWorkButton("查看供方详细信息");
		}

		// 添加图标
		btnAddLine.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnDelLine.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		btnViewLine.setIcon(EASResource.getIcon("imgTbtn_sortstandard"));

		// 设置位置、大小
		btnAddLine.setBounds(new Rectangle(width - 294, 5, 60, 19));
		btnDelLine.setBounds(new Rectangle(width - 217, 5, 60, 19));
		btnViewLine.setBounds(new Rectangle(width - 140, 5, 140, 19));

		// 按钮添加事件监听
		btnAddLine.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					actionAddSupplier_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
					logger.debug("addLine error:" + e);
				}

			}
		});

		btnDelLine.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					actionDelSupplier_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
					logger.debug("addLine error:" + e);
				}

			}
		});

		btnViewLine.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					actionViewSupplier_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
					logger.debug("addLine error:" + e);
				}
			}
		});
	}

	/**
	 * 初始F7
	 */
	protected void initF7Filter() throws Exception {

		String cuId = cu.getId().toString();

		initF7InviteProject();
		// 初始化编制部门
		FDCClientUtils.setRespDeptF7(prmtcreateDept, this);

		// 此F7为隐藏控件，主要用于分录的添加事件调用
		FDCClientUtils.initSupplierF7(this, prmtSupplier, cuId);
		((GeneralKDPromptSelectorAdaptor) prmtSupplier.getSelector())
				.setIsMultiSelect(true);
	}

	/**
	 * 初始化供应商F7
	 */
	protected void initSupplierF7() {
		String queryStr = "com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard";
		prmtSupplier = new KDBizPromptBox();
		prmtSupplier.setName("prmtSupplier");
		this.prmtSupplier.setQueryInfo(queryStr);
		this.prmtSupplier.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				try {
					prmtSupplier_dataChanged(e);
				} catch (Exception exc) {
					handUIException(exc);
				} finally {
				}
			}
		});
	}

	/****************************************************************
	 * 招标立项F7过滤 设置
	 ***************************************************************/
	private void initF7InviteProject() throws Exception {

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("project.*");
		sic.add("inviteType.*");

		Map map = getUIContext();

		EntityViewInfo evi = new EntityViewInfo();

		FilterInfo filter = new FilterInfo();
		// 已审核
		filter.getFilterItems().add(
				new FilterItemInfo("inviteProjectState",
						FDCBillStateEnum.AUDITTED_VALUE));
		// 非询价的立项
		if (!getInviteProjectIsInquiry().isEmpty()) {
			filter.getFilterItems().add(new FilterItemInfo("id", getInviteProjectIsInquiry(),
							CompareType.NOTINCLUDE));
		}
		// 采购类型
		if (map.containsKey("type")
				&& map.get("type") instanceof InviteTypeInfo) {

			InviteTypeInfo inviteType = (InviteTypeInfo) map.get("type");
			BOSUuid id = inviteType.getId();

			filter.getFilterItems().add(
					new FilterItemInfo("inviteType.id", getInviteTypeIdSet(id),
							CompareType.INCLUDE));
		}
		// 当前组织下的立项
		if (currentOrg != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("orgUnit.id", currentOrg.getId()
							.toString()));
		}
		evi.setFilter(filter);

		prmtInviteProject.setSelectorCollection(sic);

		prmtInviteProject.setEntityViewInfo(evi);
	}

	/**
	 * 描述：获取已询价的立项
	 */
	private Set getInviteProjectIsInquiry() throws Exception {

		Set ips = new HashSet();
		Iterator it = getBizInterface().getCollection().iterator();
		while (it.hasNext()) {
			InquiryResultInfo info = (InquiryResultInfo) it.next();
			if (info != null && info.getInviteProject() != null)
				ips.add(info.getInviteProject().getId().toString());
		}

		return ips;
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

		InviteTypeCollection typeCols = InviteTypeFactory.getRemoteInstance()
				.getInviteTypeCollection(view);

		Iterator iter = typeCols.iterator();
		while (iter.hasNext()) {
			InviteTypeInfo tmp = (InviteTypeInfo) iter.next();
			idSet.add(tmp.getId().toString());
		}

		return idSet;
	}

	/************************************************************
	 * 列表框中显示附件处理
	 ***********************************************************/

	/**
	 * 描述：附件上传
	 */
	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAttachment_actionPerformed(e);
		
		/*
		 * 工作流中允许当前审批人上传附件 并删除自己上传的附件
		 */
		String boID = getSelectBOID();
		if (boID == null) {
			return;
		}
		boolean isEdit = false;
		
		if (STATUS_ADDNEW.equals(getOprtState())
				|| STATUS_EDIT.equals(getOprtState())) {
			isEdit = true;
		}
		isEdit = AttachmentPermissionUtil.checkAuditingAttachmentEdit(editData.getState(), boID,isEdit);
		
		AttachmentUIContextInfo info = new AttachmentUIContextInfo();
		info.setBoID(boID);
		MultiApproveUtil.showAttachmentManager(info,this,editData,String.valueOf("1"),isEdit);

		// 列表框中显示附件
		getAttachmentNamesToShow();
	}

	private boolean getAttachmentNamesToShow() throws Exception {

		isEvent = false;
		this.cmbAttachments.removeAllItems();
		isEvent = true;
		String boID = getSelectBOID();
		boolean hasAttachment = false;
		if (boID == null) {
			return hasAttachment;
		}

		EntityViewInfo view = new EntityViewInfo();

		SelectorItemCollection itemColl = new SelectorItemCollection();
		itemColl.add(new SelectorItemInfo("id"));
		itemColl.add(new SelectorItemInfo("attachment.name"));
		itemColl.add(new SelectorItemInfo("attachment.id"));

		view.getSelector().addObjectCollection(itemColl);

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("boID", boID));

		view.setFilter(filter);

		BoAttchAssoCollection boAttchColl = BoAttchAssoFactory
				.getRemoteInstance().getBoAttchAssoCollection(view);

		if (boAttchColl != null && boAttchColl.size() > 0) {
			hasAttachment = true;
			isEvent = false;
			for (int i = 0; i < boAttchColl.size(); i++) {
				AttachmentInfo attachment = (AttachmentInfo) boAttchColl.get(i)
						.getAttachment();
				this.cmbAttachments.addItem(attachment);
				this.cmbAttachments.setUserObject(attachment);
			}
			if (boAttchColl.size() == 1) {
				this.cmbAttachments.addItem("    ");
				this.cmbAttachments.setUserObject(null);
			}

			isEvent = true;
		}
		return hasAttachment;
	}

	/**
	 * 下拉附件选择时，打开文件
	 */
	protected void cmbAttachments_itemStateChanged(ItemEvent e)
			throws Exception {
		super.cmbAttachments_itemStateChanged(e);
		if (StringUtils.isEmpty(e.getItem().toString()))
			return;
		if (isEvent && e.getStateChange() == ItemEvent.SELECTED) {
			// 得到下拉列表框中选中的附件的ID
			FileGetter fileGetter = getFileGetter();
			Object selectObj = this.cmbAttachments.getSelectedItem();
			if (selectObj != null) {
				String attachId = ((AttachmentInfo) selectObj).getId()
						.toString();
				fileGetter.viewAttachment(attachId);
			}
		}
	}

	/**
	 * 得到调用FilterGetter类"下载附件"和"打开附件"的实例zouwen 避免产生多次调用，缓存起来
	 * 
	 * @return FileGetter类的实例
	 * @throws Exception
	 */

	private FileGetter getFileGetter() throws Exception {
		FileGetter fileGetter = null;

		if (getUIContext().get("fileGetter") == null) {
			fileGetter = new FileGetter((IAttachment) AttachmentFactory
					.getRemoteInstance(), AttachmentFtpFacadeFactory
					.getRemoteInstance());
			getUIContext().put("fileGetter", fileGetter);
		} else {
			fileGetter = (FileGetter) getUIContext().get("fileGetter");
		}
		return fileGetter;
	}

	/*********************************************************
	 * 自定义分录【添加、删除、查看】供应商按钮
	 *********************************************************/

	private void addUserDefineBtn() {
		kdtEntrys.checkParsed();

		KDPanel ctrlPnl = getControlPanel();
		Component[] btns = ctrlPnl.getComponents();
		for (int m = 0, n = btns.length; m < n; m++) {
			if (btns[m] instanceof KDWorkButton) {
				KDWorkButton btn = (KDWorkButton) btns[m];
				btn.setVisible(false);
			}
		}

		// 添加按钮
		ctrlPnl.add(btnAddLine, getBtnConstraints(btnAddLine));
		ctrlPnl.add(btnDelLine, getBtnConstraints(btnDelLine));
		ctrlPnl.add(btnViewLine, getBtnConstraints(btnViewLine));
	}

	/**
	 * 获取按钮的布局
	 */
	private Constraints getBtnConstraints(KDWorkButton btn) {
		Rectangle rect = btn.getBounds();
		return new Constraints(rect.x, rect.y, rect.width, rect.height, anchor);
	}

	/**
	 * 获取分录表格的添加行按钮所在的Panel
	 */
	private KDPanel getControlPanel() {
		Component[] pnls = kdtEntrys_detailPanel.getComponents();
		String ctrlPnlName = "controlPanel";
		KDPanel ctrlPnl = null;
		for (int i = 0; i < pnls.length; i++) {
			if (pnls[i] instanceof KDPanel) {
				ctrlPnl = (KDPanel) pnls[i];
				if (ctrlPnlName.equals(ctrlPnl.getName())) {
					break;
				}
			}
		}
		return ctrlPnl;
	}

	/***********************************************************
	 * 分录【添加、删除、查看】供应商事件处理
	 ***********************************************************/

	/**
	 * 添加供应商
	 */
	private void actionAddSupplier_actionPerformed(ActionEvent e)
			throws Exception {
		prmtSupplier.setDataBySelector();
		// IRow row = this.kdtEntrys.addRow();
		// dataBinder.loadLineFields(kdtEntrys, row,
		// createNewDetailData(kdtEntrys));
	}

	/**
	 * 删除供应商
	 */
	private void actionDelSupplier_actionPerformed(ActionEvent e)
			throws Exception {
		if (OprtState.VIEW.endsWith(getOprtState())) {
			return;
		}
		checkSelectedRow();
		int top = kdtEntrys.getSelectManager().get().getTop();

		if (kdtEntrys.getRow(top) == null) {
			MsgBox.showInfo(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_NoneEntry"));
			return;
		}

		IObjectValue detailData = (IObjectValue) kdtEntrys.getRow(top)
				.getUserObject();
		// fireRemoveLineBeforeAction(detailData);
		kdtEntrys.removeRow(top);

		InquiryResultEntryInfo entry = (InquiryResultEntryInfo) detailData;
		supplierIDs.remove(entry.getSupplier().getId().toString());

		IObjectCollection collection = (IObjectCollection) kdtEntrys
				.getUserObject();
		if (collection == null) {
			logger.error("collection not be binded to table");
		} else {
			// Modify By Jacky Zhang
			if (detailData != null) {
				collection.removeObject(top);
			}
		}

		// KDTSelectManager selMgr = kdtEntrys.getSelectManager();
		// KDTSelectBlock selBlk = selMgr.get();
		// kdtEntrys.removeRow(selBlk.getTop());
	}

	/**
	 * 查看供应商
	 */
	private void actionViewSupplier_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelectedRow();
		// 在查看数据项之前检查数据是否还存在
		checkObjectExists();

		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, getSelectedEntryId());
		IUIWindow uiWindow = null;
		if (SwingUtilities.getWindowAncestor(this) != null
				&& SwingUtilities.getWindowAncestor(this) instanceof JDialog) {
			uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
					getSupplierEditUIName(), uiContext, null, OprtState.VIEW);

		} else {
			// 创建UI对象并显示
			uiWindow = UIFactory.createUIFactory(getEditUIModal()).create(
					getSupplierEditUIName(), uiContext, null, OprtState.VIEW);
		}

		uiWindow.show();
	}

	/**
	 * 供应商选择修改分录
	 */
	protected void prmtSupplier_dataChanged(DataChangeEvent e) throws Exception {
		Object obj = e.getNewValue();

		if (OprtState.VIEW.endsWith(getOprtState())) {
			return;
		}
		if (obj instanceof Object[]) {
			Object[] suppliers = (Object[]) obj;
			for (int i = 0, n = suppliers.length; i < n; i++) {
				if (suppliers[i] == null) {
					continue;
				}
				addSupplierInEntry((SupplierInfo) suppliers[i]);
			}
		} else if (obj instanceof SupplierInfo) {
				addSupplierInEntry((SupplierInfo) obj);
		} else {

		}
	}

	/**
	 * 添加供应商到分录
	 */
	protected void addSupplierInEntry(SupplierInfo supplier) throws Exception {

		if (supplierIDs.containsKey(supplier.getId().toString())) {
			throw new FDCInviteException(FDCInviteException.INQUIRY_SUPPLIER,
					new Object[] { "[" + supplier.getNumber() + "  "
							+ supplier.getName() + "]" });
		}

		IRow row = this.kdtEntrys.addRow();
		InquiryResultEntryInfo entry = (InquiryResultEntryInfo) createNewDetailData(kdtEntrys);
		entry.setSupplier(supplier);
		dataBinder.loadLineFields(kdtEntrys, row, entry);
		dealRowDataDisplay(row);

		supplierIDs.put(supplier.getId().toString(), supplier);
	}

	/**
	 * 处理分录供应商数据显示不全
	 * 
	 * @throws Exception
	 */
	private void dealEntryDataDisplay() throws Exception {
		int count = kdtEntrys.getRowCount();
		if (count <= 0) {
			return;
		}

		for (int r = 0; r < count; r++) {
			IRow row = kdtEntrys.getRow(r);
			dealRowDataDisplay(row);
		}
	}

	/**
	 * 处理分录中行数据显示不全
	 * 
	 * @param row
	 * @throws Exception
	 */
	private void dealRowDataDisplay(IRow row) throws Exception {
		ISupplier supplierDao = SupplierFactory.getRemoteInstance();
		
		SupplierInfo supplier = null;
		InquiryResultEntryInfo entry = null;
		SupplierPurchaseInfoInfo supplierPur = null;
		Object obj = row.getUserObject();
		if (obj != null && (obj instanceof InquiryResultEntryInfo)) {
			entry = (InquiryResultEntryInfo) obj;
		}

		if ((entry == null) || (entry.getSupplier() == null)) {
			return;
		}
		supplier = entry.getSupplier();
		IObjectPK pk = new ObjectUuidPK(supplier.getId());
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("*");
		selector.add("industry.name");
		selector.add("industry.number");
		
		supplier = supplierDao.getSupplierInfo(pk, selector);
		supplierPur = getSupplierPurchaseInfo(supplier);
		supplierIDs.put(supplier.getId().toString(), supplier);
		
		if (supplier.getIndustry() != null) {
			row.getCell("type").setValue(supplier.getIndustry().getName());
		}

		if (supplierPur == null) {
			return;
		}

		if (supplierPur.getGrade() != null) {
			row.getCell("class").setValue(supplierPur.getGrade());
		}
		// 取供应商采购信息中联系人的第一个
		if (supplierPur.getSupplierLinkMan() != null
				&& supplierPur.getSupplierLinkMan().get(0) != null) {
			SupplierLinkManInfo linkMan = supplierPur.getSupplierLinkMan().get(
					0);
			row.getCell("linkman").setValue(linkMan.getContactPerson());
			row.getCell("telephone").setValue(linkMan.getPhone());
		}
		
	}

	/**
	 * 获取供应商采购信息
	 * 
	 * @param supplier
	 * @return
	 * @throws Exception
	 */
	private SupplierPurchaseInfoInfo getSupplierPurchaseInfo(
			SupplierInfo supplier) throws Exception {

		// 查询实体
		EntityViewInfo evi = new EntityViewInfo();

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("grade");
		sic.add("supplier.*");
		sic.add("supplier.industry.name");
		sic.add("supplierLinkMan.*");
		evi.setSelector(sic);

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("supplier.id", supplier.getId().toString()));

		evi.setFilter(filter);

		SupplierPurchaseInfoCollection supplierPur = SupplierPurchaseInfoFactory
				.getRemoteInstance().getSupplierPurchaseInfoCollection(evi);
		if (supplierPur != null && supplierPur.get(0) != null) {
			return supplierPur.get(0);
		}

		return null;
	}

	/**************************************************************
	 * 招标立项带出相关值处理
	 **************************************************************/
	/**
	 * 招标立项值变化处理
	 */
	protected void prmtInviteProject_dataChanged(DataChangeEvent e)
			throws Exception {
		super.prmtInviteProject_dataChanged(e);
		Object obj = this.prmtInviteProject.getData();
		if (obj != null) {
			IInviteProject invPrjDao = InviteProjectFactory.getRemoteInstance();
			InviteProjectInfo invPrj = (InviteProjectInfo) obj;
			IObjectPK pk = new ObjectUuidPK(invPrj.getId());

			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			sic.add("id");
			sic.add("number");
			// 所属项目
			sic.add("project.id");
			sic.add("project.name");
			sic.add("project.number");
			// 招标类型
			sic.add("inviteType.id");
			sic.add("inviteType.name");
			sic.add("inviteType.number");

			invPrj = invPrjDao.getInviteProjectInfo(pk, sic);

			// 填充控件
			txtProjectNumber.setText(invPrj.getNumber());
		/*	if (invPrj.getProject() != null) {
				txtCurProject.setText(invPrj.getProject().getName());
				// 保存到当前实体上
				editData.setCurProject(invPrj.getProject());
			}*/

			if (invPrj.getInviteType() != null) {
				txtInviteType.setText(invPrj.getInviteType().getName());
			}
		}
	}

	/**
	 * 获取分录选中的供应商
	 * 
	 * @return
	 */
	private String getSelectedEntryId() {
		String id = "";
		KDTSelectBlock selBlock = kdtEntrys.getSelectManager().get();
		Object obj = kdtEntrys.getRow(selBlock.getTop()).getUserObject();
		if (obj != null) {
			id = ((InquiryResultEntryInfo) obj).getSupplier().getId()
					.toString();
		}
		return id;
	}

	private String getSupplierEditUIName() {
		return SupplierEditUI.class.getName();
	}

	/**
	 * private int selectIndex = -1; /** 获取当前选择行的主键
	 * 
	 * @return 返回当前选择行的主键，若当前选择行为空，或者当前选中行的主键列为空，则返回null
	 * 
	 *         protected String getSelectedKeyValue() { String keyFiledName =
	 *         "supplier.id"; int[] selectRows =
	 *         KDTableUtil.getSelectedRows(this.kdtEntrys); selectIndex = -1; if
	 *         (selectRows.length > 0) { selectIndex = selectRows[0]; } return
	 *         ListUiHelper.getSelectedKeyValue(selectRows, this.kdtEntrys,
	 *         keyFiledName); }
	 **/

	/**
	 * 描述：打开查看供应商界面的模式
	 */
	protected String getEditUIModal() {
		String openModel = UIConfigUtility.getOpenModel();
		if (openModel != null) {
			return openModel;
		} else {
			return UIFactoryName.NEWWIN;
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("*"));
		return sic;
	}

	/**
	 * 为提高性能屏蔽刷新功能 haiti_yang 2007-03-19
	 */
	public void checkSelectedRow() {
		if (kdtEntrys.getRowCount() == 0
				|| kdtEntrys.getSelectManager().size() == 0) {
			MsgBox.showWarning(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_MustSelected"));
			SysUtil.abort();
		}
	}

	/**
	 * 在操作选中的列表项之前，检查对象是否存在。不存在则异常通知控制器。
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws Exception
	 */
	private void checkObjectExists() throws BOSException, EASBizException,
			Exception {
		ISupplier iSupplier = SupplierFactory.getRemoteInstance();
		if (getSelectedEntryId() == null) {
			return;
		}
		if (!iSupplier.exists(new ObjectUuidPK(BOSUuid
				.read(getSelectedEntryId())))) {
			throw new EASBizException(EASBizException.CHECKEXIST);
		}
	}

	protected void verifyInputForSave() throws Exception {
		super.verifyInputForSave();
		if (StringUtils.isEmpty(editData.getName())) {
			MsgBox.showWarning(this, "文件标题不能为空！");
			txtFileTitle.requestFocus(true);
			abort();
		}
	}

	protected void verifyInputForSubmint() throws Exception {
		super.verifyInputForSubmint();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return InquiryResultFactory.getRemoteInstance();
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		InquiryResultEntryInfo entry = new InquiryResultEntryInfo();
		return entry;
	}

	protected IObjectValue createNewData() {
		InquiryResultInfo objectValue = new InquiryResultInfo();
		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		objectValue.setOrgUnit(SysContext.getSysContext().getCurrentCtrlUnit().castToFullOrgUnitInfo());
		return objectValue;
	}

	protected void attachListeners() {
	}

	protected void detachListeners() {

	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

}