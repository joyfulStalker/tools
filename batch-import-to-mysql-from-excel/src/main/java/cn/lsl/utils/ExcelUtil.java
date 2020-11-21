package cn.lsl.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	private static final String EXCEL_XLS = "xls";

	private static final String EXCEL_XLSX = "xlsx";

	public static Workbook getWorkbook(String fileName, InputStream in) throws IOException {

		Workbook wb = null;
		// FileInputStream in = new FileInputStream(file);
		if (fileName.endsWith(EXCEL_XLS)) { // Excel&nbsp;2003
			wb = new HSSFWorkbook(in);
		} else if (fileName.endsWith(EXCEL_XLSX)) { // Excel 2007/2010
			wb = new XSSFWorkbook(in);
		}
		return wb;
	}

}
