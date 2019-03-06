package com.borinskym.stock.simulator.date;

import lombok.Builder;
import lombok.Data;

import static java.lang.Integer.parseInt;

@Builder
@Data
public class SparseDate implements Comparable<SparseDate> {
    private int year;
    private int month;

    public static SparseDate from(int year, int month){
        return SparseDate.builder().year(year).month(month).build();
    }

    public static SparseDate parse(String s){
        String[] split = s.split("/");
        return SparseDate.builder()
                .year(parseInt(split[1]))
                .month(parseInt(split[0]))
                .build();
    }

    @Override
    public int compareTo(SparseDate o) {
        int diffYears = year - o.getYear();
        return diffYears == 0? month - o.getMonth(): diffYears;
    }
}
