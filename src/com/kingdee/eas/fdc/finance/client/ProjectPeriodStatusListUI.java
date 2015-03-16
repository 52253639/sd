/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolUtils;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectListUI;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.IProjectPeriodStatus;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusFactory;
import com.kingdee.eas.fi.gl.client.InitClientHelp;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ProjectPeriodStatusListUI extends AbstractProjectPeriodStatusListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectPeriodStatusListUI.class);
    
	private DefaultKingdeeTreeNode lastSelectNode = null;
	
	//当前组织是否是财务组织
	private String curOrgId = null;
	private boolean isCompany = false;
	
	//
	private Map initData = null;
    /**
     * output class constructor
     */
    public ProjectPeriodStatusListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {

    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {

    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
        //super.treeMain_valueChanged(e);
    	//tblMain.removeRows();
    	
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			// return;
			if (lastSelectNode != null) {
				node = this.lastSelectNode;
			} else {
				return;
			}
		}
		if (OrgViewUtils.isTreeNodeDisable(node)) {
			return;
		}
		
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("longNumber"));
			//sic.add(new SelectorItemInfo("id"));
			//sic.add(new SelectorItemInfo("fullOrgUnit.*"));
			projectInfo = CurProjectFactory.getRemoteInstance()
					.getCurProjectInfo(new ObjectStringPK(projectInfo.getId().toString()),sic);
			//FullOrgUnitInfo info = projectInfo.getFullOrgUnit();
			String longNumber = projectInfo.getLongNumber().toString();
			HashSet lnUps = new HashSet();
			lnUps.add(projectInfo.getId().toString());
			try {
				CurProjectCollection curProjectCollection = CurProjectFactory
						.getRemoteInstance().getCurProjectCollection("select id where longNumber like '"+ longNumber + "!%'");
				
				for (int i = 0; i < curProjectCollection.size(); i++) {
					lnUps.add(curProjectCollection.get(i).getId().toString());
				}
			} catch (BOSException e1) {
				handUIException(e1);
			}
			
			FilterInfo filterInfo = new FilterInfo();		
			if (lnUps.size() != 0) {
				filterInfo.getFilterItems().add(
						new FilterItemInfo("project.id", lnUps, CompareType.INCLUDE));
//				filterInfo.getFilterItems().add(
//						new FilterItemInfo("project.isEnabled", new Integer(1)));
				this.mainQuery.setFilter(filterInfo);
			}
		
			tblMain.removeRows();// 触发新查询
			for (int i = 0; i < tblMain.getRowCount(); i++) {// 定位节点相应的叙事簿明细
				if (projectInfo.getId().toString().equals(
						tblMain.getRow(i).getCell("id").getValue().toString())) {
					tblMain.getSelectManager().select(i, 2);
					break;
				}
			}
		
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
;
			tblMain.getSelectManager().select(0, 0);
			if ((node.getChildCount() > 0)
					&& (((DefaultKingdeeTreeNode) node.getChildAt(0))
							.getUserObject() instanceof OrgStructureInfo)) {// 选择结点不适合新增,灰掉新增按钮
				OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
				if (oui == null || oui.getUnit() == null)
					return;
				
				FullOrgUnitInfo info = oui.getUnit();
				HashSet lnUps = new HashSet();
				ProjectListUI.genLeafNodesIdSet(node, lnUps);
				lnUps.add(info.getId().toString());

				FilterInfo filterInfo = new FilterInfo();
				if (lnUps.size() != 0) {
					filterInfo.getFilterItems().add(
							new FilterItemInfo("orgUnit.id", lnUps,CompareType.INCLUDE));
					filterInfo.setMaskString(" #0 ");
					this.mainQuery.setFilter(filterInfo);
				}

				execQuery();

			} else {
				OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
				if (oui == null || oui.getUnit() == null)
					return;
				FullOrgUnitInfo info = oui.getUnit();
				String id = info.getId().toString();

				FilterInfo filterInfo = new FilterInfo();
				filterInfo.getFilterItems().add(
						new FilterItemInfo("orgUnit.id", id,	CompareType.EQUALS));
				filterInfo.setMaskString(" #0 ");
				this.mainQuery.setFilter(filterInfo);
	
				execQuery();	
			}			
		}
    }

    /**
     * output treeMain_mouseClicked method
     */
    protected void treeMain_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
		lastSelectNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
        
		//initData = ((IProjectPeriodStatus)getBizInterface()).closeAll(curOrgId,isCompany);
		
    }

    /**
     * output actionCloseInit_actionPerformed method
     */
    public void actionCloseInit_actionPerformed(ActionEvent e) throws Exception
    {
    	//检查是否选中
    	this.checkSelected();
    	
    	List seleIds = this.getSelectedIdValues();

    	initData = ((IProjectPeriodStatus)getBizInterface()).closeInit(seleIds,curOrgId,isCompany);
		
		execQuery();
		
    }

    /**
     * output actionCloseProject_actionPerformed method
     */
    public void actionCloseProject_actionPerformed(ActionEvent e) throws Exception
    {
    	super.checkSelected();
    	
    	List seleIds = this.getSelectedIdValues();

		((IProjectPeriodStatus)getBizInterface()).closeProject(seleIds);
		
		execQuery();
    	
    }

    protected void execQuery()
    {
    	super.execQuery();
    	
		try {
			initData = 	((IProjectPeriodStatus)getBizInterface()).fetchInitData(curOrgId,isCompany);
			setButtonStatus();
		} catch (Exception e) {			
			e.printStackTrace();
		}
    }
    
    /**
     * output actionCloseInitAll_actionPerformed method
     */
    public void actionCloseInitAll_actionPerformed(ActionEvent e) throws Exception
    {
    	//判断当前财务组织系统状态控制已经设置正确
    	
    	
    	//确认全部结束初始化
		int result = MsgBox.showConfirm2(this,"确认全部结束初始化");
		
		if(result == MsgBox.OK){
			initData = ((IProjectPeriodStatus)getBizInterface()).closeAll(curOrgId,isCompany);
			
			execQuery();

		}	
		
    }
    
    /**
     * output actionUnCloseInit_actionPerformed method
     */
    public void actionUnCloseInit_actionPerformed(ActionEvent e) throws Exception
    {
    	//检查是否选中
    	this.checkSelected();
    	
    	
    	List seleIds = this.getSelectedIdValues();

    	initData = ((IProjectPeriodStatus)getBizInterface()).closeUnInit(seleIds,curOrgId,isCompany);
				
		execQuery();
		
    }

    /**
     * output actionUnCloseProject_actionPerformed method
     */
    public void actionUnCloseProject_actionPerformed(ActionEvent e) throws Exception
    {
    	//检查是否选中
    	this.checkSelected();
    	
    	List seleIds = this.getSelectedIdValues();

		((IProjectPeriodStatus)getBizInterface()).closeUnProject(seleIds);
		
		//MsgBox.
		execQuery();
    }

    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
    	
    }  

    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
    	
    }
    
    /**
     * output actionUnCloseInitAll_actionPerformed method
     */
    public void actionUnCloseInitAll_actionPerformed(ActionEvent e) throws Exception
    {
    	//确认全部结束初始化
		int result = MsgBox.showConfirm2(this,"确认全部反初始化");
		
		if(result == MsgBox.OK){
			initData = 	((IProjectPeriodStatus)getBizInterface()).closeUnAll(curOrgId,isCompany);
			
			execQuery();
			
		}
    }
    
    
	public void onLoad() throws Exception {
		super.onLoad();
		
		//校验是否启用了成本财务一体化.启用成本财务一体化
		boolean isIncorporation = false;
		//启用成本财务一体化
		HashMap paramItem = FDCUtils.getDefaultFDCParam(null,SysContext.getSysContext().getCurrentFIUnit().getId().toString());
		if(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION)!=null){
			isIncorporation = Boolean.valueOf(paramItem.get(FDCConstants.FDC_PARAM_INCORPORATION).toString()).booleanValue();
		}
		if(!isIncorporation){
			MsgBox.showError(this,"没有启用成本月结");
			SysUtil.abort();
		}
		String comid  = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		((IProjectPeriodStatus)getBizInterface()).paramCheck(comid);
		
		//构建左边工程项目树
		FDCTableHelper.setColumnMoveable(tblMain, true);
		buildProjectTree();

		
		//初始化按钮
		initData = 	((IProjectPeriodStatus)getBizInterface()).fetchInitData(curOrgId,isCompany);
		setButtonStatus();
		
		this.treeMain.setSelectionRow(0);
	}
	
	
	private void setButtonStatus() throws EASBizException, BOSException{
		
		//当前
		boolean isAllClosed =((Boolean)initData.get("isAllClosed")).booleanValue();
		boolean isUnAllClosed = ((Boolean)initData.get("isUnAllClosed")).booleanValue();
		boolean hasClosed = ((Boolean)initData.get("hasClosed")).booleanValue();
		
		//设置按钮状态
		if(isAllClosed){
			actionUnCloseInit.setEnabled(true);
			actionUnCloseInitAll.setEnabled(true);
			actionCloseInit.setEnabled(false);
			actionCloseInitAll.setEnabled(false);
		}else 		
		if(isUnAllClosed){
			actionUnCloseInit.setEnabled(false);
			actionUnCloseInitAll.setEnabled(false);
			actionCloseInit.setEnabled(true);
			actionCloseInitAll.setEnabled(true);
		}else 		
		if(hasClosed){
			actionCloseInit.setEnabled(true);
			actionCloseInitAll.setEnabled(true);
			actionUnCloseInit.setEnabled(true);
			actionUnCloseInitAll.setEnabled(true);
		}	
	}
	
    /**
     * 设置各按钮的文字与图标状态
     */
    protected void initWorkButton()
    {
        super.initWorkButton();
        
        this.actionAddNew.setEnabled(false);
        this.actionEdit.setEnabled(false);
        this.actionView.setEnabled(false);
        this.actionRemove.setEnabled(false);
        this.actionCancel.setEnabled(false);
        this.actionCancelCancel.setEnabled(false);
        this.actionLocate.setEnabled(false);
        this.actionQuery.setEnabled(false);     
        
        this.actionAddNew.setVisible(false);
        this.actionEdit.setVisible(false);
        this.actionView.setVisible(false);
        this.actionRemove.setVisible(false);
        this.actionCancel.setVisible(false);
        this.actionCancelCancel.setVisible(false);
        this.actionLocate.setVisible(false);
        this.actionQuery.setVisible(false);
        
        InitClientHelp.setIcon(this.btnClose, InitClientHelp.IconHelp.CLOSEINIT);
        InitClientHelp.setIcon(this.menuItemClose, InitClientHelp.IconHelp.CLOSEINIT);
        
        InitClientHelp.setIcon(this.btnCloseInitAll, InitClientHelp.IconHelp.CLOSEALLINIT);
        InitClientHelp.setIcon(this.menuItemCloseAll, InitClientHelp.IconHelp.CLOSEALLINIT);
        
        InitClientHelp.setIcon(this.btnUnClose, InitClientHelp.IconHelp.CANCELINIT);
        InitClientHelp.setIcon(this.menuItemUnClose, InitClientHelp.IconHelp.CANCELINIT);
        
        InitClientHelp.setIcon(this.btnUnCloseInitAll, InitClientHelp.IconHelp.CANCELALLINIT);
        InitClientHelp.setIcon(this.menuItemUnCloseAll, InitClientHelp.IconHelp.CANCELALLINIT);
        
        btnCloseProject.setIcon(EASResource.getIcon("imgTbtn_grantcollocate"));
        menuItemColseProject.setIcon(EASResource.getIcon("imgTbtn_grantcollocate"));
        
        btnUnCloseProject.setIcon(EASResource.getIcon("imgTbtn_cancelcollocate"));
        menuItemUnColseProject.setIcon(EASResource.getIcon("imgTbtn_cancelcollocate"));
        
        this.menuEdit.setVisible(false);
        this.menuBiz.setVisible(true);
        menuBiz.setEnabled(true);
        
        actionUnCloseProject.setEnabled(true);
        
        actionUnCloseProject.setVisible(false);
        actionCloseProject.setVisible(false);
    }

	protected ITreeBase getTreeInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}
	
	protected void refresh(ActionEvent e) throws Exception {

		//CacheServiceFactory.getInstance().discardQuery(this.mainQueryPK);
		execQuery();
		buildProjectTree();
	}
	
	private void buildProjectTree() throws Exception {
		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false);
		treeMain.setShowsRootHandles(true);
		projectTreeBuilder.build(this, treeMain, actionOnLoad);
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getModel().getRoot();
		
		if (node.getUserObject() instanceof CurProjectInfo) {
			curOrgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
			isCompany = false;
		}else{
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui == null || oui.getUnit() == null)
				return;
			
			FullOrgUnitInfo info = oui.getUnit();
			
			curOrgId = info.getId().toString();
			isCompany = true;
		}
	}
	
    protected boolean isIgnoreCUFilter()
    {
        return true;
    }
    
    protected ICoreBase getBizInterface() throws Exception
    {
        return ProjectPeriodStatusFactory.getRemoteInstance();
        
    }
    

	/**
	 * 初始化时构造Tree，几乎不用重载
	 */
	protected void initTree() throws Exception {

	}
	
}