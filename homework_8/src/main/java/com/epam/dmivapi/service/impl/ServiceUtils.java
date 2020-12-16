package com.epam.dmivapi.service.impl;

class ServiceUtils {
    public static int calculateNumOfPages(int numOfRows, int recordsPerPage) {
        if (recordsPerPage == 0)
            return numOfRows == 0 ? 0 : 1;

        int nOfPages = numOfRows / recordsPerPage;

        if (numOfRows % recordsPerPage > 0)
            nOfPages++;

        return nOfPages;
    }
}
