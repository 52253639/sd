package com.kingdee.eas.fdc.contract.app;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.util.StringUtil;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ContractDetailDefCollection;
import com.kingdee.eas.fdc.basedata.ContractDetailDefFactory;
import com.kingdee.eas.fdc.basedata.ContractDetailDefInfo;
import com.kingdee.eas.fdc.basedata.DataTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBillEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.jdbc.rowset.IRowSet;

public class ContractBillEntryImport extends AbstractDataTransmission{

	private static String resource = "com.kingdee.eas.fdc.contract.ContractResource";
	
	ContractBillInfo  maininfo = null;//合同
	ContractBillEntryInfo info = null;//合同分录详细信息
	
	private static final String strName[] = new String[]{"备注","是否单独计算","不计成本的金额","对应主合同编码","对应主合同名称"};
	private static final String strData[] = new String[]{"40","20","30","40","40"};
	private static final String strRowKey[] = new String[]{"","lo","am","nu","na"};
	
	// 设置一次性加载数据
	public int getSubmitType() {
		return SUBMITMULTIRECTYPE;
	}
	
	protected ICoreBase getController(Context ctx)
			throws TaskExternalException {
		ICoreBase factory = null;
		try {
			//factory = ContractBillEntryFactory.getLocalInstance(ctx);
			factory = ContractBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}
	

	public String getMainField() {
		return "FCodingNumber";
	}

	//校验
	public CoreBaseInfo transmit(Hashtable htable, Context ctx)
	throws TaskExternalException {
        int seq = 0;// 设置序号
        String oldValue="",newValue="";
        List conlist = new ArrayList();
 
		try {
			int listLen = htable.size();//excel的总行数
			for(int i=0 ;i<listLen; i++){
				Hashtable subHashTable = (Hashtable)htable.get(new Integer(i));//取出第i个元素
				String conNumber = (String) ((DataToken) subHashTable.get("FCodingNumber")).data;//第i行的合同编码
				if(conNumber.trim().equals("") || conNumber==null){
					FDCTransmissionHelper.isThrow(getResource(ctx,"Import_fContractCodingNumberNotNull"));//合同编码不能为空
				}
				newValue = conNumber;
				
				if(!newValue.trim().equals(oldValue)){//新的合同  重新取合同单头和特殊的详细信息分录
					if(maininfo!=null){//取新合同的时候，先将上个合同处理掉
						this.checkDefCount(ctx);//验证上个合同的合同信息个数是否合法
						conlist.add(maininfo);//将上一个合同的信息放入list
					}
					
					//取单头
					maininfo = transmitHead(subHashTable, ctx);
					if (maininfo == null) {//没有取到 单头
						FDCTransmissionHelper.isThrow(getResource(ctx, "htbmzxtzbcz"));
					}
					
					//特殊分录
					List specialEntryList = transmitSpecialEntry(subHashTable, ctx , maininfo);
					Iterator it = specialEntryList.iterator();
					while(it.hasNext()){
						ContractBillEntryInfo cbEntry = (ContractBillEntryInfo)it.next();
			            seq = maininfo.getEntrys().size() + 1;
			            cbEntry.setSeq(seq);
			            cbEntry.setParent(maininfo);
			            maininfo.getEntrys().add(cbEntry);
					}
				}
				
				//(分录)
				ContractBillEntryInfo cbEntry = transmitEntry(subHashTable, ctx, maininfo);
	            seq = maininfo.getEntrys().size() + 1;
	            cbEntry.setSeq(seq);
	            cbEntry.setParent(maininfo);
	            maininfo.getEntrys().add(cbEntry);
	            oldValue=newValue;
	            
	    		if(i==listLen-1){//最后一行的时候
	    			this.checkDefCount(ctx);//验证最后一个合同个数是否合法
					conlist.add(maininfo);//将最后一个合同放入list
				}
			}
			
			//迭代合同
			Iterator it = conlist.iterator();
			while(it.hasNext()){
				ContractBillInfo conInfo = (ContractBillInfo)it.next();//获得一个合同
				String fParentId = conInfo.getId().toString();//分录中 FParentid 属性
				
				ContractBillEntryCollection conEntryList =  conInfo.getEntrys();//拿到 分录list
				Iterator entryIt = conEntryList.iterator();
				while(entryIt.hasNext()){
	 				ContractBillEntryInfo conEntryInfo = (ContractBillEntryInfo)entryIt.next();
					//通过过滤查询， 分录中有没有相应的记录
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("parent",fParentId,CompareType.EQUALS));
					filter.getFilterItems().add(new FilterItemInfo("detailDefID",conEntryInfo.getDetailDefID(),CompareType.EQUALS));
					filter.getFilterItems().add(new FilterItemInfo("detail",conEntryInfo.getDetail(),CompareType.EQUALS));
					filter.getFilterItems().add(new FilterItemInfo("rowkey",conEntryInfo.getRowKey(),CompareType.EQUALS));
					filter.getFilterItems().add(new FilterItemInfo("datatype",String.valueOf(conEntryInfo.getDataType().getValue()),CompareType.EQUALS));
					view.setFilter(filter);
			        ContractBillEntryInfo info = ContractBillEntryFactory.getLocalInstance(ctx).getContractBillEntryCollection(view).get(0);
					String id = "";
			        if(info!=null){
						id = info.getId().toString();
						conEntryInfo.setSeq(info.getSeq());//序号不能被更新
						conEntryInfo.setId(info.getId());//id不能更新
					}
			        //没有记录，新增
			        if (StringUtil.isEmptyString(id)) {
			        	ContractBillEntryFactory.getLocalInstance(ctx).addnew(conEntryInfo);
					} else {//有记录 ，修改
						String defId = conEntryInfo.getDetailDefID();
						ContractBillEntryFactory.getLocalInstance(ctx).update(new ObjectUuidPK(id), conEntryInfo);
					}
				}
			}
		} catch (TaskExternalException e) {
			maininfo = null;
			throw e;
		} catch (EASBizException e1) {
			maininfo = null;
		} catch (BOSException e2) {
			maininfo = null;
		}
		
		return null;
	}
	

