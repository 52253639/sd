package com.kingdee.eas.custom.fdcrptcz;

import java.math.BigDecimal;

import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.insider.integralComputationType;

//����ָ���
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
		headRow.getCell("seq").setValue("���");
		headRow.getCell("name").setValue("����ָ��");
		headRow.getCell("value").setValue("ͳ��ֵ");
		
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
					row.getCell("seq").setValue("һ");
					row.getCell("name").setValue("������Ӧ���б���");
					
//					2��	�б�������Ӧ���б����������ա����˱��С�������Ӧ���б��������ֶ�ȡ���߼���
//					3��	����ɲɹ����б�ĺ�ͬ�����������ա����˱��С�ʵ�ʲɹ���ʽ���ֶ�ȡ���߼��������ж���������ʵ�ʲɹ���ʽΪ�б�ĸ�����
//					4��	���ʣ��б�������Ӧ���б�����/����ɲɹ����б�ĺ�ͬ��������
					
					//ȡ��һ�л���ֵ
					if(tblCheck.getRow(0) != null && tblCheck.getRow(0).getCell("column17").getValue() != null){
						BigDecimal value1 = FDCHelper.toBigDecimal(tblCheck.getRow(0).getCell("winBuildCount").getValue()); //������Ӧ���б�����
						BigDecimal value2 = FDCHelper.toBigDecimal(tblCheck.getRow(0).getCell("column17").getValue()); // ����ɲɹ����б�ĺ�ͬ������
						
						if(value2.equals(FDCHelper.ZERO) || value2.toString().equals("0.00") || value2.toString().equals("0")){
							row.getCell("value").setValue("0.00%");
						}else{
							row.getCell("value").setValue(value1.divide(value2,2,BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED)  +"%");
						}
					}
					break;
				case 1:
					row.getCell("seq").setValue("��");
					row.getCell("name").setValue("Ŀ��ɱ����۴����");
					
//					2��	����ɲɹ��ҷ��ϲɹ��ƻ���Ŀ��ɱ����۵ĺ�ͬ���������ա����˱��ж�Ӧ��Ŀ��ɱ����۴�ɵ��������ֶ�ȡ���߼���
//					3��	����ɲɹ��ĺ�ͬ�����������ա����˱��С�����ɲɹ��ĺ�ͬ��������ȡ���߼���
//					4��	���ʣ�����ɲɹ��ҷ��ϲɹ��ƻ���Ŀ��ɱ����۵ĺ�ͬ����/����ɲɹ��ĺ�ͬ������
					
					//ȡ��һ�л���ֵ
					if(tblCheck.getRow(0) != null && tblCheck.getRow(0).getCell("totalCon").getValue() != null){
						BigDecimal value1 = FDCHelper.toBigDecimal(tblCheck.getRow(0).getCell("targetCostCount").getValue()); //Ŀ��ɱ����۴�ɵ�����
						BigDecimal value2 = FDCHelper.toBigDecimal(tblCheck.getRow(0).getCell("totalCon").getValue()); // ����ɲɹ��ĺ�ͬ������
						
						if(value2.equals(FDCHelper.ZERO) || value2.toString().equals("0.00") || value2.toString().equals("0")){
							row.getCell("value").setValue("0.00%");
						}else{
							row.getCell("value").setValue(value1.divide(value2,2,BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED)  +"%");
						}
					}
					break;
				case 2:
					row.getCell("seq").setValue("��");
					row.getCell("name").setValue("�б���");
					
