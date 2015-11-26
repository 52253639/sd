package com.kingdee.eas.fdc.contract;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.ejb.EJBFactory;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.EnactmentServiceProxy;
import com.kingdee.bos.workflow.service.IWfDefineService;
import com.kingdee.bos.workflow.service.WfDefineService;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.param.IParamControl;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.IPermission;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.assistant.IPeriod;
import com.kingdee.eas.basedata.assistant.PeriodFactory;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.assistant.VoucherTypeFactory;
import com.kingdee.eas.basedata.assistant.VoucherTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewFactory;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.IFullOrgUnit;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.ICostAccountWithAccount;
import com.kingdee.eas.fdc.basedata.ICurProject;
import com.kingdee.eas.fdc.finance.IProjectPeriodStatus;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusCollection;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusInfo;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.UuidException;
import com.kingdee.util.db.SQLUtils;

/**
 * 房地产系统工具类
 * @author jinxp
 *
 */
public class FDCUtils {

    /**
     * 描述：判断单据是否在工作流中
     * 创建时间：2006-12-27 <p>
     */
    public static boolean isRunningWorkflow(Context ctx, String objId) throws BOSException {
        boolean hasWorkflow = false;
        IWfDefineService service = WfDefineService.getService(ctx);
        String procDefID = service.findSubmitProcDef((BOSUuid.read(objId)).getType(), ContextUtil.getCurrentUserInfo(ctx).getId().toString());
        if (procDefID != null) {
            IEnactmentService service2 = EnactmentServiceProxy.getEnacementService(ctx);
            ProcessInstInfo[] procInsts = service2.getProcessInstanceByHoldedObjectId(objId);
            for (int i = 0, n = procInsts.length; i < n; i++) {
                if ("open.running".equals(procInsts[i].getState())) {
                    hasWorkflow = true;
                    break;
                }
            }
        }
        return hasWorkflow;
    }
    
    /**
     * 描述：判断单据是否在工作流中 client
     * 创建时间：2009-1-8 <p>
     */
    public static boolean isRunningWorkflow(String objId) throws BOSException {
        boolean hasWorkflow = false;
        IEnactmentService service2 = EnactmentServiceFactory.createRemoteEnactService();
        ProcessInstInfo[] procInsts = service2.getProcessInstanceByHoldedObjectId(objId);
        for (int i = 0, n = procInsts.length; i < n; i++) {
            if ("open.running".equals(procInsts[i].getState())) {
                hasWorkflow = true;
                break;
            }
        }
        return hasWorkflow;
    }
    /**
     * 获得房地产系统参数
     * 
     * @param ctx
     * @param companyID
     * @return
     */
    public static HashMap getDefaultFDCParam(Context ctx, String companyID) throws BOSException, EASBizException {
        IObjectPK comPK = null; 
        if(companyID!=null){	
        	comPK =  new ObjectUuidPK(companyID);
        }
        
        HashMap hmParamIn = new HashMap();
        hmParamIn.put(FDCConstants.FDC_PARAM_STARTMG, comPK);
        hmParamIn.put(FDCConstants.FDC_PARAM_SELECTPERSON, null);
        hmParamIn.put(FDCConstants.FDC_PARAM_MORESETTER, null);
        hmParamIn.put(FDCConstants.FDC_PARAM_MORESETTER_ALLNOTPAID, comPK);  // Added by Owen_wen 2010-07-28
        //集团参数：再次归档时更新合同编号
        hmParamIn.put(FDCConstants.FDC_PARAM_UPDATECONTRACTNO, null);
        hmParamIn.put(FDCConstants.FDC_PARAM_INCORPORATION, comPK);
        hmParamIn.put(FDCConstants.FDC_PARAM_ACCOUNTVIEW, comPK);
        hmParamIn.put(FDCConstants.FDC_PARAM_PROJECTINDEX, null);
        
        hmParamIn.put(FDCConstants.FDC_PARAM_WFISPASSISFALSENOTPRINT, comPK);
        hmParamIn.put(FDCConstants.FDC_PARAM_WFIsDuplicateNotPrint, comPK);
        hmParamIn.put(FDCConstants.FDC_PARAM_PROJECTTREESORTBYSORTNO, null); //工程项目树及序时簿列表是否按照开发顺序排序
        
        //报表是否显示成本合计行
        hmParamIn.put(FDCConstants.FDC_PARAM_TOTALCOST, null);
        hmParamIn.put(FDCConstants.FDC_PARAM_INCLUDENOCOSTACCOUNT, null);
        
        //
        hmParamIn.put(FDCConstants.FDC_PARAM_COSTMEASURE, null);
        
        //生成凭证的凭证类型
        hmParamIn.put(FDCConstants.FDC_PARAM_VOUCHERTYPE, comPK);
        
        //产品结算单使用成本期间还是财务期间
        hmParamIn.put(FDCConstants.FDC_PARAM_USECOSTORFINANCE, comPK);
        //付款申请单付款金额不允许超过可付款额度
        hmParamIn.put(FDCConstants.FDC_PARAM_OUTPAYAMOUNT, comPK);
        //项目合同签约总金额超过项目
        hmParamIn.put(FDCConstants.FDC_PARAM_OUTCOST, comPK);
        //自定义参数
        hmParamIn.put(FDCConstants.FDC_PARAM_CONTROLTYPE, comPK);
        hmParamIn.put(FDCConstants.FDC_PARAM_ACCTBUDGET, null);
        
        hmParamIn.put(FDCConstants.FDC_PARAM_CONSETTTYPE, null);
        hmParamIn.put(FDCConstants.FDC_PARAM_COSTCENTERCONSTRATE, null);
        
        //合同审批前进行拆分
        hmParamIn.put(FDCConstants.FDC_PARAM_SPLITBFAUDIT, comPK);
        //付款申请单可以直接付保修金，而不需要付结算款
        hmParamIn.put(FDCConstants.FDC_PARAM_KEEPBEFORESETTLEMENT, comPK);
        //未结算合同的实付款大于已实现产值时是否严格控制  集团参数 by jian_wen 2009.12.15
        hmParamIn.put(FDCConstants.FDC_PARAM_ISCONTROLPAYMENT, comPK);
        //合同结算时是否要求变更必须结算完毕,非集团控制 by zhiyuan_tang 2010/07/28
        hmParamIn.put(FDCConstants.FDC_PARAM_CONTRACTCHANGESETTLEMENTMUSTCOMPLETE, comPK);
        
        //付款申请单收款银行和收款账号为必录项
        hmParamIn.put(FDCConstants.FDC_PARAM_BANKREQURE, comPK);
        //目标成本查询录入功能
        hmParamIn.put(FDCConstants.FDC_PARAM_AIMCOSTINPUT, null);
        //测算调整系数
        hmParamIn.put(FDCConstants.FDC_PARAM_MEASUREADJUST, null);
        //品质特征
        hmParamIn.put(FDCConstants.FDC_PARAM_MEASUREQUALITY, null);
        //使用自定义指标
        hmParamIn.put(FDCConstants.FDC_PARAM_USECOSTOMINDEX, null);
        //目标成本与调整记录是否允许直接录入
        hmParamIn.put(FDCConstants.FDC_PARAM_AIMCOSTADJUSTINPUT,null);
        //允许删除待发生成本的调整记录
        hmParamIn.put(FDCConstants.FDC_PARAM_AIMCOSTADJUSTDELETE, null);
        //目标成本测算时，各产品分摊是否按集团规定的指标进行分摊
        hmParamIn.put(FDCConstants.FDC_PARAM_MEASUREINDEX, null);
        //申请单进度款付款比例自动为100%
        hmParamIn.put(FDCConstants.FDC_PARAM_PAYPROGRESS, comPK);
        //合同执行情况表计划列
        hmParamIn.put(FDCConstants.FDC_PARAM_CONTRACTEXEC, null);
        //付款申请单审批前必须录入合同付款计划
        hmParamIn.put(FDCConstants.FDC_PARAM_CONPAYPLAN, comPK);
        //房地产单据强制不进行进入出纳系统
        hmParamIn.put(FDCConstants.FDC_PARAM_NOTENTERCAS, comPK);
        
        hmParamIn.put(FDCConstants.FDC_PARAM_VIEWPLAN, comPK);
        hmParamIn.put(FDCConstants.FDC_PARAM_CREATEPARTADEDUCT, comPK);
        hmParamIn.put(FDCConstants.FDC_PARAM_PARTA_MAINCONTRACT, comPK);
        //结算拆分生成凭证
        hmParamIn.put(FDCConstants.FDC_PARAM_SETTLEMENTCOSTSPLIT, null);
        
        //预算控制参数
        //合同计划控制
        hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_CONTRACTCTRPAY, comPK);
        //成本科目付款计划控制
        hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_COSTACCTCTRPAY, comPK);
        //严格控制
        hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_STRICTCTRL, comPK);
        //预算系统控制
        hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_BGSYSCTRPAY, comPK);
        //预算系统控制时控制到成本/付款申请金额
        hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_CTRLCOSTACCOUNT, comPK);
        //显示成本列
        hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_DISPLAYCOST, comPK);
        //合同月度付款计划是否有部门合同付款计划直接生成
        hmParamIn.put(FDCConstants.FDC_PARAM_DEPCONPAYPLAN, null);
        //隐藏全项目已实现成本 by sxhong
        hmParamIn.put(FDCConstants.FDC_PARAM_HIDEREALIZEDCOST, null);
        //合同费用项目 2008-12-10
        hmParamIn.put(FDCConstants.FDC_PARAM_CHARGETYPE,null);
        //经办人维护合同
        hmParamIn.put(FDCConstants.FDC_PARAM_CREATORATTACHMENT,null);
        //审批后提示进行拆分
        hmParamIn.put(FDCConstants.FDC_PARAM_SPLITAFTERAUDIT,null);
        //跨项目拆分的工程项目也可进行合同结算及请款
        hmParamIn.put(FDCConstants.FDC_PARAM_CROSSPROJECTSPLIT, null);
        //目标成本修订审批之后才影响动态成本
        hmParamIn.put(FDCConstants.FDC_PARAM_AIMCOSTAUDIT, null);
        hmParamIn.put(FDCConstants.FDC_PARAM_CANMODIFYCONTRACTNUMBER, null);
        hmParamIn.put(FDCConstants.FDC_PARAM_INCLUDECHANGEAUDIT, null);
        hmParamIn.put(FDCConstants.FDC_PARAM_ISOPENPAYMENTEDITUI, null);
        
        /*
         * by Cassiel_peng
         */
    	//付款计划差异计算
        hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_PLANDIF,null);
        //项目计划执行表是否显示付款单上实际付款拆分金额及对应差异列
        hmParamIn.put(FDCConstants.FDC_PARAM_BUDGET_ACTDIF, null);
        //房地产业务系统附件管理启用笔迹留痕管理
        hmParamIn.put(FDCConstants.FDC_PARAM_WRITEMARK, null);
        //是否允许拆分提交状态的单据
        hmParamIn.put(FDCConstants.FDC_PARAM_SPLITSUBMIT, null);
        //审批状态的单据可以上传附件
        hmParamIn.put(FDCConstants.FDC_PARAM_UPLOADAUDITEDBILL,comPK);
        //合同实际付款情况依据付款单而定
