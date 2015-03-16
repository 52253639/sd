package com.kingdee.eas.fdc.market.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.eas.fdc.market.CompeterCollection;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseControllerBean;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.market.CompeterInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class CompeterControllerBean extends AbstractCompeterControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.market.app.CompeterControllerBean");
    
    /**
     * 描述：覆盖父类的方法。设置单据的一些属性，对于导入单据可能组织设置存在问题，必须取工程项目对应的成本中心
     * 其中设置OrgUnit为当前的销售组织。 <a>
     * @author pu_zhang  <a> <a> 2010-6-30
     * @throws EASBizException, BOSException
     * @param Context，FDCBillInfo
     * 
     */
    protected void setPropsForBill(Context ctx, FDCBillInfo fDCBillInfo) throws EASBizException, BOSException {
    	
    	CurProjectInfo projectInfo = null;
		if(fDCBillInfo.getOrgUnit() == null) {			
			if(fDCBillInfo.get("curProject")!=null){
				projectInfo =(CurProjectInfo)fDCBillInfo.get("curProject");
				if( projectInfo.getCostCenter()==null || projectInfo.getCU()==null){
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("CU.id");
					sic.add("costCenter.id");				
					projectInfo = CurProjectFactory.getLocalInstance(ctx)
						.getCurProjectInfo(new ObjectUuidPK(projectInfo.getId().toString()),sic);
				}
				if(projectInfo.getCostCenter()!=null) {
					FullOrgUnitInfo orgUnit = projectInfo.getCostCenter().castToFullOrgUnitInfo();
					fDCBillInfo.setOrgUnit(orgUnit);
				}
			}else{
				if(ContextUtil.getCurrentSaleUnit(ctx)==null){
					throw new EASBizException(new NumericExceptionSubItem("100","当前组织不是销售组织"));
				}
				FullOrgUnitInfo orgUnit = ContextUtil.getCurrentSaleUnit(ctx).castToFullOrgUnitInfo();
				fDCBillInfo.setOrgUnit(orgUnit);
			}
		}
		if(fDCBillInfo.getCU() == null) {
			if(fDCBillInfo.get("curProject")!=null){
				if(projectInfo==null){
					projectInfo =(CurProjectInfo)fDCBillInfo.get("curProject");
				}
				if(projectInfo.getCU()==null){
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("CU.id");				
					projectInfo = CurProjectFactory.getLocalInstance(ctx)
						.getCurProjectInfo(new ObjectUuidPK(projectInfo.getId().toString()),sic);
				}				
				fDCBillInfo.setCU(projectInfo.getCU());
				
			}else{
				CtrlUnitInfo currentCtrlUnit = ContextUtil.getCurrentCtrlUnit(ctx);
				fDCBillInfo.setCU(currentCtrlUnit);
			}
		}	
    	
    }
    
}