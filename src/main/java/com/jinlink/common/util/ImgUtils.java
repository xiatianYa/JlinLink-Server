package com.jinlink.common.util;

import cn.hutool.core.util.ObjectUtil;
import com.jinlink.modules.game.entity.vo.SteamServerVo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ImgUtils extends JFrame {
    public static File serverImgBuilder(List<SteamServerVo> gameServerVoList) throws IOException {
        // 加载背景图片
        File backgroundFile = new File("D:\\Bot\\serverQueryBg.png");
        BufferedImage backgroundImage = ImageIO.read(backgroundFile);

        // 创建一个新的图片，大小与背景图片相同
        BufferedImage combinedImage = new BufferedImage(backgroundImage.getWidth(), backgroundImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = combinedImage.createGraphics();
        // 绘制背景图片
        g2d.drawImage(backgroundImage, 0, 0, null);
        // 设置字体
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 17));

        // 计算需要多少行来容纳所有盒子
        int rows = Math.min((int) Math.ceil((double) gameServerVoList.size() / 6), 6);
        // 盒子宽度
        int boxWidth = 392;
        // 盒子高度
        int boxHeight = 200;
        // 盒子内边距
        int margin = 15;
        // 盒子的颜色 和 样式
        Graphics2D roundedG2d = (Graphics2D) g2d.create();
        roundedG2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color semiTransparentGray = new Color(0, 0, 0, 160);

        // 绘制圆角盒子和文字
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < 6 && row * 6 + col < gameServerVoList.size(); col++) {
                int x = margin + col * (boxWidth + margin);
                int y = margin + row * (boxHeight + margin);

                //给盒子里添加文字
                SteamServerVo steamServerVo = gameServerVoList.get(row * 6 + col);

                //添加盒子背景
                try {
                    if (ObjectUtil.isNotNull(steamServerVo.getMapUrl())){
                        BufferedImage boxImage = resizeImage(steamServerVo.getMapUrl(), boxWidth, boxHeight);
                        g2d.drawImage(boxImage, x, y, boxWidth, boxHeight, null);
                    }else{
                        //添加盒子
                        RoundRectangle2D.Double roundRect = new RoundRectangle2D.Double(x, y, boxWidth, boxHeight, 0, 0);
                        roundedG2d.setColor(semiTransparentGray); // 设置圆角矩形的颜色
                        roundedG2d.fill(roundRect);
                    }
                }catch (Exception e){
                    System.out.println("图片绘制失败");
                }

                // 在盒子顶部添加加载条
                drawLoadingBar(g2d,x,y, steamServerVo.getPlayers(),steamServerVo.getMaxPlayers(),boxWidth,10);

                roundedG2d.setColor(Color.WHITE);
                String serverName = steamServerVo.getServerName();
                roundedG2d.drawString(serverName, x + 5, y + 40);
                String onLineUser="在线玩家("+steamServerVo.getPlayers()+"/"+steamServerVo.getMaxPlayers()+")";
                roundedG2d.drawString(onLineUser, x+5,y+70);
                String map="地图名:"+steamServerVo.getMapName();
                roundedG2d.drawString(map, x+5,y+100);
                String label="地图译名:"+steamServerVo.getMapLabel();
                roundedG2d.drawString(label, x+5,y+130);

            }
        }

        // 保存生成的图片
        File outputFile = new File("D:\\Bot\\background_with_boxes.png");
        ImageIO.write(combinedImage, "png", outputFile);

        return outputFile;
    }

    /**
     * 绘制加载条
     *
     * @param g2d        Graphics2D对象，用于绘制
     * @param x          加载条左上角的x坐标
     * @param y          加载条左上角的y坐标（注意：这将是加载条底部的y坐标，因为加载条是向上绘制的）
     * @param player     当前玩家数
     * @param maxPlayer  最大玩家数
     * @param loadBarMaxWidth 加载条的最大宽度
     * @param loadBarHeight 加载条的高度
     */
    public static void drawLoadingBar(Graphics2D g2d, int x, int y, int player, int maxPlayer, int loadBarMaxWidth, int loadBarHeight) {
        // 确保最大玩家数不为零
        if (maxPlayer == 0) {
            throw new IllegalArgumentException("maxPlayer cannot be zero");
        }

        // 根据玩家比例计算加载条的宽度
        double playerRatio = (double) player / maxPlayer;
        int loadBarWidth = (int) Math.min(playerRatio * loadBarMaxWidth, loadBarMaxWidth); // 确保宽度不会超过最大值

        // 调整加载条的位置，使其底部与给定的y坐标对齐
        int loadBarY = y + 10 - loadBarHeight;

        // 创建加载条矩形
        RoundRectangle2D loadBar = new RoundRectangle2D.Double(x, loadBarY, loadBarWidth, loadBarHeight,0,0);

        // 设置颜色并绘制加载条
        if (loadBarMaxWidth <= 118) {
            g2d.setColor(new Color(0, 249, 26));
        } else if (loadBarWidth <= 236) {
            g2d.setColor(new Color(84, 112, 238));
        } else if (loadBarWidth <= 344) {
            g2d.setColor(new Color(255, 163, 37));
        }else{
            g2d.setColor(new Color(255, 79, 0));
        }
        g2d.fill(loadBar);
    }

    /**
     * 下载网络图片 调整图片大学奥
     */
    public static BufferedImage resizeImage(String imageUrl, int targetWidth, int targetHeight) throws IOException {
        BufferedImage originalImage = ImageIO.read(new URL(imageUrl));
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(originalImage.getScaledInstance(targetWidth, targetHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
        g2d.dispose();
        return resizedImage;
    }
}
