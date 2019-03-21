package com.ant.constant;

public enum TransactionType 
{
	unknow("\u672A\u77E5"),
	wechatpay("\u5FAE\u4FE1\u5145\u503C"),
	alipay("\u652F\u4ED8\u5B9D\u652F\u4ED8"),
	cash("\u73B0\u91D1"),
	wechatscan("\u5FAE\u4FE1\u626B\u7801"),
	alipayscan("\u652F\u4ED8\u5B9D\u626B\u7801"),
	card("\u5237\u5361"),
	charge("\u6263\u8D39"),
	withdraw("\u9000\u6B3E");
	
	private String description;
	
	private TransactionType(String description) 
	{
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
}
