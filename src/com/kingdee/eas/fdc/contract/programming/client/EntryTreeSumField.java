package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeListener;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.util.StringUtility;
import com.kingdee.eas.util.client.EASResource;

public class EntryTreeSumField {
	private Map tableToSumField=new HashMap();
    private Map tableToSumIndex=new HashMap();
    /**
     * 设置表格对应的统计列
     * @param table
     */
    protected void setProTableToSumField(KDTable table,String[] columnFields)
    {
        if (tableToSumField.get(table)!=null) return;
        tableToSumField.put(table,columnFields);
        tableKeyToIndex(table,columnFields,this.tableToSumIndex);
        addTablePropertyList(table);
    }
    
    private void ConvertKeyToIndex(KDTable table)
    {
    	if (tableToSumField.containsKey(table))
    	{
    		tableToSumIndex.remove(table);
    		tableKeyToIndex(table,(String[])tableToSumField.get(table),this.tableToSumIndex);
    	}
    }
    
    private void tableKeyToIndex(KDTable table,String[] columnFields,Map indexMap)
    {
            if (columnFields!=null)
            {
               String[] indexField=new String[columnFields.length];
               for (int i=0;i<columnFields.length;i++)
               {
            	   if (columnFields[i]==null)  continue;
                   String[] fieldKey=columnFields[i].split("@");
                   int index=table.getColumnIndex(fieldKey[0]);
                   if (fieldKey.length>1)
                   {
                	   indexField[i]=index+"@"+fieldKey[1]; 
                   }else
                   {
                	   indexField[i]=String.valueOf(index);
                   }
               }
               indexMap.put(table,indexField);
            }
    }
    
    /**
     * 在当前表格加入单元格编辑统计列监听器
     */
    private void addTablePropertyList(final KDTable table){
        KDTPropertyChangeListener propertyChangeListener=new KDTPropertyChangeListener(){
            public void propertyChange(KDTPropertyChangeEvent evt)
            {
               if ((evt.getType() == KDTStyleConstants.BODY_ROW) && (evt.getPropertyName().equals(KDTStyleConstants.CELL_VALUE))){
                   int columnIndex=evt.getColIndex();
                   String colKey=table.getColumn(columnIndex).getKey();
                   if (colKey!=null)
                   {
                       Iterator lt=tableToSumField.entrySet().iterator();
                       while (lt.hasNext())
                       {
                           Map.Entry entry=(Map.Entry)lt.next();
                           KDTable tableSum=(KDTable)entry.getKey();
                           String[] fields=(String[])entry.getValue();
                           if (fields==null) continue;
                           for (int i=0;i<fields.length;i++){

                               if (fields[i]!=null)
                               {
                            	   if (fields[i].equalsIgnoreCase(colKey))
                            	   {
                            		   appendProFootRow(tableSum,colKey);
                            	   }
                               }
                           }
                       }
                   }
               }
            }
       };
       table.addKDTPropertyChangeListener(propertyChangeListener);
    }
    
    /**
     * 根据列名称计算某列的值
     */
    protected void appendProFootRow(KDTable table,String columnKey)
    {
        if(tableToSumIndex.size()==0) return;
        ConvertKeyToIndex(table);
        Map columnMap=new HashMap();
        Iterator lt=tableToSumIndex.entrySet().iterator();
         while (lt.hasNext()){
             Map.Entry entry=(Map.Entry)lt.next();
             KDTable tableSum=(KDTable)entry.getKey();
             String[] field=(String[])entry.getValue();
             if (tableSum==null||field==null)
                 continue;
             if (table!=null&&table!=tableSum)
                 continue;

             //循环遍历每个表的各个字段进行匹配，查找统计字段
             columnMap.clear();
             for (int j=0;j<field.length;j++)
             {            	  
                  if (field[j]==null)  continue;
                  int keyIndex=Integer.parseInt(field[j]);
                  if (tableSum.getColumn(keyIndex)==null)
                  {
                      continue;
                  }
                  if (columnKey!=null)
                  {
                      if (!tableSum.getColumnKey(keyIndex).equals(columnKey))
                      {
                          continue;
                      }
                  }
  
                  //求此列的值
                  BigDecimal dbSum=new BigDecimal("0");
                  for (int r=0;r<tableSum.getRowCount();r++)
                  {
                	  Object o = tableSum.getCell(r, "level").getValue();
                	  int level = 0;
                	  if(o != null){
                	  	level = new Integer(o.toString()).intValue();
                	  }
                	  if(level == 1){
	                	  if (tableSum.getRow(r).getStyleAttributes().isHided())
	                	  {
	                		  continue;
	                	  }
	                      ICell cell=tableSum.getRow(r).getCell(keyIndex);
	
	                      String cellValue=tableSum.getCellDisplayText(cell);
	                      if (cellValue!=null)
	                          cellValue=cellValue.toString().replaceAll(",","");
	
	                      if (!StringUtility.isNumber(cellValue)){
	                          Object cellObj=cell.getValue();
	                          if (cellObj!=null) cellValue=cellObj.toString();
	                          if (!StringUtility.isNumber(cellValue))
	                              continue;
	                      }
	                      BigDecimal bigdem=new BigDecimal(String.valueOf(cellValue).trim());
	                      dbSum=dbSum.add(bigdem);
                	  }
                  }

                  String strSum=dbSum.toString();
                  columnMap.put(field[j],strSum);
              }

             if (columnMap.size()>0)
             {
                 IRow footRow=null;
                 KDTFootManager footRowManager= tableSum.getFootManager();
                 if (footRowManager==null)
                 {
                     footRowManager = new KDTFootManager(tableSum);
                     footRowManager.addFootView();
                     tableSum.setFootManager(footRowManager);
                 }
                 footRow=footRowManager.getFootRow(0);
                 if (footRow==null)
                 {
                     String colFormat="%{0.##########}f";
                     int colIndex=-1;
                     footRow= footRowManager.addFootRow(0);
                     String total=EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Total");
                     tableSum.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
                     tableSum.getIndexColumn().setWidth(30);
                     footRowManager.addIndexText(0, total);
                     footRow.getStyleAttributes().setBackground(new Color(0xf6, 0xf6, 0xbf));
                     Iterator valueIterator=columnMap.entrySet().iterator();
                     while (valueIterator.hasNext())
                     {
                         Map.Entry colEntry=(Map.Entry)valueIterator.next();
                         if (colEntry.getKey()==null)
                             continue;
                         String colArray[]=String.valueOf(colEntry.getKey()).split("@");
                         colIndex=Integer.parseInt(colArray[0]);
                         if (colArray.length>1)
                             colFormat=colArray[1];
                         footRow.getCell(colIndex).getStyleAttributes().setNumberFormat(colFormat);
                         footRow.getCell(colIndex).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
                         footRow.getCell(colIndex).getStyleAttributes().setFontColor(Color.BLACK);
                     }
                 }

                 //添充数据到单元格
                 Iterator valueIterator=columnMap.entrySet().iterator();
                 while (valueIterator.hasNext())
                 {
                     Map.Entry colEntry=(Map.Entry)valueIterator.next();
                     int    colIndex=Integer.parseInt(String.valueOf(colEntry.getKey()).split("@")[0]);
                     String colValue=(String)colEntry.getValue();
                     footRow.getCell(colIndex).setValue(colValue);
                 }
             }
         }
    }
    
