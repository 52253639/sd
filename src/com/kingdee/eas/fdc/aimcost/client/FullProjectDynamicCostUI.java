/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.chart.ChartType;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.aimcost.FullDynamicCostMap;
import com.kingdee.eas.fdc.aimcost.HappenDataGetter;
import com.kingdee.eas.fdc.aimcost.HappenDataInfo;
import com.kingdee.eas.fdc.aimcost.ProjectCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.AcctAccreditHelper;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.ChangeTypeCollection;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.basedata.client.ChartData;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class FullProjectDynamicCostUI extends AbstractFullProjectDynamicCostUI {
	private static final Logger logger = CoreUIObject.getLogger(FullProjectDynamicCostUI.class);
	
	public static final String SETTLE_ADJUST = "settleAdjust";

	public static final String BUILD_PART = "buildPart";

	public static final String SELL_PART = "sellPart";

	public static final String DIFF = "diff";

	public static final String DYNAMIC_COST = "dynamicCost";

	public static final String AIM_COST = "aimCost";

	public static final String INTENDING_HAPPEN = "intendingHappen";

	public static final String HAS_HAPPEN = "hasHappen";

	public static final String NO_TEXT = "noText";
	
	public static final String HAS_PAY = "hasPay";

	public static final String HAS_PAID = "hasPaid";
	
	public static final String SETTLE = "Settle";

	public static final String NO_SETTLE = "NoSettle";

	public static final String SUB_TOTAL_SETTLE = "subTotalSettle";

	public static final String SUB_TOTAL_NO_SETTLE = "subTotalNoSettle";

	public static final String ASSIGN_AMOUNT_SETTLE = "assignAmountSettle";

	public static final String ASSIGN_AMOUNT_NO_SETTLE = "assignAmountNoSettle";
	/**
	 * 部分结算 by sxhong 2008-05-30 13:45:49
	 */
	public static final String PARTSETTLE_AMOUNT = "partSettleAmount";
	/**
	 *已实现产值 by sxhong 2008-05-30 13:45:51
	 */
	public static final String HASALLSETTLED_AMOUNT = "hasAllSettled";

	private ChangeTypeCollection changeTypes;

	private Map acctMap = new HashMap();
	private Map acctMaps = new HashMap();//用于缓存成本科目map

	private Map dynamicCostMap = new HashMap();

	private Map aimCostMap = new HashMap();

	private HappenDataGetter happenGetter;

	private BigDecimal sellArea = null;

	private BigDecimal buildArea = null;
	
	private boolean hideRealizedCost = false;
	
	private RetValue retValueNotLeaf;

	/**
	 * output class constructor
	 */
	public FullProjectDynamicCostUI() throws Exception {
		super();
	}

	// 数据对象变化时，刷新界面状态
	protected void setActionState() {

	}

	/**
	 * 屏蔽回车键
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		//ctrl+L 用于测试程序
/*		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if(proNode!=null&&proNode.getUserObject() instanceof CurProjectInfo){
			String prjId=((CurProjectInfo)proNode.getUserObject()).getId().toString();
			BigDecimal amount=FDCCostRptFacadeFactory.getRemoteInstance().getAimCost(prjId);
			System.out.println("aimCost:"+amount);
			amount=FDCCostRptFacadeFactory.getRemoteInstance().getDynCost(prjId);
			System.out.println("dynCost:"+amount);
			amount=FDCCostRptFacadeFactory.getRemoteInstance().getHappendCost(prjId);
			System.out.println("HappendCost:"+amount);
		}
		System.out.println("test");*/
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		// super.tblMain_tableClicked(e);

		if (e.getClickCount() == 2) {

			int rowIndex = e.getRowIndex();

			// if(getMainTable().getCell(rowIndex, colIndex).getValue() == null)
			// return;

			if(getMainTable().getRow(rowIndex)==null)
				return;
			
			Object value = getMainTable().getRow(rowIndex).getUserObject();
			if (value == null)
				return;
			CostAccountInfo acctInfo = (CostAccountInfo) value;

			boolean b = acctInfo.isIsLeaf();
			if (!b)
				return;
			this.setCursorOfWair();
			Map map = new HashMap();

			map.put("acctId", acctInfo.getId().toString());

			Object no_set_amt = getMainTable().getRow(rowIndex).getCell(
					"assignAmountNoSettle").getValue();
//			根据变更类型动态设值 未结算
			for(int i =0;i<this.changeTypes.size();i++){
				Object no_set_type = getMainTable()
					.getRow(rowIndex).getCell(changeTypes.get(i).getId().toString()+NO_SETTLE).getValue();
				map.put(changeTypes.get(i).getId().toString()+NO_SETTLE,no_set_type);
			}
//			Object no_set_design = getMainTable().getRow(rowIndex).getCell(
//					FDCConstants.CHANGE_TYPE_DESIGN + NO_SETTLE).getValue();
//			Object no_set_sign = getMainTable().getRow(rowIndex).getCell(
//					FDCConstants.CHANGE_TYPE_SIGN + NO_SETTLE).getValue();
//			Object no_set_other = getMainTable().getRow(rowIndex).getCell(
//					FDCConstants.CHANGE_TYPE_OTHER + NO_SETTLE).getValue();
			Object no_set_total = getMainTable().getRow(rowIndex).getCell(
					"subTotalNoSettle").getValue();

			Object set_amt = getMainTable().getRow(rowIndex).getCell(
					"assignAmountSettle").getValue();
			//根据变更类型动态设值 已结算
			for(int i =0;i<this.changeTypes.size();i++){
				Object set_type = getMainTable()
					.getRow(rowIndex).getCell(changeTypes.get(i).getId().toString()+SETTLE).getValue();
				map.put(changeTypes.get(i).getId().toString()+SETTLE,set_type);
			}
//			Object set_design = getMainTable().getRow(rowIndex).getCell(
//					FDCConstants.CHANGE_TYPE_DESIGN + SETTLE).getValue();
//			Object set_sign = getMainTable().getRow(rowIndex).getCell(
//					FDCConstants.CHANGE_TYPE_SIGN + SETTLE).getValue();
//			Object set_other = getMainTable().getRow(rowIndex).getCell(
//					FDCConstants.CHANGE_TYPE_OTHER + SETTLE).getValue();
			Object set_total = getMainTable().getRow(rowIndex).getCell(
					"subTotalSettle").getValue();

			Object no_text = getMainTable().getRow(rowIndex).getCell(NO_TEXT)
					.getValue();

			Object will_happen = getMainTable().getRow(rowIndex).getCell(
					INTENDING_HAPPEN).getValue();

			map.put("no_set_amt", no_set_amt);
//			map.put("no_set_design", no_set_design);
//			map.put("no_set_sign", no_set_sign);
//			map.put("no_set_other", no_set_other);
			map.put("no_set_total", no_set_total);

			map.put("set_amt", set_amt);
//			map.put("set_design", set_design);
//			map.put("set_sign", set_sign);
//			map.put("set_other", set_other);
			map.put("set_total", set_total);

			map.put("no_text", no_text);
			map.put("will_happen", will_happen);
			map.put("FullDyDetailInfoTitle","全项目动态成本详细信息");
			FullDyDetailInfoUI.showDialogWindows(this, map);
			this.setCursorOfDefault();

		}
	}

	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
	}

	protected String getEditUIName() {
		// TODO 自动生成方法存根
		return null;
	}

	protected void initTree() throws Exception {
		this.initMainTable();
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
		treeBuilder.build(this, this.treeMain, this.actionOnLoad);
		this.treeMain.expandAllNodes(true,
				(TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
	}

	/**
	 * 设置表格属性
	 * 
	 * @throws BOSException
	 */
	private void initMainTable() throws BOSException {
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT);
		tblMain.getViewManager().setFreezeView(-1, 2);
		tblMain.getColumn(AIM_COST).getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblMain.getColumn(HAS_HAPPEN).getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblMain.getColumn(HAS_PAY).getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblMain.getColumn(HAS_PAID).getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);		
		tblMain.getColumn(INTENDING_HAPPEN).getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn(DYNAMIC_COST).getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn(NO_TEXT).getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblMain.getColumn(DIFF).getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblMain.getColumn(SELL_PART).getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblMain.getColumn(BUILD_PART).getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);

		tblMain.getColumn(AIM_COST).getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn(HAS_HAPPEN).getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn(HAS_PAY).getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn(HAS_PAID).getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn(INTENDING_HAPPEN).getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn(DYNAMIC_COST).getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn(NO_TEXT).getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn(DIFF).getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn(SELL_PART).getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		tblMain.getColumn(BUILD_PART).getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));

		tblMain.addHeadRow(1, (IRow) tblMain.getHeadRow(0).clone());
