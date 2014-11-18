package com.medical.common;

public class Pager {

	// ��ǰҳ��
	private int currentpage;

	// ÿҳ��ʾ��Ϣ����
	private int pagesize = 16;

	// ��һҳ
	private int nextpage;

	// ��һҳ
	private int previouspage;

	// �ܼ�¼��
	private int all;

	// ��ҳ��
	private int pagecount;

	// ��ʼ����
	private int start;

	// ��������
	private int end;

	// ���ɲ˵�����
	private String toolsmenu = "";

	// ����
	private String url;

	//

	public int getNextpage() {
		return nextpage;
	}

	public void setNextpage(int nextpage) {
		this.nextpage = nextpage;
	}

	public int getPreviouspage() {
		return previouspage;
	}

	public void setPreviouspage(int previouspage) {
		this.previouspage = previouspage;
	}

	public int getAll() {
		return all;
	}

	public void setAll(int all) {
		this.all = all;
	}

	public int getPagecount() {
		return pagecount;
	}

	public void setPagecount(int pagecount) {
		this.pagecount = pagecount;
	}

	public String getToolsmenu() {
		return this.genToolsmenu();
	}

	public void setToolsmenu(String toolsmenu) {
		this.toolsmenu = toolsmenu;
	}

	public String genToolsmenu() {
		toolsmenu = "";

		if (currentpage > 0) {
			this.nextpage = currentpage + 1;
			this.previouspage = currentpage - 1;
			if (all % pagesize > 0) {
				pagecount = (all / pagesize) + 1;
			} else {
				pagecount = all / pagesize;
			}
			if (1 == pagecount) {
				currentpage=1;
			}
			this.start = (currentpage - 1) * pagesize;
			this.end = currentpage * pagesize;
		} else {
			return null;
		}

		if (url == null || url.equals("")) {
			return null;
		}

		String temp = "";
		if (url.indexOf("?") == -1) {
			temp = "?";
		} else {
			temp = "&";
		}

		if (1 == currentpage) {
			toolsmenu += "��ҳ ��һҳ&nbsp;";
		} else {
			toolsmenu += "<a href='" + url + temp + "cur_page=1'>��ҳ</a>&nbsp;";
			toolsmenu += "<a href='" + url + temp + "cur_page=" + previouspage
					+ "'>��һҳ</a>&nbsp;";
		}
		if (pagecount == currentpage) {
			toolsmenu += "��һҳ βҳ&nbsp;";
		} else {
			toolsmenu += "<a href='" + url + temp + "cur_page=" + nextpage
					+ "'>��һҳ</a>&nbsp;";
			toolsmenu += "<a href='" + url + temp + "cur_page=" + pagecount
					+ "'>βҳ</a>&nbsp;";
		}
		toolsmenu += "&nbsp;��<b>" + all + "</b>����¼&nbsp;��<b>" + pagecount
				+ "</b>ҳ";
		toolsmenu += "&nbsp;ת��<input id=\"page\" type=\"text\" value=\""
				+ currentpage
				+ "\" size=\"5\"/><input type=\"button\" value=\"ת��\" onclick=\"document.location.reload('"
				+ url + temp
				+ "cur_page='+document.getElementById('page').value)\"/>";
		return toolsmenu;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