//        hmParamIn.put(FDCConstants.FDC_PARAM_BASEONPAYMENTBILL, null);
        //启用变更结算工作流审批
        hmParamIn.put(FDCConstants.FDC_PARAM_CHANGESETTAUDIT, comPK);
        //设计变更单“提出方”及“施工单位”是否必填 
    	hmParamIn.put(FDCConstants.FDC_PARAM_ISREQUIREDFORASKANDCONSTRCTION, null);
    	//是否启用变更下发  
    	hmParamIn.put(FDCConstants.FDC_PARAM_ALLOWDISPATCH, null);
    	//变更指令单是否自动改为工程签证单  
    	hmParamIn.put(FDCConstants.FDC_PARAM_AUTOCHANGETOPROJECTVISA, null);
        //指令单是否必须在审批单审批后才能生成
    	hmParamIn.put(FDCConstants.FDC_PARAM_GENERATEAFTERAUDIT, null);
    	//允许审批后可增加合同的正文
    	hmParamIn.put(FDCConstants.FDC_PARAM_ADDCONTENTAUDITED, null);
    	//结算单上保修金比例是否保留6位小数
    	hmParamIn.put(FDCConstants.FDC_PARAM_FDC224_KEEP6FORGUARANTERATE, null);
    	//项目月度计划申请表显示合同经济条款及形象进度
    	hmParamIn.put(FDCConstants.FDC_PARAM_FDC322_DISECOITEMPROCESS, null);
    	// 合同结算时，汇率是否取合同签订时的汇率
    	hmParamIn.put(FDCConstants.FDC_PARAM_FDC323_SELECTEXECHANGERATE, null);
    	// 合同是否上传正文控制付款申请单新增。
    	hmParamIn.put(FDCConstants.FDC_PARAM_FDC324_NEWPAYREQWITHCONTRACTATT, comPK);   
    	// 合同、变更拆分审批后方可被后续拆分引用，后续拆分包括：结算拆分、付款拆分、工程量拆分。
    	hmParamIn.put(FDCConstants.FDC_PARAM_FDC5014_NEXTSPLITISBASEONPREAUDITED, null);   
    	
    	//累计付款超过合同最新造价约定比例严格控制
    	hmParamIn.put(FDCConstants.FDC_PARAM_ALLPAIDMORETHANCONPRICE, null);
    	//启用甘特图的调整申请模式
    	hmParamIn.put(FDCConstants.FDC_PARAM_ADJUSTBYGRANT, comPK);
    	//责任人是否按当前用户所在组织进行过滤
    	hmParamIn.put(FDCConstants.FDC_PARAM_FILTERRESPPERSON, comPK);
    	//任务工程量填报是否只能按责任人填报
    	hmParamIn.put(FDCConstants.FDC_PARAM_FILLBYRESPPERSON, comPK);
    	////计划部门、责任部门可选全集团组织
    	hmParamIn.put(FDCConstants.FDC_PARAM_CHOOSEALLORG, comPK);
    	//合同提交审批时是否必须与计划任务进行关联 2010-08-09
    	hmParamIn.put(FDCConstants.FDC_PARAM_RELATEDTASK, comPK);
    	
    	
    	
        //合同完工工程量取进度系统工程量填报数据
        hmParamIn.put(FDCConstants.FDC_PARAM_PROJECTFILLBILL,null);
    	
        //已实现产值为0时的控制
        hmParamIn.put(FDCConstants.FDC_PARAM_REALIZEDZEROCTRL,null);
        //合同变更、合同结算的拆分是否自动引用合同拆分的成本科目、拆分比例
        hmParamIn.put(FDCConstants.FDC_PARAM_IMPORTCONSPLIT, comPK);
        ////工程量，付款拆分是否允许使用按比例拆分
        hmParamIn.put(FDCConstants.FDC_PARAM_SPLITBASEONPROP, comPK);
        //合同未拆分，允许录入付款申请单
        hmParamIn.put(FDCConstants.FDC_PARAM_CHECKALLSPLIT,null);
        //付款单生成凭证时检查付款单是否已拆分
        hmParamIn.put(FDCConstants.FDC_PARAM_CHECKPAYMENTSPLIT, comPK);
        //付款单提交时检查付款单已经拆分完成
        hmParamIn.put(FDCConstants.FDC_PARAM_CHECKPAYMENTALLSPLIT, comPK);
        //付款拆分科目的金额受对应科目已拆分成本金额的限制
        hmParamIn.put(FDCConstants.FDC_PARAM_LIMITCOST, comPK);
        
        //是否启用量价拆分
        hmParamIn.put(FDCConstants.FDC_PARAM_MEASURESPLIT,null);
        //启用项目计划任务授权
        hmParamIn.put(FDCConstants.FDC_PARAM_PLANMANDATE, comPK);
        //审批和反审批必须同一个用户
        hmParamIn.put(FDCConstants.FDC_PARAM_AUDITORMUSTBETHESAMEUSER, null);
        hmParamIn.put(FDCConstants.FDC_PARAM_SELECTSUPPLY, comPK);
        
        //一体化参数
        hmParamIn.put(FDCConstants.FDC_PARAM_FINACIAL, comPK);
        hmParamIn.put(FDCConstants.FDC_PARAM_SIMPLEINVOICE, comPK);
        hmParamIn.put(FDCConstants.FDC_PARAM_INVOICEOFFSET, null);
        hmParamIn.put(FDCConstants.FDC_PARAM_SIMPLEFINACIAL, comPK);
        
        hmParamIn.put(FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND, comPK);
        //财务一体化启用成本反月结
        hmParamIn.put(FDCConstants.FDC_PARAM_UNINCORPORATION, comPK);
    	//财务成本一体化调整凭证模式
        hmParamIn.put(FDCConstants.FDC_PARAM_ADJUSTVOURCHER, comPK);
        //工程量与付款分离
    	hmParamIn.put(FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT, comPK);
    	//付款单生成时创建人是付款申请单的创建人还是审批人
    	hmParamIn.put(FDCConstants.FDC_PARAM_PAYMENTCREATOR, comPK);
    	//付款申请单及无文本合同发票号、发票金额必录
    	hmParamIn.put(FDCConstants.FDC_PARAM_INVOICEREQUIRED, null);
    	hmParamIn.put(FDCConstants.FDC_PARAM_INVOICEMRG, comPK);
    	// 付款申请单及结算单根据审批时间设置期间值
		hmParamIn.put(FDCConstants.FDC_PARAM_RESETPERIOD, comPK);
		// 可研成本测算及目标成本测算时，规划指标表上：人行道及广场计算在“绿化用地合计”内
		hmParamIn.put(FDCConstants.FDC_PARAM_PLANINDEXLOGIC, null);
		// 可研成本测算、目标成本测算上项目建筑单方计算，目标成本测算导出项目建筑面积指标都使用参与分摊的产品建筑面积之和，而不是总建筑面积
		hmParamIn.put(FDCConstants.FDC_PARAM_BUILDPARTLOGIC, null);
		hmParamIn.put(FDCConstants.FDC_PARAM_MEASURECOSTISCONTAINDEVPROJECT, null);
		hmParamIn.put(FDCConstants.FDC_PARAM_SETTLEMENTOVERCONTRACTAMOUNT, comPK);
		hmParamIn.put(FDCConstants.FDC_PARAM_ADVANCEPAYMENTNUMBER,comPK);
		hmParamIn.put(FDCConstants.FDC_PARAM_LIMITSELLAREA, null);
		
		//采购招投标
    	//招标文件合成、供应商资格预审、确定评标专家、确定评标模板一块审批
    	hmParamIn.put(FDCConstants.FDC_PARAM_INVITE_SUPPLIER_EXPERT_TEMPLATE, comPK);
    	hmParamIn.put(FDCConstants.FDC_PARAM_INVITE_PURCHASEBOMMUSTPARTA, comPK);
    	hmParamIn.put(FDCConstants.FDC_PARAM_INVITE_SUPPLIER_FROM_FDCSUPPLIER, comPK);
    	
    	//供应商
    	hmParamIn.put(FDCConstants.FDC_PARAM_SUPPLIER_LIMIT, comPK);
    	
    	
    	//投资测算
    	hmParamIn.put(FDCConstants.FDC_PARAM_MEASURE_COSTMEASURE, null);
    	
    	//成本测算与收入测算是否联用
    	hmParamIn.put(FDCConstants.FDC_PARAM_ISINCOMEJOINCOST, null);
    	
    	// 是否允许付款申请单累计发票金额大于合同最新造价
		hmParamIn.put(FDCConstants.FDC_PARAM_OVERRUNCONPRICE, comPK);
