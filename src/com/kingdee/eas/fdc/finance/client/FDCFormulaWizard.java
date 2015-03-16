package com.kingdee.eas.fdc.finance.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.IFormatter;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.ICostCenterOrgUnit;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUCollection;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUFactory;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.RespDeptDataChangeLisenter;
import com.kingdee.eas.fi.rpt.client.FormulaWizardUI;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;

/**
 * @author jianxing_zhou 2007-8-28
 * @version EAS-FDC 530
 */
public class FDCFormulaWizard extends FormulaWizardUI {

	private static final long serialVersionUID = -6852520131463808505L;

	private static final Logger logger = Logger.getLogger(FDCFormulaWizard.class);

	// 项目
	private KDBizPromptBox fdcProject;

	// 组织
	private KDBizPromptBox orgUnit;

	public FDCFormulaWizard() throws Exception {
	}

	/*
	 * @see com.kingdee.eas.fi.rpt.client.FormulaWizardUI#onOpen()
	 */
	protected void onOpen() {
		orgUnit = (KDBizPromptBox) super.getParamComponent("orgUnit");
		fdcProject = (KDBizPromptBox) super.getParamComponent("fdcProject");

		final IFormatter displayFormatter = fdcProject.getDisplayFormatter();

		IFormatter format = new IFormatter() {
			public void applyPattern(String pattern) {
				displayFormatter.applyPattern(pattern);
			}

			public String valueToString(Object o) {
				if (displayFormatter.valueToString(o) != null) {
					return displayFormatter.valueToString(o).replaceAll("!", "\\.");
				}
				return null;
			}
		};
		fdcProject.setDisplayFormatter(format);
		orgUnit.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent event) {
				try {
					if(orgUnit.getValue()!=null){
						if (!(orgUnit.getValue() instanceof String)) {
							setFDCProjectFilter(((CostCenterOrgUnitInfo) orgUnit.getValue()).getId().toString());
						} else {
							setFDCProjectFilter((String) orgUnit.getValue());
						}
					}else{
						fdcProject.setValue(null);
						return ;
					}
				} catch (Exception e) {
					logger.error(e);
				}
			}
		});

		fdcProject.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent event) {
				if (orgUnit.getValue() instanceof String && ((String) orgUnit.getValue()).length() < 2) {
					MsgBox.showWarning(fdcProject, "请先选定组织!");
					SysUtil.abort();
				}
			}
		});

		// 只能选择明细的，增加监听交验

		if (orgUnit.getClientProperty("OrgDataChangeLisenter") == null) {
			OrgDataChangeLisenter ls = new OrgDataChangeLisenter(null);
			orgUnit.addDataChangeListener(ls);
			orgUnit.putClientProperty("OrgDataChangeLisenter", ls);
		}

		// 防止输入编码调出通用F7来选择，那样无法做到隔离
		orgUnit.setQueryInfo(null);
		
		if ((orgUnit.getValue() instanceof String)){ 
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", orgUnit.getValue()));
			view.setFilter(filter);
			try {
				CostCenterOrgUnitCollection orgCol = CostCenterOrgUnitFactory.getRemoteInstance().getCostCenterOrgUnitCollection(view);
				if(orgCol!=null && orgCol.size()>0){
					setFDCProjectFilter(orgCol.get(0).getId().toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
			
	}

	/**
	 * 根据组织过滤工程项目
	 * 
	 * @param id
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void setFDCProjectFilter(String id) throws BOSException, EASBizException {
		if(id==null){
			fdcProject.setValue(null);
			return ;
		}else{
			if(fdcProject.getValue()!=null){
				//String 
				if(fdcProject.getValue() instanceof CurProjectInfo){
					CurProjectInfo projectInfo = (CurProjectInfo)fdcProject.getValue();
					if(!id.equals(projectInfo.getCostCenter().getId().toString())){
						fdcProject.setValue(null);
						fdcProject.setText(null);
						fdcProject.setData(null);
						fdcProject.repaint();
					}
				}else if(fdcProject.getValue() instanceof String){
					String longNumber = (String)fdcProject.getValue() ;

					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber));
					filter.getFilterItems().add(new FilterItemInfo("costCenter.id", id));
					
					if(!CurProjectFactory.getRemoteInstance().exists(filter)){
						fdcProject.setValue(null);
						fdcProject.setText(null);
						fdcProject.setData(null);
					}
				}
			}
		}
		//用项目上的costCenter过滤即可，没必要这么复杂
	
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("costCenter.id", id));
		view.setFilter(filter);
		fdcProject.getQueryAgent().setEntityViewInfo(view);
		fdcProject.getQueryAgent().resetRuntimeEntityView();
/*		if(true){
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("costCenterOU.id", id));
		view.setFilter(filter);

		ProjectWithCostCenterOUCollection relations = ProjectWithCostCenterOUFactory.getRemoteInstance().getProjectWithCostCenterOUCollection(view);
		if (relations == null || relations.size() == 0
				|| !CurProjectFactory.getRemoteInstance().exists(new ObjectUuidPK(relations.get(0).getCurProject().getId()))) {
			//
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", null));
			view.setFilter(filter);
			fdcProject.getQueryAgent().setEntityViewInfo(view);
			fdcProject.getQueryAgent().resetRuntimeEntityView();
			return;
		}

		String projNumber = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(relations.get(0).getCurProject().getId())).getLongNumber();
		// Filter
		FilterInfo projFilter = new FilterInfo();
		projFilter.getFilterItems().add(new FilterItemInfo("longnumber", projNumber + ".%", CompareType.LIKE));
		// projFilter.getFilterItems().add(new FilterItemInfo("longnumber", projNumber));
		// projFilter.setMaskString("#0 or #1");
		view = new EntityViewInfo();
		view.setFilter(projFilter);
		fdcProject.getQueryAgent().setEntityViewInfo(view);
		fdcProject.getQueryAgent().resetRuntimeEntityView();*/
	}

	private class OrgDataChangeLisenter implements DataChangeListener {

		private CoreUIObject ui;

		public OrgDataChangeLisenter(CoreUIObject ui) {
			super();
			this.setUi(ui);
		}

		public void dataChanged(DataChangeEvent eventObj) {
			KDBizPromptBox box = (KDBizPromptBox) eventObj.getSource();
			if (box.getData() instanceof TreeBaseInfo) {

				TreeBaseInfo info = (TreeBaseInfo) box.getData();

				if (info != null) {
					SelectorItemCollection selectors = new SelectorItemCollection();
					selectors.add("id");
					selectors.add("number");
					selectors.add("longNumber");
					selectors.add("name");
					selectors.add("isLeaf");
					try {
						info = CostCenterOrgUnitFactory.getRemoteInstance().getCostCenterOrgUnitInfo(new ObjectUuidPK(info.getId()), selectors);
					} catch (Exception e) {
						ExceptionHandler.handle(e);
					}
					if (!info.isIsLeaf()) {
						box.setUserObject(null);
						box.setText(null);
						MsgBox.showWarning(getUi(), FDCClientUtils.getRes("selectLeaf"));
					}
				}
			}
		}

		private void setUi(CoreUIObject ui) {
			this.ui = ui;
		}

		private CoreUIObject getUi() {
			return ui;
		}

	}

}
