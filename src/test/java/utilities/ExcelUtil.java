package utilities;

import org.apache.poi.ss.usermodel.*;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *This is a reUsable method to use test data In Excel sheets to the test framework
 * It accepts the path of the file and the name of the sheet for the test
**/
public class ExcelUtil {

    private Workbook workBook;
    private Sheet workSheet;
    private String path;


    public ExcelUtil(String path, String sheetName) {//This Constructor is to open and access the excel file
        this.path = path;
        try {
            // We are opening the Excel File
            FileInputStream fileInputStream = new FileInputStream(path);
            // To access the workbook
            workBook = WorkbookFactory.create(fileInputStream);
            // To access the sheet
            workSheet = workBook.getSheet(sheetName);
            //We check if the file is empty
            Assert.assertNotNull(workSheet, "Worksheet: \"" + sheetName + "\" was not found\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //-------We count the number of columns active in the first rox where index of Row is 0----------
    public int columnCount() {

        //We assume the names of the fields are in the first row to read
        return workSheet.getRow(0).getLastCellNum();
    }

    /*----------We are getting the rowNumber.Index starts at 0 -----------*/
    public int rowCount() {
        return  workSheet.getLastRowNum()+1; }      //We have to add 1 to the index starting with 0

    /*----------to get the data at (rowNum,colNum) -----------*/
    public String getCellData(int rowNum, int colNum) {
        Cell cell;
        try {
            cell = workSheet.getRow(rowNum).getCell(colNum);
            String cellData = cell.toString();
            return cellData;
        } catch (Exception e) {
            return "";
//            throw new RuntimeException(e);
        }
    }

    /* ------------- We are getting all the data to a 2 dimensional array ---------*/
    public String[][] getDataArray() {
        String[][] data = new String[rowCount()][columnCount()];
        for (int i = 0; i < rowCount(); i++) {
            for (int j = 0; j < columnCount(); j++) {
                String value = getCellData(i, j);
                data[i][j] = value;
            }
        }
        return data;
    }

    //This will get the list of the data in the excel file
    //This is a list of map. This takes the data as string and will return the data as a Map of String
    public List<Map<String, String>> getDataList() {

        // We first get Column Names for the keys of the Map
        List<String> columns = getColumnsNames();

        // We will register data as a Map so we initiliaze the final variable
        List<Map<String, String>> data = new ArrayList<>();

        for (int i = 1; i < rowCount(); i++) {
            // we get each active row
            Row row = workSheet.getRow(i);
            // creating map of the row using the column and value
            // key=columnName, value=cellData
            Map<String, String> rowMap = new HashMap<String, String>();
            for (Cell cell : row) {
                int columnIndex = cell.getColumnIndex();
                rowMap.put(columns.get(columnIndex), cell.toString());
            }
            data.add(rowMap);
        }

        return data;
    }
    /*-- We read each data on the 1st row which we assume the names of the datas are registered in the first column --------*/
    public List<String> getColumnsNames() {
        List<String> columns = new ArrayList<>();
        for (Cell cell : workSheet.getRow(0)) {
            columns.add(cell.toString());
        }
        return columns;
    }

    // ----------------- Will set the value of the cell when data,row and column number entered --------------//
    public void setCellData(String value, int rowNum, int colNum) {
        Cell cell;
        Row row;
        try {
            row = workSheet.getRow(rowNum);
            cell = row.getCell(colNum);
            if (cell == null) {//if there is no value, create a cell.
                cell = row.createCell(colNum);
                cell.setCellValue(value);
            } else {
                cell.setCellValue(value);
            }
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            workBook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCellData(String value, String columnName, int row) {
        int column = getColumnsNames().indexOf(columnName);
        setCellData(value, row, column);
    }

}

