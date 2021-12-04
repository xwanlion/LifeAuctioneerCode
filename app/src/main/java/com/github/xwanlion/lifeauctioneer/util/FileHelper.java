package com.github.xwanlion.lifeauctioneer.util;

import android.graphics.Bitmap;

import com.github.xwanlion.lifeauctioneer.conf.Msg;
import com.github.xwanlion.lifeauctioneer.conf.System;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileHelper {
	private FileType fileType;
	private java.io.File sFile;
	private String suffix;
	private final String[] IMAGE_FILE_TYPES = { "jpg", "bmp", "tiff", "png" };
	private final String[] MEDIA_FILE_TYPE = { "mp3", "acc", "wav", "midi" };
	private final String[] MEDIA_FILE_TYPES = { "avi", "wmv", "mpg", "rmvb" };

	public FileHelper() {}

	public FileHelper(String filePath) {
		java.io.File file = new java.io.File(filePath);
		this.setFile(file);
	}

	/**
	 * file type:</br>
	 * -1: Unkown File</br>
	 * 0: Image File</br>
	 * 1: Audio File</br>
	 * 2: media File</br>
	 */
	public FileType getFileType() {
		return fileType;
	}

	public java.io.File getFile() {
		return sFile;
	}

	public void setFile(java.io.File file) {
		this.parseFileType(file);
		this.sFile = file;
	}

	public String getSuffix() {
		return suffix;
	}

	public boolean isAudioFile() {
		return this.fileType.equals(FileType.AUDIO);
	}

	public boolean isMediaFile() {
		return this.fileType.equals(FileType.VIDEO);
	}

	public boolean isImageFile() {
		return this.fileType.equals(FileType.IMAGE);
	}

	public static FileHelper loadFile(String filePath) {
		FileHelper mFile = new FileHelper(filePath);
		return mFile;
	}

	public static Msg outputToFile(Bitmap image, String outputFileName, Bitmap.CompressFormat format) {
		OutputStream os = null;
		String fileName = outputFileName;
		if (format != Bitmap.CompressFormat.PNG && format != Bitmap.CompressFormat.JPEG) format = Bitmap.CompressFormat.PNG;
		if (format == Bitmap.CompressFormat.PNG) fileName = fileName + ".png";
		if (format == Bitmap.CompressFormat.JPEG) fileName = fileName + ".jpg";

		if (SdCardHelper.createPath(fileName) == false) return Msg.FOLDER_CREATE_FAIL;

		try {
			os = new FileOutputStream(fileName);
			image.compress(format, 100, os);
			os.flush();
			return Msg.ERR_UNKONW;

		} catch (Exception e) {
			e.printStackTrace();
			return Msg.ERR_UNKONW;

		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String generateFileName() {
		return generateFileName(System.SYSTEM_FOLDER);
	}

	public static String generateFileName(String folder) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String strDate = simpleDateFormat.format(new Date());

		StringBuffer fileName = new StringBuffer();
		fileName.append(SdCardHelper.getSdCardPath());
		fileName.append(java.io.File.separator + folder + java.io.File.separator);
		fileName.append(strDate);
		return fileName.toString();

	}


	public static boolean fileTypeMismatch(FileHelper publicFile, FileHelper toBeEncryptedFile) {
		if (publicFile == null || toBeEncryptedFile == null) return true;
		return !publicFile.getFileType().equals(toBeEncryptedFile.getFileType());
	}

	private boolean isSpecifyFile(String[] fileTypes, String fileType) {
		if (fileType == null || fileTypes == null || fileTypes.length == 0) return false;
		for (String type : fileTypes) {
			if (fileType.equalsIgnoreCase(type)) return true;
		}
		return false;
	}

	private void parseFileType(java.io.File file) {
		this.parseFileSuffix(file);

		if (this.isSpecifyFile(IMAGE_FILE_TYPES, this.suffix)) {
			this.fileType = FileType.IMAGE;
		} else if (this.isSpecifyFile(MEDIA_FILE_TYPE, this.suffix)) {
			this.fileType = FileType.AUDIO;
		} else if (this.isSpecifyFile(MEDIA_FILE_TYPES, this.suffix)) {
			this.fileType = FileType.VIDEO;
		} else {
			this.fileType = FileType.UNKNOWN;
		}
	}

	private void parseFileSuffix(java.io.File file) {
		String fileName = file.getName();
		String[] strArray = fileName.split("\\.");
		this.suffix = strArray[strArray.length - 1];
	}


}
