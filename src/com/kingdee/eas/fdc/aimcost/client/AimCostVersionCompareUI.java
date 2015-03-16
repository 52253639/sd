/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class AimCostVersionCompareUI extends AbstractAimCostVersionCompareUI
{
    private static final Logger logger = CoreUIObject.getLogger(AimCostVersionCompareUI.class);
    
    /**
     * output class constructor
     */
    public AimCostVersionCompareUI() throws Exception
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
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
//        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
//        super.tblMain_tableSelectChanged(e);
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
//        super.treeMain_valueChanged(e);
        clear();
    }
	protected String getEditUIName() {
		return null;
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		SelectorItemCollection selectors=new SelectorItemCollection();
		selectors.add("*");
		prmtBaseVer.setCommitFormat("$versionNumber$");
		prmtBaseVer.setEditFormat("$versionNumber$");
		prmtBaseVer.setDisplayFormat("$versionNumber$_$versionName$");
		prmtBaseVer.setRequired(true);
		prmtBaseVer.setSelectorCollection(selectors);
		prmtCompareVer.setEnabledMultiSelection(false);
		prmtCompareVer.setCommitFormat("$versionNumber$");
		prmtCompareVer.setEditFormat("$versionNumber$");
		prmtCompareVer.setDisplayFormat("$versionNumber$_$versionName$");
		prmtCompareVer.setRequired(true);
		prmtCompareVer.setEnabledMultiSelection(true);
		prmtCompareVer.setSelectorCollection(selectors);
		clear();
	}
	private void clear(){
		prmtBaseVer.setValue(null);
		prmtCompareVer.setValue(null);
		chkAimCost.setSelected(true);
		chkBuildArea.setSelected(false);
		chkSellArea.setSelected(false);
		resetTableHead();
        
		String prjId=getSelectObjId();
		EntityViewInfo view=new  EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("orgOrProId", prjId);
		//add by david_yang PT048268 2011.04.06
		view.getFilter().getFilterItems().add(new FilterItemInfo("versionNumber", "0.0",CompareType.NOTEQUALS));
		this.prmtBaseVer.getQueryAgent().resetRuntimeEntityView();
		this.prmtBaseVer.setEntityViewInfo(view);
		this.prmtCompareVer.getQueryAgent().resetRuntimeEntityView();
		this.prmtCompareVer.setEntityViewInfo(view);


	}
	protected void fillTable(){
		resetTableHead();
		CostAccountCollection accts=(CostAccountCollection)dataMap.get("accts");
		if(accts==null||accts.size()==1){
			return;
		}
		int maxLevel=0;
		for(int i=0;i<accts.size();i++){
			if(accts.get(i).getLevel()>maxLevel){
				maxLevel=accts.get(i).getLevel();
			}
		}
//		Map costEntrys=(Map)dataMap.get("costEntrys");
//		if(costEntrys==null){
//			costEntrys=new HashMap();
//		}
		tblMain.getTreeColumn().setDepth(maxLevel);
		for(Iterator iter=accts.iterator();iter.hasNext();){
			CostAccountInfo info=(CostAccountInfo)iter.next();
			fillNode(info);
		}
		setUnionData();
		
		//由于表格初始化序号列时会出现列宽不够
		tblMain.invalidate();
	}
	
	private void fillNode(CostAccountInfo info){
		IRow row=tblMain.addRow();
		row.getCell("acctNumber").setValue(info.getLongNumber().replace('!', '.'));
		row.getCell("acctName").setValue(info.getName());
		row.setTreeLevel(info.getLevel()-1);
		if(info.isIsLeaf()){
			row.setUserObject(info);
			for(int i=3;i<tblMain.getColumnCount();i++){
				String colKey = tblMain.getColumnKey(i);
				String aimId=getId(colKey);
				if(aimId.equals(diffID)||aimId.equals(diffRate)){
					continue;
				}
				String key=aimId+info.getId().toString();
				BigDecimal amt=(BigDecimal)dataMap.get(key);
				if(colKey.equals(aimId+"_build")){
					BigDecimal buildArea=(BigDecimal)dataMap.get("buildArea");
					amt=FDCNumberHelper.divide(amt, buildArea);
				}
				if(colKey.equals(aimId+"_sell")){
					BigDecimal sellArea=(BigDecimal)dataMap.get("sellArea");
					amt=FDCNumberHelper.divide(amt, sellArea);
				}
				if(amt!=null&&amt.signum()==0){
					amt=null;
				}
				row.getCell(colKey).setValue(amt);
			}
		}
	}
	protected void setUnionData(){
		//汇总
		List cols=new  ArrayList();
		for(int i=3;i<tblMain.getColumnCount();i++){
			String key = tblMain.getColumnKey(i);
			if(key!=null&&(key.startsWith(diffID)||key.startsWith(diffRate))){
				continue;
			}
			cols.add(key);
		}
		KDTable table=tblMain;
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getUserObject()==null) {
				// 设置汇总行
				int level = row.getTreeLevel();
				List aimRowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() instanceof CostAccountInfo) {
						aimRowList.add(rowAfter);
					}

				}
