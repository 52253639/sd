package com.kingdee.eas.fdc.basedata;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.AimCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.CostSplitEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.CostSplitFactory;
import com.kingdee.eas.fdc.aimcost.CostSplitInfo;
import com.kingdee.eas.fdc.aimcost.ProjectCostChangeLogFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.ISettlementCostSplitEntry;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.SettlementCostSplitInfo;
import com.kingdee.eas.fdc.finance.IPaymentSplitEntry;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

/**
 * 此类包含房地产单据拆分的拆分逻辑及相关方法
 * Date 2006-12-7
 */
public class FDCCostSplit
{
    private static final Logger logger = CoreUIObject.getLogger(FDCCostSplit.class);
    private Context ctx=null;
    
    public FDCCostSplit(Context ctx){
    	this.ctx=ctx;
    }

	/**
	 * 描述：汇总分摊指标值
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-11-07 <p>
	 */
    public void totApptValue(IObjectCollection allEntrys,FDCSplitBillEntryInfo curEntry){
    	int curIndex=allEntrys.indexOf(curEntry);
    	if(curIndex==-1){
    		return;
    	}
    	CostSplitTypeEnum costSplitType=curEntry.getSplitType();
		//计算汇总数	
		if(CostSplitTypeEnum.PRODSPLIT==costSplitType){
			totApptValueProduct(allEntrys,curIndex);
		}else if(CostSplitTypeEnum.BOTUPSPLIT==costSplitType){
			totApptValueBotUp(allEntrys,curIndex);
		}else{
			totApptValueTopDown(allEntrys,curIndex);
		}	
    
    }
    
    /*
    public boolean canSplitProd(FDCSplitBillEntryInfo entry){		
		boolean isTrue=false;
		
		if(entry.getSplitType()==null || entry.getSplitType().equals(CostSplitType.MANUALSPLIT)){
			if(entry.getCostAccount().isIsLeaf()){
    			isTrue=true;
    		}
		}else if(entry.getSplitType().equals(CostSplitType.PROJSPLIT) || entry.getSplitType().equals(CostSplitType.BOTUPSPLIT)){
    		if(entry.getCostAccount().isIsLeaf() && entry.getCostAccount().getCurProject().isIsLeaf()){
    			isTrue=true;
    		}
    	}else if(entry.getSplitType().equals(CostSplitType.PRODSPLIT)){
    		//if(entry.getProduct().equals(null)){
    		if(!entry.isIsLeaf()){
    			isTrue=true;
    		}
    	}
		
		return isTrue;
	}*/

	
	/**
	 * 描述：是否产品拆分父级
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-11-30 <p>
	 */
    public boolean isProdSplitParent(FDCSplitBillEntryInfo entry){		
		boolean isTrue=false;
		
		if(!entry.isIsLeaf()
				&& entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
			isTrue=true;
		}
		
		return isTrue;
	}

	
	/**
	 * 描述：是否下级工程项目的相同科目（长编码相同）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-12-28 <p>
	 */
    public static boolean isChildProjSameAcct(FDCSplitBillEntryInfo entry, FDCSplitBillEntryInfo parent){		
		boolean isTrue=false;

		String topAcctNo=parent.getCostAccount().getLongNumber().replace('.','!');

		//if(entry.getCostAccount().getCurProject().getLevel()==(parent.getCostAccount().getCurProject().getLevel()+1)
		if(entry.getLevel()==parent.getLevel()+1
				&& entry.getCostAccount().getCurProject().getLevel()==parent.getCostAccount().getCurProject().getLevel()+1
				&& entry.getCostAccount().getCurProject().getParent()!=null 
				&& entry.getCostAccount().getCurProject().getParent().getId().equals(parent.getCostAccount().getCurProject().getId())
				&& entry.getCostAccount().getLongNumber().replace('.','!').equals(topAcctNo)
				&& !isProdSplitLeaf(entry)){
			isTrue=true;
		}
		
		return isTrue;
	}
    
    
    
	
	/**
	 * 描述：是否产品拆分明细
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-11-30 <p>
	 */
	public static boolean isProdSplitLeaf(FDCSplitBillEntryInfo entry){
    	boolean isTrue=false;
    	
    	if(entry.isIsLeaf() && entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
    		isTrue=true;
    	}
    	
    	return isTrue;
    }
    

	
	/**
	 * 描述：是否允许产品拆分
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-12-26 <p>
	 */
    public boolean isProdSplitEnabled(FDCSplitBillEntryInfo entry){		
		boolean isTrue=false;
		
		if(entry.getCostAccount().isIsLeaf() && entry.getCostAccount().getCurProject().isIsLeaf()){
			
			if(entry.getSplitType()==null || entry.getSplitType().equals(CostSplitTypeEnum.MANUALSPLIT)){
				isTrue=true;
			}else if(entry.getSplitType().equals(CostSplitTypeEnum.PROJSPLIT) || entry.getSplitType().equals(CostSplitTypeEnum.BOTUPSPLIT)){
				isTrue=true;
	    	}else if(entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
	    		if(!entry.isIsLeaf()){
	    			isTrue=true;
	    		}
	    	}			
		}
		
		return isTrue;
	}
	
       
    
    
    
    /**
     * 汇总分摊金额
     * @author sxhong  		Date 2006-12-11
     * @param curIndex
     */
    public BigDecimal totAmount(IObjectCollection allEntrys){
    	
    	

		BigDecimal amountTotal=FDCHelper.ZERO;
		BigDecimal amount=FDCHelper.ZERO;
		FDCSplitBillEntryInfo entry=null;
		//计算拆分总金额
		for(int i=0; i<allEntrys.size(); i++){
			entry =(FDCSplitBillEntryInfo)(allEntrys.getObject(i));
			
			if(entry.getLevel()==0){
				amount=entry.getAmount();
				if(amount!=null){
					amountTotal=amountTotal.add(amount);
				}
			}						
		}			
//		TODO 设置到汇总控件
//		txtSplitedAmount.setValue(amountTotal);
		
    	return amountTotal;
    }
	/**
	 * 描述：汇总分摊指标值（产品拆分）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
	 */
    private void totApptValueProduct(IObjectCollection allEntrys,int curIndex){
    	FDCSplitBillEntryInfo curEntry = (FDCSplitBillEntryInfo)allEntrys.getObject(curIndex); 
    	if(curEntry.isIsLeaf()){
    		return;
    	}
    	
    	int level=curEntry.getLevel();
    	int projLevel=curEntry.getCostAccount().getCurProject().getLevel();
    	BOSUuid acctId=curEntry.getCostAccount().getId();

		BigDecimal apportionValue=FDCHelper.ZERO;
		BigDecimal apportionTotal=FDCHelper.ZERO;

		BigDecimal directAmount=FDCHelper.ZERO;
		BigDecimal directTotal=FDCHelper.ZERO;
		
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			FDCSplitBillEntryInfo entry = (FDCSplitBillEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()==level+1){				
				//累计
				apportionValue=entry.getApportionValue();
				if(apportionValue==null){
					apportionValue=FDCHelper.ZERO;
				}
				directAmount=entry.getDirectAmount();
				if(directAmount==null){
					directAmount=FDCHelper.ZERO;
				}
				
				apportionTotal=apportionTotal.add(apportionValue);
				directTotal=directTotal.add(directAmount);	
								
				//calcApportionDataTopDown(i);
			}
			else if(entry.getLevel()<=level){
				break;
			}			
		}	

		curEntry.setApportionValueTotal(apportionTotal);
		curEntry.setDirectAmountTotal(directTotal);
    
    }
    
	/**
	 * 描述：汇总分摊指标值（自动拆分，同时适用其中包含的产品拆分）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
	 */
    private void totApptValueTopDown(IObjectCollection allEntrys,int topIndex){
    	FDCSplitBillEntryInfo topEntry =(FDCSplitBillEntryInfo)allEntrys.getObject(topIndex);  
    	
    	if(topEntry.isIsLeaf()){
    		return;
    	}
    	
    	int topLevel=topEntry.getLevel();
    	//BOSUuid acctId=topEntry.getCostAccount().getId();

		BigDecimal apportionValue=FDCHelper.ZERO;
		BigDecimal apportionTotal=FDCHelper.ZERO;

		BigDecimal directAmount=FDCHelper.ZERO;
		BigDecimal directTotal=FDCHelper.ZERO;
		
		
		FDCSplitBillEntryInfo entry=null;		

		if(topEntry.getCostAccount().getCurProject().isIsLeaf()){		
			//明细工程的科目
			
    		if(isProdSplitParent(topEntry)){
    			totApptValueProduct(allEntrys,topIndex);				    			
    		}else{
    			totApptValueAddlAcct(allEntrys,topIndex);	    			
    		}	
			
			
		}else{		
			//非明细工程的各科目的成本从下级工程相同科目累加
			
			for(int i=topIndex+1; i<allEntrys.size(); i++){				
				entry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);
				
				if(entry.getLevel()>topLevel){
					if(isChildProjSameAcct(entry,topEntry)){	
						//递归调用
						totApptValueTopDown(allEntrys, i);
						
						//累计
						apportionValue=entry.getApportionValue();
						if(apportionValue==null){
							apportionValue=FDCHelper.ZERO;
						}
						directAmount=entry.getDirectAmount();
						if(directAmount==null){
							directAmount=FDCHelper.ZERO;
						}
						
						apportionTotal=apportionTotal.add(apportionValue);
						directTotal=directTotal.add(directAmount);	
									
						if(CostSplitTypeEnum.PRODSPLIT==entry.getSplitType()){
							totApptValueProduct(allEntrys, i);
						}else{
							totApptValueTopDown(allEntrys,i);
						}
					}
				}else if(entry.getLevel()<=topLevel){
					break;
				}
			}
			
			topEntry.setApportionValueTotal(apportionTotal);
			topEntry.setDirectAmountTotal(directTotal);
		}
		
		
    	/*FDCSplitBillEntryInfo curEntry =(FDCSplitBillEntryInfo)allEntrys.getObject(curIndex);  
    	
    	if(curEntry.isIsLeaf()){
    		return;
    	}
    	
    	int level=curEntry.getLevel();
    	int projLevel=curEntry.getCostAccount().getCurProject().getLevel();
    	BOSUuid acctId=curEntry.getCostAccount().getId();

		BigDecimal apportionValue=FDCHelper.ZERO;
		BigDecimal apportionTotal=FDCHelper.ZERO;

		BigDecimal directAmount=FDCHelper.ZERO;
		BigDecimal directTotal=FDCHelper.ZERO;
		
		
		FDCSplitBillEntryInfo entry=null;
		
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			entry = (FDCSplitBillEntryInfo)allEntrys.getObject(i);
			//自动拆分下的产品拆分
			if(CostSplitType.PRODSPLIT==entry.getSplitType()&&entry.isIsLeaf()){
				//continue;
				totApptValueProduct(allEntrys,i);
			}
			
			//if(entry.getLevel()==level+1){
			if(entry.getCostAccount().getCurProject().getLevel()==projLevel+1){
				//非附加科目
				if(!entry.isIsAddlAccount()){
					//累计
					apportionValue=entry.getApportionValue();
					if(apportionValue==null){
						apportionValue=FDCHelper.ZERO;
					}
					directAmount=entry.getDirectAmount();
					if(directAmount==null){
						directAmount=FDCHelper.ZERO;
					}
					
					apportionTotal=apportionTotal.add(apportionValue);
					directTotal=directTotal.add(directAmount);	
								
					if(CostSplitType.PRODSPLIT==entry.getSplitType()){
						totApptValueProduct(allEntrys, i);
					}else{
						totApptValueTopDown(allEntrys,i);
					}
				}
			}
			else if(entry.getLevel()<=level){
				break;
			}			
		}	

		curEntry.setApportionValueTotal(apportionTotal);
		curEntry.setDirectAmountTotal(directTotal);
		
		//附加科目
		if(!curEntry.isIsAddlAccount()){
			totApptValueAddlAcct(allEntrys,curIndex);
		}
		
		//产品拆分
		if(curEntry.getSplitType()!=null && curEntry.getSplitType().equals(CostSplitType.PRODSPLIT)){
			totApptValueProduct(allEntrys,curIndex);			
		}*/
				
    	
    	
    	/*FDCSplitBillEntryInfo curEntry = (FDCSplitBillEntryInfo)kdtEntrys.getRow(curIndex).getUserObject();    
    	
    	if(curEntry.isIsLeaf()){
    		return;
    	}
    	int level=curEntry.getLevel();

		BigDecimal apportionValue=FDCHelper.ZERO;
		apportionValue.setScale(10);		
		BigDecimal apportionTotal=FDCHelper.ZERO;
		apportionTotal.setScale(10);

		BigDecimal directAmount=FDCHelper.ZERO;
		directAmount.setScale(10);		
		BigDecimal directTotal=FDCHelper.ZERO;
		directTotal.setScale(10);
		
		
		FDCSplitBillEntryInfo entry=null;
		
		for(int i=curIndex+1; i<kdtEntrys.getRowCount(); i++){				
			entry = (FDCSplitBillEntryInfo)kdtEntrys.getRow(i).getUserObject();
			
			if(entry.getLevel()==level+1){		
				//累计
				apportionValue=entry.getApportionValue();
				if(apportionValue==null){
					apportionValue=FDCHelper.ZERO;
				}
				directAmount=entry.getDirectAmount();
				if(directAmount==null){
					directAmount=FDCHelper.ZERO;
				}
				
				apportionTotal=apportionTotal.add(apportionValue);
				directTotal=directTotal.add(directAmount);	
								
				calcApportionDataTopDown(i);
			}
			else if(entry.getLevel()<=level){
				break;
			}			
		}	

		curEntry.setApportionValueTotal(apportionTotal);
		curEntry.setDirectAmountTotal(directTotal);*/
    	
		/*//test
		kdtEntrys.getCell(curIndex,"apportionValueTotal").setValue(apportionTotal);   	 
		kdtEntrys.getCell(curIndex,"directAmountTotal").setValue(directTotal);   	*/				
	}
	


    
	/**
	 * 描述：汇总分摊指标值（末级拆分）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-25 <p>
	 */
    private void totApptValueBotUp(IObjectCollection allEntrys,int curIndex){
    	//先调用普通的分摊值汇总
    	totApptValueTopDown(allEntrys,curIndex);
    	
    	
    	FDCSplitBillEntryInfo topEntry = (FDCSplitBillEntryInfo)allEntrys.getObject(curIndex);    
		
    	//根结点
    	if(topEntry.getLevel()!=0){
    		return;
    	}
    	
    	int level=topEntry.getLevel();
		BigDecimal apportionValue=FDCHelper.ZERO;
		BigDecimal apportionTotal=FDCHelper.ZERO;

		BigDecimal directAmount=FDCHelper.ZERO;
		BigDecimal directTotal=FDCHelper.ZERO;
		
		int lastIndex=0;

		boolean isTrue=false;

		/*//直接费用
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);
			//if(entry.getLevel()>0){
			//if(entry.getLevel()>0 && !entry.isIsAddlAccount()){
			if(entry.getLevel()>0){				
				lastIndex=i;
				
				//不包括附加科目
				if(!entry.isIsAddlAccount()){
					directAmount=entry.getDirectAmount();
					if(directAmount==null){
						directAmount=FDCHelper.ZERO;
					}
					directTotal=directTotal.add(directAmount);
				}
			}else{
				break;
			}
		}	*/
		


		//直接费用
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);
			//if(entry.getLevel()>0){
			//if(entry.getLevel()>0 && !entry.isIsAddlAccount()){
			if(entry.getLevel()>0){			
				
				//不包括附加科目
				//if(!entry.isIsAddlAccount()){
				if(entry.getCostAccount().getCurProject().isIsLeaf()
						&& !entry.isIsAddlAccount() && !isProdSplitLeaf(entry)){
					directAmount=entry.getDirectAmount();
					if(directAmount==null){
						directAmount=FDCHelper.ZERO;
					}
					directTotal=directTotal.add(directAmount);
					
					
					
					apportionValue=entry.getApportionValue();
					if(apportionValue==null){
						apportionValue=FDCHelper.ZERO;
					}
					apportionTotal=apportionTotal.add(apportionValue);
				}
			}else{
				break;
			}
		}	
		
		
		
		/*
		//分摊比值
		FDCSplitBillEntryInfo curEntry=null;
		BOSUuid projId=null;

		//for(int i=rowLast; i>curIndex; i--){	
		int index=lastIndex;
		while(index>curIndex){
			FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)allEntrys.getObject(index);
			projId=entry.getCostAccount().getCurProject().getId();
						
			if(entry.getLevel()>0){		
				//加强判断，处理产品子结点类型 sxhong
				//要汇总的是子结点非产品以及产品非子页结点
				if((entry.isIsLeaf()&&entry.getSplitType()!=CostSplitType.PRODSPLIT)||
						(!entry.isIsLeaf()&&entry.getSplitType()==CostSplitType.PRODSPLIT)){
					//获取明细科目（非附加科目）
					curEntry=null;
					if(entry.isIsAddlAccount()){
						for(int j=index-1; j>curIndex; j--){				
							entry=(FDCSplitBillEntryInfo)allEntrys.getObject(index);
							if(entry.getCostAccount().getCurProject().getId().equals(projId) 
									&& !entry.isIsAddlAccount()){
								curEntry=entry;
								index=j;
								break;
							}
						}
					}else{
						curEntry=entry;
					}
					
					
					if(curEntry!=null){    				
						apportionValue=entry.getApportionValue();
						if(apportionValue==null){
							apportionValue=FDCHelper.ZERO;
						}
						apportionTotal=apportionTotal.add(apportionValue);
					}
					
				}
				
								
			}else{
				break;
			}
			
			index--;
		}
		*/
		
		
		
		topEntry.setApportionValueTotal(apportionTotal);
		topEntry.setDirectAmountTotal(directTotal);
