/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.Date;
import java.util.HashMap;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import org.apache.log4j.Logger;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;

import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.market.MarketTypeCollection;
import com.kingdee.eas.fdc.market.MarketTypeFactory;
import com.kingdee.eas.fdc.market.MarketTypeInfo;
import com.kingdee.eas.fdc.market.MarketingCostFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;


/**
 * output class name
 */
public class MarketingCostRptListUI extends AbstractMarketingCostRptListUI
{
    /**
     * liangfx 
     * 营销费用统计表
	 */
	private static final long serialVersionUID = -3178762168758512656L;
	private static final Logger logger = CoreUIObject.getLogger(MarketingCostRptListUI.class);
	        private String allMarketIds = "nullnull";			//所包含所有的活动类型id
	        private String allProjectIds = "nullnull"; // 所包含所有的销售项目id
			private Timestamp bizDateBegin = null ;
			private Timestamp bizDateEnd = null ;
			private String sellproject = null ;
	 /**
		 * 创建活动类型树
		 * @throws Exception
		 */
		public static void getMarketTypeTree(KDTree treeMain) throws Exception
		{
			DefaultKingdeeTreeNode rootNode =createTreeNode(true);
			DefaultTreeModel model = new DefaultTreeModel(rootNode);
			treeMain.setModel(model);
			treeMain.expandAllNodes(false, rootNode);
			if (rootNode.getChildCount() > 0)
				treeMain.setSelectionNode((DefaultKingdeeTreeNode) rootNode.getChildAt(0));
			else
				treeMain.setSelectionNode(rootNode);
			treeMain.setShowsRootHandles(true);
			
		}

		
		public static DefaultKingdeeTreeNode createTreeNode(boolean includeEmployeeType) throws Exception
		{
			//根节点
			 DefaultKingdeeTreeNode rootTreeNode = new DefaultKingdeeTreeNode("活动类型"),subTreeNode = null,midTreeNode=null;
			 HashMap nodeMap = new HashMap();
			
			MarketTypeCollection marketUnitColl = MarketTypeFactory.getRemoteInstance().getMarketTypeCollection(" Order by Level");
			MarketTypeCollection leafUnits = new MarketTypeCollection();
			//将活动类型存入树的一级节点
			for(int i=0;i<marketUnitColl.size();i++)
			{
				MarketTypeInfo unit = marketUnitColl.get(i);
				if(unit.getParent() != null){
//					subTreeNode = new DefaultKingdeeTreeNode(marketUnitColl.get(i));
//					nodeMap.put(marketUnitColl.get(i).getId().toString(),subTreeNode);
					leafUnits.add(unit);
				}
				else{
					subTreeNode = new DefaultKingdeeTreeNode(marketUnitColl.get(i));
					nodeMap.put(marketUnitColl.get(i).getId().toString(),subTreeNode);
					rootTreeNode.add(subTreeNode);
				}
				
			}
			 
			 
			//将活动类型存入二级或者二级以下所有节点
	        for(int i=0;i<leafUnits.size();i++)
	        {
	        	MarketTypeInfo marketInfo = leafUnits.get(i);
	        	subTreeNode = new DefaultKingdeeTreeNode(marketInfo);
	        	
	        	//查找等级所属的活动类型，加入到其子节点
	        	if (!marketInfo.isIsLeaf()) {
	        		subTreeNode = new DefaultKingdeeTreeNode(leafUnits.get(i));
					nodeMap.put(leafUnits.get(i).getId().toString(),subTreeNode);
					
	        	}
	        	if(marketInfo.getId().toString() != null && nodeMap.containsKey(marketInfo.getParent().getId().toString()))
	        	{
	        		((DefaultKingdeeTreeNode) nodeMap.get(marketInfo.getParent().getId().toString())).add(subTreeNode);
	        	}
	        }       
	        
	        nodeMap = null;
	        return rootTreeNode;
		}


