/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class SellHouseEndPeriodUI extends AbstractSellHouseEndPeriodUI
{
    private static final Logger logger = CoreUIObject.getLogger(SellHouseEndPeriodUI.class);
    private PeriodInfo companyCurrentPeriod = null;
	private CompanyOrgUnitInfo companyInfo = null;
	private PeriodInfo prevPeriodInfo = null;
	UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
    public SellHouseEndPeriodUI() throws Exception
    {
        super();
    }
    
    public void storeFields()
    {
        super.storeFields();
    }
    
    public void onLoad() throws Exception {
    	companyInfo = SysContext.getSysContext().getCurrentFIUnit();
    	SaleOrgUnitInfo saleOrg = SysContext.getSysContext().getCurrentSaleUnit();
		if (!companyInfo.isIsBizUnit() && !saleOrg.isIsBizUnit()) {
			MsgBox.showError(this, "当前公司为虚体，不允许使用该功能。");
			SysUtil.abort();
		}
		boolean flag = SystemStatusCtrolUtils.isStart(null,SystemEnum.FDC_SHE, companyInfo);
		if(!flag){
			MsgBox.showInfo("系统未启用，不能进行结账反结账！");
			SysUtil.abort();
		}
    	super.onLoad();
    	labPic.setIcon(EASResource.getIcon("imgGuide_pic260"));
    	radEndInit.setSelected(true);
    	companyCurrentPeriod = SystemStatusCtrolUtils
		.getCurrentPeriod(null, SystemEnum.FDC_SHE, new ObjectUuidPK(companyInfo.getId()));
    	PeriodInfo nextPeriod = PeriodUtils.getNextPeriodInfo(companyCurrentPeriod);
    	StringBuffer sb = new StringBuffer();
    	sb.append("当前期间:"+companyCurrentPeriod.getPeriodYear()+"年第"+companyCurrentPeriod.getPeriodNumber()+"期"+ "\n");
    	if(nextPeriod!=null){
    		sb.append("下一期间:"+ nextPeriod.getPeriodYear()+"年第"+ nextPeriod.getPeriodNumber()+"期"+ "\n\n");
    	}else{
    		FDCMsgBox.showWarning(this,"下一期间不能为空！");
    		SysUtil.abort();
    	}
    	kDTextArea2.setText(""+sb);
    }

    protected void radEndInit_stateChanged(ChangeEvent e) throws Exception {
    	super.radEndInit_stateChanged(e);
    	if(radEndInit.isSelected())
    	{
    		kDTextArea1.setText("在月结之前，应检查当前实体财务组织下所有已经结束初始化且启用期间在当" +
    				"前期间或之前的销售项目在财务组织的当前期间都已进行月结。");
    		if(companyCurrentPeriod!=null){
    			PeriodInfo companyCurrentPeriod = SystemStatusCtrolUtils
    			.getCurrentPeriod(null, SystemEnum.FDC_SHE, new ObjectUuidPK(companyInfo.getId()));
    			PeriodInfo nextPeriod = PeriodUtils.getNextPeriodInfo(companyCurrentPeriod);
    	    	StringBuffer sb = new StringBuffer();
    	    	sb.append("当前期间:"+companyCurrentPeriod.getPeriodYear()+"年第"+companyCurrentPeriod.getPeriodNumber()+"期"+ "\n");
    	    	sb.append("下一期间:"+ nextPeriod.getPeriodYear()+"年第"+ nextPeriod.getPeriodNumber()+"期"+ "\n\n");
    	    	kDTextArea2.setText(""+sb);
			}
    	}
    }
    
    protected void radUnInit_stateChanged(ChangeEvent e) throws Exception {
    	super.radUnInit_stateChanged(e);
    	if(radUnInit.isSelected())
    	{
    		kDTextArea1.setText("在反月结之前，若与总账进行了联用，则必须当前实体财务组" +
    				"织的总账当前期间为前一期间才可以进行反月结。");
    		if(companyCurrentPeriod!=null){
    			PeriodInfo companyCurrentPeriod = SystemStatusCtrolUtils
    			.getCurrentPeriod(null, SystemEnum.FDC_SHE, new ObjectUuidPK(companyInfo.getId()));
    			PeriodInfo nextPeriod = PeriodUtils.getNextPeriodInfo(companyCurrentPeriod);
    	    	StringBuffer sb = new StringBuffer();
    	    	sb.append("当前期间:"+companyCurrentPeriod.getPeriodYear()+"年第"+companyCurrentPeriod.getPeriodNumber()+"期"+ "\n");
    	    	sb.append("下一期间:"+ nextPeriod.getPeriodYear()+"年第"+ nextPeriod.getPeriodNumber()+"期"+ "\n\n");
    	    	kDTextArea2.setText(""+sb);
			}
    	}
    }
    
    protected void btnConfim_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnConfim_actionPerformed(e);
        if(radEndInit.isSelected())
        {
        	//校验权限:某个组织某个用户某个功能的权限是否有权限
    		if(!PermissionFactory.getRemoteInstance().hasFunctionPermission(
    				new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
    				new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()),
    				new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client.SellHouseEndPeriodUI"),
    				new MetaDataPK("ActionEndSale") )){
//    			throw new  Permission//AccountBooksException(AccountBooksException.NOSWITCH,new Object[]{scheme.getName()});
    			MsgBox.showError(this,"没有月结权限");
    			abort();
    		}
        	//如果当前组织已结束初始化的项目的当前期间小于等于公司的当前期间的话，那么说明项目
        	//还没有月结，那么不允许进行期末结账
        	//当前用户在登录组织下能看到的初始化结束的项目的集合
        	String str = CommerceHelper.getPermitProjectIdSql(userInfo);
        	EntityViewInfo view = new EntityViewInfo();
    		FilterInfo filter = new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("id",str,CompareType.INNER));
    		filter.getFilterItems().add(new FilterItemInfo("isEndInit",Boolean.TRUE));
    		view.setFilter(filter);
    		view.getSelector().add("*");
    		view.getSelector().add("saleNowPeriod.*");
    		SellProjectCollection sellProjectColl = SellProjectFactory.getRemoteInstance().getSellProjectCollection(view);
        	String projectName = new String();
        	//公司的当前期间
        	companyCurrentPeriod = SystemStatusCtrolUtils
    		.getCurrentPeriod(null, SystemEnum.FDC_SHE, new ObjectUuidPK(companyInfo.getId()));
        	for(int i=0;i<sellProjectColl.size();i++)
        	{
        		SellProjectInfo sellProInfo = (SellProjectInfo)sellProjectColl.get(i);
        		//当前期间
        		PeriodInfo projectPeriod = sellProInfo.getSaleNowPeriod();
        		//启用期间
        		PeriodInfo strartPeriod = sellProInfo.getSalePeriod();
        		//项目的当前期间小于等于公司的当前期间并且项目的启用期间也是小于等于公司的当前期间的
        		//如2个项目 项目1 启用期间200908 当前期间200909 项目2 启用期间200909 当前期间200909 公司的当前期间为200908进行8月份期末结算
        		//那么项目2在结账时就不需要检查。只是有这种情况吗？
        		if(projectPeriod.getNumber()<=companyCurrentPeriod.getNumber() && strartPeriod.getNumber()<= companyCurrentPeriod.getNumber())
        		{
        			projectName+="，"+sellProInfo.getName();
        		}
        	}
        	if(projectName.length()>0)
        	{
        		MsgBox.showInfo("当前实体财务组织下存在已结束初始化启用期间在当前期间或之前，" +
        				"但当前期间未进行月结的销售项目，项目名称为"+projectName+"，请先进行销售项目的月结。");
        		this.abort();
        	}else
        	{
        		SellProjectFactory.getRemoteInstance().nextSystem(companyInfo.getId().toString(), userInfo);
        		PeriodInfo companyCurrentPeriod = SystemStatusCtrolUtils
    			.getCurrentPeriod(null, SystemEnum.FDC_SHE, new ObjectUuidPK(companyInfo.getId()));
    			PeriodInfo nextPeriod = PeriodUtils.getNextPeriodInfo(companyCurrentPeriod);
    			if(companyCurrentPeriod!=null){
        	    	StringBuffer sb = new StringBuffer();
        	    	sb.append("当前期间:"+companyCurrentPeriod.getPeriodYear()+"年第"+companyCurrentPeriod.getPeriodNumber()+"期"+ "\n");
        	    	sb.append("下一期间:"+ nextPeriod.getPeriodYear()+"年第"+ nextPeriod.getPeriodNumber()+"期"+ "\n\n");
        	    	kDTextArea2.setText(""+sb);
    			}
        	}
        }else if(radUnInit.isSelected())
        {
        	//校验权限:某个组织某个用户某个功能的权限是否有权限
    		if(!PermissionFactory.getRemoteInstance().hasFunctionPermission(
    				new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId().toString()),
    				new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()),
    				new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client.SellHouseEndPeriodUI"),
    				new MetaDataPK("ActionUnEndSale") )){
//    			throw new  Permission//AccountBooksException(AccountBooksException.NOSWITCH,new Object[]{scheme.getName()});
    			MsgBox.showError(this,"没有反月结权限");
    			abort();
    		}
        	//如果在系统状态出已经与总账联用，那么总账的当前期间必须大于等于公司的当前期间才
        	//可以反结账
        	String companyId = companyInfo.getId().toString();
			SystemStatusCtrolUtils sscUtils = new SystemStatusCtrolUtils();			
			//当前期间已经与总账联用，请先取消与总账[联用]。
			if(sscUtils.isRelatedAccount4Client(companyId,SystemEnum.FDC_SHE)){			
				PeriodInfo  glCurPeriod=SystemStatusCtrolUtils.getCurrentPeriod(null,SystemEnum.GENERALLEDGER,companyInfo);
				if(glCurPeriod!=null && companyCurrentPeriod!=null &&  companyCurrentPeriod.getPeriodYear()==glCurPeriod.getPeriodYear()&&
						companyCurrentPeriod.getPeriodNumber()==glCurPeriod.getPeriodNumber()){
					MsgBox.showInfo(this,"房地产当前期间与总账一样，不能反结账！");
					SysUtil.abort();
				}
				
			   if(sscUtils.isEqualsCurPeriodAndRelPeriod4Client(companyId,SystemEnum.CASHMANAGEMENT)){
				   MsgBox.showInfo("请取消与总账联用！");
				   SysUtil.abort();
			   }
			}
			PeriodInfo currentPeriod = SystemStatusCtrolUtils
			.getCurrentPeriod(null, SystemEnum.FDC_SHE, new ObjectUuidPK(companyInfo.getId()));
			
			PeriodInfo startPeriod = SystemStatusCtrolUtils
			.getStartPeriod(null, SystemEnum.FDC_SHE, new ObjectUuidPK(companyInfo.getId()));
			if(currentPeriod.getNumber()==startPeriod.getNumber())
			{
				MsgBox.showInfo("当前期间为系统启用期间，无法进行反月结.");
				SysUtil.abort();
			}
			SellProjectFactory.getRemoteInstance().preSystem(companyId);
			PeriodInfo companyCurrentPeriod = SystemStatusCtrolUtils
			.getCurrentPeriod(null, SystemEnum.FDC_SHE, new ObjectUuidPK(companyInfo.getId()));
			PeriodInfo nextPeriod = PeriodUtils.getNextPeriodInfo(companyCurrentPeriod);
			if(companyCurrentPeriod!=null){
    	    	StringBuffer sb = new StringBuffer();
    	    	sb.append("当前期间:"+companyCurrentPeriod.getPeriodYear()+"年第"+companyCurrentPeriod.getPeriodNumber()+"期"+ "\n");
    	    	sb.append("下一期间:"+ nextPeriod.getPeriodYear()+"年第"+ nextPeriod.getPeriodNumber()+"期"+ "\n\n");
    	    	kDTextArea2.setText(""+sb);
			}
        }
    }

    protected void btnCancel_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnCancel_actionPerformed(e);
        //setConfirm(false);
		//disposeUIWindow();
        this.destroyWindow();
    }

}