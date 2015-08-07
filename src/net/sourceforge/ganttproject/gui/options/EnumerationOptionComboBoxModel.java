/*
 * Created on 02.04.2005
 */
package net.sourceforge.ganttproject.gui.options;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import com.kingdee.eas.fdc.schedule.framework.ext.GanttTreeTableModelExt;

import net.sourceforge.ganttproject.gui.options.model.EnumerationOption;
import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.task.TaskProperties;

/**
 * @author bard
 */
class EnumerationOptionComboBoxModel extends AbstractListModel implements
        ComboBoxModel {
    private final List myValues;

    private Item mySelectedItem;

    private final EnumerationOption myOption;

    public EnumerationOptionComboBoxModel(EnumerationOption option) {
        myOption = option;
        String currentValue = option.getValue();
        Item currentItem = null;
        String[] ids = option.getAvailableValues();
        String[] i18nedValues = geti18nedValues(option);

        myValues = new ArrayList(ids.length);
        for (int i = 0; i < ids.length; i++) {
            Item nextItem = new Item(ids[i], i18nedValues[i]);
            myValues.add(nextItem);
            if (ids[i].equals(currentValue)) {
                currentItem = nextItem;
            }
        }
        if (currentItem != null) {
            setSelectedItem(currentItem);
        }
    }

    public void setSelectedItem(Object item) {
        mySelectedItem = (Item) item;
        myOption.setValue(mySelectedItem.myID);
    }

    public Object getSelectedItem() {
        return mySelectedItem;
    }

    public int getSize() {
        return myValues.size();
    }

    public Object getElementAt(int index) {
    	//修正一个bug by sxhong 
    	if(index<0||index>=myValues.size()){
    		return null;
    	}
        return myValues.get(index);
    }

    String[] geti18nedValues(EnumerationOption option) {
    	//将
        String[] ids = option.getAvailableValues();
        String[] result = new String[ids.length];
        for (int i = 0; i < ids.length; i++) {
            result[i] = GanttLanguage.getInstance().getText(
                    "optionValue." + ids[i] + ".label");
            
            if(TaskProperties.ID_TASK_WBS.equals(ids[i])){
            	//WBS
            	result[i]=GanttTreeTableModelExt.strColWBSNumber;
            }
            if(TaskProperties.ID_TASK_LENGTH.equals(ids[i])){
            	//持续改成有效工期
            	result[i]=GanttTreeTableModelExt.strColEffectTimes;
            }
            if (result[i].startsWith(GanttLanguage.MISSING_RESOURCE)) {
//                System.err.println(result[i]);
                result[i] = ids[i];
            }
        }
        return result;
    }

    private static class Item {
        private final String myID;

        private final String myDisplayValue;

        public Item(String id, String displayValue) {
            myID = id;
            myDisplayValue = displayValue;
        }

        public String toString() {
            return myDisplayValue;
        }
    }
}
