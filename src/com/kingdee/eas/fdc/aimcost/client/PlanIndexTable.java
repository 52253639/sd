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
import com.kingdee.bos.ctrl.common.util.ObjectUtil;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
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
import com.kingdee.bos.ctrl.swing.KDCheckBox;
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
import com.kingdee.eas.fdc.aimcost.ConstructPlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.ConstructPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.CustomPlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexFactory;
import com.kingdee.eas.fdc.aimcost.PlanIndexInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexTypeEnum;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CellBinder;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;

public class PlanIndexTable {
	private KDTable table=null;
	private String headId=null; 
	private CellBinder binder=null;
	private int dynRowBase=10;
	private AimMeasureCostEditUI measureCostEditU=null;
	private PlanIndexInfo planIndexInfo=null;
	private MeasureCostInfo costInfo=null;
	
	public PlanIndexTable(PlanIndexInfo planIndexInfo,AimMeasureCostEditUI measureCostEditUI) throws EASBizException, BOSException{
		table=new KDTable(23,1,0);
		table.setName("规划指标表");
		initCtrlListener();
		binder=new CellBinder();
		this.measureCostEditU=measureCostEditUI;
		isHasSubTable=measureCostEditUI.isUseCustomIndex();
		isPlanIndexLogic=measureCostEditUI.isPlanIndexLogic();
		isBuildPartLogic=measureCostEditUI.isBuildPartLogic();
		costInfo=measureCostEditUI.getEditData();
//		initTable(planIndexInfo);
		initTableForXU(planIndexInfo);
		initConstructTable(costInfo);
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
	
	public void initTableForXU(PlanIndexInfo info){
			((KDTTransferAction) table.getActionMap().get(KDTAction.PASTE)).setPasteMode(KDTEditHelper.VALUE);
			table.getHeadRow(0).getCell(0).setValue("规划指标表");
			table.getHeadRow(0).getCell(0).setUserObject("planIndex");
			table.getColumn(3).setWidth(120);
			table.getColumn(4).setWidth(120);
			table.getColumn(6).setWidth(120);
			table.getColumn(7).setWidth(120);
			table.getColumn(10).setWidth(200);
			table.getColumn(11).setWidth(150);
			table.getColumn(12).setWidth(170);
			table.getColumn(14).setWidth(150);
			initFixTableForXU(info);
			initDynTable(info);
			ICellEditor f7Editor = new KDTDefaultCellEditor(getF7productType());
			table.getColumn(1).setEditor(f7Editor);
			table.getCell(table.getRowCount()-1, 0).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
			table.getColumn(1).getStyleAttributes().setNumberFormat("@");
			
			KDTextField textField=new  KDTextField();
			textField.setMaxLength(255);
			table.getColumn(21).setEditor(PlanIndexTable.getCheckBoxEdit());
			table.getColumn(22).setEditor(new KDTDefaultCellEditor(textField));
//				reSetExpressions();
			KDTEditEvent e=new KDTEditEvent(table, null,null, dynRowBase+1, 1,false,10000);
			calc(e);
			if(isHasSubTable()){
				initSubTable(info);
			}
			
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
		table.getHeadRow(0).getCell(0).setUserObject("planIndex");
//		table.getHeadRow(0).getStyleAttributes().setBold(true);
//		table.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//		table.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
/*		for(int i=0;i<table.getColumnCount()-1;i++){
			table.getColumns().autoFitColumnWidth(i);
			if(i==0) continue;
//			table.getColumn(i).getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
//			table.getColumn(i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); 
		}*/
		
		table.getColumn(3).setWidth(150);
		table.getColumn(7).setWidth(150);
		table.getColumn(8).setWidth(120);
		table.getColumn(10).setWidth(50);
		table.getColumn(11).setWidth(50);
		
		table.getColumn(12).setWidth(50);
		table.getColumn(13).setWidth(50);
		table.getColumn(14).getStyleAttributes().setLocked(false);
		initFixTable(info);
		initDynTable(info);
		ICellEditor f7Editor = new KDTDefaultCellEditor(getF7productType());
		table.getColumn(1).setEditor(f7Editor);

		//设置风格
		Color lockColor=new Color(0xF0EDD9);
		StyleAttributes sa = table.getRow(3).getStyleAttributes();
		sa.setHorizontalAlign(HorizontalAlignment.CENTER);
		sa.setBackground(lockColor);
		sa = table.getRow(5).getStyleAttributes();
		sa.setHorizontalAlign(HorizontalAlignment.CENTER);
		sa.setBackground(lockColor);
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
		//这里隐藏了一列所以不是14是15 by hpw
		table.getColumn(15).setEditor(new KDTDefaultCellEditor(textField));
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
		binder.bindCell(table, 0, 1,"总占地面积(m2)"	,"totalContainArea",true);//jf总占地面积
		binder.bindCell(table, 0, 4,"建设用地面积(m2)"	,"buildArea",true);//jf建筑用地面积
		binder.bindCell(table, 0, 6,"总建筑面积(m2)"	,"totalBuildArea",true);
		binder.bindCell(table, 0, 8,"规划总建筑面积(m2)"	,"totalConstructArea",true);//jf
		
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
		table.getCell(8, 1).setValue("产品类型");
		table.getCell(8, 2).setValue("占地面积");
		table.getCell(8, 3).setValue("容积率");
		table.getCell(8, 4).setValue("规划建筑面积");
		table.getCell(8, 5).setValue("建筑面积");
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
		//不加了，隐藏 by hpw jf
//		table.getColumn(4).getStyleAttributes().setHided(true);
//		table.getRow(3).getStyleAttributes().setHided(true);
//		table.getRow(4).getStyleAttributes().setHided(true);
//		table.getRow(5).getStyleAttributes().setHided(true);
//		table.getRow(6).getStyleAttributes().setHided(true);
		
		binder.bindCell(table, 3, 0,"道路用地合计"	,"totalRoadArea",true,true);
		if(info!=null)
			binder.setCellsValue(info);
	}
	
	/**
	 * 旭辉目标成本测算的项目指标表
	 * @param info
	 */
	private void initFixTableForXU(PlanIndexInfo info){
		table.addRows(10);
		//绑定单元格
		binder.bindCell(table, 0, 1,"用地面积(m2)"	,"totalContainArea",true);//jf总占地面积
//		binder.bindCell(table, 0, 4,"用地性质"	,"landUsage",false);
//		binder.bindCell(table, 0, 6,"代征用面积(m2)"	,"requisitionArea",true);
//		binder.bindCell(table, 0, 8,"建设用地面积(m2)","buildArea",true);
//		binder.bindCell(table, 0, 10,"住宅用地面积(m2)","houseArea",true);
//		binder.bindCell(table, 0, 12,"商办用地面积(m2)","commBuildArea",true);
//		binder.bindCell(table, 0, 14,"其他用地面积(m2)","otherArea",true);
		
//		binder.bindCell(table, 1, 1,"容积率"	,"cubageRate",true);
//		binder.bindCell(table, 1, 4,"建筑密度"	,"buildDensity",true);
//		binder.bindCell(table, 1, 6,"绿地率"	,"greenAreaRate",true);
//		binder.bindCell(table, 1, 8,"计容积率面积(m2)(上限)","cubageRateArea",true);
//		binder.bindCell(table, 1, 10,"建筑限高(m)","maxHeight",true);
//		binder.bindCell(table, 1, 12,"停车位配比要求","parkingRequire",false);
		
		binder.bindCell(table, 1, 1,"建筑面积(m2)"	,"totalBuildArea",true);//总建筑面积(m2)
//		binder.bindCell(table, 2, 4,"计容积率面积(m2)(实际)"	,"rateCoverbuildArea",true);
//		binder.bindCell(table, 2, 6,"地上建筑面积(m2)","groundBuildArea",true);
//		binder.bindCell(table, 2, 8,"地下建筑面积(m2)","underGroundBuildArea",true);
////		binder.bindCell(table, 2, 8,"公共配套用房面积(m2)","publicSetHouse",true);
		
		binder.bindCell(table, 2, 1,"可售面积(m2)"	,"totalSellArea",true);
//		binder.bindCell(table, 3, 4,"测绘总建筑面积(m2)"	,"totalConstructArea",true);
//		binder.bindCell(table, 3, 6,"可售比"	,"saleRate",true);
//		
//		//空一行
//
//		binder.bindCell(table, 5, 1,"建筑物占地面积(m2)"	,"buildContainArea",true);
//		binder.bindCell(table, 5, 4,"景观面积(m2)"	,"sightSpotArea",true);
//		binder.bindCell(table, 5, 6,"精装修面积(m2)"	,"decorationArea",true);
//		binder.bindCell(table, 5, 8,"小区道路面积(m2)","roadArea",true);
//		
//		
//		binder.bindCell(table, 6, 1,"小区围墙长度(m)","lengthOfWall",true);
//		binder.bindCell(table, 6, 4,"大门数量"	,"doorNumber",false);
//		binder.bindCell(table, 6, 6,"硬地率"	,"rateOfHardFloor",true);
//		binder.bindCell(table, 6, 8,"地上停车位数量(个)"	,"groundparkCarNum",false);
//		binder.bindCell(table, 6, 10,"地下停车位数量(个)"	,"underGroundparkCarNum",false);
//		
//		binder.bindCell(table, 7, 1,"商业面积比%","commAreaRate",true);
//		binder.bindCell(table, 7, 4,"托儿所面积比%","nurseryAreaRate",true);
//		binder.bindCell(table, 7, 6,"精装房面积%","clothboundRoomRate",true);
//		binder.bindCell(table, 7, 8,"其它政府要求送的面积(m2)","giveArea",true);
//		
//		binder.bindCell(table, 5, 8,"地下室面积(m2)"	,"basementArea",true);
		
		setLockAndBk(0,3);
		setLockAndBk(1,3);
		setLockAndBk(2,3);
		table.getRow(3).getStyleAttributes().setHided(true);
		table.getRow(4).getStyleAttributes().setHided(true);
		table.getRow(5).getStyleAttributes().setHided(true);
		table.getRow(6).getStyleAttributes().setHided(true);
		table.getRow(7).getStyleAttributes().setHided(true);
				
		//合并与锁定
		KDTMergeManager mm = table.getMergeManager();
		//合并前8行的（0，1）列，（3，4）列
		for(int i=0;i<8;i++){
			mm.mergeBlock(i, 0, i, 1, KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(i, 3, i, 4, KDTMergeManager.SPECIFY_MERGE);
		}
		//第五行，第八行空行
		mm.mergeBlock(4, 0, 4, 16);
		mm.mergeBlock(8, 0, 8, 16);
		setLockAndBk(0, 16);
		setLockAndBk(1, 14);
		setLockAndBk(2, 10);
		setLockAndBk(3, 8);
		setLockAndBk(5, 10);
		setLockAndBk(6, 12);
		setLockAndBk(7, 10);
		table.getRow(4).getStyleAttributes().setLocked(true);
		table.getRow(4).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		table.getRow(8).getStyleAttributes().setLocked(true);
		table.getRow(8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		
		//空一行,设置产品头
		table.getCell(9, 0).setValue("产品构成");
		table.getCell(9, 1).setValue("产品类型");
		table.getCell(9, 2).setValue("占地面积(m2)");
		table.getCell(9, 3).setValue("地上建筑面积(m2)");
		table.getCell(9, 4).setValue("地下建筑面积(m2)");
		table.getCell(9, 5).setValue("建筑面积(m2)");
		
		table.getCell(9, 6).setValue("赠送建筑面积(m2)");
		table.getCell(9, 7).setValue("实际建设面积(m2)");
		
		table.getCell(9, 8).setValue("可售面积(m2)");
		
		table.getCell(9, 9).setValue("可租面积(m2)");
		table.getCell(9, 10).setValue("地上可租售面积（产权面积）(m2)");
		table.getCell(9, 11).setValue("公共区域精装面积(m2)");
		table.getCell(9, 12).setValue("精装交付的可售面积面积(m2)");
		
		table.getCell(9, 13).setValue("产品比例");
		table.getCell(9, 14).setValue("平均每户面积(m2)");
		table.getCell(9, 15).setValue("电梯");
		table.getCell(9, 16).setValue("层数");
		table.getCell(9, 17).setValue("层高");
		table.getCell(9, 18).setValue("幢数");
		table.getCell(9, 19).setValue("单元数");
		table.getCell(9, 20).setValue("户数");
		table.getCell(9, 21).setValue("是否分摊");
		table.getCell(9, 22).setValue("备注");
		table.getRow(9).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		table.getRow(9).getStyleAttributes().setLocked(true);
		table.getRow(9).getStyleAttributes().setBackground(new Color(0xF0EDD9));
		//格式化
		table.getCell(1, 5).getStyleAttributes().setNumberFormat("0.00%");
		table.getCell(1, 7).getStyleAttributes().setNumberFormat("0.00%");
		table.getCell(6, 7).getStyleAttributes().setNumberFormat("0.00%");
		table.getCell(7, 2).getStyleAttributes().setNumberFormat("0.00%");
		table.getCell(7, 5).getStyleAttributes().setNumberFormat("0.00%");
		table.getCell(7, 7).getStyleAttributes().setNumberFormat("0.00%");
		
		table.getCell(6, 5).setEditor(CellBinder.getCellIntegerNumberEdit());
		table.getCell(6, 5).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(6, 9).setEditor(CellBinder.getCellIntegerNumberEdit());
		table.getCell(6, 9).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getCell(6, 11).setEditor(CellBinder.getCellIntegerNumberEdit());
		table.getCell(6, 11).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		//不能修改的值
//		table.getCell(0, 7).getStyleAttributes().setLocked(true);
//		table.getCell(0, 7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//		table.getCell(0, 2).getStyleAttributes().setLocked(true);
//		table.getCell(0, 2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//		table.getCell(1,2).getStyleAttributes().setLocked(true);
//		table.getCell(1,2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//		table.getCell(1, 5).getStyleAttributes().setLocked(true);
//		table.getCell(1, 5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//		table.getCell(1, 7).getStyleAttributes().setLocked(true);
//		table.getCell(1, 7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		
		if(info!=null)
			binder.setCellsValue(info);
	}
	private void  setLockAndBk(int row, int col ){
		int i = row;
		for(int j = col; j < 17 ;j++){
			table.getCell(i, j).getStyleAttributes().setLocked(true);
			table.getCell(i, j).getStyleAttributes().setBackground(new Color(15789529));
		}
		table.getMergeManager().mergeBlock(i, col, i, 22);
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
			PlanIndexEntryInfo entry=info.getEntrys().get(i);
			//编辑，查看时 没有必要显示空行 20120509
			if(entry.getType()==lastType&&lastType!=null &&entry.getProduct() == null){
				continue;
			}
			IRow row=table.addRow(rows);
			if(entry.getType()!=lastType&&lastType!=null){
				row.getCell(0).setValue(lastType);
				row.getCell(1).setValue("小计");
				loadRow(null, row);
				row=table.addRow(++rows);
				start=rows;
				if(entry.getType()==PlanIndexTypeEnum.parking){
					mm.mergeBlock(rows, 0, rows, 1);
					row.getCell(2).setValue("占地面积(m2)");
					row.getCell(3).setValue("地上建筑面积(m2)");
					row.getCell(4).setValue("地下建筑面积(m2)");//jf
					row.getCell(5).setValue("建筑面积(m2)");
					row.getCell(8).setValue("可售面积(m2)");
					row.getCell(13).setValue("车位数(个)");
					row.getCell(14).setValue("可售车位数(个)");
					row.getCell(15).setValue("车位数可售比");
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
		if(isSubTotalRow(row)){
			sa.getNumberFormat();
			sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
			sa.setLocked(true);
			sa.setBackground(FDCTableHelper.yearTotalColor);
			row.getCell(0).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			row.getCell(1).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			return;
		}else if(isTotalRow(row)){
			sa.setNumberFormat("#,##0.00;-#,##0.00");
			sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
			sa.setLocked(true);
			sa.setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
			return;
		}
		if(entry==null){
			row.getCell(21).setValue(Boolean.FALSE);
			row.getCell(5).getStyleAttributes().setLocked(true);
			row.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
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
			row.getCell(3).setValue(entry.getGroundArea());
			row.getCell(4).setValue(entry.getUnderGroundArea());
			row.getCell(5).setValue(entry.getBuildArea());
			
			row.getCell(5).getStyleAttributes().setLocked(true);
			row.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			
			row.getCell(6).setValue(entry.getGivingArea());
			row.getCell(7).setValue(entry.getConstructArea());
			
			row.getCell(8).setValue(entry.getSellArea());
			
			row.getCell(9).setValue(entry.getRentArea());
			row.getCell(10).setValue(entry.getGroundRentArea());
			row.getCell(11).setValue(entry.getPublicArea());
			row.getCell(12).setValue(entry.getDeliveryArea());
			
			row.getCell(13).setValue(entry.getProductRate());
			row.getCell(14).setValue(entry.getUnitArea());
			if(entry.getElevators()>0){
				row.getCell(15).setValue(new Integer(entry.getElevators()));
			}
			if((PlanIndexTypeEnum)row.getCell(0).getValue()==PlanIndexTypeEnum.house || (PlanIndexTypeEnum)row.getCell(0).getValue()==PlanIndexTypeEnum.business
					|| (PlanIndexTypeEnum)row.getCell(0).getValue()==PlanIndexTypeEnum.publicBuild){
				if(entry.getFloors()>0){
					row.getCell(16).setValue(new Integer(entry.getFloors()));
				}
				row.getCell(17).setValue(entry.getFloorHeight());
				//幢数
				row.getCell(18).setValue(new Integer(entry.getBuild()));
				row.getCell(19).setValue(entry.getUnits());
				row.getCell(20).setValue(entry.getDoors());
				row.getCell(22).setValue(entry.getDesc());
			}
			row.getCell(21).setValue(Boolean.valueOf(entry.isIsSplit()));
		}
		if((PlanIndexTypeEnum)row.getCell(0).getValue()==PlanIndexTypeEnum.house || (PlanIndexTypeEnum)row.getCell(0).getValue()==PlanIndexTypeEnum.business
				|| (PlanIndexTypeEnum)row.getCell(0).getValue()==PlanIndexTypeEnum.publicBuild){
			row.getCell(13).getStyleAttributes().setLocked(true);
			row.getCell(13).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			
			row.getCell(14).getStyleAttributes().setLocked(true);
			row.getCell(14).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		}else{
			row.getCell(6).getStyleAttributes().setLocked(true);
			row.getCell(6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			row.getCell(7).getStyleAttributes().setLocked(true);
			row.getCell(7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			row.getCell(9).getStyleAttributes().setLocked(true);
			row.getCell(9).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			row.getCell(10).getStyleAttributes().setLocked(true);
			row.getCell(10).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			row.getCell(11).getStyleAttributes().setLocked(true);
			row.getCell(11).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			row.getCell(12).getStyleAttributes().setLocked(true);
			row.getCell(12).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			for(int i=16;i<table.getColumnCount()-2;i++){
				row.getCell(i).getStyleAttributes().setLocked(true);
				row.getCell(i).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			}
		}
		for(int i=2;i<21;i++){
			sa=row.getCell(i).getStyleAttributes();
			sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
			
			if((PlanIndexTypeEnum)row.getCell(0).getValue()==PlanIndexTypeEnum.house || (PlanIndexTypeEnum)row.getCell(0).getValue()==PlanIndexTypeEnum.business
					|| (PlanIndexTypeEnum)row.getCell(0).getValue()==PlanIndexTypeEnum.publicBuild){
				if(i==15||i==16||i==18||i==19||i==20){
					row.getCell(i).setEditor(CellBinder.getCellIntegerNumberEdit());
				}else{
					row.getCell(i).setEditor(CellBinder.getCellNumberEdit());
					sa.setNumberFormat("#,##0.00;-#,##0.00");
				}
			}else{
				if(i==13||i==14||i==15){
					row.getCell(i).setEditor(CellBinder.getCellIntegerNumberEdit());
				}else{
					row.getCell(i).setEditor(CellBinder.getCellNumberEdit());
					sa.setNumberFormat("#,##0.00;-#,##0.00");
				}
			}
		}
		row.getCell(0).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		row.getCell(1).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
	}
	
	public static KDTDefaultCellEditor getCheckBoxEdit(){
		KDCheckBox kdc = new KDCheckBox();
        kdc.setVisible(true);
        kdc.setEnabled(true);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
	
	public KDTDefaultCellEditor getCellEditor(){
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor();
		KDFormattedTextField formatText = new KDFormattedTextField();
		formatText.setPrecision(8);
		cellEditor = new KDTDefaultCellEditor(formatText);
		return cellEditor;
	}
	
	public void addConstrIndexRow(ActionEvent e){
		int index = constrTable.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			constrTable.addRow();
		}else{
			constrTable.addRow(index+1);
		}
	}
	public void deleteConstrIndexRow(ActionEvent e){
		int index = constrTable.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			return;
		}
		constrTable.removeRow(index);
		
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
						//update by david_yang R110407-289 2011.04.18
						if(product!=null){
//						if(getPlanIndexEntryInfo(product.getId().toString())!=null){
							measureCostEditU.deleteProductTypeTable(product);
							if(measureCostEditU.getplTables().getSelectedIndex()!=2){
								measureCostEditU.getplTables().setSelectedIndex(2);
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
//				row.getCell(2).setExpressions("=ROUND(E"+(i+1)+"/D"+(i+1)+",2)");
				//建筑面子 = 地上建筑面积 + 地下建筑面积
				row.getCell(5).setExpressions("=sum(C"+(i+1)+":D"+(i+1));
				//产品比例=可售面积/可售面积小计（住宅）
				row.getCell(6).setExpressions("=ROUND(F"+(i+1)+"/sum(F"+(dynRowBase+1)+":F"+(houseSubIndex)+"),2)");
				//平均第户面积=可售面积/户数
				row.getCell(7).setExpressions("=ROUND(F"+(i+1)+"/J"+(i+1)+",2)");
				row.getCell(7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
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
//				row.getCell(2).setExpressions("=ROUND(E"+(i+1)+"/D"+(i+1)+",2)");
				//建筑面子 = 地上建筑面积 + 地下建筑面积
				row.getCell(5).setExpressions("=sum(C"+(i+1)+":D"+(i+1));
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
//				row.getCell(2).setExpressions("=ROUND(E"+(i+1)+"/D"+(i+1)+",2)");
				//建筑面子 = 地上建筑面积 + 地下建筑面积
				row.getCell(5).setExpressions("=sum(C"+(i+1)+":D"+(i+1));

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
			for(int j=2;j<7;j++){
				char c=(char)('A'+j);
				String exp=null;				
				exp="=sum("+c+(publicSubIndex+2)+":"+c+(parkingSubIndex)+")";
				parkRow.getCell(j).setExpressions(exp);
			}
			
			for(int i=parkingSubIndex+1;i<table.getRowCount()-2;i++){
				IRow row=getTable().getRow(i);
				//占地面积=建筑面积/容积率
//				row.getCell(2).setExpressions("=ROUND(E"+(i+1)+"/D"+(i+1)+",2)");
				//建筑面子 = 地上建筑面积 + 地下建筑面积
				row.getCell(5).setExpressions("=sum(C"+(i+1)+":D"+(i+1));

			}
		}else{
			IRow parkRow=getTable().getRow(parkingSubIndex);
			for(int j=2;j<7;j++){
				parkRow.getCell(j).setValue(null);
				parkRow.getCell(j).setExpressions("");
			}
		}
		
		//合计
		IRow row=table.getRow(table.getRowCount()-1);
		row.getCell(0).setValue("合计");
		
		//改在按小计列统计
		String exp="=ROUND(sum(";
//		String expSellArea="=ROUND(sum(";
		boolean isPark=false;
		for(int i=dynRowBase;i<table.getRowCount()-1;i++){
			if(table.getCell(i, 1).getValue()!=null&&table.getCell(i, 1).getValue().equals("小计")){
				continue;
			}				
//			if(table.getCell(i, 2).getValue()!=null&&table.getCell(i, 2).getValue().equals("占地面积")){
//				isPark=true;
//				continue;
//			}
			exp=exp+"A"+(i+1)+",";
//			expSellArea=expSellArea+"A"+(i+1)+",";
		}
		
		exp=exp.substring(0,exp.length()-1)+"),2)"	;
//		expSellArea=expSellArea.substring(0,expSellArea.length()-1)+"),2)"	;
		//可售面积单独汇总
		for(int j=2;j<=5;j++){
//			if(j==3) continue;//容积率与车位数不汇总
			char c=(char)('A'+j);
//			if(j==5){
//				row.getCell(j).setExpressions(expSellArea.replaceAll("A", c+""));
//			}else{
				row.getCell(j).setExpressions(exp.replaceAll("A", c+""));
//			}
		}

		//总建筑面积
//		table.getCell(0, 6).setExpressions("=E"+table.getRowCount());
		//建筑密度=建筑物占地面积/建筑用地面积；		
		//容积率=计容积率面积/建筑用地面积
		table.getCell(3, 7).setExpressions("=ROUND(C2/F1,4)");
//		table.getCell(1, 4).getStyleAttributes().setNumberFormat("0.00%");
//		table.getCell(1, 6).getStyleAttributes().setNumberFormat("0.00%");
		table.getCell(3, 5).setExpressions("=ROUND(C3/F1,2)");
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
//						entry.setCubageRate((BigDecimal)row.getCell(3).getValue());
						entry.setGroundArea((BigDecimal)row.getCell(3).getValue());
					}
					if(row.getCell(4).getValue() instanceof BigDecimal){//jf
						entry.setUnderGroundArea((BigDecimal)row.getCell(4).getValue());
//						entry.setConstructArea((BigDecimal)row.getCell(4).getValue());
					}
					if(row.getCell(5).getValue() instanceof BigDecimal){
						entry.setBuildArea((BigDecimal)row.getCell(5).getValue());
					}
					if(row.getCell(6).getValue() instanceof BigDecimal){
						entry.setGivingArea((BigDecimal)row.getCell(6).getValue());
					}
					if(row.getCell(7).getValue() instanceof BigDecimal){
						entry.setConstructArea((BigDecimal)row.getCell(7).getValue());
					}
					
					if(row.getCell(8).getValue() instanceof BigDecimal){
						entry.setSellArea((BigDecimal)row.getCell(8).getValue());
					}
					
					if(row.getCell(9).getValue() instanceof BigDecimal){
						entry.setRentArea((BigDecimal)row.getCell(9).getValue());
					}
					if(row.getCell(10).getValue() instanceof BigDecimal){
						entry.setGroundRentArea((BigDecimal)row.getCell(10).getValue());
					}
					if(row.getCell(11).getValue() instanceof BigDecimal){
						entry.setPublicArea((BigDecimal)row.getCell(11).getValue());
					}
					if(row.getCell(12).getValue() instanceof BigDecimal){
						entry.setDeliveryArea((BigDecimal)row.getCell(12).getValue());
					}
					
					if(FDCHelper.toBigDecimal(row.getCell(13).getValue()) instanceof BigDecimal){
						entry.setProductRate(FDCHelper.toBigDecimal(row.getCell(13).getValue()));//车位个数，产品比例
					}
					if(FDCHelper.toBigDecimal(row.getCell(14).getValue()) instanceof BigDecimal){
						entry.setUnitArea(FDCHelper.toBigDecimal(row.getCell(14).getValue()));
//						entry.setElevators(((Integer)row.getCell(8).getValue()).intValue());//电梯、可售车位数(个)
					}
					if(row.getCell(15).getValue() instanceof Integer){
//						entry.setUnitArea(FDCHelper.toBigDecimal(row.getCell(9).getValue()));//平均每户面积、车位可售比
						entry.setElevators(((Integer)row.getCell(15).getValue()).intValue());
					}
					if(row.getCell(16).getValue() instanceof Integer){
						entry.setFloors(((Integer)row.getCell(16).getValue()).intValue());
					}
					if(row.getCell(17).getValue() instanceof BigDecimal){
						entry.setFloorHeight((BigDecimal)row.getCell(17).getValue());
					}
				
					if(row.getCell(18).getValue() instanceof Integer){
						entry.setBuild(((Integer)row.getCell(18).getValue()).intValue());
					}
					if(row.getCell(19).getValue() instanceof Integer){
						entry.setUnits(new BigDecimal(row.getCell(19).getValue().toString()));
					}
					if(row.getCell(20).getValue() instanceof Integer){
						entry.setDoors(new BigDecimal(row.getCell(20).getValue().toString()));
					}
					if(row.getCell(21).getValue() instanceof Boolean){
						entry.setIsSplit(((Boolean)row.getCell(21).getValue()).booleanValue());
					}else{
						entry.setIsSplit(false);
					}
					entry.setDesc((String)row.getCell(22).getValue());
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
		measureCostEditU.setDataChange(true);
	}
	private KDBizPromptBox f7productType=null; 
	public KDBizPromptBox  getF7productType(){
		f7productType = new KDBizPromptBox()
		{
			protected Object stringToValue(String t) {
				 Object obj= super.stringToValue(t);
				 if(obj instanceof  IObjectValue){
					 return obj;
				 }else{
					 return t;
				 }
				 
			}
		};
		f7productType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		view.setFilter(filter);
		f7productType.setEntityViewInfo(view);
		f7productType.setEditable(false);
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
						MsgBox.showWarning(measureCostEditU, "该产品类型已经设置了指标");
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
				if(objNew!=null&&objNew.equals(f7productType.getUserObject())){
					return;
				}
				PlanIndexInfo info=null;
				if(objNew instanceof ProductTypeInfo){
					table.getCell(rowIndex,1).setValue(objNew);
					info=getPlanIndexInfo();
					measureCostEditU.addProductTypeTable((ProductTypeInfo)objNew);
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
					if(count==1) measureCostEditU.deleteProductTypeTable((ProductTypeInfo)objOld);
				}
				
			}
		});
		
		return f7productType;
	}

	private void table_tableClicked(KDTMouseEvent e){
		if(measureCostEditU==null||measureCostEditU.getOprtState().equals(OprtState.VIEW)){
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
	//----------第1行-------------------
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
	
	//----------第2行-------------------
	/**
	 * 总可售面积（m2）
	 */
	private ICell totalSellAreaCell = null;
	/**
	 * 总地上面积（m2）
	 */
	private ICell totalGroundAreaCell = null;
	/**
	 * 总地下面积（m2）
	 */
	private ICell totalUnderGroundAreaCell = null;
	//----------第3行-------------------
	/**
	 * 建筑物占地面积(m2)
	 */
	private ICell buildContailAreaCell=null;	
	
	/**
	 * 公共配套用户面积(m2)
	 */
	private ICell publicSetHouseCell=null;
	/**
	 * 绿地率
	 */
	private ICell greenAreaRateCell=null;
	//----------第4行-------------------
	/**
	 * 计容积率面积(m2)
	 */
	private ICell cubageRateAreaCell=null;
	/**
	 * 容积率
	 */
	private ICell cubageRateCell=null;
	/**
	 * 建筑密度
	 */
	private ICell buildDensityCell=null;
	//----------第5行-------------------
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
	 * 地上建筑面积
	 */
	private int groundAreaColummnIndex=3;
	
	/**
	 * 地下建筑面积
	 */
	private int underGroundAreaColummnIndex=4;
	/**
	 * 建筑面积
	 */
	private int buildAreaColummnIndex=5;
	
	/**
	 * 赠送建筑面积
	 */
	private int givingAreaColummnIndex=6;
	
	/**
	 * 实际建设面积
	 */
	private int constructAreaColummnIndex=7;
	/**
	 * 可售面积
	 */
	private int sellAreaColummnIndex=8;
	/**
	 * 可租面积
	 */
	private int rentAreaColummnIndex=9;
	/**
	 * 地上可租售面积
	 */
	private int groundRentAreaColummnIndex=10;
	/**
	 * 公共区域精装面积
	 */
	private int publicAreaColummnIndex=11;
	/**
	 * 精装交付的可售面积面积
	 */
	private int deliveryAreaColummnIndex=12;
	/**
	 * 产品比例
	 */
	private int productRateColummnIndex=13;
	/**
	 * 平均每户面积
	 */
	private int avgHuColummnIndex=14;
	/**
	 * 电梯
	 */
	private int elevatorsIndex=15;
	/**
	 * 楼层
	 */
	private int floorsIndex=16;
	/**
	 * 层高
	 */
	private int floorHeightIndex=17;
	
	/**
	 * 幢数
	 */
	private int buildColummnIndex=18;
	/**
	 * 单元数
	 */
	private int unitColummnIndex=19;
	/**
	 * 户数
	 */
	private int huColummnIndex=20;
	/**
	 * 是否分摊
	 */
	private int splitColummnIndex=21;
	/**
	 * 备注
	 */
	private int descColummnIndex=22;
	/**
	 * 车位数
	 */
	private int parksColummnIndex=13;
	/**
	 * 可售车位数(个)
	 */
	private int sellParksColummnIndex=14;
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
		//占地面积
		totalContainAreaCell=table.getCell(0, 2);
		//建筑面积
		totalBuildAreaCell=table.getCell(1,2);	
		
		totalSellAreaCell = table.getCell(2, 2);
		
		totalGroundAreaCell = table.getCell(2, 7);
		totalUnderGroundAreaCell = table.getCell(2, 9);
		
		buildAreaCell=table.getCell(2, 2);	
//		buildContailAreaCell=table.getCell(2, 2);	
		greenAreaRateCell=table.getCell(2, 5);
		publicSetHouseCell=table.getCell(2, 7);
		
		cubageRateAreaCell=table.getCell(3, 2);
		cubageRateCell=table.getCell(3,5);
		buildDensityCell=table.getCell(3, 7);
		
		
//		totalRoadAreaCell=table.getCell(4, 0);
//		pitchRoadCell=table.getCell(4, 2);
//		concreteRoadCell=table.getCell(4,3);
//		hardRoadCell=table.getCell(4,5);
//		if(isPlanIndexLogic()){
//			hardSquareCell=table.getCell(6,7);
//			hardManRoadCell=table.getCell(6,8);
//		}else{
//			hardSquareCell=table.getCell(4,6);
//			hardManRoadCell=table.getCell(4,7);
//		}
//		totalGreenAreaCell=table.getCell(6,0);
//		importPubGreenAreaCell=table.getCell(6,2);
//		houseGreenAreaCell=table.getCell(6,3);
//		privateGardenCell=table.getCell(6,5);
//		warterViewAreaCell=table.getCell(6,6);
		
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
//		calcArea();
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
//			BigDecimal oldGreenAmt=FDCHelper.toBigDecimal(totalGreenAreaCell.getValue());
		// 修改20111104
//			BigDecimal oldContainAmt=FDCHelper.toBigDecimal(totalContainAreaCell.getValue());
//			if(e!=null&&e.getRowIndex()==totalContainAreaCell.getRowIndex()&&e.getColIndex()==totalContainAreaCell.getColumnIndex()){
//				oldContainAmt=FDCHelper.toBigDecimal(e.getOldValue());
//			}
//			BigDecimal buildAreaAmt=FDCHelper.toBigDecimal(buildAreaCell.getValue());
//			if(buildAreaAmt.signum()==0){
//				buildDensityCell.setValue(null);
//				cubageRateCell.setValue(null);
//			}else{
//				buildDensityCell.setValue(FDCHelper.divide(buildContailAreaCell.getValue(), buildAreaAmt, 4, BigDecimal.ROUND_HALF_UP));
//				cubageRateCell.setValue(FDCHelper.divide(cubageRateAreaCell.getValue(), buildAreaAmt, 2, BigDecimal.ROUND_HALF_UP));
//			}
			
			//----
//			BigDecimal amt=FDCHelper.ZERO;
//			amt=FDCNumberHelper.add(amt,pitchRoadCell.getValue());
//			amt=FDCNumberHelper.add(amt,concreteRoadCell.getValue());
//			amt=FDCNumberHelper.add(amt, hardRoadCell.getValue());
//			if(!isPlanIndexLogic()){
//				amt=FDCNumberHelper.add(amt, hardSquareCell.getValue());
//				amt=FDCNumberHelper.add(amt, hardManRoadCell.getValue());
//			}
//			totalRoadAreaCell.setValue(amt);
//			amt=FDCHelper.ZERO;
//			amt=FDCNumberHelper.add(amt,importPubGreenAreaCell.getValue());
//			amt=FDCNumberHelper.add(amt,houseGreenAreaCell.getValue());
//			amt=FDCNumberHelper.add(amt, privateGardenCell.getValue());
//			amt=FDCNumberHelper.add(amt, warterViewAreaCell.getValue());
//			if(isPlanIndexLogic()){
//				amt=FDCNumberHelper.add(amt, hardSquareCell.getValue());
//				amt=FDCNumberHelper.add(amt, hardManRoadCell.getValue());
//			}
////			totalGreenAreaCell.setValue(amt);
//			if(amt.compareTo(oldGreenAmt)!=0||FDCHelper.toBigDecimal(totalContainAreaCell.getValue()).compareTo(oldContainAmt)!=0){
//				greenAreaRateCell.setValue(FDCNumberHelper.divide(amt, totalContainAreaCell.getValue(),4,BigDecimal.ROUND_HALF_UP));
//			}
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
			//占地面积
			BigDecimal oldtotalContainArea =FDCHelper.toBigDecimal(table.getCell(totalIndex, containAreaColummnIndex).getValue());
			BigDecimal oldSellBuildArea=FDCHelper.toBigDecimal(table.getCell(totalIndex, sellAreaColummnIndex).getValue());
			BigDecimal oldtotalGroundArea=FDCHelper.toBigDecimal(table.getCell(totalIndex, groundAreaColummnIndex).getValue());
			BigDecimal oldtotalUnderGroundArea=FDCHelper.toBigDecimal(table.getCell(totalIndex, underGroundAreaColummnIndex).getValue());
			 
//			BigDecimal oldSellBuildArea=FDCHelper.toBigDecimal(table.getCell(totalIndex, sellAreaColummnIndex).getValue());
			boolean isChange = true;
			if(e.getOldValue() instanceof BigDecimal && e.getValue() instanceof BigDecimal){
				BigDecimal old = (BigDecimal)e.getOldValue();
				BigDecimal value = (BigDecimal)e.getValue();
				if(old != null && value != null){
					isChange = !(old.floatValue() == value.floatValue());
				}
			}
			//住宅
			Map sumMap=new HashMap();
			int currentRow = e.getRowIndex();
			for(int i=dynRowBase;i<houseSubIndex;i++){
				if( i == currentRow && isChange){
					int changedCol = e.getColIndex();
					BigDecimal buildArea = FDCNumberHelper.toBigDecimal(
							table.getCell(i, buildAreaColummnIndex).getValue());
					if(changedCol == 2){
						
					}else if(changedCol == 3){
						BigDecimal tmpConArea = FDCNumberHelper.add(table.getCell(i, groundAreaColummnIndex).getValue()
								, table.getCell(i, underGroundAreaColummnIndex).getValue());
							table.getCell(i, buildAreaColummnIndex).setValue(tmpConArea!=null?tmpConArea:FDCHelper.ZERO);
					}else if(changedCol == 4){
						BigDecimal tmpConArea = FDCNumberHelper.add(table.getCell(i, groundAreaColummnIndex).getValue()
								, table.getCell(i, underGroundAreaColummnIndex).getValue());
						table.getCell(i, buildAreaColummnIndex).setValue(tmpConArea!=null?tmpConArea:FDCHelper.ZERO);
					}
					BigDecimal tmp = FDCHelper.ZERO;
					tmp=FDCNumberHelper.divide(table.getCell(i, sellAreaColummnIndex).getValue(), table.getCell(i, huColummnIndex).getValue());
					table.getCell(i, avgHuColummnIndex).setValue(tmp);
				}
				sumMap.put("containArea", FDCNumberHelper.add(sumMap.get("containArea"), table.getCell(i, containAreaColummnIndex).getValue()));
				sumMap.put("groundArea", FDCNumberHelper.add(sumMap.get("groundArea"), table.getCell(i, groundAreaColummnIndex).getValue()));
				sumMap.put("underGroundArea", FDCNumberHelper.add(sumMap.get("underGroundArea"), table.getCell(i, underGroundAreaColummnIndex).getValue()));
				sumMap.put("buildArea", FDCNumberHelper.add(sumMap.get("buildArea"), table.getCell(i, buildAreaColummnIndex).getValue()));
				
				sumMap.put("givingArea", FDCNumberHelper.add(sumMap.get("givingArea"), table.getCell(i, givingAreaColummnIndex).getValue()));
				sumMap.put("constructArea", FDCNumberHelper.add(sumMap.get("constructArea"), table.getCell(i, constructAreaColummnIndex).getValue()));
				
				sumMap.put("sellArea", FDCNumberHelper.add(sumMap.get("sellArea"), table.getCell(i, sellAreaColummnIndex).getValue()));
				
				sumMap.put("rentArea", FDCNumberHelper.add(sumMap.get("rentArea"), table.getCell(i, rentAreaColummnIndex).getValue()));
				sumMap.put("groundRentArea", FDCNumberHelper.add(sumMap.get("groundRentArea"), table.getCell(i, groundRentAreaColummnIndex).getValue()));
				sumMap.put("publicArea", FDCNumberHelper.add(sumMap.get("publicArea"), table.getCell(i, publicAreaColummnIndex).getValue()));
				sumMap.put("deliveryArea", FDCNumberHelper.add(sumMap.get("deliveryArea"), table.getCell(i, deliveryAreaColummnIndex).getValue()));
				
				sumMap.put("elevators", FDCNumberHelper.add(sumMap.get("elevators"), table.getCell(i, elevatorsIndex).getValue()));
				sumMap.put("floors", FDCNumberHelper.add(sumMap.get("floors"), table.getCell(i, floorsIndex).getValue()));
				sumMap.put("floorHeight", FDCNumberHelper.add(sumMap.get("floorHeight"), table.getCell(i, floorHeightIndex).getValue()));
				sumMap.put("unit", FDCNumberHelper.add(sumMap.get("unit"), table.getCell(i, unitColummnIndex).getValue()));
				sumMap.put("hu", FDCNumberHelper.add(sumMap.get("hu"), table.getCell(i, huColummnIndex).getValue()));
				sumMap.put("build", FDCNumberHelper.add(sumMap.get("build"), table.getCell(i, buildColummnIndex).getValue()));
				
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
			//地上建筑面积
			table.getCell(houseSubIndex, groundAreaColummnIndex).setValue(sumMap.get("groundArea"));
			table.getCell(houseSubIndex, underGroundAreaColummnIndex).setValue(sumMap.get("underGroundArea"));
			table.getCell(houseSubIndex, buildColummnIndex).setValue(sumMap.get("build"));
			
			table.getCell(houseSubIndex, buildAreaColummnIndex).setValue(sumMap.get("buildArea"));
			
			table.getCell(houseSubIndex, givingAreaColummnIndex).setValue(sumMap.get("givingArea"));
			table.getCell(houseSubIndex, constructAreaColummnIndex).setValue(sumMap.get("constructArea"));
			
			table.getCell(houseSubIndex, sellAreaColummnIndex).setValue(sumMap.get("sellArea"));
			
			table.getCell(houseSubIndex, rentAreaColummnIndex).setValue(sumMap.get("rentArea"));
			table.getCell(houseSubIndex, groundRentAreaColummnIndex).setValue(sumMap.get("groundRentArea"));
			table.getCell(houseSubIndex, publicAreaColummnIndex).setValue(sumMap.get("publicArea"));
			table.getCell(houseSubIndex, deliveryAreaColummnIndex).setValue(sumMap.get("deliveryArea"));
			
			table.getCell(houseSubIndex, elevatorsIndex).setValue(sumMap.get("elevators"));
//			table.getCell(houseSubIndex, floorsIndex).setValue(sumMap.get("floors"));
//			table.getCell(houseSubIndex, floorHeightIndex).setValue(sumMap.get("floorHeight"));
			table.getCell(houseSubIndex, unitColummnIndex).setValue(sumMap.get("unit"));
			table.getCell(houseSubIndex, huColummnIndex).setValue(sumMap.get("hu"));
			table.getCell(houseSubIndex, productRateColummnIndex).setValue(FDCNumberHelper.ONE);
			//容积率小计＝建筑面积小计/占地面积小计
//			table.getCell(houseSubIndex, cubageRateColummnIndex).setValue(FDCNumberHelper.divide(sumMap.get("buildArea"), sumMap.get("containArea")));
			//平均每户面积小计＝可售面积小计/户数小计
			table.getCell(houseSubIndex, avgHuColummnIndex).setValue(FDCNumberHelper.divide(sumMap.get("sellArea"), sumMap.get("hu")));
			
			//商业
			sumMap.clear();
			for(int i=houseSubIndex+1;i<businessSubIndex;i++){
				if( i == currentRow && isChange){
					BigDecimal tmp = FDCHelper.ZERO;
					int changedCol = e.getColIndex();
					BigDecimal buildArea = FDCNumberHelper.toBigDecimal(
							table.getCell(i, buildAreaColummnIndex).getValue());
					if(changedCol == 2){
					}else if(changedCol == 3){
						BigDecimal tmpConArea = FDCNumberHelper.add(table.getCell(i, groundAreaColummnIndex).getValue()
								, table.getCell(i, underGroundAreaColummnIndex).getValue());
						table.getCell(i, buildAreaColummnIndex).setValue(tmpConArea!=null?tmpConArea:FDCHelper.ZERO);
					}else if(changedCol == 4){
						BigDecimal tmpConArea = FDCNumberHelper.add(table.getCell(i, groundAreaColummnIndex).getValue()
								, table.getCell(i, underGroundAreaColummnIndex).getValue());
						table.getCell(i, buildAreaColummnIndex).setValue(tmpConArea!=null?tmpConArea:FDCHelper.ZERO);
					}
					tmp=FDCNumberHelper.divide(table.getCell(i, sellAreaColummnIndex).getValue(), table.getCell(i, huColummnIndex).getValue());
					table.getCell(i, avgHuColummnIndex).setValue(tmp);
				}
				sumMap.put("containArea", FDCNumberHelper.add(sumMap.get("containArea"), table.getCell(i, containAreaColummnIndex).getValue()));
				sumMap.put("groundArea", FDCNumberHelper.add(sumMap.get("groundArea"), table.getCell(i, groundAreaColummnIndex).getValue()));
				sumMap.put("underGroundArea", FDCNumberHelper.add(sumMap.get("underGroundArea"), table.getCell(i, underGroundAreaColummnIndex).getValue()));
				sumMap.put("buildArea", FDCNumberHelper.add(sumMap.get("buildArea"), table.getCell(i, buildAreaColummnIndex).getValue()));
				
				sumMap.put("givingArea", FDCNumberHelper.add(sumMap.get("givingArea"), table.getCell(i, givingAreaColummnIndex).getValue()));
				sumMap.put("constructArea", FDCNumberHelper.add(sumMap.get("constructArea"), table.getCell(i, constructAreaColummnIndex).getValue()));
				
				sumMap.put("sellArea", FDCNumberHelper.add(sumMap.get("sellArea"), table.getCell(i, sellAreaColummnIndex).getValue()));
				
				sumMap.put("rentArea", FDCNumberHelper.add(sumMap.get("rentArea"), table.getCell(i, rentAreaColummnIndex).getValue()));
				sumMap.put("groundRentArea", FDCNumberHelper.add(sumMap.get("groundRentArea"), table.getCell(i, groundRentAreaColummnIndex).getValue()));
				sumMap.put("publicArea", FDCNumberHelper.add(sumMap.get("publicArea"), table.getCell(i, publicAreaColummnIndex).getValue()));
				sumMap.put("deliveryArea", FDCNumberHelper.add(sumMap.get("deliveryArea"), table.getCell(i, deliveryAreaColummnIndex).getValue()));
				
				sumMap.put("elevators", FDCNumberHelper.add(sumMap.get("elevators"), table.getCell(i, elevatorsIndex).getValue()));
				sumMap.put("floors", FDCNumberHelper.add(sumMap.get("floors"), table.getCell(i, floorsIndex).getValue()));
				sumMap.put("floorHeight", FDCNumberHelper.add(sumMap.get("floorHeight"), table.getCell(i, floorHeightIndex).getValue()));
				sumMap.put("unit", FDCNumberHelper.add(sumMap.get("unit"), table.getCell(i, unitColummnIndex).getValue()));
				sumMap.put("hu", FDCNumberHelper.add(sumMap.get("hu"), table.getCell(i, huColummnIndex).getValue()));
				sumMap.put("build", FDCNumberHelper.add(sumMap.get("build"), table.getCell(i, buildColummnIndex).getValue()));
			}
			table.getCell(businessSubIndex, containAreaColummnIndex).setValue(sumMap.get("containArea"));
			//小计
			table.getCell(businessSubIndex, containAreaColummnIndex).setValue(sumMap.get("containArea"));
			//地上建筑面积
			table.getCell(businessSubIndex, groundAreaColummnIndex).setValue(sumMap.get("groundArea"));
			table.getCell(businessSubIndex, underGroundAreaColummnIndex).setValue(sumMap.get("underGroundArea"));
			table.getCell(businessSubIndex, buildColummnIndex).setValue(sumMap.get("build"));
			
			table.getCell(businessSubIndex, buildAreaColummnIndex).setValue(sumMap.get("buildArea"));
			
			table.getCell(businessSubIndex, givingAreaColummnIndex).setValue(sumMap.get("givingArea"));
			table.getCell(businessSubIndex, constructAreaColummnIndex).setValue(sumMap.get("constructArea"));
			
			table.getCell(businessSubIndex, sellAreaColummnIndex).setValue(sumMap.get("sellArea"));
			
			table.getCell(businessSubIndex, rentAreaColummnIndex).setValue(sumMap.get("rentArea"));
			table.getCell(businessSubIndex, groundRentAreaColummnIndex).setValue(sumMap.get("groundRentArea"));
			table.getCell(businessSubIndex, publicAreaColummnIndex).setValue(sumMap.get("publicArea"));
			table.getCell(businessSubIndex, deliveryAreaColummnIndex).setValue(sumMap.get("deliveryArea"));
			
			table.getCell(businessSubIndex, elevatorsIndex).setValue(sumMap.get("elevators"));
//			table.getCell(businessSubIndex, floorsIndex).setValue(sumMap.get("floors"));
//			table.getCell(businessSubIndex, floorHeightIndex).setValue(sumMap.get("floorHeight"));
			table.getCell(businessSubIndex, unitColummnIndex).setValue(sumMap.get("unit"));
			table.getCell(businessSubIndex, huColummnIndex).setValue(sumMap.get("hu"));
			table.getCell(businessSubIndex, productRateColummnIndex).setValue(FDCNumberHelper.ONE);
			//容积率小计＝建筑面积小计/占地面积小计
//			table.getCell(houseSubIndex, cubageRateColummnIndex).setValue(FDCNumberHelper.divide(sumMap.get("buildArea"), sumMap.get("containArea")));
			//平均每户面积小计＝可售面积小计/户数小计
			table.getCell(businessSubIndex, avgHuColummnIndex).setValue(FDCNumberHelper.divide(sumMap.get("sellArea"), sumMap.get("hu")));
			
			//公共配套
			sumMap.clear();
			for(int i=businessSubIndex+1;i<publicSubIndex;i++){
				if( i == currentRow && isChange){
					int changedCol = e.getColIndex();
					BigDecimal buildArea = FDCNumberHelper.toBigDecimal(
							table.getCell(i, buildAreaColummnIndex).getValue());
					if(changedCol == 2){
					}else if(changedCol == 3){
						BigDecimal tmpConArea = FDCNumberHelper.add(table.getCell(i, groundAreaColummnIndex).getValue()
								, table.getCell(i, underGroundAreaColummnIndex).getValue());
						table.getCell(i, buildAreaColummnIndex).setValue(tmpConArea!=null?tmpConArea:FDCHelper.ZERO);
					}else if(changedCol == 4){
						BigDecimal tmpConArea = FDCNumberHelper.add(table.getCell(i, groundAreaColummnIndex).getValue()
								, table.getCell(i, underGroundAreaColummnIndex).getValue());
						table.getCell(i, buildAreaColummnIndex).setValue(tmpConArea!=null?tmpConArea:FDCHelper.ZERO);
					}
				}
				sumMap.put("containArea", FDCNumberHelper.add(sumMap.get("containArea"), table.getCell(i, containAreaColummnIndex).getValue()));
				sumMap.put("groundArea", FDCNumberHelper.add(sumMap.get("groundArea"), table.getCell(i, groundAreaColummnIndex).getValue()));
				sumMap.put("underGroundArea", FDCNumberHelper.add(sumMap.get("underGroundArea"), table.getCell(i, underGroundAreaColummnIndex).getValue()));
				sumMap.put("buildArea", FDCNumberHelper.add(sumMap.get("buildArea"), table.getCell(i, buildAreaColummnIndex).getValue()));
				
				sumMap.put("givingArea", FDCNumberHelper.add(sumMap.get("givingArea"), table.getCell(i, givingAreaColummnIndex).getValue()));
				sumMap.put("constructArea", FDCNumberHelper.add(sumMap.get("constructArea"), table.getCell(i, constructAreaColummnIndex).getValue()));
				
				sumMap.put("sellArea", FDCNumberHelper.add(sumMap.get("sellArea"), table.getCell(i, sellAreaColummnIndex).getValue()));
				
				sumMap.put("rentArea", FDCNumberHelper.add(sumMap.get("rentArea"), table.getCell(i, rentAreaColummnIndex).getValue()));
				sumMap.put("groundRentArea", FDCNumberHelper.add(sumMap.get("groundRentArea"), table.getCell(i, groundRentAreaColummnIndex).getValue()));
				sumMap.put("publicArea", FDCNumberHelper.add(sumMap.get("publicArea"), table.getCell(i, publicAreaColummnIndex).getValue()));
				sumMap.put("deliveryArea", FDCNumberHelper.add(sumMap.get("deliveryArea"), table.getCell(i, deliveryAreaColummnIndex).getValue()));
				
				sumMap.put("elevators", FDCNumberHelper.add(sumMap.get("elevators"), table.getCell(i, elevatorsIndex).getValue()));
				sumMap.put("floors", FDCNumberHelper.add(sumMap.get("floors"), table.getCell(i, floorsIndex).getValue()));
				sumMap.put("floorHeight", FDCNumberHelper.add(sumMap.get("floorHeight"), table.getCell(i, floorHeightIndex).getValue()));
				sumMap.put("unit", FDCNumberHelper.add(sumMap.get("unit"), table.getCell(i, unitColummnIndex).getValue()));
				sumMap.put("hu", FDCNumberHelper.add(sumMap.get("hu"), table.getCell(i, huColummnIndex).getValue()));
				sumMap.put("build", FDCNumberHelper.add(sumMap.get("build"), table.getCell(i, buildColummnIndex).getValue()));
			}
			table.getCell(publicSubIndex, containAreaColummnIndex).setValue(sumMap.get("containArea"));
			//小计
			table.getCell(publicSubIndex, containAreaColummnIndex).setValue(sumMap.get("containArea"));
			//地上建筑面积
			table.getCell(publicSubIndex, groundAreaColummnIndex).setValue(sumMap.get("groundArea"));
			table.getCell(publicSubIndex, underGroundAreaColummnIndex).setValue(sumMap.get("underGroundArea"));
			table.getCell(publicSubIndex, buildColummnIndex).setValue(sumMap.get("build"));
			
			table.getCell(publicSubIndex, buildAreaColummnIndex).setValue(sumMap.get("buildArea"));
			
			table.getCell(publicSubIndex, givingAreaColummnIndex).setValue(sumMap.get("givingArea"));
			table.getCell(publicSubIndex, constructAreaColummnIndex).setValue(sumMap.get("constructArea"));
			
			table.getCell(publicSubIndex, sellAreaColummnIndex).setValue(sumMap.get("sellArea"));
			
			table.getCell(publicSubIndex, rentAreaColummnIndex).setValue(sumMap.get("rentArea"));
			table.getCell(publicSubIndex, groundRentAreaColummnIndex).setValue(sumMap.get("groundRentArea"));
			table.getCell(publicSubIndex, publicAreaColummnIndex).setValue(sumMap.get("publicArea"));
			table.getCell(publicSubIndex, deliveryAreaColummnIndex).setValue(sumMap.get("deliveryArea"));
			
			table.getCell(publicSubIndex, elevatorsIndex).setValue(sumMap.get("elevators"));
//			table.getCell(publicSubIndex, floorsIndex).setValue(sumMap.get("floors"));
//			table.getCell(publicSubIndex, floorHeightIndex).setValue(sumMap.get("floorHeight"));
			table.getCell(publicSubIndex, unitColummnIndex).setValue(sumMap.get("unit"));
			table.getCell(publicSubIndex, huColummnIndex).setValue(sumMap.get("hu"));
			table.getCell(publicSubIndex, productRateColummnIndex).setValue(FDCNumberHelper.ONE);
			//容积率小计＝建筑面积小计/占地面积小计
//			table.getCell(publicSubIndex, cubageRateColummnIndex).setValue(FDCNumberHelper.divide(sumMap.get("buildArea"), sumMap.get("containArea")));
			//平均每户面积小计＝可售面积小计/户数小计
			table.getCell(publicSubIndex, avgHuColummnIndex).setValue(FDCNumberHelper.divide(sumMap.get("sellArea"), sumMap.get("hu")));
			
			//停车
			sumMap.clear();
			for(int i=publicSubIndex+2;i<parkingSubIndex;i++){
				if( i == currentRow && isChange){
					BigDecimal tmp = FDCHelper.ZERO;
					int changedCol = e.getColIndex();
					BigDecimal buildArea = FDCNumberHelper.toBigDecimal(table.getCell(i, buildAreaColummnIndex).getValue());
					 if(changedCol == 3){
						BigDecimal tmpConArea = FDCNumberHelper.add(table.getCell(i, groundAreaColummnIndex).getValue()
								, table.getCell(i, underGroundAreaColummnIndex).getValue());
						table.getCell(i, buildAreaColummnIndex).setValue(tmpConArea!=null?tmpConArea:FDCHelper.ZERO);
					}else if(changedCol == 4){
						BigDecimal tmpConArea = FDCNumberHelper.add(table.getCell(i, groundAreaColummnIndex).getValue()
								, table.getCell(i, underGroundAreaColummnIndex).getValue());
						table.getCell(i, buildAreaColummnIndex).setValue(tmpConArea!=null?tmpConArea:FDCHelper.ZERO);
					}
//					tmp=FDCNumberHelper.divide(table.getCell(i, sellAreaColummnIndex).getValue(), table.getCell(i, huColummnIndex).getValue());
//					table.getCell(i, avgHuColummnIndex).setValue(tmp);
				}
				sumMap.put("containArea", FDCNumberHelper.add(sumMap.get("containArea"), table.getCell(i, containAreaColummnIndex).getValue()));
				sumMap.put("buildArea", FDCNumberHelper.add(sumMap.get("buildArea"), table.getCell(i, buildAreaColummnIndex).getValue()));
				sumMap.put("sellArea", FDCNumberHelper.add(sumMap.get("sellArea"), table.getCell(i, sellAreaColummnIndex).getValue()));
				sumMap.put("park", FDCNumberHelper.add(sumMap.get("park"), table.getCell(i, parksColummnIndex).getValue()));
				sumMap.put("sellPark", FDCNumberHelper.add(sumMap.get("sellPark"), table.getCell(i, sellParksColummnIndex).getValue()));
				//地上建筑面积
				sumMap.put("groundArea", FDCNumberHelper.add(sumMap.get("groundArea"), table.getCell(i, groundAreaColummnIndex).getValue()));
				//地下建筑面积
				sumMap.put("underGroundArea", FDCNumberHelper.add(sumMap.get("underGroundArea"), table.getCell(i, underGroundAreaColummnIndex).getValue()));
			}
			table.getCell(parkingSubIndex, containAreaColummnIndex).setValue(sumMap.get("containArea"));
			table.getCell(parkingSubIndex, buildAreaColummnIndex).setValue(sumMap.get("buildArea"));
			table.getCell(parkingSubIndex, sellAreaColummnIndex).setValue(sumMap.get("sellArea"));
			table.getCell(parkingSubIndex, parksColummnIndex).setValue(sumMap.get("park"));
			table.getCell(parkingSubIndex, sellParksColummnIndex).setValue(sumMap.get("sellPark"));
			//地上建筑面积
			table.getCell(parkingSubIndex, groundAreaColummnIndex).setValue(sumMap.get("groundArea"));
			table.getCell(parkingSubIndex, underGroundAreaColummnIndex).setValue(sumMap.get("underGroundArea"));

			//合计
			BigDecimal total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, containAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, containAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, containAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, containAreaColummnIndex).getValue());
			table.getCell(totalIndex, containAreaColummnIndex).setValue(total);
			
			total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, groundAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, groundAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, groundAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, groundAreaColummnIndex).getValue());
			table.getCell(totalIndex, groundAreaColummnIndex).setValue(total);//jf
			
			total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, underGroundAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, underGroundAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, underGroundAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, underGroundAreaColummnIndex).getValue());
			table.getCell(totalIndex, underGroundAreaColummnIndex).setValue(total);//jf
			
			total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, buildAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, buildAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, buildAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, buildAreaColummnIndex).getValue());
			table.getCell(totalIndex, buildAreaColummnIndex).setValue(total);
			
			total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, givingAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, givingAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, givingAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, givingAreaColummnIndex).getValue());
			table.getCell(totalIndex, givingAreaColummnIndex).setValue(total);
			
			total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, constructAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, constructAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, constructAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, constructAreaColummnIndex).getValue());
			table.getCell(totalIndex, constructAreaColummnIndex).setValue(total);
			
			total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, sellAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, sellAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, sellAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, sellAreaColummnIndex).getValue());
			table.getCell(totalIndex, sellAreaColummnIndex).setValue(total);
			
			total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, rentAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, rentAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, rentAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, rentAreaColummnIndex).getValue());
			table.getCell(totalIndex, rentAreaColummnIndex).setValue(total);
			
			total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, groundRentAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, groundRentAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, groundRentAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, groundRentAreaColummnIndex).getValue());
			table.getCell(totalIndex, groundRentAreaColummnIndex).setValue(total);
			
			total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, publicAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, publicAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, publicAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, publicAreaColummnIndex).getValue());
			table.getCell(totalIndex, publicAreaColummnIndex).setValue(total);
			
			total=FDCHelper.ZERO;
			total=FDCNumberHelper.add(total, table.getCell(houseSubIndex, deliveryAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(businessSubIndex, deliveryAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(publicSubIndex, deliveryAreaColummnIndex).getValue());
			total=FDCNumberHelper.add(total, table.getCell(parkingSubIndex, deliveryAreaColummnIndex).getValue());
			table.getCell(totalIndex, deliveryAreaColummnIndex).setValue(total);
			
//			BigDecimal newGroundBuildArea=FDCHelper.toBigDecimal(table.getCell(totalIndex, groundAreaColummnIndex).getValue());
//			if(totalGroundAreaCell!=null&&e.getType()!=10000&&oldtotalGroundArea.compareTo(newGroundBuildArea)!=0){
//				totalGroundAreaCell.setValue(newGroundBuildArea);
//			}
//			
//			BigDecimal newUnderGroundBuildArea=FDCHelper.toBigDecimal(table.getCell(totalIndex, underGroundAreaColummnIndex).getValue());
//			if(totalUnderGroundAreaCell!=null&&e.getType()!=10000&&oldtotalUnderGroundArea.compareTo(newUnderGroundBuildArea)!=0){
//				totalUnderGroundAreaCell.setValue(newUnderGroundBuildArea);
//			}
//			//总建筑面积
//			totalBuildAreaCell.setValue(FDCHelper.toBigDecimal(totalGroundAreaCell.getValue()).add(FDCHelper.toBigDecimal(totalUnderGroundAreaCell.getValue())));
//		
//			BigDecimal newtotalBuildArea=FDCHelper.toBigDecimal(table.getCell(totalIndex, sellAreaColummnIndex).getValue());
//			if(totalSellAreaCell!=null&&e.getType()!=10000&&oldSellBuildArea.compareTo(newtotalBuildArea)!=0){
//				totalSellAreaCell.setValue(newtotalBuildArea);
//			}
//			//占地面积
//			BigDecimal newtotalContainArea =FDCHelper.toBigDecimal(table.getCell(totalIndex, containAreaColummnIndex).getValue());
//			if(totalContainAreaCell!=null&&e.getType()!=10000&&oldtotalContainArea.compareTo(newtotalContainArea)!=0){
//				totalContainAreaCell.setValue(newtotalContainArea);
//			}
			
			
			
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
//		totalRoadAreaCell.setValue(amt);
		amt=FDCHelper.ZERO;
		amt=FDCNumberHelper.add(amt,importPubGreenAreaCell.getValue());
		amt=FDCNumberHelper.add(amt,houseGreenAreaCell.getValue());
		amt=FDCNumberHelper.add(amt, privateGardenCell.getValue());
		amt=FDCNumberHelper.add(amt, warterViewAreaCell.getValue());
		if(isPlanIndexLogic()){
			amt=FDCNumberHelper.add(amt, hardSquareCell.getValue());
			amt=FDCNumberHelper.add(amt, hardManRoadCell.getValue());
		}
//		totalGreenAreaCell.setValue(amt);
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
		this.measureCostEditU=null;
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
			btnAdd.setEnabled(true);
			btnAdd.setVisible(true);
			btnDel.setEnabled(true);
			btnDel.setVisible(true);
			panel.add(con,KDSplitPane.BOTTOM);
//			panel.setDividerLocation(0.7);
	        if(measureCostEditU.getOprtState().equals(OprtState.VIEW)){
	        	subTable.getStyleAttributes().setLocked(true);
	        	btnDel.setEnabled(false);
	        	btnAdd.setEnabled(false);
			}
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
		subTable.setName("自定义指标");
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
		
		
		final String indexSql = "select fapportiontypeid from t_fdc_measureindex where fisenabled=1 and ftype='1CUSTOM' ";
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
					filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
					//TODO 成本分摊过滤
					filter.getFilterItems().add(new FilterItemInfo("forCostApportion",Boolean.TRUE));
					//TODO 有点性能问题 过滤集团指标
					boolean isMeasureIndex = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_MEASUREINDEX);
					if(isMeasureIndex){
						filter.getFilterItems().add(new FilterItemInfo("id",indexSql,CompareType.INNER));
					}
					filter.mergeFilter(ApportionTypeInfo.getCUFilter(SysContext.getSysContext().getCurrentCtrlUnit()), "and");
				} catch (BOSException e1) {
					measureCostEditU.handUIException(e1);
				} catch (EASBizException e2) {
					measureCostEditU.handUIException(e2);
				}
				view.setFilter(filter);
				f7.setEntityViewInfo(view);
			}
		});
		//verifyCustom校验，指标+产品不能重复
		/*f7.addPreChangeListener(new PreChangeListener(){
			public void preChange(PreChangeEvent e) {
				Object objNew=e.getData();
				int rowIndex=KDTableUtil.getSelectedRow(table);
				if(objNew instanceof ApportionTypeInfo){
					for(int i = 0; i < subTable.getRowCount(); ++i){
						if(rowIndex != i){
							Object obj=subTable.getRow(i).getCell("number").getValue();
							if(obj != null && obj instanceof ApportionTypeInfo){
								if(((ApportionTypeInfo)obj).getId().equals(((ApportionTypeInfo)objNew).getId())){
									MsgBox.showWarning(measureCostEditU, "该指标已经被定义在第"+(i+1)+"行");
									e.setResult(PreChangeEvent.S_FALSE);
									return;
								}
							}
						}
					}					
				}
			}
		});*/
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
					measureCostEditU.handUIException(e1);
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
		//willShow第一次加载条件不生效带出了所有产品 by hpw 2010.09.03
		Set productSet=getProductIdSet();
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		if(productSet.size()>0){
			view.getFilter().getFilterItems().add(new FilterItemInfo("id",productSet,CompareType.INCLUDE));
		}
		f7.setEntityViewInfo(view);
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
	
	private KDTable constrTable=null;
	public KDTable getConstructTable(){
		return constrTable;
	}
	private void initConstructTable(MeasureCostInfo info){
		constrTable = new KDTable(4,1,0);
		constrTable.setName("建造标准");
		IRow headRow=constrTable.getHeadRow(0);
		constrTable.getColumn(0).setKey("id");
		constrTable.getColumn(0).getStyleAttributes().setHided(true);
		constrTable.getColumn(1).setKey("indexName1");
		constrTable.getColumn(2).setKey("indexName2");
		constrTable.getColumn(3).setKey("indexName3");
		
		headRow.getCell(0).setUserObject("construct");
		headRow.getCell(0).setValue("ID");
		headRow.getCell(1).setValue("一级指标名称");
		headRow.getCell(2).setValue("二级指标名称");
		headRow.getCell(3).setValue("三级指标名称");
		
		if(info==null||info.getConstrEntrys()==null||info.getConstrEntrys().size()==0){
        	return;
        }
		
		for(Iterator iter2=info.getConstrEntrys().iterator();iter2.hasNext();){
			ConstructPlanIndexEntryInfo indexEntry = (ConstructPlanIndexEntryInfo)iter2.next();
			
			if(indexEntry.getProductType()==null){
				continue;
			}
			ProductTypeInfo indexProduct = indexEntry.getProductType();
			String productId = indexProduct.getId().toString();
			String entryKey = indexEntry.getIndexName1()+indexEntry.getIndexName2()+indexEntry.getIndexName3();
			if(constrTable.getRowCount()==0){
				IColumn column = constrTable.addColumn();
    			column.setWidth(150);
    			column.setKey(productId);
    			headRow.getCell(productId).setValue(indexProduct.getName());
    			headRow.getCell(productId).setUserObject(indexProduct);
    			IRow row = constrTable.addRow();
    			row.getCell("id").setValue(indexEntry.getId().toString());
	        	row.getCell("indexName1").setValue(indexEntry.getIndexName1());
	        	row.getCell("indexName2").setValue(indexEntry.getIndexName2());
	        	row.getCell("indexName3").setValue(indexEntry.getIndexName3());
	        	row.getCell(productId).setValue(indexEntry.getDesc());
	        	row.getCell(productId).setUserObject(indexEntry);
			}
			boolean isAdd = false;
			for(int i=0;i<constrTable.getRowCount();i++){
        		IRow idxRow = constrTable.getRow(i);
        		String idxKey = (String)idxRow.getCell("indexName1").getValue()+idxRow.getCell("indexName2").getValue()+idxRow.getCell("indexName3").getValue();
        		if(entryKey.equals(idxKey)){
        			isAdd=true;
        			if(headRow.getCell(productId)==null){
        				IColumn column = constrTable.addColumn();
            			column.setWidth(150);
            			column.setKey(productId);
            			headRow.getCell(productId).setValue(indexProduct.getName());
            			headRow.getCell(productId).setUserObject(indexProduct);
        			}
        			idxRow.getCell(productId).setValue(indexEntry.getDesc());
        			idxRow.getCell(productId).setUserObject(indexEntry);
        		}
			}
        	if (!isAdd) {
				IRow row = constrTable.addRow();
				row.getCell("id").setValue(indexEntry.getId().toString());
				row.getCell("indexName1").setValue(indexEntry.getIndexName1());
				row.getCell("indexName2").setValue(indexEntry.getIndexName2());
				row.getCell("indexName3").setValue(indexEntry.getIndexName3());
				if (headRow.getCell(productId) == null) {
					IColumn column = constrTable.addColumn();
					column.setWidth(150);
					column.setKey(productId);
					headRow.getCell(productId).setValue(indexProduct.getName());
					headRow.getCell(productId).setUserObject(indexProduct);
				}
				row.getCell(productId).setValue(indexEntry.getDesc());
				row.getCell(productId).setUserObject(indexEntry);
			}
	        
		}
		
		
		if(true)return;
		for(Iterator iter2=info.getConstrEntrys().iterator();iter2.hasNext();){
			//表头
			ConstructPlanIndexEntryInfo indexEntry = (ConstructPlanIndexEntryInfo)iter2.next();
			if(indexEntry.getProductType()==null){
				continue;
			}
			ProductTypeInfo indexProduct = indexEntry.getProductType();
			String productId = indexProduct.getId().toString();
			
			//表体
			for(Iterator iter=info.getConstrEntrys().iterator();iter.hasNext();){
	        	ConstructPlanIndexEntryInfo entry=(ConstructPlanIndexEntryInfo)iter.next();
	        	String entryKey = entry.getIndexName1()+entry.getIndexName2()+entry.getIndexName3();
	        	ProductTypeInfo entryProduct = entry.getProductType();	
	        	String entryProductId = entryProduct.getId().toString();
	        	boolean isAdd=false;
	        	for(int i=0;i<constrTable.getRowCount();i++){
	        		IRow idxRow = constrTable.getRow(i);
	        		String idxKey = (String)idxRow.getCell("indexName1").getValue()+idxRow.getCell("indexName2").getValue()+idxRow.getCell("indexName3").getValue();
	        		if(entryKey.equals(idxKey)){
	        			IColumn column = constrTable.addColumn();
	        			column.setWidth(150);
	        			column.setKey(entryProductId);
	        			headRow.getCell(entryProductId).setValue(entryProduct.getName());
	        			headRow.getCell(entryProductId).setUserObject(entryProduct);
	        			
	        			idxRow.getCell(entryProductId).setValue(entry.getDesc());
	        			idxRow.getCell(entryProductId).setUserObject(entry);
	        		}else{
	        			IRow row=constrTable.addRow();
			        	if(productId.equals(entryProductId)){
				        	row.getCell("id").setValue(entry.getId().toString());
				        	row.getCell("indexName1").setValue(entry.getIndexName1());
				        	row.getCell("indexName2").setValue(entry.getIndexName2());
				        	row.getCell("indexName3").setValue(entry.getIndexName3());
				        	row.getCell(entryProductId).setValue(entry.getDesc());
				        	row.getCell(entryProductId).setUserObject(entry);
			        	}
	        		}
	        	}
	        	if(!isAdd){
		        	IRow row=constrTable.addRow();
		        	if(productId.equals(entryProductId)){
			        	row.getCell("id").setValue(entry.getId().toString());
			        	row.getCell("indexName1").setValue(entry.getIndexName1());
			        	row.getCell("indexName2").setValue(entry.getIndexName2());
			        	row.getCell("indexName3").setValue(entry.getIndexName3());
			        	row.getCell(productId).setValue(entry.getDesc());
			        	row.getCell(productId).setUserObject(entry);
		        	}
	        	}
			}
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
	
	public ConstructPlanIndexEntryCollection getConstrEntrys() {
//		verifyConstruct();
		ConstructPlanIndexEntryCollection constructEntrys = costInfo.getConstrEntrys();
		if(constructEntrys==null){
			constructEntrys=new ConstructPlanIndexEntryCollection();
		}
		constructEntrys.clear();
		//产品列
		for(int j=4;j<constrTable.getColumnCount();j++){
			IRow headRow = constrTable.getHeadRow(0);
			ProductTypeInfo product = (ProductTypeInfo)headRow.getCell(j).getUserObject();
			String productId = product.getId().toString();
			//指标行
			for(int i=0;i<constrTable.getRowCount();i++){
				IRow row=constrTable.getRow(i);
				if(row==null) continue;
				ConstructPlanIndexEntryInfo entry=row.getCell(productId)==null?null:(ConstructPlanIndexEntryInfo)row.getCell(productId).getUserObject();
				if(entry==null){
					entry=new ConstructPlanIndexEntryInfo();
					entry.setId(BOSUuid.create(entry.getBOSType()));
					row.getCell(productId).setUserObject(entry);
				}
				entry.setIndexName1((String)row.getCell("indexName1").getValue());
				entry.setIndexName2((String)row.getCell("indexName2").getValue());
				entry.setIndexName3((String)row.getCell("indexName3").getValue());
				entry.setDesc((String)row.getCell(productId).getValue());
				entry.setProductType(product);
				constructEntrys.add(entry);
			}
		}
		return constructEntrys;
	}
	
	private void verifyConstruct() {
		// TODO Auto-generated method stub
		
	}
	private void verifyCustom(){
		//指标+产品唯一,指标必录
		for(int i=0;i<subTable.getRowCount();i++){
			IRow row=subTable.getRow(i);
			ApportionTypeInfo apportType = (ApportionTypeInfo)row.getCell("number").getValue();
			if(apportType==null){
				FDCMsgBox.showError(measureCostEditU, "编码必录");
				subTable.getSelectManager().select(i, 1,i,1);
				measureCostEditU.getplTables().setSelectedIndex(1);
				SysUtil.abort();
			}
			
			if(row.getCell("value").getValue()==null){
				FDCMsgBox.showError(measureCostEditU, "指标值必录");
				subTable.getSelectManager().select(i, 3,i,3);
				measureCostEditU.getplTables().setSelectedIndex(1);
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
					if(productType!=null&&productType2!=null&&productType.getId().toString().equals(productType2.getId().toString())){
						isDup=true;
					}
					if(isDup){
						FDCMsgBox.showError(measureCostEditU, "第"+(i+1)+"行与第"+(j+1)+"行存在重复");
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
