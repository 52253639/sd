package net.sourceforge.ganttproject;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import javax.swing.tree.DefaultMutableTreeNode;

import com.kingdee.eas.fdc.schedule.framework.ext.GanttTreeTableModelExt;

import net.sourceforge.ganttproject.font.Fonts;
import net.sourceforge.ganttproject.gui.UIConfiguration;
import net.sourceforge.ganttproject.task.BlankLineNode;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.util.TextLengthCalculatorImpl;

class TaskTreeImageGenerator {
    private GanttTree2 myTreeView;
    private UIConfiguration myUIConfiguration;
    private int myWidth;

    TaskTreeImageGenerator(GanttTree2 treeView, UIConfiguration uiConfiguration) {
        myTreeView = treeView;
        myUIConfiguration = uiConfiguration;
        
    }
    
    private GanttTree2 getTree() {
        return myTreeView;
    }
    
    List getPrintableNodes(GanttExportSettings settings) {
        List myItemsToConsider;
        if (settings.isOnlySelectedItem()) {
            myItemsToConsider = Arrays.asList(getTree().getSelectedNodes());
        }
        else {
            myItemsToConsider = getTree().getAllVisibleNodes();
        }
        //System.out.println("TaskToConsider.size = " + myItemsToConsider.size());

        for (int i = 0; i < myItemsToConsider.size(); i++) {
            if (((DefaultMutableTreeNode) myItemsToConsider.get(i)).isRoot()) {
                myItemsToConsider.remove(i);
                break;
            }
        }
        return myItemsToConsider;
        
    }
    
    //创建左边的工程任务列表
    Image createImage(List myItemsToConsider) {
        BufferedImage tmpImage = new BufferedImage(10, 10,
                BufferedImage.TYPE_INT_RGB);

        FontMetrics fmetric = tmpImage.getGraphics().getFontMetrics(
        myUIConfiguration.getChartMainFont().deriveFont(15f));
        int fourEmWidth = fmetric.stringWidth("mmmm");
        
        //计算宽度和高度: 宽度用字符个数计算
        int width = 0;
        int nameWidth = 150;
        int height = getTree().getTreeTable().getRowHeight()*3 + 6 ;
        for (Iterator tasks = myItemsToConsider.iterator(); tasks.hasNext();) {
            DefaultMutableTreeNode nextTreeNode = (DefaultMutableTreeNode) tasks
                    .next();

            if (nextTreeNode instanceof BlankLineNode) {
                height += getTree().getTreeTable().getRowHeight();
                continue;
            }

            Task next = (Task) nextTreeNode.getUserObject();
            if ("None".equals(next.toString())) {
                continue;
            }
            if (isVisible(next)) {
                height += getTree().getTreeTable().getRowHeight();
//                //计算文本的个数
                int nbchar = fmetric.stringWidth(next.getName())+next.getManager().getTaskHierarchy().getDepth(next)*fourEmWidth;
                if (nbchar > nameWidth) {
                	nameWidth = nbchar;                
                }
            }
        }

        width += 50;//标志号
        width += nameWidth; //名称
        width += 80;//工期
        width += 120;//开始时间
        width += 120;//完成时间
        
        myWidth = width;
        
        System.out.println("1.createImage:width=" + width+",height="+height);
        
        //创建一个width=134, height=606的图形
        BufferedImage image2 = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        // setSize(sizeTOC, getHeight());
        Graphics g2 = image2.getGraphics();
        ((Graphics2D) g2).setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        //0, 0, width, height 的矩形面积图白
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, width, height);
        
        System.out.println("2.fillRect:width=" + width+",height="+height);
        
