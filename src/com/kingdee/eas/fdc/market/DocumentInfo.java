package com.kingdee.eas.fdc.market;

import java.awt.Font;
import java.io.Serializable;

import javax.swing.SwingConstants;

public class DocumentInfo extends AbstractDocumentInfo implements Serializable ,DocumentProperties
{

	public final static int MAX_FONT_SIZE = 40;
	public final static int MIN_FONT_SIZE = 5;
	
	
	public final static int MIN_HEIGHT = 100;
	public final static int MIN_WIDTH = 100;
	
	public final static int MAX_MARGE = 40;
	public final static int MIN_MARGE = 1;
	
	public final static int MIN_COLUMN_COUNT = 1;
	public final static int MAX_COLUMN_COUNT = 4;
	
	
    public DocumentInfo()
    {
        super();
    }
    
    
    protected DocumentInfo(String pkField)
    {
        super(pkField);
    }
    
    
    

	
    
    public int getHeight() {
		int height = super.getHeight();
		return Math.max(height,MIN_HEIGHT);
	}

	public int getWidth() {
		int width = super.getWidth();
		return Math.max(width,MIN_WIDTH);
	}
	public int getLeftMarge() {
		int marge = super.getLeftMarge();
		return Math.min(Math.max(marge,MIN_MARGE),MAX_MARGE);
	}
	public int getRightMarge() {
		int marge = super.getRightMarge();
		return Math.min(Math.max(marge,MIN_MARGE),MAX_MARGE);
	}
	public int getTopMarge() {
		int marge = super.getTopMarge();
		return Math.min(Math.max(marge,MIN_MARGE),MAX_MARGE);
	}
	
	public int getBottomMarge() {
		int marge = super.getBottomMarge();
		return Math.min(Math.max(marge,MIN_MARGE),MAX_MARGE);
	}
	public int getColumnCount() {
		int count = super.getColumnCount();
		return Math.min(Math.max(count,MIN_COLUMN_COUNT),MAX_COLUMN_COUNT);
	}
	public Font getFont() {
		Font font = null;
		if(getXFontName() == null || getXFontSize() <= MIN_FONT_SIZE || getXFontSize() >= MAX_FONT_SIZE){
			font = getDefaultFont();
		}else {
			font = new Font(getXFontName(),Font.PLAIN,getXFontSize());
		}
		return font;
	}
    
    
    
	public Font getDefaultFont(){
		return new Font("Dialog",Font.PLAIN,12);
	}
    
    
    public int getMaxSubjectSerialNumber(){
    	DocumentSubjectCollection c = getSubjects();
    	if(c.size() > 0){
    		return 0;
    	}else{
    		return c.get(c.size() - 1).getSubjectNumber();
    	}
    	
    }

    
    
}