//		for (int i = 0; i < tblMain.getColumnCount(); i++) {
//			tblMain.getHeadMergeManager().mergeBlock(0, i, 1, i);
//		}
		int colIndex = 2;
		IColumn col = tblMain.addColumn(colIndex++);
		String key = ASSIGN_AMOUNT_NO_SETTLE;
		col.setKey(key);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(0).getCell(key).setValue(
				AimCostHandler.getResource("NoSettleContract"));
		tblMain.getHeadRow(1).getCell(key).setValue(
				AimCostHandler.getResource("AssignAmount"));
		for (int i = 0; i < this.changeTypes.size(); i++) {
			ChangeTypeInfo changeTypeInfo = this.changeTypes.get(i);
			key = changeTypeInfo.getId().toString() + NO_SETTLE;
			col = tblMain.addColumn(colIndex++);
			col.setKey(key);
			tblMain.getHeadRow(0).getCell(key).setValue(
					AimCostHandler.getResource("NoSettleContract"));
			tblMain.getHeadRow(1).getCell(key).setValue(
					changeTypeInfo.getName());
			col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			col.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
		}
		col = tblMain.addColumn(colIndex++);
		key = SUB_TOTAL_NO_SETTLE;
		col.setKey(key);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(0).getCell(key).setValue(
				AimCostHandler.getResource("NoSettleContract"));
		tblMain.getHeadRow(1).getCell(key).setValue(
				AimCostHandler.getResource("ContractSubTotal"));

		if(isUsePartSettle()){
			//部分结算列
			col = tblMain.addColumn(colIndex++);
			key = PARTSETTLE_AMOUNT;
			col.setKey(key);
			tblMain.getHeadRow(0).getCell(key).setValue(
					AimCostHandler.getResource("NoSettleContract"));
			tblMain.getHeadRow(1).getCell(key).setValue(
					AimCostHandler.getResource("PartSettle"));
			
			//已实现产值列
			col=tblMain.addColumn(tblMain.getColumnIndex(HAS_PAID)+1);
			key = HASALLSETTLED_AMOUNT;
			col.setKey(key);
			FDCHelper.formatTableNumber(tblMain,new String[]{HASALLSETTLED_AMOUNT,PARTSETTLE_AMOUNT});
			tblMain.getHeadRow(0).getCell(key).setValue(
					AimCostHandler.getResource("HasAllSettled"));
			tblMain.getHeadRow(1).getCell(key).setValue(
					AimCostHandler.getResource("HasAllSettled"));
		}
