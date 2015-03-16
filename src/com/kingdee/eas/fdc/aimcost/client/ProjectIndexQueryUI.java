/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataFactory;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class ProjectIndexQueryUI extends AbstractProjectIndexQueryUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectIndexQueryUI.class);
	private ProjectIndexQueryFilterUI filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	
	FDCCustomerParams para = new FDCCustomerParams();
    /**
     * output class constructor
     */
    public ProjectIndexQueryUI() throws Exception
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
    
    public void onLoad() throws Exception {
    	tblMain.addKDTDataFillListener(new KDTDataFillListener(){
    		public void afterDataFill(KDTDataRequestEvent e) {
    			tblMain_afterDataFill(e);
    		}
    	});
    	
    	super.onLoad();
    	tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    }
    
    protected void execQuery() {
    	FilterInfo filter=new  FilterInfo();
    	CustomerParams cp = this.filterUI.getCustomerParams();
    	FDCCustomerParams param=new FDCCustomerParams(cp);
    	
    	filter.getFilterItems().add(new FilterItemInfo("projOrOrgID",FDCHelper.getSetByArray(param.getStringArray(filterUI.PROJECT_IDS)),CompareType.INCLUDE));
    	String projectStage = param.getString(filterUI.PROJECTSTATE);
    	apportIds = FDCHelper.getSetByArray(param.getStringArray(filterUI.APPORTION_TYPE_IDS));
//    	if(apportIds.size()>0){
//    		filter.getFilterItems().add(new FilterItemInfo("apportionType.id",FDCHelper.getSetByArray(param.getStringArray(filterUI.APPORTION_TYPE_IDS)),CompareType.INCLUDE));
//    	}
    	if(projectStage!=null){
    		filter.appendFilterItem("projectStage", projectStage);
    	}
    	Set productIds = FDCHelper.getSetByArray(param.getStringArray(filterUI.PRODUCT_TYPE_IDS));
    	if(productIds.size()==0){
    		filter.getFilterItems().add(new FilterItemInfo("productType.id",null));
    	}else{
    		filter.getFilterItems().add(new FilterItemInfo("productType.id",FDCHelper.getSetByArray(param.getStringArray(filterUI.PRODUCT_TYPE_IDS)),CompareType.INCLUDE));
    	}
    	filter.getFilterItems().add(new FilterItemInfo("aimMeasureID",null));
/*    	Set apportIds = FDCHelper.getSetByArray(param.getStringArray(filterUI.APPORTION_TYPE_IDS));
		if(apportIds.size()>0){
    		filter.getFilterItems().add(new FilterItemInfo("apportionType.id",FDCHelper.getSetByArray(param.getStringArray(filterUI.APPORTION_TYPE_IDS)),CompareType.INCLUDE));
    	}*///KSQL翻译有问题，在表格中直接过滤
    	mainQuery.setFilter(filter);
    	super.execQuery();
    	
/*    	if(tblMain.getRowCount()>0){
    		for(int i=0;i<tblMain.getRowCount();i++){
    			if(tblMain.getCell(i, "")){
    				
    			}
    		}
    	}*/
    	
/*    	tblMain.getColumn("curProject.longNumber").getStyleAttributes().setHided(true);
    	tblMain.getColumn("productType.number").getStyleAttributes().setHided(true);
    	tblMain.getColumn("apportionType.number").getStyleAttributes().setHided(true);*/
    }
    
    protected ICoreBase getBizInterface() throws Exception {
    	return ProjectIndexDataFactory.getRemoteInstance();
    }
    protected String getEditUIName() {
    	return null;
    }
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setShowFilter(false);
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setOwner(this);
		return commonQueryDialog;
	}
	
	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI =new ProjectIndexQueryFilterUI(this,this.actionOnLoad);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}
	protected boolean initDefaultFilter() {
		return true;
	}
	
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	public KDTable getMainTable(){
		return this.tblMain;
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		return;
	}
	private Set apportIds =null; 
	public void tblMain_afterDataFill(KDTDataRequestEvent e){
		if(apportIds==null||apportIds.size()<=0){
			return;
		}
		int start = e.getFirstRow();
		int end = e.getLastRow();
		for(int i=end;i>=start;i--){
			if(tblMain.getCell(i, "apportionType.id")!=null&&tblMain.getCell(i, "apportionType.id").getValue()!=null){
				String id=tblMain.getCell(i, "apportionType.id").getValue().toString();
				if(!apportIds.contains(id)){
					tblMain.removeRow(i);
				}
			}
		}
	}
}