  //金额类字段在修改时自动向上汇总
	protected void caclTotalAmount(KDTable kdtEntries) {
		int maxLevel = 0;
		int[] levelArray = new int[kdtEntries.getRowCount()];
		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			IRow row = kdtEntries.getRow(i);
			int level = Integer.parseInt(row.getCell("level").getValue().toString());
			levelArray[i] = level;
		}

		for (int i = 0; i < kdtEntries.getRowCount(); i++) {
			maxLevel = Math.max(levelArray[i], maxLevel);
		}
    	
		if(maxLevel == 1){
			return;
		}
		
		while(maxLevel > 0){
			List parentList = new ArrayList();
			Map columnMap=new HashMap();
			String[] sumColNames = getProSumFields();
			for (int j = 0; j < sumColNames.length; j++) {
				parentList.clear();
				//先找去最深层次的数据
				for (int i = 0; i < kdtEntries.getRowCount(); i++) {
					BigDecimal dbSum = FDCHelper.ZERO;
					columnMap.clear();
					int level = new Integer(kdtEntries.getCell(i, "level").getValue().toString()).intValue();
					//如果最后一行，如果最后一行是根节点。则不进行汇总计算
					if(i == kdtEntries.getRowCount() - 1){
						if(level == 1)
							continue;
					}
					boolean isHasChild = false;
					dbSum = FDCHelper.ZERO;
					String parentNumber = "";
					if (maxLevel == level) {
						if (level == 1) {
							parentNumber = kdtEntries.getCell(i, "longNumber").getValue().toString();
						} else {
							parentNumber = kdtEntries.getCell(i, "headNumber").getValue().toString();
						}
						// 累加当前级下的金额。以上级编码为依据
						for (int k = i; k < kdtEntries.getRowCount(); k++) {
							int level_k = new Integer(kdtEntries.getCell(k, "level").getValue().toString()).intValue();
							String headNumber = "";
							if(level_k == 1){
								continue;
							}else{
								headNumber = kdtEntries.getCell(k, "headNumber").getValue().toString();
							}
							//累加金额
							if (headNumber.equals(parentNumber)) {
								ICell cell = kdtEntries.getRow(k).getCell(sumColNames[j]);
								String cellValue = kdtEntries.getCellDisplayText(cell);
								if (cellValue != null)
									cellValue = cellValue.toString().replaceAll(",", "");

								if (!StringUtility.isNumber(cellValue)) {
									Object cellObj = cell.getValue();
									if (cellObj != null)
										cellValue = cellObj.toString();
									if (!StringUtility.isNumber(cellValue))
										continue;
								}
								BigDecimal bigdem = new BigDecimal(String.valueOf(cellValue).trim());
								dbSum = dbSum.add(bigdem);
								isHasChild = true;
							}
						}
						columnMap.put(parentNumber, dbSum);
					}
					if(!isHasChild){
						continue;
					}
					//设置金额
					if(parentList.contains(parentNumber)){
						continue;
					}
					if(columnMap.size() > 0){
						for(int k = 0 ; k < kdtEntries.getRowCount() ; k++){
							String lnumber = kdtEntries.getCell(k, "longNumber").getValue().toString();
							if(columnMap.containsKey(lnumber)){
								kdtEntries.getCell(k, sumColNames[j]).setValue(columnMap.get(lnumber));
								parentList.add(parentNumber);
								break;
							}
						}
						
					}
					
				}
			}
			
			maxLevel--;
		}
	}
	
	  private String[] getProSumFields(){
	    	return new String[] {"signUpAmount", "changeAmount", "settleAmount", "balance","controlBalance" , "buildPerSquare" ,"soldPerSquare"};
	    }
}
