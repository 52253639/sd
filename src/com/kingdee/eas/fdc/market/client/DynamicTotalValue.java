/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.market.client.TableUtils;
import com.kingdee.eas.fdc.market.ActivityValueReportFactory;
import com.kingdee.eas.fdc.market.ActivityValueReportInfo;
import com.kingdee.eas.fdc.market.AreaSetFactory;
import com.kingdee.eas.fdc.market.AreaSetInfo;
import com.kingdee.eas.fdc.market.DynamicTotalValueFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.eas.fdc.market.region;

/**
 * output class name
 */
public class DynamicTotalValue extends AbstractDynamicTotalValue
{
    private static final Logger logger = CoreUIObject.getLogger(DynamicTotalValue.class);
    public static String quickName = null;//快照名称
    public static String quickDes = null;//快照描述
    public static final BigDecimal WAN = new BigDecimal(10000);
    /**
     * output class constructor
     */
    public DynamicTotalValue() throws Exception
    {
        super();
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(1);
        enableExportExcel(tblMain);
        disposeUIWindow();
        btnChart.setVisible(true);
        
    }
    
    
    public void onShow()throws Exception
    {
        super.onShow();
        tblMain.setEditable(false);
        tblMain.getViewManager().setFreezeView(-1, 7);
    }
    
    //设置小计行
    public void setSmallTotalRow(KDTable table){
    	KDTMergeManager mm = table.getMergeManager();
    	int rowCount = table.getRowCount();
    	String productType = "";
    	String oldProductType = productType;
    	String planIndexType = "";
    	String oldPlanIndexType = planIndexType;
    	for(int i=0;i<rowCount;i++){
    		planIndexType = (String)table.getCell(i, 4).getValue();
    		if("".equals(oldPlanIndexType) || oldPlanIndexType==PlanIndexTypeEnum.house.getAlias()){
	    		if(table.getCell(i, 5).getValue() == null){
	    			continue;
	    		}else{
	    			productType = table.getCell(i, 5).getValue().toString();
	    			if(!planIndexType.equals(oldPlanIndexType)){
	    				if(!"".equals(oldProductType)){
	    					IRow row = table.addRow(i);
	        				rowCount++;
	    					row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
	    					row.getStyleAttributes().setLocked(true);
	    					row.getCell(6).setValue("小计");
	    					row.getCell(4).setValue(oldPlanIndexType);
	    					row.getCell(5).setValue(oldProductType);
	    					row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	    					row.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
	    					row.setEditor( CommerceHelper.getKDFormattedTextDecimalEditor());
	    				}
	    				oldProductType = "";
	    			}
	    			oldPlanIndexType = planIndexType;
	    			
	    			if(!oldProductType.equals(productType)){
	    				if(!"".equals(oldProductType)){
	        				IRow row = table.addRow(i);
	        				rowCount++;
	    					row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
	    					row.getStyleAttributes().setLocked(true);
	    					row.getCell(6).setValue("小计");
	    					row.getCell(4).setValue(planIndexType);
	    					row.getCell(5).setValue(oldProductType);
	    					row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	    					row.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
	    					row.setEditor( CommerceHelper.getKDFormattedTextDecimalEditor());
	    				}
						oldProductType = productType;
	    			}
	    			
	    			/*if(i==rowCount-1){
	    				if(!"".equals(oldProductType)){
		    				IRow row = table.addRow(++i);
		    				rowCount++;
							row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
							row.getStyleAttributes().setLocked(true);
							row.getCell(6).setValue("小计");
							row.getCell(4).setValue(planIndexType);
							row.getCell(5).setValue(oldProductType);
							row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
							row.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
							row.setEditor( CommerceHelper.getKDFormattedTextDecimalEditor());
	    				}
	    			}*/
	    		}
	    	}
	    	table.setRowCount(rowCount);
    	}
    }
    
