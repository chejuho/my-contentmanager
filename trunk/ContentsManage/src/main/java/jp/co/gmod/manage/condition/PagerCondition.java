package jp.co.gmod.manage.condition;

import java.io.Serializable;

public class PagerCondition implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	//現在ページ
	private int nowPage = 0;
	//総カウンタ
	private int totalCount = 0;
	//ページ毎に表示する数
	private int page_per = 10;
	//1グループに表示するページ
	private int group_per = 20;



	public PagerCondition(int totalCount){
		this.totalCount =  totalCount;
		this.nowPage = 1;
	}
	public PagerCondition(int page_per, int group_per, int totalCount) {
		this.page_per =  page_per;
		this.group_per =  group_per;
		this.totalCount =  totalCount;
		this.nowPage = 1;
	}


	/**
	 * nowPageを取得します。
	 * @return nowPage
	 */
	public int getNowPage() {
	    return nowPage;
	}
	/**
	 * nowPageを設定します。
	 * @param nowPage nowPage
	 */
	public void setNowPage(int nowPage) {
	    this.nowPage = nowPage;
	}

	/**
	 * totalCountを取得します。
	 * @return totalCount
	 */
	public int getTotalCount() {
	    return totalCount;
	}
	/**
	 * totalCountを設定します。
	 * @param totalCount totalCount
	 */
	public void setTotalCount(int totalCount) {
	    this.totalCount = totalCount;
	}

	/**
	 *
	 * @return
	 */
	public int getLimit() {
		return page_per;

	}

	public int getOffset() {
		return (nowPage-1)*page_per;

	}

	//何件～
	public int getStartIndex() {
		return getOffset() + 1;
	}
	//何件まで
	public int getEndIndex() {

		int endIndex = getOffset() + page_per;

		if (endIndex > totalCount) {
			return totalCount;
		}

		return getOffset() + page_per;
	}

	/**
	 * displayPageIndexBeginを取得します。
	 * @return displayPageIndexBegin
	 */
	public int getDisplayPageIndexBegin() {

		int count = nowPage / group_per;
		if (nowPage % group_per == 0) {
			count -= 1;
		}
		return (count * group_per) + 1;
	}

	/**
	 * currentPageCountを取得します。
	 * @return currentPageCount
	 */
	public int getCurrentPageCount() {
	    return nowPage;
	}

	/**
	 * displayPageIndexEndを取得します。
	 * @return displayPageIndexEnd
	 */
	public int getDisplayPageIndexEnd() {

		int end = getDisplayPageIndexBegin() + group_per - 1;

		if (end > getLastPageIndex()) {
			end = getLastPageIndex();
		}
	    return end;
	}

	/**
	 * lastPageIndexを取得します。
	 * @return lastPageIndex
	 */
	public int getLastPageIndex() {

		int pageindex = totalCount / page_per + 1;
		if (totalCount % page_per == 0) {
			pageindex -= 1;
		}
	    return pageindex;
	}

	/**
	 * isPrevを取得します。
	 * @return isPrev
	 */
	public boolean isPrev() {

		int count = nowPage / group_per;
		if (nowPage % group_per == 0) {
			count -= 1;
		}

	    return count > 0 ? true : false;
	}

	/**
	 * isNextを取得します。
	 * @return isNext
	 */
	public boolean isNext() {
	    return getDisplayPageIndexEnd() < getLastPageIndex();
	}



}
