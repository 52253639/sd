/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.programming.IProgramming;
import com.kingdee.eas.fdc.contract.programming.ProgrammingCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * output class name
 */
public class ProgrammingListUI extends AbstractProgrammingListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProgrammingListUI.class);
    //获取有权限的组织
	protected Set authorizedOrgs = null;
	
	public static final String IS_MODIFY = "isModify";
    
    /**
     * output class constructor
     */
    public ProgrammingListUI() throws Exception
    {
        super();
        uiInit();
    }
    
	private void uiInit() {
		this.btnSet.setIcon(EASResource.getIcon("imgTbtn_duizsetting"));
        this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
        this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
        actionModify.setEnabled(true);
        btnAudit.setEnabled(true);
        btnUnAudit.setEnabled(true);
        
        this.tblMain.checkParsed();
        StyleAttributes sa = tblMain.getColumn("version").getStyleAttributes();
        sa.setHorizontalAlign(HorizontalAlignment.CENTER);
        sa.setNumberFormat("0.0");
        tblMain.getColumn("aimCost.versionNumber").setStyleAttributes(sa);
        tblMain.getColumn("state").setStyleAttributes(sa);
        
        StyleAttributes saDate = tblMain.getColumn("createTime").getStyleAttributes();
        saDate.setNumberFormat("yyyy-MM-dd");
        saDate.setHorizontalAlign(HorizontalAlignment.CENTER);
        tblMain.getColumn("lastUpdateTime").setStyleAttributes(saDate);
	}

    public void onLoad() throws Exception {
    	super.onLoad();
    	initToolBar();
    	buildProjectTree();
    }

	private void initToolBar(){
    	this.actionAuditResult.setVisible(false);
    }
    
    protected String getEditUIName(){
        return ProgrammingEditUI.class.getName();
    }
    
    protected String getEditUIModal(){
        return UIFactoryName.NEWTAB;
    }
    
    protected ICoreBase getBizInterface() throws BOSException {
    	return ProgrammingFactory.getRemoteInstance();
    }
    
    protected IObjectValue createNewData()
    {
        ProgrammingInfo objectValue = new ProgrammingInfo();
        objectValue.setVersion(new BigDecimal("1.0"));
        return objectValue;
    }
    
    protected void treeProject_valueChanged(TreeSelectionEvent e) throws Exception {
    	treeSelectChange();
    }
    
    protected void treeSelectChange() throws Exception {
		DefaultKingdeeTreeNode projectNode  = (DefaultKingdeeTreeNode) treeProject.getLastSelectedPathComponent();
		Object project  = null;
		if(projectNode!=null) {
			project = projectNode.getUserObject();
			if (project instanceof CurProjectInfo) {
				actionAddNew.setEnabled(true);
				kDContainer1.setTitle(((CurProjectInfo) project).getDisplayName());
			} else {
				actionAddNew.setEnabled(false);
				kDContainer1.setTitle(null);
			}
		}
		
		mainQuery.setFilter(getTreeSelectFilter(project));
		SorterItemCollection sorter = mainQuery.getSorter();
    	sorter.clear();
//    	SorterItemInfo sorterName = new SorterItemInfo("name");
    	SorterItemInfo sorterVsersion = new SorterItemInfo("version");
    	sorterVsersion.setSortType(SortType.DESCEND);
//    	sorter.add(sorterName);
    	sorter.add(sorterVsersion);
		execQuery();
    }
    
	public void buildProjectTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();

		projectTreeBuilder.build(this, treeProject, actionOnLoad);
		
		authorizedOrgs = (Set)ActionCache.get("FDCBillListUIHandler.authorizedOrgs");
		if(authorizedOrgs==null){
			authorizedOrgs = new HashSet();
			Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
					 new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()),
			            OrgType.CostCenter, 
			            null,  null, null);
			if(orgs!=null){
				Set orgSet = orgs.keySet();
				Iterator it = orgSet.iterator();
				while(it.hasNext()){
					authorizedOrgs.add(it.next());
				}
			}		
		}
		if (treeProject.getRowCount() > 0) {
			treeProject.setSelectionRow(0);
			treeProject.expandPath(treeProject.getSelectionPath());
		}
	}
	
	protected FilterInfo getTreeSelectFilter(Object projectNode) throws Exception {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		if (projectNode != null && projectNode instanceof CoreBaseInfo) {
			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			String id = projTreeNodeInfo.getId().toString();
			filterItems.add(new FilterItemInfo("project.id", projTreeNodeInfo instanceof CurProjectInfo ? id : ""));
		}
		return filter;
	}
	
	private void changeActionState(boolean state) {
		actionAddNew.setEnabled(state);
		actionView.setEnabled(state);
		actionEdit.setEnabled(state);
		actionModify.setEnabled(state);
		actionRemove.setEnabled(state);
		actionWorkFlowG.setEnabled(state);
		actionNextPerson.setEnabled(state);
		actionAudit.setEnabled(state);
		actionUnAudit.setEnabled(state);
	}
	
	private void changeActonState(IItemAction action, boolean state) {
		if (action != null) {
			action.setEnabled(state);
		}
	}
	
	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
		super.tblMain_tableSelectChanged(e);
		initActionStatus();
	}

	private void initActionStatus() throws BOSException, EASBizException {
		changeActionState(true);
		
		ProgrammingInfo info = getSelectedInfo();
		FDCBillStateEnum state = info.getState();
		if (state == FDCBillStateEnum.SAVED || state == FDCBillStateEnum.SUBMITTED) {
			changeActonState(actionAddNew, false);
			changeActonState(actionModify, false);
			changeActonState(actionUnAudit, false);
		} else if (state == FDCBillStateEnum.AUDITTING) {
			changeActonState(actionAddNew, false);
			changeActonState(actionEdit, false);
			changeActonState(actionModify, false);
			changeActonState(actionRemove, false);
			changeActonState(actionAudit, false);
			changeActonState(actionUnAudit, false);
		} else if (state == FDCBillStateEnum.AUDITTED) {
			changeActonState(actionAddNew, false);
			changeActonState(actionEdit, false);
			changeActonState(actionRemove, false);
			changeActonState(actionNextPerson, false);
			changeActonState(actionAudit, false);
//			boolean isLastVersion = getServiceInterface().isLastVersion(
//					new ObjectUuidPK(getSelectedKeyValue()));
			if (!info.isIsLatest())
				changeActonState(actionModify, false);
		}
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String id = getSelectedKeyValue();
		ProgrammingInfo programmingInfo = ProgrammingFactory.getRemoteInstance().getProgrammingInfo(new ObjectUuidPK(id));
		if (programmingInfo.getState().equals(FDCBillStateEnum.SAVED)) {
			FDCMsgBox.showWarning("存在不符合审批条件的记录，请重新选择，保证所选的记录都是提交状态的");
			SysUtil.abort();
		}
		IProgramming service = getServiceInterface();
		service.audit(BOSUuid.read(id));
		this.setMessageText("审批成功");
		this.showMessage();
		refresh(e);
	}
	
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		checkReferenced(getSelectedIdValues());
		String id = getSelectedKeyValue();
		IProgramming service = getServiceInterface();
		boolean isLastVersion = service.isLastVersion(new ObjectUuidPK(id));
		if (!isLastVersion) {
			throw new EASBizException(new NumericExceptionSubItem("1", "非最新版本不能反审批"));
		}
		checkHighVersion();
		service.unAudit(BOSUuid.read(id));
		this.setMessageText("反审批成功");
		this.showMessage();
		refresh(e);
	}

	// 修订
	public void actionModify_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		checkAudited();
		checkLastVersion();
		checkHighVersion();
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		uiContext.put(IS_MODIFY, Boolean.TRUE);
		uiContext.put("modify", "modify");
		uiContext.put("parent", this);
		prepareUIContext(uiContext, e);
		IUIWindow ui = UIFactory.createUIFactory(getEditUIModal()).create(getEditUIName(), uiContext, null,	OprtState.EDIT);
		ui.show();
		setLocatePre(false);
		refresh(e);
		setPreSelecteRow();
		setLocatePre(true);
	}

	private void checkHighVersion() throws BOSException, EASBizException {
		ProgrammingInfo info = getSelectedInfo();
		BigDecimal version = info.getVersion().add(FDCHelper.ONE);
		String versionGroup = info.getVersionGroup();
		
		String oql = "where version = '".concat(version.toString()).concat("' and versionGroup = '")
			.concat(versionGroup).concat("'");
		if (getServiceInterface().exists(oql)) {
			throw new EASBizException(new NumericExceptionSubItem("1", "存在更高版本不能进行此操作"));
		}
	}

	private void checkAudited() throws BOSException, EASBizException {
		ProgrammingInfo info = getSelectedInfo();
		if (!FDCUtils.isBillAudited(info)) {
			throw new EASBizException(new NumericExceptionSubItem("1", "非审批单据不能修订"));
		}
	}

	private void checkLastVersion() throws BOSException, EASBizException {
		String id = getSelectedKeyValue();
		IProgramming service = getServiceInterface();
		boolean isLastVersion = service.isLastVersion(new ObjectUuidPK(id));
		if (!isLastVersion) {
			throw new EASBizException(new NumericExceptionSubItem("1", "非最新版本不能修订"));
		}
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode node  = (DefaultKingdeeTreeNode) treeProject.getLastSelectedPathComponent();
		if (node != null) {
			Object obj = node.getUserObject();
			if (obj instanceof CurProjectInfo) {
				uiContext.put("treeSelectedObj", obj);
			}
		}
		super.prepareUIContext(uiContext, e);
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		
		DefaultKingdeeTreeNode node  = (DefaultKingdeeTreeNode) treeProject.getLastSelectedPathComponent();
		FDCClientUtils.checkSelectProj(this, node);
//		if (node == null) {
//			throw new EASBizException(new NumericExceptionSubItem("1", "请选择工程项目"));
//		}
//		
		Object obj = node.getUserObject();
//		if (!(obj instanceof CurProjectInfo)) {
//			throw new EASBizException(new NumericExceptionSubItem("1", "请选择工程项目"));
//		}
//		
		
		String proId = ((CurProjectInfo) obj).getId().toString();
		boolean isExist = getBizInterface().exists("where project = '".concat(proId).concat("'"));
		if (isExist) {
			throw new EASBizException(new NumericExceptionSubItem("1", "此工程项目下已存在合约框架\n不允许再新增"));
		}
		super.actionAddNew_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		checkReferenced(getSelectedIdValues());
		FDCBillStateEnum state = getSelectedInfo().getState();
		if (state != FDCBillStateEnum.SAVED && state != FDCBillStateEnum.SUBMITTED) {
			throw new EASBizException(new NumericExceptionSubItem("1", "该单据状态不允许此操作"));
		}
		super.actionRemove_actionPerformed(e);
		TreePath selectionPath = treeProject.getSelectionPath();
		treeProject.setSelectionRow(0);
		treeProject.setSelectionPath(selectionPath);
		changeActionState(false);
		if (tblMain.getRowCount() < 1) {
			changeActonState(actionAddNew, true);
		}
	}
	
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		// 只读状态的附件管理器
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		acm.showAttachmentListUIByBoID(getSelectedKeyValue(), this, false);
	}

	/**
	 * 框架合约被引用不允许删除
	 * @param idList
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void checkReferenced(List idList) throws BOSException, EASBizException {
		String ids = FDCUtils.buildBillIds(idList);
		String oql = "select programming, programming.isCiting where id in ".concat(ids);
		ProgrammingCollection collection = ((IProgramming) getBizInterface()).getProgrammingCollection(oql);
		for (int i = 0, size = collection.size(); i < size; i++) {
			ProgrammingInfo info = collection.get(i);
			ProgrammingContractCollection entries = info.getEntries();
			for (int j = 0, count = entries.size(); j < count; j++) {
				ProgrammingContractInfo entry = entries.get(j);
				if (entry.isIsCiting()) {
					throw new EASBizException(new NumericExceptionSubItem("1", 
							"合约框架中存在被引用的框架合约\n不允许此操作"));
				}
			}
		}
	}
	
	private IProgramming getServiceInterface() throws BOSException {
		IProgramming service = (IProgramming) getBizInterface();
		return service;
	}
	
	private ProgrammingInfo getSelectedInfo() throws BOSException, EASBizException {
		checkSelected();
		return getServiceInterface().getProgrammingInfo(new ObjectUuidPK(getSelectedKeyValue()), getSelectors());
	}
}