    //设置合计行以及融合
	public void setTotalRow(KDTable table){
		int business = 0;
		int park = 0;
		int publicBuild = 0;
		KDTMergeManager mm = table.getMergeManager();
		int rowCount = table.getRowCount();
		for(int i=0;i<rowCount;i++){
			String planIndexType = (String)table.getCell(i, 4).getValue();
			if(PlanIndexTypeEnum.business.getAlias().equals(planIndexType)){
				if(business==0){
					business = i;
					IRow row = table.addRow(i);
					rowCount++;
					row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
					row.getStyleAttributes().setLocked(true);
					row.getCell(5).setValue("合计");
					row.getCell(4).setValue(PlanIndexTypeEnum.house.getAlias());
					row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
					row.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
					row.setEditor( CommerceHelper.getKDFormattedTextDecimalEditor());
				}
			}else if(PlanIndexTypeEnum.publicBuild.getAlias().equals(planIndexType)){
				if(park==0){
					park = i;
					IRow row = table.addRow(i);
					rowCount++;
					row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
					row.getStyleAttributes().setLocked(true);
					row.getCell(5).setValue("合计");
					row.getCell(4).setValue(PlanIndexTypeEnum.business.getAlias());
					row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
					row.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
					row.setEditor( CommerceHelper.getKDFormattedTextDecimalEditor());
				}
			}else if(PlanIndexTypeEnum.parking.getAlias().equals(planIndexType)){
				if(publicBuild==0){
					publicBuild=i;
					IRow row = table.addRow(i);
					rowCount++;
					row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
					row.getStyleAttributes().setLocked(true);
					row.getCell(5).setValue("合计");
					row.getCell(4).setValue(PlanIndexTypeEnum.publicBuild.getAlias());
					row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
					row.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
					row.setEditor( CommerceHelper.getKDFormattedTextDecimalEditor());
				}
			}
			if(i==rowCount-1){
				IRow row = table.addRow(++i);
				rowCount++;
				row.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
				row.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
				row.getStyleAttributes().setLocked(true);
				row.getCell(5).setValue("合计");
				row.getCell(4).setValue(PlanIndexTypeEnum.parking.getAlias());
			}
		}
		EnterprisePlanEditUI.mergerTable(table,new String[]{"typename"},new String[]{"typename"});
		mm.mergeBlock(0, 4, business, 4, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(business+1, 4, park, 4, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(park+1, 4, publicBuild, 4, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(publicBuild+1, 4, rowCount-1, 4, KDTMergeManager.SPECIFY_MERGE);
		/*mm.mergeBlock(business+1, 4, publicBuild, 4, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(publicBuild+1, 4, park, 4, KDTMergeManager.SPECIFY_MERGE);
		mm.mergeBlock(park+1, 4, rowCount-1, 4, KDTMergeManager.SPECIFY_MERGE);*/
		table.setRowCount(rowCount);
	}
	public static void reSetExpressions(KDTable table){
		int hjColumn = 7;
    	int dynRowBase = 4;
		int houseSubIndex=0;
		int businessSubIndex=0;
		int publicSubIndex=0;
		int rowCount = table.getRowCount();
		if(table.getRowCount()>0){
			int parkingSubIndex=rowCount-1;
			for(int i=0;i<rowCount-1;i++){
				IRow row=table.getRow(i);
				if(row.getCell(dynRowBase).getValue().equals(PlanIndexTypeEnum.house.getAlias()) && isTotalRow(row)){
					houseSubIndex=i;
				}
				if(row.getCell(dynRowBase).getValue().equals(PlanIndexTypeEnum.business.getAlias()) && isTotalRow(row)){
					businessSubIndex=i;
				}
				if(row.getCell(dynRowBase).getValue().equals(PlanIndexTypeEnum.publicBuild.getAlias()) && isTotalRow(row)){
					publicSubIndex=i;
				}
				
			}
			//住宅
			if(houseSubIndex>0){
				IRow houseRow=table.getRow(houseSubIndex);
				reSetExpressions(table,houseRow,hjColumn,1,houseSubIndex);
			}
			//商业
			if(businessSubIndex>(houseSubIndex+1)){
				IRow businessRow=table.getRow(businessSubIndex);
				reSetExpressions(table,businessRow,hjColumn,houseSubIndex+2,businessSubIndex);
			}
			//公建
			if(publicSubIndex>(businessSubIndex+1)){
				IRow publicRow=table.getRow(publicSubIndex);
				reSetParkExpressions(table,publicRow,hjColumn,businessSubIndex+2,publicSubIndex);
			}
			//停车
			if(parkingSubIndex>(publicSubIndex+1)){
				IRow parkRow=table.getRow(parkingSubIndex);
				reSetExpressions(table,parkRow,hjColumn,publicSubIndex+2,parkingSubIndex);
			}
			//停车
			/*if(parkingSubIndex>(businessSubIndex+1)){
				IRow parkRow=table.getRow(parkingSubIndex);
				reSetParkExpressions(table,parkRow,hjColumn,businessSubIndex+2,parkingSubIndex);
			}*/
		}
	}
	
	public static void reSetSmallExpressions(KDTable table){
		int hjColumn = 7;
    	int dynRowBase = 4;
		int index = 0;
		int oldIndex=1;
		String planIndexType = "";
		int publicSubIndex=0;
		int rowCount = table.getRowCount();
		if(table.getRowCount()>0){
			int parkingSubIndex=rowCount-1;
			for(int i=0;i<rowCount-1;i++){
				IRow row=table.getRow(i);
				planIndexType = (String)table.getCell(i, 4).getValue();
	    		if(planIndexType==PlanIndexTypeEnum.house.getAlias()){
	    			if(isSmallTotalRow(row)){
						index = i;
						reSetExpressions(table,row,hjColumn,oldIndex,index);
						oldIndex = index + 2;
					}
	    		}
			}
		}
	}
	
	public static void reSetParkExpressions(KDTable tblMain,IRow row,int hjColumn,int n ,int m){
		for(int j=hjColumn;j<tblMain.getColumnCount();j++){
			String c ="";
			String f ="";
			String i ="";
			String exp = "";
			if(j<=25){
			   c=String.valueOf(((char)('A'+j)));
			}else{
			   c=(char)('A'+j/26-1)+String.valueOf(((char)('A'+j%26)));
			}
			exp="=sum("+c+(n)+":"+c+(m)+")";
			row.getCell(j).setExpressions(exp);
			if((j-5)%4==0){
				BigDecimal sumAmount = FDCHelper.ZERO;
		        BigDecimal acreage = FDCHelper.ZERO;
		        if(row.getCell(j).getValue()!=null)
		        	sumAmount = new BigDecimal(row.getCell(j).getValue().toString());
		        if(row.getCell(j-2).getValue()!=null)
		        	acreage = new BigDecimal(row.getCell(j-2).getValue().toString());
		        if(acreage.intValue()!=0)
		        	row.getCell(j-1).setValue(sumAmount.divide(acreage,4,2));
				row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
				row.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			}
		}
	}
	//求小计
	public static void reSetExpressions(KDTable tblMain,IRow row,int hjColumn,int n ,int m){
		for(int j=hjColumn;j<tblMain.getColumnCount();j++){
			String c ="";
			String exp = "";
			if(j<=25){
			   c=String.valueOf(((char)('A'+j)));
			}else{
			   c=(char)('A'+j/26-1)+String.valueOf(((char)('A'+j%26)));
			}
			exp="=sum("+c+(n)+":"+c+(m)+")";
			if(row.getCell(4).getValue().toString()==PlanIndexTypeEnum.house.getAlias() && isTotalRow(row)){
				exp="=sum("+c+(n)+":"+c+(m)+")/2";
			}
			row.getCell(j).setExpressions(exp);
			if((j-6)%4==0){
				BigDecimal sumAmount = FDCHelper.ZERO;
		        BigDecimal acreage = FDCHelper.ZERO;
		        if(row.getCell(j).getValue()!=null)
		        	sumAmount = new BigDecimal(row.getCell(j).getValue().toString());
		        if(row.getCell(j-3).getValue()!=null)
		        	acreage = new BigDecimal(row.getCell(j-3).getValue().toString());
		        if(acreage.intValue()!=0)
		        	row.getCell(j-1).setValue(sumAmount.divide(acreage,4,2));
				row.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
				row.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			}
			if(j==tblMain.getColumnCount()-1){
				//最新版测算货值金额
				BigDecimal latestamount = row.getCell("latestamount").getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell("latestamount").getValue().toString());
    			//最新测算货值面积 
    			BigDecimal latestarea = row.getCell("latestarea").getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell("latestarea").getValue().toString());
    			//已售部分合计面积
    			BigDecimal hadsellarea = row.getCell("hadsellarea").getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell("hadsellarea").getValue().toString());
    			//已售部分合计金额
    			BigDecimal hadsellamount = row.getCell("hadsellamount").getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell("hadsellamount").getValue().toString());
    			//已取预证已定价面积
    			BigDecimal hadpricearea = row.getCell("hadpricearea").getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell("hadpricearea").getValue().toString());
    			//已取预证已定价金额
    			BigDecimal hadpriceamount = row.getCell("hadpriceamount").getValue()==null?BigDecimal.ZERO:new BigDecimal(row.getCell("hadpriceamount").getValue().toString());
    			if(latestarea.subtract(hadsellarea).subtract(hadpricearea).compareTo(BigDecimal.ZERO) != 0){
    				row.getCell("syhzjj").setValue((latestamount.subtract(hadsellamount).subtract(hadpriceamount)).divide((latestarea.subtract(hadsellarea).subtract(hadpricearea)), BigDecimal.ROUND_HALF_UP).setScale(2,BigDecimal.ROUND_HALF_UP));
    			}
			}
		}
	}
	private static boolean isTotalRow(IRow row){
		boolean isSubTotalRow=false;
		if(row.getCell(5).getValue()!=null&&row.getCell(5).getValue().equals("合计")){
			isSubTotalRow=true;
		}
		return isSubTotalRow;
	}
	
	private static boolean isSmallTotalRow(IRow row){
		boolean isSubTotalRow=false;
		if(row.getCell(6).getValue()!=null&&row.getCell(6).getValue().equals("小计")){
			isSubTotalRow=true;
		}
		return isSubTotalRow;
	}
	
	public static void getFootRow(KDTable tblMain,Set columnName){
		IRow footRow = null;
        KDTFootManager footRowManager = tblMain.getFootManager();
        if(footRowManager == null)
        {
            String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
            footRowManager = new KDTFootManager(tblMain);
            footRowManager.addFootView();
            tblMain.setFootManager(footRowManager);
            footRow = footRowManager.addFootRow(0);
            footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
            tblMain.getIndexColumn().setWidthAdjustMode((short)1);
            tblMain.getIndexColumn().setWidth(30);
            footRowManager.addIndexText(0, total);
        } else
        {
            footRow = footRowManager.getFootRow(0);
        }
        int columnCount = tblMain.getColumnCount();
        for(int c = 0; c < columnCount; c++)
        {
        	if(columnName.contains(String.valueOf(c))){
	            ICell cell = footRow.getCell(c);
	            cell.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	            cell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
	            cell.getStyleAttributes().setFontColor(java.awt.Color.BLACK);
	            if(c==columnCount-1){
	            	//最新版测算货值金额
					BigDecimal latestamount = footRow.getCell("latestamount").getValue()==null?BigDecimal.ZERO:new BigDecimal(footRow.getCell("latestamount").getValue().toString());
	    			//最新测算货值面积 
	    			BigDecimal latestarea = footRow.getCell("latestarea").getValue()==null?BigDecimal.ZERO:new BigDecimal(footRow.getCell("latestarea").getValue().toString());
	    			//已售部分合计面积
	    			BigDecimal hadsellarea = footRow.getCell("hadsellarea").getValue()==null?BigDecimal.ZERO:new BigDecimal(footRow.getCell("hadsellarea").getValue().toString());
	    			//已售部分合计金额
	    			BigDecimal hadsellamount = footRow.getCell("hadsellamount").getValue()==null?BigDecimal.ZERO:new BigDecimal(footRow.getCell("hadsellamount").getValue().toString());
	    			//已取预证已定价面积
	    			BigDecimal hadpricearea = footRow.getCell("hadpricearea").getValue()==null?BigDecimal.ZERO:new BigDecimal(footRow.getCell("hadpricearea").getValue().toString());
	    			//已取预证已定价金额
	    			BigDecimal hadpriceamount = footRow.getCell("hadpriceamount").getValue()==null?BigDecimal.ZERO:new BigDecimal(footRow.getCell("hadpriceamount").getValue().toString());
	    			if(latestarea.subtract(hadsellarea).subtract(hadpricearea).compareTo(BigDecimal.ZERO) != 0){
	    				footRow.getCell("syhzjj").setValue((latestamount.subtract(hadsellamount).subtract(hadpriceamount)).divide((latestarea.subtract(hadsellarea).subtract(hadpricearea)), BigDecimal.ROUND_HALF_UP).setScale(2));
	    			}
	            }else{
		            if((c-5)%4!=0){
		            	cell.setValue(TableUtils.getColumnValueSum(tblMain,c));
	        	    }else{
	        	    	cell.setValue(FDCHelper.divide(TableUtils.getColumnValueSum(tblMain,c+1),TableUtils.getColumnValueSum(tblMain,c-2) ));
	        	    }
	            }
        	}
        }
        footRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
        footRow.getCell(10).getStyleAttributes().setFontColor(Color.RED);
        footRow.getCell(14).getStyleAttributes().setFontColor(Color.RED);
        footRow.getCell(18).getStyleAttributes().setFontColor(Color.RED);
	}
	/**
	 * 行总计
	 * */
	public static void getRowSum(KDTable table){
		Set columnName = new HashSet();
		for(int i=7;i<table.getColumnCount();i++){
			columnName.add(String.valueOf(i));
		}
		getFootRow(table,columnName);
		IRow footRow = null;
        KDTFootManager footRowManager = table.getFootManager();
        footRow = footRowManager.getFootRow(0);
       /* TableUtils.changeTableNumberFormat(table, new String[]{"tararea","tarcount","tarunitprice","taramount",
        		"yeararea","yearcount","yearunitprice","yearamount",
				"zjarea","zjcount","zjunitprice","zjamount",
				"wqzarea","wqzcount","wqzunitprice","wqzamount",
				"yqzwdjarea","yqzwdjcount","yqzwdjunitprice","yqzwdjamount",
				"wqzydjarea","wqzydjcount","wqzydjunitprice","wqzydjamount",
				"yqzydjarea","yqzydjcount","yqzydjunitprice","yqzydjamount",
				"ysarea","yscount","ysunitprice","ysamount"});*/
        footRow.getCell(5).setValue("总计");
	}
    protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return null;
		//return new ActivityValueReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return DynamicTotalValueFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected void query() {
		tblMain.removeColumns();
		tblMain.removeRows();
		setSmallTotalRow(tblMain);
		setTotalRow(tblMain);
		reSetExpressions(tblMain);
		reSetSmallExpressions(tblMain);
		//getRowSum(tblMain);
	}

	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		try {
            this.tblMain.removeColumns();
        	RptParams pp=(RptParams)params.clone();
        	RptParams rpt = getRemoteInstance().createTempTable(pp);
            RptTableHeader header = (RptTableHeader)rpt.getObject("header");
            
            //FIXME ADD BY YLW 动态添加表单列
            DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeProject.getLastSelectedPathComponent();
            IRowSet rowSet = null;
    		if(node!=null){
    			if (node.getUserObject() instanceof SellProjectInfo) {
    				SellProjectInfo info = (SellProjectInfo) node.getUserObject();
    				//得到项目Id
    				String projectId = info.getId().toString();
    				//根据项目Id查询出最新货值测算
    				FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
    				sqlBuilder.appendSql("/*dialect*/select distinct region from( \n")
	        		  .appendSql("select case to_char(cfyzrq,'yyyy') when to_char(sysdate,'yyyy') then \n")
	        		  .appendSql("case when to_char(cfyzrq,'mm') >='01' and to_char(cfyzrq,'mm') <='03' then to_char(cfyzrq,'yyyy') || '年1季度' \n")
	        		  .appendSql("else case when to_char(cfyzrq,'mm') >='04' and to_char(cfyzrq,'mm') <='06' then to_char(cfyzrq,'yyyy') || '年2季度' \n")
	        		  .appendSql("else case when to_char(cfyzrq,'mm') >='07' and to_char(cfyzrq,'mm') <='09' then to_char(cfyzrq,'yyyy') || '年3季度' \n")
	        		  .appendSql("else case when to_char(cfyzrq,'mm') >='10' and to_char(cfyzrq,'mm') <='12' then to_char(cfyzrq,'yyyy') || '年4季度' end end end end \n")
	        		  .appendSql("else case when to_char(cfyzrq,'mm') >='01' and to_char(cfyzrq,'mm') <='06' then to_char(cfyzrq,'yyyy') || '年上半年'  \n")
	        		  .appendSql("else case when to_char(cfyzrq,'mm') >='07' and to_char(cfyzrq,'mm') <='12' then to_char(cfyzrq,'yyyy') || '年下半年' end end \n")
	        		  .appendSql("end as region \n")
    				  .appendSql("from ct_mar_sellsupplyplanentry a,ct_mar_sellsupplyplan b \n")
    				  .appendSql("where a.fhadacquired = 0 and a.fparentid = b.fid and cfyzrq>sysdate and b.fsellprojectid='").appendSql(projectId).appendSql("' \n")
    				  .appendSql(") t order by t.region desc ");
    				rowSet = sqlBuilder.executeQuery();
    				RptTableColumn col = null;
    				if(rowSet != null && rowSet.size()>0){
    					col = new RptTableColumn("totalsy01");
			            col.setWidth(80);
			            header.addColumn(39,col);
			            col = new RptTableColumn("totalsy02");
			            col.setWidth(80);
			            header.addColumn(40,col);
			            col = new RptTableColumn("totalsy03");
			            col.setWidth(80);
			            header.addColumn(41,col);
			            col = new RptTableColumn("totalsy04");
			            col.setWidth(120);
			            header.addColumn(42,col);
			            Object[][] labels = addRptColumn(header, "剩余部分", "合计");
			            header.setLabels(labels,true);
			            
    					
    					String region1;
    					while(rowSet.next()){
    						region1 = rowSet.getString("region");
    						col = new RptTableColumn("temp"+region1+"01");
    			            col.setWidth(80);
    			            header.addColumn(39,col);
    			            col = new RptTableColumn("temp"+region1+"02");
    			            col.setWidth(80);
    			            header.addColumn(40,col);
    			            col = new RptTableColumn("temp"+region1+"03");
    			            col.setWidth(80);
    			            header.addColumn(41,col);
    			            col = new RptTableColumn("temp"+region1+"04");
    			            col.setWidth(120);
    			            header.addColumn(42,col);
    			            
    			            labels = addRptColumn(header, "剩余部分", region1);
    			            header.setLabels(labels,true);
    					}
    				}
		            this.tblMain.repaint();
		            KDTableUtil.setHeader(header, tblMain);
		            rpt=getRemoteInstance().query(pp);
		            tblMain.setRowCount(rpt.getInt("count"));
			        RptRowSet rs = (RptRowSet)rpt.getObject("rowset");
			        KDTableUtil.insertRows(rs, 0, tblMain);
			        
			        this.tblMain.getColumn("productConstitute").setMergeable(true);
			        
			        //填充Table
			        /*ValueBreakInfo valueBreak = ValueBreakFactory.getRemoteInstance().getValueBreakInfo(" where isNewVersoin = 1 and sellProject.id="+projectId);
			        ValueBreakEntryCollection valueBreakEntry = valueBreak.getEntrys();*/
			        sqlBuilder.clear();
			        sqlBuilder.appendSql("/*dialect*/select * from ( \n")
			        		  .appendSql("select nvl(p.ftype,t.ftype) as ftype,nvl(p.fproducttypeid,t.fproducttypeid) as fproducttypeid,nvl(p.fnewarearange,t.fnewarearange) as fnewarearange,t.farea,t.fploidy,t.fprice,t.famount,\n")
			        		  .appendSql("p.farea as latestarea,p.fploidy as latestploidy,p.fprice as latestprice,p.famount as latestamount \n")
			        		  .appendSql("from( \n")
			        		  .appendSql("select b.ftype,b.fproducttypeid,b.fnewarearange,b.farea,b.fploidy,b.fprice,b.famount \n")
			        		  .appendSql("from t_mar_valuebreak a,t_mar_valuebreakentry b,t_bd_measurephase c \n")
			        		  .appendSql("where a.fsellprojectid='").appendSql(projectId).appendSql("' and a.fid=b.fheadid and a.fmeasurestageid  =c.fid \n")
			        		  .appendSql("and c.fnumber='001' and b.fyear=0) t \n")
			        		  .appendSql("full outer join ( \n")
			        		  .appendSql("select b.ftype,b.fproducttypeid,b.fnewarearange,b.farea,b.fploidy,b.fprice,b.famount \n")
			        		  .appendSql("from t_mar_valuebreak a,t_mar_valuebreakentry b \n")
			        		  .appendSql("where a.fsellprojectid='").appendSql(projectId).appendSql("' and a.fid=b.fheadid \n")
			        		  .appendSql("and b.fyear=0 and a.fisnewversoin=1 and b.farea<>0) p \n")
			        		  .appendSql("on t.ftype=p.ftype and t.fproducttypeid=p.fproducttypeid and t.fnewarearange=p.fnewarearange) tt \n")
			        		  .appendSql("order by tt.ftype,tt.fproducttypeid,tt.fnewarearange");
			        rowSet = sqlBuilder.executeQuery();
			        if(rowSet != null){
			        	String type,producttype,arearange,area,ploidy,latestarea,latestploidy;
			        	BigDecimal price = BigDecimal.ZERO;
			        	BigDecimal amount = BigDecimal.ZERO;
			        	BigDecimal latestprice = BigDecimal.ZERO;
			        	BigDecimal latestamount = BigDecimal.ZERO;
			        	IRow row = null;
			        	int index = 0;
			        	while(rowSet.next()){
			        		++index;
			        		type = getValue(rowSet.getString("ftype"));
			        		if(!"".equals(type)){
			        			type = PlanIndexTypeEnum.getEnum(type).getAlias();
			        		}
			        		producttype = getValue(rowSet.getString("fproducttypeid"));
			        		if(!"".equals(producttype)){
			        			ProductTypeInfo productTypeInfo = ProductTypeFactory.getRemoteInstance().getProductTypeInfo(new ObjectUuidPK(producttype));
			        			if(productTypeInfo != null){
			        				producttype = productTypeInfo.getName();
			        			}
			        		}
			        		arearange = getValue(rowSet.getString("fnewarearange"));
			        		if(!"".equals(arearange)){
			        			AreaSetInfo areaSetInfo = AreaSetFactory.getRemoteInstance().getAreaSetInfo(new ObjectUuidPK(arearange));
			        			if(areaSetInfo != null)
			        				arearange = areaSetInfo.getName();
			        		}
			        		area = getValueDefault(rowSet.getString("farea"),"0");
			        		ploidy = getValueDefault(rowSet.getString("fploidy"),"0");
			        		price = rowSet.getBigDecimal("fprice")==null?BigDecimal.ZERO:rowSet.getBigDecimal("fprice").setScale(2).divide(WAN,BigDecimal.ROUND_HALF_UP).setScale(2);
			        		amount = rowSet.getBigDecimal("famount")==null?BigDecimal.ZERO:rowSet.getBigDecimal("famount").setScale(2).divide(WAN,BigDecimal.ROUND_HALF_UP).setScale(2);
			        		latestarea = getValueDefault(rowSet.getString("latestarea"),"0");
			        		latestploidy = getValueDefault(rowSet.getString("latestploidy"),"0");
			        		latestprice = rowSet.getBigDecimal("latestprice")==null?BigDecimal.ZERO:rowSet.getBigDecimal("latestprice").setScale(2).divide(WAN,BigDecimal.ROUND_HALF_UP).setScale(2);
			        		latestamount = rowSet.getBigDecimal("latestamount")==null?BigDecimal.ZERO:rowSet.getBigDecimal("latestamount").setScale(2).divide(WAN,BigDecimal.ROUND_HALF_UP).setScale(2);
			        		
		        			row = this.tblMain.addRow();
		        			row.getCell("projectlongnumber").setValue(getValue(rowSet.getString("ftype")).concat(getValue(rowSet.getString("fproducttypeid"))).concat(getValue(rowSet.getString("fnewarearange"))));
		        			row.getCell("productConstitute").setValue(type);
			        		row.getCell("typename").setValue(producttype);
			        		row.getCell("areaRange").setValue(arearange);
			        		row.getCell("latestarea").setValue(latestarea);
			        		row.getCell("latestcount").setValue(latestploidy);
			        		row.getCell("latestprice").setValue(latestprice);
			        		row.getCell("latestamount").setValue(latestamount);
			        		row.getCell("firstarea").setValue(area);
			        		row.getCell("firstcount").setValue(ploidy);
			        		row.getCell("firstprice").setValue(price);
			        		row.getCell("firstamount").setValue(amount);
			        		
			        	}
			        	tblMain.setRowCount(index);
			        }
			        
			        sqlBuilder.clear();
			        sqlBuilder.appendSql("/*dialect*/select ftype,fproducttypeid,fnewarearange,yearbefore,sum(area) as area,count(*) as fploidy,round(avg(FBuildPrice),2) as fprice,round(sum(fsellamount),2) as famount \n")
			        		  .appendSql("from( select t.*,p.fid as fnewarearange from( \n")
			        		  .appendSql("select project.fid as projectid,type.fid as fproducttypeid,property.fnumber ftype, \n")
			        		  .appendSql("case signman.fsellType when 'PlanningSell' then signman.fstrdPlanBuildingArea when 'PreSell' then signman.fbulidingArea else signman.fstrdActualBuildingArea end ")
			        		  .appendSql("as area,room.FBuildPrice,signman.fsellamount, \n")
			        		  .appendSql("case when signman.fbizdate<trunc(sysdate ,'YEAR') then '0' else '1' end as yearbefore \n")
			        		  .appendSql("from T_SHE_Room room  \n")
			        		  .appendSql("left join T_SHE_BuildingProperty property on property.fid = room.fbuildingpropertyid \n")
			        		  .appendSql("left join T_SHE_Building build on build.fid=room.FBuildingID  \n")
			        		  .appendSql("left join T_FDC_ProductType type on type.fid=build.fproducttypeid  \n")
			        		  .appendSql("left join T_SHE_SellProject project on build.FSellProjectID=project.fid \n")
			        		  .appendSql("left join T_SHE_SignManage signman on signman.froomid=room.fid \n")
			        		  .appendSql("where room.fsellstate='Sign' and signman.fbizState in('SignApple','SignAudit') \n")
			        		  .appendSql("and project.fid='").appendSql(projectId).appendSql("') t \n")
			        		  .appendSql("left outer join t_mar_areaset p on t.area > p.fminarea and t.area<= p.fmaxarea) m \n")
			        		  .appendSql("group by ftype,fproducttypeid,fnewarearange,yearbefore \n")
			        		  .appendSql("order by ftype,fproducttypeid,fnewarearange");
			        rowSet = sqlBuilder.executeQuery();
			        if(rowSet != null){
			        	String type,producttype,arearange,tempid,yearbefore;
			        	BigDecimal price = BigDecimal.ZERO;
			        	BigDecimal amount = BigDecimal.ZERO;
			        	BigDecimal area = BigDecimal.ZERO;
			        	int ploidy = 0;
			        	while(rowSet.next()){
			        		type = getValue(rowSet.getString("ftype"));
			        		if(type.equals("001")){
			        			type = "1house";
			        		}else if(type.equals("002")){
			        			type = "2business";
			        		}else if(type.equals("006")){
			        			type = "2publicBuild";
			        		}else{
			        			type = "3parking";
			        		}
			        		producttype = getValue(rowSet.getString("fproducttypeid"));
			        		arearange = getValue(rowSet.getString("fnewarearange"));
			        		tempid = type.concat(producttype).concat(arearange);
			        		yearbefore = getValue(rowSet.getString("yearbefore"));
			        		area = rowSet.getBigDecimal("area").setScale(2);
			        		ploidy = rowSet.getInt("fploidy");
			        		price = rowSet.getBigDecimal("fprice")==null?BigDecimal.ZERO:rowSet.getBigDecimal("fprice").setScale(2).divide(WAN,BigDecimal.ROUND_HALF_UP).setScale(2);
			        		amount = rowSet.getBigDecimal("famount")==null?BigDecimal.ZERO:rowSet.getBigDecimal("famount").setScale(2).divide(WAN,BigDecimal.ROUND_HALF_UP).setScale(2);
			        		for(int i=0;i<this.tblMain.getRowCount();i++){
			        			IRow currentRow = this.tblMain.getRow(i);
			        			if(tempid.equals(currentRow.getCell("projectlongnumber").getValue().toString())){
			        				if("0".equals(yearbefore)){
			        					currentRow.getCell("beforeyeararea").setValue(area);
			        					currentRow.getCell("beforeyearcount").setValue(String.valueOf(ploidy));
			        					if(area.compareTo(BigDecimal.ZERO)!=0){
			        						currentRow.getCell("beforeyearprice").setValue(amount.divide(area,BigDecimal.ROUND_HALF_UP).setScale(2));
			        					}
			        					currentRow.getCell("beforeyearamount").setValue(amount);
			        				}else{
			        					currentRow.getCell("yeararea").setValue(area);
			        					currentRow.getCell("yearcount").setValue(String.valueOf(ploidy));
			        					//currentRow.getCell("yearprice").setValue(price);
			        					if(area.compareTo(BigDecimal.ZERO)!=0){
			        						currentRow.getCell("yearprice").setValue(amount.divide(area,BigDecimal.ROUND_HALF_UP).setScale(2));
			        					}
			        					currentRow.getCell("yearamount").setValue(amount);
			        				}
			        				currentRow.getCell("hadsellarea").setValue(addToTotal(currentRow.getCell("beforeyeararea").getValue(),currentRow.getCell("yeararea").getValue()));
		        					currentRow.getCell("hadsellcount").setValue(String.valueOf((addToTotal(currentRow.getCell("beforeyearcount").getValue(),currentRow.getCell("yearcount").getValue())).intValue()));
		        					currentRow.getCell("hadsellprice").setValue(
		        							addToTotal(currentRow.getCell("beforeyearamount").getValue(),currentRow.getCell("yearamount").getValue())
		        							.divide(
		        							addToTotal(currentRow.getCell("beforeyeararea").getValue(),currentRow.getCell("yeararea").getValue()),BigDecimal.ROUND_HALF_UP).setScale(2));
		        					currentRow.getCell("hadsellamount").setValue(addToTotal(currentRow.getCell("beforeyearamount").getValue(),currentRow.getCell("yearamount").getValue()));
			        				break;
			        			}
			        		}
			        	}
			        }
			        
			        sqlBuilder.clear();
			        sqlBuilder.appendSql("/*dialect*/select ftype,fproducttypeid,fnewarearange,sum(area) as area,count(*) as fploidy,round(avg(FBuildPrice),2) as fprice,round(sum(amount*(cfmanageragio/100)),2) as famount,hadPrice from( \n")
			        		  .appendSql("select t.*,p.fid as fnewarearange from( \n")
			        		  .appendSql("select project.fid as projectid,type.fid fproducttypeid,property.fnumber ftype,cfmanageragio, \n")
			        		  .appendSql("case when FIsActualAreaAudited='1' then room.factualBuildingArea \n")
			        		  .appendSql("else case when fispre='1' then room.fBuildingArea \n")
			        		  .appendSql("else case when FIsPlan='1' then room.fplanBuildingArea \n")
			        		  .appendSql("else room.fplanBuildingArea end end end as area,room.FBuildPrice,room.fstandardTotalAmount as amount, \n")
			        		  .appendSql("case when room.FBuildPrice is null then '0' else '1' end as hadPrice \n")
			        		  .appendSql("from T_SHE_Room room \n")
			        		  .appendSql("left join T_SHE_BuildingProperty property on property.fid = room.fbuildingpropertyid \n")
			        		  .appendSql("left join T_SHE_Building build on build.fid=room.FBuildingID \n")
			        		  .appendSql("left join T_FDC_ProductType type on type.fid=build.fproducttypeid \n")
			        		  .appendSql("left join T_SHE_SellProject project on build.FSellProjectID=project.fid \n")
			        		  .appendSql("where build.fisgetcertificated=1 \n")
			        		  .appendSql("and project.fid='").appendSql(projectId).appendSql("') t ")
			        		  .appendSql("left outer join t_mar_areaset p on t.area > p.fminarea and t.area<= p.fmaxarea) m \n")
			        		  .appendSql("group by ftype,fproducttypeid,fnewarearange,hadPrice \n")
			        		  .appendSql("order by ftype,fproducttypeid,fnewarearange");
			        rowSet = sqlBuilder.executeQuery();
			        if(rowSet != null){
			        	String type,producttype,arearange,tempid,hadprice;
			        	BigDecimal price = BigDecimal.ZERO;
			        	BigDecimal amount = BigDecimal.ZERO;
			        	BigDecimal area = BigDecimal.ZERO;
			        	int ploidy = 0;
			        	while(rowSet.next()){
			        		type = getValue(rowSet.getString("ftype"));
			        		if(type.equals("001")){
			        			type = "1house";
			        		}else if(type.equals("002")){
			        			type = "2business";
			        		}else if(type.equals("006")){
			        			type = "2publicBuild";
			        		}else{
			        			type = "3parking";
			        		}
			        		producttype = getValue(rowSet.getString("fproducttypeid"));
			        		arearange = getValue(rowSet.getString("fnewarearange"));
			        		tempid = type.concat(producttype).concat(arearange);
			        		hadprice = getValueDefault(rowSet.getString("hadPrice"), "0");
			        		area = rowSet.getBigDecimal("area").setScale(2);
			        		ploidy = rowSet.getInt("fploidy");
			        		for(int i=0;i<this.tblMain.getRowCount();i++){
			        			IRow currentRow = this.tblMain.getRow(i);
			        			if(tempid.equals(currentRow.getCell("projectlongnumber").getValue().toString())){
			        				if("0".equals(hadprice)){ //针对已取预证未定价的情况，均价取最新测算货值均价
					        			price = currentRow.getCell("latestprice").getValue() == null?BigDecimal.ZERO:new BigDecimal(currentRow.getCell("latestprice").getValue().toString()).setScale(2);
					        			currentRow.getCell("nopricearea").setValue(area);
			        					currentRow.getCell("nopricecount").setValue(String.valueOf(ploidy));
			        					currentRow.getCell("nopriceprice").setValue(price);
			        					currentRow.getCell("nopriceamount").setValue(price.multiply(area).setScale(2,BigDecimal.ROUND_HALF_UP));
			        				}else{
					        			//price = rowSet.getBigDecimal("fprice")==null?BigDecimal.ZERO:rowSet.getBigDecimal("fprice").setScale(2).divide(WAN,BigDecimal.ROUND_HALF_UP).setScale(2);
					        			amount = rowSet.getBigDecimal("famount")==null?BigDecimal.ZERO:rowSet.getBigDecimal("famount").setScale(2).divide(WAN,BigDecimal.ROUND_HALF_UP).setScale(2);
					        			currentRow.getCell("hadpricearea").setValue(area);
			        					currentRow.getCell("hadpricecount").setValue(String.valueOf(ploidy));
			        					//currentRow.getCell("hadpriceprice").setValue(price);
			        					if(area.compareTo(BigDecimal.ZERO) != 0){
			        						currentRow.getCell("hadpriceprice").setValue(amount.divide(area,BigDecimal.ROUND_HALF_UP).setScale(2));
			        					}
			        					currentRow.getCell("hadpriceamount").setValue(amount);
			        				}
			        				break;
			        			}
			        		}
			        	}
			        }
			        
			        sqlBuilder.clear();
			        sqlBuilder.appendSql("/*dialect*/select t.cfproductcompiste,t.cfproducttype,t.cfareaarange,t.region,sum(t.area) as area,sum(t.ploidy) as ploidy,sum(t.famount) as famount from( \n")
			        		  .appendSql("select case b.cfproductcompiste when N'住宅' then '1house' when N'公共配套建筑' then '2publicBuild' when N'车位' then '3parking' else '2business' end as cfproductcompiste, \n")
			        		  .appendSql("d.fid as cfproducttype,c.fid as cfareaarange,b.cfarea area,b.cfploidy ploidy,b.cftotalamount as famount, \n")
			        		  .appendSql("case to_char(cfyzrq,'yyyy') when to_char(sysdate,'yyyy') then \n")
			        		  .appendSql("case when to_char(cfyzrq,'mm') >='01' and to_char(cfyzrq,'mm') <='03' then to_char(cfyzrq,'yyyy') || '年1季度' \n")
			        		  .appendSql("else case when to_char(cfyzrq,'mm') >='04' and to_char(cfyzrq,'mm') <='06' then to_char(cfyzrq,'yyyy') || '年2季度' \n")
			        		  .appendSql("else case when to_char(cfyzrq,'mm') >='07' and to_char(cfyzrq,'mm') <='09' then to_char(cfyzrq,'yyyy') || '年3季度' \n")
			        		  .appendSql("else case when to_char(cfyzrq,'mm') >='10' and to_char(cfyzrq,'mm') <='12' then to_char(cfyzrq,'yyyy') || '年4季度' end end end end \n")
			        		  .appendSql("else case when to_char(cfyzrq,'mm') >='01' and to_char(cfyzrq,'mm') <='06' then to_char(cfyzrq,'yyyy') || '年上半年' \n")
			        		  .appendSql("else case when to_char(cfyzrq,'mm') >='07' and to_char(cfyzrq,'mm') <='12' then to_char(cfyzrq,'yyyy') || '年下半年' end end end as region \n")
			        		  .appendSql("from ct_mar_sellsupplyplan a,ct_mar_sellsupplyplanentry b  \n")
			        		  .appendSql("left outer join t_mar_areaset c on c.fname_l2 = b.cfareaarange  \n")
			        		  .appendSql("left outer join T_FDC_ProductType d on d.fname_l2 = b.cfproducttype  \n")
			        		  .appendSql("where b.fhadacquired = 0 and a.fid = b.fparentid and b.cfyzrq>sysdate \n")
			        		  .appendSql("and a.fsellprojectid='").appendSql(projectId).appendSql("' ) t \n")
			        		  .appendSql("group by t.cfproductcompiste,t.cfproducttype,t.cfareaarange,t.region \n")
			        		  .appendSql("order by t.cfproductcompiste,t.cfproducttype,t.cfareaarange,t.region");
			        rowSet = sqlBuilder.executeQuery();
			        if(rowSet != null){
			        	String type,producttype,arearange,tempid,region,temp="";
			        	BigDecimal price = BigDecimal.ZERO;
			        	BigDecimal amount = BigDecimal.ZERO;
			        	BigDecimal area = BigDecimal.ZERO;
			        	BigDecimal totalamount = BigDecimal.ZERO;
			        	BigDecimal totalarea = BigDecimal.ZERO;
			        	int ploidy=0,totalPloidy=0;
			        	while(rowSet.next()){
			        		type = getValue(rowSet.getString("cfproductcompiste"));
			        		producttype = getValue(rowSet.getString("cfproducttype"));
			        		arearange = getValue(rowSet.getString("cfareaarange"));
			        		tempid = type.concat(producttype).concat(arearange);
			        		region = getValue(rowSet.getString("region"));
			        		area = rowSet.getBigDecimal("area").setScale(2);
			        		ploidy = rowSet.getInt("ploidy");
			        		amount = rowSet.getBigDecimal("famount").setScale(2);
			        		for(int i=0;i<this.tblMain.getRowCount();i++){
			        			IRow currentRow = this.tblMain.getRow(i);
			        			if(tempid.equals(currentRow.getCell("projectlongnumber").getValue().toString())){
			        				if(!temp.equals(tempid)){
			        					totalamount = BigDecimal.ZERO;
				        				totalarea = BigDecimal.ZERO;
				        				totalPloidy = 0;
			        					totalamount = totalamount.add(amount);
				        				totalarea = totalarea.add(area);
				        				totalPloidy += ploidy;
			        				}else{
			        					totalamount = totalamount.add(amount);
				        				totalarea = totalarea.add(area);
				        				totalPloidy += ploidy;
			        				}
			        				temp = tempid;
				        			price = amount.divide(area,BigDecimal.ROUND_HALF_UP).setScale(2).divide(WAN,BigDecimal.ROUND_HALF_UP).setScale(2);
				        			currentRow.getCell("temp"+region+"01").setValue(area);
		        					currentRow.getCell("temp"+region+"02").setValue(String.valueOf(ploidy));
		        					currentRow.getCell("temp"+region+"03").setValue(price);
		        					currentRow.getCell("temp"+region+"04").setValue(amount.divide(WAN,BigDecimal.ROUND_HALF_UP));
			        				
		        					currentRow.getCell("totalsy01").setValue(totalarea);
		        					currentRow.getCell("totalsy02").setValue(String.valueOf(totalPloidy));
		        					currentRow.getCell("totalsy03").setValue(totalamount.divide(totalarea,BigDecimal.ROUND_HALF_UP).setScale(2).divide(WAN,BigDecimal.ROUND_HALF_UP).setScale(2));
		        					currentRow.getCell("totalsy04").setValue(totalamount.divide(WAN,BigDecimal.ROUND_HALF_UP));
		        					break;
			        			}
			        		}
			        	}
			        }
			        
			        //动态总货值与最新版测算货值
			        for(int i=0;i<this.tblMain.getRowCount();i++){
	        			IRow currentRow = this.tblMain.getRow(i);
	        			//最新测算货值金额
	        			BigDecimal latestamount = currentRow.getCell("latestamount").getValue()==null?BigDecimal.ZERO:(BigDecimal)currentRow.getCell("latestamount").getValue();
	        			//最新测算货值面积 
	        			BigDecimal latestarea = currentRow.getCell("latestarea").getValue()==null?BigDecimal.ZERO:new BigDecimal(currentRow.getCell("latestarea").getValue().toString());
	        			//已售部分合计面积
	        			BigDecimal hadsellarea = currentRow.getCell("hadsellarea").getValue()==null?BigDecimal.ZERO:(BigDecimal)currentRow.getCell("hadsellarea").getValue();
	        			//已售部分合计金额
	        			BigDecimal hadsellamount = currentRow.getCell("hadsellamount").getValue()==null?BigDecimal.ZERO:(BigDecimal)currentRow.getCell("hadsellamount").getValue();
	        			//已售部分合计套数
	        			int hadsellcount = currentRow.getCell("hadsellcount").getValue()==null?0:Integer.parseInt(currentRow.getCell("hadsellcount").getValue().toString());
	        			//已取预证已定价面积
	        			BigDecimal hadpricearea = currentRow.getCell("hadpricearea").getValue()==null?BigDecimal.ZERO:(BigDecimal)currentRow.getCell("hadpricearea").getValue();
	        			//已取预证已定价金额
	        			BigDecimal hadpriceamount = currentRow.getCell("hadpriceamount").getValue()==null?BigDecimal.ZERO:(BigDecimal)currentRow.getCell("hadpriceamount").getValue();
	        			//已取预证已定价套数
	        			int hadpricecount = currentRow.getCell("hadpricecount").getValue()==null?0:Integer.parseInt(currentRow.getCell("hadpricecount").getValue().toString());
	        			//已取预证未定价面积
	        			BigDecimal nopricearea = currentRow.getCell("nopricearea").getValue()==null?BigDecimal.ZERO:(BigDecimal)currentRow.getCell("nopricearea").getValue();
	        			//已取预证未定价金额
	        			BigDecimal nopriceamount = currentRow.getCell("nopriceamount").getValue()==null?BigDecimal.ZERO:(BigDecimal)currentRow.getCell("nopriceamount").getValue();
	        			//已取预证未定价套数
	        			int nopricecount = currentRow.getCell("nopricecount").getValue()==null?0:Integer.parseInt(currentRow.getCell("nopricecount").getValue().toString());
	        			//剩余部分合计面积
	        			BigDecimal totalsy01 = 
	        				currentRow.getCell("totalsy01")==null?BigDecimal.ZERO:currentRow.getCell("totalsy01").getValue()==null?BigDecimal.ZERO:(BigDecimal)currentRow.getCell("totalsy01").getValue();
	        			//剩余部分合计金额
	        			BigDecimal totalsy04 = 
	        				currentRow.getCell("totalsy04")==null?BigDecimal.ZERO:currentRow.getCell("totalsy04").getValue()==null?BigDecimal.ZERO:(BigDecimal)currentRow.getCell("totalsy04").getValue();
	        			//剩余部分合计套数
	        			int totalsy02 = currentRow.getCell("totalsy02")==null?0:currentRow.getCell("totalsy02").getValue()==null?0:Integer.parseInt(currentRow.getCell("totalsy02").getValue().toString());
	        			
	        			currentRow.getCell("totalarea").setValue(hadsellarea.add(hadpricearea).add(nopricearea).add(totalsy01));
	        			currentRow.getCell("totalcount").setValue(String.valueOf(hadsellcount+hadpricecount+nopricecount+totalsy02));
	        			if(hadsellarea.add(hadpricearea).add(nopricearea).add(totalsy01).compareTo(BigDecimal.ZERO) != 0){
	        				currentRow.getCell("totalprice").setValue((hadsellamount.add(hadpriceamount).add(nopriceamount).add(totalsy04)).divide((hadsellarea.add(hadpricearea).add(nopricearea).add(totalsy01)), BigDecimal.ROUND_HALF_UP).setScale(2));
	        			}
	        			currentRow.getCell("totalamount").setValue(hadsellamount.add(hadpriceamount).add(nopriceamount).add(totalsy04));
	        			
	        			if(latestarea.subtract(hadsellarea).subtract(hadpricearea).compareTo(BigDecimal.ZERO) != 0){
	        				currentRow.getCell("syhzjj").setValue((latestamount.subtract(hadsellamount).subtract(hadpriceamount)).divide((latestarea.subtract(hadsellarea).subtract(hadpricearea)), BigDecimal.ROUND_HALF_UP).setScale(2));
	        			}
			        }
			        
			        
    			}
    		}
    		
    		getRowSum(tblMain);
    		
        } catch (Exception e) {
			e.printStackTrace();
		} 
	}


	private Object[][] addRptColumn(RptTableHeader header, String level1,String level2) {
		Object[][] array = header.getLabels();
		Object[][] labels = new Object[3][array[0].length+4];
		for(int i=0;i<3;i++){
			for(int j=0;j<labels[i].length;j++){
				if(j<39){
					labels[i][j] = array[i][j];
				}else if(j>=39 && j<=42){
					if(i==0){
						labels[i][j] = level1;
						//labels[i][j+4] = array[i][j];
					}
					else if(i==1){
						labels[i][j] = level2;
						//labels[i][j+4] = array[i][j];
					}else{
						if(j==39){
							labels[i][j] = "面积[㎡]";
						}
						else if(j==40){
							labels[i][j] = "套数[套]";
						}
						else if(j==41){
							labels[i][j] = "均价[万元]";
						}
						else if(j==42){
							labels[i][j] = "金额[万元]";
						}
						//labels[i][j+4] = array[i][j];
					}
					
				}else{
					labels[i][j] = array[i][j-4];
				}
			}
		}
		return labels;
	}
	
	private BigDecimal addToTotal(Object obj1,Object obj2){
		if(obj1 == null || "".equals(obj1.toString()))
			obj1 = "0";
		if(obj2 == null || "".equals(obj2.toString()))
			obj2 = "0";
		return new BigDecimal(obj1.toString()).add(new BigDecimal(obj2.toString())).setScale(2);
	}
	
	private String getValue(Object obj){
		if(obj == null || "".equals(obj)){
			return "";
		}else{
			return obj.toString();
		}
	}
	
	private String getValueDefault(Object obj,String defaultValue){
		if(obj == null || "".equals(obj)){
			return defaultValue;
		}else{
			return obj.toString();
		}
	}
	
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    public void onLoad() throws Exception {
    	setShowDialogOnLoad(false);
    	super.onLoad();
    	this.setUITitle("实时总货值");
    	this.btnQuickPic.setVisible(false);
    	this.btnHistory.setVisible(false);
    	//btnQuickPic.setIcon(EASResource.getIcon("imgTbtn_startupserver"));
    	//btnHistory.setIcon(EASResource.getIcon("imgTbtn_demandhistorydata"));
    	this.treeProject.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
    	this.actionQuery.setVisible(false);
    }
    /**
     * 项目树
     * */
    protected void treeProject_valueChanged(TreeSelectionEvent e)throws Exception {
		super.treeProject_valueChanged(e);
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeProject.getLastSelectedPathComponent();
		if(treeNode !=null && treeNode.getUserObject() instanceof SellProjectInfo){
			SellProjectInfo project = (SellProjectInfo)treeNode.getUserObject();
			params.setString("projectId", project.getId().toString());
			query();
			if(tblMain.getColumn("projectname")!=null)
				tblMain.getColumn("projectname").setWidth(0);
		}
	}
	
    /**
     * 历史版本
     * */
    public void actionHistory_actionPerformed(ActionEvent e) throws Exception {
		super.actionHistory_actionPerformed(e);
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeProject.getLastSelectedPathComponent();
		if(treeNode !=null && treeNode.getUserObject() instanceof SellProjectInfo){
			SellProjectInfo project = (SellProjectInfo)treeNode.getUserObject();
			UIContext uiContext = new UIContext(this);
			uiContext.put("projectId", project.getId().toString());
			String editUIName = ActivityValueHistoryListUI.class.getName();
			UIFactory.createUIFactory(UIFactoryName.MODEL).create(editUIName, uiContext, null, OprtState.VIEW).show();

		}
	}
    
    /**
     * 形成快照
     * */
	public void actionQuickPic_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuickPic_actionPerformed(e);
		UIContext uiContext = new UIContext(this);
		uiContext.put("ui",this);
		quickName = null;
		quickDes = null;
		String editUIName = ActivityQuickNameUI.class.getName();
		UIFactory.createUIFactory(UIFactoryName.MODEL).create(editUIName, uiContext, null, OprtState.ADDNEW).show();
		if(quickName!=null && !"".equals(quickName)){
			ActivityValueReportInfo info = new ActivityValueReportInfo();
			String projectid =  "";
		    String version = null;
		    boolean flag = false;
			for(int i=0;i<tblMain.getRowCount();i++){
				IRow row = tblMain.getRow(i);
				if(!isTotalRow(row)){
					if(row.getCell("projectid").getValue()!=null){
						projectid = row.getCell("projectid").getValue().toString();
						info.setProjectid(projectid);
					}
					if(row.getCell("projectlongnumber").getValue()!=null)
						info.setProjectlongnumber(row.getCell("projectlongnumber").getValue().toString());
					if(row.getCell("projectname").getValue()!=null)
						info.setProjectname(row.getCell("projectname").getValue().toString());
					if(row.getCell("productConstitute").getValue()!=null)
						info.setProductConstitute(row.getCell("productConstitute").getValue().toString());
					if(row.getCell("typename").getValue()!=null)
						info.setTypename(row.getCell("typename").getValue().toString());
					if(row.getCell("areaRange").getValue()!=null)
						info.setAreaRange(row.getCell("areaRange").getValue().toString());
					
					if(row.getCell("tararea").getValue()!=null)
						info.setTararea((BigDecimal)row.getCell("tararea").getValue());
					if(row.getCell("tarcount").getValue()!=null)
						info.setTarcount((BigDecimal)row.getCell("tarcount").getValue());
					if(row.getCell("tarunitprice").getValue()!=null)
						info.setTarunitprice((BigDecimal)row.getCell("tarunitprice").getValue());
					if(row.getCell("taramount").getValue()!=null)
						info.setTaramount((BigDecimal)row.getCell("taramount").getValue());
					
					if(row.getCell("yeararea").getValue()!=null)
						info.setTararea((BigDecimal)row.getCell("yeararea").getValue());
					if(row.getCell("yearcount").getValue()!=null)
						info.setTarcount((BigDecimal)row.getCell("yearcount").getValue());
					if(row.getCell("yearunitprice").getValue()!=null)
						info.setTarunitprice((BigDecimal)row.getCell("yearunitprice").getValue());
					if(row.getCell("yearamount").getValue()!=null)
						info.setTaramount((BigDecimal)row.getCell("yearamount").getValue());
					
					
					if(row.getCell("zjarea").getValue()!=null)
						info.setZjarea((BigDecimal)row.getCell("zjarea").getValue());
					if(row.getCell("zjcount").getValue()!=null)
						info.setZjcount((BigDecimal)row.getCell("zjcount").getValue());
					if(row.getCell("zjunitprice").getValue()!=null)
						info.setZjunitprice((BigDecimal)row.getCell("zjunitprice").getValue());
					if(row.getCell("zjamount").getValue()!=null)
						info.setZjamount((BigDecimal)row.getCell("zjamount").getValue());
					
					if(row.getCell("wqzarea").getValue()!=null)
						info.setWqzarea((BigDecimal)row.getCell("wqzarea").getValue());
					if(row.getCell("wqzcount").getValue()!=null)
						info.setWqzcount((BigDecimal)row.getCell("wqzcount").getValue());
					if(row.getCell("wqzunitprice").getValue()!=null)
						info.setWqzunitprice((BigDecimal)row.getCell("wqzunitprice").getValue());
					if(row.getCell("wqzamount").getValue()!=null)
						info.setWqzamount((BigDecimal)row.getCell("wqzamount").getValue());
					
					if(row.getCell("wqzydjarea").getValue()!=null)
						info.setWqzarea((BigDecimal)row.getCell("wqzydjarea").getValue());
					if(row.getCell("wqzydjcount").getValue()!=null)
						info.setWqzydjcount((BigDecimal)row.getCell("wqzydjcount").getValue());
					if(row.getCell("wqzydjunitprice").getValue()!=null)
						info.setWqzydjunitprice((BigDecimal)row.getCell("wqzydjunitprice").getValue());
					if(row.getCell("wqzydjamount").getValue()!=null)
						info.setWqzydjamount((BigDecimal)row.getCell("wqzydjamount").getValue());
					
					if(row.getCell("yqzydjarea").getValue()!=null)
						info.setWqzarea((BigDecimal)row.getCell("yqzydjarea").getValue());
					if(row.getCell("yqzydjcount").getValue()!=null)
						info.setYqzydjcount((BigDecimal)row.getCell("yqzydjcount").getValue());
					if(row.getCell("yqzydjunitprice").getValue()!=null)
						info.setYqzydjunitprice((BigDecimal)row.getCell("yqzydjunitprice").getValue());
					if(row.getCell("yqzydjamount").getValue()!=null)
						info.setYqzydjamount((BigDecimal)row.getCell("yqzydjamount").getValue());
					
					if(row.getCell("yqzwdjarea").getValue()!=null)
						info.setYqzwdjarea((BigDecimal)row.getCell("yqzwdjarea").getValue());
					if(row.getCell("yqzwdjcount").getValue()!=null)
						info.setYqzwdjcount((BigDecimal)row.getCell("yqzwdjcount").getValue());
					if(row.getCell("yqzwdjunitprice").getValue()!=null)
						info.setYqzwdjunitprice((BigDecimal)row.getCell("yqzwdjunitprice").getValue());
					if(row.getCell("yqzwdjamount").getValue()!=null)
						info.setYqzwdjamount((BigDecimal)row.getCell("yqzwdjamount").getValue());
					
					if(row.getCell("ysarea").getValue()!=null)
						info.setYsarea((BigDecimal)row.getCell("ysarea").getValue());
					if(row.getCell("yscount").getValue()!=null)
						info.setYscount((BigDecimal)row.getCell("yscount").getValue());
					if(row.getCell("ysunitprice").getValue()!=null)
						info.setYsunitprice((BigDecimal)row.getCell("ysunitprice").getValue());
					if(row.getCell("ysamount").getValue()!=null)
						info.setYsamount((BigDecimal)row.getCell("ysamount").getValue());
					if(version==null){
						version = getVersion(projectid);
						if("".equals(version)||version==null)
							version = "V1.0";
						else {
							int index = version.indexOf(".");
							int v = Integer.parseInt(version.substring(index+1))+1;
							version = version.substring(0, index+1)+v;
						}
					}
					info.setDescription(version);
					info.setSeq(i);
					info.setSimpleName(""+i);
					info.setQuickName(quickName);
					info.setQuickDes(quickDes);
					BOSUuid id = BOSUuid.create(info.getBOSType());
					info.setId(id);
					info.setName(id.toString());
					info.setNumber(id.toString());
					ActivityValueReportFactory.getRemoteInstance().addnew(info);
					flag = true;
				}
			}
			if(flag){
				MsgBox.showInfo("操作成功！");
			}
		}
	}

	/**
	 * 获取版本号
	 * */
	public String getVersion(String projectid) throws Exception{
		String version = "";
		String sql = "select fdescription_l2 from T_MAR_ActivityValueReport where fprojectid='"+projectid+"' order by fdescription_l2 desc";
		FDCSQLBuilder db = new FDCSQLBuilder();
		db.appendSql(sql);
		IRowSet rowset = db.executeQuery();
		while(rowset.next()){
			version = rowset.getString("fdescription_l2");
			break;
		}
		return version;
	}

	/**
     * output actionChart_actionPerformed
     */
    public void actionChart_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionChart_actionPerformed(e);
    }

}