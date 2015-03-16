/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.util.EventListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.ormapping_ex.ast.SelectItem;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.fdc.basedata.ApportionTypeCollection;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.ProjectTypeFactory;
import com.kingdee.eas.fdc.basedata.ProjectTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ProjectIndexQueryFilterUI extends AbstractProjectIndexQueryFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProjectIndexQueryFilterUI.class);
    
	protected ItemAction actionListOnLoad;

	private boolean isLoaded;

	protected ListUI listUI;
    public static final String PROJECT_TYPE_ID = "projectTypeId";

	public static final String PRODUCT_TYPE_IDS = "productTypeIds";

	public static final String DF_TYPE_VALUE = "dfTypeValue";
	
	public static final String PROJECT_IDS = "projectIds";
	
	public static final String APPORTION_TYPE_IDS = "apportion_type_ids";
	public static final String PROJECTSTATE = "PROJECTSTATE";
	public static final String PROJECT_TYPE_NAME = "projectTypeName";
	public ProjectIndexQueryFilterUI(ListUI listUI, ItemAction actionListOnLoad) throws Exception {
		super();
		this.listUI = listUI;
		this.actionListOnLoad = actionListOnLoad;
	}
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	/**
	 * output chkWholeProject_stateChanged method
	 */
	protected void chkWholeProject_stateChanged(javax.swing.event.ChangeEvent e)
			throws Exception {
		super.chkWholeProject_stateChanged(e);
		if (this.chkWholeProject.isSelected()) {
			this.prmtProductType.setEnabled(false);
			this.prmtProductType.setValue(null);
		} else {
			this.prmtProductType.setEnabled(true);
		}
	}

	
	public FilterInfo getFilterInfo() {
		return new FilterInfo();
	}

	public void clear() {
//		this.prmtProjectType.setValue(null);
//		this.prmtProductType.setValue(null);
//		this.prmtProject.setValue(null);
//		this.prmtApportion.setValue(null);
//		this.comboStage.setSelectedItem(ProjectStageEnum.DYNCOST);

	}
	public void onLoad() throws Exception {
		super.onLoad();
		
		prmtProject.setEnabledMultiSelection(true);
		prmtProject.setDisplayFormat("$name$");
		prmtProject.setEditFormat("$number$");
		prmtProject.setCommitFormat("$number$");
		prmtProjectType.setRequired(true);
		prmtProductType.setEnabledMultiSelection(true);
		prmtApportion.setEnabledMultiSelection(true);
		prmtApportion.setDisplayFormat("$name$");
		prmtApportion.setEditFormat("$number$");
		prmtApportion.setCommitFormat("$number$");
		if (!isLoaded) {
			this.clear();
		}
		isLoaded = true;
		removeDataChangeLiseten();
		String  projectId = (String)this.listUI.getUIContext().get("project");
		ProductTypeInfo productTypeInfo =(ProductTypeInfo) this.listUI.getUIContext().get("productTypeInfo");
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("*");
		sel.add("projectType.*");
		CurProjectInfo info = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(projectId), sel);
		CurProjectInfo[] curArray = {info};
		prmtProject.setValue(curArray);
		prmtProjectType.setValue(info.getProjectType());
		prmtProductType.setValue(productTypeInfo);
		comboStage.removeItem(ProjectStageEnum.RESEARCH);
		comboStage.setSelectedItem(ProjectStageEnum.AIMCOST);
		
		addDataChangeLiseten();
		if(productTypeInfo == null){
			this.chkWholeProject.setSelected(true);
		}
		
	}
	public boolean verify() {
		if (this.prmtProjectType.getValue()==null) {
			MsgBox.showWarning(this, "没有选择项目系列");
			prmtProductType.requestFocus();
			return false;
		}
		Object[] proj = (Object[]) this.prmtProject.getValue();
		if (FDCHelper.isEmpty(proj)) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("noSelectProj"));
			prmtProject.requestFocus();
			return false;
		}
		return true;
	}
	protected void prmtProject_willShow(SelectorEvent e) throws Exception {
		prmtProject.getQueryAgent().resetRuntimeEntityView();
		String projectTypeId = FDCHelper.getF7Id(prmtProjectType);
		if(projectTypeId==null){
			MsgBox.showWarning(this, "请先选择项目系列");
			SysUtil.abort();
		}
		EntityViewInfo view = prmtProject.getEntityViewInfo();
		if(view==null){
			view=new EntityViewInfo();
		}
		FilterInfo filter=new FilterInfo();
		
		filter.appendFilterItem("projectType.id", projectTypeId);
		view.setFilter(filter);
		prmtProject.setEntityViewInfo(view);
		super.prmtProject_willShow(e);
	}
	
	protected void prmtProjectType_dataChanged(DataChangeEvent e)
			throws Exception {
		super.prmtProjectType_dataChanged(e);
		prmtProject.setValue(null);
	}
	
	DataChangeListener[] projectData = null;
	DataChangeListener[] projectTypeData = null;
	private void removeDataChangeLiseten(){
		projectData =  (DataChangeListener[])prmtProject.getListeners(DataChangeListener.class);
		for(int i = 0 ; i < projectData.length ; i++){
			prmtProject.removeDataChangeListener(projectData[i]);
		}
		projectTypeData =  (DataChangeListener[])prmtProjectType.getListeners(DataChangeListener.class);
		for(int i = 0 ; i < projectTypeData.length ; i++){
			prmtProjectType.removeDataChangeListener(projectTypeData[i]);
		}
	}
	
	private void addDataChangeLiseten(){
		if(projectData != null){
			for(int i = 0 ; i < projectData.length ; i++){
				prmtProject.addDataChangeListener(projectData[i]);
			}
		}
		if(projectTypeData != null){
			for(int i = 0 ; i < projectTypeData.length ; i++){
				prmtProjectType.addDataChangeListener(projectTypeData[i]);
			}
		}
	}
	public void setCustomerParams(CustomerParams cp) {
		
		if(cp == null) return;
		
		FDCCustomerParams para = new FDCCustomerParams(cp);
		
		if(para.getString(PROJECT_TYPE_ID) != null) {
			String id = (String)para.get(PROJECT_TYPE_ID);
			ProjectTypeInfo info = null;
			try {
				info = ProjectTypeFactory.getRemoteInstance().getProjectTypeInfo(new ObjectUuidPK(id));
			} catch (Exception e) {
				handUIException(e);
			} 
			prmtProjectType.setValue(info);
		}
		if(para.getString(PROJECT_IDS) != null) {
			String[] ids = (String[])para.getStringArray(PROJECT_IDS);
			if(!FDCHelper.isEmpty(ids)) {
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", FDCHelper.getSetByArray(ids), CompareType.INCLUDE));
				view.setFilter(filter);
				view.getSelector().add("id");
				view.getSelector().add("number");
				view.getSelector().add("name");
				
				CurProjectCollection colls = null;
				try {
					colls = CurProjectFactory.getRemoteInstance().getCurProjectCollection(view);
				} catch (Exception e) {
					handUIException(e);
				} 
				if(colls != null)
				{
					prmtProject.setValue(colls.toArray());
				}
			}
		}
		
		if(para.getString(PRODUCT_TYPE_IDS) != null) {
			String[] ids = (String[])para.getStringArray(PRODUCT_TYPE_IDS);
			if(!FDCHelper.isEmpty(ids)) {
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", FDCHelper.getSetByArray(ids), CompareType.INCLUDE));
				view.setFilter(filter);
				view.getSelector().add("id");
				view.getSelector().add("number");
				view.getSelector().add("name");
				
				ProductTypeCollection colls = null;
				try {
					colls = ProductTypeFactory.getRemoteInstance().getProductTypeCollection(view);
				} catch (Exception e) {
					handUIException(e);
				} 
				
				if(colls != null)
				{
					prmtProductType.setValue(colls.toArray());
				}
			}
		}else{
			chkWholeProject.setSelected(true);
		}
		
		if(para.getString(APPORTION_TYPE_IDS) != null) {
			String[] ids = (String[])para.getStringArray(APPORTION_TYPE_IDS);
			if(!FDCHelper.isEmpty(ids)) {
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", FDCHelper.getSetByArray(ids), CompareType.INCLUDE));
				view.setFilter(filter);
				view.getSelector().add("id");
				view.getSelector().add("number");
				view.getSelector().add("name");
				
				ApportionTypeCollection colls = null;
				try {
					colls = ApportionTypeFactory.getRemoteInstance().getApportionTypeCollection(view);
				} catch (Exception e) {
					handUIException(e);
				} 
				
				if(colls != null)
				{
					prmtProductType.setValue(colls.toArray());
				}
			}
		}
		if(para.getString(PROJECTSTATE)!=null){
			comboStage.setSelectedItem(ProjectStageEnum.getEnum(para.getString(PROJECTSTATE)));
		}
	}
	
	public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();
		
		String projectTypeId = FDCHelper.getF7Id(prmtProjectType);
		
		if(!FDCHelper.isEmpty(projectTypeId)) {
			param.add(PROJECT_TYPE_ID, projectTypeId);
			param.add(PROJECT_TYPE_NAME, ((DataBaseInfo)prmtProjectType.getData()).getName());
		}
		String[] prjIds = FDCHelper.getF7Ids(prmtProject);
		if(!FDCHelper.isEmpty(prjIds)){
			param.add(PROJECT_IDS, prjIds);
		}
		
		String[] productTypeIds = FDCHelper.getF7Ids(prmtProductType);
		if(!FDCHelper.isEmpty(productTypeIds)) {
			param.add(PRODUCT_TYPE_IDS, productTypeIds);
		}
		String[] apportIds = FDCHelper.getF7Ids(prmtApportion);
		if(!FDCHelper.isEmpty(apportIds)) {
			param.add(APPORTION_TYPE_IDS, apportIds);
		}
		ProjectStageEnum projectstate=(ProjectStageEnum)comboStage.getSelectedItem();
		if(!FDCHelper.isEmpty(projectstate)){
			param.add(PROJECTSTATE, projectstate.getValue());
		}
		return param.getCustomerParams();
	}
	
	public boolean destroyWindow() {
		return super.destroyWindow();
	}
}