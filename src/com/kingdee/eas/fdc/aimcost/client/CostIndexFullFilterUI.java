/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.ProjectTypeCollection;
import com.kingdee.eas.fdc.basedata.ProjectTypeFactory;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUCollection;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUFactory;
import com.kingdee.eas.fdc.basedata.RptParamConst;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.client.F7ProjectTreeSelectorPromptBox;
import com.kingdee.eas.fdc.invite.InviteTypeCollection;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelCollection;
import com.kingdee.eas.fdc.sellhouse.RoomModelFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataCollection;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataFactory;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.framework.report.util.RptConditionManager;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptParamsUtil;

/**
 * output class name
 */
public class CostIndexFullFilterUI extends AbstractCostIndexFullFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(CostIndexFullFilterUI.class);
    protected ListUI listUI;
    protected ItemAction actionListOnLoad;
    public CostIndexFullFilterUI(ListUI listUI, ItemAction actionListOnLoad) throws Exception
    {
        super();
        this.listUI = listUI;
        this.actionListOnLoad = actionListOnLoad;		
    }
    private F7ProjectTreeSelectorPromptBox getProjectSelecotorBox()throws Exception {
		ProjectTreeBuilder builder = new ProjectTreeBuilder(true,true);
		
		KDTree tree = new KDTree();
		builder.build(this.listUI, tree, this.actionListOnLoad);
		TreeModel model = tree.getModel();
		FDCHelper.setTreeForbidNode((DefaultKingdeeTreeNode) model.getRoot(),"isIsEnabled", Boolean.FALSE);
		F7ProjectTreeSelectorPromptBox projectTreeSelectorPromptBox=new F7ProjectTreeSelectorPromptBox(this, model, "getId,toString",FDCHelper.getF7Ids(prmtProject));
		return projectTreeSelectorPromptBox;
	}
    public void clear() {
		this.prmtProject.setValue(null);
		this.prmtInviteType.setValue(null);
		this.txtBuildPriceFrom.setValue(null);
		this.txtBuildPriceTo.setValue(null);
		this.txtSalePriceFrom.setValue(null);
		this.txtSalePriceTo.setValue(null);
		this.pkFromDate.setValue(null);
		this.pkToDate.setValue(null);
	}
	public void onLoad() throws Exception {
		super.onLoad();
		
	}
    protected void prmtProject_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
        F7ProjectTreeSelectorPromptBox projectTreeSelectorPromptBox = getProjectSelecotorBox();
		this.prmtProject.setSelector(projectTreeSelectorPromptBox);