        g2.setColor(Color.black);
        g2.setFont(Fonts.PRINT_CHART_FONT);
        int y = printTask(g2, myItemsToConsider,nameWidth);
        g2.drawRect(0, y, 530-1, height-y); 
        g2.drawLine(529, y , 529, height-y);
          
        
//        GanttImagePanel but = new GanttImagePanel("big.gif", 300, 47);
//        g2.setColor(new Color(102, 153, 153));
//        g2.fillRect(0,0, width, but.getHeight());
//        but.paintComponent(g2);        
        return image2;
    }
    
    private int getWidth() {
        return myWidth;
    }
    
    private void printTasks(Graphics g, List taskNodes,int nameWidth) {

        g.setColor(Color.black);

        // g.setFont(myUIConfiguration.getChartMainFont().deriveFont(12f));
        g.setFont(Fonts.PRINT_CHART_FONT);

        //new Font("SansSerif",   Font.PLAIN, 14);
        //g.setFont(new Font("SansSerif",   Font.PLAIN, 12));
        
        // JA Changed 42 to 66
        // This is related to the hardcoded headerheight
        // TODO: Fix hard-coded part
        // printTask(g,5,66,getTree().getAllChildTask(getTree().getRoot()));
        printTask(g,taskNodes,nameWidth);

    }

    //在此画表格
    private int printTask(Graphics g, List child,int nameWidth) {
        int rowCount=0;
        final int h = getTree().getTreeTable().getRowHeight();
        Stack nestingStack = new Stack();
        //int x = 5;
        final int fourEmWidth = new TextLengthCalculatorImpl(g).getTextLength("mmmm");
        int y = getTree().getTable().getTableHeader().getHeight()+ 3;
        
        //画一个表头:任务名称
        g.setColor(new Color((float) 0.933, (float) 0.933,(float) 0.933));
        g.fillRect(0, 0, getWidth() , y);
        g.setColor(Color.black);
        g.drawRect(0, 0, 50, y);
        g.drawString(GanttTreeTableModel.strColID, 10,y-20);  //序号
        
        g.drawRect(50, 0, nameWidth, y);
        g.drawString(GanttTreeTableModelExt.strColTaskName, 70,y-20);   //任务名称
        
        g.drawRect(nameWidth+50, 0, 80, y);
        g.drawString(GanttTreeTableModelExt.strColEffectTimes, nameWidth+70,y-20);  //工期
        
        g.drawRect(nameWidth+130, 0, 120, y);
        g.drawString(GanttTreeTableModel.strColBegDate,nameWidth+150,y-20);  //开始时间
        
        g.drawRect(nameWidth+250, 0,120-1, y);
        g.drawString(GanttTreeTableModel.strColEndDate, nameWidth+270,y-20);  //完成时间
        
        g.drawLine(1, y-1, getWidth(), y-1);
        
        for (Iterator tasks = child.iterator(); tasks.hasNext();) {
            DefaultMutableTreeNode nextTreeNode = (DefaultMutableTreeNode) tasks
                    .next();

            boolean blankline = nextTreeNode instanceof BlankLineNode;
            Task next = null;
            if (!blankline) {
                next = (Task) nextTreeNode.getUserObject();
                while (!nestingStack.isEmpty()) {
                	DefaultMutableTreeNode topStackNode = (DefaultMutableTreeNode) nestingStack.pop();
                	if (nextTreeNode.getParent()==topStackNode) {
                		nestingStack.push(topStackNode);
                		break;
                	}
                }
        		nestingStack.push(nextTreeNode);
            }
            if (blankline || isVisible(next)) {
                if (rowCount % 2 == 1) {
                    g.setColor(new Color((float) 0.933, (float) 0.933,
                            (float) 0.933));
                    g.fillRect(0, y, getWidth(), h);
                }
                g.setColor(Color.black);
                
                g.drawRect(0, y+h, nameWidth+369, h);      
                
                g.drawLine(50, y , 50, y + h);
                g.drawLine(nameWidth+50, y , nameWidth+50, y + h);
                g.drawLine(nameWidth+130, y , nameWidth+130, y + h);
                g.drawLine(nameWidth+250, y , nameWidth+250, y + h);
                g.drawLine(nameWidth+370, y , nameWidth+369, y + h);
//                g.drawRect(50, y+h, 150 , h);
//                g.drawRect(200, y+h, 80, h );
//                g.drawRect(280, y+h, 120,h);
//                g.drawRect(400, y+h, 130, h);
                
                if (!blankline) {
                    String name = (String)next.getCustomValues().getValue(GanttTreeTableModelExt.strColTaskName);//next.getName();
					int charH = (int) g.getFontMetrics().getLineMetrics(
                            name, g).getAscent();
                    int x = (nestingStack.size()-1)*fourEmWidth+5;
                    
                    g.drawString(""+(rowCount+1), 10		,y + charH+ (h - charH) / 2);  
                    
                    g.drawString(name,60+x	,y + charH + (h - charH) / 2);
                    
                    g.drawString(next.getDuration().getLength()+"工作日",nameWidth+60	,y + charH + (h - charH) / 2);
                    
                    String stateDate =  next.getStart().getYear()+"-"+next.getStart().getMonth()+"-"+next.getStart().getDay();                    
                    g.drawString(stateDate,nameWidth+140	,y + charH + (h - charH) / 2);
                    
                    String endDate =  next.getEnd().getYear()+"-"+next.getEnd().getMonth()+"-"+next.getEnd().getDay();
                    g.drawString(endDate,nameWidth+260	,y + charH + (h - charH) / 2);

                }

                g.setColor(new Color((float) 0.807, (float) 0.807,
                        (float) 0.807));

                g.drawLine(1, y + h-1, getWidth(), y + h-1);
                y += h;

                rowCount++;

                // if (nextTreeNode.getChildCount() != 0) {
                // y = printTask(g, x + (h / 2), y, getTree().getAllChildTask(
                // nextTreeNode));
                // }
            }else{
            	
            }
        }
        return y;
    }

    
    
    private boolean isVisible(Task thetask) {
        boolean res = true;
        DefaultMutableTreeNode father = getTree().getFatherNode(thetask);
        if (father == null) {
            return false;
        }

        while (father != null) {
            Task taskFather = (Task) (father.getUserObject());
            if (!taskFather.getExpand()) {
                res = false;
            }
            father = (DefaultMutableTreeNode) (father.getParent());
        }
        return res;
    }
    
    private static final int HEADER_OFFSET = 0;
    
}
