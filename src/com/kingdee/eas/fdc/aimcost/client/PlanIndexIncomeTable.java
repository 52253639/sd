package com.kingdee.eas.fdc.aimcost.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTEditHelper;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTTransferAction;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDSplitPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.CommitListener;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.PreChangeEvent;
import com.kingdee.bos.ctrl.swing.event.PreChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexFactory;
import com.kingdee.eas.fdc.aimcost.PlanIndexInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CellBinder;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;

public class PlanIndexIncomeTable {
	private KDTable table=null;
	private String headId=null; 
	private CellBinder binder=null;
	private int dynRowBase=9;
	private MeasureIncomeEditUI measureIncomeEditU=null;
	private PlanIndexInfo planIndexInfo=null;
	public PlanIndexIncomeTable(PlanIndexInfo planIndexInfo,MeasureIncomeEditUI measureIncomeEditUI) throws EASBizException, BOSException{
		table=new KDTable(16,1,0);
		table.setEditable(false);
		initCtrlListener();
		binder=new CellBinder();
		this.measureIncomeEditU=measureIncomeEditUI;
		isHasSubTable=measureIncomeEditUI.isUseCustomIndex();
		isPlanIndexLogic=measureIncomeEditUI.isPlanIndexLogic();
		isBuildPartLogic=measureIncomeEditUI.isBuildPartLogic();
		initTable(planIndexInfo);
	}
	protected void initCtrlListener(){
		table.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
			public void editStopped(
					com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
				table_editStopped(e);
			}
		});
		table.addKDTMouseListener(new KDTMouseListener(){
			public void tableClicked(KDTMouseEvent e) {
				table_tableClicked(e);
			}
		});
		table.setBeforeAction(new BeforeActionListener(){
			public void beforeAction(BeforeActionEvent e) {

				if(BeforeActionEvent.ACTION_DELETE==e.getType()){
					int colIndex=table.getSelectManager().getActiveColumnIndex();
					if(colIndex==1){
						e.setCancel(true);
					}
				}

			
			}
		});
		
		table.setAfterAction(new BeforeActionListener(){
			public void beforeAction(BeforeActionEvent e) {

				if(BeforeActionEvent.ACTION_DELETE==e.getType()){
					KDTEditEvent event=new KDTEditEvent(table);
					int activeColumnIndex = table.getSelectManager().getActiveColumnIndex();
					int activeRowIndex =table.getSelectManager().getActiveRowIndex();
					event.setColIndex(activeColumnIndex);
					event.setRowIndex(activeRowIndex);
					event.setOldValue(FDCHelper.ONE);
					event.setValue(null);
					table_editStopped(event);
				}

			
			}
		});
	}
	public KDTable getTable(){
		return table;
	}
	
	public void initTable(PlanIndexInfo info){
/*		table.setHeadDisplayMode(KDTStyleConstants.HEAD_DISPLAY_EXCEL);
		IRow addHeadRow = table.addHeadRow();
		for(int i=0;i<table.getColumnCount()-1;i++){
			addHeadRow.getCell(i).setValue((i+1)+"");
		}*/
//		FDCTableHelper.setDebugHead(table);
		((KDTTransferAction) table.getActionMap().get(KDTAction.PASTE)).setPasteMode(KDTEditHelper.VALUE);
//		KDTMergeManager mm = table.getHeadMergeManager();
//		mm.mergeBlock(0, 0, 0, 11, KDTMergeManager.SPECIFY_MERGE);
		table.getHeadRow(0).getCell(0).setValue("规划指标表");
//		table.getHeadRow(0).getStyleAttributes().setBold(true);
//		table.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//		table.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
/*		for(int i=0;i<table.getColumnCount()-1;i++){
			table.getColumns().autoFitColumnWidth(i);
			if(i==0) continue;
//			table.getColumn(i).getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//			table.getColumn(i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); 
		}*/
		
		table.getColumn(3).setWidth(100);
		table.getColumn(7).setWidth(120);
		table.getColumn(8).setWidth(120);
		table.getColumn(10).setWidth(50);
		table.getColumn(11).setWidth(50);
		
		table.getColumn(12).setWidth(50);
		table.getColumn(13).setWidth(50);
		table.getColumn(14).getStyleAttributes().setLocked(true);
		initFixTable(info);
		initDynTable(info);
		ICellEditor f7Editor = new KDTDefaultCellEditor(getF7productType());
		table.getColumn(1).setEditor(f7Editor);

		//设置风格
		Color lockColor=new Color(0xF0EDD9);
		StyleAttributes sa = table.getRow(3).getStyleAttributes();
		sa.setHorizontalAlign(HorizontalAlignment.CENTER);
//		sa.setBackground(lockColor);
		sa = table.getRow(5).getStyleAttributes();
		sa.setHorizontalAlign(HorizontalAlignment.CENTER);
//		sa.setBackground(lockColor);
		sa=table.getRow(7).getStyleAttributes();
		sa.setLocked(true);
		sa.setBackground(new Color(0xF0EDf1));
		sa=table.getRow(8).getStyleAttributes();
		sa.setHorizontalAlign(HorizontalAlignment.CENTER);
		sa.setBackground(lockColor);
		sa.setLocked(true);
		
		table.getCell(table.getRowCount()-1, 0).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);

		table.getColumn(1).getStyleAttributes().setNumberFormat("@");
		KDTextField textField=new  KDTextField();
		textField.setMaxLength(300);
		table.getColumn(14).setEditor(new KDTDefaultCellEditor(textField));
		for(int i=0;i<7;i++){
			for(int j=9;j<table.getColumnCount();j++)	{
				if(i<3&&j==9){
					continue;
				}
				if(table.getCell(i, j)==null) continue;
				if(isPlanIndexLogic()){
					if(j==8&&(i==6||i==7)){
						continue;
					}
				}
				table.getCell(i, j).getStyleAttributes().setLocked(true);
				table.getCell(i, j).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			}
		}
		table.getCell(2, 6).getStyleAttributes().setLocked(true);
		table.getCell(2, 6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		table.getCell(2, 7).getStyleAttributes().setLocked(true);
		table.getCell(2, 7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		table.getCell(3, 8).getStyleAttributes().setLocked(true);
		table.getCell(3, 8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		table.getCell(4, 8).getStyleAttributes().setLocked(true);
		table.getCell(4, 8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		if(isPlanIndexLogic()){
			table.getCell(3, 6).getStyleAttributes().setLocked(true);
			table.getCell(3, 6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			
			table.getCell(3, 7).getStyleAttributes().setLocked(true);
			table.getCell(3, 7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			
			table.getCell(4, 6).getStyleAttributes().setLocked(true);
			table.getCell(4, 6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			
			table.getCell(4, 7).getStyleAttributes().setLocked(true);
			table.getCell(4, 7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);

		}else{
			table.getCell(5, 7).getStyleAttributes().setLocked(true);
			table.getCell(6, 7).getStyleAttributes().setLocked(true);
			table.getCell(5, 7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			table.getCell(6, 7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			table.getCell(5, 8).getStyleAttributes().setLocked(true);
			table.getCell(5, 8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			table.getCell(6, 8).getStyleAttributes().setLocked(true);
			table.getCell(6, 8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			
		}
		
		//总建筑面积可以编辑 by sxhong 2008/1/2
//		table.getCell(0, 6).getStyleAttributes().setLocked(true);
//		table.getCell(0, 6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//		table.getCell(1, 4).getStyleAttributes().setLocked(true);
//		table.getCell(1, 4).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		//建筑密度
		table.getCell(2, 5).getStyleAttributes().setLocked(true);
		table.getCell(2, 5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		//道路用地合计
		table.getCell(4, 0).getStyleAttributes().setLocked(true);
		table.getCell(4, 0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		//绿地用地合计
		table.getCell(6, 0).getStyleAttributes().setLocked(true);
		table.getCell(6, 0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//		table.getCell(1, 6).getStyleAttributes().setLocked(true);
//		table.getCell(1, 6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//		table.getCell(1, 4).getStyleAttributes().setNumberFormat("0.00%");
		table.getCell(1, 7).getStyleAttributes().setNumberFormat("0.00%");
		table.getCell(2, 7).getStyleAttributes().setNumberFormat("0.00%");
		reSetExpressions();
/*		KDFormattedTextField txtEditor=(KDFormattedTextField)table.getCell(0,6).getEditor().getComponent();
		txtEditor.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);*/
		KDTEditEvent e=new KDTEditEvent(table, null,null, dynRowBase+1, 1,false,10000);
		calc(e);
		if(isHasSubTable()){
			initSubTable(info);
		}
	}
	
	private void initFixTable(PlanIndexInfo info){
		table.addRows(9);

		//绑定单元格
//		binder.bindCell(table, 0, 0,"总占地面积"	,"totalContainArea",true);
		binder.bindCell(table, 0, 1,"总用地面积(m2)"	,"totalContainArea",true);//jf总占地面积
		binder.bindCell(table, 0, 4,"建设用地面积(m2)"	,"buildArea",true);//jf建筑用地面积
		binder.bindCell(table, 0, 6,"实际总建造面积(m2)"	,"totalBuildArea",true);
		binder.bindCell(table, 0, 8,"规划总建筑面积(m2)"	,"totalConstructArea",true);
		
		binder.bindCell(table, 1, 1,"建筑物占地面积(m2)"	,"buildContainArea",true);
		binder.bindCell(table, 1, 4,"公共配套用房面积(m2)"	,"publicSetHouse",true);
		binder.bindCell(table, 1, 6,"绿地率"	,"greenAreaRate",true);
		binder.bindCell(table, 1, 8,"地下室面积(m2)"	,"basementArea",true);
		
		binder.bindCell(table, 2, 1,"计容积率面积(m2)"	,"cubageRateArea",true);
		binder.bindCell(table, 2, 4,"容积率"	,"cubageRate",true);
		binder.bindCell(table, 2, 6,"建筑密度"	,"buildDensity",true);
		binder.bindCell(table, 2, 8,"精装修面积(m2)"	,"decorationArea",true);
		
		binder.bindCell(table, 3, 0,"道路用地合计"	,"totalRoadArea",true,true);
		binder.bindCell(table, 3, 2,"沥青路面车行道 "	,"pitchRoad",true,true);
		binder.bindCell(table, 3, 3,"砼路面车行道（停车场）"	,"concreteRoad",true,true);
		binder.bindCell(table, 3, 5,"硬质铺装车行道 "	,"hardRoad",true,true);
		if(isPlanIndexLogic()){
			binder.bindCell(table, 5, 7,"硬质铺装广场 "	,"hardSquare",true,true);
			binder.bindCell(table, 5, 8,"硬质铺装人行道  ","hardManRoad",true,true);
		}else{
			binder.bindCell(table, 3, 6,"硬质铺装广场 "	,"hardSquare",true,true);
			binder.bindCell(table, 3, 7,"硬质铺装人行道  ","hardManRoad",true,true);
		}
		binder.bindCell(table, 5, 0,"绿化用地合计 "	,"totalGreenArea",true,true);
		binder.bindCell(table, 5, 2,"重要公共绿地 "	,"importPubGreenArea",true,true);
		binder.bindCell(table, 5, 3,"组团宅间绿化  "	,"houseGreenArea",true,true);
		binder.bindCell(table, 5, 5,"底层私家花园 "	,"privateGarden",true,true);
		binder.bindCell(table, 5, 6,"水景面积 "	,"warterViewArea",true,true);
		KDTMergeManager mm = table.getMergeManager();
		for(int i=0;i<8;i++){
			mm.mergeBlock(i, 0, i, 1, KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(i, 3, i, 4, KDTMergeManager.SPECIFY_MERGE);
		}
		mm.mergeBlock(7, 0, 7, 13);
		
		//空一行,设置产品头
		table.getCell(8, 0).setValue("产品构成");
		table.getCell(8, 2).setValue("占地面积");
		table.getCell(8, 3).setValue("容积率");
		table.getCell(8, 4).setValue("规划建筑面积");//jf
		table.getCell(8, 5).setValue("实际建造面积");
		table.getCell(8, 6).setValue("可售面积");
		table.getCell(8, 7).setValue("产品比例");
		table.getCell(8, 8).setValue("平均每户面积");
		table.getCell(8, 9).setValue("电梯");
		table.getCell(8, 10).setValue("层数");
		table.getCell(8, 11).setValue("层高");
		table.getCell(8, 12).setValue("单元数");
		table.getCell(8, 13).setValue("户数");
		table.getCell(8, 14).setValue("是否分摊");
		table.getCell(8, 15).setValue("备注");
		//隐藏 实际建造面积 列 jf
//		table.getColumn(4).getStyleAttributes().setHided(true);		
		table.getRow(3).getStyleAttributes().setHided(true);
		table.getRow(4).getStyleAttributes().setHided(true);
		table.getRow(5).getStyleAttributes().setHided(true);
		table.getRow(6).getStyleAttributes().setHided(true);
		if(info!=null)
			binder.setCellsValue(info);
	}
	
	private void initDynTable(PlanIndexInfo info){
		int base=dynRowBase;
		int start=base;
		int rows=base;
		PlanIndexTypeEnum lastType=null;
		KDTMergeManager mm = table.getMergeManager();
		if(info==null){
			info=new PlanIndexInfo();
			PlanIndexEntryInfo entry=new PlanIndexEntryInfo();
			entry.setType(PlanIndexTypeEnum.house);
			info.getEntrys().add(entry);
			
			entry=new PlanIndexEntryInfo();
			entry.setType(PlanIndexTypeEnum.business);
			info.getEntrys().add(entry);
			
			entry=new PlanIndexEntryInfo();
			entry.setType(PlanIndexTypeEnum.publicBuild);
			info.getEntrys().add(entry);
			
			entry=new PlanIndexEntryInfo();
			entry.setType(PlanIndexTypeEnum.parking);
			info.getEntrys().add(entry);
		}else{
			//旧数据增加商业 by hpw 2009-08-28
			PlanIndexEntryCollection entrys= new PlanIndexEntryCollection();
			boolean isOldData = true;
			for(int i=0;i<info.getEntrys().size();i++){
				if(PlanIndexTypeEnum.business==info.getEntrys().get(i).getType()){
					isOldData=false;
				}
				entrys.add(info.getEntrys().get(i));
			}
			if(isOldData){
				info.getEntrys().clear();
				PlanIndexEntryCollection newEntrys = new PlanIndexEntryCollection();
				for(int i=0;i<entrys.size();i++){
					if(PlanIndexTypeEnum.publicBuild==entrys.get(i).getType()){//公共配套建筑之前增加
						PlanIndexEntryInfo entry=new PlanIndexEntryInfo();
						entry.setType(PlanIndexTypeEnum.business);
						newEntrys.add(entry);
					}
					newEntrys.add(entrys.get(i));
				}
				info.getEntrys().addCollection(newEntrys);
			}
		}
		for(int i=0;i<info.getEntrys().size();i++){
//			rows=rows+i;
			PlanIndexEntryInfo entry=info.getEntrys().get(i);
			IRow row=table.addRow(rows);
			if(entry.getType()!=lastType&&lastType!=null){
				row.getCell(0).setValue(lastType);
				row.getCell(1).setValue("小计");
				loadRow(null, row);
				
				row=table.addRow(++rows);
				start=rows;
				
				if(entry.getType()==PlanIndexTypeEnum.parking){
					mm.mergeBlock(rows, 0, rows, 1);
					row.getCell(2).setValue("占地面积");
					row.getCell(3).setValue("车位数");
					row.getCell(4).setValue("规划建筑面积");//jf
					row.getCell(5).setValue("实际建造面积");
					row.getCell(5).setValue("建筑面积");
					row.getCell(6).setValue("可售面积");
					StyleAttributes sa = row.getStyleAttributes();
					sa.setHorizontalAlign(HorizontalAlignment.CENTER);
					sa.setLocked(true);
					sa.setBackground(new Color(0xF0EDD9));
					row=table.addRow(++rows);
					start=rows;
				}
			}
			loadRow(entry, row);
			lastType=entry.getType();
			rows++;
		}
		
		IRow row=table.addRow(rows);
		row.getCell(0).setValue(lastType);
		row.getCell(1).setValue("小计");
		loadRow(null, row);
		
		row=table.addRow(++rows);
		row.getCell(0).setValue("合计");
		loadRow(null, row);
	
		mm.mergeBlock(base, 0, rows-1, 0, KDTMergeManager.FREE_ROW_MERGE);
		mm.mergeBlock(row.getRowIndex(), 0, row.getRowIndex(), 1);
	}
	
	private void loadRow(PlanIndexEntryInfo entry,IRow row){
		StyleAttributes sa = row.getStyleAttributes();
//		Object value = row.getCell(0).getValue();
		if(isSubTotalRow(row)){
			sa.getNumberFormat();
			sa.setNumberFormat("#,##0.00;-#,##0.00");
			sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
			sa.setLocked(true);
			sa.setBackground(FDCTableHelper.yearTotalColor);
			row.getCell(0).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			row.getCell(1).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			row.getCell(9).getStyleAttributes().setNumberFormat("");
			row.getCell(10).getStyleAttributes().setNumberFormat("");
			
			return;
		}else if(isTotalRow(row)){
			sa.setNumberFormat("#,##0.00;-#,##0.00");
			sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
			sa.setLocked(true);
			sa.setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
			return;
		}
//		row.getCell(10).setValue(Boolean.FALSE);
		if(entry==null){
//			entry=new PlanIndexEntryInfo();
			row.getCell(14).setValue(Boolean.FALSE);
		}else{
			row.getCell(0).setValue(entry.getType());
			row.getCell(0).getStyleAttributes().setLocked(true);
			//产品
			if(entry.isIsProduct()){
				row.getCell(1).setValue(entry.getProduct());
			}else{
				row.getCell(1).setValue(entry.getName());
			}
			
			row.getCell(2).setValue(entry.getContainArea());
			row.getCell(3).setValue(entry.getCubageRate());
			row.getCell(4).setValue(entry.getConstructArea());//jf
			row.getCell(5).setValue(entry.getBuildArea());
			row.getCell(6).setValue(entry.getSellArea());
			
			if(entry.getType()==PlanIndexTypeEnum.house || entry.getType()==PlanIndexTypeEnum.business){
				row.getCell(7).setValue(entry.getProductRate());
				row.getCell(8).setValue(entry.getUnitArea());
				if(entry.getElevators()>0){
					row.getCell(9).setValue(new Integer(entry.getElevators()));
				}
				if(entry.getFloors()>0){
					row.getCell(10).setValue(new Integer(entry.getFloors()));
				}
				row.getCell(11).setValue(entry.getFloorHeight());
				row.getCell(12).setValue(entry.getUnits());
				row.getCell(13).setValue(entry.getDoors());
				
			}
			row.getCell(14).setValue(Boolean.valueOf(entry.isIsSplit()));
			row.getCell(15).setValue(entry.getDesc());
////			row.getCell(10).setValue("test");
//			KDCheckBox box=new KDCheckBox();
//			box.setSelected(entry.isIsSplit());
//			row.getCell(10).setEditor(new KDTDefaultCellEditor(box));
			
		}
		

		
/*		ICellEditor f7Editor = new KDTDefaultCellEditor(getF7productType(row));
		row.getCell(1).setEditor(f7Editor);*/
		for(int i=2;i<14;i++){
			sa=row.getCell(i).getStyleAttributes();
			sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
			if(i==9 || i==10){
				row.getCell(i).setEditor(CellBinder.getCellIntegerNumberEdit());
			}else{
				row.getCell(i).setEditor(CellBinder.getCellNumberEdit());
				sa.setNumberFormat("#,##0.00;-#,##0.00");
			}
		}
		
		
		boolean isPark=false;
		if(row.getCell(0)!=null&&row.getCell(0).getValue() instanceof PlanIndexTypeEnum){
			PlanIndexTypeEnum type=(PlanIndexTypeEnum)row.getCell(0).getValue();
			if(type==PlanIndexTypeEnum.parking){
				isPark=true;
			}
			
			if(type==PlanIndexTypeEnum.parking||type==PlanIndexTypeEnum.publicBuild){
				for(int i=7;i<table.getColumnCount()-2;i++){
					row.getCell(i).getStyleAttributes().setLocked(true);
					row.getCell(i).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
				}
				
			} else if (type == PlanIndexTypeEnum.house
					|| (type == PlanIndexTypeEnum.business && (entry == null || entry
							.getProduct() == null))) {//没有产品则认为空记录
				// 住宅必需分摊,停车，公共配套建筑，商业(如上)分摊取自定义值
				row.getCell(14).setValue(Boolean.TRUE);
			}
			
			
		}

		if(!isPark){
//			row.getCell(2).getStyleAttributes().setLocked(true);
//			row.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			row.getCell(3).getStyleAttributes().setLocked(true);//jf
			row.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			row.getCell(7).getStyleAttributes().setLocked(true);
			row.getCell(7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			row.getCell(8).getStyleAttributes().setLocked(true);
			row.getCell(8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			/**
			 * 修改容积率为编辑时8位小数，一般可见2位小数
			 */
			row.getCell(3).setEditor(getCellNumberEdit(8));
		}
		
		row.getCell(0).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		row.getCell(1).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
//		row.getCell(10).setValue(Boolean.valueOf(entry.isIsSplit()));
		
		
	}
	
	public KDTDefaultCellEditor getCellEditor(){
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor();
		KDFormattedTextField formatText = new KDFormattedTextField();
		formatText.setPrecision(8);
		cellEditor = new KDTDefaultCellEditor(formatText);
		return cellEditor;
	}
	
	public void addRow(ActionEvent e){
		//在小计行之前插入，然后重设统计行
		boolean hasAdd=false;
		int index = table.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			return;
		}
		IRow selectRow = table.getRow(index);
		if(selectRow.getCell(0).getValue() instanceof PlanIndexTypeEnum){
			for(int i=index;i<table.getRowCount()-1;i++){
				IRow row=table.getRow(i);
				if(isSubTotalRow(row)){
					row=table.addRow(i);
//					table.setRow(i);
					table.getScriptManager().removeAll();
					row.getCell(0).setValue(selectRow.getCell(0).getValue());
					loadRow(null, row);
					hasAdd=true;
					break;
				}
			}
		}
		if(hasAdd){
			reSetExpressions();
		}
		
	}
	public void deleteRow(ActionEvent e){
		//删除行后重设统计行
		boolean hasDelete=false;
		KDTSelectManager selectManager = table.getSelectManager();
		if (selectManager == null || selectManager.size() == 0) {
			return;
		}
		
//		table.getScriptManager().setAutoAdjustFormula(false);
		boolean mustAdd=false;
		for (int i = 0; i < selectManager.size(); i++) {
			KDTSelectBlock selectBlock = selectManager.get(i);
			for (int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++) {
				IRow selectRow = table.getRow(j);
				if (selectRow == null) {
					continue;
				}
				if(selectRow.getCell(0).getValue() instanceof PlanIndexTypeEnum){
					if(selectRow.getCell(1).getValue()!=null&&selectRow.getCell(1).getValue().equals("小计")){
						continue;
					}
					
					if((table.getCell(j-1, 1).getValue()!=null&&table.getCell(j-1, 1).getValue().equals("小计"))
							&&(table.getCell(j+1, 1).getValue()!=null&&table.getCell(j+1, 1).getValue().equals("小计"))){
						mustAdd=true;
					}else{
						if((table.getCell(j-1, 2).getValue()!=null&&table.getCell(j-1, 2).getValue().equals("占地面积"))
								&&(table.getCell(j+1, 1).getValue()!=null&&table.getCell(j+1, 1).getValue().equals("小计"))){
							mustAdd=true;
						}
					}
					table.getScriptManager().removeAll();
					if(mustAdd){
						addRow(e);
					}
					IRow removeRow = table.removeRow(selectRow.getRowIndex());
					hasDelete=true;
					Object value = removeRow.getCell(1).getValue();
					if(value instanceof ProductTypeInfo){
						ProductTypeInfo product = (ProductTypeInfo)value;
						if(getPlanIndexEntryInfo(product.getId().toString())!=null){
							measureIncomeEditU.deleteProductTypeTable(product);
							if(measureIncomeEditU.getplTables().getSelectedIndex()!=1){
								measureIncomeEditU.getplTables().setSelectedIndex(1);
							}
						}
					}
//					table.getBody().removeRow(selectRow.getRowIndex());
				}
				break;//只支持单行
			}
		}
		if(hasDelete&&!mustAdd){
			reSetExpressions();
		}
		
		KDTEditEvent event=new KDTEditEvent(table);
		int activeColumnIndex = 4;
		int activeRowIndex =table.getSelectManager().getActiveRowIndex();
		event.setColIndex(activeColumnIndex);
		event.setRowIndex(activeRowIndex);
		event.setOldValue(FDCHelper.ONE);
		event.setValue(null);
		table_editStopped(event);
		

	}
	private void reSetExpressions(){
		if(true) return;
//		TODO 太乱了，找时间再优化 by sxhong
		table.getScriptManager().setDiv0DefaultValue("0");
		PlanIndexTypeEnum lastType=PlanIndexTypeEnum.house;
//		table.getScriptManager().setAutoRun(false);
		//得到住宅小计的行号;
		int houseSubIndex=dynRowBase;
		int businessSubIndex=dynRowBase;
		int publicSubIndex=dynRowBase;
		int parkingSubIndex=getTable().getRowCount()-2;
		for(int i=dynRowBase;i<getTable().getRowCount()-1;i++){
			IRow row=getTable().getRow(i);
			if(row.getCell(0).getValue()==PlanIndexTypeEnum.house
					&&isSubTotalRow(row)){
				houseSubIndex=i;
			}
			if(row.getCell(0).getValue()==PlanIndexTypeEnum.business
					&&isSubTotalRow(row)){
				businessSubIndex=i;
			}
			if(row.getCell(0).getValue()==PlanIndexTypeEnum.publicBuild
					&&isSubTotalRow(row)){
				publicSubIndex=i;
				break;
			}
		}
		//住宅
		if(houseSubIndex>dynRowBase){
			IRow houseRow=getTable().getRow(houseSubIndex);
			for(int j=2;j<getTable().getColumnCount()-2;j++){
				if(j==6){
					//产品比例
					houseRow.getCell(j).setValue(FDCHelper.ONE);
					continue;
				}
				if(j==7){//平均每户面积
					char c1=(char)'A'+5;
					String areaExp="sum("+c1+(dynRowBase+1)+":"+c1+(houseSubIndex)+")";
					c1=(char)'A'+9;
					String huExp="sum("+c1+(dynRowBase+1)+":"+c1+(houseSubIndex)+")";
					String exp="="+areaExp+"/"+huExp;
					houseRow.getCell(j).setExpressions(exp);
//					houseRow.getCell(j).getStyleAttributes().setLocked(false);
					continue;
				}
				char c=(char)('A'+j);
				String exp=null;				
				exp="=sum("+c+(dynRowBase+1)+":"+c+(houseSubIndex)+")";
				houseRow.getCell(j).setExpressions(exp);
			}
			
			
			//占地面积=建筑面积/容积率,产品比例=建筑面积/总建筑面积 
			//刘丛胜新要求：修改成产品比例=可售面积/可售面积小计（住宅）
			for(int i=dynRowBase;i<houseSubIndex;i++){
				IRow row=getTable().getRow(i);
				//占地面积=建筑面积/容积率
				row.getCell(2).setExpressions("=ROUND(E"+(i+1)+"/D"+(i+1)+",2)");
				//产品比例=可售面积/可售面积小计（住宅）
				row.getCell(6).setExpressions("=ROUND(F"+(i+1)+"/sum(F"+(dynRowBase+1)+":F"+(houseSubIndex)+"),2)");
//				row.getCell(6).getStyleAttributes().setLocked(false);
				//平均第户面积=可售面积/户数
				row.getCell(7).setExpressions("=ROUND(F"+(i+1)+"/J"+(i+1)+",2)");
				row.getCell(7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//				row.getCell(7).getStyleAttributes().setLocked(true);
			}
		}else{
			IRow houseRow=getTable().getRow(houseSubIndex);
			for(int j=2;j<getTable().getColumnCount()-2;j++){
				houseRow.getCell(j).setValue(null);
				houseRow.getCell(j).setExpressions("");
			}
		}
		//商业
		if(businessSubIndex>(houseSubIndex+1)){
			IRow businessRow=getTable().getRow(businessSubIndex);
			for(int j=2;j<6;j++){
				char c=(char)('A'+j);
				String exp=null;				
				exp="=sum("+c+(houseSubIndex+2)+":"+c+(businessSubIndex)+")";
				businessRow.getCell(j).setExpressions(exp);
			}
			
			
			for(int i=houseSubIndex+1;i<businessSubIndex;i++){
				IRow row=getTable().getRow(i);
				//占地面积=建筑面积/容积率
				row.getCell(2).setExpressions("=ROUND(E"+(i+1)+"/D"+(i+1)+",2)");
				//产品比例=可售面积/可售面积小计（住宅）
				row.getCell(6).setExpressions("=ROUND(F"+(i+1)+"/sum(F"+(dynRowBase+1)+":F"+(houseSubIndex)+"),2)");
				//平均第户面积=可售面积/户数
				row.getCell(7).setExpressions("=ROUND(F"+(i+1)+"/J"+(i+1)+",2)");
				row.getCell(7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			}
			
		}else{
			IRow businessRow=getTable().getRow(businessSubIndex);
			for(int j=2;j<6;j++){
				businessRow.getCell(j).setValue(null);
				businessRow.getCell(j).setExpressions("");
			}
		}
		//公建
		if(publicSubIndex>(businessSubIndex+1)){
			IRow publicRow=getTable().getRow(publicSubIndex);
			for(int j=2;j<6;j++){
				char c=(char)('A'+j);
				String exp=null;				
				exp="=sum("+c+(businessSubIndex+2)+":"+c+(publicSubIndex)+")";
				publicRow.getCell(j).setExpressions(exp);
			}
			
			
			for(int i=businessSubIndex+1;i<publicSubIndex;i++){
				IRow row=getTable().getRow(i);
				//占地面积=建筑面积/容积率
				row.getCell(2).setExpressions("=ROUND(E"+(i+1)+"/D"+(i+1)+",2)");

			}
			
		}else{
			IRow publicRow=getTable().getRow(publicSubIndex);
			for(int j=2;j<6;j++){
				publicRow.getCell(j).setValue(null);
				publicRow.getCell(j).setExpressions("");
			}
		}
		//停车
		if(parkingSubIndex>(publicSubIndex+2)){
			IRow parkRow=getTable().getRow(parkingSubIndex);
			for(int j=2;j<6;j++){
				char c=(char)('A'+j);
				String exp=null;				
				exp="=sum("+c+(publicSubIndex+2)+":"+c+(parkingSubIndex)+")";
				parkRow.getCell(j).setExpressions(exp);
			}
		}else{
			IRow parkRow=getTable().getRow(parkingSubIndex);
			for(int j=2;j<6;j++){
				parkRow.getCell(j).setValue(null);
				parkRow.getCell(j).setExpressions("");
			}
		}
		
//		table.getScriptManager().runAll();
		//合计
		IRow row=table.getRow(table.getRowCount()-1);
		row.getCell(0).setValue("合计");
		
		//改在按小计列统计
		String exp="=ROUND(sum(";
		String expSellArea="=ROUND(sum(";
		boolean isPark=false;
		for(int i=dynRowBase;i<table.getRowCount()-1;i++){
			if(table.getCell(i, 1).getValue()!=null&&table.getCell(i, 1).getValue().equals("小计")){
				continue;
			}				
			if(table.getCell(i, 2).getValue()!=null&&table.getCell(i, 2).getValue().equals("占地面积")){
				isPark=true;
				continue;
			}

			exp=exp+"A"+(i+1)+",";
			
//			if(!isPark){
				expSellArea=expSellArea+"A"+(i+1)+",";
//			}
		}
		
		exp=exp.substring(0,exp.length()-1)+"),2)"	;
		expSellArea=expSellArea.substring(0,expSellArea.length()-1)+"),2)"	;
		//可售面积单独汇总
		for(int j=2;j<=5;j++){
			if(j==3) continue;//容积率与车位数不汇总
			char c=(char)('A'+j);
			
			if(j==5){
				row.getCell(j).setExpressions(expSellArea.replaceAll("A", c+""));
			}else{
				row.getCell(j).setExpressions(exp.replaceAll("A", c+""));
			}
		}

		//总建筑面积
//		table.getCell(0, 6).setExpressions("=E"+table.getRowCount());
		//建筑密度=建筑物占地面积/建筑用地面积；		
		//容积率=计容积率面积/建筑用地面积
		table.getCell(1, 4).setExpressions("=ROUND(C2/E1,4)");
		table.getCell(1, 4).getStyleAttributes().setNumberFormat("0.00%");
		table.getCell(1, 6).getStyleAttributes().setNumberFormat("0.00%");
		table.getCell(2, 4).setExpressions("=ROUND(C3/E1,2)");
		//道路,绿化合计
		table.getCell(4, 0).setExpressions("=ROUND(sum(C5:G5),2)");
		table.getCell(6, 0).setExpressions("=ROUND(sum(C7:F7),2)");
		KDTMergeManager mm = table.getMergeManager();
		mm.mergeBlock(dynRowBase, 0, table.getRowCount()-2, 0, KDTMergeManager.FREE_ROW_MERGE);
		table.getScriptManager().runAll();
		table.getScriptManager().setAutoRun(true);
		
	}
	public void save(String headID) throws EASBizException, BOSException{
		Object obj = table.getUserObject();
		PlanIndexInfo planIndexInfo=null;
		if(obj instanceof PlanIndexInfo){
			planIndexInfo=(PlanIndexInfo)obj;
			planIndexInfo.getEntrys().clear();
		}else{
			planIndexInfo=new PlanIndexInfo();
		}
		planIndexInfo.setHeadID(headID);
		binder.setObjectValue(planIndexInfo);
		
		//设置分录的值
		PlanIndexEntryInfo entry=new PlanIndexEntryInfo();
		entry.setType(PlanIndexTypeEnum.house);
		planIndexInfo.getEntrys().add(entry);
		
		entry=new PlanIndexEntryInfo();
		entry.setType(PlanIndexTypeEnum.business);
		planIndexInfo.getEntrys().add(entry);
		
		entry=new PlanIndexEntryInfo();
		entry.setType(PlanIndexTypeEnum.publicBuild);
		planIndexInfo.getEntrys().add(entry);
		
		entry=new PlanIndexEntryInfo();
		entry.setType(PlanIndexTypeEnum.parking);
		planIndexInfo.getEntrys().add(entry);
		//保存到数据库
		IObjectPK pk = PlanIndexFactory.getRemoteInstance().save(planIndexInfo);
		planIndexInfo.setId(BOSUuid.read(pk.toString()));
		table.setUserObject(planIndexInfo);
		
		
	}
	
	/**
	 * 取得规划指标表里面的产品
	 * @return
	 */
	public Set getProductIdSet(){
		Set idSet=new HashSet();
		for(int i=dynRowBase;i<table.getRowCount()-1;i++){
			IRow row=table.getRow(i);
			Object value = row.getCell(1).getValue();
			if(value instanceof ProductTypeInfo){
				idSet.add(((ProductTypeInfo)value).getId().toString());
			}
		}
		return idSet;
	}
	
	public PlanIndexInfo getPlanIndexInfo(){
//		Object obj = table.getUserObject();
//		PlanIndexInfo planIndexInfo=null;
//		if(obj instanceof PlanIndexInfo){
//			planIndexInfo=(PlanIndexInfo)obj;
//			planIndexInfo.getEntrys().clear();
//		}else{
//			planIndexInfo=new PlanIndexInfo();
//		}
		if(planIndexInfo==null){
			planIndexInfo=new PlanIndexInfo();
		}
//		planIndexInfo=new PlanIndexInfo();
		planIndexInfo.getEntrys().clear();
		binder.setObjectValue(planIndexInfo);
		
		//设置分录的值
		for(int i=dynRowBase;i<table.getRowCount()-1;i++){
			IRow row=table.getRow(i);
			if(row.getCell(0).getValue() instanceof PlanIndexTypeEnum){
				Object value = row.getCell(1).getValue();
				if(value==null||value.toString().trim().length()<1){
					continue;
				}
				if(value.equals("小计")){
					//插入一空行
					PlanIndexEntryInfo entry=new PlanIndexEntryInfo();
					entry.setType((PlanIndexTypeEnum)row.getCell(0).getValue());
					entry.setIsProduct(false);
					entry.setIndex(i);
					entry.setIsSplit(false);
					planIndexInfo.getEntrys().add(entry);
				}else{
					PlanIndexEntryInfo entry=new PlanIndexEntryInfo();
					entry.setIndex(i);
					entry.setType((PlanIndexTypeEnum)row.getCell(0).getValue());
					//产品
					if(value instanceof ProductTypeInfo){
						entry.setIsProduct(true);
						entry.setProduct((ProductTypeInfo)value);
					}else{
						//过滤空白
						if(FDCHelper.isEmpty(value)){
							boolean isEmpty=true;
							for(int h=2;h<table.getColumnCount()-2;h++){
								ICell cell = row.getCell(h);
								if(cell!=null&&FDCHelper.toBigDecimal(cell.getValue()).compareTo(FDCHelper.ZERO)==0){
									isEmpty=true;
								}else{
									isEmpty=false;
									break;
								}
							}
							if(isEmpty){
								continue;
							}
						}
						
						entry.setName(value.toString());
						entry.setIsProduct(false);
					}
					
					if(FDCHelper.toBigDecimal(row.getCell(2).getValue()) instanceof BigDecimal){
						entry.setContainArea(FDCHelper.toBigDecimal(row.getCell(2).getValue()));
					}
					if(row.getCell(3).getValue() instanceof BigDecimal){
						entry.setCubageRate((BigDecimal)row.getCell(3).getValue());
					}
					if(row.getCell(4).getValue() instanceof BigDecimal){//jf
						entry.setConstructArea((BigDecimal)row.getCell(4).getValue());
					}
					if(row.getCell(5).getValue() instanceof BigDecimal){
						entry.setBuildArea((BigDecimal)row.getCell(5).getValue());
					}
					if(row.getCell(6).getValue() instanceof BigDecimal){
						entry.setSellArea((BigDecimal)row.getCell(6).getValue());
					}
					if(FDCHelper.toBigDecimal(row.getCell(7).getValue()) instanceof BigDecimal){
						entry.setProductRate(FDCHelper.toBigDecimal(row.getCell(7).getValue()));
					}
					if(FDCHelper.toBigDecimal(row.getCell(8).getValue()) instanceof BigDecimal){
						entry.setUnitArea(FDCHelper.toBigDecimal(row.getCell(8).getValue()));
					}
					if(row.getCell(9).getValue() instanceof Integer){
						entry.setElevators(((Integer)row.getCell(9).getValue()).intValue());
					}
					if(row.getCell(10).getValue() instanceof Integer){
						entry.setFloors(((Integer)row.getCell(10).getValue()).intValue());
					}
					if(row.getCell(11).getValue() instanceof BigDecimal){
						entry.setFloorHeight((BigDecimal)row.getCell(11).getValue());
					}
					if(row.getCell(12).getValue() instanceof BigDecimal){
						entry.setUnits((BigDecimal) row.getCell(12).getValue());
					}
					if(row.getCell(13).getValue() instanceof BigDecimal){
						entry.setDoors((BigDecimal)row.getCell(13).getValue());
					}
					if(row.getCell(14).getValue() instanceof Boolean){
						entry.setIsSplit(((Boolean)row.getCell(14).getValue()).booleanValue());
					}else{
						entry.setIsSplit(false);
					}
					entry.setDesc((String)row.getCell(15).getValue());
					entry.setParent(planIndexInfo);
					planIndexInfo.getEntrys().add(entry);
				}
			}
			
			
		}
		//保存自定义分录
		storeCustom(planIndexInfo);
		//保存到数据库
//		IObjectPK pk = PlanIndexFactory.getRemoteInstance().save(planIndexInfo);
//		planIndexInfo.setId(BOSUuid.read(pk.toString()));
//		table.setUserObject(planIndexInfo);
//		table.setUserObject(planIndexInfo);
		return planIndexInfo;
		
	}
	public PlanIndexEntryInfo getPlanIndexEntryInfo(String productId){
		if(planIndexInfo==null||productId==null){
			return null;
		}
		for(int i=0;i<planIndexInfo.getEntrys().size();i++){
			PlanIndexEntryInfo entry = planIndexInfo.getEntrys().get(i);
			if(entry.getProduct()!=null&&entry.getProduct().getId().toString().equals(productId)){
				return entry;
			}
		}
		return null;
	}
	
	/**
	 * 取自定义的指标分录,暂时分两类,项目或产品
	 * @param productId
	 * @return
	 */
	public CustomPlanIndexEntryCollection getCustomPlanIndexs(String productId){
		CustomPlanIndexEntryCollection entrys=new CustomPlanIndexEntryCollection();
		if(planIndexInfo==null||!isHasSubTable()){
			return entrys;
		}
		for(int i=0;i<planIndexInfo.getCustomEntrys().size();i++){
			CustomPlanIndexEntryInfo entry = planIndexInfo.getCustomEntrys().get(i);
			if(productId==null){
				if(entry.getProductType()==null){
					entrys.add(entry);
				}
			}else{
				if(entry.getProductType()!=null
//						&&entry.getProductType().getId().toString().equals(productId)
						){
					entrys.add(entry);
				}
			}
		}
		return entrys;
	}
	
	
	public CustomPlanIndexEntryInfo getCustomPlanIndexEntryInfo(String apportTypeId,String productId){
		if(planIndexInfo==null||!isHasSubTable()){
			return null;
		}
		for(int i=0;i<planIndexInfo.getCustomEntrys().size();i++){
			CustomPlanIndexEntryInfo entry = planIndexInfo.getCustomEntrys().get(i);
			if (productId == null
					&& entry.getProductType() == null
					&& entry.getApportType().getId().toString().equals(
							apportTypeId)) {
				return entry;
			}
			if(productId!=null&&entry.getProductType()!=null&&entry.getProductType().getId().toString().equals(productId)&&entry.getApportType().getId().toString().equals(apportTypeId)){
				return entry;
			}
		}
		return null;
	}
	
	protected void table_editStopped(KDTEditEvent e) {
		if(table==null){
			return;
		}
		Object objOld=e.getOldValue();
		Object objNew=e.getValue();
		if(objOld==null&&objNew==null){
			return;
		}
		if(objOld!=null&&objNew!=null&&objOld.equals(objNew)){
			return;
		}
/*		int buildAreaIdx=4;
		if(e.getColIndex()==buildAreaIdx&&e.getRowIndex()>=dynRowBase){
			BigDecimal amount=FDCHelper.ZERO;
			for(int i=dynRowBase;i<table.getRowCount()-2;i++){
				IRow row=table.getRow(i);
				if(!isSubTotalRow(row)){
					amount=amount.add(FDCHelper.toBigDecimal(row.getCell(buildAreaIdx).getValue()));
				}
			}
			table.getCell(0, 6).setValue(amount);
		}*/
		calc(e);
		measureIncomeEditU.setDataChange(true);
	}
	private KDBizPromptBox f7productType=null; 
	public KDBizPromptBox  getF7productType(){
/*		if(f7productType!=null) {
			return f7productType;
		}*/
//		final IRow row=r;
		f7productType = new KDBizPromptBox()
		{
			protected Object stringToValue(String t) {
				// TODO Auto-generated method stub
				 Object obj= super.stringToValue(t);
				 if(obj instanceof  IObjectValue){
					 return obj;
				 }else{
					 return t;
				 }
				 
			}
		};
		f7productType
				.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		view.setFilter(filter);
		f7productType.setEntityViewInfo(view);
		f7productType.setEditable(true);
		f7productType.setDisplayFormat("$name$");
		f7productType.setEditFormat("$number$");
		f7productType.setCommitFormat("$number$");
		f7productType.addPreChangeListener(new PreChangeListener(){
			public void preChange(PreChangeEvent e) {
				//如果产品值重复则不进行更改
				Object objNew=e.getData();
				if(objNew instanceof ProductTypeInfo){
//					PlanIndexInfo info=getPlanIndexInfo();
					int count=0;
					int flag=-1;//判断是否选择了本身
					for(int i=dynRowBase;i<table.getRowCount()-1;i++){
						IRow row2=table.getRow(i);
						Object value = row2.getCell(1).getValue();
						if(value instanceof ProductTypeInfo){
							if(((ProductTypeInfo)value).getId().equals(((ProductTypeInfo)objNew).getId())){
								count++;
								flag=i;
							}
						}
					}
					int rowIndex=KDTableUtil.getSelectedRow(table);
					if(count==1&&rowIndex!=flag) {
						MsgBox.showWarning(measureIncomeEditU, "该产品类型已经设置了指标");
						e.setResult(PreChangeEvent.S_FALSE);
					}
					
				}
			}
		});
		f7productType.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent e) {
				int rowIndex=KDTableUtil.getSelectedRow(table);
				
				Object objOld=table.getCell(rowIndex,1).getValue();//e.getOldValue();
				Object objNew=e.getNewValue();
				if(objOld==null&&objNew==null) return;
				if(objOld!=null&&objOld.equals(objNew)) return;
				PlanIndexInfo info=getPlanIndexInfo();
				if(objNew!=null&&objNew.equals(f7productType.getUserObject())){
					return;
				}
				if(objNew instanceof ProductTypeInfo){
					measureIncomeEditU.addProductTypeTable((ProductTypeInfo)objNew);
				}
				
				if(objOld instanceof ProductTypeInfo){
					int count=0;
					if(info!=null){
						for(int i=0;i<info.getEntrys().size();i++){
							PlanIndexEntryInfo entry=info.getEntrys().get(i);
							if(entry.getProduct()!=null&&entry.getProduct().getId().equals(((ProductTypeInfo)objOld).getId())){
								count++;
								if(count>1) {
									break;
								}
							}
						}
					}
					if(count==1) measureIncomeEditU.deleteProductTypeTable((ProductTypeInfo)objOld);
				}
				
			}
		});
		
		return f7productType;
	}

	private void table_tableClicked(KDTMouseEvent e){
		if(measureIncomeEditU==null||measureIncomeEditU.getOprtState().equals(OprtState.VIEW)){
			return;
		}
		if(e.getColIndex()==10&&e.getRowIndex()>=9){
			ICell cell = table.getCell(e.getRowIndex(), 0);
			if(cell.getValue()!=null&&cell.getValue() instanceof PlanIndexTypeEnum){
				if(cell.getValue()==PlanIndexTypeEnum.house){
					return;
				}
			}
			cell = table.getCell(e.getRowIndex(), e.getColIndex());
			if(cell.getValue() instanceof Boolean){
				cell.setValue(Boolean.valueOf(!((Boolean)cell.getValue()).booleanValue()));
			}
		}
	}

	public static BigDecimal getAllSellArea(PlanIndexInfo info){
		BigDecimal amount=FDCHelper.ZERO;
		for(int i=0;i<info.getEntrys().size();i++){
			//暂时不处理
//			不包括停车的
//			if(info.getEntrys().get(i).getType()==PlanIndexTypeEnum.parking){
//				continue;
//			}
			if(info.getEntrys().get(i).isIsProduct()&&info.getEntrys().get(i).isIsSplit()){
				amount=amount.add(FDCHelper.toBigDecimal(info.getEntrys().get(i).getSellArea()));
			}
		}
		
		return amount;
	}
	
	/**
	 * 参数否，总建筑面积 = 规划指标表上的总建筑面积，
	 * 导出指标时也跟系统原有逻辑保持一致，将总建筑面积导出作为项目的建筑面积指标；
	 * 
	 * 参数是，总建筑面积 = 规划指标表上的是否分摊为是的产品建筑面积之和，
	 * 导出指标时将规划指标表上的是否分摊为是的产品建筑面积之和导出作为工程项目的建筑面积
	 * @param PlanIndexInfo
	 * @return
	 */
	public static BigDecimal getAllBuildArea(PlanIndexInfo info){
		BigDecimal amount=FDCHelper.ZERO;
		if(isBuildPartLogic()){
			for(int i=0;i<info.getEntrys().size();i++){
				//与可售一致，包括停车
				if(info.getEntrys().get(i).isIsProduct()&&info.getEntrys().get(i).isIsSplit()){
					amount=amount.add(FDCHelper.toBigDecimal(info.getEntrys().get(i).getBuildArea()));
				}
			}
		}else{//直接赋值可能为空amount = info.getTotalBuildArea();
			amount = amount.add(FDCHelper.toBigDecimal(info.getTotalBuildArea()));
		}
		
		return amount;
	}
	private boolean isSubTotalRow(IRow row){
		boolean isSubTotalRow=false;
		if(row.getCell(1).getValue()!=null&&row.getCell(1).getValue().equals("小计")){
			isSubTotalRow=true;
		}
		return isSubTotalRow;
	}
	
	private boolean isTotalRow(IRow row){
		boolean isTotalRow=false;
		Object value = row.getCell(0).getValue();
		if(value!=null&&value.equals("合计")){
			isTotalRow=true;
		}
		return isTotalRow;
	}

	/**
	 * 总占地面积(m2)
	 */
	private ICell totalContainAreaCell=null;
	/**
	 * 建筑用地面积(m2)
	 */
	private ICell buildAreaCell=null;	
	/**
	 * 总建筑面积(m2)
	 */
	private ICell totalBuildAreaCell=null;	
	/**
	 * 建筑物占地面积(m2)
	 */
	private ICell buildContailAreaCell=null;	
	/**
	 * 建筑密度
	 */
	private ICell buildDensityCell=null;
	/**
	 * 公共配套用户面积(m2)
	 */
	private ICell publicSetHouseCell=null;
	/**
	 * 绿地率
	 */
	private ICell greenAreaRateCell=null;
	/**
	 * 计容积率面积(m2)
	 */
	private ICell cubageRateAreaCell=null;
	/**
	 * 容积率
	 */
	private ICell cubageRateCell=null;
	/**
	 * 道路用地合计
	 */
	private ICell totalRoadAreaCell=null;
	/**
	 * 沥青路面车行道
	 */
	private ICell pitchRoadCell=null;
	/**
	 * 砼路面车行道（停车场）
	 */
	private ICell concreteRoadCell=null;

	/**
	 * 硬质铺装车行道
	 */
	private ICell hardRoadCell=null;
	/**
	 * 硬质铺装广场
	 */
	private ICell hardSquareCell=null;
	/**
	 * 硬质铺装人行道
	 */
	private ICell hardManRoadCell=null;
	/**
	 * 绿化用地合计
	 */
	private ICell totalGreenAreaCell=null;
	/**
	 * 重要公共绿地
	 */
	private ICell importPubGreenAreaCell=null;
	/**
	 * 组团宅间绿化
	 */
	private ICell houseGreenAreaCell=null;
	/**
	 * 底层私家花园
	 */
	private ICell privateGardenCell=null;
	/**
	 * 水景面积
	 */
	private ICell warterViewAreaCell=null;
	//产品列索引
	private int productColummnIndex=1;
	/**
	 * 占地面积
	 */
	private int containAreaColummnIndex=2;
	/**
	 * 容积率面积
	 */
	private int cubageRateColummnIndex=3;
	/**
	 * 规划建筑面积
	 */
	private int constructAreaColumnIndex=4;
	/**
	 * 建筑面积
	 */
	private int buildAreaColummnIndex=5;
	/**
	 * 可售面积
	 */
	private int sellAreaColummnIndex=6;
	/**
	 * 产品比例
	 */
	private int productRateColummnIndex=7;
	/**
	 * 平均每户面积
	 */
	private int avgHuColummnIndex=8;
	/**
	 * 电梯
	 */
	private int elevatorsIndex=9;
	/**
	 * 楼层
	 */
	private int floorsIndex=10;
	/**
	 * 层高
	 */
	private int floorHeightIndex=11;
	/**
	 * 单元数
	 */
	private int unitColummnIndex=12;
	/**
	 * 户数
	 */
	private int huColummnIndex=13;
	/**
	 * 是否分摊
	 */
	private int splitColummnIndex=14;
	/**
	 * 备注
	 */
	private int descColummnIndex=15;
	/**
	 * 车位数
	 */
	private int parksColummnIndex=3;
	/**
	 * 住宅小计行
	 */
	private int houseSubIndex=0;
	/**
	 * 商业小计行
	 */
	private int businessSubIndex=0;
	/**
	 * 公共配套建筑行
	 */
	private int publicSubIndex=0;
	/**
	 * 停车小计行
	 */
	private int parkingSubIndex=0;
	/**
	 * 合计行
	 */
	private int totalIndex=0;
	
	private void initVariable(){
		KDTable table=getTable();
		totalContainAreaCell=table.getCell(0, 2);
		buildAreaCell=table.getCell(0, 5);	
		totalBuildAreaCell=table.getCell(0, 7);	
		buildContailAreaCell=table.getCell(1, 2);	
		publicSetHouseCell=table.getCell(1, 5);
		greenAreaRateCell=table.getCell(1, 7);
		cubageRateAreaCell=table.getCell(2, 2);
		cubageRateCell=table.getCell(2,5);
		buildDensityCell=table.getCell(2, 7);
		totalRoadAreaCell=table.getCell(4, 0);
		pitchRoadCell=table.getCell(4, 2);
		concreteRoadCell=table.getCell(4,3);
		hardRoadCell=table.getCell(4,5);
		if(isPlanIndexLogic()){
			hardSquareCell=table.getCell(6,7);
			hardManRoadCell=table.getCell(6,8);
		}else{
			hardSquareCell=table.getCell(4,6);
			hardManRoadCell=table.getCell(4,7);
		}
		totalGreenAreaCell=table.getCell(6,0);
		importPubGreenAreaCell=table.getCell(6,2);
		houseGreenAreaCell=table.getCell(6,3);
		privateGardenCell=table.getCell(6,5);
		warterViewAreaCell=table.getCell(6,6);
		
		//得到住宅小计的行号;
		houseSubIndex=dynRowBase;
		businessSubIndex=dynRowBase;
		publicSubIndex=dynRowBase;
		parkingSubIndex=getTable().getRowCount()-2;
		for(int i=dynRowBase;i<getTable().getRowCount()-1;i++){
			IRow row=getTable().getRow(i);
			if(row.getCell(0).getValue()==PlanIndexTypeEnum.house
					&&isSubTotalRow(row)){
				houseSubIndex=i;
			}
			if(row.getCell(0).getValue()==PlanIndexTypeEnum.business
					&&isSubTotalRow(row)){
				businessSubIndex=i;
			}
			if(row.getCell(0).getValue()==PlanIndexTypeEnum.publicBuild
					&&isSubTotalRow(row)){
				publicSubIndex=i;
				break;
			}
		}
		totalIndex=table.getRowCount()-1;
		calcArea();
	}
	private void calc(KDTEditEvent e){
		initVariable();
		KDTable table=getTable();
		if(e!=null&&e.getRowIndex()<dynRowBase){
	/*		公摊：
			建筑密度=建筑物占地面积/建筑用地面积；		
			容积率=计容积率面积/建筑用地面积
			道路用地合计=沥表路面车行道+砼路面车行道(停车场)+硬质铺装车行道+硬质铺装广场+硬质铺装人行道
			绿化合计=重要公共绿地+组团宅间绿化+底层私家花园+水景面积
			绿地率＝绿化用地合计/总占地面积
			总建筑面积＝产品建筑面积之和但是可以修改
			
			参数值为是，则将“硬质铺装广场”、“硬质铺装人行道”两个指标下移到绿化用地合计行，且修改计算公式为：
			道路用地合计=沥青路面车行道+砼路面车行道（停车场）+硬质铺装车行道
			绿化用地合计=重要公共绿地+组团宅间绿化+底层私家花园+水景面积+硬质铺装广场+硬质铺装人行道

	*/
			BigDecimal oldGreenAmt=FDCHelper.toBigDecimal(totalGreenAreaCell.getValue());
			BigDecimal oldContainAmt=FDCHelper.toBigDecimal(totalContainAreaCell.getValue());
			if(e!=null&&e.getRowIndex()==totalContainAreaCell.getRowIndex()&&e.getColIndex()==totalContainAreaCell.getColumnIndex()){
				oldContainAmt=FDCHelper.toBigDecimal(e.getOldValue());
			}
			BigDecimal buildAreaAmt=FDCHelper.toBigDecimal(buildAreaCell.getValue());
			if(buildAreaAmt.signum()==0){
				buildDensityCell.setValue(null);
				cubageRateCell.setValue(null);
			}else{
				buildDensityCell.setValue(FDCHelper.divide(buildContailAreaCell.getValue(), buildAreaAmt, 4, BigDecimal.ROUND_HALF_UP));
				cubageRateCell.setValue(FDCHelper.divide(cubageRateAreaCell.getValue(), buildAreaAmt, 2, BigDecimal.ROUND_HALF_UP));
			}
			BigDecimal amt=FDCHelper.ZERO;
			amt=FDCNumberHelper.add(amt,pitchRoadCell.getValue());
			amt=FDCNumberHelper.add(amt,concreteRoadCell.getValue());
			amt=FDCNumberHelper.add(amt, hardRoadCell.getValue());
			if(!isPlanIndexLogic()){
				amt=FDCNumberHelper.add(amt, hardSquareCell.getValue());
				amt=FDCNumberHelper.add(amt, hardManRoadCell.getValue());
			}
			totalRoadAreaCell.setValue(amt);
			amt=FDCHelper.ZERO;
			amt=FDCNumberHelper.add(amt,importPubGreenAreaCell.getValue());
			amt=FDCNumberHelper.add(amt,houseGreenAreaCell.getValue());
			amt=FDCNumberHelper.add(amt, privateGardenCell.getValue());
			amt=FDCNumberHelper.add(amt, warterViewAreaCell.getValue());
			if(isPlanIndexLogic()){
				amt=FDCNumberHelper.add(amt, hardSquareCell.getValue());
				amt=FDCNumberHelper.add(amt, hardManRoadCell.getValue());
			}
			totalGreenAreaCell.setValue(amt);
			if(amt.compareTo(oldGreenAmt)!=0||FDCHelper.toBigDecimal(totalContainAreaCell.getValue()).compareTo(oldContainAmt)!=0){
				greenAreaRateCell.setValue(FDCNumberHelper.divide(amt, totalContainAreaCell.getValue(),4,BigDecimal.ROUND_HALF_UP));
			}
		}else{
/*
  			占地面积=建筑面积/容积率
			产品比例=可售面积/可售面积小计（住宅）
			平均每户面积=可售面积/户数
			平均每户面积小计＝可售面积小计/户数小计
			容积率小计＝建筑面积小计/占地面积小计

			合计:
			占地面积合计,建筑面积合计,可售面积合计

			用于分摊的总的可售面积=参与分摊的产品面积之和
*/
			//合计建筑面积
			BigDecimal oldtotalBuildArea=FDCHelper.toBigDecimal(table.getCell(totalIndex, buildAreaColummnIndex).getValue());
			//住宅
			Map sumMap=new HashMap();
			for(int i=dynRowBase;i<houseSubIndex;i++){
				BigDecimal tmp = FDCHelper.ZERO;
				int changedCol = e.getColIndex();
				BigDecimal buildArea = FDCNumberHelper.toBigDecimal(
						table.getCell(i, buildAreaColummnIndex).getValue());
				if(changedCol == 2){
					BigDecimal tmpCubRate = FDCHelper.ZERO;
					if(!FDCHelper.isNullZero(buildArea)){
						tmpCubRate = FDCNumberHelper.divide(
								table.getCell(i, buildAreaColummnIndex).getValue(), 
								table.getCell(i,containAreaColummnIndex).getValue());
						table.getCell(i, cubageRateColummnIndex).setValue(tmpCubRate);
					}
					else {
						tmpCubRate = FDCNumberHelper.multiply(
								table.getCell(i, containAreaColummnIndex).getValue(),
								table.getCell(i, cubageRateColummnIndex).getValue());
						table.getCell(i, buildAreaColummnIndex).setValue(tmpCubRate);
					}
					
				}else if(changedCol == 3){
					BigDecimal tmpConArea = FDCHelper.ZERO;
					if(!FDCHelper.isNullZero(buildArea)){
						tmpConArea = FDCNumberHelper.divide(
								table.getCell(i, buildAreaColummnIndex).getValue(),
								table.getCell(i, cubageRateColummnIndex).getValue());
						table.getCell(i, containAreaColummnIndex).setValue(tmpConArea);
					}else{
						tmpConArea = FDCNumberHelper.multiply(
								table.getCell(i, containAreaColummnIndex).getValue(),
								table.getCell(i, cubageRateColummnIndex).getValue());
						table.getCell(i, buildAreaColummnIndex).setValue(tmpConArea);
					}
				}else if(changedCol == 5){
					BigDecimal tmpConArea = FDCHelper.ZERO;
					if(!FDCHelper.isNullZero(
							FDCNumberHelper.toBigDecimal(
									table.getCell(i, cubageRateColummnIndex).getValue()))){
						tmpConArea = FDCNumberHelper.divide(
							table.getCell(i, buildAreaColummnIndex).getValue(),
							table.getCell(i, cubageRateColummnIndex).getValue());
						table.getCell(i, containAreaColummnIndex).setValue(tmpConArea);
					}else{
						tmpConArea = FDCNumberHelper.divide(
								table.getCell(i, buildAreaColummnIndex).getValue(),
								table.getCell(i, containAreaColummnIndex).getValue());
							table.getCell(i, cubageRateColummnIndex).setValue(tmpConArea);
					}
				}
				tmp=FDCNumberHelper.divide(table.getCell(i, sellAreaColummnIndex).getValue(), table.getCell(i, huColummnIndex).getValue());
				table.getCell(i, avgHuColummnIndex).setValue(tmp);
				sumMap.put("cubageRate", FDCNumberHelper.add(sumMap.get("cubageRate"), table.getCell(i, cubageRateColummnIndex).getValue()));
				sumMap.put("containArea", FDCNumberHelper.add(sumMap.get("containArea"), table.getCell(i, containAreaColummnIndex).getValue()));
				sumMap.put("constructArea", FDCNumberHelper.add(sumMap.get("constructArea"), table.getCell(i, constructAreaColumnIndex).getValue()));//jf
				sumMap.put("buildArea", FDCNumberHelper.add(sumMap.get("buildArea"), table.getCell(i, buildAreaColummnIndex).getValue()));
				sumMap.put("sellArea", FDCNumberHelper.add(sumMap.get("sellArea"), table.getCell(i, sellAreaColummnIndex).getValue()));
				sumMap.put("elevators", FDCNumberHelper.add(sumMap.get("elevators"), table.getCell(i, elevatorsIndex).getValue()));
				sumMap.put("floors", FDCNumberHelper.add(sumMap.get("floors"), table.getCell(i, floorsIndex).getValue()));
				sumMap.put("floorHeight", FDCNumberHelper.add(sumMap.get("floorHeight"), table.getCell(i, floorHeightIndex).getValue()));
				sumMap.put("unit", FDCNumberHelper.add(sumMap.get("unit"), table.getCell(i, unitColummnIndex).getValue()));
				sumMap.put("hu", FDCNumberHelper.add(sumMap.get("hu"), table.getCell(i, huColummnIndex).getValue()));
				
				
				
//				tmp = FDCNumberHelper.divide(table.getCell(i, buildAreaColummnIndex).getValue(), table.getCell(i, cubageRateColummnIndex).getValue());
//				table.getCell(i, containAreaColummnIndex).setValue(tmp);
//				tmp=FDCNumberHelper.divide(table.getCell(i, sellAreaColummnIndex).getValue(), table.getCell(i, huColummnIndex).getValue());
//				table.getCell(i, avgHuColummnIndex).setValue(tmp);
//				sumMap.put("containArea", FDCNumberHelper.add(sumMap.get("containArea"), table.getCell(i, containAreaColummnIndex).getValue()));
//				sumMap.put("buildArea", FDCNumberHelper.add(sumMap.get("buildArea"), table.getCell(i, buildAreaColummnIndex).getValue()));
//				sumMap.put("sellArea", FDCNumberHelper.add(sumMap.get("sellArea"), table.getCell(i, sellAreaColummnIndex).getValue()));
//				sumMap.put("unit", FDCNumberHelper.add(sumMap.get("unit"), table.getCell(i, unitColummnIndex).getValue()));
//				sumMap.put("hu", FDCNumberHelper.add(sumMap.get("hu"), table.getCell(i, huColummnIndex).getValue()));
				
				
			}
			//产品比例
			BigDecimal max=FDCHelper.ZERO;
			int maxProductRate=dynRowBase;
			for(int i=dynRowBase;i<houseSubIndex;i++){
				BigDecimal tmp=FDCNumberHelper.divide(table.getCell(i, sellAreaColummnIndex).getValue(), sumMap.get("sellArea"));
				table.getCell(i, productRateColummnIndex).setValue(tmp);
				if(tmp!=null&&tmp.compareTo(max)>0){
					max=tmp;
					maxProductRate=i;
				}
				sumMap.put("productRate", FDCNumberHelper.add(sumMap.get("productRate"), tmp));
			}
			BigDecimal diff=FDCHelper.ONE.subtract(FDCNumberHelper.toBigDecimal(sumMap.get("productRate")));
			if(diff.signum()!=0){
				table.getCell(maxProductRate, productRateColummnIndex).setValue(FDCNumberHelper.add(table.getCell(maxProductRate, productRateColummnIndex).getValue(), diff));
			}
			//小计
			table.getCell(houseSubIndex, containAreaColummnIndex).setValue(sumMap.get("containArea"));
			table.getCell(houseSubIndex, constructAreaColumnIndex).setValue(sumMap.get("constructArea"));//jf
			table.getCell(houseSubIndex, buildAreaColummnIndex).setValue(sumMap.get("buildArea"));
			table.getCell(houseSubIndex, sellAreaColummnIndex).setValue(sumMap.get("sellArea"));
			table.getCell(houseSubIndex, elevatorsIndex).setValue(sumMap.get("elevators"));
			table.getCell(houseSubIndex, floorsIndex).setValue(sumMap.get("floors"));
			table.getCell(houseSubIndex, floorHeightIndex).setValue(sumMap.get("floorHeight"));
			table.getCell(houseSubIndex, unitColummnIndex).setValue(sumMap.get("unit"));
			table.getCell(houseSubIndex, huColummnIndex).setValue(sumMap.get("hu"));
			table.getCell(houseSubIndex, productRateColummnIndex).setValue(FDCNumberHelper.ONE);
			//容积率小计＝建筑面积小计/占地面积小计
			table.getCell(houseSubIndex, cubageRateColummnIndex).setValue(FDCNumberHelper.divide(sumMap.get("buildArea"), sumMap.get("containArea")));
			//平均每户面积小计＝可售面积小计/户数小计
			table.getCell(houseSubIndex, avgHuColummnIndex).setValue(FDCNumberHelper.divide(sumMap.get("sellArea"), sumMap.get("hu")));
			
			//商业
			sumMap.clear();
			for(int i=houseSubIndex+1;i<businessSubIndex;i++){
				BigDecimal tmp = FDCHelper.ZERO;
				int changedCol = e.getColIndex();
				BigDecimal buildArea = FDCNumberHelper.toBigDecimal(
						table.getCell(i, buildAreaColummnIndex).getValue());
				if(changedCol == 2){
					BigDecimal tmpCubRate = FDCHelper.ZERO;
					if(!FDCHelper.isNullZero(buildArea)){
						tmpCubRate = FDCNumberHelper.divide(
								table.getCell(i, buildAreaColummnIndex).getValue(), 
								table.getCell(i,containAreaColummnIndex).getValue());
						table.getCell(i, cubageRateColummnIndex).setValue(tmpCubRate);
					}
					else {
						tmpCubRate = FDCNumberHelper.multiply(
								table.getCell(i, containAreaColummnIndex).getValue(),
								table.getCell(i, cubageRateColummnIndex).getValue());
						table.getCell(i, buildAreaColummnIndex).setValue(tmpCubRate);
					}
					
				}else if(changedCol == 3){
					BigDecimal tmpConArea = FDCHelper.ZERO;
					if(!FDCHelper.isNullZero(buildArea)){
						tmpConArea = FDCNumberHelper.divide(
								table.getCell(i, buildAreaColummnIndex).getValue(),
								table.getCell(i, cubageRateColummnIndex).getValue());
						table.getCell(i, containAreaColummnIndex).setValue(tmpConArea);
					}else{
						tmpConArea = FDCNumberHelper.multiply(
								table.getCell(i, containAreaColummnIndex).getValue(),
								table.getCell(i, cubageRateColummnIndex).getValue());
						table.getCell(i, buildAreaColummnIndex).setValue(tmpConArea);
					}
				}else if(changedCol == 5){
					BigDecimal tmpConArea = FDCHelper.ZERO;
					if(!FDCHelper.isNullZero(
							FDCNumberHelper.toBigDecimal(
									table.getCell(i, cubageRateColummnIndex).getValue()))){
						tmpConArea = FDCNumberHelper.divide(
							table.getCell(i, buildAreaColummnIndex).getValue(),
							table.getCell(i, cubageRateColummnIndex).getValue());
						table.getCell(i, containAreaColummnIndex).setValue(tmpConArea);
					}else{
						tmpConArea = FDCNumberHelper.divide(
								table.getCell(i, buildAreaColummnIndex).getValue(),
								table.getCell(i, containAreaColummnIndex).getValue());
							table.getCell(i, cubageRateColummnIndex).setValue(tmpConArea);
					}
				}
				tmp=FDCNumberHelper.divide(table.getCell(i, sellAreaColummnIndex).getValue(), table.getCell(i, huColummnIndex).getValue());
				table.getCell(i, avgHuColummnIndex).setValue(tmp);
				sumMap.put("containArea", FDCNumberHelper.add(sumMap.get("containArea"), table.getCell(i, containAreaColummnIndex).getValue()));
				sumMap.put("constructArea", FDCNumberHelper.add(sumMap.get("constructArea"), table.getCell(i, constructAreaColumnIndex).getValue()));
				sumMap.put("buildArea", FDCNumberHelper.add(sumMap.get("buildArea"), table.getCell(i, buildAreaColummnIndex).getValue()));
				sumMap.put("sellArea", FDCNumberHelper.add(sumMap.get("sellArea"), table.getCell(i, sellAreaColummnIndex).getValue()));
				sumMap.put("unit", FDCNumberHelper.add(sumMap.get("unit"), table.getCell(i, unitColummnIndex).getValue()));
				sumMap.put("hu", FDCNumberHelper.add(sumMap.get("hu"), table.getCell(i, huColummnIndex).getValue()));
			}
			table.getCell(businessSubIndex, containAreaColummnIndex).setValue(sumMap.get("containArea"));
			table.getCell(businessSubIndex, constructAreaColumnIndex).setValue(sumMap.get("constructArea"));//jf
			table.getCell(businessSubIndex, buildAreaColummnIndex).setValue(sumMap.get("buildArea"));
			table.getCell(businessSubIndex, sellAreaColummnIndex).setValue(sumMap.get("sellArea"));
			table.getCell(businessSubIndex, unitColummnIndex).setValue(sumMap.get("unit"));
			table.getCell(businessSubIndex, huColummnIndex).setValue(sumMap.get("hu"));
			//容积率小计＝建筑面积小计/占地面积小计
			table.getCell(businessSubIndex, cubageRateColummnIndex).setValue(FDCNumberHelper.divide(sumMap.get("buildArea"), sumMap.get("containArea")));
			//平均每户面积小计＝可售面积小计/户数小计
			table.getCell(businessSubIndex, avgHuColummnIndex).setValue(FDCNumberHelper.divide(sumMap.get("sellArea"), sumMap.get("hu")));
			
			//公共配套
			sumMap.clear();
			for(int i=businessSubIndex+1;i<publicSubIndex;i++){
				int changedCol = e.getColIndex();
				BigDecimal buildArea = FDCNumberHelper.toBigDecimal(
						table.getCell(i, buildAreaColummnIndex).getValue());
				if(changedCol == 2){
					BigDecimal tmpCubRate = FDCHelper.ZERO;
					if(!FDCHelper.isNullZero(buildArea)){
						tmpCubRate = FDCNumberHelper.divide(
								table.getCell(i, buildAreaColummnIndex).getValue(), 
								table.getCell(i,containAreaColummnIndex).getValue());
						table.getCell(i, cubageRateColummnIndex).setValue(tmpCubRate);
					}
					else {
						tmpCubRate = FDCNumberHelper.multiply(
								table.getCell(i, containAreaColummnIndex).getValue(),
								table.getCell(i, cubageRateColummnIndex).getValue());
						table.getCell(i, buildAreaColummnIndex).setValue(tmpCubRate);
					}
					
				}else if(changedCol == 3){
					BigDecimal tmpConArea = FDCHelper.ZERO;
					if(!FDCHelper.isNullZero(buildArea)){
						tmpConArea = FDCNumberHelper.divide(
								table.getCell(i, buildAreaColummnIndex).getValue(),
								table.getCell(i, cubageRateColummnIndex).getValue());
						table.getCell(i, containAreaColummnIndex).setValue(tmpConArea);
					}else{
						tmpConArea = FDCNumberHelper.multiply(
								table.getCell(i, containAreaColummnIndex).getValue(),
								table.getCell(i, cubageRateColummnIndex).getValue());
						table.getCell(i, buildAreaColummnIndex).setValue(tmpConArea);
					}
				}else if(changedCol == 5){
					BigDecimal tmpConArea = FDCHelper.ZERO;
					if(!FDCHelper.isNullZero(
							FDCNumberHelper.toBigDecimal(
									table.getCell(i, cubageRateColummnIndex).getValue()))){
						tmpConArea = FDCNumberHelper.divide(
							table.getCell(i, buildAreaColummnIndex).getValue(),
							table.getCell(i, cubageRateColummnIndex).getValue());
						table.getCell(i, containAreaColummnIndex).setValue(tmpConArea);
					}else{
						tmpConArea = FDCNumberHelper.divide(
								table.getCell(i, buildAreaColummnIndex).getValue(),
								table.getCell(i, containAreaColummnIndex).getValue());
							table.getCell(i, cubageRateColummnIndex).setValue(tmpConArea);
					}
				}
				
				sumMap.put("containArea", FDCNumberHelper.add(sumMap.get("containArea"), table.getCell(i, containAreaColummnIndex).getValue()));
				sumMap.put("constructArea", FDCNumberHelper.add(sumMap.get("constructArea"), table.getCell(i, constructAreaColumnIndex).getValue()));
				sumMap.put("buildArea", FDCNumberHelper.add(sumMap.get("buildArea"), table.getCell(i, buildAreaColummnIndex).getValue()));
				sumMap.put("sellArea", FDCNumberHelper.add(sumMap.get("sellArea"), table.getCell(i, sellAreaColummnIndex).getValue()));
			}
			table.getCell(publicSubIndex, containAreaColummnIndex).setValue(sumMap.get("containArea"));
			table.getCell(publicSubIndex, constructAreaColumnIndex).setValue(sumMap.get("constructArea"));//jf
			table.getCell(publicSubIndex, buildAreaColummnIndex).setValue(sumMap.get("buildArea"));
			table.getCell(publicSubIndex, sellAreaColummnIndex).setValue(sumMap.get("sellArea"));
			table.getCell(publicSubIndex, cubageRateColummnIndex).setValue(FDCNumberHelper.divide(sumMap.get("buildArea"), sumMap.get("containArea")));
			//停车
			sumMap.clear();
			for(int i=publicSubIndex+2;i<parkingSubIndex;i++){
				sumMap.put("containArea", FDCNumberHelper.add(sumMap.get("containArea"), table.getCell(i, containAreaColummnIndex).getValue()));
				sumMap.put("constructArea", FDCNumberHelper.add(sumMap.get("constructArea"), table.getCell(i, constructAreaColumnIndex).getValue()));
				sumMap.put("buildArea", FDCNumberHelper.add(sumMap.get("buildArea"), table.getCell(i, buildAreaColummnIndex).getValue()));
				sumMap.put("sellArea", FDCNumberHelper.add(sumMap.get("sellArea"), table.getCell(i, sellAreaColummnIndex).getValue()));
				sumMap.put("park", FDCNumberHelper.add(sumMap.get("park"), table.getCell(i, parksColummnIndex).getValue()));
			}
			table.getCell(parkingSubIndex, containAreaColummnIndex).setValue(sumMap.get("containArea"));
			table.getCell(parkingSubIndex, constructAreaColumnIndex).setValue(sumMap.get("constructArea"));//jf
			table.getCell(parkingSubIndex, buildAreaColummnIndex).setValue(sumMap.get("buildArea"));
			table.getCell(parkingSubIndex, sellAreaColummnIndex).setValue(sumMap.get("sellArea"));
			table.getCell(parkingSubIndex, parksColummnIndex).setValue(sumMap.get("park"));

			//合计
			BigDecimal total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, containAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, containAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, containAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, containAreaColummnIndex).getValue());
			table.getCell(totalIndex, containAreaColummnIndex).setValue(total);
			
			total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, constructAreaColumnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, constructAreaColumnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, constructAreaColumnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, constructAreaColumnIndex).getValue());
			table.getCell(totalIndex, constructAreaColumnIndex).setValue(total);//jf
			
			total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, buildAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, buildAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, buildAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, buildAreaColummnIndex).getValue());
			table.getCell(totalIndex, buildAreaColummnIndex).setValue(total);
			
			total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, sellAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, sellAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, sellAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, sellAreaColummnIndex).getValue());
			table.getCell(totalIndex, sellAreaColummnIndex).setValue(total);
			
			BigDecimal newtotalBuildArea=FDCHelper.toBigDecimal(table.getCell(totalIndex, buildAreaColummnIndex).getValue());
			if(totalBuildAreaCell!=null&&e.getType()!=10000&&oldtotalBuildArea.compareTo(newtotalBuildArea)!=0){
				totalBuildAreaCell.setValue(newtotalBuildArea);
			}
			
		}
		
	}
	//防止客户随意改参数，道路用地合计，绿化用地合计重新计算
	private void calcArea(){
		BigDecimal oldGreenAmt=FDCHelper.toBigDecimal(totalGreenAreaCell.getValue());
		BigDecimal oldContainAmt=FDCHelper.toBigDecimal(totalContainAreaCell.getValue());
		BigDecimal amt=FDCHelper.ZERO;
		amt=FDCNumberHelper.add(amt,pitchRoadCell.getValue());
		amt=FDCNumberHelper.add(amt,concreteRoadCell.getValue());
		amt=FDCNumberHelper.add(amt, hardRoadCell.getValue());
		if(!isPlanIndexLogic()){
			amt=FDCNumberHelper.add(amt, hardSquareCell.getValue());
			amt=FDCNumberHelper.add(amt, hardManRoadCell.getValue());
		}
		totalRoadAreaCell.setValue(amt);
		amt=FDCHelper.ZERO;
		amt=FDCNumberHelper.add(amt,importPubGreenAreaCell.getValue());
		amt=FDCNumberHelper.add(amt,houseGreenAreaCell.getValue());
		amt=FDCNumberHelper.add(amt, privateGardenCell.getValue());
		amt=FDCNumberHelper.add(amt, warterViewAreaCell.getValue());
		if(isPlanIndexLogic()){
			amt=FDCNumberHelper.add(amt, hardSquareCell.getValue());
			amt=FDCNumberHelper.add(amt, hardManRoadCell.getValue());
		}
		totalGreenAreaCell.setValue(amt);
		if(amt.compareTo(oldGreenAmt)!=0||FDCHelper.toBigDecimal(totalContainAreaCell.getValue()).compareTo(oldContainAmt)!=0){
			greenAreaRateCell.setValue(FDCNumberHelper.divide(amt, totalContainAreaCell.getValue(),4,BigDecimal.ROUND_HALF_UP));
		}
	}
	void clear(){
		EventListener[] listeners =f7productType.getListeners(PreChangeListener.class);
		for(int i=0;i<listeners.length;i++){
			f7productType.removePreChangeListener((PreChangeListener)listeners[i]);
		}
		listeners =f7productType.getListeners(DataChangeListener.class);
		for(int i=0;i<listeners.length;i++){
			f7productType.removeDataChangeListener((DataChangeListener)listeners[i]);
		}
		f7productType.removeAll();
		f7productType=null;
		listeners = this.table.getListeners(KDTEditListener.class);
		for(int i=0;i<listeners.length;i++){
			this.table.removeKDTEditListener((KDTEditListener)listeners[i]);
		}
		
		listeners = this.table.getListeners(KDTMouseListener.class);
		for(int i=0;i<listeners.length;i++){
			this.table.removeKDTMouseListener((KDTMouseListener)listeners[i]);
		}
		this.table.setBeforeAction(null);
		this.table.setAfterAction(null);
		this.table.removeAll();
		this.table=null;
		this.headId=null;
		this.binder=null;
		this.measureIncomeEditU=null;
		this.planIndexInfo=null;
	}
	

	public JComponent getContentPanel(){
		if(isHasSubTable()){
			KDSplitPane panel=new KDSplitPane();			
			panel.setOrientation(0);
			panel.setDividerLocation(300);
			panel.setOneTouchExpandable(true);
//			panel.setLayout(new BorderLayout());
//			this.getTable().setSize(500, 500);
			panel.add(this.getTable(),KDSplitPane.TOP);
			KDContainer con=new KDContainer();
			con.setContainerType(KDContainer.STATICSTYLE);
			con.setEnableActive(false);
			con.setTitle("自定义指标");
			con.getContentPane().setLayout(new BorderLayout());
			con.getContentPane().add(this.getSubTable(),BorderLayout.CENTER);
			JButton btnAdd=con.add(new AbstractAction(){
				{
					putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_addline"));
					putValue(Action.SHORT_DESCRIPTION, "添加行");
				}
				public void actionPerformed(ActionEvent e) {
					subTable.addRow();
				}
			});
			
			JButton btnDel=con.add(new AbstractAction(){
				{
					putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_deleteline"));
					putValue(Action.SHORT_DESCRIPTION, "删除行");
				}
				public void actionPerformed(ActionEvent e) {
					int[] selectedRows = KDTableUtil.getSelectedRows(subTable);
					for(int i=selectedRows.length-1;i>=0;i--){
						subTable.removeRow(selectedRows[i]);
					}
				}
			});
//			con.setSize(500, 100);
			btnAdd.setEnabled(false);
			btnAdd.setVisible(true);
			btnDel.setEnabled(false);
			btnDel.setVisible(true);
			panel.add(con,KDSplitPane.BOTTOM);
//			panel.setDividerLocation(0.7);
//	        if(measureIncomeEditU.getOprtState().equals(OprtState.VIEW)){
//	        	subTable.getStyleAttributes().setLocked(true);
//	        	btnDel.setEnabled(false);
//	        	btnAdd.setEnabled(false);
//			}
	        subTable.setEditable(false);
			return panel;
		}else{
			return getTable();
		}
		
	}
	private static boolean isBuildPartLogic = false;
	private static boolean isBuildPartLogic(){
		return !isBuildPartLogic;
	}
	private boolean isPlanIndexLogic = false;
	private boolean isPlanIndexLogic(){
		return isPlanIndexLogic;
	}
	private boolean isHasSubTable=false;
	private boolean isHasSubTable(){
		return isHasSubTable;
	}
	private KDTable subTable=null;
	private void initSubTable(PlanIndexInfo info){
		//id,number,name,value,isProduct
		subTable=new KDTable(5,1,0);
		IRow headRow=subTable.getHeadRow(0);
		subTable.getColumn(0).setKey("id");
		subTable.getColumn(0).getStyleAttributes().setHided(true);
		subTable.getColumn(1).setKey("number");
		subTable.getColumn(2).setKey("name");
		subTable.getColumn(3).setKey("value");
		subTable.getColumn(4).setKey("productType");
		subTable.getColumn("name").getStyleAttributes().setLocked(true);
		subTable.getColumn("number").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		subTable.getColumn("value").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		subTable.addKDTEditListener(new KDTEditAdapter(){
			public void editStopped(KDTEditEvent e) {
				
				int rowIndex = e.getRowIndex();
				int colIndex=e.getColIndex();
				IRow row=subTable.getRow(rowIndex);
				if(colIndex==subTable.getColumnIndex("number")){
					ApportionTypeInfo info=(ApportionTypeInfo)row.getCell("number").getValue();
					if(info!=null){
						row.getCell("name").setValue(info.getName());
					}else{
						row.getCell("name").setValue(null);
					}
				}
			}
		});
		subTable.addKDTMouseListener(new KDTMouseListener(){
			public void tableClicked(KDTMouseEvent e) {/*
				int rowIndex = e.getRowIndex();
				int colIndex=e.getColIndex();
				IRow row=subTable.getRow(rowIndex);
				if(colIndex==subTable.getColumnIndex("product")){
					Object object = row.getCell("isProduct").getValue();
					row.getCell("isProduct").setValue(Boolean.valueOf(object==Boolean.FALSE));
				}
			*/}
		});
		headRow.getCell("id").setValue("ID");
		headRow.getCell("number").setValue("编码");
		headRow.getCell("name").setValue("名称");
		headRow.getCell("value").setValue("指标值");
		headRow.getCell("productType").setValue("产品");
		
		//暂时过滤，后续做成预设指标
		final Set nameSet = new HashSet();
		String[] name =  new String[] {
					"总占地面积(m2)", "建筑用地面积(m2)", "总建筑面积(m2)",
					"建筑物占地面积(m2)", "建筑密度", "绿地率", "计容积率面积(m2)",
					"容积率", "沥青路面车行道", "砼路面车行道（停车场）", "硬质铺装车行道",
					"硬质铺装广场", "硬质铺装人行道", "重要公共绿地", "组团宅间绿化",
					"底层私家花园", "水景面积", "占地面积", "建筑面积", "可售面积",
					"产品比例", "平均每户面积", "单元数", "户数"};
		for(int i=0;i<name.length;i++){
			nameSet.add(name[i]);
		}
		KDBizPromptBox f7 = new KDBizPromptBox();
		f7.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ApportionTypeQuery");
		f7.setDisplayFormat("$number$");
		f7.setCommitFormat("$number$");
		f7.setEditFormat("$number$");
		f7.setRequired(true);
		f7.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				KDBizPromptBox f7 =(KDBizPromptBox)e.getSource();
				f7.getQueryAgent().setDefaultFilterInfo(null);
				f7.getQueryAgent().setHasCUDefaultFilter(false);
				f7.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				try {
					filter.getFilterItems().add(new FilterItemInfo("id", ApportionTypeInfo.buildAreaType, CompareType.NOTEQUALS));
					filter.getFilterItems().add(new FilterItemInfo("id", ApportionTypeInfo.sellAreaType, CompareType.NOTEQUALS));
					filter.getFilterItems().add(new FilterItemInfo("id", ApportionTypeInfo.placeAreaType, CompareType.NOTEQUALS));
					filter.getFilterItems().add(new FilterItemInfo("id", ApportionTypeInfo.aimCostType, CompareType.NOTEQUALS));
					filter.getFilterItems().add(new FilterItemInfo("id", ApportionTypeInfo.appointType, CompareType.NOTEQUALS));
					filter.getFilterItems().add(
							new FilterItemInfo("name", nameSet,
									CompareType.NOTINCLUDE));
					filter.mergeFilter(ApportionTypeInfo.getCUFilter(SysContext.getSysContext().getCurrentCtrlUnit()), "and");
				} catch (BOSException e1) {
					measureIncomeEditU.handUIException(e1);
				}
				view.setFilter(filter);
				f7.setEntityViewInfo(view);
			}
		});
		f7.addCommitListener(new CommitListener(){
			public void willCommit(CommitEvent e) {
				KDBizPromptBox f7 =(KDBizPromptBox)e.getSource();
				f7.getQueryAgent().setDefaultFilterInfo(null);
				f7.getQueryAgent().setHasCUDefaultFilter(false);
				f7.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				try {
					filter.mergeFilter(ApportionTypeInfo.getCUFilter(SysContext.getSysContext().getCurrentCtrlUnit()), "and");
				} catch (BOSException e1) {
					measureIncomeEditU.handUIException(e1);
				}
				view.setFilter(filter);
				f7.setEntityViewInfo(view);
			}
		});
		
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(f7);
        subTable.getColumn("number").setEditor(editor);
        
        ObjectValueRender render = new ObjectValueRender();
        render.setFormat(new com.kingdee.bos.ctrl.extendcontrols.BizDataFormat("$number$"));
		subTable.getColumn("number").setRenderer(render);
		
        f7 = new KDBizPromptBox();
		f7.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
		
		f7.setDisplayFormat("$name$");
		f7.setCommitFormat("$number$");
		f7.setEditFormat("$number$");
		f7.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent e) {
				Set productSet=getProductIdSet();
				EntityViewInfo view=new EntityViewInfo();
				view.setFilter(new FilterInfo());
				if(productSet.size()>0){
					view.getFilter().getFilterItems().add(new FilterItemInfo("id",productSet,CompareType.INCLUDE));
				}
				((KDBizPromptBox)e.getSource()).setEntityViewInfo(view);
				
			}
		});
        editor = new KDTDefaultCellEditor(f7);
        subTable.getColumn("productType").setEditor(editor);
        subTable.getColumn("value").setEditor(getCellNumberEdit(8));
        FDCHelper.formatTableNumber(subTable, "value");
        if(info==null||info.getCustomEntrys()==null||info.getCustomEntrys().size()==0){
        	return;
        }
        for(Iterator iter=info.getCustomEntrys().iterator();iter.hasNext();){
        	IRow row=subTable.addRow();
        	CustomPlanIndexEntryInfo entry=(CustomPlanIndexEntryInfo)iter.next();
        	row.getCell("id").setValue(entry.getId().toString());
        	row.getCell("number").setValue(entry.getApportType());
        	row.getCell("name").setValue(entry.getApportType().getName());
        	row.getCell("productType").setValue(entry.getProductType());
        	row.getCell("value").setValue(entry.getValue());
        	row.setUserObject(entry);
        }
	}
	public KDTable getSubTable(){
		return subTable;
	}
	
	private void storeCustom(PlanIndexInfo planIndexInfo) {
		if(!isHasSubTable()){
			return;
		}
		verifyCustom();
		CustomPlanIndexEntryCollection customEntrys = planIndexInfo.getCustomEntrys();
		if(customEntrys==null){
			customEntrys=new CustomPlanIndexEntryCollection();
		}
		customEntrys.clear();
		
		for(int i=0;i<subTable.getRowCount();i++){
			IRow row=subTable.getRow(i);
			CustomPlanIndexEntryInfo entry=(CustomPlanIndexEntryInfo)row.getUserObject();
			if(entry==null){
				entry=new CustomPlanIndexEntryInfo();
				entry.setId(BOSUuid.create(entry.getBOSType()));
				row.setUserObject(entry);
			}
			entry.setApportType((ApportionTypeInfo)row.getCell("number").getValue());
			entry.setProductType((ProductTypeInfo)row.getCell("productType").getValue());
			entry.setValue((BigDecimal)row.getCell("value").getValue());
			entry.setParent(planIndexInfo);
			if(entry.getProductType()!=null){
				entry.setIsProduct(true);
			}else{
				entry.setIsProduct(false);
			}
			customEntrys.add(entry);
		}
		
	}
	
	private void verifyCustom(){
		//指标+产品唯一,指标必录
		for(int i=0;i<subTable.getRowCount();i++){
			IRow row=subTable.getRow(i);
			ApportionTypeInfo apportType = (ApportionTypeInfo)row.getCell("number").getValue();
			if(apportType==null){
				FDCMsgBox.showError(measureIncomeEditU, "编码必录");
				subTable.getSelectManager().select(i, 1,i,1);
				measureIncomeEditU.getplTables().setSelectedIndex(1);
				SysUtil.abort();
			}
			
			if(row.getCell("value").getValue()==null){
				FDCMsgBox.showError(measureIncomeEditU, "指标值必录");
				subTable.getSelectManager().select(i, 3,i,3);
				measureIncomeEditU.getplTables().setSelectedIndex(1);
				SysUtil.abort();
			}
		}
		
		for(int i=0;i<subTable.getRowCount();i++){
			IRow row=subTable.getRow(i);
			ApportionTypeInfo apportType = (ApportionTypeInfo)row.getCell("number").getValue();
			ProductTypeInfo productType = (ProductTypeInfo)row.getCell("productType").getValue();
			for(int j=i+1;j<subTable.getRowCount();j++){
				IRow row2=subTable.getRow(j);
				ApportionTypeInfo apportType2= (ApportionTypeInfo)row2.getCell("number").getValue();
				ProductTypeInfo productType2 = (ProductTypeInfo)row2.getCell("productType").getValue();
				if(apportType.getId().toString().equals(apportType2.getId().toString())){
					boolean isDup=false;
					if(productType==null&&productType2==null){
						isDup=true;
					}
					if(productType!=null&&productType2!=null&&productType.equals(productType2)){
						isDup=true;
					}
					if(isDup){
						FDCMsgBox.showError(measureIncomeEditU, "第"+(i+1)+"行与第"+(j+1)+"行存在重复");
						SysUtil.abort();
					}
				}
			}
		}
	}
	
	/**
	 * 得到一个-1.0E17－－1.0E17的BigDecimal CellEditor
	 * @author sxhong  		Date 2006-10-26
	 * @return
	 */
	public static KDTDefaultCellEditor getCellNumberEdit(int Precision){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(Precision);
        kdc.setRequired(true);
        kdc.setMinimumValue(FDCHelper.ZERO);
        kdc.setMaximumValue(FDCHelper.MAX_VALUE);
        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEnabled(true);
//        kdc.setRequired(false);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}	
}
