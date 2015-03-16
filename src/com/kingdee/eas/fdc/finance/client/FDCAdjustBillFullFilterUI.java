/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.SubEntityInfo;
import com.kingdee.bos.metadata.query.SubObjectInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OUException;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProjectInfo;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.client.TreeSelectDialog;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class FDCAdjustBillFullFilterUI extends AbstractFDCAdjustBillFullFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCAdjustBillFullFilterUI.class);
    
    public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";

	protected ItemAction actionListOnLoad;

	private TreeSelectDialog companySelectDlg;

	private boolean isLoaded;

	protected ListUI listUI;

	private TreeSelectDialog projectSelectDlg;
	
    /**
     * output class constructor
     */
    public FDCAdjustBillFullFilterUI(ListUI listUI, ItemAction actionListOnLoad) throws Exception
    {
        super();
        this.listUI = listUI;
		this.actionListOnLoad = actionListOnLoad;
    }

    protected void btnCompanySelect_actionPerformed(ActionEvent e)
	throws Exception {
		if (this.companySelectDlg == null) {
			this.initCompanyDlg(null);
		}
		Object object = companySelectDlg.showDialog();
		setCompanyByTree(object);
		super.btnCompanySelect_actionPerformed(e);
	}
	
	protected void btnProjectSelect_actionPerformed(ActionEvent e)
		throws Exception {
		if (this.projectSelectDlg == null) {
			this.initProjectDlg(null);
		}
		Object object = projectSelectDlg.showDialog();
		setProjectByTree(object);
		super.btnProjectSelect_actionPerformed(e);
	}
	
	public void clear() {
		this.companySelectDlg = null;
		this.txtCompany.setText(null);
		this.txtCompany.setUserObject(null);
		//CostCenterOrgUnitInfo currentCompany = SysContext.getSysContext()
				//.getCurrentCostUnit();
		CompanyOrgUnitInfo currentCompany = SysContext.getSysContext().getCurrentFIUnit();
		if (currentCompany.isIsBizUnit()) {
			this.btnCompanySelect.setEnabled(false);
			this.txtCompany.setText(currentCompany.getName());
			this.txtCompany.setUserObject(new String[] { currentCompany.getId()
					.toString() });
		}
		this.projectSelectDlg = null;
		this.txtProject.setText(null);
		this.txtProject.setUserObject(null);
		this.f7Contract.setValue(null);
		
		this.radioConAll.setSelected(true);
		this.f7Contract.setDisplayFormat("$name$");
		this.f7Contract.setDisplayFormat("$number$");
		this.f7Contract
				.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractAndWithoutUnionQuery");
		f7Contract.addSelectorListener(new SelectorListener(){
			public void willShow(SelectorEvent e)
			{
				if(txtProject.getUserObject()==null||((Object[]) txtProject.getUserObject()).length<1){
					MsgBox.showWarning(EASResource.getString(resourcePath, "SelectCurProj"));
					e.setCanceled(true);
					SysUtil.abort();
				}
				Object [] arrays=(Object [])txtProject.getUserObject();
				HashSet idSet=new HashSet();
				for(int i=0;i<arrays.length;i++){
					idSet.add(arrays[i]);
				}
				KDBizPromptBox f7=(KDBizPromptBox)e.getSource();
				f7.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo view=new EntityViewInfo();
				FilterInfo filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("curProject.id",idSet,CompareType.INCLUDE));
				view.setFilter(filter);
				f7.setEntityViewInfo(view);
				
			}
		});
	}
	
	public FilterInfo getFilterInfo() {
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", null, CompareType.NOTEQUALS));
		if (para.isNotNull("companyIds")) {
			filter.getFilterItems().add(
					new FilterItemInfo("curProject.fullOrgUnit.id", FDCHelper
							.getSetByArray(para.getStringArray("companyIds")),
							CompareType.INCLUDE));
		}else{
		
			if(companySelectDlg==null){
				try{
					initCompanyDlg(null);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			TreeModel tree = (DefaultTreeModel) companySelectDlg.getTree();
			DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) tree.getRoot();
			java.util.List list = new ArrayList();
			if(root.getUserObject()!=null){
				list.add(root.getUserObject());
			}
			popNode(list, root);
			HashSet set=new HashSet();
			for(Iterator iter=list.iterator();iter.hasNext();){
				OrgStructureInfo company = (OrgStructureInfo)iter.next();
				set.add(company.getUnit().getId().toString());
			}
			
			if(set.size()>0){
				filter.getFilterItems().add(new FilterItemInfo("curProject.fullOrgUnit.id", set,CompareType.INCLUDE));	
			}
		}
		if (para.isNotNull("projectIds")) {
			filter.getFilterItems().add(
					new FilterItemInfo("curProject.id", FDCHelper
							.getSetByArray(para.getStringArray("projectIds")),
							CompareType.INCLUDE));
		}
		if (para.isNotNull("contractIds")) {
			filter.getFilterItems().add(
					new FilterItemInfo("contractBill.id", FDCHelper
							.getSetByArray(para.getStringArray("contractIds")),
							CompareType.INCLUDE));
		}
		if (para.isNotNull("contractIds")) {
			Set idSet = new HashSet();
			Set conIDSet = new HashSet();
			String[] oo=para.getStringArray("contractIds");
			BOSObjectType type = (new ContractWithoutTextInfo()).getBOSType();
			for(int i=0;i<oo.length;i++){
				if(type.equals(BOSUuid.read(oo[i]).getType())){
					
					idSet.add(oo[i]);
				}else{
					conIDSet.add(oo[i]);
				}
			}
			if(para.getInt("conType") == 0){
				filter.getFilterItems().add(
						new FilterItemInfo("contractBill.id", conIDSet,
								CompareType.INCLUDE));
			}else if(para.getInt("conType") == 1){
				filter.getFilterItems().add(
						new FilterItemInfo("contractBill.id", idSet,
								CompareType.INCLUDE));
			}
		}
		if (para.getInt("conType") == 0) {
			filter.getFilterItems().add(
					new FilterItemInfo(
							"contractBaseData.isNoText",
							Boolean.FALSE));
		} else if (para.getInt("conType") == 1) {
			filter.getFilterItems().add(
					new FilterItemInfo(
							"contractBaseData.isNoText",
							Boolean.TRUE));
		}
		return filter;
	}
	
	public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();
		param.add("companyIds", (String[]) this.txtCompany.getUserObject());
		param.add("projectIds", (String[]) this.txtProject.getUserObject());
		
		Object[] contracts = (Object[]) this.f7Contract.getValue();
		if (!FDCHelper.isEmpty(contracts)) {
			String[] ids = new String[contracts.length];
			for (int i = 0; i < contracts.length; i++) {
				if (contracts[i] instanceof ContractBillInfo) {
					ids[i] = ((ContractBillInfo) contracts[i]).getId()
							.toString();
				} else if (contracts[i] instanceof ContractWithoutTextInfo) {
					ids[i] = ((ContractWithoutTextInfo) contracts[i]).getId()
							.toString();
				}
			}
			param.add("contractIds",ids);//.setContractIds(ids);
		}

		if(this.radioContract.isSelected())				param.add("conType", 0);
		else if(this.radioConWithoutText.isSelected())	param.add("conType", 1);
		else if(this.radioConAll.isSelected()) 			param.add("conType", 2);
		return param.getCustomerParams();
	}
	
	private void initCompanyDlg(String[] selectCompayIds) throws OUException,
		Exception {
		/*CtrlUnitInfo cuInfo = SysContext.getSysContext().getCurrentCtrlUnit();
		if (cuInfo == null) {
			throw new OUException(OUException.CU_CAN_NOT_NULL);
		}*/
		
		//非CU组织会过滤出当前CU的其它财务组织数据，以当前财务组织过滤
		OrgUnitInfo orgUnitInfo = SysContext.getSysContext().getCurrentFIUnit();
		if(orgUnitInfo==null){
			orgUnitInfo=SysContext.getSysContext().getCurrentOrgUnit();
		}
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(
				OrgViewType.COMPANY, "", orgUnitInfo.getId().toString(),
				null, FDCHelper
						.getActionPK(this.actionListOnLoad));
		this.companySelectDlg = new TreeSelectDialog(this, orgTreeModel,
				"getUnit,getId,toString", selectCompayIds);
	}
	
	private void initProjectDlg(String[] projectIds) throws Exception {
		ProjectTreeBuilder builder = new ProjectTreeBuilder(true);
		KDTree tree = new KDTree();
		if (this.companySelectDlg != null
				&& this.companySelectDlg.getSelectTree() != null) {
			builder.buildByOrgTree(tree, this.companySelectDlg.getSelectTree());
		} else {
			builder.build(this.listUI, tree, this.actionListOnLoad);
		}
		TreeModel model = tree.getModel();
		FDCHelper.setTreeForbidNode((DefaultKingdeeTreeNode) model.getRoot(),
				"isIsEnabled", Boolean.FALSE);
		projectSelectDlg = new TreeSelectDialog(this, model, "getId,toString",
				projectIds);
	}
	
	public void onLoad() throws Exception {
	//添加一个内部类，重载KDCommonPromptDialog，解决只能返回一种类型合同的问题	-by neo
		KDCommonPromptDialog kdComDialog = new KDCommonPromptDialog(){
			public ArrayList getValueCollection(ArrayList ids, com.kingdee.bos.metadata.entity.SelectorItemCollection sic) {
				try
				{
					IObjectCollection coll =null;
					SubObjectInfo mainObjectInfo = this.getQueryInfo().getMainObject();
					SubEntityInfo mainEntityInfo = null;
					BOSUuid id = null;
					if(ids != null && ids.size() > 0){
						id = BOSUuid.read(ids.get(0).toString());	
					}
					
					if (mainObjectInfo instanceof SubEntityInfo)
					{
						mainEntityInfo = (SubEntityInfo) mainObjectInfo;
						mainEntityInfo.getEntityRef();
					}
		
					EntityViewInfo evInfo = new EntityViewInfo();
					FilterInfo fInfo = new FilterInfo();
					Set ids1 =new HashSet();
					ids1.addAll(ids);
					fInfo.getFilterItems().add(new FilterItemInfo("id",ids1,CompareType.INCLUDE));
					evInfo.setFilter(fInfo);
					evInfo.getSelector().addObjectCollection(sic);
					coll =  DynamicObjectFactory.getRemoteInstance().getCollection(new BOSObjectType("0D6DD1F4"),evInfo);
					coll.addObjectCollection(DynamicObjectFactory.getRemoteInstance().getCollection(new BOSObjectType("3D9A5388"),evInfo));
					ArrayList result = new ArrayList();
					for(int i = 0, count = coll.size(); i < count; i++){
						IObjectValue ov = coll.getObject(i);
						result.add(ov);
					}
					ids.clear();
					ids.addAll(result);
					return ids;
				} catch (BOSException e)
				{
					ExceptionHandler.handle(e);
					return null;
		
				}
			}
		};
		f7Contract.setSelector(kdComDialog);
		super.onLoad();
		
		if (!isLoaded) {
			this.clear();
		}
		isLoaded = true;
	}
	
	private void setCompanyByTree(Object object) {
		List companyIdList = new ArrayList();
		if (object != null) {
			List companyList = (List) object;
			String text = "";
			for (int i = 0; i < companyList.size(); i++) {
				OrgStructureInfo company = (OrgStructureInfo) companyList
						.get(i);
				companyIdList.add(company.getUnit().getId().toString());
				if (company.getUnit().isIsCostOrgUnit()//.isIsCompanyOrgUnit()
						|| company.getUnit().isIsProfitOrgUnit()) {
					if (company.isIsLeaf()) {
						if (!text.equals("")) {
							text += ",";
						}
						text += company.getUnit().getName();
					}
				}
			}
			Object[] ids = companyIdList.toArray(new String[] {});
			ArrayList oldArray = new ArrayList(FDCHelper
					.getSetByArray((String[]) ids));
			ArrayList newArray = new ArrayList(FDCHelper
					.getSetByArray((String[]) this.txtCompany.getUserObject()));
			if (!oldArray.equals(newArray)) {
				try {
					this.initProjectDlg(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.txtProject.setUserObject(null);
				this.txtProject.setText(null);
			}
			this.txtCompany.setText(text);
			if (FDCHelper.isEmpty(ids)) {
				this.txtCompany.setUserObject(null);
			} else {
				this.txtCompany.setUserObject(ids);
			}
		}
	}
	
	public void setCustomerParams(CustomerParams  cp) {
	
		if(cp == null) return;
		
		FDCCustomerParams para = new FDCCustomerParams(cp);
		try {
			initCompanyDlg(para.getStringArray("companyIds"));
			setCompanyByTree(companySelectDlg.getUserObject());
			initProjectDlg(para.getStringArray("projectIds"));
			setProjectByTree(projectSelectDlg.getUserObject());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!FDCHelper.isEmpty(para.getStringArray("contractIds"))){//.getContractIds())) {
			ContractBillCollection contractBills = new ContractBillCollection();
			ContractWithoutTextCollection contractBillWithoutTexts = new ContractWithoutTextCollection();
			try {
				contractBills = ContractBillFactory.getRemoteInstance()
						.getContractBillCollection(
								FDCHelper.getIncludeEntityView("id", para
										.getStringArray("contractIds")));//.getContractIds()));
				contractBillWithoutTexts = ContractWithoutTextFactory
						.getRemoteInstance().getContractWithoutTextCollection(
								FDCHelper.getIncludeEntityView("id", para
										.getStringArray("contractIds")));//.getContractIds()));
			} catch (BOSException e) {
				e.printStackTrace();
			}
			int size=contractBills.size()+ contractBillWithoutTexts.size();
			Object [] objects=new Object[size];
			for (int i = 0; i < contractBills.size(); i++) {
				objects[i]=contractBills.get(i);
			}
			for (int j = contractBills.size(); j < size; j++) {
				objects[j]=contractBillWithoutTexts.get(j-contractBills.size());
			}
			this.f7Contract.setValue(objects);
		}
		
		if(para.getInt("conType") == 0) this.radioContract.setSelected(true);
		else if(para.getInt("conType") == 1) this.radioConWithoutText.setSelected(true);
		else if(para.getInt("conType") == 2) this.radioConAll.setSelected(true);
		super.setCustomerParams(para.getCustomerParams());
	}
	
	private void setProjectByTree(Object object) {
	List projectIdList = new ArrayList();
	if (object != null) {
		List projectList = (List) object;
		String text = "";
		for (int i = 0; i < projectList.size(); i++) {
			if (projectList.get(i) instanceof ProjectInfo) {
				ProjectInfo project = (ProjectInfo) projectList.get(i);
				if (project.isIsLeaf()) {
					if (!text.equals("")) {
						text += ",";
					}
					text += project.getName();
				}
				projectIdList.add(project.getId().toString());
			}
		}
		this.txtProject.setText(text);
		Object[] ids = projectIdList.toArray(new String[] {});
		if (FDCHelper.isEmpty(ids)) {
			this.txtProject.setUserObject(null);
		} else {
			this.txtProject.setUserObject(ids);
		}
	}
	}
	
	/**
	* output storeFields method
	*/
	public void storeFields() {
		super.storeFields();
	}

	private void popNode(java.util.List list, DefaultKingdeeTreeNode root) {
		for (Enumeration c = root.children(); c.hasMoreElements();) {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) c
					.nextElement();
			if(node.isLeaf()) {
				list.add(node.getUserObject());
			}
			popNode(list, node);
		}
	}
}