package com.jinlink;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileCompressor  {
    private static final long SIZE_THRESHOLD = 50 * 1024;
    public static void main(String[] args) {
        String sourceDirectory = "D:\\jinLink";
        compressImagesInDirectory(new File(sourceDirectory));
    }
    public static void compressImagesInDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        compressImagesInDirectory(file);
                    } else {
                        String fileName = file.getName().toLowerCase();
                        if ((fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png"))
                                && file.length() > SIZE_THRESHOLD) {
                            System.out.println("Processing file: " + file.getAbsolutePath());
                            compressImage(file, 555, 312);
                        }
                    }
                }
            }
        }
    }
    public static void compressImage(File inputFile, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(inputFile);
            if (originalImage == null) {
                System.err.println("Failed to read image: " + inputFile.getAbsolutePath());
                return;
            }
            BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(originalImage, 0, 0, width, height, null);
            g2d.dispose();
            String outputFilePath = inputFile.getAbsolutePath();
            String fileExtension = outputFilePath.substring(outputFilePath.lastIndexOf(".") + 1);
            File outputFile = new File(outputFilePath);
            ImageIO.write(resizedImage, fileExtension, outputFile);
            System.out.println("Compressed: " + inputFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error compressing image: " + inputFile.getAbsolutePath());
            e.printStackTrace();
        }
    }
}
