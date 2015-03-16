/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
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
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.ormapping.ObjectReferedException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeReasonFactory;
import com.kingdee.eas.fdc.sellhouse.ChangeReasonInfo;
import com.kingdee.eas.fdc.sellhouse.IChangeReason;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.framework.client.ListUIController;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class ChangeReasonListUI extends AbstractChangeReasonListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ChangeReasonListUI.class);
    protected ChangeBizTypeEnum bizType=null;
    protected boolean isSaleHouseOrg= FDCSysContext.getInstance().getOrgMap().containsKey(SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
    protected FullOrgUnitInfo org=SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
    public ChangeReasonListUI() throws Exception
    {
        super();
    }

    protected FDCDataBaseInfo getBaseDataInfo() {
    	ChangeReasonInfo reasonInfo = new ChangeReasonInfo();
    	reasonInfo.setIsEnabled(true);
		return reasonInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ChangeReasonFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return ChangeReasonEditUI.class.getName();
	}
	
	public void onLoad() throws Exception {
		initTree();
		super.onLoad();
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.treeMain.setSelectionRow(0);
		
		if (!isSaleHouseOrg)
		{
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
		}
	}
	
	protected void initTree() throws Exception {
		KDTreeNode root = new KDTreeNode("变更原因");
		KingdeeTreeModel mode=new KingdeeTreeModel(root);
		for(int i=0;i<ChangeBizTypeEnum.getEnumList().size();i++){
			KDTreeNode child = new KDTreeNode(ChangeBizTypeEnum.getEnumList().get(i)+"原因");
			child.setUserObject(ChangeBizTypeEnum.getEnumList().get(i));
			root.add(child);
		}
		mode.setRoot(root);
		this.treeMain.setModel(mode);
	}
    protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		uiContext.put("bizType", bizType);
	}
	
	protected Set getAllOrgIDSet(FullOrgUnitInfo info) throws BOSException{
		Set id=new HashSet();
		if(info!=null&&info.getParent()!=null){
			id.add(info.getParent().getId());
			id.addAll(getAllOrgIDSet(info.getParent()));
		}
		id.add(info.getId());
		return id;
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		try {
			FilterInfo filter = new FilterInfo();
			if(node!=null&&node.getUserObject() instanceof ChangeBizTypeEnum){
				filter.getFilterItems().add(new FilterItemInfo("bizType", ((ChangeBizTypeEnum)node.getUserObject()).getValue()));	
				filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", getAllOrgIDSet(org),CompareType.INNER));	
				
			}else{
				filter.getFilterItems().add(new FilterItemInfo("bizType", "null"));	
			}
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
		} catch (BOSException e) {
			handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if(node!=null&&node.getUserObject() instanceof ChangeBizTypeEnum){
			bizType=(ChangeBizTypeEnum)node.getUserObject();
			if(isSaleHouseOrg){
				this.actionAddNew.setEnabled(true);
			}
		}else{
			this.actionAddNew.setEnabled(false);
		}
		this.execQuery();
	}
    
    protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
    	if(isSaleHouseOrg){
    		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
    		if (activeRowIndex != -1) {
    			if (this.tblMain.getRow(activeRowIndex).getCell("isEnabled") != null) {
    				boolean status = ((Boolean) this.tblMain.getCell(activeRowIndex, "isEnabled").getValue()).booleanValue();
    				actionCancelCancel.setEnabled(!status);
    				actionCancel.setEnabled(status);
    			}
    		} else {
    			actionCancelCancel.setEnabled(false);
    			actionCancel.setEnabled(false);
    		}
    	}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0)
			return;
		String selectID = this.tblMain.getCell(activeRowIndex, "id").getValue().toString();
		SelectorItemCollection sic = super.getSelectors();
        sic.add("orgUnit.id");
		if(!((IChangeReason)getBizInterface()).getChangeReasonInfo(new ObjectUuidPK(selectID),sic).getOrgUnit().getId().equals(org.getId())){
			FDCMsgBox.showWarning(this,"创建组织才能进行修改!");
			return;
		}
		if (outPutWarningSentanceAndVerifyCancelorCancelCancelByID("修改", selectID)) {// 判断是否启用禁用
			return;
		}
		try {
    		this.getBizInterface().isReferenced(new ObjectUuidPK(selectID));
		} catch (ObjectReferedException ex) {
			FDCMsgBox.showWarning(this,"已经发生业务不能修改!");
			return;
		}
		IUIWindow uiWindow = showEditUI1(e);
		uiWindow.show();

		if (isDoRefresh(uiWindow)) {
			if (UtilRequest.isPrepare("ActionRefresh", this)) {
				prepareRefresh(null).callHandler();
			}
			setLocatePre(false);
			refresh(e);
			setPreSelecteRow();
			setLocatePre(true);
		}
	}

	private IUIWindow showEditUI1(ActionEvent e) throws EASBizException, BOSException, Exception {
		checkSelected();
		checkObjectExists();
		UIContext uiContext = new UIContext(this);
		prepareUIContext(uiContext, e);
		IUIWindow uiWindow = null;
		if (SwingUtilities.getWindowAncestor(this) != null && SwingUtilities.getWindowAncestor(this) instanceof JDialog) {
			uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(getEditUIName(), uiContext, null,
					OprtState.EDIT);
		} else {
			uiWindow = UIFactory.createUIFactory(getEditUIModal()).create(getEditUIName(), uiContext, null,
					OprtState.EDIT);
		}
		return uiWindow;
	}

	private void checkObjectExists() throws BOSException, EASBizException, Exception {
		if (getSelectedKeyValue() == null) {
			return;
		}
		if (!getBizInterface().exists(new ObjectUuidPK(BOSUuid.read(getSelectedKeyValue())))) {
			refreshList();
			throw new EASBizException(EASBizException.CHECKEXIST);
		}
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0)
			return;
		String selectID = this.tblMain.getCell(activeRowIndex, "id").getValue().toString();
		
		SelectorItemCollection sic = super.getSelectors();
        sic.add("orgUnit.id");
		if(!((IChangeReason)getBizInterface()).getChangeReasonInfo(new ObjectUuidPK(selectID),sic).getOrgUnit().getId().equals(org.getId())){
			FDCMsgBox.showWarning(this,"创建组织才能进行删除!");
			return;
		}
		if (outPutWarningSentanceAndVerifyCancelorCancelCancelByID("删除", selectID)) {
			return;
		}
		checkSelected();
		try {
    		this.getBizInterface().isReferenced(new ObjectUuidPK(selectID));
		} catch (ObjectReferedException ex) {
			FDCMsgBox.showWarning(this,"已经发生业务不能删除!");
			return;
		}
		if (confirmRemove()) {
			Remove();
			this.refresh(e);
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {

		UIContext uiContext = new UIContext(this);
		prepareUIContext(uiContext, e);
		this.getUIContext().putAll(uiContext);

		IUIWindow uiWindow = null;

		((ListUIController) super.getUIController()).actionAddNew();

		uiWindow = ((ListUIController) super.getUIController()).getNavigator().getUIWindow();

		Window win = SwingUtilities.getWindowAncestor((Component) uiWindow.getUIObject());
		if (!win.isActive()) {
			if (win instanceof JFrame && ((JFrame) win).getExtendedState() == Frame.ICONIFIED) {
				((JFrame) win).setExtendedState(Frame.NORMAL);
			}
		}

		setActionEvent(e);
		if (uiWindow != null && isDoRefresh(uiWindow)) {
			if (UtilRequest.isPrepare("ActionRefresh", this)) {
				prepareRefresh(null).callHandler();
			}
			refresh(e);
			setPreSelecteRow();
		}

	}
	
	protected void setIsEnabled(boolean flag) throws Exception {
		int activeRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (activeRowIndex < 0)
			return;
		String id = tblMain.getRow(activeRowIndex).getCell("id").getValue().toString().trim();
		FDCDataBaseInfo info = getBaseDataInfo();
		info.setId(BOSUuid.read(id));
		info.setIsEnabled(flag);
		String number = tblMain.getRow(activeRowIndex).getCell("number").getValue().toString().trim();
		String name = tblMain.getRow(activeRowIndex).getCell("name").getValue().toString().trim();
		info.setNumber(number);
		info.setName(name);
		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add(new SelectorItemInfo("isEnabled"));
		String message = null;
		if (flag) {
			getBizInterface().updatePartial(info, sic);
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK");
		}
		else {
			getBizInterface().updatePartial(info, sic);
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK");
		}
		setMessageText(message);
		showMessage();
		tblMain.refresh();
		loadFields();
		actionCancel.setEnabled(false);
		actionCancelCancel.setEnabled(false);
	}
}