//		进度管理完工工程量的确认是否严格控制
		hmParamIn.put(FDCConstants.FDCSCH_PARAM_ISFILLBILLCONTROLSTRICT, comPK);
		hmParamIn.put(FDCConstants.FDCSCH_PARAM_BASEONTASK, comPK);
		hmParamIn.put(FDCConstants.FDC_PARAM_OLDCONTRACT_PROGRAM, null);
		
		hmParamIn.put(FDCConstants.FDC_PARAM_OLDCONTRACT_PROGRAM, comPK);
        IParamControl pc;
        if(ctx!=null)
        	pc = ParamControlFactory.getLocalInstance(ctx);
        else
        	pc= ParamControlFactory.getRemoteInstance();
        HashMap hmAllParam = pc.getParamHashMap(hmParamIn);
      
        
        return hmAllParam;
    }
    
  
    /**
     * 易用性方法,通过传入参数值Map,及参数编码取参数
     * 例:
     * Map paramMap=FDCUtils.getDefaultFDCParam(...);
     * boolean flag=FDCUtils.getParamValue(paramMap,FDCConstants.FDC_PARAM_BUDGET_DISPLAYCOST);
     *  by sxhong 2008-09-23 17:03:05
     * @param paramMap
     * @param fdcParamKey
     * @return
     */
    public static boolean getParamValue(Map paramMap,String fdcParamKey){
        Object theValue = paramMap.get(fdcParamKey);
        if(theValue != null){
			return Boolean.valueOf(theValue.toString()).booleanValue();
		}
        return false;
    }
    
    /**
     * 获得房地产系统参数
     * 
     * @param ctx
     * @param companyID
     * @return
     */
    public static HashMap getDefaultCashOrGLParam(Context ctx, String companyID) throws BOSException, EASBizException {
        IObjectPK comPK = null; 
        if(companyID!=null){	
        	comPK =  new ObjectUuidPK(companyID);
        }
        
        HashMap hmParamIn = new HashMap();
        hmParamIn.put("CS050", comPK);
        hmParamIn.put("CS017", comPK);
        hmParamIn.put("CS031", comPK);
        hmParamIn.put("CS034", comPK);
        hmParamIn.put("CS036", comPK);
        hmParamIn.put("FDC302_MODIFY", comPK);
        
        IParamControl pc;
        if(ctx!=null)
        	pc = ParamControlFactory.getLocalInstance(ctx);
        else
        	pc= ParamControlFactory.getRemoteInstance();
        HashMap hmAllParam = pc.getParamHashMap(hmParamIn);
        
        return hmAllParam;
    }
    
    /**
     * 获得房地产系统参数,仅限于值为是/否类型的参数
     * 
     * @param ctx
     * @param companyID
     * @param fdcParamKey
     * @return
     */
    public static boolean getDefaultFDCParamByKey(Context ctx, String companyID,String fdcParamKey) throws BOSException, EASBizException {
        HashMap hmAllParam = getDefaultFDCParam(ctx,companyID);
        Object theValue = hmAllParam.get(fdcParamKey);
        if(theValue != null){
			return Boolean.valueOf(theValue.toString()).booleanValue();
		}
        return false;
    }
    
    /**
     * 获得房地产系统参数值, 适用于 <b>枚举</b> 类型的参数
     * @param ctx
     * @param companyID 传入组织ID
     * @param paramNumber 参数编码
     * @return fdcParamValue 以String形式返回结果
     * @throws EASBizException
     * @throws BOSException
     * @author owen_wen 2010-11-19
     */
    public static String getFDCParamByKey(Context ctx, String companyID,String paramNumber) throws EASBizException, BOSException{
    	String fdcParamValue = "";
		IObjectPK comPK = new ObjectUuidPK(BOSUuid.read(companyID));
		fdcParamValue = ParamControlFactory.getLocalInstance(ctx).getParamValue(comPK, paramNumber);
		return fdcParamValue;
    }
    
    /**
     * 获得房地产系统参数值, 适用于 <b>布尔</b> 类型的参数 
     * @param ctx
     * @param companyID 传入组织ID，如果是集团参数，传入null即可。非集团参数用SysContext.getSysContext().getCurrentOrgUnit().getId().toString()
     * @param paramNumber 参数编码
     * @return 参数值
     * @throws EASBizException
     * @throws BOSException
     * @author owen_wen 2010-11-30
     * @see com.kingdee.eas.fdc.basedata.FDCHelper.getBooleanValue4FDCParamByKey(Context ctx, String companyID,String paramNumber)
     */
    public static boolean getBooleanValue4FDCParamByKey(Context ctx, String companyID,String paramNumber) throws EASBizException, BOSException{
		return FDCHelper.getBooleanValue4FDCParamByKey(ctx, companyID, paramNumber);
    }
    
    
    //获取某个工程项目当前的期间
    public static PeriodInfo fetchCostPeriod(String projectId, java.util.Date bookedDate) throws Exception{
    	
    	return ProjectPeriodStatusFactory.getRemoteInstance().fetchPeriod(projectId,  bookedDate,true);
    }
    
    //获取某个工程项目当前的期间
    public static PeriodInfo fetchFinacialPeriod(String projectId, java.util.Date bookedDate) throws Exception{
    	
    	return ProjectPeriodStatusFactory.getRemoteInstance().fetchPeriod(projectId,  bookedDate,false);
    }
    
    /**
     * 获取期间 创建时间 2004-12-2
     * 
     * @param periodTypeId
     * @param time
     * @return
     * @throws BOSException
     * @throws EASBizException
     */
    public static PeriodInfo fetchPeriod(Context ctx, String periodTypeId, Date time) throws BOSException, EASBizException {
        EntityViewInfo view = new EntityViewInfo();
        SelectorItemCollection sic = view.getSelector();
        sic.add(new SelectorItemInfo("periodYear"));
        sic.add(new SelectorItemInfo("periodNumber"));
        sic.add(new SelectorItemInfo("beginDate"));
        sic.add(new SelectorItemInfo("endDate"));
        sic.add(new SelectorItemInfo("number"));

        FilterInfo filter = new FilterInfo();
        view.setFilter(filter);
        FilterItemCollection fic = filter.getFilterItems();
        fic.add(new FilterItemInfo("periodType.id", periodTypeId));
        fic.add(new FilterItemInfo("beginDate", time, CompareType.LESS_EQUALS));
        fic.add(new FilterItemInfo("endDate", time, CompareType.GREATER_EQUALS));

        SorterItemCollection soter = view.getSorter();
        soter.add(new SorterItemInfo("number"));
        
        IPeriod prdCtrl = null;
        if(ctx!=null){
        	prdCtrl = PeriodFactory.getLocalInstance(ctx);
        }else{
        	prdCtrl = PeriodFactory.getRemoteInstance();
        }

        CoreBaseCollection objs = prdCtrl.getCollection(view);

        if (objs.size() > 0) {
            return (PeriodInfo) objs.getObject(0);
        } else {
            
	        //if(period==null){
        	//throw new ProjectPeriodStatusException(ProjectPeriodStatusException.BOOKEDDATENOTPERIOD);
	        //	throw new PeriodException(PeriodException.COMPANYNOPERIOD);
	        //}
        	return null;
        }
    }
    
    /**
     * 当前工程项目成本 是否冻结
     * @param isCost  True为成本当前期间 Fasle为财务当前期间
     * @throws BOSException 
     * @throws EASBizException 
     */
    static public boolean isFreeze(Context ctx, String projectId,boolean isCost) throws BOSException, EASBizException{
    	boolean isFreeze = false;
		IProjectPeriodStatus iProjectCrrol  = ProjectPeriodStatusFactory.getLocalInstance(ctx);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id",projectId));
		filter.getFilterItems().add(new FilterItemInfo(isCost?"isCostFreeze":"isFinaclaFreeze",new Integer(1)));
		
        if (iProjectCrrol.exists(filter)) {
        	isFreeze = true ;
        }
        
    	return isFreeze;
    }
    
    /**
     * 根据工程项目获得期间
     * @param ctx
     * @param projectId
     * @param isCost  True为成本 Fasle为财务
     * @return PeriodInfo
     * @throws BOSException
     * @throws EASBizException
     */
    static public PeriodInfo getCurrentPeriod(Context ctx, String projectId,boolean isCost) throws BOSException, EASBizException {
        EntityViewInfo view = new EntityViewInfo();
        FilterInfo filter = new FilterInfo();
        view.setFilter(filter);

        FilterItemCollection fic = filter.getFilterItems();
        //fic.add(new FilterItemInfo("systemStatus.name", new Integer(SystemEnum.FDC.getValue())));
        fic.add(new FilterItemInfo("project.id", projectId));       

        SelectorItemCollection sic = view.getSelector();
	    if(isCost){
	        sic.add(new SelectorItemInfo("costPeriod.periodYear"));
	        sic.add(new SelectorItemInfo("costPeriod.periodNumber"));
	        sic.add(new SelectorItemInfo("costPeriod.beginDate"));
	        sic.add(new SelectorItemInfo("costPeriod.endDate"));
	        sic.add(new SelectorItemInfo("costPeriod.number"));
        }else{

            sic.add(new SelectorItemInfo("finacialPeriod.periodYear"));
            sic.add(new SelectorItemInfo("finacialPeriod.periodNumber"));
            sic.add(new SelectorItemInfo("finacialPeriod.beginDate"));
            sic.add(new SelectorItemInfo("finacialPeriod.endDate"));
            sic.add(new SelectorItemInfo("finacialPeriod.number"));	
        }

        ProjectPeriodStatusCollection col = new ProjectPeriodStatusCollection();
        if(ctx!=null){
        	col = ProjectPeriodStatusFactory.getLocalInstance(ctx).getProjectPeriodStatusCollection(view);
        }else{
        	col = ProjectPeriodStatusFactory.getRemoteInstance().getProjectPeriodStatusCollection(view);
        }
        
        PeriodInfo prd = null;
        if (col.size() > 0) {
        	 if(isCost){
        		 prd = col.get(0).getCostPeriod();
        	 }else{
        		 prd = col.get(0).getFinacialPeriod();
        	 }
        }

        if (prd == null) {        	
			sic = new SelectorItemCollection();
			sic.add("isLeaf");
			sic.add("isEnabled");
			sic.add("startDate");
			sic.add("fullOrgUnit.id");
			CurProjectInfo curProject =null;
			if(ctx!=null){
				curProject =  CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(projectId),sic);
			}else{
				curProject= CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(projectId),sic);
			}
    		// 启用成本财务一体化
    		boolean isIncorporation = FDCUtils.IsInCorporation(ctx, curProject.getFullOrgUnit().getId().toString());
    		if(!isIncorporation){
    			CompanyOrgUnitInfo companyInfo = GlUtils.getCurrentCompany(ctx,  curProject.getFullOrgUnit().getId().toString(),
    					null, false);
    			prd = FDCUtils.fetchPeriod(ctx, companyInfo
						.getAccountPeriodType().getId().toString(), curProject
						.getStartDate());
    			
    			if(prd==null){
    				prd = PeriodUtils.getPeriodInfo(ctx ,new Date() ,companyInfo);
    			}
			}
        }

        return prd;
    }
    
    /***************************************************************************
	 * 批量获取--根据工程项目获得期间 返回值：key=projectId value=PeriondInfo
	 * 
	 * @param ctx
	 * @param projectIds
	 * @param isCost
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	static public Map getCurrentPeriod(Context ctx, Set projectIds,
			boolean isCost) throws BOSException, EASBizException {
		Map periods = new HashMap();
		if (projectIds == null || projectIds.size() == 0)
			return periods;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);

		FilterItemCollection fic = filter.getFilterItems();
		fic.add(new FilterItemInfo("project.id", projectIds,
				CompareType.INCLUDE));

		SelectorItemCollection sic = view.getSelector();
		if (isCost) {
			sic.add(new SelectorItemInfo("costPeriod.periodYear"));
			sic.add(new SelectorItemInfo("costPeriod.periodNumber"));
			sic.add(new SelectorItemInfo("costPeriod.beginDate"));
			sic.add(new SelectorItemInfo("costPeriod.endDate"));
			sic.add(new SelectorItemInfo("costPeriod.number"));
		} else {
			sic.add(new SelectorItemInfo("finacialPeriod.periodYear"));
			sic.add(new SelectorItemInfo("finacialPeriod.periodNumber"));
			sic.add(new SelectorItemInfo("finacialPeriod.beginDate"));
			sic.add(new SelectorItemInfo("finacialPeriod.endDate"));
			sic.add(new SelectorItemInfo("finacialPeriod.number"));
		}
		ProjectPeriodStatusCollection col = new ProjectPeriodStatusCollection();
		if (ctx != null) {
			col = ProjectPeriodStatusFactory.getLocalInstance(ctx)
					.getProjectPeriodStatusCollection(view);
		} else {
			col = ProjectPeriodStatusFactory.getRemoteInstance()
					.getProjectPeriodStatusCollection(view);
		}
		for (Iterator it = col.iterator(); it.hasNext();) {
			ProjectPeriodStatusInfo info = (ProjectPeriodStatusInfo) it.next();
			if (isCost)
				periods.put(info.getProject().getId().toString(), info
						.getCostPeriod());
			else
				periods.put(info.getProject().getId().toString(), info
						.getFinacialPeriod());
		}
		for (Iterator it = projectIds.iterator(); it.hasNext();) {
			String projectId = (String) it.next();
			if (!periods.containsKey(projectId)) {
				PeriodInfo prd = null;
				sic = new SelectorItemCollection();
				sic.add("isLeaf");
				sic.add("isEnabled");
				sic.add("startDate");
				sic.add("fullOrgUnit.id");
				CurProjectInfo curProject;
				if (ctx != null)
					curProject = CurProjectFactory
							.getLocalInstance(ctx)
							.getCurProjectInfo(new ObjectUuidPK(projectId), sic);
				else
					curProject = CurProjectFactory
							.getRemoteInstance()
							.getCurProjectInfo(new ObjectUuidPK(projectId), sic);
				// 启用成本财务一体化
				boolean isIncorporation = FDCUtils.IsInCorporation(ctx,
						curProject.getFullOrgUnit().getId().toString());
				if (!isIncorporation) {
					CompanyOrgUnitInfo companyInfo = GlUtils.getCurrentCompany(
							ctx,
							curProject.getFullOrgUnit().getId().toString(),
							null, false);
					prd = FDCUtils.fetchPeriod(ctx, companyInfo
							.getAccountPeriodType().getId().toString(),
							curProject.getStartDate());

					if (prd == null) {
						prd = PeriodUtils.getPeriodInfo(ctx, new Date(),
								companyInfo);
					}
				}
				if (prd != null)
					periods.put(projectId, prd);
			}
		}
		return periods;
	}
    /**
     * 获得房地产系统参数的中转科目
     * 
     * @param ctx
     * @param companyID
     * @return AccountViewInfo
     */
    public static AccountViewInfo getDefaultFDCParamAccount(Context ctx, String companyID) throws BOSException, EASBizException {
    	AccountViewInfo account = new AccountViewInfo();
    	HashMap param = getDefaultFDCParam(ctx, companyID);			
		if(param.get(FDCConstants.FDC_PARAM_ACCOUNTVIEW)!=null){
			String accountId = param.get(FDCConstants.FDC_PARAM_ACCOUNTVIEW).toString();
			if(accountId!=null && accountId.trim().length()>0 &&!accountId.equals("none")){
				if (ctx == null) {
					account = AccountViewFactory.getRemoteInstance()
							.getAccountViewInfo(
									new ObjectUuidPK(BOSUuid.read(accountId)));
				} else {
					account = AccountViewFactory.getLocalInstance(ctx)
							.getAccountViewInfo(
									new ObjectUuidPK(BOSUuid.read(accountId)));
				}
			}
		}
        return account;
    }
    
    /**
     * 获得房地产系统参数的中转科目
     * 
     * @param ctx
     * @param companyID
     * @return AccountViewInfo
     */
    public static VoucherTypeInfo getDefaultFDCParamVoucherType(Context ctx, String companyID) throws BOSException, EASBizException {
    	VoucherTypeInfo voucherType = null;
    	HashMap param = getDefaultFDCParam(ctx, companyID);			
		if(param.get(FDCConstants.FDC_PARAM_VOUCHERTYPE)!=null){
			String typeId = param.get(FDCConstants.FDC_PARAM_VOUCHERTYPE).toString();
			if(!FDCHelper.isEmpty(typeId)&&!typeId.equals("none")){
				if (ctx == null) {
					voucherType = VoucherTypeFactory.getRemoteInstance()
							.getVoucherTypeInfo(
									new ObjectUuidPK(BOSUuid.read(typeId)));
				} else {
					voucherType = VoucherTypeFactory.getLocalInstance(ctx)
							.getVoucherTypeInfo(
									new ObjectUuidPK(BOSUuid.read(typeId)));
				}
			}
		}
        return voucherType;
    }
    
    /**
     * 获得房地产系统参数的是否启用成本月结
     * 
     * @param ctx
     * @param companyID
     * @return boolean
     */
    public static boolean IsInCorporation(Context ctx, String companyID) throws BOSException, EASBizException {
    	boolean isInCorporation = false;
    	HashMap param = getDefaultFDCParam(ctx, companyID);			
		if(param.get(FDCConstants.FDC_PARAM_INCORPORATION.toString())!=null){
			isInCorporation = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_INCORPORATION).toString()).booleanValue();
			
		}
        return isInCorporation;
    }
    
    /**
     * 获得房地产系统参数的是否启用复杂模式财务成本一体化
     * 
     * @param ctx
     * @param companyID
     * @return boolean
     */
    public static boolean IsFinacial(Context ctx, String companyID) throws BOSException, EASBizException {
    	boolean isFinacial = false;
    	HashMap param = getDefaultFDCParam(ctx, companyID);			
		if(param.get(FDCConstants.FDC_PARAM_FINACIAL.toString())!=null){
			isFinacial = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_FINACIAL).toString()).booleanValue();
		}
        return isFinacial;
    }
    /**
     * 获得房地产系统参数的是否启用结算拆分生成凭证
     * 
     * @param ctx
     * @param companyID
     * @return boolean
     */
    public static boolean IsSettlementCostSplitVoucher(Context ctx, String companyID) throws BOSException, EASBizException {
    	boolean isSettlementCostSplitVoucher = false;
    	HashMap param = getDefaultFDCParam(ctx, companyID);			
		if(param.get(FDCConstants.FDC_PARAM_SETTLEMENTCOSTSPLIT.toString())!=null){
			isSettlementCostSplitVoucher = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SETTLEMENTCOSTSPLIT).toString()).booleanValue();
		}
        return isSettlementCostSplitVoucher;
    }
    
    /**
     * 获得房地产系统参数的产品结算单采用成本期间还是财务期间
     * 
     * @param ctx
     * @param companyID
     * @return boolean
     */
    public static boolean IsUseCostOrFinance(Context ctx, String companyID) throws BOSException, EASBizException {
    	boolean isUseCostOrFinance = false;
    	HashMap param = getDefaultFDCParam(ctx, companyID);			
		if(param.get(FDCConstants.FDC_PARAM_USECOSTORFINANCE)!=null){
			isUseCostOrFinance = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_USECOSTORFINANCE).toString()).booleanValue();
		}
        return isUseCostOrFinance;
    }
    
    /**
     * 获取成本中心Id
     * @param ctx
     * @param prjId
     * @return
     * @throws BOSException
     * @throws EASBizException
     */
    public static String findCostCenterOrgId(Context ctx,String prjId) throws BOSException, EASBizException {
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("parent.id");
		selector.add("name");
		selector.add("number");
		selector.add("longNumber");
		
		CurProjectInfo project = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(prjId),selector);
		
		CostCenterOrgUnitInfo costCenterOrg = FDCHelper.getCostCenter(project,ctx);
		
		if(costCenterOrg==null){
			return null;
		}
    	
		return costCenterOrg.getId().toString();					
    }
    
    
    private static void lockAssignAccount(Context ctx, String accountType) throws BOSException {
		// 数据连接
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
	        con = EJBFactory.getConnection(ctx);
	        // 转换为Timestamp,时间精确记录
	        Timestamp stnow = GlUtils.getTime();
	        int timeCount = 0;
	        //String comId = "00000000-0000-0000-0000-000000000000CCE7AED4";	        	
	        // cimid!=null 那就只更新一个公司,看是否在T_BAS_Transaction有记录,没有插入一条
	        
	        //2.查找上一次执行的时间，以及需要执行的时间t_bas_trasaction
			String selectSql = "select fupdateTime,ftimecount from T_BAS_Transaction where fsystype=37 and fnumber='" + accountType + "' ";
	        // 更新时间,先锁住这条记录
			String updateSql = "update T_BAS_Transaction set fupdateTime=?,ftimecount=? where fsystype=37 and fnumber='" + accountType
					+ "' ";
	        
	        //R110130-015：由于财务新补丁在t_bas_trasaction中增加了一个字段FISUPDATING,所以需要判断这个字段是否存在 by zhiyuan_tang
			String insertSql = "insert into T_BAS_Transaction (FID,FSYSTYPE,FNUMBER,FUPDATETIME,FTIMECOUNT) values('00000000-0000-0000-0000-000000000000CCE7AED4',37,'"
					+ accountType + "',getDate(),0)";
	        String hasFieldSql = "select 1 from KSQL_USERCOLUMNS where KSQL_COL_NAME ='FISUPDATING' and KSQL_COL_TABNAME='T_BAS_Transaction'";
	        PreparedStatement hasFieldPs = con.prepareStatement(hasFieldSql);
	        ResultSet hasFieldRs = hasFieldPs.executeQuery();
	        if (hasFieldRs != null && hasFieldRs.next()) {
	        	//存在FISUPDATING字段，则insert时需要给该字段赋值为0
				insertSql = "insert into T_BAS_Transaction (FID,FSYSTYPE,FNUMBER,FUPDATETIME,FTIMECOUNT,FISUPDATING) values('00000000-0000-0000-0000-000000000000CCE7AED4',37,'"
						+ accountType + "',getDate(),0,0)";
			}	        
	        String comsql = "and  fid='00000000-0000-0000-0000-000000000000CCE7AED4'";
	        if (true) {
	            selectSql += comsql;
	            ps = con.prepareStatement(selectSql);
	            rs = ps.executeQuery();
	
	            if (rs == null || !rs.next()) {
	                ps = con.prepareStatement(insertSql);
	                ps.execute();
	            } else {
	                timeCount = rs.getInt("ftimecount");
	                if (timeCount > 99999999)
	                    timeCount = 0;
	            }
	            updateSql += comsql;
	        }
	
	        // Update T_BAS_Transaction 锁住当前公司,确保一个公司串行执行
	        ps = con.prepareStatement(updateSql);
	        ps.setTimestamp(1, stnow);
	        ps.setInt(2, timeCount + 1);
	        ps.execute();
	        
        } catch (SQLException e) {
        	e.printStackTrace();
        	throw new BOSException(e);
        } finally {
            SQLUtils.cleanup(rs, ps, con);
        }
    }
    
    // 锁定分配
	public static void lockAssignCostAccount(Context ctx) throws BOSException {
		lockAssignAccount(ctx, "assignCostAccount");
	}
    
  //收入科目 锁定分配
    public static void lockAssignIncomeAccount(Context ctx) throws BOSException{
    	lockAssignAccount(ctx, "assignIncomeAccount"); 
    }    
    
	/**
	 * 合同最新造价:金额变更部分
	 * @throws UuidException 
	 * @throws EASBizException 
	 */
	public static BigDecimal getContractLastPrice (Context ctx,String contractId,boolean needCon) throws BOSException, SQLException, EASBizException, UuidException{
		BigDecimal lastAmount = FDCHelper.ZERO;
		boolean hasSettled = false;
		IContractBill iContractBill = null;
		IContractChangeBill iContractChangeBill = null;
		if(ctx==null){
			iContractBill = ContractBillFactory.getRemoteInstance();
			iContractChangeBill = ContractChangeBillFactory.getRemoteInstance();
		}else{
			iContractBill = ContractBillFactory.getLocalInstance(ctx);
			iContractChangeBill = ContractChangeBillFactory.getLocalInstance(ctx);
		}
		
		//包含合同金额
		if(needCon){
			SelectorItemCollection selector=new SelectorItemCollection();
			selector.add("amount");
			selector.add("hasSettled");
			selector.add("settleAmt");
			
			ContractBillInfo contract = iContractBill.getContractBillInfo(
					new ObjectUuidPK(BOSUuid.read(contractId)),selector);
			
			hasSettled = contract.isHasSettled();
			if (contract.isHasSettled()) {
				lastAmount = contract.getSettleAmt();				
			} else {
				lastAmount = contract.getAmount();
			}
			if(lastAmount==null){
				lastAmount=FDCHelper.ZERO;
			}
		}
		
		//还没有结算
		if(!hasSettled){		
			//变更
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractId));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.VISA_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.ANNOUNCE_VALUE));
			filter.setMaskString("#0 and (#1 or #2 or #3)");
			view.setFilter(filter);
			view.getSelector().add("amount");
			view.getSelector().add("balanceAmount");
			view.getSelector().add("hasSettled");
			IObjectCollection collection = iContractChangeBill.getContractChangeBillCollection(view);
			ContractChangeBillInfo billInfo;
	
			BigDecimal changeAmount = FDCHelper.ZERO;
			for (Iterator iter = collection.iterator(); iter.hasNext();) {
				billInfo = (ContractChangeBillInfo) iter.next();
				if (billInfo.getAmount() != null) {
					if(billInfo.isHasSettled()){
						if(billInfo.getBalanceAmount()!=null) //by tim_gao
						changeAmount = changeAmount.add(billInfo.getBalanceAmount());
					}else{
						changeAmount = changeAmount.add(billInfo.getAmount());
					}
				}
			}
			
			if(changeAmount==null){
				changeAmount =FDCHelper.ZERO;
			}
			
			lastAmount = lastAmount.add(changeAmount);
		}

