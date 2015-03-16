/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.RevListInfo;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.AgingScheduleFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.FunctionSetting;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractCollection;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fm.common.DateHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class AgingScheduleUI extends AbstractAgingScheduleUI
{
    private static final BigDecimal ZERO = new BigDecimal("0.00");

	private static final Logger logger = CoreUIObject.getLogger(AgingScheduleUI.class);
    
    private AgingScheduleFilterUI filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	
	private Map queryMap=new HashMap();
	
    public AgingScheduleUI() throws Exception
    {
        super();
        this.mainQuery = null;
    }

	protected ICoreBase getBizInterface() throws Exception
	{
		return PurchaseFactory.getRemoteInstance();
	}
	
	 protected boolean initDefaultFilter()
	{
		return true;
	}

	protected String getEditUIName()
	{
		//return 
		return PurchaseEditUI.class.getName();
	}

	List filterList =new ArrayList();
	boolean fistI = false;
	protected void execQuery() {
		//super.execQuery();
		Map data = null;
		
		
		try {
			// 获取报表的过滤参数表
			Map para = this.getRptParamMap();
			
			this.fillTreePara(para);
			// 获取报表的相关数据
			 data = AgingScheduleFacadeFactory.getRemoteInstance().getAllRptData(para);
	

		} catch (Exception e) {
			super.handUIException(e);
		}
		
		initTableMain(data);
	}
	String hideColl="orgUnit.name";
	
	public void handUIException(Throwable exc) {
		if(exc instanceof BOSException&&exc.getMessage().startsWith("Can't found propertyUnit:")){
			logger.error(exc);
		}
		else
			super.handUIException(exc);
	}

	protected Map getRptParamMap() throws Exception {
		/**
		 * paramMap 描述： IncludeAttachment = 包括附属房产，为真是包括，为假时不包括 BuildArea =
		 * 表示面积类型，为真是建筑面积，为假时为套内面积 PreArea = 表示面积类型，为真是预测面积，为假时为实测面积
		 * IncludeOrder = 包括预订业务， 为真是包括，为假时不包括
		 */
		Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
		this.queryMap=filterMap;
		
		Map paramMap = new HashMap();
		paramMap.put("IncludeAttachment", Boolean.valueOf(isIncludeAttach()));
		paramMap.put("purchaseDateFrom", getFromDate());
		paramMap.put("purchaseDateTo", getToDate());
		

		return paramMap;
	}
	
	private boolean isIncludeAttach() {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI()
				.getCustomerParams());
		boolean isIncludeAttach=false;
		if(this.queryMap.containsKey("includeAttach")){
			int includeAttach=((Integer)this.queryMap.get("includeAttach")).intValue();
			if(includeAttach==0){
				isIncludeAttach=false;
			}else{
				isIncludeAttach=true;
			}
			if(this.queryMap.get("includeAttach")!=null){
				return isIncludeAttach;
			}
		}
		return this.getFilterUI().isIncludeAttach(para);
	}
	
	private Date getFromDate() {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI()
				.getCustomerParams());
		if(this.queryMap.containsKey("agingBeginDate")){
			return (Date)queryMap.get("agingBeginDate");
		}
		return this.getFilterUI().getBeginQueryDate(para);
	}
	private Date getToDate() {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI()
				.getCustomerParams());
		if(this.queryMap.containsKey("purchaseDateTo")){
			return (Date)queryMap.get("purchaseDateTo");
		}
		return this.getFilterUI().getEndQueryDate(para);
	}
	
	public  void fillTreePara(Map para){
		KDTreeNode node = null;
		TreeNode treenode = null;
		DefaultKingdeeTreeNode orgNode=null;
		try{
			node =(KDTreeNode) kDTree1.getLastSelectedPathComponent();
		}catch (Exception e) {
			orgNode=(DefaultKingdeeTreeNode) kDTree1.getLastSelectedPathComponent();
		}
		
		if(node==null  && orgNode==null){
			try{
				treenode = (TreeNode)kDTree1.getModel().getRoot();
			}catch(Exception e){
			}
		}
		
		hideColl="orgUnit.name";
		
		if (node != null) {   //根据选中的树的节点过滤
			if (node.getUserObject() instanceof Integer) {  //单元  -- 已作废
			}else if (node.getUserObject() instanceof BuildingUnitInfo) {  //单元
				BuildingUnitInfo buildUnit = (BuildingUnitInfo) node.getUserObject();
				BuildingInfo building = (BuildingInfo) ((KDTreeNode) node.getParent()).getUserObject();
				para.put("building.name",building.getName());
				para.put("buildUnit.name",buildUnit.getName());

			} else if (node.getUserObject() instanceof BuildingInfo) {  //楼栋
				BuildingInfo building = (BuildingInfo) node.getUserObject();
				para.put("building.name",building.getName());

			} else if (node.getUserObject() instanceof SubareaInfo) { // 分区
				SubareaInfo subarea = (SubareaInfo) node.getUserObject();
				para.put("building.subarea.id",subarea.getId().toString());

			} else if (node.getUserObject() instanceof SellProjectInfo) { // 销售项目
				SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
				//Set set = new HashSet();
				//set.add(sellProject.getId().toString());
				para.put("sellProject.id","'"+sellProject.getId().toString()+"'");

			}else{  //取所选节点下的所有项目过滤
				//StringBuffer sellProFilter = new StringBuffer();
				//getAllProjectIds(node,sellProFilter);
				//if(!sellProFilter.toString().equals("")){
				//		sellProFilter.toString().replaceFirst("or", "");
						//para.put("sellProject.id",sellProFilter);
			//		}
			}
		}else{
			if(orgNode!=null){
				if (orgNode.getUserObject() instanceof Integer) {  //单元  -- 已作废
				}else if (orgNode.getUserObject() instanceof BuildingUnitInfo) {  //单元
					BuildingUnitInfo buildUnit = (BuildingUnitInfo) orgNode.getUserObject();
					BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) orgNode.getParent()).getUserObject();
					para.put("building.name",building.getName());
					para.put("buildUnit.name",buildUnit.getName());
					
				} else if (orgNode.getUserObject() instanceof BuildingInfo) {  //楼栋
					BuildingInfo building = (BuildingInfo) orgNode.getUserObject();
					para.put("building.name",building.getName());
					
					
				} else if (orgNode.getUserObject() instanceof SubareaInfo) { // 分区
					SubareaInfo subarea = (SubareaInfo) orgNode.getUserObject();
					para.put("building.subarea.id",subarea.getId().toString());
					
					
				} else if (orgNode.getUserObject() instanceof SellProjectInfo) { // 销售项目
					SellProjectInfo sellProject = (SellProjectInfo) orgNode.getUserObject();
					//Set set = new HashSet();
					///set.add(sellProject.getId().toString());
					para.put("sellProject.id","'"+sellProject.getId().toString()+"'");
					//para.put("sellProject.id",set);
					
				}else if(orgNode.getUserObject() instanceof  OrgStructureInfo ){
					//OrgStructureInfo orgInfo=  (OrgStructureInfo) orgNode.getUserObject();
					//Set set = new HashSet();
					String sellprj = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(orgNode, "SellProject").keySet());
					//String orgset = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(orgNode, "OrgStructure").keySet());
					//this.getAllProjectIds(orgNode, set);
					if(sellprj!=null && sellprj.length()>0){
						para.put("sellProject.id",sellprj);
					}
					/*if(orgset!=null && orgset.length()>0){
						para.put("orgUnit.id",orgset);
					}*/
				}
			}
		}
		if(node==null  && orgNode==null && treenode!=null){
		//	Set set = new HashSet();
			String sellprj=null;
			//String orgset=null;
			sellprj = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treenode, "SellProject").keySet());
			//orgset = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treenode, "OrgStructure").keySet());
			//this.getAllProjectIds(treenode, set);
			if(sellprj!=null && sellprj.length()<1){
				try {
					boolean isbizunit = SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit();
						this.kDTree1.setModel(SHEHelper.getUnitTree(this.actionOnLoad,
								MoneySysTypeEnum.SalehouseSys,isbizunit));
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					this.kDTree1.expandAllNodes(true, (TreeNode) this.kDTree1.getModel()
							.getRoot());
					treenode = 	(TreeNode) this.kDTree1.getModel().getRoot();
					
					 sellprj = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treenode, "SellProject").keySet());
					 //orgset = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treenode, "OrgStructure").keySet());
					
					//this.getAllProjectIds(treenode, set);
			}
			if(sellprj!=null && sellprj.length()>0){
				para.put("sellProject.id",sellprj);
			}
			/*if(orgset!=null && orgset.length()>0){
				para.put("orgUnit.id",orgset);
			}*/
			
		}
	}

	
	
	protected void beforeExcutQuery(EntityViewInfo ev)
	{
		super.beforeExcutQuery(ev);
	}

	
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	/**
	 * 查询所有的销售项目id,组合成sql
	 */
	private void getAllProjectIds(TreeNode treeNode,Set sellproject) {
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)treeNode;
		if(thisNode.getUserObject() instanceof SellProjectInfo){
			 SellProjectInfo project = (SellProjectInfo)thisNode.getUserObject();
			 //filterSql.append("or SELLPROJECT.FID='" + project.getId().toString()+"' ");
			 sellproject.add(project.getId().toString());
		}

		int childCount = treeNode.getChildCount();
		while(childCount>0) {				
			getAllProjectIds(treeNode.getChildAt(childCount-1),sellproject);		
			 childCount --;
		}		
	}
	
	protected void kDTree1_valueChanged(TreeSelectionEvent e) throws Exception
	{
		execQuery();
	}

	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(450);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}
	
	private AgingScheduleFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new AgingScheduleFilterUI(this, this.actionOnLoad);
				this.filterUI.onLoad();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}
	
	public void onLoad() throws Exception
	{
//		initCommonQueryDialog();
//		commonQueryDialog.show();
		super.onLoad();
		
		boolean isbizunit = SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit();
		
		//this.kDTree1.setModel(SHEHelper.getUnitTree(this.actionOnLoad,
				//MoneySysTypeEnum.SalehouseSys,isbizunit));
		//this.kDTree1.expandAllNodes(true, (TreeNode) this.kDTree1.getModel()
		//		.getRoot());

		
		actionAddNew.setVisible(false);
		actionEdit.setVisible(false);
		actionRemove.setVisible(false);
		actionView.setVisible(false);
		actionLocate.setVisible(false);
		this.menuEdit.setVisible(false);
		fistI=true;

		
		//tblMain.removeRows(false);
	}
	
	public void mergeBlockCol(){
		tblMain.getHeadMergeManager().setMergeMode(KDTMergeManager.FREE_ROW_MERGE);
		// 表头指定融合
		// 获取表头融合管理器
		KDTMergeManager mm = tblMain.getHeadMergeManager();
		// 进行指定融合
		for(int i = 0 ; i <29 ; i++){
			mm.mergeBlock(0, i, 3, i, KDTMergeManager.SPECIFY_MERGE);
		}
		mm.mergeBlock(0,29, 0, tblMain.getColumnCount()-1,KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(1,29, 1, tblMain.getColumnCount()-1,KDTMergeManager.SPECIFY_MERGE);
	}
	
	protected void setSortForQuery(SorterItemInfo sortItem,
			SorterItemInfo oldSortItem) throws Exception
	{
	}

	
	public void initTableMain(Map data){

		Set set = (Set)data.get("tblMain");
		PurchaseCollection puc = (PurchaseCollection)data.get("pur");
		FDCReceivingBillCollection  frc =(FDCReceivingBillCollection)data.get("frc");
		RoomSignContractCollection  roomSign =(RoomSignContractCollection)data.get("roomcoll");
		
		/*if(rowSet!=null){
			try {
				IRow  row=null;
				rowSet.first();
				while(rowSet.next())
				{
					row= this.tblMain.addRow();
					String ID = rowSet.getString("ID");
					row.getCell("id").setValue(ID);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}*/
		if(set!=null && set.size()>0){
			IRow  row=null;
			Iterator it= set.iterator();
			while(it.hasNext())
			{
				row= this.tblMain.addRow();
				String ID = (String)it.next();
				row.getCell("id").setValue(ID);
			}
		}
		
		tblMain.removeRows(false);
		updateTblMain(puc,frc,roomSign);
	}
	public void updateTblMain(PurchaseCollection puc,FDCReceivingBillCollection  frc,RoomSignContractCollection  roomSign){
		//tblMain.getColumn("conFundItem").getStyleAttributes().setHided(true);
		
		//表头时间区间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String datesp = "查询区间数据("+sdf.format(getFromDate())+" - "+sdf.format(getToDate())+")";
		
		if(puc==null){
			puc= new PurchaseCollection();
		}
		if(puc!=null){
			//取消融合
			KDTMergeManager mm2 = tblMain.getHeadMergeManager();
			mm2.removeAllMergeBlock();
			//删除动态列
			for(int i =(tblMain.getColumnCount()-1);i>28;i--){
				tblMain.removeColumn(i);
			}
			List list=new ArrayList();
			if(getFilterUI().isUseSpecialChk){
				IColumn ic=null;
				IRow headRow =null;
				//删除表头
				if(tblMain.getHeadRow(2)!=null){
					tblMain.removeHeadRow(2);
				}
				if(tblMain.getHeadRow(1)!=null){
					tblMain.removeHeadRow(1);
				}
				IRow headRow1 =tblMain.addHeadRow();
				IRow headRow2 =tblMain.addHeadRow();
				IRow headRow3 =tblMain.addHeadRow();
				for(int i = 0 ; i <3;i++){
					Map map=new HashMap();
					if(i==2){
						map.put("caption","71天及以上");
						map.put("fate", "1000");
					}else if(i==1){
						map.put("caption","31-70");
						map.put("fate", "40");
					}else if(i==0){
						map.put("caption","1-30");
						map.put("fate", "30");
					}
					for(int j = 0; j<5;j++){
						ic=	tblMain.addColumn(29+i*5+j);
						ic.setKey((i*5+1+j)+"");
						ic.getStyleAttributes().setNumberFormat("#,##0.00");
						ic.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
						map.put((i*5+1+j)+"", ZERO);
						map.put((i*5+1+j)+"color", "3");
					}
					list.add(map);
					headRow = tblMain.getHeadRow(0);
					headRow.getCell(29).setValue(datesp);
					
					headRow1 = tblMain.getHeadRow(1);
					headRow1.getCell(29).setValue("合同逾期款项");
					if(i==2){
						headRow2.getCell(29+i*5).setValue("71天及以上");
					}else if(i==1){
						headRow2.getCell(29+i*5).setValue("31-70");
					}else if(i==0){
						headRow2.getCell(29+i*5).setValue("1-30");
					}
					headRow3.getCell(29+i*5+0).setValue("合同总逾期");
					headRow3.getCell(29+i*5+1).setValue("非合同总逾期");
					headRow3.getCell(29+i*5+2).setValue("合同非按揭类逾期");
					//headRow2.getCell(29+i*5+3).setValue("非合同非按揭类逾期");
					//headRow2.getCell(29+i*5+4).setValue("合同按揭类逾期");
					headRow3.getCell(29+i*5+3).setValue("合同按揭类逾期");
					headRow3.getCell(29+i*5+4).setValue("合同公积金逾期");
					// 表头指定融合
					// 获取表头融合管理器
					KDTMergeManager mm = tblMain.getHeadMergeManager();
					// 进行指定融合
					mm.mergeBlock(2, 29+i*5, 2, tblMain.getColumnCount()-1, KDTMergeManager.SPECIFY_MERGE);
				}
			}else{
				int rowCount=getFilterUI().kDTable1.getRowCount();
				IRow CountRow=null;
				IColumn ic=null;
				IRow headRow =null;
				//删除表头
				//
				if(tblMain.getHeadRow(3)!=null){
					tblMain.removeHeadRow(3);
				}
				
				if(tblMain.getHeadRow(2)!=null){
					tblMain.removeHeadRow(2);
				}
				if(tblMain.getHeadRow(1)!=null){
					tblMain.removeHeadRow(1);
				}
				IRow headRow1 =tblMain.addHeadRow();
				IRow headRow2 =tblMain.addHeadRow();
				IRow headRow3 =tblMain.addHeadRow();
				int lastCaption = 0;
				
				for(int i = 0 ; i <=rowCount;i++){
					Map map=new HashMap();
					CountRow=getFilterUI().kDTable1.getRow(i);
					if(i==rowCount){
						map.put("caption", (lastCaption+1)+"天及以上");
						map.put("fate", "1000");
					}else{
						map.put("caption", CountRow.getCell("caption").getValue());
						map.put("fate", CountRow.getCell("fate").getValue());
						lastCaption +=Integer.parseInt(CountRow.getCell("fate").getValue().toString());
					}
					for(int j = 0; j<5;j++){
						ic=	tblMain.addColumn(29+i*5+j);
						ic.setKey((i*5+1+j)+"");
						ic.getStyleAttributes().setNumberFormat("#,##0.00");
						ic.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
						map.put((i*5+1+j)+"", ZERO);
						map.put((i*5+1+j)+"color", "3");
					}
					list.add(map);
					headRow = tblMain.getHeadRow(0);
					headRow.getCell(29).setValue(datesp);
					headRow1 = tblMain.getHeadRow(1);
					headRow1.getCell(29).setValue("合同逾期款项");
					if(i==rowCount){
						headRow2.getCell(29+i*5).setValue("逾期"+(lastCaption+1)+"天及以上");
					}else{
						headRow2.getCell(29+i*5).setValue(CountRow.getCell("caption").getValue().toString());
					}
					headRow3.getCell(29+i*5+0).setValue("合同总逾期");
					headRow3.getCell(29+i*5+1).setValue("非合同总逾期");
					headRow3.getCell(29+i*5+2).setValue("合同非按揭类逾期");
					//headRow2.getCell(29+i*5+3).setValue("非合同非按揭类逾期");
					//headRow2.getCell(29+i*5+4).setValue("合同按揭类逾期");
					headRow3.getCell(29+i*5+3).setValue("合同按揭类逾期");
					headRow3.getCell(29+i*5+4).setValue("合同公积金逾期");
					// 表头指定融合
					// 获取表头融合管理器
					KDTMergeManager mm = tblMain.getHeadMergeManager();
					// 进行指定融合
					mm.mergeBlock(2, 29+i*5, 2, tblMain.getColumnCount()-1, KDTMergeManager.SPECIFY_MERGE);
				}
				
				
			}
			
			//设置融合
			mergeBlockCol();
			
			int count=puc.size();
			Set set=new HashSet();
			for(int i = 0 ; i<count;i++){
				PurchaseInfo puInfo=puc.get(i);
				set.add(puInfo.getId());
			}
			//ReceivingBillCollection frc=null;
			//frc=getFDCRBC(set);
			
			/////
			Date start = this.getFilterUI().getBeginQueryDate(new FDCCustomerParams(getFilterUI().getCustomerParams()));
			Date end   = this.getFilterUI().getEndQueryDate(new FDCCustomerParams(getFilterUI().getCustomerParams()));
			
			
			////
			
			
			//合同总价（合计）
			BigDecimal contractTotalAmountSum=ZERO;
			//合同回款（合计）
			BigDecimal conBackFundSum =ZERO;
			//合同按时付款（合计）
			BigDecimal conPaymentSum =ZERO;
			//合同逾期付款（合计）
			BigDecimal conOverduePaymentSum =ZERO;
			//合同欠款总额（合计）
			BigDecimal conArrearageAmountSum =ZERO;
			//合同未逾期欠款（合计）
			BigDecimal conArrearageSum =ZERO;
			
			
			//合同逾期款项（合计）
			BigDecimal conFundItemSum =ZERO;
			
			
			//按揭类逾期款项（合计）
			BigDecimal conAJFundItemSum =ZERO;
			//合同最长逾期天数（合计）
			int conFateSum =0;
			
			//非合同总价（合计）
			BigDecimal unContractTotalAmountSum=ZERO;
			//非合同回款（合计）
			BigDecimal unConBackFundSum =ZERO;
			//非合同按时付款（合计）
			BigDecimal unConPaymentSum =ZERO;
			//非合同逾期付款（合计）
			BigDecimal unConOverduePaymentSum =ZERO;
			//非合同欠款总额（合计）
			BigDecimal unConArrearageAmountSum =ZERO;
			//非合同未逾期欠款（合计）
			BigDecimal unConArrearageSum =ZERO;
			//非合同逾期付清（合计）
			BigDecimal UNOVERDUEPAYOFFSUM =ZERO;
			//非合同最长逾期天数（合计）
			int unConFateSum =0;
			Map sumMapSum=new HashMap();
			
			//取定金是否隶属于房款
			//RoomDisplaySetting setting = new RoomDisplaySetting();
			
			boolean isbizunit = SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit();
			
			for(int i = 0 ; i<count;i++){
				Map sumMap=new HashMap();
				PurchaseInfo puInfo=puc.get(i);
				//id
				String id=puInfo.getId().toString();
				//组织
				String orgUnitName=puInfo.getOrgUnit().getName();
				
				//实体组织显示为当前销售组织
				if(isbizunit){
					orgUnitName =  SysContext.getSysContext().getCurrentSaleUnit().getName();
				}
				
				//项目
				String sellProjectName=puInfo.getSellProject().getName();
				
				String sellProId=puInfo.getSellProject().getId().toString();
				//楼栋
				String buildingName="";
				//分区
				String subareaName="";
				if(puInfo.getRoom()==null){
					System.out.print("");
				}
				if(puInfo.getRoom().getBuilding()!=null){
					buildingName=puInfo.getRoom().getBuilding().getName();
					if(puInfo.getRoom().getBuilding().getSubarea()!=null){
						subareaName = puInfo.getRoom().getBuilding().getSubarea().getName();
					}
				}
				//单元
				String buildUnitName="";
				if(puInfo.getRoom().getBuildUnit()!=null){
					buildUnitName=puInfo.getRoom().getBuildUnit().getName();
				}
				//房间
				String roomName=puInfo.getRoom().getDisplayName();
				//认购单号
				String PurchaseNumber=puInfo.getNumber();
				//产品类型
				String productTypeName="";
				if(puInfo.getRoom().getProductType()!=null){
					productTypeName=puInfo.getRoom().getProductType().getName();
				}
				//户型
				String roomModelName="";
				if(puInfo.getRoom().getRoomModel()!=null){
					roomModelName=puInfo.getRoom().getRoomModel().getName();
				}
				//合同总价
				BigDecimal contractTotalAmount=puInfo.getContractTotalAmount();
				//合同回款
				BigDecimal conBackFund =ZERO;
				//合同按时付款
				BigDecimal conPayment =ZERO;
				//合同逾期付款
				BigDecimal conOverduePayment =ZERO;
				//合同未逾期欠款
				BigDecimal conArrearage =ZERO;
				
				//合同逾期金额（款项）
				BigDecimal conFundItem =ZERO;
				
				//合同欠款总额
				BigDecimal conArrearageAmount =ZERO;
				
				
				
				List listUNAJOVERDUEARR=new ArrayList();//合同非按揭逾期欠款
				List listAJOVERDUEARR=new ArrayList();//合同按揭逾期欠款
				
				List listACCFUNDARR=new ArrayList();//合同公积金逾期
				for(int ii = 0 ;ii<list.size();ii++){
					listAJOVERDUEARR.add(ZERO);
					listUNAJOVERDUEARR.add(ZERO);
					
					listACCFUNDARR.add(ZERO);
				}
				//按揭类逾期款项(未完)
				BigDecimal conAJFundItem =ZERO;
				//合同最长逾期天数
				int conFate =0;
				
				
				//房间状态
				//RoomSellStateEnum roomStatus=puInfo.getRoom().getSellState();
				
				///统计房间状态为 认购 或 签约的
				//if(roomStatus.equals(RoomSellStateEnum.Sign) || roomStatus.equals(RoomSellStateEnum.Purchase)){
					
				//	
				//FunctionSetting proSet = (FunctionSetting)setting.getFunctionSetMap().get(sellProId);
				boolean isHouseMoney  = puInfo.isIsEarnestInHouseAmount();
				if(puInfo.getPayListEntry()!=null){
					for(int pay = 0 ;pay<puInfo.getPayListEntry().size();pay++){
						//获取付款明细实体
						PurchasePayListEntryInfo pue=puInfo.getPayListEntry().get(pay);
						
						//取定金是否隶属于房款
						///boolean isHouseMoney  = (proSet==null?true:proSet.getIsHouseMoney().booleanValue());
						
						//定金不隶属于房款 跳过
						if(!isHouseMoney && MoneyTypeEnum.EarnestMoney.equals(pue.getMoneyDefine().getMoneyType())){
							continue;
						}
						
						
						//预订转认购 是 可能会 日期字段为空 , 将预订日期付给 “日期”  //TODO 可能有情况没考虑到
						if(pue.getAppDate()==null && puInfo.getPrePurchaseDate()!=null){
							pue.setAppDate(puInfo.getPrePurchaseDate());
						}
						//分录“日期” , “实收日期”不在 过滤日期范围内 不统计
						if(!compareDate(start,end,pue.getAppDate())){
							continue;
						}
						//预订金不统计
						if(MoneyTypeEnum.PreconcertMoney.equals(pue.getMoneyDefine().getMoneyType())){
							continue;
						}
						
						//实收日期 为空 赋 过滤结束日期
						if(pue.getActRevDate()==null){
							//pue.setActRevDate(getToDate());
						}/*else if(!compareDate(start,end,pue.getActRevDate())){
							continue;
						}*/
						
						
						BigDecimal actPayAmount = getNewActPayAmount(pue);
						
						if(actPayAmount!=null && actPayAmount.compareTo(ZERO)>=0){
							
							//如果实付金额不等于空，累加到 合同回款
							conBackFund=conBackFund.add(actPayAmount);
							if(pue.getAppDate()!=null && pue.getActRevDate()!=null){
								//if(pue.getAppDate().compareTo(pue.getActRevDate())>=0){
								if(compareDate(pue.getAppDate(),pue.getActRevDate())>=0){
									//如果应付日期 大于/等于 实付日期，累加到 合同按时付款
									conPayment=conPayment.add(actPayAmount);
									//如果应付金额 大于/等于 实付金额 ，并且 应付日期大于/等于 当前日期，累加到 合同未逾期欠款
									if(pue.getApAmount().compareTo(actPayAmount)>=0){
										if(pue.getAppDate().compareTo(DateHelper.getDayBegin())>=0){
											conArrearage=conArrearage.add(pue.getApAmount().subtract(actPayAmount));
										}
									}
								}
								
								
								//if((pue.getAppDate().compareTo(pue.getActRevDate())<0)||
								if(compareDate(pue.getAppDate(),pue.getActRevDate())<0 
										//|| ((pue.getAppDate().compareTo(DateHelper.getDayEnd())<0) &&(pue.getApAmount().compareTo(actPayAmount)>0))
										|| ( (compareDate(pue.getAppDate(), DateHelper.getDayEnd())<0) &&(pue.getApAmount().compareTo(actPayAmount)>0))
										){
									//如果款项类型为按揭，累加到按揭逾期欠款
									//否则，累加到非按揭逾期欠款
									int oldFate=0;
									int oldLastFate=0;
									for(int m = 0;m<list.size();m++){
										Map newMap=new HashMap();
										newMap=(Map) list.get(m);
										oldFate+=Integer.parseInt(newMap.get("fate").toString());
										int olDdate=getDiffDays(pue.getAppDate(),new Date());
										
										BigDecimal ajoverduearr = (BigDecimal)listAJOVERDUEARR.get(m);
										BigDecimal unajoverduearr = (BigDecimal)listUNAJOVERDUEARR.get(m);
										
										BigDecimal accfundarr = (BigDecimal)listACCFUNDARR.get(m);
										
										if(m==0){
											int newfate=Integer.parseInt(newMap.get("fate").toString());
											if(olDdate<=newfate){
												if(pue.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.LoanAmount) || pue.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.AccFundAmount) ){
													listAJOVERDUEARR.set(m, ajoverduearr.add(pue.getApAmount().subtract(actPayAmount)));
													//合同公积金逾期
													if(pue.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
														listACCFUNDARR.set(m, accfundarr.add(pue.getApAmount().subtract(actPayAmount)));
													}
												}else{
													listUNAJOVERDUEARR.set(m, unajoverduearr.add(pue.getApAmount().subtract(actPayAmount)));
												}
											}
										}else{
											if(olDdate<=oldFate && olDdate>oldLastFate){
												if(pue.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.LoanAmount) || pue.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
													listAJOVERDUEARR.set(m, ajoverduearr.add(pue.getApAmount().subtract(actPayAmount)));
													//合同公积金逾期
													if(pue.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
														listACCFUNDARR.set(m, ajoverduearr.add(pue.getApAmount().subtract(actPayAmount)));
													}
												}else{
													listUNAJOVERDUEARR.set(m, unajoverduearr.add(pue.getApAmount().subtract(actPayAmount)));
												}
											}
										}
										oldLastFate+=Integer.parseInt(newMap.get("fate").toString());
									}
									
									if(pue.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.LoanAmount) || pue.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
										conAJFundItem=conAJFundItem.add(pue.getApAmount().subtract(actPayAmount));
									}
									/*
									//合同逾期款项  不包含“预定金”的“金额”-“实收款”
									if(!MoneyTypeEnum.PreconcertMoney.equals(pue.getMoneyDefine().getMoneyType())){
										conFundItem=conFundItem.add(pue.getApAmount().subtract(actPayAmount));
									}*/
									conFundItem=conFundItem.add(pue.getApAmount().subtract(actPayAmount));
									
									//如果应付日期小于实付日期，累加到 合同逾期付款
									if(compareDate(pue.getAppDate(),pue.getActRevDate())<0){
										conOverduePayment=conOverduePayment.add(actPayAmount);
									}
									
									//处理动态列 合同总逾期天数
									int newConFate=conFate;
		
									//已收完款  实收日期-应收日期
									if(pue.getApAmount().compareTo(actPayAmount)<=0){
										conFate =getDiffDays(pue.getAppDate(),pue.getActPayDate());
									}else{//未收完 或者 未收到  过滤 结束日期-应收日期
										conFate = getDiffDays(pue.getAppDate(),this.getToDate());
									}
									if(newConFate>conFate){
										conFate=newConFate;
									}
								}
							}
						}else{
							//如果实付金额等于空，并且应付日期 大于/等于 当前日期（或者 实收日期为空 且过滤截止日期等于应收日期），累加到合同未逾期欠款
							//否则，累加到逾期欠款
							
							if(actPayAmount==null){
								actPayAmount= ZERO;
							}
							
							
							if((pue.getAppDate()!=null && compareDate(pue.getAppDate(), DateHelper.getDayEnd())>=0)
							|| (pue.getAppDate()!=null && pue.getActPayDate()==null && compareDate(pue.getAppDate(),this.getToDate())==0)
							){
								conArrearage=conArrearage.add(pue.getApAmount());
							}else if(pue.getAppDate()!=null){
								//如果款项类型为按揭，累加到按揭类逾期款项 和 按揭逾期欠款 
								//否则，累加到非按揭逾期欠款
								if(pue.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.LoanAmount) || pue.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
									conAJFundItem=conAJFundItem.add(pue.getApAmount());
								}
								
								/*//合同逾期款项  不包含“预定金”的“金额”-“实收款”
								if(!MoneyTypeEnum.PreconcertMoney.equals(pue.getMoneyDefine().getMoneyType())){
									conFundItem=conFundItem.add(pue.getApAmount().subtract(actPayAmount));
								}*/
								conFundItem=conFundItem.add(pue.getApAmount().subtract(actPayAmount));
								
								int oldFate=0;
								int oldLastFate=0;
								for(int m = 0;m<list.size();m++){
									Map newMap=new HashMap();
									newMap=(Map) list.get(m);
									oldFate+=Integer.parseInt(newMap.get("fate").toString());
									int olDdate=getDiffDays(pue.getAppDate(),new Date());
									BigDecimal unAJOVERDUEARR = (BigDecimal)listUNAJOVERDUEARR.get(m);
									BigDecimal ajoverduearr = (BigDecimal)listAJOVERDUEARR.get(m);
									
									BigDecimal accfundarr = (BigDecimal)listACCFUNDARR.get(m);
									if(m==0){
										int newfate=Integer.parseInt(newMap.get("fate").toString());
										if(olDdate<=newfate){
											if(pue.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.LoanAmount)||pue.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
												listAJOVERDUEARR.set(m, ajoverduearr.add(pue.getApAmount()));
												//合同公积金逾期
												if(pue.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
													listACCFUNDARR.set(m, accfundarr.add(pue.getApAmount().subtract(actPayAmount)));
												}
											}else{
												listUNAJOVERDUEARR.set(m, unAJOVERDUEARR.add(pue.getApAmount()));
											}
										}
									}else{
										if(olDdate<=oldFate && olDdate>oldLastFate){
											if(pue.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.LoanAmount) || pue.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
												listAJOVERDUEARR.set(m, ajoverduearr.add(pue.getApAmount()));
												//合同公积金逾期
												if(pue.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
													listACCFUNDARR.set(m, accfundarr.add(pue.getApAmount().subtract(actPayAmount)));
												}
											}else{
												listUNAJOVERDUEARR.set(m, unAJOVERDUEARR.add(pue.getApAmount()));
											}
										}
									}
									oldLastFate+=Integer.parseInt(newMap.get("fate").toString());
								}
								
								//处理动态列 合同总逾期天数
								int newConFate=conFate;
								/*if(pue.getActPayDate()==null){
									conFate = getDiffDays(pue.getAppDate(),this.getToDate());
								}else{
									conFate = getDiffDays(pue.getAppDate(),pue.getActPayDate());
								}*/
								
								//已收完款  实收日期-应收日期
								if(pue.getApAmount().compareTo(actPayAmount)<=0){
									if(pue.getActPayDate()==null){
										conFate = getDiffDays(pue.getAppDate(),this.getToDate());
									}else{
										conFate =getDiffDays(pue.getAppDate(),pue.getActPayDate());
									}
								}else{//未收完 或者 未收  过滤 结束日期-应收日期
									conFate = getDiffDays(pue.getAppDate(),this.getToDate());
								}
								if(newConFate>conFate){
									conFate=newConFate;
								}
							}
						}
						if(pue.getApAmount()!=null){
							//if(!MoneyTypeEnum.PreconcertMoney.equals(pue.getMoneyDefine().getMoneyType())){
								if(actPayAmount==null){
									actPayAmount = FDCHelper.ZERO;
								}
								conArrearageAmount = conArrearageAmount.add(pue.getApAmount().subtract(actPayAmount).abs());
						//	}
						}
				//	}
				}
				
				}
		
				
				
				//非合同总价
				BigDecimal unContractTotalAmount =ZERO;
				//非合同回款
				BigDecimal unConBackFund =ZERO;
				//非合同按时付款
				BigDecimal unConPayment =ZERO;
				//非合同逾期付款
				BigDecimal unConOverduePayment =ZERO;
				//非合同未逾期欠款
				BigDecimal unConArrearage =ZERO;
				//非合同逾期付清
				BigDecimal UNOVERDUEPAYOFF =ZERO;
				//非合同逾期欠款
				BigDecimal UNOVERDUEARR =ZERO;
				
				List listUUNAJOVERDUEARR=new ArrayList();//非合同非按揭逾期欠款
				List listUAJOVERDUEARR=new ArrayList();//非合同按揭逾期欠款
				for(int ii = 0 ;ii<list.size();ii++){
					listUUNAJOVERDUEARR.add(ZERO);
					listUAJOVERDUEARR.add(ZERO);
				}
				//非合同最长逾期天数
				int unconFate =0;
				
				/////统计房间状态为 认购 或 签约的
			//	if(roomStatus.equals(RoomSellStateEnum.Sign) || roomStatus.equals(RoomSellStateEnum.Purchase)){
				////
				if(puInfo.getElsePayListEntry()!=null){
					for(int ple = 0 ;ple<puInfo.getElsePayListEntry().size();ple++){
						PurchaseElsePayListEntryInfo pleInfo=puInfo.getElsePayListEntry().get(ple);
						//累加到非合同总价
						BigDecimal actPayAmount = getNewActPayAmount(pleInfo);
						unContractTotalAmount=unContractTotalAmount.add(pleInfo.getApAmount());
						if(actPayAmount!=null && actPayAmount.compareTo(ZERO)>=0){
							//如果实付金额不为空，累加到非合同回款
							unConBackFund=unConBackFund.add(actPayAmount);
							if(pleInfo.getAppDate()!=null && pleInfo.getActRevDate()!=null){
								//if(pleInfo.getAppDate().compareTo(pleInfo.getActRevDate())>=0){
								if(compareDate(pleInfo.getAppDate(),pleInfo.getActRevDate())>=0){
									//如果应付日期 大于/等于 实付日期，累加到 非合同按时付款
									unConPayment=unConPayment.add(actPayAmount);
									//如果应付日期 大于/等于 当前时间，并且应付金额大于实付金额，累加到 非合同未逾期欠款
									if(pleInfo.getApAmount().compareTo(actPayAmount)>=0){
										if(pleInfo.getAppDate().compareTo(DateHelper.getDayBegin())>=0){
											unConArrearage=unConArrearage.add(pleInfo.getApAmount().subtract(actPayAmount));
										}
									}
								}
								//逾期
								//if((pleInfo.getAppDate().compareTo(pleInfo.getActRevDate())<0)||
								if(compareDate(pleInfo.getAppDate(),pleInfo.getActRevDate())<0 ||
										//((pleInfo.getAppDate().compareTo(DateHelper.getDayEnd())<0)&&(pleInfo.getApAmount().compareTo(actPayAmount)>0))){
										((compareDate(pleInfo.getAppDate(), DateHelper.getDayEnd())<0)&&(pleInfo.getApAmount().compareTo(actPayAmount)>0))){
									//如果应付日期 小于 实付日期，累加到 非合同逾期欠款
									UNOVERDUEARR=UNOVERDUEARR.add(pleInfo.getApAmount().subtract(actPayAmount));
									//如果款项类型为按揭，累加到非合同按揭逾期欠款
									//否则，累加到非合同非按揭逾期欠款
									int oldFate=0;
									int oldLastFate=0;
									for(int m = 0;m<list.size();m++){
										Map newMap=new HashMap();
										newMap=(Map) list.get(m);
										oldFate+=Integer.parseInt(newMap.get("fate").toString());
										int olDdate=getDiffDays(pleInfo.getAppDate(),new Date());
										BigDecimal uajoverduearr = (BigDecimal)listUAJOVERDUEARR.get(m);
										BigDecimal uunajoverduearr = (BigDecimal)listUUNAJOVERDUEARR.get(m);
										if(m==0){
											int newfate=Integer.parseInt(newMap.get("fate").toString());
											if(olDdate<=newfate){
												if(pleInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.LoanAmount) || pleInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
													listUAJOVERDUEARR.set(m, uajoverduearr.add(pleInfo.getApAmount().subtract(actPayAmount)));
												}else{
													listUUNAJOVERDUEARR.set(m, uunajoverduearr.add(pleInfo.getApAmount().subtract(actPayAmount)));
												}
											}
										}else{
											if(olDdate<=oldFate && olDdate>oldLastFate){
												if(pleInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.LoanAmount) || pleInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
													listUAJOVERDUEARR.set(m, uajoverduearr.add(pleInfo.getApAmount().subtract(actPayAmount)));
												}else{
													listUUNAJOVERDUEARR.set(m, uunajoverduearr.add(pleInfo.getApAmount().subtract(actPayAmount)));
												}
											}
										}
										oldLastFate+=Integer.parseInt(newMap.get("fate").toString());
									}
									
									//如果应付日期小于实付日期，累加到 非合同逾期付款
									if(compareDate(pleInfo.getAppDate(),pleInfo.getActRevDate())<0 ){
										unConOverduePayment=unConOverduePayment.add(actPayAmount);
									}
									
									//计算非合同最长逾期天数
									int newConFate=unconFate;
									
									//已收完款  实收日期-应收日期
									if(pleInfo.getApAmount().compareTo(actPayAmount)<=0){
										unconFate =getDiffDays(pleInfo.getAppDate(),pleInfo.getActRevDate());
									}else{//未收完 或者 未收到  过滤 结束日期-应收日期
										unconFate = getDiffDays(pleInfo.getAppDate(),this.getToDate());
									}
									if(newConFate>unconFate){
										unconFate=newConFate;
									}
								}
							}
						}else{
							
							if(actPayAmount==null){
								actPayAmount= ZERO;
							}
							
							
							if(pleInfo.getAppDate()!=null){
								//如果实付金额等于空，并且应付日期 大于/等于 当前日期（或者 实收日期为空 且过滤截止日期等于应收日期），累加到非合同未逾期欠款
								if((compareDate(pleInfo.getAppDate(), DateHelper.getDayEnd()))>=0
								 || (pleInfo.getActRevDate()==null && compareDate(pleInfo.getAppDate(),this.getToDate())==0)		
								){
									unConArrearage=unConArrearage.add(pleInfo.getApAmount());
								}else{
									UNOVERDUEARR=UNOVERDUEARR.add(pleInfo.getApAmount());
									
									//如果类型是按揭 累加到按揭类逾期款项
									if(pleInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.LoanAmount) || pleInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
										conAJFundItem=conAJFundItem.add(pleInfo.getApAmount());
									}
									
									int oldFate=0;
									int oldLastFate=0;
									for(int m = 0;m<list.size();m++){
										Map newMap=new HashMap();
										newMap=(Map) list.get(m);
										oldFate+=Integer.parseInt(newMap.get("fate").toString());
										int olDdate=getDiffDays(pleInfo.getAppDate(),new Date());
										BigDecimal uajoverduearr = (BigDecimal)listUAJOVERDUEARR.get(m);
										BigDecimal uunajoverduearr = (BigDecimal)listUUNAJOVERDUEARR.get(m);
										if(m==0){
											int newfate=Integer.parseInt(newMap.get("fate").toString());
											if(olDdate<=newfate){
												if(pleInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.LoanAmount)||pleInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
													listUAJOVERDUEARR.set(m, uajoverduearr.add(pleInfo.getApAmount()));
												}else{
													listUUNAJOVERDUEARR.set(m, uunajoverduearr.add(pleInfo.getApAmount()));
												}
											}
										}else{
											if(olDdate<=oldFate && olDdate>oldLastFate){
												if(pleInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.LoanAmount) || pleInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
													listUAJOVERDUEARR.set(m, uajoverduearr.add(pleInfo.getApAmount()));
												}else{
													listUUNAJOVERDUEARR.set(m, uunajoverduearr.add(pleInfo.getApAmount()));
												}
											}
										}
										oldLastFate+=Integer.parseInt(newMap.get("fate").toString());
									}
									
									//计算非合同最长逾期天数
									int newConFate=unconFate;
									
									//已收完款  实收日期-应收日期
									if(pleInfo.getApAmount().compareTo(actPayAmount)<=0){
										if(pleInfo.getActRevDate()==null){
											conFate = getDiffDays(pleInfo.getAppDate(),this.getToDate());
										}else{
											conFate =getDiffDays(pleInfo.getAppDate(),pleInfo.getActRevDate());
										}
									}else{//未收完 或者 未收到  过滤 结束日期-应收日期
										conFate = getDiffDays(pleInfo.getAppDate(),this.getToDate());
									}
									if(newConFate>conFate){
										conFate=newConFate;
									}
			//					}
							}
						}
					}
				}
				///
				}
				////
				IRow row= tblMain.addRow();
				
				row.getCell("id").setValue(id);
				row.getCell("orgUnit.name").setValue(orgUnitName);
				row.getCell("sellProject.name").setValue(sellProjectName);
				row.getCell("building.name").setValue(buildingName);
				row.getCell("sbuarea.name").setValue(subareaName);
				row.getCell("buildUnit.name").setValue(buildUnitName);
				row.getCell("room.name").setValue(roomName);
				row.getCell("Purchase.number").setValue(PurchaseNumber);
				row.getCell("productType.name").setValue(productTypeName);
				row.getCell("roomModel.name").setValue(roomModelName);
				row.getCell("contractTotalAmount").setValue(contractTotalAmount);
				contractTotalAmountSum=contractTotalAmountSum.add(contractTotalAmount);
				row.getCell("conBackFund").setValue(conBackFund);
				conBackFundSum=conBackFundSum.add(conBackFund);
				row.getCell("conPayment").setValue(conPayment);
				conPaymentSum=conPaymentSum.add(conPayment);
				row.getCell("conOverduePayment").setValue(conOverduePayment);
				conOverduePaymentSum=conOverduePaymentSum.add(conOverduePayment);
				//row.getCell("conArrearageAmount").setValue(contractTotalAmount.subtract(conBackFund));
				row.getCell("conArrearageAmount").setValue(conArrearageAmount);
				conArrearageAmountSum=conArrearageAmountSum.add(conArrearageAmount);
				row.getCell("conArrearage").setValue(conArrearage);
				conArrearageSum=conArrearageSum.add(conArrearage);
				//非合同逾期款项
				//AgingScheduleEnum ase= (AgingScheduleEnum) getFilterUI().overdueType.getSelectedItem();
				
				//取全部逾期类
				//if(ase.equals(AgingScheduleEnum.OVERDUEALL)){
					BigDecimal bigSum=ZERO;
					for(int n = 0 ;n<list.size();n++){
						BigDecimal big2=ZERO;
						big2 =((BigDecimal)listUUNAJOVERDUEARR.get(n)).add(((BigDecimal)listUAJOVERDUEARR.get(n)));
						if(sumMap.get((n*5+2)+"")!=null){
							big2=new BigDecimal (sumMap.get((n*5+2)+"").toString()).add(big2);
						}
						bigSum=bigSum.add(big2);
					}
					row.getCell("unConFundItem").setValue(bigSum);
				

				//溢交处理
				if(conAJFundItem.compareTo(ZERO)<0){
					conAJFundItem=ZERO;
				}
				if(conFundItem.compareTo(ZERO)<0){
					conFundItem=ZERO;
				}
					
				UNOVERDUEPAYOFFSUM=UNOVERDUEPAYOFFSUM.add(new BigDecimal(row.getCell("unConFundItem").getValue().toString()));
				row.getCell("conAJFundItem").setValue(conAJFundItem+"");
				conAJFundItemSum=conAJFundItemSum.add(conAJFundItem);
				
				//合同逾期款项
				row.getCell("conFundItem").setValue(conFundItem+"");
				conFundItemSum=conFundItemSum.add(conFundItem);
				
				
				row.getCell("conFate").setValue(conFate+"");
				conFateSum+=conFate;
				row.getCell("unContractTotalAmount").setValue(unContractTotalAmount);
				unContractTotalAmountSum=unContractTotalAmountSum.add(unContractTotalAmount);
				row.getCell("unConBackFund").setValue(unConBackFund);
				unConBackFundSum=unConBackFundSum.add(unConBackFund);
				row.getCell("unConPayment").setValue(unConPayment);
				unConPaymentSum=unConPaymentSum.add(unConPayment);
				row.getCell("unConOverduePayment").setValue(unConOverduePayment);
				unConOverduePaymentSum=unConOverduePaymentSum.add(unConOverduePayment);
				row.getCell("unConArrearageAmount").setValue(unContractTotalAmount.subtract(unConBackFund));
				unConArrearageAmountSum=unConArrearageAmountSum.add(unContractTotalAmount.subtract(unConBackFund));
				row.getCell("unConArrearage").setValue(unConArrearage);
				unConArrearageSum=unConArrearageSum.add(unConArrearage);
				row.getCell("unConFate").setValue(unconFate+"");
				unConFateSum+=unconFate;
				
				Date toSignDate = gettoSignDate(puInfo,roomSign);
				if(toSignDate!=null){
					row.getCell("toSignDate").setValue(toSignDate);
				}
				
				row.getCell("salesman.name").setValue(puInfo.getSalesman()!=null?puInfo.getSalesman().getName():null);
				
				
				//默认  全部逾期 
				//if(ase.equals(AgingScheduleEnum.OVERDUEALL)){
					for(int n = 0 ;n<list.size();n++){
						//合同总逾期
						BigDecimal big1=ZERO;
						big1 =((BigDecimal)listUNAJOVERDUEARR.get(n)).add(((BigDecimal)listAJOVERDUEARR.get(n)));
						boolean a1=false;
						if(sumMap.get((n*5+1)+"")!=null){
							big1=new BigDecimal (sumMap.get((n*5+1)+"").toString()).add(big1);
							a1=true;
						}
						row.getCell(29+n*5).setValue(big1);
						if(sumMapSum.get((n*5+1)+"")!=null){
							sumMapSum.put((n*5+1)+"", new BigDecimal(sumMapSum.get((n*5+1)+"").toString()).add(big1));
						}else{
							sumMapSum.put((n*5+1)+"", big1);
						}
						if(a1){
							intercalateColor(row,(29+n*5),new BigDecimal (sumMap.get((n*5+1)+"").toString()),((BigDecimal)listUNAJOVERDUEARR.get(n)).add(((BigDecimal)listAJOVERDUEARR.get(n))));
						}else{
							intercalateColor(row,(29+n*5),ZERO,((BigDecimal)listUNAJOVERDUEARR.get(n)).add(((BigDecimal)listAJOVERDUEARR.get(n))));
						}
						
						//非合同总逾期
						BigDecimal big2=ZERO;
						big2 =((BigDecimal)listUUNAJOVERDUEARR.get(n)).add(((BigDecimal)listUAJOVERDUEARR.get(n)));
						boolean a2=false;
						if(sumMap.get((n*5+2)+"")!=null){
							big2=new BigDecimal (sumMap.get((n*5+2)+"").toString()).add(big2);
							a2=true;
						}
						row.getCell(29+n*5+1).setValue(big2);
						if(sumMapSum.get((n*5+2)+"")!=null){
							sumMapSum.put((n*5+2)+"", new BigDecimal(sumMapSum.get((n*5+2)+"").toString()).add(big2));
						}else{
							sumMapSum.put((n*5+2)+"", big2);
						}
						if(a2){
							intercalateColor(row,(29+n*5+1),new BigDecimal (sumMap.get((n*5+2)+"").toString()),((BigDecimal)listUUNAJOVERDUEARR.get(n)).add(((BigDecimal)listUAJOVERDUEARR.get(n))));
						}else{
							intercalateColor(row,(29+n*5+1),ZERO,((BigDecimal)listUUNAJOVERDUEARR.get(n)).add(((BigDecimal)listUAJOVERDUEARR.get(n))));
						}
						
						//合同非按揭类逾期
						BigDecimal big3=ZERO;
						big3 =((BigDecimal)listUNAJOVERDUEARR.get(n));
						boolean a3=false;
						if(sumMap.get((n*5+3)+"")!=null){
							big3=new BigDecimal (sumMap.get((n*5+3)+"").toString()).add(big3);
							a3=true;
						}
						row.getCell(29+n*5+2).setValue(big3);
						if(sumMapSum.get((n*5+3)+"")!=null){
							sumMapSum.put((n*5+3)+"", new BigDecimal(sumMapSum.get((n*5+3)+"").toString()).add(big3));
						}else{
							sumMapSum.put((n*5+3)+"", big3);
						}
						if(a3){
							intercalateColor(row,(29+n*5+2),new BigDecimal (sumMap.get((n*5+3)+"").toString()),((BigDecimal)listUNAJOVERDUEARR.get(n)));
						}else{
							intercalateColor(row,(29+n*5+2),ZERO,((BigDecimal)listUNAJOVERDUEARR.get(n)));
						}
				
						
						
						//合同按揭类逾期
						BigDecimal big4=ZERO;
						big4 =((BigDecimal)listAJOVERDUEARR.get(n));
						boolean a4=false;
						if(sumMap.get((n*5+4)+"")!=null){
							big4=new BigDecimal (sumMap.get((n*5+4)+"").toString()).add(big4);
							a4=true;
						}
						row.getCell(29+n*5+3).setValue(big4);
						if(sumMapSum.get((n*5+4)+"")!=null){
							sumMapSum.put((n*5+4)+"", new BigDecimal(sumMapSum.get((n*5+4)+"").toString()).add(big4));
						}else{
							sumMapSum.put((n*5+4)+"", big4);
						}
						if(a4){
							intercalateColor(row,(29+n*5+3),new BigDecimal (sumMap.get((n*5+4)+"").toString()),((BigDecimal)listAJOVERDUEARR.get(n)));
						}else{
							intercalateColor(row,(29+n*5+3),ZERO,((BigDecimal)listAJOVERDUEARR.get(n)));
						}
						
						//合同公积金逾期
						BigDecimal big5=ZERO;
						big5 =((BigDecimal)listACCFUNDARR.get(n));
						boolean a5=false;
						if(sumMap.get((n*5+5)+"")!=null){
							big5=new BigDecimal (sumMap.get((n*5+5)+"").toString()).add(big5);
							a5=true;
						}
						row.getCell(29+n*5+4).setValue(big5);
						if(sumMapSum.get((n*5+5)+"")!=null){
							sumMapSum.put((n*5+5)+"", new BigDecimal(sumMapSum.get((n*5+5)+"").toString()).add(big5));
						}else{
							sumMapSum.put((n*5+5)+"", big5);
						}
						if(a5){
							intercalateColor(row,(29+n*5+4),new BigDecimal (sumMap.get((n*5+5)+"").toString()),((BigDecimal)listACCFUNDARR.get(n)));
						}else{
							intercalateColor(row,(29+n*5+4),ZERO,((BigDecimal)listACCFUNDARR.get(n)));
						}
						
					}
				
			}
			
			if(puc!=null ){
				
				//合计行
				IRow row=null;
				KDTFootManager footRowManager= tblMain.getFootManager();
		        if (footRowManager==null)
		        {
		            String total=EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Total");
		            footRowManager = new KDTFootManager(this.tblMain);
		            footRowManager.addFootView();
		            this.tblMain.setFootManager(footRowManager);
		            row= footRowManager.addFootRow(0);
		            row.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		            this.tblMain.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
		            this.tblMain.getIndexColumn().setWidth(30);
		            footRowManager.addIndexText(0, total);
		        }else{
		            row=footRowManager.getFootRow(0);
		        }
				row.getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);

				
				
				
				if (this.tblMain.getFootManager() != null
						&& this.tblMain.getFootRow(0) != null) {
					this.tblMain.getFootRow(0).getStyleAttributes().setNumberFormat(
							FDCHelper.getNumberFtm(2));
				}

			//	this.tblMain.getGroupManager().setTotalize(true);
				//IRow row = this.tblMain.getGroupManager().getStatRowTemplate(-1);
			//	row.getStyleAttributes().setBackground(
			//			CommerceHelper.BK_COLOR_MUST);
				
				row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
				/*if(row.getCell(hideColl).getStyleAttributes().isHided()){
					hideColl = getDisplyTotalCol();
				}*/
				//row.getCell(1).setValue("合计");
				row.getCell("contractTotalAmount").setValue(contractTotalAmountSum);
				row.getCell("conBackFund").setValue(conBackFundSum);	
				row.getCell("conPayment").setValue(conPaymentSum);
				row.getCell("conOverduePayment").setValue(conOverduePaymentSum);
				row.getCell("conArrearageAmount").setValue(conArrearageAmountSum);
				row.getCell("conArrearage").setValue(conArrearageSum);
				for(int n = 0 ;n<list.size();n++){
					if(sumMapSum.get(n*5+1+"")!=null){
						row.getCell((n*5+1+0)+"").setValue(sumMapSum.get(n*5+1+""));
					}else{
						row.getCell((n*5+1+0)+"").setValue("0");
					}
					if(sumMapSum.get(n*5+2+"")!=null){
						row.getCell((n*5+1+1)+"").setValue(sumMapSum.get(n*5+2+""));
					}else{
						row.getCell((n*5+1+1)+"").setValue("0");
					}
					if(sumMapSum.get(n*5+3+"")!=null){
						row.getCell((n*5+1+2)+"").setValue(sumMapSum.get(n*5+3+""));
					}else{
						row.getCell((n*5+1+2)+"").setValue("0");
					}
					if(sumMapSum.get(n*5+4+"")!=null){
						row.getCell((n*5+1+3)+"").setValue(sumMapSum.get(n*5+4+""));
					}else{
						row.getCell((n*5+1+3)+"").setValue("0");
					}
					if(sumMapSum.get(n*5+5+"")!=null){
						row.getCell((n*5+1+4)+"").setValue(sumMapSum.get(n*5+5+""));
					}else{
						row.getCell((n*5+1+4)+"").setValue("0");
					}
				}
				row.getCell("conAJFundItem").setValue(conAJFundItemSum+"");
				
				row.getCell("conFundItem").setValue(conFundItemSum+"");
				
				row.getCell("conFate").setValue("");
				
				row.getCell("unContractTotalAmount").setValue(unContractTotalAmountSum);
				row.getCell("unConBackFund").setValue(unConBackFundSum);	
				row.getCell("unConPayment").setValue(unConPaymentSum);
				row.getCell("unConOverduePayment").setValue(unConOverduePaymentSum);
				row.getCell("unConArrearageAmount").setValue(unConArrearageAmountSum);
				row.getCell("unConArrearage").setValue(unConArrearageSum);
				//row.getCell("unConFate").setValue(unConFateSum+"");
				row.getCell("unConFate").setValue("");
				row.getCell("unConFundItem").setValue(UNOVERDUEPAYOFFSUM);
				
				tblMain.reLayoutAndPaint();
				this.tblMain.getGroupManager().group();
			}
		}
	}
	
	private Date gettoSignDate(PurchaseInfo pue,RoomSignContractCollection  roomSign){
		
		if(roomSign!=null && pue!=null){
			for(int i=0;i<roomSign.size();i++){
				RoomSignContractInfo  roomsign = roomSign.get(i);
				if(roomsign!=null && roomsign.getPurchase()!=null){
					if(roomsign.getPurchase().getId().toString().equals(pue.getId().toString())){
						return roomsign.getSignDate();
					}
				}
			}
		}
		
		return null;
	}
	
	private String getDisplyTotalCol(){
		for(int i =0;i<tblMain.getColumnCount();i++){
			if(!tblMain.getColumn(i).getStyleAttributes().isHided()){
				return tblMain.getColumn(i).getKey();
			}
		}
		return hideColl;
	}
	
	
	private BigDecimal getNewActPayAmount(RevListInfo pue){
		
		if(pue==null){
			return null;
		}
		if(pue!=null ){
			if( pue.getActRevAmount()==null){
				return null;
			}
			if(pue.getActRevAmount().equals(FDCHelper.ZERO) && pue.getActRevDate()==null){
				return null;
			}
		}
		
		BigDecimal actPay = pue.getActRevAmount();
		if(pue.getHasTransferredAmount()!=null){
			actPay =	actPay.subtract(pue.getHasTransferredAmount());
		}
		if(pue.getHasAdjustedAmount()!=null){
			actPay =	actPay.subtract(pue.getHasAdjustedAmount());
		}
		if(pue.getHasRefundmentAmount()!=null){
			actPay =	actPay.subtract(pue.getHasRefundmentAmount());
		}
		
		return actPay;
	}
	
	
	
	//合同颜色
	public void initColor(PurchasePayListEntryInfo pue,Map newMap,Map sumMap,int m,int i){
		if(pue.getActPayAmount()==null || pue.getActPayAmount().compareTo(ZERO)==0){
			if(sumMap.containsKey((m*5+i)+"color")){
				if(sumMap.get((m*5+i)+"color").equals("0")){
					sumMap.put((m*5+i)+"color","2");
				}else if(sumMap.get((m*5+i)+"color").equals("1")){
					sumMap.put((m*5+i)+"color","1");
				}
			}else{
				sumMap.put((m*5+i)+"color","1");
			}
		}else{
			if(pue.getApAmount().compareTo(pue.getActPayAmount())>0){
				if(sumMap.containsKey((m*5+i)+"color")){
					if(sumMap.get((m*5+i)+"color").equals("0")){
						sumMap.put((m*5+i)+"color","2");
					}else if(sumMap.get((m*5+i)+"color").equals("1")){
						sumMap.put((m*5+i)+"color","1");
					}
				}else{
					sumMap.put((m*5+i)+"color","1");
				}
			}else{
				if(sumMap.containsKey((m*5+i)+"color")){
					if(sumMap.get((m*5+i)+"color").equals("0")){
						sumMap.put((m*5+i)+"color","0");
					}else if(sumMap.get((m*5+i)+"color").equals("1")){
						sumMap.put((m*5+i)+"color","2");
					}
				}else{
					sumMap.put((m*5+i)+"color","0");
				}
			}
		}
		
	}
	
	//非合同颜色
	public void initColor(PurchaseElsePayListEntryInfo pue,Map newMap,Map sumMap,int m,int i){
		if(pue.getActPayAmount()==null || pue.getActPayAmount().compareTo(ZERO)==0){
			if(newMap.get((m*5+i)+"color").equals("0")){
				sumMap.put((m*5+i)+"color","2");
			}else if(newMap.get((m*5+i)+"color").equals("1")){
				sumMap.put((m*5+i)+"color","1");
			}else{
				sumMap.put((m*5+i)+"color","1");
			}
		}else{
			if(pue.getApAmount().compareTo(pue.getActPayAmount())>0){
				if(newMap.get((m*5+i)+"color").equals("0")){
					sumMap.put((m*5+i)+"color","2");
				}else if(newMap.get((m*5+i)+"color").equals("1")){
					sumMap.put((m*5+i)+"color","1");
				}else{
					sumMap.put((m*5+i)+"color","1");
				}
			}else{
				if(newMap.get((m*5+i)+"color").equals("0")){
					sumMap.put((m*5+i)+"color","0");
				}else if(newMap.get((m*5+i)+"color").equals("1")){
					sumMap.put((m*5+i)+"color","2");
				}else{
					sumMap.put((m*5+i)+"color","0");
				}
			}
		}
		
	}
	
	public void intercalateColor(IRow row,int rows,BigDecimal a,BigDecimal b){
		boolean aa =false;
		boolean bb =false;
		if(a.compareTo(ZERO)>0 || a.compareTo(ZERO)<0 ){
			aa=true;
		}
		if(b.compareTo(ZERO)>0 ||b.compareTo(ZERO)<0){
			bb =true;
		}
		if(aa && !bb){
			//row.getCell(rows).getStyleAttributes().setFontColor(Color.BLUE);
		}else if(!aa && bb){
			//row.getCell(rows).getStyleAttributes().setFontColor(Color.RED);
		}else if(aa && bb){
			//row.getCell(rows).getStyleAttributes().setFontColor(Color.ORANGE.darker());
		}
	}
	
	// 返回两个日期的比较天数
	public static int getDiffDays(Date beginDate, Date endDate) {
		if (beginDate == null || endDate == null) {
			throw new IllegalArgumentException("getDiffDays param is null!");
		}
		long diff = (DateHelper.getDayBegin(endDate).getTime() - DateHelper.getDayBegin(beginDate).getTime())
				/ (1000 * 60 * 60 * 24);
		int days = new Long(diff).intValue() ;
		return days;
	}
	
	private boolean compareDate(Date start,Date end,Date val){
		if(val==null){
			return false;
		}
		
		if(start==null || end==null){
			return false;
		}
		
		if(val.compareTo(start)>=0  && val.compareTo(end)<=0 ){
			return true;
		}
		return  false;
	}
	
	private int compareDate(Date val1,Date val2){
		
		val1 = DateTimeUtils.truncateDate(val1);
		val2 = DateTimeUtils.truncateDate(val2);
		
		return val1.compareTo(val2);
	}
	

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		//super.tblMain_tableClicked(e);
	}
	
}