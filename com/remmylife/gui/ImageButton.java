package com.remmylife.gui;

import java.awt.*;

import javax.swing.*;

public class ImageButton extends JButton {

    public ImageButton(String img) {
        this(new ImageIcon(img));
    }

    public ImageButton(ImageIcon icon) {
        setIcon(icon);
        setMargin(new Insets(0,0,0,0));
        setIconTextGap(0);
        setBorderPainted(false);
        setBorder(null);
        setFont(new Font("¿¬Ìå",Font.BOLD,15));
        this.setForeground(Color.black);
        this.setOpaque(false);
        this.setBackground(Color.getHSBColor(91,32, 85));
        setSize(icon.getImage().getWidth(null),
                icon.getImage().getHeight(null));
    }

}