//		tblMain.getHeadMergeManager().mergeBlock(0, 2, 0, colIndex - 1);
		col = tblMain.addColumn(colIndex++);
		key = ASSIGN_AMOUNT_SETTLE;
		col.setKey(key);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(0).getCell(key).setValue(
				AimCostHandler.getResource("SettleContract"));
		tblMain.getHeadRow(1).getCell(key).setValue(
				AimCostHandler.getResource("AssignAmount"));
		for (int i = 0; i < this.changeTypes.size(); i++) {
			ChangeTypeInfo changeTypeInfo = this.changeTypes.get(i);
			key = changeTypeInfo.getId().toString() + SETTLE;
			col = tblMain.addColumn(colIndex++);
			col.setKey(key);
			tblMain.getHeadRow(0).getCell(key).setValue(
					AimCostHandler.getResource("SettleContract"));
			tblMain.getHeadRow(1).getCell(key).setValue(
					changeTypeInfo.getName());
			col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			col.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
		}

		col = tblMain.addColumn(colIndex++);
		key = SETTLE_ADJUST;
		col.setKey(key);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(0).getCell(key).setValue(
				AimCostHandler.getResource("SettleContract"));
		tblMain.getHeadRow(1).getCell(key).setValue(
				AimCostHandler.getResource("SettleAdjust"));

		col = tblMain.addColumn(colIndex++);
		key = SUB_TOTAL_SETTLE;
		col.setKey(key);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(0).getCell(key).setValue(
				AimCostHandler.getResource("SettleContract"));
		tblMain.getHeadRow(1).getCell(key).setValue(
				AimCostHandler.getResource("ContractSubTotal"));
		/*tblMain.getHeadMergeManager().mergeBlock(0,
				this.changeTypes.size() + 4, 0, colIndex - 1);*/
		tblMain.getHeadMergeManager().mergeBlock(0,0, 1, tblMain.getColumnCount() - 1,KDTMergeManager.FREE_MERGE);
	}

	/**
	 * 获取query中的主键列名称，返回供编辑/删除时获取主键用，默认值为"id"，继承类可以重载
	 * 
	 */
	protected String getKeyFieldName() {
		return "acctNumber";
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		fetchAndFill(getSelectObjId());
		initUserConfig();
	}
	
	private void fetchAndFill(String selectObjId) throws EASBizException,BOSException, Exception {
		LongTimeDialog dialog = UITools.getDialog(this);
		if(dialog==null)
			return;
        dialog.setLongTimeTask(new ILongTimeTask() {
            public Object exec() throws Exception {
            	String selectObjId = getSelectObjId(); 
            	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
        		.getLastSelectedPathComponent();
        		if (node.isLeaf()&&node.getUserObject() instanceof CurProjectInfo) {
        			if (selectObjId == null) {
        				return null;
        			}
        			fillMainTable(selectObjId);
        			int acctNameIndex=tblMain.getColumn("acctName").getColumnIndex()+1;
        			tblMain.getViewManager().freeze(0, acctNameIndex);
        		} else {
        			Set leafPrjIds = FDCClientHelper.getProjectLeafsOfNode(node);
        			boolean selectObjIsPrj = node.getUserObject() instanceof CurProjectInfo;
        			fetchDataNotLeaf(selectObjId,leafPrjIds,selectObjIsPrj);
        			fillTableNotLeaf();
        		}
                return null;
            }

            public void afterExec(Object result) throws Exception {
                
            }
        });
        if(dialog!=null)
    		dialog.show();
	}
	private void fetchDataNotLeaf(String objId,Set leafPrjIds,boolean selectObjIsPrj) throws EASBizException, BOSException{
		TimeTools.getInstance().msValuePrintln("start fetchData");
		ParamValue paramValue = new ParamValue();
		paramValue.put("selectObjID", objId);
		paramValue.put("leafPrjIDs", leafPrjIds);
		paramValue.put("selectObjIsPrj", Boolean.valueOf(selectObjIsPrj));
		retValueNotLeaf = ProjectCostRptFacadeFactory.getRemoteInstance().getCollectionFullDynCost(paramValue);
		TimeTools.getInstance().msValuePrintln("end fetchData");
	}
	public void fillTableNotLeaf() throws Exception{
		tblMain.removeRows();
		this.actionSave.setEnabled(false);
		
		CostAccountCollection costAccounts = (CostAccountCollection)retValueNotLeaf.get("CostAccountCollection");
		if(costAccounts==null)
			return;
		int maxLevel = retValueNotLeaf.getInt("maxLevel");
		
		RetValue costValues = (RetValue)retValueNotLeaf.get("costValues");
		RetValue settEntryValues = (RetValue)retValueNotLeaf.get("settEntryValues");
		RetValue areaValue = (RetValue)retValueNotLeaf.get("areaValue");
		
		BigDecimal sellAreaValue = FDCHelper.toBigDecimal(areaValue.getBigDecimal(ApportionTypeInfo.sellAreaType));
		this.sellArea = sellAreaValue;
		BigDecimal buildAreaValue= FDCHelper.toBigDecimal(areaValue.getBigDecimal(ApportionTypeInfo.buildAreaType));
		this.buildArea = buildAreaValue;
		
		/*****
		 * 开始填充数据
		 */
		tblMain.getTreeColumn().setDepth(maxLevel);
		
		for(Iterator it=costAccounts.iterator();it.hasNext();){
			CostAccountInfo costAccountInfo = (CostAccountInfo)it.next();
			IRow row = tblMain.addRow();
			row.setTreeLevel(costAccountInfo.getLevel() - 1);
			String longNumber = costAccountInfo.getLongNumber();
			row.getCell("acctNumber").setValue(longNumber.replace('!', '.'));
			row.getCell("acctName").setValue(costAccountInfo.getName());
			
			if(costAccountInfo.isIsLeaf()){
				row.setUserObject(costAccountInfo);
				RetValue costValue = (RetValue)costValues.get(longNumber);
				if(costValue==null)
					continue;
				RetValue settEntryValue = (RetValue)settEntryValues.get(longNumber);
				
				//未结算合同 - 签约合同金额
				BigDecimal unSettSignAmt = FDCHelper.toBigDecimal(costValue.getBigDecimal("unSettSignAmt"));
				if(unSettSignAmt.compareTo(FDCHelper.ZERO)!=0){
					row.getCell(ASSIGN_AMOUNT_NO_SETTLE).setValue(unSettSignAmt);
				}
				//已结算合同 - 签约合同金额
				BigDecimal settSignAmt = FDCHelper.toBigDecimal(costValue.getBigDecimal("settSignAmt"));
				if(settSignAmt.compareTo(FDCHelper.ZERO)!=0){
					row.getCell(ASSIGN_AMOUNT_SETTLE).setValue(settSignAmt);
				}
				BigDecimal unSettConTotalAmt = unSettSignAmt;
				BigDecimal settConTotalAmt = settSignAmt;
				//各变更类型对应的金额
				for (int i = 0; i < changeTypes.size()&&settEntryValue!=null; i++) {
					ChangeTypeInfo change = changeTypes.get(i);
					String cellKey  = change.getId().toString()+NO_SETTLE;
					String valueKey = change.getId().toString()+"unSettleAmt";
					BigDecimal changeAmount = FDCHelper.toBigDecimal(settEntryValue.getBigDecimal(valueKey));
					if(changeAmount.compareTo(FDCHelper.ZERO)!=0)
						row.getCell(cellKey).setValue(changeAmount);
					unSettConTotalAmt = unSettConTotalAmt.add(changeAmount);
					
					cellKey  = change.getId().toString()+SETTLE;
					valueKey = change.getId().toString()+"settleAmt";
					
					changeAmount = FDCHelper.toBigDecimal(settEntryValue.getBigDecimal(valueKey));
					if(changeAmount.compareTo(FDCHelper.ZERO)!=0)
						row.getCell(cellKey).setValue(changeAmount);
					settConTotalAmt = settConTotalAmt.add(changeAmount);
				}
				//结算调整
				BigDecimal settAdjustAmt = FDCHelper.toBigDecimal(costValue.getBigDecimal("settAdjustAmt"));
				if(settAdjustAmt.compareTo(FDCHelper.ZERO)!=0)
					row.getCell(SETTLE_ADJUST).setValue(settAdjustAmt);
				settConTotalAmt = settConTotalAmt.add(settAdjustAmt);
				
				//未结算合同小计
				if(unSettConTotalAmt.compareTo(FDCHelper.ZERO)!=0)
					row.getCell(SUB_TOTAL_NO_SETTLE).setValue(unSettConTotalAmt);
				//结算合同小计
				if(settConTotalAmt.compareTo(FDCHelper.ZERO)!=0)
					row.getCell(SUB_TOTAL_SETTLE).setValue(settConTotalAmt);
				
				//非合同性成本 --无文本合同
				BigDecimal  unContractCostAmt = FDCHelper.toBigDecimal(costValue.getBigDecimal("unContractCostAmt"));
				if(unContractCostAmt.compareTo(FDCHelper.ZERO)!=0)
					row.getCell(NO_TEXT).setValue(unContractCostAmt);
				
				//目前已发生 = 未结算合同小计 + 已结算合同小计 + 非合同性成本
				BigDecimal happenAmt = unSettConTotalAmt.add(settConTotalAmt).add(unContractCostAmt);
				if(happenAmt.compareTo(FDCHelper.ZERO)!=0)
					row.getCell(HAS_HAPPEN).setValue(happenAmt);
				//已实现
				BigDecimal costPayedAmt = FDCHelper.toBigDecimal(costValue.getBigDecimal("costPayedAmt"));
				if(costPayedAmt.compareTo(FDCHelper.ZERO)!=0)
					row.getCell(HAS_PAY).setValue(costPayedAmt);
				
				//已付款
				BigDecimal payedAmt = FDCHelper.toBigDecimal(costValue.getBigDecimal("payedAmt"));
				if(payedAmt.compareTo(FDCHelper.ZERO)!=0)
					row.getCell(HAS_PAID).setValue(payedAmt);
				
				//目标成本
				BigDecimal aimCost = FDCHelper.toBigDecimal(costValue.getBigDecimal("aimCost"));
				if(aimCost.compareTo(FDCHelper.ZERO)!=0)
					row.getCell(AIM_COST).setValue(aimCost);
				
				//差异
				BigDecimal diffAmt = FDCHelper.toBigDecimal(costValue.getBigDecimal("diffAmt"));
				if(diffAmt.compareTo(FDCHelper.ZERO)!=0)
					row.getCell(DIFF).setValue(diffAmt);
				
				//动态成本=目标成本＋差异
				BigDecimal dynCost = aimCost.add(diffAmt);
				if(dynCost.compareTo(FDCHelper.ZERO)!=0)
					row.getCell(DYNAMIC_COST).setValue(dynCost);
				
				
				//目前待发生＝动态成本－目前已发生；
				BigDecimal intendingAmt = dynCost.subtract(happenAmt);
				if(intendingAmt.compareTo(FDCHelper.ZERO)!=0)
					row.getCell(INTENDING_HAPPEN).setValue(intendingAmt);
				
				//可售单方
				if(sellAreaValue.compareTo(FDCHelper.ZERO)!=0&&dynCost.compareTo(FDCHelper.ZERO)!=0){
					BigDecimal sellPart = dynCost.divide(sellAreaValue,2,BigDecimal.ROUND_HALF_UP);
					row.getCell(SELL_PART).setValue(sellPart);
				}
				//建筑单方
				if(buildAreaValue.compareTo(FDCHelper.ZERO)!=0&&dynCost.compareTo(FDCHelper.ZERO)!=0){
					BigDecimal buildPart = dynCost.divide(buildArea,2,BigDecimal.ROUND_HALF_UP);
					row.getCell(BUILD_PART).setValue(buildPart);
				}
			}
			else{
				row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
			}
			
			
			
			
		}
		setUnionData();
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		fetchAndFill(selectObjId);
		//填充完数据之后重新刷新tblMain,来解决第一列宽度不自动扩展的问题，不再使用冻结科目名称列的做法   By zhiyuan_tang 2010/06/29
		tblMain.reLayoutAndPaint();
//		int acctNameIndex=tblMain.getColumn("acctName").getColumnIndex()+1;
//		tblMain.getViewManager().freeze(0, acctNameIndex);
	}

	private String getSelectObjId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node==null||node.getUserObject() == null
				|| OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo.getId().toString();
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui.getUnit() == null) {

			}
			FullOrgUnitInfo info = oui.getUnit();
			return info.getId().toString();
		}
		return null;
	}

	public void fillMainTable(String objectId) throws Exception {
		tblMain.removeRows();
		this.actionSave.setEnabled(true);
		BOSObjectType bosType = BOSUuid.read(objectId).getType();
		FilterInfo acctFilter = new FilterInfo();
		if (new CurProjectInfo().getBOSType().equals(bosType)) {
			acctFilter.getFilterItems().add(
					new FilterItemInfo("curProject.id", objectId));
		} else {
			acctFilter.getFilterItems().add(
					new FilterItemInfo("fullOrgUnit.id", objectId));
		}
		acctFilter.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		AcctAccreditHelper.handAcctAccreditFilter(null, objectId, acctFilter);
		TreeModel costAcctTree = FDCClientHelper.createDataTree(
				CostAccountFactory.getRemoteInstance(), acctFilter);
		if(acctMaps.containsKey(objectId)){
			acctMap=(Map)acctMaps.get(objectId);
		}else{
			acctMap=this.initAcct(acctFilter);
			acctMaps.put(objectId, acctMap);
		}
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree
				.getRoot();
		Enumeration childrens = root.depthFirstEnumeration();
		int maxLevel = 0;
		while (childrens.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens
					.nextElement();
			if (node.getUserObject() != null && node.getLevel() > maxLevel) {
				maxLevel = node.getLevel();
			}
		}
		this.tblMain.getTreeColumn().setDepth(maxLevel);
		fetchData(objectId);
/*		setApportionData();
		initAimCostData(objectId);
		initDynamicCostData(objectId);
		this.happenGetter = new HappenDataGetter(objectId, true, true);
*/
		// addTotalRows();
		for (int i = 0; i < root.getChildCount(); i++) {
			fillNode((DefaultMutableTreeNode) root.getChildAt(i));
		}
		this.setUnionData();
		
		//全项目动态成本表中的“已实现成本”字段隐藏（是，隐藏；否，不隐藏）
		hideRealizedCost = FDCUtils.getDefaultFDCParamByKey(null,null,FDCConstants.FDC_PARAM_HIDEREALIZEDCOST);
		tblMain.getColumn(HAS_PAY).getStyleAttributes().setHided(hideRealizedCost);
	}



	/**
	 * 设置父科目汇总数
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setUnionData() {
		KDTable table = this.tblMain;
		List zeroLeverRowList = new ArrayList();
		List amountColumns = new ArrayList();
		amountColumns.add(ASSIGN_AMOUNT_SETTLE);
		amountColumns.add(ASSIGN_AMOUNT_NO_SETTLE);
		if(isUsePartSettle()){
			amountColumns.add(HASALLSETTLED_AMOUNT);
			amountColumns.add(PARTSETTLE_AMOUNT);
		}
		for (int j = 0; j < this.changeTypes.size(); j++) {
			amountColumns
					.add(changeTypes.get(j).getId().toString() + NO_SETTLE);
			amountColumns.add(changeTypes.get(j).getId().toString() + SETTLE);
		}
		amountColumns.add(SETTLE_ADJUST);
		amountColumns.add(SUB_TOTAL_NO_SETTLE);
		amountColumns.add(SUB_TOTAL_SETTLE);
		amountColumns.add(NO_TEXT);
		amountColumns.add(HAS_PAY);
		amountColumns.add(HAS_PAID);
		amountColumns.add(AIM_COST);
		amountColumns.add(HAS_HAPPEN);
		amountColumns.add(INTENDING_HAPPEN);
		amountColumns.add(DYNAMIC_COST);
		amountColumns.add(DIFF);
		amountColumns.add(SELL_PART);
		amountColumns.add(BUILD_PART);
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getTreeLevel() == 0) {
				zeroLeverRowList.add(row);
			}
			if (row.getUserObject() == null) {
				// 设置汇总行
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					}
				}
				for (int k = 0; k < amountColumns.size(); k++) {
					String colName = (String) amountColumns.get(k);
					BigDecimal amount = FDCHelper.toBigDecimal(row.getCell(colName).getValue());
					boolean hasData = false;
					
					/**
					 * 所有报表的所有单方不存在上下级汇总关系，而应该是各个级次的都等于各自的对应成本除以对应的面积，而非下级的单方汇总
					 * @author pengwei_hou Date: 2009-01-18 16:57:23
					 */
					if(colName.equals(BUILD_PART)){
						
						BigDecimal rowBuildAmt = FDCHelper.toBigDecimal(row.getCell(DYNAMIC_COST).getValue());
						amount = FDCNumberHelper.divide(rowBuildAmt, this.buildArea, 2, BigDecimal.ROUND_HALF_UP);
						if(rowBuildAmt != null){
							hasData = true;
						}
						
					} else if(colName.equals(SELL_PART)){
						
						BigDecimal rowSellAmt = FDCHelper.toBigDecimal(row.getCell(DYNAMIC_COST).getValue());
						amount = FDCNumberHelper.divide(rowSellAmt, this.sellArea, 2, BigDecimal.ROUND_HALF_UP);
						if(rowSellAmt != null){
							hasData = true;
						}
						
					} else {
					
						for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
							IRow rowAdd = (IRow) rowList.get(rowIndex);
							if (rowAdd.getCell(colName).getStyleAttributes()
									.getFontColor().equals(Color.RED)) {
							}
							Object value = rowAdd.getCell(colName).getValue();
							if (value != null) {
								if (value instanceof BigDecimal) {
									amount = amount.add((BigDecimal) value);
								} else if (value instanceof Integer) {
									amount = amount.add(new BigDecimal(
											((Integer) value).toString()));
								}
								hasData = true;
							}
						}
						
					}
					//if (hasData) {
						if(amount!=null&&amount.compareTo(FDCHelper.ZERO) != 0){
							row.getCell(colName).setValue(amount);
						}
						else
							row.getCell(colName).setValue(null);
					//}
				}
			}
		}
		if(amountColumns.size()>0){
			String[] columns=new String[amountColumns.size()];
			amountColumns.toArray(columns);
			try{
				AimCostClientHelper.setTotalCostRow(table, columns);
			}catch(Exception e){
				handUIException(e);
			}
		}
		// IRow projectRow = table.getRow(0);
		// IRow noSplitRow = table.getRow(1);
		// IRow splitRow = table.getRow(2);
		// for (int i = 0; i < amountColumns.size(); i++) {
		// String colName = (String) amountColumns.get(i);
		// BigDecimal amount = FMConstants.ZERO;
		// boolean hasData = false;
		// for (int rowIndex = 0; rowIndex < zeroLeverRowList.size();
		// rowIndex++) {
		// IRow rowAdd = (IRow) zeroLeverRowList.get(rowIndex);
		// Object value = rowAdd.getCell(colName).getValue();
		// if (value != null) {
		// if (value instanceof BigDecimal) {
		// amount = amount.add((BigDecimal) value);
		// } else if (value instanceof Integer) {
		// amount = amount.add(new BigDecimal(((Integer) value)
		// .toString()));
		// }
		// hasData = true;
		// }
		// }
		// if (hasData) {
		// splitRow.getCell(colName).setValue(amount);
		// }
		// }
		// for (int i = 0; i < amountColumns.size(); i++) {
		// String colName = (String) amountColumns.get(i);
		// BigDecimal projectValue = (BigDecimal)
		// projectRow.getCell(colName).getValue();
		// BigDecimal splitValue = (BigDecimal)
		// splitRow.getCell(colName).getValue();
		// if (projectValue == null&&splitValue == null) {
		// continue;
		// }
		// if (projectValue == null) {
		// projectValue=FDCHelper.ZERO;
		// }
		// if (splitValue == null) {
		// splitValue=FDCHelper.ZERO;
		// }
		// BigDecimal noSplitValue = projectValue.subtract(splitValue);
		// noSplitRow.getCell(colName).setValue(noSplitValue);
		// }

	}

	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {

	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.btnChart.setIcon(EASResource.getIcon("imgTbtn_planchart"));
		this.menuChat.setIcon(EASResource.getIcon("imgTbtn_planchart"));
		menuItemSave.setIcon(FDCClientHelper.ICON_SAVE);
		btnSave.setIcon(FDCClientHelper.ICON_SAVE);
		actionMonthSave.setEnabled(false);
		actionMonthSave.setVisible(false);
		btnMonthSave.setEnabled(false);
		btnMonthSave.setVisible(false);
		menuBiz.setVisible(false);
//		btnMonthSave.setIcon(EASResource.getIcon("imgTbtn_closeinitialize"));
		this.actionSave.setEnabled(false);
	}

	public void showChart(int type) {
		ChartData data = new ChartData();
		if (type == 1) {
			List colKeys = new ArrayList();
			colKeys.add(AIM_COST);
			colKeys.add(DYNAMIC_COST);
			String[] serials = new String[colKeys.size()];
			for (int i = 0; i < colKeys.size(); i++) {
				serials[i] = (String) tblMain.getHeadRow(0).getCell(
						(String) colKeys.get(i)).getValue();
			}
			data.setSeriesKeys(serials);
			List rows = new ArrayList();
			for (int i = 0; i < tblMain.getSelectManager().size(); i++) {
				KDTSelectBlock selectBlock = tblMain.getSelectManager().get(i);
				for (int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++) {
					IRow row = this.tblMain.getRow(j);
					rows.add(row);
				}
			}

			for (int i = 0; i < rows.size(); i++) {
				fillChartDataByRow(data, (IRow) rows.get(i), colKeys);
			}
			data.setChartType(ChartType.CT_COLUMNCLUSTERED3D);
		} else if (type == 2) {
			String noText = (String) tblMain.getHeadRow(0).getCell(NO_TEXT)
					.getValue();
			String intendingHappen = (String) tblMain.getHeadRow(0).getCell(
					INTENDING_HAPPEN).getValue();
			String[] serials = new String[] { "合同性成本", noText, intendingHappen };
			data.setSeriesKeys(serials);

			List rows = new ArrayList();
			for (int i = 0; i < tblMain.getSelectManager().size(); i++) {
				KDTSelectBlock selectBlock = tblMain.getSelectManager().get(i);
				for (int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++) {
					IRow row = this.tblMain.getRow(j);
					rows.add(row);
				}
			}
			for (int i = 0; i < rows.size(); i++) {
				IRow row = (IRow) rows.get(i);
				BigDecimal[] values = new BigDecimal[serials.length];
				BigDecimal subNoSettle = (BigDecimal) row.getCell(
						SUB_TOTAL_NO_SETTLE).getValue();
				BigDecimal subSettle = (BigDecimal) row.getCell(
						SUB_TOTAL_SETTLE).getValue();
				if (subNoSettle == null) {
					subNoSettle = FDCHelper.ZERO;
				}
				if (subSettle == null) {
					subSettle = FDCHelper.ZERO;
				}
				values[0] = subNoSettle.add(subSettle);
				values[1] = (BigDecimal) row.getCell(NO_TEXT).getValue();
				values[2] = (BigDecimal) row.getCell(INTENDING_HAPPEN)
						.getValue();
				data.addGroupData(
						row.getCell("acctName").getValue().toString(), values);
			}
			data.setChartType(ChartType.CT_MULTIPIE);
		}
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		// colKeys.add("intendingHappen");

		data.setTitle(proNode.getText());

		try {
			ChartUI.showChart(this, data);
		} catch (UIException e) {
			e.printStackTrace();
		}
	}

	private void fillChartDataByRow(ChartData data, IRow row, List colKeys) {
		BigDecimal[] values = new BigDecimal[colKeys.size()];
		for (int k = 0; k < colKeys.size(); k++) {
			values[k] = (BigDecimal) row.getCell((String) colKeys.get(k))
					.getValue();
		}
		data
				.addGroupData(row.getCell("acctName").getValue().toString(),
						values);
	}

	public void onLoad() throws Exception {		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.getSorter().add(new SorterItemInfo("number"));
		view.getSelector().add("id");
		view.getSelector().add("name");
		this.changeTypes = ChangeTypeFactory.getRemoteInstance()
				.getChangeTypeCollection(view);
		//在super.onLoad()调用之前,对actionQuery进行设置,否则表格设置会保存不住  By zhiyuan_tang 2010/06/29
		this.actionQuery.setVisible(false);
		this.actionQuery.setEnabled(false);
		super.onLoad();
		initControl();
		actionSave.setEnabled(false);
//		actionSave.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
		menuItemSave.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
//		getActionMap().put("actionSave", actionSave);
//		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl S"),"actionSave");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl shift S"),"actionSave");
		actionMonthSave.setEnabled(true);
//		actionSave.putValue(Action.ACCELERATOR_KEY, ks);
	}

	private void initControl() {
		this.menuEdit.setVisible(false);
		this.separatorEdit1.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setVisible(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setVisible(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setVisible(false);
		this.actionView.setEnabled(false);
		//actionQuery的设置，要在super.onload()之前调用  By zhiyuan_tang 2010/06/29
//		this.actionQuery.setVisible(false);
//		this.actionQuery.setEnabled(false);
		this.actionLocate.setVisible(false);
		this.actionLocate.setEnabled(false);
		this.menuItemSubmit.setVisible(false);
		KDMenuItem menuItem1 = new KDMenuItem();
		menuItem1.setAction(new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				showChart(1);
			}
		});
		menuItem1.setText("全项目成本差额图");
		this.btnChart.addAssistMenuItem(menuItem1);
		KDMenuItem menuItem2 = new KDMenuItem();
		menuItem2.setAction(new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				showChart(2);
			}
		});
		menuItem2.setText("动态成本构成图");
		this.btnChart.addAssistMenuItem(menuItem2);
		this.menuItemChart1.setAction(new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				showChart(1);
			}
		});
		this.menuItemChart1.setText("全项目成本差额图");
		this.menuItemChart2.setAction(new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				showChart(2);
			}
		});
		this.menuItemChart2.setText("动态成本构成图");
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
	}

	private void fillNode(DefaultMutableTreeNode node) throws BOSException,
			SQLException, EASBizException {
		DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (!proNode.isLeaf()) {
			return;
		}
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
			return;
		}
		String acctId = costAcct.getId().toString();
		IRow row = tblMain.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		row.getCell("acctNumber").setValue(
				costAcct.getLongNumber().replace('!', '.'));
		row.getCell("acctName").setValue(costAcct.getName());
		if (node.isLeaf()) {
			BigDecimal aimAmount = (BigDecimal) this.aimCostMap.get(acctId);
			row.getCell(AIM_COST).setValue(aimAmount);
			HappenDataInfo happenDataInfo = (HappenDataInfo) this.happenGetter.conSplitMap
					.get(acctId + 0);
			BigDecimal noSettConAmount = null;
			if (happenDataInfo != null) {
				noSettConAmount = happenDataInfo.getAmount();
			}
			row.getCell(ASSIGN_AMOUNT_NO_SETTLE).setValue(noSettConAmount);
			BigDecimal noSettleChangeSumAmount = null;
			for (int i = 0; i < changeTypes.size(); i++) {
				ChangeTypeInfo change = changeTypes.get(i);
				happenDataInfo = (HappenDataInfo) this.happenGetter.changeSplitMap
						.get(acctId + change.getId().toString() + 0);
				BigDecimal changeAmount = null;
				if (happenDataInfo != null) {
					changeAmount = happenDataInfo.getAmount();
				}
				if (changeAmount != null) {
					row.getCell(change.getId().toString() + NO_SETTLE)
							.setValue(changeAmount);
					if (noSettleChangeSumAmount == null) {
						noSettleChangeSumAmount = FDCHelper.ZERO;
					}
					noSettleChangeSumAmount = noSettleChangeSumAmount
							.add(changeAmount);
				}
			}
			BigDecimal noSettleTotal = null;
			if (noSettConAmount != null) {
				noSettleTotal = noSettConAmount;
			}
			if (noSettleChangeSumAmount != null) {
				if (noSettleTotal == null) {
					noSettleTotal = noSettleChangeSumAmount;
				} else {
					noSettleTotal = noSettleTotal.add(noSettleChangeSumAmount);
				}
			}
			row.getCell(SUB_TOTAL_NO_SETTLE).setValue(noSettleTotal);
			happenDataInfo = (HappenDataInfo) this.happenGetter.conSplitMap
					.get(acctId + 1);
			BigDecimal settConAmount = null;
			if (happenDataInfo != null) {
				settConAmount = happenDataInfo.getAmount();
			}
			row.getCell(ASSIGN_AMOUNT_SETTLE).setValue(settConAmount);
			BigDecimal settleChangeSumAmount = null;
			for (int i = 0; i < changeTypes.size(); i++) {
				ChangeTypeInfo change = changeTypes.get(i);
				happenDataInfo = (HappenDataInfo) this.happenGetter.changeSplitMap
						.get(acctId + change.getId().toString() + 1);
				BigDecimal changeAmount = null;
				if (happenDataInfo != null) {
					changeAmount = happenDataInfo.getAmount();
				}
				if (changeAmount != null) {
					row.getCell(change.getId().toString() + SETTLE).setValue(
							changeAmount);
					if (settleChangeSumAmount == null) {
						settleChangeSumAmount = FDCHelper.ZERO;
					}
					settleChangeSumAmount = settleChangeSumAmount
							.add(changeAmount);
				}
			}
			happenDataInfo = (HappenDataInfo) this.happenGetter.settleSplitMap.get(acctId);
			BigDecimal settleTotal = null;
			if (happenDataInfo != null) {
				settleTotal = happenDataInfo.getAmount();
			}
			row.getCell(SUB_TOTAL_SETTLE).setValue(settleTotal);

			BigDecimal settleAdjust = null;
			if (settleTotal != null) {
				settleAdjust = settleTotal;
			}
			if (settConAmount != null) {
				if (settleAdjust == null) {
					settleAdjust = FDCHelper.ZERO;
				}
				settleAdjust = settleAdjust.subtract(settConAmount);
			}
			if (settleChangeSumAmount != null) {
				if (settleAdjust == null) {
					settleAdjust = FDCHelper.ZERO;
				}
				settleAdjust = settleAdjust.subtract(settleChangeSumAmount);
			}
			row.getCell(SETTLE_ADJUST).setValue(settleAdjust);

			happenDataInfo = (HappenDataInfo) this.happenGetter.noTextSplitMap
					.get(acctId);
			BigDecimal noTextAmount = null;
			if (happenDataInfo != null) {
				noTextAmount = happenDataInfo.getAmount();
			}
			row.getCell(NO_TEXT).setValue(noTextAmount);

			//已实现=已完工工程量
			happenDataInfo = (HappenDataInfo) this.happenGetter.paySplitMap
					.get(acctId);
			BigDecimal hasPayAmount = null;
			if (happenDataInfo != null) {
				hasPayAmount = happenDataInfo.getAmount();
			}
			row.getCell(HAS_PAY).setValue(hasPayAmount);

			//已付款
			happenDataInfo = (HappenDataInfo) this.happenGetter.paidSplitMap
			.get(acctId);
			BigDecimal hasPaidAmount = null;
			if (happenDataInfo != null) {
				hasPaidAmount = happenDataInfo.getAmount();
			}
			row.getCell(HAS_PAID).setValue(hasPaidAmount);
	
			BigDecimal hasHappenAmount = null;
			if (noSettleTotal != null) {
				hasHappenAmount = noSettleTotal;
			}
			if (settleTotal != null) {
				if (hasHappenAmount == null) {
					hasHappenAmount = FDCHelper.ZERO;
				}
				hasHappenAmount = hasHappenAmount.add(settleTotal);
			}
			if (noTextAmount != null) {
				if (hasHappenAmount == null) {
					hasHappenAmount = FDCHelper.ZERO;
				}
				hasHappenAmount = hasHappenAmount.add(noTextAmount);
			}
			row.getCell(HAS_HAPPEN).setValue(hasHappenAmount);

			BigDecimal adjustAmount = null;
			BigDecimal dynamicAmount = null;
			// 动态成本跟节点要求汇总
			if (proNode.isLeaf()) {
				adjustAmount = (BigDecimal) this.dynamicCostMap.get(acctId);
			} else {
				adjustAmount = getSumAdjustCol(acctId);
			}
			if (aimAmount != null) {
				dynamicAmount = aimAmount;
			}
			if (adjustAmount != null) {
				if (dynamicAmount == null) {
					dynamicAmount = adjustAmount;
				} else {
					dynamicAmount = dynamicAmount.add(adjustAmount);
				}
			}
			row.getCell(DYNAMIC_COST).setValue(dynamicAmount);

			BigDecimal intendingHappen = null;
			if (dynamicAmount != null) {
				intendingHappen = dynamicAmount;
			}
			if (hasHappenAmount != null) {
				if (intendingHappen == null) {
					intendingHappen = FDCHelper.ZERO;
				}
				intendingHappen = intendingHappen.subtract(hasHappenAmount);
			}
			row.getCell(INTENDING_HAPPEN).setValue(intendingHappen);
			BigDecimal diff = null;
			if (dynamicAmount != null) {
				diff = dynamicAmount;
			}
			if (aimAmount != null) {
				if (diff == null) {
					diff = FDCHelper.ZERO;
				}
				diff = diff.subtract(aimAmount);
			}
			row.getCell(DIFF).setValue(diff);

			BigDecimal sellPart = null;
			if (dynamicAmount != null && sellArea != null
					&& sellArea.compareTo(FDCHelper.ZERO) != 0) {
				sellPart = dynamicAmount.divide(sellArea,2,
						BigDecimal.ROUND_HALF_UP);
				row.getCell(SELL_PART).setValue(sellPart);
			}

			BigDecimal buildPart = null;
			if (dynamicAmount != null && buildArea != null
					&& buildArea.compareTo(FDCHelper.ZERO) != 0) {
				buildPart = dynamicAmount.divide(buildArea,2,
						BigDecimal.ROUND_HALF_UP);
				row.getCell(BUILD_PART).setValue(buildPart);
			}
			// 区分叶子节点
			row.setUserObject(costAcct);
			
			if(isUsePartSettle()){
				HappenDataInfo partSettInfo = this.happenGetter.getPartSettleInfo(acctId);
				BigDecimal partSettleAmt=null;
				if(partSettInfo!=null){
					partSettleAmt=partSettInfo.getAmount();
				}
				row.getCell(PARTSETTLE_AMOUNT).setValue(partSettleAmt);
//				已实现产值：部分结算+已结算合同+非合同性成本之和
				BigDecimal hasAllSettleAmt=FDCNumberHelper.add(partSettleAmt, settleTotal);
				hasAllSettleAmt=FDCNumberHelper.add(hasAllSettleAmt, noTextAmount);
				row.getCell(HASALLSETTLED_AMOUNT).setValue(hasAllSettleAmt);
			}
		}
		else{
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			this.fillNode((DefaultMutableTreeNode) node.getChildAt(i));
		}
	}

	private BigDecimal getSumAdjustCol(String acctId) throws BOSException,
			SQLException {
		StringBuffer innerSql = new StringBuffer();
		innerSql.append("select fid from " + FDCHelper.getFullAcctSql()
				+ " where ");
		String fullNumber = "";
		CostAccountInfo acct = (CostAccountInfo) this.acctMap.get(acctId);
		if (acct.getCurProject() != null) {
			fullNumber = acct.getCurProject().getFullOrgUnit().getLongNumber()
					+ "!" + acct.getCurProject().getLongNumber();
		} else {
			fullNumber = acct.getFullOrgUnit().getLongNumber();
		}
		String longNumber = acct.getLongNumber();
		innerSql.append(" (FLongNumber ='").append(longNumber).append("'")
				.append(" or FLongNumber like '").append(longNumber).append(
						"!%' ").append(" or FLongNumber like '%!").append(
						longNumber).append("!%') ");
		innerSql.append("and (FullNumber =	'").append(fullNumber).append("'")
				.append(" or FullNumber like '").append(fullNumber).append(
						"!%' ").append(" or FullNumber like '%!").append(
						fullNumber).append("!%') ");

		String sql = "select sum(FAdjustSumAmount) sumAmount from T_AIM_DynamicCost where FAccountId in ("
				+ innerSql.toString() + ")";
		IRowSet rs = SQLExecutorFactory.getRemoteInstance(sql).executeSQL();
		BigDecimal adjustAmount = null;
		while (rs.next()) {
			adjustAmount = rs.getBigDecimal("sumAmount");
		}
		if (adjustAmount != null && adjustAmount.compareTo(FDCHelper.ZERO) == 0) {
			return null;
		}
		return adjustAmount;
	}

	private Map initAcct(FilterInfo acctFilter) throws BOSException {
		Map acctMap=new HashMap();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("longNumber"));
		sel.add(new SelectorItemInfo("fullOrgUnit.longNumber"));
		sel.add(new SelectorItemInfo("curProject.longNumber"));
		sel.add(new SelectorItemInfo("curProject.fullOrgUnit"));
		sel.add(new SelectorItemInfo("curProject.fullOrgUnit.longNumber"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(acctFilter);
		CostAccountCollection accts = CostAccountFactory.getRemoteInstance()
				.getCostAccountCollection(view);
		for (int i = 0; i < accts.size(); i++) {
			CostAccountInfo info = accts.get(i);
			this.acctMap.put(info.getId().toString(), info);
		}
		return acctMap;
	}

	public void actionChart_actionPerformed(ActionEvent e) throws Exception {
		this.showChart(1);
	}

	/**
	 * 保存全项目动态成本表和各产品类型动态成本表的数据到成本数据库(动态成本快照)
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {

		saveSnapShot(false);

	}

	public void actionMonthSave_actionPerformed(ActionEvent e) throws Exception {
		saveSnapShot(true);
	}
	
	private void saveSnapShot(boolean isMonthSave) throws BOSException, EASBizException {
		FDCClientUtils.checkSelectProj(this, (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent());

		int rowCount2 = getMainTable().getRowCount();
		String selectProjId = getSelectObjId();
		if (selectProjId == null || rowCount2 == 0)
			return;
		CostMonthlySaveTypeEnum savedType=isMonthSave?CostMonthlySaveTypeEnum.AUTOSAVE:CostMonthlySaveTypeEnum.COMMON;
//		savedType=CostMonthlySaveTypeEnum.COSTMONTHLY;
		FDCCostRptFacadeFactory.getRemoteInstance().saveSnapShot(selectProjId, savedType);
		FDCClientUtils.showOprtOK(this);
	}
	
	public boolean destroyWindow() {
		final boolean isDestroyWindow = super.destroyWindow();
		if(isDestroyWindow){
			clear();
		}
		return isDestroyWindow;
	}
	private void fetchData(String prjId) throws BOSException,EASBizException{
		final FullDynamicCostMap fullDynamicCostMap = FDCCostRptFacadeFactory.getRemoteInstance().getFullDynamicCost(prjId, null);
		this.buildArea=fullDynamicCostMap.getDynBuildApp();
		this.sellArea=fullDynamicCostMap.getDynSellApp();
		this.aimCostMap=fullDynamicCostMap.getAimCostMap();
		this.dynamicCostMap=fullDynamicCostMap.getDynamicCostMapp();
		this.happenGetter=fullDynamicCostMap.getHappenDataGetter();
	}
	
	/**
	 * release resource
	 */
	private void clear(){
		if(aimCostMap!=null)
			this.aimCostMap.clear();
		if(dynamicCostMap!=null)
			this.dynamicCostMap.clear();
//		this.happenGetter.clear();
		if(acctMap!=null)
			this.acctMap.clear();
		if(acctMaps!=null)
			this.acctMaps.clear();
		if(this.retValueNotLeaf!=null)
			this.retValueNotLeaf.clear();
		
		
	}
	private boolean hasInit=false;
	private boolean isUsePartSettle=false;
	/**
	 * 是否启用部分结算
	 * @return
	 *  by sxhong 2008-05-30 13:31:20
	 */
	private  boolean isUsePartSettle(){
		if(!hasInit){
			try{
				isUsePartSettle=FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_MORESETTER);
			}catch(Exception e){
				e.printStackTrace();
			}
			hasInit=true;
		}
		return isUsePartSettle;
	}
	
	
	 public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		 logger.info("actionExport_actionPerformed...");
		try {
			super.actionExport_actionPerformed(e);
		} catch (Exception ex) {
			logger.error(ex.getCause(), ex);
		}
	}

	public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception {
		try { 
			super.actionExportSelected_actionPerformed(e);
		} catch (Exception ex) {
			logger.error(ex.getCause(), ex);
		}
	}
	public void handUIException(Throwable exc) {		
		logger.info("handUIException: "+exc.getMessage());
		if(exc instanceof BOSException&&exc.getMessage().startsWith("Can't found propertyUnit: [CU] in")){
			logger.error(exc);
		}else{
			super.handUIException(exc);
		}
	}
	//导出报错问题：报表手工取数不用query
	public int getRowCountFromDB() {
		/*if(this.rowCount == -1){
    		try {
    			IQueryExecutor iexec = getQueryExecutor(this.mainQueryPK, this.mainQuery);
    			this.rowCount = iexec.getRowCount();
    		} catch (BOSException e) {
    			handUIException(e);
    		}
    	}
    	return this.rowCount;*/
		return -1;
	}
	
}