//		
//		//奖励
//		BigDecimal guerdonAmt=FDCHelper.ZERO;
//		BigDecimal compenseAmt=FDCHelper.ZERO;
//		BigDecimal deductAmt=FDCHelper.ZERO;
//		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
//		builder.appendSql("select sum(famount) as amount from T_CON_GuerdonBill where  fcontractid =? AND fstate = ? AND fisGuerdoned = 1");
//		builder.addParam(contractId);
//		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
//		IRowSet rowSet = builder.executeQuery();
//		if(rowSet.size()==1){
//			rowSet.next();
//			guerdonAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
//		}
//		
//		//违约
//		builder.clear();
//		builder.appendSql("select sum(famount) as amount from T_CON_CompensationBill where  fcontractid =? AND fstate = ? AND fisCompensated = 1");
//		builder.addParam(contractId);
//		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
//		rowSet = builder.executeQuery();
//		if(rowSet.size()==1){
//			rowSet.next();
//			compenseAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
//		}
//		
//		//扣款
//		builder.clear();
//		builder.appendSql("select sum(famount) as amount from T_CON_DeductOfPayReqBill " +
//				"where fpayRequestBillId in (select fid from T_CON_PayRequestBill where fcontractid=?)");
//		builder.addParam(contractId);
//		rowSet = builder.executeQuery();
//		if(rowSet.size()==1){
//			rowSet.next();
//			deductAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("amount"));
//		}
		
		/*
		 * 万科最新要求,未结算的合同最新造价=合同金额+变更金额+奖励-索赔,扣款(已被申请单关联的) 
		 * by sxhong 2007/09/28
		 */
		//BigDecimal lastAmount = conAmount.add(FDCHelper.toBigDecimal(changeAmount));
