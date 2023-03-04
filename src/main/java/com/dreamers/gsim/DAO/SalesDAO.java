package com.dreamers.gsim.DAO;



import model.SalesBook;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface SalesDAO {
    public List<SalesBook> getBestSellers(LocalDate startDate, LocalDate endDate);


}
