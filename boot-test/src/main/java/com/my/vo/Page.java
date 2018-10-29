package com.my.vo;

import java.util.List;

public class Page<T>
{
    private static final int DEFAULT_CUR_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 5;
    
    /* ****************需要查询的数据******************** */
    private List<T> pageList;
    private Long maxCount;
    
    /* ****************需要客户端提供的数据******************** */
    private Integer curPage;
    private Integer pageSize;
    
    /* ****************需要 自己计算 数据******************** */
    private long maxPage;
    private int itemCountOfPage;
    private Boolean isFirst;
    private Boolean isLast;
    private int offset;
    
    public Page()
    {
    }
    
    public Page(Integer curPage, Integer pageSize)
    {
        super();
        this.curPage = curPage;
        this.pageSize = pageSize;
    }
    
    public int getOffset()
    {
        this.offset = this.getCurPage() * this.getPageSize();
        return offset;
    }
    
    public long getMaxPage()
    {
        maxPage = getMaxCount() % getPageSize() == 0 ? getMaxCount() / getPageSize()
                : (getMaxCount() / getPageSize()) + 1;
        return maxPage;
    }
    
    public int getItemCountOfPage()
    {
        itemCountOfPage = null == pageList || pageList.isEmpty() ? 0 : pageList.size();
        return itemCountOfPage;
    }
    
    public Boolean getIsFirst()
    {
        isFirst = getCurPage() <= 0;
        return isFirst;
    }
    
    public Boolean getIsLast()
    {
        isLast = getCurPage() >= getMaxPage() - 1;
        return isLast;
    }
    
    public Integer getCurPage()
    {
        curPage = null == curPage || curPage < 0 ? DEFAULT_CUR_PAGE : curPage;
        return curPage;
    }
    
    public Integer getPageSize()
    {
        pageSize = null == pageSize || pageSize < 0 ? DEFAULT_PAGE_SIZE : pageSize;
        return pageSize;
    }
    
    public Long getMaxCount()
    {
        maxCount = null == maxCount ? 0L : maxCount;
        return maxCount;
    }
    
    public List<T> getPageList()
    {
        return pageList;
    }
    
    public void setPageList(List<T> pageList)
    {
        this.pageList = pageList;
    }
    
    public void setCurPage(Integer curPage)
    {
        this.curPage = curPage;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public void setMaxCount(Long maxCount)
    {
        this.maxCount = maxCount;
    }
    
    public void setMaxPage(long maxPage)
    {
        this.maxPage = maxPage;
    }
    
    public void setItemCountOfPage(int itemCountOfPage)
    {
        this.itemCountOfPage = itemCountOfPage;
    }
    
    public void setIsFirst(Boolean isFirst)
    {
        this.isFirst = isFirst;
    }
    
    public void setIsLast(Boolean isLast)
    {
        this.isLast = isLast;
    }
    
    public void setOffset(int offset)
    {
        this.offset = offset;
    }
    
}