	    public void onLoad() throws Exception {
			// TODO Auto-generated method stub
	    	/**
	    	 * 
	    	 * 描述：初始化活动类型树
	    	 * @author:liangfx
	    	 */
	    	this.getMarketTypeTree(treeMain);
	    	/**
	    	 * 初始化项目树
	    	 * @author:liangfx
	    	 */
	    	this.treeSellproject.setModel(FDCTreeHelper.getSellProjectTree(actionOnLoad, null)); // SHEHelper		
			this.treeSellproject.expandAllNodes(true, (TreeNode) this.treeSellproject.getModel().getRoot());
//			this.MarketFeeTable.getColumn("sellProject.id").getStyleAttributes().setHided(true);

			super.onLoad();
		}
		
	    boolean isTreeAction = false;
	    

		/**
		 * 
		 * 描述：项目类型树节点变化事件
		 * @author:liangfx
		 */    
	protected void treeSellproject_valueChanged(TreeSelectionEvent e)
				throws Exception {
			// TODO Auto-generated method stub
    	int rowCount = MarketFeeTable.getBody().size();
    	for(int i=rowCount;i>=0;i--)
    	{
    		MarketFeeTable.removeRow(i);
    	}
    	   getQueryList();
			super.treeSellproject_valueChanged(e);
		}


