package com.kingdee.eas.custom.fdcrptcz;

import java.math.BigDecimal;

import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.eas.fdc.basedata.FDCHelper;

//�б���
public class checkRptUIWCZBL extends checkRptUI2{


	public checkRptUIWCZBL() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	KDTable tblCheckForTotal = new KDTable();
	
	@Override
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		
tblCheck.getStyleAttributes().setHided(true);
		
		
		
		IColumn col1 = tblCheckForTotal.addColumn();
		col1.setKey("seq");
		col1.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		IColumn col2 = tblCheckForTotal.addColumn();
		col2.setKey("name");
		col2.setWidth(200);
		col2.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		IColumn col3 = tblCheckForTotal.addColumn();
		col3.setKey("value1");
		col3.setWidth(300);
		col3.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		IColumn col4 = tblCheckForTotal.addColumn();
		col4.setKey("value2");
		col4.setWidth(200);
		col4.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		IColumn col5 = tblCheckForTotal.addColumn();
		col5.setKey("value");
		col5.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		IRow headRow = tblCheckForTotal.addHeadRow();
		headRow.getCell("seq").setValue("���");
		headRow.getCell("name").setValue("����");
		headRow.getCell("value1").setValue("�ƶ�Ҫ���б���ʵ�ʲ����б�ĺ�ͬ����");
		headRow.getCell("value2").setValue("�ƶ�Ҫ���б�ĺ�ͬ����");
		headRow.getCell("value").setValue("����");
		
		tblCheckForTotal.checkParsed();
		
		kDSplitPane2.add(tblCheckForTotal, "bottom");
		tblCheckForTotal.getStyleAttributes().setLocked(true);
	}
	
	
	@Override
	protected void treeSelectChange() throws Exception {
		// TODO Auto-generated method stub
		super.treeSelectChange();
		tblCheckForTotal.removeRows();
		if(tblCheckForTotal.getColumn("seq") == null){
			  return;	
			}
		rowCount = tblCheck.getRowCount();
		step = 0;
		for(int i=0; i<5; i++){
			IRow row = tblCheckForTotal.addRow();
			switch (i){
				case 0:
					row.getCell("seq").setValue("һ");
					row.getCell("name").setValue("ǰ�ڹ��̷�");
					setValue(row,"01.02"); 
					break;
				case 1:
					row.getCell("seq").setValue("��");
					row.getCell("name").setValue("�������̷�"); 
					setValue(row,"01.03"); 
					break;
				case 2:
					row.getCell("seq").setValue("��");
					row.getCell("name").setValue("������ʩ��"); 
					setValue(row,"01.04");
					break;
				case 3:
					row.getCell("seq").setValue("��");
					row.getCell("name").setValue("����������ʩ��"); 
					setValue(row,"01.05");
					break;
				case 4:
					row.getCell("seq").setValue("��");
					row.getCell("name").setValue("�������С��");
					
					//ȡ��һ�л���ֵ
					if(tblCheck.getRow(0) != null ){
						BigDecimal value1 = FDCHelper.toBigDecimal(tblCheck.getRow(0).getCell("column18").getValue()); //�ƶ�Ҫ���б���ʵ�ʲ����б�ĺ�ͬ����
						BigDecimal value2 = FDCHelper.toBigDecimal(tblCheck.getRow(0).getCell("column19").getValue()); // �ƶ�Ҫ���б�ĺ�ͬ����
						
						row.getCell("value1").setValue(value1.intValue());
						row.getCell("value2").setValue(value2.intValue());
						
						if(tblCheck.getRow(0).getCell("column19").getValue() != null	){
							if(value2.equals(FDCHelper.ZERO) || value2.toString().equals("0.00") || value2.toString().equals("0")){
								row.getCell("value").setValue("0.00%");
							}else{
								row.getCell("value").setValue(value1.divide(value2,2,BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED) +"%");
							}
						}else{
							row.getCell("value").setValue("0.00%");
						}
					}
					break;
			}
		}
		
	}
	int step =0; 
	int rowCount =0;
	public void setValue(IRow row, String longNum){
		for( ; step<rowCount; step ++){
			if(tblCheck.getRow(step).getCell("longNumber").getValue() != null 
					&& longNum.equals(tblCheck.getRow(step).getCell("longNumber").getValue().toString())
			){
				BigDecimal value1 = FDCHelper.toBigDecimal(tblCheck.getRow(step).getCell("column18").getValue()); //�ƶ�Ҫ���б���ʵ�ʲ����б�ĺ�ͬ����
				BigDecimal value2 = FDCHelper.toBigDecimal(tblCheck.getRow(step).getCell("column19").getValue()); // �ƶ�Ҫ���б�ĺ�ͬ����
				
				row.getCell("value1").setValue(value1.intValue());
				row.getCell("value2").setValue(value2.intValue());
				
				if(tblCheck.getRow(step).getCell("column19").getValue() != null	){
					if(value2.equals(FDCHelper.ZERO) || value2.toString().equals("0.00") || value2.toString().equals("0")){
						row.getCell("value").setValue("0.00%");
					}else{
						row.getCell("value").setValue(value1.divide(value2,2,BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED) +"%");
					}
				}else{
					row.getCell("value").setValue("0.00%");
				}
				
				break;
			}else{
				row.getCell("value1").setValue(0);
				row.getCell("value2").setValue(0);
				row.getCell("value").setValue("0.00%");
			}
		}
	}


}
