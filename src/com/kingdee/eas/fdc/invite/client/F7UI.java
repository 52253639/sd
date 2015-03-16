/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Container;
import java.awt.event.ActionEvent;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.cssp.CSStringUtils;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.eas.fdc.invite.AppraiseGuideLineFactory;
import com.kingdee.eas.fdc.invite.AppraiseGuideLineInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.f7.IF7Provider;
import com.kingdee.eas.scm.common.client.GeneralF7TreeListUI;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class F7UI extends AbstractF7UI implements IF7Provider
{
    private static final Logger logger = CoreUIObject.getLogger(F7UI.class);
    
    private FilterInfo quickFilterInfo;

	private IMetaDataPK queryPK;

	private boolean isMultiSelect = false;

	private IMetaDataPK treeBasePK;

	private String treeBaseBOSType;
	
	 private boolean isFirstLoaded = true;

	public IMetaDataPK getTreeBasePK() {
		return treeBasePK;
	}

	public void setTreeBasePK(IMetaDataPK treeBasePK) {
		this.treeBasePK = treeBasePK;
	}

	public String getPropName() {
		  return (propName != null ? propName : "browseGroup.id");
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public FilterInfo getDefaultFilterInfo() {
		return defaultFilterInfo;
	}

	public void setDefaultFilterInfo(FilterInfo defaultFilterInfo) {
		this.defaultFilterInfo = defaultFilterInfo;
	}

	public DefaultKingdeeTreeNode getOldTreeNode() {
		return oldTreeNode;
	}

	public void setOldTreeNode(DefaultKingdeeTreeNode oldTreeNode) {
		this.oldTreeNode = oldTreeNode;
	}

	public static Logger getLogger() {
		return logger;
	}

	public FilterInfo getQuickFilterInfo() {
		return quickFilterInfo;
	}

	public IMetaDataPK getQueryPK() {
		return queryPK;
	}

	public boolean isMultiSelect() {
		return isMultiSelect;
	}

	public String getTreeBaseBOSType() {
		return treeBaseBOSType;
	}

	private String propName;

	private FilterInfo defaultFilterInfo;

	private DefaultKingdeeTreeNode oldTreeNode = null;

    
    
    
//    public void onLoad() throws Exception {
//		
//			setIsNeedDefaultFilter(false);
//			this.actionQuery.setVisible(false);//这里这样设置是因为第一次query会被忽略掉，导致框架不找表格设置属性
//												// ，冻结等无效
//			super.onLoad();
//			this.actionQuery.setVisible(true);
//			this.treeView.setTitle("评标指标类型");
//			// this.setAssociatePropertyName("guideLineType.id");
//			// FilterInfo filter = new FilterInfo();
//			// filter.getFilterItems().add(new
//			// FilterItemInfo("isEnable",Boolean.TRUE));
//			// this.setFilterForQuery(filter);
//			// addKDTableLisener();
//			tblMain.setRefresh(true);
//			tblMain.reLayoutAndPaint();
//
//			this.treeView.setShowControlPanel(false);
//
//	
//	}

    
    
    public Object getData() {
        if (this.isMultiSelect) {
            Object[] keyValues = getSelectedIdValues().toArray();
	        if (keyValues != null && keyValues.length>0) {
	            try {
	                //获得TreeListUI对应的业务接口
	                ICoreBase iBiz = getBizInterface();
	                ObjectUuidPK pk = null;
	                AppraiseGuideLineInfo[] supplierInfo = new AppraiseGuideLineInfo[keyValues.length];
	                for(int i=0,c=keyValues.length;i<c;i++){
	                    pk = new ObjectUuidPK(BOSUuid.read(keyValues[i].toString()));
	                    supplierInfo[i] = (AppraiseGuideLineInfo)iBiz.getValue(pk);
	                }
	                if(keyValues.length == 1){
	                    return supplierInfo[0];
	                }else{
	                    return supplierInfo;
	                }
	            } catch (Exception er) {
	                super.handUIException(er);
	            }
	        }
        }else {
            String keyValue = getSelectedKeyValue();
            if (keyValue != null) {
                try {
                    //获得TreeListUI对应的业务接口
                    ICoreBase iBiz = getBizInterface();
                    ObjectUuidPK pk = new ObjectUuidPK(BOSUuid.read(keyValue));
                    System.out.println(pk);
                    Object o = iBiz.getValue(pk);
                    System.out.println(o);
                    return o;
                } catch (Exception er) {
                    super.handUIException(er);
                }
            }
        }
        return null;
    }

    /**
     * 描述：重载KDTable双击事件，返回相应的供应商vo
     * 
     * @author:dongpeng
     * @see com.kingdee.eas.framework.client.AbstractListUI#tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent)
     */
    protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
        if (isOrderForClickTableHead() && e.getClickCount() != 2) {
            super.tblMain_tableClicked(e);
        }
        if (getData() != null && e.getClickCount() == 2) {
            Container c = getParent();
            while (c != null && !c.getClass().equals(GeneralF7TreeListUI.class)) {
                c = c.getParent();
            }
            ((GeneralF7TreeListUI) c).clickOKBtn();
        }
        //点击KDTable时，不清空快速查询条件，只有点击树节点时清空
        oldTreeNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
    }

    public void onLoad() throws Exception {
    	/*
    	 * nicklas.guan date:07.06.04
    	 * 为性能优化，避免加载默认过滤方案。
    	 */
    	setIsNeedDefaultFilter(false);
    	
		this.actionQuery.setVisible(false);//这里这样设置是因为第一次query会被忽略掉，导致框架不找表格设置属性，冻结等无效
		super.onLoad();
		this.actionQuery.setVisible(true);
		initInterface();
        //addKDTableLisener();
        tblMain.setRefresh(true);
        tblMain.reLayoutAndPaint();
        isFirstLoaded = false;

        if(!this.isMultiSelect){
        	 tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
        }else{
        	tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
        }
    }
    
    
    
    private void initInterface() {
        treeView.setShowControlPanel(false);
        pnlMain.setDividerLocation(210);
        this.remove(contGroupStandard);
        this.add(contGroupStandard, new KDLayout.Constraints(10, 10, 210, 19, 0));
         this.setUITitle("请选择评标指标类型！");
    }
    
    
    
    protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
    	if (this.mainQuery != null) {
			int start = ((Integer) e.getParam1()).intValue();
			int length = ((Integer) e.getParam2()).intValue() - start + 1;
			try {
				// 这里可以取到QueryPK, 同时我们也要指定ViewInfo
				IQueryExecutor exec;
				if (defaultFilterInfo != null) {
					if (queryPK != null) {
						exec = this.getQueryExecutor(this.queryPK,
								getDefaultQuery());
					} else {
						exec = this.getQueryExecutor(this.mainQueryPK,
								getDefaultQuery());
					}
				} else if (quickFilterInfo != null) {
					if (queryPK != null) {
						exec = this.getQueryExecutor(this.queryPK,
								getMainQuery());
					} else {
						exec = this.getQueryExecutor(this.mainQueryPK,
								getMainQuery());
					}
				} else {
					// 如果没有快速过滤条件时, 则同原有的刷新行为一致
					if (queryPK != null) {
						exec = this.getQueryExecutor(this.queryPK,
								this.mainQuery);
					} else {
						exec = this.getQueryExecutor(this.mainQueryPK,
								this.mainQuery);
					}
				}

				IRowSet rowSet = exec.executeQuery(start, length);
				e.setRowSet(rowSet);
				onGetRowSet(rowSet);
			} catch (Exception ee) {
				handUIException(ee);
			}
		}
    }
    
    public EntityViewInfo getDefaultQuery(){
    	EntityViewInfo evi = this.mainQuery;

		FilterInfo tmpFilterInfo;
		try {
			tmpFilterInfo = (FilterInfo) defaultFilterInfo.clone();
			if (quickFilterInfo != null) {
				tmpFilterInfo.mergeFilter(quickFilterInfo, "and");
			}
			tmpFilterInfo.mergeFilter(this.mainQuery.getFilter(), "and");
			evi.setFilter(tmpFilterInfo);
		} catch (Exception e) {
			super.handUIException(e);
		}
		return evi;
    }
	/**
     * output class constructor
     */
    
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
    	DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
        if(oldTreeNode == null){//初始化TreeNode
            oldTreeNode = treeNode;
        }
        ///treeMain.getSelectionPath().getLastPathComponent();
        if(treeNode != null && !treeNode.getText().equals(oldTreeNode.getText())){
            quickFilterInfo = null;
            Container c = getParent();
            while (c != null && !c.getClass().equals(GeneralF7TreeListUI.class)) {
                c = c.getParent();
                ((GeneralF7TreeListUI)c).setQuickQuery(false);
            }
        }
        oldTreeNode = treeNode;
        super.treeMain_valueChanged(e);
    }
    
    public F7UI() throws Exception
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
//    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
//    {
//        super.tblMain_tableClicked(e);
//    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output chkIncludeChild_itemStateChanged method
     */
    protected void chkIncludeChild_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        super.chkIncludeChild_itemStateChanged(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRefresh_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionImportData_actionPerformed
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportData_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionExportData_actionPerformed
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }

    /**
     * output actionToExcel_actionPerformed
     */
    public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToExcel_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionPublishReport_actionPerformed
     */
    public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPublishReport_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionQueryScheme_actionPerformed
     */
    public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQueryScheme_actionPerformed(e);
    }

	protected Container getParentUI() {
		
			Container c = getParent();
			while (c != null && !c.getClass().equals(GeneralF7TreeListUI.class)) {
	            c = c.getParent();
	        }
			return c;
		
	}
	
	
