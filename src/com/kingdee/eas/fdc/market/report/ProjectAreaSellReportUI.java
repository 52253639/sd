/**
 * output package name
 */
package com.kingdee.eas.fdc.market.report;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ProjectAreaSellReportUI extends AbstractProjectAreaSellReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectAreaSellReportUI.class);
    
	private TreeModel tree;
    
    private HashMap map;
    
    public ProjectAreaSellReportUI() throws Exception
    {
        super();
        kDTable1.checkParsed();
        kDTable1.getDataRequestManager().addDataRequestListener(this);
        kDTable1.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(kDTable1);
        map = new HashMap();
    }

	protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new ProjectAreaSellReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return null;
	}

	protected KDTable getTableForPrintSetting() {
		return kDTable1;
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		initTable();
		initTree();
		tree=(TreeModel) params.getObject("tree");
		kDTable1.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
	}
	
	protected void initTable(){
		kDTable1.getStyleAttributes().setLocked(true);
		kDTable1.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		kDTable1.getDataRequestManager().addDataRequestListener(this);
		kDTable1.getDataRequestManager().setDataRequestMode(1);
        this.kDTable1.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
        enableExportExcel(kDTable1);
        //设置垂直滚动条
        getTableForPrintSetting().setScrollStateVertical(KDTStyleConstants.SCROLL_STATE_SHOW);
        //设置水平滚动条
        getTableForPrintSetting().setScrollStateHorizon(KDTStyleConstants.SCROLL_STATE_SHOW);
        
		String[] fields=new String[kDTable1.getColumnCount()];
		for(int i=0;i<kDTable1.getColumnCount();i++){
			fields[i]=kDTable1.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(kDTable1,fields);
	}

	protected void initTree() throws Exception{
		if((tree==null&&params.getObject("tree")!=null)||(tree!=null&&params.getObject("tree")==null)||(tree!=null&&params.getObject("tree")!=null&&!tree.equals(params.getObject("tree")))||(tree==null&&params.getObject("tree")==null&&!this.isShowing())){
			if(params.getObject("tree")!=null){
				tree=(TreeModel) params.getObject("tree");
				this.kDTree1.setModel((TreeModel) params.getObject("tree"));
			}else{
				this.kDTree1.setModel(FDCTreeHelper.getSellProjectForSHESellProject(actionOnLoad, MoneySysTypeEnum.SalehouseSys));
			}
		}
	}

	protected void query() {
		int sum_taoshu = 0;
		BigDecimal sum_jianzhu = new BigDecimal("0");
		BigDecimal sum_taonei = new BigDecimal("0");
		BigDecimal sum_qianyue = new BigDecimal("0");
		BigDecimal sum_hetong = new BigDecimal("0");
		BigDecimal sum_fukuan = new BigDecimal("0");
		kDTable1.checkParsed();
		getTableForPrintSetting().removeRows();
		try {
			if(params.getObject("year") != null){
				map.put("year", params.getObject("year"));
			}
			Map mapData = ProjectAreaSellReportFacadeFactory.getRemoteInstance().getDatas(map);
			if(mapData.get("rowAmount")!=null){
				IRowSet rowSet = (IRowSet)mapData.get("rowAmount");
				while(rowSet.next()){
					IRow row = kDTable1.addRow();
					row.getCell("column1").setValue(rowSet.getObject("month")==null?"0":rowSet.getObject("month"));
					row.getCell("column2").setValue(rowSet.getObject("taoshu")==null?"0":rowSet.getObject("taoshu"));
					if(rowSet.getObject("jianzhu") != null){
						BigDecimal jine = new BigDecimal(rowSet.getObject("jianzhu").toString());
						BigDecimal bd2 = jine.setScale(2,BigDecimal.ROUND_HALF_UP);
						row.getCell("column3").setValue(bd2);
					}
					else{
						row.getCell("column3").setValue("0");
					}
					if(rowSet.getObject("taonei") != null){
						BigDecimal jine = new BigDecimal(rowSet.getObject("taonei").toString());
						BigDecimal bd2 = jine.setScale(2,BigDecimal.ROUND_HALF_UP);
						row.getCell("column4").setValue(bd2);
					}
					else{
						row.getCell("column4").setValue("0");
					}
					if(rowSet.getObject("jine") != null){
						BigDecimal jine = new BigDecimal(rowSet.getObject("jine").toString());
						BigDecimal bd2 = jine.setScale(2,BigDecimal.ROUND_HALF_UP);
						row.getCell("column5").setValue(bd2);
					}
					else{
						row.getCell("column5").setValue("0");
					}
					if(rowSet.getObject("jianjun") != null){
						BigDecimal jine = new BigDecimal(rowSet.getObject("jianjun").toString());
						BigDecimal bd2 = jine.setScale(2,BigDecimal.ROUND_HALF_UP);
						row.getCell("column6").setValue(bd2);
					}
					else{
						row.getCell("column6").setValue("0");
					}
					if(rowSet.getObject("taojun") != null){
						BigDecimal jine = new BigDecimal(rowSet.getObject("taojun").toString());
						BigDecimal bd2 = jine.setScale(2,BigDecimal.ROUND_HALF_UP);
						row.getCell("column7").setValue(bd2);
					}
					else{
						row.getCell("column7").setValue("0");
					}
					row.getCell("column8").setValue("0");
					row.getCell("column9").setValue("0");
				}
			}
			//合同信息
			if(mapData.get("rowContract")!=null){
				IRowSet rowSet = (IRowSet)mapData.get("rowContract");
				while(rowSet.next()){
					int rowNum=0;
					if(rowSet.getObject("time1").toString().length()==5){
						rowNum = Integer.parseInt(rowSet.getObject("time1").toString().substring(4,5));
					}else{
						rowNum = Integer.parseInt(rowSet.getObject("time1").toString().substring(4,6));
					}
					IRow row = kDTable1.getRow(rowNum-1);
					row.getCell("column8").setValue(rowSet.getObject("amount")==null?"0":rowSet.getObject("amount"));
					row.getCell("column8").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
				}
			}
			if(mapData.get("payMent")!=null){
				IRowSet rowSet = (IRowSet)mapData.get("payMent");
				while(rowSet.next()){
					int rowNum=0;
					if(rowSet.getObject("time2").toString().length()==5){
						rowNum = Integer.parseInt(rowSet.getObject("time2").toString().substring(4,5));
					}else{
						rowNum = Integer.parseInt(rowSet.getObject("time2").toString().substring(4,6));
					}
					IRow row = kDTable1.getRow(rowNum-1);
					row.getCell("column9").setValue(rowSet.getObject("actpaylocamt")==null?"0":rowSet.getObject("actpaylocamt"));
					row.getCell("column9").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
				}
			}
			
			KDTFootManager fm = new KDTFootManager(kDTable1);
			fm.addFootView();
			kDTable1.setFootManager(fm);
			IRow row = kDTable1.addFootRow(0);//添加一个Foot行
			row.getStyleAttributes().setBackground(new Color(255,255,204));
			for(int i=0;i<kDTable1.getRowCount();i++){
				IRow rows = kDTable1.getRow(i);
				sum_taoshu = sum_taoshu + Integer.parseInt(rows.getCell("column2").getValue()==null?"0":rows.getCell("column2").getValue().toString());
				sum_jianzhu = sum_jianzhu.add(new BigDecimal(rows.getCell("column3").getValue()==null?"0":rows.getCell("column3").getValue().toString()));
				sum_taonei = sum_taonei.add(new BigDecimal(rows.getCell("column4").getValue()==null?"0":rows.getCell("column4").getValue().toString()));
				sum_qianyue = sum_qianyue.add(new BigDecimal(rows.getCell("column5").getValue()==null?"0":rows.getCell("column5").getValue().toString()));
				sum_hetong = sum_hetong.add(new BigDecimal(rows.getCell("column8").getValue()==null?"0":rows.getCell("column8").getValue().toString()));
				sum_fukuan = sum_fukuan.add(new BigDecimal(rows.getCell("column9").getValue()==null?"0":rows.getCell("column9").getValue().toString()));
			}
			row.getCell("column1").setValue("合计");
			row.getCell("column2").setValue(String.valueOf(sum_taoshu));
			row.getCell("column3").setValue(String.valueOf(sum_jianzhu));
			row.getCell("column4").setValue(String.valueOf(sum_taonei));
			row.getCell("column5").setValue(String.valueOf(sum_qianyue));
			row.getCell("column8").setValue(String.valueOf(sum_hetong));
			row.getCell("column9").setValue(String.valueOf(sum_fukuan));
			row.getCell("column2").getStyleAttributes().setNumberFormat("#,##0;-#,##0");
			row.getCell("column3").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			row.getCell("column4").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			row.getCell("column5").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			row.getCell("column8").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			row.getCell("column9").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			row.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			row.getStyleAttributes().setFontColor(java.awt.Color.BLACK);
			row.getCell("column1").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("center"));
			
			for(int i=0;i<kDTable1.getRowCount();i++){
				IRow rows = kDTable1.getRow(i);
				rows.getCell("column1").setValue(map.get("year")+"年"+rows.getCell("column1").getValue()+"月");
				if(rows.getCell("column5").getValue().toString().equals("0") || rows.getCell("column8").getValue().toString().equals("0")){
					rows.getCell("column10").setValue(String.valueOf("0%"));
				}
				else{
					rows.getCell("column10").setValue(new BigDecimal(rows.getCell("column8").getValue().toString()).divide(new BigDecimal(rows.getCell("column5").getValue().toString()),4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP)+"%");
				}
				if(rows.getCell("column5").getValue().toString().equals("0") || rows.getCell("column9").getValue().toString().equals("0")){
					rows.getCell("column11").setValue(String.valueOf("0%"));
				}
				else{
					rows.getCell("column11").setValue(new BigDecimal(rows.getCell("column9").getValue().toString()).divide(new BigDecimal(rows.getCell("column5").getValue().toString()),4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP)+"%");
				}
				if(rows.getCell("column3").getValue().toString().equals("0") || rows.getCell("column8").getValue().toString().equals("0")){
					rows.getCell("column12").setValue(String.valueOf("0"));
				}
				else{
					rows.getCell("column12").setValue(new BigDecimal(rows.getCell("column8").getValue().toString()).divide(new BigDecimal(rows.getCell("column3").getValue().toString()),2, RoundingMode.HALF_UP));
				}
				if(rows.getCell("column4").getValue().toString().equals("0") || rows.getCell("column8").getValue().toString().equals("0")){
					rows.getCell("column13").setValue(String.valueOf("0"));
				}
				else{
					rows.getCell("column13").setValue(new BigDecimal(rows.getCell("column8").getValue().toString()).divide(new BigDecimal(rows.getCell("column4").getValue().toString()),2, RoundingMode.HALF_UP));
				}
				if(rows.getCell("column3").getValue().toString().equals("0") || rows.getCell("column9").getValue().toString().equals("0")){
					rows.getCell("column14").setValue(String.valueOf("0"));
				}
				else{
					rows.getCell("column14").setValue(new BigDecimal(rows.getCell("column9").getValue().toString()).divide(new BigDecimal(rows.getCell("column3").getValue().toString()),2, RoundingMode.HALF_UP));
				}
				if(rows.getCell("column4").getValue().toString().equals("0") || rows.getCell("column9").getValue().toString().equals("0")){
					rows.getCell("column15").setValue(String.valueOf("0"));
				}
				else{
					rows.getCell("column15").setValue(new BigDecimal(rows.getCell("column9").getValue().toString()).divide(new BigDecimal(rows.getCell("column4").getValue().toString()),2, RoundingMode.HALF_UP));
				}
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected void kDTree1_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)kDTree1.getLastSelectedPathComponent();
		if(treeNode!=null){
			Object obj = treeNode.getUserObject();
			if (obj instanceof OrgStructureInfo) {
				map.put("OrgStructureInfo", obj);
				map.put("sellProject", null);
				params.setObject("sellProject", null);
				String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(treeNode, "SellProject").keySet());
				if(allSpIdStr.trim().length()!=0){
					params.setObject("org", allSpIdStr);
					map.put("org", allSpIdStr);
				}else{
					params.setObject("org", null);
					map.put("org", null);
				}
			}else if(obj instanceof SellProjectInfo){
				map.put("sellProject", treeNode.getUserObject());
				params.setObject("sellProject", treeNode.getUserObject());
			}else if(obj instanceof ProductTypeInfo){
				map.put("sellProject", ((DefaultKingdeeTreeNode)treeNode.getParent()).getUserObject());
				params.setObject("sellProject", ((DefaultKingdeeTreeNode)treeNode.getParent()).getUserObject());
			}
			OrgStructureInfo orgStructureInfo = (OrgStructureInfo) map.get("OrgStructureInfo");
			AdminOrgUnitInfo admin = SysContext.getSysContext().getCurrentAdminUnit();
			if(orgStructureInfo != null){
				map.put("longNumber", orgStructureInfo.getLongNumber());
			}
			else{
				map.put("longNumber", admin.getLongNumber());
			}
			query();
		}
	}

	public void tableDataRequest(KDTDataRequestEvent arg0) {
		
	}

}