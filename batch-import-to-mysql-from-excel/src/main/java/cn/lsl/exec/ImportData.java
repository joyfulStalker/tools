package cn.lsl.exec;

import cn.lsl.utils.ConnectionUtil;
import cn.lsl.utils.ExcelUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImportData {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            Long startTime = System.currentTimeMillis();

            String fileName = "/user.xlsx";
            InputStream in = ImportData.class.getResourceAsStream(fileName);
            Workbook workBook = ExcelUtil.getWorkbook(fileName, in);

            // 设置连接信息
            conn = ConnectionUtil.getConn();
            String sql = "INSERT INTO batchImportFromExcel(uname,uphone,uaddress) VALUES(?,?,?)";
            pstm = conn.prepareStatement(sql);
            conn.setAutoCommit(false);// 关闭自动提交

            //////////////////// --------------------业务处理区--------------------------------------
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(0);
            int rowNum = sheet.getLastRowNum();

            for (int i = 1; i < rowNum; i++) {
                Row row = sheet.getRow(i);
                row.getCell(0).setCellType(CellType.STRING);
                row.getCell(1).setCellType(CellType.STRING);
                row.getCell(2).setCellType(CellType.STRING);
                String uname = row.getCell(0).getStringCellValue();
                String uphone = row.getCell(1).getStringCellValue();
                String uaddress = row.getCell(2).getStringCellValue();
                // ..........

                pstm.setString(1, uname);
                pstm.setString(2, uphone);
                pstm.setString(3, uaddress);

                pstm.addBatch();
                if (i % 5000 == 0) {// 设置多久执行一次批操作
                    pstm.executeBatch();
                }
            }

            //////////////////// --------------------业务处理区--------------------------------------

            conn.commit();
            Long endTime = System.currentTimeMillis();
            System.out.println("OK,用时：" + (endTime - startTime));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
