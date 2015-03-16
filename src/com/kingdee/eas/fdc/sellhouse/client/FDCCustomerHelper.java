package com.kingdee.eas.fdc.sellhouse.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.PositionHierarchyCollection;
import com.kingdee.eas.basedata.org.PositionHierarchyFactory;
import com.kingdee.eas.basedata.org.PositionHierarchyInfo;
import com.kingdee.eas.basedata.org.PositionInfo;
import com.kingdee.eas.basedata.org.PositionMemberCollection;
import com.kingdee.eas.basedata.org.PositionMemberFactory;
import com.kingdee.eas.basedata.org.PositionMemberInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.CertifacateNameEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.TrackRecordFactory;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

public class FDCCustomerHelper {
	private static final Logger log = Logger.getLogger(FDCCustomerHelper.class);
	
	/**
	 * 验证证件号码是否可以被录入
	 * */
	public static boolean verifyCertificateNumber(CertifacateNameEnum nameEnum, String certificateNum, String exceptCusId) throws BOSException {
		if(StringUtils.isEmpty(certificateNum)){
			return true;
		}
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("certificateName", nameEnum.getValue()));
		filter.getFilterItems().add(new FilterItemInfo("certificateNumber", certificateNum));
		if(exceptCusId != null){
			filter.getFilterItems().add(new FilterItemInfo("id", exceptCusId, CompareType.NOTEQUALS));
		}
		SelectorItemCollection sel = new SelectorItemCollection();
	    sel.add(new SelectorItemInfo("*"));
	    sel.add(new SelectorItemInfo("salesman.name"));
    	EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(filter);
		
		FDCCustomerCollection cuses = FDCCustomerFactory.getRemoteInstance().getFDCCustomerCollection(view);
		if(cuses == null  ||  cuses.isEmpty()){
			return true;
		}
		
