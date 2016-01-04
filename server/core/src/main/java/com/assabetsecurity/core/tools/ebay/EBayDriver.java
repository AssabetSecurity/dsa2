package com.assabetsecurity.core.tools.ebay;

import com.assabetsecurity.core.tools.ebay.EBayRequest;
/**
 * Created by alyas on 1/3/16.
 */
public class EBayDriver {

    private String categoryId;
    private int page;
    private boolean lastPage;

    public EBayDriver(String categoryId) {
        this.categoryId = categoryId;
        this.lastPage = false;
        this.page = 0;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public String getItems(){
        while (!lastPage){

           //getNextPage

        }

        return "";
    }




}
