package zandbak;

import java.util.Date;

public class ExcelDateConversion {

  static Date convertExcelDate(double excelDateValue) {
    // Convert Excel numeric date value to Java Date object
    long excelEpochInMillis = (long) ((excelDateValue - 25569) * 86400000);
    Date javaDate = new Date(excelEpochInMillis);
    return javaDate;
  }

  public static void main(String[] args) {
    double excelDate = 44887.0;
    Date dt = convertExcelDate(excelDate);
    System.out.println("Date : " + dt.toString());

  }
}
