/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.ProjectCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCRenderHelper;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class ProjectCostRptUI extends AbstractProjectCostRptUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectCostRptUI.class);
    
    /**
     * output class constructor
     */
    public ProjectCostRptUI() throws Exception
    {
        super();
    }
    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
        super.treeMain_valueChanged(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionView_actionPerformed(e);
    }

	protected String getEditUIName() {
		return null;
	}
	protected void fillTable() throws Exception {
		super.fillTable();
		KDTable table = getMainTable();
		table.removeRows(false);
		fetchData();
		if(retValue==null){
			table.getHeadMergeManager().mergeBlock(0, 0, 2, table.getColumnCount()-1, KDTMergeManager.FREE_MERGE);
			return;
		}
		resetHead();
		CostAccountCollection accts=getAccts();
		if(accts==null){
			return;
		}
		table.getTreeColumn().setDepth(getMaxLevel());
		for(int i=0;i<accts.size();i++){
			CostAccountInfo info=accts.get(i);
			IRow row=table.addRow();
			row.getCell("id").setValue(info.getId().toString());
			row.getCell("acctNumber").setValue(info.getLongNumber());
			row.getCell("acctName").setValue(info.getName());
			row.setTreeLevel(info.getLevel()-1);
			if(info.isIsLeaf()){
				row.setUserObject(info);
				loadRow(row);
			}
		}
		setUnionData();
	}
	
	
	
	public void onLoad() throws Exception {
		super.onLoad();
		//设置保存当前样式
		tHelper = new TablePreferencesHelper(this);
	}
	protected void initTable() {
		KDTable table=getMainTable();
		table.checkParsed();
		super.initTable();
		table.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		table.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		table.getColumn("id").getStyleAttributes().setHided(true);
		tblMain.getColumn("acctNumber").setRenderer(FDCRenderHelper.getLongNumberRender());
	}
	
	private void resetHead(){
		KDTable table=getMainTable();
		for(int i=table.getColumnCount()-1;i>0;i--){
			if(table.getColumnKey(i)!=null&&table.getColumnKey(i).equals("dif")){
				break;
			}
			table.removeColumn(i);
		}
		getHeadKeySet().clear();
		getHeadTotalKeyMap().clear();
		IRow headRow0=table.getHeadRow(0);
		IRow headRow1=table.getHeadRow(1);
		IRow headRow2=table.getHeadRow(2);
		for(Iterator iter=getProducts().iterator();iter.hasNext();){
			ProductTypeInfo product=(ProductTypeInfo)iter.next();
			CurProjectCollection prjs = (CurProjectCollection)getPrjs().clone();
			CurProjectInfo totalPrj=new CurProjectInfo();
			totalPrj.setName("合计");
			prjs.add(totalPrj);
			String totalKey="total_"+product.getId().toString();
			Set subSet=new HashSet();
			getHeadTotalKeyMap().put(totalKey, subSet);
			
			for(Iterator iter2=prjs.iterator();iter2.hasNext();){
				CurProjectInfo prj=(CurProjectInfo)iter2.next();
				String key=null;
				if(prj==totalPrj){
					//总计行
					key=totalKey;
				}else{
					key=prj.getId().toString()+product.getId().toString();
					getHeadKeySet().add(key);
					subSet.add(key);
				}
				IColumn col = table.addColumn();
				col.setKey(key+"aimCost");
				col = table.addColumn();
				col.setKey(key+"happenCost");
				col = table.addColumn();
				col.setKey(key+"waitCost");
				col = table.addColumn();
				col.setKey(key+"dynCost");
				col = table.addColumn();
				col.setKey(key+"dif");
				
				headRow0.getCell(key+"aimCost").setValue(product.getName());
				headRow0.getCell(key+"happenCost").setValue(product.getName());
				headRow0.getCell(key+"waitCost").setValue(product.getName());
				headRow0.getCell(key+"dynCost").setValue(product.getName());
				headRow0.getCell(key+"dif").setValue(product.getName());
				
				headRow1.getCell(key+"aimCost").setValue(prj.getName());
				headRow1.getCell(key+"happenCost").setValue(prj.getName());
				headRow1.getCell(key+"waitCost").setValue(prj.getName());
				headRow1.getCell(key+"dynCost").setValue(prj.getName());
				headRow1.getCell(key+"dif").setValue(prj.getName());
				
				headRow2.getCell(key+"aimCost").setValue("目标成本");
				headRow2.getCell(key+"happenCost").setValue("已发生成本");
				headRow2.getCell(key+"waitCost").setValue("待发生成本");
				headRow2.getCell(key+"dynCost").setValue("动态成本");
				headRow2.getCell(key+"dif").setValue("差异");

			}
			
		}
		table.getHeadMergeManager().mergeBlock(0, 0, 2, table.getColumnCount()-1, KDTMergeManager.FREE_MERGE);
		FDCHelper.formatTableNumber(table, table.getColumnIndex("acctName")+1, table.getColumnCount()-1);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int number_col_index = tblMain.getColumn("acctName").getColumnIndex();
				tblMain.getViewManager().setFreezeView(-1, number_col_index+1);
		}});
	}
	protected void setUnionData(){
		super.setUnionData();
		KDTable table=getMainTable();
		List columns=new ArrayList();
		for(int i=tblMain.getColumnIndex("acctName")+1;i<table.getColumnCount();i++){
			columns.add(table.getColumnKey(i));
		}
		String cols[]=new String[columns.size()];
		columns.toArray(cols);
		
		for(int i=0;i<table.getRowCount();i++){
			IRow row = table.getRow(i);
			int level=row.getTreeLevel();
			if(level==0){
				_setUnionSubRow(row, columns);
			}
		}
		try {
			AimCostClientHelper.setTotalCostRow(table, cols);
		} catch (EASBizException e) {
			handUIException(e);
		} catch (BOSException e) {
			handUIException(e);
		}
	}
	
	/**
	 * 对一个科目树进行递归汇总
	 * @param row
	 */
	private void _setUnionSubRow(IRow row,List sumCols) {
		KDTable table=getMainTable();
		Object obj=row.getUserObject();
		int level=row.getTreeLevel();
		if(obj instanceof CostAccountInfo){
			//明细行
			return;
		}
		List subRows=new ArrayList();
		for(int i=row.getRowIndex()+1;i<table.getRowCount();i++){
			IRow tmpRow=table.getRow(i);
			if(tmpRow.getTreeLevel()==level+1){
				_setUnionSubRow(tmpRow, sumCols);
				subRows.add(tmpRow);
			}
			if(tmpRow.getTreeLevel()<=level){
				break;
			}
		}
		sumRow(row, subRows, sumCols);
	
	}
	
	/**
	 * 将subRows按cols列汇总到row
	 * @param row
	 * @param subRows
	 * @param cols
	 */
	private void sumRow(IRow row,List subRows,List cols){
		if(cols.size()==0){
			return;
		}
		for(Iterator iter=cols.iterator();iter.hasNext();){
			//先清空再汇总
			row.getCell((String)iter.next()).setValue(null);
		}

		for(Iterator iter=subRows.iterator();iter.hasNext();){
			IRow tmpRow=(IRow)iter.next();
			for(Iterator iter2=cols.iterator();iter2.hasNext();){
				String key=(String)iter2.next();
				setCellValue(row.getCell(key), FDCNumberHelper.add(row.getCell(key).getValue(), tmpRow.getCell(key).getValue()));
			}
		}
	
	}
	private void setCellValue(com.kingdee.bos.ctrl.kdf.table.ICell cell,BigDecimal value){
		if(FDCHelper.toBigDecimal(value).signum()==0){
			value=null;
		}
		cell.setValue(value);
	}
	private RetValue retValue=null;
	protected void fetchData() throws Exception {
		super.fetchData();
		CurProjectInfo prj = getSelectProject();
		if(prj==null){
			retValue=null;
			return;
		}
		String prjId=prj.getId().toString();
		ParamValue param=new ParamValue();
		param.setString("prjId", prjId);
		retValue=ProjectCostRptFacadeFactory.getRemoteInstance().getData(param);
	}
	
	private CurProjectCollection getPrjs(){
		CurProjectCollection prjs=null;
		if(retValue!=null){
			prjs=(CurProjectCollection)retValue.get("CurProjectCollection");
		}
		return prjs; 
	}
	
	private ProductTypeCollection getProducts(){
		ProductTypeCollection products=null;
		if(retValue!=null){
			products=(ProductTypeCollection)retValue.get("ProductTypeCollection");
		}
		return products;
	}
	private CostAccountCollection getAccts(){
		CostAccountCollection accts=null;
		if(retValue!=null){
			accts=(CostAccountCollection)retValue.get("CostAccountCollection");
		}
		return accts;
	}
	
	private int getMaxLevel(){
		int maxLevel=0;
		if(retValue!=null){
			maxLevel=retValue.getInt("maxLevel");
		}
		return maxLevel;
	}
	
	private RetValue getRowRetValue(String acctNumber){
		RetValue rowRetValue=null;
		if(retValue!=null){
			rowRetValue=(RetValue)retValue.get(acctNumber);
		}
		return rowRetValue;
	}
	private Set headKeySet=new HashSet();
	private Set getHeadKeySet(){
		return headKeySet;
	}
	
	//用于存储合计列对应的要汇总的项目－产品列的信息
	private Map totalMap=new HashMap();
	private Map getHeadTotalKeyMap(){
		return totalMap;
	}
	private void loadRow(IRow row){
		CostAccountInfo info=(CostAccountInfo)row.getUserObject();
		String acctNumber=info.getLongNumber();
		RetValue rowRetValue=getRowRetValue(acctNumber);
		if(rowRetValue==null){
			return;
		}
		//项目数据
		setCellValue(row.getCell("aimCost"), rowRetValue.getBigDecimal("aimCost"));
		setCellValue(row.getCell("dynCost"), rowRetValue.getBigDecimal("dynCost"));
		setCellValue(row.getCell("happenCost"), rowRetValue.getBigDecimal("happenCost"));
		setCellValue(row.getCell("waitCost"), FDCNumberHelper.subtract(rowRetValue.getBigDecimal("dynCost"), rowRetValue.getBigDecimal("happenCost")));
		setCellValue(row.getCell("dif"), FDCNumberHelper.subtract(rowRetValue.getBigDecimal("dynCost"), rowRetValue.getBigDecimal("aimCost")));
		
		//产品数据
		for(Iterator iter=getHeadKeySet().iterator();iter.hasNext();){
			String key=(String)iter.next();
			setCellValue(row.getCell(key+"aimCost"), rowRetValue.getBigDecimal(key+"aimCost"));
			setCellValue(row.getCell(key+"dynCost"), rowRetValue.getBigDecimal(key+"dynCost"));
			setCellValue(row.getCell(key+"happenCost"), rowRetValue.getBigDecimal(key+"happenCost"));
			setCellValue(row.getCell(key+"waitCost"), FDCNumberHelper.subtract(rowRetValue.getBigDecimal(key+"dynCost"), rowRetValue.getBigDecimal(key+"happenCost")));
			setCellValue(row.getCell(key+"dif"), FDCNumberHelper.subtract(rowRetValue.getBigDecimal(key+"dynCost"), rowRetValue.getBigDecimal(key+"aimCost")));
		}
		//合计行数据
		for(Iterator iter=getHeadTotalKeyMap().keySet().iterator();iter.hasNext();){
			String key=(String)iter.next();
			Set set=(Set)getHeadTotalKeyMap().get(key);
			if(set==null||set.size()==0){
				continue;
			}
			for(Iterator iter2=set.iterator();iter2.hasNext();){
				String subKey=(String)iter2.next();
				setCellValue(row.getCell(key+"aimCost"),FDCNumberHelper.add(row.getCell(key+"aimCost").getValue(), row.getCell(subKey+"aimCost").getValue()));
				setCellValue(row.getCell(key+"dynCost"),FDCNumberHelper.add(row.getCell(key+"dynCost").getValue(), row.getCell(subKey+"dynCost").getValue()));
				setCellValue(row.getCell(key+"happenCost"),FDCNumberHelper.add(row.getCell(key+"happenCost").getValue(), row.getCell(subKey+"happenCost").getValue()));
			}
			setCellValue(row.getCell(key+"waitCost"),FDCNumberHelper.subtract(row.getCell(key+"dynCost").getValue(), row.getCell(key+"happenCost").getValue()));
			setCellValue(row.getCell(key+"dif"),FDCNumberHelper.subtract(row.getCell(key+"dynCost").getValue(), row.getCell(key+"aimCost").getValue()));
		}
	}
	public boolean destroyWindow() {
		boolean destroyWindow = super.destroyWindow();
		if(destroyWindow){
			clear();
		}
		return destroyWindow;
	}
	protected void initWorkButton() {
		super.initWorkButton();
		actionUpdateData.putValue(javax.swing.Action.SMALL_ICON, EASResource.getIcon("imgTbtn_synchronization"));
		actionUpdateData.setEnabled(true);
		actionUpdateData.setVisible(true);
		FDCClientHelper.setActionEnable(new ItemAction[]{actionAddNew,actionEdit,actionLocate,actionView,actionRemove,actionQuery}, false);
		this.menuEdit.setEnabled(false);
		this.menuEdit.setVisible(false);
	}
	private void clear(){
		getHeadKeySet().clear();
		if(retValue!=null){
			retValue.clear();
		}
	}
	public void actionUpdateData_actionPerformed(ActionEvent e)
			throws Exception {
		CurProjectInfo prj = getSelectProject();
		if(prj==null){
			retValue=null;
			return;
		}
		String prjId=prj.getId().toString();
		ProjectCostRptFacadeFactory.getRemoteInstance().updateData(prjId);
	}
	
	public int getRowCountFromDB() {
//		return super.getRowCountFromDB();
		return -1;
	}
}