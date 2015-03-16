package com.kingdee.eas.fdc.finance.client;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.client.DeductBillFullFilterUI;
import com.kingdee.eas.framework.client.ListUI;

public class DeductBillListFilterUI extends DeductBillFullFilterUI {
	private static final Logger logger = CoreUIObject.getLogger("com.kingdee.eas.fdc.finance.client.DeductBillListFilterUI");

	private FullOrgUnitInfo company;
	private CurProjectInfo project;
	private Set authorizedOrgs;
	public DeductBillListFilterUI(ListUI listUI, ItemAction load) throws Exception {
		super(listUI, load);
	}
	public void clear() {
		this.pkDateFrom.setValue(pkdate[0]);
		this.pkDateTo.setValue(pkdate[1]);
		this.radioStateAll.setSelected(true);
	}
	public void onLoad() throws Exception {
		super.onLoad();
		this.btnCompanySelect.setVisible(false);
		this.btnProjectSelect.setVisible(false);
	}
	public void setAuthorizedOrgs(Set authorizedOrgs) {
		if (authorizedOrgs == null) {
			authorizedOrgs = (Set) ActionCache.get("FDCBillListUIHandler.authorizedOrgs");
			if (authorizedOrgs == null) {
				try {
					authorizedOrgs = new HashSet();
					Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
							new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()), OrgType.CostCenter, null, null, null);
					if (orgs != null) {
						Set orgSet = orgs.keySet();
						Iterator it = orgSet.iterator();
						while (it.hasNext()) {
							authorizedOrgs.add(it.next());
						}
					}
				} catch (Exception e) {
					logger.error(e.getCause(), e);
				}
			}
		}
		this.authorizedOrgs = authorizedOrgs;
	}

	public void setCompany(FullOrgUnitInfo company) {
		if (company == null) {
			this.txtCompany.setText(null);
		} else {
			this.txtCompany.setText(company.getName());
		}
		this.company = company;
	}

	public void setProject(CurProjectInfo project) {
		if (project == null) {
			this.txtProject.setText(null);
			this.txtProject.setUserObject(null);
		} else {
			this.txtProject.setText(project.getName());
		}
		this.project = project;
	}
	
	protected void getCompanyFilter(FDCCustomerParams para, FilterInfo filter) {
		if (company != null) {
			String orgUnitLongNumber = company.getLongNumber();
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%", CompareType.LIKE));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", authorizedOrgs, CompareType.INCLUDE));
		}
	}

	protected void getProjectFilter(FDCCustomerParams para, FilterInfo filter) {
		if (project != null) {
			try {
				Set idSet = FDCClientUtils.genProjectIdSet(project.getId());
				filter.getFilterItems().add(new FilterItemInfo("curProject.id", idSet, CompareType.INCLUDE));
			} catch (Exception e) {
				logger.error(e.getCause(), e);
			}
		}
	}
}
