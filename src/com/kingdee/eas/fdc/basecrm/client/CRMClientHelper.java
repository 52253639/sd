package com.kingdee.eas.fdc.basecrm.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.RelatBizType;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.SHERevBillCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.SHERevBillFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeManageInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

import common.Logger;
/**
 * 客户端帮助类
 * 所有存在依赖业务系统的代码,请不要放在这里面
 * */
public class CRMClientHelper {
	
	private static Logger logger = Logger.getLogger(CRMClientHelper.class);
	
	/**
	 * 给row行的colKey列设置值
	 * */
	public static void setColValue(IRow row, String colKey, Object value) {
		ICell cell = row.getCell(colKey);
		if(cell != null){
			cell.setValue(value);
		}
	}
	
	/**
	 * 构建一个金额的cellEditor
	 * */
	public static ICellEditor getKDTDefaultCellEditor() {
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		return numberEditor;
	}
	
	/**
	 * 构建一个整数的cellEditor
	 * */
	public static ICellEditor getIntegerCellEditor() {
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.INTEGER);
		// formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		return numberEditor;
	}
	
	/**
	 * 判断编辑前后值是否有改变
	 * */
	public static boolean isValueChanged(KDTEditEvent e){
		Object oldValue = e.getOldValue();
		Object newValue = e.getValue();
		if(oldValue == newValue){
			return false;
		}
		if(oldValue != null  &&  newValue != null  &&  oldValue.equals(newValue)){
			return false;
		}
		return true;
	}
	
	/**
	 * 获得用户选择的多行记录
	 * @author liang_ren969
	 * @date 2010-6-12
	 * @param table  表对象
	 * @param keyFieldName 字段属性
	 * @return
	 */
	public static List getSelectedIdValues(KDTable table, String keyFieldName) {

		List ids = new ArrayList();

		int[] selectedRows = KDTableUtil.getSelectedRows(table);

		for (int i = 0; i < selectedRows.length; i++) {
			if(table.getCell(selectedRows[i], keyFieldName)==null){
				return null;
			}
			String id = (String) table.getCell(selectedRows[i], keyFieldName)
					.getValue();
			ids.add(id);
		}

		return ids;
	}
	
	/**
	 * 读取网签功能所使用的DLL文件
	 * @param path
	 * @author liang_ren969
	 * @date 2011-1-16
	 */
	public static void getDLLForWebMark(String path){
		try {
			if (path == null || path.length() == 0) {
				System.loadLibrary("jdic");
				System.loadLibrary("tray");
			} else {
				/**
				 * 经过检查在客户端是有有fdc目录的，但是fdc目录中不一定有jdic.dll和tray.dll这2个文件
				 * 可是，这2个是在fdc的上一级目录中一定有，所以处理下
				 * update by renliang at 2011-1-26
				 */
				System.load(path + "/fdc/jdic.dll");
				System.load(path + "/fdc/tray.dll");
			}
		} catch (Throwable e) {
			logger.error("没有在"+path+"/fdc/找到dll文件"+e.getMessage());
			try{
				System.load(path + "/jdic.dll");
				System.load(path + "/tray.dll");
			}catch(Throwable ex){
				logger.error("没有在"+path+"找到dll文件"+e.getMessage());
				FDCMsgBox.showWarning("无法加载解析的DLL文件，请联系开发人员解决此问题！");
				SysUtil.abort();
			}
		}
	}
	
	
	
	
	/**
	 * 以ModelDialog模式打开收款单界面;  
	 * 如果revBillId为空则打开其新增界面,否则为查看界面
	 * @param owner
	 * @param uiClassName
	 * @param revBillId		收款单id
	 * @param spInfo	当前销售项目
	 * @param reltInfo 关联的交易单据，可能是预约、预订、认购、或签约单,"RelateBizType"属性 (单据类型如为转款时，此参数代表转出关联单据)
	 * @param custObjs	数组对象，内容必须是客户SheCustomerInfo							 (单据类型如为转款时，此参数代表转出款项客户，只会取第一个客户)
	 * @param transEntryIds 交易明细的id集
	 */
	public static void openTheGatherRevBillWindow(Object owner,String revBillId,SellProjectInfo spInfo
			,FDCBillInfo reltInfo ,Object[] custObjs ,Set transEntryIds) {		
		UIContext uiContext = new UIContext(owner); 		
		
		String opera = OprtState.ADDNEW;
		if(revBillId!=null && !revBillId.trim().equals(""))  {
			uiContext.put(UIContext.ID, revBillId);
			opera = OprtState.VIEW;
		}else{
			if(reltInfo!=null){
				reltInfo=(FDCBillInfo)reltInfo.clone();
				if(reltInfo instanceof SincerityPurchaseInfo)
					reltInfo.put("RelateBizType", RelatBizType.PreOrder);
				else if(reltInfo instanceof PrePurchaseManageInfo)
					reltInfo.put("RelateBizType", RelatBizType.PrePur);
				else if(reltInfo instanceof PurchaseManageInfo)
					reltInfo.put("RelateBizType", RelatBizType.Purchase);
				else if(reltInfo instanceof SignManageInfo)
					reltInfo.put("RelateBizType", RelatBizType.Sign);
				else if(reltInfo instanceof ChangeManageInfo)
					reltInfo.put("RelateBizType", RelatBizType.Change);			
			}

			if(reltInfo!=null) {
				RelatBizType bizType = (RelatBizType)reltInfo.get("RelateBizType");
				if(bizType!=null)
					reltInfo.setNumber(bizType.getAlias()+ " "+reltInfo.getNumber());
			}
			
			uiContext.put("SellProjectInfo", spInfo);
			uiContext.put("RevBillTypeEnum", RevBillTypeEnum.gathering);
			uiContext.put("FDCBillInfo", reltInfo);
			uiContext.put("CustomerEntrys", custObjs);
			
			RoomInfo roomInfo = null;
			if(transEntryIds!=null && transEntryIds.size()>0) {
				String transIdsStr = CRMHelper.getStringFromSet(transEntryIds);	
				TranBusinessOverViewCollection transfEntryColl = null;
				try {
					if(transIdsStr!=null && !transIdsStr.equals("")) {
						transfEntryColl = TranBusinessOverViewFactory.getRemoteInstance()
									.getTranBusinessOverViewCollection("select *,moneyDefine.*,head.room.name,head.room.number where id in ("+transIdsStr+") order by seq ");
						SHERevBillEntryCollection revEntryColl = new SHERevBillEntryCollection();
						for (int i = 0; i < transfEntryColl.size(); i++) {
							SHERevBillEntryInfo revEntryInfo = new SHERevBillEntryInfo();
							revEntryInfo.setSeq(i);
							revEntryInfo.setMoneyDefine(transfEntryColl.get(i).getMoneyDefine());
							revEntryInfo.setRevAmount(transfEntryColl.get(i).getUnPayAmount());
							if(transfEntryColl.get(i).getUnPayAmount().compareTo(new BigDecimal("0"))!=0)
								revEntryColl.add(revEntryInfo);
							if(roomInfo==null) 
								roomInfo = transfEntryColl.get(i).getHead().getRoom();
						}
						if(transfEntryColl.size()==0){
							SHERevBillEntryInfo revEntryInfo = new SHERevBillEntryInfo();
							revEntryInfo.setSeq(0);
							try {
								revEntryInfo.setMoneyDefine(SHEManageHelper.getSinMoneyDefine());
							} catch (EASBizException e) {
								e.printStackTrace();
							} catch (BOSException e) {
								e.printStackTrace();
							}
							revEntryColl.add(revEntryInfo);
						}
						uiContext.put("RevEntryColl", revEntryColl);
					}
				} catch (BOSException e1) {
					e1.printStackTrace();
				}
			}else{
				if(reltInfo instanceof SincerityPurchaseInfo){
					SHERevBillEntryCollection revEntryColl = new SHERevBillEntryCollection();
					SHERevBillEntryInfo revEntryInfo = new SHERevBillEntryInfo();
					revEntryInfo.setSeq(0);
					try {
						revEntryInfo.setMoneyDefine(SHEManageHelper.getSinMoneyDefine());
					} catch (EASBizException e) {
						e.printStackTrace();
					} catch (BOSException e) {
						e.printStackTrace();
					}
					revEntryColl.add(revEntryInfo);
					uiContext.put("RevEntryColl", revEntryColl);
				}
			}
			if(roomInfo!=null) uiContext.put("RoomInfo", roomInfo);
		}
		
		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
							.create(NewSHERevBillEditUI.class.getName(), uiContext, null, opera);
			uiWindow.show();
		} catch (UIException e) {
			e.printStackTrace();
			SysUtil.abort();
		}
	}
	
	
	/**
	 * 根据收款单，打开对应的转款或退款单界面
	 * revBillIdStr 选择的收款单id
	 * */
	public static void openTheQuitORTransfRevBillWindow(Object owner,RevBillTypeEnum billType,SellProjectInfo sellProject,String revBillIdStr) {	
		if(revBillIdStr==null) return;
		if(billType==null) return;
		if(!billType.equals(RevBillTypeEnum.refundment) && !billType.equals(RevBillTypeEnum.transfer)) return;
		
		try {
			SHERevBillInfo revBillInfo = SHERevBillFactory.getRemoteInstance()
					.getSHERevBillInfo("select relateBizBillId,relateBizBillNumber,relateTransId," +
							"customerEntry.sheCustomer.name,customerEntry.sheCustomer.number,room.id,room.name,room.number " +
							(billType.equals(RevBillTypeEnum.refundment)?",entrys.*,entrys.moneyDefine.* ":"") 
							+" where id = '"+revBillIdStr+"'");
			BaseTransactionInfo fdcBillInfo = null;
			if(revBillInfo!=null && revBillInfo.getRelateBizBillId()!=null) {
				fdcBillInfo = new BaseTransactionInfo();
				fdcBillInfo.setId(BOSUuid.read(revBillInfo.getRelateBizBillId()));
				fdcBillInfo.setNumber(revBillInfo.getRelateBizBillNumber());
				fdcBillInfo.setTransactionID(BOSUuid.read(revBillInfo.getRelateTransId()));
				fdcBillInfo.setRoom(revBillInfo.getRoom());
			}
			
			Object[] sheCustInfos = new Object[revBillInfo.getCustomerEntry().size()];
			for (int i = 0; i < revBillInfo.getCustomerEntry().size(); i++) {
				sheCustInfos[i] = revBillInfo.getCustomerEntry().get(i).getSheCustomer();
			}	
			
			SHERevBillEntryCollection toQuitEntryColl = null;
			if(billType.equals(RevBillTypeEnum.refundment)) {
				for (int i = 0; i < revBillInfo.getEntrys().size(); i++) {
					SHERevBillEntryInfo revEntryInfo = revBillInfo.getEntrys().get(i);
					SHERevBillEntryInfo toQuitInfo = new SHERevBillEntryInfo();
					toQuitInfo.setSeq(i);
					toQuitInfo.setMoneyDefine(revEntryInfo.getMoneyDefine());
					if(revEntryInfo.getRemainAmount().compareTo(new BigDecimal("0"))>0)
							toQuitInfo.setRevAmount(revEntryInfo.getRemainAmount().negate());
					toQuitInfo.setTransferFromEntryId(revEntryInfo.getId().toString());
				}	
			}
			CRMClientHelper.openTheRevBillWindow(owner, null, sellProject, billType, fdcBillInfo, sheCustInfos, toQuitEntryColl);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
	

	
	/**
	 * 根据多个收款单明细，打开对应的退款/转款单界面
	 * revBillIdStr 选择的收款单id
	 * (多收款单明细对于的收款单客户附录必须有相同的客户，否则不允许收款、退款或转款)
	 * */
	public static void openTheQuitOrTransfRevBillWindow(Object owner,RevBillTypeEnum billType,SellProjectInfo sellProject,Set revEnrtyIdSet) {	
		if(revEnrtyIdSet==null || revEnrtyIdSet.size()==0) return;
		if(billType==null) return;
		if(!RevBillTypeEnum.refundment.equals(billType) && !RevBillTypeEnum.transfer.equals(billType)) return;

		String entryIdsStr = "";
		Iterator iter = revEnrtyIdSet.iterator();
		while(iter.hasNext())
			entryIdsStr += ",'"+ iter.next() +"'"; 
		
		try {
			String idsStr = "";
			SHERevBillEntryCollection toQuitEntryColl = null;
			SHERevBillEntryCollection revEntryColl = SHERevBillEntryFactory.getRemoteInstance()
					.getSHERevBillEntryCollection("select *,parent.id,moneyDefine.name,moneyDefine.number," +
							"moneyDefine.moneyType where id in ("+entryIdsStr.substring(1)+")");
			for (int i = 0; i < revEntryColl.size(); i++) {
				idsStr += ",'"+revEntryColl.get(i).getParent().getId()+"'";
			}	
			if(billType.equals(RevBillTypeEnum.refundment)) {
				toQuitEntryColl = new SHERevBillEntryCollection();
				for (int i = 0; i < revEntryColl.size(); i++) {
					SHERevBillEntryInfo revEntryInfo = revEntryColl.get(i);
					SHERevBillEntryInfo toQuitInfo = new SHERevBillEntryInfo();
					toQuitInfo.setSeq(i);
					toQuitInfo.setMoneyDefine(revEntryInfo.getMoneyDefine());
					if(revEntryInfo.getRemainAmount().compareTo(new BigDecimal("0"))>0) 
							toQuitInfo.setRevAmount(revEntryInfo.getRemainAmount().negate());
					toQuitInfo.setTransferFromEntryId(revEntryInfo.getId().toString());
					toQuitEntryColl.add(toQuitInfo);
				}	
			}			
			
			SHERevBillCollection revBillColl = SHERevBillFactory.getRemoteInstance()
					.getSHERevBillCollection("select relateBizBillId,relateBizBillNumber,relateTransId,room.id,room.name,room.number," +
							"customerEntry.sheCustomer.name,customerEntry.sheCustomer.number" 
							+ " where id in ("+idsStr.substring(1)+") ");
			BaseTransactionInfo fdcBillInfo = null;
			boolean isTansIdAllSame = true;
			Object[] sheSameCustInfos = null;
			Map custsMap = new HashMap();
			
			for (int i = 0; i < revBillColl.size(); i++) {
				SHERevBillInfo revBillInfo = revBillColl.get(i);
	
				if(isTansIdAllSame && revBillInfo!=null && revBillInfo.getRelateBizBillId()!=null) {  //交易单据必须是相同的，否则不传递给收付款单
					if(fdcBillInfo!=null){
						if(!fdcBillInfo.getId().toString().equals(revBillInfo.getRelateBizBillId())){
							isTansIdAllSame = false;
							fdcBillInfo = null;
						}
					}else{
						fdcBillInfo = new BaseTransactionInfo();
						fdcBillInfo.setId(BOSUuid.read(revBillInfo.getRelateBizBillId()));
						fdcBillInfo.setNumber(revBillInfo.getRelateBizBillNumber());
						fdcBillInfo.setTransactionID(BOSUuid.read(revBillInfo.getRelateTransId()));
						fdcBillInfo.setRoom(revBillInfo.getRoom());
					}
				}
				
				if(i==0) sheSameCustInfos = new Object[revBillInfo.getCustomerEntry().size()];	//客户必须是相同的，否则不允许传递给收款单
				for (int j = 0; j < revBillInfo.getCustomerEntry().size(); j++) {
					SHECustomerInfo tempSheCustInfo = revBillInfo.getCustomerEntry().get(j).getSheCustomer();
					if(i==0) {
						custsMap.put(tempSheCustInfo.getId().toString(), tempSheCustInfo);
						sheSameCustInfos[j] = tempSheCustInfo;
					}else{
						if(!custsMap.containsKey(tempSheCustInfo.getId().toString())){
							FDCMsgBox.showWarning("多笔收款单的客户不相同，不允许操作！");
							return;
						}						
					}
				}		
			}
			
			CRMClientHelper.openTheRevBillWindow(owner, null, sellProject, billType, fdcBillInfo, sheSameCustInfos, toQuitEntryColl);
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}	
	
	
	/**
	 * 以ModelDialog模式打开收款单界面;  
	 * 如果revBillId为空则打开其新增界面,否则为查看界面
	 * @param owner
	 * @param uiClassName
	 * @param revBillId		收款单id
	 * @param spInfo	当前销售项目
	 * @param billType 单据类型 收款、退款、转款
	 * @param reltInfo 关联的交易单据，可能是预约、预订、认购、或签约单,"RelateBizType"属性 (单据类型如为转款时，此参数代表转出关联单据)
	 * @param custObjs	数组对象，内容必须是客户SheCustomerInfo							 (单据类型如为转款时，此参数代表转出款项客户，只会取第一个客户)
	 * @param revEntryColl 收款明细 （单据类型为收款时才有用） 其中，款项和收款金额属性一定要传值过来哦
	 * 					  退款时用也可以，但前提是必须指明明细中的transferFromEntryId（针对哪笔收款明细退款）
	 */
	public static void openTheRevBillWindow(Object owner,String revBillId,SellProjectInfo spInfo,RevBillTypeEnum billType
			,FDCBillInfo reltInfo ,Object[] custObjs ,SHERevBillEntryCollection revEntryColl) {		
		UIContext uiContext = new UIContext(owner); 		
		
		String opera = OprtState.ADDNEW;
		if(revBillId!=null && !revBillId.trim().equals(""))  {
			uiContext.put(UIContext.ID, revBillId);
			opera = OprtState.VIEW;
		}else{
			if(reltInfo!=null){
				if(reltInfo instanceof SincerityPurchaseInfo)
					reltInfo.put("RelateBizType", RelatBizType.PreOrder);
				else if(reltInfo instanceof PrePurchaseManageInfo)
					reltInfo.put("RelateBizType", RelatBizType.PrePur);
				else if(reltInfo instanceof PurchaseManageInfo)
					reltInfo.put("RelateBizType", RelatBizType.Purchase);
				else if(reltInfo instanceof SignManageInfo)
					reltInfo.put("RelateBizType", RelatBizType.Sign);
				else if(reltInfo instanceof ChangeManageInfo)
					reltInfo.put("RelateBizType", RelatBizType.Change);			
			}

			if(reltInfo!=null) {
				RelatBizType bizType = (RelatBizType)reltInfo.get("RelateBizType");
				if(bizType!=null)
					reltInfo.setNumber(bizType.getAlias()+ " "+reltInfo.getNumber());				
			}
			
			uiContext.put("SellProjectInfo", spInfo);
			uiContext.put("RevBillTypeEnum", billType);
			uiContext.put("FDCBillInfo", reltInfo);
			uiContext.put("CustomerEntrys", custObjs);
			if(billType!=null) {
				if(billType.equals(RevBillTypeEnum.refundment) || billType.equals(RevBillTypeEnum.gathering)){
					uiContext.put("RevEntryColl", revEntryColl);
				}
			}
		}
		
		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
							.create(NewSHERevBillEditUI.class.getName(), uiContext, null, opera);
			uiWindow.show();
		} catch (UIException e) {
			e.printStackTrace();
			SysUtil.abort();
		}
	}
	
	
	public static ICellEditor getF7CellEditorForMoneyDefine(MoneySysTypeEnum sysType){
        EntityViewInfo kdtEntrys_moneyDefine_f7View = new EntityViewInfo();
        FilterInfo kdtEntrys_moneyDefine_f7Filter = new FilterInfo();
        kdtEntrys_moneyDefine_f7View.setFilter(kdtEntrys_moneyDefine_f7Filter);
        kdtEntrys_moneyDefine_f7Filter.getFilterItems().add(new FilterItemInfo("sysType",sysType.getValue()));
        return getF7CellEditorForMoneyDefine(kdtEntrys_moneyDefine_f7View);
	}
	
	
	public static ICellEditor getF7CellEditorForMoneyDefine(EntityViewInfo viewInfo){
        KDBizPromptBox kdtEntrys_moneyDefine_f7PromptBox = new KDBizPromptBox();
        kdtEntrys_moneyDefine_f7PromptBox.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
        kdtEntrys_moneyDefine_f7PromptBox.setVisible(true);
        kdtEntrys_moneyDefine_f7PromptBox.setEditable(true);
        kdtEntrys_moneyDefine_f7PromptBox.setDisplayFormat("$number$");
        kdtEntrys_moneyDefine_f7PromptBox.setEditFormat("$number$");
        kdtEntrys_moneyDefine_f7PromptBox.setCommitFormat("$number$");
        kdtEntrys_moneyDefine_f7PromptBox.setEntityViewInfo(viewInfo);
        KDTDefaultCellEditor kdtEntrys_moneyDefine_f7CellEditor = new KDTDefaultCellEditor(kdtEntrys_moneyDefine_f7PromptBox);
        return kdtEntrys_moneyDefine_f7CellEditor;
	}
	
	 /**
     * 给指定table设定金额格式，并且右对齐
     * 
     * @param table 表格
     * @param columnNames 列名数组
     */
    public static void changeTableNumberFormat(KDTable table, String[] columnNames) {
        for (int i = 0; i < columnNames.length; i++)
            changeTableNumberFormat(table, columnNames[i]);
    }
    
    /**
     * 给指定table设定金额格式，并且右对齐
     * 
     * @param table 表格
     * @param columnName 列名
     */
    private static String strDataFormat = "#,##0.00;-#,##0.00";
    public static void changeTableNumberFormat(KDTable table, String columnName) {
    	if(table.getColumn(columnName)!=null){
    		table.getColumn(columnName).getStyleAttributes().setNumberFormat(strDataFormat);
            table.getColumn(columnName).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT); // 右对齐
    	}
    }
    /**
     * 格式化日期，将日期格式化为“YYYY-MM-DD”
     * 
     * @param table 表格
     * @param columnKeys 列键名
     * @see com.kingdee.eas.fi.fa.manage.client.FaClientUtils#fmtDate(KDTable table, String[] columnKey)
     */
    public static void fmtDate(KDTable table, String[] columnKeys) {
        for (int i = 0; i < columnKeys.length; i++) {
            fmtDate(table, columnKeys[i]);
        }
    }
    /**
     * 格式化日期，将日期格式化为“YYYY-MM-DD”
     * 
     * @param table 表格
     * @param columnKey 列键名
     * @see com.kingdee.eas.fi.fa.manage.client.FaClientUtils#fmtDate(KDTable table, String columnKey)
     */
    public static void fmtDate(KDTable table, String columnKey) {
	    if(table.getColumn(columnKey)!=null)	
	    	table.getColumn(columnKey).getStyleAttributes().setNumberFormat("yyyy-MM-dd");
    }
    /**
	 * 设置合计行
	 * 可以对多列求和，
	 * @param columnName，列名数据
	 * **/
	public static void getFootRow(KDTable tblMain,String[] columnName){
		IRow footRow = null;
        KDTFootManager footRowManager = tblMain.getFootManager();
        if(footRowManager == null)
        {
            String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
            footRowManager = new KDTFootManager(tblMain);
            footRowManager.addFootView();
            tblMain.setFootManager(footRowManager);
            footRow = footRowManager.addFootRow(0);
            footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
            tblMain.getIndexColumn().setWidthAdjustMode((short)1);
            tblMain.getIndexColumn().setWidth(30);
            footRowManager.addIndexText(0, total);
        } else
        {
            footRow = footRowManager.getFootRow(0);
        }
        int columnCount = tblMain.getColumnCount();
        for(int c = 0; c < columnCount; c++)
        {
            String fieldName = tblMain.getColumn(c).getKey();
            for(int i = 0; i < columnName.length; i++)
            {
                String colName = (String)columnName[i];
                if(colName.equalsIgnoreCase(fieldName))
                {
                    ICell cell = footRow.getCell(c);
                    cell.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                    cell.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
                    cell.getStyleAttributes().setFontColor(java.awt.Color.BLACK);
                    cell.setValue(getColumnValueSum(tblMain,colName));
                }
            }

        }
        footRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
	}
	  /**
     * 给指定table数字列求和
     * 
     * @param table 表格
     * @param columnName 表格列名
     */
    public static BigDecimal getColumnValueSum(KDTable table,String columnName) {
    	BigDecimal sum = new BigDecimal(0);
        for(int i=0;i<table.getRowCount();i++){
        	if(table.getRow(i).getCell(columnName).getValue()!=null ){
        		if( table.getRow(i).getCell(columnName).getValue() instanceof BigDecimal)
            		sum = sum.add((BigDecimal)table.getRow(i).getCell(columnName).getValue());
            	else if(table.getRow(i).getCell(columnName).getValue() instanceof String){
            		String value = (String)table.getRow(i).getCell(columnName).getValue();
            		if(value.indexOf("零")==-1 && value.indexOf("[]")==-1){
            			value = value.replaceAll(",", "");
                		sum = sum.add(new BigDecimal(value));
            		}
            	}
            	else if(table.getRow(i).getCell(columnName).getValue() instanceof Integer){
            		String value = table.getRow(i).getCell(columnName).getValue().toString();
            		sum = sum.add(new BigDecimal(value));
            	}
        	}
        }
        return sum;
    }
    public static List getMapFieldValues(KDTable table, String fieldNames[])
    {
        List allList = new ArrayList();
        if(table != null && fieldNames != null)
        {
            for(int i = 0; i < table.getRowCount(); i++)
            {
                Map map = getMapFieldValues(table, i, fieldNames);
                allList.add(map);
            }

        }
        return allList;
    }
    /**
	 * 根据指定行号，列名数组 获取单元格值的集合 与 列名的映射
	 * @param table
	 */
    public static Map getMapFieldValues(KDTable table, int rowIndex, String fieldNames[])
    {
        Map map = new HashMap();
        List list = getFieldValues(table, rowIndex, fieldNames);
        for(int j = 0; j < fieldNames.length; j++)
            map.put(fieldNames[j], list.get(j));

        return map;
    }
    /**
	 * 根据指定行号，列名数组 获取单元格值的集合
	 * @param table
	 */
    public static List getFieldValues(KDTable table, int rowIndex, String fieldNames[])
    {
        List values = new ArrayList();
        if(table != null && rowIndex > -1 && fieldNames != null)
        {
            int size = fieldNames.length;
            for(int i = 0; i < size; i++)
                values.add(i, getFieldValue(table, rowIndex, fieldNames[i]));

        }
        return values;
    }
    /**
	 * 根据指定行号，列名 获取单元格值
	 * @param table
	 */
    public static Object getFieldValue(KDTable table, int rowIndex, String fieldName)
    {
        Object result = null;
        ICell cell = getCell(table, rowIndex, fieldName);
        if(cell != null)
            result = cell.getValue();
        return result;
    }
    /**
	 * 根据指定行号，列名 获取单元格
	 * @param table
	 */
    public static ICell getCell(KDTable table, int rowIndex, String fieldName)
    {
        ICell cell = null;
        if(table != null && rowIndex > -1 && fieldName != null && fieldName.trim().length() != 0)
            cell = table.getRow(rowIndex).getCell(fieldName);
        return cell;
    }
	/**
     * 融合
     * */
    public static void mergeThemeRow(KDTable table,String columnName,int colIndex){
        String theme = "";
        String lastTheme = "";
        boolean isMulTheme = false;
        KDTMergeManager mm = table.getMergeManager();
        int rowIndx = 0;
        int endIndx = 0;
        for(int i=0 ;i<table.getRowCount();i++){
        	endIndx = i;
        	theme = (String)table.getRow(i).getCell(columnName).getValue(); 
        	if(i>0){
        		lastTheme = (String)table.getRow(i-1).getCell(columnName).getValue(); 
            	if(!theme.equals(lastTheme)){ 
            		mm.mergeBlock(rowIndx, colIndex,endIndx-1, colIndex); 
            		rowIndx = endIndx;
            	}
        	}
        }
     }
	
}
