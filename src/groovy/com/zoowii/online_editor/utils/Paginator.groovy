package com.zoowii.online_editor.utils

import com.alibaba.fastjson.annotation.JSONField

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zoowii.common.Pair;

/**
 * TODO: migrate to Grails GORM
 */
public class Paginator extends com.zoowii.web.Paginator {
    public int page = 1;
    public int pageSize = 10;
    public int totalCount = -1;
    public String whereString = null;
    public List<Pair<String, Object>> equalConditions = new ArrayList<Pair<String, Object>>();
//    public Pair<Expression, Expression> orCondition = null;
    public Pair<String, String> likePair = null;
    public List<Object> parameters = new ArrayList<Object>();
    public String sort = null;
    public boolean asc = false;
    public String test = "<h1>hi</h1>";
    public int paginationDisplayCount = 5; // 分页显示时最多显示多少页的页码
    public String extraUrlParams = null; // 生成HTML的分页组件时，里面的URL会自动加上这一部分到query string里面

    public Paginator(Paginator source) {
        this.page = source.page;
        this.pageSize = source.pageSize;
        this.totalCount = source.totalCount;
        this.whereString = source.whereString;
        this.equalConditions = source.equalConditions;
        this.orCondition = source.orCondition;
        this.likePair = source.likePair;
        this.parameters = source.parameters;
        this.sort = source.sort;
        this.asc = source.asc;
        this.paginationDisplayCount = source.paginationDisplayCount;
        this.extraUrlParams = source.extraUrlParams;
    }

//    /**
//     * 设置属性name值在一个数组values中的查询表达式
//     *
//     * @param name
//     * @param values
//     * @param <T>
//     */
//    public <T> Expression setOneOfSomeCondition(String name, List<T> values) {
//        if (values.size() < 1) {
//            return Expr.eq(name, null);
//        }
//        if (values.size() == 1) {
//            return Expr.eq(name, values.get(0));
//        }
//        return Expr.or(Expr.eq(name, values.get(0)),
//                setOneOfSomeCondition(name, values.subList(0, values.size())));
//    }

    public String getOrderByString() {
        if (sort != null) {
            return sort + " " + (asc ? "asc" : "desc");
        } else {
            return null;
        }
    }

    private static Integer getIntParamFromRequest(HttpServletRequest request, String name) {
        if (request.getParameter(name)) {
            try {
                return Integer.valueOf(request.getParameter(name))
            } catch (Exception e) {

            }
        }
        return null
    }

    private static Boolean getBooleanParamFromRequest(HttpServletRequest request, String name) {
        if (request.getParameter(name)) {
            try {
                return Boolean.valueOf(request.getParameter(name))
            } catch (Exception e) {

            }
        }
        return null
    }

    private void reConsParams() {
        if (this.page < 1) {
            this.page = 1
        }
        if (pageSize < 1) {
            this.pageSize = 1
        }
    }

    public Paginator(HttpServletRequest request) {
        def page = getIntParamFromRequest(request, 'page')
        if (page) {
            this.page = page
        }
        def pageSize = getIntParamFromRequest(request, 'pageSize')
        if (pageSize) {
            this.pageSize = pageSize
        }
        reConsParams()
        this.sort = request.getParameter("sort")
        def asc = getBooleanParamFromRequest(request, "asc");
        if (asc != null) {
            this.asc = asc
        }
    }

    public Paginator() {

    }

    public int getTotal() {
        return totalCount;
    }

    public int skippedCount() {
        return (page - 1) * pageSize;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = (int) totalCount
    }

    public int getPageCount() {
        return 1 + (totalCount - 1) / pageSize;
    }

    @JSONField(serialize = false)
    public String getPaginationHtml() {
        return getPagination2();
    }

    def getOffset() {
        def offset = (this.page - 1) * this.pageSize
        return offset < 0 ? 0 : offset
    }

