package com.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class GenerateCheckCodeUtils
{
    public static interface CallBack
    {
        void doSomething(String checkCode);
    }
    
    public static void createAndSend(CallBack callBack, OutputStream output) throws IOException
    {
        int width = 360; // 图片宽度
        int height = 100; // 图片高度
        String imgType = "jpeg"; // 指定图片格式 (不是指MIME类型)
        
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphic = image.getGraphics();
        
        graphic.setColor(Color.getColor("F8F8F8"));
        graphic.fillRect(0, 0, width, height);
        
        Color[] colors = new Color[]
        { Color.BLUE, Color.GRAY, Color.GREEN, Color.RED, Color.BLACK, Color.ORANGE, Color.CYAN };
        // 在 "画板"上生成干扰线条 ( 50 是线条个数)
        for (int i = 0; i < 50; i++)
        {
            graphic.setColor(colors[random.nextInt(colors.length)]);
            final int x = random.nextInt(width);
            final int y = random.nextInt(height);
            final int w = random.nextInt(20);
            final int h = random.nextInt(20);
            final int signA = random.nextBoolean() ? 1 : -1;
            final int signB = random.nextBoolean() ? 1 : -1;
            graphic.drawLine(x, y, x + w * signA, y + h * signB);
        }
        
        // 在 "画板"上绘制字母
        graphic.setFont(new Font("Comic Sans MS", Font.BOLD, 100));
        for (int i = 0; i < 6; i++)
        {
            final int temp = random.nextInt(26) + 97;
            String s = String.valueOf((char) temp);
            sb.append(s);
            graphic.setColor(colors[random.nextInt(colors.length)]);
            graphic.drawString(s, i * (width / 6), height - (height / 3));
        }
        
        String checkCode = sb.toString();// TODO 想办法给Servlet
        callBack.doSomething(checkCode);
        
        graphic.dispose();
        
        ImageIO.write(image, imgType, output);
        
    }
    
    public static void main(String[] args)
    {
        for (int i = 97; i < (97 + 26); i++)
        {
            System.out.println((char) i);
        }
    }
}