//		this.prmtProject.getQueryAgent().resetRuntimeEntityView();
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter=new FilterInfo();
//		CostCenterOrgUnitInfo currentCompany = SysContext.getSysContext().getCurrentCostUnit();
//		if(currentCompany.isIsBizUnit()){
//			Set companySet=new HashSet();
//			companySet.add(currentCompany.getId());
//			//找出它对应的成本中心
//			EntityViewInfo prjView=new EntityViewInfo();
//			FilterInfo myFilter=new FilterInfo();
//			prjView.setFilter(myFilter);
//			myFilter.getFilterItems().add(new FilterItemInfo("costCenterOU",companySet,CompareType.INCLUDE));
//			prjView.getSelector().add("curProject.id");
//			prjView.getSelector().add("curProject.longNumber");
//			final ProjectWithCostCenterOUCollection projectWithCostCenterOUCollection = ProjectWithCostCenterOUFactory.getRemoteInstance().getProjectWithCostCenterOUCollection(prjView);
//			FilterInfo temp=new  FilterInfo();
//			for(int i=0;i<projectWithCostCenterOUCollection.size();i++){
//				String longNumber = projectWithCostCenterOUCollection.get(i).getCurProject().getLongNumber();
//				FilterInfo tempFilter=new FilterInfo();
//				tempFilter.getFilterItems().add(new  FilterItemInfo("longNumber",longNumber,CompareType.EQUALS));
//				tempFilter.getFilterItems().add(new  FilterItemInfo("longNumber",longNumber+".%",CompareType.LIKE));
//				tempFilter.setMaskString("#0 or #1");
//				temp.mergeFilter(tempFilter, "or");
//			}
//			filter.mergeFilter(temp, "and");
//		}
//		view.setFilter(filter);
//		this.prmtProject.setEntityViewInfo(view);
    }
    public FilterInfo getFilterInfo()
	{
	    FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
	    FilterInfo filter = new FilterInfo();
	    String curProject=para.getString("curProject");
	    String inviteType=para.getString("inviteType");
	    Date fromDate=para.getDate("fromDate");
	    Date toDate=para.getDate("toDate");
	    String fromBuildPrice=para.getString("fromBuildPrice");
	    String toBuildPrice=para.getString("toBuildPrice");
	    String fromSalePrice=para.getString("fromSalePrice");
	    String toSalePrice=para.getString("toSalePrice");
	    String productType=para.getString("productType");
	    if(curProject != null){
	    	filter.getFilterItems().add(new FilterItemInfo("contract.curProject.id", curProject, CompareType.INNER));
	    }
	    if(inviteType != null){
	    	filter.getFilterItems().add(new FilterItemInfo("inviteType.id", inviteType, CompareType.INNER));
	    }
	    if(fromDate!=null){
	    	filter.getFilterItems().add(new FilterItemInfo("contract.auditTime", fromDate, CompareType.GREATER_EQUALS));
	    }
	    if(toDate!=null){
	    	filter.getFilterItems().add(new FilterItemInfo("contract.auditTime", FDCDateHelper.addDays(toDate, 1), CompareType.LESS_EQUALS));
	    }
	    if(fromBuildPrice!=null){
	    	filter.getFilterItems().add(new FilterItemInfo("buildPrice", fromBuildPrice, CompareType.GREATER_EQUALS));
	    }
	    if(toBuildPrice!=null){
	    	filter.getFilterItems().add(new FilterItemInfo("buildPrice", toBuildPrice, CompareType.LESS_EQUALS));
	    }
	    if(fromSalePrice!=null){
	    	filter.getFilterItems().add(new FilterItemInfo("salePrice", fromSalePrice, CompareType.GREATER_EQUALS));
	    }
	    if(toSalePrice!=null){
	    	filter.getFilterItems().add(new FilterItemInfo("salePrice", toSalePrice, CompareType.LESS_EQUALS));
	    }
	    if(productType!=null){
	    	filter.getFilterItems().add(new FilterItemInfo("productType","%"+productType+"%", CompareType.LIKE));
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
	    if(this.prmtInviteType.getValue() != null){
	    	Object[] inviteType = (Object[])this.prmtInviteType.getValue();
	    	String inviteTypeString="";
	    	for(int i=0;i<inviteType.length;i++){
	    		if(inviteType[i]==null) continue;
	    		if(i==0)
	    			inviteTypeString+="'"+((InviteTypeInfo)inviteType[i]).getId().toString()+"'";
	    		else
	    			inviteTypeString+=",'"+((InviteTypeInfo)inviteType[i]).getId().toString()+"'";
	    	}
	    	if(!inviteTypeString.equals("")) param.add("inviteType", inviteTypeString);
	    }
	    if(this.pkFromDate.getValue()!=null){
	    	param.add("fromDate",(Date)this.pkFromDate.getValue());
	    }
	    if(this.pkToDate.getValue()!=null){
	    	param.add("toDate", (Date)this.pkToDate.getValue());
	    }
	    if(this.txtBuildPriceFrom.getBigDecimalValue()!=null){
	    	param.add("fromBuildPrice", this.txtBuildPriceFrom.getBigDecimalValue().toString());
	    }
	    if(this.txtBuildPriceTo.getBigDecimalValue()!=null){
	    	param.add("toBuildPrice", this.txtBuildPriceTo.getBigDecimalValue().toString());
	    }
	    if(this.txtSalePriceFrom.getBigDecimalValue()!=null){
	    	param.add("fromSalePrice", this.txtSalePriceFrom.getBigDecimalValue().toString());
	    }
	    if(this.txtSalePriceTo.getBigDecimalValue()!=null){
	    	param.add("toSalePrice", this.txtSalePriceTo.getBigDecimalValue().toString());
	    }
	    if(this.txtProductType.getText()!=null&&!"".equals(this.txtProductType.getText().trim())){
	    	param.add("productType", this.txtProductType.getText());
	    }
	    return param.getCustomerParams();
	}
    public void setCustomerParams(CustomerParams cp){
	    if(cp == null)
	        return;
	    FDCCustomerParams para = new FDCCustomerParams(cp);
	    String curProjectString=para.getString("curProject");
	    String inviteTypeString=para.getString("inviteType");
	    if(curProjectString != null){
			try {
				CurProjectCollection col = CurProjectFactory.getRemoteInstance().getCurProjectCollection("select * from where id in("+curProjectString+")");
				this.prmtProject.setValue(col.toArray());
			} catch (BOSException e) {
				e.printStackTrace();
			}
	    }
	    if(inviteTypeString != null){
			try {
				InviteTypeCollection col = InviteTypeFactory.getRemoteInstance().getInviteTypeCollection("select * from where id in("+inviteTypeString+")");
				this.prmtInviteType.setValue(col.toArray());
			} catch (BOSException e) {
				e.printStackTrace();
			}
	    }
	    if(para.get("fromDate")!=null){
	    	this.pkFromDate.setValue(para.getDate("fromDate"));
	    }
	    if(para.get("toDate")!=null){
	    	this.pkToDate.setValue(para.getDate("toDate"));
	    }
	    if(para.get("buildPriceFrom")!=null){
	    	this.txtBuildPriceFrom.setValue(new BigDecimal(para.getString("buildPriceFrom")));
	    }
	    if(para.get("buildPriceTo")!=null){
	    	this.txtBuildPriceTo.setValue(new BigDecimal(para.getString("buildPriceTo")));
	    }
	    if(para.get("salePriceFrom")!=null){
	    	this.txtSalePriceFrom.setValue(new BigDecimal(para.getString("salePriceFrom")));
	    }
	    if(para.get("salePriceTo")!=null){
	    	this.txtSalePriceTo.setValue(new BigDecimal(para.getString("salePriceTo")));
	    }
	    if(para.get("productType")!=null){
	    	this.txtProductType.setText(para.getString("productType"));
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
    
}