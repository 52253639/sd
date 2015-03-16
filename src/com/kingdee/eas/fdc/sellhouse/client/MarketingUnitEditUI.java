/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.permission.UserType;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberInfo;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitSellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitSellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.DeletedStatusEnum;
import com.kingdee.eas.framework.EffectedStatusEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MarketingUnitEditUI extends AbstractMarketingUnitEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketingUnitEditUI.class);
    //--------------------------------销售项目列名----------------------------------------
    private static final String SP_NUMBER = "number";
    private static final String SP_NAME = "sellProject";
    //--------------------------------成员信息列名----------------------------------------
    private static final String MER_ACCOUNT = "memberAccount";//成员账号number
    private static final String MER_NAME = "member";
    private static final String MER_ACCDATE = "accessionDate";
    private static final String MER_DIMDATE = "dimissionDate";
    private static final String MER_DUTYMAN = "isDutyMan";
    private  FullOrgUnitInfo fullorgInfo = null; 
    private MarketingUnitInfo muInfo = null;
    public MarketingUnitEditUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
	   super.onLoad();
	   MarketingUnitInfo parentMuInfo1 = this.editData.getParent();
		if(this.getOprtState().equals(OprtState.ADDNEW))
			parentMuInfo1 = (MarketingUnitInfo)this.getUIContext().get("MarketingUnitInfo");
		if(parentMuInfo1!=null) {
			this.editData.setParent(parentMuInfo1);
			this.prmtParentUnit.setValue(parentMuInfo1);
		}
	   initTblSellroject();
	   initTblMember();
	   initBtnStatus();
	   SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
		
	   this.actionRemove.setVisible(false);
	}
    
    public void onShow() throws Exception {
    	super.onShow();
    	
    	if(!OprtState.ADDNEW.equals(this.getOprtState())){
    		FullOrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
    		if(this.prmtOrgUnit.getValue()!=null){
    			FullOrgUnitInfo org = (FullOrgUnitInfo)this.prmtOrgUnit.getValue();
    			if(!org.getId().toString().equals(orgUnit.getId().toString())){
    				this.actionAddNew.setEnabled(false);
    			}
    		}
    	}
	
    }
    
	private void initTblMember() {
		this.tblMember.checkParsed();
		this.tblMember.getColumn(MER_ACCOUNT).getStyleAttributes().setLocked(true);
		this.tblMember.getColumn(MER_NAME).getStyleAttributes().setLocked(true);		
		String formatString = "yyyy-MM-dd";
		this.tblMember.getColumn(MER_DIMDATE).getStyleAttributes()
				.setNumberFormat(formatString);
		this.tblMember.getColumn(MER_ACCDATE).getStyleAttributes()
		.setNumberFormat(formatString);
		this.tblMember.getColumn(MER_DIMDATE).setEditor(CommerceHelper.getKDDatePickerEditor());
		this.tblMember.getColumn(MER_ACCDATE).setEditor(CommerceHelper.getKDDatePickerEditor());
		this.tblMember.getColumn(MER_ACCDATE).getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
	}
	
	private void initTblSellroject(){
		this.tblSellroject.checkParsed();
		this.tblSellroject.getStyleAttributes().setLocked(true);
		this.tblSellroject.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		this.tblSellroject.getColumn(SP_NUMBER).getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		this.tblSellroject.getColumn(SP_NAME).getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
	}

	protected IObjectValue createNewData() {
		MarketingUnitInfo newMuInfo = new MarketingUnitInfo();
		FullOrgUnitInfo orgStrInfo =(FullOrgUnitInfo)this.getUIContext().get("OrgUnitInfo"); 
		if(orgStrInfo!=null) {
			newMuInfo.setOrgUnit(orgStrInfo);
		}else {//查看以后点新增
			newMuInfo.setOrgUnit(fullorgInfo);
		}
		newMuInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		newMuInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		MarketingUnitInfo parentMuInfo = (MarketingUnitInfo)this.getUIContext().get("MarketingUnitInfo");
		if(parentMuInfo!=null)newMuInfo.setParent(parentMuInfo); else newMuInfo.setParent(muInfo);
		if(parentMuInfo!=null && orgStrInfo==null) {
			newMuInfo.setOrgUnit(parentMuInfo.getOrgUnit());
		}
		if(parentMuInfo!=null){
			newMuInfo.setIsEnabled(parentMuInfo.isIsEnabled());
		} else {
			newMuInfo.setIsEnabled(true);
		}
		newMuInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		return newMuInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return MarketingUnitFactory.getRemoteInstance();
	}
	
	protected void btnAddProject_actionPerformed(ActionEvent e)	throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		FilterInfo orgfilter = new FilterInfo();
		String longNumber = this.editData.getOrgUnit().getLongNumber();
		orgfilter.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber",longNumber+"%",CompareType.LIKE));
		FilterInfo shareFilter = new FilterInfo();  //项目共享给其他组织的 
		shareFilter.getFilterItems().add(new FilterItemInfo("orgUnitShareList.orgUnit.longNumber",longNumber+"%",CompareType.LIKE));		
		orgfilter.mergeFilter(shareFilter, "OR");
		filter.mergeFilter(orgfilter, "AND");
		if(this.editData.getParent()!=null){
			String idSqlStr = "select FSellProjectID from T_SHE_MarketingUnitSellProject where FheadId = '"+this.editData.getParent().getId().toString()+"'";
			FilterInfo parentFilter = new FilterInfo();
			parentFilter.getFilterItems().add(new FilterItemInfo("id",idSqlStr,CompareType.INNER));
			filter.mergeFilter(parentFilter, "AND");
		} 
		//项目现在为有级次的，这里只能选择1级项目
		FilterInfo pfilter = new FilterInfo();
		pfilter.getFilterItems().add(new FilterItemInfo("parent.id",null));
		pfilter.getFilterItems().add(new FilterItemInfo("isForSHE",Boolean.TRUE));
		filter.mergeFilter(pfilter, "AND");
		view.setFilter(filter);	
		
		KDCommonPromptDialog dlg = CommerceHelper.getANewCommonDialog(this,"com.kingdee.eas.fdc.sellhouse.app.CRMSellProjectQuery", true, view);		
		dlg.show();
		Object[] objects = (Object[])dlg.getData();
		if(objects!=null) {
			Set sellProIdSet = new HashSet();
			MarketingUnitSellProjectCollection  muSellProColl = this.editData.getSellProject();
			for(int i=0;i<muSellProColl.size();i++) {
				sellProIdSet.add(muSellProColl.get(i).getSellProject().getId().toString());
			}
			for(int i=0;i<objects.length;i++) {
				SellProjectInfo sellProInfo = (SellProjectInfo)objects[i];
				if(!sellProIdSet.contains(sellProInfo.getId().toString())) {
					MarketingUnitSellProjectInfo muSellProInfo = new MarketingUnitSellProjectInfo();
					muSellProInfo.setHead(this.editData);
					muSellProInfo.setSellProject(sellProInfo);
					this.editData.getSellProject().add(muSellProInfo);
					IRow addRow = this.tblSellroject.addRow();
					addRow.setUserObject(muSellProInfo);
					addRow.getCell("sellProject").setValue(sellProInfo.getName());
					addRow.getCell("number").setValue(sellProInfo.getNumber());
				}
			}
		}
	}
	
	protected void btnDelSellProject_actionPerformed(ActionEvent e) throws Exception {
		int[] rows = KDTableUtil.getSelectedRows(this.tblSellroject);
		Set muSellProject = new HashSet();
		for(int i = 0 ;i <rows.length;i++){
			IRow row = this.tblSellroject.getRow(rows[i]);
			if(((MarketingUnitSellProjectInfo)row.getUserObject()).getId()!=null){//还没ID的就随便删，不做验证！
				muSellProject.add(((MarketingUnitSellProjectInfo)row.getUserObject()).getSellProject().getId().toString());
			}
		}
		
		if(OprtState.EDIT.equals(this.oprtState)){
			//看查是否被下级引用
			Set result = null;
			if(muSellProject.size()>0){
				result = MarketingUnitFactory.getRemoteInstance().checkRefByNext(muSellProject, this.editData);	
			}
			StringBuffer reultString = null;
			if(result != null && result.size()>0){
				reultString = new StringBuffer();
				int i = 0;
				for(Iterator it =result.iterator();it.hasNext();){
					MarketingUnitSellProjectInfo muspInfo = (MarketingUnitSellProjectInfo)it.next();
					if(i>0){
						reultString =reultString.append(",");
					}
					reultString.append(muspInfo.getSellProject().getName());
					i++;
				}
				FDCMsgBox.showWarning("项目："+reultString.toString()+" 已经被下级营销团队引用不能删除");
				this.abort();
			}
		}
		for(int i = 0 ;i <rows.length;i++){
			IRow row = this.tblSellroject.getRow(rows[i]);
			this.tblSellroject.removeRow(rows[i]);
			this.editData.getSellProject().remove((MarketingUnitSellProjectInfo)row.getUserObject());
		}
	}
	
	protected void btnAddMember_actionPerformed(ActionEvent e) throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("type", new Integer(UserType.PERSON_VALUE),CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("type",new Integer(UserType.OTHER_VALUE),CompareType.EQUALS) );
		filter.getFilterItems().add(new FilterItemInfo("isDelete", Boolean.TRUE,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("isLocked", Boolean.TRUE,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("isForbidden", Boolean.TRUE,CompareType.NOTEQUALS));
		
		if(this.tblMember.getRowCount()>0){
			Set id=new HashSet();
			for(int i=0;i<this.tblMember.getRowCount();i++){
				IRow row = this.tblMember.getRow(i);
				id.add(((MarketingUnitMemberInfo)row.getUserObject()).getMember().getId().toString());
			}
			filter.getFilterItems().add(new FilterItemInfo("id", id,CompareType.NOTINCLUDE));
			filter.setMaskString("(#0 or #1) and #2 and #3 and #4 and #5");
		}else{
			filter.setMaskString("(#0 or #1) and #2 and #3 and #4");
		}
		view.setFilter(filter);		
		KDCommonPromptDialog dlg = CommerceHelper.getANewCommonDialog(this,"com.kingdee.eas.fdc.sellhouse.app.F7UserQuery", true, view);
		dlg.show();
		
		Object[] objects = (Object[])dlg.getData();
		if(objects!=null) {
			Date accessionDate = FDCSQLFacadeFactory.getRemoteInstance()
			.getServerTime();
			for(int i=0;i<objects.length;i++) {
				UserInfo userInfo = (UserInfo)objects[i];
				MarketingUnitMemberInfo muMemberInfo = new MarketingUnitMemberInfo();
				muMemberInfo.setHead(this.editData);
				muMemberInfo.setMember(userInfo);
				muMemberInfo.setIsDuty(false);
				muMemberInfo.setIsOperation(false);
				muMemberInfo.setIsUpdateMember(false);
				muMemberInfo.setIsSharePercent(false);
				muMemberInfo.setTakePercentage(new BigDecimal(0.00));
				if (accessionDate != null) {
					muMemberInfo.setAccessionDate(FDCDateHelper
							.stringToDate(FDCDateHelper
									.formatDate2(accessionDate)));
				}
				IRow Row = this.tblMember.addRow();
				Row.setUserObject(muMemberInfo);
				Row.getCell(MER_ACCOUNT).setValue(userInfo.getNumber());
				Row.getCell(MER_NAME).setValue(userInfo.getName());
				Row.getCell(MER_ACCDATE).setValue(muMemberInfo.getAccessionDate());
				Row.getCell(MER_DIMDATE).setValue(muMemberInfo.getDimissionDate());
				Row.getCell(MER_DUTYMAN).setValue(Boolean.FALSE);
				this.editData.getMember().add(muMemberInfo);				
			}
		}
		
	}
	
	protected void btnDelMember_actionPerformed(ActionEvent e) throws Exception {
		if ((tblMember.getSelectManager().size() == 0)){
			MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
			return;
		}
		if (confirmRemove()) {
			KDTSelectManager selectManager = tblMember.getSelectManager();
			int size = selectManager.size();
			KDTSelectBlock selectBlock = null;
			Set indexSet = new HashSet();

			for (int blockIndex = 0; blockIndex < size; blockIndex++) {
				selectBlock = selectManager.get(blockIndex);
				int top = selectBlock.getBeginRow();
				int bottom = selectBlock.getEndRow();
				if (tblMember.getRow(top) == null) {
					MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
					return;
				}
				for (int i = top; i <= bottom; i++) {
					indexSet.add(new Integer(i));
				}
			}
			Integer[] indexArr = new Integer[indexSet.size()];
			Object[] indexObj = indexSet.toArray();
			System.arraycopy(indexObj, 0, indexArr, 0, indexArr.length);
			Arrays.sort(indexArr);
			if (indexArr == null){
				return;
			}
			for (int i = indexArr.length - 1; i >= 0; i--) {
				int rowIndex = Integer.parseInt(String.valueOf(indexArr[i]));
				IRow delRow =tblMember.getRow(rowIndex);
				this.editData.getMember().remove((MarketingUnitMemberInfo)delRow.getUserObject());
				tblMember.removeRow(rowIndex);
			}
			if (tblMember.getRow(0) != null){
				tblMember.getSelectManager().select(0, 0);
			}
		}
	}
	
	public void loadFields() {
		super.loadFields();
		this.prmtOrgUnit.setValue(this.editData.getOrgUnit());
		loadSellProject();
		loadMemebers();
	}
	
	private void loadMemebers() {
		this.tblMember.checkParsed();
		this.tblMember.removeRows();
		MarketingUnitMemberCollection memberColl = this.editData.getMember();
		for(int i = 0; i<memberColl.size();i++){
			IRow row = this.tblMember.addRow();
			MarketingUnitMemberInfo member = memberColl.get(i);
			row.setUserObject(member);
			row.getCell(MER_ACCOUNT).setValue(member.getMember()!=null?member.getMember().getNumber():null);
			row.getCell(MER_NAME).setValue(member.getMember()!=null?member.getMember().getName():null);
			row.getCell(MER_ACCDATE).setValue(member.getAccessionDate());
			row.getCell(MER_DIMDATE).setValue(member.getDimissionDate());
			row.getCell(MER_DUTYMAN).setValue(Boolean.valueOf(member.isIsDuty()));
		}
		
	}

	private void loadSellProject() {
		this.tblSellroject.checkParsed();
		this.tblSellroject.removeRows();
		MarketingUnitSellProjectCollection sellProjectColl =  this.editData.getSellProject();
		for(int i = 0; i<sellProjectColl.size();i++){
			IRow row = this.tblSellroject.addRow();
			MarketingUnitSellProjectInfo MusellProject = sellProjectColl.get(i);
			row.setUserObject(MusellProject);
			row.getCell(SP_NUMBER).setValue(MusellProject.getSellProject()!=null?MusellProject.getSellProject().getNumber():null);
			row.getCell(SP_NAME).setValue(MusellProject.getSellProject()!=null?MusellProject.getSellProject().getName():null);
		}
	}

	public void storeFields() {
		storeSellProject();
		storeMemebers();
		super.storeFields();
	}

	private void storeMemebers() {
		//MarketingUnitMemberCollection memberColl = this.editData.getMember();
		//memberColl.clear();
		this.editData.getMember().clear();
		for(int i = 0 ; i<this.tblMember.getRowCount();i++){
			IRow row = this.tblMember.getRow(i);
			MarketingUnitMemberInfo member = (MarketingUnitMemberInfo)row.getUserObject();
			member.setAccessionDate((Date)row.getCell(MER_ACCDATE).getValue());
			member.setDimissionDate((Date)row.getCell(MER_DIMDATE).getValue());
			member.setIsDuty(((Boolean)row.getCell(MER_DUTYMAN).getValue()).booleanValue());
			this.editData.getMember().add(member);
		}
	}

	private MarketingUnitMemberCollection getMemberCollection(MarketingUnitMemberCollection memberColl) {
		for(int i = 0 ; i<this.tblMember.getRowCount();i++){
			IRow row = this.tblMember.getRow(i);
			MarketingUnitMemberInfo member = (MarketingUnitMemberInfo)row.getUserObject();
			member.setAccessionDate((Date)row.getCell(MER_ACCDATE).getValue());
			member.setDimissionDate((Date)row.getCell(MER_DIMDATE).getValue());
			member.setIsDuty(((Boolean)row.getCell(MER_DUTYMAN).getValue()).booleanValue());
			memberColl.add((MarketingUnitMemberInfo)row.getUserObject());
		}

		return memberColl;
	}

	private void storeSellProject() {
		MarketingUnitSellProjectCollection sellProjectColl = this.editData.getSellProject();
		sellProjectColl.clear();
		for(int i = 0 ; i<this.tblSellroject.getRowCount();i++){
			IRow row = this.tblSellroject.getRow(i);
			sellProjectColl.add((MarketingUnitSellProjectInfo)row.getUserObject());
		}
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic =  super.getSelectors();
		sic.add(new SelectorItemInfo("member.member.id"));
		sic.add(new SelectorItemInfo("member.member.number"));
	    sic.add(new SelectorItemInfo("member.member.name"));
	    sic.add(new SelectorItemInfo("member.accessionDate"));
	    sic.add(new SelectorItemInfo("member.dimissionDate"));
	    sic.add(new SelectorItemInfo("member.isDuty"));
	    sic.add(new SelectorItemInfo("sellProject.sellProject.*"));
	    sic.add(new SelectorItemInfo("isEnabled"));
	    sic.add(new SelectorItemInfo("CU.*"));
	    return sic;
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		if(this.txtNumber.getText()==null || this.txtNumber.getText().length()==0) {
			FDCMsgBox.showInfo("编码必须录入！");
			this.abort();
		}
		if(this.txtName.getText()==null || this.txtName.getText().length()==0) {
			FDCMsgBox.showInfo("名称必须录入！");
			this.abort();
		}
		verifySellProject();
		verifyMember();
	}

	private void verifySellProject() {
		if(this.tblSellroject.getRowCount()==0) {
			MsgBox.showInfo("项目信息必须录入！");
			this.abort();
		}
		
	}

	private void verifyMember() throws BOSException, EASBizException {
		if(this.tblMember.getRowCount()==0) {
			MsgBox.showInfo("成员信息必须录入！");
			this.abort();
		}
		MarketingUnitMemberCollection memberColl = new MarketingUnitMemberCollection();
		memberColl = getMemberCollection(memberColl);
		//效验是否选择重复的用户
		Map tempMap = new HashMap();
		boolean flag = false;
		for(int i = 0 ; i < memberColl.size(); i++){
			MarketingUnitMemberInfo  mu = memberColl.get(i);
			if(mu.isIsDuty())flag = mu.isIsDuty();
			String muInfo = mu.getMember().getId().toString();
			if(!tempMap.containsKey(muInfo)){
				tempMap.put(muInfo, new Integer(i));
			}else{
				FDCMsgBox.showWarning("同一营销单元下不能存在同一成员！");
				this.abort();
			}
		}
		//效验在同一售楼组织内，不允许管理相同项目的营销团队成员重复,同时也不能是该组织的管控人员
		if(this.editData.isIsEnabled())
			MarketingUnitFactory.getRemoteInstance().checkMemeberOfSameSellPorject(this.editData);
		
		//效验上岗日期
		for(int i = 0 ; i < memberColl.size(); i++){
			MarketingUnitMemberInfo  mu = memberColl.get(i);
			if(mu.getAccessionDate()==null){
				FDCMsgBox.showWarning("成员:"+mu.getMember().getName()+"的上岗日期请填写！");
				this.abort();
			}
		}
		if(!flag){//没有负责人
			int result = FDCMsgBox.showConfirm2("没有指定负责人，是否保存？");
			if(2==result){
				this.abort();
			}
		}
		
	}
	protected void tblSellroject_editStopped(KDTEditEvent e) throws Exception {
		
	}
	
	protected void tblMember_editStopped(KDTEditEvent e) throws Exception {
		super.tblMember_editStopped(e);
	}
	
	protected void btnCancel_actionPerformed(ActionEvent e) throws Exception {
		super.btnCancel_actionPerformed(e);
	}
	
	private void initBtnStatus(){
        if (STATUS_EDIT.equals(getOprtState()) && (editData.get("effectedStatus") != null) && (editData.getInt("effectedStatus") == EffectedStatusEnum.EFFECTED_VALUE)) {
            //对于已生效数据，不能再进行暂存操作
            actionSave.setEnabled(false);
            actionSubmit.setEnabled(true);
            actionRemove.setEnabled(true);
        }
        else if(STATUS_EDIT.equals(getOprtState()))
        {
            actionEdit.setEnabled(false);
            if(actionSave.isVisible())
            {
                actionSave.setEnabled(true);
            }
            actionSubmit.setEnabled(true);
            if(actionCopy.isVisible())
            {
                actionCopy.setEnabled(true);
            }
            actionRemove.setEnabled(true);
            if(((MarketingUnitInfo)this.editData).isIsEnabled()){
        		 actionCancel.setEnabled(true);
        		 actionCancelCancel.setEnabled(false);
        		 actionRemove.setEnabled(false);
        	}else{
        		actionCancel.setEnabled(false);
        		actionCancelCancel.setEnabled(true);
        		 actionRemove.setEnabled(true);
        	}
        }
        else if (!STATUS_VIEW.equals(getOprtState()))
        {
            if(actionSave.isVisible())
            {
                actionSave.setEnabled(true);
            }
            actionSubmit.setEnabled(true);
            actionEdit.setEnabled(false);
        }

        if (!STATUS_VIEW.equals(getOprtState()) && (editData.get("deletedStatus") != null) && (editData.getInt("deletedStatus") == DeletedStatusEnum.NORMAL_VALUE)) {
            actionCancel.setEnabled(true);
            actionCancelCancel.setEnabled(false);
        } else if (!STATUS_VIEW.equals(getOprtState()) && (editData.get("deletedStatus") != null) && (editData.getInt("deletedStatus") == DeletedStatusEnum.DELETED_VALUE)) {
            actionCancel.setEnabled(false);
            actionCancelCancel.setEnabled(true);
        } else if (!STATUS_VIEW.equals(getOprtState())) {
            actionCancel.setEnabled(false);
            actionCancelCancel.setEnabled(false);
        }
        //view 进这个
        if(STATUS_VIEW.equals(getOprtState()))
        {
        	if(((MarketingUnitInfo)this.editData).isIsEnabled()){
        		this.actionCancel.setEnabled(true);
        		 actionCancelCancel.setEnabled(false);
        		 actionRemove.setEnabled(false);
        		 actionEdit.setEnabled(false);
        	}else{
        		this.actionCancel.setEnabled(false);
        		actionCancelCancel.setEnabled(true);
        		actionRemove.setEnabled(true);
        		actionEdit.setEnabled(true);
        	}
        	lockUIForViewStatus();
        }
        actionAttachment.setVisible(isShowAttachmentAction()); 
        
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if(STATUS_VIEW.equals(getOprtState()))
        {
			this.btnAddProject.setEnabled(false);
    		this.btnAddMember.setEnabled(false);
    		this.btnDelMember.setEnabled(false);
    		this.btnDelSellProject.setEnabled(false);
        }else {
    		this.btnAddProject.setEnabled(true);
    		this.btnAddMember.setEnabled(true);
    		this.btnDelMember.setEnabled(true);
    		this.btnDelSellProject.setEnabled(true);
        }
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		fullorgInfo = this.editData.getOrgUnit();
		muInfo = this.editData;
		if(MarketingUnitFactory.getRemoteInstance().exists("where parent.id = '"+this.editData.getId().toString()+"'")) {
			MsgBox.showInfo("非明细营销单元节点不能删除！");
			return;
		}
		if(this.editData.isIsEnabled()){
			MsgBox.showInfo("处于启用状态的营销单元节点不能直接删除！");
			return;
		}
		super.actionRemove_actionPerformed(e);
	}
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		fullorgInfo = this.editData.getOrgUnit();
		muInfo = this.editData;
		super.actionAddNew_actionPerformed(e);
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
	}
	
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData==null){
			return;
		}
		Boolean flag = (Boolean)this.getUIContext().get("isCanCancel");
		Map resutMap = (Map)this.getUIContext().get("cancelMap");
		if(flag!=null&&!flag.booleanValue()){//不能使用禁用 要给提示
			int result = FDCMsgBox.showConfirm2("操作禁用的营销团队有下级团队，禁用后下级营销团队也会被禁用，是否继续？");
			if(result ==2){
				this.abort();
			}else{
/*				String param = FDCTreeHelper.getStringFromSet(resutMap.keySet());
				FDCSQLBuilder build = new FDCSQLBuilder();
				String sql = "update t_she_marketingunit set fisEnabled = 0 where fid in ("+param +")";
				build.appendSql(sql);
				build.executeUpdate();*/
				
				Iterator keySet = resutMap.keySet().iterator(); 
				while(keySet.hasNext()){
					String idStr = (String)keySet.next();
					MarketingUnitInfo info = new MarketingUnitInfo();
					info.setId(BOSUuid.read(idStr));
					info.setIsEnabled(false);
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add(new SelectorItemInfo("isEnabled"));
					getBizInterface().updatePartial(info, sic);
				}
			}
		} else {
			MarketingUnitInfo info = new MarketingUnitInfo();
			info.setId(BOSUuid.read(this.editData.getId().toString()));
			info.setIsEnabled(false);
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("isEnabled"));
			getBizInterface().updatePartial(info, sic);
		}
		MsgBox.showInfo(this,EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
	    setDataObject(getValue(new ObjectUuidPK(this.editData.getId())));
	    loadFields();
	    initOldData(this.editData);
	    setSave(true);
	    setSaved(true);
	    this.actionCancelCancel.setEnabled(true);
	    this.actionCancel.setEnabled(false);
	    this.actionEdit.setEnabled(true);
	    initBtnStatus();
     }
	
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData==null){
			return;
		}
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("parent.*");
		MarketingUnitInfo muInfo = MarketingUnitFactory.getRemoteInstance().getMarketingUnitInfo(new ObjectUuidPK(this.editData.getId()),sel);
		if(muInfo.getParent()!=null){
			if(!muInfo.getParent().isIsEnabled()){
				FDCMsgBox.showWarning("营销团队的上级没有启用，下级不能启用！");
				this.abort();
			}
		}
		
		//效验在同一售楼组织内，不允许管理相同项目的营销团队成员重复,同时也不能是该组织的管控人员
		MarketingUnitFactory.getRemoteInstance().checkMemeberOfSameSellPorject(this.editData);		
		
		MarketingUnitInfo info = new MarketingUnitInfo();
		info.setId(BOSUuid.read(this.editData.getId().toString()));
		info.setIsEnabled(true);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("isEnabled"));
		getBizInterface().updatePartial(info, sic);
		MsgBox.showInfo(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
	    setDataObject(getValue(new ObjectUuidPK(this.editData.getId())));
	    loadFields();
	    initOldData(this.editData);
	    setSave(true);
	    setSaved(true);
	    this.actionCancel.setEnabled(true);
	    this.actionCancelCancel.setEnabled(false);
	    this.actionEdit.setEnabled(false);
	    initBtnStatus();
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		SHEManageHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
	}
	
}