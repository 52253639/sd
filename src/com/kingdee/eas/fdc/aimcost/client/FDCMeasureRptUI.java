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
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTEditHelper;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTTransferAction;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.MeasureCostCollection;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.MeasureEntryCollection;
import com.kingdee.eas.fdc.aimcost.MeasureEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexInfo;
import com.kingdee.eas.fdc.basedata.CellBinder;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述：测算汇总表
 * 参考MeasureCollectTable,PlanIndexTable,AimMeasureCostEditUI实现
 */
public class FDCMeasureRptUI extends AbstractFDCMeasureRptUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCMeasureRptUI.class);
    
    private TreeModel costAcctTree = null;
    private boolean isFirstLoad=true;
    private List tables = null;
    private Map initData = null;
    private MeasureCostCollection measureCosts = null;
    private Map measureCostsMap = null;
	private Map costEntrysMap = new HashMap();
    private Map planIndexsMap = null;
    private int entrysSize =0;
    private CellBinder binder=null;
    /**
     * output class constructor
     */
    public FDCMeasureRptUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = (KDTable) this.tables.get(this.plTables
				.getSelectedIndex());
		table.getPrintManager().print();
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		KDTable table = (KDTable) this.tables.get(this.plTables
				.getSelectedIndex());
		table.getPrintManager().printPreview();
	}

    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

	protected IObjectValue createNewData() {
		// TODO Auto-generated method stub
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}

	public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = super.getSelectors();
        sic.add("name");
        sic.add("number");
        sic.add("fullOrgUnit.id");
        return sic;
    }
	
	public void onLoad() throws Exception {
		super.onLoad();
		fetchData();
		prmtProject.setValue(editData);
		// 取非明细工程项目的成本科目
		String orgId = editData.getFullOrgUnit().getId().toString();
		if (costAcctTree == null) {
			FilterInfo acctFilter = new FilterInfo();
			acctFilter.getFilterItems().add(
					new FilterItemInfo("curProject.id", editData.getId().toString()));
			acctFilter.getFilterItems().add(
					new FilterItemInfo("isEnabled", new Integer(1)));
			try {
				costAcctTree = FDCClientHelper.createDataTree(
						CostAccountFactory.getRemoteInstance(), acctFilter);
			} catch (BOSException e) {
				throw new BOSException(e);
			} catch (Exception e) {
				throw new BOSException(e);
			}

		}
		
		try {
			addPanel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void fetchData() throws EASBizException, BOSException {
		Map params = new HashMap();
		params.put("curProjectInfo", editData);
		initData = MeasureCostFactory.getRemoteInstance().getMeasureRptData(
				params);
		measureCosts = (MeasureCostCollection)initData.get("measureCosts");
		measureCostsMap = (Map)initData.get("measureCostsMap");
		costEntrysMap = (Map)initData.get("costEntrysMap");
		planIndexsMap = (Map)initData.get("planIndexsMap");
		entrysSize = ((Integer)initData.get("entrysSize")).intValue();
		
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		actionAddNew.setVisible(false);
		actionAddNew.setEnabled(false);
		actionRemove.setVisible(false);
		actionRemove.setEnabled(false);
		actionEdit.setVisible(false);
		actionEdit.setEnabled(false);
		actionPre.setVisible(false);
		actionPre.setEnabled(false);
		actionNext.setEnabled(false);
		actionNext.setVisible(false);
		actionFirst.setEnabled(false);
		actionFirst.setVisible(false);
		actionLast.setVisible(false);
		actionLast.setEnabled(false);
		actionCancel.setVisible(false);
		actionCancel.setEnabled(false);
		actionCancelCancel.setEnabled(false);
		actionCancelCancel.setVisible(false);
		actionSave.setVisible(false);
		actionSave.setEnabled(false);
		actionCopy.setVisible(false);
		actionCopy.setEnabled(false);
		actionSubmit.setVisible(false);
		actionSubmit.setEnabled(false);
		menuEdit.setVisible(false);
		menuEdit.setEnabled(false);
		menuBiz.setVisible(false);
		menuBiz.setEnabled(false);
		menuView.setVisible(false);
		menuView.setEnabled(false);
//		menuFile.setVisible(false);
//		menuFile.setEnabled(false);
		
		actionPrint.setEnabled(true);
		actionPrintPreview.setEnabled(true);
	}
	public void loadFields() {
		super.loadFields();
		
	}
	private void addPanel() throws Exception {
		if(isFirstLoad) {
			isFirstLoad=false;
		}else{
			return;
		}
		ChangeListener[] changeListeners = plTables.getChangeListeners();
		for(int i=0;i<changeListeners.length;i++){
			plTables.removeChangeListener(changeListeners[i]);
		}
		this.plTables.removeAll();
		tables = new ArrayList();
		KDTable table =new KDTable();
		tables.add(table);
		FDCTableHelper.setColumnMoveable(table, true);
		FDCTableHelper.addTableMenu(table);
		this.plTables.add(table, "测算汇总表");
		dealCollectTable(table);
		table.setEnabled(false);
		
		table=new KDTable(12,1,0);
		binder = new CellBinder();
		tables.add(table);
		FDCTableHelper.setColumnMoveable(table, true);
		FDCTableHelper.addTableMenu(table);
		this.plTables.add(table, "规划指标表");
		dealIndexTable(table);
		table.setEnabled(false);
		
		table = new KDTable();
		tables.add(table);
		FDCTableHelper.addTableMenu(table);
		FDCTableHelper.setColumnMoveable(table, true);
		this.plTables.add(table, "六类公摊及期间费");
		
		dealSixTable(table);
		table.setEnabled(false);
		
		for(int i=0;i<measureCosts.size();i++){
			PlanIndexInfo planIndex = (PlanIndexInfo)planIndexsMap.get(measureCosts.get(i).getId().toString());
			for(int j=0;j<planIndex.getEntrys().size();j++){
				PlanIndexEntryInfo entry=planIndex.getEntrys().get(j);
				if(entry!=null&&entry.getProduct()!=null){
					table=addProductTypeTable(planIndex,entry);
					
				}
			}
			
		}
	
		//从界面取数
		for(int i=0;i<changeListeners.length;i++){
			plTables.addChangeListener(changeListeners[i]);
		}
	}
	public KDTable addProductTypeTable(PlanIndexInfo info,PlanIndexEntryInfo entry){
		boolean isadd=true; 
		for(int i=3;i<tables.size();i++){
			KDTable table=(KDTable) tables.get(i);
			if(table.getHeadRow(0).getUserObject() instanceof ProductTypeInfo){
				if(((ProductTypeInfo)table.getHeadRow(0).getUserObject()).getId().equals(entry.getProduct().getId())){
					isadd=false;
					break;
				}
			}
		}
		if(!isadd) return null;
		
		KDTable table = new KDTable();
		FDCTableHelper.setColumnMoveable(table, true);
		initTable(table, CostAccountTypeEnum.MAIN,entry.getProduct().getId().toString());
		CurProjectInfo project = ((MeasureCostInfo) measureCostsMap.get(info
				.getHeadID())).getProject();
		String tableName = project.getName()
				+ entry.getProduct().getName();
		table.getHeadRow(0).getCell(0).setUserObject(entry);
		table.getHeadRow(0).getCell(1).setUserObject(project);
		table.getHeadRow(0).setUserObject(entry.getProduct());
		try {
			fillTable(table);
			setUnionData(table);
		} catch (Exception e) {
			handUIException(e);
		}
		this.plTables.add(table, tableName);
		FDCTableHelper.addTableMenu(table);
		table.setEnabled(false);
		return table;
		
	}
	/**
	 * 设置父科目汇总数
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setUnionData(KDTable table) {
		String[] cols = new String[] { 
				"sumPrice","amount","buildPart","sellPart","adjustAmt"
				};
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getUserObject() instanceof CostAccountInfo) {
				// 设置汇总行
				int level = row.getTreeLevel();
				List aimRowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() instanceof MeasureEntryInfo) {
						aimRowList.add(rowAfter);
					}

				}
				
				for (int j = 0; j < cols.length; j++) {
					BigDecimal sum = FDCHelper.ZERO;
					if(cols[j].equals("sellPart")){
						BigDecimal amount = (BigDecimal)row.getCell("amount").getValue();
						if (amount != null) {
							Object obj=table.getHeadRow(0).getCell(0).getUserObject();
							if(obj instanceof PlanIndexEntryInfo){
								BigDecimal sellArea = ((PlanIndexEntryInfo)obj).getSellArea();
								if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
									BigDecimal sellPart = FDCNumberHelper.divide(amount, sellArea, 2, BigDecimal.ROUND_HALF_UP);
									sum = sellPart;
								}
							}else if(obj instanceof PlanIndexInfo){
								BigDecimal sellArea = getAllSellArea();
								if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
									BigDecimal sellPart = FDCNumberHelper.divide(amount, sellArea, 2,BigDecimal.ROUND_HALF_UP);
									sum = sellPart;
								}
							}
						} 
						
					}else if(cols[j].equals("buildPart")) {
						BigDecimal amount = (BigDecimal)row.getCell("amount").getValue();
						if (amount != null) {
							Object obj=table.getHeadRow(0).getCell(0).getUserObject();
							if(obj instanceof PlanIndexEntryInfo){
								BigDecimal buildArea = ((PlanIndexEntryInfo)obj).getBuildArea();
								if (buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0) {
									BigDecimal buildPart = FDCNumberHelper.divide(amount, buildArea, 2, BigDecimal.ROUND_HALF_UP);
									sum = buildPart;
								}
							}else if(obj instanceof PlanIndexInfo){
								BigDecimal buildArea = getAllBuildArea();
								if (buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0) {
									BigDecimal buildPart = FDCNumberHelper.divide(amount, buildArea, 2,BigDecimal.ROUND_HALF_UP);
									sum = buildPart;
								}
							}
						} 
					}
					else {
						
						for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
							IRow rowAdd = (IRow) aimRowList.get(rowIndex);
							Object value = rowAdd.getCell(cols[j]).getValue();
							if (value != null) {
								if (value instanceof BigDecimal) {
									sum = sum.add((BigDecimal) value);
								} else if (value instanceof Integer) {
									sum = sum.add(new BigDecimal(((Integer) value)
											.toString()));
								}
							}
						}
						
					}
					if(sum != null && sum.compareTo(FDCHelper.ZERO)==0){
						sum = null;
					}
					row.getCell(cols[j]).setValue(sum);
				}
			}
		}
		addTotalRow(table);
	}
	private void addTotalRow(KDTable table){
		try {
			AimCostClientHelper.setTotalCostRow(table, new String[]{"sumPrice","buildPart","sellPart","adjustAmt","amount"});
		} catch (Exception e) {
			handUIException(e);
		}
    }
	
	private void dealCollectTable(KDTable table) {
		initCollectTable(table);
		fillCollectTable(table);
		setCollectUnionData(table);
	}

	private void dealIndexTable(KDTable table) {
		initIndexTable(table);
	}

	private void dealSixTable(KDTable table) {
		initTable(table, CostAccountTypeEnum.SIX,null);
		table.getHeadRow(0).getCell(0).setUserObject(getFixIndexInfo());
		try {
			fillTable(table);
			setUnionData(table);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void initCollectTable(KDTable table) {
		table.removeColumns();
		int colums = 4 + 2 + entrysSize * 3 ;
		table.createBlankTable(colums, 2, 0);
		table.getColumn(0).setKey("acctNumber");
		table.getColumn(1).setKey("acctName");
		int base = 2;//科目名称下一列，即数据列
		int k = 0;
		for (int i = 0; i < measureCosts.size(); i++) {
			MeasureCostInfo measureCost = measureCosts.get(i);
			PlanIndexInfo planIndex = (PlanIndexInfo)planIndexsMap.get(measureCost.getId().toString());
			for (int j = 0; j < planIndex.getEntrys().size(); j++) {
				if(planIndex.getEntrys().get(j)==null || planIndex.getEntrys().get(j).getProduct()==null){
					continue;
				}
				//项目+产品两个维度定义列
				table.getColumn(base + k).setKey("buildAvg" + i + j);
				FDCHelper.formatTableNumber(table, "buildAvg" + i + j);
				k++;
			}
		}
		table.getColumn(base + entrysSize).setKey("buildAvg");
		FDCHelper.formatTableNumber(table, "buildAvg");
		
		base = base + entrysSize + 1;
		k=0;
		for (int i = 0; i < measureCosts.size(); i++) {
			MeasureCostInfo measureCost = measureCosts.get(i);
			PlanIndexInfo planIndex = (PlanIndexInfo)planIndexsMap.get(measureCost.getId().toString());
			for (int j = 0; j < planIndex.getEntrys().size(); j++) {
				if(planIndex.getEntrys().get(j)==null || planIndex.getEntrys().get(j).getProduct()==null){
					continue;
				}
				table.getColumn(base + k).setKey("avg" + i + j);
				FDCHelper.formatTableNumber(table, "avg" + i + j);
				k++;
			}
		}
		table.getColumn(base + entrysSize).setKey("avg");
		FDCHelper.formatTableNumber(table, "avg");

		base = base + entrysSize + 1;
		k=0;
		for (int i = 0; i < measureCosts.size(); i++) {
			MeasureCostInfo measureCost = measureCosts.get(i);
			PlanIndexInfo planIndex = (PlanIndexInfo)planIndexsMap.get(measureCost.getId().toString());
			for (int j = 0; j < planIndex.getEntrys().size(); j++) {
				if(planIndex.getEntrys().get(j)==null || planIndex.getEntrys().get(j).getProduct()==null){
					continue;
				}
				table.getColumn(base + k).setKey("split" + i + j);
				FDCHelper.formatTableNumber(table, "split" + i + j);
				k++;
			}
		}
		table.getColumn(base + entrysSize).setKey("total");
		FDCHelper.formatTableNumber(table, "total");
		table.getColumn(colums - 1).setKey("splitType");

		// title
		IRow headRow = table.getHeadRow(0);
		headRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		headRow.getCell("acctNumber").setValue("科目编码");
		headRow.getCell("acctName").setValue("科目名称");
		headRow.getCell("buildAvg").setValue("建筑面积单位成本");
		headRow.getCell("avg").setValue("可售面积单位成本");
		headRow.getCell("total").setValue("总成本");
		headRow.getCell("splitType").setValue("分配方式");
		headRow = table.getHeadRow(1);
		headRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		for (int i = 0; i < measureCosts.size(); i++) {
			MeasureCostInfo measureCost = measureCosts.get(i);
			PlanIndexInfo planIndex = (PlanIndexInfo)planIndexsMap.get(measureCost.getId().toString());
			int entrySize = planIndex.getEntrys().size();
			for (int j = 0; j < entrySize; j++) {
				if(planIndex.getEntrys().get(j)==null || planIndex.getEntrys().get(j).getProduct()==null){
					continue;
				}
				String name = measureCost.getProject().getName()+planIndex.getEntrys().get(j).getProduct().getName();
//				System.out.print("=========================: "+ i + j);
				headRow.getCell("buildAvg" + i+j).setValue(name);
				headRow.getCell("buildAvg" + i+j).setUserObject(measureCost.getProject());
				headRow.getCell("avg" + i+j).setValue(name);
				headRow.getCell("avg" + i+j).setUserObject(measureCost.getProject());
				headRow.getCell("split" + i+j).setValue(name);
			}
		}
		
		headRow.getCell("buildAvg").setValue("平均");
		headRow.getCell("avg").setValue("平均");
		headRow.getCell("total").setValue("合计");
		// merge
		KDTMergeManager mm = table.getHeadMergeManager();
		// 科目编码
		mm.mergeBlock(0, 0, 1, 0);
		// 科目名称
		mm.mergeBlock(0, 1, 1, 1);
		// 分配方式
		mm.mergeBlock(0, colums - 1, 1, colums - 1);
		// 建筑面积单位成本
		mm.mergeBlock(0, 2, 0, 2 + entrysSize);
		// 可售面积单位成本(加1是因为建筑面积单位成本多了平均列，总成本加2类同 by hpw)
		mm.mergeBlock(0, 2 + entrysSize + 1, 0, 2 + entrysSize * 2 + 1);
		// 总成本
		mm.mergeBlock(0, 2 + entrysSize * 2 + 2, 0, colums - 2);
		// 冻结
		table.getViewManager().setFreezeView(-1, 2);
	}
	public void initIndexTable(KDTable table){
				((KDTTransferAction) table.getActionMap().get(KDTAction.PASTE)).setPasteMode(KDTEditHelper.VALUE);
				KDTMergeManager mm = table.getHeadMergeManager();
				mm.mergeBlock(0, 0, 0, 11, KDTMergeManager.SPECIFY_MERGE);
				table.getHeadRow(0).getCell(0).setValue("规划指标表");
				table.setEnabled(false);
				table.getColumn(3).setWidth(150);
				table.getColumn(6).setWidth(120);
				if(isPlanIndexLogic()){
					table.getColumn(7).setWidth(120);
				}else{
					table.getColumn(7).setWidth(90);
				}
				table.getColumn(8).setWidth(50);
				table.getColumn(9).setWidth(50);
				table.getColumn(10).setWidth(70);
				initFixTable(table);

				//设置风格
				StyleAttributes sa = table.getRow(3).getStyleAttributes();
				sa.setHorizontalAlign(HorizontalAlignment.CENTER);
				sa = table.getRow(5).getStyleAttributes();
				sa.setHorizontalAlign(HorizontalAlignment.CENTER);
				sa=table.getRow(6).getStyleAttributes();
				sa.setHorizontalAlign(HorizontalAlignment.RIGHT);

				table.getColumn(1).getStyleAttributes().setNumberFormat("@");
				KDTextField textField=new  KDTextField();
				textField.setMaxLength(300);
				table.getColumn(11).setEditor(new KDTDefaultCellEditor(textField));
				for(int i=0;i<7;i++){
					for(int j=7;j<table.getColumnCount();j++)	{
						if(table.getCell(i, j)==null) continue;
						if(isPlanIndexLogic()){
							if(j==7&&(i==5||i==6)){
								continue;
							}
						}
						table.getCell(i, j).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
					}
				}
				table.getCell(2, 5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
				table.getCell(2, 6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
				if(isPlanIndexLogic()){
					table.getCell(3, 5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
					table.getCell(3, 6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
					table.getCell(4, 5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
					table.getCell(4, 6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
				}else{
					table.getCell(5, 6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
					table.getCell(6, 6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
				}
				table.getCell(1, 4).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
				table.getCell(2, 4).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
				table.getCell(4, 0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
				table.getCell(6, 0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
				table.getCell(1, 4).getStyleAttributes().setNumberFormat("0.00%");
				table.getCell(1, 6).getStyleAttributes().setNumberFormat("0.00%");
				initVariable(table);
				
	}
	public void initTable(KDTable table, CostAccountTypeEnum type,String productId) {
		table.getViewManager().setFreezeView(-1, 3);
		table.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		IColumn column = table.addColumn();
		column.setKey("id");
		column.getStyleAttributes().setHided(true);
		column = table.addColumn();
		column.setKey("acctNumber");
		column = table.addColumn();
		column.setKey("acctName");
		column = table.addColumn();
		column.setKey("indexName");
		column = table.addColumn();
		column.setKey("index");
		column = table.addColumn();
		column.setKey("coefficientName");
		column = table.addColumn();
		column.setKey("coefficient");
		column = table.addColumn();
		column.setKey("workload");
		column = table.addColumn();
		column.setKey("unit");
		column = table.addColumn();
		column.setKey("price");
		column = table.addColumn();
		column.setKey("sumPrice");
		//start调整系数三列
		column = table.addColumn();
		column.setKey("adjustCoefficient");
		column = table.addColumn();
		column.setKey("adjustAmt");
		column = table.addColumn();
		column.setKey("amount");
		
		//end
		column = table.addColumn();
		column.setKey("buildPart");
		column = table.addColumn();
		column.setKey("sellPart");
		column = table.addColumn();
		column.setKey("description");
		IRow row = table.addHeadRow();
		row.getCell("acctNumber").setValue("科目编码");
		row.getCell("acctName").setValue("科目名称");
		row.getCell("indexName").setValue("原始指标名称");
		row.getCell("index").setValue("原始指标值");
		row.getCell("coefficientName").setValue("系数名称");
		row.getCell("coefficient").setValue("系数值");
		row.getCell("workload").setValue("工作量");
		row.getCell("unit").setValue("单位");
		row.getCell("price").setValue("单价");
		if(isUseAdjustCoefficient()){
			row.getCell("sumPrice").setValue("调整前合价");
		}else{
			row.getCell("sumPrice").setValue("合价");
		}
		row.getCell("adjustCoefficient").setValue("调整系数");
		row.getCell("adjustAmt").setValue("调整金额");
		row.getCell("amount").setValue("合价");
		row.getCell("buildPart").setValue("建筑单方");
		row.getCell("sellPart").setValue("可售单方");
		
		if(isUseQuality()){
			row.getCell("description").setValue("品质特征");
		}else{
			table.getColumn("description").getStyleAttributes().setHided(true);
		}
		
		if(!isUseAdjustCoefficient()){
			table.getColumn("adjustCoefficient").getStyleAttributes().setHided(true);
			table.getColumn("adjustAmt").getStyleAttributes().setHided(true);
			table.getColumn("amount").getStyleAttributes().setHided(true);
		}
		FDCHelper.formatTableNumber(table, new String[]{
				"index","workload","sumPrice","amount","adjustAmt","buildPart","sellPart"});
		FDCHelper.formatTableNumber(table, "adjustCoefficient", "#,##0.000000;-#,##0.000000");
		FDCHelper.formatTableNumber(table, "coefficient", "#,##0.0000;-#,##0.0000");
		FDCHelper.formatTableNumber(table, "price", "#,##0.0000;-#,##0.0000");
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("acctName").setEditor(txtEditor);

		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("index").setEditor(numberEditor);

		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("coefficientName").setEditor(txtEditor);

		/*** 项目公司在于进行目标成本测算的时候需要将单价和系数值精确到小数点后四位数，目前只能精确到小数点后两位数
		 * R090514-137
		 * ——by neo****/
		formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(4);
		formattedTextField.setMaximumValue(FDCNumberHelper.TEN_THOUSAND);
		formattedTextField.setMinimumValue(FDCNumberHelper._TEN_THOUSAND);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(true);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("coefficient").setEditor(numberEditor);
		table.getColumn("price").setEditor(numberEditor);
		
		/*******/
		formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("workload").setEditor(numberEditor);

		textField = new KDTextField();
		textField.setMaxLength(80);
		//单位只能选择,不能手工录入
		KDBizPromptBox f7Unit = new KDBizPromptBox();
		f7Unit.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery");
		f7Unit.setEditable(true);
		f7Unit.setDisplayFormat("$name$");
		f7Unit.setEditFormat("$number$");
		f7Unit.setCommitFormat("$number$");
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7Unit);
		table.getColumn("unit").setEditor(f7Editor);
		formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("sumPrice").setEditor(numberEditor);
		table.getColumn("amount").setEditor(numberEditor);
		table.getColumn("adjustAmt").setEditor(numberEditor);
		formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(6);
		formattedTextField.setMaximumValue(FDCNumberHelper.TEN_THOUSAND);
		formattedTextField.setMinimumValue(FDCNumberHelper._TEN_THOUSAND);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(true);
		numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn("adjustCoefficient").setEditor(numberEditor);

		textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
		table.getColumn("description").setEditor(txtEditor);

		Color lockColor =FDCTableHelper.cantEditColor;// new Color(0xF0AAD9);
		table.getColumn("acctNumber").getStyleAttributes().setBackground(
				lockColor);
		table.getColumn("index").getStyleAttributes().setBackground(lockColor);
		table.getColumn("buildPart").getStyleAttributes().setBackground(
				lockColor);
		table.getColumn("sellPart").getStyleAttributes().setBackground(
				lockColor);
		table.getColumn("amount").getStyleAttributes().setBackground(lockColor);
	}
	
	
	/**
	 * 总占地面积(m2)
	 */
	private ICell totalContainAreaCell = null;
	/**
	 * 建筑用地面积(m2)
	 */
	private ICell buildAreaCell = null;
	/**
	 * 总建筑面积(m2)
	 */
	private ICell totalBuildAreaCell = null;
	/**
	 * 建筑物占地面积(m2)
	 */
	private ICell buildContailAreaCell = null;
	/**
	 * 建筑密度
	 */
	private ICell buildDensityCell = null;
	/**
	 * 绿地率
	 */
	private ICell greenAreaRateCell = null;
	/**
	 * 计容积率面积(m2)
	 */
	private ICell cubageRateAreaCell = null;
	/**
	 * 容积率
	 */
	private ICell cubageRateCell = null;
	/**
	 * 道路用地合计
	 */
	private ICell totalRoadAreaCell = null;
	/**
	 * 沥青路面车行道
	 */
	private ICell pitchRoadCell = null;
	/**
	 * 砼路面车行道（停车场）
	 */
	private ICell concreteRoadCell = null;

	/**
	 * 硬质铺装车行道
	 */
	private ICell hardRoadCell = null;
	/**
	 * 硬质铺装广场
	 */
	private ICell hardSquareCell = null;
	/**
	 * 硬质铺装人行道
	 */
	private ICell hardManRoadCell = null;
	/**
	 * 绿化用地合计
	 */
	private ICell totalGreenAreaCell = null;
	/**
	 * 重要公共绿地
	 */
	private ICell importPubGreenAreaCell = null;
	/**
	 * 组团宅间绿化
	 */
	private ICell houseGreenAreaCell = null;
	/**
	 * 底层私家花园
	 */
	private ICell privateGardenCell = null;
	/**
	 * 水景面积
	 */
	private ICell warterViewAreaCell = null;
	// 产品列索引
	private int productColummnIndex = 1;
	/**
	 * 占地面积
	 */
	private int containAreaColummnIndex = 2;
	/**
	 * 容积率面积
	 */
	private int cubageRateColummnIndex = 3;
	/**
	 * 建筑面积
	 */
	private int buildAreaColummnIndex = 4;
	/**
	 * 可售面积
	 */
	private int sellAreaColummnIndex = 5;
	/**
	 * 产品比例
	 */
	private int productRateColummnIndex = 6;
	/**
	 * 平均每户面积
	 */
	private int avgHuColummnIndex = 7;
	/**
	 * 单元数
	 */
	private int unitColummnIndex = 8;
	/**
	 * 户数
	 */
	private int huColummnIndex = 9;
	/**
	 * 是否分摊
	 */
	private int splitColummnIndex = 10;
	/**
	 * 备注
	 */
	private int descColummnIndex = 11;
	/**
	 * 车位数
	 */
	private int parksColummnIndex = 3;
	/**
	 * 住宅小计行
	 */
	private int houseSubIndex = 0;
	/**
	 * 商业小计行
	 */
	private int businessSubIndex = 0;
	/**
	 * 公共配套建筑行
	 */
	private int publicSubIndex = 0;
	/**
	 * 停车小计行
	 */
	private int parkingSubIndex = 0;
	/**
	 * 合计行
	 */
	private int totalIndex=0;
	private void initVariable(KDTable table){
		totalContainAreaCell=table.getCell(0, 2);
		buildAreaCell=table.getCell(0, 4);	
		totalBuildAreaCell=table.getCell(0, 6);	
		buildContailAreaCell=table.getCell(1, 2);	
		buildDensityCell=table.getCell(1, 4);	
		greenAreaRateCell=table.getCell(1, 6);
		cubageRateAreaCell=table.getCell(2, 2);
		cubageRateCell=table.getCell(2, 4);
		totalRoadAreaCell=table.getCell(4, 0);
		pitchRoadCell=table.getCell(4, 2);
		concreteRoadCell=table.getCell(4,3);
		hardRoadCell=table.getCell(4,4);
		if(isPlanIndexLogic()){
			hardSquareCell=table.getCell(6,6);
			hardManRoadCell=table.getCell(6,7);
		}else{
			hardSquareCell=table.getCell(4,5);
			hardManRoadCell=table.getCell(4,6);
		}
		totalGreenAreaCell=table.getCell(6,0);
		importPubGreenAreaCell=table.getCell(6,2);
		houseGreenAreaCell=table.getCell(6,3);
		privateGardenCell=table.getCell(6,4);
		warterViewAreaCell=table.getCell(6,5);
		
		totalIndex=table.getRowCount()-1;
	}
	private void initFixTable(KDTable table){
		table.addRows(7);
		//绑定单元格
		binder.bindCell(table, 0, 1,"总占地面积(m2)"	,"totalContainArea",true);
		binder.bindCell(table, 0, 3,"建筑用地面积(m2)"	,"buildArea",true);
		binder.bindCell(table, 0, 5,"总建筑面积(m2)"	,"totalBuildArea",true);
		binder.bindCell(table, 1, 1,"建筑物占地面积(m2)"	,"buildContainArea",true);
		binder.bindCell(table, 1, 3,"建筑密度"	,"buildDensity",true);
		binder.bindCell(table, 1, 5,"绿地率"	,"greenAreaRate",true);
		binder.bindCell(table, 2, 1,"计容积率面积(m2)"	,"cubageRateArea",true);
		binder.bindCell(table, 2, 3,"容积率"	,"cubageRate",true);
		binder.bindCell(table, 3, 0,"道路用地合计"	,"totalRoadArea",true,true);
		binder.bindCell(table, 3, 2,"沥青路面车行道 "	,"pitchRoad",true,true);
		binder.bindCell(table, 3, 3,"砼路面车行道（停车场）"	,"concreteRoad",true,true);
		binder.bindCell(table, 3, 4,"硬质铺装车行道 "	,"hardRoad",true,true);
		if(isPlanIndexLogic()){
			binder.bindCell(table, 5, 6,"硬质铺装广场 "	,"hardSquare",true,true);
			binder.bindCell(table, 5, 7,"硬质铺装人行道  ","hardManRoad",true,true);
		}else{
			binder.bindCell(table, 3, 5,"硬质铺装广场 "	,"hardSquare",true,true);
			binder.bindCell(table, 3, 6,"硬质铺装人行道  ","hardManRoad",true,true);
		}
		binder.bindCell(table, 5, 0,"绿化用地合计 "	,"totalGreenArea",true,true);
		binder.bindCell(table, 5, 2,"重要公共绿地 "	,"importPubGreenArea",true,true);
		binder.bindCell(table, 5, 3,"组团宅间绿化  "	,"houseGreenArea",true,true);
		binder.bindCell(table, 5, 4,"底层私家花园 "	,"privateGarden",true,true);
		binder.bindCell(table, 5, 5,"水景面积 "	,"warterViewArea",true,true);
		KDTMergeManager mm = table.getMergeManager();
		for(int i=0;i<9;i++){
			mm.mergeBlock(i, 0, i, 1, KDTMergeManager.SPECIFY_MERGE);
		}
		binder.setCellsValue(getFixIndexInfo());
	}
	private PlanIndexInfo getFixIndexInfo(){
		if(initData.get("fixInfo")!=null){
			return (PlanIndexInfo)initData.get("fixInfo");
		}
		return new PlanIndexInfo();
	}
	private void fillCollectTable(KDTable table) {
		table.removeRows();
		table.setUserObject(null);
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree.getRoot();
		Enumeration childrens = root.depthFirstEnumeration();
		int maxLevel = 0;
		while (childrens.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens.nextElement();
			if (node.getUserObject() != null && node.getLevel() > maxLevel) {
				maxLevel = node.getLevel();
			}
		}
		table.getTreeColumn().setDepth(maxLevel + 1);
		for (int i = 0; i < root.getChildCount(); i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) root.getChildAt(i);
			fillCollectNode(child,table);
		}
	}

	private void fillCollectNode(DefaultMutableTreeNode node,KDTable table) {
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
			return;
		}
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		row.setUserObject(costAcct);
		String longNumber = costAcct.getLongNumber();
		longNumber = longNumber.replace('!', '.');
		row.getCell("acctNumber").setValue(longNumber);
		row.getCell("acctName").setValue(costAcct.getName());
		if (node.isLeaf()) {
			loadCollectRow(table,row);
		} else {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillCollectNode((DefaultMutableTreeNode) node.getChildAt(i),table);
			}
		}
	}

	private void loadCollectRow(KDTable table, IRow row) {
		Object obj = row.getUserObject();
		if (!(obj instanceof CostAccountInfo)) {
			return;
		}
		CostAccountInfo costAcct = (CostAccountInfo) obj;
		String key = costAcct.getLongNumber();
		if (costAcct.getType() == CostAccountTypeEnum.SIX) {
			// 取得分摊标准后进行分摊-六大类
			MeasureEntryCollection coll = (MeasureEntryCollection) costEntrysMap.get(key);
			String splitType = null;

			if (coll != null && coll.size() > 0) {
				BigDecimal total = FDCHelper.ZERO;
				for (int i = 0; i < coll.size(); i++) {
					MeasureEntryInfo info = coll.get(i);
					total = total.add(FDCHelper.toBigDecimal(info.getAmount()));
				}
				
				if(total.signum()<=0){
					return;
				}
				row.getCell("total").setValue(total);
			}

		} else {
			// 直接指定-主体建安
			BigDecimal allTotal = FDCHelper.ZERO;
			for (int i = 0; i < measureCosts.size(); i++) {
				MeasureCostInfo measureCost = measureCosts.get(i);
				PlanIndexInfo planIndex = (PlanIndexInfo) planIndexsMap
						.get(measureCost.getId().toString());
				for (int j = 0; j < planIndex.getEntrys().size(); j++) {
					PlanIndexEntryInfo entry = planIndex.getEntrys().get(j);
					if(entry==null ||entry.getProduct()==null){
						continue;
					}
					BigDecimal sellArea = entry.getSellArea();
					BigDecimal buildArea = entry.getBuildArea();
					if (FDCHelper.toBigDecimal(sellArea).compareTo(
							FDCHelper.ZERO) == 0) {
						sellArea = null;
					}
					if (FDCHelper.toBigDecimal(buildArea).compareTo(
							FDCHelper.ZERO) == 0) {
						buildArea = null;
					}
					String productKey = key
							+ measureCost.getProject().getId().toString()
							+ entry.getProduct().getId().toString();
					BigDecimal total = FDCHelper.ZERO;
					MeasureEntryCollection coll = (MeasureEntryCollection) costEntrysMap.get(productKey);
					if (coll != null && coll.size() > 0) {
						for (int k = 0; k < coll.size(); k++) {
							MeasureEntryInfo info = coll.get(k);
							if (info == null)
								continue;
							total = total.add(FDCHelper.toBigDecimal(info.getAmount()));
						}
						allTotal = allTotal.add(total);
						
					}
					if(FDCHelper.toBigDecimal(total).compareTo(FDCHelper.ZERO)==0){
						continue;
					}
					row.getCell("split" + i + j).setValue(total);
					if (sellArea != null) {
						BigDecimal avg = FDCHelper.divide(total, sellArea, 2, BigDecimal.ROUND_HALF_UP); 
						row.getCell("avg" + i + j).setValue(avg);
					}
					if (buildArea != null) {
						BigDecimal avg =FDCHelper.divide(total, buildArea, 2, BigDecimal.ROUND_HALF_UP);  
						row.getCell("buildAvg" + i + j).setValue(avg);
					}
				}

			}
			if (allTotal.compareTo(FDCHelper.ZERO) == 0)
				return;
			row.getCell("total").setValue(allTotal);
		}

	}
	public void setCollectUnionData(final KDTable table) {
		String[] cols = new String[entrysSize * 3 + 3];
		int k=0;
		for (int i = 0; i < measureCosts.size(); i++) {
			MeasureCostInfo measureCost = measureCosts.get(i);
			PlanIndexInfo planIndex = (PlanIndexInfo)planIndexsMap.get(measureCost.getId().toString());
			for (int j = 0; j < planIndex.getEntrys().size(); j++) {
				PlanIndexEntryInfo planIndexEntry = planIndex.getEntrys().get(j);
				if(planIndexEntry==null||planIndexEntry.getProduct()==null){
					continue;
				}
				cols[k] = "buildAvg" + i + j;
				cols[entrysSize + 1 + k] = "avg" + i + j;
				cols[entrysSize * 2 + 2 + k] = "split" + i + j;
				k++;
			}
		}
		cols[entrysSize] = "buildAvg";
		cols[entrysSize * 2 + 1] = "avg";
		cols[entrysSize * 3 + 2] = "total";
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getUserObject() instanceof CostAccountInfo) {
				CostAccountInfo acct = (CostAccountInfo) row.getUserObject();
				if (acct.isIsLeaf())
					continue;
				// 设置汇总行
				int level = row.getTreeLevel();
				List aimRowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() instanceof CostAccountInfo) {
						acct = (CostAccountInfo) rowAfter.getUserObject();
						if (acct.isIsLeaf()) {
							aimRowList.add(rowAfter);
						}
					}

				}
				for (int j = 0; j < cols.length; j++) {
					BigDecimal sum = null;
					for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) aimRowList.get(rowIndex);
						Object value = rowAdd.getCell(cols[j]).getValue();
						if (value != null) {
							sum = FDCHelper.toBigDecimal(sum, 2).add(FDCHelper.toBigDecimal(value, 2));
						}
					}
					row.getCell(cols[j]).setValue(sum);
				}
			}
		}
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				try {
					AimCostClientHelper.setTotalCostRow(table,getColumns());
				} catch (Exception e) {
				}
			}
		});
		afterSetUnionData(table);
	}
	private String[] getColumns(){
		int size=1 + entrysSize;
		String[] columns=new String[size];
		columns[0]="total";
		for(int i=1;i<size;i++){
			columns[i]="split"+(i-1);
		}
		
		return columns;
	}
	
	//重新计算 平均 
	private void afterSetUnionData(KDTable table){
		BigDecimal allSellArea = getAllSellArea();
		BigDecimal allBuildArea = getAllBuildArea();

		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getUserObject() instanceof CostAccountInfo) {
				BigDecimal allTotal = (BigDecimal)row.getCell("total").getValue();//总成本_合计（含六类公摊及期间费）
				if(allTotal==null){
					continue;
				}
				if (FDCHelper.toBigDecimal(allSellArea).compareTo(FDCHelper.ZERO) != 0) {
					BigDecimal avg = allTotal.divide(allSellArea, 2, BigDecimal.ROUND_HALF_UP);
					row.getCell("avg").setValue(avg);
				}
				if (FDCHelper.toBigDecimal(allBuildArea).compareTo(FDCHelper.ZERO) != 0) {
					BigDecimal avg = allTotal.divide(allBuildArea, 2, BigDecimal.ROUND_HALF_UP);
					row.getCell("buildAvg").setValue(avg);
				}
			}
		}
	}

	public void fillTable(KDTable table) throws Exception {

		table.removeRows();
		table.setUserObject(null);
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
		table.getTreeColumn().setDepth(maxLevel + 1);
		for (int i = 0; i < root.getChildCount(); i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) root
					.getChildAt(i);
			fillNode(table, child);
		}
	}

	private void fillNode(KDTable table, DefaultMutableTreeNode node)
			throws BOSException, SQLException, EASBizException {
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		
		//应该先判断是否为空
		if (costAcct == null) {
			MsgBox.showError("成本科目的级别太多!");
			return;
		}
		
		if (costAcct.getType() != null) {
			if (table.getHeadRow(0).getUserObject() != null) {
				if (costAcct.getType().equals(CostAccountTypeEnum.SIX)) {
					return;
				}
			} else {
				if (costAcct.getType().equals(CostAccountTypeEnum.MAIN)) {
					return;
				}
			}
		}
		CurProjectInfo project = (CurProjectInfo)table.getHeadRow(0).getCell(1).getUserObject();
		ProductTypeInfo product = (ProductTypeInfo) table.getHeadRow(0)
				.getUserObject();
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		String longNumber = costAcct.getLongNumber();
		longNumber = longNumber.replace('!', '.');
		row.getCell("acctNumber").setValue(longNumber);
		row.getCell("acctName").setValue(costAcct.getName());
		row.setUserObject(costAcct);
		if (node.isLeaf()&&node.getLevel()>1) {
			String key = costAcct.getLongNumber();
			if (product != null) {
				key += project.getId().toString()+product.getId().toString();
			}
			MeasureEntryCollection coll = (MeasureEntryCollection) costEntrysMap
			.get(key);
			
			if (coll != null && coll.size() > 0) {
				if(coll.size()==1 && product==null){
					MeasureEntryInfo info = coll.get(0);
					IRow entryRow = row;
					entryRow.setUserObject(info);
					loadRow(table,entryRow, product);
					row.getCell("acctName").setValue(costAcct.getName());
				}else{
//					row.getStyleAttributes().setLocked(true);
//					row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
//					row.setUserObject(costAcct);
					IRow entryRow = row;
					for (int i = 0; i < coll.size(); i++) {
						MeasureEntryInfo info = coll.get(i);
						if(i!=0){
							entryRow = table.addRow();
							entryRow.setTreeLevel(node.getLevel());
						}
						entryRow.setUserObject(info);
						loadManRow(table,entryRow, product);
					}
				}
			}else{
				//空科目的情况
				MeasureEntryInfo info = new MeasureEntryInfo();
				info.setCostAccount(costAcct);
				row.setUserObject(info);
			}
		} else {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode(table, (DefaultMutableTreeNode) node
						.getChildAt(i));
			}
			row.getStyleAttributes().setLocked(true);
			row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
		}
	}

	public void loadRow(KDTable table,IRow row, ProductTypeInfo product) {
		MeasureEntryInfo info = (MeasureEntryInfo) row.getUserObject();
		row.getCell("acctName").setValue(info.getEntryName());
		row.getCell("index").setValue(info.getIndexValue());
		row.getCell("indexName").setValue(info.getIndexName());
		row.getCell("index").setValue(info.getIndexValue());
		row.getCell("workload").setValue(info.getWorkload());
//		row.getCell("unit").setValue(info.getUnit()==null?null:info.getUnit().getName());
		//老数据升级　unit保存在name里面
		if(info.getUnit()==null){
			row.getCell("unit").setValue(info.getName());
		}else{
			row.getCell("unit").setValue(info.getUnit().getName());
		}
		row.getCell("price").setValue(
				FDCHelper.divide(info.getAmount(), info.getWorkload(), 4,
						BigDecimal.ROUND_HALF_UP));
//		row.getCell("sumPrice").setValue(info.getCostAmount());
		// 六类公摊的单方
		BigDecimal sellArea = getAllSellArea();
		if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
			BigDecimal sellPart = FDCNumberHelper.divide(info.getAmount(),
					sellArea);
			row.getCell("sellPart").setValue(sellPart);
		} else {
			row.getCell("sellPart").setValue(null);
		}
		BigDecimal buildArea = getAllBuildArea();
		if (buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0) {
			BigDecimal buildPart = FDCNumberHelper.divide(info.getAmount(),
					buildArea);
			row.getCell("buildPart").setValue(buildPart);
		} else {
			row.getCell("buildPart").setValue(null);
		}
		row.getCell("amount").setValue(info.getAmount());