    /**
     * 另一个分页HTML组件的实现
     */
    @JSONField(serialize = false)
    public String getPagination2() {
        StringBuilder builder = new StringBuilder();
        int totalPages = getPageCount();
        if (page > totalPages) {
            page = totalPages;
        }
        if (page < 0) {
            page = 1;
        }
        int startPage = page - paginationDisplayCount / 2;
        if (startPage < 1) {
            startPage = 1;
        }
        int endPage = startPage + paginationDisplayCount - 1;
        if (endPage > totalPages) {
            endPage = totalPages;
        }
        builder.append("<div class='page' data-page-size='" + pageSize + "' data-extra-params='" + (extraUrlParams == null ? "" : extraUrlParams) + "'>");
        builder.append("<p>共<strong>" + this.totalCount + "</strong>条</p>");
        String tmpUrl;
        builder.append("<em><a class='z_next pager-link' data-page='1' title='到首页'>&nbsp;</a></em>");
        builder.append("<em><a class='l_next pager-link' data-page='" + (page < 2 ? 1 : page - 1) + "' title='上一页'>&nbsp;</a></em>");
        builder.append("<p><strong>" + page + "</strong>/" + totalPages + "</p>");
        builder.append("<em><a class='r_next pager-link' data-page='" + (page > totalPages - 1 ? totalPages : (page + 1)) + "' title='下一页'>&nbsp;</a></em>");
        builder.append("<em><a class='y_next pager-link' data-page='" + totalPages + "' title='到尾页'>&nbsp;</a></em>");
        int[] pageSizeOptions = [10, 20, 30, 40, 50, 60, 70, 80, 90];
        builder.append("<p>每页显示<select class='page-size-select'>");
        if (!Common.inArray(pageSize, pageSizeOptions)) {
            builder.append("<option value='" + pageSize + "' selected='selected'>" + pageSize + "</option>");
        }
        for (int pageSizeOption : pageSizeOptions) {
            builder.append("<option value='" + pageSizeOption + "'" + (pageSizeOption == pageSize ? " selected='selected'" : "") + ">" + pageSizeOption + "</option>");
        }
        builder.append("</select>条</p>");
        builder.append("<p>跳转到<input class='jump-page-field'></p>");
        builder.append("<em><a class='tz jump-btn' title=\"跳转\"></a></em>");
        builder.append("</div>");
        return builder.toString();
    }

    protected String makeExtraUrl(String url) {
        if (this.extraUrlParams != null) {
            return url + "&" + this.extraUrlParams;
        } else {
            return url;
        }
    }

    @JSONField(serialize = false)
    protected String getFirstPageUrl() {
        return makeExtraUrl("?page=1&pageSize=" + pageSize);
    }

    @JSONField(serialize = false)
    protected String getUrlOfPage(int page) {
        if (page < 1) {
            page = 1;
        }
        if (page > getPageCount()) {
            page = getPageCount();
        }
        return makeExtraUrl("?page=" + page + "&pageSize=" + pageSize);
    }

    /**
     * 生成一个简单的分页HTML
     */
    @JSONField(serialize = false)
    public String getSimplePaginationHtml() {
        StringBuilder builder = new StringBuilder();
        builder.append("<ul class=\"pagination\">");
        int totalPages = getPageCount();
        if (page > totalPages) {
            page = totalPages;
        }
        if (page < 1) {
            page = 1;
        }
        int startPage = page - paginationDisplayCount / 2;
        if (startPage < 1) {
            startPage = 1;
        }
        int endPage = startPage + paginationDisplayCount - 1;
        if (endPage > totalPages) {
            endPage = totalPages;
        }
        String tmpUrl;
        for (int i = startPage; i <= endPage; ++i) {
            if (i == startPage) {
                tmpUrl = "?page=1&pageSize=" + pageSize;
                if (this.extraUrlParams != null) {
                    tmpUrl += this.extraUrlParams;
                }
                builder.append("<li><a href='" + tmpUrl + "'>&laquo;</a></li>");
                if (startPage <= 1) {
                    builder.append("<li class=\"disabled\"><a href=\"#\">&laquo;</a></li>");
                } else {
                    tmpUrl = "?page='" + (startPage - paginationDisplayCount) + "&pageSize=" + pageSize;
                    if (this.extraUrlParams != null) {
                        tmpUrl += this.extraUrlParams;
                    }
                    builder.append("<li class=\"\"><a href='" + tmpUrl + "'>&laquo;</a></li>");
                }
            }

            if (i == page) {
                tmpUrl = "?page=" + i + "&pageSize=" + pageSize;
                if (this.extraUrlParams != null) {
                    tmpUrl += this.extraUrlParams;
                }
                builder.append("<li class=\"active\"><a href='" + tmpUrl + "'>" + i + " <span class=\"sr-only\">(current)</span></a></li>");
            } else {
                tmpUrl = "?page=" + i + "&pageSize=" + pageSize;
                if (this.extraUrlParams != null) {
                    tmpUrl += this.extraUrlParams;
                }
                builder.append("<li><a href='" + tmpUrl + "'>" + i + "</a></li>");
            }
            if (i == endPage) {
                if (endPage >= totalPages) {
                    builder.append("<li class=\"disabled\"><a href=\"#\">&raquo;</a></li>");
                } else {
                    tmpUrl = "?page=" + (endPage + paginationDisplayCount) + "&pageSize=" + pageSize;
                    if (this.extraUrlParams != null) {
                        tmpUrl += this.extraUrlParams;
                    }
                    builder.append("<li class=\"\"><a href='" + tmpUrl + "'>&raquo;</a></li>");
                }
                tmpUrl = "?page=" + totalPages + "&pageSize=" + pageSize;
                if (this.extraUrlParams != null) {
                    tmpUrl += this.extraUrlParams;
                }
                builder.append("<li><a href='" + tmpUrl + "'>&raquo;</a></li>");
            }
        }
        builder.append("<li>总页数: " + totalPages + " Pages</li>");
        builder.append("</ul>");
        return builder.toString();
    }
}