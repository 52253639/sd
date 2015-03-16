/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OUException;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProjectInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.client.TreeSelectDialog;
import com.kingdee.eas.fdc.invite.IInviteType;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.framework.client.ListUI;


public class InquiryFileFilterUI extends AbstractInquiryFileFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(InquiryFileFilterUI.class);
    
    private static final String PROJECT_IDS = "projectIds";

	private static final String COMPANY_IDS = "companyIds";
	
	private static final String INVITETYPE_ID = "inviteTypeId";
	
	private static final String NAME = "name";
	
	private static final String CREATETIME_FROM = "createTime_From";
	
	private static final String CREATETIME_TO = "createTime_To";
	
	private static final String CONTRACT_STATE = "contractState";
	
    protected ListUI listUI;
    protected ItemAction actionListOnLoad;
    private TreeSelectDialog companySelectDlg;
    private TreeSelectDialog projectSelectDlg;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Org;
    
    private boolean isLoaded;
    
    public InquiryFileFilterUI() throws Exception
    {
        super();
    }

   
	public InquiryFileFilterUI(ListUI listUI, ItemAction actionListOnLoad) throws Exception {
		
		super();
		this.listUI = listUI;
		this.actionListOnLoad = actionListOnLoad;
	}
    
	
	public void initOrgF7() throws OUException, Exception{
		this.f7Org = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
		this.f7Org.setName("f7Org");
		f7Org.setDisplayFormat("$name$");
		f7Org.setCommitFormat("$name$");
		f7Org.setEditFormat("$name$");
		f7Org.setRequired(true);
		f7Org.setEnabledMultiSelection(true);
		
		CompanyTreeSelectF7Util orgF7SelectUI = new CompanyTreeSelectF7Util(this);
		orgF7SelectUI.setTreeModel(getOrgTreeModel());
		//设置格式，去掉多选择后，留下的中括号
//		List v = new ArrayList();
//    	FullOrgUnitInfo compInfo = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
//		if (compInfo != null) {
//			v.add(compInfo);
//		}
//		orgF7SelectUI.setF7OrgValue(v);
		f7Org.setSelector(orgF7SelectUI); 
	}
	
	public void onLoad() throws Exception {
		
		initOrgF7();
		super.onLoad();
		CtrlUnitInfo cuInfo = SysContext.getSysContext().getCurrentCtrlUnit();
		if (cuInfo == null) {
			throw new OUException(OUException.CU_CAN_NOT_NULL);
		}
		if (!isLoaded) {
			this.clear();
		}
		isLoaded = true;
	}
	
    public void storeFields()
    {
        super.storeFields();
    }
    
    protected void btnCompanySelect_actionPerformed(ActionEvent e) throws Exception {
    	
//		if (this.companySelectDlg == null) {
//			this.initCompanyDlg(null);
//		}
		f7Org.setDataBySelector();
		Object object = f7Org.getValue();
//		Object object = companySelectDlg.showDialog();
		setCompanyByTree(object);
//		super.btnCompanySelect_actionPerformed(e);
	}
    
    protected void btnProjectSelect_actionPerformed(ActionEvent e) throws Exception {
		if (this.projectSelectDlg == null) {
			this.initProjectDlg(null);
		}
		Object object = projectSelectDlg.showDialog();
		setProjectByTree(object);
		super.btnProjectSelect_actionPerformed(e);
	}

    private void initProjectDlg(String[] projectIds) throws Exception {
		ProjectTreeBuilder builder = new ProjectTreeBuilder(true);
		KDTree tree = new KDTree();
		if (this.companySelectDlg != null && this.companySelectDlg.getSelectTree() != null) {
			builder.buildByCostOrgTree(tree, this.companySelectDlg.getSelectTree());
		} else {
			builder.build(this.listUI, tree, this.actionListOnLoad);
		}
		TreeModel model = tree.getModel();
		FDCHelper.setTreeForbidNode((DefaultKingdeeTreeNode) model.getRoot(), "isIsEnabled", Boolean.FALSE);
		projectSelectDlg = new TreeSelectDialog(this, model, "getId,toString", projectIds);
	}
    
    private void initInviteType(String inviteTypeId) throws Exception{
    	IInviteType inviteTypeInstance = InviteTypeFactory.getRemoteInstance();
    	IObjectPK pk = new ObjectUuidPK(inviteTypeId);
    	if(inviteTypeInstance.exists(pk)){
    		InviteTypeInfo inviteType = inviteTypeInstance.getInviteTypeInfo(pk);
    		prmtInviteType.setData(inviteType);
    	}
    }
    
	private void initCompanyDlg(String[] selectCompayIds) throws OUException, Exception {
		CtrlUnitInfo cuInfo = SysContext.getSysContext().getCurrentCtrlUnit();
		if (cuInfo == null) {
			throw new OUException(OUException.CU_CAN_NOT_NULL);
		}
		
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY,
				"", SysContext.getSysContext().getCurrentFIUnit().getId().toString(), null, FDCHelper.getActionPK(this.actionListOnLoad));
		
		this.companySelectDlg = new TreeSelectDialog(this, orgTreeModel, "getUnit,getId,toString", selectCompayIds);
	}
	
	
	private TreeModel getOrgTreeModel() throws OUException, Exception  {
		
		CtrlUnitInfo cuInfo = SysContext.getSysContext().getCurrentCtrlUnit();
		if (cuInfo == null) {
			throw new OUException(OUException.CU_CAN_NOT_NULL);
		}
		
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY,
				"", SysContext.getSysContext().getCurrentFIUnit().getId().toString(), null, FDCHelper.getActionPK(this.actionListOnLoad));
		
		return orgTreeModel;
	}
	
	private void setCompanyByTree(Object object) {
		List companyIdList = new ArrayList();
		if (object != null) {
			List companyList = (List) object;
			String text = "";
			for (int i = 0; i < companyList.size(); i++) {
				OrgStructureInfo company = (OrgStructureInfo) companyList.get(i);
				companyIdList.add(company.getUnit().getId().toString());
				if (company.getUnit().isIsCompanyOrgUnit()) {
						if (!text.equals("")) {
							text += ",";
						}
						text += company.getUnit().getName();
				}
			}
			Object[] ids = companyIdList.toArray(new String[] {});
			ArrayList oldArray = new ArrayList(FDCHelper.getSetByArray((String[]) ids));
			ArrayList newArray = new ArrayList(FDCHelper.getSetByArray((String[]) this.txtCompany.getUserObject()));
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
		}else{
			this.txtCompany.setText(null);
			this.txtCompany.setUserObject(null);
			this.f7Org.setData(null);
		}
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
	
	
	
	public void setCustomerParams(CustomerParams cp) {

		if (cp == null)
			return;
		FDCCustomerParams para = new FDCCustomerParams(cp);
		try {
			//成本中心
			initCompanyDlg(para.getStringArray(COMPANY_IDS));
			setCompanyByTree(companySelectDlg.getUserObject());
			//工程项目
			initProjectDlg(para.getStringArray(PROJECT_IDS));
			setProjectByTree(projectSelectDlg.getUserObject());
			//采购类型
			initInviteType(para.getString(INVITETYPE_ID));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//文件标题
		txtTitleName.setText(para.getString(NAME));
		//编制时间从
		pkCreateTimeFrom.setValue(para.getDate(CREATETIME_FROM));
		//编制时间至
		pkCreateTimeTo.setValue(para.getDate(CREATETIME_TO));
		//单据状态
		if (para.getInt(CONTRACT_STATE) == 0) {
			this.radioSave.setSelected(true);
		} else if (para.getInt(CONTRACT_STATE) == 1) {
			this.radioSubmit.setSelected(true);
		} else if (para.getInt(CONTRACT_STATE) == 2) {
			this.radioAuditing.setSelected(true);
		} else if (para.getInt(CONTRACT_STATE) == 3) {
			this.radioAudited.setSelected(true);
		} else if (para.getInt(CONTRACT_STATE) == 4) {
			this.radioStateAll.setSelected(true);
		}

		super.setCustomerParams(para.getCustomerParams());
	}
	
	public void clear() {
		super.clear();
//		this.companySelectDlg = null;
		this.txtCompany.setText(null);
		this.txtCompany.setUserObject(null);
		CostCenterOrgUnitInfo currentCompany = SysContext.getSysContext().getCurrentCostUnit();
		if (currentCompany.isIsBizUnit()) {
			this.btnCompanySelect.setEnabled(false);
			this.txtCompany.setText(currentCompany.getName());
			this.txtCompany.setUserObject(new String[] { currentCompany.getId().toString() });
		}
		this.projectSelectDlg = null;
		this.txtProject.setText(null);
		this.txtProject.setUserObject(null);
		this.txtTitleName.setText(null);
		this.txtTitleName.setUserObject(null);

		this.prmtInviteType.setData(null);
		this.prmtInviteType.setData(null);
		this.pkCreateTimeFrom.setValue(new Date());
		this.pkCreateTimeTo.setValue(new Date());
		this.radioStateAll.setSelected(true);
	}
	
	public boolean verify() {
		if(this.pkCreateTimeFrom!=null && this.pkCreateTimeTo != null){
			
		}
		return super.verify();
	}
	
	
	protected void pkCreateTimeFrom_dataChanged(DataChangeEvent e)
			throws Exception {
		
		if(pkCreateTimeFrom.getValue()!=null && pkCreateTimeTo.getValue()!=null){
			Date pkFrom = (Date) pkCreateTimeFrom.getValue();
			Date pkTo = (Date) pkCreateTimeTo.getValue();
			if(pkFrom.after(pkTo)){
				pkCreateTimeTo.setValue(pkFrom);
			}
		}
		
	}
	
	
	protected void pkCreateTimeTo_dataChanged(DataChangeEvent e)
			throws Exception {
		
		if(pkCreateTimeFrom.getValue()!=null && pkCreateTimeTo.getValue()!=null){
			Date pkFrom = (Date) pkCreateTimeFrom.getValue();
			Date pkTo = (Date) pkCreateTimeTo.getValue();
			if(pkFrom.after(pkTo)){
				pkCreateTimeTo.setValue(pkFrom);
			}
		}
		
	}
	
	public FilterInfo getFilterInfo() {
		
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", null, CompareType.NOTEQUALS));
		if (para.isNotNull(COMPANY_IDS)) {
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", FDCHelper.getSetByArray(para.getStringArray(COMPANY_IDS)), CompareType.INCLUDE));
		} else {

			if (companySelectDlg == null) {
				try {
					initCompanyDlg(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			TreeModel tree = (DefaultTreeModel) companySelectDlg.getTree();
			DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) tree.getRoot();
			java.util.List list = new ArrayList();
			if (root.getUserObject() != null) {
				list.add(root.getUserObject());
			}
			popNode(list, root);
			HashSet set = new HashSet();
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				OrgStructureInfo company = (OrgStructureInfo) iter.next();
				set.add(company.getUnit().getId().toString());
			}

			if (set.size() > 0) {
				filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", set, CompareType.INCLUDE));
			}
		}
		//工程项目
		if (para.isNotNull(PROJECT_IDS)) {
			filter.getFilterItems().add(new FilterItemInfo("inviteProject.project.id", FDCHelper.getSetByArray(para.getStringArray(PROJECT_IDS)), CompareType.INCLUDE));
		} 
		
		//采购类型
		if(para.isNotNull(INVITETYPE_ID)){
			filter.getFilterItems().add(new FilterItemInfo("inviteProject.inviteType.id",para.getString(INVITETYPE_ID)));
		}
		//文件标题
		if(para.isNotNull(NAME)){
			filter.getFilterItems().add(new FilterItemInfo("name","%"+para.getString(NAME)+"%",CompareType.LIKE));
		}
		//编制日期从
		if(para.isNotNull(CREATETIME_FROM)){
			filter.getFilterItems().add(new FilterItemInfo("createTime",para.getDate(CREATETIME_FROM),CompareType.GREATER_EQUALS));
		}
		//编制日期至
		if(para.isNotNull(CREATETIME_TO)){
			filter.getFilterItems().add(new FilterItemInfo("createTime",getDayEnd(para.getDate(CREATETIME_TO)),CompareType.LESS_EQUALS));
		}
		//单据状态
		if (para.isNotNull(CONTRACT_STATE)) {
			String state = null;
			if (para.getInt(CONTRACT_STATE) == 0) {
				state = FDCBillStateEnum.SAVED_VALUE;
			} else if (para.getInt(CONTRACT_STATE) == 1) {
				state = FDCBillStateEnum.SUBMITTED_VALUE;
			} else if (para.getInt(CONTRACT_STATE) == 2) {
				state = FDCBillStateEnum.AUDITTING_VALUE;
			} else if (para.getInt(CONTRACT_STATE) == 3) {
				state = FDCBillStateEnum.AUDITTED_VALUE;
			} 
			if (state != null) {
				filter.getFilterItems().add(new FilterItemInfo("state", state));
			}
		}

		return filter;
	}

	/**
	 * 
	 * 描述：开始日期
	 * 
	 * @param da
	 * @return
	 * @author jxd 创建时间：2005-11-17
	 */
	public Date getDayEnd(Date da) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(da);

		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		java.util.Date ds = new java.util.Date(cal.getTimeInMillis());
		return ds;

	}
	
	private void popNode(List list, DefaultKingdeeTreeNode root) {
		for (Enumeration c = root.children(); c.hasMoreElements();) {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) c.nextElement();
			// 描述：非叶子结节嵌套调用，叶子节点则加入
			if (!node.isLeaf()) {
				popNode(list, node);
			}
			// 分期的上级组织也加上,老数据一些单据成本中心保存的为上级的
			list.add(node.getUserObject());
		}
	}
	
	public CustomerParams getCustomerParams() {
		FDCCustomerParams param = new FDCCustomerParams();
		
		//成本中心
		String[] companyIds = (String[]) this.txtCompany.getUserObject();
		if (!FDCHelper.isEmpty(companyIds)) {
			param.add(COMPANY_IDS, companyIds);
		}
		//工程项目
		String[] projIds = (String[]) this.txtProject.getUserObject();
		if (!FDCHelper.isEmpty(projIds)) {
			param.add(PROJECT_IDS, projIds);
		}
		//采购类型
		InviteTypeInfo inviteTypeInfo = (InviteTypeInfo) this.prmtInviteType.getData();
		if (inviteTypeInfo != null) {
			param.add(INVITETYPE_ID, inviteTypeInfo.getId().toString());
		}
		//文件标题
		if(!FDCHelper.isEmpty(txtTitleName.getText())){
			param.add(NAME, txtTitleName.getText());
		}
		//编制时间从
		param.add(CREATETIME_FROM, (Date)pkCreateTimeFrom.getValue());
		//编制时间至
		param.add(CREATETIME_TO, (Date)pkCreateTimeTo.getValue());
		//单据状态
		if (this.radioSave.isSelected()) {
			param.add(CONTRACT_STATE, 0);
		} else if (this.radioSubmit.isSelected()) {
			param.add(CONTRACT_STATE, 1);
		} else if (this.radioAuditing.isSelected()) {
			param.add(CONTRACT_STATE, 2);
		} else if (this.radioAudited.isSelected()) {
			param.add(CONTRACT_STATE, 3);
		} else if (this.radioStateAll.isSelected()) {
			param.add(CONTRACT_STATE, 4);
		} 
		
		return param.getCustomerParams();
	}

}