//		row.getCell("adjustAmt").setValue(info.getAdjustAmt());
	}
	private BigDecimal getAllSellArea() {
		return (BigDecimal)initData.get("allSellArea");
	}
	private BigDecimal getAllBuildArea() {
		return (BigDecimal)initData.get("allBuildArea");
	}
	public void loadManRow(KDTable table,IRow row, ProductTypeInfo product) {
		MeasureEntryInfo info = (MeasureEntryInfo) row.getUserObject();
//		row.getCell("acctName").setValue(info.getEntryName());
		row.getCell("index").setValue(info.getIndexValue());
		row.getCell("indexName").setValue(info.getIndexName());
		row.getCell("coefficientName").setValue(info.getCoefficientName());
		BigDecimal coefficient = info.getCoefficient();
		if (coefficient != null && coefficient.compareTo(FDCHelper.ZERO) == 0) {
			coefficient = null;
		}
		row.getCell("coefficient").setValue(FDCHelper.toBigDecimal(coefficient, 2));
		BigDecimal workload = info.getWorkload();
		if (workload != null && workload.compareTo(FDCHelper.ZERO) == 0) {
			workload = null;
		}
		if (info.getSimpleName() != null && coefficient != null) {
			row.getCell("workload").getStyleAttributes().setLocked(true);
		}
		row.getCell("workload").setValue(workload);
		//老数据升级　unit保存在name里面
		if(info.getUnit()==null){
			row.getCell("unit").setValue(info.getName());
		}else{
			row.getCell("unit").setValue(info.getUnit().getName());
		}
		BigDecimal price = info.getPrice();
		if (price != null && price.compareTo(FDCHelper.ZERO) == 0) {
			price = null;
		}
		row.getCell("price").setValue(FDCHelper.toBigDecimal(price,4));
		row.getCell("sumPrice").setValue(info.getCostAmount());
		if (workload != null && price != null) {
			row.getCell("sumPrice").getStyleAttributes().setLocked(true);
		}

		Object obj=table.getHeadRow(0).getCell(0).getUserObject();
		if (obj instanceof PlanIndexEntryInfo) {
			BigDecimal sellArea = ((PlanIndexEntryInfo) obj).getSellArea();
			if (sellArea != null && sellArea.compareTo(FDCHelper.ZERO) != 0) {
				BigDecimal sellPart = FDCNumberHelper.divide(info.getAmount(),
						sellArea);
				row.getCell("sellPart").setValue(sellPart);
			} else {
				row.getCell("sellPart").setValue(null);
			}
			BigDecimal buildArea = ((PlanIndexEntryInfo) obj).getBuildArea();
			if (buildArea != null && buildArea.compareTo(FDCHelper.ZERO) != 0) {
				BigDecimal buildPart = FDCNumberHelper.divide(info.getAmount(),
						buildArea);
				row.getCell("buildPart").setValue(buildPart);
			} else {
				row.getCell("buildPart").setValue(null);
			}
		}
		row.getCell("amount").setValue(info.getAmount());
		row.getCell("adjustCoefficient").setValue(info.getAdjustCoefficient());
		row.getCell("adjustAmt").setValue(info.getAdjustAmt());
		row.getCell("description").setValue(info.getDescription());
	}
	
	public boolean destroyWindow() {
		boolean destroyWindow = super.destroyWindow();
		if(destroyWindow){
			//释放资源
			if(true)return true;
			this.removeAll();
			costAcctTree=null;
			tables=null;
			tHelper=null;
			this.plTables=null;
			measureCosts=null;
			measureCostsMap=null;
			costEntrysMap=null;
			planIndexsMap=null;
			initData=null;
			FDCClientHelper.clearMenuKeyboardHelper();
		}
		return destroyWindow;
	}
	
	private boolean isUseAdjustCoefficient() {
		return Boolean.valueOf(
				initData.get("isUseAdjustCoefficient").toString())
				.booleanValue();
	}

	private boolean isUseQuality() {
		return Boolean.valueOf(
				initData.get("isUseQuality").toString())
				.booleanValue();
	}
	
	protected boolean isUseCustomIndex() {
		return Boolean.valueOf(
				initData.get("isUseCustomIndex").toString())
				.booleanValue();
	}
	
	protected boolean isPlanIndexLogic() {
		return Boolean.valueOf(
				initData.get("isPlanIndexLogic").toString())
				.booleanValue();
	}
	
	protected boolean isBuildPartLogic() {
		return Boolean.valueOf(
				initData.get("isBuildPartLogic").toString())
				.booleanValue();
	}
	
	private KDBizPromptBox f7productType=null; 
	public KDBizPromptBox  getF7productType(KDTable table){
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
		return f7productType;
	}
	private void initCostAccount(String id){
		
	}
}