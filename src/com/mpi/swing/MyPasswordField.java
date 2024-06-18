package com.mpi.swing;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.awt.Toolkit;
import java.io.IOException;

public class MyPasswordField extends JPasswordField {

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public Icon getPrefixIcon() {
        return prefixIcon;
    }

    public void setPrefixIcon(Icon prefixIcon) {
        this.prefixIcon = prefixIcon;
        initBorder();
    }

    public Icon getSuffixIcon() {
        return suffixIcon;
    }

    public void setSuffixIcon(Icon suffixIcon) {
        this.suffixIcon = suffixIcon;
        initBorder();
    }

    private Icon prefixIcon;
    private Icon suffixIcon;
    private String hint = "";

    public MyPasswordField() {
        super();
        // Add key listener for select all, copy, and paste
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if ((e.getKeyCode() == KeyEvent.VK_A) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
                    selectAll();
                }
                if ((e.getKeyCode() == KeyEvent.VK_V) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    Transferable contents = clipboard.getContents(null);
                    try {
                        String pastedText = (String) contents.getTransferData(DataFlavor.stringFlavor);
                        replaceSelection(pastedText);
                    } catch (UnsupportedFlavorException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(0, 0, 0, 0));
        setForeground(Color.decode("#7A8C8D"));
        setFont(new java.awt.Font("sansserif", 0, 13));
        setSelectionColor(new Color(75, 175, 152));
        ActionMap am = getActionMap();
        InputMap im = getInputMap(JComponent.WHEN_FOCUSED);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), DefaultEditorKit.copyAction);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), DefaultEditorKit.pasteAction);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), DefaultEditorKit.selectAllAction);

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(230, 245, 241));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
        paintIcon(g);
        super.paintComponent(g);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (getPassword().length == 0) {
            int h = getHeight();
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            Insets ins = getInsets();
            FontMetrics fm = g.getFontMetrics();
            g.setColor(new Color(200, 200, 200));
            g.drawString(hint, ins.left, h / 2 + fm.getAscent() / 2 - 2);
        }
    }

    private void paintIcon(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (prefixIcon != null) {
            Image prefix = ((ImageIcon) prefixIcon).getImage();
            int y = (getHeight() - prefixIcon.getIconHeight()) / 2;
            g2.drawImage(prefix, 10, y, this);
        }
        if (suffixIcon != null) {
            Image suffix = ((ImageIcon) suffixIcon).getImage();
            int y = (getHeight() - suffixIcon.getIconHeight()) / 2;
            g2.drawImage(suffix, getWidth() - suffixIcon.getIconWidth() - 10, y, this);
        }
    }

    private void initBorder() {
        int left = 15;
        int right = 15;
        //  5 is default
        if (prefixIcon != null) {
            //  prefix is left
            left = prefixIcon.getIconWidth() + 15;
        }
        if (suffixIcon != null) {
            //  suffix is right
            right = suffixIcon.getIconWidth() + 15;
        }
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, left, 10, right));
    }
}