//		return  changeAmount.add(guerdonAmt).subtract(compenseAmt).subtract(deductAmt);
		
		//万科最新要求 20080801 取消奖励-索赔,扣款
		
		return  lastAmount;
	}

	/**
	 * 
	 * @param dateType
	 * @param yearFrom
	 * @param yearTo
	 * @param monthFrom
	 * @param MonthTo
	 * @return
	 */
	public static Date getBeginQueryDate(int dateType,int yearFrom,int monthFrom) {
		Date date = null;
		if (dateType == 0) {
			//date = para.getDate(DATE_FROM);
		} else if (dateType == 1) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, yearFrom);
			cal.set(Calendar.MONTH, monthFrom - 1);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		} else if (dateType == 2) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, yearFrom);
			cal.set(Calendar.MONTH, (monthFrom-1) * 3 );
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		} else if (dateType == 3) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, yearFrom);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		}
		return DateTimeUtils.truncateDate(date);
	}
	
	public static Date getEndQueryDate(int dateType,int yearTo,int monthTo) {
		Date date = null;
		if (dateType == 0) {
			//date = para.getDate(DATE_TO);
		} else if (dateType == 1) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR,yearTo);
			cal.set(Calendar.MONTH, monthTo);
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		} else if (dateType == 2) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, 		yearTo);
			cal.set(Calendar.MONTH, monthTo* 3);
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		} else if (dateType == 3) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, yearTo+1);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		}
		return DateTimeUtils.truncateDate(date);
	}
	
	/**
	 * 取单个合同的最新造价，调用批量取数方法
	 * @param ctx
	 * @param contractId
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static BigDecimal getContractLastAmt(Context ctx,String contractId) throws BOSException, EASBizException {
		Map map=(Map)FDCUtils.getLastAmt_Batch(ctx, new String[]{contractId});
		BigDecimal latestPrice=(BigDecimal)map.get(contractId);
		return latestPrice;
	}
	
	public static boolean isIncludeChangeAudit(Context ctx){
		boolean isIncludeChangeAudit = false;
		try {
			if(ctx == null){
				isIncludeChangeAudit = getDefaultFDCParamByKey(null,null,FDCConstants.FDC_PARAM_INCLUDECHANGEAUDIT);	
			}else{
				isIncludeChangeAudit = getDefaultFDCParamByKey(ctx,null,FDCConstants.FDC_PARAM_INCLUDECHANGEAUDIT);
			}
			
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isIncludeChangeAudit;
	}

	/**
	 * 批量-取合同最新本位币造价（如果已结算，则取结算价；否则取合同金额+变更金额+奖励-索赔-扣款(已被申请单关联的)）
	 * <p>
	 * 此算法后面有改动：合同最新造价，如果已结算，则取结算价；否则取 合同金额+变更金额 By Owen_wen 2011-07-12
	 * 
	 * @param String
	 *            []
	 * @return Map
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static Map getLastAmt_Batch(Context ctx,String[] contractIdList) throws BOSException, EASBizException {
		if(contractIdList==null || contractIdList.length==0){
			return new HashMap();
		}
		BigDecimal bdZero = FDCNumberHelper.ZERO;
		String noSettleContractIdList =  null;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		try {
			builder.appendSql("select FID,FHasSettled,FSettleAmt,FAmount from T_CON_ContractBill where ");
			builder.appendParam("fid", contractIdList, "varchar(44)");

			IRowSet rs = builder.executeQuery();
			if (rs == null || rs.size() == 0) {
				return new HashMap();
			}
			Map lastAmtMap = new HashMap(rs.size());
			try {
				while (rs.next()) {
					String contractId = rs.getString("FID");
					BigDecimal contractAmount = FDCNumberHelper.ZERO;
					if (rs.getBoolean("FHasSettled")) {
						contractAmount = rs.getBigDecimal("FSettleAmt");
					} else {
						if (noSettleContractIdList == null) {
							noSettleContractIdList = contractId;
						} else {
							noSettleContractIdList = noSettleContractIdList + "," + contractId;
						}
						contractAmount = rs.getBigDecimal("FAmount");
					}
					lastAmtMap.put(contractId, contractAmount == null ? bdZero : contractAmount);
				}
				/*
				 * 万科最新要求,未结算的合同最新造价=合同金额+变更金额+奖励-索赔,扣款(已被申请单关联的) by sxhong 2007/09/28
				 */
				if (noSettleContractIdList != null) {
					String[] noSettleContractIdArray = FDCHelper.stringToStrArray(noSettleContractIdList);
					// 变更

					if (isIncludeChangeAudit(ctx)) {
						builder.clear();
						builder.appendSql("select FContractBillID,sum(fchangeAmount) as changeAmount from ( ");
						builder.appendSql("select FContractBillID,FBalanceAmount as fchangeAmount from T_CON_ContractChangeBill ");
						builder.appendSql("where FHasSettled=1 and ");
						builder.appendParam("FContractBillID", noSettleContractIdArray, "varchar(44)");
						builder.appendSql(" and (");
						builder.appendParam("FState", FDCBillStateEnum.AUDITTED_VALUE);
						builder.appendSql(" or ");
						builder.appendParam("FState", FDCBillStateEnum.VISA_VALUE);
						builder.appendSql(" or ");
						builder.appendParam("FState", FDCBillStateEnum.ANNOUNCE_VALUE);
						builder.appendSql(" ) union all ");
						builder.appendSql("select FContractBillID,FAmount as fchangeAmount from T_CON_ContractChangeBill ");
						builder.appendSql("where FHasSettled=0 and ");
						builder.appendParam("FContractBillID", noSettleContractIdArray, "varchar(44)");
						builder.appendSql(" and (");
						builder.appendParam("FState", FDCBillStateEnum.AUDITTED_VALUE);
						builder.appendSql(" or ");
						builder.appendParam("FState", FDCBillStateEnum.VISA_VALUE);
						builder.appendSql(" or ");
						builder.appendParam("FState", FDCBillStateEnum.ANNOUNCE_VALUE);
						builder.appendSql(" )) change group by FContractBillID");
						rs = builder.executeQuery();
						while (rs.next()) {
							String contractId = rs.getString("FContractBillID");
							BigDecimal changeAmount = rs.getBigDecimal("changeAmount");
							if (lastAmtMap.containsKey(contractId) && changeAmount != null) {
								lastAmtMap.put(contractId, ((BigDecimal) lastAmtMap.get(contractId)).add(changeAmount));
							}
						}
					}
				}
			} catch (SQLException e) {
				throw new BOSException(e);
			}
			return lastAmtMap;
		} finally {
			builder.releasTempTables();
		}
	}

	/**
	 * 批量-取合同最新原币造价（如果已结算，则取结算价；否则取合同金额+变更金额+奖励-索赔-扣款(已被申请单关联的)）
	 * <p>
	 * 此算法后面有改动：合同最新造价，如果已结算，则取结算价；否则取 合同金额+变更金额 By Owen_wen 2011-07-12
	 * 
	 * @param String
	 *            []
	 * @return Map
	 * @throws BOSException
	 * @throws SQLException
	 */
	public static Map getLastOriginalAmt_Batch(Context ctx,String[] contractIdList) throws Exception {
		if(contractIdList==null || contractIdList.length==0){
			return new HashMap();
		}
		BigDecimal bdZero = FDCNumberHelper.ZERO;
		String noSettleContractIdList =  null;		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		try {
			builder
					.appendSql("select fcontractbillid,FTotalOriginalAmount as FTotalOriginalAmount from t_con_contractsettlementbill where FIsFinalSettle=1 and ");
			builder.appendParam("fcontractbillid", contractIdList, "varchar(44)");

			IRowSet rs = builder.executeQuery();
			Map lastAmtMapSettlement = new HashMap();
			if (rs != null && rs.size() > 0) {
				while (rs.next()) {
					lastAmtMapSettlement.put(rs.getString("fcontractbillid"), rs.getBigDecimal("FTotalOriginalAmount"));
				}
			}

			builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select FID,FHasSettled,FSettleAmt,FOriginalAmount from T_CON_ContractBill where ");
			builder.appendParam("fid", contractIdList, "varchar(44)");
			rs = builder.executeQuery();
			if (rs == null || rs.size() == 0) {
				return new HashMap();
			}
			Map lastAmtMap = new HashMap(rs.size());
			while (rs.next()) {
				String contractId = rs.getString("FID");
				BigDecimal contractAmount = FDCNumberHelper.ZERO;
				if (lastAmtMapSettlement.containsKey(contractId)) {
					contractAmount = (BigDecimal) lastAmtMapSettlement.get(contractId);
				} else {
					if (noSettleContractIdList == null) {
						noSettleContractIdList = contractId;
					} else {
						noSettleContractIdList = noSettleContractIdList + "," + contractId;
					}
					contractAmount = rs.getBigDecimal("FOriginalAmount");
				}
				lastAmtMap.put(contractId, contractAmount == null ? bdZero : contractAmount);
			}
			/*
			 * 万科最新要求,未结算的合同最新造价=合同金额+变更金额+奖励-索赔,扣款(已被申请单关联的) by sxhong 2007/09/28
			 */
			if (noSettleContractIdList != null) {
				if (isIncludeChangeAudit(ctx)) {
					String[] noSettleContractIdArray = FDCHelper.stringToStrArray(noSettleContractIdList);
					// 变更
					builder.clear();
					builder.appendSql("select FContractBillID,sum(fchangeAmount) as changeAmount from ( ");
					// 改为取原币FBalanceAmount--》FOriBalanceAmount
					builder.appendSql("select FContractBillID,FOriBalanceAmount as fchangeAmount from T_CON_ContractChangeBill ");
					builder.appendSql("where FHasSettled=1 and ");
					builder.appendParam("FContractBillID", noSettleContractIdArray, "varchar(44)");
					builder.appendSql(" and (");
					builder.appendParam("FState", FDCBillStateEnum.AUDITTED_VALUE);
					builder.appendSql(" or ");
					builder.appendParam("FState", FDCBillStateEnum.VISA_VALUE);
					builder.appendSql(" or ");
					builder.appendParam("FState", FDCBillStateEnum.ANNOUNCE_VALUE);
					builder.appendSql(" ) union all ");
					// 改为取原币FAmount --> FOriginalAmount
					builder.appendSql("select FContractBillID,FOriginalAmount as fchangeAmount from T_CON_ContractChangeBill ");
					builder.appendSql("where FHasSettled=0 and ");
					builder.appendParam("FContractBillID", noSettleContractIdArray, "varchar(44)");
					builder.appendSql(" and (");
					builder.appendParam("FState", FDCBillStateEnum.AUDITTED_VALUE);
					builder.appendSql(" or ");
					builder.appendParam("FState", FDCBillStateEnum.VISA_VALUE);
					builder.appendSql(" or ");
					builder.appendParam("FState", FDCBillStateEnum.ANNOUNCE_VALUE);
					builder.appendSql(" )) change group by FContractBillID");

					rs = builder.executeQuery();
					while (rs.next()) {
						String contractId = rs.getString("FContractBillID");
						BigDecimal changeAmount = rs.getBigDecimal("changeAmount");
						if (lastAmtMap.containsKey(contractId) && changeAmount != null) {
							lastAmtMap.put(contractId, ((BigDecimal) lastAmtMap.get(contractId)).add(changeAmount));
						}
					}
				}
			}
			return lastAmtMap;
		} finally {
			builder.releasTempTables();
		}
	}
	
	/***
	 * 递归找到成本科目父级对应的会计科目
	 * @param key
	 * @param costAccountInfo
	 * @param costAccountMap
	 * @param costAccountWithAccountMap
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private static Object getCostAccountWithAccount(String key,CostAccountInfo costAccountInfo,Map costAccountMap,Map costAccountWithAccountMap) throws BOSException, EASBizException{
		if(costAccountInfo.getParent()==null)
			return null;
//			throw new ContractException(
//					ContractException.CANNOTFINDCOSTACCOUNTWITHACCOUNT);
		key = costAccountInfo.getCurProject().getId().toString() + "_" + costAccountInfo.getParent().getId().toString();
		if(costAccountWithAccountMap.containsKey(key))
			return costAccountWithAccountMap.get(key);
		else{
			if(costAccountMap.containsKey(costAccountInfo.getParent().getId().toString())){
				costAccountInfo = (CostAccountInfo)costAccountMap.get(costAccountInfo.getParent().getId().toString());
				return getCostAccountWithAccount(key,costAccountInfo,costAccountMap,costAccountWithAccountMap);
			}
			else
				throw new BOSException();//can't arrive
		}
			
	}
	private static Object getCostAccountWithAccount(String key,CostAccountInfo costAccountInfo,Map costAccountMap,Map costAccountWithAccountMap,boolean keyIsCostAccountLongNumber) throws BOSException, EASBizException{
		if(costAccountInfo.getParent()==null)
			return null;
//			throw new ContractException(
//					ContractException.CANNOTFINDCOSTACCOUNTWITHACCOUNT);
		key = costAccountInfo.getCurProject().getId().toString() + "_" + costAccountInfo.getParent().getId().toString();
		String key2 = key;
		if(keyIsCostAccountLongNumber)
			key2 = costAccountInfo.getCurProject().getId().toString()+ "_" + costAccountInfo.getLongNumber();
		if(costAccountWithAccountMap.containsKey(key2))
			return costAccountWithAccountMap.get(key2);
		else{
			if(costAccountMap.containsKey(costAccountInfo.getParent().getId().toString())){
				costAccountInfo = (CostAccountInfo)costAccountMap.get(costAccountInfo.getParent().getId().toString());
				return getCostAccountWithAccount(key2,costAccountInfo,costAccountMap,costAccountWithAccountMap,keyIsCostAccountLongNumber);
			}
			else
				throw new BOSException();//can't arrive
		}
			
	}
	/**
	 * @param ctx
	 * @param curProjectIds
	 * @param view
	 * @return
	 * @throws BOSException
	 * @throws ContractException
	 */
	public static Map getCostAccountMap(Context ctx, Set curProjectIds) throws BOSException, ContractException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		Iterator it;
		/***
		 * 取工程项目下所有的成本科目
		 */
		Map costAccountMap = new HashMap();
		ICostAccount iCostAccount = null;
		if(ctx==null)
			iCostAccount = CostAccountFactory.getRemoteInstance();
		else
			iCostAccount = CostAccountFactory.getLocalInstance(ctx);
		view.getSelector().clear();
		view.getSelector().add("id");
		view.getSelector().add("longNumber");
		view.getSelector().add("level");
		view.getSelector().add("parent.id");
		//view.getSelector().add("parent.name");
		view.getSelector().add("parent.level");
		view.getSelector().add("curProject.id");
		//view.getSelector().add("curProject.name");
		view.getFilter().getFilterItems().clear();
		//由于curProjectIds有Map.keySet()获得，无法序列化，会导致NotSerializableException，故先转换为HashSet(可序列化)
		curProjectIds = new HashSet(curProjectIds);
		view.getFilter().getFilterItems().add(new FilterItemInfo("curProject.id",curProjectIds,CompareType.INCLUDE));
		CostAccountCollection costAccountColl = iCostAccount.getCostAccountCollection(view);
		/****
		 * 
		 */