	/**
	 * 
	 * 描述：活动类型树节点变化事件
	 * @author:liangfx
	 */
	
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
    	//清行
    	int rowCount = MarketFeeTable.getBody().size();
    	for(int i=rowCount;i>=0;i--)
    	{
    		MarketFeeTable.removeRow(i);
    	}
//    	MarketFeeTable.checkParsed(true);
    	isTreeAction = true;
    	getQueryList();
		super.treeMain_valueChanged(e);
		
	}
    
    /**
	 * 
	 * 描述：列表查询
	 * @author:liangfx
	 */
    protected void getQueryList() throws Exception {
    	TreeNode marketNode = (TreeNode) this.treeMain.getLastSelectedPathComponent();
		TreeNode projectNode = (TreeNode) this.treeSellproject.getLastSelectedPathComponent();
    	//参数
    	if(params!=null){
        	bizDateBegin = null ;
    		bizDateEnd = null ;
        	bizDateBegin = (Timestamp) params.getObject(MarketingCostUI.BIZDATEBEGIN);
        	bizDateEnd = (Timestamp)params.getObject(MarketingCostUI.BIZDATEEND); 

    	
    	    allMarketIds="nullnull";
    		if (marketNode != null)
    			getAllMarketIds(marketNode);
    		else
    			getAllMarketIds((TreeNode) this.treeMain.getModel().getRoot());
    		
       	    allProjectIds="nullnull";
       	 if (projectNode != null)
 			getAllProjectIds(projectNode);
 		else
 			getAllProjectIds((TreeNode) this.treeSellproject.getModel().getRoot());
       	 
       	 
    		FDCSQLBuilder strSql = new FDCSQLBuilder();

    			//			allMarketIds=null;
//    			quitBuilder.appendSql("SELECT a.FID    FID,b.FName_l2        MARKETTYPENAME,d.FName_l2 MediaName,         \r\n");
//    			quitBuilder.appendSql("a.FBizDate   BIZDATE,a.FContractNumber  CONTRACTNUMBER,a.FPlanTotalMoney   PLANTOTALMONEY,       \r\n");
//    			quitBuilder.appendSql("a.FFactTotalMoney FACTTOTALMONEY, a.FFactPayment    FACTPAYMENT,       \r\n");
//    			quitBuilder.appendSql("a.FFactTotalMoney-a.FFactPayment  nopayment      \r\n");
//    			quitBuilder.appendSql("FROM T_MAR_MarketManage  a      \r\n");
//    			quitBuilder.appendSql("LEFT OUTER JOIN T_MAR_MarketType  b ON a.FMarkettypeID =b.FID        \r\n");
//    			quitBuilder.appendSql("LEFT JOIN T_MAR_MarketManageMedia  c ON a.FID =c.FParentID      \r\n");
//    			quitBuilder.appendSql("LEFT JOIN T_MAR_Media  d ON c.FMediaTypeID = d.FID      \r\n");
//    			quitBuilder.appendSql("Where a.FBizDate between to_date('"+bizDateBegin+"') and to_date('"+bizDateEnd+"')      \r\n");
//    			quitBuilder.appendSql("and a.FSellProject in( '"+allProjectIds+"' )     \r\n");
//    			quitBuilder.appendSql(" and b.FID in ( '"+allMarketIds+"' )       \r\n");
//    			quitBuilder.appendSql("      \r\n");
    			
    			
    			
    			strSql.appendSql("SELECT market.FID FID,market.FNumber    FNUMBER , market.FName    FNAME , mp.FName    MOVEMENTPLAN ,  \r\n");
    			strSql.appendSql("market.FOrgName    ORGNAME , market.FMovemntTheme    MOVEMNTTHEME ,    \r\n");
    			strSql.appendSql("mt.FName_l2    MARKETTYPENAME , market.FContractNumber    CONTRACTNUMBER ,     \r\n");
    			strSql.appendSql("market.FPlanTotalMoney    PLANTOTALMONEY , market.FFactTotalMoney    FACTTOTALMONEY ,     \r\n");
    			strSql.appendSql("market.FFactPayment    FACTPAYMENT ,market.FFactTotalMoney -market.FFactPayment nopayment,    \r\n");
    			strSql.appendSql("market.FBeginDate    BEGINDATE ,market.FEndDate    ENDDATE     \r\n");
    			strSql.appendSql("    \r\n");
    			strSql.appendSql("FROM T_MAR_MarketManage   market     \r\n");
    			strSql.appendSql("LEFT OUTER JOIN T_ORG_Company   company ON market.FCompanyID = company.FID     \r\n");
    			strSql.appendSql("LEFT OUTER JOIN T_MAR_MarketType   mt ON market.FMarkettypeID = mt.FID    \r\n");
    			strSql.appendSql("INNER JOIN T_SHE_SellProject   spt ON market.FSellProject = spt.FID    \r\n");
    			strSql.appendSql("INNER JOIN T_MAR_MovementPlan   mp ON market.FMarketPlan = mp.FID    \r\n");
    			strSql.appendSql("Where market.FBizDate between to_date('"+bizDateBegin+"') and to_date('"+bizDateEnd+"')   \r\n");
    			strSql.appendSql("and market.FSellProject in( '"+allProjectIds+"' )     \r\n");
    			strSql.appendSql("and mt.FID in ( '"+allMarketIds+"' )       \r\n");
    			strSql.appendSql("      \r\n");
    			logger.info("输出sql>>>>>>>>>>>>>>:"+strSql.getSql());
    			
    			IRowSet quitSet = null;
    			try {
    				quitSet = strSql.executeQuery();
    			} catch (BOSException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    				MsgBox.showInfo("查询失败！");
    				SysUtil.abort();
    			}
    			// 参数赋值
    		try {
//    			MarketFeeTable.checkParsed(true);
    				while (quitSet.next()) {
    					IRow rows = this.MarketFeeTable.addRow();
    					String FID = quitSet.getString("FID");
    					String FNUMBER = quitSet.getString("FNUMBER");
    					String FNAME = quitSet.getString("FNAME");
    					String MOVEMENTPLAN = quitSet.getString("MOVEMENTPLAN");
    					String ORGNAME = quitSet.getString("ORGNAME");
    					String MOVEMNTTHEME = quitSet.getString("MOVEMNTTHEME");
    					String MARKETTYPENAME = quitSet.getString("MARKETTYPENAME");
    					String CONTRACTNUMBER = quitSet.getString("CONTRACTNUMBER");

    					BigDecimal PLANTOTALMONEY = quitSet.getBigDecimal("PLANTOTALMONEY");
    					BigDecimal FACTTOTALMONEY = quitSet.getBigDecimal("FACTTOTALMONEY");
    					BigDecimal FACTPAYMENT = quitSet.getBigDecimal("FACTPAYMENT");
    					BigDecimal nopayment = quitSet.getBigDecimal("nopayment");
    					
    					String BEGINDATE = quitSet.getString("BEGINDATE");
    					String ENDDATE = quitSet.getString("ENDDATE");

    					
    					
    					// 赋值
    					rows.getCell("id").setValue(FID);
    					rows.getCell("fNumber").setValue(FNUMBER);
    					rows.getCell("fName").setValue(FNAME);
    					rows.getCell("movementPlan").setValue(MOVEMENTPLAN);
    					rows.getCell("orgName").setValue(ORGNAME);
    					rows.getCell("moveMntTheme").setValue(MOVEMNTTHEME);
    					rows.getCell("marketTypeName").setValue(MARKETTYPENAME);
    					rows.getCell("contractNumber").setValue(CONTRACTNUMBER);
    					rows.getCell("planTotalMoney").setValue(PLANTOTALMONEY);
    					rows.getCell("fActTotalMoney").setValue(FACTTOTALMONEY);
    					rows.getCell("fActPayMent").setValue(FACTPAYMENT);
    					rows.getCell("nopayment").setValue(nopayment);
    					rows.getCell("beginDate").setValue(BEGINDATE);
    					rows.getCell("endDate").setValue(ENDDATE);
    				}
    			} catch (SQLException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
    	}
    	}
    /**
	 * 查询所有的销售项目id
	 * 
	 * @param treeNode
	 */
	private void getAllProjectIds(TreeNode treeNode) {
		if (treeNode.isLeaf()) {
			DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
			if (thisNode.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo project = (SellProjectInfo) thisNode.getUserObject();
				allProjectIds += "','" + project.getId().toString();
			}
		} else {
			int childCount = treeNode.getChildCount();
			while (childCount > 0) {
				getAllProjectIds(treeNode.getChildAt(childCount - 1));
				childCount--;
			}
		}
	}
    /**
	 * 查询节点下所有的活动类型id
	 * @param treeNode
	 */
	private void getAllMarketIds(TreeNode treeNode){
		if(treeNode.isLeaf()){
			DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)treeNode;
			if(thisNode.getUserObject() instanceof MarketTypeInfo){
				MarketTypeInfo market = (MarketTypeInfo)thisNode.getUserObject();
				 allMarketIds += "','" +market.getId().toString();
			}
		}else{
			int childCount = treeNode.getChildCount();
			while(childCount>0) {				
				 getAllMarketIds(treeNode.getChildAt(childCount-1));		
				 childCount --;
			}	
		}
	}
	/**
     * output class constructor
     */
    public MarketingCostRptListUI() throws Exception
    {
        super();
        
        this.MarketFeeTable.checkParsed();
		this.MarketFeeTable.getDataRequestManager().addDataRequestListener(this);
		this.MarketFeeTable.getDataRequestManager().setDataRequestMode(
		KDTDataRequestManager.VIRTUAL_MODE_PAGE);
		enableExportExcel(this.MarketFeeTable);
		KDTSortManager manager = new KDTSortManager(this.MarketFeeTable);
		manager.setSortAuto(true);
    }

	
	protected RptParams getParamsForInit() {
		// TODO Auto-generated method stub
		return params;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		MarketingCostUI marketingCostUI=new MarketingCostUI();
		return marketingCostUI;
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		// TODO Auto-generated method stub
		return MarketingCostFacadeFactory.getRemoteInstance();

	}

	protected KDTable getTableForPrintSetting() {
		// TODO Auto-generated method stub
		return MarketFeeTable;
	}

	protected void query() {
		// TODO Auto-generated method stub
		this.MarketFeeTable.removeRows();
	}

	public void tableDataRequest(KDTDataRequestEvent e) {

		try {
			int from = e.getFirstRow();
			int len = e.getLastRow() - from + 1;
			RptRowSet rs = null;
			RptParams rpt =null;
			{
				if (from == 0) {
					// 临时表
					params.setString("tempTable", this.getTempTable());

					 rpt = MarketingCostFacadeFactory
							.getRemoteInstance().createTempTable(params);

					this.setTempTable(rpt.getString("tempTable"));

					this.MarketFeeTable.setRowCount(rpt.getInt("verticalCount"));	

				}
				// 查询并填充数据
				params.setString("tempTable", this.getTempTable());
					 rpt = MarketingCostFacadeFactory.getRemoteInstance()
					.query(params, from, len);
					 
				rs = (RptRowSet) rpt.getObject("rowset");

//				KDTableUtil.insertRows(rs, from, MarketFeeTable);
			}
			if(rs!=null)
				KDTableUtil.insertRows(rs, from, MarketFeeTable);
			
		} catch (Exception ex) {
			this.handleException(ex);
		}
		
	}

}