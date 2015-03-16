/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;

import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.tenancy.AgencyInfo;
import com.kingdee.eas.fdc.tenancy.IAgency;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AgencyEditUI extends AbstractAgencyEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AgencyEditUI.class);
    
    /**
     * output class constructor
     */
    public AgencyEditUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception
    {
    	super.onLoad();
    	handleCodingRule();
    	this.actionSave.setVisible(false);
    	this.actionCancel.setVisible(false);
    	this.actionCancelCancel.setVisible(false);
    	this.actionCopy.setVisible(false);
    	this.txtNumber.setRequired(true);
    	this.txtName.setRequired(true);
    	
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {	
        super.loadFields();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    
    public SelectorItemCollection getSelectors()
	{
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("persons.*");
		return sels;
	}

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable()
    {        
        return null;
	} 
   
    protected IObjectValue createNewDetailData(KDTable table)
    {
		
        return new com.kingdee.eas.fdc.tenancy.AgencyInfo();
    }
    protected void verifyInput(ActionEvent e) throws Exception {
    	//super.verifyInput(e);
    	verifyPhone();

    	if (this.txtNumber.isEnabled()==true&&StringUtils.isEmpty(this.txtNumber.getText())) {
    		MsgBox.showInfo("编码必须录入！");
			abort();
		 }
		if (StringUtils.isEmpty(this.txtName.getText())) {
			MsgBox.showInfo("名称必须录入！");
			abort();
		}if(this.txtDescription.getText().length()>255)
		{
			MsgBox.showInfo("描述不能超过255个字符！");
			abort();
		}if(this.kdtPersons.getRowCount()>0)
		{
			for(int i=0;i<kdtPersons.getRowCount();i++)
			{
				IRow row = kdtPersons.getRow(i);
				if(row.getCell("name").getValue()==null)
				{
					MsgBox.showInfo("机构成员名称不能为空!");
					this.abort();
				}
				String phone = (String)row.getCell("phone").getValue();
				if(!StringUtils.isEmpty(phone))
				{
					isValidInput(phone,"联系电话只能输入数字");
				}
			}
		}
    }
    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.tenancy.AgencyInfo objectValue = new com.kingdee.eas.fdc.tenancy.AgencyInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		
        return objectValue;
    }
    
    private void verifyPhone()
    {
    	String fax = this.txtfax.getText();
    	if(!StringUtils.isEmpty(fax))
    	{
    		isValidInput(fax,"传真只能输入数字");
    	}
    	String phone = this.txtphone.getText();
    	if(!StringUtils.isEmpty(phone))
    	{
    		isValidInput(phone,"电话只能输入数字,多个电话号码之间用;分隔");
    	} 	
    }
    
    private static void isValidInput(String phone,String msg) {
		String regEx="[\\d]{1,}[\\d|;|；]{0,}";
        boolean result=Pattern.compile(regEx).matcher(phone).matches();
        if(!result){
        	MsgBox.showInfo(msg);
        	SysUtil.abort();
        }
	}
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.storeFields();
		if(!getOprtState().equals(OprtState.ADDNEW)){
			actionSave.setEnabled(false);
		}else{
			actionSave.setEnabled(true);
			handleCodingRule();
		}
    }
    
    /**
	 * 处理编码规则
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 */
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {

		/*
		 * 2008-09-27编码规则的取值取错了，应当取FDCBillInfo中关联的ID
		 */
		String currentOrgId = "";//FDCClientHelper.getCurrentOrgId();
		
		
		//by tim_gao 2010-11-17有更改
			//-- 销售组织下获取成本中心为空的处理 zhicheng_jin 090319 --
			OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
			if(org == null){
				org = SysContext.getSysContext().getCurrentOrgUnit();
			}
			currentOrgId = org.getId().toString();
			
		
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
		.getRemoteInstance();
		/*
		 * 2008-09-27如果不是新增状态，直接返回
		 * 然后分别判断成本中心和当前组织是否存在编码规则
		 */
		if(!STATUS_ADDNEW.equals(this.oprtState)){
			return;
		}
		boolean isExist = true;
		if(currentOrgId.length()==0||!iCodingRuleManager.isExist(editData, currentOrgId))
		{
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if(!iCodingRuleManager.isExist(editData, currentOrgId)) {
				//EditUI提供了方法，但没有调用，在onload后调用，以覆盖抽象类loadfields里面的调用（该调用没有处理断号选择）
				isExist = false;
			}
		}
				
		if( isExist ){
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(editData, currentOrgId);
			if(isAddView) {
				getNumberByCodingRule(editData, currentOrgId);
			}
			else {
				String number = null;

				if (iCodingRuleManager.isUseIntermitNumber(editData,
						currentOrgId)) { // 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
					if (iCodingRuleManager.isUserSelect(editData, currentOrgId)) {
						// 启用了断号支持功能,同时启用了用户选择断号功能
						// KDBizPromptBox pb = new KDBizPromptBox();
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								editData, currentOrgId, null, null);
						// pb.setSelector(intermilNOF7);
						// 要判断是否存在断号,是则弹出,否则不弹//////////////////////////////////////////
						Object object = null;
						if (iCodingRuleManager
								.isDHExist(editData, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}
				}
				getNumberCtrl().setText(number);
			}

			setNumberTextEnabled();
		}
	}
	/**
	 * getNumberByCodingRule只提供了获取编码的功能，没有提供设置到控件的功能，实现此方法将根据编码规则的"是否新增显示"属性设置编码到控件
	 */
	protected void prepareNumber(IObjectValue caller, String number) {
		super.prepareNumber(caller, number);

		getNumberCtrl().setText(number);

	}
	
	
	/**
	 *
	 * 描述：返回编码控件
	 * @return
	 */
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	
	//重写EditUI的获取编码
    //通过编码规则获取编码。子类可用。
    protected void getNumberByCodingRule(IObjectValue caller, String orgId) {
        try {
            ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
            if (orgId == null || orgId.trim().length() == 0)
            {
//              当前用户所属组织不存在时，缺省实现是用集团的
                 orgId = OrgConstants.DEF_CU_ID;
            }
            if (iCodingRuleManager.isExist(caller, orgId))
            {
                String number = "";
                if (iCodingRuleManager.isUseIntermitNumber(caller, orgId))
                { // 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
                    if (iCodingRuleManager.isUserSelect(caller, orgId))
                    {
                        // 启用了断号支持功能,同时启用了用户选择断号功能
                        // KDBizPromptBox pb = new KDBizPromptBox();
                        CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
                                caller, orgId, null, null);
                        // pb.setSelector(intermilNOF7);
                        // 要判断是否存在断号,是则弹出,否则不弹//////////////////////////////////////////
                        Object object = null;
                        if (iCodingRuleManager.isDHExist(caller, orgId))
                        {
                            intermilNOF7.show();
                            object = intermilNOF7.getData();
                        }
                        if (object != null)
                        {
                            number = object.toString();
                        }
                        else
                        {
                            // 如果没有使用用户选择功能,直接getNumber用于保存,为什么不是read?是因为使用用户选择功能也是get!
                            number = iCodingRuleManager
                                    .getNumber(caller, orgId);
                        }
                    }
                    else
                    {
                        // 只启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
                        number = iCodingRuleManager.readNumber(caller, orgId);
                    }
                }
                else
                {
                	// 没有启用断号支持功能，此时获取了编码规则产生的编码
                	String costCenterId = null;
            		
//            			costCenterId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();售楼组织成本中心为空 
            			costCenterId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
            		
            		
                    number =  prepareNumberForAddnew(iCodingRuleManager,(AgencyInfo)editData, orgId,costCenterId, null); 
                    	//iCodingRuleManager.getNumber(caller, orgId);
                }

                // 把number的值赋予caller中相应的属性，并把TEXT控件的编辑状态设置好。
                prepareNumber(caller, number);
                if (iCodingRuleManager.isModifiable(caller, orgId))
                {
                    //如果启用了用户可修改
                    setNumberTextEnabled();
                }
                return;
            }

            /*
            if (orgId != null && orgId.trim().length() > 0 && iCodingRuleManager.isExist(caller, orgId)) {
                String number = "";

                if (iCodingRuleManager.isUseIntermitNumber(caller, orgId)) //此处的orgId
                                                                           // 与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
                {
                    //启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
                    number = iCodingRuleManager.readNumber(caller, orgId);
                } else {
                    //没有启用断号支持功能，此时获取了编码规则产生的编码
                    number = iCodingRuleManager.getNumber(caller, orgId);
                }

                //把number的值赋予caller中相应的属性，并把TEXT控件的编辑状态设置好。
                prepareNumber(caller, number);

                return;
            } else {
                //当前用户所属组织不存在时，缺省实现是用集团的
                String companyId = getNextCompanyId();

                if (companyId != null && companyId.trim().length() > 0 && iCodingRuleManager.isExist(caller, companyId)) {
                    String number = "";

                    if (iCodingRuleManager.isUseIntermitNumber(caller, companyId)) //此处的orgId
                                                                                   // 与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
                    {
                        //启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
                        number = iCodingRuleManager.readNumber(caller, companyId);
                    } else {
                        //没有启用断号支持功能，此时获取了编码规则产生的编码
                        number = iCodingRuleManager.getNumber(caller, companyId);
                    }

                    //把number的值赋予caller中相应的属性，并把TEXT控件的编辑状态设置好。
                    prepareNumber(caller, number);

                    return;
                }
            }
            */
        } catch (Exception err) {
            //获取编码规则出错，现可以手工输入编码！
            handleCodingRuleError(err);

            //把放编码规则的TEXT控件的设置为可编辑状态，让用户可以输入
            setNumberTextEnabled();
        }

        //把放编码规则的TEXT控件的设置为可编辑状态，让用户可以输入
        setNumberTextEnabled();
    }
    
  //编码规则中启用了“新增显示”,必须检验是否已经重复
	protected String prepareNumberForAddnew (ICodingRuleManager iCodingRuleManager,AgencyInfo info,String orgId,String costerId,String bindingProperty)throws Exception{

		String number = null;
		FilterInfo filter = null;
		int i=0;
		do {
			//如果编码重复重新取编码
			if(bindingProperty!=null){
				number = iCodingRuleManager.getNumber(editData, orgId, bindingProperty, "");
			}else{
				number = iCodingRuleManager.getNumber(editData, orgId);
			}
			
			filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("number", number));
			if (info.getId() != null) {
				filter.getFilterItems().add(
						new FilterItemInfo("id", info.getId().toString(),
								CompareType.NOTEQUALS));
			}
			i++;
		}while (((IAgency)getBizInterface()).exists(filter) && i<1000);
		
		return number;
	}
}