//		if(costAccountColl==null||costAccountColl.size()==0)
//			throw new ContractException(
//					ContractException.CANNOTFINDCOSTACCOUNT);
		for(it=costAccountColl.iterator();it.hasNext();){
			CostAccountInfo costAccountInfo = (CostAccountInfo)it.next();
			if(costAccountInfo.getId().toString().equals("959+ZAEdEADgABR3wKgQs4Qj/24=")){
				System.out.print("959+ZAEdEADgABR3wKgQs4Qj/24=");
			}
			costAccountMap.put(costAccountInfo.getId().toString(),costAccountInfo);
		}
		return costAccountMap;
	}
	/**
	 * @param ctx
	 * @param curProjectIds
	 * @param view
	 * @return
	 * @throws BOSException
	 * @throws ContractException
	 */
	public static Map getCostAccountWithAccountMap(Context ctx, Set curProjectIds) throws BOSException, ContractException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		Iterator it;
		/***
		 * 取成本科目对应的会计科目
		 */
		Map costAccountWithAccountMap = new HashMap();
		ICostAccountWithAccount iCostAccountWithAccount = null;
		if(ctx==null)
			iCostAccountWithAccount = CostAccountWithAccountFactory.getRemoteInstance();
		else
			iCostAccountWithAccount = CostAccountWithAccountFactory.getLocalInstance(ctx);
		view.getSelector().clear();
		
		view.getSelector().add("costAccount.id");
		view.getSelector().add("costAccount.longnumber");
		view.getSelector().add("costAccount.name");
		view.getSelector().add("costAccount.level");
		view.getSelector().add("costAccount.parent.*");
		view.getSelector().add("costAccount.curProject.id");
		view.getSelector().add("costAccount.curProject.name");	
		view.getSelector().add("costAccount.curProject.longnumber");
		view.getSelector().add("costAccount.curProject.fullOrgUnit.id");
		view.getSelector().add("costAccount.curProject.fullOrgUnit.name");
		view.getSelector().add("account.id");
		view.getSelector().add("account.number");
		view.getSelector().add("account.name"); 
		//将付款情况统计表从530同步到600所需添加   by ling_peng 2009-6-24
		view.getSelector().add("account.longnumber");
		view.getSelector().add("account.level");
		view.getSelector().add("account.isLeaf");
		
		view.getSelector().add("account.DC");
		view.getFilter().getFilterItems().clear();
		//从调用方法传递过来的curProjectIds是由Map.keySet()方法获得，无法序列化，必须转换。
		curProjectIds=new  HashSet(curProjectIds);
		view.getFilter().getFilterItems().add(new FilterItemInfo("costAccount.curProject.id",curProjectIds,CompareType.INCLUDE));
		CostAccountWithAccountCollection costAccountWithAccountColl = iCostAccountWithAccount.getCostAccountWithAccountCollection(view);
		if(costAccountWithAccountColl==null||costAccountWithAccountColl.size()==0)
			throw new ContractException(
					ContractException.CANNOTFINDCOSTACCOUNTWITHACCOUNT);
		for(it=costAccountWithAccountColl.iterator();it.hasNext();){
			CostAccountWithAccountInfo info = (CostAccountWithAccountInfo)it.next();
			String key = info.getCostAccount().getCurProject().getId().toString();
			key += "_" + info.getCostAccount().getId().toString();
			costAccountWithAccountMap.put(key,info);
		}
		return costAccountWithAccountMap;
	}
	/***
	 * 
	 * @param ctx
	 * @param curProjectIds
	 * @param keyIsCostAccountLongNumber
	 * @return
	 * @throws BOSException
	 * @throws ContractException
	 */
	public static Map getCostAccountWithAccountMap(Context ctx, Set curProjectIds,boolean keyIsCostAccountLongNumber) throws BOSException, ContractException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		Iterator it;
		/***
		 * 取成本科目对应的会计科目
		 */
		Map costAccountWithAccountMap = new HashMap();
		ICostAccountWithAccount iCostAccountWithAccount = null;
		if(ctx==null)
			iCostAccountWithAccount = CostAccountWithAccountFactory.getRemoteInstance();
		else
			iCostAccountWithAccount = CostAccountWithAccountFactory.getLocalInstance(ctx);
		view.getSelector().clear();
		
		view.getSelector().add("costAccount.id");
		view.getSelector().add("costAccount.longnumber");
		view.getSelector().add("costAccount.name");
		view.getSelector().add("costAccount.level");
		view.getSelector().add("costAccount.parent.*");
		view.getSelector().add("costAccount.curProject.id");
		view.getSelector().add("costAccount.curProject.name");	
		view.getSelector().add("costAccount.curProject.longnumber");
		view.getSelector().add("costAccount.curProject.fullOrgUnit.id");
		view.getSelector().add("costAccount.curProject.fullOrgUnit.name");
		view.getSelector().add("account.id");
		view.getSelector().add("account.number");
		view.getSelector().add("account.name"); 
		//将付款情况统计表从530同步到600所需添加   by ling_peng 2009-6-24
		view.getSelector().add("account.longnumber");
		view.getSelector().add("account.level");
		view.getSelector().add("account.isLeaf");
		
		view.getSelector().add("account.DC");
		view.getFilter().getFilterItems().clear();
		//从调用方法传递过来的curProjectIds是由Map.keySet()方法获得，无法序列化，必须转换。
		curProjectIds=new  HashSet(curProjectIds);
		view.getFilter().getFilterItems().add(new FilterItemInfo("costAccount.curProject.id",curProjectIds,CompareType.INCLUDE));
		CostAccountWithAccountCollection costAccountWithAccountColl = iCostAccountWithAccount.getCostAccountWithAccountCollection(view);
		if(costAccountWithAccountColl==null||costAccountWithAccountColl.size()==0)
			throw new ContractException(
					ContractException.CANNOTFINDCOSTACCOUNTWITHACCOUNT);
		for(it=costAccountWithAccountColl.iterator();it.hasNext();){
			CostAccountWithAccountInfo info = (CostAccountWithAccountInfo)it.next();
			String key = info.getCostAccount().getCurProject().getId().toString();
			if(keyIsCostAccountLongNumber)
				key += "_" + info.getCostAccount().getLongNumber();
			else
				key += "_" + info.getCostAccount().getId().toString();
			costAccountWithAccountMap.put(key,info);
		}
		return costAccountWithAccountMap;
	}
	/**
	 * 获取工程项目下的所有成本科目对应的会计科目的map
	 * key   = curProjectIds_costAccountIds
	 * value = AccountViewInfo
	 * @param ctx
	 * @param curProjectIds
	 * @param view
	 * @return
	 * @throws BOSException
	 * @throws ContractException
	 * @throws EASBizException
	 * @deprecated
	 * @see com.kingdee.eas.fdc.basedata.util.CostAccountWithAccountHelper.getCostAcctWithAcctMapByCostAccountIds
	 */
	public static Map getCostAccountWithAccountMapAll(Context ctx, Set curProjectIds,Set costAccountIds) throws BOSException, ContractException, EASBizException {
		return getCostAccountWithAccountMapAll(ctx,curProjectIds,false);
	}
	/***
	 * 
	 * @param ctx
	 * @param curProjectIds
	 * @param keyIsCostAccountLongNumber
	 * @return
	 * @throws BOSException
	 * @throws ContractException
	 * @throws EASBizException
	 */
	public static Map getCostAccountWithAccountMapAll(Context ctx, Set curProjectIds,boolean keyIsCostAccountLongNumber) throws BOSException, ContractException, EASBizException {
		if(curProjectIds==null||curProjectIds.size()==0)
			return new HashMap();
		Iterator it;
		Map costAccountWithAccountMap = getCostAccountWithAccountMap(ctx, curProjectIds,keyIsCostAccountLongNumber);
		Map costAccountMap = getCostAccountMap(ctx, curProjectIds);
		/***
		 * 处理成本科目对应的会计科目
		 * 例如：	工程项目1
		 * 	1		成本科目1       -------   会计科目1
		 * 	2		 |+明细成本科目1 -------   |+明细会计科目1
		 * 	3		 |+明细成本科目2 -------   【没有对应】
		 * 目前  
		 * 	costAccountWithAccountMap 保存了1，2
		 * 	costAccountMap 保存了3明细成本科目2 
		 * 
		 * 需要把
		 *       明细成本科目2 --------- 会计科目1
		 *       放入costAccountWithAccountMap
		 *       
		 * 最终costAccountWithAccountMap的结果
		 * 	1		成本科目1       -------   会计科目1
		 * 	2		 |+明细成本科目1 -------   |+明细会计科目1
		 * 	3		 |+明细成本科目2 -------   |+会计科目1
		 */
		Set costAccountKeys = costAccountMap.keySet();
		
		for(it=costAccountKeys.iterator();it.hasNext();){
			String key = (String) it.next();
			CostAccountInfo costAccountInfo = (CostAccountInfo)costAccountMap.get(key);
			key = costAccountInfo.getCurProject().getId().toString() + "_" + costAccountInfo.getId().toString();
			String key2 = key;
			if(keyIsCostAccountLongNumber)
				key2 = costAccountInfo.getCurProject().getId().toString() + "_" + costAccountInfo.getLongNumber();
			if(!costAccountWithAccountMap.containsKey(key2)){
				costAccountWithAccountMap.put(key2,getCostAccountWithAccount(key2,costAccountInfo,costAccountMap,costAccountWithAccountMap,keyIsCostAccountLongNumber));
			}
		}
		return costAccountWithAccountMap;
	}
	
    /* 已经启用调整凭证模式，用于控制财务成本一体化时用调整凭证生
    * @param ctx
    * @return
    * @throws EASBizException
    * @throws BOSException
    */
   public static boolean isAdjustVourcherModel(Context ctx,String companyId) throws EASBizException, BOSException{
   	return getDefaultFDCParamByKey(ctx, companyId, FDCConstants.FDC_PARAM_ADJUSTVOURCHER);
   }
   /**
    * 获得房地产系统参数的是否启用简单模式财务成本一体化
    * 
    * @param ctx
    * @param companyID
    * @return boolean
    */
   public static boolean IsSimpleFinacial(Context ctx, String companyID) throws BOSException, EASBizException {
   	boolean isFinacial = false;
   	HashMap param = getDefaultFDCParam(ctx, companyID);			
		if(param.get(FDCConstants.FDC_PARAM_SIMPLEFINACIAL.toString())!=null){
			isFinacial = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SIMPLEFINACIAL).toString()).booleanValue();
		}
       return isFinacial;
   }
   /**
    * 获得房地产系统参数的是否启用简单模式财务成本一体化处理扣款、违约、奖励
    * 
    * @param ctx
    * @param companyID
    * @return boolean
    */
   public static boolean IsSimpleFinacialExtend(Context ctx, String companyID) throws BOSException, EASBizException {
   	boolean isFinacialExtend = false;
   	HashMap param = getDefaultFDCParam(ctx, companyID);			
		if(param.get(FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND.toString())!=null){
			isFinacialExtend = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND).toString()).booleanValue();
		}
       return isFinacialExtend;
   }
   
   /**
    * 获得房地产系统参数的是否启用成本反月结
    * 
    * @param ctx
    * @param companyID
    * @return boolean
    */
   public static boolean IsUnInCorporation(Context ctx, String companyID) throws BOSException, EASBizException {
   	boolean isUnInCorporation = false;
   	HashMap param = getDefaultFDCParam(ctx, companyID);
   	if(param.get(FDCConstants.FDC_PARAM_UNINCORPORATION.toString())!=null){
   		isUnInCorporation = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_UNINCORPORATION).toString()).booleanValue();
   	}
   	return isUnInCorporation;
   }
   
   /**
    * 合同或无文本，是否进入动态成本
    * @param ctx
    * @param contractID
    * @return
    * @throws BOSException
    * @throws EASBizException
    * @throws SQLException 
    */
   public static boolean isCostSplit(Context ctx, String contractID) throws BOSException,EASBizException, SQLException{
	   boolean isCostSplit = false;
	   if(contractID==null)
		   return isCostSplit;
	   FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
	   String table = isContractBill(ctx, contractID)?"t_con_contractBill":"t_con_contractwithouttext";
	   builder.appendSql("select fiscostsplit from "+table+" where fid =? ");
	   builder.addParam(contractID);
	   IRowSet rowSet = builder.executeQuery();
	   if(rowSet.size()==1){
		   rowSet.next();
		   isCostSplit = rowSet.getBoolean("fiscostsplit");
	   }
	   return isCostSplit;
   }
   
   /**
    * 判断是合同或无文本合同
    * @param ctx
    * @param contractID
    * @return true合同,false无文本合同
    * @throws BOSException
    * @throws EASBizException
    */
   public static boolean isContractBill(Context ctx, String contractID)
			throws BOSException, EASBizException {
		return  contractID==null?false:(new ContractBillInfo().getBOSType()).equals(BOSUuid.read(contractID).getType());
   }
   
   /**
	 * 当前用户权限组织下的工程项目
	 * @author pengwei_hou date:2009-07-18
	 * @param ctx
	 * @param orgUnitIDSet
	 * @return map
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static Map getOrgUnitProjects(Context ctx, String objectID)
			throws BOSException, EASBizException {
		BOSObjectType CurProjectType = (new CurProjectInfo()).getBOSType();
		BOSObjectType FullOrgUnitType = (new FullOrgUnitInfo()).getBOSType();
		BOSObjectType ObjectType = BOSUuid.read(objectID).getType();
		Set idSet = new HashSet();

		Map retMap = new HashMap();
		ICurProject iCurProject = null;
		CurProjectCollection colls = null;
		if (ctx == null) {
			iCurProject = CurProjectFactory.getRemoteInstance();
		} else {
			iCurProject = CurProjectFactory.getLocalInstance(ctx);
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("name");
		SorterItemInfo sort = new SorterItemInfo("longNumber");
		sort.setSortType(SortType.ASCEND);
		view.getSorter().add(sort);
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.valueOf(true)));
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", new Integer(1)));
		if (CurProjectType.equals(ObjectType)) {
			filter.getFilterItems().add(new FilterItemInfo("id", objectID));
		} else if (FullOrgUnitType.equals(ObjectType)) {
			if(OrgConstants.DEF_CU_ID.equals(objectID)){
				//集团,当前用户有权限组织对应的工程项目
				idSet = getAuthorizedOrgs(ctx);
				//成本中心下存在多级工程项目情况
				filter.getFilterItems().add(
						new FilterItemInfo("fullOrgUnit.id", idSet,
								CompareType.INCLUDE));
				filter.getFilterItems().add(
						new FilterItemInfo("costCenter.id", idSet,
								CompareType.INCLUDE));
				filter.setMaskString(" #0 and #1 and (#2 or #3) ");
			} else{
				// 取组织及下级财务组织
				idSet = getCompanyOrgUnitIDSet(ctx, objectID);
				if (idSet.size() == 1) {
					//成本中心
					filter.getFilterItems().add(
							new FilterItemInfo("costCenter.id", idSet,
									CompareType.INCLUDE));
				} else {
					//成本中心下存在多级工程项目情况
					filter.getFilterItems().add(
							new FilterItemInfo("fullOrgUnit.id", idSet,
									CompareType.INCLUDE));
					filter.getFilterItems().add(
							new FilterItemInfo("costCenter.id", idSet,
									CompareType.INCLUDE));
					filter.setMaskString(" #0 and #1 and (#2 or #3) ");
				}
			}
		} 
		colls = iCurProject.getCurProjectCollection(view);
		if (colls != null) {
			for (Iterator iter = colls.iterator(); iter.hasNext();) {
				CurProjectInfo info = (CurProjectInfo) iter.next();
				retMap.put(info.getId().toString(), info);
			}
		}
		retMap.put("colls", colls);
		return retMap;
	}
	
	/**
	 * 返回下级所有财务组织
	 * @param ctx
	 * @param objctID 传入一组织ID
	 * @return 返回该组织下级所有财务组织
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private static Set getCompanyOrgUnitIDSet(Context ctx, String objectID)
			throws BOSException, EASBizException {
		Set leafIDSet = new HashSet();
		leafIDSet.add(objectID);
		IFullOrgUnit iFullOrgUnit = null;
		FullOrgUnitCollection colls = null;
		if (ctx == null) {
			iFullOrgUnit = FullOrgUnitFactory.getRemoteInstance();
		} else {
			iFullOrgUnit = FullOrgUnitFactory.getLocalInstance(ctx);
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		SelectorItemCollection sic = view.getSelector();
		sic.add(new SelectorItemInfo("*"));
		//TODO
		//只需要id,parent.id为什么selector属性不行?
		
//		view.getSelector().add("id");
//		view.getSelector().add("parent.id");
//		filter.getFilterItems().add(
//				new FilterItemInfo("isFreeze", Boolean.valueOf(false)));
//		filter.getFilterItems().add(
//				new FilterItemInfo("isCompanyOrgUnit", Boolean.valueOf(true)));
//		filter.getFilterItems().add(
//				new FilterItemInfo("isCostOrgUnit", Boolean.valueOf(true)));
		colls = iFullOrgUnit.getFullOrgUnitCollection(view);
		Map companyMap = new HashMap();
		if (colls != null) {
			for (int i=0;i<colls.size();i++) {
				FullOrgUnitInfo info = (FullOrgUnitInfo)colls.get(i);
				companyMap.put(info.getId().toString(), info);
			}
			companyMap.remove(objectID);
			return getCompanyIDSet(companyMap,leafIDSet,objectID);
		}
		return new HashSet();
	}
	
	private static Set getCompanyIDSet(Map companyMap, Set leafIDSet,String orgUnitID){
		for (Iterator iter = companyMap.keySet().iterator(); iter.hasNext();) {
			FullOrgUnitInfo info = (FullOrgUnitInfo)companyMap.get(iter.next());
			if(info!=null){
				if(info.getParent()!=null&&leafIDSet.contains(info.getParent().getId().toString())){
					leafIDSet.add(info.getId().toString());
					companyMap.remove(info.getId().toString());
					return getCompanyIDSet(companyMap,leafIDSet,info.getId().toString());
				} else{
					
				}
			}
		}
		return leafIDSet;
	}
	
	/**
	 * 用户权限组织
	 * 
	 * @param ctx
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws SQLException
	 */
	public static Set getAuthorizedOrgs(Context ctx) throws BOSException,
			EASBizException {
		IPermission iPermission = null;
		BOSUuid userID = null;
		if (ctx == null) {
			iPermission = PermissionFactory.getRemoteInstance();
			userID = SysContext
			.getSysContext().getCurrentUserInfo().getId();
		} else {
			iPermission = PermissionFactory.getLocalInstance(ctx);
			userID = ContextUtil.getCurrentUserInfo(ctx).getId();
		}
		Set authorizedOrgs = new HashSet();
		Map orgs = null;
		/*if (orgs == null) {
			orgs = iPermission.getAuthorizedOrgs(new ObjectUuidPK(userID),
					OrgType.CostCenter, null, null, null);
		}*/
		//update by renliang
		orgs = iPermission.getAuthorizedOrgs(new ObjectUuidPK(userID),
				OrgType.CostCenter, null, null, null);
		
		if (orgs != null) {
			Set orgSet = orgs.keySet();
			Iterator it = orgSet.iterator();
			while (it.hasNext()) {
				authorizedOrgs.add(it.next());
			}
		}
		return authorizedOrgs;
	}
	
	public static boolean isBillAudited(FDCBillInfo info) {
		return info != null && FDCBillStateEnum.AUDITTED == info.getState();
	}

	public static String buildBillIds(Collection ids) {
		if (ids == null || ids.isEmpty()) {
			throw new IllegalArgumentException("param ids is empty");
		}
		StringBuffer idsSB = new StringBuffer("(");
		for (Iterator it = ids.iterator(); it.hasNext();) {
			idsSB.append("'").append(it.next().toString()).append("',");
		}
		idsSB.setLength(idsSB.length() - 1);
		return idsSB.append(")").toString();
	}
}