    //合同编码
	private ContractBillInfo transmitHead(Hashtable htable, Context ctx)
	throws TaskExternalException {
		try {
			String FCodingNumber = (String) ((DataToken) htable.get("FCodingNumber")).data;//*合同编码
			//格式判断  是否合法   是否为空  是否超过长度
			FDCTransmissionHelper.valueFormat(getResource(ctx,"htbm"),FCodingNumber,"String",true,80);//合同编码
			maininfo = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(this.getView("number", FCodingNumber)).get(0);
			if(maininfo==null){
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillEntryImport_FCodingNumber"));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} 
		
		//设置值
		return maininfo;
	}
	
	
	//特殊的分录
	private List transmitSpecialEntry(Hashtable htable, Context ctx ,ContractBillInfo cbinfo)
	throws TaskExternalException, BOSException {
		List list = new ArrayList();
		
		//取值   //excel表格中后5列的值   
		String FRemark = getFieldValue(htable,"FRemark");//备注
		String isAlongCont = getFieldValue(htable,"isAlongCont");//是否单独计算
		String FAmtWithoutCost = getFieldValue(htable,"FAmtWithoutCost");//不计成本的金额
		String FMainContractNumber = getFieldValue(htable,"FMainContractNumber");//对应主合同编码
		String FMainContractNname = getFieldValue(htable,"FMainContractNname");//对应主合同名称
		
		//校验数据的格式     
		FDCTransmissionHelper.valueFormat(getResource(ctx,"beizhu"),FRemark,"String",false,600);//备注
		//拿到合同性质    合同性质为三方或补充合同时    是否单独计算,不计成本的金额,对应主合同编码必须必需要显示出来
		String FContractPropert = cbinfo.getContractPropert().getValue();
		//合同性质为三方或补充合同时    不计成本的金额   对应主合同编码必需要显示出来       单独计算是在界面上选择的，所以必录。其他无所谓
		FContractPropert = FContractPropert.trim();
		if(FContractPropert.equals("THREE_PARTY") || FContractPropert.equals("SUPPLY")){
			FDCTransmissionHelper.isBoolean(getResource(ctx,"shifoudandujisuan"), isAlongCont, true, "是","否",-1);//是否单独计算
			FDCTransmissionHelper.bdValueFormat(getResource(ctx,"bujichengbenjie"), FAmtWithoutCost, false, 15,4);//不计成本的金额
			FDCTransmissionHelper.valueFormat(getResource(ctx,"duiyinzhuhetongbianma"),FMainContractNumber,"String",true,80);//对应主合同编码
			
			//如果是单独计算的情况，则不计成本的金额不能填写。
			String iac = isAlongCont.trim();
			if(iac.equals("是") && !FAmtWithoutCost.trim().equals("")){
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillEntryImport_FAmtWithoutCost"));
			}
			//主合同编码  名称  
			String number="";
			String name="";
			try {
				//主合同编码不为空
				if(!FMainContractNumber.trim().equals("")){
					//主合同编码只能是审批过后的合同,但是如果是补充合同可以选择另外2种状态：审核中，提交    
					//主合同编码不能是自己  //不能是已结算的  //是同一个工程项目下的  //合同类型必须启用
					if(!this.cehckMainConNumber(cbinfo, FMainContractNumber, ctx)){//主合同编码填写不正确，原因是：提示一大堆
						FDCTransmissionHelper.isThrow(getResource(ctx,"zhtbmtxbzqyyrx"));
					}
					ContractBillInfo binfo = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(this.getView("number", FMainContractNumber)).get(0);
					if(binfo==null){ //主合同编码在系统中不存在
						FDCTransmissionHelper.isThrow(getResource(ctx,"zhtbmzxtzbcz"));
					}

					number = binfo.getId().toString();//主合同编码中要求填写id
					name = binfo.getName();
					if(!name.equals(FMainContractNname)&&!FMainContractNname.trim().equals("") ){
						FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillEntryImport_FMainContractNumber"));
					}
				}
				//主合同编码为空，主合同名字不为空
				if((FMainContractNumber.trim().equals("")||FMainContractNumber==null)&&!FMainContractNname.trim().equals("")){
					FDCTransmissionHelper.isThrow(getResource(ctx,"txlzhtmchmtxzhtbm"));//填写了主合同名称，必须要填写主合同编码
				}
				
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			//将特殊分录的值放入数组中
			String strValue[] = new String[5];
			strValue[0]=FRemark;
			strValue[1]=isAlongCont;
			strValue[2]=FAmtWithoutCost;
			strValue[3]=number;
			strValue[4]=name;
			
			//根据合同的类型  来查看用户填写的信息定义编码 是否在数据库中
			String contypeid = cbinfo.getContractType().getId().toString();//合同类型id
            //在合同信息中  找到  合同类型对应的合同信息
			ContractDetailDefCollection cddiList = ContractDetailDefFactory.getLocalInstance(ctx).getContractDetailDefCollection(this.getView("contracttype", contypeid));
			int len = cddiList.size();//合同信息的个数
			int ik = 0;
			if(len>0){//如果有合同信息 就不填写备注 ，但是如果没有任何的合同信息就必录备注 
				ik=1;//↓方的数组中有五个值，分别是备注，单独计算，不计成本金额，主合同编码，主合同名字  。所以又合同信息 数组下标从1 开始 
			}
			for(int i=ik;i<strName.length;i++){
				info = new ContractBillEntryInfo();//合同分录详细信息
				info.setDetail(strName[i]);//详细定义名称
				info.setRowKey(strRowKey[i]);
				info.setContent(strValue[i]);//详细内容
				DataTypeEnum rnums  = DataTypeEnum.getEnum(Integer.parseInt(strData[i]));
				info.setDataType(rnums);//数据类型
				list.add(info);
			}
		}else{//不是的情况
			FDCTransmissionHelper.isBoolean(getResource(ctx,"shifoudandujisuan"), isAlongCont, false, "是","否",-1);
			FDCTransmissionHelper.bdValueFormat(getResource(ctx,"bujichengbenjie"), FAmtWithoutCost, false, 15,4);
			FDCTransmissionHelper.valueFormat(getResource(ctx,"duiyinzhuhetongbianma"),FMainContractNumber,"String",false,80);
			
			if(!isAlongCont.trim().equals("")){//合同状态为直接合同的时候， 不能填写是否单独计算！
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillEntryImport_isAlongCont"));
			}else if(!FAmtWithoutCost.trim().equals("")){//合同状态为直接合同的时候，不能填写不计成本的金额！
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillEntryImport_FAmtWithoutCost2"));
			}else if(!FMainContractNumber.trim().equals("")){//合同状态为直接合同的时候，不能填写对应主合同编码!
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillEntryImport_FMainContractNumber2"));
			}else if(!FMainContractNname.trim().equals("")){//合同状态为直接合同的时候，不能填写对应主合同名称!
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillEntryImport_FMainContractNname"));
			}
		}
		
		return list;
	}
	
	
	//分录
	private ContractBillEntryInfo transmitEntry(Hashtable htable, Context ctx,ContractBillInfo cbinfo)
		throws TaskExternalException {
			
		info = new ContractBillEntryInfo();//合同分录详细信息
       
		String FEntrys_detailDefID = getFieldValue(htable,"FEntrys_detailDefID");//*详细信息定义编码
		String FEntrys_dataType = getFieldValue(htable,"FEntrys_dataType");//数据类型
		String FEntrys_detail = getFieldValue(htable,"FEntrys_detail");//*详细内容

		//格式判断  是否合法   是否为空  是否超过长度
		FDCTransmissionHelper.valueFormat(getResource(ctx,"xiangxidingyibianma"),FEntrys_detailDefID,"String",true,80);//详细信息定义编码
		FDCTransmissionHelper.valueFormat(getResource(ctx,"shujuleixing"),FEntrys_dataType,"String",false,40);//数据类型
		FDCTransmissionHelper.valueFormat(getResource(ctx,"xiangxileirong"),FEntrys_detail,"String",false,600);//详细内容

		//数据库校验
		ContractDetailDefInfo cddinfo = null;//详细定义编码定义
		try {
	        //首先根据合同的类型  来查看用户填写的信息定义编码 是否在数据库中
			String contypeid = cbinfo.getContractType().getId().toString();//合同类型id
            //在合同信息中  找到  合同类型对应的详细信息
			ContractDetailDefCollection cddiList = ContractDetailDefFactory.getLocalInstance(ctx).getContractDetailDefCollection(this.getView("contracttype", contypeid));
			
			int len = cddiList.size();//合同信息的个数
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			
			//过滤的条件：where number=用户输入 and (number=合同信息 or number=合同信息)
			filter.getFilterItems().add(new FilterItemInfo("number",FEntrys_detailDefID,CompareType.EQUALS));
			Iterator it= cddiList.iterator();
			while(it.hasNext()){
				ContractDetailDefInfo item = (ContractDetailDefInfo)it.next();
				String strNumber = item.getNumber();
				filter.getFilterItems().add(new FilterItemInfo("number",strNumber,CompareType.EQUALS));
			}
			
			String strbuf = " #0 and (#1 ";
			for(int i=1; i<len; i++){
				strbuf = strbuf + " or #"+(i+1)+" ";
			}
			strbuf = strbuf + " ) ";
			filter.setMaskString(strbuf);
			view.setFilter(filter);
			
			cddinfo = ContractDetailDefFactory.getLocalInstance(ctx).getContractDetailDefCollection(view).get(0);
		    if(cddinfo==null){ //详细定义编码在数据库中不存在！
		    	FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillEntryImport_FEntrys_detailDefID"));
		    }

		} catch (BOSException e) {
			FDCTransmissionHelper.isThrow(e.toString());
		}
		
		//验证数据类型
		String dt = FEntrys_dataType.trim();
		int ie = 0;
		if(dt.equals("布尔类型")){
			ie=20;
		}else if(dt.equals("数值类型")){
			ie=30;
		}else if(dt.equals("字符类型")){
			ie=40;
		}else if(dt.equals("日期类型")){
			ie=10;
		}else if(dt.equals("基础资料")){
			ie=50;
		}else{//请根据模板的提示输入正确的数据类型！
			FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillEntryImport_FEntrys_dataType"));
		}
		DataTypeEnum dtenum = DataTypeEnum.getEnum(ie);//FEntrys_dataType
		
		//分录的设置
		info.setDetailDefID(cddinfo.getId().toString());// 详细信息定义编码
		info.setDetail(cddinfo.getName());//详细名称
		info.setContent(FEntrys_detail);//内容
		info.setDataType(dtenum);//数据类型
        info.setParent(cbinfo);
        
		return info;
	}
	
	//并且验证一个合同的详细信息个数
	private void checkDefCount(Context ctx) throws TaskExternalException,BOSException {
		String conPropert = maininfo.getContractPropert().getValue();//拿到合同性质    合同性质为三方或补充合同时   
		String contypeid = maininfo.getContractType().getId().toString();//合同类型id
        //在合同信息中  找到  合同类型对应的详细信息
		ContractDetailDefCollection cddiList = ContractDetailDefFactory.getLocalInstance(ctx).getContractDetailDefCollection(this.getView("contracttype", contypeid));
		
		//算出数据库中  分录中合同信息个数
		ContractBillEntryCollection conBE = ContractBillEntryFactory.getLocalInstance(ctx).getContractBillEntryCollection(this.getView("parent",maininfo.getId().toString()));
		int entryLen = maininfo.getEntrys().size()-conBE.size();//用户导入的详细信息个数 
		if(entryLen==0){
			FDCTransmissionHelper.isThrow(getResource(ctx, "qtxbz"));//请填写备注
		}
		int defCout = cddiList.size();//合同的实际详细信息的个数
		//合同性质为三方或补充合同时   合同的实际详细信息数量+4
		if(conPropert.trim().equals("THREE_PARTY") || conPropert.trim().equals("SUPPLY")){
			defCout = defCout+4;
		}
		if(entryLen!=defCout){//如果个数存在差异
			FDCTransmissionHelper.isThrow(getResource(ctx,"contract")+maininfo.getNumber()+getResource(ctx,"drdxxxxgsysjqkbfh"));//导入的详细信息个数与实际情况不符合
		}
	}

	//返回视图
	private EntityViewInfo getView(String sqlcolnum,Object item){
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(sqlcolnum,item,CompareType.EQUALS));
        view.setFilter(filter);
		return view;	
	}
	
	
	//检查 主合同编码的情况
	private boolean cehckMainConNumber(ContractBillInfo coninfo, String mainNumber, Context ctx) 
	throws BOSException, SQLException{
		//主合同编码范围：
		//合同性质必须是三方合同或者补充合同；         //只能是审批过后的合同，但如果是补充合同可以选择另外2种状态：审核中、提交；
		//不能是当前的合同；         //必须和当前合同处于同一个工程项目；        //不能是已结算的合同；       //合同类型必须启用；
		MetaDataPK obj = new MetaDataPK("com.kingdee.eas.fdc.contract.app.ContractBillF7SimpleQuery");
    	IQueryExecutor exec = QueryExecutorFactory.getLocalInstance(ctx,obj);
	
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		EntityViewInfo view = new EntityViewInfo();
		
		HashSet set = new HashSet();
		set.add(ContractPropertyEnum.DIRECT_VALUE);//合同性质必须是三方合同或者补充合同
		set.add(ContractPropertyEnum.THREE_PARTY_VALUE);
		filterItems.add(new FilterItemInfo("contractPropert", set,CompareType.INCLUDE));
		filterItems.add(new FilterItemInfo("isAmtWithoutCost", Boolean.FALSE));
		filterItems.add(new FilterItemInfo("contractType.isEnabled",Boolean.TRUE)); //合同类型必须启用
		
		Set stateSet = new HashSet();
		stateSet.add(FDCBillStateEnum.AUDITTED_VALUE);//只能是审批过后的合同，但如果是补充合同可以选择另外2种状态：审核中、提交
		if(coninfo.getContractPropert().getValue().equals("SUPPLY")){
			stateSet.add(FDCBillStateEnum.AUDITTING_VALUE);
			stateSet.add(FDCBillStateEnum.SUBMITTED_VALUE);
		}
		filterItems.add(new FilterItemInfo("state", stateSet, CompareType.INCLUDE));
		filterItems.add(new FilterItemInfo("hasSettled", Boolean.FALSE)); //不能是已结算的合同
		filterItems.add(new FilterItemInfo("curProject.id",coninfo.getCurProject().getId().toString(),CompareType.EQUALS));
		filterItems.add(new FilterItemInfo("id", coninfo.getId().toString(), CompareType.NOTEQUALS));
		filterItems.add(new FilterItemInfo("number", mainNumber, CompareType.EQUALS));
		  
		view.setFilter(filter);
		exec.setObjectView(view);
		
		IRowSet iRowSet = exec.executeQuery();	
        iRowSet.beforeFirst();
        
        if(iRowSet.size()>0){
        	return true;
        }else{
        	return false;
        }
       
	}
	
	/**
	 * 得到资源文件
	 * @author 郑杰元
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
	
	
	/**
	 * 	从Hashtable中提取字段值
	 * @param hsData  数据源	
	 * @param key	键
	 * @return	根据键返回值	
	 */
	public String getFieldValue(Hashtable hsData, String key) {
		return ((String) ((DataToken) hsData.get(key)).data).trim();
	}
	
}