//					2��	�ƶ�Ҫ���б���ʵ�ʲ����б�ĺ�ͬ���������ա����˱��С��ƶ�Ҫ��Ĳɹ���ʽ������ʵ�ʲɹ���ʽ��ȡ���߼����ж������������ƶ�Ҫ��ɹ���ʽ��Ϊ���бꡱ�����ҡ�ʵ�ʲɹ���ʽ��ҲΪ���бꡱ�Ĳɹ��ƻ�������
//					3��	�ƶ�Ҫ���б�ĺ�ͬ���������ա����˱��С��ƶ�Ҫ��Ĳɹ���ʽ��ȡ���߼����ж������������ƶ�Ҫ��ɹ���ʽ��Ϊ���бꡱ�ĸ�����
//					4��	���ʣ��ƶ�Ҫ���б���ʵ�ʲ����б�ĺ�ͬ����/�ƶ�Ҫ���б�ĺ�ͬ����
					//ȡ��һ�л���ֵ
					if(tblCheck.getRow(0) != null && tblCheck.getRow(0).getCell("column19").getValue() != null){
						BigDecimal value1 = FDCHelper.toBigDecimal(tblCheck.getRow(0).getCell("column18").getValue()); //�ƶ�Ҫ���б���ʵ�ʲ����б�ĺ�ͬ����
						BigDecimal value2 = FDCHelper.toBigDecimal(tblCheck.getRow(0).getCell("column19").getValue()); // �ƶ�Ҫ���б�ĺ�ͬ����
						
						if(value2.equals(FDCHelper.ZERO) || value2.toString().equals("0.00") || value2.toString().equals("0")){
							row.getCell("value").setValue("0.00%");
						}else{
							row.getCell("value").setValue(value1.divide(value2,2,BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED)  +"%");
						}
						
					}
					break;
				case 3:
					row.getCell("seq").setValue("��");
					row.getCell("name").setValue("��ͼ��б���");
					
//					2��	������ͼ��б�ĺ�ͬ���������ա����˱��С�������ͼ��б�ĺ�ͬ������ȡ���߼���
//					3��	����ɲɹ��ĺ�ͬ�����������ա����˱��С�����ɲɹ��ĺ�ͬ��������ȡ���߼���
//					4��	���ʣ�������ͼ��б�ĺ�ͬ����/����ɲɹ��ĺ�ͬ������
					//ȡ��һ�л���ֵ
					if(tblCheck.getRow(0) != null && tblCheck.getRow(0).getCell("totalCon").getValue() != null){
						BigDecimal value1 = FDCHelper.toBigDecimal(tblCheck.getRow(0).getCell("minConCount").getValue()); //������ͼ��б�ĺ�ͬ����
						BigDecimal value2 = FDCHelper.toBigDecimal(tblCheck.getRow(0).getCell("totalCon").getValue()); //����ɲɹ��ĺ�ͬ������
						if(value2.equals(FDCHelper.ZERO) || value2.toString().equals("0.00") || value2.toString().equals("0")){
							row.getCell("value").setValue("0.00%");
						}else{
							row.getCell("value").setValue(value1.divide(value2,2,BigDecimal.ROUND_HALF_UP).multiply(FDCHelper.ONE_HUNDRED)  +"%");
						}
					}
					break;
				case 4:
					row.getCell("seq").setValue("��");
					row.getCell("name").setValue("�ɹ��ƻ������");
					
//					2��	��ʱ��ɲɹ��ĺ�ͬ���������ա����˱��С���ʱ��ɲɹ��ĺ�ͬ������ȡ���߼���
//					3��	����ɲɹ��ĺ�ͬ�����������ա����˱��С�����ɲɹ��ĺ�ͬ��������ȡ���߼���
//					4��	���ʣ���ʱ��ɲɹ��ĺ�ͬ����/����ɲɹ��ĺ�ͬ������
					//ȡ��һ�л���ֵ
					if(tblCheck.getRow(0) != null && tblCheck.getRow(0).getCell("totalCon").getValue() != null){
						BigDecimal value1 = FDCHelper.toBigDecimal(tblCheck.getRow(0).getCell("timeCount").getValue()); //��ʱ��ɲɹ��ĺ�ͬ����
						BigDecimal value2 = FDCHelper.toBigDecimal(tblCheck.getRow(0).getCell("totalCon").getValue()); //����ɲɹ��ĺ�ͬ������
						
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
