package com.borinskym.stock.simulator.date;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SparseDate implements Comparable<SparseDate> {
    private int year;
    private int month;

    public static SparseDate from(int year, int month){
        return SparseDate.builder().year(year).month(month).build();
    }

    @Override
    public int compareTo(SparseDate o) {
        int diffYears = year - o.getYear();
        return diffYears == 0? month - o.getMonth(): diffYears;
    }
}
