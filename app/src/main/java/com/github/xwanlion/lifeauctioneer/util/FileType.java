package com.github.xwanlion.lifeauctioneer.util;

public enum FileType {
	UNKNOWN("-1"), IMAGE("0"), AUDIO("1"), VIDEO("2"), TEXT("3");

	private String type;

	FileType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

}