//				for (int j = 0; j < cols.size(); j++) {
//					BigDecimal sum = null;
//					for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
//						IRow rowAdd = (IRow) aimRowList.get(rowIndex);
//						Object value = rowAdd.getCell((String)cols.get(j)).getValue();
//						if (value != null) {
//							if (sum == null) {
//								sum = FMConstants.ZERO;
//							}
//							if (value instanceof BigDecimal) {
//								sum = sum.add((BigDecimal) value);
//							} else if (value instanceof Integer) {
//								sum = sum.add(new BigDecimal(((Integer) value)
//										.toString()));
//							}
//						}
//					}
//					row.getCell((String)cols.get(j)).setValue(sum);
//				}
				
				/**
				 * 所有报表的所有单方不存在上下级汇总关系，而应该是各个级次的都等于各自的对应成本除以对应的面积，而非下级的单方汇总
				 * @author pengwei_hou Date: 2009-01-18 14:40:23
				 */
				for (int j = 0; j < cols.size(); j++) {
					BigDecimal sum = null;
					String key = cols.get(j).toString();
					if(key.endsWith("_aim")){
						for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
							IRow rowAdd = (IRow) aimRowList.get(rowIndex);
							Object value = rowAdd.getCell((String)cols.get(j)).getValue();
							if (value != null) {
								if (sum == null) {
									sum = FMConstants.ZERO;
								}
								if (value instanceof BigDecimal) {
									sum = sum.add((BigDecimal) value);
								} else if (value instanceof Integer) {
									sum = sum.add(new BigDecimal(((Integer) value)
											.toString()));
								}
							}
						}
					}
					if(key.endsWith("_build")){
						BigDecimal rowBuildAmt = FDCHelper.toBigDecimal(row.getCell(getId(key)+"_aim").getValue());
						sum = FDCNumberHelper.divide(rowBuildAmt, this.buildArea, 2, BigDecimal.ROUND_HALF_UP);
					}
					if(key.endsWith("_sell")){
						BigDecimal rowSellAmt = FDCHelper.toBigDecimal(row.getCell(getId(key)+"_aim").getValue());
						sum = FDCNumberHelper.divide(rowSellAmt, this.sellArea, 2, BigDecimal.ROUND_HALF_UP);
					}
					if(sum != null && sum.compareTo(FDCHelper.ZERO)==0){
						sum = null;
					}
					row.getCell(key).setValue(sum);
				}
				
			}
		}
		//计算差值
		AimCostInfo baseInfo=(AimCostInfo)prmtBaseVer.getData();
		Object[] otherData = FDCHelper.getF7Data(prmtCompareVer);
		if(otherData.length==1){
			AimCostInfo verInfo=(AimCostInfo)otherData[0];
			String baseId=baseInfo.getId().toString();
			String verId=verInfo.getId().toString();
			for(int i=0;i<tblMain.getRowCount();i++){
				IRow row=tblMain.getRow(i);
				if(isAimCostSelect()){
					row.getCell(diffID+"_aim").setValue(FDCNumberHelper.subtract(row.getCell(verId+"_aim").getValue(), row.getCell(baseId+"_aim").getValue()));
					row.getCell(diffRate+"_aim").setValue(FDCNumberHelper.divide(row.getCell(diffID+"_aim").getValue(),row.getCell(baseId+"_aim").getValue(),
							                              4, BigDecimal.ROUND_HALF_UP));
				}
				if(isBuildSelect()){
					row.getCell(diffID+"_build").setValue(FDCNumberHelper.subtract(row.getCell(verId+"_build").getValue(), row.getCell(baseId+"_build").getValue()));
					row.getCell(diffRate+"_build").setValue(FDCNumberHelper.divide(row.getCell(diffID+"_build").getValue(),row.getCell(baseId+"_build").getValue(),
							                              4, BigDecimal.ROUND_HALF_UP));
				}
				if(isSellSelect()){
					row.getCell(diffID+"_sell").setValue(FDCNumberHelper.subtract(row.getCell(verId+"_sell").getValue(), row.getCell(baseId+"_sell").getValue()));
					row.getCell(diffRate+"_sell").setValue(FDCNumberHelper.divide(row.getCell(diffID+"_sell").getValue(),row.getCell(baseId+"_sell").getValue(),
							                              4, BigDecimal.ROUND_HALF_UP));
				}
			}
			
		}
		try {
			String keys[]=new String[cols.size()];
			cols.toArray(keys);
			AimCostClientHelper.setTotalCostRow(table, keys);
			
			KDTFootManager footRowManager= table.getFootManager();
			
			if(footRowManager!=null && otherData.length==1){
				IRow footRow=table.getFootRow(0);
				
				AimCostInfo verInfo=(AimCostInfo)otherData[0];
				String baseId=baseInfo.getId().toString();
				String verId=verInfo.getId().toString();
				if(footRow!=null){
					if(isAimCostSelect())
					{
						footRow.getCell(diffID+"_aim").setValue(FDCNumberHelper.subtract(footRow.getCell(verId+"_aim").getValue(), 
								footRow.getCell(baseId+"_aim").getValue()));
						footRow.getCell(diffRate+"_aim").setValue(FDCNumberHelper.divide(footRow.getCell(diffID+"_aim").getValue(),
								footRow.getCell(baseId+"_aim").getValue(),4, BigDecimal.ROUND_HALF_UP));
					}
					if(isBuildSelect()){
						footRow.getCell(diffID+"_build").setValue(FDCNumberHelper.subtract(footRow.getCell(verId+"_build").getValue(), 
								footRow.getCell(baseId+"_build").getValue()));
						footRow.getCell(diffRate+"_build").setValue(FDCNumberHelper.divide(footRow.getCell(diffID+"_build").getValue(),
								footRow.getCell(baseId+"_build").getValue(),4, BigDecimal.ROUND_HALF_UP));
					}
					if(isSellSelect()){
						footRow.getCell(diffID+"_sell").setValue(FDCNumberHelper.subtract(footRow.getCell(verId+"_sell").getValue(), 
								footRow.getCell(baseId+"_sell").getValue()));
						footRow.getCell(diffRate+"_sell").setValue(FDCNumberHelper.divide(footRow.getCell(diffID+"_sell").getValue(),
								footRow.getCell(baseId+"_sell").getValue(),4, BigDecimal.ROUND_HALF_UP));
					}
				}
			}
		} catch (Exception e) {
			handUIException(e);
		}
	}
	protected void btnOK_actionPerformed(ActionEvent e) throws Exception {
		super.btnOK_actionPerformed(e);
		verify();
		fetchData();
		fillTable();
	}
	
	private void verify(){
		FDCClientVerifyHelper.verifyEmpty(this, prmtBaseVer);
		FDCClientVerifyHelper.verifyEmpty(this, prmtCompareVer);
		String id = FDCHelper.getF7Id(prmtBaseVer);
		Set set=FDCHelper.getF7IdSet(prmtCompareVer);
		if(set.contains(id)){
			FDCMsgBox.showWarning(this,"比较版本里面含有基准版本");
			SysUtil.abort();
		}
		if(!isAimCostSelect()&&!isBuildSelect()&&!isSellSelect()){
			FDCMsgBox.showWarning(this, "目标成本，建筑单方，可售单方必须选择一个");
			SysUtil.abort();
		}
	}
	private Map dataMap=null;
	private BigDecimal buildArea = FDCHelper.ZERO;
	private BigDecimal sellArea = FDCHelper.ZERO;
	protected void fetchData() throws Exception{
		Map param=new HashMap();
		String prjId=getSelectObjId();
		if(prjId==null){
			FDCMsgBox.showWarning(this, "当前选择的树结点不是明细工程项目");
			SysUtil.abort();
		}
		param.put("prjId", prjId);
		Set set=FDCHelper.getF7IdSet(prmtCompareVer);
		set.add(FDCHelper.getF7Id(prmtBaseVer));
		param.put("idSet", set);
		dataMap=AimCostFactory.getRemoteInstance().getAimCostVers(param);
		if(dataMap==null){
			dataMap=new  HashMap();
		}else{
			buildArea = FDCHelper.toBigDecimal(dataMap.get("buildArea"));
			sellArea = FDCHelper.toBigDecimal(dataMap.get("sellArea"));
		}
	}
	protected String getSelectObjId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null
				|| OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			if(projectInfo.isIsLeaf()){
				return projectInfo.getId().toString();
			}
		} 
		return null;
	}
	protected void initTable(){
		IRow headRow=(IRow)tblMain.getHeadRow(0).clone();
		tblMain.addHeadRow(1, headRow);
		tblMain.getHeadMergeManager().mergeBlock(0, 0, 1, tblMain.getColumnCount()-1, KDTMergeManager.FREE_MERGE);
		tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}
	private void resetTableHead(){
		tblMain.removeRows(false);
		for(int i=tblMain.getColumnCount()-1;i>2;i--){
			tblMain.removeColumn(i);
		}
		if(prmtBaseVer.getData()==null){
			return;
		}
		AimCostInfo baseInfo=(AimCostInfo)prmtBaseVer.getData();
		Object[] otherData = FDCHelper.getF7Data(prmtCompareVer);
		AimCostCollection aimCol=new AimCostCollection();
		for(int i=0;i<otherData.length;i++){
			aimCol.add((AimCostInfo)otherData[i]);
		}
		FDCHelper.sortObjectCollection(aimCol, "$versionNumber$");
		addColumn(baseInfo);
		
		for(Iterator iter=aimCol.iterator();iter.hasNext();){
			AimCostInfo aimCostInfo=(AimCostInfo)iter.next();
			addColumn(aimCostInfo);
		}
		if (aimCol.size() == 1) {
			AimCostInfo diffInfo = new AimCostInfo();
			diffInfo.setId(BOSUuid.read(diffID));
			diffInfo.setVersionNumber("差异");
			addColumn(diffInfo);

			AimCostInfo diffRateInfo = new AimCostInfo();
			diffRateInfo.setId(BOSUuid.read(diffRate));
			diffRateInfo.setVersionNumber("差异率");
			addColumn(diffRateInfo);
		}
		tblMain.getHeadMergeManager().mergeBlock(0, 0, 1, tblMain.getColumnCount()-1, KDTMergeManager.FREE_MERGE);
		int number_col_index = getMainTable().getColumn("acctName").getColumnIndex();
		getMainTable().getViewManager().setFreezeView(-1, number_col_index+1);
	}
	private void addColumn(AimCostInfo info){
		if(true){
			IColumn col=tblMain.addColumn();
			col.setKey(info.getId().toString()+"_aim");
			tblMain.getHeadRow(0).getCell(col.getKey()).setValue(info.getVersionNumber());
			tblMain.getHeadRow(1).getCell(col.getKey()).setValue("目标成本");
			setId(col.getKey(), info);
			if(info.getId().toString().equals(diffRate)){
				FDCHelper.formatTableNumber(tblMain, col.getKey(), "0.00%");
			}else{
				FDCHelper.formatTableNumber(tblMain, col.getKey());
			}
			if(!isAimCostSelect()){
				col.getStyleAttributes().setHided(true);
			}
		}
		
		if(isBuildSelect()){
			IColumn col=tblMain.addColumn();
			col.setKey(info.getId().toString()+"_build");
			tblMain.getHeadRow(0).getCell(col.getKey()).setValue(info.getVersionNumber());
			tblMain.getHeadRow(1).getCell(col.getKey()).setValue("建筑单方");
			setId(col.getKey(), info);
			if(info.getId().toString().equals(diffRate)){
				FDCHelper.formatTableNumber(tblMain, col.getKey(), "0.00%");
			}else{
				FDCHelper.formatTableNumber(tblMain, col.getKey());
			}
		}
		
		if(isSellSelect()){
			IColumn col=tblMain.addColumn();
			col.setKey(info.getId().toString()+"_sell");
			tblMain.getHeadRow(0).getCell(col.getKey()).setValue(info.getVersionNumber());
			tblMain.getHeadRow(1).getCell(col.getKey()).setValue("可售单方");
			setId(col.getKey(), info);
			if(info.getId().toString().equals(diffRate)){
				FDCHelper.formatTableNumber(tblMain, col.getKey(), "0.00%");
			}else{
				FDCHelper.formatTableNumber(tblMain, col.getKey());
			}
		}
	}
	private void setId(String colKey,AimCostInfo info){
		tblMain.getHeadRow(1).getCell(colKey).setUserObject(info.getId().toString());
	}
	private String getId(String colKey){
		if(tblMain.getHeadRow(1).getCell(colKey)!=null){
			return (String)tblMain.getHeadRow(1).getCell(colKey).getUserObject();
		}
		return null;
	}
	private boolean isAimCostSelect(){
		return chkAimCost.isSelected();
	}
	private boolean isBuildSelect(){
		return chkBuildArea.isSelected();
	}
	private boolean isSellSelect(){
		return chkSellArea.isSelected();
	}
	protected void initWorkButton() {
		super.initWorkButton();
		actionRemove.setEnabled(false);
		actionRemove.setVisible(false);
		actionLocate.setEnabled(false);
		actionLocate.setVisible(false);
		actionAddNew.setEnabled(false);
		actionAddNew.setVisible(false);
		actionEdit.setEnabled(false);
		actionEdit.setVisible(false);
		actionView.setEnabled(false);
		actionView.setVisible(false);
		actionQuery.setEnabled(false);
		actionQuery.setVisible(false);
		actionRefresh.setVisible(false);
		actionRefresh.setEnabled(false);
		menuView.setVisible(false);
		menuEdit.setVisible(false);
	}
	
	protected static final String diffID = BOSUuid.create("diff").toString();
	protected static final String diffRate = BOSUuid.create("diffRate").toString();
	protected void initCtrlListener(){
		SelectorListener selectorListener=new SelectorListener(){
			public void willShow(SelectorEvent e) {
				KDBizPromptBox f7=(KDBizPromptBox)e.getSource();
				String prjId=getSelectObjId();
				if(prjId!=null){
					EntityViewInfo view=new  EntityViewInfo();
					view.setFilter(new FilterInfo());
					view.getFilter().appendFilterItem("orgOrProId", prjId);
					view.getFilter().getFilterItems().add(new FilterItemInfo("versionNumber", "0.0", CompareType.NOTEQUALS));
					f7.getQueryAgent().resetRuntimeEntityView();
					f7.setEntityViewInfo(view);
				}else{
					FDCMsgBox.showWarning(FDCClientHelper.getCurrentActiveWindow(),"请选择明细工程项目节点");
					e.setCanceled(true);
				}
			}
		};
		prmtBaseVer.addSelectorListener(selectorListener);
		prmtCompareVer.addSelectorListener(selectorListener);
	}
	
    
}