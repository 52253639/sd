/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Container;
import java.awt.event.*;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.eas.cm.common.client.GeneralF7TreeListUI;
import com.kingdee.eas.cm.common.client.IF7Provider;
import com.kingdee.eas.fdc.invite.AppraiseGuideLineFactory;
import com.kingdee.eas.fdc.invite.AppraiseGuideLineInfo;
import com.kingdee.eas.fdc.invite.AppraiseGuideLineTypeInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.tree.KDTreeNode;

/**
 * output class name
 */
public class F7AppraiseGuideLineSelectUI extends AppraiseGuideLineListUI implements IF7Provider,TreeSelectionListener
{
private static final Logger logger = CoreUIObject.getLogger(F7AppraiseGuideLineSelectUI.class);
    
    
    private FilterInfo quickFilterInfo;

    
    private IMetaDataPK queryPK;

   
    private IMetaDataPK treeBasePK;

  
    private String treeBaseBOSType;

    
    private String propName;

    private FilterInfo defaultFilterInfo;
    
    private boolean isMultiSelect = false;
    
    private String filterCUId = null; 

    private String queryName;

    private OrgUnitCollection bizOrgCollection;
    
    private DefaultKingdeeTreeNode currentNode = null;
    
    
    
    public FilterInfo getDefaultFilterInfo(){
    	return this.defaultFilterInfo;
    }
    public F7AppraiseGuideLineSelectUI() throws Exception
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
     * 表格双击选取相应的值
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	if (isOrderForClickTableHead() && e.getClickCount() !=2)
        {
            super.tblMain_tableClicked(e);
        }
    	if (e.getClickCount() == 2) {
            Container c = getParent();
            while (c != null ) {
            	if(c.getClass().equals(GeneralF7TreeListUI.class))
            		break;
                c = c.getParent();
            }
            
            if(this.getData() != null)
               	((GeneralF7TreeListUI) c).clickOKBtn();
        }
     }

	public Object getData() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("guideLineType.id");
		sic.add("guideLineType.name");
		sic.add("guideLineType.number");
		if (this.isMultiSelect) {

           Object[] keyValues = getSelectedIdValues().toArray();

            if (keyValues != null) {
                
                if(keyValues.length <= 0) return null;
                
                try {

                    //获得TreeListUI对应的业务接口

                    ICoreBase iBiz = getBizInterface();

                    ObjectUuidPK pk = null;

                    AppraiseGuideLineInfo[] Info = new AppraiseGuideLineInfo[keyValues.length];

                    for (int i = 0, c = keyValues.length; i < c; i++) {

                        pk = new ObjectUuidPK(BOSUuid.read(keyValues[i]

                                .toString()));

                        Info[i] = (AppraiseGuideLineInfo) iBiz.getValue(pk, sic);

                    }

                return Info;

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

                    return iBiz.getValue(pk, sic);

                } catch (Exception er) {

                    super.handUIException(er);

                }

            }

        }

        return null;
	}
	
	 protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
	        if (this.mainQuery != null) {
	            int start = ((Integer) e.getParam1()).intValue();
	            int length = ((Integer) e.getParam2()).intValue() - start + 1;
	           //只显示已启用的指标类型
	            FilterInfo isEnableInfo = new FilterInfo();
	            isEnableInfo.getFilterItems().add(new FilterItemInfo("isEnable",Boolean.TRUE));
	            try {
					this.mainQuery.getFilter().mergeFilter(isEnableInfo, "and");
				} catch (BOSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            try {
	                // 这里可以取到QueryPK, 同时我们也要指定ViewInfo
	                IQueryExecutor exec;
	                if (defaultFilterInfo != null) {
	                    if (queryPK != null) {
	                    	/*
	                    	 * 屏蔽原来的调用，调用框架提供的方法，框架方法能够初始化单据列表
	                    	 * modify by:Nicklas  date:06.12.06
	                    	 * bug:BT156214
	                    	 */
	                    	super.mainQueryPK = this.queryPK;
	                    	super.mainQuery = getDefaultQuery();
	                    	
	                    	/*根据树的选择来刷新表(20091014兰远军)
	                    	 * */
	                    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) this.treeMain.getLastSelectedPathComponent();
	                    	if(node != null){
	                    		if(!(node.getUserObject() instanceof String)){
	                    			AppraiseGuideLineTypeInfo typeInfo = (AppraiseGuideLineTypeInfo) node.getUserObject();
		                    		FilterInfo info = new FilterInfo();
		                    		info.getFilterItems().add(new FilterItemInfo("guideLineType.longNumber",typeInfo.getLongNumber()+"!%",CompareType.LIKE));
		                    		super.mainQuery.getFilter().mergeFilter(info, "and");
	                    		}
	                    		
	                    		
	                    	}
	                    	super.tblMain_doRequestRowSetForHasQueryPK(e);
//	                        exec = this.getQueryExecutor(this.queryPK,
//	                                getDefaultQuery());
	                    } else {
	                    	super.mainQuery = getDefaultQuery();
	                    	super.tblMain_doRequestRowSetForHasQueryPK(e);
//	                        exec = this.getQueryExecutor(this.mainQueryPK,
//	                                getDefaultQuery());
	                    }
	                } else if(quickFilterInfo != null){
	                    if (queryPK != null) {
	                    	super.mainQueryPK = this.queryPK;
	                    	super.mainQuery = getDefaultQuery();
	                    	super.tblMain_doRequestRowSetForHasQueryPK(e);
//	                        exec = this.getQueryExecutor(this.queryPK,
//	                                getMainQuery());
	                    } else {
	                    	super.mainQuery = getDefaultQuery();
	                    	super.tblMain_doRequestRowSetForHasQueryPK(e);
//	                        exec = this.getQueryExecutor(this.mainQueryPK,
//	                                getMainQuery());
	                    }
	                }else{
	                
	                    // 如果没有快速过滤条件时, 则同原有的刷新行为一致
	                	//add by:Nicklas.Guan date: 06.11.10 处理树形F7的排序功能
	                	EntityViewInfo query = this.mainQuery;
	                	query.getSorter().addObjectCollection(this.mainQuery.getSorter());
	                	if (queryPK != null) {
	                		super.mainQueryPK = this.queryPK;
	                    	super.mainQuery = query;
	                    	super.tblMain_doRequestRowSetForHasQueryPK(e);
//	                		exec = this.getQueryExecutor(this.queryPK,
//	                				query);
	                	} else {
	                		super.mainQuery = query;
	                    	super.tblMain_doRequestRowSetForHasQueryPK(e);
//	                		exec = this.getQueryExecutor(this.mainQueryPK,
//	                				query);
	                	}
	                	// end by:Nicklas
//	                    if (queryPK != null) {
//	                        exec = this.getQueryExecutor(this.queryPK,
//	                                this.mainQuery);
//	                    } else {
//	                        exec = this.getQueryExecutor(this.mainQueryPK,
//	                                this.mainQuery);
//	                    }
	                }


//	                IRowSet rowSet = exec.executeQuery(start, length);
//	                e.setRowSet(rowSet);
//	                onGetRowSet(rowSet);
	            } catch (Exception ee) {
	                handUIException(ee);
	            }
	        }
	    }

	 
	
	 public EntityViewInfo getDefaultQuery(){
	    	EntityViewInfo evi= new EntityViewInfo();
	    	 FilterInfo tmpFilterInfo;
	         try {
	         	if( defaultFilterInfo != null )
	         	{
	         		tmpFilterInfo = (FilterInfo)defaultFilterInfo.clone();
	         	}
	         	else
	         	{
	         		tmpFilterInfo = new FilterInfo();
	         	}
	             if(quickFilterInfo != null){
	             	if( tmpFilterInfo.getFilterItems().size() != 0 )
	             		tmpFilterInfo.mergeFilter(quickFilterInfo,"and");
	             	else
	             		tmpFilterInfo = (FilterInfo)quickFilterInfo.clone();
	             }
	                    
	                                                 
	             evi.setFilter(tmpFilterInfo);
	             evi.getSorter().clear();
	             evi.getSorter().addObjectCollection(this.mainQuery.getSorter());
	         } catch (Exception e) {
	             super.handUIException(e);
	         }
	    	return evi ;
	    }
	    

	public void onLoad() throws Exception {
		setIsNeedDefaultFilter(false);//是否需要默认加载方案
		super.onLoad();
		this.setUITitle("评标指标");
		this.toolBar.removeAll();
		
		
		
		treeView.setShowControlPanel(false);
		treeMain.setVisibleRowCount(10);
		treeMain.addTreeSelectionListener(this);
		pnlMain.setDividerLocation(190);
        
		tblMain.setRefresh(true);
		tblMain.reLayoutAndPaint();
		addKDTableLisener();
		
		//回车键和取消键的监听
		  tblMain.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),"View_1");
	      tblMain.getActionMap().put("View_1", new AbstractAction()
	                {
	                    public void actionPerformed(ActionEvent e)
	                    {
	                    	Container c = getParent();
	                        while (c != null
	                                && !c.getClass().equals(GeneralF7TreeListUI.class)) {
	                            c = c.getParent();
	                        }
	                        try {
	                            ((GeneralF7TreeListUI) c).clickOKBtn();
	                           
	                            
	                        } catch (Exception e1) {
	                            e1.printStackTrace();
	                        }
	                   }
	                });
	        
	        tblMain.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "esc2");
	        tblMain.getActionMap().put("esc2", new AbstractAction()
	        {
	            public void actionPerformed(ActionEvent e)
	            { 
	            	
	            	Container c = getParent();
	                while (c != null
	                        && !c.getClass().equals(GeneralF7TreeListUI.class)) {
	                    c = c.getParent();
	                }
	                try {
	                    ((GeneralF7TreeListUI) c).clickOKBtn();
	                } catch (Exception e1) {
	                    e1.printStackTrace();
	                }
	                
	           }
	        });
	        
	        this.actionRefresh.setVisible(false);
		
	}
	
	/*
	 * 对回车键进行监听
	 * 
	 */
	private void addKDTableLisener() {
        tblMain.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
               
                if (e.getKeyChar() == '\n') {
                    Container c = getParent();
                    while (c != null
                            && !c.getClass().equals(GeneralF7TreeListUI.class)) {
                        c = c.getParent();
                    }
                    try {
                        ((GeneralF7TreeListUI) c).clickOKBtn();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                
              
            }
        });
    }

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return AppraiseGuideLineFactory.getRemoteInstance();
	}

	public void setAssociatePropertyName(String propName) {
		// TODO Auto-generated method stub
		this.propName = propName;
	}

	public void setFilterCU(String queryName, OrgUnitCollection bizOrgCollection) {
		// TODO Auto-generated method stub
		
	}

	public void setFilterCUID(String cuId) {
		
		
	}

	public void setFilterInfo(FilterInfo defaultFilterInfo,
			FilterInfo quickFilterInfo) {
//		this.defaultFilterInfo = defaultFilterInfo;
		this.quickFilterInfo = quickFilterInfo;
		
	}

	public void setMultiSelect(boolean isMulti) {
		this.isMultiSelect = isMulti;
		
	}

	public void setQueryPK(IMetaDataPK pk) {
		this.queryPK = pk;
		
	}

	public void setQuickFilterInfo(FilterInfo quickFilterInfo) {
		this.quickFilterInfo = quickFilterInfo;
		
	}

	public void setTreeBaseBOSType(String type) {
		
		this.treeBaseBOSType = type;
		
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
        tblMain.setScrollStateVertical(KDTStyleConstants.SCROLL_STATE_SHOW);
    	super.treeMain_valueChanged(e);
    	tblMain.setScrollStateVertical(KDTStyleConstants.SCROLL_STATE_AUTO);
        
        KDTreeNode treeNode = (KDTreeNode) treeMain.getLastSelectedPathComponent();
        if (treeNode!=null)
        {
            quickFilterInfo = null;
        }
        
        Container c = getParent();
        while (c != null && !c.getClass().equals(GeneralF7TreeListUI.class)) {
            c = c.getParent();
        }
        if(c instanceof GeneralF7TreeListUI){
        	((GeneralF7TreeListUI)c).setQuickQuery(false);
        }
     }
    
	public void valueChanged(TreeSelectionEvent arg0) {
		currentNode = (DefaultKingdeeTreeNode) arg0.getPath().getLastPathComponent();
	}

	protected ITreeBase getTreeInterface() throws Exception {
        if (treeBaseBOSType != null) {
            return (com.kingdee.eas.framework.ITreeBase) 
            

            BOSObjectFactory.createRemoteBOSObject(new BOSObjectType(treeBaseBOSType), 
                    com.kingdee.eas.framework.ITreeBase.class);
        } else {
            return super.getTreeInterface();
        }
    }
    
	protected IObjectPK getSelectedTreeKeyValue() {
        int propColumnNo = -1; // 关联属性所在的列
        //如果没有设置关联属性时，按照父类的方式返回关联属性PK
        if (propName != null) {
            //找出关联属性所在列的值
            for (int i = 0; i < tblMain.getColumnCount(); i++) {
                if (propName.equals(tblMain.getColumn(i).getFieldName())) {
                    propColumnNo = i;
                    break;
                }
            }
            if (propColumnNo != -1) {
                // 如果找到的话， 则返回指定列位置的值作为IObjectPK返回
                KDTSelectBlock sb = tblMain.getSelectManager().get();
                int top = sb.getTop();
                ICell cell = tblMain.getCell(top, propColumnNo);
                String id = (String) cell.getValue();
                
                if(id == null || id.equals(""))
                    return null;
                
                BOSUuid uuid = BOSUuid.read(id);
                return new ObjectUuidPK(uuid);
            }
            // 找不到的情况下，返回null
            return null;
        } else {
            return super.getSelectedTreeKeyValue();
        }
    }
    
    protected void chkIncludeChild_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        Container c = getParent();
        while (c != null && !c.getClass().equals(GeneralF7TreeListUI.class)) {
            c = c.getParent();
        }
        if(c instanceof GeneralF7TreeListUI){
        	((GeneralF7TreeListUI)c).setQuickQuery(false);
        }
        tblMain.setScrollStateVertical(KDTStyleConstants.SCROLL_STATE_SHOW);
        super.chkIncludeChild_itemStateChanged(e);
        tblMain.setScrollStateVertical(KDTStyleConstants.SCROLL_STATE_AUTO);

    }
   
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output treeMain_valueChanged method
     */
   

  
    
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

    /**
     * output actionGroupAddNew_actionPerformed
     */
    public void actionGroupAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionGroupAddNew_actionPerformed(e);
    }

    /**
     * output actionGroupView_actionPerformed
     */
    public void actionGroupView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionGroupView_actionPerformed(e);
    }

    /**
     * output actionGroupEdit_actionPerformed
     */
    public void actionGroupEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionGroupEdit_actionPerformed(e);
    }

    /**
     * output actionGroupRemove_actionPerformed
     */
    public void actionGroupRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionGroupRemove_actionPerformed(e);
    }

    /**
     * output actionGroupMoveTree_actionPerformed
     */
    public void actionGroupMoveTree_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionGroupMoveTree_actionPerformed(e);
    }

    /**
     * output actionMoveTree_actionPerformed
     */
    public void actionMoveTree_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMoveTree_actionPerformed(e);
    }

}