/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.client.f7.CostCenterF7;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateNewEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.ContractMonthPayPlanDepStateEnum;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class ContractMonthPayPlanFilterUI extends
		AbstractContractMonthPayPlanFilterUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ContractMonthPayPlanFilterUI.class);

	// 是否使用成本月结,当前财务组织的期间
	private Date[] pkdate;

	protected ListUI listUI;

	protected ItemAction actionListOnLoad;

	private String EDITMONTH;

	private boolean isLoaded;

	/**
	 * output class constructor
	 */
	public ContractMonthPayPlanFilterUI() throws Exception {
		super();
	}

	/**
	 * output class constructor
	 */
	public ContractMonthPayPlanFilterUI(ListUI listUI,
			ItemAction actionListOnLoad) throws Exception {
		super();
		this.listUI = listUI;
		this.actionListOnLoad = actionListOnLoad;

		pkdate = FDCClientHelper.getCompanyCurrentDate();
		// contPayee.setVisible(true);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	private void setDateMonth() {
		SpinnerNumberModel model = new SpinnerNumberModel();
		model.setMinimum(new Integer(1900));
		model.setMaximum(new Integer(3000));
		model.setStepSize(new Integer(1));
		this.spYear.setModel(model);
		model = new SpinnerNumberModel();
		model.setMinimum(new Integer(1));
		model.setMaximum(new Integer(12));
		model.setStepSize(new Integer(1));
		this.spMonth.setModel(model);
		Calendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		this.spYear.setValue(new Integer(year));
		this.spMonth.setValue(new Integer(month));
	}

	public void onLoad() throws Exception {
		super.onLoad();
		setDateMonth();
//		String cuId = SysContext.getSysContext().getCurrentCtrlUnit().getId()
//				.toString();
//		// 责任部门的过滤跟合同编辑界面保持一致
//		FDCClientUtils.setRespDeptF7(f7Deptment, this,
//				canSelecOtherOrgPerson() ? null : cuId);
		initCostCenterF7();
		String orgID = null;
		CompanyOrgUnitInfo fiOrg = SysContext.getSysContext().getCurrentFIUnit();
		if (fiOrg == null) {
			// 取上级的所有项目，如果上级还不是财务组织，那就木办法了
			FullOrgUnitInfo parent = SysContext.getSysContext()
					.getCurrentOrgUnit().castToFullOrgUnitInfo().getParent();
			if (parent != null) {
				orgID = parent.getId().toString();
			}
		} else {
			orgID = fiOrg.getId().toString();
		}
		if (orgID != null) {
			Map prjs = FDCUtils.getOrgUnitProjects(null, orgID);
			Set keys = prjs.keySet();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("id", keys, CompareType.INCLUDE));
			view.setFilter(filter);
			f7CurProject.setEntityViewInfo(view);
		}
		if (!isLoaded) {
			this.clear();
		}
		isLoaded = true;
		depState.insertItemAt(" ", 0);
		depState.setSelectedItem(null);
	}

	public void clear() {
		super.clear();
		this.f7CurProject.setValue(null);
		this.f7Deptment.setValue(null);
		setDateMonth();
		this.depState.setSelectedItem(ContractMonthPayPlanDepStateEnum.all);
	}

	// 责任人是否可以选择其他部门的人员
	private boolean canSelecOtherOrgPerson() {
		boolean canSelectOtherOrgPerson = false;
		try {
			canSelectOtherOrgPerson = FDCUtils.getDefaultFDCParamByKey(null,
					null, FDCConstants.FDC_PARAM_SELECTPERSON);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return canSelectOtherOrgPerson;
	}

	public FilterInfo getFilterInfo() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());

		FilterInfo filter = new FilterInfo();

		if (para.get(F7DEPTMENTIDS) != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("deptment.id", para.get(F7DEPTMENTIDS)));
		} else {
			String orgNum = SysContext.getSysContext().getCurrentOrgUnit()
					.getLongNumber();
			filter.getFilterItems().add(
					new FilterItemInfo("deptment.longNumber", orgNum+"%",CompareType.LIKE));
		}
		if (para.get(F7CURPROJECTIDS) != null) {
			filter.getFilterItems()
					.add(
							new FilterItemInfo("project.id", para
									.get(F7CURPROJECTIDS)));
		}

		if (para.get(DATE) != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("editMonth", para.get(DATE)));
		}
		if (para.get(DEPSTATEID) != null) {
			if (depState.getSelectedItem().equals(" ")) {
				return filter;
			} else {
				filter.getFilterItems().add(
						new FilterItemInfo("state", para.get(DEPSTATEID)));
			}
		}

		return filter;

	}

	public static final String F7DEPTMENTIDS = "f7DeptmentIds";
	public static final String F7CURPROJECTIDS = "f7CurProjectIds";
	public static final String DATE = "EDITMONTH";
	// public static final String F7DEPTMENT = "f7DeptmentIds";
	public static final String DEPSTATEID = "state";

	public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();

		if (f7Deptment.getValue() != null) {
			param.add(F7DEPTMENTIDS, ((CostCenterOrgUnitInfo) f7Deptment.getValue())
					.getId().toString());
		}
		if (f7CurProject.getValue() != null) {
			param.add(F7CURPROJECTIDS, ((CurProjectInfo) f7CurProject
					.getValue()).getId().toString());
		}

		if (depState.getSelectedItem() != null) {
			if (depState.getSelectedItem().equals(" ")) {
				param.add(DEPSTATEID, 0);
			} else {
				param.add(DEPSTATEID, ((FDCBillStateNewEnum) depState
						.getSelectedItem()).getValue());
			}
		}

		String yearStr = spYear.getValue().toString();
		String monthStr = spMonth.getValue().toString();
		String editMonth = "";
		// if (Integer.parseInt(monthStr) < 10) {
		// monthStr = "0" + monthStr;
		// }
		editMonth = yearStr + "-" + monthStr;
		if (spYear.getValue() != null && spMonth.getValue() != null) {
			param.add(DATE, editMonth);
		}

		return param.getCustomerParams();

	}

	public void setCustomerParams(CustomerParams cp) {
		if (cp == null)
			return;
		FDCCustomerParams para = new FDCCustomerParams(cp);

		if (para.get(F7DEPTMENTIDS) != null) {
			try {
				f7Deptment.setValue(CostCenterOrgUnitFactory.getRemoteInstance()
						.getCostCenterOrgUnitInfo(
								new ObjectUuidPK(BOSUuid.read(para
										.get(F7DEPTMENTIDS)))));
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (UuidException e) {
				e.printStackTrace();
			}
		}
		if (para.get(F7CURPROJECTIDS) != null) {
			try {
				f7CurProject.setValue(CurProjectFactory.getRemoteInstance()
						.getCurProjectInfo(
								new ObjectUuidPK(BOSUuid.read(para
										.get(F7CURPROJECTIDS)))));
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (UuidException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}

		if (para.get(DATE) != null) {
			String year = para.get(DATE).substring(0, 4);
			String month = para.get(DATE).substring(5);
			try {
				this.spYear.setValue(new Integer(year));
				this.spMonth.setValue(new Integer(month));
			} catch (Exception e) {
				handUIException(e);
			}
		}

		if (para.get(DEPSTATEID) != null) {
			if (FDCBillStateNewEnum.SAVED_VALUE.equals(para.get(DEPSTATEID))) {
				depState.setSelectedItem(FDCBillStateNewEnum.SAVED);
			} else if (FDCBillStateNewEnum.SUBMITTED_VALUE.equals(para
					.get(DEPSTATEID))) {
				depState.setSelectedItem(FDCBillStateNewEnum.SUBMITTED);
			} else if (FDCBillStateNewEnum.AUDITTED_VALUE.equals(para
					.get(DEPSTATEID))) {
				depState.setSelectedItem(FDCBillStateNewEnum.AUDITTED);
			} else if (FDCBillStateNewEnum.AUDITTING_VALUE.equals(para
					.get(DEPSTATEID))) {
				depState.setSelectedItem(FDCBillStateNewEnum.AUDITTING);
			} else if (FDCBillStateNewEnum.PUBLISH_VALUE.equals(para
					.get(DEPSTATEID))) {
				depState.setSelectedItem(FDCBillStateNewEnum.PUBLISH);
			} else if (FDCBillStateNewEnum.BACK_VALUE.equals(para
					.get(DEPSTATEID))) {
				depState.setSelectedItem(FDCBillStateNewEnum.BACK);
			} else if (FDCBillStateNewEnum.REVISE_VALUE.equals(para
					.get(DEPSTATEID))) {
				depState.setSelectedItem(FDCBillStateNewEnum.REVISE);
			} else if (depState.getSelectedItem() == null
					|| depState.getSelectedItem().equals("0")) {
				depState.setSelectedItem(" ");
			} else if (FDCBillStateNewEnum.REVISING_VALUE.equals(para
					.get(DEPSTATEID))) {
				depState.setSelectedItem(FDCBillStateNewEnum.REVISING);
			}
		}

		super.setCustomerParams(para.getCustomerParams());

	}

	public void initCostCenterF7() {
		f7Deptment
				.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery");
		String cuID = SysContext.getSysContext().getCurrentCtrlUnit().getId()
				.toString();
		EntityViewInfo view = new EntityViewInfo();

		SorterItemCollection sorc = view.getSorter();
		SorterItemInfo sort = new SorterItemInfo("number");
		sorc.add(sort);

		// String longNumber = SysContext.getSysContext().getCurrentFIUnit()
		// .getLongNumber();
		FilterInfo filter = new FilterInfo();

		FilterItemCollection fic = filter.getFilterItems();
		// fic.add(new FilterItemInfo("isFreeze", new Integer(0)));
		// fic.add(new FilterItemInfo("isSealUp", new Integer(0)));

		if (cuID != null) {
			fic.add(new FilterItemInfo("CU.id", cuID));
		}

		// 用户组织范围内的组织才能选择
		try {
			Set authorizedOrgs = new HashSet();
			Map orgs = (Map) ActionCache
					.get("FDCBillEditUIHandler.authorizedOrgs");
			if (orgs == null) {
				orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
						new ObjectUuidPK(SysContext.getSysContext()
								.getCurrentUserInfo().getId()), OrgType.CostCenter,
						null, null, null);
			}
			if (orgs != null) {
				Set orgSet = orgs.keySet();
				Iterator it = orgSet.iterator();
				while (it.hasNext()) {
					authorizedOrgs.add(it.next());
				}
			}
			FilterInfo filterID = new FilterInfo();
			filterID.getFilterItems().add(
					new FilterItemInfo("id", authorizedOrgs,
							CompareType.INCLUDE));

			filter.mergeFilter(filterID, "and");

		} catch (Exception e) {
			e.printStackTrace();
		}

		view.setFilter(filter);
		f7Deptment.setEntityViewInfo(view);

		CostCenterF7 f7 = new CostCenterF7(this);
		f7.showCheckBoxOfShowingAllOUs();
		f7.setIsCUFilter(true);
		f7.setRootUnitID(cuID);
		//
		if (cuID != null)
			f7.setCurrentCUID(cuID);

		f7Deptment.setSelector(f7);
		SelectorItemCollection sic = f7Deptment.getSelectorCollection();
		if (sic == null) {
			sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("number"));
			sic.add(new SelectorItemInfo("name"));
			sic.add(new SelectorItemInfo("isLeaf"));
			sic.add(new SelectorItemInfo("displayName"));
			sic.add(new SelectorItemInfo("longNumber"));
			f7Deptment.setSelectorCollection(sic);
		}

		// 只能选择明细的，增加监听交验

//		if (f7Deptment.getClientProperty("RespDeptDataChangeLisenter") == null) {
//			RespDeptDataChangeLisenter ls = new RespDeptDataChangeLisenter(this);
//			f7Deptment.addDataChangeListener(ls);
//			f7Deptment.putClientProperty("RespDeptDataChangeLisenter", ls);
//		}

		// 通用F7是可以做到隔离的,只要bizPromptBox.setEntityViewInfo(view)正确 JinXP 20071217
		/*
		 * //防止输入编码调出通用F7来选择，那样无法做到隔离 bizPromptBox.setQueryInfo(null);
		 */

	}

}