//	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
//		if (this.mainQuery != null) {
//			int start = ((Integer) e.getParam1()).intValue();
//			int length = ((Integer) e.getParam2()).intValue() - start + 1;
//			try {
//				// 这里可以取到QueryPK, 同时我们也要指定ViewInfo
//				IQueryExecutor exec;
//				if (defaultFilterInfo != null) {
//					if (queryPK != null) {
//						exec = this.getQueryExecutor(this.queryPK,
//								getDefaultQuery());
//					} else {
//						exec = this.getQueryExecutor(this.mainQueryPK,
//								getDefaultQuery());
//					}
//				} else if (quickFilterInfo != null) {
//					if (queryPK != null) {
//						exec = this.getQueryExecutor(this.queryPK,
//								getMainQuery());
//					} else {
//						exec = this.getQueryExecutor(this.mainQueryPK,
//								getMainQuery());
//					}
//				} else {
//					// 如果没有快速过滤条件时, 则同原有的刷新行为一致
//					if (queryPK != null) {
//						exec = this.getQueryExecutor(this.queryPK,
//								this.mainQuery);
//					} else {
//						exec = this.getQueryExecutor(this.mainQueryPK,
//								this.mainQuery);
//					}
//				}
//
//				IRowSet rowSet = exec.executeQuery(start, length);
//				e.setRowSet(rowSet);
//				onGetRowSet(rowSet);
//			} catch (Exception ee) {
//				handUIException(ee);
//			}
//		}
//	}

	
	
	
	
	protected ITreeBase getTreeInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

//	public Object getData() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return AppraiseGuideLineFactory.getRemoteInstance();
	}

	public void setAssociatePropertyName(String propName) {
	
		
	}

	public void setFilterCU(String queryName, OrgUnitCollection bizOrgCollection) {
		// TODO Auto-generated method stub
		
	}

	public void setFilterCUID(String cuId) {
		// TODO Auto-generated method stub
		
	}

	public void setFilterInfo(FilterInfo defaultFilterInfo,
			FilterInfo quickFilterInfo) {
		// TODO Auto-generated method stub
		
		this.defaultFilterInfo = defaultFilterInfo;
		this.quickFilterInfo = quickFilterInfo;
		
	}

	public void setMultiSelect(boolean isMulti) {
		// TODO Auto-generated method stub
		
	}

	public void setQueryPK(IMetaDataPK pk) {
		// TODO Auto-generated method stub
		
	}

	public void setQuickFilterInfo(FilterInfo quickFilterInfo) {
		// TODO Auto-generated method stub
		
	}

	public void setTreeBaseBOSType(String type) {
		// TODO Auto-generated method stub
		
	}

}