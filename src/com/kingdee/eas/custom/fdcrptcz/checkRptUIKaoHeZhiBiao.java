package com.kingdee.eas.custom.fdcrptcz;

import java.math.BigDecimal;

import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.insider.integralComputationType;

//考核指标表
public class checkRptUIKaoHeZhiBiao extends checkRptUI2{

	public checkRptUIKaoHeZhiBiao() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		
		tblCheck.getStyleAttributes().setHided(true); 
		
		if(tblCheckForTotal.getColumn("seq") != null){
			  return;	
		}
		
		IColumn col1 = tblCheckForTotal.addColumn();
		col1.setKey("seq");
		col1.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		IColumn col2 = tblCheckForTotal.addColumn();
		col2.setKey("name");
		col2.setWidth(200);
		col2.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		IColumn col3 = tblCheckForTotal.addColumn();
		col3.setKey("value");
		col3.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		IRow headRow = tblCheckForTotal.addHeadRow();
		headRow.getCell("seq").setValue("序号");
		headRow.getCell("name").setValue("考核指标");
		headRow.getCell("value").setValue("统计值");
		
		tblCheckForTotal.checkParsed();
		
		kDSplitPane2.add(tblCheckForTotal, "bottom"); 
		
		tblCheckForTotal.getStyleAttributes().setLocked(true);
		
	}
	KDTable tblCheckForTotal = new KDTable();
	
	@Override
	protected void treeSelectChange() throws Exception {
		// TODO Auto-generated method stub
		super.treeSelectChange(); 
		
		System.out.println("tblCheckRowCount:"+tblCheck.getRowCount());
		
		int rowCount = tblCheck.getRowCount();
		if(tblCheckForTotal.getColumn("seq") == null){
			  return;	
			}
		tblCheckForTotal.removeRows();
		for(int i=0; i<5; i++){
			IRow row = tblCheckForTotal.addRow();
			switch (i){
				case 0:
					row.getCell("seq").setValue("一");
					row.getCell("name").setValue("新增供应商中标率");
					
//					2、	招标新增供应商中标项数：按照【考核表】中‘新增供应商中标项数’字段取数逻辑；
//					3、	已完成采购的招标的合同总项数：按照【考核表】中‘实际采购方式’字段取数逻辑，但做判断条件——实际采购方式为招标的个数；
//					4、	比率：招标新增供应商中标项数/已完成采购的招标的合同总项数；
					
					//取第一行汇总值
					if(tblCheck.getRow(0) != null && tblCheck.getRow(0).getCell("column17").getValue() != null){
						BigDecimal value1 = FDCHelper.toBigDecimal(tblCheck.getRow(0).getCell("winBuildCount").getValue()); //新增供应商中标项数
						BigDecimal value2 = FDCHelper.toBigDecimal(tblCheck.getRow(0).getCell("column17").getValue()); // 已完成采购的招标的合同总项数
						
						if(value2.equals(FDCHelper.ZERO) || value2.toString().equals("0.00") || value2.toString().equals("0")){
							row.getCell("value").setValue("0.00%");
						}else{
							row.getCell("value").setValue(value1.divide(value2,2,BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED)  +"%");
						}
					}
					break;
				case 1:
					row.getCell("seq").setValue("二");
					row.getCell("name").setValue("目标成本单价达成率");
					
//					2、	已完成采购且符合采购计划中目标成本单价的合同项数：按照【考核表】中对应‘目标成本单价达成的项数‘字段取数逻辑；
//					3、	已完成采购的合同总项数：按照【考核表】中‘已完成采购的合同总项数’取数逻辑；
//					4、	比率：已完成采购且符合采购计划中目标成本单价的合同项数/已完成采购的合同总项数
					
					//取第一行汇总值
					if(tblCheck.getRow(0) != null && tblCheck.getRow(0).getCell("totalCon").getValue() != null){
						BigDecimal value1 = FDCHelper.toBigDecimal(tblCheck.getRow(0).getCell("targetCostCount").getValue()); //目标成本单价达成的项数
						BigDecimal value2 = FDCHelper.toBigDecimal(tblCheck.getRow(0).getCell("totalCon").getValue()); // 已完成采购的合同总项数
						
						if(value2.equals(FDCHelper.ZERO) || value2.toString().equals("0.00") || value2.toString().equals("0")){
							row.getCell("value").setValue("0.00%");
						}else{
							row.getCell("value").setValue(value1.divide(value2,2,BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED)  +"%");
						}
					}
					break;
				case 2:
					row.getCell("seq").setValue("三");
					row.getCell("name").setValue("招标率");
					
//					2、	制度要求招标且实际采用招标的合同项数：按照【考核表】中‘制度要求的采购方式’、‘实际采购方式‘取数逻辑，判断条件——’制度要求采购方式‘为“招标”，并且‘实际采购方式’也为“招标”的采购计划个数；
//					3、	制度要求招标的合同项数：按照【考核表】中‘制度要求的采购方式’取数逻辑，判断条件——’制度要求采购方式‘为“招标”的个数；
//					4、	比率：制度要求招标且实际采用招标的合同项数/制度要求招标的合同项数
					//取第一行汇总值
					if(tblCheck.getRow(0) != null && tblCheck.getRow(0).getCell("column19").getValue() != null){
						BigDecimal value1 = FDCHelper.toBigDecimal(tblCheck.getRow(0).getCell("column18").getValue()); //制度要求招标且实际采用招标的合同项数
						BigDecimal value2 = FDCHelper.toBigDecimal(tblCheck.getRow(0).getCell("column19").getValue()); // 制度要求招标的合同项数
						
						if(value2.equals(FDCHelper.ZERO) || value2.toString().equals("0.00") || value2.toString().equals("0")){
							row.getCell("value").setValue("0.00%");
						}else{
							row.getCell("value").setValue(value1.divide(value2,2,BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED)  +"%");
						}
						
					}
					break;
				case 3:
					row.getCell("seq").setValue("四");
					row.getCell("name").setValue("最低价中标率");
					
//					2、	采用最低价中标的合同项数：按照【考核表】中‘采用最低价中标的合同项数’取数逻辑；
//					3、	已完成采购的合同总项数：按照【考核表】中‘已完成采购的合同总项数’取数逻辑；
//					4、	比率：采用最低价中标的合同项数/已完成采购的合同总项数
					//取第一行汇总值
					if(tblCheck.getRow(0) != null && tblCheck.getRow(0).getCell("totalCon").getValue() != null){
						BigDecimal value1 = FDCHelper.toBigDecimal(tblCheck.getRow(0).getCell("minConCount").getValue()); //采用最低价中标的合同项数
						BigDecimal value2 = FDCHelper.toBigDecimal(tblCheck.getRow(0).getCell("totalCon").getValue()); //已完成采购的合同总项数
						if(value2.equals(FDCHelper.ZERO) || value2.toString().equals("0.00") || value2.toString().equals("0")){
							row.getCell("value").setValue("0.00%");
						}else{
							row.getCell("value").setValue(value1.divide(value2,2,BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED)  +"%");
						}
					}
					break;
				case 4:
					row.getCell("seq").setValue("五");
					row.getCell("name").setValue("采购计划达成率");
					
//					2、	按时完成采购的合同项数：按照【考核表】中‘按时完成采购的合同项数’取数逻辑；
//					3、	已完成采购的合同总项数：按照【考核表】中‘已完成采购的合同总项数‘取数逻辑；
//					4、	比率：按时完成采购的合同项数/已完成采购的合同总项数
					//取第一行汇总值
					if(tblCheck.getRow(0) != null && tblCheck.getRow(0).getCell("totalCon").getValue() != null){
						BigDecimal value1 = FDCHelper.toBigDecimal(tblCheck.getRow(0).getCell("timeCount").getValue()); //按时完成采购的合同项数
						BigDecimal value2 = FDCHelper.toBigDecimal(tblCheck.getRow(0).getCell("totalCon").getValue()); //已完成采购的合同总项数
						
						if(value2.equals(FDCHelper.ZERO) || value2.toString().equals("0.00") || value2.toString().equals("0")){
							row.getCell("value").setValue("0.00%");
						}else{
							row.getCell("value").setValue(value1.divide(value2,2,BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED)  +"%");
						}
					}
					break;
			}
		}
		
	}

}
