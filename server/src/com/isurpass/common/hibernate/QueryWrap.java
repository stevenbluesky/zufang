package com.isurpass.common.hibernate;

import java.util.List;

public interface QueryWrap {

	public int count();
	public <T> List<T> list();
}
