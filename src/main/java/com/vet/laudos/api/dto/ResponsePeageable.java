package com.vet.laudos.api.dto;

import java.io.Serializable;

import org.springframework.data.domain.Page;

import com.vet.laudos.domain.model.Info;

public class ResponsePeageable <T extends Serializable> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Object content;
	private long totalElements;
	private long totalPages;
	private long size;
	private long page;
	private Info info = new Info();
	
	public ResponsePeageable (Page<T> page) {
		this.totalElements = page.getTotalElements();
		this.totalPages = page.getTotalPages();
		this.size = page.getSize();
		this.page = page.getPageable().getPageNumber()+1;
		this.content = page.getContent();
	}
	
	public ResponsePeageable (Info info) {
		this.info = info;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

}
