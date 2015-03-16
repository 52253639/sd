/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.PositionMemberCollection;
import com.kingdee.eas.basedata.org.PositionMemberFactory;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUCollection;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUFactory;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.invite.InviteTypeCollection;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.ListUI;

/**
 * output class name
 */
public class ContractPayPlanFullFilterUI extends AbstractContractPayPlanFullFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractPayPlanFullFilterUI.class);
    protected ListUI listUI;
    protected ItemAction actionListOnLoad;
    public ContractPayPlanFullFilterUI(ListUI listUI, ItemAction actionListOnLoad) throws Exception
    {
        super();
        this.listUI = listUI;
        this.actionListOnLoad = actionListOnLoad;	
    }
    private F7ProjectTreeSelectorPromptBox getProjectSelecotorBox()throws Exception {
		ProjectTreeBuilder builder = new ProjectTreeBuilder(true);
		
		KDTree tree = new KDTree();
		builder.build(this.listUI, tree, this.actionListOnLoad);
		TreeModel model = tree.getModel();
		FDCHelper.setTreeForbidNode((DefaultKingdeeTreeNode) model.getRoot(),"isIsEnabled", Boolean.FALSE);
		F7ProjectTreeSelectorPromptBox projectTreeSelectorPromptBox=new F7ProjectTreeSelectorPromptBox(this, model, "getId,toString",FDCHelper.getF7Ids(prmtProject));
		return projectTreeSelectorPromptBox;
	}
    public void clear() {
		this.prmtProject.setValue(null);
		this.prmtRespDept.setValue(null);
		this.prmtRespPerson.setValue(null);
		this.pkFromDate.setValue(null);
		this.pkToDate.setValue(null);
	}
	public void onLoad() throws Exception {
		super.onLoad();
		String cuId=SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
    	Map param = (Map) ActionCache.get("FDCBillEditUIHandler.orgParamItem");
		if (param == null) {
			param = FDCUtils.getDefaultFDCParam(null, SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo().getId().toString());
		}
		boolean canSelectOtherOrgPerson = false;
		if (param.get(FDCConstants.FDC_PARAM_SELECTPERSON) != null) {
			canSelectOtherOrgPerson = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SELECTPERSON).toString()).booleanValue();
		}
		FDCClientUtils.setRespDeptF7(prmtRespDept, this,canSelectOtherOrgPerson ? null : cuId);
		FDCClientUtils.setPersonF7(prmtRespPerson, this,canSelectOtherOrgPerson ? null : cuId);
	}
    protected void prmtProject_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
        F7ProjectTreeSelectorPromptBox projectTreeSelectorPromptBox = getProjectSelecotorBox();
		this.prmtProject.setSelector(projectTreeSelectorPromptBox);
		this.prmtProject.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		CostCenterOrgUnitInfo currentCompany = SysContext.getSysContext().getCurrentCostUnit();
		if(currentCompany.isIsBizUnit()){
			Set companySet=new HashSet();
			companySet.add(currentCompany.getId());
			//找出它对应的成本中心
			EntityViewInfo prjView=new EntityViewInfo();
			FilterInfo myFilter=new FilterInfo();
			prjView.setFilter(myFilter);
			myFilter.getFilterItems().add(new FilterItemInfo("costCenterOU",companySet,CompareType.INCLUDE));
			prjView.getSelector().add("curProject.id");
			prjView.getSelector().add("curProject.longNumber");
			final ProjectWithCostCenterOUCollection projectWithCostCenterOUCollection = ProjectWithCostCenterOUFactory.getRemoteInstance().getProjectWithCostCenterOUCollection(prjView);
			FilterInfo temp=new  FilterInfo();
			for(int i=0;i<projectWithCostCenterOUCollection.size();i++){
				String longNumber = projectWithCostCenterOUCollection.get(i).getCurProject().getLongNumber();
				FilterInfo tempFilter=new FilterInfo();
				tempFilter.getFilterItems().add(new  FilterItemInfo("longNumber",longNumber,CompareType.EQUALS));
				tempFilter.getFilterItems().add(new  FilterItemInfo("longNumber",longNumber+".%",CompareType.LIKE));
				tempFilter.setMaskString("#0 or #1");
				temp.mergeFilter(tempFilter, "or");
			}
			filter.mergeFilter(temp, "and");
		}
		view.setFilter(filter);
		this.prmtProject.setEntityViewInfo(view);
    }
    public FilterInfo getFilterInfo()
	{
	    FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
	    FilterInfo filter = new FilterInfo();
	    String curProject=para.getString("curProject");
	    Date fromDate=para.getDate("fromDate");
	    Date toDate=para.getDate("toDate");
	    String respPerson=para.getString("respPerson");
	    String respDept=para.getString("respDept");
	    if(curProject != null){
	    	filter.getFilterItems().add(new FilterItemInfo("contractBill.curProject.id", curProject, CompareType.INNER));
	    }
	    if(fromDate!=null){
	    	filter.getFilterItems().add(new FilterItemInfo("contractBill.auditTime", fromDate, CompareType.GREATER_EQUALS));
	    }
	    if(toDate!=null){
	    	filter.getFilterItems().add(new FilterItemInfo("contractBill.auditTime", FDCDateHelper.addDays(toDate, 1), CompareType.LESS_EQUALS));
	    }
	    if(respPerson!=null){
	    	filter.getFilterItems().add(new FilterItemInfo("respPerson.id", respPerson));
	    }
	    if(respDept!=null){
	    	filter.getFilterItems().add(new FilterItemInfo("respDept.id", respDept));
	    }
	    filter.getFilterItems().add(new FilterItemInfo("isLatest",Boolean.TRUE));
	    return filter;
	}
    public CustomerParams getCustomerParams()
	{
	    FDCCustomerParams param = new FDCCustomerParams();
	    if(this.prmtProject.getValue() != null){
	    	Object[] curProject = (Object[])this.prmtProject.getValue();
	    	List curProjectList=new ArrayList();
	    	String curProjectString="";
	    	for(int i=0;i<curProject.length;i++){
	    		if(curProject[i]==null||!(curProject[i] instanceof CurProjectInfo)) continue;
	    		curProjectList.add(curProject[i]);
	    	}
	    	for(int i=0;i<curProjectList.toArray().length;i++){
	    		if(i==0)
	    			curProjectString+="'"+((CurProjectInfo)curProjectList.toArray()[i]).getId().toString()+"'";
	    		else
	    			curProjectString+=",'"+((CurProjectInfo)curProjectList.toArray()[i]).getId().toString()+"'";
	    	}
	    	if(!curProjectString.equals("")) param.add("curProject", curProjectString);
	    }
	    if(this.prmtRespPerson.getValue() != null){
	    	param.add("respPerson", ((PersonInfo)this.prmtRespPerson.getValue()).getId().toString());
	    }
	    if(this.prmtRespDept.getValue() != null){
	    	param.add("respDept", ((AdminOrgUnitInfo)this.prmtRespDept.getValue()).getId().toString());
	    }
	    if(this.pkFromDate.getValue()!=null){
	    	param.add("fromDate",(Date)this.pkFromDate.getValue());
	    }
	    if(this.pkToDate.getValue()!=null){
	    	param.add("toDate", (Date)this.pkToDate.getValue());
	    }
	    return param.getCustomerParams();
	}
    public void setCustomerParams(CustomerParams cp){
	    if(cp == null)
	        return;
	    FDCCustomerParams para = new FDCCustomerParams(cp);
	    String curProjectString=para.getString("curProject");
	    String respPersonString=para.getString("respPerson");
	    String respDeptString=para.getString("respDept");
	    if(curProjectString != null){
			try {
				CurProjectCollection col = CurProjectFactory.getRemoteInstance().getCurProjectCollection("select * from where id in("+curProjectString+")");
				this.prmtProject.setValue(col.toArray());
			} catch (BOSException e) {
				e.printStackTrace();
			}
	    }
	    if(respPersonString != null){
			try {
				PersonInfo person=PersonFactory.getRemoteInstance().getPersonInfo(new ObjectUuidPK(respPersonString));
				this.prmtRespPerson.setValue(person);
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (EASBizException e) {
				e.printStackTrace();
			}
	    }
	    if(respDeptString != null){
			try {
				AdminOrgUnitInfo orgUnit=AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitInfo(new ObjectUuidPK(respDeptString));
				this.prmtRespDept.setValue(orgUnit);
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (EASBizException e) {
				e.printStackTrace();
			}
	    }
	    if(para.get("fromDate")!=null){
	    	this.pkFromDate.setValue(para.getDate("fromDate"));
	    }
	    if(para.get("toDate")!=null){
	    	this.pkToDate.setValue(para.getDate("toDate"));
	    }
	    super.setCustomerParams(para.getCustomerParams());
	}
	public boolean verify() {
		if(this.prmtProject.getValue()==null){
			 FDCMsgBox.showWarning("工程项目不能为空！");
			 return false;
		}
		return true;
	}
	protected AdminOrgUnitInfo getDepByPerson(PersonInfo person) throws BOSException{
		SelectorItemCollection personsel=new SelectorItemCollection();
		personsel.add("position.adminOrgUnit.*");
		if(person!=null){
			EntityViewInfo view = new EntityViewInfo();
    		FilterInfo filter = new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("person.id",person.getId()));
    		filter.getFilterItems().add(new FilterItemInfo("isPrimary",Boolean.TRUE));
    		view.setFilter(filter);
    		view.setSelector(personsel);
			
			PositionMemberCollection pmcol=PositionMemberFactory.getRemoteInstance().getPositionMemberCollection(view);
			if(pmcol.size()>0){
				return pmcol.get(0).getPosition().getAdminOrgUnit();
			}
		}
		return null;
	}
	protected void prmtRespPerson_dataChanged(DataChangeEvent e) throws Exception {
		PersonInfo person=(PersonInfo) prmtRespPerson.getValue();
		if(person!=null){
			this.prmtRespDept.setValue(getDepByPerson(person));
		}else{
			this.prmtRespDept.setValue(null);
		}
	}
}