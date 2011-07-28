package jp.co.gmod.manage.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import jp.co.gmod.manage.condition.PagerCondition;

public class PageHelper {


	private final static String INIT_KEY = "init";
	private final static String BEFORE_KEY = "before";
	private final static String AFTER_KEY = "after";

	public static PagerCondition pageProcess(
			HttpSession session,
			String pageKey,
			String pageNum,
			int pagePerNum,
			int groupPerPage,
			int totalCnt) {

		if (INIT_KEY.equals(pageNum)) {
			return createPager(session, pageKey, pagePerNum, groupPerPage, totalCnt);
		} else if (BEFORE_KEY.equals(pageNum)) {
			PagerCondition pager = prevPage(session, pageKey);
			if (pager == null) {
				return createPager(session, pageKey, pagePerNum, groupPerPage, totalCnt);
			} else {
				return pager;
			}
		} else if (AFTER_KEY.equals(pageNum)) {
			PagerCondition pager = nextPage(session, pageKey);
			if (pager == null) {
				return createPager(session, pageKey, pagePerNum, groupPerPage, totalCnt);
			} else {
				return pager;
			}
		} else {
			PagerCondition pager = jumpPage(session, pageKey, Integer.parseInt(pageNum));
			if (pager == null) {
				return createPager(session, pageKey, pagePerNum, groupPerPage, totalCnt);
			} else {
				return pager;
			}
		}
	}
	public static PagerCondition getPager(HttpSession session, String pageKey, String pageNum) {

		if ("init".equals(pageNum)) {
			session.removeAttribute(pageKey);
			return null;
		}

		return (PagerCondition) session.getAttribute(pageKey);
	}

	public static PagerCondition getPager(HttpSession session, String pageKey) {

		return (PagerCondition) session.getAttribute(pageKey);
	}

	public static void deletePageInfo(HttpSession session, String pageKey) {
		session.removeAttribute(pageKey);
	}

	public static String getView(HttpSession session, String pageKey) {
		PagerCondition pager = getPager(session, pageKey);

		List<String> links = new ArrayList<String>();

		links.add("<div id='paging'>");
        //前グループに戻る
        if (pager.isPrev()) {
        	links.add("<a  href='before'><<</a>");
        }

        int startNum = pager.getDisplayPageIndexBegin();
        int endNum = pager.getDisplayPageIndexEnd();

        for(int i = startNum; i <= endNum;i++) {
            if( i == pager.getCurrentPageCount() ) {
                links.add(String.format("%d", i));
            } else {
                links.add(String.format("<a href='%d'>%d</a>", i, i));
            }
        }

        if (pager.isNext()) {
        	links.add("<a href='after'>>></a>");
        }

        StringBuilder buf = new StringBuilder();

        for (String link : links) {
        	 buf.append(link);
        	 buf.append(" ");
        }

        links.add("</div>");
        return buf.toString();
	}

	private static PagerCondition createPager(HttpSession session, String pageKey, int pagePerNum, int groupPerPage, int totalCnt) {
		PagerCondition pager = new PagerCondition(pagePerNum, groupPerPage, totalCnt);
		session.setAttribute(pageKey, pager);
		return pager;
	}


	private static PagerCondition jumpPage(HttpSession session, String pageKey, int pageNum) {
		PagerCondition pager = getPager(session, pageKey);
		if (pager == null) {
			return pager;
		}
		if (pageNum > pager.getLastPageIndex()) {
			return pager;
		}

		pager.setNowPage(pageNum);
		session.setAttribute(pageKey, pager);
		return pager;
	}

	private static PagerCondition prevPage(HttpSession session, String pageKey) {
		PagerCondition pager = getPager(session, pageKey);
		if (pager == null) {
			return pager;
		}
		int pageNum = pager.getNowPage();
		if (pageNum - 1 == 0) {
			return pager;
		}
		pager.setNowPage(pageNum - 1);
		return pager;
	}

	private static PagerCondition nextPage(HttpSession session, String pageKey) {
		PagerCondition pager = getPager(session, pageKey);
		if (pager == null) {
			return pager;
		}
		int pageNum = pager.getNowPage();

		if (pageNum + 1 > pager.getLastPageIndex()) {
			return pager;
		}
		pager.setNowPage(pageNum + 1);
		return pager;
	}




}