/*		
		//调试 begin
		kdtEntrys.getCell(curIndex,"apportionValueTotal").setValue(apportionTotal);   	 
		kdtEntrys.getCell(curIndex,"directAmountTotal").setValue(directTotal);
		//调试 end
		
*/
	}
	
	/**
	 * 描述：汇总分摊指标值（附加科目）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
	 */
    private void totApptValueAddlAcct(IObjectCollection allEntrys,int curIndex){
    	FDCSplitBillEntryInfo curEntry = (FDCSplitBillEntryInfo)allEntrys.getObject(curIndex);    
    	if(curEntry.isIsLeaf()){
    		return;
    	}    	
    	
    	/*if(curEntry.getSplitType()!=null && curEntry.getSplitType().equals(CostSplitType.PRODSPLIT)){
    		return;
    	}*/
    	
		//产品拆分
		if(curEntry.getSplitType()!=null && curEntry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
			totApptValueProduct(allEntrys,curIndex);			
    		return;
		}
    	
    	int level=curEntry.getLevel();
    	int atcctLevel=curEntry.getCostAccount().getLevel();
    	BOSUuid projId=curEntry.getCostAccount().getCurProject().getId();

		BigDecimal apportionValue=FDCHelper.ZERO;
		BigDecimal apportionTotal=FDCHelper.ZERO;

		BigDecimal directAmount=FDCHelper.ZERO;
		BigDecimal directTotal=FDCHelper.ZERO;
		
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);
			
			if(entry.getCostAccount().getCurProject().getId().equals(projId)){
				if(entry.getCostAccount().getLevel()==atcctLevel+1 
						&& entry.getLevel()==level+1){
					//累计
					apportionValue=entry.getApportionValue();
					if(apportionValue==null){
						apportionValue=FDCHelper.ZERO;
					}
					directAmount=entry.getDirectAmount();
					if(directAmount==null){
						directAmount=FDCHelper.ZERO;
					}
					
					apportionTotal=apportionTotal.add(apportionValue);
					//directTotal=directTotal.add(directAmount);	
									
					totApptValueAddlAcct(allEntrys,i);
				}
			}
			else if(entry.getLevel()<=level){
				break;
			}			
		}	

		curEntry.setOtherRatioTotal(apportionTotal);
		//curEntry.setDirectAmountTotal(directTotal);//test
		
