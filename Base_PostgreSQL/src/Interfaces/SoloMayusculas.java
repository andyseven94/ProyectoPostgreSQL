/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Andy
 */
public class SoloMayusculas extends PlainDocument {
    @Override
    public void insertString(int offset,String str, AttributeSet atrr) throws BadLocationException{
        super.insertString(offset , str.toUpperCase(),atrr);
    }
}