		FDCCustomerInfo info = getFirstBookedCustomer(cuses);
		int result = MsgBox.showConfirm2New(null, "客户证件号码已在 <" + info.getCreateTime().toString() +"> 录入,姓名为 <" + info.getName()+"> ,销售顾问为 <"+ info.getSalesman().getName() + "> ,是否继续？");
		if(result == MsgBox.YES){
			return true;
		}
		return false;
	}
	
	/**
	 * 验证证件号码是否可以被录入
	 * update by renliang at 2011-3-2
	 * */ 
	public static boolean verifyCertificateNumber(String certificateId, String certificateNum, String exceptCusId) throws BOSException {
		if(StringUtils.isEmpty(certificateNum)){
			return true;
		}
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("certificateName.id", certificateId));
		filter.getFilterItems().add(new FilterItemInfo("certificateNumber", certificateNum));
		if(exceptCusId != null){
			filter.getFilterItems().add(new FilterItemInfo("id", exceptCusId, CompareType.NOTEQUALS));
		}
		SelectorItemCollection sel = new SelectorItemCollection();
	    sel.add(new SelectorItemInfo("*"));
	    sel.add(new SelectorItemInfo("salesman.name"));
    	EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(filter);
		
		FDCCustomerCollection cuses = FDCCustomerFactory.getRemoteInstance().getFDCCustomerCollection(view);
		if(cuses == null  ||  cuses.isEmpty()){
			return true;
		}
		
		FDCCustomerInfo info = getFirstBookedCustomer(cuses);
		int result = MsgBox.showConfirm2New(null, "客户证件号码已在 <" + info.getCreateTime().toString() +"> 录入,姓名为 <" + info.getName()+"> ,销售顾问为 <"+ info.getSalesman().getName() + "> ,是否继续？");
		if(result == MsgBox.YES){
			return true;
		}
		return false;
	}
	
	/**
	 * 验证客户名称是否可以被录入
	 * @throws BOSException 
	 * */
	public static boolean verifyCustomerName(String customerName,MarketDisplaySetting setting) throws BOSException
	{
		if(StringUtils.isEmpty(customerName)){
			return true;
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", customerName));
		SelectorItemCollection sel = new SelectorItemCollection();
	    sel.add(new SelectorItemInfo("*"));
	    sel.add(new SelectorItemInfo("salesman.name"));
    	EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(filter);
		
		FDCCustomerCollection cuses = FDCCustomerFactory.getRemoteInstance().getFDCCustomerCollection(view);
		if(cuses == null  ||  cuses.isEmpty()){
			return true;
		}else if(cuses!=null && setting.getNameRepeat()==0)
		{
			FDCCustomerInfo info = getFirstBookedCustomer(cuses);
			StringBuffer msg = new StringBuffer();
			msg.append("客户名称已在 <");
			msg.append(info.getCreateTime().toString());
			msg.append("> 录入,姓名为 <");
			msg.append(info.getName());
			msg.append("> ,销售顾问为 <");
			msg.append(info.getSalesman().getName());
			msg.append(">");
			MsgBox.showInfo(msg.toString());
			return false;
		}else if(cuses!=null && setting.getNameRepeat()==1)
		{
			FDCCustomerInfo info = getFirstBookedCustomer(cuses);
			int result = MsgBox.showConfirm2New(null, "客户证件号码已在 <" + info.getCreateTime().toString() +"> 录入,姓名为 <" + info.getName()+"> ,销售顾问为 <"+ info.getSalesman().getName() + "> ,是否继续？");
			if(result == MsgBox.YES){
				return true;
			}
		}else if(cuses!=null && setting.getNameRepeat()==2)
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * 判定txt的输入是否为空
	 * @param txt
	 * 			文本控件
	 * @param msg
	 * 			如果为空提示的内容
	 * @return 文本内容是否为空
	 * */
	public static boolean verifyInputNotNull(KDTextField txt, String msg) {
		String str = txt.getText().trim();
		if(str.equals("")){
			MsgBox.showWarning(msg);
			return false;
		}
		return true;
	}
	
	/**
	 * 验证该电话号码是否能被录入
	 * @param phone
	 * 			待验证的号码
	 * @param exceptCusId
	 * 			要除外的客户编码（该参数主要针对客户修改时,要将当前修改的客户记录除外）
	 * */
	public static boolean verifyPhone(String phone, String exceptCusId) throws BOSException {
		if(phone == null  ||  phone.equals("") ||  !isValidInput(phone)){
			return false;
		}
		
		FDCCustomerCollection cusList = getSamePhoneRecord(phone, exceptCusId);
		if(cusList == null){
			return false;
		}
		
		if(cusList.isEmpty()){
			return true;
		}
		
		FDCCustomerInfo info = getFirstBookedCustomer(cusList);
		
		StringBuffer msg = new StringBuffer();
		msg.append("客户联系电话已在 <");
		msg.append(info.getCreateTime().toString());
		msg.append("> 录入,姓名为 <");
		msg.append(info.getName());
		msg.append("> ,销售顾问为 <");
		msg.append(info.getSalesman().getName());
		msg.append("> ,是否继续？");
		
//		String msg1 = "客户联系电话已在 <" + info.getBookedDate().toString() +"> 录入,姓名为 <" + info.getName()+"> ,销售顾问为 <"+ info.getSalesman().getName() + "> ,是否继续？";
		int result = MsgBox.showConfirm2New(null, msg.toString());
		if(result == MsgBox.YES){
			return true;
		}
		return false;
	}
	
	/**
	 * 验证该电话号码是否能被录入
	 * @param phone
	 * 			待验证的号码
	 * @return 输入正确，已有记录中没有相同号码的纪录则返回True，有相同记录根据用户选择返回
	 * */
	public static boolean verifyPhone(String phone) throws BOSException {
		return verifyPhone(phone, null);
	}
	
	/**
	 * 验证该电话号码是否能被录入
	 * @param phone
	 * 			待验证的号码
	 * @return 输入正确，已有记录中没有相同号码的纪录则返回True，有相同记录根据用户选择返回
	 * */
	public static boolean verifyPhone2(String phone,MarketDisplaySetting setting) throws BOSException {
		return verifyPhone(phone, null,setting);
	}
	
	/**
	 * 验证该电话号码是否能被录入
	 * @param phone
	 * 			待验证的号码
	 * @param exceptCusId
	 * 			要除外的客户编码（该参数主要针对客户修改时,要将当前修改的客户记录除外）
	 * */
	public static boolean verifyPhone(String phone, String exceptCusId,MarketDisplaySetting setting) throws BOSException {
		if(phone == null  ||  phone.equals("") ||  !isValidInput(phone)){
			return false;
		}
		
		FDCCustomerCollection cusList = getSamePhoneRecord(phone, exceptCusId);
		
		
		if(cusList.isEmpty()){
			return true;
		}
		
		if(cusList != null && setting.getPhoneRepeat()==0){
			FDCCustomerInfo info = getFirstBookedCustomer(cusList);
			
			StringBuffer msg = new StringBuffer();
			msg.append("客户联系电话已在 <");
			msg.append(info.getCreateTime().toString());
			msg.append("> 录入,姓名为 <");
			msg.append(info.getName());
			msg.append("> ,销售顾问为 <");
			msg.append(info.getSalesman().getName());
			msg.append(">");
			MsgBox.showInfo(msg.toString());
			return false;
		}if(cusList != null && setting.getPhoneRepeat()==1)
		{
			FDCCustomerInfo info = getFirstBookedCustomer(cusList);
			
			StringBuffer msg = new StringBuffer();
			msg.append("客户联系电话已在 <");
			msg.append(info.getCreateTime().toString());
			msg.append("> 录入,姓名为 <");
			msg.append(info.getName());
			msg.append("> ,销售顾问为 <");
			msg.append(info.getSalesman().getName());
			msg.append("> ,是否继续？");
			int result = MsgBox.showConfirm2New(null, msg.toString());
			if(result == MsgBox.YES){
				return true;
			}
			return false;
		}if(cusList != null && setting.getPhoneRepeat()==2)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * 验证输入是否有效
	 * */
	private static boolean isValidInput(String phone) {
		if(phone == null  ||  phone.length() == 0){
			return false;
		}
		String regEx="[\\d]{1,}[\\d|;|；]{0,}";
		
//		String regEx="[\\d|;|；]{0,}";
        boolean result=Pattern.compile(regEx).matcher(phone).matches();
        if(!result){
        	MsgBox.showInfo("联系电话只能输入数字,多个电话号码之间用;分隔!");
        	return false;
        }
		return true;
	}
	
	/**
	 * 获得具有相同电话号码的客户记录
	 * @param exceptCusId 
	 * */
	private static FDCCustomerCollection getSamePhoneRecord(String phone, String exceptCusId) throws BOSException{
		FilterInfo cusFilter = getCusFilter(phone, exceptCusId);
		if(cusFilter == null){
			return null;
		}
		SelectorItemCollection sel = new SelectorItemCollection();
	    sel.add(new SelectorItemInfo("*"));
	    sel.add(new SelectorItemInfo("salesman.name"));
    	EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(cusFilter);
		
		return FDCCustomerFactory.getRemoteInstance().getFDCCustomerCollection(view);
	}
	
	/**
	 * 获得查询包含相同电话号码的FilterInfo
	 * @param exceptCusId 
	 * */
	private static FilterInfo getCusFilter(String phone, String exceptCusId){
		String[] phones = phone.split("[；|;]{1}");
		if(phones.length == 0){//用户只输入;这个符号会导致该情况
			MsgBox.showInfo("联系电话只能输入数字,多个电话号码之间用;分隔!");
			return null;
		}
		
		FilterInfo filter = null;
		for(int i=0; i<phones.length; i++){
			if(phones[i].trim().equals("")){
				continue;
			}
			if(filter == null){
				filter = createFilterPerPhone(phones[i]);
			}else{
				FilterInfo tmp = createFilterPerPhone(phones[i]);
				try {
					filter.mergeFilter(tmp, "or");
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(exceptCusId != null){
			FilterInfo filter2 = new FilterInfo();
			filter2.getFilterItems().add(new FilterItemInfo("id", exceptCusId, CompareType.NOTEQUALS));
			try {
				filter.mergeFilter(filter2, "and");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		return filter;
	}
	
	/**
	 * 根据一条电话号码构造查询Filter,表中电话号码是1个字段,多个号码之间用,或，分隔的
	 * 注:如果直接用like %phone% 会导致匹配不精确,如表中存在号码12345,用123去匹配就不准确了
	 * */
	private static FilterInfo createFilterPerPhone(String phone) {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("phone", phone, CompareType.EQUALS));
		
		mergeOr(filter, new FilterItemInfo("phone", "%;"+phone, CompareType.LIKE));
		mergeOr(filter, new FilterItemInfo("phone", phone+";%", CompareType.LIKE));
		mergeOr(filter, new FilterItemInfo("phone", "%;"+phone+";%", CompareType.LIKE));
		mergeOr(filter, new FilterItemInfo("phone", "%;"+phone+"；%", CompareType.LIKE));
		
		mergeOr(filter, new FilterItemInfo("phone", "%；"+phone, CompareType.LIKE));
		mergeOr(filter, new FilterItemInfo("phone", phone+"；%", CompareType.LIKE));
		mergeOr(filter, new FilterItemInfo("phone", "%；"+phone+"；%", CompareType.LIKE));
		mergeOr(filter, new FilterItemInfo("phone", "%；"+phone+";%", CompareType.LIKE));
		
//		filter.getFilterItems().add(new FilterItemInfo("phone", "%;"+phone, CompareType.LIKE));
//		filter.getFilterItems().add(new FilterItemInfo("phone", phone+";%", CompareType.LIKE));
//		filter.getFilterItems().add(new FilterItemInfo("phone", "%;"+phone+";%", CompareType.LIKE));
//		filter.getFilterItems().add(new FilterItemInfo("phone", "%;"+phone+"；%", CompareType.LIKE));
//		filter.getFilterItems().add(new FilterItemInfo("phone", "%；"+phone, CompareType.LIKE));
//		filter.getFilterItems().add(new FilterItemInfo("phone", phone+"；%", CompareType.LIKE));
//		filter.getFilterItems().add(new FilterItemInfo("phone", "%；"+phone+"；%", CompareType.LIKE));
//		filter.getFilterItems().add(new FilterItemInfo("phone", "%；"+phone+";%", CompareType.LIKE));
//		filter.setMaskString("#1 or #2 or #3 or #4 or #5 or #6 or #7 or #8 or #9");
		return filter;
	}
	
	private static void mergeOr(FilterInfo filter, FilterItemInfo info) {
		FilterInfo tmp = new FilterInfo();
		tmp.getFilterItems().add(info);
		try {
			filter.mergeFilter(tmp, "OR");
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从客户集合中获得最早录入的那个客户
	 * */
	private static FDCCustomerInfo getFirstBookedCustomer(FDCCustomerCollection cusList) {
		FDCCustomerInfo firstBookedCustomer = null;
		for(int i=0; i<cusList.size(); i++){
			FDCCustomerInfo tmp = cusList.get(i);
			if(firstBookedCustomer == null){
				firstBookedCustomer = tmp;
				continue;
			}
			if(tmp.getCreateTime().before(firstBookedCustomer.getCreateTime())){
				firstBookedCustomer = tmp;
			}
		}
		return firstBookedCustomer;
	}
	
	/**
	 * 验证客户基础资料是否被客户引用
	 * @param key
	 * 			客户中关联的基础资料的主键名称
	 * @param selectedIds
	 * 			选中的基础资料的主键集合
	 * @return 是否被引用
	 * @throws BOSException 
	 * @throws EASBizException 
	 * */
	private static boolean hasRelatedByFDCCustomer(String key, List selectedIds) throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(key, FDCHelper.getSetByList(selectedIds),CompareType.INCLUDE));
		return FDCCustomerFactory.getRemoteInstance().exists(filter);
	}
	
	/**
	 * 验证客户基础资料是否被客户引用,如果被引用,提示框提示,并中止后续操作
	 * @param key
	 * 			客户中关联的基础资料的主键名称
	 * @param selectedIds
	 * 			选中的基础资料的主键集合
	 * @throws BOSException 
	 * @throws EASBizException 
	 * */
	public static void checkHasRelatedByFDCCustomer(String key, List selectedIds) throws EASBizException, BOSException{
		if(hasRelatedByFDCCustomer(key, selectedIds)){
			MsgBox.showInfo(FDCCustomerConstant.RELATED_BY_CUSTOMER_DES);
			SysUtil.abort();
		}
	}
	
	/**
	 * 验证客户基础资料是否被客户跟进记录引用,如果被引用,提示框提示,并中止后续操作
	 * @param key
	 * 			客户跟进记录中关联的基础资料的主键名称
	 * @param selectedIds
	 * 			选中的基础资料的主键集合
	 * @throws BOSException 
	 * @throws EASBizException 
	 * */
	public static void checkHasRelatedByTrackRecord(String key, List selectedIds) throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(key, FDCHelper.getSetByList(selectedIds),CompareType.INCLUDE));
		if(TrackRecordFactory.getRemoteInstance().exists(filter)){
			MsgBox.showInfo(FDCCustomerConstant.RELATED_BY_TRACK_RECORD_DES);
			SysUtil.abort();
		}
	}
	
	/**
	 * 根据职员所处的职位，有可能1个职员对应多个职位，故返回集合.
	 * @param personInfo
	 * 			职员实体对象
	 * @return 所属职位的集合，查询后台出错时返回null
	 * */
	public static List getCurrentPositions(PersonInfo personInfo) {
		List list = new ArrayList();
		FilterInfo pmFilter = new FilterInfo();
		pmFilter.getFilterItems().add(new FilterItemInfo("person.id", personInfo.getId()));
		
    	SelectorItemCollection sel = new SelectorItemCollection();
//    	sel.add(new SelectorItemInfo("position.*"));
//    	sel.add(new SelectorItemInfo("position"));
    	sel.add(new SelectorItemInfo("position.id"));
    	sel.add(new SelectorItemInfo("position.number"));
    	EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(pmFilter);
		
		PositionMemberCollection pmList = null;
		try {
			pmList = PositionMemberFactory.getRemoteInstance().getPositionMemberCollection(view);
		} catch (BOSException e) {
			log.warn("取职员职位出错："+e.getMessage());
			log.debug("", e);
			return null;
		}
		
		for(int i=0; i<pmList.size(); i++){
			PositionMemberInfo memberInfo = pmList.get(i);
			PositionInfo positionInfo = memberInfo.getPosition();
			if(positionInfo != null)
				list.add(positionInfo);
		}
		return list;
	}
	
	/**
	 * 根据职位的id获得所有这些职位下的Person的ID集合
	 * @param belongedPositionIds
	 * 			职位的number集合
	 * @return 该职位下的所有职员的ID集合
	 * */
	public static Set getPersonIds(Set belongedPositionIds) {
		Set set = new HashSet();
		FilterInfo pmFilter = new FilterInfo();
		pmFilter.getFilterItems().add(new FilterItemInfo("position.id", belongedPositionIds, CompareType.INCLUDE));
//    	SelectorItemCollection sel = new SelectorItemCollection();
//    	sel.add(new SelectorItemInfo("person.id"));
    	EntityViewInfo view = new EntityViewInfo();
//		view.getSelector().addObjectCollection(sel);
		view.setFilter(pmFilter);
		
		PositionMemberCollection pmList = null;
		try {
			pmList = PositionMemberFactory.getRemoteInstance().getPositionMemberCollection(view);
		} catch (BOSException e) {
			log.warn(e.getMessage());
			log.debug("", e);
		}
		for(int i=0; i<pmList.size(); i++){
			PositionMemberInfo info = pmList.get(i);
			set.add(info.getPerson().getId().toString());
		}
		return set;
	}
	
	/**
	 * 获得当前用户的所有下属职员的ID集合,包括所有下属职员和当前用户所属职员。非职员用户返回Null
	 * @throws BOSException 
	 * */
	public static Set getChildPersonIdsOfCurrentUser(){
		Set childPersonIds = new HashSet();
		
		//获得当前用户
		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		PersonInfo personInfo = currentUserInfo.getPerson();
		if(personInfo == null){//非职员用户
			return null;
		}
		
		childPersonIds.add(personInfo.getId().toString());//当前用户所属职员
		
		//获得当前用户的职位
		List currentPositions = FDCCustomerHelper.getCurrentPositions(personInfo);
		if(currentPositions == null  ||  currentPositions.isEmpty()){//无职位职员
			return childPersonIds;
		}
		
		//获得这些职位的所有下属职位编号集合，不包含自身职位
		Set childPositionIds = null;
		try {
			childPositionIds = getChildPositionIds(currentPositions);
		} catch (BOSException e) {
			log.warn("获得下属职位出错："+e.getMessage());
			log.debug("", e);
		}
		
		if(childPositionIds == null  ||  childPositionIds.isEmpty()){
			return childPersonIds;
		}
		
		//获得所有这些下属职位下的职员集合
		Set personIds = FDCCustomerHelper.getPersonIds(childPositionIds);
		
		if(personIds==null  ||  personIds.isEmpty()){
			return childPersonIds;
		}
		childPersonIds.addAll(personIds);
		return childPersonIds;
	}
	
	private static Set getChildPositionIds(List currentPositions) throws BOSException {
		Set childPositionIds = new HashSet();
		
		//获得子职位为当前用户职位的记录长编码
		Set longNums = new HashSet();
		for(int i=0; i<currentPositions.size(); i++){
			PositionInfo positionInfo = (PositionInfo) currentPositions.get(i);
			String currentPositionId = positionInfo.getId().toString();
			
			FilterInfo phFilter = new FilterInfo();
			phFilter.getFilterItems().add(new FilterItemInfo("child.id", currentPositionId, CompareType.EQUALS));
	    	SelectorItemCollection sel = new SelectorItemCollection();
	    	sel.add(new SelectorItemInfo("longNumber"));
	    	EntityViewInfo view = new EntityViewInfo();
			view.getSelector().addObjectCollection(sel);
			view.setFilter(phFilter);
			PositionHierarchyCollection hierarchyCollection = PositionHierarchyFactory.getRemoteInstance().getPositionHierarchyCollection(view);
			if(hierarchyCollection.isEmpty()){
				longNums.add(positionInfo.getNumber());
			}else if(hierarchyCollection.size() == 1){
				longNums.add(hierarchyCollection.get(0).getLongNumber());
			}else{
				log.warn("职位上下级判定逻辑错误，请检查。");
				return null;
			}
		}
		
		//利用这些长编码去做匹配,长编码为longNumber*左匹配的记录，均为符合的子职位
		FilterInfo phFilter = new FilterInfo();
		for(Iterator itor = longNums.iterator(); itor.hasNext(); ){
			String longNum = (String) itor.next();
			FilterItemInfo tmpItem = new FilterItemInfo("longNumber", longNum+"!%", CompareType.LIKE);
			FilterInfo tmpFilter = new FilterInfo();
			tmpFilter.getFilterItems().add(tmpItem);
			
			phFilter.mergeFilter(tmpFilter, "or");
		}
		SelectorItemCollection sel = new SelectorItemCollection();
    	sel.add(new SelectorItemInfo("child.id"));
    	EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(phFilter);
		PositionHierarchyCollection hierarchyCollection = PositionHierarchyFactory.getRemoteInstance().getPositionHierarchyCollection(view);
		
		for(int i=0; i<hierarchyCollection.size(); i++){
			PositionHierarchyInfo hierarchyInfo = hierarchyCollection.get(i);
			childPositionIds.add(hierarchyInfo.getChild().getId().toString());
		}
		return childPositionIds;
	}
	

/***
 * 根据营销设置中对重复条件的设置来检查是否有重复。
 * @param rMap
 * @param ui
 * @return
 * 如果启用营销设置中的名称和移动电话重复是强制不保存，则忽略名称重复和移动电话重复的判断，否则，先判断名称是否重复，再判断移动电话是否重复，
 * 最后判断名称和移动电话是否都重复。
 * 
 */
public static boolean verifyPhoneAndName(Map rMap,CoreUI ui){
	    MarketDisplaySetting setting = new MarketDisplaySetting();
		boolean isAbort = false;
//		if((rMap.containsKey("dupNameMsg")&&setting.getNameRepeat()==0)&&(rMap.containsKey("dupPhoneMsg")&&setting.getPhoneRepeat()==0)){
//			//两个都强制不保存，合并显示
//			FDCMsgBox.showConfirm3a(ui, "不能保存，请查看详细信息", (String)rMap.get("dupNameMsg")+"\n"+(String)rMap.get("dupPhoneMsg"));
////			abort();//强制不保存
//			return true;
//		}
		//by tim_gao 2010-11-17 客户录入，姓名与号码的重复判断
		//情况1:先判断两种重复是否都同时存在，都存在都合起来的3种提示
		//情况2:当两种重复只有一种存在，或者都不存在再走各自的3种提示
		if(rMap.containsKey("dupNameMsg")&&rMap.containsKey("dupPhoneMsg")){//情况1:姓名重复，电话重复都存在
			if(setting.getNameAndPhoneRepeat()==2){
				//不提示，直接保存
			}else if(setting.getNameAndPhoneRepeat()==0){
				FDCMsgBox.showConfirm3a(ui, "不能保存，请查看详细信息", (String)rMap.get("dupNameMsg")+(String)rMap.get("dupPhoneMsg"));
				return true;//强制不保存
			}else if(setting.getNameAndPhoneRepeat()==1){
				//提示
				if(FDCMsgBox.YES!=FDCMsgBox.showConfirm3a(ui, "存在重复的信息，请查看详细信息，确定是否保存？", (String)rMap.get("dupNameMsg")+(String)rMap.get("dupPhoneMsg"))){
					return true;
				}
			}else {
				return true;
			}
			
		}else{//只有一个重复存在
			if(rMap.containsKey("dupNameMsg")){//情况2:当姓名重复存在
				if(setting.getNameRepeat()==2)
					{
						//不提示，直接保存
					}else if(setting.getNameRepeat()==0){
						FDCMsgBox.showConfirm3a(ui, "不能保存，请查看详细信息", (String)rMap.get("dupNameMsg"));
						return true;//强制不保存
					}else if(setting.getNameRepeat()==1){
						//给出提示，客户选择保存;
						if(FDCMsgBox.YES!=FDCMsgBox.showConfirm3a(ui, "存在重复的信息，请查看详细信息，确定是否保存？", (String)rMap.get("dupNameMsg"))){
							return true;
						}
					}else{
						return true;
					}
				}
			if(rMap.containsKey("dupPhoneMsg")){//当电话重复存在
				if(setting.getPhoneRepeat()==2)
				{
					//不提示，直接保存
				}else if(setting.getPhoneRepeat()==0){
					FDCMsgBox.showConfirm3a(ui, "不能保存，请查看详细信息", (String)rMap.get("dupPhoneMsg"));
					return true;//强制不保存
				}else if(setting.getPhoneRepeat()==1){
					//给出提示，客户选择保存;
					if(FDCMsgBox.YES!=FDCMsgBox.showConfirm3a(ui, "存在重复的信息，请查看详细信息，确定是否保存？", (String)rMap.get("dupPhoneMsg"))){
						return true;
					}
				}else{
					return true;
				}
			}
		}
		
		
		
		
		
		
		
		
		
//		
//		if(setting.getNameAndPhoneRepeat() == 0){
//			if(rMap.containsKey("dupNameAndPhoneMsg")){
//				FDCMsgBox.showConfirm3a(ui, "不能保存，请查看详细信息", (String)rMap.get("dupNameAndPhoneMsg"));
//				return true;//强制不保存
//			}
//		}else{
//			if(rMap.containsKey("dupNameMsg")){
//				if(setting.getNameRepeat()==2)
//					{
//						//不提示，直接保存
//					}else if(setting.getNameRepeat()==0){
//						FDCMsgBox.showConfirm3a(ui, "不能保存，请查看详细信息", (String)rMap.get("dupNameMsg"));
//						return true;//强制不保存
//					}else if(setting.getNameRepeat()==1){
//						//给出提示，客户选择保存;
//						if(FDCMsgBox.YES!=FDCMsgBox.showConfirm3a(ui, "存在重复的信息，请查看详细信息，确定是否保存？", (String)rMap.get("dupNameMsg"))){
//							return true;
//						}
//					}else{
//						return true;
//					}
//				}
//				
//				if(rMap.containsKey("dupPhoneMsg")){
//					if(setting.getPhoneRepeat()==2)
//					{
//						//不提示，直接保存
//					}else if(setting.getPhoneRepeat()==0){
//						FDCMsgBox.showConfirm3a(ui, "不能保存，请查看详细信息", (String)rMap.get("dupPhoneMsg"));
//						return true;//强制不保存
//					}else if(setting.getPhoneRepeat()==1){
//						//给出提示，客户选择保存;
//						if(FDCMsgBox.YES!=FDCMsgBox.showConfirm3a(ui, "存在重复的信息，请查看详细信息，确定是否保存？", (String)rMap.get("dupPhoneMsg"))){
//							return true;
//						}
//					}else{
//						return true;
//					}
//				}
//				if(rMap.containsKey("dupNameAndPhoneMsg")){
//					if(setting.getNameAndPhoneRepeat() == 2){
//						
//					}else if(setting.getNameAndPhoneRepeat()==0){
//						FDCMsgBox.showConfirm3a(ui, "不能保存，请查看详细信息", (String)rMap.get("dupNameAndPhoneMsg"));
//						return true;//强制不保存
//					}else if(setting.getNameAndPhoneRepeat()==1){
//						//给出提示，客户选择保存;
//						if(FDCMsgBox.YES!=FDCMsgBox.showConfirm3a(ui, "存在重复的信息，请查看详细信息，确定是否保存？", (String)rMap.get("dupNameAndPhoneMsg"))){
//							return true;
//						}
//					}else{
//						return true;
//					}
//				}
//		}
		
		
			return isAbort;
	}
	
}