//		kdtEntrys.getCell(rowIndex,"otherRatioTotal").setValue(apportionTotal);   	 
		//kdtEntrys.getCell(rowIndex,"directAmountTotal").setValue(directTotal); 
		
    			
	}
    

	/**
	 * 描述：成本分摊计算
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
	 */    
    public void apptAmount(IObjectCollection allEntrys,FDCSplitBillEntryInfo curEntry){
		CostSplitTypeEnum costSplitType=curEntry.getSplitType();
		int curIndex=allEntrys.indexOf(curEntry);
		if(curIndex==-1){
			return;
		}
		
    	//如果归属金额为0不进行拆分，避免因直接费用而出现拆分金额为负数的情况	jelon 12/7/06
    	if(costSplitType==null || costSplitType.equals(CostSplitTypeEnum.MANUALSPLIT)){
    		//
    	}else{	    		
    		int level=curEntry.getLevel();    		
    			    			
			BigDecimal amount=curEntry.getAmount();
			if(amount==null || amount.compareTo(FDCHelper.ZERO)==0){
				
				for(int i=curIndex+1; i<allEntrys.size(); i++){
		    		FDCSplitBillEntryInfo entry = (FDCSplitBillEntryInfo)allEntrys.getObject(i);
					if(entry.getLevel()>level){
						entry.setAmount(FDCHelper.ZERO);
					}else{
						break;    						
					}
				}
				
				return;
			}
    		
		}
    	
    	//出现了costAccount的longNumber为空的情况，故先对costAccount进行一下处理
    	ICostAccount icostAccount =null;
    	try{
	    	if(ctx!=null){
	    		icostAccount = CostAccountFactory.getLocalInstance(ctx);
	    	}else{
	    		icostAccount = CostAccountFactory.getRemoteInstance();
	    	}

	    	SelectorItemCollection sic=new SelectorItemCollection();
	        sic.add(new SelectorItemInfo("*"));
	        sic.add(new SelectorItemInfo("curProject.*"));
	        if(icostAccount==null) return;
			for(int i=curIndex+1; i<allEntrys.size(); i++){
	    		FDCSplitBillEntryInfo entry = (FDCSplitBillEntryInfo)allEntrys.getObject(i);
	    		if(entry.getCostAccount()!=null&&StringUtils.isEmpty(entry.getCostAccount().getLongNumber())){
	    			CostAccountInfo costAccount=icostAccount.getCostAccountInfo(new ObjectUuidPK(entry.getCostAccount().getId()), sic);
	    			entry.setCostAccount(costAccount);
	    		}
			}
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    	
    	
    	//成本分摊
    	if(costSplitType==null || costSplitType.equals(CostSplitTypeEnum.MANUALSPLIT)){
    		//
    	}else if(costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)){	
			apptAmountProduct(allEntrys,curIndex);
		}else if(costSplitType.equals(CostSplitTypeEnum.BOTUPSPLIT)){	
			apptAmountBotUp(allEntrys,curIndex);
		}else{	
			apptAmountTopDown(allEntrys,curIndex);
		};
		    	
		


		//修正金额，保证上下级的汇总关系
		adjustAmount(allEntrys,curEntry);
		/*金额修正对存在产品拆分的数据会有影响,因修正前的拆分数据与修正后不一致,
		 *修正的结果与按比例拆分的结果会存在精度上的差别,一般为0.01
		 *在修正后进行一次产品拆分
		*/
		
		
		//汇总生成非明细工程项目中附加科目的成本		jelon 12/29/2006
		totAmountAddlAcct(allEntrys,curIndex);
    }

    /**
	 * 描述：成本分摊计算（自动拆分）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
	 */
    private void apptAmountTopDown(IObjectCollection allEntrys,int curIndex){
    	if(curIndex>=allEntrys.size()){
    		return;
    	}
    	FDCSplitBillEntryInfo curEntry = (FDCSplitBillEntryInfo)allEntrys.getObject(curIndex);		
		/*
    	if(curEntry.isIsLeaf()){
			return;
		}
		 */  				
		//金额	
		BigDecimal amount=FDCHelper.ZERO;
		BigDecimal amountTotal=FDCHelper.ZERO;	
		
		//分摊值汇总、直接费用汇总
		BigDecimal apportionValue=FDCHelper.ZERO;
		BigDecimal apportionTotal=FDCHelper.ZERO;
		
		BigDecimal directAmount=FDCHelper.ZERO;
		BigDecimal directTotal=FDCHelper.ZERO;
			
					
		//父级
		int level=curEntry.getLevel();
		int projLevel=curEntry.getCostAccount().getCurProject().getLevel();
		
		amountTotal=curEntry.getAmount();				
		if(amountTotal==null){
			amountTotal=FDCHelper.ZERO;
		}					
		
		//汇总
		apportionTotal=curEntry.getApportionValueTotal();				
		if(apportionTotal==null){
			apportionTotal=FDCHelper.ZERO;
		}					
		directTotal=curEntry.getDirectAmountTotal();				
		if(directTotal==null){
			directTotal=FDCHelper.ZERO;
		}					
		
		//分摊到子级
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);;
			
			
			if(entry.getLevel()<=level){
				break;
			}
			
			
			//直接下级
			if(entry.getLevel()==(level+1) 
					&& entry.getCostAccount().getCurProject().getLevel()==(projLevel+1)){
				
				//分摊
				apportionValue=entry.getApportionValue();
				if(apportionValue==null){
					apportionValue=FDCHelper.ZERO;
				}
				directAmount=entry.getDirectAmount();
				if(directAmount==null){
					directAmount=FDCHelper.ZERO;
				}
				
				if(apportionValue.compareTo(FDCHelper.ZERO)==0 || apportionTotal.compareTo(FDCHelper.ZERO)==0){
					amount=directAmount;
				}else{
					//amount=(apportionValue/approtionTotal) * (amountTotal-directTotal) + directAmount
					
					amount=apportionValue.multiply(amountTotal.subtract(directTotal));
					//amount=amount.divide(apportionTotal,2,BigDecimal.ROUND_HALF_EVEN);
					amount=amount.divide(apportionTotal,10,BigDecimal.ROUND_HALF_EVEN);
					amount=amount.add(directAmount);
				}
				
				entry.setAmount(amount);					

				//处理包含的附加科目和产品拆分	jelon 12/22/2006	
	    		if(isProdSplitParent(entry)){
	    			//产品拆分
		    		apptAmountProduct(allEntrys,i);
	    			
	    		}else if(!entry.getCostAccount().isIsLeaf()){
	    			//附加科目
	    			apptAmountAddlAcct(allEntrys,i);	    			
	    		}
				/*
		    	if(!entry.getCostAccount().isIsLeaf()){
		    		if(isProdSplitParent(entry)){
		    			//产品拆分
			    		calcApportionAmountProduct(i);
		    		}else{
		    			//附加科目
		    			calcApportionAmountAddlAcct(i);
		    		}
		    	}
		    	*/
		    	
		    	//下级工程项目的拆分
		    	if(!entry.getCostAccount().getCurProject().isIsLeaf()){
		    		apptAmountTopDown(allEntrys,i);
		    	}
		    	
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
		
		
		if(!curEntry.isIsAddlAccount()){
			//calcApportionAmountAddlAcct(rowIndex);
		}
    	
    	
    	/*
    	FDCSplitBillEntryInfo curEntry = (FDCSplitBillEntryInfo)kdtEntrys.getRow(rowIndex).getUserObject();		
		if(curEntry.isIsLeaf()){
			return;
		}
		    		
		
		//金额	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		amountTotal.setScale(10);	
		
		//分摊值汇总、直接费用汇总
		BigDecimal apportionValue=FDCHelper.ZERO;//null;
		BigDecimal apportionTotal=FDCHelper.ZERO;
		apportionTotal.setScale(10);	
		
		BigDecimal directAmount=FDCHelper.ZERO;//null;	
		BigDecimal directTotal=FDCHelper.ZERO;
		directTotal.setScale(10);
			
					
		//父级
		int level=curEntry.getLevel();
		int projLevel=curEntry.getCostAccount().getCurProject().getLevel();
		
		amountTotal=curEntry.getAmount();				
		if(amountTotal==null){
			amountTotal=FDCHelper.ZERO;
		}					
		
		//汇总
		apportionTotal=curEntry.getApportionValueTotal();				
		if(apportionTotal==null){
			apportionTotal=FDCHelper.ZERO;
		}					
		directTotal=curEntry.getDirectAmountTotal();				
		if(directTotal==null){
			directTotal=FDCHelper.ZERO;
		}					
		
		//子级
		FDCSplitBillEntryInfo entry=null;
		IRow row;
		
		for(int i=rowIndex+1; i<kdtEntrys.getRowCount(); i++){				
			row=kdtEntrys.getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			if(entry.getLevel()<=level){
				break;
			}
			//直接下级
			//if(entry.getLevel()==(level+1)){
			//if(entry.getCostAccount().getCurProject().getLevel()==(projLevel+1)){		//	Jelon	Dec 13, 2006
			if(entry.getLevel()==(level+1) && entry.getCostAccount().getCurProject().getLevel()==(projLevel+1)){
				if(!entry.isIsAddlAccount()){
					apportionValue=entry.getApportionValue();
					if(apportionValue==null){
						apportionValue=FDCHelper.ZERO;
					}
					directAmount=entry.getDirectAmount();
					if(directAmount==null){
						directAmount=FDCHelper.ZERO;
					}
					
					//if(apportionValue.equals(FDCHelper.ZERO)|| apportionTotal.equals(FDCHelper.ZERO)){
					if(apportionValue.compareTo(FDCHelper.ZERO)==0|| apportionTotal.compareTo(FDCHelper.ZERO)==0){
						amount=directAmount;
					}
					else	//存在分摊
					{				
						amount=apportionValue.multiply(amountTotal.subtract(directTotal));
						amount=amount.divide(apportionTotal,2,BigDecimal.ROUND_HALF_EVEN);
						amount=amount.add(directAmount);
					}
					entry.setAmount(amount);					
					row.getCell("amount").setValue(amount);
					
					//calcApportionAmountTopDown(i);
			    	if(!entry.isIsLeaf() && entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitType.PRODSPLIT)){
			    		calcApportionAmountProduct(i);
			    	}else{
			    		calcApportionAmountTopDown(i);
			    	}
					
				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
		

		if(!curEntry.isIsAddlAccount()){
			calcApportionAmountAddlAcct(rowIndex);
		}*/
    }
    

	/**
	 * 描述：成本分摊计算（产品拆分）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
	 */
    private void apptAmountProduct(IObjectCollection allEntrys,int curIndex){
    	FDCSplitBillEntryInfo curEntry = (FDCSplitBillEntryInfo)allEntrys.getObject(curIndex);		
		if(curEntry.isIsLeaf()){
			return;
		}
		    		
		
		//金额	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		
		//分摊值汇总、直接费用汇总
		BigDecimal apportionValue=FDCHelper.ZERO;//null;
		BigDecimal apportionTotal=FDCHelper.ZERO;
		
		BigDecimal directAmount=FDCHelper.ZERO;//null;	
		BigDecimal directTotal=FDCHelper.ZERO;
			
		//父级
		int level=curEntry.getLevel();
		int projLevel=curEntry.getCostAccount().getCurProject().getLevel();
		
		amountTotal=curEntry.getAmount();			
		if(amountTotal==null){
			amountTotal=FDCHelper.ZERO;
		}					
		
		//汇总
		apportionTotal=curEntry.getApportionValueTotal();				
		if(apportionTotal==null){
			apportionTotal=FDCHelper.ZERO;
		}					
		directTotal=curEntry.getDirectAmountTotal();				
		if(directTotal==null){
			directTotal=FDCHelper.ZERO;
		}					
//		amountTotal=amountTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
		//子级
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			FDCSplitBillEntryInfo entry =(FDCSplitBillEntryInfo)allEntrys.getObject(i);
			
			//直接下级
			if(entry.getLevel()==(level+1)){
				if(entry.getCostAccount().getId().equals(curEntry.getCostAccount().getId())){
					apportionValue=entry.getApportionValue();
					if(apportionValue==null){
						apportionValue=FDCHelper.ZERO;
					}
					directAmount=entry.getDirectAmount();
					if(directAmount==null){
						directAmount=FDCHelper.ZERO;
					}
					
					//if(apportionValue.equals(FDCHelper.ZERO)|| apportionTotal.equals(FDCHelper.ZERO)){
					if(apportionValue.compareTo(FDCHelper.ZERO)==0|| apportionTotal.compareTo(FDCHelper.ZERO)==0){
//					if(directAmount.compareTo(FDCHelper.ZERO)>0){
						//直接金额
						amount=directAmount;
					}
					else{
//						//存在分摊
						amount=apportionValue.multiply(amountTotal.subtract(directTotal));
						//amount=amount.divide(apportionTotal,2,BigDecimal.ROUND_HALF_EVEN);
						amount=amount.divide(apportionTotal,10,BigDecimal.ROUND_HALF_EVEN);
						amount=amount.add(directAmount);
					}
					entry.setAmount(amount);					
				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    			

    }


	/**
	 * 描述：成本分摊计算（末级拆分）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
	 */
    private void apptAmountBotUp(IObjectCollection allEntrys,int curIndex){
    	int level;
		//金额	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		amountTotal=amountTotal.setScale(10);	
		
		
		
		//分摊值汇总、直接费用汇总
		BigDecimal apportionValue=FDCHelper.ZERO;//null;
		BigDecimal apportionTotal=FDCHelper.ZERO;
		
		BigDecimal directAmount=FDCHelper.ZERO;//null;	
		BigDecimal directTotal=FDCHelper.ZERO;
		
		//金额	
		BigDecimal ratio=FDCHelper.ZERO;
		
    	FDCSplitBillEntryInfo curEntry=(FDCSplitBillEntryInfo)allEntrys.getObject(curIndex);		
    	
    	//根结点
    	if(curEntry.getLevel()!=0){
    		return;
    	} 
    	int topLevel=curEntry.getLevel();
    	
    	amountTotal=curEntry.getAmount();
    	if(amountTotal==null){
    		amountTotal=FDCHelper.ZERO;
    	}
    	
    	apportionTotal=curEntry.getApportionValueTotal();
    	if(apportionTotal==null){
    		apportionTotal=FDCHelper.ZERO;
    	}
    	
    	directTotal=curEntry.getDirectAmountTotal();
    	if(directTotal==null){
    		directTotal=FDCHelper.ZERO;
    	}
    	    	
    	amount=amountTotal.subtract(directTotal);
    	if(apportionTotal.compareTo(FDCHelper.ZERO)==0){
    		ratio=FDCHelper.ZERO;
    	}else{
    		//比例值，非直接显示值，需要保证小数位数
    		ratio=amount.divide(apportionTotal,10,BigDecimal.ROUND_HALF_EVEN);	
    	}
    	
    	//calcApportionAmountBotUp(curIndex,ratio,FDCHelper.ZERO,FDCHelper.ZERO);

		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()==topLevel+1){
				apptAmountBotUp(allEntrys,i,ratio,FDCHelper.ZERO,FDCHelper.ZERO);
							
			}else if(entry.getLevel()<=topLevel){
				break;
			}
		}
		
		
    	
    	
    	
    	/*    	
		if(entry.isIsLeaf()){
			return;
		}
		
		
			
		//父级
		level=entry.getLevel();
		amountTotal=entry.getAmount();
		
		//汇总
		apportionTotal=entry.getApportionValueTotal();				
		if(apportionTotal==null){
			apportionTotal=FDCHelper.ZERO;
		}					
		directTotal=entry.getDirectAmountTotal();				
		if(directTotal==null){
			directTotal=FDCHelper.ZERO;
		}					
		
		//子级
		for(int i=curIndex+1; i<kdtEntrys.getRowCount(); i++){				
			row=kdtEntrys.getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			
			//直接下级
			if(entry.getLevel()==(level+1)){				
				apportionValue=entry.getApportionValue();
				if(apportionValue==null){
					apportionValue=FDCHelper.ZERO;
				}
				directAmount=entry.getDirectAmount();
				if(directAmount==null){
					directAmount=FDCHelper.ZERO;
				}
				
				if(apportionValue.equals(FDCHelper.ZERO)){
					amount=directAmount;
				}
				else	//存在分摊
				{					
					amount=apportionValue.multiply(amountTotal.subtract(directTotal));
					amount=amount.divide(apportionTotal,2,BigDecimal.ROUND_HALF_EVEN);
					amount=amount.add(directAmount);
				}
				entry.setAmount(amount);					
				row.getCell("amount").setValue(amount);
				
				calcApportionAmountBotUp(i);
				
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	*/
    }
    

	/**
	 * 描述：成本分摊计算（附加科目）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
	 */
    private void apptAmountAddlAcct(IObjectCollection allEntrys,int curIndex){		
    	FDCSplitBillEntryInfo curEntry =(FDCSplitBillEntryInfo)allEntrys.getObject(curIndex);		
		if(curEntry.isIsLeaf()){
			return;
		}
		
		//产品拆分
		if(curEntry.getSplitType()!=null && curEntry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
			apptAmountProduct(allEntrys,curIndex);
			return;
		}
		
    			
		//金额	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;	
		
		//分摊值汇总、直接费用汇总
		BigDecimal apportionValue=FDCHelper.ZERO;//null;
		BigDecimal apportionTotal=FDCHelper.ZERO;	
		
		BigDecimal directAmount=FDCHelper.ZERO;//null;	
		BigDecimal directTotal=FDCHelper.ZERO;
			
		
			
		//父级
		int level=curEntry.getLevel();
		int acctLevel=curEntry.getCostAccount().getLevel();
		BOSUuid projId=curEntry.getCostAccount().getCurProject().getId();
		
		amountTotal=curEntry.getAmount();
		if(amountTotal==null){
			amountTotal=FDCHelper.ZERO;
		}					
		
		//汇总	
		apportionTotal=curEntry.getOtherRatioTotal();
		if(apportionTotal==null){
			apportionTotal=FDCHelper.ZERO;
		}					
		directTotal=curEntry.getDirectAmountTotal();				
		if(directTotal==null){
			directTotal=FDCHelper.ZERO;
		}					
		
		/*
		//明细工程项目分摊金额，非明细工程项目，累加下级工程项目的相同科目金额
		if(entry.getCostAccount().getCurProject().isIsLeaf()){
			
		}
		*/
		
		
		//（没有设置分摊比例）检测是否为唯一的子科目
		int count=0;		
		if(apportionTotal.compareTo(FDCHelper.ZERO)==0){
			for(int i=curIndex+1; i<allEntrys.size(); i++){				
				FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);
				//直接下级
				if(entry.getCostAccount().getCurProject().getId().equals(projId)){
					if(entry.getCostAccount().getLevel()==(acctLevel+1)){						
						count++;
						//idx=i;
					}
				}else if(entry.getLevel()<=level){
					break;
				}
			}    				
		}
		
		
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);
			
			//直接下级
			if(entry.getLevel()==(level+1)){
				if(entry.getCostAccount().getCurProject().getId().equals(projId)){
					if(entry.getCostAccount().getLevel()==(acctLevel+1)){
						if(count==1){
							amount=amountTotal;
							
						}else{
							apportionValue=entry.getApportionValue();
							if(apportionValue==null){
								apportionValue=FDCHelper.ZERO;
							}
							directAmount=entry.getDirectAmount();
							if(directAmount==null){
								directAmount=FDCHelper.ZERO;
							}
							
							//if(apportionValue.equals(FDCHelper.ZERO)|| apportionTotal.equals(FDCHelper.ZERO)){
							if(apportionValue.compareTo(FDCHelper.ZERO)==0|| apportionTotal.compareTo(FDCHelper.ZERO)==0){
								amount=directAmount;
							}else{	
//								存在分摊
								//TODO 可能要处理精度
								amount=apportionValue.multiply(amountTotal.subtract(directTotal));
								//amount=amount.divide(apportionTotal,2,BigDecimal.ROUND_HALF_EVEN);
								amount=amount.divide(apportionTotal,10,BigDecimal.ROUND_HALF_EVEN);
								amount=amount.add(directAmount);
							}						
						}					
						entry.setAmount(amount);					
						apptAmountAddlAcct(allEntrys,i);		
						
					}
				}
			}else if(entry.getLevel()<=level){
				break;
			}
		} 
		
		
		
    }



    /**
	 * 描述：成本分摊计算（末级拆分，递归拆分）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
	 */
    private void apptAmountBotUp(IObjectCollection allEntrys,int curIndex, BigDecimal ratio, BigDecimal parApportionTotal, BigDecimal parDirectAmount){
    	if(curIndex>=allEntrys.size()){
    		return;
    	}
    	if(parApportionTotal==null){
			parApportionTotal=FDCHelper.ZERO;
		}
		if(parDirectAmount==null){
			parDirectAmount=FDCHelper.ZERO;
		}
		
		FDCSplitBillEntryInfo topEntry =(FDCSplitBillEntryInfo)allEntrys.getObject(curIndex);
		
    	int topLevel=topEntry.getLevel();
    	int projLevel=topEntry.getCostAccount().getCurProject().getLevel();
		
		//BigDecimal apportionValue=FDCHelper.ZERO;
		//BigDecimal apportionTotal=FDCHelper.ZERO;
		//BigDecimal directAmount=FDCHelper.ZERO;
    	
		

		BigDecimal apportionValue=topEntry.getApportionValue();
		if(apportionValue==null){
			apportionValue=FDCHelper.ZERO;
		}
		BigDecimal apportionTotal=topEntry.getApportionValueTotal();	
		if(apportionTotal==null){
			apportionTotal=FDCHelper.ZERO;
		}	
		
		BigDecimal directAmount=topEntry.getDirectAmount();
		if(directAmount==null){
			directAmount=FDCHelper.ZERO;
		}

		BigDecimal directTotal=FDCHelper.ZERO;
		    	
		BigDecimal amount=FDCHelper.ZERO;
		BigDecimal amountTotal=FDCHelper.ZERO;
		
		
		//分摊直接费用（目前只有明细工程明细科目才有直接费用，直接费用实际不用分摊）
		if(parApportionTotal.compareTo(FDCHelper.ZERO)!=0){
			/*directAmount=directAmount.add(
					parDirectAmount.multiply(apportionValue).divide(parApportionTotal,2,BigDecimal.ROUND_HALF_EVEN));*/	
			directAmount=directAmount.add(
					parDirectAmount.multiply(apportionValue).divide(parApportionTotal,10,BigDecimal.ROUND_HALF_EVEN));	
		}				
		
		
		if(topEntry.getCostAccount().getCurProject().isIsLeaf()){		
			//明细工程的科目直接分摊
			
			/*amount=apportionValue.multiply(ratio).add(directAmount);
			amount=amount.setScale(2,BigDecimal.ROUND_HALF_EVEN);
			amountTotal=amount;*/
			
			//第一级科目进行末级分摊
	    	if(!topEntry.isIsAddlAccount() && !isProdSplitLeaf(topEntry)){
	    		amount=apportionValue.multiply(ratio).add(directAmount);
				//amount=amount.setScale(2,BigDecimal.ROUND_HALF_EVEN);
				//amountTotal=amount;	    		
	    	}
			topEntry.setAmount(amount);
			

			//处理包含的附加科目和产品拆分	jelon 12/22/2006				
	    	/*if(topEntry.isIsAddlAccount()){
	    		calcApportionAmountAddlAcct(rowIndex);
	    	}else if(isProdSplitParent(topEntry)){
	    		calcApportionAmountProduct(rowIndex);
	    	}	*/		
	    	/*if(!topEntry.getCostAccount().isIsLeaf()){
	    		if(isProdSplitParent(topEntry)){
	    			//产品拆分
		    		calcApportionAmountProduct(rowIndex);
	    		}else{
	    			//附加科目
	    			calcApportionAmountAddlAcct(rowIndex);
	    		}
	    	}*/
	    	
			//其他下级科目需按附加科目分摊或按产品分摊
    		if(isProdSplitParent(topEntry)){
    			//产品拆分
	    		apptAmountProduct(allEntrys, curIndex);
    			
    		}else if(!topEntry.getCostAccount().isIsLeaf()){
    			//附加科目
    			apptAmountAddlAcct(allEntrys, curIndex);	    			
    		}
			
			
		}else{		
			//非明细工程的各科目的成本从下级工程相同科目累加
			
			String topAcctNo=topEntry.getCostAccount().getLongNumber().replace('.','!');
			
			for(int i=curIndex+1; i<allEntrys.size(); i++){				
				FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);
				
				if(entry.getLevel()>topLevel){
					/*if(entry.getCostAccount().getCurProject().getLevel()==(projLevel+1)
							&& entry.getCostAccount().getLongNumber().replace('.','!').equals(topAcctNo)
							&& !isProdSplitLeaf(entry)){*/
					if(isChildProjSameAcct(entry,topEntry)){
						
						//if(!entry.isIsAddlAccount()){
							apptAmountBotUp(allEntrys,i,ratio,apportionTotal,directAmount);					
		
							amountTotal=amountTotal.add(entry.getAmount());			
						//}					
					}
				}else if(entry.getLevel()<=topLevel){
					break;
				}
			}
			
			topEntry.setAmount(amountTotal);
		}			
    		
	}
 

    /**
     * 面子刷新接口（用于合同，变更拆分，无文本合同付款拆分）
     * @author sxhong  		Date 2006-12-13
     * @param fdcSplitBillInfo 
     * @param iFDCSplitBillEntry 本地接口或者远程接口，null值则不更新到数据库
     * @throws BOSException
     * @throws EASBizException
     */
    public void refreshApportionAmount(FDCSplitBillInfo fdcSplitBillInfo,IFDCSplitBillEntry iFDCSplitBillEntry) throws BOSException,EASBizException{
    	
    	IObjectCollection allEntrys=(IObjectCollection) fdcSplitBillInfo.get("entrys");
    	FDCSplitBillEntryInfo entry=null;
    	for(int i=0;i<allEntrys.size();i++){
    		entry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);
    		if(entry.getLevel()==0&&entry.getSplitType()!=null&&entry.getSplitType()!=CostSplitTypeEnum.MANUALSPLIT){
    			CurProjectCollection curProjectCollection=getEntryCurProject(entry);
    			setApportionValue(allEntrys,i,curProjectCollection);
    			totApptValue(allEntrys,entry);
    			apptAmount(allEntrys,entry);
    		}
    	}
    	if(iFDCSplitBillEntry==null) return;
    	SelectorItemCollection selector=new SelectorItemCollection();
    	selector.add("amount");
    	selector.add("apportionValue");
    	selector.add("apportionValueTotal");
    	selector.add("otherRatioTotal");
    	selector.add("workLoad");
    	for(Iterator iter=allEntrys.iterator();iter.hasNext();){
			FDCSplitBillEntryInfo info = (FDCSplitBillEntryInfo)iter.next();
			info.setWorkLoad(FDCHelper.divide(info.getAmount(),info.getPrice()));
			iFDCSplitBillEntry.updatePartial(info, selector);
    	}
    }
    


	/**
	 * 描述：设置分摊指标值
	 * @return
	 * @author: 
	 * 创建时间：2006-12-29 <p>
	 */
	private void setApportionValue(IObjectCollection entrys, int index,CurProjectCollection curProjectCollection) throws BOSException{
		
		FDCSplitBillEntryInfo topEntry=(FDCSplitBillEntryInfo)entrys.getObject(index);		
		if(topEntry==null||topEntry.isIsLeaf()){
			return;
		}
		int topIndex=index;
		int topLevel=topEntry.getLevel();		
		//分摊类型，根据父级的分摊类型设置直接下级的分摊指标数据
		ApportionTypeInfo apptType=topEntry.getApportionType();

		BigDecimal apptValue=null;		
		FDCSplitBillEntryInfo entry=null;

		if(topEntry.getCostAccount().getCurProject().isIsLeaf()){		
			//明细工程的科目
			//产品的直接费用拆分可以不选择分摊方案即apptType==null时产品拆分是不选择分摊方案的直接费用拆分
    		if(isProdSplitParent(topEntry)&&apptType!=null){
				//产品拆分
    			ProductTypeInfo productType=null;

				//目标成本比处理
    			AimCostSplitDataGetter aimCost=null;
    			Map mapAimCost=null;
				if(apptType.getId().toString().equals(ApportionTypeInfo.aimCostType)){
					//目标成本
					aimCost=new AimCostSplitDataGetter(ctx,
							topEntry.getCostAccount().getCurProject().getId().toString());	
					//取得产品的金额map
					mapAimCost=aimCost.getProductMap(topEntry.getCostAccount().getId().toString());
				}
    			
    			for(int i=topIndex+1; i<entrys.size(); i++){				
    				entry=(FDCSplitBillEntryInfo)entrys.getObject(i);
    				
    				if(entry.getLevel()==topLevel+1	&& isProdSplitLeaf(entry)){
    					productType=entry.getProduct();
    					
    					apptValue=null;
    					
    					if(apptType.getId().toString().equals(ApportionTypeInfo.aimCostType)){
        					//目标成本比处理
    						//目标成本
    						//AimCostSplitDataGetter aimCost=new AimCostSplitDataGetter(ctx,entry.getCostAccount().getCurProject().getId().toString());	
    						//取得产品的金额map
    						//Map mapAimCost=aimCost.getProductMap( entry.getCostAccount().getId().toString());
    						//是否完全拆分，完全了就是有成本比
    						//isAimFullAppt=aimCost.isFullApportion(acctId);	
    						if (mapAimCost.containsKey(productType.getId().toString())){
    							apptValue = (BigDecimal) mapAimCost.get(productType.getId().toString());							
    						}
    					}else{
    						//只要有分摊方案就要刷新 by sxhong 2008/1/25
    						apptValue= ProjectHelper.getIndexValueByProjProdIdx(ctx, 
    								entry.getCostAccount().getCurProject().getId().toString(),
    								productType.getId().toString(), 
    								apptType.getId().toString(), ProjectStageEnum.DYNCOST);    						
    					}
    					
    					if(apptValue==null ){
    						apptValue=FDCHelper.ZERO;
    					}
						entry.setApportionValue(apptValue);
    					
    				}else{
    					break;
    				}
    			}
    			
    		}else{						
				//附加科目
    			for(int i=topIndex+1; i<entrys.size(); i++){				
    				entry=(FDCSplitBillEntryInfo)entrys.getObject(i);
    				
//    				if(entry.getLevel()>topLevel+1
    				if(entry.getLevel()>topLevel
    						&& entry.getCostAccount().getCurProject().getId().equals(topEntry.getCostAccount().getCurProject().getId())){
    					//递归调用（主要用于处理其中包含的产品拆分）
    					setApportionValue(entrys, i, curProjectCollection);
    				}
    			}	
    			
    			//附加科目不用处理分摊指标值    	
    			//附加科目指标值为分摊时指定的值，不能刷新处理
    		}	
			
			
		}else{		
			//非明细工程的各科目的成本从下级工程相同科目累加
			
			for(int i=topIndex+1; i<entrys.size(); i++){				
				entry=(FDCSplitBillEntryInfo)entrys.getObject(i);
				
				if(entry.getLevel()>topLevel){
					if(isChildProjSameAcct(entry,topEntry)){							
						//递归调用
						setApportionValue(entrys, i, curProjectCollection);
						
						//工程项目分摊指标
						apptValue=null;

						//参与分摊，否则只有直接费用而不用设置分摊指标值
						if(entry.isIsApportion() && apptType!=null){
							for (Iterator iter2 = curProjectCollection.iterator(); iter2.hasNext();){
								CurProjectInfo curProj=(CurProjectInfo)iter2.next();
								
								if(curProj.getId()!=null 
										&& entry!=null && entry.getCostAccount()!=null && entry.getCostAccount().getCurProject()!=null
										&& entry.getCostAccount().getCurProject().getId()!=null
										&& curProj.getId().equals(entry.getCostAccount().getCurProject().getId())){
									//工程项目分摊值集
									CurProjCostEntriesCollection projCosts=curProj.getCurProjCostEntries();
									
									for (Iterator iter3 = projCosts.iterator(); iter3.hasNext();){
										CurProjCostEntriesInfo projCost=(CurProjCostEntriesInfo)iter3.next();										
										if(projCost.getApportionType()!=null 
												&& projCost.getApportionType().getId().equals(apptType.getId())){
											apptValue=projCost.getValue();
											//entry.setApportionValue(apptValue);
//											logger.info("**********\nType:"+projCost.getApportionType()+"apportionValue:"+apptValue);
											break;
										}
									}
									
									break;						
								}					
							}							
						}
						
						if(apptValue==null){
							apptValue=FDCHelper.ZERO;
						}
						entry.setApportionValue(apptValue);				
					}
					
				}else if(entry.getLevel()<=topLevel){
					break;
				}
			}

		}
		
		
		
		/*BigDecimal value=FDCHelper.ZERO;
		FDCSplitBillEntryInfo curEntry=(FDCSplitBillEntryInfo)entrys.getObject(index);		
		if(curEntry.isIsLeaf()){
			return;
		}
		//几个变量 
//		CurProjectCollection curProjectCollection=getEntryCurProject(curEntry);
		String costSplitAcctNo=curEntry.getCostAccount().getLongNumber().replace('!','.');
		int acctLevel=curEntry.getCostAccount().getLevel();
		int projLevel=curEntry.getCostAccount().getCurProject().getLevel();
		
		CurProjectInfo proj=curEntry.getCostAccount().getCurProject();
		boolean isAddlAcct=curEntry.isIsAddlAccount();
		
		//分摊类型，根据父级的分摊类型设置直接下级的分摊指标数据
		ApportionTypeInfo apptType=curEntry.getApportionType();
		ProductTypeInfo productType=curEntry.getProduct();
		
		boolean isTrue=false;//是否汇总的标志
		for(int i=index+1; i<entrys.size(); i++){
			FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)entrys.getObject(i);
			isTrue=false;
			if(isAddlAcct){
				//当前工程的直接下级科目	
				if(entry.getCostAccount().getCurProject().getId().equals(proj.getId())
						&& entry.getCostAccount().getLevel()==acctLevel+1){
					isTrue=true;
				}				
			}else{		
				//直接下级工程的相同科目
				if(entry.getCostAccount().getCurProject().getLevel()==projLevel+1
						&& entry.getCostAccount().getLongNumber().replace('!','.').equals(costSplitAcctNo)){
					isTrue=true;
				}				
			}
				
			if(isTrue){
				
				if(entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitType.PRODSPLIT)&&productType==null){
					//自动拆分和末级拆分中的产品拆分
					int j=i;
					int level=entry.getLevel();
					while (true){
						j++;
						FDCSplitBillEntryInfo entryProd=(FDCSplitBillEntryInfo)entrys.getObject(j);
						if(entryProd==null||entryProd.getLevel()!=level+1||entryProd.getProduct()==null){
							break;
						}
						
						//增加目标成本比处理		jelon 12/27/2006
						BigDecimal idxValue = ProjectHelper.getIndexValueByProjProdIdx(ctx, entryProd.getCostAccount().getCurProject().getId().toString(),
								entryProd.getProduct().getId().toString(), entry.getApportionType().getId().toString());
						BigDecimal idxValue=null;

						//目标成本比处理
						if(entry.getApportionType().getId().toString().equals(ApportionTypeInfo.aimCostType)){
							//目标成本
							AimCostSplitDataGetter aimCost=new AimCostSplitDataGetter(ctx,entryProd.getCostAccount().getCurProject().getId().toString());	
							//取得产品的金额map
							Map mapAimCost=aimCost.getProductMap( entryProd.getCostAccount().getId().toString());
							//是否完全拆分，完全了就是有成本比
							//isAimFullAppt=aimCost.isFullApportion(acctId);	
							if (mapAimCost.containsKey(entryProd.getProduct().getId().toString())){
								idxValue = (BigDecimal) mapAimCost.get(entryProd.getProduct().getId().toString());							
							}
						}else{
							idxValue= ProjectHelper.getIndexValueByProjProdIdx(ctx, entryProd.getCostAccount().getCurProject().getId().toString(),
									entryProd.getProduct().getId().toString(), entry.getApportionType().getId().toString());
							
						}
						//edit end
						
						if(idxValue !=null ){
//							System.out.println("\n**********");
//							System.out.println(idxValue);
							entryProd.setApportionValue(idxValue);
						}
					}
										
				}else if(entry.isIsAddlAccount()){
					//附加科目，将直接费用作为分摊值
					entry.setApportionValue(entry.getDirectAmount());
					entry.setDirectAmount(FDCHelper.ZERO);
					
				}else if(!entry.isIsApportion()){
					//不参与分摊，只有直接费用
					entry.setApportionValue(FDCHelper.ZERO);
					
				}else{
					//使用工程项目的分摊指标作为分摊值
					//工程项目集
					for (Iterator iter2 = curProjectCollection.iterator(); iter2.hasNext();){
						CurProjectInfo curProj=(CurProjectInfo)iter2.next();
						
						if(curProj.getId()!=null 
								&& entry!=null && entry.getCostAccount()!=null && entry.getCostAccount().getCurProject()!=null
								&& entry.getCostAccount().getCurProject().getId()!=null
								&& curProj.getId().equals(entry.getCostAccount().getCurProject().getId())){
							//工程项目分摊值集
							CurProjCostEntriesCollection projCosts=curProj.getCurProjCostEntries();
							
							for (Iterator iter3 = projCosts.iterator(); iter3.hasNext();){
								CurProjCostEntriesInfo projCost=(CurProjCostEntriesInfo)iter3.next();
								
								if(projCost.getApportionType()!=null 
										&& apptType!=null		//科目下级为附加科目，apptType==null
										&& projCost.getApportionType().getId().equals(apptType.getId())){
									value=projCost.getValue();
									entry.setApportionValue(value);
									logger.info("**********\nType:"+projCost.getApportionType()+"apportionValue:"+value);
									break;
								}
							}
							
							break;						
						}					
					}
					
				}
				
				//递归调用
				setApportionValue(entrys,i,curProjectCollection);
			}				
			//递归调用
//			setApportionValue(entrys,i);
			
		}*/
	}
	
	/**
	 * 当前分录下的工程
	 * @author sxhong  		Date 2006-12-12
	 * @param topEntry
	 * @return
	 * @throws BOSException
	 */
	private CurProjectCollection getEntryCurProject(FDCSplitBillEntryInfo topEntry) throws BOSException{
//		if(topEntry.getLevel()!=0){
//			return;
//		}
		//工程项目
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("longNumber", 
    			topEntry.getCostAccount().getCurProject().getLongNumber().replace('.','!')+"!%", CompareType.LIKE));
    	filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
    	view.setFilter(filter);
    	view.getSelector().add("id");
    	view.getSelector().add("name");
    	view.getSelector().add("longNumber");
    	view.getSelector().add("parent");
    	view.getSelector().add("curProjCostEntries.apportionType.id");
    	view.getSelector().add("curProjCostEntries.value");
    	 	
    	
    	view.getSorter().add(new SorterItemInfo("longNumber"));
    	view.getSorter().add(new SorterItemInfo("sortNo"));
    	
		ICurProject iCurProj = null;
		if(ctx!=null){
			iCurProj=CurProjectFactory.getLocalInstance(ctx);
		}else{
			iCurProj=CurProjectFactory.getRemoteInstance();
			
		}
		CurProjectCollection curProjs = iCurProj.getCurProjectCollection(view);
		//apptObjIds=new String[apptObjs.size()];
		boolean debug=false;
		if(debug){
			for(Iterator iter=curProjs.iterator();iter.hasNext();){
				CurProjectInfo proj=(CurProjectInfo)iter.next();
				System.out.println("curPrj:"+proj.getName()+" \t"+proj.getLongNumber().replace('!', '.'));
				for(Iterator iter2=proj.getCurProjCostEntries().iterator();iter2.hasNext();){
					CurProjCostEntriesInfo entry=(CurProjCostEntriesInfo)iter2.next();
					System.out.println("appType:"+entry.getApportionType()+"\tValue:"+entry.getValue());
				}
			}
		}
		return curProjs;
	}
	/**
	 * 结算拆分的面积刷新接口
	 * @author sxhong  		Date 2006-12-13
	 * @param SettlementCostSplitInfo
	 * @param iSettlementCostSplitEntry 远程或者本地接口，null则不更新到数据库
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public void refreshSettlementSplitApportionAmount(SettlementCostSplitInfo settlementCostSplitInfo,
			ISettlementCostSplitEntry iSettlementCostSplitEntry) throws BOSException,EASBizException{
		//处理直接金额的拆分，不刷新而直接跳出
		SettlementCostSplitEntryCollection entrys=settlementCostSplitInfo.getEntrys();
		
		updateSettlementSplitAmout_ApportionValue(entrys);
		boolean isDirectAmt=false;
		for(Iterator iter=entrys.iterator();iter.hasNext();){
			SettlementCostSplitEntryInfo entry=(SettlementCostSplitEntryInfo)iter.next();
			if(entry.getDirectAmt()!=null&& entry.getDirectAmt().compareTo(FDCHelper.ZERO)>0){
//				return;
				isDirectAmt=true;
				break;
			}
		}
		if(!isDirectAmt){//非直接金额进行分摊
			for(int i=0;i<entrys.size();i++){
	    		SettlementCostSplitEntryInfo entry=(SettlementCostSplitEntryInfo)entrys.get(i);
	    		if(entry.getLevel()==0){
	    			apptAmount(entrys,entry);
	    		}
	    		//刷新保修款
				ContractSettlementBillInfo settlementBill = settlementCostSplitInfo.getSettlementBill();
				if(FDCHelper.toBigDecimal(settlementBill.getSettlePrice()).signum()>0){
					BigDecimal grtAmt=FDCHelper.toBigDecimal(settlementBill.getQualityGuarante())
					.multiply(FDCHelper.toBigDecimal(entry.getAmount()))
					.divide(FDCHelper.toBigDecimal(settlementBill.getSettlePrice()), 5,BigDecimal.ROUND_HALF_UP);
					entry.setGrtSplitAmt(grtAmt);
				}
	    	}
		}
    	
    	
    	if(iSettlementCostSplitEntry==null) return;
    	SelectorItemCollection selector=new SelectorItemCollection();
    	selector.add("amount");
    	selector.add("apportionValue");
    	selector.add("contractAmt");
    	selector.add("changeAmt");
    	selector.add("apportionValueTotal");
    	selector.add("otherRatioTotal");
    	selector.add("grtSplitAmt");
    	for(Iterator iter=entrys.iterator();iter.hasNext();){
    		iSettlementCostSplitEntry.updatePartial((FDCSplitBillEntryInfo)iter.next(), selector);
    	}
	}
	
	/**
	 * 更新引入的合同及变更的金额，同时更新分摊值
	 * @author sxhong  		Date 2006-12-13
	 * @param entrys
	 * @param ctx
	 * @throws BOSException
	 */
	private void updateSettlementSplitAmout_ApportionValue(SettlementCostSplitEntryCollection entrys) throws BOSException{
		SettlementCostSplitEntryInfo entry;
		IObjectCollection sourceEntrys=null;
		EntityViewInfo view=new EntityViewInfo();
		view.getSelector().add("entrys.amount");
		view.getSelector().add("entrys.seq");
		
		BOSObjectType contractType = (new ContractBillInfo()).getBOSType();
//		BOSObjectType conChangeType = (new ContractChangeBillInfo()).getBOSType();
		IFDCSplitBill iFDCSplitBill=null; 
		
		for(int i=0;i<entrys.size();i++){
			entry=entrys.get(i);
			if(entry.getLevel()==0){
				BOSUuid costBill=entry.getCostBillId();
				if(costBill==null){
					//程序失败的提示
					logger.info("costBill ID is null,game over!");
					return;
				}
				FilterInfo filter=new FilterInfo();
				if(contractType.equals(costBill.getType())){
					//合同
					if(ctx!=null){
						iFDCSplitBill=ContractCostSplitFactory.getLocalInstance(ctx);
						
					}else{
						iFDCSplitBill=ContractCostSplitFactory.getRemoteInstance();
					}
					filter.getFilterItems().add(new FilterItemInfo("contractBill.id",costBill.toString()));
					filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
					view.setFilter(filter);
					FDCBillCollection billCollection = iFDCSplitBill.getFDCBillCollection(view);
					//一张合同对应一张拆分
					if(billCollection.size()==1){
						sourceEntrys=(IObjectCollection) billCollection.get(0).get("entrys");
					
						int j=0;
						for(;j<sourceEntrys.size();j++){
							//只要合同没有乱序应该不会乱序
	//						i=i+j;
							entry=entrys.get(i+j);
							if(entry==null) break;
	//						sourceEntrys.get
							BigDecimal amount = ((ContractCostSplitEntryInfo)sourceEntrys.getObject(j)).getAmount();
							entry.setContractAmt(amount);
							entry.setApportionValue(amount);
							entry.setApportionValueTotal(amount);
							entry.setOtherRatioTotal(amount);
						}
						i=i+j-1;
					}
				}else{
					//变更
					if(ctx!=null){
						iFDCSplitBill=ConChangeSplitFactory.getLocalInstance(ctx);
						
					}else{
						iFDCSplitBill=ConChangeSplitFactory.getRemoteInstance();
					}
					filter.getFilterItems().add(new FilterItemInfo("contractChange.id",costBill.toString()));
					filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
					view.setFilter(filter);
					FDCBillCollection billCollection = iFDCSplitBill.getFDCBillCollection(view);
					if(billCollection.size()==1){
						sourceEntrys=(IObjectCollection) billCollection.get(0).get("entrys");
						int j=0;
						for(;j<sourceEntrys.size();j++){
							//只要变更没有乱序应该不会乱序
	//						i=i+j;
							entry=entrys.get(i+j);
							if(entry==null) break;
							BigDecimal amount = ((ConChangeSplitEntryInfo)sourceEntrys.getObject(j)).getAmount();
							entry.setChangeAmt(amount);
							entry.setApportionValue(amount);
							entry.setApportionValueTotal(amount);
							entry.setOtherRatioTotal(amount);
						}
						i=i+j-1;
					}
				}
		
			}
		}
	}

	/**
	 * 付款拆分更新更新合同、变更、成本和已拆分的数据
	 * @param allEntrys
	 * @param ctx
	 * @throws Exception
	 * @author:zhonghui_luo
	 * 2006-12-13 11:40:36
	 *
	 */
	public void updatePaymentSplitAmoutAppt(PaymentSplitEntryCollection allEntrys,PaymentSplitInfo bill) throws BOSException,EASBizException {
		if(allEntrys.size()<=0) {
			logger.error("传入的参数有问题");
			return;
		}
		
		//更新合同和变更的数据，成本和已拆分
		for(int i=0;i<allEntrys.size();i++){
			PaymentSplitEntryInfo entry=allEntrys.get(i);
			if(entry.getCostBillId()!= null){
				String acc = entry.getCostAccount().getId().toString();
				String costId = entry.getCostBillId().toString();
				BigDecimal temp = FDCHelper.ZERO;
				BigDecimal temppay = FDCHelper.ZERO;
				
				
				
				//如果引入的是合同，则更新合同的数据
				if((BOSUuid.read(costId).getType()).equals(new ContractBillInfo().getBOSType())){
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("contractBill",costId));
					view.setFilter(filter);
					view.getSelector().add("id");
					ContractCostSplitCollection coll = null;
					ContractCostSplitInfo cont = null;
					if(ctx!=null){
						coll = ContractCostSplitFactory.getLocalInstance(ctx).getContractCostSplitCollection(view);
					}
					else{
						coll = ContractCostSplitFactory.getRemoteInstance().getContractCostSplitCollection(view);
					}
					if(coll.iterator().hasNext()){
						cont = (ContractCostSplitInfo) coll.iterator().next();
					}
					if(cont!=null){
						String contSplit = cont.getId().toString();
						ContractCostSplitEntryCollection contcoll=null;
						ContractCostSplitEntryInfo itemcont =null;
						EntityViewInfo viewtemp = new EntityViewInfo();
						FilterInfo filtertemp = new FilterInfo();
						filtertemp.getFilterItems().add(new FilterItemInfo("costAccount",acc));
						filtertemp.getFilterItems().add(new FilterItemInfo("parent",contSplit));
						if(entry.getProduct()!=null){
							String product = entry.getProduct().getId().toString();
							filtertemp.getFilterItems().add(new FilterItemInfo("product",product));
						}
						else
							filtertemp.getFilterItems().add(new FilterItemInfo("product",null));
						viewtemp.setFilter(filtertemp);
						viewtemp.getSelector().add("amount");
						if(ctx!=null){
							contcoll = ContractCostSplitEntryFactory.getLocalInstance(ctx).getContractCostSplitEntryCollection(viewtemp);
						}
						else{
							contcoll = ContractCostSplitEntryFactory.getRemoteInstance().getContractCostSplitEntryCollection(viewtemp);
						}
						if(contcoll.iterator().hasNext()){
							itemcont = (ContractCostSplitEntryInfo)contcoll.iterator().next();
							if(itemcont.getAmount()!=null){
								entry.setContractAmt(itemcont.getAmount());
							}
						}
					}
				}
				
				//如果引入的是变更，则更新变更的数据
				else if((BOSUuid.read(costId).getType()).equals(new ContractChangeBillInfo().getBOSType())){
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("contractChange",costId));
					view.setFilter(filter);
					view.getSelector().add("id");
					ConChangeSplitCollection coll = null;
					ConChangeSplitInfo cont = null;
					if(ctx!=null){
						coll = ConChangeSplitFactory.getLocalInstance(ctx).getConChangeSplitCollection(view);
					}
					else{
						coll = ConChangeSplitFactory.getRemoteInstance().getConChangeSplitCollection(view);
					}
					if(coll.iterator().hasNext()){
						cont = (ConChangeSplitInfo) coll.iterator().next();
					}
					if(cont!=null){
						String contSplit = cont.getId().toString();
						ConChangeSplitEntryCollection contcoll=null;
						ConChangeSplitEntryInfo itemcont =null;
						EntityViewInfo viewtemp = new EntityViewInfo();
						FilterInfo filtertemp = new FilterInfo();
						filtertemp.getFilterItems().add(new FilterItemInfo("costAccount",acc));
						filtertemp.getFilterItems().add(new FilterItemInfo("parent",contSplit));
						if(entry.getProduct()!=null){
							String product = entry.getProduct().getId().toString();
							filtertemp.getFilterItems().add(new FilterItemInfo("product",product));
						}
						else
							filtertemp.getFilterItems().add(new FilterItemInfo("product",null));
						viewtemp.setFilter(filtertemp);
						viewtemp.getSelector().add("amount");
						if(ctx!=null){
							contcoll = ConChangeSplitEntryFactory.getLocalInstance(ctx).getConChangeSplitEntryCollection(viewtemp);
						}
						else{
							contcoll = ConChangeSplitEntryFactory.getRemoteInstance().getConChangeSplitEntryCollection(viewtemp);
						}
						if(contcoll.iterator().hasNext()){
							itemcont = (ConChangeSplitEntryInfo)contcoll.iterator().next();
							if(itemcont.getAmount()!=null){
								entry.setChangeAmt(itemcont.getAmount());
							}
						}
					}
				}
				
				//更新成本或者已拆分的.然后将新的成本金额复制到ApportionValue和ApportionValueTotal
				boolean refresh = true;
				if(bill!=null){
					String contractId = null;
					if(bill.getPaymentBill().getContractBillId()!=null){
						contractId = bill.getPaymentBill().getContractBillId();
						ContractBillInfo contractBill = null;
						if(ctx!=null){
							contractBill = ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
						}
						else{
							contractBill = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
						}
						if(!contractBill.isHasSettled()){
							refresh=false;
						}
					}
				}
				SettlementCostSplitEntryCollection coll=null;
				SettlementCostSplitEntryInfo item=null; 
				PaymentSplitEntryCollection collpay=null;
				PaymentSplitEntryInfo itempay=null;
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("costAccount",acc));
				filter.getFilterItems().add(new FilterItemInfo("costBillId",costId));
				if(entry.getProduct()!=null){
					String product = entry.getProduct().getId().toString();
					filter.getFilterItems().add(new FilterItemInfo("product",product));
				}
				else
					filter.getFilterItems().add(new FilterItemInfo("product",null));
				view.getSelector().add("amount");
				view.setFilter(filter);	
				if(refresh == true){
					if(ctx!=null){
						coll = SettlementCostSplitEntryFactory.getLocalInstance(ctx).getSettlementCostSplitEntryCollection(view);
					}
					else{
						coll = SettlementCostSplitEntryFactory.getRemoteInstance().getSettlementCostSplitEntryCollection(view);
					}
					for(Iterator iter = coll.iterator(); iter.hasNext();){
						item = (SettlementCostSplitEntryInfo) iter.next();	
						if(item.getAmount() != null)
							temp = temp.add(item.getAmount());
					}
					entry.setCostAmt(temp);
					entry.setApportionValue(temp);
					entry.setApportionValueTotal(temp);
				}
				
				else{
					BigDecimal tempCon = FDCHelper.ZERO;
					if(entry.getContractAmt()!=null){
						tempCon = entry.getContractAmt();
					}
					BigDecimal tempCha =FDCHelper.ZERO;
					if(entry.getChangeAmt()!=null){
						tempCha = entry.getChangeAmt();
					}
					BigDecimal tempAmt= tempCon.add(tempCha);
					entry.setCostAmt(tempAmt);
					entry.setApportionValue(tempAmt);
					entry.setApportionValueTotal(tempAmt);
				}
				if(ctx!=null){
					collpay = PaymentSplitEntryFactory.getLocalInstance(ctx).getPaymentSplitEntryCollection(view);
				}
				else{
					collpay = PaymentSplitEntryFactory.getRemoteInstance().getPaymentSplitEntryCollection(view);
				}
				for(Iterator iter = collpay.iterator(); iter.hasNext();){
					itempay = (PaymentSplitEntryInfo) iter.next();	
					if(itempay.getAmount() != null)
						temppay = temppay.add(itempay.getAmount());
				}
				entry.setSplitedCostAmt(temppay);						
			}
		}
	}
	
	public void refreshPaymentSplit(PaymentSplitInfo bill,IPaymentSplitEntry iPaymentSplitEntry) throws BOSException,EASBizException{		
		PaymentSplitEntryCollection temp = bill.getEntrys();
		updatePaymentSplitAmoutAppt(temp,bill);
		boolean isDir = false;
		//如果有直接金额，不更新金额。没有，则按照新的值计算金额
		for (Iterator iter = temp.iterator(); iter.hasNext();){
			PaymentSplitEntryInfo info = (PaymentSplitEntryInfo) iter.next();
			if((info.getDirectAmt()!=null)&&(info.getDirectAmt().compareTo(FDCHelper.ZERO)>0)){
				isDir = true;
				break;
			}				
		}
		//调用重新计算拆分值
		if(isDir==false){
			for(Iterator iter = temp.iterator(); iter.hasNext();){
				PaymentSplitEntryInfo info = (PaymentSplitEntryInfo) iter.next();
				if(info.getLevel()==0){
					//apptAmount(bill.getEntrys(),info);
					apptAmount(temp,info);
				}
			}
		}
		if(iPaymentSplitEntry==null)
			return;
		SelectorItemCollection selector=new SelectorItemCollection();
    	selector.add("amount");
    	selector.add("contractAmt");
    	selector.add("changeAmt");
    	selector.add("costAmt");
    	selector.add("splitedCostAmt");
    	selector.add("apportionValue");
    	selector.add("apportionValueTotal");
    	selector.add("otherRatioTotal");
    	for(Iterator iter=temp.iterator();iter.hasNext();){
    		iPaymentSplitEntry.updatePartial((PaymentSplitEntryInfo)iter.next(), selector);
    	}
	}
	
	
	public void refreshConWithoutTextPaymentSplit(PaymentSplitInfo bill,IFDCSplitBillEntry iFDCSplitBillEntry) throws BOSException,EASBizException{
    	IObjectCollection allEntrys=(IObjectCollection) bill.get("entrys");
    	FDCSplitBillEntryInfo entry=null;
    	for(int i=0;i<allEntrys.size();i++){
    		entry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);
    		if(entry.getLevel()==0&&entry.getSplitType()!=null&&entry.getSplitType()!=CostSplitTypeEnum.MANUALSPLIT){
    			CurProjectCollection curProjectCollection=getEntryCurProject(entry);
    			setApportionValue(allEntrys,i,curProjectCollection);
    			totApptValue(allEntrys,entry);
    			apptAmount(allEntrys,entry);
    		}
    		((PaymentSplitEntryInfo)entry).setPayedAmt(((PaymentSplitEntryInfo)entry).getAmount());
    	}
    	if(iFDCSplitBillEntry==null) return;
    	SelectorItemCollection selector=new SelectorItemCollection();
    	selector.add("amount");
    	selector.add("apportionValue");
    	selector.add("apportionValueTotal");
    	selector.add("otherRatioTotal");
    	selector.add("payedAmt");
    	for(Iterator iter=allEntrys.iterator();iter.hasNext();){
			iFDCSplitBillEntry.updatePartial((FDCSplitBillEntryInfo)iter.next(), selector);
    	}
	}


	
	/**
	 * 描述：修正金额
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-12-26 <p>
	 */
    private void adjustAmount(IObjectCollection allEntrys){
    	FDCSplitBillEntryInfo entry=null;

		for(int i=0; i<allEntrys.size(); i++){		
			entry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);
			if(entry.getLevel()==0){				
				
				adjustAmountProject(allEntrys, i);
			}
		}    	
    }
    

	
	/**
	 * 修正金额前进行四舍五入,修正后对二次拆分(产品拆分,附加科目拆分进行再次拆分) by sxhong 2007年6月21日 
	 * 描述：修正金额
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-12-26 <p>
	 */
    private void adjustAmount(IObjectCollection allEntrys,FDCSplitBillEntryInfo curEntry){
    	FDCSplitBillEntryInfo entry=curEntry;    	
		int index=allEntrys.indexOf(curEntry);
		if(index==-1){
			return;
		}
		//由于向上累加时存在误差，所以四舍五入在分摊完成后再进行处理	jelon 12/26/2006
		//修正金额前进行四舍五入 by sxhong 2007年6月21日
		int level=curEntry.getLevel();
		BigDecimal amount=null;
		for(int i=index+1; i<allEntrys.size(); i++){				
			FDCSplitBillEntryInfo tempEntry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);
			
			if(tempEntry.getLevel()>level){
				amount=tempEntry.getAmount();
				
				boolean isDirectAmt=false;
				if(tempEntry.getDirectAmount()!=null
				   &&tempEntry.getDirectAmount().compareTo(FDCHelper.ZERO)>0
						&&(tempEntry.getApportionValue()==null
						   ||tempEntry.getApportionValue().compareTo(FDCHelper.ZERO)==0)){
					//直接金额大于0且没有分摊值
					isDirectAmt=true;
				}
				if(amount!=null&&!isDirectAmt){//直接金额不做处理
					amount=amount.setScale(2,BigDecimal.ROUND_HALF_EVEN);
					tempEntry.setAmount(amount);
				}
							
			}else{
				break;
			}
		}
    	
		CostSplitTypeEnum splitType = curEntry.getSplitType();
		if(splitType!=null&&splitType==CostSplitTypeEnum.BOTUPSPLIT){
			adjustBotupAmt(allEntrys, curEntry, index, level);
		}else{
	    	//其它的拆分修正
	    	boolean isProj=false;
	    	if(!entry.isIsAddlAccount()){
	    		if(entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
	    			if(!entry.isIsLeaf()){
	    				isProj=true;
	    			}
	    		}else{
					isProj=true;
	    		}
			}    	
	    	
	    	if(isProj){
	    		adjustAmountProject(allEntrys, index);
	//    		System.out.println("test");
	    	}else{
	    		adjustAmountAccount(allEntrys, index);    		
	    	}
		}
    	//对修正后的数据进行产品拆分
    	for(int i=index+1;i<allEntrys.size();i++){
    		FDCSplitBillEntryInfo topEntry=(FDCSplitBillEntryInfo) allEntrys.getObject(i);
    		if(topEntry.getLevel()<=level){
    			break;
    		}
    		if(topEntry.getCostAccount().getCurProject().isIsLeaf()){		  	    	
    			//其他下级科目需按附加科目分摊或按产品分摊
        		if(isProdSplitParent(topEntry)){
        			//产品拆分
    	    		apptAmountProduct(allEntrys, i);
    	    		adjustAmount(allEntrys,topEntry);
        		}else if(!topEntry.getCostAccount().isIsLeaf()){
        			//附加科目
        			//TODO 暂时不处理精度,产品拆分及附加科目的拆分应该在拆分内部进行精度调整
//        			apptAmountAddlAcct(allEntrys, i);	    			
        		}
    			
    		}
    	}
    	
    }

	/**
	 *末级拆分修正 by sxhong //2008-02-49 09:09:36 
	 * @param allEntrys
	 * @param curEntry
	 * @param index
	 * @param level
	 */
	private void adjustBotupAmt(IObjectCollection allEntrys,
			FDCSplitBillEntryInfo curEntry, int index, int level) {
		//末级拆分修正
		BigDecimal total=FDCHelper.ZERO;
		BigDecimal maxAmount=FDCHelper.ZERO;
		int max=index;
		for(int i=index+1; i<allEntrys.size(); i++){		
			FDCSplitBillEntryInfo myEntry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);
			if(myEntry.getLevel()<=level){
				break;
			}
			if(myEntry.getCostAccount().getCurProject().isIsLeaf() 
					&&!myEntry.isIsAddlAccount() 
					&& !isProdSplitLeaf(myEntry)){						
				//明细科目修正
				BigDecimal amt = FDCHelper.toBigDecimal(myEntry.getAmount());
				total=total.add(amt);
				if(amt.compareTo(maxAmount)>0){
					maxAmount=amt;
					max=i;
				}
			}
		}
		if(max>index){
			FDCSplitBillEntryInfo myEntry=(FDCSplitBillEntryInfo)allEntrys.getObject(max);
			BigDecimal amt=FDCHelper.toBigDecimal(myEntry.getAmount());
			amt=FDCHelper.toBigDecimal(curEntry.getAmount()).subtract(total).add(amt);
			myEntry.setAmount(amt);
			//汇总到非明细
			for(int i=index+1; i<allEntrys.size(); i++){		
				FDCSplitBillEntryInfo myEntry1=(FDCSplitBillEntryInfo)allEntrys.getObject(i);
				if(myEntry1.getLevel()<=level){
					break;
				}
				if(!myEntry1.getCostAccount().getCurProject().isIsLeaf()){
					BigDecimal sum=FDCHelper.ZERO;
					for(int j=i+1;j<allEntrys.size();j++){
						FDCSplitBillEntryInfo myEntry2=(FDCSplitBillEntryInfo)allEntrys.getObject(j);
						if(myEntry2.getLevel()<=myEntry1.getLevel()){
							break;
						}
						if(myEntry2.getLevel()==myEntry1.getLevel()+1){
							sum=sum.add(FDCHelper.toBigDecimal(myEntry2.getAmount()));
						}
					}
					myEntry1.setAmount(sum);
				}
			}
		}
	}
    
    
    
   

	
	/**
	 * 描述：修正金额
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-12-26 <p>
	 */
    private void adjustAmountProject(IObjectCollection allEntrys, int index){
		FDCSplitBillEntryInfo curEntry =(FDCSplitBillEntryInfo)allEntrys.getObject(index);		
		if(curEntry.isIsLeaf()){
			return;
		}
		
		//父级
		BigDecimal curAmount=curEntry.getAmount();
		
		//金额	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		amountTotal = amountTotal.setScale(10);			
		
			
		int level=curEntry.getLevel();
		//int projLevel=curEntry.getCostAccount().getCurProject().getLevel();		

		
		//直接下级工程
		IRow row;
		FDCSplitBillEntryInfo entry=null;

		int idx=0;
		BigDecimal amountMax=FDCHelper.ZERO;
		
		for(int i=index+1; i<allEntrys.size(); i++){		
			entry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()==(level+1)){
				if(entry.getCostAccount().getLongNumber().replace('!','.').equals(
						curEntry.getCostAccount().getLongNumber().replace('!','.'))){
					amount=entry.getAmount();
					if(amount==null){
						amount=FDCHelper.ZERO;
					}
					
					amountTotal=amountTotal.add(amount);

					//修正项为金额最大的项
					//if(amount.compareTo(FDCHelper.ZERO)!=0){
					if(amount.compareTo(amountMax)>=0){
						amountMax=amount;
						idx=i;						
					}
				}else{
					
				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
		

		if(idx>0 && curAmount!=null&&curAmount.compareTo(amountTotal)!=0){	
			entry=(FDCSplitBillEntryInfo)allEntrys.getObject(idx);

			amount=entry.getAmount();
			if(amount==null){
				amount=FDCHelper.ZERO;
			}
			amount=amount.add(curAmount.subtract(amountTotal));			
			entry.setAmount(amount);

		}


		for(int i=index+1; i<allEntrys.size(); i++){		
			entry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()==(level+1)){
				if(entry.getCostAccount().getLongNumber().replace('!','.').equals(
						curEntry.getCostAccount().getLongNumber().replace('!','.'))){
					
					if(!entry.isIsLeaf()){
						adjustAmountAccount(allEntrys,i);
					}
					
					if(!entry.getCostAccount().getCurProject().isIsLeaf()){
						adjustAmountProject(allEntrys,i);
						
					}
					
				}else{
					
				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
    }
    
	
	
	/**
	 * 描述：修正金额
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-12-26 <p>
	 */
    private void adjustAmountAccount(IObjectCollection allEntrys, int index){
		FDCSplitBillEntryInfo curEntry =(FDCSplitBillEntryInfo)allEntrys.getObject(index);	
		if(curEntry.isIsLeaf()){
			return;
		}
		
		//父级
		BigDecimal curAmount=curEntry.getAmount();
		
		//金额	
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		//BigDeciaml 使用的时候，一定要重新赋值
		amountTotal = amountTotal.setScale(10);			
		
			
		int level=curEntry.getLevel();
		//int projLevel=curEntry.getCostAccount().getCurProject().getLevel();		

		
		//直接下级工程
		IRow row=null;
		FDCSplitBillEntryInfo entry=null;

		int idx=0;
		
		for(int i=index+1; i<allEntrys.size(); i++){			
			entry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);	
						
			if(entry.getLevel()==(level+1)){
				if(entry.getCostAccount().getCurProject().getId().equals(curEntry.getCostAccount().getCurProject().getId())){				
					amount=entry.getAmount();
					if(amount==null){
						amount=FDCHelper.ZERO;
					}
					
					amountTotal=amountTotal.add(amount);
					
					if(amount.compareTo(FDCHelper.ZERO)!=0){
						idx=i;						
					}
				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
		

		if(idx>0 && curAmount.compareTo(amountTotal)!=0){			
			entry=(FDCSplitBillEntryInfo)allEntrys.getObject(idx);	

			amount=entry.getAmount();
			if(amount==null){
				amount=FDCHelper.ZERO;
			}
			amount=amount.add(curAmount.subtract(amountTotal));			
			entry.setAmount(amount);
		}
		

		for(int i=index+1; i<allEntrys.size(); i++){			
			entry=(FDCSplitBillEntryInfo)allEntrys.getObject(idx);	
			
			
			if(entry.getLevel()==(level+1)){
				if(entry.getCostAccount().getCurProject().getId().equals(curEntry.getCostAccount().getCurProject().getId())){				
					if(!entry.isIsLeaf()){
						adjustAmountAccount(allEntrys,i);
					}
				}
			}else if(entry.getLevel()<=level){
				break;
			}
		}    	
    }
    

	
	/**
	 * 描述：设置拆分分录的Seq，以保证显示顺序
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-12-28 <p>
	 */
	public void setEntrySeq(FDCSplitBillEntryInfo entry){
		//index作为seq，actionSave_actionPerformed(ActionEvent e)设置index
		entry.setSeq(entry.getIndex());
		
		//index作为拆分组号，costSplit()设置index，并需要禁用actionSave_actionPerformed(ActionEvent e)中的设置
		//entry.setSeq(entry.getIndex()*100000 + entry.getSeq()%100000);	
	}
    
    
	/******************************************************************/
	/* 				服务器端汇总	sxhong										  */
	/******************************************************************/
	
	/**
	 * 
	 * 描述：生成拆分单据的拆分汇总数据
	 * 保留之前的汇总接口
	 * @param ctx
	 * @param CostSplitBillTypeEnum	拆分类型
	 * @param costBillId				成本单据
	 * @param CostBillEntrys			拆分分录
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:sxhong
	 * 
	 * 创建时间：2006-11-24CoreBillBaseInfo
	 *               <p>
	 */	
	public void collectCostSplit_old(CostSplitBillTypeEnum costSplitBillType, CoreBillBaseInfo coreBillInfo, BOSUuid splitBillId, AbstractObjectCollection CostBillEntrys) throws BOSException, EASBizException {
		if(ctx==null) return;
		if(coreBillInfo==null||coreBillInfo.getId()==null||coreBillInfo.getCU()==null){
			throw new NullPointerException("the method collectCostsplit params coreBillInfo and it's id CU can't be null!");
		}
		BOSUuid costBillId=coreBillInfo.getId();
		//分录集类型转换
		FDCSplitBillEntryCollection billEntrys=new FDCSplitBillEntryCollection();
		FDCSplitBillEntryInfo billEntry=null;	
		
		//获取明细工程项目记录成本变化
		Set prjSet=new HashSet();
		
		for(Iterator iter = CostBillEntrys.iterator(); iter.hasNext();){
			billEntry=(FDCSplitBillEntryInfo)iter.next();	
			
			if(billEntry.getLevel()>=0){
				billEntrys.add(billEntry);
			}
			
			//将明细工程项目添加以记录变化
			if(billEntry.getCostAccount()!=null
					&&billEntry.getCostAccount().getCurProject()!=null
					&&billEntry.getCostAccount().getCurProject().getId()!=null
					&&billEntry.getCostAccount().getCurProject().isIsLeaf()){
				prjSet.add(billEntry.getCostAccount().getCurProject().getId().toString());
			}
		}
						
		
		FilterInfo filter = null;		
		
		//删除原来的汇总
		deleteCostSplit(ctx,costSplitBillType,costBillId);
		if(prjSet.size()>0){
			ProjectCostChangeLogFactory.getLocalInstance(ctx).insertLog(prjSet);
		}
				
		//生成新的汇总		
		CostSplitInfo split=new CostSplitInfo();
		//split.setCostBillType(CostSplitBillTypeEnum.PAYMENTSPLIT);
		split.setCostBillType(costSplitBillType);
		split.setCostBillId(costBillId);
		split.setSplitBillId(splitBillId);
		split.setIsInvalid(false);
		split.setControlUnitId(coreBillInfo.getCU().getId().toString());
		
		CostSplitEntryInfo item=null;
		CostSplitEntryInfo entry=null;
				
		
		boolean isFound=false;
				
		
		//检索拆分明细相关工程项目的成本科目
		HashSet setProj=new HashSet();
		for(Iterator iter = billEntrys.iterator(); iter.hasNext();){
			billEntry=(FDCSplitBillEntryInfo)iter.next();			
			setProj.add(billEntry.getCostAccount().getCurProject().getId());		
		}
		
		EntityViewInfo view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", setProj,CompareType.INCLUDE));
    	filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
    	view.setFilter(filter);
    	    	    	
    	view.getSelector().add("id");
    	view.getSelector().add("name");
    	view.getSelector().add("longNumber");
    	view.getSelector().add("level");
    	view.getSelector().add("isLeaf");
    	view.getSelector().add("parent");
    	view.getSelector().add("parent.id");
    	
    	view.getSelector().add("curProject.id");
    	view.getSelector().add("curProject.name");
    	view.getSelector().add("curProject.longNumber");
    	view.getSelector().add("curProject.level");
    	view.getSelector().add("curProject.isLeaf");
    	view.getSelector().add("curProject.parent");
    	view.getSelector().add("curProject.parent.id");
    	      	
    	view.getSelector().add("fullOrgUnit.id");
    	view.getSelector().add("fullOrgUnit.name");
    	view.getSelector().add("fullOrgUnit.longNumber");
    	view.getSelector().add("fullOrgUnit.level");
    	view.getSelector().add("fullOrgUnit.isLeaf");
    	view.getSelector().add("fullOrgUnit.parent");
    	view.getSelector().add("fullOrgUnit.parent.id");

    	view.getSorter().add(new SorterItemInfo("curProject.id"));
    	view.getSorter().add(new SorterItemInfo("longNumber"));
    	
    	CostAccountCollection collAcct = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);  
		

    	
    	CostSplitEntryCollection splitEntrys=null;
    	CostSplitEntryCollection splitEntrys2=new CostSplitEntryCollection();
    	CostSplitEntryCollection splitEntrys4=null;
    	CostSplitEntryCollection splitEntrys3=null;
    	
    	
    	CostSplitEntryCollection acctSplit=null;
    	CostSplitEntryCollection projSplit=null;
    	
    	
    	FDCSplitBillEntryCollection billEntrys2=null;
    	
		int index=0;
		boolean isFirst=false;
		
    	while(index<billEntrys.size()){
//    		进行成本科目拆分的一棵拆分树
    		billEntrys2=new FDCSplitBillEntryCollection();
    		
    		isFirst=true;
        	for(int i=index; i<billEntrys.size(); i++){       		
        		
        		
        		billEntry=billEntrys.get(i);
        		//直到下一个成本
        		if(!isFirst && billEntry.getLevel()==0){
        			index=i;
        			break;
        		}else{       		        	
            		billEntry.setCostAccount(getCostAccount(collAcct,billEntry.getCostAccount().getId()));
            		billEntrys2.add(billEntry);
            		
        			index=i+1;
        		}
        		isFirst=false;
        	}
    		
//        	对一棵拆分树进行汇总
        	acctSplit=collectCostSplitByTree(ctx,billEntrys2, collAcct);
    		for(int i=0; i<acctSplit.size(); i++){
    			entry=acctSplit.get(i);
    			
    			splitEntrys2.add(entry);
    		}                	
    	}
    	
     	//生成汇总分录,将splitEntrys2内的分录汇总到split.getEntrys()内
    	//对产品进行累加
    	BigDecimal amount= FDCHelper.ZERO;//null;
    	BigDecimal prodAmount= FDCHelper.ZERO;
    	//已付款列
    	BigDecimal paidAmount= FDCHelper.ZERO;//null;
    	BigDecimal paidProdAmount= FDCHelper.ZERO;
    	
    	splitEntrys=split.getEntrys();
    	
    	for(int i=0; i<splitEntrys2.size(); i++){
    		entry=splitEntrys2.get(i);
    		amount=entry.getAmount();
    		prodAmount=entry.getProdAmount();
    		paidAmount=entry.getPaidAmount();
    		if(paidAmount==null){
    			paidAmount = FDCHelper.ZERO;
    			entry.setPaidAmount(paidAmount);
    		}

    		paidProdAmount=entry.getPaidProdAmount();
    		if(paidProdAmount==null){
    			paidProdAmount = FDCHelper.ZERO;
    			entry.setPaidProdAmount(paidProdAmount);
    		}
    		
			isFound=false;
			for(int j=0; j<splitEntrys.size(); j++){
				item=splitEntrys.get(j);
				
				//if(item.getCostAccount().getId().equals(entry.getCostAccount().getId())){
				/*if(item.getCostAccount().getId().equals(entry.getCostAccount().getId())
						&& item.getProduct()==entry.getProduct()){
					isFound=true;
					amount=amount.add(item.getAmount());
					prodAmount=prodAmount.add(item.getProdAmount());
					item.setAmount(amount);
					item.setProdAmount(prodAmount);
					
					break;
				}*/
				//可参照如下的条件判断	01/09/2007
				if(item.getCostAccount().getId().equals(entry.getCostAccount().getId())){
					if((!item.isIsProduct() && !entry.isIsProduct())){
						//科目 金额累加
						isFound=true;
						amount=amount.add(item.getAmount());
						item.setAmount(amount);
						
						if(item.getPaidAmount()!=null){
							paidAmount=paidAmount.add(item.getPaidAmount());
						}
						item.setPaidAmount(paidAmount);
						
						break;						
					}else if(item.isIsProduct() && entry.isIsProduct() 
							&& item.getProduct()!=null && entry.getProduct()!=null
							&& item.getProduct().getId().equals(entry.getProduct().getId())){
						//产品 累加
						isFound=true;		
						prodAmount=prodAmount.add(item.getProdAmount());
						item.setProdAmount(prodAmount);
						
						if(item.getPaidProdAmount()!=null){
							paidProdAmount=paidProdAmount.add(item.getPaidProdAmount());
						}
						item.setPaidProdAmount(paidProdAmount);
						break;
						
					}
				}
				
			}
			//isFound=false;
			if(!isFound){
				splitEntrys.add(entry);
			}
    	}
/*    //被替换 2007/4/11 sxhong	
    	//生成汇总分录,将splitEntrys2内合适的分录汇总到split.getEntrys()内
    	BigDecimal amount= FDCHelper.ZERO;//null;
    	
    	splitEntrys=split.getEntrys();
    	
    	for(int i=0; i<splitEntrys2.size(); i++){
    		entry=splitEntrys2.get(i);
    		amount=entry.getAmount();

			isFound=false;
			for(int j=0; j<splitEntrys.size(); j++){
				item=splitEntrys.get(j);
				
				//if(item.getCostAccount().getId().equals(entry.getCostAccount().getId())){
				if(item.getCostAccount().getId().equals(entry.getCostAccount().getId())
						&& item.getProduct()==entry.getProduct()){
					isFound=true;
					amount=amount.add(item.getAmount());
					
					item.setAmount(amount);
					item.setProdAmount(item.getProdAmount().add(entry.getProdAmount()));
					break;
				}
			}
			
			if(!isFound){
				splitEntrys.add(entry);
			}
    	}
    	
*/    	
    	//非产品拆分中不应有产品金额数据（临时处理）		Jelon	12/14/2006
    	for(int i=0; i<splitEntrys.size(); i++){
    		entry=splitEntrys.get(i);
    		if(!entry.isIsProduct()){
    			entry.setProdAmount(FDCHelper.ZERO);
    			entry.setPaidProdAmount(FDCHelper.ZERO);
    		}
    		if(entry.getCostAccount()!=null){
    			if(entry.getCostAccount().getCurProject()!=null){
    				entry.setObjectId(entry.getCostAccount().getCurProject().getId().toString());
    			}else if(entry.getCostAccount().getFullOrgUnit()!=null){
    				entry.setObjectId(entry.getCostAccount().getFullOrgUnit().getId().toString());
    			}
    		}
    	}
    	
    	CostSplitFactory.getLocalInstance(ctx).save(split);
	}
	
	/**
	 * 
	 * 描述：删除拆分单据的拆分汇总数据
	 * 
	 * @param ctx
	 * @param CostSplitBillTypeEnum	拆分类型
	 * @param costBillId				成本单据
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:Jelon Lee 
	 * 
	 * 创建时间：2006-11-24
	 *               <p>
	 */	
	public void deleteCostSplit(Context ctx,CostSplitBillTypeEnum costSplitBillType, BOSUuid costBillId) throws BOSException, EASBizException {
		//删除原来的汇总
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select distinct fobjectid from T_AIM_CostSplitEntry entry inner join T_AIM_CostSplit head on head.fid=entry.fparentid where head.fcostBillId=? and exists (select fid from T_FDC_CurProject where fid=entry.fobjectId and fisleaf=1)");
		builder.addParam(costBillId.toString());
		IRowSet rowSet=builder.executeQuery();
		Set prjSet=new HashSet();
		try{
			while(rowSet.next()){
				prjSet.add(rowSet.getString("fobjectid"));
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		//filterItems.add(new FilterItemInfo("costBillType", costSplitBillType.toString()));
		filterItems.add(new FilterItemInfo("costBillType", costSplitBillType.getValue()));
		filterItems.add(new FilterItemInfo("costBillId", costBillId.toString()));
		filterItems.add(new FilterItemInfo("isInvalid",Boolean.TRUE,CompareType.NOTEQUALS));
		CostSplitFactory.getLocalInstance(ctx).delete(filter);
		
		ProjectCostChangeLogFactory.getLocalInstance(ctx).insertLog(prjSet);
	}

	private CostSplitEntryCollection collectCostSplitByTree(Context ctx, FDCSplitBillEntryCollection billEntrys, CostAccountCollection collAcct) throws BOSException, EASBizException {
		CostSplitEntryCollection collSplit=new CostSplitEntryCollection();
		CostSplitEntryCollection collProd=new CostSplitEntryCollection();
		
		CostSplitEntryInfo item=null;
		CostSplitEntryInfo entry=null;
		
		//CostAccountCollection collAcct=null;
		CostAccountInfo acct=null;
		CurProjectInfo proj=null;
		
		BOSUuid acctId=null;
		
		BigDecimal amount=FDCHelper.ZERO;//null;
		BigDecimal paidAmount=FDCHelper.ZERO;//null;
		
		boolean isFound=false;
		boolean isTrue=true;
						
		FDCSplitBillEntryInfo billEntry=null;	

		boolean isAddProd=false;
		boolean isProduct=false;
		
		//是否是付款拆分
		boolean isPaymentSplit  =  billEntrys.size()>0 && (billEntrys.get(0) instanceof PaymentSplitEntryInfo);
		
		//拆分单据中的所有科目，处理重复科目（目前根据树形结构处理，没有必要处理重复）		
		for(Iterator iter = billEntrys.iterator(); iter.hasNext();){
			billEntry=(FDCSplitBillEntryInfo)iter.next();		
			if(billEntry==null||billEntry.getCostAccount()==null) continue;
			if(billEntry.getCostAccount().isIsLeaf()){

				acct=billEntry.getCostAccount();	
				acctId=acct.getId();

				if(billEntry.getAmount()!= null)
					amount=billEntry.getAmount();
				else
					amount=FDCHelper.ZERO;
				
				if(isPaymentSplit){
					paidAmount = ((PaymentSplitEntryInfo)billEntry).getPayedAmt();
					if(paidAmount==null){
						paidAmount=FDCHelper.ZERO;
					}
				}
				
				entry=new CostSplitEntryInfo();
				
				
				entry.setCostAccount(acct);					
				entry.setAmount(amount);
				entry.setPaidAmount(paidAmount);
				
				entry.setIsProduct(false);
				entry.setProdAmount(FDCHelper.ZERO);	
				entry.setPaidProdAmount(FDCHelper.ZERO);	

				//产品拆分处理
				
				//if(!isFound){	
					isAddProd=false;
					isProduct=false;
					
					if(billEntry.getSplitType()==null 
							|| !billEntry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
						if(billEntry.getProduct()!=null && billEntry.getProduct().getId()!=null){
							//增加产品拆分分录
							isAddProd=true;
						}

					/*}else if(billEntry.getSplitType().equals(CostSplitType.PRODSPLIT)
							&& billEntry.isIsLeaf()){*/
					}else if(billEntry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
						if(billEntry.isIsLeaf()){
							//设置产品拆分分录	
							isProduct=true;
							entry.setAmount(FDCHelper.ZERO);	
							entry.setPaidAmount(FDCHelper.ZERO);	
							
							entry.setIsProduct(true);
							entry.setProduct(billEntry.getProduct());
//							if(billEntry.getAmount()!= null)
//								entry.setProdAmount(billEntry.getAmount());		
							entry.setProdAmount(amount);
							entry.setPaidProdAmount(paidAmount);
						}else{
							entry.setProdAmount(amount);
							entry.setPaidProdAmount(paidAmount);
						}
						
						
					}
					
					if(isProduct){
						collProd.add(entry);
					}else{
						collSplit.add(entry);
					}					
										
					if(isAddProd){//直接归属增加一条产品分录
						entry=new CostSplitEntryInfo();
						//collSplit.add(entry);
						
						entry.setCostAccount(acct);
						entry.setAmount(FDCHelper.ZERO);	
						entry.setPaidAmount(FDCHelper.ZERO);	
						
						entry.setIsProduct(true);
						entry.setProduct(billEntry.getProduct());
						entry.setProdAmount(amount);		
						entry.setPaidProdAmount(paidAmount);
						
						collProd.add(entry);							
					}			
					
				//}
			}				
		}		
		
	
		
		
			
		
		//生成上级科目
		CostAccountInfo parent=null;
		BOSUuid parentId=null;
		
		
		for(Iterator iter = billEntrys.iterator(); iter.hasNext();){
			billEntry=(FDCSplitBillEntryInfo)iter.next();	
			if(billEntry==null||billEntry.getCostAccount()==null) continue;
			if(billEntry.getCostAccount()!=null&&billEntry.getCostAccount().isIsLeaf()){	
				parent=billEntry.getCostAccount().getParent();	
				//findbugs 发现的RCN问题：这里应该判断parent
				if(parent==null) continue;
				parentId=parent.getId();
				//检查拆分分录中是否已经存在
				entry=null;
				isFound=false;
				for(int i=0; i<collSplit.size(); i++){
					item=collSplit.get(i);
					if(item.getCostAccount().getId().equals(parentId)){
						entry=item;
						isFound=true;
						break;
					}
				}
				
				
//				不存在此科目,则生成所有的上级科目
				if(!isFound){	

					isTrue=true;
					while(isTrue){
						isTrue=false;
						
						parent=getCostAccount(collAcct,parentId);
						if(parent!=null){

							
							entry=new CostSplitEntryInfo();
							entry.setCostAccount(parent);
							//entry.setProduct(billEntry.getProduct());							
							
							entry.setIsProduct(false);
							//entry.setProdAmount(billEntry.getAmount());
							entry.setProdAmount(FDCHelper.ZERO);
							
							//entry.setAmount(amount);
							entry.setAmount(FDCHelper.ZERO);
							collSplit.add(entry);	
																						
							parent=parent.getParent();							
							if(parent!=null){
								parentId=parent.getId();	
								isTrue=true;
							}	
						}
						
						
					}
					
				}
			}		
			
			
		}	
		
		
		
		//有产品拆分的工程，生成产品拆分的上级科目		
		CostSplitEntryCollection collProdTemp=(CostSplitEntryCollection) collProd.clone();
		ProductTypeInfo product=null;
		BOSUuid prodId=null;
		
		for(Iterator iter = collProdTemp.iterator(); iter.hasNext();){
			entry=(CostSplitEntryInfo)iter.next();	

			if(entry.isIsProduct() && entry.getCostAccount().isIsLeaf()){	
				parent=entry.getCostAccount().getParent();	
				if(parent==null) break;
				parentId=parent.getId();
				product=entry.getProduct();
				prodId=entry.getProduct().getId();

				//检查拆分分录中是否已经存在
				entry=null;
				isFound=false;
				for(int i=0; i<collProd.size(); i++){
					item=collProd.get(i);
					if(item.getCostAccount().getId().equals(parentId)
							&& item.getProduct().getId().equals(prodId)){
						entry=item;
						isFound=true;
						break;
					}
				}				
				
				//不存在此科目
				if(!isFound){	

					isTrue=true;
					while(isTrue){
						isTrue=false;
						
						parent=getCostAccount(collAcct,parentId);
						if(parent!=null){							
							entry=new CostSplitEntryInfo();
							entry.setCostAccount(parent);	
							entry.setAmount(FDCHelper.ZERO);					
							
							entry.setIsProduct(true);
							entry.setProduct(product);	
							entry.setProdAmount(FDCHelper.ZERO);
							
							collSplit.add(entry);	
																						
							parent=parent.getParent();							
							if(parent!=null){
								parentId=parent.getId();	
								isTrue=true;
							}	
						}
						
						
					}
					
				}
			}		
			
			
		}	

		for(Iterator iter = collProd.iterator(); iter.hasNext();){
			item=(CostSplitEntryInfo)iter.next();	
			collSplit.add(item);
		}
		
		
		
		
		String acctNo=null;
		

		for(Iterator iter2 = collSplit.iterator(); iter2.hasNext();){
			item=(CostSplitEntryInfo) iter2.next();
								
			if(!item.isIsProduct() && item.getCostAccount().getParent()==null){
				collectCostSplitByAcct(collSplit,item.getCostAccount(),collAcct);
			}					
		}
		
		
		//根据拆分根节点科目的工程进行处理
		for(Iterator iter = billEntrys.iterator(); iter.hasNext();){
			billEntry=(FDCSplitBillEntryInfo)iter.next();	
			if(billEntry==null||billEntry.getCostAccount()==null) continue;
			if(billEntry.getLevel()==0){	
								
				//处理每级工程的产品拆分
		    	acct=getCostAccount(collAcct,billEntry.getCostAccount().getId());
				proj=acct.getCurProject();
				
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("longNumber", proj.getLongNumber()+"!%",CompareType.LIKE));
		    	filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		    	view.setFilter(filter);		    	  	
		    	
		    	view.getSelector().add("id");
		    	view.getSelector().add("name");
		    	view.getSelector().add("longNumber");
		    	view.getSelector().add("level");
		    	view.getSelector().add("isLeaf");
		    	view.getSelector().add("parent");
		    	view.getSelector().add("parent.id");
		    	
				CurProjectCollection collProj = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(view);
				
				int level=0;
				int maxLevel=0;
				int minLevel=99999;

				for(Iterator iter2 = collProj.iterator(); iter2.hasNext();){
					proj=(CurProjectInfo) iter2.next();
					level=proj.getLevel();
					
					if(minLevel>level){
						minLevel=level;
					}
					
					if(maxLevel<level){
						maxLevel=level;
					}					
				}
				
				for(int i=maxLevel; i>=minLevel; i--){
					for(Iterator iter2 = collProj.iterator(); iter2.hasNext();){
						proj=(CurProjectInfo) iter2.next();
						level=proj.getLevel();
						
						if(level==i){
//							汇总产品拆分成本（主要用于汇总下级工程项目的产品拆分成本）
							collectCostSplitByProd(collSplit,proj,collAcct);
						}
					}					
				}
				collectCostSplitByProd(collSplit,billEntry.getCostAccount().getCurProject(),collAcct);
							
				

				//生成上级工程及组织的产品拆分	
				isTrue=true;				
				CurProjectInfo projParent=billEntry.getCostAccount().getCurProject().getParent();
				if(projParent==null){
					isTrue=false;
				}
				while(isTrue){
					isTrue=false;

					collectCostSplitByProj(ctx,collSplit,projParent);
					
					
					if(projParent.getParent()!=null){
						projParent=projParent.getParent();
						isTrue=true;
					}
				}
				
				
				SelectorItemCollection sic=new SelectorItemCollection();
				sic.add(new SelectorItemInfo("id"));
				//sic.add(new SelectorItemInfo("fullOrgUnit.*"));
				sic.add(new SelectorItemInfo("fullOrgUnit.id"));
				sic.add(new SelectorItemInfo("fullOrgUnit.parent"));
				sic.add(new SelectorItemInfo("fullOrgUnit.parent.id"));

				isTrue=true;
				if(billEntry.getCostAccount().getCurProject().getParent()==null){
					projParent=billEntry.getCostAccount().getCurProject();
				}
				projParent=CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(projParent.getId()),sic);
				FullOrgUnitInfo orgParent=projParent.getFullOrgUnit();
				if(orgParent==null){
					isTrue=false;
				}
				while(isTrue){
					isTrue=false;
					
					collectCostSplitByOrg(ctx,collSplit,orgParent,projParent);					
					//projParent只用于最底层组织与最上级工程建立关联
					projParent=null;
					
					sic=new SelectorItemCollection();
					sic.add(new SelectorItemInfo("id"));
					sic.add(new SelectorItemInfo("parent"));
					sic.add(new SelectorItemInfo("parent.id"));
					orgParent=FullOrgUnitFactory.getLocalInstance(ctx).getFullOrgUnitInfo(new ObjectUuidPK(orgParent.getId()),sic);

					orgParent=orgParent.getParent();
					if(orgParent!=null){
						isTrue=true;
					}
				}
				
				
				/*if(billEntry.getCostAccount().getCurProject().getParent()!=null){
					collSplit=collectCostSplitByProj2(ctx,collSplit,billEntry.getCostAccount().getCurProject().getParent());
				}*/
				//
				break;
			}
		}
		
		
		return collSplit;
	}
    
	

	

	private CostSplitEntryInfo collectCostSplitByAcct(CostSplitEntryCollection collSplit, CostAccountInfo costAccount, CostAccountCollection collAcct) throws BOSException, EASBizException {
		//CostSplitEntryCollection costSplitEntrys=new CostSplitEntryCollection();
		CostSplitEntryInfo costSplitEntry=null;
		

		CostSplitEntryInfo item=null;
		CostSplitEntryInfo entry=null;
		
		/*
		if(costAccount.isIsLeaf()){
			for(int i=0; i<coll.size(); i++){
				item=coll.get(i);
				if(item.getCostAccount().getId().equals(costAccount.getId()) && !item.isIsProduct()){
					costSplitEntry=item;
					break;
				}
			}
			return costSplitEntry;
		}
		*/

		for(int i=0; i<collSplit.size(); i++){
			item=collSplit.get(i);
			if(item.getCostAccount().getId().equals(costAccount.getId()) && !item.isIsProduct()){
				costSplitEntry=item;
				break;
			}
		}
		//costSplitEntrys.add(costSplitEntry);
		
		if(costAccount.isIsLeaf() || costSplitEntry==null){
			return costSplitEntry;			
			//return costSplitEntrys;
		}
		
		
		CostAccountInfo acct=null;
		CurProjectInfo proj=null;
		
		//BOSUuid acctId=null;
		
		BigDecimal amount=FDCHelper.ZERO;//null;
		
		boolean isFound=false;
		boolean isTrue=true;
								

		FDCSplitBillEntryInfo billEntry=null;	

		boolean isProd=false;
					
		
		//拆分单据中的所有科目，处理上级科目
		CostAccountInfo parent=costAccount;
		BOSUuid parentId=null;		
		parentId=parent.getId();
		
		/*
		//检查拆分分录中是否已经存在
		entry=null;
		isFound=false;
		for(int i=0; i<coll.size(); i++){
			item=coll.get(i);
			if(item.getCostAccount().getId().equals(parentId) && !item.isIsProduct()){
				entry=item;
				isFound=true;
				break;
			}
		}
		*/		

		//CostSplitEntryCollection collProd=null;
		CostSplitEntryCollection collProdAll=new CostSplitEntryCollection();
			

		//累加下级科目金额
		//if(costSplitEntry!=null){

		//累加下级金额
		amount=FDCHelper.ZERO;
		amount=amount.setScale(10);
		
		item=null;			
		//for(Iterator iter = collAcct.iterator(); iter.hasNext();){
		//for(Iterator iter = coll.iterator(); iter.hasNext();){
		int count=collSplit.size();
		for(int j=0; j<count; j++){
			//item=(CostSplitEntryInfo) iter.next();
			item=collSplit.get(j);
			
			//acct=(CostAccountInfo)iter.next();
			acct=item.getCostAccount();
							
			if(acct.getParent()!=null 
					&& acct.getParent().getId().equals(parentId)
					&& !item.isIsProduct()){

				//递归调用					
				item=collectCostSplitByAcct(collSplit,acct,collAcct);
				/*if(!acct.isIsLeaf()){
					collProd=collectCostSplitByAcct(collSplit,acct,collAcct);
					if(collProd.size()>0){
						item=collProd.get(0);
						
						//返回的产品
						for(int i=1; i<collProd.size(); i++){
							collProdAll.add(collProd.get(i));
						}
					}
				}*/
				
				if(item!=null && item.getAmount()!=null){
					amount=amount.add(item.getAmount());
				}
			}
		}
								
		costSplitEntry.setAmount(amount);
		
		/*//附加产品拆分
		for(int i=1; i<collProdAll.size(); i++){
			entry=collProdAll.get(i);
			
			isFound=false;
			for(Iterator iter = collSplit.iterator(); iter.hasNext();){
				item=(CostSplitEntryInfo)iter.next();					
				
				if(item.isIsProduct() 
						&& item.getCostAccount().getId().equals(entry.getCostAccount().getId())){
					isFound=true;
					break;
				}
			}
			
			if(!isFound){
				//coll.add(entry);
			}
		}*/
		
		
		
		
		
		
		
		//----------------------------------------------------------------------------
		//产品拆分处理
		//collProd=new CostSplitEntryCollection();
		ProductTypeInfo product=null;	
		
		BigDecimal amountTotal=FDCHelper.ZERO;	
		amountTotal=amountTotal.setScale(10);
		

		//检查下级科目的拆分分录中,是否已经存在产品拆分
		isFound=false;
		for(int i=0; i<collSplit.size(); i++){
			item=collSplit.get(i);
			if(item.isIsProduct() 
					&& item.getCostAccount().getParent()!=null 
					&& item.getCostAccount().getParent().getId().equals(parentId)){
				isFound=true;
				break;
			}
		}	
		

		if(isFound){	

			amountTotal=FDCHelper.ZERO;	
			
			for(Iterator iter = collSplit.iterator(); iter.hasNext();){
				item=(CostSplitEntryInfo)iter.next();	
				
				
				if(item.isIsProduct() 
						&& item.getCostAccount().getParent()!=null && item.getCostAccount().getParent().getId().equals(parentId)){
					product=item.getProduct();

					amount=FDCHelper.ZERO;	
					if(item.getProdAmount()!=null){
						amount=item.getProdAmount();
						amountTotal=amountTotal.add(amount);
					}
					
					//是否已存在此产品的拆分
					isFound=false;
					entry=null;
					for(Iterator iter2 = collSplit.iterator(); iter2.hasNext();){
						item=(CostSplitEntryInfo)iter2.next();					

						/*if(item.getCostAccount().getId().equals(parentId) 
								&& item.getProduct()!=null && item.getProduct().getName()!=null && item.getProduct().getName()!="否" 
								&& item.getProduct().getId().equals(product.getId())){*/
						if(item.getCostAccount().getId().equals(parentId) 
								&& item.getProduct()!=null && item.getProduct().getId()!=null
								&& item.getProduct().getId().equals(product.getId())){
							entry=item;
							isFound=true;
							break;
						}
					}
					
					if(isFound){
						if(entry.getProdAmount()!=null){
							amount=amount.add(entry.getProdAmount());						
							entry.setProdAmount(amount);	
							
						}
						
					}else{
						/*entry=new CostSplitEntryInfo();
						collProd.add(entry);
						
						entry.setCostAccount(parent);	
						entry.setAmount(FDCHelper.ZERO);
						
						entry.setIsProduct(true);
						entry.setProduct(product);	*/
										
					}
					//entry.setProdAmount(amount);	
				}
				
				
			}		
			
			/*
			for(Iterator iter = collProd.iterator(); iter.hasNext();){
				item=(CostSplitEntryInfo)iter.next();					
				coll.add(item);
			}*/
			
			/*
			for(Iterator iter = collSplit.iterator(); iter.hasNext();){
				item=(CostSplitEntryInfo)iter.next();	
								
				if(!item.isIsProduct() 
						&& item.getCostAccount().getId().equals(parentId)){
					item.setProdAmount(amountTotal);
				}
			}*/
			costSplitEntry.setProdAmount(amountTotal);
	
		}
		
		/*
		if(collProd.size()>0){
			for(Iterator iter = collProd.iterator(); iter.hasNext();){
				item=(CostSplitEntryInfo)iter.next();					
				costSplitEntrys.add(item);
			}			
		}
		
		return costSplitEntrys;*/
		return costSplitEntry;
	}
    
	

	
	

	private void collectCostSplitByProd(CostSplitEntryCollection coll, CurProjectInfo curProject, CostAccountCollection collAcct) throws BOSException, EASBizException {

		CurProjectInfo parent=curProject;		
		
		CostSplitEntryInfo item=null;
		CostSplitEntryInfo entry=null;
		
		CostAccountInfo acct=null;
		CurProjectInfo proj=null;
				
		BigDecimal amount=FDCHelper.ZERO;//null;
		
		boolean isFound=false;
		boolean isTrue=true;
		
		boolean isProd=false;
		ProductTypeInfo product=null;
		//BigDecimal amtProd=FDCHelper.ZERO;
										
				
		//检查拆分分录中是否已经存在子工程项目的数据						
		isFound=false;
		for(int j=0; j<coll.size(); j++){
			item=coll.get(j);
			if(item.getCostAccount().getCurProject().getParent()!=null
					&& item.getCostAccount().getCurProject().getParent().getId().equals(parent.getId())){
				isFound=true;
				break;
			}
		}	    			
		if(!isFound){
			return;
		}
		
		
		
		//检索子工程的数据
		CostSplitEntryCollection collProd=new CostSplitEntryCollection();
		
    	for(int i=0; i<coll.size(); i++){
    		item=coll.get(i);
    		proj=item.getCostAccount().getCurProject().getParent();
    		
    		if(item.isIsProduct() 
    				&& proj!=null && proj.getId().equals(parent.getId())){
    			collProd.add(item);
    		}
    	}
    	

		String acctNo=null;		
		
		//遍历子工程的科目,汇总到coll中:产品则累加金额，否则添加新记录(存在科目)
		for(int i=0; i<collProd.size(); i++){
    		item=collProd.get(i);
    		
    		acct=item.getCostAccount();
    		acctNo=acct.getLongNumber();
    		//proj=acct.getCurProject().getParent();
    		//isProd=item.isIsProduct();
    		
    		product=item.getProduct();
    		amount=item.getProdAmount();
		
	    			
			//当前工程的科目
			isFound=false;
			entry=null;
			
	    	for(int j=0; j<coll.size(); j++){
	    		item=coll.get(j);
	    		
	    		acct=item.getCostAccount();
	    		proj=acct.getCurProject();

	    		if(item.isIsProduct() 
	    				&& proj.getId().equals(parent.getId())
	    				&& acct.getLongNumber().equals(acctNo)
	    				&& item.getProduct().equals(product.getId())){
	    			
	    			amount=amount.add(item.getProdAmount());
	    			entry=item;
	    			
	    			isFound=true;    	    			
	    			break;
	    		}
	    	}

	    	
	    	if(!isFound){					
				acct=getCostAccountByProj(collAcct,parent.getId(),acctNo);
				if(acct!=null){
	    			entry=new CostSplitEntryInfo();
					coll.add(entry);
					entry.setCostAccount(acct);
					entry.setAmount(FDCHelper.ZERO);

					entry.setIsProduct(true);
					entry.setProduct(product);
					
					entry.setProdAmount(amount);
				}
	    	}else{
				entry.setProdAmount(amount);	
	    	}
		}
		
		
		
		//汇总当前工程科目的产品拆分
		BOSUuid acctId=null;
		
		for(int i=0; i<coll.size(); i++){
    		entry=coll.get(i);
    		
    		acct=entry.getCostAccount();
    		proj=acct.getCurProject();
    		
    		if(!entry.isIsProduct() && !acct.isIsLeaf() && proj.getId().equals(parent.getId())){
        		acctId=acct.getId();        		
        		amount=FDCHelper.ZERO;
    			
    	    	for(int j=0; j<coll.size(); j++){
    	    		item=coll.get(j);    	    		
    	    		acct=item.getCostAccount();

    	    		if(item.isIsProduct() 
    	    				&& item.getCostAccount().getId().equals(acctId)){    	    			
    	    			amount=amount.add(item.getProdAmount());
    	    		}
    	    	}    			
    	    	
    	    	//find bugs工具发现：这里entry一定不是空的
    	    	/*if(entry!=null){
    	    		entry.setProdAmount(amount);
    	    	}*/
    	    	/**
    	    	 * 如果此处的entry为空的话，以上的entry操作一定出包错
    	    	 */
    	    	//updage by renliang
    	    	entry.setProdAmount(amount);
    		}    		
		}

	}
	
	
	

	

	
	private void collectCostSplitByProj(Context ctx, CostSplitEntryCollection collSplit, CurProjectInfo curProject) throws BOSException, EASBizException {
	

		CostSplitEntryCollection coll=(CostSplitEntryCollection) collSplit.clone();
		
		CostSplitEntryInfo item=null;
		CostSplitEntryInfo entry=null;
		
		//上级工程项目，按科目编码汇总
		CostAccountInfo acct=null;
		CurProjectInfo proj=null;
		CurProjectInfo parent=null;
		
		BigDecimal amount=FDCHelper.ZERO;//null;
		
		boolean isFound=false;
		boolean isTrue=true;
		
		CostSplitEntryInfo splitEntry=null;
		
		ProductTypeInfo product=null;
		boolean isProd=false;
		BigDecimal amtProd=FDCHelper.ZERO;
		
		CostAccountCollection collAcct = null;
		
		EntityViewInfo view = null;
		FilterInfo filter = null;
		
				
		
		parent=curProject;
								
										
												
		//上级工程项目的所有科目
		view = new EntityViewInfo();
		filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("curProject.id", parent.getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
    	view.setFilter(filter);		    	    	
    	
    	view.getSelector().add("id");
    	view.getSelector().add("name");
    	view.getSelector().add("longNumber");
    	view.getSelector().add("level");
    	view.getSelector().add("isLeaf");
    	view.getSelector().add("parent");
    	view.getSelector().add("parent.id");
    	
    	view.getSelector().add("curProject.id");
    	view.getSelector().add("curProject.name");
    	view.getSelector().add("curProject.level");
    	view.getSelector().add("curProject.isLeaf");
    	view.getSelector().add("curProject.parent");
    	view.getSelector().add("curProject.parent.id");

      	
    	view.getSelector().add("fullOrgUnit.id");
    	view.getSelector().add("fullOrgUnit.name");
    	view.getSelector().add("fullOrgUnit.longNumber");
    	view.getSelector().add("fullOrgUnit.level");
    	view.getSelector().add("fullOrgUnit.isLeaf");
    	view.getSelector().add("fullOrgUnit.parent");
    	view.getSelector().add("fullOrgUnit.parent.id");

    	
    	
    	view.getSorter().add(new SorterItemInfo("curProject.id"));
    	view.getSorter().add(new SorterItemInfo("longNumber"));
    	
    	collAcct = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
						
		
    	//已有拆分（所有下级）
    	for(int i=0; i<coll.size(); i++){
    		splitEntry=coll.get(i);
    		acct=splitEntry.getCostAccount();
    		proj=acct.getCurProject().getParent();
    		
    		isProd=splitEntry.isIsProduct();
    		product=splitEntry.getProduct();
    		
    		//下级工程的科目
    		if(proj!=null && proj.getId().equals(parent.getId())){
    			
    			//生成当前工程的科目
    			acct=getCostAccountByProj(collAcct,parent.getId(),acct.getLongNumber());				    							    			
    			if(acct!=null){					
					//金额
    				if(splitEntry.getAmount()!= null)
    					amount=splitEntry.getAmount();
    				else
    					amount=FDCHelper.ZERO;
    				if(splitEntry.getProdAmount()!= null)
    					amtProd=splitEntry.getProdAmount();
    				else
    					amtProd=FDCHelper.ZERO;
					 
					//检查当前工程是否存在此科目
					entry=null;
					isFound=false;
			    	for(int j=0; j<collSplit.size(); j++){
			    		item=collSplit.get(j);
			    		
			    		if(item.getCostAccount().getId().equals(acct.getId())){
			    			
			    			if((isProd && item.isIsProduct() && item.getProduct().getId().equals(product.getId()))
			    					|| (!isProd && !item.isIsProduct())){
			    				if(item.getAmount()!=null)
			    					amount=amount.add(item.getAmount());
			    				if(item.getProdAmount()!=null)
			    					amtProd=amtProd.add(item.getProdAmount());
				    			entry=item;
				    			isFound=true;
				    			
			    			}
			    			
			    		}
			    	}
			    	
			    	//没有则新增此科目
			    	if(isFound){
						//entry.setProdAmount(billEntry.getAmount());
			    	}else{

		    			entry=new CostSplitEntryInfo();
						collSplit.add(entry);	
						entry.setCostAccount(acct);
						entry.setProduct(splitEntry.getProduct());	
						entry.setIsProduct(isProd);
			    	}
			    	if(entry!=null){
			    		entry.setAmount(amount);	
			    		entry.setProdAmount(amtProd);
			    	}
    			}
    		}
    	}
				    		
		
    	for(int i=0; i<coll.size(); i++){
    		//collSplit.add(coll.get(i));
    	}
						
		
	}
	
	
	


	
	private void collectCostSplitByOrg(Context ctx, CostSplitEntryCollection collSplit, 
			FullOrgUnitInfo curOrgUnit, CurProjectInfo curProject) throws BOSException, EASBizException {
	
		CostSplitEntryCollection coll=new CostSplitEntryCollection();
		coll=(CostSplitEntryCollection) collSplit.clone();
		
		CostSplitEntryInfo item=null;
		CostSplitEntryInfo entry=null;
		
		//上级工程项目，按科目编码汇总
		CostAccountInfo acct=null;
		CurProjectInfo proj=null;
		CurProjectInfo parent=curProject;
		
		BigDecimal amount=FDCHelper.ZERO;
		
		boolean isFound=false;
		boolean isTrue=true;
		
		CostSplitEntryInfo splitEntry=null;
		
		ProductTypeInfo product=null;
		boolean isProd=false;
		BigDecimal amtProd=FDCHelper.ZERO;
		
		CostAccountCollection collAcct = null;
		
		EntityViewInfo view = null;
		FilterInfo filter = null;
		

		FullOrgUnitInfo orgParent=curOrgUnit;
		FullOrgUnitInfo orgUnit=null;
		
		boolean isChild=false;
		
				
												
		//上级组织的所有科目
		view = new EntityViewInfo();
		filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", orgParent.getId().toString()));
    	//filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
    	view.setFilter(filter);		    	    	
    	
    	view.getSelector().add("id");
    	view.getSelector().add("name");
    	view.getSelector().add("longNumber");
    	view.getSelector().add("level");
    	view.getSelector().add("isLeaf");
    	view.getSelector().add("parent");
    	view.getSelector().add("parent.id");
    	
    	view.getSelector().add("fullOrgUnit.id");
    	view.getSelector().add("fullOrgUnit.name");
    	view.getSelector().add("fullOrgUnit.level");
    	view.getSelector().add("fullOrgUnit.isLeaf");
    	view.getSelector().add("fullOrgUnit.parent");
    	view.getSelector().add("fullOrgUnit.parent.id");

    	view.getSorter().add(new SorterItemInfo("longNumber"));
    	
    	collAcct = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
						

    	for(int i=0; i<coll.size(); i++){
    		splitEntry=coll.get(i);
    		acct=splitEntry.getCostAccount();				    		
    		    		
    		isProd=splitEntry.isIsProduct();
    		product=splitEntry.getProduct();
    		
    		
    		//当前组织的下级工程的科目		
    		isChild=false;
    		if(parent!=null){
	    		if(acct.getCurProject()!=null && acct.getCurProject().getId().equals(parent.getId())){
	    			isChild=true;
	    		}	
    		}else{	    		
	    		if(acct.getFullOrgUnit()!=null){
	    			orgUnit=acct.getFullOrgUnit().getParent();
		    		if(orgUnit!=null 
		    				&& orgUnit.getId().equals(orgParent.getId())){
		    			isChild=true;
		    		}				  
	    		}

    		}
    		
    		if(isChild){
    			//生成当前组织的科目（根据编码生成）
    			acct=getCostAccountByOrg(collAcct,orgParent.getId(),acct.getLongNumber());
    							    			
    			if(acct!=null){					
					//累加金额
    				if(splitEntry.getAmount()!= null)
    					amount=splitEntry.getAmount();
    				else
    					amount=FDCHelper.ZERO;
    				if(splitEntry.getProdAmount()!= null)
    					amtProd=splitEntry.getProdAmount();
    				else
    					amtProd=FDCHelper.ZERO;
					 
					//检查当前组织的科目是否存在
					entry=null;
					isFound=false;
			    	for(int j=0; j<collSplit.size(); j++){
			    		item=collSplit.get(j);
			    		
			    		if(item.getCostAccount().getId().equals(acct.getId())){			    			
			    			if((isProd && item.isIsProduct() && item.getProduct().getId().equals(product.getId()))
			    					|| (!isProd && !item.isIsProduct())){
			    				if(item.getAmount()!= null)
			    					amount=amount.add(item.getAmount());
			    				if(item.getProdAmount()!= null)
			    					amtProd=amtProd.add(item.getProdAmount());
				    			entry=item;
				    			isFound=true;
//				    			break;
			    			}			    			
			    		}
			    	}
			    	
			    	
			    	if(isFound){
						//entry.setProdAmount(billEntry.getAmount());
			    	}else{

		    			entry=new CostSplitEntryInfo();
						collSplit.add(entry);	
						entry.setCostAccount(acct);
						entry.setProduct(splitEntry.getProduct());	
						entry.setIsProduct(isProd);
			    	}
			    	if(entry!=null){
			    		entry.setAmount(amount);	
			    		entry.setProdAmount(amtProd);
			    	}
    				
    			}
    		
    			
    		}
    	}
				    		
		
    	for(int i=0; i<coll.size(); i++){
    		//collSplit.add(coll.get(i));
    	}
		
	}
	
	
	

	
	private CostAccountInfo getCostAccount(CostAccountCollection coll, BOSUuid uuid){
		CostAccountInfo item=null;		
		for(int i=0; i<coll.size(); i++){
			item=coll.get(i);
			if(item.getId().equals(uuid)){
				return item;
			}
		}
				
		return null;
	}
	

	private CostAccountInfo getCostAccountByProj(CostAccountCollection coll, BOSUuid projectId, String longNumber){
		CostAccountInfo item=null;		
		for(int i=0; i<coll.size(); i++){
			item=coll.get(i);
			if(item.getLongNumber().equals(longNumber)
					&& item.getCurProject().getId().equals(projectId)){
				return item;
			}
		}
				
		return null;
	}
	

	private CostAccountInfo getCostAccountByOrg(CostAccountCollection coll, BOSUuid orgUnitId, String longNumber){
		CostAccountInfo item=null;		
		for(int i=0; i<coll.size(); i++){
			item=coll.get(i);
			if(item.getLongNumber().equals(longNumber)
					&& item.getFullOrgUnit().getId().equals(orgUnitId)){
				return item;
			}
		}
				
		return null;
	}
	





    /**
	 * 描述：汇总分摊金额（汇总生成非明细工程项目中附加科目的成本，其值为下级工程项目相同科目的成本之和）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-12-29 <p>
	 */
    public void totAmountAddlAcct(IObjectCollection allEntrys,int curIndex){
    	if(curIndex>=allEntrys.size()){
    		return;
    	}
		
		FDCSplitBillEntryInfo topEntry =(FDCSplitBillEntryInfo)allEntrys.getObject(curIndex);
    	if(topEntry==null||topEntry.getCostAccount()==null||topEntry.getCostAccount().getCurProject()==null||topEntry.getCostAccount().getCurProject().isIsLeaf()){		
			//明细工程的科目		
			return;			
		}
		
		 	
				
		
		//非明细工程的各科目的成本从下级工程相同科目累加
		/*for(int i=curIndex+1; i<allEntrys.size(); i++){				
			FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()>topLevel){
				if(entry.getCostAccount().getCurProject().getId().equals(topEntry.getCostAccount().getCurProject().getId())){
					//当前工程项目的下级成本科目
					if(entry.getCostAccount().getLevel()==acctLevel+1){
						totAmountAddlAcct(allEntrys,i);										
					}
					
				}else if(isChildProjSameAcct(entry,topEntry)){
					//下级工程项目的相同成本科目
					totAmountAddlAcct(allEntrys,i);				
					
					amount=entry.getAmount();
					if(amount==null){
						amount=FDCHelper.ZERO;
					}
					amountTotal=amountTotal.add(amount);
					
				}
				
			}else if(entry.getLevel()<topLevel){
				//break;
			}
		}*/
		

		//当前工程项目的下级成本科目
    	int acctLevel=topEntry.getCostAccount().getLevel();
    	
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);

			if(entry.getCostAccount().getCurProject().getId().equals(topEntry.getCostAccount().getCurProject().getId())){
				if(entry.getCostAccount().getLevel()>acctLevel){
					if(entry.getCostAccount().getLevel()==acctLevel+1){
						totAmountAddlAcct(allEntrys,i);										
					}					
				}else{
					break;
				}
				
			}else{
				break;
			}
		}
		

		//下级工程项目的相同成本科目
    	int topLevel=topEntry.getLevel();
    	int projLevel=topEntry.getCostAccount().getCurProject().getLevel();
    	
		BigDecimal amount=null;
		BigDecimal amountTotal=FDCHelper.ZERO;
		
		for(int i=curIndex+1; i<allEntrys.size(); i++){				
			FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)allEntrys.getObject(i);
			
			if(entry.getLevel()>topLevel){
				if(entry.getLevel()==topLevel+1){
					if(isChildProjSameAcct(entry,topEntry)){
						totAmountAddlAcct(allEntrys,i);				
						
						amount=entry.getAmount();
						if(amount==null){
							amount=FDCHelper.ZERO;
						}
						amountTotal=amountTotal.add(amount);					
					}					
				}
				
			}else if(entry.getLevel()<topLevel){
				//break;
			}
		}
		
		//topEntry.isIsAddlAccount() 条件增加 by sxhong 这段代码应该是有问题的
		if(topLevel!=0&&topEntry.isIsAddlAccount()){
			topEntry.setAmount(amountTotal);
		}
    		
	}
    
    public static SelectorItemCollection setSelectorsEntry(SelectorItemCollection sic, boolean isEntry) {
		String prefix="";
		if(!isEntry){
			prefix="entrys.";
		}
		
		if(sic==null){
			sic=new SelectorItemCollection();
		}
        sic.add(new SelectorItemInfo(prefix + "*"));
        sic.add(new SelectorItemInfo(prefix + "apportionType.id"));
        sic.add(new SelectorItemInfo(prefix + "apportionType.name"));
        sic.add(new SelectorItemInfo(prefix + "product.*"));

        sic.add(new SelectorItemInfo(prefix + "costAccount"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.id"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.name"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.longNumber"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.displayName"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.isLeaf"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.level"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.parent.id"));
        
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject.id"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject.name"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject.displayName"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject.longNumber"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject.isLeaf"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject.level"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject.parent"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject.parent.id"));
        sic.add(new SelectorItemInfo(prefix + "costAccount.curProject.sortNo"));
        return sic;
	}
    
	/**
	 * 使用新的接口程序以前的汇总接口改成collectCostSplit_old，530暂时不改变程序逻辑 by sxhong 2008-10-29 17:01:13
	 * 待程序稳定后直接用新接口
	 * @param costSplitBillType
	 * @param coreBillInfo
	 * @param splitBillId
	 * @param CostBillEntrys
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public void collectCostSplit(CostSplitBillTypeEnum costSplitBillType, CoreBillBaseInfo coreBillInfo, BOSUuid splitBillId, AbstractObjectCollection CostBillEntrys) throws BOSException, EASBizException {
		if(ctx==null) return;
		if(false){
			collectCostSplit_old(costSplitBillType, coreBillInfo, splitBillId, CostBillEntrys);
			return;
		}
		if(coreBillInfo==null||coreBillInfo.getId()==null){
			throw new NullPointerException("the method collectCostsplit params coreBillInfo and it's id CU can't be null!");
		}
		//删除原来的汇总
		deleteCostSplit(ctx,costSplitBillType,coreBillInfo.getId());
		Set set=new HashSet();
		set.add(splitBillId.toString());
		collectCostSplitNew(costSplitBillType, set);
	}
	/**
	 * 使用临时表实现的新接口，传参少，支持批量处理 by sxhong 2008-10-29 17:01:18
	 * @param costSplitBillType
	 * @param splitIds
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public void collectCostSplitNew(CostSplitBillTypeEnum costSplitBillType, Set splitIds) throws BOSException, EASBizException {
		FDCCostSplitAppHelper.collectCostSplit(ctx, costSplitBillType, splitIds);
		
		//删除原来的汇总
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select distinct fobjectid from T_AIM_CostSplitEntry entry inner join T_AIM_CostSplit head on head.fid=entry.fparentid where ");
		builder.appendParam("head.fsplitBillId", splitIds.toArray());
		builder.appendSql(" and exists (select fid from T_FDC_CurProject where fid=entry.fobjectId and fisleaf=1)");
		IRowSet rowSet=builder.executeQuery();
		Set prjSet=new HashSet();
		try{
			while(rowSet.next()){
				prjSet.add(rowSet.getString("fobjectid"));
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		ProjectCostChangeLogFactory.getLocalInstance(ctx).insertLog(prjSet);
	}
	
}
